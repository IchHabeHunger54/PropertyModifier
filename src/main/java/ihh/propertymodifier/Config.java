package ihh.propertymodifier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

public final class Config {
    static final ForgeConfigSpec SPEC;
    static final ForgeConfigSpec.BooleanValue LOG_SUCCESSFUL;
    static final ForgeConfigSpec.BooleanValue LOG_ERRORS;
    private static final ForgeConfigSpec.BooleanValue GENERATE_ITEM_GROUPS;
    private static final ForgeConfigSpec.BooleanValue GENERATE_BLOCK_STATES;
    private static final ForgeConfigSpec.BooleanValue GENERATE_BLOCKS;
    private static final ForgeConfigSpec.BooleanValue GENERATE_ITEMS;
    private static final ForgeConfigSpec.BooleanValue GENERATE_ARMOR;
    private static final ForgeConfigSpec.BooleanValue GENERATE_TOOLS;
    private static final ForgeConfigSpec.BooleanValue GENERATE_ENCHANTMENTS;
    private static final ForgeConfigSpec.BooleanValue GENERATE_COMPOSTING;
    private static final ForgeConfigSpec.IntValue DEFAULT_ENCHANTMENT_VALUE;
    private static final ConfigHelper.ConfigObject<Map<String, ResourceLocation>> ITEM_GROUP;
    private static final ConfigHelper.ConfigObject<Map<String, Boolean>> ITEM_GROUP_SEARCH;
    private static final ConfigHelper.ConfigObject<Map<String, ResourceLocation>> ITEM_GROUP_BACKGROUND;
    private static final ConfigHelper.ConfigObject<Map<String, List<String>>> ITEM_GROUP_ENCHANTMENTS;
    private static final ForgeConfigSpec.BooleanValue REMOVE_EMPTY_ITEM_GROUPS;
    private static final ForgeConfigSpec.BooleanValue SORT_ITEM_GROUPS;
    private static final ForgeConfigSpec.ConfigValue<List<String>> FORCE_REMOVE_ITEM_GROUPS;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> DESTROY_TIME;
    private static final ConfigHelper.ConfigObject<Map<String, Boolean>> REQUIRES_TOOL;
    private static final ConfigHelper.ConfigObject<Map<String, Integer>> LIGHT_EMISSION;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> EXPLOSION_RESISTANCE;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> FRICTION;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> SPEED_FACTOR;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> JUMP_FACTOR;
    private static final ConfigHelper.ConfigObject<Map<String, Integer>> MAX_DAMAGE;
    private static final ConfigHelper.ConfigObject<Map<String, Integer>> MAX_STACK_SIZE;
    private static final ConfigHelper.ConfigObject<Map<String, String>> GROUP;
    private static final ConfigHelper.ConfigObject<Map<String, Boolean>> FIRE_RESISTANT;
    private static final ConfigHelper.ConfigObject<Map<String, String>> RARITY;
    private static final ConfigHelper.ConfigObject<Map<String, Integer>> ENCHANTMENT_VALUE;
    private static final ConfigHelper.ConfigObject<Map<String, String>> REPAIR_MATERIAL;
    private static final ConfigHelper.ConfigObject<Map<String, Integer>> DEFENSE;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> TOUGHNESS;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> KNOCKBACK_RESISTANCE;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> EFFICIENCY;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> ATTACK_DAMAGE;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> ATTACK_SPEED;
    private static final ConfigHelper.ConfigObject<Map<String, String>> ENCHANTMENT_RARITY;
    private static final ForgeConfigSpec.BooleanValue CLEAR_COMPOSTING;
    private static final ConfigHelper.ConfigObject<Map<String, Float>> COMPOSTING;
    static final ForgeConfigSpec.BooleanValue CLEAR_STRIPPING;
    private static final ConfigHelper.ConfigObject<Map<String, String>> STRIPPING;
    static final ForgeConfigSpec.BooleanValue CLEAR_FLATTENING;
    private static final ConfigHelper.ConfigObject<Map<String, String>> FLATTENING;
    static final ForgeConfigSpec.BooleanValue CLEAR_TILLING;
    private static final ConfigHelper.ConfigObject<Map<String, String>> TILLING;
    private static final ConfigHelper.ConfigObject<Map<String, Boolean>> TILLING_NEEDS_AIR;
    private static final ConfigHelper.ConfigObject<Map<String, ResourceLocation>> TILLING_ITEM_DROP;
    private static final ConfigHelper.ConfigObject<Map<String, String>> ATTRIBUTES;
    public static Map<BlockState, Float> DESTROY_TIME_STATES = new HashMap<>();
    public static Map<BlockState, Boolean> REQUIRES_TOOL_STATES = new HashMap<>();
    public static Map<BlockState, Integer> LIGHT_EMISSION_STATES = new HashMap<>();
    public static Map<Block, Float> DESTROY_TIME_BLOCKS = new HashMap<>();
    public static Map<Block, Boolean> REQUIRES_TOOL_BLOCKS = new HashMap<>();
    public static Map<Block, Integer> LIGHT_EMISSION_BLOCKS = new HashMap<>();
    public static Map<Item, Integer> ENCHANTMENT_VALUES = new HashMap<>();
    public static Map<Item, Lazy<Ingredient>> REPAIR_MATERIALS = new HashMap<>();
    static Map<Item, Multimap<Attribute, AttributeModifier>> MODIFIERS = new HashMap<>();
    static Map<Block, BlockState> AXE_STRIPPING = new HashMap<>();
    static Map<Block, BlockState> SHOVEL_FLATTENING = new HashMap<>();
    static Map<Block, Triple<BlockState, Boolean, Item>> HOE_TILLING = new HashMap<>();
    static Map<EntityType<?>, Map<Attribute, Double>> ENTITY_ATTRIBUTES = new HashMap<>();
    private static final AttributeModifier EMPTY_MODIFIER = new AttributeModifier("Property Modifier empty modifier", 0, AttributeModifier.Operation.ADDITION);
    private static boolean SEARCH_RELOAD = false;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("logging");
        LOG_SUCCESSFUL = builder.comment("Whether to log successful operations or not.").define("log_successful", false);
        LOG_ERRORS = builder.comment("Whether to log failed operations or not.").define("log_errors", true);
        builder.pop();
        builder.comment("Generates a config with all non-default values in " + PropertyModifier.MOD_ID + "-generated.toml.").push("generating");
        GENERATE_ITEM_GROUPS = builder.comment("Generates item group-related entries.").define("item_groups", false);
        GENERATE_BLOCK_STATES = builder.comment("Generates block state-related entries.").define("block_states", false);
        GENERATE_BLOCKS = builder.comment("Generates block-related entries.").define("blocks", false);
        GENERATE_ITEMS = builder.comment("Generates item-related entries.").define("items", false);
        GENERATE_ARMOR = builder.comment("Generates armor-related entries.").define("armor", false);
        GENERATE_TOOLS = builder.comment("Generates tool-related entries.").define("tools", false);
        GENERATE_ENCHANTMENTS = builder.comment("Generates enchantment-related entries.").define("enchantments", false);
        GENERATE_COMPOSTING = builder.comment("Generates composting-related entries.").define("composting", false);
        DEFAULT_ENCHANTMENT_VALUE = builder.comment("The default enchantment value used. 0 in vanilla, but mods such as Apotheosis may change this.").defineInRange("default_enchantment_value", 0, 0, 1000000);
        builder.pop();
        builder.comment("The test values are needed because the library Forge uses for configs has a bug that doesn't allow empty default config values. If you add a value to a category, you can safely remove the test value.", "", "Set the respective values here by adding lines in the respective groups. If you want to create entirely new item groups, set an icon for a group name that doesn't exist yet. The tab \"missingno\" is provided by this mod, any items in it are removed after running everything else.").push("item_groups");
        ITEM_GROUP = ConfigHelper.define(builder.comment("Set an icon for a creative tab. Must be a valid item id. Example (without the leading #):", "test = \"minecraft:nether_star\""), "icon", Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC), Map.of("test", new ResourceLocation("missingno")));
        ITEM_GROUP_SEARCH = ConfigHelper.define(builder.comment("Set whether item groups should have a search bar or not. Must be true or false. Only works for tabs created by this mod. Example (without the leading #):", "test = true"), "search", Codec.unboundedMap(Codec.STRING, Codec.BOOL), Map.of("test", false));
        ITEM_GROUP_BACKGROUND = ConfigHelper.define(builder.comment("Set an alternative background for an item group. Must be a valid resource location. Example (without the leading #):", "minecraft:textures/gui/container/creative_inventory/tab_inventory.png"), "background", Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC), Map.of("test", new ResourceLocation("missingno")));
        ITEM_GROUP_ENCHANTMENTS = ConfigHelper.define(builder.comment("Set the enchantment categories for an item group. Must be a list of valid enchantment category names. Use [] for no enchantment groups (this is the default for newly-created groups). Example (without the leading #):", "[\"vanishable\", \"breakable\", \"digger\"]", "Default enchantment groups (mods may add more!): \"armor\", \"armor_feet\", \"armor_legs\", \"armor_chest\", \"armor_head\", \"weapon\", \"digger\", \"fishing_rod\", \"trident\", \"breakable\", \"bow\", \"wearable\", \"crossbow\", \"vanishable\""), "enchantments", Codec.unboundedMap(Codec.STRING, Codec.list(Codec.STRING)), Map.of("test", new ArrayList<>()));
        REMOVE_EMPTY_ITEM_GROUPS = builder.comment("Whether to remove empty item groups or not.").define("remove_empty", true);
        SORT_ITEM_GROUPS = builder.comment("Whether to sort all item groups or not.").define("sort", false);
        FORCE_REMOVE_ITEM_GROUPS = builder.comment("A list of groups that should be removed under all circumstances. Cannot remove \"hotbar\", \"search\" and \"inventory\".").define("force_remove", new ArrayList<>());
        builder.pop();
        builder.comment("Set the respective values here by adding lines in the respective groups. Keys can be either a block (e.g. \"minecraft:stripped_birch_wood\"), a block regex (e.g. \"minecraft:.*_block\") or a blockstate definition (e.g. \"minecraft:grass_block[snowy=true]\"). Block regexes cannot contain the [] characters, as their presence will be interpreted as a blockstate definition instead.").push("blocks_and_blockstates");
        DESTROY_TIME = ConfigHelper.define(builder.comment("Set the destroy time for a block (state). Dirt has 0.5, stone has 1.5, obsidian has 50. -1 makes the block unbreakable. Examples (without the leading #):", "\"minecraft:grass_block[snowy=true]\" = 100", "\"minecraft:.*_planks\" = 100"), "destroy_time", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        REQUIRES_TOOL = ConfigHelper.define(builder.comment("Set the tool requirement of a block (state). Must be true or false. Examples (without the leading #):", "\"minecraft:diamond_ore\" = false", "\"minecraft:oak_log\" = true"), "requires_tool", Codec.unboundedMap(Codec.STRING, Codec.BOOL), Map.of("test", false));
        LIGHT_EMISSION = ConfigHelper.define(builder.comment("Set the light emission of a block (state). Must be an integer between 0 and 15. Examples (without the leading #):", "\"minecraft:redstone_torch[lit=true]\" = 15", "\"minecraft:.*_bricks\" = 7"), "light_emission", Codec.unboundedMap(Codec.STRING, Codec.INT), Map.of("test", 0));
        builder.pop();
        builder.comment("Set the respective values here by adding lines in the respective groups. Keys can be either a block (e.g. \"minecraft:stripped_birch_wood\") or a block regex (e.g. \"minecraft:.*_block\").").push("blocks");
        EXPLOSION_RESISTANCE = ConfigHelper.define(builder.comment("Set the explosion resistance for a block. Dirt has 0.5, stone and has 6, obsidian has 1200. 3600000 or more makes the block completely explosion resistant. Example (without the leading #):", "\"minecraft:dirt\" = 1000"), "explosion_resistance", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        FRICTION = ConfigHelper.define(builder.comment("Set the friction for a block. 0.6 for most blocks, 0.8 for slime blocks, 0.98 for ice, packed ice and frosted ice, and 0.989 for blue ice. Example (without the leading #):", "\"minecraft:.*ice\" = 0.6"), "friction", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        SPEED_FACTOR = ConfigHelper.define(builder.comment("Set the speed factor for a block. 1 for most blocks, 0.4 for soul sand and honey blocks. Example (without the leading #):", "\"minecraft:soul_sand\" = 1"), "speed_factor", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        JUMP_FACTOR = ConfigHelper.define(builder.comment("Set the speed factor for a block. 1 for most blocks, 0.5 for honey blocks. Example (without the leading #):", "\"minecraft:honey_block\" = 1"), "jump_factor", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        builder.pop();
        builder.comment("Set the respective values here by adding lines in the respective groups. Keys can be either an item (e.g. \"minecraft:stripped_birch_wood\") or an item regex (e.g. \"minecraft:diamond.*\").").push("items");
        MAX_DAMAGE = ConfigHelper.define(builder.comment("Set the max damage (durability) of an item. Only items that already have durability can have this value altered. Example (without the leading #):", "\"minecraft:diamond_.*\" = 5000"), "max_damage", Codec.unboundedMap(Codec.STRING, Codec.INT), Map.of("test", 0));
        MAX_STACK_SIZE = ConfigHelper.define(builder.comment("Set the max stack size of an item. Only values between 2 and 64 are supported. Items that have durability cannot have this value altered. Example (without the leading #):", "\"minecraft:ender_pearl\" = 64"), "max_stack_size", Codec.unboundedMap(Codec.STRING, Codec.INT), Map.of("test", 0));
        GROUP = ConfigHelper.define(builder.comment("Set the item group (creative tab) of an item. Use \"missingno\" to remove the item from any item groups. Example (without the leading #):", "\"minecraft:command_block\" = \"decorations\""), "group", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        FIRE_RESISTANT = ConfigHelper.define(builder.comment("Set this value to true to make an item fire resistant (like netherite is), or set it to false to make it burn in fire and lava. Example (without the leading #):", "\"minecraft:netherite_ingot\" = false"), "fire_resistant", Codec.unboundedMap(Codec.STRING, Codec.BOOL), Map.of("test", false));
        RARITY = ConfigHelper.define(builder.comment("Set the rarity (item color) of an item. Must be a valid rarity name. Example (without the leading #):", "\"minecraft:beacon\" = \"common\"", "Default rarities (mods may add more!): \"common\", \"uncommon\", \"rare\", \"epic\""), "rarity", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        ENCHANTMENT_VALUE = ConfigHelper.define(builder.comment("Set the enchantment value of an item. Higher value means better enchantments on average. May not always have a noticeable effect. Example (without the leading #):", "\"minecraft:.*_hoe\" = 40"), "enchantment_value", Codec.unboundedMap(Codec.STRING, Codec.INT), Map.of("test", 0));
        REPAIR_MATERIAL = ConfigHelper.define(builder.comment("Set the repair material of an item. May be an item or a tag containing multiple items, with a # before the tag id. Example (without the leading #):", "\"minecraft:wooden_.*\" = \"#minecraft:fishes\""), "repair_material", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        builder.comment("Set the values for armor.").push("armor");
        DEFENSE = ConfigHelper.define(builder.comment("Set the defense value of an armor item. May not work on modded armors, please file an issue if that's the case. Example (without the leading #):", "\"minecraft:golden_chestplate\" = 10"), "defense", Codec.unboundedMap(Codec.STRING, Codec.INT), Map.of("test", 0));
        TOUGHNESS = ConfigHelper.define(builder.comment("Set the toughness value of an armor item. May not work on modded armors, please file an issue if that's the case. Example (without the leading #):", "\"minecraft:golden_boots\" = 1"), "toughness", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        KNOCKBACK_RESISTANCE = ConfigHelper.define(builder.comment("Set the knockback resistance of an armor item. May not work on modded armors, please file an issue if that's the case. Example (without the leading #):", "\"minecraft:golden_leggings\" = 0.1"), "knockback_resistance", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        builder.pop();
        builder.comment("Set the values for tools, swords and tridents.").push("tools");
        EFFICIENCY = ConfigHelper.define(builder.comment("Set the efficiency value of a tool. May not work on modded tools, please file an issue if that's the case. Also doesn't work on shears, due to internal problems with that. Example (without the leading #):", "\"minecraft:iron_pickaxe\" = 16"), "efficiency", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        ATTACK_DAMAGE = ConfigHelper.define(builder.comment("Set the attack damage of a tool or weapon. May not work on modded tools, please file an issue if that's the case. Example (without the leading #):", "\"minecraft:netherite_sword\" = 12"), "attack_damage", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        ATTACK_SPEED = ConfigHelper.define(builder.comment("Set the attack speed of a tool or weapon. May not work on modded tools, please file an issue if that's the case. Example (without the leading #):", "\"minecraft:netherite_pickaxe\" = 2.5"), "attack_speed", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        builder.pop();
        builder.pop();
        builder.comment("Set the values for enchantments.").push("enchantments");
        ENCHANTMENT_RARITY = ConfigHelper.define(builder.comment("Set the rarity of an enchantment. Must be a valid rarity name. Example (without the leading #):", "\"minecraft:mending\" = \"common\"", "Default rarities (mods may add more!): \"common\", \"uncommon\", \"rare\", \"very_rare\""), "rarity", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        builder.pop();
        builder.comment("Add or remove compostables.").push("composting");
        CLEAR_COMPOSTING = builder.comment("Whether to remove all predefined compostables or not.").define("clear", false);
        COMPOSTING = ConfigHelper.define(builder.comment("Add new compostables here. Consists of an item or item regex and a float between 0 and 1, representing the chance that the composter level is increased. Example (without the leading #):", "\"minecraft:netherite_scrap\" = 0.8"), "composting", Codec.unboundedMap(Codec.STRING, Codec.FLOAT), Map.of("test", 0f));
        builder.pop();
        builder.comment("Set the values for axe stripping.").push("stripping");
        CLEAR_STRIPPING = builder.comment("Whether to remove all predefined stripping values or not.").define("clear", false);
        STRIPPING = ConfigHelper.define(builder.comment("Add new stripping transitions here. Consists of a block or block regex and a block or block state, representing the output. Example (without the leading #):", "\"minecraft:.*_planks\" = \"minecraft:infested_cobblestone\""), "stripping", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        builder.pop();
        builder.comment("Set the values for shovel flattening.").push("flattening");
        CLEAR_FLATTENING = builder.comment("Whether to remove all predefined flattening values or not.").define("clear", false);
        FLATTENING = ConfigHelper.define(builder.comment("Add new flattening transitions here. Consists of a block or block regex and a block or block state, representing the output. Example (without the leading #):", "\"minecraft:.*_planks\" = \"minecraft:infested_cobblestone\""), "flattening", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        builder.pop();
        builder.comment("Set the values for hoe tilling.").push("tilling");
        CLEAR_TILLING = builder.comment("Whether to remove all predefined tilling values or not.").define("clear", false);
        TILLING = ConfigHelper.define(builder.comment("Add new tilling transitions here. Consists of a block or block regex and a block or block state, representing the output. Example (without the leading #):", "\"minecraft:diamond_block\" = \"minecraft:emerald_block\""), "tilling", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        TILLING_NEEDS_AIR = ConfigHelper.define(builder.comment("Whether a certain tilling transition needs air above to work. Consists of a block or block regex and a boolean value (true or false). If left unspecified, true is assumed. Example (without the leading #):", "\"minecraft:diamond_block\" = false"), "needs_air", Codec.unboundedMap(Codec.STRING, Codec.BOOL), Map.of("test", false));
        TILLING_ITEM_DROP = ConfigHelper.define(builder.comment("The item that is dropped when tilling. Consists of a block or block regex and an item id. If left unspecified, no item is dropped. Example (without the leading #):", "\"minecraft:diamond_block\" = \"minecraft:iron_nugget\""), "item_drop", Codec.unboundedMap(Codec.STRING, ResourceLocation.CODEC), Map.of("test", new ResourceLocation("missingno")));
        builder.pop();
        builder.comment("Set the values for entities.").push("entities");
        ATTRIBUTES = ConfigHelper.define(builder.comment("Set the default attributes for entities. Format is \"entityid\" = \"attributeid;value\". Cannot add new attributes to mobs, can only alter those that are present by default anyway. Example (without the leading #):", "\"minecraft:zombie\" = \"minecraft:generic.attack_damage;10\""), "default_attributes", Codec.unboundedMap(Codec.STRING, Codec.STRING), Map.of("test", ""));
        builder.pop();
        SPEC = builder.build();
    }

    static void read() {
        Map<String, Pair<Item, Boolean>> tabs = new HashMap<>();
        ITEM_GROUP.get().forEach((k, v) -> tabs.put(k, new Pair<>(ForgeRegistries.ITEMS.getValue(v), false)));
        ITEM_GROUP_SEARCH.get().forEach((k, v) -> {
            Pair<Item, Boolean> pair = tabs.get(k);
            if (pair != null) {
                pair.b = v;
            }
        });
        tabs.forEach((k, v) -> new CreativeModeTab(k) {
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(v.a);
            }

            @Override
            public boolean hasSearchBar() {
                return v.b;
            }
        });
        ITEM_GROUP_BACKGROUND.get().forEach((k, v) -> getItemGroup(k).ifPresent(tab -> tab.setBackgroundImage(v)));
        ITEM_GROUP_ENCHANTMENTS.get().forEach((k, v) -> getItemGroup(k).ifPresent(tab -> tab.setEnchantmentCategories(v.stream().map(e -> {
            try {
                return EnchantmentCategory.valueOf(e.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ex) {
                Logger.error("Could not find enchantment category " + k + " mentioned in entry \"" + k + "\" = " + v);
                return null;
            }
        }).filter(Objects::nonNull).toList().toArray(new EnchantmentCategory[0]))));
        DESTROY_TIME.get().forEach(parseBlockStatesIntoMap(DESTROY_TIME_STATES, DESTROY_TIME_BLOCKS));
        DESTROY_TIME_STATES.keySet().stream().filter(e -> DESTROY_TIME_STATES.get(e) < -1).forEach(e -> {
            DESTROY_TIME_STATES.remove(e);
            Logger.error("Invalid destroy time value " + e);
        });
        DESTROY_TIME_BLOCKS.keySet().stream().filter(e -> DESTROY_TIME_BLOCKS.get(e) < -1).forEach(e -> {
            DESTROY_TIME_BLOCKS.remove(e);
            Logger.error("Invalid destroy time value " + e);
        });
        REQUIRES_TOOL.get().forEach(parseBlockStatesIntoMap(REQUIRES_TOOL_STATES, REQUIRES_TOOL_BLOCKS));
        LIGHT_EMISSION.get().forEach(parseBlockStatesIntoMap(LIGHT_EMISSION_STATES, LIGHT_EMISSION_BLOCKS));
        LIGHT_EMISSION_STATES.keySet().stream().filter(e -> LIGHT_EMISSION_STATES.get(e) < 0 || LIGHT_EMISSION_STATES.get(e) > 15).forEach(e -> {
            LIGHT_EMISSION_STATES.remove(e);
            Logger.error("Invalid light emission value " + e);
        });
        LIGHT_EMISSION_BLOCKS.keySet().stream().filter(e -> LIGHT_EMISSION_BLOCKS.get(e) < -1).forEach(e -> {
            LIGHT_EMISSION_BLOCKS.remove(e);
            Logger.error("Invalid light emission value " + e);
        });
        Map<Block, Float> explosionResistance = new HashMap<>();
        EXPLOSION_RESISTANCE.get().forEach(parseBlocksIntoMap(explosionResistance));
        for (Block block : explosionResistance.keySet()) {
            float value = explosionResistance.get(block);
            if (value < 0) {
                Logger.error("Invalid explosion resistance value " + value);
            } else {
                block.explosionResistance = value;
            }
        }
        Map<Block, Float> friction = new HashMap<>();
        FRICTION.get().forEach(parseBlocksIntoMap(friction));
        for (Block block : friction.keySet()) {
            float value = friction.get(block);
            if (value < 0) {
                Logger.error("Invalid friction value " + value);
            } else {
                block.friction = value;
            }
        }
        Map<Block, Float> speedFactor = new HashMap<>();
        SPEED_FACTOR.get().forEach(parseBlocksIntoMap(speedFactor));
        for (Block block : speedFactor.keySet()) {
            float value = speedFactor.get(block);
            if (value < 0) {
                Logger.error("Invalid speed factor value " + value);
            } else {
                block.speedFactor = value;
            }
        }
        Map<Block, Float> jumpFactor = new HashMap<>();
        JUMP_FACTOR.get().forEach(parseBlocksIntoMap(jumpFactor));
        for (Block block : jumpFactor.keySet()) {
            float value = jumpFactor.get(block);
            if (value < 0) {
                Logger.error("Invalid jump factor value " + value);
            } else {
                block.jumpFactor = value;
            }
        }
        Map<Item, Integer> maxDamage = new HashMap<>();
        MAX_DAMAGE.get().forEach(parseItemsIntoMap(maxDamage));
        for (Item item : maxDamage.keySet()) {
            int value = maxDamage.get(item);
            if (item.maxDamage == 0) {
                Logger.error("Cannot set the max damage value for non-damageable item " + item);
            } else if (value < 0) {
                Logger.error("Invalid max damage value " + value);
            } else {
                item.maxDamage = value;
            }
        }
        Map<Item, Integer> maxStackSize = new HashMap<>();
        MAX_STACK_SIZE.get().forEach(parseItemsIntoMap(maxStackSize));
        for (Item item : maxStackSize.keySet()) {
            int value = maxStackSize.get(item);
            if (item.maxStackSize == 1) {
                Logger.error("Cannot set the max stack size value for non-damageable item " + item);
            } else if (value < 1 || value > 64) {
                Logger.error("Invalid max stack size value " + value);
            } else {
                item.maxStackSize = value;
            }
        }
        Map<Item, CreativeModeTab> group = new HashMap<>();
        GROUP.get().forEach((k, v) -> getItemGroup(v).ifPresent(tab -> ForgeRegistries.ITEMS.getKeys().stream().filter(e -> e.toString().matches(k)).forEach(e -> group.put(ForgeRegistries.ITEMS.getValue(e), tab))));
        group.forEach((k, v) -> k.category = v);
        if (!group.isEmpty()) {
            SEARCH_RELOAD = true;
        }
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.category == PropertyModifier.MISSINGNO_TAB) {
                item.category = null;
            }
        }
        Map<Item, Boolean> fireResistant = new HashMap<>();
        FIRE_RESISTANT.get().forEach(parseItemsIntoMap(fireResistant));
        fireResistant.forEach((k, v) -> k.isFireResistant = v);
        Map<Item, Rarity> rarity = new HashMap<>();
        RARITY.get().forEach((k, v) -> {
            try {
                Rarity r = Rarity.valueOf(v.toUpperCase(Locale.ROOT));
                ForgeRegistries.ITEMS.getKeys().stream().filter(e -> e.toString().matches(k)).forEach(e -> rarity.put(ForgeRegistries.ITEMS.getValue(e), r));
            } catch (IllegalArgumentException e) {
                Logger.error("Could not find rarity " + v + " mentioned in entry \"" + k + "\" = " + v);
            }
        });
        rarity.forEach((k, v) -> k.rarity = v);
        Map<Item, Integer> enchantmentValue = new HashMap<>();
        ENCHANTMENT_VALUE.get().forEach(parseItemsIntoMap(enchantmentValue));
        for (Item item : enchantmentValue.keySet()) {
            int value = enchantmentValue.get(item);
            if (value < 0) {
                Logger.error("Invalid enchantment value " + value);
            } else {
                ENCHANTMENT_VALUES.put(item, value);
            }
        }
        REPAIR_MATERIAL.get().forEach((k, v) -> ForgeRegistries.ITEMS.getKeys().stream().filter(e -> e.toString().matches(k)).forEach(e -> REPAIR_MATERIALS.put(ForgeRegistries.ITEMS.getValue(e), Lazy.of(() -> v.startsWith("#") ? Ingredient.of(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(v.substring(1)))) : Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(v)))))));
        Map<ArmorItem, Triple<Integer, Float, Float>> armor = new HashMap<>();
        Map<Item, Integer> defense = new HashMap<>();
        DEFENSE.get().forEach(parseItemsIntoMap(defense));
        for (Item item : defense.keySet()) {
            int value = defense.get(item);
            if (!(item instanceof ArmorItem)) {
                Logger.error(item + " is not an armor item and thus cannot have its defense modified");
            } else if (value < 0) {
                Logger.error("Invalid defense value " + value);
            } else {
                Triple<Integer, Float, Float> triple = armor.getOrDefault(item, new Triple<>(null, null, null));
                triple.a = value;
                armor.put((ArmorItem) item, triple);
            }
        }
        Map<Item, Float> toughness = new HashMap<>();
        TOUGHNESS.get().forEach(parseItemsIntoMap(toughness));
        for (Item item : toughness.keySet()) {
            float value = toughness.get(item);
            if (!(item instanceof ArmorItem)) {
                Logger.error(item + " is not an armor item and thus cannot have its toughness modified");
            } else if (value < 0) {
                Logger.error("Invalid toughness value " + value);
            } else {
                Triple<Integer, Float, Float> triple = armor.getOrDefault(item, new Triple<>(null, null, null));
                triple.b = value;
                armor.put((ArmorItem) item, triple);
            }
        }
        Map<Item, Float> knockbackResistance = new HashMap<>();
        KNOCKBACK_RESISTANCE.get().forEach(parseItemsIntoMap(knockbackResistance));
        for (Item item : knockbackResistance.keySet()) {
            float value = knockbackResistance.get(item);
            if (!(item instanceof ArmorItem)) {
                Logger.error(item + " is not an armor item and thus cannot have its knockback resistance modified");
            } else if (value < 0) {
                Logger.error("Invalid knockback resistance value " + value);
            } else {
                Triple<Integer, Float, Float> triple = armor.getOrDefault(item, new Triple<>(null, null, null));
                triple.c = value;
                armor.put((ArmorItem) item, triple);
            }
        }
        for (ArmorItem item : armor.keySet()) {
            Triple<Integer, Float, Float> triple = armor.get(item);
            if (triple.a != null) {
                item.defense = triple.a;
            }
            if (triple.b != null) {
                item.toughness = triple.b;
            }
            if (triple.c != null) {
                item.knockbackResistance = triple.c;
            }
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            for (Attribute attribute : item.defaultModifiers.keys()) {
                if (attribute != Attributes.ARMOR && attribute != Attributes.ARMOR_TOUGHNESS && attribute != Attributes.KNOCKBACK_RESISTANCE) {
                    builder.putAll(attribute, item.defaultModifiers.get(attribute));
                }
            }
            Pair<AttributeModifier, Double> oldDefense = getAttributeValue(Attributes.ARMOR, item.defaultModifiers);
            Pair<AttributeModifier, Double> oldToughness = getAttributeValue(Attributes.ARMOR_TOUGHNESS, item.defaultModifiers);
            Pair<AttributeModifier, Double> oldKnockbackResistance = getAttributeValue(Attributes.KNOCKBACK_RESISTANCE, item.defaultModifiers);
            builder.put(Attributes.ARMOR, new AttributeModifier(oldDefense.a.getId(), oldDefense.a.getName(), triple.a != null ? triple.a : oldDefense.b, oldDefense.a.getOperation()));
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(oldToughness.a.getId(), oldToughness.a.getName(), triple.b != null ? triple.b : oldToughness.b, oldToughness.a.getOperation()));
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(oldKnockbackResistance.a.getId(), oldKnockbackResistance.a.getName(), triple.c != null ? triple.c / 10f : oldKnockbackResistance.b, oldKnockbackResistance.a.getOperation()));
            item.defaultModifiers = builder.build();
        }
        Map<Item, Float> efficiency = new HashMap<>();
        EFFICIENCY.get().forEach(parseItemsIntoMap(efficiency));
        for (Item item : efficiency.keySet()) {
            float value = efficiency.get(item);
            if (!(item instanceof DiggerItem)) {
                Logger.error(item + " is not a tool item and thus cannot have its efficiency modified");
            } else if (value < 0) {
                Logger.error("Invalid efficiency value " + value);
            } else {
                ((DiggerItem) item).speed = value;
            }
        }
        Map<Item, Pair<Float, Float>> weapons = new HashMap<>();
        Map<Item, Float> attackDamage = new HashMap<>();
        ATTACK_DAMAGE.get().forEach(parseItemsIntoMap(attackDamage));
        for (Item item : attackDamage.keySet()) {
            float value = attackDamage.get(item);
            if (value < 0) {
                Logger.error("Invalid attack damage value " + value);
            } else {
                Pair<Float, Float> pair = weapons.getOrDefault(item, new Pair<>(null, null));
                pair.a = value;
                weapons.put(item, pair);
            }
        }
        Map<Item, Float> attackSpeed = new HashMap<>();
        ATTACK_SPEED.get().forEach(parseItemsIntoMap(attackSpeed));
        for (Item item : attackSpeed.keySet()) {
            float value = attackSpeed.get(item);
            if (value < 0) {
                Logger.error("Invalid attack speed value " + value);
            } else {
                Pair<Float, Float> pair = weapons.getOrDefault(item, new Pair<>(null, null));
                pair.b = value;
                weapons.put(item, pair);
            }
        }
        for (Item item : weapons.keySet()) {
            Float damage = weapons.get(item).a;
            Float speed = weapons.get(item).b;
            MODIFIERS.put(item, createToolAttributes(item.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND), damage, speed));
            if (item instanceof DiggerItem digger) {
                if (damage != null) {
                    digger.attackDamageBaseline = damage;
                }
            } else if (item instanceof SwordItem sword) {
                if (damage != null) {
                    sword.attackDamage = damage;
                }
            }
        }
        Map<Enchantment, Enchantment.Rarity> enchantmentRarity = new HashMap<>();
        ENCHANTMENT_RARITY.get().forEach((k, v) -> {
            try {
                Enchantment.Rarity r = Enchantment.Rarity.valueOf(v.toUpperCase(Locale.ROOT));
                ForgeRegistries.ENCHANTMENTS.getKeys().stream().filter(e -> e.toString().matches(k)).forEach(e -> enchantmentRarity.put(ForgeRegistries.ENCHANTMENTS.getValue(e), r));
            } catch (IllegalArgumentException e) {
                Logger.error("Could not find rarity " + v + " mentioned in entry \"" + k + "\" = " + v);
            }
        });
        enchantmentRarity.forEach((k, v) -> k.rarity = v);
        if (CLEAR_COMPOSTING.get()) {
            ComposterBlock.COMPOSTABLES.clear();
        }
        Map<Item, Float> composting = new HashMap<>();
        COMPOSTING.get().forEach(parseItemsIntoMap(composting));
        for (Item item : composting.keySet()) {
            float value = composting.get(item);
            if (value < 0 || value > 1) {
                Logger.error("Invalid composting value " + value);
            } else {
                ComposterBlock.COMPOSTABLES.put(item, value);
            }
        }
        Map<Block, String> stripping = new HashMap<>();
        STRIPPING.get().forEach(parseBlocksIntoMap(stripping));
        for (Block block : stripping.keySet()) {
            String value = stripping.get(block);
            try {
                AXE_STRIPPING.put(block, BlockStateParser.parseForBlock(Registry.BLOCK, value, true).blockState());
            } catch (CommandSyntaxException e) {
                Logger.error("Could not find block state " + value);
            }
        }
        Map<Block, String> flattening = new HashMap<>();
        FLATTENING.get().forEach(parseBlocksIntoMap(flattening));
        for (Block block : flattening.keySet()) {
            String value = flattening.get(block);
            try {
                SHOVEL_FLATTENING.put(block, BlockStateParser.parseForBlock(Registry.BLOCK, value, true).blockState());
            } catch (CommandSyntaxException e) {
                Logger.error("Could not find block state " + value);
            }
        }
        Map<Block, String> tillingTemp = new HashMap<>();
        TILLING.get().forEach(parseBlocksIntoMap(tillingTemp));
        for (Block block : tillingTemp.keySet()) {
            String value = tillingTemp.get(block);
            Triple<BlockState, Boolean, Item> triple = HOE_TILLING.getOrDefault(block, new Triple<>(null, null, null));
            try {
                triple.a = BlockStateParser.parseForBlock(Registry.BLOCK, value, true).blockState();
                HOE_TILLING.put(block, triple);
            } catch (CommandSyntaxException e) {
                Logger.error("Could not find block state " + value);
            }
        }
        Map<Block, Boolean> tillingNeedsAir = new HashMap<>();
        TILLING_NEEDS_AIR.get().forEach(parseBlocksIntoMap(tillingNeedsAir));
        for (Block block : tillingNeedsAir.keySet()) {
            Triple<BlockState, Boolean, Item> triple = HOE_TILLING.getOrDefault(block, new Triple<>(null, null, null));
            triple.b = tillingNeedsAir.get(block);
            HOE_TILLING.put(block, triple);
        }
        Map<Block, ResourceLocation> tillingItemDrop = new HashMap<>();
        TILLING_ITEM_DROP.get().forEach(parseBlocksIntoMap(tillingItemDrop));
        for (Block block : tillingItemDrop.keySet()) {
            ResourceLocation value = tillingItemDrop.get(block);
            Item item = ForgeRegistries.ITEMS.getValue(value);
            if (item != null) {
                Triple<BlockState, Boolean, Item> triple = HOE_TILLING.getOrDefault(block, new Triple<>(null, null, null));
                triple.c = item;
                HOE_TILLING.put(block, triple);
            } else {
                Logger.error("Could not find item " + value);
            }
        }
        ATTRIBUTES.get().forEach((k, v) -> {
            List<EntityType<?>> types = ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(e -> ForgeRegistries.ENTITY_TYPES.getKey(e).toString().matches(k)).toList();
            if (types.isEmpty()) {
                Logger.error("Could not find entity type " + k);
                return;
            }
            String[] array = v.split(";");
            if (array.length != 2) return;
            List<Attribute> attributes = ForgeRegistries.ATTRIBUTES.getValues().stream().filter(e -> ForgeRegistries.ATTRIBUTES.getKey(e).toString().matches(array[0])).toList();
            if (attributes.isEmpty()) {
                Logger.error("Could not find attribute " + k);
                return;
            }
            try {
                double value = Double.parseDouble(array[1]);
                for (EntityType<?> type : types) {
                    Map<Attribute, Double> map = ENTITY_ATTRIBUTES.getOrDefault(type, new HashMap<>());
                    for (Attribute attribute : attributes) {
                        map.put(attribute, value);
                    }
                    ENTITY_ATTRIBUTES.put(type, map);
                }
            } catch (NumberFormatException e) {
                Logger.error("Invalid attribute value " + array[1]);
            }
        });
        List<CreativeModeTab> list = new ArrayList<>();
        List<CreativeModeTab> temp = new ArrayList<>(List.of(CreativeModeTab.TABS));
        temp.remove(CreativeModeTab.TAB_HOTBAR);
        temp.remove(CreativeModeTab.TAB_SEARCH);
        temp.remove(CreativeModeTab.TAB_INVENTORY);
        if (REMOVE_EMPTY_ITEM_GROUPS.get()) {
            temp.removeIf(e -> {
                if (FORCE_REMOVE_ITEM_GROUPS.get().contains(e.getRecipeFolderName())) return true;
                for (Item item : ForgeRegistries.ITEMS.getValues()) {
                    if (item.category == e) return false;
                }
                return true;
            });
        }
        if (SORT_ITEM_GROUPS.get()) {
            temp.sort(Comparator.comparing(CreativeModeTab::getRecipeFolderName));
        }
        while (temp.size() < 9) {
            temp.add(new CreativeModeTab("missingno") {
                @Override
                public ItemStack makeIcon() {
                    return ItemStack.EMPTY;
                }
            }.hideTitle());
        }
        for (int i = 0; i < 4; i++) {
            list.add(temp.get(0));
            temp.remove(temp.get(0));
        }
        list.add(CreativeModeTab.TAB_HOTBAR);
        list.add(CreativeModeTab.TAB_SEARCH);
        for (int i = 0; i < 5; i++) {
            list.add(temp.get(0));
            temp.remove(temp.get(0));
        }
        list.add(CreativeModeTab.TAB_INVENTORY);
        while (temp.size() > 0) {
            list.add(temp.get(0));
            temp.remove(temp.get(0));
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).id = i;
        }
        CreativeModeTab.TABS = list.toArray(new CreativeModeTab[0]);
        generate();
    }

    static void searchReload() {
        if (SEARCH_RELOAD) {
            Minecraft.getInstance().createSearchTrees();
        }
    }

    private static Optional<CreativeModeTab> getItemGroup(String name) {
        Optional<CreativeModeTab> optional = Arrays.stream(CreativeModeTab.TABS).filter(e -> e.getRecipeFolderName().equals(name)).findAny();
        if (optional.isEmpty()) {
            Logger.error("Could not find item group " + name);
        }
        return optional;
    }

    /**
     * adapted from {@link net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor#processBlock}
     */
    private static <T> BiConsumer<String, T> parseBlockStatesIntoMap(Map<BlockState, T> stateMap, Map<Block, T> blockMap) {
        return (k, v) -> {
            if (k.contains("[")) {
                try {
                    stateMap.put(BlockStateParser.parseForBlock(Registry.BLOCK, k, true).blockState(), v);
                } catch (CommandSyntaxException e) {
                    Logger.error("Could not find block state " + k + " mentioned in entry \"" + k + "\" = " + v);
                }
            } else {
                parseBlocksIntoMap(blockMap).accept(k, v);
            }
        };
    }

    private static <T> BiConsumer<String, T> parseBlocksIntoMap(Map<Block, T> map) {
        return (k, v) -> ForgeRegistries.BLOCKS.getKeys().stream().filter(e -> e.toString().matches(k)).forEach(e -> map.put(ForgeRegistries.BLOCKS.getValue(e), v));
    }

    private static <T> BiConsumer<String, T> parseItemsIntoMap(Map<Item, T> map) {
        return (k, v) -> ForgeRegistries.ITEMS.getKeys().stream().filter(e -> e.toString().matches(k)).forEach(e -> map.put(ForgeRegistries.ITEMS.getValue(e), v));
    }

    private static Multimap<Attribute, AttributeModifier> createToolAttributes(Multimap<Attribute, AttributeModifier> map, Float damage, Float speed) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        for (Attribute attribute : map.keys()) {
            if (attribute != Attributes.ATTACK_DAMAGE && attribute != Attributes.ATTACK_SPEED) {
                builder.putAll(attribute, map.get(attribute));
            }
        }
        Pair<AttributeModifier, Double> oldDamage = getAttributeValue(Attributes.ATTACK_DAMAGE, map);
        Pair<AttributeModifier, Double> oldSpeed = getAttributeValue(Attributes.ATTACK_SPEED, map);
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(oldDamage.a.getId(), oldDamage.a.getName(), (damage != null ? damage : oldDamage.b) - 1, oldDamage.a.getOperation()));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(oldSpeed.a.getId(), oldSpeed.a.getName(), (speed != null ? speed : oldSpeed.b) - 4, oldSpeed.a.getOperation()));
        return builder.build();
    }

    private static Pair<AttributeModifier, Double> getAttributeValue(Attribute attribute, Multimap<Attribute, AttributeModifier> map) {
        Collection<AttributeModifier> collection = map.get(attribute);
        double base = attribute.getDefaultValue();
        double result = base;
        for (AttributeModifier modifier : collection) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                result += modifier.getAmount();
            }
        }
        for (AttributeModifier modifier : collection) {
            if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                result += base * modifier.getAmount();
            }
        }
        for (AttributeModifier modifier : collection) {
            if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                result *= modifier.getAmount();
            }
        }
        return new Pair<>(collection.stream().findFirst().orElse(EMPTY_MODIFIER), result);
    }

    private static void generate() {
        StringBuilder builder = new StringBuilder("# This config file is for getting default values, for use in " + PropertyModifier.MOD_ID + "-common.toml. It is NOT directly usable!\n");
        if (GENERATE_ITEM_GROUPS.get()) {
            builder.append("[item_groups]\n");
            builder.append("    [item_groups.icon]\n");
            for (CreativeModeTab tab : CreativeModeTab.TABS) {
                builder.append("        ").append(tab.getRecipeFolderName()).append(" = \"").append(ForgeRegistries.ITEMS.getKey(tab.getIconItem().getItem())).append("\"\n");
            }
            builder.append("    # If not listed, the value for an item group is false.\n    [item_groups.search]\n");
            for (CreativeModeTab tab : CreativeModeTab.TABS) {
                boolean search = tab.hasSearchBar();
                if (search) {
                    builder.append("        ").append(tab.getRecipeFolderName()).append(" = ").append(true).append("\n");
                }
            }
            builder.append("    # If not listed, the value for an item group is \"minecraft:textures/gui/container/creative_inventory/tab_items.png\".\n    [item_groups.background]\n");
            for (CreativeModeTab tab : CreativeModeTab.TABS) {
                ResourceLocation background = tab.getBackgroundImage();
                if (!background.toString().equals("minecraft:textures/gui/container/creative_inventory/tab_items.png")) {
                    builder.append("        ").append(tab.getRecipeFolderName()).append(" = \"").append(background).append("\"\n");
                }
            }
            builder.append("    # If not listed, the value for an item group is [].\n    [item_groups.enchantments]\n");
            for (CreativeModeTab tab : CreativeModeTab.TABS) {
                EnchantmentCategory[] enchantments = tab.getEnchantmentCategories();
                if (enchantments.length > 0) {
                    builder.append("        ").append(tab.getRecipeFolderName()).append(" = [");
                    Iterator<EnchantmentCategory> iterator = Arrays.stream(enchantments).iterator();
                    while (iterator.hasNext()) {
                        builder.append("\"").append(iterator.next().toString().toLowerCase(Locale.ROOT)).append("\"");
                        if (iterator.hasNext()) {
                            builder.append(", ");
                        }
                    }
                    builder.append("]\n");
                }
            }
        }
        if (GENERATE_BLOCK_STATES.get()) {
            builder.append("# Note: For blocks with multiple states, only the value of the default state is listed.\n[blocks_and_blockstates]\n");
            builder.append("    # If not listed, the value for a block is 0.\n    [blocks_and_blockstates.destroy_time]\n");
            for (Block block : ForgeRegistries.BLOCKS.getValues()) {
                float destroySpeed = block.defaultBlockState().destroySpeed;
                if (destroySpeed != 0) {
                    builder.append("        \"").append(ForgeRegistries.BLOCKS.getKey(block)).append("\" = ").append(destroySpeed).append("\n");
                }
            }
            builder.append("    # If not listed, the value for a block is false.\n    [blocks_and_blockstates.requires_tool]\n");
            for (Block block : ForgeRegistries.BLOCKS.getValues()) {
                boolean requiresTool = block.defaultBlockState().requiresCorrectToolForDrops();
                if (requiresTool) {
                    builder.append("        \"").append(ForgeRegistries.BLOCKS.getKey(block)).append("\" = ").append(true).append("\n");
                }
            }
            builder.append("    # If not listed, the value for a block is 0.\n    [blocks_and_blockstates.light_emission]\n");
            for (Block block : ForgeRegistries.BLOCKS.getValues()) {
                float lightEmission = block.defaultBlockState().lightEmission;
                if (lightEmission != 0) {
                    builder.append("        \"").append(ForgeRegistries.BLOCKS.getKey(block)).append("\" = ").append(lightEmission).append("\n");
                }
            }
        }
        if (GENERATE_BLOCKS.get()) {
            builder.append("[blocks]\n");
            builder.append("    # If not listed, the value for a block is 0.\n    [blocks.explosion_resistance]\n");
            for (Block block : ForgeRegistries.BLOCKS.getValues()) {
                float explosionResistance = block.getExplosionResistance();
                if (explosionResistance != 0) {
                    builder.append("        \"").append(ForgeRegistries.BLOCKS.getKey(block)).append("\" = ").append(explosionResistance).append("\n");
                }
            }
            builder.append("    # If not listed, the value for a block is 0.6.\n    [blocks.friction]\n");
            for (Block block : ForgeRegistries.BLOCKS.getValues()) {
                float friction = block.getFriction();
                if (friction != 0.6f) {
                    builder.append("        \"").append(ForgeRegistries.BLOCKS.getKey(block)).append("\" = ").append(friction).append("\n");
                }
            }
            builder.append("    # If not listed, the value for a block is 1.\n    [blocks.speed_factor]\n");
            for (Block block : ForgeRegistries.BLOCKS.getValues()) {
                float speedFactor = block.getSpeedFactor();
                if (speedFactor != 1f) {
                    builder.append("        \"").append(ForgeRegistries.BLOCKS.getKey(block)).append("\" = ").append(speedFactor).append("\n");
                }
            }
            builder.append("    # If not listed, the value for a block is 1.\n    [blocks.jump_factor]\n");
            for (Block block : ForgeRegistries.BLOCKS.getValues()) {
                float jumpFactor = block.getJumpFactor();
                if (jumpFactor != 1f) {
                    builder.append("        \"").append(ForgeRegistries.BLOCKS.getKey(block)).append("\" = ").append(jumpFactor).append("\n");
                }
            }
        }
        if (GENERATE_ITEMS.get()) {
            builder.append("# Note: Due to repair materials not being resolvable at the time this config is generated, it is impossible to list them.\n[items]\n");
            builder.append("    # If not listed, the value for an item is 0.\n    [items.max_damage]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                int maxDamage = item.getMaxDamage();
                if (maxDamage != 0) {
                    builder.append("        \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(maxDamage).append("\n");
                }
            }
            builder.append("    # If not listed, the value for an item is 64.\n    [items.max_stack_size]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                int maxStackSize = item.getMaxStackSize();
                if (maxStackSize != 64) {
                    builder.append("        \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(maxStackSize).append("\n");
                }
            }
            builder.append("    # If not listed, the value for an item is \"missingno\".\n    [items.group]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                CreativeModeTab category = item.getItemCategory();
                if (category != null) {
                    builder.append("        \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = \"").append(category.getRecipeFolderName()).append("\"\n");
                }
            }
            builder.append("    # If not listed, the value for an item is false.\n    [items.fire_resistant]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                boolean fireResistant = item.isFireResistant();
                if (fireResistant) {
                    builder.append("        \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(true).append("\n");
                }
            }
            builder.append("    # If not listed, the value for an item is \"common\".\n    [items.rarity]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                Rarity rarity = item.rarity;
                if (rarity != Rarity.COMMON) {
                    builder.append("        \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = \"").append(rarity.toString().toLowerCase(Locale.ROOT)).append("\"\n");
                }
            }
            builder.append("    # If not listed, the value for an item is the value specified in default_enchantment_value.\n    [items.enchantment_value]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                int enchantmentValue = item.getEnchantmentValue();
                if (enchantmentValue != DEFAULT_ENCHANTMENT_VALUE.get()) {
                    builder.append("        \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(enchantmentValue).append("\n");
                }
            }
        }
        if (GENERATE_ARMOR.get()) {
            builder.append("    [items.armor]\n");
            builder.append("        # If not listed, the value for an item is 0.\n        [items.armor.defense]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof ArmorItem armor) {
                    int defense = armor.getDefense();
                    if (defense != 0) {
                        builder.append("            \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(defense).append("\n");
                    }
                }
            }
            builder.append("        # If not listed, the value for an item is 0.\n        [items.armor.toughness]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof ArmorItem armor) {
                    float toughness = armor.getToughness();
                    if (toughness != 0) {
                        builder.append("            \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(toughness).append("\n");
                    }
                }
            }
            builder.append("        # If not listed, the value for an item is 0.\n        [items.armor.knockback_resistance]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof ArmorItem armor) {
                    float knockbackResistance = armor.knockbackResistance;
                    if (knockbackResistance != 0) {
                        builder.append("            \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(knockbackResistance).append("\n");
                    }
                }
            }
        }
        if (GENERATE_TOOLS.get()) {
            builder.append("    [items.tools]\n");
            builder.append("        # If not listed, the value for an item is 0.\n        [items.tools.efficiency]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof DiggerItem digger) {
                    float efficiency = digger.speed;
                    if (efficiency != 0) {
                        builder.append("            \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(efficiency).append("\n");
                    }
                }
            }
            builder.append("        # If not listed, the value for an item is 0.\n        [items.tools.attack_damage]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                float damage;
                if (item instanceof DiggerItem digger) {
                    damage = getAttributeValue(Attributes.ATTACK_DAMAGE, digger.defaultModifiers).b.floatValue();
                } else if (item instanceof SwordItem sword) {
                    damage = getAttributeValue(Attributes.ATTACK_DAMAGE, sword.defaultModifiers).b.floatValue();
                } else if (item instanceof TridentItem trident) {
                    damage = getAttributeValue(Attributes.ATTACK_DAMAGE, trident.defaultModifiers).b.floatValue();
                } else {
                    continue;
                }
                if (damage != 0) {
                    builder.append("            \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(damage).append("\n");
                }
            }
            builder.append("        # If not listed, the value for an item is 0.\n        [items.tools.attack_speed]\n");
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                float speed;
                if (item instanceof DiggerItem digger) {
                    speed = getAttributeValue(Attributes.ATTACK_SPEED, digger.defaultModifiers).b.floatValue();
                } else if (item instanceof SwordItem sword) {
                    speed = getAttributeValue(Attributes.ATTACK_SPEED, sword.defaultModifiers).b.floatValue();
                } else if (item instanceof TridentItem trident) {
                    speed = getAttributeValue(Attributes.ATTACK_SPEED, trident.defaultModifiers).b.floatValue();
                } else {
                    continue;
                }
                if (speed != 0) {
                    builder.append("            \"").append(ForgeRegistries.ITEMS.getKey(item)).append("\" = ").append(speed).append("\n");
                }
            }
        }
        if (GENERATE_ENCHANTMENTS.get()) {
            builder.append("[enchantments]\n");
            builder.append("    # If not listed, the value for an enchantment is \"common\".\n    [enchantment.rarity]\n");
            for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
                Enchantment.Rarity rarity = enchantment.getRarity();
                if (rarity != Enchantment.Rarity.COMMON) {
                    builder.append("        \"").append(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)).append("\" = \"").append(rarity.toString().toLowerCase(Locale.ROOT)).append("\"\n");
                }
            }
        }
        if (GENERATE_COMPOSTING.get()) {
            builder.append("[composting]\n");
            builder.append("    [composting.composting]\n");
            for (ItemLike item : ComposterBlock.COMPOSTABLES.keySet()) {
                builder.append("        \"").append(ForgeRegistries.ITEMS.getKey(item.asItem())).append("\" = \"").append(ComposterBlock.COMPOSTABLES.getFloat(item)).append("\"\n");
            }
        }
        builder.append("# Due to internal reasons, stripping, flattening, tilling and entity modifiers cannot be extracted.\n");
        File file = new File(FMLPaths.CONFIGDIR.get().toString() + File.separator + PropertyModifier.MOD_ID + "_generated.toml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                if (!file.exists() || file.isDirectory() || !file.canWrite()) {
                    Logger.error("Could not write default config!");
                    return;
                }
            } catch (IOException e) {
                Logger.error("Could not write default config!");
                return;
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(builder.toString());
        } catch (IOException e) {
            Logger.error("Could not write default config!");
        }
    }

    static class Pair<A, B> {
        public A a;
        public B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    static class Triple<A, B, C> {
        public A a;
        public B b;
        public C c;

        public Triple(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}
