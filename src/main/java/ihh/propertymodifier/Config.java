package ihh.propertymodifier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Function5;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TieredItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings("FieldMayBeFinal")
public final class Config {
    private static final LinkedHashMap<Block, Properties.Block> BLOCKS = new LinkedHashMap<>();
    private static final LinkedHashMap<Item, Properties.Item> ITEMS = new LinkedHashMap<>();
    private static final LinkedHashMap<Enchantment, Properties.Enchantment> ENCHANTMENTS = new LinkedHashMap<>();
    public static final HashMap<Block, Float> MIXIN_HARDNESS = new HashMap<>();
    public static final HashMap<Block, Integer> MIXIN_HARVEST_LEVEL = new HashMap<>();
    public static final HashMap<Block, ToolType> MIXIN_HARVEST_TOOL = new HashMap<>();
    public static final HashMap<Block, Integer> MIXIN_LIGHT_LEVEL = new HashMap<>();
    public static final HashMap<Block, Boolean> MIXIN_REQUIRES_TOOL = new HashMap<>();
    public static final HashMap<Item, Integer> MIXIN_ENCHANTABILITY = new HashMap<>();
    public static final HashMap<Item, LazyValue<Ingredient>> MIXIN_REPAIR_MATERIAL = new HashMap<>();
    public static final HashMap<TieredItem, Integer> MIXIN_TOOL_HARVEST_LEVEL = new HashMap<>();
    public static final HashMap<Enchantment, Integer> MIXIN_MAX_LEVEL = new HashMap<>();
    public static final HashMap<Enchantment, Pair<Integer, Integer>> MIXIN_MIN_ENCHANTABILITY = new HashMap<>();
    public static final HashMap<Enchantment, Triple<Integer, Integer, Boolean>> MIXIN_MAX_ENCHANTABILITY = new HashMap<>();
    public static final HashMap<Enchantment, Boolean> MIXIN_IS_TREASURE = new HashMap<>();
    public static final HashMap<Enchantment, Boolean> MIXIN_CAN_VILLAGER_TRADE = new HashMap<>();
    public static final HashMap<Enchantment, Boolean> MIXIN_CAN_GENERATE_IN_LOOT = new HashMap<>();
    public static final HashMap<Enchantment, HashSet<Enchantment>> MIXIN_CAN_COMBINE = new HashMap<>();
    static ForgeConfigSpec SPEC;
    static ForgeConfigSpec.BooleanValue LOG_SUCCESSFUL;
    static ForgeConfigSpec.BooleanValue LOG_ERRORS;
    static ForgeConfigSpec.BooleanValue REMOVE_EMPTY_ITEM_GROUPS;
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
    private static ForgeConfigSpec.ConfigValue<List<String>> MAX_LEVEL;
    private static ForgeConfigSpec.ConfigValue<List<String>> MIN_ENCHANTABILITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> MAX_ENCHANTABILITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> IS_TREASURE;
    private static ForgeConfigSpec.ConfigValue<List<String>> CAN_VILLAGER_TRADE;
    private static ForgeConfigSpec.ConfigValue<List<String>> CAN_GENERATE_IN_LOOT;
    private static ForgeConfigSpec.ConfigValue<List<String>> ENCHANTMENT_RARITY;
    private static ForgeConfigSpec.ConfigValue<List<String>> CAN_COMBINE;
    private static ForgeConfigSpec.ConfigValue<List<String>> COMPOSTER_INPUTS;
    static ForgeConfigSpec.BooleanValue COMPOSTER_CLEAR;
    private static ForgeConfigSpec.ConfigValue<List<String>> AXE_BLOCKS;
    static ForgeConfigSpec.BooleanValue AXE_CLEAR;
    private static ForgeConfigSpec.ConfigValue<List<String>> SHOVEL_BLOCKS;
    static ForgeConfigSpec.BooleanValue SHOVEL_CLEAR;
    private static ForgeConfigSpec.ConfigValue<List<String>> HOE_BLOCKS;
    static ForgeConfigSpec.BooleanValue HOE_CLEAR;

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
        builder.comment("Settings related to tools. Only tool and sword items can be affected, anything else will be skipped.").push("tools");
        ATTACK_DAMAGE = builder.comment("Sets the tool/sword attack damage.").define("attack_damage", new ArrayList<>());
        ATTACK_SPEED = builder.comment("Sets the tool/sword attack speed.").define("attack_speed", new ArrayList<>());
        TOOL_HARVEST_LEVEL = builder.comment("Sets the tool harvest level. 0 is wood/gold, 1 is stone, 2 is iron, 3 is diamond/netherite. Does not work for swords.").define("harvest_level", new ArrayList<>());
        EFFICIENCY = builder.comment("Sets the efficiency. Wood has 2, stone has 4, iron has 6, diamond has 8, netherite has 9, gold has 12. Does not work for swords.").define("efficiency", new ArrayList<>());
        builder.pop();
        builder.pop();
        builder.comment("Settings related to enchantments. Format is always \"enchantment;value\", with enchantment being in the format \"modid:enchantmentid\", unless stated otherwise. Alternatively, you can use \"any\" to apply the setting to all enchantments (usable e.g. to make all enchantments have the same rarity.) Note that entries are read from left to right, so you should put \"any\"-entries at the start, as they will overwrite anything stated before them. NONE OF THESE WORK! (except for rarity, rarity works)").push("enchantments");
        MAX_LEVEL = builder.comment("Sets the max enchantment level.").define("max_level", new ArrayList<>());
        MIN_ENCHANTABILITY = builder.comment("Sets the min enchantability required for this enchantment. Format is \"enchantment;constant;variable\". Calculation is performed as follows: constant + enchantmentlevel * variable. Vanilla values are:", "\"minecraft:aqua_affinity;1;0\", \"minecraft:bane_of_arthropods;-3;8\", \"minecraft:binding_curse;25;0\", \"minecraft:blast_protection;-3;8\", \"minecraft:channeling;20;0\", \"minecraft:depth_strider;0;10\", \"minecraft:efficiency;-9;10\", \"minecraft:feather_falling;-1;6\", \"minecraft:fire_aspect;-10;20\", \"minecraft:fire_protection;2;8\", \"minecraft:flame;20;0\", \"minecraft:fortune;6;9\", \"minecraft:frost_walker;0;10\", \"minecraft:impaling;-7;8\", \"minecraft:infinity;20;0\", \"minecraft:knockback;-15;20\", \"minecraft:looting;6;9\", \"minecraft:loyalty;5;7\", \"minecraft:luck_of_the_sea;6;9\", \"minecraft:lure;6;9\", \"minecraft:mending;0;25\", \"minecraft:multishot;20;0\", \"minecraft:piercing;-9;10\", \"minecraft:power;-9;10\", \"minecraft:projectile_protection;-3;6\", \"minecraft:protection;-10;11\", \"minecraft:punch;-8;20\", \"minecraft:quick_charge;-8;20\", \"minecraft:respiration;0;10\", \"minecraft:riptide;10;7\", \"minecraft:sharpness;-10;11\", \"minecraft:silk_touch;15;0\", \"minecraft:smite;-3;8\", \"minecraft:soul_speed;0;10\", \"minecraft:sweeping;-4;9\", \"minecraft:thorns;-10;20\", \"minecraft:unbreaking;-3;8\", \"minecraft:vanishing_curse;25;0\"").define("min_enchantability", new ArrayList<>());
        MAX_ENCHANTABILITY = builder.comment("Sets the max enchantability required for this enchantment. Format is \"enchantment;constant;variable;add\". Calculation is performed as follows: constant + enchantmentlevel * variable. If \"add\" is set to true, this value will be added to the min enchantability of this enchantment, if set to false, this will not happen. Vanilla values are:", "\"minecraft:aqua_affinity;40;0;true\", \"minecraft:bane_of_arthropods;20;0;true\", \"minecraft:binding_curse;50;0;false\", \"minecraft:blast_protection;8;0;true\", \"minecraft:channeling;50;0;false\", \"minecraft:depth_strider;15;0;true\", \"minecraft:efficiency;50;0;true\", \"minecraft:feather_falling;6;0;true\", \"minecraft:fire_aspect;50;0;true\", \"minecraft:fire_protection;8;0;true\", \"minecraft:flame;50;0;false\", \"minecraft:fortune;50;0;true\", \"minecraft:frost_walker;15;0;true\", \"minecraft:impaling;20;0;true\", \"minecraft:infinity;50;0;false\", \"minecraft:knockback;50;0;true\", \"minecraft:looting;50;0;true\", \"minecraft:loyalty;50;0;false\", \"minecraft:luck_of_the_sea;50;0;true\", \"minecraft:lure;50;0;true\", \"minecraft:mending;50;0;true\", \"minecraft:multishot;50;0;false\", \"minecraft:piercing;50;0;false\", \"minecraft:power;15;0;true\", \"minecraft:projectile_protection;6;0;true\", \"minecraft:protection;11;0;true\", \"minecraft:punch;25;0;true\", \"minecraft:quick_charge;50;0;false\", \"minecraft:respiration;30;0;true\", \"minecraft:riptide;50;0;false\", \"minecraft:sharpness;20;0;true\", \"minecraft:silk_touch;50;0;true\", \"minecraft:smite;20;0;true\", \"minecraft:soul_speed;15;0;true\", \"minecraft:sweeping;15;0;true\", \"minecraft:thorns;50;0;true\", \"minecraft:unbreaking;50;0;true\", \"minecraft:vanishing_curse;50;0;false\"").define("max_enchantability", new ArrayList<>());
        IS_TREASURE = builder.comment("Whether this item is considered a treasure enchantment or not. Is true for binding_curse, frost_walker, mending, soul_speed, vanishing_curse.").define("is_treasure", new ArrayList<>());
        CAN_VILLAGER_TRADE = builder.comment("Whether this item can be traded from villagers. True for every vanilla enchantment except soul_speed.").define("can_villager_trade", new ArrayList<>());
        CAN_GENERATE_IN_LOOT = builder.comment("Whether this item can generate from loot tables. True for every vanilla enchantment except soul_speed.").define("can_generate_in_loot", new ArrayList<>());
        ENCHANTMENT_RARITY = builder.comment("The enchantment rarity of this enchantment. Can take the following values: common (10), uncommon (5), rare (2) and very_rare (1). Vanilla values are:", "\"minecraft:aqua_affinity;rare\", \"minecraft:bane_of_arthropods;uncommon\", \"minecraft:binding_curse;very_rare\", \"minecraft:blast_protection;rare\", \"minecraft:channeling;very_rare\", \"minecraft:depth_strider;rare\", \"minecraft:efficiency;common\", \"minecraft:feather_falling;uncommon\", \"minecraft:fire_aspect;rare\", \"minecraft:fire_protection;uncommon\", \"minecraft:flame;rare\", \"minecraft:fortune;rare\", \"minecraft:frost_walker;rare\", \"minecraft:impaling;rare\", \"minecraft:infinity;very_rare\", \"minecraft:knockback;uncommon\", \"minecraft:looting;rare\", \"minecraft:loyalty;uncommon\", \"minecraft:luck_of_the_sea;rare\", \"minecraft:lure;rare\", \"minecraft:mending;rare\", \"minecraft:multishot;rare\", \"minecraft:piercing;common\", \"minecraft:power;common\", \"minecraft:projectile_protection;uncommon\", \"minecraft:protection;common\", \"minecraft:punch;rare\", \"minecraft:quick_charge;rare\", \"minecraft:respiration;rare\", \"minecraft:riptide;rare\", \"minecraft:sharpness;common\", \"minecraft:silk_touch;very_rare\", \"minecraft:smite;uncommon\", \"minecraft:soul_speed;very_rare\", \"minecraft:sweeping;rare\", \"minecraft:thorns;very_rare\", \"minecraft:unbreaking;uncommon\", \"minecraft:vanishing_curse;very_rare\"").define("rarity", new ArrayList<>());
        CAN_COMBINE = builder.comment("Two enchantments that cannot be on the same item. Does not need to be set by both sides (e.g. by frost walker AND by depth strider), but it's recommended. Once you overwrite it for an enchantment, all previously existing restrictions will be removed, and you must re-add them if you still want them. Vanilla values are:", "\"minecraft:bane_of_arthropods;minecraft:sharpness\", \"minecraft:bane_of_arthropods;minecraft:smite\", \"minecraft:sharpness;minecraft:bane_of_arthropods\", \"minecraft:sharpness;minecraft:smite\", \"minecraft:smite;minecraft:bane_of_arthropods\", \"minecraft:smite;minecraft:sharpness\", \"minecraft:blast_protection;minecraft:fire_protection\", \"minecraft:blast_protection;minecraft:projectile_protection\", \"minecraft:blast_protection;minecraft:protection\", \"minecraft:fire_protection;minecraft:blast_protection\", \"minecraft:fire_protection;minecraft:projectile_protection\", \"minecraft:fire_protection;minecraft:protection\", \"minecraft:projectile_protection;minecraft:blast_protection\", \"minecraft:projectile_protection;minecraft:fire_protection\", \"minecraft:projectile_protection;minecraft:protection\", \"minecraft:protection;minecraft:blast_protection\", \"minecraft:protection;minecraft:fire_protection\", \"minecraft:protection;minecraft:projectile_protection\", \"minecraft:channeling;minecraft:riptide\", \"minecraft:loyalty;minecraft:riptide\", \"minecraft:riptide;minecraft:channeling\", \"minecraft:riptide;minecraft:loyalty\", \"minecraft:depth_strider;minecraft:frost_walker\", \"minecraft:frost_walker;minecraft:depth_strider\", \"minecraft:fortune;minecraft:silk_touch\", \"minecraft:silk_touch;minecraft:fortune\", \"minecraft:infinity;minecraft:mending\", \"minecraft:mending;minecraft:infinity\", \"minecraft:multishot;minecraft:piercing\", \"minecraft:piercing;minecraft:multishot\"").define("can_combine", new ArrayList<>());
        builder.pop();
        builder.push("composter");
        COMPOSTER_INPUTS = builder.comment("Define additional composter inputs here. Format is \"itemid;chance\", with item id being in the modid:itemid format and chance being a precentage between 0.0 and 1.0. Vanilla values are:", "\"minecraft:oak_leaves;0.3\", \"minecraft:spruce_leaves;0.3\", \"minecraft:birch_leaves;0.3\", \"minecraft:jungle_leaves;0.3\", \"minecraft:acacia_leaves;0.3\", \"minecraft:dark_oak_leaves;0.3\", \"minecraft:oak_sapling;0.3\", \"minecraft:spruce_sapling;0.3\", \"minecraft:birch_sapling;0.3\", \"minecraft:jungle_sapling;0.3\", \"minecraft:acacia_sapling;0.3\", \"minecraft:dark_oak_sapling;0.3\", \"minecraft:beetroot_seeds;0.3\", \"minecraft:dried_kelp;0.3\", \"minecraft:grass;0.3\", \"minecraft:kelp;0.3\", \"minecraft:melon_seeds;0.3\", \"minecraft:pumpkin_seeds;0.3\", \"minecraft:seagrass;0.3\", \"minecraft:sweet_berries;0.3\", \"minecraft:wheat_seeds;0.3\", \"minecraft:cactus;0.5\", \"minecraft:dried_kelp_block;0.5\", \"minecraft:melon_slice;0.5\", \"minecraft:nether_sprouts;0.5\", \"minecraft:sugar_cane;0.5\", \"minecraft:tall_grass;0.5\", \"minecraft:twisted_vines;0.5\", \"minecraft:vine;0.5\", \"minecraft:weeping_vines;0.5\", \"minecraft:crimson_roots;0.65\", \"minecraft:warped_roots;0.65\", \"minecraft:crimson_fungus;0.65\", \"minecraft:warped_fungus;0.65\", \"minecraft:brown_mushroom;0.65\", \"minecraft:red_mushroom;0.65\", \"minecraft:mushroom_stem;0.65\", \"minecraft:dandelion;0.65\", \"minecraft:poppy;0.65\", \"minecraft:orange_tulip;0.65\", \"minecraft:pink_tulip;0.65\", \"minecraft:red_tulip;0.65\", \"minecraft:white_tulip;0.65\", \"minecraft:allium;0.65\", \"minecraft:azure_bluet;0.65\", \"minecraft:blue_orchid;0.65\", \"minecraft:oxeye_daisy;0.65\", \"minecraft:cornflower;0.65\", \"minecraft:lily_of_the_valley;0.65\", \"minecraft:wither_rose;0.65\", \"minecraft:fern;0.65\", \"minecraft:large_fern;0.65\", \"minecraft:lilac;0.65\", \"minecraft:peony;0.65\", \"minecraft:rose_bush;0.65\", \"minecraft:sunflower;0.65\", \"minecraft:crimson_roots;0.65\", \"minecraft:warped_roots;0.65\", \"minecraft:wheat;0.65\", \"minecraft:potato;0.65\", \"minecraft:carrot;0.65\", \"minecraft:beetroot;0.65\", \"minecraft:apple;0.65\", \"minecraft:carved_pumpkin;0.65\", \"minecraft:cocoa_beans;0.65\", \"minecraft:lily_pad;0.65\", \"minecraft:melon;0.65\", \"minecraft:nether_wart;0.65\", \"minecraft:pumpkin;0.65\", \"minecraft:sea_pickle;0.65\", \"minecraft:shroomlight;0.65\", \"minecraft:brown_mushroom_block;0.85\", \"minecraft:red_mushroom_block;0.85\", \"minecraft:nether_wart_block;0.85\", \"minecraft:warped_wart_block;0.85\", \"minecraft:baked_potato;0.85\", \"minecraft:bread;0.85\", \"minecraft:cookie;0.85\", \"minecraft:hay_block;0.85\", \"minecraft:cake;1\", \"minecraft:pumpkin_pie;1\"").define("inputs", new ArrayList<>());
        COMPOSTER_CLEAR = builder.comment("Whether to clear the default composter inputs and have the composter inputs only contain the stuff defined here.").define("clear", false);
        builder.pop();
        builder.push("stripping");
        AXE_BLOCKS = builder.comment("Define additional stripping transitions here. Format is \"fromid;toid\", with both of them being in the modid:blockid format. Vanilla values are:", "\"minecraft:oak_log;minecraft:stripped_oak_log\", \"minecraft:oak_wood;minecraft:stripped_oak_wood\", \"minecraft:spruce_log;minecraft:stripped_spruce_log\", \"minecraft:spruce_wood;minecraft:stripped_spruce_wood\", \"minecraft:birch_log;minecraft:stripped_birch_log\", \"minecraft:birch_wood;minecraft:stripped_birch_wood\", \"minecraft:jungle_log;minecraft:stripped_jungle_log\", \"minecraft:jungle_wood;minecraft:stripped_jungle_wood\", \"minecraft:acacia_log;minecraft:stripped_acacia_log\", \"minecraft:acacia_wood;minecraft:stripped_acacia_wood\", \"minecraft:dark_oak_log;minecraft:stripped_dark_oak_log\", \"minecraft:dark_oak_wood;minecraft:stripped_dark_oak_wood\", \"minecraft:crimson_stem;minecraft:stripped_crimson_stem\", \"minecraft:crimson_hyphae;minecraft:stripped_crimson_hyphae\", \"minecraft:warped_stem;minecraft:stripped_warped_stem\", \"minecraft:warped_hyphae;minecraft:stripped_warped_hyphae\"").define("transitions", new ArrayList<>());
        AXE_CLEAR = builder.comment("Whether to clear the default stripping transitions and have the stripping transitions only contain the stuff defined here.").define("clear", false);
        builder.pop();
        builder.push("pathing");
        SHOVEL_BLOCKS = builder.comment("Define additional pathing transitions here. Format is \"fromid;toid\", with both of them being in the modid:blockid format. Vanilla values are:", "\"minecraft:grass_block;minecraft:grass_path\"").define("transitions", new ArrayList<>());
        SHOVEL_CLEAR = builder.comment("Whether to clear the default pathing transitions and have the pathing transitions only contain the stuff defined here.").define("clear", false);
        builder.pop();
        builder.push("tilling");
        HOE_BLOCKS = builder.comment("Define additional tilling transitions here. Format is \"fromid;toid\", with both of them being in the modid:blockid format. Vanilla values are:", "\"minecraft:grass_block;minecraft:farmland\", \"minecraft:grass_path;minecraft:farmland\", \"minecraft:dirt;minecraft:farmland\", \"minecraft:coarse_dirt;minecraft:dirt\"").define("transitions", new ArrayList<>());
        HOE_CLEAR = builder.comment("Whether to clear the default tilling transitions and have the tilling transitions only contain the stuff defined here.").define("clear", false);
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
        TIERED_REGISTRY.removeIf(e -> !(e instanceof TieredItem));
        TOOL_REGISTRY.removeIf(e -> !(e instanceof ToolItem));
        LinkedHashMap<String, ItemStack> m = new LinkedHashMap<>();
        for (String v : ITEM_GROUP.get()) {
            String[] s = v.split(";");
            Item i = fromRegistry(s[1], ITEM_REGISTRY);
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
        for (Map.Entry<Block, Float> entry : getMap(HARDNESS, BLOCK_REGISTRY, Config::parseFloat, e -> e >= 0 || e == -1, "Hardness must be either -1, 0 or greater than 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARDNESS = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : getMap(RESISTANCE, BLOCK_REGISTRY, Config::parseFloat, e -> e >= 0 && e <= 3600000, "Resistance must be between 0 and 3600000").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.RESISTANCE = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Integer> entry : getMap(HARVEST_LEVEL, BLOCK_REGISTRY, Config::parseInt, e -> e > -2, "Harvest level must be at least -1").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARVEST_LEVEL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, ToolType> entry : getMap(HARVEST_TOOL, BLOCK_REGISTRY, Config::parseToolType, e -> true, "").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARVEST_TOOL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Boolean> entry : getMap(REQUIRES_TOOL, BLOCK_REGISTRY, Config::parseBoolean, e -> true, "").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.REQUIRES_TOOL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Integer> entry : getMap(LIGHT_LEVEL, BLOCK_REGISTRY, Config::parseInt, e -> e > -1 && e < 16, "Light level must be between 0 and 15").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.LIGHT_LEVEL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : getMap(SLIPPERINESS, BLOCK_REGISTRY, Config::parseFloat, e -> e >= 0, "Slipperiness must be at least 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SLIPPERINESS = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : getMap(SPEED_FACTOR, BLOCK_REGISTRY, Config::parseFloat, e -> e >= 0, "Speed factor must be at least 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SPEED_FACTOR = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : getMap(JUMP_FACTOR, BLOCK_REGISTRY, Config::parseFloat, e -> e >= 0, "Jump factor must be at least 0").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.JUMP_FACTOR = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, SoundType> entry : getMap(SOUND_TYPE, BLOCK_REGISTRY, Config::parseSoundType, e -> true, "").entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SOUND_TYPE = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : getMap(MAX_DAMAGE, ITEM_REGISTRY, Config::parseInt, e -> e > 0, "Durability must be at least 1").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.MAX_DAMAGE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : getMap(MAX_STACK_SIZE, ITEM_REGISTRY, Config::parseInt, e -> e > 0, "Max stack size must be at least 1").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.MAX_STACK_SIZE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, ItemGroup> entry : getMap(GROUP, ITEM_REGISTRY, Config::parseItemGroup, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.GROUP = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Boolean> entry : getMap(IS_IMMUNE_TO_FIRE, ITEM_REGISTRY, Config::parseBoolean, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.IS_IMMUNE_TO_FIRE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Rarity> entry : getMap(RARITY, ITEM_REGISTRY, Config::parseRarity, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.RARITY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : getMap(ENCHANTABILITY, ITEM_REGISTRY, Config::parseInt, e -> e > -1, "Enchantability must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.ENCHANTABILITY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, LazyValue<Ingredient>> entry : getMap(REPAIR_MATERIAL, ITEM_REGISTRY, Config::parseRepairMaterial, e -> true, "").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.REPAIR_MATERIAL = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : getMap(ARMOR, ARMOR_REGISTRY, Config::parseInt, e -> e > -1, "Armor must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).ARMOR = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : getMap(TOUGHNESS, ARMOR_REGISTRY, Config::parseFloat, e -> e >= 0, "Toughness must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).TOUGHNESS = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : getMap(KNOCKBACK_RESISTANCE, ARMOR_REGISTRY, Config::parseFloat, e -> e >= 0, "Knockback resistance must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).KNOCKBACK_RESISTANCE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : getMap(ATTACK_DAMAGE, TIERED_REGISTRY, Config::parseFloat, e -> e >= 0, "Attack damage must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).ATTACK_DAMAGE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : getMap(ATTACK_SPEED, TIERED_REGISTRY, Config::parseFloat, e -> e >= 0, "Attack speed must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).ATTACK_SPEED = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : getMap(TOOL_HARVEST_LEVEL, TOOL_REGISTRY, Config::parseInt, e -> e > -2, "Harvest level must be at least -1").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).HARVEST_LEVEL = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : getMap(EFFICIENCY, TOOL_REGISTRY, Config::parseFloat, e -> e >= 0, "Efficiency must be at least 0").entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).EFFICIENCY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Integer> entry : getMap(MAX_LEVEL, ENCHANTMENT_REGISTRY, Config::parseInt, e -> e > 0, "Max level must be at least 1").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MAX_LEVEL = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Pair<Integer, Integer>> entry : pairMap(MIN_ENCHANTABILITY, ENCHANTMENT_REGISTRY, Config::parseInt, e -> true, Config::parseInt, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MIN_ENCHANTABILITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Triple<Integer, Integer, Boolean>> entry : tripleMap(MAX_ENCHANTABILITY, ENCHANTMENT_REGISTRY, Config::parseInt, e -> true, Config::parseInt, e -> true, Config::parseBoolean, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MAX_ENCHANTABILITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : getMap(IS_TREASURE, ENCHANTMENT_REGISTRY, Config::parseBoolean, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.IS_TREASURE = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : getMap(CAN_VILLAGER_TRADE, ENCHANTMENT_REGISTRY, Config::parseBoolean, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.CAN_VILLAGER_TRADE = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : getMap(CAN_GENERATE_IN_LOOT, ENCHANTMENT_REGISTRY, Config::parseBoolean, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.CAN_GENERATE_IN_LOOT = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Enchantment.Rarity> entry : getMap(ENCHANTMENT_RARITY, ENCHANTMENT_REGISTRY, Config::parseEnchantmentRarity, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.RARITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Enchantment> entry : getMap(CAN_COMBINE, ENCHANTMENT_REGISTRY, Config::parseEnchantment, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            if (prop.INCOMPATIBLES == null) prop.INCOMPATIBLES = new HashSet<>();
            prop.INCOMPATIBLES.add(entry.getValue());
            ENCHANTMENTS.put(entry.getKey(), prop);
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
        boolean searchReload = false;
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
            if (prop.CAN_GENERATE_IN_LOOT != null)
                MIXIN_CAN_GENERATE_IN_LOOT.put(enchantment, prop.CAN_GENERATE_IN_LOOT);
            if (prop.INCOMPATIBLES != null) MIXIN_CAN_COMBINE.put(enchantment, prop.INCOMPATIBLES);
        }
        if (COMPOSTER_CLEAR.get()) ComposterBlock.CHANCES.clear();
        if (AXE_CLEAR.get()) AxeItem.BLOCK_STRIPPING_MAP = new HashMap<>();
        else AxeItem.BLOCK_STRIPPING_MAP = new HashMap<>(AxeItem.BLOCK_STRIPPING_MAP);
        if (SHOVEL_CLEAR.get()) ShovelItem.SHOVEL_LOOKUP.clear();
        if (HOE_CLEAR.get()) HoeItem.HOE_LOOKUP.clear();
        for (Map.Entry<Item, Float> entry : getMap(COMPOSTER_INPUTS, ITEM_REGISTRY, Config::parseFloat, e -> e >= 0 && e <= 1, "Composter probability must be between 0 and 1").entrySet())
            if (entry.getValue() != null) ComposterBlock.CHANCES.put(entry.getKey(), (float) entry.getValue());
        for (Map.Entry<Block, Block> entry : getMap(AXE_BLOCKS, BLOCK_REGISTRY, Config::parseBlock, e -> true, "").entrySet())
            if (entry.getValue() != null) AxeItem.BLOCK_STRIPPING_MAP.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Block, Block> entry : getMap(SHOVEL_BLOCKS, BLOCK_REGISTRY, Config::parseBlock, e -> true, "").entrySet())
            if (entry.getValue() != null)
                ShovelItem.SHOVEL_LOOKUP.put(entry.getKey(), entry.getValue().getDefaultState());
        for (Map.Entry<Block, Block> entry : getMap(HOE_BLOCKS, BLOCK_REGISTRY, Config::parseBlock, e -> true, "").entrySet())
            if (entry.getValue() != null) HoeItem.HOE_LOOKUP.put(entry.getKey(), entry.getValue().getDefaultState());
        for (Item item : ForgeRegistries.ITEMS) if (item.group != null && item.group.getPath().equals("none")) item.group = null;
        if (REMOVE_EMPTY_ITEM_GROUPS.get()) {
            ArrayList<ItemGroup> groups = Lists.newArrayList(ItemGroup.GROUPS);
            ArrayList<ItemGroup> result = Lists.newArrayList(ItemGroup.GROUPS);
            for (ItemGroup t : groups) {
                if (t == ItemGroup.SEARCH || t == ItemGroup.HOTBAR || t == ItemGroup.INVENTORY) continue;
                boolean b = false;
                if (t.getRelevantEnchantmentTypes().length > 0) b = true;
                else for (Item item : ForgeRegistries.ITEMS)
                    if (item.group == t) {
                        b = true;
                        break;
                    }
                if (!b) result.remove(t);
            }
            ItemGroup.GROUPS = result.toArray(new ItemGroup[0]);
        }
        if (searchReload) Minecraft.getInstance().populateSearchTreeManager();
    }

    private static <T extends IForgeRegistryEntry<T>, U> LinkedHashMap<T, U> getMap(ForgeConfigSpec.ConfigValue<List<String>> l, List<? extends T> r, Function5<String, String, String, Predicate<U>, String, U> f, Predicate<U> p, String e) {
        LinkedHashMap<T, U> m = new LinkedHashMap<>();
        for (String x : l.get()) {
            String[] s = split(x, 2, l);
            if (s == null) continue;
            U u = f.apply(s[1], x, getPath(l.getPath()), p, e);
            if (u == null) continue;
            if (s[0].equals("any")) {
                for (T t : r) {
                    m.put(t, u);
                    logProperty(getLast(l.getPath()), t, u);
                }
            } else {
                T t = fromRegistry(s[0], r);
                if (t != null) {
                    m.put(t, u);
                    logProperty(getLast(l.getPath()), t, u);
                }
            }
        }
        return m;
    }

    private static <T extends IForgeRegistryEntry<T>, U, V> LinkedHashMap<T, Pair<U, V>> pairMap(ForgeConfigSpec.ConfigValue<List<String>> l, List<? extends T> r, Function5<String, String, String, Predicate<U>, String, U> fu, Predicate<U> pu, Function5<String, String, String, Predicate<V>, String, V> fv, Predicate<V> pv) {
        LinkedHashMap<T, Pair<U, V>> m = new LinkedHashMap<>();
        for (String x : l.get()) {
            String[] s = split(x, 3, l);
            if (s == null) continue;
            U u = fu.apply(s[1], x, getPath(l.getPath()), pu, "");
            if (u == null) continue;
            V v = fv.apply(s[2], x, getPath(l.getPath()), pv, "");
            if (v == null) continue;
            if (s[0].equals("any")) {
                for (T t : r) {
                    m.put(t, new Pair<>(u, v));
                    logProperty(getLast(l.getPath()), t, u);
                }
            } else {
                T t = fromRegistry(s[0], r);
                if (t != null) {
                    m.put(t, new Pair<>(u, v));
                    logProperty(getLast(l.getPath()), t, u);
                }
            }
        }
        return m;
    }

    private static <T extends IForgeRegistryEntry<T>, U, V, W> LinkedHashMap<T, Triple<U, V, W>> tripleMap(ForgeConfigSpec.ConfigValue<List<String>> l, List<? extends T> r, Function5<String, String, String, Predicate<U>, String, U> fu, Predicate<U> pu, Function5<String, String, String, Predicate<V>, String, V> fv, Predicate<V> pv, Function5<String, String, String, Predicate<W>, String, W> fw, Predicate<W> pw) {
        LinkedHashMap<T, Triple<U, V, W>> m = new LinkedHashMap<>();
        for (String x : l.get()) {
            String[] s = split(x, 4, l);
            if (s == null) continue;
            U u = fu.apply(s[1], x, getPath(l.getPath()), pu, "");
            if (u == null) continue;
            V v = fv.apply(s[2], x, getPath(l.getPath()), pv, "");
            if (v == null) continue;
            W w = fw.apply(s[3], x, getPath(l.getPath()), pw, "");
            if (w == null) continue;
            if (s[0].equals("any")) {
                for (T t : r) {
                    m.put(t, new Triple<>(u, v, w));
                    logProperty(getLast(l.getPath()), t, u);
                }
            } else {
                T t = fromRegistry(s[0], r);
                if (t != null) {
                    m.put(t, new Triple<>(u, v, w));
                    logProperty(getLast(l.getPath()), t, u);
                }
            }
        }
        return m;
    }

    private static Boolean parseBoolean(String v, String e, String n, Predicate<Boolean> p, String m) {
        switch (v) {
            case "true":
                return true;
            case "false":
                return false;
        }
        Logger.error(v + " is not a boolean (is invalid for " + e + " in " + n + ")");
        return null;
    }

    private static Integer parseInt(String v, String e, String n, Predicate<Integer> p, String m) {
        try {
            int r = Integer.parseInt(v);
            if (!p.test(r))
                Logger.error(m + " (is invalid for " + e + " in " + n + ")");
            else return r;
        } catch (NumberFormatException x) {
            Logger.error(v + " is not an integer (is invalid for " + e + " in " + n + ")");
        }
        return null;
    }

    private static Float parseFloat(String v, String e, String n, Predicate<Float> c, String m) {
        try {
            float r = Float.parseFloat(v);
            if (!c.test(r))
                Logger.error(m + " (is invalid for " + e + " in " + n + ")");
            else return r;
        } catch (NumberFormatException x) {
            Logger.error(v + " is not a number (is invalid for " + e + " in " + n + ")");
        }
        return null;
    }

    private static Block parseBlock(String v, String e, String n, Predicate<Block> p, String m) {
        Block b = fromRegistry(v, new ArrayList<>(ForgeRegistries.BLOCKS.getValues()));
        if (b == null) Logger.error("Unknown block " + v + " (is invalid for " + e + " in " + n + ")");
        else if (b.properties.isAir)
            Logger.error("Invalid air-like block " + v + " (is invalid for " + e + " in " + n + ")");
        else if (p.test(b)) return b;
        else Logger.error(m + " " + v + " (is invalid for " + e + " in " + n + ")");
        return null;
    }

    private static Enchantment parseEnchantment(String v, String e, String n, Predicate<Enchantment> p, String m) {
        Enchantment t = fromRegistry(v, new ArrayList<>(ForgeRegistries.ENCHANTMENTS.getValues()));
        if (t == null) Logger.error("Unknown enchantment " + v + " (is invalid for " + e + " in " + n + ")");
        else if (p.test(t)) return t;
        else Logger.error(m + " " + v + " (is invalid for " + e + " in " + n + ")");
        return null;
    }

    private static Enchantment.Rarity parseEnchantmentRarity(String v, String e, String n, Predicate<Enchantment.Rarity> p, String m) {
        try {
            return Enchantment.Rarity.valueOf(v.toUpperCase());
        } catch (RuntimeException x) {
            Logger.error("Invalid enchantment rarity " + v + " (is invalid for " + e + " in " + n + ")");
            return null;
        }
    }

    private static LazyValue<Ingredient> parseRepairMaterial(String v, String e, String n, Predicate<LazyValue<Ingredient>> p, String m) {
        return new LazyValue<>(() -> v.startsWith("#") ? Ingredient.fromTag(ItemTags.makeWrapperTag(v.substring(1))) : Ingredient.fromItems(fromRegistry(v, new ArrayList<>(ForgeRegistries.ITEMS.getValues()))));
    }

    private static ItemGroup parseItemGroup(String v, String e, String n, Predicate<ItemGroup> p, String m) {
        ItemGroup g = null;
        for (ItemGroup i : ItemGroup.GROUPS) if (i.getPath().equals(v)) g = i;
        if (g == null) Logger.error("Could not find item group " + v + " (is invalid for " + e + " in " + n + ")");
        return g;
    }

    private static Rarity parseRarity(String v, String e, String n, Predicate<Rarity> p, String m) {
        try {
            return Rarity.valueOf(v.toUpperCase());
        } catch (RuntimeException x) {
            Logger.error("Invalid rarity " + v + " (is invalid for " + e + " in " + n + ")");
            return null;
        }
    }

    private static SoundType parseSoundType(String v, String e, String n, Predicate<SoundType> p, String m) {
        switch (v) {
            case "wood":
                return SoundType.WOOD;
            case "ground":
                return SoundType.GROUND;
            case "plant":
                return SoundType.PLANT;
            case "lily_pads":
                return SoundType.LILY_PADS;
            case "stone":
                return SoundType.STONE;
            case "metal":
                return SoundType.METAL;
            case "glass":
                return SoundType.GLASS;
            case "cloth":
                return SoundType.CLOTH;
            case "sand":
                return SoundType.SAND;
            case "snow":
                return SoundType.SNOW;
            case "ladder":
                return SoundType.LADDER;
            case "anvil":
                return SoundType.ANVIL;
            case "slime":
                return SoundType.SLIME;
            case "honey":
                return SoundType.HONEY;
            case "wet_grass":
                return SoundType.WET_GRASS;
            case "coral":
                return SoundType.CORAL;
            case "bamboo":
                return SoundType.BAMBOO;
            case "bamboo_sapling":
                return SoundType.BAMBOO_SAPLING;
            case "scaffolding":
                return SoundType.SCAFFOLDING;
            case "sweet_berry_bush":
                return SoundType.SWEET_BERRY_BUSH;
            case "crop":
                return SoundType.CROP;
            case "stem":
                return SoundType.STEM;
            case "vine":
                return SoundType.VINE;
            case "nether_wart":
                return SoundType.NETHER_WART;
            case "lantern":
                return SoundType.LANTERN;
            case "hyphae":
                return SoundType.HYPHAE;
            case "nylium":
                return SoundType.NYLIUM;
            case "fungus":
                return SoundType.FUNGUS;
            case "root":
                return SoundType.ROOT;
            case "shroomlight":
                return SoundType.SHROOMLIGHT;
            case "nether_vine":
                return SoundType.NETHER_VINE;
            case "nether_vine_lower_pitch":
                return SoundType.NETHER_VINE_LOWER_PITCH;
            case "soul_sand":
                return SoundType.SOUL_SAND;
            case "soul_soil":
                return SoundType.SOUL_SOIL;
            case "basalt":
                return SoundType.BASALT;
            case "wart":
                return SoundType.WART;
            case "netherrack":
                return SoundType.NETHERRACK;
            case "nether_brick":
                return SoundType.NETHER_BRICK;
            case "nether_sprout":
                return SoundType.NETHER_SPROUT;
            case "nether_ore":
                return SoundType.NETHER_ORE;
            case "bone":
                return SoundType.BONE;
            case "netherite":
                return SoundType.NETHERITE;
            case "ancient_debris":
                return SoundType.ANCIENT_DEBRIS;
            case "lodestone":
                return SoundType.LODESTONE;
            case "chain":
                return SoundType.CHAIN;
            case "nether_gold":
                return SoundType.NETHER_GOLD;
            case "gilded_blackstone":
                return SoundType.GILDED_BLACKSTONE;
        }
        Logger.error("Unknown sound type " + v + " (is invalid for " + e + " in " + n + ")");
        return null;
    }

    private static ToolType parseToolType(String v, String e, String n, Predicate<ToolType> p, String m) {
        try {
            return ToolType.get(v);
        } catch (IllegalArgumentException x) {
            Logger.error("Invalid tool type " + v + " (is invalid for " + e + " in " + n + ")");
            return null;
        }
    }

    private static <T extends IForgeRegistryEntry<T>> T fromRegistry(String s, List<? extends T> l) {
        for (T t : l) if (t.getRegistryName().toString().equals(s)) return t;
        Logger.error("Could not find " + s);
        return null;
    }

    private static <T> T getLast(List<? extends T> l) {
        return l.get(l.size() - 1);
    }

    private static <T> String getPath(List<T> l) {
        StringBuilder b = new StringBuilder();
        for (T t : l) b.append(t).append('.');
        return b.substring(0, b.length() - 1);
    }

    private static Properties.Item itemProperties(Item item) {
        return item instanceof ArmorItem ? new Properties.Armor() : item instanceof TieredItem ? new Properties.Tool() : new Properties.Item();
    }

    private static void logProperty(String s, IForgeRegistryEntry<?> e, Object v) {
        Logger.info("Set " + s + " for " + e.getRegistryName().toString() + " to " + v);
    }

    private static String[] split(String s, int i, ForgeConfigSpec.ConfigValue<List<String>> l) {
        String[] v = s.split(";");
        if (v.length != i) {
            Logger.error("Entry " + s + " in " + getPath(l.getPath()) + " is invalid, needs " + i + " entries separated by semicolons");
            return null;
        }
        return v;
    }
}
