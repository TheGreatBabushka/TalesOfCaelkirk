 package skeeter144.toc.items;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.armor.ArmorMaterialList;
import skeeter144.toc.items.armor.CustomArmor;
import skeeter144.toc.items.consumable.ItemHealingPotion;
import skeeter144.toc.items.consumable.ItemManaPotion;
import skeeter144.toc.items.magic.BasicWand;
import skeeter144.toc.items.misc.ItemGlassVial;
import skeeter144.toc.items.quest.QuestItem;
import skeeter144.toc.items.tools.BlacksmithHammer;
import skeeter144.toc.items.tools.TOCAxe;
import skeeter144.toc.items.tools.TOCPickaxe;
import skeeter144.toc.items.tools.TOCShears;
import skeeter144.toc.items.weapons.TOCArrow;
import skeeter144.toc.items.weapons.TOCBow;
import skeeter144.toc.items.weapons.TOCGreatAxe;
import skeeter144.toc.items.weapons.TOCGreatSword;
import skeeter144.toc.items.weapons.TOCSword;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.util.Reference;

public class TOCItems {
	public static final ItemGroup MOD_GROUP = new ItemGroup(Reference.MODID) {
		public ItemStack createIcon() {return new ItemStack(Items.CREEPER_HEAD);}
	};
	
	
	public static void registerAllItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		try {
			for (Field f : TOCItems.class.getFields()) {
				if (f.get(null) instanceof Item) {
					registry.register((Item) f.get(null));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// We need to go over the entire registry so that we include any potential Registry Overrides
		for (final Block block : ForgeRegistries.BLOCKS.getValues()) {

			final ResourceLocation blockRegistryName = block.getRegistryName();
			Preconditions.checkNotNull(blockRegistryName, "Registry Name of Block \"" + block + "\" is null! This is not allowed!");

			// Check that the blocks is from our mod, if not, continue to the next block
			if (!blockRegistryName.getNamespace().equals(Reference.MODID)) {
				continue;
			}

			// If you have blocks that don't have a corresponding ItemBlock, uncomment this code and create an Interface - or even better an Annotation - called NoAutomaticItemBlock with no methods and implement it on your blocks that shouldn't have ItemBlocks
//			if (!(block instanceof NoAutomaticItemBlock)) {
//				continue;
//			}

			// Make the properties, and make it so that the item will be on our ItemGroup (CreativeTab)
			final Item.Properties properties = new Item.Properties().group(MOD_GROUP).group(TOCItems.MOD_GROUP);
			// Create the new ItemBlock with the block and it's properties
			final ItemBlock itemBlock = new ItemBlock(block, properties);
			// Setup the new ItemBlock with the block's registry name and register it
			registry.register(setup(itemBlock, blockRegistryName));
		}
		TOCMain.LOGGER.debug("Registered Items");
	}
	
//TODO
//	public static CreativeTabs quest_items_tab = new CreativeTabs("quest_items") {
//		public ItemStack getTabIconItem() {
//			return new ItemStack(TOCItems.rat_tail);
//		}
//	};
//
//	public static CreativeTabs resources_tab = new CreativeTabs("resources") {
//		public ItemStack getTabIconItem() {
//			return new ItemStack(TOCItems.ingot_steel);
//		}
//	};

//	public static ToolMaterial BRONZE = EnumHelper.addToolMaterial("BRONZE", 1, 100, 2, 3, 0);
//	public static ToolMaterial IRON = EnumHelper.addToolMaterial("IRON", 2, 100, 5, 5, 0);
//	public static ToolMaterial STEEL = EnumHelper.addToolMaterial("STEEL", 3, 100, 8, 8, 0);
//	public static ToolMaterial MITHRIL = EnumHelper.addToolMaterial("MITHRIL", 100, 100, 16, 12, 0);
//	public static ToolMaterial ADAMANTITE = EnumHelper.addToolMaterial("ADAMANTITE", 4, 100, 24, 17, 0);
//	public static ToolMaterial RUNITE = EnumHelper.addToolMaterial("RUNITE", 5, 100, 34, 23, 0);
//	public static ToolMaterial DRAGONSTONE = EnumHelper.addToolMaterial("DRAGONSTONE", 6, 100, 44, 30, 0);
//
//	public static ArmorMaterial VIKING_STEEL_ARMOR_HELM = EnumHelper.addArmorMaterial("viking_helm", "toc:viking_helm", 1, new int[] { 2, 7, 5, 4 }, 0, null, 1);
//	public static ArmorMaterial VIKING_STEEL_ARMOR = EnumHelper.addArmorMaterial("viking", "toc:viking", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);
//	public static ArmorMaterial BRONZE_ARMOR = EnumHelper.addArmorMaterial("bronze", "toc:bronze", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);
//	public static ArmorMaterial IRON_ARMOR = EnumHelper.addArmorMaterial("iron", "toc:iron", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);
//	public static ArmorMaterial STEEL_ARMOR = EnumHelper.addArmorMaterial("steel", "toc:steel", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);
//	public static ArmorMaterial MITHRIL_ARMOR = EnumHelper.addArmorMaterial("mithril", "toc:mithril", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);
//	public static ArmorMaterial ADAMANTITE_ARMOR = EnumHelper.addArmorMaterial("adamantite", "toc:adamantite", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);
//	public static ArmorMaterial RUNITE_ARMOR = EnumHelper.addArmorMaterial("runite", "toc:runite", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);
//	public static ArmorMaterial DRAGONSTONE_ARMOR = EnumHelper.addArmorMaterial("dragonstone", "toc:dragonstone", 1, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1);

	//public static Item SHEARS = new CustomItem(new Item.Properties().group(MOD_GROUP), "shears", 1);
	
	public static Item ore_copper = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_copper", 1);
	public static Item ore_tin = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_tin", 1);
	public static Item ore_iron = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_iron", 1);
	public static Item ore_coal = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_coal", 1);
	public static Item ore_gold = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_gold", 1);
	public static Item ore_mithril = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_mithril", 1);
	public static Item ore_adamantite = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_adamantite", 1);
	public static Item ore_runite = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_runite", 1);
	public static Item ore_dragonstone = new CustomItem(new Item.Properties().group(MOD_GROUP), "ore_dragonstone", 1);

	public static Item ingot_bronze = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_bronze", 1);
	public static Item ingot_iron = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_iron", 1);
	public static Item ingot_steel = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_steel", 1);
	public static Item ingot_gold = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_gold", 1);
	public static Item ingot_mithril = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_mithril", 1);
	public static Item ingot_adamantite = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_adamantite", 1);
	public static Item ingot_runite = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_runite", 1);
	public static Item ingot_dragonstone = new CustomItem(new Item.Properties().group(MOD_GROUP), "ingot_dragonstone", 1);

	public static Item glass_vial = new ItemGlassVial(new Item.Properties().group(MOD_GROUP), "glass_vial");
	public static Item potion_healing = new ItemHealingPotion(new Item.Properties().group(MOD_GROUP), "potion_healing", 200, 50f);
	public static Item potion_mana = new ItemManaPotion(new Item.Properties().group(MOD_GROUP), "potion_mana", 200, 70f);
	public static Item wand_basic = new BasicWand(new Item.Properties().group(MOD_GROUP), "wand_basic");

	public static Item copper_coin = new CustomItem(new Item.Properties().group(MOD_GROUP), "coin_copper", 50);
	public static Item silver_coin = new CustomItem(new Item.Properties().group(MOD_GROUP), "coin_silver", 50);
	public static Item gold_coin = new CustomItem(new Item.Properties().group(MOD_GROUP), "coin_gold", 50);

	public static Item axe_bronze = new TOCAxe(ItemTier.IRON, 3, -2.5f, new Item.Properties().group(MOD_GROUP), "axe_bronze");
	public static Item axe_iron = new TOCAxe(ItemTier.IRON, 5, -2.5f, new Item.Properties().group(MOD_GROUP), "axe_iron");
	public static Item axe_steel = new TOCAxe(ItemTier.IRON, 9, -2.5f, new Item.Properties().group(MOD_GROUP), "axe_steel");
	public static Item axe_mithril = new TOCAxe(ItemTier.IRON, 14, -2.5f, new Item.Properties().group(MOD_GROUP), "axe_mithril");
	public static Item axe_adamantite = new TOCAxe(ItemTier.IRON, 20, -2.5f, new Item.Properties().group(MOD_GROUP), "axe_adamantite");
	public static Item axe_runite = new TOCAxe(ItemTier.IRON,  27, -2.5f, new Item.Properties().group(MOD_GROUP), "axe_runite");
	public static Item axe_dragonstone = new TOCAxe(ItemTier.IRON, 35, -2.5f, new Item.Properties().group(MOD_GROUP), "axe_dragonstone");

	public static Item bronze_great_sword = new TOCGreatSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_sword_bronze", 14, -3f, false);
	public static Item iron_great_sword = new TOCGreatSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_sword_iron", 18, -3f, false);
	public static Item steel_great_sword = new TOCGreatSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_sword_steel", 26, -3f, false);
	public static Item mithril_great_sword = new TOCGreatSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_sword_mithril", 36, -3f, false);
	public static Item adamantite_great_sword = new TOCGreatSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_sword_adamantite", 48, -3f, false);
	public static Item runite_great_sword = new TOCGreatSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_sword_runite", 62, -3f, true);
	public static Item dragonstone_great_sword = new TOCGreatSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_sword_dragonstone", 78, -3f, true);

	public static Item bronze_short_sword = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "short_sword_bronze", 7, -1.5f, Levels.ATTACK);
	public static Item iron_short_sword = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "short_sword_iron", 11, -1.5f, Levels.ATTACK);
	public static Item steel_short_sword = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "short_sword_steel", 16, -1.5f, Levels.ATTACK);
	public static Item mithril_short_sword = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "short_sword_mithril", 21, -1.5f, Levels.ATTACK);
	public static Item adamantite_short_sword = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "short_sword_adamantite", 27, -1.5f, Levels.ATTACK);
	public static Item runite_short_sword = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "short_sword_runite", 36, -1.5f, Levels.ATTACK);
	public static Item dragonstone_short_sword = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "short_sword_dragonstone", 47, -1.5f, Levels.ATTACK);

	public static Item bronze_great_axe = new TOCGreatAxe(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_axe_bronze", 14, -3f, false);
	public static Item iron_great_axe = new TOCGreatAxe(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_axe_iron", 18, -3f, false);
	public static Item steel_great_axe = new TOCGreatAxe(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_axe_steel", 26, -3f, false);
	public static Item mithril_great_axe = new TOCGreatAxe(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_axe_mithril", 36, -3f, false);
	public static Item adamantite_great_axe = new TOCGreatAxe(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_axe_adamantite", 48, -3f, false);
	public static Item runite_great_axe = new TOCGreatAxe(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_axe_runite", 62, -3f, true);
	public static Item dragonstone_great_axe = new TOCGreatAxe(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "great_axe_dragonstone", 78, -3f, true);

	public static Item bronze_club = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "club_bronze", 9, -2.5f, Levels.STRENGTH);
	public static Item iron_club = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "club_iron", 13, -2.5f, Levels.STRENGTH);
	public static Item steel_club = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "club_steel", 18, -2.5f, Levels.STRENGTH);
	public static Item mithril_club = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "club_mithril", 23, -2.5f, Levels.STRENGTH);
	public static Item adamantite_club = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "club_adamantite", 29, -2.5f, Levels.STRENGTH);
	public static Item runite_club = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "club_runite", 38, -2.5f, Levels.STRENGTH);
	public static Item dragonstone_club = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "club_dragonstone", 49, -2.5f, Levels.STRENGTH);

	public static Item bronze_warhammer = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "warhammer_bronze", 11, -2.5f, Levels.STRENGTH);
	public static Item iron_warhammer = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "warhammer_iron", 15, -2.5f, Levels.STRENGTH);
	public static Item steel_warhammer = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "warhammer_steel", 20, -2.5f, Levels.STRENGTH);
	public static Item mithril_warhammer = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "warhammer_mithril", 25, -2.5f, Levels.STRENGTH);
	public static Item adamantite_warhammer = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "warhammer_adamantite", 31, -2.5f, Levels.STRENGTH);
	public static Item runite_warhammer = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "warhammer_runite", 40, -2.5f, Levels.STRENGTH);
	public static Item dragonstone_warhammer = new TOCSword(ItemTier.IRON, new Item.Properties().group(MOD_GROUP), "warhammer_dragonstone", 51, -2.5f, Levels.STRENGTH);

	public static Item bronze_pickaxe = new TOCPickaxe(ItemTier.IRON, "pickaxe_bronze", 1, 1, new Item.Properties().group(MOD_GROUP));
	public static Item iron_pickaxe = new TOCPickaxe(ItemTier.IRON, "pickaxe_iron", 1, 1, new Item.Properties().group(MOD_GROUP));
	public static Item steel_pickaxe = new TOCPickaxe(ItemTier.IRON, "pickaxe_steel",  1, 1, new Item.Properties().group(MOD_GROUP));
	public static Item mithril_pickaxe = new TOCPickaxe(ItemTier.IRON, "pickaxe_mithril", 1, 1, new Item.Properties().group(MOD_GROUP));
	public static Item adamantite_pickaxe = new TOCPickaxe(ItemTier.IRON, "pickaxe_adamantite", 1, 1, new Item.Properties().group(MOD_GROUP));
	public static Item runite_pickaxe = new TOCPickaxe(ItemTier.IRON, "pickaxe_runite", 1, 1, new Item.Properties().group(MOD_GROUP));
	public static Item dragonstone_pickaxe = new TOCPickaxe(ItemTier.IRON, "pickaxe_dragonstone", 1, 1, new Item.Properties().group(MOD_GROUP));

