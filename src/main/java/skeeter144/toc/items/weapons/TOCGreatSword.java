package skeeter144.toc.items.weapons;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.Util;

public class TOCGreatSword extends SwordItem implements ISpecialAttackWeapon{
	String name;
	float damage, speed;
	boolean hasSpecial = false;
	public TOCGreatSword(IItemTier tier, Item.Properties builder, String name, float damage, float speed, boolean hasSpecial) {
		super(tier, (int) damage, speed, builder);
		this.damage = damage;
		this.speed = speed;
		this.hasSpecial = hasSpecial;
		setRegistryName(Reference.MODID, name);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		Multimap<String, AttributeModifier> map = HashMultimap.<String, AttributeModifier>create();
		 if (equipmentSlot == EquipmentSlotType.MAINHAND)
	     {
			 map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, AttributeModifier.Operation.ADDITION));
			 map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speed, AttributeModifier.Operation.ADDITION));
	     }
		
		return map;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if(hand == Hand.OFF_HAND || !hasSpecial)
			 return new ActionResult<ItemStack>(ActionResultType.FAIL, player.getHeldItem(hand));
		
		return new ActionResult<ItemStack>(fireSpecialAttack(player, hand, null, 20, false) ? ActionResultType.SUCCESS : ActionResultType.FAIL, 
				player.getHeldItem(hand));
	}


	@Override
	public void doAttack(PlayerEntity attacker, Hand hand, Entity attacked) {
		attacker.swingArm(hand);
		attacker.spawnSweepParticles();
		
		if(!attacker.world.isRemote) {
			List<Entity> entities = attacker.world.getEntitiesWithinAABBExcludingEntity(attacker, 
												new AxisAlignedBB(attacker.posX- 5, attacker.posY - 5, attacker.posZ - 5, 
																  attacker.posX + 5, attacker.posY + 5, attacker.posZ + 5));
			for(Entity e : entities) {
				boolean hit = Util.isEntityWithinViewCone(e, attacker, -45, 45);
				
				if(hit) {
					e.attackEntityFrom(new TOCDamageSource(DamageType.PHYSICAL, attacker), this.damage);
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Base Damage: " + (int)(this.damage * CombatManager.LOWER_DAMAGE_PCT) + " - " + (int)this.damage));
	}
}
