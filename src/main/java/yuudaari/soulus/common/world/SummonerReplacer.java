package yuudaari.soulus.common.world;

import java.util.ArrayList;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import yuudaari.soulus.Soulus;
import yuudaari.soulus.common.block.summoner.Summoner;
import yuudaari.soulus.common.block.summoner.Summoner.Upgrade;
import yuudaari.soulus.common.block.summoner.SummonerTileEntity;
import yuudaari.soulus.common.config.ConfigInjected;
import yuudaari.soulus.common.config.ConfigInjected.Inject;
import yuudaari.soulus.common.config.world.summoner_replacement.ConfigReplacement;
import yuudaari.soulus.common.config.world.summoner_replacement.ConfigStructure;
import yuudaari.soulus.common.config.world.summoner_replacement.ConfigSummonerReplacement;
import yuudaari.soulus.common.registration.BlockRegistry;
import yuudaari.soulus.common.util.GeneratorName;
import yuudaari.soulus.common.util.Logger;

@Mod.EventBusSubscriber
@ConfigInjected(Soulus.MODID)
public class SummonerReplacer {

	@Inject public static ConfigSummonerReplacement CONFIG;

	private static long lastReplacementTime = 0;

	@SubscribeEvent
	public static void onTick (TickEvent.PlayerTickEvent event) {
		final long now = System.currentTimeMillis();
		if (now - lastReplacementTime > 10000) {
			lastReplacementTime = now;
			final int chunkx = (int) (event.player.posX) >> 4;
			final int chunkz = (int) (event.player.posZ) >> 4;
			for (int offsetx = -3; offsetx < 3; offsetx++) {
				for (int offsetz = -3; offsetz < 3; offsetz++) {
					replaceSummonersInChunk(event.player.world, chunkx + offsetx, chunkz + offsetz, null);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void populateChunkPost (PopulateChunkEvent.Post event) {
		replaceSummonersInChunk(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getGenerator());
	}

	private static void replaceSummonersInChunk (World world, int x, int z, IChunkGenerator generator) {
		Chunk chunk = world.getChunkFromChunkCoords(x, z);

		Map<BlockPos, TileEntity> teMap = chunk.getTileEntityMap();
		if (teMap.size() == 0)
			return;

		ConfigStructure defaultStructureConfig = CONFIG.structures.get("*");
		if (defaultStructureConfig == null)
			return;

		for (TileEntity te : new ArrayList<>(teMap.values())) {
			Block block = te.getBlockType();
			// Logger.info("found a tile entity " + block.getRegistryName());
			if (block == Blocks.MOB_SPAWNER) {
				BlockPos pos = te.getPos();
				ConfigStructure structureConfig = defaultStructureConfig;
				for (Map.Entry<String, ConfigStructure> structureConfigEntry : CONFIG.structures
					.entrySet()) {
					if (generator != null && generator.isInsideStructure(world, GeneratorName.get(structureConfigEntry.getKey()), pos)) {
						structureConfig = structureConfigEntry.getValue();
					}
				}

				String entityType = getTheIdFromAStupidMobSpawnerTileEntity(te);
				// Logger.info("found a " + entityType + " spawner at " + pos.getX() + "," + pos.getY() + "," + pos.getZ());

				ConfigReplacement replacement = structureConfig.replacementsByCreature.get(entityType);
				if (replacement == null) {
					replacement = structureConfig.replacementsByCreature
						.get(new ResourceLocation(entityType).getResourceDomain() + ":*");
					if (replacement == null) {
						replacement = structureConfig.replacementsByCreature.get("*");
						if (replacement == null) {
							// this spawner isn't configured to be replaced
							// Logger.info("not configured to be replaced");
							return;
						}
					}
				}

				// Logger.info("replacement type " + replacement.type.getName());

				world.setBlockState(pos, BlockRegistry.SUMMONER.getDefaultState()
					.withProperty(Summoner.VARIANT, replacement.type)
					.withProperty(Summoner.HAS_SOULBOOK, replacement.midnightJewel), 7);

				if (!replacement.midnightJewel) return;

				TileEntity nte = world.getTileEntity(pos);
				if (nte == null || !(nte instanceof SummonerTileEntity)) {
					Logger.warn("Unable to insert midnight jewel into replaced summoner");
					return;
				}

				SummonerTileEntity ste = (SummonerTileEntity) nte;
				ste.setEssenceType(entityType);
				ste.upgrades.put(Upgrade.CRYSTAL_DARK, 1);
			}
		}
	}

	public static String getTheIdFromAStupidMobSpawnerTileEntity (TileEntity te) {
		if (!(te instanceof TileEntityMobSpawner))
			return null;

		TileEntityMobSpawner mste = (TileEntityMobSpawner) te;
		NBTTagCompound nbt = new NBTTagCompound();
		mste.writeToNBT(nbt);

		NBTTagList taglist = nbt.getTagList("SpawnPotentials", 10);
		NBTBase firstSpawnPotential = taglist.get(0);
		if (!(firstSpawnPotential instanceof NBTTagCompound))
			return null;

		NBTTagCompound firstSpawnPotentialTheRealOne = (NBTTagCompound) firstSpawnPotential;
		NBTTagCompound theActualEntityOhMyGod = firstSpawnPotentialTheRealOne.getCompoundTag("Entity");
		return theActualEntityOhMyGod.getString("id");
	}
}
