package ihh.propertymodifier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

public final class Config {
    public static final Map<Block, Float> MIXIN_DESTROY_TIME = new HashMap<>();
    public static final Map<Block, Integer> MIXIN_LIGHT_EMISSION = new HashMap<>();
    public static final Map<Block, Boolean> MIXIN_REQUIRES_TOOL = new HashMap<>();
    public static final Map<Item, Integer> MIXIN_ENCHANTMENT_VALUE = new HashMap<>();
    public static final Map<Item, Lazy<Ingredient>> MIXIN_REPAIR_ITEM = new HashMap<>();
    private static final Map<Block, Properties.Block> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Properties.Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Enchantment, Properties.Enchantment> ENCHANTMENTS = new LinkedHashMap<>();
    private static final Map<CreativeModeTab, List<EnchantmentCategory>> ENCHANTMENT_GROUPS = new LinkedHashMap<>();
    public static Map<EntityType<?>, Map<Attribute, List<AttributeModifier>>> MODIFIERS = new HashMap<>();
    static ForgeConfigSpec SPEC;
    static ForgeConfigSpec.BooleanValue LOG_SUCCESSFUL;
    static ForgeConfigSpec.BooleanValue LOG_ERRORS;
    static ForgeConfigSpec.BooleanValue AXE_CLEAR;
    static ForgeConfigSpec.BooleanValue SHOVEL_CLEAR;
    static ForgeConfigSpec.BooleanValue HOE_CLEAR;
    static ForgeConfigSpec.IntValue DEFAULT_ENCHANTMENT_VALUE;
    static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_1_TRADES;
    static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_2_TRADES;
    static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_3_TRADES;
    static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_4_TRADES;
    static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_5_TRADES;
    static ForgeConfigSpec.ConfigValue<List<String>> TRADER_NORMAL_TRADES;
    static ForgeConfigSpec.ConfigValue<List<String>> TRADER_LAST_TRADES;
    private static final ForgeConfigSpec.BooleanValue REMOVE_EMPTY_ITEM_GROUPS;
    private static final ForgeConfigSpec.BooleanValue DUMP_BLOCKS;
    private static final ForgeConfigSpec.BooleanValue DUMP_BLOCKS_AFTER;
    private static final ForgeConfigSpec.BooleanValue DUMP_BLOCKS_NON_DEFAULT;
    private static final ForgeConfigSpec.BooleanValue DUMP_BLOCKS_AFTER_NON_DEFAULT;
    private static final ForgeConfigSpec.BooleanValue DUMP_ITEMS;
    private static final ForgeConfigSpec.BooleanValue DUMP_ITEMS_AFTER;
    private static final ForgeConfigSpec.BooleanValue DUMP_ITEMS_NON_DEFAULT;
    private static final ForgeConfigSpec.BooleanValue DUMP_ITEMS_AFTER_NON_DEFAULT;
    private static final ForgeConfigSpec.BooleanValue DUMP_ENCHANTMENTS;
    private static final ForgeConfigSpec.BooleanValue DUMP_ENCHANTMENTS_AFTER;
    private static final ForgeConfigSpec.BooleanValue DUMP_GROUPS;
    private static final ForgeConfigSpec.BooleanValue DUMP_GROUPS_AFTER;
    private static final ForgeConfigSpec.BooleanValue DUMP_COMPOSTER;
    private static final ForgeConfigSpec.BooleanValue DUMP_COMPOSTER_AFTER;
    private static final ForgeConfigSpec.BooleanValue DUMP_STRIPPING;
    private static final ForgeConfigSpec.BooleanValue DUMP_STRIPPING_AFTER;
    private static final ForgeConfigSpec.BooleanValue DUMP_PATHING;
    private static final ForgeConfigSpec.BooleanValue DUMP_PATHING_AFTER;
    private static final ForgeConfigSpec.BooleanValue REMOVE_ENCHANTMENT_ITEM_GROUPS;
    private static final ForgeConfigSpec.BooleanValue COMPOSTER_CLEAR;
    private static final ForgeConfigSpec.ConfigValue<List<String>> ITEM_GROUP;
    private static final ForgeConfigSpec.ConfigValue<List<String>> DESTROY_TIME;
    private static final ForgeConfigSpec.ConfigValue<List<String>> EXPLOSION_RESISTANCE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> REQUIRES_TOOL;
    private static final ForgeConfigSpec.ConfigValue<List<String>> LIGHT_EMISSION;
    private static final ForgeConfigSpec.ConfigValue<List<String>> FRICTION;
    private static final ForgeConfigSpec.ConfigValue<List<String>> SPEED_FACTOR;
    private static final ForgeConfigSpec.ConfigValue<List<String>> JUMP_FACTOR;
    private static final ForgeConfigSpec.ConfigValue<List<String>> SOUND_TYPE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> MAX_DAMAGE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> MAX_STACK_SIZE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> GROUP;
    private static final ForgeConfigSpec.ConfigValue<List<String>> IS_FIRE_RESISTANT;
    private static final ForgeConfigSpec.ConfigValue<List<String>> RARITY;
    private static final ForgeConfigSpec.ConfigValue<List<String>> ENCHANTMENT_VALUE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> REPAIR_MATERIAL;
    private static final ForgeConfigSpec.ConfigValue<List<String>> DEFENSE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> TOUGHNESS;
    private static final ForgeConfigSpec.ConfigValue<List<String>> KNOCKBACK_RESISTANCE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> ATTACK_DAMAGE;
    private static final ForgeConfigSpec.ConfigValue<List<String>> ATTACK_SPEED;
    private static final ForgeConfigSpec.ConfigValue<List<String>> EFFICIENCY;
    private static final ForgeConfigSpec.ConfigValue<List<String>> ENCHANTMENT_RARITY;
    private static final ForgeConfigSpec.ConfigValue<List<String>> ENCHANTMENT_ITEM_GROUP;
    private static final ForgeConfigSpec.ConfigValue<List<String>> COMPOSTER_INPUTS;
    private static final ForgeConfigSpec.ConfigValue<List<String>> AXE_BLOCKS;
    private static final ForgeConfigSpec.ConfigValue<List<String>> SHOVEL_BLOCKS;
    private static final ForgeConfigSpec.ConfigValue<List<String>> HOE_BLOCKS;
    private static final ForgeConfigSpec.ConfigValue<List<String>> ENTITY_MODIFIERS;
    private static boolean searchReload = false;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder().comment("A few general things to note:", "Any invalid entries will result in a log warning, but will just be skipped, and working entries will work.", "Most entries will utilize a block, item or enchantment id, which needs to be in the modid:blockid, modid:itemid or modid:enchantmentid format, respectively. \"minecraft:\" is not optional.", "All of these support regex. So you could use .* to select all entries, or minecraft:.* to select all elements in the minecraft namespace. For further information, please consult Google.", "Entries are read from left to right, this means that entries will overwrite entries read before it if they modify the same block/item/enchantment.", "NBT and blockstates are currently not supported in any way.");
        builder.push("logging");
        LOG_SUCCESSFUL = builder.comment("Whether to log successful operations or not.").define("log_successful", true);
        LOG_ERRORS = builder.comment("Whether to log failed operations or not.").define("log_errors", true);
        builder.pop();
        builder.push("item_groups");
        ITEM_GROUP = builder.comment("Define new item groups. Format is \"id;icon\", with icon being an item id. Will run before the below stuff, allowing you to use these groups below. Note that you need to set a translation using a resource pack, otherwise an itemGroup.<id> translation key will appear. Do not use \"none\" as a name, as this is the key used to remove an item from any group.").define("item_group", new ArrayList<>());
        REMOVE_EMPTY_ITEM_GROUPS = builder.comment("Removes item groups that have no items, including empty ones created by this mod. Runs after the below stuff, clearing up any empty groups left from moving all items out of them.").define("remove_empty_item_groups", true);
        builder.pop();
        builder.comment("Dumps all corresponding values with their changeable properties into the logs. Dumping tilling transitions or villager trades is impossible code-wise, hence they are missing in this list.").push("dump");
        DUMP_BLOCKS = builder.comment("Dump blocks BEFORE applying the changes.").define("dump_blocks", false);
        DUMP_BLOCKS_AFTER = builder.comment("Dump blocks AFTER applying the changes.").define("dump_blocks_after", false);
        DUMP_BLOCKS_NON_DEFAULT = builder.comment("Dump blocks BEFORE applying the changes. Only dumps non-default values. Does nothing if dump_blocks is set to true.").define("dump_blocks_non_default", false);
        DUMP_BLOCKS_AFTER_NON_DEFAULT = builder.comment("Dump blocks AFTER applying the changes. Only dumps non-default values. Does nothing if dump_blocks_after is set to true.").define("dump_blocks_after_non_default", false);
        DUMP_ITEMS = builder.comment("Dump items BEFORE applying the changes. Due to technical reasons, repair materials cannot be dumped.").define("dump_items", false);
        DUMP_ITEMS_AFTER = builder.comment("Dump items AFTER applying the changes. Due to technical reasons, repair materials cannot be dumped.").define("dump_items_after", false);
        DUMP_ITEMS_NON_DEFAULT = builder.comment("Dump items BEFORE applying the changes. Repair materials cannot be dumped (technical limitations). Only dumps non-default values. Does nothing if dump_items is set to true.").define("dump_items_non_default", false);
        DUMP_ITEMS_AFTER_NON_DEFAULT = builder.comment("Dump items AFTER applying the changes. Repair materials cannot be dumped (technical limitations). Only dumps non-default values. Does nothing if dump_items is set to true.").define("dump_items_after_non_default", false);
        DUMP_ENCHANTMENTS = builder.comment("Dump enchantments BEFORE applying the changes.").define("dump_enchantments", false);
        DUMP_ENCHANTMENTS_AFTER = builder.comment("Dump enchantments AFTER applying the changes.").define("dump_enchantments_after", false);
        DUMP_GROUPS = builder.comment("Dump item groups BEFORE applying the changes.").define("dump_groups", false);
        DUMP_GROUPS_AFTER = builder.comment("Dump item groups AFTER applying the changes.").define("dump_groups_after", false);
        DUMP_COMPOSTER = builder.comment("Dump composter inputs BEFORE applying the changes.").define("dump_composter", false);
        DUMP_COMPOSTER_AFTER = builder.comment("Dump composter inputs AFTER applying the changes.").define("dump_composter_after", false);
        DUMP_STRIPPING = builder.comment("Dump stripping transitions BEFORE applying the changes.").define("dump_stripping", false);
        DUMP_STRIPPING_AFTER = builder.comment("Dump stripping transitions AFTER applying the changes.").define("dump_stripping_after", false);
        DUMP_PATHING = builder.comment("Dump pathing transitions BEFORE applying the changes.").define("dump_pathing", false);
        DUMP_PATHING_AFTER = builder.comment("Dump pathing transitions AFTER applying the changes.").define("dump_pathing_after", false);
        DEFAULT_ENCHANTMENT_VALUE = builder.comment("The default enchantment value of items. Change this if you have a mod installed that makes every item enchantable. If you're unsure, leave this unchanged and run the item dumping. You'll see if you need to change it or not.").defineInRange("default_enchantment_value", 0, 0, Integer.MAX_VALUE);
        builder.pop();
        builder.comment("Settings related to blocks. Format is \"blockid;value\", unless stated otherwise.").push("blocks");
        DESTROY_TIME = builder.comment("How long the block takes to break. 0.5 is dirt, 1.5 is stone, 50 is obsidian. -1 makes the block unbreakable. Not recommended for blocks with blockstate-dependent destroy time that may be added by other mods. Default value: 0").define("destroy_time", new ArrayList<>());
        EXPLOSION_RESISTANCE = builder.comment("How explosion-resistant the block is. 0.5 is dirt, 6 is stone and cobblestone, 1200 is obsidian. 3600000 or more makes the block completely explosion resistant. Default value: 0").define("explosion_resistance", new ArrayList<>());
        REQUIRES_TOOL = builder.comment("Whether you need a corresponding tool to get block drops (e.g. stone, ores) or if the harvest tool type only speeds up the breaking speed (e.g. dirt, gravel). Default value: false").define("requires_tool", new ArrayList<>());
        LIGHT_EMISSION = builder.comment("The light emitted by this block. Not recommended for blocks with blockstate-dependant light emission (e. g. furnaces). Default value: 0").define("light_emission", new ArrayList<>());
        FRICTION = builder.comment("The friction multiplier applied to entities moving on this block. Lower value means more slowdown. Default value: 0.6").define("friction", new ArrayList<>());
        SPEED_FACTOR = builder.comment("Accelerates (if > 1) or slows down (if between 0 and 1) entities that walk upon this block. Default value: 1").define("speed_factor", new ArrayList<>());
        JUMP_FACTOR = builder.comment("Allows entities on this block to jump higher (if > 1) or lower (if between 0 and 1). Default value: 1").define("jump_factor", new ArrayList<>());
        SOUND_TYPE = builder.comment("The sound type the block has. Only vanilla sound types are supported. Default value: stone. Vanilla sound types are:", "wood, gravel, grass, lily_pad, stone, metal, glass, wool, sand, snow, ladder, anvil, slime_block, honey_block, wet_grass, coral_block, bamboo, bamboo_sapling, scaffolding, sweet_berry_bush, crop, hard_crop, vine, nether_wart, lantern, stem, nylium, fungus, roots, shroomlight, weeping_vine, twisting_vine, soul_sand, soul_soil, basalt, wart_block, netherrack, nether_bricks, nether_sprouts, nether_ore, bone_block, netherite_block, ancient_debris, lodestone, chain, nether_gold_ore, gilded_blackstone").define("sound_type", new ArrayList<>());
        builder.pop();
        builder.comment("Settings related to items. Format is \"item;value\", unless stated otherwise.").push("items");
        MAX_DAMAGE = builder.comment("The max durability an item has. Can only be set on damageable items. Default value: 0").define("max_damage", new ArrayList<>());
        MAX_STACK_SIZE = builder.comment("The max stack size an item has. Can't be set on damageable items. Default value: 64 (1 for damageable items)").define("max_stack_size", new ArrayList<>());
        GROUP = builder.comment("The item group (= creative tab) of an item. Use \"none\" to remove the item from any item group. Default value: none").define("group", new ArrayList<>());
        IS_FIRE_RESISTANT = builder.comment("Whether the item should have the fire and lava shielding properties of netherite or not. Default value: false").define("is_fire_resistant", new ArrayList<>());
        RARITY = builder.comment("Sets the item rarity (currently only affects text color). Must be one of common (white), uncommon (yellow), rare (aqua) and epic (light purple). Default value: common").define("rarity", new ArrayList<>());
        ENCHANTMENT_VALUE = builder.comment("Sets the enchantment value of the item. Default value: the default enchantment value defined above").define("enchantment_value", new ArrayList<>());
        REPAIR_MATERIAL = builder.comment("Sets the repair material of the item. Tags (e.g. #minecraft:planks) are also allowed.").define("repair_material", new ArrayList<>());
        builder.comment("Settings related to armor. Only armor items (excluding the elytra) can be affected, anything else will be skipped.").push("armor");
        DEFENSE = builder.comment("Sets the armor value.").define("defense", new ArrayList<>());
        TOUGHNESS = builder.comment("Sets the armor toughness value. Default value: 0").define("toughness", new ArrayList<>());
        KNOCKBACK_RESISTANCE = builder.comment("Sets the knockback resistance. Default value: 0").define("knockback_resistance", new ArrayList<>());
        builder.pop();
        builder.comment("Settings related to tools. Only tool, sword and trident items can be affected, anything else will be skipped.").push("tools");
        ATTACK_DAMAGE = builder.comment("Sets the attack damage.").define("attack_damage", new ArrayList<>());
        ATTACK_SPEED = builder.comment("Sets the attack speed.").define("attack_speed", new ArrayList<>());
        EFFICIENCY = builder.comment("Sets the efficiency. Does not work for swords and tridents.").define("efficiency", new ArrayList<>());
        builder.pop();
        builder.pop();
        builder.comment("Settings related to enchantments. Format is \"enchantment;value\", unless stated otherwise.").push("enchantments");
        ENCHANTMENT_RARITY = builder.comment("The enchantment rarity of this enchantment. Must be one of common (10), uncommon (5), rare (2) and very_rare (1).").define("rarity", new ArrayList<>());
        ENCHANTMENT_ITEM_GROUP = builder.comment("The item group this enchantment type's enchanted books are in. As soon as you add one for an item group, you need to re-add every enchantment type for that group as well. Enchantment types are:", "vanishable, breakable, wearable, armor, armor_chest, armor_feet, armor_head, armor_legs, weapon, digger, bow, crossbow, fishing_rod, trident").define("group", new ArrayList<>());
        REMOVE_ENCHANTMENT_ITEM_GROUPS = builder.comment("Remove enchanted books from creative tabs. Runs before group, so re-adding is possible.").define("remove_item_groups", false);
        builder.pop();
        builder.push("composter");
        COMPOSTER_INPUTS = builder.comment("Define additional composter inputs. Format is \"itemid;chance\", with chance being a percentage between 0.0 and 1.0.").define("inputs", new ArrayList<>());
        COMPOSTER_CLEAR = builder.comment("Whether to clear the default composter inputs and have the composter inputs only contain the stuff defined here.").define("clear", false);
        builder.pop();
        builder.push("stripping");
        AXE_BLOCKS = builder.comment("Define additional stripping transitions. Format is \"fromid;toid\", with both of them being in the modid:blockid format.").define("transitions", new ArrayList<>());
        AXE_CLEAR = builder.comment("Whether to clear the default stripping transitions and have the stripping transitions only contain the stuff defined here.").define("clear", false);
        builder.pop();
        builder.push("pathing");
        SHOVEL_BLOCKS = builder.comment("Define additional pathing transitions. Format is \"fromid;toid\", with both of them being in the modid:blockid format.").define("transitions", new ArrayList<>());
        SHOVEL_CLEAR = builder.comment("Whether to clear the default pathing transitions and have the pathing transitions only contain the stuff defined here.").define("clear", false);
        builder.pop();
        builder.push("tilling");
        HOE_BLOCKS = builder.comment("Define additional tilling transitions. Format is \"fromid;toid\", with both of them being in the modid:blockid format.").define("transitions", new ArrayList<>());
        HOE_CLEAR = builder.comment("Whether to clear the default tilling transitions and have the tilling transitions only contain the stuff defined here.").define("clear", false);
        builder.pop();
        builder.push("entities");
        ENTITY_MODIFIERS = builder.comment("Apply entity attribute modifiers on spawning. To get the default value of an attribute, make a superflat world without mob spawning, spawn the desired mob, and run \"/attribute @e[type=<entityid>,limit=1] <attributeid> get\". Format is \"entity;attribute;amount;operation\":", "entity: the entity id (e.g. minecraft:rabbit)", "attribute: the attribute id (e.g. minecraft:generic.max_health)", "amount: the amount of the modifier (e.g. 5)", "operation: 0 for addition, 1 for multiply base, 2 for multiply total. See https://minecraft.fandom.com/wiki/Attribute for more information").define("modifiers", new ArrayList<>());
        builder.pop();
        builder.push("villager_trading");
        builder.comment("Adds new villager trades. villager_x_trades defines the villager level (1-5). Due to technical reasons, if you add trades for a specific profession for a specific level, you need to re-add all trades for that profession level. E. g. if you wanted to add an enchanted book trade to a level 5 librarian, you need to re-add all other trades for a level 5 librarian as well. See https://minecraft.fandom.com/wiki/Trading for the vanilla defaults", "Format is profession;uses;xp;pricemultiplier;tradetype;tradetype-specific-args", "profession: villager profession (e. g. minecraft:librarian)", "uses: how often the trade can be traded before it is locked", "xp: the amount of villager xp given to the villager", "price multiplier: every time you trade with a villager, the selling price is multiplied with 1 - this value (e. g. 0.1 means that every time you use the trade, the price is lowered by 10%)", "tradetype and corresponding tradetype-specific-args can take the following values (values in [] are optional):", "  normal: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;sellItemCount - a normal trade that takes 1 or 2 stacks in and gives 1 stack out. The items are item ids (e. g. minecraft:emerald), the item counts are numbers between 1 and 64", "  dyed: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem\" - the sell item (e. g. leather armor) will be randomly dyed", "  map: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]structure;mapdecoration\" - structure is a structure id (e. g. minecraft:stronghold), mapdecoration can be one of the following: \"player\", \"frame\", \"red_marker\", \"blue_marker\", \"target_x\", \"target_point\", \"player_off_map\", \"player_off_limits\", \"mansion\", \"monument\", \"red_x\", \"banner_black\", \"banner_blue\", \"banner_brown\", \"banner_cyan\", \"banner_gray\", \"banner_green\", \"banner_light_blue\", \"banner_light_gray\", \"banner_lime\", \"banner_magenta\", \"banner_orange\", \"banner_pink\", \"banner_purple\", \"banner_red\", \"banner_white\", \"banner_yellow\"", "  enchantedbook: buyItem1;[buyItem2;buyItemCount2;]enchantment[;level] - enchantment to use (e. g. minecraft:sharpness, supports regex), level for the level (randomly selected if not stated). Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 book costs approx. 5 times more than a lvl 1 book. Adding more than one enchantment is impossible", "  enchanteditem: buyItem1;[buyItem2;buyItemCount2;]sellItem;enchantment;level - enchantment to use (e. g. minecraft:sharpness, supports regex), level for the level (randomly selected if not stated). Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 helmet costs approx. 5 times more than a lvl 1 helmet. Adding more than one enchantment is impossible", "  potion: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;potion - the potion (supports regex, randomly selects one if multiple fit) is applied to the sell item, so unless you have additional potion-like items added by other mods, this should only be minecraft:potion, minecraft:splash_potion, minecraft:lingering_potion or minecraft:tipped_arrow", "  stew: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]effect;duration - effect is an effect id (e. g. minecraft:strength), duration is the duration in ticks");
        VILLAGER_1_TRADES = builder.define("villager_1_trades", new ArrayList<>());
        VILLAGER_2_TRADES = builder.define("villager_2_trades", new ArrayList<>());
        VILLAGER_3_TRADES = builder.define("villager_3_trades", new ArrayList<>());
        VILLAGER_4_TRADES = builder.define("villager_4_trades", new ArrayList<>());
        VILLAGER_5_TRADES = builder.define("villager_5_trades", new ArrayList<>());
        builder.pop();
        builder.push("wandering_trader_trading");
        builder.comment("Adds new wandering trader trades. Due to how the wandering trader works, there are two lists: normal and last trades. When the trader spawns, five normal trades and one last trade are each randomly chosen from their corresponding lists. Note that as soon as you add anything in any of the lists, it removes all other trades (including the ones from the other list), and only this mod's added trades remain, so you need to re-add most things if you only want to add one trade. See https://minecraft.fandom.com/wiki/Trading for the vanilla defaults", "Format is uses;pricemultiplier;tradetype;tradetype-specific-args", "uses: how often the trade can be traded before it is locked", "price multiplier: every time you trade with a villager, the selling price is multiplied with 1 - this value (e. g. 0.1 means that every time you use the trade, the price is lowered by 10%)", "tradetype and corresponding tradetype-specific-args can take the following values (values in [] are optional):", "  normal: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;sellItemCount - a normal trade that takes 1 or 2 stacks in and gives 1 stack out. The items are item ids (e. g. minecraft:emerald), the item counts are numbers between 1 and 64", "  dyed: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem\" - the sell item (e. g. leather armor) will be randomly dyed", "  map: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]structure;mapdecoration\" - structure is a structure id (e. g. minecraft:stronghold), mapdecoration can be one of the following: \"player\", \"frame\", \"red_marker\", \"blue_marker\", \"target_x\", \"target_point\", \"player_off_map\", \"player_off_limits\", \"mansion\", \"monument\", \"red_x\", \"banner_black\", \"banner_blue\", \"banner_brown\", \"banner_cyan\", \"banner_gray\", \"banner_green\", \"banner_light_blue\", \"banner_light_gray\", \"banner_lime\", \"banner_magenta\", \"banner_orange\", \"banner_pink\", \"banner_purple\", \"banner_red\", \"banner_white\", \"banner_yellow\"", "  enchantedbook: buyItem1;[buyItem2;buyItemCount2;]enchantment[;level] - enchantment to use (e. g. minecraft:sharpness, supports regex), level for the level (randomly selected if not stated). Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 book costs approx. 5 times more than a lvl 1 book. Adding more than one enchantment is impossible", "  enchanteditem: buyItem1;[buyItem2;buyItemCount2;]sellItem;enchantment;level - enchantment to use (e. g. minecraft:sharpness, supports regex), level for the level (randomly selected if not stated). Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 helmet costs approx. 5 times more than a lvl 1 helmet. Adding more than one enchantment is impossible", "  potion: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;potion - the potion (supports regex, randomly selects one if multiple fit) is applied to the sell item, so unless you have additional potion-like items added by other mods, this should only be minecraft:potion, minecraft:splash_potion, minecraft:lingering_potion or minecraft:tipped_arrow", "  stew: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]effect;duration - effect is an effect id (e. g. minecraft:strength), duration is the duration in ticks");
        TRADER_NORMAL_TRADES = builder.define("trader_normal_trades", new ArrayList<>());
        TRADER_LAST_TRADES = builder.define("trader_last_trades", new ArrayList<>());
        builder.pop();
        SPEC = builder.build();
    }

    public static void read() {
        List<Block> BLOCK_REGISTRY = new ArrayList<>(ForgeRegistries.BLOCKS.getValues());
        List<Item> ITEM_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Item> ARMOR_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Item> TIERED_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Item> DIGGER_REGISTRY = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        List<Enchantment> ENCHANTMENT_REGISTRY = new ArrayList<>(ForgeRegistries.ENCHANTMENTS.getValues());
        BLOCK_REGISTRY.removeIf(e -> e.properties.isAir);
        ITEM_REGISTRY.remove(Items.AIR);
        ARMOR_REGISTRY.removeIf(e -> !(e instanceof ArmorItem));
        TIERED_REGISTRY.removeIf(e -> !(e instanceof TieredItem) && !(e instanceof TridentItem));
        DIGGER_REGISTRY.removeIf(e -> !(e instanceof DiggerItem));
        dump(DUMP_BLOCKS.get(), DUMP_BLOCKS_NON_DEFAULT.get(), DUMP_ITEMS.get(), DUMP_ITEMS_NON_DEFAULT.get(), DUMP_ENCHANTMENTS.get(), DUMP_GROUPS.get(), DUMP_COMPOSTER.get(), DUMP_STRIPPING.get(), DUMP_PATHING.get());
        if (REMOVE_ENCHANTMENT_ITEM_GROUPS.get()) {
            for (CreativeModeTab group : CreativeModeTab.TABS) {
                group.setEnchantmentCategories();
            }
        }
        LinkedHashMap<String, ItemStack> map = new LinkedHashMap<>();
        for (String group : ITEM_GROUP.get()) {
            String[] array = group.split(";");
            Item item = ConfigUtil.fromCollection(array[1], ITEM_REGISTRY);
            if (!array[0].equals("none")) {
                map.put(array[0], item == null ? ItemStack.EMPTY : new ItemStack(item));
            } else {
                Logger.error("Cannot use none as an item group id (is invalid for " + group + " in item_groups.item_group");
            }
        }
        for (Map.Entry<String, ItemStack> entry : map.entrySet()) {
            new CreativeModeTab(entry.getKey()) {
                @Override
                @Nonnull
                public ItemStack makeIcon() {
                    return entry.getValue();
                }
            };
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(DESTROY_TIME, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0 || e == -1).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.DESTROY_TIME = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(EXPLOSION_RESISTANCE, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0 && e <= 3600000).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.EXPLOSION_RESISTANCE = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Boolean> entry : ConfigUtil.getMap(REQUIRES_TOOL, BLOCK_REGISTRY, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.REQUIRES_TOOL = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Integer> entry : ConfigUtil.getMap(LIGHT_EMISSION, BLOCK_REGISTRY, ParsingUtil::parseInt, e -> e > -1 && e < 16).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.LIGHT_EMISSION = entry.getValue();
            BLOCKS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Block, Float> entry : ConfigUtil.getMap(FRICTION, BLOCK_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Block prop = BLOCKS.getOrDefault(entry.getKey(), new Properties.Block());
            prop.FRICTION = entry.getValue();
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
        for (Map.Entry<Item, CreativeModeTab> entry : ConfigUtil.getMap(GROUP, ITEM_REGISTRY, ParsingUtil::parseCreativeModeTab, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.GROUP = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Boolean> entry : ConfigUtil.getMap(IS_FIRE_RESISTANT, ITEM_REGISTRY, ParsingUtil::parseBoolean, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.IS_FIRE_RESISTANT = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Rarity> entry : ConfigUtil.getMap(RARITY, ITEM_REGISTRY, ParsingUtil::parseRarity, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.RARITY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(ENCHANTMENT_VALUE, ITEM_REGISTRY, ParsingUtil::parseInt, e -> e > -1).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.ENCHANTMENT_VALUE = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Lazy<Ingredient>> entry : ConfigUtil.getMap(REPAIR_MATERIAL, ITEM_REGISTRY, ParsingUtil::parseRepairMaterial, e -> true).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), itemProperties(entry.getKey()));
            prop.REPAIR_MATERIAL = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Item, Integer> entry : ConfigUtil.getMap(DEFENSE, ARMOR_REGISTRY, ParsingUtil::parseInt, e -> e > -1).entrySet()) {
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
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(EFFICIENCY, DIGGER_REGISTRY, ParsingUtil::parseFloat, e -> e >= 0).entrySet()) {
            Properties.Item prop = ITEMS.getOrDefault(entry.getKey(), new Properties.Tool());
            ((Properties.Tool) prop).EFFICIENCY = entry.getValue();
            ITEMS.put(entry.getKey(), prop);
        }
        for (Map.Entry<Enchantment, Enchantment.Rarity> entry : ConfigUtil.getMap(ENCHANTMENT_RARITY, ENCHANTMENT_REGISTRY, ParsingUtil::parseEnchantmentRarity, e -> true).entrySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.getOrDefault(entry.getKey(), new Properties.Enchantment());
            prop.RARITY = entry.getValue();
            ENCHANTMENTS.put(entry.getKey(), prop);
        }
        for (String value : ENCHANTMENT_ITEM_GROUP.get()) {
            String[] array = value.split(";");
            try {
                List<EnchantmentCategory> category = Arrays.stream(EnchantmentCategory.values()).filter(e -> e.name().matches(array[0].toUpperCase())).collect(Collectors.toList());
                CreativeModeTab group = ParsingUtil.parseCreativeModeTab(array[1], value, ConfigUtil.getPath(ENCHANTMENT_ITEM_GROUP.getPath()), e -> true);
                if (group != null) {
                    List<EnchantmentCategory> list = ENCHANTMENT_GROUPS.getOrDefault(group, new ArrayList<>());
                    list.addAll(category);
                    list = list.stream().distinct().collect(Collectors.toList());
                    ENCHANTMENT_GROUPS.put(group, list);
                } else {
                    ConfigUtil.logInvalid(array[1], value, ConfigUtil.getPath(ENCHANTMENT_ITEM_GROUP.getPath()));
                }
            } catch (IllegalArgumentException e) {
                ConfigUtil.logInvalid(array[0], value, ConfigUtil.getPath(ENCHANTMENT_ITEM_GROUP.getPath()));
            }
        }
    }

    public static void workCreative() {
        for (Item item : ITEMS.keySet()) {
            Properties.Item prop = ITEMS.get(item);
            if (prop.GROUP != null) {
                item.category = prop.GROUP;
                searchReload = true;
            }
        }
    }

    public static void work() {
        for (Block block : BLOCKS.keySet()) {
            Properties.Block prop = BLOCKS.get(block);
            if (prop.DESTROY_TIME != null) {
                block.properties.destroyTime = prop.DESTROY_TIME;
                MIXIN_DESTROY_TIME.put(block, prop.DESTROY_TIME);
            }
            if (prop.EXPLOSION_RESISTANCE != null) {
                block.properties.explosionResistance = prop.EXPLOSION_RESISTANCE;
                block.explosionResistance = prop.EXPLOSION_RESISTANCE;
            }
            if (prop.REQUIRES_TOOL != null) {
                block.properties.requiresCorrectToolForDrops = prop.REQUIRES_TOOL;
                MIXIN_REQUIRES_TOOL.put(block, prop.REQUIRES_TOOL);
            }
            if (prop.LIGHT_EMISSION != null) {
                block.properties.lightEmission = l -> prop.LIGHT_EMISSION;
                MIXIN_LIGHT_EMISSION.put(block, prop.LIGHT_EMISSION);
            }
            if (prop.FRICTION != null) {
                block.properties.friction = prop.FRICTION;
                block.friction = prop.FRICTION;
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
            if (prop.MAX_DAMAGE != null && item.maxDamage > 0) {
                item.maxDamage = prop.MAX_DAMAGE;
            }
            if (prop.MAX_STACK_SIZE != null && item.maxDamage == 0) {
                item.maxStackSize = prop.MAX_STACK_SIZE;
            }
            if (prop.IS_FIRE_RESISTANT != null) {
                item.isFireResistant = prop.IS_FIRE_RESISTANT;
            }
            if (prop.RARITY != null) {
                item.rarity = prop.RARITY;
            }
            if (prop.ENCHANTMENT_VALUE != null) {
                MIXIN_ENCHANTMENT_VALUE.put(item, prop.ENCHANTMENT_VALUE);
            }
            if (prop.REPAIR_MATERIAL != null) {
                MIXIN_REPAIR_ITEM.put(item, prop.REPAIR_MATERIAL);
            }
            if (item instanceof ArmorItem armoritem) {
                Properties.Armor armorprop = (Properties.Armor) prop;
                if (armorprop.ARMOR != null) {
                    armoritem.defense = armorprop.ARMOR;
                }
                if (armorprop.TOUGHNESS != null) {
                    armoritem.toughness = armorprop.TOUGHNESS;
                }
                if (armorprop.KNOCKBACK_RESISTANCE != null) {
                    armoritem.knockbackResistance = armorprop.KNOCKBACK_RESISTANCE;
                }
                if (armorprop.ARMOR != null || armorprop.TOUGHNESS != null || armorprop.KNOCKBACK_RESISTANCE != null) {
                    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                    UUID uuid = ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[armoritem.getSlot().getIndex()];
                    if (armoritem.defense > 0) {
                        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", armoritem.defense, AttributeModifier.Operation.ADDITION));
                    }
                    if (armoritem.toughness > 0) {
                        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", armoritem.toughness, AttributeModifier.Operation.ADDITION));
                    }
                    if (armoritem.knockbackResistance > 0) {
                        builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", armoritem.knockbackResistance, AttributeModifier.Operation.ADDITION));
                    }
                    armoritem.defaultModifiers = builder.build();
                }
            } else if (item instanceof TieredItem toolitem) {
                Properties.Tool toolprop = (Properties.Tool) prop;
                if (toolprop.EFFICIENCY != null && toolitem instanceof DiggerItem) {
                    ((DiggerItem) toolitem).speed = toolprop.EFFICIENCY;
                }
                if (toolprop.ATTACK_DAMAGE != null || toolprop.ATTACK_SPEED != null) {
                    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                    if (toolitem instanceof DiggerItem tool) {
                        AttributeModifier speedMod = tool.defaultModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        double speed = toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedMod != null ? speedMod.getAmount() : 0;
                        if (toolprop.ATTACK_DAMAGE != null) {
                            tool.attackDamageBaseline = toolprop.ATTACK_DAMAGE - 1;
                        }
                        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Tool modifier", tool.attackDamageBaseline, AttributeModifier.Operation.ADDITION));
                        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Tool modifier", speed, AttributeModifier.Operation.ADDITION));
                        tool.defaultModifiers = builder.build();
                    }
                    if (toolitem instanceof SwordItem sword) {
                        AttributeModifier speedMod = sword.defaultModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                        double speed = toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedMod != null ? speedMod.getAmount() : 0;
                        if (toolprop.ATTACK_DAMAGE != null) {
                            sword.attackDamage = toolprop.ATTACK_DAMAGE - 1;
                        }
                        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Tool modifier", sword.attackDamage, AttributeModifier.Operation.ADDITION));
                        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Tool modifier", speed, AttributeModifier.Operation.ADDITION));
                        sword.defaultModifiers = builder.build();
                    }
                }
            } else if (item instanceof TridentItem trident) {
                Properties.Tool toolprop = (Properties.Tool) prop;
                AttributeModifier damageMod = trident.defaultModifiers.get(Attributes.ATTACK_DAMAGE).stream().findFirst().orElse(null);
                AttributeModifier speedMod = trident.defaultModifiers.get(Attributes.ATTACK_SPEED).stream().findFirst().orElse(null);
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Tool modifier", toolprop.ATTACK_DAMAGE != null ? toolprop.ATTACK_DAMAGE - 4 : damageMod != null ? damageMod.getAmount() : 0, AttributeModifier.Operation.ADDITION));
                builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Tool modifier", toolprop.ATTACK_SPEED != null ? toolprop.ATTACK_SPEED - 4 : speedMod != null ? speedMod.getAmount() : 0, AttributeModifier.Operation.ADDITION));
                trident.defaultModifiers = builder.build();
            }
        }
        for (Enchantment enchantment : ENCHANTMENTS.keySet()) {
            Properties.Enchantment prop = ENCHANTMENTS.get(enchantment);
            if (prop.RARITY != null) {
                enchantment.rarity = prop.RARITY;
            }
        }
        for (Item item : ForgeRegistries.ITEMS) {
            if (item.category != null && item.category.getRecipeFolderName().equals("none")) {
                item.category = null;
            }
        }
        for (CreativeModeTab group : ENCHANTMENT_GROUPS.keySet()) {
            group.setEnchantmentCategories(ENCHANTMENT_GROUPS.get(group).toArray(new EnchantmentCategory[0]));
        }
        if (REMOVE_EMPTY_ITEM_GROUPS.get()) {
            List<CreativeModeTab> groups = Lists.newArrayList(CreativeModeTab.TABS);
            List<CreativeModeTab> result = Lists.newArrayList(CreativeModeTab.TABS);
            for (CreativeModeTab group : groups) {
                if (group.getEnchantmentCategories().length > 0) continue;
                boolean b = false;
                for (Item item : ForgeRegistries.ITEMS) {
                    if (item.category == group) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    result.remove(group);
                }
            }
            while (result.size() < 4) {
                result.add(null);
            }
            result.add(4, CreativeModeTab.TAB_HOTBAR);
            result.add(5, CreativeModeTab.TAB_SEARCH);
            while (result.size() < 11) {
                result.add(null);
            }
            result.add(11, CreativeModeTab.TAB_INVENTORY);
            for (int i = 0; i < result.size(); i++) {
                CreativeModeTab group = result.get(i);
                if (group != null) {
                    group.id = i;
                }
            }
            CreativeModeTab.TABS = result.toArray(new CreativeModeTab[0]);
            CreativeModeTab.TAB_BUILDING_BLOCKS.id = 0;
        }
        Object2FloatMap<ItemLike> composterTransitions = new Object2FloatOpenHashMap<>(ComposterBlock.COMPOSTABLES);
        Map<Block, Block> axeTransitions = new HashMap<>(AxeItem.STRIPPABLES);
        Map<Block, BlockState> shovelTransitions = new HashMap<>(ShovelItem.FLATTENABLES);
        Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> hoeTransitions = new HashMap<>(HoeItem.TILLABLES);
        ComposterBlock.COMPOSTABLES.clear();
        if (!COMPOSTER_CLEAR.get()) {
            ComposterBlock.COMPOSTABLES.putAll(composterTransitions);
        }
        AxeItem.STRIPPABLES = new HashMap<>();
        if (!AXE_CLEAR.get()) {
            AxeItem.STRIPPABLES.putAll(axeTransitions);
        }
        ShovelItem.FLATTENABLES.clear();
        if (!SHOVEL_CLEAR.get()) {
            ShovelItem.FLATTENABLES.putAll(shovelTransitions);
        }
        HoeItem.TILLABLES.clear();
        if (!HOE_CLEAR.get()) {
            HoeItem.TILLABLES.putAll(hoeTransitions);
        }
        List<Block> BLOCK_REGISTRY = new ArrayList<>(ForgeRegistries.BLOCKS.getValues());
        BLOCK_REGISTRY.removeIf(e -> e.properties.isAir);
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(COMPOSTER_INPUTS, ForgeRegistries.ITEMS.getValues(), ParsingUtil::parseFloat, e -> e >= 0 && e <= 1).entrySet()) {
            if (entry.getValue() != null) {
                ComposterBlock.COMPOSTABLES.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(AXE_BLOCKS, BLOCK_REGISTRY, ParsingUtil::parseBlock, e -> true).entrySet()) {
            if (entry.getValue() != null) {
                AxeItem.STRIPPABLES.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(SHOVEL_BLOCKS, BLOCK_REGISTRY, ParsingUtil::parseBlock, e -> true).entrySet()) {
            if (entry.getValue() != null) {
                ShovelItem.FLATTENABLES.put(entry.getKey(), entry.getValue().defaultBlockState());
            }
        }
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(HOE_BLOCKS, BLOCK_REGISTRY, ParsingUtil::parseBlock, e -> true).entrySet()) {
            if (entry.getValue() != null) {
                HoeItem.TILLABLES.put(entry.getKey(), Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(entry.getValue().defaultBlockState())));
            }
        }
        MODIFIERS.clear();
        for (Map.Entry<EntityType<?>, Map<Attribute, List<AttributeModifier>>> entry : ConfigUtil.parseAttributeList(ENTITY_MODIFIERS).entrySet()) {
            for (Attribute attribute : entry.getValue().keySet()) {
                for (AttributeModifier modifier : entry.getValue().get(attribute)) {
                    Map<Attribute, List<AttributeModifier>> map = MODIFIERS.getOrDefault(entry.getKey(), new HashMap<>());
                    List<AttributeModifier> list = map.getOrDefault(attribute, new ArrayList<>());
                    list.add(modifier);
                    map.put(attribute, list);
                    MODIFIERS.put(entry.getKey(), map);
                }
            }
        }
        dump(DUMP_BLOCKS_AFTER.get(), DUMP_BLOCKS_AFTER_NON_DEFAULT.get(), DUMP_ITEMS_AFTER.get(), DUMP_ITEMS_AFTER_NON_DEFAULT.get(), DUMP_ENCHANTMENTS_AFTER.get(), DUMP_GROUPS_AFTER.get(), DUMP_COMPOSTER_AFTER.get(), DUMP_STRIPPING_AFTER.get(), DUMP_PATHING_AFTER.get());
    }

    private static void dump(boolean blocks, boolean blocksNonDefault, boolean items, boolean itemsNonDefault, boolean enchantments, boolean groups, boolean composter, boolean stripping, boolean pathing) {
        if (blocks || blocksNonDefault) {
            Logger.forceInfo("Blocks:");
            for (Block block : ForgeRegistries.BLOCKS) {
                if (block.properties.isAir) continue;
                StringBuilder builder = new StringBuilder(block.getRegistryName().toString()).append(" - ");
                DumpingUtil.appendHardness(builder, block, blocksNonDefault);
                DumpingUtil.appendResistance(builder, block, blocksNonDefault);
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
                if (item == Items.AIR) continue;
                StringBuilder builder = new StringBuilder(item.getRegistryName().toString()).append(" - ");
                DumpingUtil.appendMaxDamage(builder, item, itemsNonDefault);
                DumpingUtil.appendMaxStackSize(builder, item, itemsNonDefault);
                DumpingUtil.appendCreativeModeTab(builder, item, itemsNonDefault);
                DumpingUtil.appendIsImmuneToFire(builder, item, itemsNonDefault);
                DumpingUtil.appendRarity(builder, item, itemsNonDefault);
                DumpingUtil.appendEnchantmentValue(builder, item, itemsNonDefault);
                if (item instanceof ArmorItem armor) {
                    DumpingUtil.appendArmor(builder, armor, itemsNonDefault);
                    DumpingUtil.appendToughness(builder, armor, itemsNonDefault);
                    DumpingUtil.appendKnockbackResistance(builder, armor, itemsNonDefault);
                } else if (item instanceof DiggerItem tool) {
                    DumpingUtil.appendEfficiency(builder, tool, itemsNonDefault);
                    DumpingUtil.appendDamage(builder, tool, itemsNonDefault);
                    DumpingUtil.appendSpeed(builder, tool, itemsNonDefault);
                } else if (item instanceof SwordItem sword) {
                    DumpingUtil.appendDamage(builder, sword, itemsNonDefault);
                    DumpingUtil.appendSpeed(builder, sword, itemsNonDefault);
                } else if (item instanceof TridentItem trident) {
                    DumpingUtil.appendDamage(builder, trident, itemsNonDefault);
                    DumpingUtil.appendSpeed(builder, trident, itemsNonDefault);
                }
                Logger.forceInfo(builder.substring(0, builder.length() - (builder.toString().equals(item.getRegistryName().toString() + " - ") ? 3 : 2)));
            }
        }
        if (enchantments) {
            Logger.forceInfo("Enchantments:");
            for (Enchantment e : ForgeRegistries.ENCHANTMENTS) {
                Logger.forceInfo(e.getRegistryName().toString() + " - rarity: " + e.getRarity().toString().toLowerCase() + ", type: " + e.category.toString().toLowerCase());
            }
        }
        if (groups) {
            Logger.forceInfo("Item groups:");
            for (CreativeModeTab group : CreativeModeTab.TABS) {
                if (group == null) continue;
                EnchantmentCategory[] types = group.getEnchantmentCategories();
                StringBuilder builder = new StringBuilder(group.getRecipeFolderName());
                if (types.length > 0) {
                    builder.append(" - enchantment types: ");
                }
                for (EnchantmentCategory type : types) {
                    builder.append(type.name().toLowerCase()).append(", ");
                }
                Logger.forceInfo(types.length > 0 ? builder.substring(0, builder.length() - 2) : builder.toString());
            }
        }
        if (composter) {
            Logger.forceInfo("Composter inputs:");
            for (Map.Entry<ItemLike, Float> entry : ComposterBlock.COMPOSTABLES.entrySet()) {
                if (entry.getKey() != null) {
                    Logger.forceInfo(entry.getKey().asItem().getRegistryName().toString() + " -> " + entry.getValue());
                }
            }
        }
        if (stripping) {
            Logger.forceInfo("Stripping transitions:");
            for (Map.Entry<Block, Block> entry : AxeItem.STRIPPABLES.entrySet()) {
                Logger.forceInfo(entry.getKey().getRegistryName().toString() + " -> " + entry.getValue().getRegistryName().toString());
            }
        }
        if (pathing) {
            Logger.forceInfo("Pathing transitions:");
            for (Map.Entry<Block, BlockState> entry : ShovelItem.FLATTENABLES.entrySet()) {
                Logger.forceInfo(entry.getKey().getRegistryName().toString() + " -> " + entry.getValue().getBlock().getRegistryName().toString());
            }
        }
    }

    private static Properties.Item itemProperties(Item item) {
        return item instanceof ArmorItem ? new Properties.Armor() : item instanceof TieredItem || item instanceof TridentItem ? new Properties.Tool() : new Properties.Item();
    }

    static void searchReload() {
        if (searchReload) {
            Minecraft.getInstance().createSearchTrees();
        }
    }
}
