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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;

@SuppressWarnings({"ConstantConditions", "FieldMayBeFinal", "CommentedOutCode", "DuplicatedCode"})
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
    /*
        public static final Map<Enchantment, Integer> MIXIN_MAX_LEVEL = new HashMap<>();
        public static final Map<Enchantment, Pair<Integer, Integer>> MIXIN_MIN_ENCHANTABILITY = new HashMap<>();
        public static final Map<Enchantment, Triple<Integer, Integer, Boolean>> MIXIN_MAX_ENCHANTABILITY = new HashMap<>();
        public static final Map<Enchantment, Boolean> MIXIN_IS_TREASURE = new HashMap<>();
        public static final Map<Enchantment, Boolean> MIXIN_CAN_VILLAGER_TRADE = new HashMap<>();
        public static final Map<Enchantment, Boolean> MIXIN_CAN_GENERATE_IN_LOOT = new HashMap<>();
        public static final Map<Enchantment, HashSet<Enchantment>> MIXIN_CAN_COMBINE = new HashMap<>();
    */
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
    static ForgeConfigSpec.IntValue DEFAULT_ENCHANTABILITY;
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
    private static boolean searchReload = false;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder().comment("A few general things to note:", "Any invalid entries will result in a log warning, but will just be skipped, and working entries will work.", "Most entries will utilize a block, item or enchantment id, which needs to be in the modid:blockid, modid:itemid or modid:enchantmentid format, respectively. \"minecraft:\" is not optional.", "Alternatively, you can use \"any\" instead of a block or item id to have the setting be applied to all blocks/items.", "Entries are read from left to right, so you'd better put \"any\"-entries to the left.", "NBT is currently not supported in any way.");
        builder.push("logging");
        LOG_SUCCESSFUL = builder.comment("Whether to log successful operations or not.").define("log_successful", true);
        LOG_ERRORS = builder.comment("Whether to log failed operations or not.").define("log_errors", true);
        builder.pop();
        builder.push("item_groups");
        ITEM_GROUP = builder.comment("Define new item groups. Format is \"id;icon\", with icon being an item id. Will run before the below stuff, allowing you to use these groups below. Note that you need to set a translation using a resource pack, otherwise an itemGroup.<id> translation key will appear. Also, do not use \"none\" as a name, as this is the key used to remove an item from any group.").define("item_group", new ArrayList<>());
        REMOVE_EMPTY_ITEM_GROUPS = builder.comment("Removes item groups that have no items, including empty ones created by this mod. Runs after the below stuff, clearing up any empty groups left from moving all items out of them.").define("remove_empty_item_groups", true);
        builder.pop();
        builder.comment("Dumps all corresponding values, each with their changeable properties, into the logs.").push("dump");
        DUMP_BLOCKS = builder.comment("Dump blocks BEFORE applying the changes.").define("dump_blocks", false);
        DUMP_BLOCKS_AFTER = builder.comment("Dump blocks AFTER applying the changes.").define("dump_blocks_after", false);
        DUMP_BLOCKS_NON_DEFAULT = builder.comment("Dump blocks BEFORE applying the changes. Only dumps non-default values. Does nothing if dump_blocks is set to true.").define("dump_blocks_non_default", false);
        DUMP_BLOCKS_AFTER_NON_DEFAULT = builder.comment("Dump blocks AFTER applying the changes. Only dumps non-default values. Does nothing if dump_blocks_after is set to true.").define("dump_blocks_after_non_default", false);
        DUMP_ITEMS = builder.comment("Dump items BEFORE applying the changes. Due to technical reasons, repair materials cannot be dumped.").define("dump_items", false);
        DUMP_ITEMS_AFTER = builder.comment("Dump items AFTER applying the changes. Due to technical reasons, repair materials cannot be dumped.").define("dump_items_after", false);
        DUMP_ITEMS_NON_DEFAULT = builder.comment("Dump items BEFORE applying the changes. Due to technical reasons, repair materials cannot be dumped. Only dumps non-default values. Does nothing if dump_items is set to true.").define("dump_items_non_default", false);
        DUMP_ITEMS_AFTER_NON_DEFAULT = builder.comment("Dump items AFTER applying the changes. Due to technical reasons, repair materials cannot be dumped. Only dumps non-default values. Does nothing if dump_items is set to true.").define("dump_items_after_non_default", false);
        DUMP_ENCHANTMENTS = builder.comment("Dump enchantments BEFORE applying the changes.").define("dump_enchantments", false);
        DUMP_ENCHANTMENTS_AFTER = builder.comment("Dump enchantments AFTER applying the changes.").define("dump_enchantments_after", false);
        DUMP_GROUPS = builder.comment("Dump item groups BEFORE applying the changes.").define("dump_groups", false);
        DUMP_GROUPS_AFTER = builder.comment("Dump item groups AFTER applying the changes.").define("dump_groups_after", false);
        DEFAULT_ENCHANTABILITY = builder.comment("The default enchantability of items. Change this if you have a mod installed that makes every item enchantable (and thus have a different enchantability). If you're unsure, leave this unchanged and run the item dumping. You will see if you need to change it or not.").defineInRange("default_enchantability", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        builder.pop();
        builder.comment("Settings related to blocks. Format is \"blockid;value\", unless stated otherwise.").push("blocks");
        HARDNESS = builder.comment("How long the block takes to break. 0.5 is dirt, 1.5 is stone, 50 is obsidian. -1 makes the block unbreakable. Not recommended for blocks with blockstate-dependent hardness that may be added by other mods. Default value: 0").define("hardness", new ArrayList<>());
        RESISTANCE = builder.comment("How blast-resistant the block is. 0.5 is dirt, 6 is stone and cobblestone, 1200 is obsidian. 3600000 or more makes the block unbreakable. Default value: 0").define("resistance", new ArrayList<>());
        HARVEST_LEVEL = builder.comment("The block's harvest level. -1 is any harvest level. Default value: -1").define("harvest_level", new ArrayList<>());
        HARVEST_TOOL = builder.comment("The block's harvest tool. Must be one of axe, hoe, pickaxe, shovel or none.").define("harvest_tool", new ArrayList<>());
        REQUIRES_TOOL = builder.comment("Whether you need a corresponding tool, as set by the harvest tool, at least of the harvest level to get block drops (e.g. stone) or if the harvest tool type only speeds up the breaking speed (e.g. dirt). Default value: false").define("requires_tool", new ArrayList<>());
        LIGHT_LEVEL = builder.comment("The light level emitted by this block. Not recommended for blocks with blockstate-dependant light level (e. g. furnaces). Default value: 0").define("light_level", new ArrayList<>());
        SLIPPERINESS = builder.comment("How slippery the block is. Default value: 0.6").define("slipperiness", new ArrayList<>());
        SPEED_FACTOR = builder.comment("Accelerates (if > 1) or slows down (if between 0 and 1) entities that walk upon this block. Default value: 1").define("speed_factor", new ArrayList<>());
        JUMP_FACTOR = builder.comment("Allows entities on this block to jump higher (if > 1) or lower (if between 0 and 1). Default value: 1").define("jump_factor", new ArrayList<>());
        SOUND_TYPE = builder.comment("The sound type the block has. Currently, only vanilla sound types are supported. Default value: stone. Vanilla sound types are:", "wood, ground, plant, lily_pads, stone, metal, glass, cloth, sand, snow, ladder, anvil, slime, honey, wet_grass, coral, bamboo, bamboo_sapling, scaffolding, sweet_berry_bush, crop, stem, vine, nether_wart, lantern, hyphae, nylium, fungus, root, shroomlight, nether_vine, nether_vine_lower_pitch, soul_sand, soul_soil, basalt, wart, netherrack, nether_brick, nether_sprout, nether_ore, bone, netherite, ancient_debris, lodestone, chain, nether_gold, gilded_blackstone").define("sound_type", new ArrayList<>());
        builder.pop();
        builder.comment("Settings related to items. Format is \"item;value\", unless stated otherwise.").push("items");
        MAX_DAMAGE = builder.comment("The max durability an item has. Can only be set on damageable items. Default value: 0").define("max_damage", new ArrayList<>());
        MAX_STACK_SIZE = builder.comment("The max stack size an item has. Cannot be set on damageable items. Default value: 64 (1 for damageable items)").define("max_stack_size", new ArrayList<>());
        GROUP = builder.comment("The item group (= creative tab) of an item. Use \"none\" to remove the item from any item group. Default value: none").define("group", new ArrayList<>());
        IS_IMMUNE_TO_FIRE = builder.comment("Whether the item should have the fire and lava shielding properties of netherite or not. Default value: false").define("is_immune_to_fire", new ArrayList<>());
        RARITY = builder.comment("Sets the item rarity (aka text color). Must be one of common (white), uncommon (yellow), rare (aqua) and epic (light purple). Default value: common").define("rarity", new ArrayList<>());
        ENCHANTABILITY = builder.comment("Sets the enchantability of the item. Default value: 0").define("enchantability", new ArrayList<>());
        REPAIR_MATERIAL = builder.comment("Sets the repair material of the item. Tags (e.g. #minecraft:planks) are also allowed.").define("repair_material", new ArrayList<>());
        builder.comment("Settings related to armor. Only armor items (excluding the elytra) can be affected, anything else will be skipped.").push("armor");
        ARMOR = builder.comment("Sets the armor value.").define("armor", new ArrayList<>());
        TOUGHNESS = builder.comment("Sets the armor toughness value. Default value: 0").define("toughness", new ArrayList<>());
        KNOCKBACK_RESISTANCE = builder.comment("Sets the knockback resistance. Default value: 0").define("knockback_resistance", new ArrayList<>());
        builder.pop();
        builder.comment("Settings related to tools. Only tool, sword and trident items can be affected, anything else will be skipped.").push("tools");
        ATTACK_DAMAGE = builder.comment("Sets the attack damage.").define("attack_damage", new ArrayList<>());
        ATTACK_SPEED = builder.comment("Sets the attack speed.").define("attack_speed", new ArrayList<>());
        TOOL_HARVEST_LEVEL = builder.comment("Sets the tool harvest level. Does not work for swords and tridents.").define("harvest_level", new ArrayList<>());
        EFFICIENCY = builder.comment("Sets the efficiency. Does not work for swords and tridents.").define("efficiency", new ArrayList<>());
        builder.pop();
        builder.pop();
        builder.comment("Settings related to enchantments. Format is \"enchantment;value\", unless stated otherwise.").push("enchantments");
/*
        MAX_LEVEL = builder.comment("Sets the max enchantment level.").define("max_level", new ArrayList<>());
        MIN_ENCHANTABILITY = builder.comment("Sets the min enchantability required for this enchantment. Format is \"enchantment;constant;variable\". Calculation is performed as follows: constant + enchantmentlevel * variable.").define("min_enchantability", new ArrayList<>());
        MAX_ENCHANTABILITY = builder.comment("Sets the max enchantability required for this enchantment. Format is \"enchantment;constant;variable;add\". Calculation is performed as follows: constant + enchantmentlevel * variable. If add is set to true, this value will be added to the min enchantability of this enchantment, if set to false, this will not happen.").define("max_enchantability", new ArrayList<>());
        IS_TREASURE = builder.comment("Whether this item is considered a treasure enchantment or not.").define("is_treasure", new ArrayList<>());
        CAN_VILLAGER_TRADE = builder.comment("Whether this item can be traded from villagers.").define("can_villager_trade", new ArrayList<>());
        CAN_GENERATE_IN_LOOT = builder.comment("Whether this item can generate from loot tables.").define("can_generate_in_loot", new ArrayList<>());
*/
        ENCHANTMENT_RARITY = builder.comment("The enchantment rarity of this enchantment. Must be one of common (10), uncommon (5), rare (2) and very_rare (1).").define("rarity", new ArrayList<>());
/*
        CAN_COMBINE = builder.comment("Two enchantments that can't be on the same item. Does not need to be set by both sides. Once you overwrite it for an enchantment, all previously existing restrictions will be removed, and you must re-add them if you still want them (though the restriction might still come from the other enchantment).").define("can_combine", new ArrayList<>());
*/
        ENCHANTMENT_ITEM_GROUP = builder.comment("The item group this enchantment type's enchanted books are in. As soon as you add one for an item group, you need to re-add every enchantment type for that group as well.").define("group", new ArrayList<>());
        REMOVE_ENCHANTMENT_ITEM_GROUPS = builder.comment("Remove enchantment books from creative tabs. Runs before group, so re-adding is possible.").define("remove_item_groups", false);
        builder.pop();
        SPEC = builder.build();
    }

    public static void read() {
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
        dump(DUMP_BLOCKS.get(), DUMP_BLOCKS_NON_DEFAULT.get(), DUMP_ITEMS.get(), DUMP_ITEMS_NON_DEFAULT.get(), DUMP_ENCHANTMENTS.get(), DUMP_GROUPS.get());
        if (REMOVE_ENCHANTMENT_ITEM_GROUPS.get())
            for (ItemGroup group : ItemGroup.GROUPS)
                group.setRelevantEnchantmentTypes();
        LinkedHashMap<String, ItemStack> map = new LinkedHashMap<>();
        for (String group : ITEM_GROUP.get()) {
            String[] s = group.split(";");
            Item i = ConfigUtil.fromCollection(s[1], ITEM_REGISTRY);
            if (!s[0].equals("none")) map.put(s[0], i == null ? ItemStack.EMPTY : new ItemStack(i));
            else
                Logger.error("Cannot use none as an item group id (is invalid for " + group + " in item_groups.item_group");
        }
        for (Map.Entry<String, ItemStack> entry : map.entrySet())
            new ItemGroup(entry.getKey()) {
                @Override
                @Nonnull
                public ItemStack createIcon() {
                    return entry.getValue();
                }
            };
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(HARDNESS, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0 || e == -1).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARDNESS = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(RESISTANCE, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0 && e <= 3600000).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.RESISTANCE = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Integer> entry : ConfigUtil.getMap(HARVEST_LEVEL, BLOCK_REGISTRY, ParsingUtil::parseInt, e -> e > -2).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARVEST_LEVEL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, ToolType> entry : ConfigUtil.getMap(HARVEST_TOOL, BLOCK_REGISTRY, ParsingUtil::parseToolType, e -> true).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.HARVEST_TOOL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Boolean> entry : ConfigUtil.getMap(REQUIRES_TOOL, BLOCK_REGISTRY, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.REQUIRES_TOOL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Integer> entry : ConfigUtil.getMap(LIGHT_LEVEL, BLOCK_REGISTRY, ParsingUtil::parseInt, e -> e > -1 && e < 16).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.LIGHT_LEVEL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(SLIPPERINESS, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SLIPPERINESS = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(SPEED_FACTOR, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SPEED_FACTOR = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(JUMP_FACTOR, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.JUMP_FACTOR = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, SoundType> entry : ConfigUtil.getMap(SOUND_TYPE, BLOCK_REGISTRY, ParsingUtil::parseSoundType, e -> true).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.SOUND_TYPE = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(MAX_DAMAGE, ITEM_REGISTRY, ParsingUtil::parseInt, e -> e > 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.MAX_DAMAGE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(MAX_STACK_SIZE, ITEM_REGISTRY, ParsingUtil::parseInt, e -> e > 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.MAX_STACK_SIZE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, ItemGroup> entry : ConfigUtil.getMap(GROUP, ITEM_REGISTRY, ParsingUtil::parseItemGroup, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.GROUP = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Boolean> entry : ConfigUtil.getMap(IS_IMMUNE_TO_FIRE, ITEM_REGISTRY, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.IS_IMMUNE_TO_FIRE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Rarity> entry : ConfigUtil.getMap(RARITY, ITEM_REGISTRY, ParsingUtil::parseRarity, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.RARITY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(ENCHANTABILITY, ITEM_REGISTRY, ParsingUtil::parseInt, e -> e > -1).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.ENCHANTABILITY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, LazyValue<Ingredient>> entry : ConfigUtil.getMap(REPAIR_MATERIAL, ITEM_REGISTRY, ParsingUtil::parseRepairMaterial, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.REPAIR_MATERIAL = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(ARMOR, ARMOR_REGISTRY, ParsingUtil::parseInt, e -> e > -1).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).ARMOR = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(TOUGHNESS, ARMOR_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).TOUGHNESS = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(KNOCKBACK_RESISTANCE, ARMOR_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Armor());
            ((Properties.Armor) prop).KNOCKBACK_RESISTANCE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(ATTACK_DAMAGE, TIERED_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).ATTACK_DAMAGE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(ATTACK_SPEED, TIERED_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).ATTACK_SPEED = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(TOOL_HARVEST_LEVEL, TOOL_REGISTRY, ParsingUtil::parseInt, e -> e > -2).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).HARVEST_LEVEL = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(EFFICIENCY, TOOL_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).EFFICIENCY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
/*
        for (Map.Entry<Enchantment, Integer> entry : ConfigUtil.getMap(MAX_LEVEL, ENCHANTMENT_REGISTRY, ParsingUtil::parseInt, e -> e > 0).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MAX_LEVEL = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Pair<Integer, Integer>> entry : ConfigUtil.pairMap(MIN_ENCHANTABILITY, ENCHANTMENT_REGISTRY, ParsingUtil::parseInt, e -> true, ParsingUtil::parseInt, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MIN_ENCHANTABILITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Triple<Integer, Integer, Boolean>> entry : ConfigUtil.tripleMap(MAX_ENCHANTABILITY, ENCHANTMENT_REGISTRY, ParsingUtil::parseInt, e -> true, ParsingUtil::parseInt, e -> true, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.MAX_ENCHANTABILITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : ConfigUtil.getMap(IS_TREASURE, ENCHANTMENT_REGISTRY, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.IS_TREASURE = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : ConfigUtil.getMap(CAN_VILLAGER_TRADE, ENCHANTMENT_REGISTRY, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.CAN_VILLAGER_TRADE = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Boolean> entry : ConfigUtil.getMap(CAN_GENERATE_IN_LOOT, ENCHANTMENT_REGISTRY, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.CAN_GENERATE_IN_LOOT = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
*/
        for (Map.Entry<Enchantment, Enchantment.Rarity> entry : ConfigUtil.getMap(ENCHANTMENT_RARITY, ENCHANTMENT_REGISTRY, ParsingUtil::parseEnchantmentRarity, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.RARITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
/*
        for (Map.Entry<Enchantment, Enchantment> entry : ConfigUtil.getMap(CAN_COMBINE, ENCHANTMENT_REGISTRY, ParsingUtil::parseEnchantment, e -> true, "").entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            if (prop.INCOMPATIBLES == null) prop.INCOMPATIBLES = new HashSet<>();
            prop.INCOMPATIBLES.add(entry.getValue());
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
*/
        for (String s : ENCHANTMENT_ITEM_GROUP.get()) {
            String[] array = s.split(";");
            try {
                EnchantmentType type = EnchantmentType.valueOf(array[0].toUpperCase());
                ItemGroup group = ParsingUtil.parseItemGroup(array[1], s, ConfigUtil.getPath(ENCHANTMENT_ITEM_GROUP.getPath()), e -> true);
                if (group != null) {
                    List<EnchantmentType> list = ENCHANTMENT_GROUPS.getOrDefault(group, new ArrayList<>());
                    list.add(type);
                    ENCHANTMENT_GROUPS.put(group, list);
                } else ConfigUtil.logInvalid(array[1], s, ConfigUtil.getPath(ENCHANTMENT_ITEM_GROUP.getPath()));
            } catch (IllegalArgumentException e) {
                ConfigUtil.logInvalid(array[0], s, ConfigUtil.getPath(ENCHANTMENT_ITEM_GROUP.getPath()));
            }
        }
    }

    public static void workCreative() {
        for (Item item : ITEMS.keySet()) {
            Properties.Item prop = ITEMS.get(item);
            if (prop.GROUP != null) {
                item.group = prop.GROUP;
                searchReload = true;
            }
        }
    }

    public static void work() {
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
                    Field f = Block.class.getDeclaredField("harvestLevel");
                    f.setAccessible(true);
                    f.setInt(block, prop.HARVEST_LEVEL);
                    block.properties = block.properties.harvestLevel(prop.HARVEST_LEVEL);
                    MIXIN_HARVEST_LEVEL.put(block, prop.HARVEST_LEVEL);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            if (prop.HARVEST_TOOL != null)
                try {
                    Field f = Block.class.getDeclaredField("harvestTool");
                    f.setAccessible(true);
                    f.set(block, prop.HARVEST_TOOL);
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
            } else if (item instanceof TieredItem) {
                TieredItem toolitem = (TieredItem) item;
                Properties.Tool toolprop = (Properties.Tool) prop;
                if (toolprop.HARVEST_LEVEL != null)
                    MIXIN_TOOL_HARVEST_LEVEL.put(toolitem, toolprop.HARVEST_LEVEL);
                if (toolprop.EFFICIENCY != null && toolitem instanceof ToolItem)
                    ((ToolItem) toolitem).efficiency = toolprop.EFFICIENCY;
                if (toolprop.ATTACK_DAMAGE != null || toolprop.ATTACK_SPEED != null) {
                    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                    if (toolitem instanceof ToolItem) {
                        ToolItem tool = (ToolItem) toolitem;
                        AttributeModifier speedMod = tool.toolAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        double speed = toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedMod != null ? speedMod.getAmount() : 0;
                        if (toolprop.ATTACK_DAMAGE != null) tool.attackDamage = toolprop.ATTACK_DAMAGE - 1;
                        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.ATTACK_DAMAGE_MODIFIER, "Tool modifier", tool.attackDamage, AttributeModifier.Operation.ADDITION));
                        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.ATTACK_SPEED_MODIFIER, "Tool modifier", speed, AttributeModifier.Operation.ADDITION));
                        tool.toolAttributes = builder.build();
                    }
                    if (toolitem instanceof SwordItem) {
                        SwordItem sword = (SwordItem) toolitem;
                        AttributeModifier speedMod = sword.attributeModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        double speed = toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedMod != null ? speedMod.getAmount() : 0;
                        if (toolprop.ATTACK_DAMAGE != null) sword.attackDamage = toolprop.ATTACK_DAMAGE - 1;
                        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.ATTACK_DAMAGE_MODIFIER, "Tool modifier", sword.attackDamage, AttributeModifier.Operation.ADDITION));
                        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.ATTACK_SPEED_MODIFIER, "Tool modifier", speed, AttributeModifier.Operation.ADDITION));
                        sword.attributeModifiers = builder.build();
                    }
                }
            } else if (item instanceof TridentItem) {
                TridentItem trident = (TridentItem) item;
                Properties.Tool toolprop = (Properties.Tool) prop;
                AttributeModifier damageMod = trident.tridentAttributes.get(Attributes.ATTACK_DAMAGE).stream().findFirst().orElse(null);
                AttributeModifier speedMod = trident.tridentAttributes.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.ATTACK_DAMAGE_MODIFIER, "Tool modifier", toolprop.ATTACK_DAMAGE != null ? toolprop.ATTACK_DAMAGE - 4 : damageMod != null ? damageMod.getAmount() : 0, AttributeModifier.Operation.ADDITION));
                builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.ATTACK_SPEED_MODIFIER, "Tool modifier", toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedMod != null ? speedMod.getAmount() : 0, AttributeModifier.Operation.ADDITION));
                trident.tridentAttributes = builder.build();
            }
        }
        for (Enchantment enchantment : ENCHANTMENTS.keySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.get(enchantment);
            if (prop.RARITY != null) enchantment.rarity = prop.RARITY;
/*
            if (prop.MAX_LEVEL != null) MIXIN_MAX_LEVEL.put(enchantment, prop.MAX_LEVEL);
            if (prop.MIN_ENCHANTABILITY != null) MIXIN_MIN_ENCHANTABILITY.put(enchantment, prop.MIN_ENCHANTABILITY);
            if (prop.MAX_ENCHANTABILITY != null) MIXIN_MAX_ENCHANTABILITY.put(enchantment, prop.MAX_ENCHANTABILITY);
            if (prop.IS_TREASURE != null) MIXIN_IS_TREASURE.put(enchantment, prop.IS_TREASURE);
            if (prop.CAN_VILLAGER_TRADE != null) MIXIN_CAN_VILLAGER_TRADE.put(enchantment, prop.CAN_VILLAGER_TRADE);
            if (prop.CAN_GENERATE_IN_LOOT != null) MIXIN_CAN_GENERATE_IN_LOOT.put(enchantment, prop.CAN_GENERATE_IN_LOOT);
            if (prop.INCOMPATIBLES != null) MIXIN_CAN_COMBINE.put(enchantment, prop.INCOMPATIBLES);
*/
        }
        for (Item item : ForgeRegistries.ITEMS)
            if (item.group != null && item.group.getPath().equals("none")) item.group = null;
        for (ItemGroup group : ENCHANTMENT_GROUPS.keySet())
            group.setRelevantEnchantmentTypes(ENCHANTMENT_GROUPS.get(group).toArray(new EnchantmentType[0]));
        if (REMOVE_EMPTY_ITEM_GROUPS.get()) {
            ArrayList<ItemGroup> groups = Lists.newArrayList(ItemGroup.GROUPS);
            ArrayList<ItemGroup> result = Lists.newArrayList(ItemGroup.GROUPS);
            for (ItemGroup group : groups) {
                if (group.getRelevantEnchantmentTypes().length > 0) continue;
                boolean b = false;
                for (Item item : ForgeRegistries.ITEMS)
                    if (item.group == group) {
                        b = true;
                        break;
                    }
                if (!b) result.remove(group);
            }
            result.add(4, ItemGroup.HOTBAR);
            result.add(5, ItemGroup.SEARCH);
            result.add(11, ItemGroup.INVENTORY);
            for (int i = 0; i < result.size(); i++) result.get(i).index = i;
            ItemGroup.GROUPS = result.toArray(new ItemGroup[0]);
        }
        ItemGroup.BUILDING_BLOCKS.index = 0;
        dump(DUMP_BLOCKS_AFTER.get(), DUMP_BLOCKS_AFTER_NON_DEFAULT.get(), DUMP_ITEMS_AFTER.get(), DUMP_ITEMS_AFTER_NON_DEFAULT.get(), DUMP_ENCHANTMENTS_AFTER.get(), DUMP_GROUPS_AFTER.get());
    }

    private static void dump(boolean blocks, boolean blocksNonDefault, boolean items, boolean itemsNonDefault, boolean enchantments, boolean groups) {
        if (blocks || blocksNonDefault) {
            Logger.forceInfo("Blocks:");
            for (Block block : ForgeRegistries.BLOCKS) {
                if (block.properties.isAir) continue;
                StringBuilder builder = new StringBuilder(block.getRegistryName().toString()).append(" - ");
                DumpingUtil.appendHardness(builder, block, blocksNonDefault);
                DumpingUtil.appendResistance(builder, block, blocksNonDefault);
                DumpingUtil.appendHarvestLevel(builder, block, blocksNonDefault);
                DumpingUtil.appendHarvestTool(builder, block, blocksNonDefault);
                DumpingUtil.appendRequiresTool(builder, block, blocksNonDefault);
                DumpingUtil.appendLightLevel(builder, block, blocksNonDefault);
                DumpingUtil.appendSlipperiness(builder, block, blocksNonDefault);
                DumpingUtil.appendSpeedFactor(builder, block, blocksNonDefault);
                DumpingUtil.appendJumpFactor(builder, block, blocksNonDefault);
                DumpingUtil.appendSoundType(builder, block, blocksNonDefault);
                Logger.forceInfo(builder.substring(0, builder.length() - (builder.toString().equals(block.getRegistryName().toString() + " - ") ? 3 : 2)));
            }
        }
        if (items || itemsNonDefault) {
            Logger.forceInfo("Items:");
            for (Item item : ForgeRegistries.ITEMS) {
                StringBuilder builder = new StringBuilder(item.getRegistryName().toString()).append(" - ");
                DumpingUtil.appendMaxDamage(builder, item, itemsNonDefault);
                DumpingUtil.appendMaxStackSize(builder, item, itemsNonDefault);
                DumpingUtil.appendItemGroup(builder, item, itemsNonDefault);
                DumpingUtil.appendIsImmuneToFire(builder, item, itemsNonDefault);
                DumpingUtil.appendRarity(builder, item, itemsNonDefault);
                DumpingUtil.appendEnchantability(builder, item, itemsNonDefault);
                DumpingUtil.appendToolType(builder, item, itemsNonDefault);
                if (item instanceof ArmorItem) {
                    ArmorItem armor = (ArmorItem) item;
                    DumpingUtil.appendArmor(builder, armor, itemsNonDefault);
                    DumpingUtil.appendToughness(builder, armor, itemsNonDefault);
                    DumpingUtil.appendKnockbackResistance(builder, armor, itemsNonDefault);
                } else if (item instanceof ToolItem) {
                    ToolItem tool = (ToolItem) item;
                    DumpingUtil.appendEfficiency(builder, tool, itemsNonDefault);
                    DumpingUtil.appendDamage(builder, tool, itemsNonDefault);
                    DumpingUtil.appendSpeed(builder, tool, itemsNonDefault);
                } else if (item instanceof SwordItem) {
                    SwordItem sword = (SwordItem) item;
                    DumpingUtil.appendDamage(builder, sword, itemsNonDefault);
                    DumpingUtil.appendSpeed(builder, sword, itemsNonDefault);
                } else if (item instanceof TridentItem) {
                    TridentItem trident = (TridentItem) item;
                    DumpingUtil.appendDamage(builder, trident, itemsNonDefault);
                    DumpingUtil.appendSpeed(builder, trident, itemsNonDefault);
                }
                Logger.forceInfo(builder.substring(0, builder.length() - (builder.toString().equals(item.getRegistryName().toString() + " - ") ? 3 : 2)));
            }
        }
        if (enchantments) {
            Logger.forceInfo("Enchantments:");
            for (Enchantment e : ForgeRegistries.ENCHANTMENTS)
                Logger.forceInfo(e.getRegistryName().toString() + " - rarity: " + e.getRarity().toString().toLowerCase());
        }
        if (groups) {
            Logger.forceInfo("Item groups:");
            for (ItemGroup group : ItemGroup.GROUPS) {
                EnchantmentType[] types = group.getRelevantEnchantmentTypes();
                StringBuilder builder = new StringBuilder(group.getPath());
                if (types.length > 0) builder.append(" - enchantment types: ");
                for (EnchantmentType type : types) builder.append(type.name().toLowerCase()).append(", ");
                Logger.forceInfo(types.length > 0 ? builder.substring(0, builder.length() - 2) : builder.toString());
            }
        }
    }

    private static Properties.Item itemProperties(Item item) {
        return item instanceof ArmorItem ? new Properties.Armor() : item instanceof TieredItem || item instanceof TridentItem ? new Properties.Tool() : new Properties.Item();
    }

    static void searchReload() {
        if (searchReload) Minecraft.getInstance().populateSearchTreeManager();
    }
}
