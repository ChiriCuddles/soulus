package yuudaari.soulus.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yuudaari.soulus.Soulus;
import yuudaari.soulus.common.config.ConfigInjected;
import yuudaari.soulus.common.config.ConfigInjected.Inject;
import yuudaari.soulus.common.config.item.ConfigGearOscillating;
import yuudaari.soulus.common.registration.Registration;
import yuudaari.soulus.common.util.XP;

@ConfigInjected(Soulus.MODID)
public class GearOscillating extends Registration.Item {

	@Inject public static ConfigGearOscillating CONFIG;

	public GearOscillating () {
		super("gear_oscillating");
		setHasGlint();
		setHasDescription();

		Soulus.onConfigReload( () -> setMaxStackSize(CONFIG.stackSize));
	}

	@Override
	public void onCreated (final ItemStack stack, final World world, final EntityPlayer player) {
		if (player == null)
			return;

		for (int i = 0; i < stack.getCount(); i++)
			XP.grant(player, CONFIG.xp.getInt(world.rand));
	}
}