//	public static Item viking_helmet = new ArmorVikingHelmet(VIKING_STEEL_ARMOR_HELM, 0, EntityEquipmentSlot.HEAD, .05f, 0f, .10f);
//	public static Item viking_chestplate = new CustomArmor(VIKING_STEEL_ARMOR, 1, EntityEquipmentSlot.CHEST, "viking_chestplate", .08f, 0f, .17f);
//	public static Item viking_leggings = new CustomArmor(VIKING_STEEL_ARMOR, 2, EntityEquipmentSlot.LEGS, "viking_leggings", .05f, 0f, .12f);
//	public static Item viking_boots = new CustomArmor(VIKING_STEEL_ARMOR, 3, EntityEquipmentSlot.FEET, "viking_boots", .03f, 0f, .08f);

	public static Item bronze_helmet = new CustomArmor(ArmorMaterialList.bronze, EntityEquipmentSlot.HEAD, new Item.Properties().group(MOD_GROUP), "bronze_helmet", .02f, 0f, .06f);
	public static Item bronze_chestplate = new CustomArmor(ArmorMaterialList.bronze, EntityEquipmentSlot.CHEST, new Item.Properties().group(MOD_GROUP), "bronze_chestplate", .05f, 0f, .09f);
	public static Item bronze_leggings = new CustomArmor(ArmorMaterialList.bronze, EntityEquipmentSlot.LEGS, new Item.Properties().group(MOD_GROUP), "bronze_leggings", .03f, 0f, .05f);
	public static Item bronze_boots = new CustomArmor(ArmorMaterialList.bronze, EntityEquipmentSlot.FEET, new Item.Properties().group(MOD_GROUP), "bronze_boots", .01f, 0f, .03f);
