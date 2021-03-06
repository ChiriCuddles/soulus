package yuudaari.soulus.common.registration;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import yuudaari.soulus.Soulus;
import yuudaari.soulus.common.config.item.ConfigFood;
import yuudaari.soulus.common.util.ModPotionEffect;

public class Registration {

	public static class Block extends net.minecraft.block.Block implements IBlockRegistration<Block> {

		public Block (final String name, final Material material) {
			super(material);
			setName(name);
			initialize();
		}

		@Override
		public Block getBlock () {
			return this;
		}

		@Override
		public Block setHasItem () {
			return (Block) IBlockRegistration.super.setHasItem();
		}

		@Override
		public Block setHasDescription () {
			return (Block) IBlockRegistration.super.setHasDescription();
		}

		@SuppressWarnings("deprecation")
		@Override
		public BlockRenderLayer getBlockLayer () {
			final Material material = getMaterial(null);
			if (material.isOpaque())
				return BlockRenderLayer.SOLID;
			else if (material.blocksLight())
				return BlockRenderLayer.TRANSLUCENT;
			else
				return BlockRenderLayer.CUTOUT;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean isOpaqueCube (final IBlockState state) {
			final Material material = getMaterial(null);
			return material.isOpaque() && material.blocksLight();
		}

		////////////////////////////////////
		// Rarity
		//

		private EnumRarity rarity = null;

		public Block setRarity (final EnumRarity rarity) {
			this.rarity = rarity;
			return this;
		}

		@Override
		public EnumRarity getRarity (final ItemStack stack) {
			return rarity == null ? IBlockRegistration.super.getRarity(stack) : rarity;
		}

		@Override
		public Block setHardness (float hardness) {
			super.setHardness(hardness);
			return this;
		}

		@Override
		public Block setResistance (float resistance) {
			super.setResistance(resistance);
			return this;
		}
	}

	public static class BlockPillar extends net.minecraft.block.BlockRotatedPillar implements IBlockRegistration<BlockPillar> {

		public BlockPillar (final String name, final Material material) {
			super(material);
			setName(name);
			initialize();
		}

		@Override
		public BlockPillar getBlock () {
			return this;
		}

		@Override
		public BlockPillar setHasItem () {
			return (BlockPillar) IBlockRegistration.super.setHasItem();
		}

		@Override
		public BlockPillar setHasDescription () {
			return (BlockPillar) IBlockRegistration.super.setHasDescription();
		}

		@SuppressWarnings("deprecation")
		@Override
		public BlockRenderLayer getBlockLayer () {
			final Material material = getMaterial(null);
			if (material.isOpaque())
				return BlockRenderLayer.SOLID;
			else if (material.blocksLight())
				return BlockRenderLayer.TRANSLUCENT;
			else
				return BlockRenderLayer.CUTOUT;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean isOpaqueCube (final IBlockState state) {
			final Material material = getMaterial(null);
			return material.isOpaque() && material.blocksLight();
		}

		////////////////////////////////////
		// Rarity
		//

		private EnumRarity rarity = null;

		public BlockPillar setRarity (final EnumRarity rarity) {
			this.rarity = rarity;
			return this;
		}

		@Override
		public EnumRarity getRarity (final ItemStack stack) {
			return rarity == null ? IBlockRegistration.super.getRarity(stack) : rarity;
		}

		@Override
		public BlockPillar setHardness (float hardness) {
			super.setHardness(hardness);
			return this;
		}

		@Override
		public BlockPillar setResistance (float resistance) {
			super.setResistance(resistance);
			return this;
		}
	}

	public static class BlockPane extends net.minecraft.block.BlockPane implements IBlockRegistration<BlockPane> {

		public BlockPane (final String name, final Material material) {
			super(material, true);
			setName(name);
			initialize();
		}

		@Override
		public BlockPane getBlock () {
			return this;
		}

		@Override
		public BlockPane setHasItem () {
			return (BlockPane) IBlockRegistration.super.setHasItem();
		}

		@Override
		public BlockPane setHardness (float hardness) {
			super.setHardness(hardness);
			return this;
		}

		@Override
		public BlockPane setResistance (float resistance) {
			super.setResistance(resistance);
			return this;
		}

		public BlockPane setTool (Tool tool, int level) {
			setHarvestLevel(tool.id, level);
			return this;
		}
	}

