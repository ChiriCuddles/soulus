package com.kallgirl.souls.common;

import com.kallgirl.souls.common.block.*;
import com.kallgirl.souls.common.item.*;
import com.kallgirl.souls.common.world.FossilGenerator;
import net.minecraftforge.common.MinecraftForge;

import java.util.Hashtable;

public final class ModObjects {

	static Hashtable<String, IModObject> objects;

	public static void preinit() {
		objects = new Hashtable<>();

		// items
		objects.put("essence", new Essence());
		objects.put("vibratingBone", new VibratingBone());
		objects.put("vibratingBoneChunk", new VibratingBoneChunk());
		objects.put("soulbook", new Soulbook());
		objects.put("sledgehammer", new Sledgehammer());
		objects.put("boneChunk", new BoneChunk());
		objects.put("darkBone", new DarkBone());
		objects.put("darkBoneChunk", new DarkBoneChunk());
		objects.put("darkmeal", new Darkmeal());
		objects.put("enderDust", new EnderDust());
		objects.put("ironDust", new IronDust());
		objects.put("enderIronDust", new EnderIronDust());
		objects.put("endersteel", new Endersteel());

		// blocks
		objects.put("endersteelBlock", new EndersteelBlock());
		objects.put("endersteelBars", new EndersteelBars());
		objects.put("blankSpawner", new BlankSpawner());
		objects.put("dirtFossil", new DirtFossil());
		objects.put("dirtFossilVibrating", new DirtFossilVibrating());

		// generation
		objects.put("fossilGeneration", new FossilGenerator());

		objects.forEach((name, item) -> {
			item.preinit();
			MinecraftForge.EVENT_BUS.register(item);
		});
	}

	public static void init() {
		objects.forEach((name, item) -> {
			item.init();
		});
	}

	public static void postinit() {
		objects.forEach((name, item) -> item.postinit());
	}

	public static IModItem get(String name) {
		IModObject result = objects.get(name);
		if (result instanceof IModItem) return (IModItem)result;
		else throw new IllegalArgumentException(String.format("'%s' is not a valid item or block", name));
	}
	public static Item getItem(String name) {
		IModObject item = objects.get(name);
		if (item instanceof Item) return (Item)item;
		else throw new IllegalArgumentException(String.format("'%s' is not a valid item", name));
	}
	public static Block getBlock(String name) {
		IModObject block = objects.get(name);
		if (block instanceof Block) return (Block)block;
		else throw new IllegalArgumentException(String.format("'%s' is not a valid block", name));
	}
}