//
//	public static Item iron_helmet = new CustomArmor(IRON_ARMOR, 0, EntityEquipmentSlot.HEAD, "iron_helmet", .03f, 0f, .08f);
//	public static Item iron_chestplate = new CustomArmor(IRON_ARMOR, 1, EntityEquipmentSlot.CHEST, "iron_chestplate", .07f, 0f, .12f);
//	public static Item iron_leggings = new CustomArmor(IRON_ARMOR, 2, EntityEquipmentSlot.LEGS, "iron_leggings", .05f, 0f, .06f);
//	public static Item iron_boots = new CustomArmor(IRON_ARMOR, 3, EntityEquipmentSlot.FEET, "iron_boots", .02f, 0f, .06f);
//
//	public static Item steel_helmet = new CustomArmor(STEEL_ARMOR, 0, EntityEquipmentSlot.HEAD, "steel_helmet", .03f, 0f, .08f);
//	public static Item steel_chestplate = new CustomArmor(STEEL_ARMOR, 1, EntityEquipmentSlot.CHEST, "steel_chestplate", .07f, 0f, .12f);
//	public static Item steel_leggings = new CustomArmor(STEEL_ARMOR, 2, EntityEquipmentSlot.LEGS, "steel_leggings", .05f, 0f, .06f);
//	public static Item steel_boots = new CustomArmor(STEEL_ARMOR, 3, EntityEquipmentSlot.FEET, "steel_boots", .02f, 0f, .06f);
//
//	public static Item mithril_helmet = new CustomArmor(MITHRIL_ARMOR, 0, EntityEquipmentSlot.HEAD, "mithril_helmet", .03f, 0f, .08f);
//	public static Item mithril_chestplate = new CustomArmor(MITHRIL_ARMOR, 1, EntityEquipmentSlot.CHEST, "mithril_chestplate", .07f, 0f, .12f);
//	public static Item mithril_leggings = new CustomArmor(MITHRIL_ARMOR, 2, EntityEquipmentSlot.LEGS, "mithril_leggings", .05f, 0f, .06f);
//	public static Item mithril_boots = new CustomArmor(MITHRIL_ARMOR, 3, EntityEquipmentSlot.FEET, "mithril_boots", .02f, 0f, .06f);
//
//	public static Item adamantite_helmet = new CustomArmor(ADAMANTITE_ARMOR, 0, EntityEquipmentSlot.HEAD, "adamantite_helmet", .03f, 0f, .08f);
//	public static Item adamantite_chestplate = new CustomArmor(ADAMANTITE_ARMOR, 1, EntityEquipmentSlot.CHEST, "adamantite_chestplate", .07f, 0f, .12f);
//	public static Item adamantite_leggings = new CustomArmor(ADAMANTITE_ARMOR, 2, EntityEquipmentSlot.LEGS, "adamantite_leggings", .05f, 0f, .06f);
//	public static Item adamantite_boots = new CustomArmor(ADAMANTITE_ARMOR, 3, EntityEquipmentSlot.FEET, "adamantite_boots", .02f, 0f, .06f);
//
//	public static Item runite_helmet = new CustomArmor(RUNITE_ARMOR, 0, EntityEquipmentSlot.HEAD, "runite_helmet", .03f, 0f, .08f);
//	public static Item runite_chestplate = new CustomArmor(RUNITE_ARMOR, 1, EntityEquipmentSlot.CHEST, "runite_chestplate", .07f, 0f, .12f);
//	public static Item runite_leggings = new CustomArmor(RUNITE_ARMOR, 2, EntityEquipmentSlot.LEGS, "runite_leggings", .05f, 0f, .06f);
//	public static Item runite_boots = new CustomArmor(RUNITE_ARMOR, 3, EntityEquipmentSlot.FEET, "runite_boots", .02f, 0f, .06f);
//
//	public static Item dragonstone_helmet = new CustomArmor(DRAGONSTONE_ARMOR, 0, EntityEquipmentSlot.HEAD, "dragonstone_helmet", .03f, 0f, .08f);
//	public static Item dragonstone_chestplate = new CustomArmor(DRAGONSTONE_ARMOR, 1, EntityEquipmentSlot.CHEST, "dragonstone_chestplate", .07f, 0f, .12f);
//	public static Item dragonstone_leggings = new CustomArmor(DRAGONSTONE_ARMOR, 2, EntityEquipmentSlot.LEGS, "dragonstone_leggings", .05f, 0f, .06f);
//	public static Item dragonstone_boots = new CustomArmor(DRAGONSTONE_ARMOR, 3, EntityEquipmentSlot.FEET, "dragonstone_boots", .02f, 0f, .06f);
//
	public static Item goblin_ear = new QuestItem(new Item.Properties().group(MOD_GROUP), "goblin_ear", false, 20);
	public static Item rat_tail = new QuestItem(new Item.Properties().group(MOD_GROUP), "rat_tail", false, 20);
	public static Item blacksmith_hammer = new BlacksmithHammer(new Item.Properties().group(MOD_GROUP), "blacksmith_hammer");
