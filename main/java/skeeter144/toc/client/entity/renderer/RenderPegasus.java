package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.mount.flying.EntityPegasus;
import skeeter144.toc.util.Reference;

public class RenderPegasus extends RenderLiving<EntityPegasus>{
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/pegasus_white.png");
	
	public RenderPegasus(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPegasus entity) {
		return texture;
	}

}
