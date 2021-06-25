package ihh.propertymodifier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TieredItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;

@SuppressWarnings({"ConstantConditions", "FieldMayBeFinal"})
public final class Config {
    private static final Map<Block, Properties.Block> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Properties.Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Enchantment, Properties.Enchantment> ENCHANTMENTS = new LinkedHashMap<>();
    private static final Map<ItemGroup, List<EnchantmentType>> ENCHANTMENT_GROUPS = new LinkedHashMap<>();
    public static final Map<Block, Float> MIXIN_HARDNESS = new HashMap<>();
    public static final Map<Block, Integer> MIXIN_HARVEST_LEVEL = new HashMap<>();
    public static final Map<Block, ToolType> MIXIN_HARVEST_TOOL = new HashMap<>();
    public static final Map<Block, Integer> MIXIN_LIGHT_LEVEL = new HashMap<>();
    public static final Map<Block, Boolean> MIXIN_REQUIRES_TOOL = new HashMap<>();
    public static final Map<Item, Integer> MIXIN_ENCHANTABILITY = new HashMap<>();
    public static final Map<Item, LazyValue<Ingredient>> MIXIN_REPAIR_MATERIAL = new HashMap<>();
    public static final Map<TieredItem, Integer> MIXIN_TOOL_HARVEST_LEVEL = new HashMap<>();
    public static final Map<Enchantment, Integer> MIXIN_MAX_LEVEL = new HashMap<>();
    public static final Map<Enchantment, Pair<Integer, Integer>> MIXIN_MIN_ENCHANTABILITY = new HashMap<>();
    public static final Map<Enchantment, Triple<Integer, Integer, Boolean>> MIXIN_MAX_ENCHANTABILITY = new HashMap<>();
    public static final Map<Enchantment, Boolean> MIXIN_IS_TREASURE = new HashMap<>();
    public static final Map<Enchantment, Boolean> MIXIN_CAN_VILLAGER_TRADE = new HashMap<>();
    public static final Map<Enchantment, Boolean> MIXIN_CAN_GENERATE_IN_LOOT = new HashMap<>();
    public static final Map<Enchantment, HashSet<Enchantment>> MIXIN_CAN_COMBINE = new HashMap<>();
    static ForgeConfigSpec SPEC;
    static ForgeConfigSpec.BooleanValue LOG_SUCCESSFUL;
    static ForgeConfigSpec.BooleanValue LOG_ERRORS;
    private static ForgeConfigSpec.BooleanValue REMOVE_EMPTY_ITEM_GROUPS;
    private static ForgeConfigSpec.BooleanValue DUMP_BLOCKS;
    private static ForgeConfigSpec.BooleanValue DUMP_BLOCKS_AFTER;
    private static ForgeConfigSpec.BooleanValue DUMP_BLOCKS_NON_DEFAULT;
    private static ForgeConfigSpec.BooleanValue DUMP_BLOCKS_AFTER_NON_DEFAULT;
    private static ForgeConfigSpec.BooleanValue DUMP_ITEMS;
    private static ForgeConfigSpec.BooleanValue DUMP_ITEMS_AFTER;
    private static ForgeConfigSpec.BooleanValue DUMP_ITEMS_NON_DEFAULT;
    private static ForgeConfigSpec.BooleanValue DUMP_ITEMS_AFTER_NON_DEFAULT;
    private static ForgeConfigSpec.BooleanValue DUMP_ENCHANTMENTS;
    private static ForgeConfigSpec.BooleanValue DUMP_ENCHANTMENTS_AFTER;
    private static ForgeConfigSpec.BooleanValue DUMP_GROUPS;
    private static ForgeConfigSpec.BooleanValue DUMP_GROUPS_AFTER;
    private static ForgeConfigSpec.IntValue DEFAULT_ENCHANTABILITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> ITEM_GROUP;
    private static ForgeConfigSpec.ConfigValue<List<String>> HARDNESS;
    private static ForgeConfigSpec.ConfigValue<List<String>> RESISTANCE;
    private static ForgeConfigSpec.ConfigValue<List<String>> HARVEST_LEVEL;
    private static ForgeConfigSpec.ConfigValue<List<String>> HARVEST_TOOL;
    private static ForgeConfigSpec.ConfigValue<List<String>> REQUIRES_TOOL;
    private static ForgeConfigSpec.ConfigValue<List<String>> LIGHT_LEVEL;
    private static ForgeConfigSpec.ConfigValue<List<String>> SLIPPERINESS;
    private static ForgeConfigSpec.ConfigValue<List<String>> SPEED_FACTOR;
    private static ForgeConfigSpec.ConfigValue<List<String>> JUMP_FACTOR;
    private static ForgeConfigSpec.ConfigValue<List<String>> SOUND_TYPE;
    private static ForgeConfigSpec.ConfigValue<List<String>> MAX_DAMAGE;
    private static ForgeConfigSpec.ConfigValue<List<String>> MAX_STACK_SIZE;
    private static ForgeConfigSpec.ConfigValue<List<String>> GROUP;
    private static ForgeConfigSpec.ConfigValue<List<String>> IS_IMMUNE_TO_FIRE;
    private static ForgeConfigSpec.ConfigValue<List<String>> RARITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> ENCHANTABILITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> REPAIR_MATERIAL;
    private static ForgeConfigSpec.ConfigValue<List<String>> ARMOR;
    private static ForgeConfigSpec.ConfigValue<List<String>> TOUGHNESS;
    private static ForgeConfigSpec.ConfigValue<List<String>> KNOCKBACK_RESISTANCE;
    private static ForgeConfigSpec.ConfigValue<List<String>> TOOL_HARVEST_LEVEL;
    private static ForgeConfigSpec.ConfigValue<List<String>> ATTACK_DAMAGE;
    private static ForgeConfigSpec.ConfigValue<List<String>> ATTACK_SPEED;
    private static ForgeConfigSpec.ConfigValue<List<String>> EFFICIENCY;
/*
    private static ForgeConfigSpec.ConfigValue<List<String>> MAX_LEVEL;
    private static ForgeConfigSpec.ConfigValue<List<String>> MIN_ENCHANTABILITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> MAX_ENCHANTABILITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> IS_TREASURE;
    private static ForgeConfigSpec.ConfigValue<List<String>> CAN_VILLAGER_TRADE;
    private static ForgeConfigSpec.ConfigValue<List<String>> CAN_GENERATE_IN_LOOT;
*/
    private static ForgeConfigSpec.ConfigValue<List<String>> ENCHANTMENT_RARITY;
/*
    private static ForgeConfigSpec.ConfigValue<List<String>> CAN_COMBINE;
*/
    private static ForgeConfigSpec.ConfigValue<List<String>> ENCHANTMENT_ITEM_GROUP;
    private static ForgeConfigSpec.BooleanValue REMOVE_ENCHANTMENT_ITEM_GROUPS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder().comment("Any invalid entries will result in a log warning, but will just be skipped, and working entries will work.");
        builder.push("logging");
        LOG_SUCCESSFUL = builder.comment("Whether to log successful operations or not.").define("log_successful", true);
        LOG_ERRORS = builder.comment("Whether to log failed operations or not.").define("log_errors", true);
        builder.pop();
        builder.push("item_groups");
        ITEM_GROUP = builder.comment("Define new item groups here. Format is \"groupid;icon\", with icon being an item in the format \"modid;itemid\". Will run before the below stuff, allowing you to use these groups above. Note that you need to set a translation using a resource pack, otherwise an itemGroup.<id> translation key will appear. Also, do not use \"none\" as a name, as this is the key used to remove an item from any group.").define("item_group", new ArrayList<>());
        REMOVE_EMPTY_ITEM_GROUPS = builder.comment("Removes item groups that have no items, including empty ones created by this mod. Runs after the below stuff, clearing up any empty groups left from moving all items out of them.").define("remove_empty_item_groups", true);
        builder.pop();
        builder.comment("Dumps all corresponding values, each with their changeable properties, into the logs.").push("dump");
        DUMP_BLOCKS = builder.comment("Dump blocks BEFORE applying the changes.").define("dump_blocks", false);
        DUMP_BLOCKS_AFTER = builder.comment("Dump blocks AFTER applying the changes.").define("dump_blocks_after", false);
        DUMP_BLOCKS_NON_DEFAULT = builder.comment("Dump blocks BEFORE applying the changes. Only dumps non-default values. Does nothing if dump_blocks is set to true. Default values are: ", "hardness: 0, resistance: 0, harvest level: -1, harvest tool: none, requires tool: false, light level: 0, slipperiness: 0.6, speed factor: 1, jump factor: 1, sound type: stone").define("dump_blocks_non_default", false);
        DUMP_BLOCKS_AFTER_NON_DEFAULT = builder.comment("Dump blocks AFTER applying the changes. Only dumps non-default values. Does nothing if dump_blocks_after is set to true. Default values are: ", "hardness: 0, resistance: 0, harvest level: -1, harvest tool: none, requires tool: false, light level: 0, slipperiness: 0.6, speed factor: 1, jump factor: 1, sound type: stone").define("dump_blocks_after_non_default", false);
        DUMP_ITEMS = builder.comment("Dump items BEFORE applying the changes. Note that repair materials and tool harvest levels cannot be dumped for technical reasons.").define("dump_items", false);
        DUMP_ITEMS_AFTER = builder.comment("Dump items AFTER applying the changes. Note that repair materials and tool harvest levels cannot be dumped for technical reasons.").define("dump_items_after", false);
        DUMP_ITEMS_NON_DEFAULT = builder.comment("Dump items BEFORE applying the changes. Note that repair materials and tool harvest levels cannot be dumped for technical reasons. Only dumps non-default values. Does nothing if dump_items is set to true. Default values are: ", "max damage: 0, max stack size: 64, group: none, is immune to fire: false, rarity: common, enchantability: 0, repair material: null").define("dump_items_non_default", false);
        DUMP_ITEMS_AFTER_NON_DEFAULT = builder.comment("Dump items AFTER applying the changes. Note that repair materials and tool harvest levels cannot be dumped for technical reasons. Only dumps non-default values. Does nothing if dump_items is set to true. Default values are: ", "max damage: 0, max stack size: 64, group: none, is immune to fire: false, rarity: common, enchantability: 0, repair material: null").define("dump_items_after_non_default", false);
        DUMP_ENCHANTMENTS = builder.comment("Dump enchantments BEFORE applying the changes.").define("dump_enchantments", false);
        DUMP_ENCHANTMENTS_AFTER = builder.comment("Dump enchantments AFTER applying the changes.").define("dump_enchantments_after", false);
        DUMP_GROUPS = builder.comment("Dump item groups BEFORE applying the changes.").define("dump_groups", false);
        DUMP_GROUPS_AFTER = builder.comment("Dump item groups AFTER applying the changes.").define("dump_groups_after", false);
        DEFAULT_ENCHANTABILITY = builder.comment("The default enchantability of items. Change this if you have a mod installed that makes every item enchantable (and thus have a different enchantability). If you are unsure, leave this unchanged and run the item dumping. You will see if you need to change it or not.").defineInRange("default_enchantability", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        builder.pop();
        builder.comment("Settings related to blocks. Format is always \"block;value\", with block being in the format \"modid:blockid\". Alternatively, you can use \"any\" to apply the setting to all blocks (usable e.g. to set all light levels to 0 and then add light level to only a select few blocks.) Note that entries are read from left to right, so you should put \"any\"-entries at the start, as they will overwrite anything stated before them. Note that NBT is currently not supported.").push("blocks");
        HARDNESS = builder.comment("How long the block takes to break. 0.4 is netherrack, 0.5 is dirt, 0.6 is grass block, 1.5 is stone and cobblestone, 3 is end stone, 50 is obsidian. -1 makes the block unbreakable. \"minecraft:obsidian;1.5\" would make obsidian break as fast as stone.").define("hardness", new ArrayList<>());
        RESISTANCE = builder.comment("How explosion-resistant the block is. 0.4 is netherrack, 0.5 is dirt, 0.6 is grass block, 6 is stone and cobblestone, 3 is end stone, 1200 is obsidian. 3600000 makes the block unbreakable. \"minecraft:obsidian;600\" would make obsidian have half the blast resistance it usually has.").define("resistance", new ArrayList<>());
        HARVEST_LEVEL = builder.comment("The harvest level required to get drops from this block. 0 is wood/gold, 1 is stone, 2 is iron, 3 is diamond/netherite, -1 is any harvest level. \"minecraft:obsidian;1\" would cause obsidian to drop when mined with a stone or iron pickaxe as well.").define("harvest_level", new ArrayList<>());
        HARVEST_TOOL = builder.comment("The tool required to mine the block faster. Must be one of axe, hoe, pickaxe, shovel or none. \"minecraft:obsidian;axe\" would cause obsidian to require an axe instead of a pickaxe.").define("harvest_tool", new ArrayList<>());
        REQUIRES_TOOL = builder.comment("Whether you need a corresponding tool, as set by the harvest tool, at least of the harvest level to get block drops (e.g. stone) or if the harvest tool type only speeds up the breaking speed (e.g. dirt). \"minecraft:obsidian;false\" would cause obsidian to drop when broken by hand.").define("requires_tool", new ArrayList<>());
        LIGHT_LEVEL = builder.comment("The light level emitted by this block. Must be an integer between 0 and 15 (inclusive). \"minecraft:obsidian;15\" would cause obsidian to emit a light level of 15 (like lava and glowstone).").define("light_level", new ArrayList<>());
        SLIPPERINESS = builder.comment("How slippery the block is. Most blocks have a slipperiness of 0.6, for ice-like blocks, it's 0.98.").define("slipperiness", new ArrayList<>());
        SPEED_FACTOR = builder.comment("Accelerates (if > 1) or slows down (if between 0 and 1) entities that walk upon this block. 1 for most blocks, 0.4 for soul sand and honey blocks.").define("speed_factor", new ArrayList<>());
        JUMP_FACTOR = builder.comment("Allows entities on this block to jump higher (if > 1) or lower (if between 0 and 1). 1 for most blocks, 0.5 for honey blocks.").define("jump_factor", new ArrayList<>());
        SOUND_TYPE = builder.comment("The sound type the block has. Currently, only vanilla sound types are supported. Vanilla sound types are:", "\"wood\", \"ground\" (dirt, coarse dirt, gravel, clay), \"plant\" (grass, saplings, leaves, grass blocks, etc.), \"lily_pads\", \"stone\" (stone-like blocks, ores, end stone, etc.), \"metal\" (ore blocks, iron bars, rails, etc.), \"glass\", \"cloth\" (wool, carpets), \"sand\", \"snow\", \"ladder\", \"anvil\", \"slime\", \"honey\", \"wet_grass (seagrass, kelp)\", \"coral\", \"bamboo\", \"bamboo_sapling\", \"scaffolding\", \"sweet_berry_bush\", \"crop\", \"stem\" (pumpkin/melon stem), \"vine\", \"nether_wart\", \"lantern\", \"hyphae\", \"nylium\", \"fungus\", \"root\", \"shroomlight\", \"nether_vine\" (weeping/twisting vines), \"nether_vine_lower_pitch\" (unused), \"soul_sand\", \"soul_soil\", \"basalt\", \"wart\", \"netherrack\", \"nether_brick\", \"nether_sprout\", \"nether_ore\", \"bone\", \"netherite\", \"ancient_debris\", \"lodestone\", \"chain\", \"nether_gold\", \"gilded_blackstone\"").define("sound_type", new ArrayList<>());
        builder.pop();
        builder.comment("Settings related to items. Format is always \"item;value\", with item being in the format \"modid:itemid\". Alternatively, you can use \"any\" to apply the setting to all items (usable e.g. to set all stacksizes to 64.) Note that entries are read from left to right, so you should put \"any\"-entries at the start, as they will overwrite anything stated before them. Note that NBT is currently not supported.").push("items");
        MAX_DAMAGE = builder.comment("The max durability an item has. Bow has 384, diamond tools have 1563, etc. Cannot be set if the item cannot be damaged.").define("max_damage", new ArrayList<>());
        MAX_STACK_SIZE = builder.comment("The max stack size an item has. Most have 64 or 16. Anything that is damageable will ALWAYS have a max stack size of 1, even if put in here.").define("max_stack_size", new ArrayList<>());
        GROUP = builder.comment("The item group (= creative tab) of an item. Vanilla values are building_blocks, decorations, redstone, transportation, misc, food, tools, combat, brewing. However, you can also define your own group above, or use a modded group if you know its id. Use \"none\" to remove the item from any item group.").define("group", new ArrayList<>());
        IS_IMMUNE_TO_FIRE = builder.comment("Whether the item should have the fire and lava shielding properties of netherite or not. Accepts true or false.").define("is_immune_to_fire", new ArrayList<>());
        RARITY = builder.comment("Sets the item rarity (aka text color). Values are common (white), uncommon (yellow), rare (aqua) and epic (light purple)").define("rarity", new ArrayList<>());
        ENCHANTABILITY = builder.comment("Sets the enchantability of the item.").define("enchantability", new ArrayList<>());
        REPAIR_MATERIAL = builder.comment("Sets the repair material of the item. Tags (e.g. #minecraft:planks) are also allowed.").define("repair_material", new ArrayList<>());
        builder.comment("Settings related to armor. Only armor items can be affected, anything else will be skipped.").push("armor");
        ARMOR = builder.comment("Sets the armor value. 1 means half an armor icon, 20 means a full armor bar.").define("armor", new ArrayList<>());
        TOUGHNESS = builder.comment("Sets the armor toughness value. 1 means half an armor icon, 20 means a full armor bar.").define("toughness", new ArrayList<>());
        KNOCKBACK_RESISTANCE = builder.comment("Sets the knockback resistance. Most have 0, netherite has 0.1.").define("knockback_resistance", new ArrayList<>());
        builder.pop();
        builder.comment("Settings related to tools. Only tool, sword and trident items can be affected, anything else will be skipped.").push("tools");
        ATTACK_DAMAGE = builder.comment("Sets the attack damage.").define("attack_damage", new ArrayList<>());
        ATTACK_SPEED = builder.comment("Sets the attack speed.").define("attack_speed", new ArrayList<>());
        TOOL_HARVEST_LEVEL = builder.comment("Sets the tool harvest level. 0 is wood/gold, 1 is stone, 2 is iron, 3 is diamond/netherite. Does not work for swords or tridents.").define("harvest_level", new ArrayList<>());
        EFFICIENCY = builder.comment("Sets the efficiency. Wood has 2, stone has 4, iron has 6, diamond has 8, netherite has 9, gold has 12. Does not work for swords or tridents.").define("efficiency", new ArrayList<>());
        builder.pop();
        builder.pop();
        builder.comment("Settings related to enchantments. Format is always \"enchantment;value\", with enchantment being in the format \"modid:enchantmentid\", unless stated otherwise. Alternatively, you can use \"any\" to apply the setting to all enchantments (usable e.g. to make all enchantments have the same rarity.) Note that entries are read from left to right, so you should put \"any\"-entries at the start, as they will overwrite anything stated before them.").push("enchantments");
/*
        MAX_LEVEL = builder.comment("Sets the max enchantment level.").define("max_level", new ArrayList<>());
        MIN_ENCHANTABILITY = builder.comment("Sets the min enchantability required for this enchantment. Format is \"enchantment;constant;variable\". Calculation is performed as follows: constant + enchantmentlevel * variable. Vanilla values are:", "\"minecraft:aqua_affinity;1;0\", \"minecraft:bane_of_arthropods;-3;8\", \"minecraft:binding_curse;25;0\", \"minecraft:blast_protection;-3;8\", \"minecraft:channeling;20;0\", \"minecraft:depth_strider;0;10\", \"minecraft:efficiency;-9;10\", \"minecraft:feather_falling;-1;6\", \"minecraft:fire_aspect;-10;20\", \"minecraft:fire_protection;2;8\", \"minecraft:flame;20;0\", \"minecraft:fortune;6;9\", \"minecraft:frost_walker;0;10\", \"minecraft:impaling;-7;8\", \"minecraft:infinity;20;0\", \"minecraft:knockback;-15;20\", \"minecraft:looting;6;9\", \"minecraft:loyalty;5;7\", \"minecraft:luck_of_the_sea;6;9\", \"minecraft:lure;6;9\", \"minecraft:mending;0;25\", \"minecraft:multishot;20;0\", \"minecraft:piercing;-9;10\", \"minecraft:power;-9;10\", \"minecraft:projectile_protection;-3;6\", \"minecraft:protection;-10;11\", \"minecraft:punch;-8;20\", \"minecraft:quick_charge;-8;20\", \"minecraft:respiration;0;10\", \"minecraft:riptide;10;7\", \"minecraft:sharpness;-10;11\", \"minecraft:silk_touch;15;0\", \"minecraft:smite;-3;8\", \"minecraft:soul_speed;0;10\", \"minecraft:sweeping;-4;9\", \"minecraft:thorns;-10;20\", \"minecraft:unbreaking;-3;8\", \"minecraft:vanishing_curse;25;0\"").define("min_enchantability", new ArrayList<>());
        MAX_ENCHANTABILITY = builder.comment("Sets the max enchantability required for this enchantment. Format is \"enchantment;constant;variable;add\". Calculation is performed as follows: constant + enchantmentlevel * variable. If \"add\" is set to true, this value will be added to the min enchantability of this enchantment, if set to false, this will not happen. Vanilla values are:", "\"minecraft:aqua_affinity;40;0;true\", \"minecraft:bane_of_arthropods;20;0;true\", \"minecraft:binding_curse;50;0;false\", \"minecraft:blast_protection;8;0;true\", \"minecraft:channeling;50;0;false\", \"minecraft:depth_strider;15;0;true\", \"minecraft:efficiency;50;0;true\", \"minecraft:feather_falling;6;0;true\", \"minecraft:fire_aspect;50;0;true\", \"minecraft:fire_protection;8;0;true\", \"minecraft:flame;50;0;false\", \"minecraft:fortune;50;0;true\", \"minecraft:frost_walker;15;0;true\", \"minecraft:impaling;20;0;true\", \"minecraft:infinity;50;0;false\", \"minecraft:knockback;50;0;true\", \"minecraft:looting;50;0;true\", \"minecraft:loyalty;50;0;false\", \"minecraft:luck_of_the_sea;50;0;true\", \"minecraft:lure;50;0;true\", \"minecraft:mending;50;0;true\", \"minecraft:multishot;50;0;false\", \"minecraft:piercing;50;0;false\", \"minecraft:power;15;0;true\", \"minecraft:projectile_protection;6;0;true\", \"minecraft:protection;11;0;true\", \"minecraft:punch;25;0;true\", \"minecraft:quick_charge;50;0;false\", \"minecraft:respiration;30;0;true\", \"minecraft:riptide;50;0;false\", \"minecraft:sharpness;20;0;true\", \"minecraft:silk_touch;50;0;true\", \"minecraft:smite;20;0;true\", \"minecraft:soul_speed;15;0;true\", \"minecraft:sweeping;15;0;true\", \"minecraft:thorns;50;0;true\", \"minecraft:unbreaking;50;0;true\", \"minecraft:vanishing_curse;50;0;false\"").define("max_enchantability", new ArrayList<>());
        IS_TREASURE = builder.comment("Whether this item is considered a treasure enchantment or not. Is true for binding_curse, frost_walker, mending, soul_speed, vanishing_curse.").define("is_treasure", new ArrayList<>());
        CAN_VILLAGER_TRADE = builder.comment("Whether this item can be traded from villagers. True for every vanilla enchantment except soul_speed.").define("can_villager_trade", new ArrayList<>());
        CAN_GENERATE_IN_LOOT = builder.comment("Whether this item can generate from loot tables. True for every vanilla enchantment except soul_speed.").define("can_generate_in_loot", new ArrayList<>());
*/
        ENCHANTMENT_RARITY = builder.comment("The enchantment rarity of this enchantment. Can take the following values: common (10), uncommon (5), rare (2) and very_rare (1). Vanilla values are:", "\"minecraft:aqua_affinity;rare\", \"minecraft:bane_of_arthropods;uncommon\", \"minecraft:binding_curse;very_rare\", \"minecraft:blast_protection;rare\", \"minecraft:channeling;very_rare\", \"minecraft:depth_strider;rare\", \"minecraft:efficiency;common\", \"minecraft:feather_falling;uncommon\", \"minecraft:fire_aspect;rare\", \"minecraft:fire_protection;uncommon\", \"minecraft:flame;rare\", \"minecraft:fortune;rare\", \"minecraft:frost_walker;rare\", \"minecraft:impaling;rare\", \"minecraft:infinity;very_rare\", \"minecraft:knockback;uncommon\", \"minecraft:looting;rare\", \"minecraft:loyalty;uncommon\", \"minecraft:luck_of_the_sea;rare\", \"minecraft:lure;rare\", \"minecraft:mending;rare\", \"minecraft:multishot;rare\", \"minecraft:piercing;common\", \"minecraft:power;common\", \"minecraft:projectile_protection;uncommon\", \"minecraft:protection;common\", \"minecraft:punch;rare\", \"minecraft:quick_charge;rare\", \"minecraft:respiration;rare\", \"minecraft:riptide;rare\", \"minecraft:sharpness;common\", \"minecraft:silk_touch;very_rare\", \"minecraft:smite;uncommon\", \"minecraft:soul_speed;very_rare\", \"minecraft:sweeping;rare\", \"minecraft:thorns;very_rare\", \"minecraft:unbreaking;uncommon\", \"minecraft:vanishing_curse;very_rare\"").define("rarity", new ArrayList<>());
/*
        CAN_COMBINE = builder.comment("Two enchantments that cannot be on the same item. Does not need to be set by both sides (e.g. by frost walker AND by depth strider), but it's recommended. Once you overwrite it for an enchantment, all previously existing restrictions will be removed, and you must re-add them if you still want them. Vanilla values are:", "\"minecraft:bane_of_arthropods;minecraft:sharpness\", \"minecraft:bane_of_arthropods;minecraft:smite\", \"minecraft:sharpness;minecraft:bane_of_arthropods\", \"minecraft:sharpness;minecraft:smite\", \"minecraft:smite;minecraft:bane_of_arthropods\", \"minecraft:smite;minecraft:sharpness\", \"minecraft:blast_protection;minecraft:fire_protection\", \"minecraft:blast_protection;minecraft:projectile_protection\", \"minecraft:blast_protection;minecraft:protection\", \"minecraft:fire_protection;minecraft:blast_protection\", \"minecraft:fire_protection;minecraft:projectile_protection\", \"minecraft:fire_protection;minecraft:protection\", \"minecraft:projectile_protection;minecraft:blast_protection\", \"minecraft:projectile_protection;minecraft:fire_protection\", \"minecraft:projectile_protection;minecraft:protection\", \"minecraft:protection;minecraft:blast_protection\", \"minecraft:protection;minecraft:fire_protection\", \"minecraft:protection;minecraft:projectile_protection\", \"minecraft:channeling;minecraft:riptide\", \"minecraft:loyalty;minecraft:riptide\", \"minecraft:riptide;minecraft:channeling\", \"minecraft:riptide;minecraft:loyalty\", \"minecraft:depth_strider;minecraft:frost_walker\", \"minecraft:frost_walker;minecraft:depth_strider\", \"minecraft:fortune;minecraft:silk_touch\", \"minecraft:silk_touch;minecraft:fortune\", \"minecraft:infinity;minecraft:mending\", \"minecraft:mending;minecraft:infinity\", \"minecraft:multishot;minecraft:piercing\", \"minecraft:piercing;minecraft:multishot\"").define("can_combine", new ArrayList<>());
*/
        ENCHANTMENT_ITEM_GROUP = builder.comment("The item group this enchantment type's enchanted books are in. As soon as you add one for an item group, you need to re-add every enchantment type for that group as well. Default values are: \"armor;combat\", \"armor_feet;combat\", \"armor_legs;combat\", \"armor_chest;combat\", \"armor_head;combat\", \"bow;combat\", \"weapon;combat\", \"wearable;combat\", \"breakable;combat\", \"trident;combat\", \"crossbow;combat\", \"digger;tools\", \"fishing_rod;tools\", \"vanishable;tools\", \"breakable;tools\"").define("group", new ArrayList<>());
        REMOVE_ENCHANTMENT_ITEM_GROUPS = builder.comment("Remove enchantment books from creative tabs. Runs before \"group\", so re-adding is possible.").define("remove_item_groups", false);
        builder.pop();
        SPEC = builder.build();
    }