//
//	public static Item donkey_summoner = new HorseDonkeySummoner("donkey_summoner", EntityDonkeyMount.class);
//	public static Item mule_summoner = new HorseDonkeySummoner("mule_summoner", EntityMuleMount.class);
//	public static Item horse_summoner_lame_black = new VariableHorseSummoner("horse_summoner_lame_black", 0);
//	public static Item horse_summoner_lame_dapple_gray = new VariableHorseSummoner("horse_summoner_lame_dapple_gray", 1);
//	public static Item horse_summoner_chestnut = new VariableHorseSummoner("horse_summoner_chestnut", 2);
//	public static Item horse_summoner_buckskin = new VariableHorseSummoner("horse_summoner_buckskin", 3);
//	public static Item horse_summoner_white_stallion = new VariableHorseSummoner("horse_summoner_white_stallion", 4);
//	public static Item horse_summoner_zombie = new VariableHorseSummoner("horse_summoner_zombie", 5);
//	public static Item horse_summoner_skeleton = new VariableHorseSummoner("horse_summoner_skeleton", 6);

	public static Item arrow_bronze      = new TOCArrow(new Item.Properties().group(MOD_GROUP), "arrow_bronze",       3, .5f);
	public static Item arrow_iron        = new TOCArrow(new Item.Properties().group(MOD_GROUP), "arrow_iron",         6, .4f);
	public static Item arrow_steel       = new TOCArrow(new Item.Properties().group(MOD_GROUP), "arrow_steel",       11, .4f);
	public static Item arrow_mithril     = new TOCArrow(new Item.Properties().group(MOD_GROUP), "arrow_mithril",     18, .3f);
	public static Item arrow_adamantite  = new TOCArrow(new Item.Properties().group(MOD_GROUP), "arrow_adamantite",  27, .2f);
	public static Item arrow_runite      = new TOCArrow(new Item.Properties().group(MOD_GROUP), "arrow_runite",      38, .1f);
	public static Item arrow_dragonstone = new TOCArrow(new Item.Properties().group(MOD_GROUP), "arrow_dragonstone", 52, .05f);
	
	public static Item shears = new TOCShears(new Item.Properties().group(MOD_GROUP), "shears");
	
	public static Item bow_oak_short_unstrung = new CustomItem(new Item.Properties().group(MOD_GROUP), "bow_oak_short_unstrung", 1);
	public static Item bow_oak_long_unstrung =  new CustomItem(new Item.Properties().group(MOD_GROUP), "bow_oak_long_unstrung",  1);
	
	public static Item bow_oak_short = new TOCBow(new Item.Properties().group(MOD_GROUP), "bow_oak_short", 5, 20, .2f);
	public static Item bow_oak_long = new TOCBow(new Item.Properties().group(MOD_GROUP), "bow_oak_long", 8, 30, 1.3f);
	
	public static Item bowstring = new CustomItem(new Item.Properties().group(MOD_GROUP), "bowstring", 20);
	
	public static Item stick_oak = new CustomItem(new Item.Properties().group(MOD_GROUP), "stick_oak", 20);
	public static Item stick_birch = new CustomItem(new Item.Properties().group(MOD_GROUP), "stick_birch", 20);
	public static Item stick_maple = new CustomItem(new Item.Properties().group(MOD_GROUP), "stick_maple", 20);
	public static Item stick_yew = new CustomItem(new Item.Properties().group(MOD_GROUP), "stick_yew", 20);
	public static Item stick_orc = new CustomItem(new Item.Properties().group(MOD_GROUP), "stick_orc", 20);
	public static Item stick_magic = new CustomItem(new Item.Properties().group(MOD_GROUP), "stick_magic", 20);
	
	public static Item arrowhead_bronze      = new CustomItem(new Item.Properties().group(MOD_GROUP), "arrowhead_bronze", 40);
	public static Item arrowhead_iron        = new CustomItem(new Item.Properties().group(MOD_GROUP), "arrowhead_iron", 40);
	public static Item arrowhead_steel       = new CustomItem(new Item.Properties().group(MOD_GROUP), "arrowhead_steel", 40);
	public static Item arrowhead_mithril     = new CustomItem(new Item.Properties().group(MOD_GROUP), "arrowhead_mithril", 40);
	public static Item arrowhead_adamantite  = new CustomItem(new Item.Properties().group(MOD_GROUP), "arrowhead_adamantite", 40);
	public static Item arrowhead_runite      = new CustomItem(new Item.Properties().group(MOD_GROUP), "arrowhead_runite", 40);
	public static Item arrowhead_dragonstone = new CustomItem(new Item.Properties().group(MOD_GROUP), "arrowhead_dragonstone", 40);
	
