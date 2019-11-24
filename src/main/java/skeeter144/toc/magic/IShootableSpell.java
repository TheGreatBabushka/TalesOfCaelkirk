package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public interface IShootableSpell {
	
	default void launchProjectile(Entity caster, int spellId) {
		World worldIn = caster.world;
		PlayerEntity playerIn = (PlayerEntity)caster;
		
		EntityWandProjectile spell = new EntityWandProjectile(null, worldIn, playerIn, spellId, null);
		spell.shoot(caster, caster.rotationPitch, caster.rotationYaw, 0, 2.0f, 0);
		caster.world.addEntity(spell);	
	}

	//called when the EntityWandProjectile hits something. The projectile is self-disposing
	public void onProjectileImpact(RayTraceResult res,  EntityWandProjectile proj);
	public int getSpellTrailId();
}