    public static void work() {
        List<Block> BLOCK_REGISTRY = new ArrayList<>(ForgeRegistries.BLOCKS.getValues());
        List<Item> ITEM_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Item> ARMOR_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Item> TIERED_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Item> TOOL_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Enchantment> ENCHANTMENT_REGISTRY = new ArrayList<>(ForgeRegistries.ENCHANTMENTS.getValues());
        BLOCK_REGISTRY.removeIf(e -> e.properties.isAir);
        ARMOR_REGISTRY.removeIf(e -> !(e instanceof ArmorItem));
        TIERED_REGISTRY.removeIf(e -> !(e instanceof TieredItem) && !(e instanceof TridentItem));
        TOOL_REGISTRY.removeIf(e -> !(e instanceof ToolItem));
        boolean searchReload = false;
        dump(DUMP_BLOCKS, DUMP_BLOCKS_NON_DEFAULT, DUMP_ITEMS, DUMP_ITEMS_NON_DEFAULT, DUMP_ENCHANTMENTS, DUMP_GROUPS);
        if (REMOVE_ENCHANTMENT_ITEM_GROUPS.get())
            for (ItemGroup g : ItemGroup.GROUPS)
                g.setRelevantEnchantmentTypes();
        LinkedHashMap<String, ItemStack> m = new LinkedHashMap<>();
        for (String v : ITEM_GROUP.get()) {
            String[] s = v.split(";");
            Item i = ConfigUtil.fromRegistry(s[1], ITEM_REGISTRY);
            m.put(s[0], i == null ? ItemStack.EMPTY : new ItemStack(i));
        }
        for (Map.Entry<String, ItemStack> tab : m.entrySet()) {
            new ItemGroup(tab.getKey()) {
                @Override
                @Nonnull
                public ItemStack createIcon() {
                    return tab.getValue();
                }
            };
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(HARDNESS, BLOCK_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0 || e == -1, "Hardness must be either -1, 0 or greater than 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARDNESS = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(RESISTANCE, BLOCK_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0 && e <= 3600000, "Resistance must be between 0 and 3600000").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.RESISTANCE = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Integer> entry : ConfigUtil.getMap(HARVEST_LEVEL, BLOCK_REGISTRY, ConfigUtil::parseInt, e -> e > -2, "Harvest level must be at least -1").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARVEST_LEVEL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, ToolType> entry : ConfigUtil.getMap(HARVEST_TOOL, BLOCK_REGISTRY, ConfigUtil::parseToolType, e -> true, "").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARVEST_TOOL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Boolean> entry : ConfigUtil.getMap(REQUIRES_TOOL, BLOCK_REGISTRY, ConfigUtil::parseBoolean, e -> true, "").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.REQUIRES_TOOL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Integer> entry : ConfigUtil.getMap(LIGHT_LEVEL, BLOCK_REGISTRY, ConfigUtil::parseInt, e -> e > -1 && e < 16, "Light level must be between 0 and 15").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.LIGHT_LEVEL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(SLIPPERINESS, BLOCK_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Slipperiness must be at least 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SLIPPERINESS = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(SPEED_FACTOR, BLOCK_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Speed factor must be at least 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SPEED_FACTOR = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(JUMP_FACTOR, BLOCK_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Jump factor must be at least 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.JUMP_FACTOR = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, SoundType> entry : ConfigUtil.getMap(SOUND_TYPE, BLOCK_REGISTRY, ConfigUtil::parseSoundType, e -> true, "").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SOUND_TYPE = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(MAX_DAMAGE, ITEM_REGISTRY, ConfigUtil::parseInt, e -> e > 0, "Durability must be at least 1").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.MAX_DAMAGE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(MAX_STACK_SIZE, ITEM_REGISTRY, ConfigUtil::parseInt, e -> e > 0, "Max stack size must be at least 1").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.MAX_STACK_SIZE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, ItemGroup> entry : ConfigUtil.getMap(GROUP, ITEM_REGISTRY, ConfigUtil::parseItemGroup, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.GROUP = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Boolean> entry : ConfigUtil.getMap(IS_IMMUNE_TO_FIRE, ITEM_REGISTRY, ConfigUtil::parseBoolean, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.IS_IMMUNE_TO_FIRE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Rarity> entry : ConfigUtil.getMap(RARITY, ITEM_REGISTRY, ConfigUtil::parseRarity, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.RARITY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(ENCHANTABILITY, ITEM_REGISTRY, ConfigUtil::parseInt, e -> e > -1, "Enchantability must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.ENCHANTABILITY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, LazyValue<Ingredient>> entry : ConfigUtil.getMap(REPAIR_MATERIAL, ITEM_REGISTRY, ConfigUtil::parseRepairMaterial, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.REPAIR_MATERIAL = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(ARMOR, ARMOR_REGISTRY, ConfigUtil::parseInt, e -> e > -1, "Armor must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).ARMOR = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(TOUGHNESS, ARMOR_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Toughness must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).TOUGHNESS = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(KNOCKBACK_RESISTANCE, ARMOR_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Knockback resistance must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).KNOCKBACK_RESISTANCE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(ATTACK_DAMAGE, TIERED_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Attack damage must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).ATTACK_DAMAGE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(ATTACK_SPEED, TIERED_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Attack speed must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).ATTACK_SPEED = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(TOOL_HARVEST_LEVEL, TOOL_REGISTRY, ConfigUtil::parseInt, e -> e > -2, "Harvest level must be at least -1").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).HARVEST_LEVEL = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(EFFICIENCY, TOOL_REGISTRY, ConfigUtil::parseFloat, e -> e >= 0, "Efficiency must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).EFFICIENCY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
/*
        for (Map.Entry<Enchantment, Integer> entry : ConfigUtil.getMap(MAX_LEVEL, ENCHANTMENT_REGISTRY, ConfigUtil::parseInt, e -> e > 0, "Max level must be at least 1").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MAX_LEVEL = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Pair<Integer, Integer>> entry : ConfigUtil.pairMap(MIN_ENCHANTABILITY, ENCHANTMENT_REGISTRY, ConfigUtil::parseInt, e -> true, ConfigUtil::parseInt, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MIN_ENCHANTABILITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Triple<Integer, Integer, Boolean>> entry : ConfigUtil.tripleMap(MAX_ENCHANTABILITY, ENCHANTMENT_REGISTRY, ConfigUtil::parseInt, e -> true, ConfigUtil::parseInt, e -> true, ConfigUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MAX_ENCHANTABILITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : ConfigUtil.getMap(IS_TREASURE, ENCHANTMENT_REGISTRY, ConfigUtil::parseBoolean, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.IS_TREASURE = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : ConfigUtil.getMap(CAN_VILLAGER_TRADE, ENCHANTMENT_REGISTRY, ConfigUtil::parseBoolean, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.CAN_VILLAGER_TRADE = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : ConfigUtil.getMap(CAN_GENERATE_IN_LOOT, ENCHANTMENT_REGISTRY, ConfigUtil::parseBoolean, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.CAN_GENERATE_IN_LOOT = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
*/
        for (Map.Entry<Enchantment, Enchantment.Rarity> entry : ConfigUtil.getMap(ENCHANTMENT_RARITY, ENCHANTMENT_REGISTRY, ConfigUtil::parseEnchantmentRarity, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.RARITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
/*
        for (Map.Entry<Enchantment, Enchantment> entry : ConfigUtil.getMap(CAN_COMBINE, ENCHANTMENT_REGISTRY, ConfigUtil::parseEnchantment, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            if (prop.INCOMPATIBLES == null) prop.INCOMPATIBLES = new HashSet<>();
            prop.INCOMPATIBLES.add(entry.getValue());
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
*/
        for (String v : ENCHANTMENT_ITEM_GROUP.get()) {
            String[] s = v.split(";");
            try {
                EnchantmentType t = EnchantmentType.valueOf(s[0].toUpperCase());
                ItemGroup g = ConfigUtil.parseItemGroup(s[1], v, ConfigUtil.getPath(ENCHANTMENT_ITEM_GROUP.getPath()), e -> true, "");
                if (g != null) {
                    List<EnchantmentType> l = ENCHANTMENT_GROUPS.getOrDefault(g, new ArrayList<>());
                    l.add(t);
                    ENCHANTMENT_GROUPS.put(g, l);
                } else Logger.error(s[1] + " is not an item group (is invalid for entry " + v + " in enchantment.group)");
            } catch (IllegalArgumentException e) {
                Logger.error("Unknown enchantment type " + s[0]);
            }
        }
        for (Block block : BLOCKS.keySet()) {
            Properties.Block prop = BLOCKS.get(block);
            if (prop.HARDNESS != null) {
                block.properties.hardness = prop.HARDNESS;
                MIXIN_HARDNESS.put(block, prop.HARDNESS);
            }
            if (prop.RESISTANCE != null) {
                block.properties.resistance = prop.RESISTANCE;
                block.blastResistance = prop.RESISTANCE;
            }
            if (prop.HARVEST_LEVEL != null)
                try {
                    Field bField = Block.class.getDeclaredField("harvestLevel");
                    bField.setAccessible(true);
                    bField.setInt(block, prop.HARVEST_LEVEL);
                    block.properties = block.properties.harvestLevel(prop.HARVEST_LEVEL);
                    MIXIN_HARVEST_LEVEL.put(block, prop.HARVEST_LEVEL);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            if (prop.HARVEST_TOOL != null)
                try {
                    Field bField = Block.class.getDeclaredField("harvestTool");
                    bField.setAccessible(true);
                    bField.set(block, prop.HARVEST_TOOL);
                    block.properties = block.properties.harvestTool(prop.HARVEST_TOOL);
                    MIXIN_HARVEST_TOOL.put(block, prop.HARVEST_TOOL);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            if (prop.REQUIRES_TOOL != null) {
                block.properties.requiresTool = prop.REQUIRES_TOOL;
                MIXIN_REQUIRES_TOOL.put(block, prop.REQUIRES_TOOL);
            }
            if (prop.LIGHT_LEVEL != null) {
                block.properties.lightLevel = l -> prop.LIGHT_LEVEL;
                MIXIN_LIGHT_LEVEL.put(block, prop.LIGHT_LEVEL);
            }
            if (prop.SLIPPERINESS != null) {
                block.properties.slipperiness = prop.SLIPPERINESS;
                block.slipperiness = prop.SLIPPERINESS;
            }
            if (prop.SPEED_FACTOR != null) {
                block.properties.speedFactor = prop.SPEED_FACTOR;
                block.speedFactor = prop.SPEED_FACTOR;
            }
            if (prop.JUMP_FACTOR != null) {
                block.properties.jumpFactor = prop.JUMP_FACTOR;
                block.jumpFactor = prop.JUMP_FACTOR;
            }
            if (prop.SOUND_TYPE != null) {
                block.properties.soundType = prop.SOUND_TYPE;
                block.soundType = prop.SOUND_TYPE;
            }
        }
        for (Item item : ITEMS.keySet()) {
            Properties.Item prop = ITEMS.get(item);
            if (prop.MAX_DAMAGE != null && item.maxDamage > 0) item.maxDamage = prop.MAX_DAMAGE;
            if (prop.MAX_STACK_SIZE != null && item.maxDamage == 0) item.maxStackSize = prop.MAX_STACK_SIZE;
            if (prop.GROUP != null) {
                item.group = prop.GROUP;
                searchReload = true;
            }
            if (prop.IS_IMMUNE_TO_FIRE != null) item.isImmuneToFire = prop.IS_IMMUNE_TO_FIRE;
            if (prop.RARITY != null) item.rarity = prop.RARITY;
            if (prop.ENCHANTABILITY != null) MIXIN_ENCHANTABILITY.put(item, prop.ENCHANTABILITY);
            if (prop.REPAIR_MATERIAL != null) MIXIN_REPAIR_MATERIAL.put(item, prop.REPAIR_MATERIAL);
            if (item instanceof ArmorItem) {
                ArmorItem armoritem = (ArmorItem) item;
                Properties.Armor armorprop = (Properties.Armor) prop;
                if (armorprop.ARMOR != null)
                    armoritem.damageReduceAmount = armorprop.ARMOR;
                if (armorprop.TOUGHNESS != null)
                    armoritem.toughness = armorprop.TOUGHNESS;
                if (armorprop.KNOCKBACK_RESISTANCE != null)
                    armoritem.knockbackResistance = armorprop.KNOCKBACK_RESISTANCE;
                if (armorprop.ARMOR != null || armorprop.TOUGHNESS != null || armorprop.KNOCKBACK_RESISTANCE != null) {
                    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                    UUID uuid = ArmorItem.ARMOR_MODIFIERS[armoritem.getEquipmentSlot().getIndex()];
                    if (armoritem.damageReduceAmount > 0)
                        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", armoritem.damageReduceAmount, AttributeModifier.Operation.ADDITION));
                    if (armoritem.toughness > 0)
                        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", armoritem.toughness, AttributeModifier.Operation.ADDITION));
                    if (armoritem.knockbackResistance > 0)
                        builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", armoritem.knockbackResistance, AttributeModifier.Operation.ADDITION));
                    armoritem.field_234656_m_ = builder.build();
                }
            }
            if (item instanceof TieredItem) {
                TieredItem toolitem = (TieredItem) item;
                Properties.Tool toolprop = (Properties.Tool) prop;
                if (toolprop.HARVEST_LEVEL != null)
                    MIXIN_TOOL_HARVEST_LEVEL.put(toolitem, toolprop.HARVEST_LEVEL);
                if (toolprop.EFFICIENCY != null && toolitem instanceof ToolItem)
                    ((ToolItem) toolitem).efficiency = toolprop.EFFICIENCY;
                if (toolprop.ATTACK_DAMAGE != null || toolprop.ATTACK_SPEED != null) {
                    if (toolitem instanceof ToolItem) {
                        ToolItem tool = (ToolItem) toolitem;
                        AttributeModifier speedAttr = tool.toolAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        double speed = toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedAttr != null ? speedAttr.getAmount() : 0;
                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                        if (toolprop.ATTACK_DAMAGE != null) tool.attackDamage = toolprop.ATTACK_DAMAGE - 1;
                        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.ATTACK_DAMAGE_MODIFIER, "Tool modifier", tool.attackDamage, AttributeModifier.Operation.ADDITION));
                        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.ATTACK_SPEED_MODIFIER, "Tool modifier", speed, AttributeModifier.Operation.ADDITION));
                        tool.toolAttributes = builder.build();
                    }
                    if (toolitem instanceof SwordItem) {
                        SwordItem sword = (SwordItem) toolitem;
                        AttributeModifier speedAttr = sword.attributeModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        double speed = toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedAttr != null ? speedAttr.getAmount() : 0;
                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                        if (toolprop.ATTACK_DAMAGE != null) sword.attackDamage = toolprop.ATTACK_DAMAGE - 1;
                        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.ATTACK_DAMAGE_MODIFIER, "Tool modifier", sword.attackDamage, AttributeModifier.Operation.ADDITION));
                        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.ATTACK_SPEED_MODIFIER, "Tool modifier", speed, AttributeModifier.Operation.ADDITION));
                        sword.attributeModifiers = builder.build();
                    }
                }
            } else if (item instanceof TridentItem) {
                TridentItem trident = (TridentItem) item;
                Properties.Tool toolprop = (Properties.Tool) prop;
                AttributeModifier damageAttr = trident.tridentAttributes.get(Attributes.ATTACK_DAMAGE).stream().findFirst().orElse(null);
                AttributeModifier speedAttr = trident.tridentAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.ATTACK_DAMAGE_MODIFIER, "Tool modifier", toolprop.ATTACK_DAMAGE != null ? toolprop.ATTACK_DAMAGE - 4 : damageAttr != null ? damageAttr.getAmount() : 0, AttributeModifier.Operation.ADDITION));
                builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.ATTACK_SPEED_MODIFIER, "Tool modifier", toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedAttr != null ? speedAttr.getAmount() : 0, AttributeModifier.Operation.ADDITION));
                trident.tridentAttributes = builder.build();
            }
        }
        for (Enchantment enchantment : ENCHANTMENTS.keySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.get(enchantment);
            if (prop.RARITY != null) enchantment.rarity = prop.RARITY;
            //TODO fix
            if (prop.MAX_LEVEL != null) MIXIN_MAX_LEVEL.put(enchantment, prop.MAX_LEVEL);
            if (prop.MIN_ENCHANTABILITY != null) MIXIN_MIN_ENCHANTABILITY.put(enchantment, prop.MIN_ENCHANTABILITY);
            if (prop.MAX_ENCHANTABILITY != null) MIXIN_MAX_ENCHANTABILITY.put(enchantment, prop.MAX_ENCHANTABILITY);
            if (prop.IS_TREASURE != null) MIXIN_IS_TREASURE.put(enchantment, prop.IS_TREASURE);
            if (prop.CAN_VILLAGER_TRADE != null) MIXIN_CAN_VILLAGER_TRADE.put(enchantment, prop.CAN_VILLAGER_TRADE);
            if (prop.CAN_GENERATE_IN_LOOT != null) MIXIN_CAN_GENERATE_IN_LOOT.put(enchantment, prop.CAN_GENERATE_IN_LOOT);
            if (prop.INCOMPATIBLES != null) MIXIN_CAN_COMBINE.put(enchantment, prop.INCOMPATIBLES);
        }
        for (Item item : ForgeRegistries.ITEMS)
            if (item.group != null && item.group.getPath().equals("none")) item.group = null;
        for (ItemGroup g : ENCHANTMENT_GROUPS.keySet())
            g.setRelevantEnchantmentTypes(ENCHANTMENT_GROUPS.get(g).toArray(new EnchantmentType[0]));
        if (REMOVE_EMPTY_ITEM_GROUPS.get()) {
            ArrayList<ItemGroup> groups = Lists.newArrayList(ItemGroup.GROUPS);
            ArrayList<ItemGroup> result = Lists.newArrayList(ItemGroup.GROUPS);
            for (ItemGroup t : groups) {
                if (t == ItemGroup.SEARCH || t == ItemGroup.HOTBAR || t == ItemGroup.INVENTORY || t.getRelevantEnchantmentTypes().length > 0) continue;
                boolean b = false;
                for (Item item : ForgeRegistries.ITEMS)
                    if (item.group == t) {
                        b = true;
                        break;
                    }
                if (!b) result.remove(t);
            }
            ItemGroup.GROUPS = new ItemGroup[0];
            try {
                Method d = ItemGroup.class.getDeclaredMethod("addGroupSafe", int.class, ItemGroup.class);
                d.setAccessible(true);
                for (int i = 0; i < result.size(); i++) {
                    ItemGroup g = result.get(i);
                    g.index = i;
                    d.invoke(null, i, g);
                }
            } catch (ReflectiveOperationException e) {
                Logger.error("Could not remove empty item groups:");
                e.printStackTrace();
            }
        }
        if (searchReload) Minecraft.getInstance().populateSearchTreeManager();
        dump(DUMP_BLOCKS_AFTER, DUMP_BLOCKS_AFTER_NON_DEFAULT, DUMP_ITEMS_AFTER, DUMP_ITEMS_AFTER_NON_DEFAULT, DUMP_ENCHANTMENTS_AFTER, DUMP_GROUPS_AFTER);
    }

    private static void dump(ForgeConfigSpec.BooleanValue blocks, ForgeConfigSpec.BooleanValue blocksNonDefault, ForgeConfigSpec.BooleanValue items, ForgeConfigSpec.BooleanValue itemsNonDefault, ForgeConfigSpec.BooleanValue enchantments, ForgeConfigSpec.BooleanValue groups) {
        if (blocks.get() || blocksNonDefault.get()) {
            Logger.forceInfo("Blocks:");
            for (Block b : ForgeRegistries.BLOCKS) {
                if (b.properties.isAir) continue;
                float hardness = b.properties.hardness;
                float resistance = b.blastResistance;
                int harvestLevel = b.getHarvestLevel(b.getDefaultState());
                String harvestTool = b.getHarvestTool(b.getDefaultState()) == null ? "" : b.getHarvestTool(b.getDefaultState()).getName();
                boolean requiresTool = b.properties.requiresTool;
                int lightLevel = b.properties.lightLevel.applyAsInt(b.getDefaultState());
                float slipperiness = b.slipperiness;
                float speedFactor = b.speedFactor;
                float jumpFactor = b.jumpFactor;
                String soundType = getSoundType(b.soundType);
                StringBuilder sb = new StringBuilder(b.getRegistryName().toString()).append(" - ");
                if (blocks.get()) {
                    sb.append("hardness: ").append(hardness).append(", ");
                    sb.append("resistance: ").append(resistance).append(", ");
                    sb.append("harvest level: ").append(harvestLevel).append(", ");
                    sb.append("harvest tool: ").append(harvestTool).append(", ");
                    sb.append("requires tool: ").append(requiresTool).append(", ");
                    sb.append("light level: ").append(lightLevel).append(", ");
                    sb.append("slipperiness: ").append(slipperiness).append(", ");
                    sb.append("speed factor: ").append(speedFactor).append(", ");
                    sb.append("jump factor: ").append(jumpFactor).append(", ");
                    sb.append("sound type: ").append(soundType).append(", ");
                } else if (blocksNonDefault.get()) {
                    if (hardness != 0) sb.append("hardness: ").append(hardness).append(", ");
                    if (resistance != 0) sb.append("resistance: ").append(resistance).append(", ");
                    if (harvestLevel != -1) sb.append("harvest level: ").append(harvestLevel).append(", ");
                    if (!harvestTool.equals("")) sb.append("harvest tool: ").append(harvestTool).append(", ");
                    if (requiresTool) sb.append("requires tool: ").append(requiresTool).append(", ");
                    if (lightLevel != 0) sb.append("light level: ").append(lightLevel).append(", ");
                    if (slipperiness != 0.6f) sb.append("slipperiness: ").append(slipperiness).append(", ");
                    if (speedFactor != 1) sb.append("speed factor: ").append(speedFactor).append(", ");
                    if (jumpFactor != 1) sb.append("jump factor: ").append(jumpFactor).append(", ");
                    if (soundType != null && !soundType.equals("stone")) sb.append("sound type: ").append(soundType).append(", ");
                }
                if (!sb.toString().equals(b.getRegistryName().toString() + " - ")) Logger.forceInfo(sb.substring(0, sb.length() - 2));
            }
        }
        if (items.get() || itemsNonDefault.get()) {
            Logger.forceInfo("Items:");
            for (Item i : ForgeRegistries.ITEMS) {
                int maxDamage = i.maxDamage;
                int maxStackSize = i.maxStackSize;
                String group = i.group == null ? "" : i.group.getPath();
                boolean isImmuneToFire = i.isImmuneToFire;
                String rarity = i.rarity.toString().toLowerCase();
                int enchantability = i.getItemEnchantability();
                String toolTypes = "";
                if (i.getToolTypes(null).size() > 0) for (ToolType t : i.getToolTypes(null)) toolTypes += "tool type: " + t.getName() + " (harvest level: " + i.getHarvestLevel(null, t, null, null) + "), ";
                StringBuilder sb = new StringBuilder(i.getRegistryName().toString()).append(" - ");
                if (items.get()) {
                    sb.append("max damage: ").append(maxDamage).append(", ");
                    sb.append("max stack size: ").append(maxStackSize).append(", ");
                    sb.append("group: ").append(group).append(", ");
                    sb.append("is immune to fire: ").append(isImmuneToFire).append(", ");
                    sb.append("rarity: ").append(rarity).append(", ");
                    sb.append("enchantability: ").append(enchantability).append(", ");
                    sb.append(toolTypes).append(", ");
                } else if (itemsNonDefault.get()) {
                    if (maxDamage != 0) sb.append("max damage: ").append(maxDamage).append(", ");
                    if (maxStackSize != 64) sb.append("max stack size: ").append(maxStackSize).append(", ");
                    if (!group.equals("")) sb.append("group: ").append(group).append(", ");
                    if (isImmuneToFire) sb.append("is immune to fire: ").append(isImmuneToFire).append(", ");
                    if (!rarity.equals("common")) sb.append("rarity: ").append(rarity).append(", ");
                    if (enchantability != DEFAULT_ENCHANTABILITY.get()) sb.append("enchantability: ").append(enchantability).append(", ");
                    if (!toolTypes.equals("")) sb.append(toolTypes).append(", ");
                }
                sb.append(toolTypes);
                if (i instanceof ArmorItem) {
                    int armor = ((ArmorItem) i).damageReduceAmount;
                    float toughness = ((ArmorItem) i).toughness;
                    float knockbackResistance = ((ArmorItem) i).knockbackResistance;
                    if (items.get()) {
                        sb.append("armor: ").append(armor).append(", ");
                        sb.append("toughness: ").append(toughness).append(", ");
                        sb.append("knockback resistance: ").append(knockbackResistance).append(", ");
                    } else if (itemsNonDefault.get()) {
                        if (armor != 0) sb.append("armor: ").append(armor).append(", ");
                        if (toughness != 0) sb.append("toughness: ").append(toughness).append(", ");
                        if (knockbackResistance != 0) sb.append("knockback resistance: ").append(knockbackResistance).append(", ");
                    }
                }
                if (i instanceof ToolItem || i instanceof SwordItem || i instanceof TridentItem) {
                    if (i instanceof ToolItem) sb.append("efficiency: ").append(((ToolItem) i).efficiency).append(", ");
                    float damage = 0, speed = 0;
                    if (i instanceof ToolItem) {
                        damage = ((ToolItem) i).attackDamage;
                        AttributeModifier speedAttr = ((ToolItem) i).toolAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        speed = speedAttr != null ? (float) speedAttr.getAmount() : 0;
                    } else if (i instanceof SwordItem) {
                        damage = ((SwordItem) i).attackDamage;
                        AttributeModifier speedAttr = ((SwordItem) i).attributeModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        speed = speedAttr != null ? (float) speedAttr.getAmount() : 0;
                    } else if (i instanceof TridentItem) {
                        AttributeModifier damageAttr = ((TridentItem) i).tridentAttributes.get(Attributes.ATTACK_DAMAGE).stream().findFirst().orElse(null);
                        AttributeModifier speedAttr = ((TridentItem) i).tridentAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        damage = damageAttr != null ? (float) damageAttr.getAmount() : 0;
                        speed = speedAttr != null ? (float) speedAttr.getAmount() : 0;
                    }
                    sb.append("attack damage: ").append(damage + 1f).append(", ");
                    sb.append("attack speed: ").append(speed + 4f).append(", ");
                }
                if (!sb.toString().equals(i.getRegistryName().toString() + " - ")) Logger.forceInfo(sb.substring(0, sb.length() - 2));
            }
        }
        if (enchantments.get()) {
            Logger.forceInfo("Enchantments:");
            for (Enchantment e : ForgeRegistries.ENCHANTMENTS) Logger.forceInfo(e.getRegistryName().toString() + " - rarity: " + e.getRarity().toString().toLowerCase());
        }
        if (groups.get()) {
            Logger.forceInfo("Item groups:");
            for (ItemGroup g : ItemGroup.GROUPS) {
                EnchantmentType[] l = g.getRelevantEnchantmentTypes();
                StringBuilder sb = new StringBuilder(g.getPath());
                if (l.length > 0) sb.append(" - enchantments: ");
                for (EnchantmentType t : l) sb.append(t.name().toLowerCase()).append(", ");
                Logger.forceInfo(l.length > 0 ? sb.substring(0, sb.length() - 2) : sb.toString());
            }
        }
    }

    private static String getSoundType(SoundType t) {
        if (t == SoundType.WOOD) return "wood";
        if (t == SoundType.GROUND) return "ground";
        if (t == SoundType.PLANT) return "plant";
        if (t == SoundType.LILY_PADS) return "lily_pads";
        if (t == SoundType.STONE) return "stone";
        if (t == SoundType.METAL) return "metal";
        if (t == SoundType.GLASS) return "glass";
        if (t == SoundType.CLOTH) return "cloth";
        if (t == SoundType.SAND) return "sand";
        if (t == SoundType.SNOW) return "snow";
        if (t == SoundType.LADDER) return "ladder";
        if (t == SoundType.ANVIL) return "anvil";
        if (t == SoundType.SLIME) return "slime";
        if (t == SoundType.HONEY) return "honey";
        if (t == SoundType.WET_GRASS) return "wet_grass";
        if (t == SoundType.CORAL) return "coral";
        if (t == SoundType.BAMBOO) return "bamboo";
        if (t == SoundType.BAMBOO_SAPLING) return "bamboo_sapling";
        if (t == SoundType.SCAFFOLDING) return "scaffolding";
        if (t == SoundType.SWEET_BERRY_BUSH) return "sweet_berry_bush";
        if (t == SoundType.CROP) return "crop";
        if (t == SoundType.STEM) return "stem";
        if (t == SoundType.VINE) return "vine";
        if (t == SoundType.NETHER_WART) return "nether_wart";
        if (t == SoundType.LANTERN) return "lantern";
        if (t == SoundType.HYPHAE) return "hyphae";
        if (t == SoundType.NYLIUM) return "nylium";
        if (t == SoundType.FUNGUS) return "fungus";
        if (t == SoundType.ROOT) return "root";
        if (t == SoundType.SHROOMLIGHT) return "shroomlight";
        if (t == SoundType.NETHER_VINE) return "nether_vine";
        if (t == SoundType.NETHER_VINE_LOWER_PITCH) return "nether_vine_lower_pitch";
        if (t == SoundType.SOUL_SAND) return "soul_sand";
        if (t == SoundType.SOUL_SOIL) return "soul_soil";
        if (t == SoundType.BASALT) return "basalt";
        if (t == SoundType.WART) return "wart";
        if (t == SoundType.NETHERRACK) return "netherrack";
        if (t == SoundType.NETHER_BRICK) return "nether_brick";
        if (t == SoundType.NETHER_SPROUT) return "nether_sprout";
        if (t == SoundType.NETHER_ORE) return "nether_ore";
        if (t == SoundType.BONE) return "bone";
        if (t == SoundType.NETHERITE) return "netherite";
        if (t == SoundType.ANCIENT_DEBRIS) return "ancient_debris";
        if (t == SoundType.LODESTONE) return "lodestone";
        if (t == SoundType.CHAIN) return "chain";
        if (t == SoundType.NETHER_GOLD) return "nether_gold";
        if (t == SoundType.GILDED_BLACKSTONE) return "gilded_blackstone";
        return null;
    }

    private static Properties.Item itemProperties(Item item) {
        return item instanceof ArmorItem ? new Properties.Armor() : item instanceof TieredItem || item instanceof TridentItem ? new Properties.Tool() : new Properties.Item();
    }
}