	public static class ItemBlock extends net.minecraft.item.ItemBlock implements IItemRegistration {

		public ItemBlock (final IBlockRegistration<?> block) {
			super(block.getBlock());
			setRegistryName(block.getRegistryName());
			initialize();
		}

		@Override
		public ItemBlock setHasSubtypes (final boolean hasSubtypes) {
			return (ItemBlock) super.setHasSubtypes(hasSubtypes);
		}

		@Override
		public EnumRarity getRarity (final ItemStack stack) {
			if (getBlock() instanceof IBlockRegistration) {
				final IBlockRegistration<?> block = (IBlockRegistration<?>) getBlock();
				final EnumRarity rarity = block.getRarity(stack);
				if (rarity != null)
					return rarity;
			}

			return super.getRarity(stack);
		}
	}

	public static class ItemMultiTexture extends net.minecraft.item.ItemMultiTexture implements IItemRegistration {

		public ItemMultiTexture (final Block block, final ItemMultiTexture.Mapper mapper) {
			super(block, block, mapper);
			setRegistryName(block.getRegistryName());
			initialize();
		}

		@Override
		public ItemMultiTexture setHasSubtypes (boolean hasSubtypes) {
			return (ItemMultiTexture) super.setHasSubtypes(hasSubtypes);
		}
	}

	public static class Item extends net.minecraft.item.Item implements IItemRegistration {

		public Item (final String name) {
			super();
			setName(name);
			initialize();
		}

		////////////////////////////////////
		// Glint
		//

		private Boolean glint = false;

		public Item setHasGlint () {
			glint = true;
			return this;
		}

		@Override
		public boolean hasEffect (final ItemStack stack) {
			return glint;
		}

		////////////////////////////////////
		// Rarity
		//

		private EnumRarity rarity = null;

		public Item setRarity (final EnumRarity rarity) {
			this.rarity = rarity;
			return this;
		}

		@Override
		public EnumRarity getRarity (final ItemStack stack) {
			return rarity == null ? super.getRarity(stack) : rarity;
		}

		////////////////////////////////////
		// Burn Time
		//

		private int burnTime = 0;

		@Override
		public int getItemBurnTime (final ItemStack itemStack) {
			return burnTime;
		}

		public Item setBurnTime (final int burnTime) {
			this.burnTime = burnTime;
			return this;
		}

		////////////////////////////////////
		// Overrides to return the same class
		//

		@Override
		public Item setHasDescription () {
			return (Item) IItemRegistration.super.setHasDescription();
		}

		@Override
		public Item addOreDict (final String... dictionaries) {
			return (Item) IItemRegistration.super.addOreDict(dictionaries);
		}

		@Override
		public Item setMaxStackSize (final int maxStackSize) {
			return (Item) super.setMaxStackSize(maxStackSize);
		}
	}

	public static class ItemFood extends net.minecraft.item.ItemFood implements IItemRegistration {

		protected Boolean glint = false;
		private IFoodConfigGetter foodConfigGetter = null;

		public static interface IFoodConfigGetter {

			public ConfigFood get ();
		}

		public ItemFood (final String name, final IFoodConfigGetter configGetter) {
			super(0, false);
			setName(name);
			foodConfigGetter = configGetter;

			Soulus.onConfigReload(this::onConfigReload);
			initialize();
		}

		private void onConfigReload () {
			final ConfigFood config = foodConfigGetter.get();
			alwaysEdible = config.isAlwaysEdible();
		}

		@Override
		public int getHealAmount (final ItemStack stack) {
			return foodConfigGetter.get().getAmount();
		}

		@Override
		public float getSaturationModifier (final ItemStack stack) {
			return foodConfigGetter.get().getSaturation();
		}

		@Override
		public ItemStack onItemUseFinish (final ItemStack stack, final World world, final EntityLivingBase entity) {
			super.onItemUseFinish(stack, world, entity);

			for (ModPotionEffect effect : foodConfigGetter.get().getEffects())
				effect.apply(entity);

			stack.shrink(foodConfigGetter.get().getQuantity() - 1);
			return stack;
		}

		@Override
		public int getMaxItemUseDuration (final ItemStack stack) {
			return foodConfigGetter.get().getDuration();
		}
	}
}