//	public static Item tocBook = new ItemTOCBook("info_book");
	
//	@SubscribeEvent
//	@SuppressWarnings("unchecked")
//	public void registerItems(RegistryEvent.Register<Item> event) {
//		TOCPickaxe.initPickaxeChances();
//		final IForgeRegistry<Item> registry = event.getRegistry();
//		try {
//			for (Field f : TOCItems.class.getFields()) {
//				if (f.get(null) instanceof Item) {
//					registry.register((Item) f.get(null));
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			Class<Item> itemClass = Item.class;
//			Field f = itemClass.getDeclaredField("BLOCK_TO_ITEM");
//			f.setAccessible(true);
//			Map<Block, Item> map = (Map<Block, Item>) f.get(null);
//
//			Field stackSize = itemClass.getDeclaredField("maxStackSize");
//			stackSize.setAccessible(true);
//
//			stackSize.set(map.get(Blocks.OAK_LOG), 1);
//		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}
//
//	static String[] standard_weapons = new String[] { "club", "warhammer", "axe", "short_sword" };
//	static String[] rare_weapons = new String[] { "great_axe", "great_sword" };
//
//	public static Item getRandomWeaponForClass(String weaponClass, float rarePctChance) {
//		boolean isRare = TOCMain.rand.nextFloat() < rarePctChance;
//
//		String fieldName = weaponClass + "_";
//		if (isRare) {
//			fieldName += rare_weapons[TOCMain.rand.nextInt(rare_weapons.length)];
//		} else {
//			fieldName += standard_weapons[TOCMain.rand.nextInt(standard_weapons.length)];
//		}
//
//		Class<TOCItems> c = TOCItems.class;
//		try {
//			return (Item) c.getField(fieldName).get(null);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Reference.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
	
	/**
	 * Returns null, while claiming to never return null.
	 * Useful for constants with @ObjectHolder who's values are null at compile time, but not at runtime
	 *
	 * @return null
	 */
	@Nonnull
	public static <T> T _null() {
		return null;
	}
	
}
