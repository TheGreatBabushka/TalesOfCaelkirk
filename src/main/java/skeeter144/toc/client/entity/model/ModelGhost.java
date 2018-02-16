package skeeter144.toc.client.entity.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.entity.Entity;
import skeeter144.toc.entity.mob.monster.EntityGhost;

/**
 * Ghost - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelGhost extends AdvancedModelBase {
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer left_arm;
    public AdvancedModelRenderer left_arm_1;

    public ModelGhost() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.left_arm_1 = new AdvancedModelRenderer(this, 44, 0);
        this.left_arm_1.setRotationPoint(-11.0F, -8.0F, -3.0F);
        this.left_arm_1.addBox(0.0F, 0.0F, 0.0F, 5, 15, 5, 0.0F);
        this.left_arm = new AdvancedModelRenderer(this, 44, 0);
        this.left_arm.setRotationPoint(5.0F, -8.0F, -3.0F);
        this.left_arm.addBox(0.0F, 0.0F, 0.0F, 5, 15, 5, 0.0F);
        this.head = new AdvancedModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -13.7F, -0.8F);
        this.head.addBox(-4.5F, -4.0F, -4.0F, 9, 8, 8, 0.0F);
        this.body = new AdvancedModelRenderer(this, 0, 33);
        this.body.setRotationPoint(0.0F, 3.2F, 0.0F);
        this.body.addBox(-6.0F, -9.5F, -4.0F, 12, 23, 8, 0.0F);
        this.body.addChild(this.left_arm_1);
        this.body.addChild(this.left_arm);
        this.body.addChild(this.head);
        updateDefaultPose();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor, Entity entityIn) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	
    	resetToDefaultPose();
    	
    	EntityGhost ghost = (EntityGhost)entityIn;
    	if(ghost.getAttackTarget() == null) {
	    	float f = entityIn.ticksExisted;
	        float f1 = 1;
	    	float speed = .2f;
	        float degree =  1.5f;
	    	
	        this.walk(left_arm, speed, degree / 10, false, 0, 0, f, f1);
	        this.walk(left_arm_1, speed, degree / 10, true, 0, 0, f, f1);
	        
	        bob(body, speed, 3, false, f, f1);
    	}
    }
    
    public void setRotateAngle(AdvancedModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}