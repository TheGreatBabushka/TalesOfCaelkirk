package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class RenderGoblin<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M>{
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/goblin.png");
	
	public RenderGoblin(EntityRendererManager rendermanagerIn, M ModelIn, float shadowsizeIn) {
		super(rendermanagerIn, ModelIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return texture;
	}

}
