package yuudaari.soulus.common.item;

import net.minecraft.item.crafting.Ingredient;
import yuudaari.soulus.common.compat.jei.JeiDescriptionRegistry;

public class BoneChunkEnder extends BoneChunk {

	public BoneChunkEnder () {
		super("bone_chunk_ender");
		setHasGlint();
		removeOreDict("boneChunk");
	}

	@Override
	public void onRegisterDescription (final JeiDescriptionRegistry registry) {
		registry.add(Ingredient.fromItem(this), getRegistryName());
	}
}
