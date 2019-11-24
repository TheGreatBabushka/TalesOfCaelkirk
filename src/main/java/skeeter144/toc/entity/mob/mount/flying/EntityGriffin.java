package skeeter144.toc.entity.mob.mount.flying;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;

public class EntityGriffin extends EntityAbstractFlyingMount{

	public EntityGriffin(World worldin) {
		this(TOCEntityType.GRIFFIN, worldin);
	}
	
	public EntityGriffin(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn) {
		this(type, worldIn, null);
	}

	
	public EntityGriffin(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn, UUID uuid) {
		super(type, worldIn, uuid);
	}

}
