package ihh.propertymodifier;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.VillagerTradingManager;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.v4.runtime.misc.Triple;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"FieldMayBeFinal", "ConstantConditions"})
public final class ServerConfig {
    public static ForgeConfigSpec SPEC;
    private static ForgeConfigSpec.BooleanValue DUMP_COMPOSTER;
    private static ForgeConfigSpec.BooleanValue DUMP_COMPOSTER_AFTER;
    private static ForgeConfigSpec.BooleanValue DUMP_STRIPPING;
    private static ForgeConfigSpec.BooleanValue DUMP_STRIPPING_AFTER;
    private static ForgeConfigSpec.BooleanValue DUMP_PATHING;
    private static ForgeConfigSpec.BooleanValue DUMP_PATHING_AFTER;
    private static ForgeConfigSpec.BooleanValue DUMP_TILLING;
    private static ForgeConfigSpec.BooleanValue DUMP_TILLING_AFTER;
    private static ForgeConfigSpec.ConfigValue<List<String>> COMPOSTER_INPUTS;
    private static ForgeConfigSpec.BooleanValue COMPOSTER_CLEAR;
    private static ForgeConfigSpec.ConfigValue<List<String>> AXE_BLOCKS;
    static ForgeConfigSpec.BooleanValue AXE_CLEAR;
    private static ForgeConfigSpec.ConfigValue<List<String>> SHOVEL_BLOCKS;
    static ForgeConfigSpec.BooleanValue SHOVEL_CLEAR;
    private static ForgeConfigSpec.ConfigValue<List<String>> HOE_BLOCKS;
    static ForgeConfigSpec.BooleanValue HOE_CLEAR;
    private static ForgeConfigSpec.ConfigValue<List<String>> ENTITY_MODIFIERS;
    public static Map<EntityType<?>, Map<Attribute, List<AttributeModifier>>> MODIFIERS = new HashMap<>();
    private static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_1_TRADES;
    private static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_2_TRADES;
    private static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_3_TRADES;
    private static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_4_TRADES;
    private static ForgeConfigSpec.ConfigValue<List<String>> VILLAGER_5_TRADES;
    private static ForgeConfigSpec.ConfigValue<List<String>> TRADER_NORMAL_TRADES;
    private static ForgeConfigSpec.ConfigValue<List<String>> TRADER_LAST_TRADES;
    private static Object2FloatMap<IItemProvider> COMPOSTER_TRANSITIONS;
    private static Map<Block, Block> AXE_TRANSITIONS;
    private static Map<Block, BlockState> SHOVEL_TRANSITIONS;
    private static Map<Block, BlockState> HOE_TRANSITIONS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Dumps all corresponding values into the logs.").push("dump");
        DUMP_COMPOSTER = builder.comment("Dump composter inputs BEFORE applying the changes.").define("dump_composter", false);
        DUMP_COMPOSTER_AFTER = builder.comment("Dump composter inputs AFTER applying the changes.").define("dump_composter_after", false);
        DUMP_STRIPPING = builder.comment("Dump stripping transitions BEFORE applying the changes.").define("dump_stripping", false);
        DUMP_STRIPPING_AFTER = builder.comment("Dump stripping transitions AFTER applying the changes.").define("dump_stripping_after", false);
        DUMP_PATHING = builder.comment("Dump pathing transitions BEFORE applying the changes.").define("dump_pathing", false);
        DUMP_PATHING_AFTER = builder.comment("Dump pathing transitions AFTER applying the changes.").define("dump_pathing_after", false);
        DUMP_TILLING = builder.comment("Dump tilling transitions BEFORE applying the changes.").define("dump_tilling", false);
        DUMP_TILLING_AFTER = builder.comment("Dump tilling transitions AFTER applying the changes.").define("dump_tilling_after", false);
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
        builder.push("entities");
        ENTITY_MODIFIERS = builder.comment("Apply entity attribute modifiers on spawning. To get the default value of an attribute, make a superflat world without mob spawning, spawn the desired mob, and run \"/attribute @e[type=<entityid>,limit=1] <attributeid> get\". Format is \"entity;attribute;amount;operation\":", "entity: the entity id (e.g. minecraft:rabbit)", "attribute: the attribute id (e.g. minecraft:generic.max_health)", "amount: the amount of the modifier (e.g. 5)", "operation: 0 for addition, 1 for multiply base, 2 for multiply total. See https://minecraft.fandom.com/wiki/Attribute to see what they each do").define("modifiers", new ArrayList<>());
        builder.pop();
        builder.push("villager_trading");
        builder.comment("Adds new villager trades. villager_x_trades defines the villager level (1-5). Due to technical reasons, if you add trades for a specific profession for a specific level, you need to re-add all trades for that profession level. E. g. if you wanted to add an enchanted book trade to a level 5 librarian, you need to re-add the vanilla trade for a level 5 librarian (20 emeralds -> 1 name tag, see Minecraft Wiki) as well.", "Format is \"profession;uses;xp;pricemultiplier;tradetype;tradetype-specific-arguments\"", "Profession: villager profession (e. g. \"minecraft:librarian\")", "Uses: How often the trade can be traded before it is locked. This is 12 or 16 for most trades, for enchanted items, it is 3", "Xp: The amount of villager xp given to the villager", "Price multiplier: Every time you trade with a villager, the selling price is multiplied with (1 - this value). For example, a value of 0.1 means that every time you use the trade, the price is lowered by 10%. Vanilla default for most items is 0.05, for tools, armor or enchanted books, it is 0.2", "Tradetype and corresponding tradetype-specific args can take the following values (values in [] are optional):", "  \"normal\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];sellItem;sellItemCount\" - a normal trade that takes 1 or 2 stacks in and gives 1 stack out. The items are item ids (e. g. \"minecraft:emerald\"), the item counts are numbers between 1 and 64", "  \"dyed\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];sellItem\" - the sell item (e. g. leather armor) will be randomly dyed", "  \"map\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];structure;mapdecoration\" - structure is a structure id (e. g. \"minecraft:stronghold\"), mapdecoration can be one of the following: \"player\", \"frame\", \"red_marker\", \"blue_marker\", \"target_x\", \"target_point\", \"player_off_map\", \"player_off_limits\", \"mansion\", \"monument\", \"red_x\", \"banner_black\", \"banner_blue\", \"banner_brown\", \"banner_cyan\", \"banner_gray\", \"banner_green\", \"banner_light_blue\", \"banner_light_gray\", \"banner_lime\", \"banner_magenta\", \"banner_orange\", \"banner_pink\", \"banner_purple\", \"banner_red\", \"banner_white\", \"banner_yellow\"", "  \"enchantedbook\": \"buyItem1;[buyItem2];[buyItemCount2];enchantment;level\" - outputs an enchanted book (using \"any\" will randomly enchant the book), at which rarity the quantity of buyItem1 is scaled - so if buyItem1 was minecraft:emerald, a sharpness 5 book costs approx. 5 times more emeralds than a sharpness 1 book", "  \"enchanteditem\": \"buyItem1;[buyItem2];[buyItemCount2];sellItem;enchantment;level\" - the sell item is enchanted (using \"any\" will randomly enchant the item), and the quantity of buyItem1 is scaled at the enchantment rarity, similar to the enchanted book trade type", "  \"potion\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];sellItem;potion\" - the potion (using \"any\" will randomly select one) is applied to the sell item, so unless you have additional potion-like items added by other mods, this should only be \"minecraft:potion\", \"minecraft:splash_potion\", \"minecraft:lingering_potion\" or \"minecraft:tipped_arrow\"", "  \"stew\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];effect;duration\" - effect is an effect id (e. g. \"minecraft:strength\"), duration is an integer that resembles the ticks the effects last.");
        VILLAGER_1_TRADES = builder.define("villager_1_trades", new ArrayList<>());
        VILLAGER_2_TRADES = builder.define("villager_2_trades", new ArrayList<>());
        VILLAGER_3_TRADES = builder.define("villager_3_trades", new ArrayList<>());
        VILLAGER_4_TRADES = builder.define("villager_4_trades", new ArrayList<>());
        VILLAGER_5_TRADES = builder.define("villager_5_trades", new ArrayList<>());
        builder.pop();
        builder.push("wandering_trader_trading");
        builder.comment("Adds new wandering trader trades. Due to how the wandering trader works, there are two lists: normal and last trades. When the trader spawns, five normal trades and one last trade are each randomly chosen from their corresponding lists. Note that as soon as you add anything in any of the lists, it removes all other trades (including the ones from the other list), and only this mod's added trades remain, so you need to re-add most things if you only want to add one trade. See the Minecraft Wiki for the default trades.", "Format is \"uses;pricemultiplier;tradetype;tradetype-specific-args\"", "Uses: How often the trade can be traded before it is locked. This is 12 or 16 for most trades, for enchanted items, it is 3", "Price multiplier: Every time you trade with a villager, the selling price is multiplied with (1 - this value). For example, a value of 0.1 means that every time you use the trade, the price is lowered by 10%. Vanilla default for most items is 0.05, for tools, armor, enchanted books and maps, it is 0.2", "Tradetype and corresponding tradetype-specific args can take the following values (values in [] are optional):", "  \"normal\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];sellItem;sellItemCount\" - a normal trade that takes 1 or 2 stacks in and gives 1 stack out. The items are item ids (e. g. \"minecraft:emerald\"), the item counts are numbers between 1 and 64", "  \"dyed\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];sellItem\" - the sell item (e. g. leather armor) will be randomly dyed", "  \"map\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];structure;mapdecoration\" - structure is a structure id (e. g. \"minecraft:stronghold\"), mapdecoration can be one of the following: \"player\", \"frame\", \"red_marker\", \"blue_marker\", \"target_x\", \"target_point\", \"player_off_map\", \"player_off_limits\", \"mansion\", \"monument\", \"red_x\", \"banner_black\", \"banner_blue\", \"banner_brown\", \"banner_cyan\", \"banner_gray\", \"banner_green\", \"banner_light_blue\", \"banner_light_gray\", \"banner_lime\", \"banner_magenta\", \"banner_orange\", \"banner_pink\", \"banner_purple\", \"banner_red\", \"banner_white\", \"banner_yellow\"", "  \"enchantedbook\": \"buyItem1;buyItem2;buyItemCount2\" - outputs a randomly enchanted book, at which rarity the quantity of buyItem1 is scaled - so if buyItem1 was minecraft:emerald, a sharpness 5 book costs approx. 5 times more emeralds than a sharpness 1 book", "  \"enchanteditem\": \"buyItem1;buyItem2;buyItemCount2;sellItem\" - the sell item is randomly enchanted, and the quantity of buyItem1 is scaled at the enchantment rarity, similar to the enchanted book trade type", "  \"potion\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];sellItem\" - a random potion is applied to sellItem, so unless you have additional potion-like items added by other mods, this should only be \"minecraft:potion\", \"minecraft:splash_potion\", \"minecraft:lingering_potion\" or \"minecraft:tipped_arrow\"", "  \"stew\": \"buyItem1;buyItemCount1;[buyItem2];[buyItemCount2];effect;duration\" - effect is an effect id (e. g. \"minecraft:strength\"), duration is an integer that resembles the ticks the effects last.");
        TRADER_NORMAL_TRADES = builder.define("trader_normal_trades", new ArrayList<>());
        TRADER_LAST_TRADES = builder.define("trader_last_trades", new ArrayList<>());
        builder.pop();
        SPEC = builder.build();
    }

    public static void work() {
        if (COMPOSTER_TRANSITIONS == null) COMPOSTER_TRANSITIONS = new Object2FloatOpenHashMap<>(ComposterBlock.CHANCES);
        if (AXE_TRANSITIONS == null) AXE_TRANSITIONS = new HashMap<>(AxeItem.BLOCK_STRIPPING_MAP);
        if (SHOVEL_TRANSITIONS == null) SHOVEL_TRANSITIONS = new HashMap<>(ShovelItem.SHOVEL_LOOKUP);
        if (HOE_TRANSITIONS == null) HOE_TRANSITIONS = new HashMap<>(HoeItem.HOE_LOOKUP);
        ComposterBlock.CHANCES.clear();
        ComposterBlock.CHANCES.putAll(COMPOSTER_TRANSITIONS);
        AxeItem.BLOCK_STRIPPING_MAP = new HashMap<>();
        AxeItem.BLOCK_STRIPPING_MAP.putAll(AXE_TRANSITIONS);
        ShovelItem.SHOVEL_LOOKUP.clear();
        ShovelItem.SHOVEL_LOOKUP.putAll(SHOVEL_TRANSITIONS);
        HoeItem.HOE_LOOKUP.clear();
        HoeItem.HOE_LOOKUP.putAll(HOE_TRANSITIONS);
        List<Block> BLOCK_REGISTRY = new ArrayList<>(ForgeRegistries.BLOCKS.getValues());
        BLOCK_REGISTRY.removeIf(e -> e.properties.isAir);
        dump(DUMP_COMPOSTER, DUMP_STRIPPING, DUMP_PATHING, DUMP_TILLING);
        if (COMPOSTER_CLEAR.get()) ComposterBlock.CHANCES.clear();
        if (AXE_CLEAR.get()) AxeItem.BLOCK_STRIPPING_MAP = new HashMap<>();
        if (SHOVEL_CLEAR.get()) ShovelItem.SHOVEL_LOOKUP.clear();
        if (HOE_CLEAR.get()) HoeItem.HOE_LOOKUP.clear();
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(COMPOSTER_INPUTS, new ArrayList<>(ForgeRegistries.ITEMS.getValues()), ConfigUtil::parseFloat, e -> e >= 0 && e <= 1, "Composter probability must be between 0 and 1").entrySet())
            if (entry.getValue() != null) ComposterBlock.CHANCES.put(entry.getKey(), (float) entry.getValue());
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(AXE_BLOCKS, BLOCK_REGISTRY, ConfigUtil::parseBlock, e -> true, "").entrySet())
            if (entry.getValue() != null) AxeItem.BLOCK_STRIPPING_MAP.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(SHOVEL_BLOCKS, BLOCK_REGISTRY, ConfigUtil::parseBlock, e -> true, "").entrySet())
            if (entry.getValue() != null) ShovelItem.SHOVEL_LOOKUP.put(entry.getKey(), entry.getValue().getDefaultState());
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(HOE_BLOCKS, BLOCK_REGISTRY, ConfigUtil::parseBlock, e -> true, "").entrySet())
            if (entry.getValue() != null) HoeItem.HOE_LOOKUP.put(entry.getKey(), entry.getValue().getDefaultState());
        dump(DUMP_COMPOSTER_AFTER, DUMP_STRIPPING_AFTER, DUMP_PATHING_AFTER, DUMP_TILLING_AFTER);
        for (Map.Entry<EntityType<?>, Triple<Attribute, Float, Integer>> e : ConfigUtil.tripleMap(ENTITY_MODIFIERS, new ArrayList<>(ForgeRegistries.ENTITIES.getValues()), ConfigUtil::parseAttribute, e -> true, ConfigUtil::parseFloat, e -> e >= 0, ConfigUtil::parseInt, e -> e > -1 && e < 3).entrySet()) {
            Map<Attribute, List<AttributeModifier>> p = MODIFIERS.getOrDefault(e.getKey(), new HashMap<>());
            List<AttributeModifier> l = p.getOrDefault(e.getValue().a, new ArrayList<>());
            l.add(new AttributeModifier(e.getValue().a.getAttributeName(), e.getValue().b, AttributeModifier.Operation.byId(e.getValue().c)));
            p.put(e.getValue().a, l);
            MODIFIERS.put(e.getKey(), p);
        }
        HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> v = villagerTrades();
        if (!v.isEmpty()) {
            try {
                Field f = VillagerTradingManager.class.getDeclaredField("VANILLA_TRADES");
                f.setAccessible(true);
                Field m = Field.class.getDeclaredField("modifiers");
                m.setAccessible(true);
                m.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, v);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            VillagerTrades.VILLAGER_DEFAULT_TRADES.putAll(v);
        }
        Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> t = traderTrades();
        if (!t.isEmpty()) {
            try {
                Field f = VillagerTradingManager.class.getDeclaredField("WANDERER_TRADES");
                f.setAccessible(true);
                Field m = Field.class.getDeclaredField("modifiers");
                m.setAccessible(true);
                m.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, t);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (t.get(1).length > 0) VillagerTrades.field_221240_b.put(1, t.get(1));
            if (t.get(2).length > 0) VillagerTrades.field_221240_b.put(2, t.get(2));
        }
    }

    private static void dump(ForgeConfigSpec.BooleanValue composter, ForgeConfigSpec.BooleanValue stripping, ForgeConfigSpec.BooleanValue pathing, ForgeConfigSpec.BooleanValue tilling) {
        if (composter.get()) {
            Logger.forceInfo("Composter inputs:");
            for (Map.Entry<IItemProvider, Float> e : ComposterBlock.CHANCES.entrySet())
                Logger.forceInfo(e.getKey().asItem().getRegistryName().toString() + " -> " + e.getValue());
        }
        if (stripping.get()) {
            Logger.forceInfo("Stripping transitions:");
            for (Map.Entry<Block, Block> e : AxeItem.BLOCK_STRIPPING_MAP.entrySet())
                Logger.forceInfo(e.getKey().getRegistryName().toString() + " -> " + e.getValue().getRegistryName().toString());
        }
        if (pathing.get()) {
            Logger.forceInfo("Pathing transitions:");
            for (Map.Entry<Block, BlockState> e : ShovelItem.SHOVEL_LOOKUP.entrySet())
                Logger.forceInfo(e.getKey().getRegistryName().toString() + " -> " + e.getValue().getBlock().getRegistryName().toString());
        }
        if (tilling.get()) {
            Logger.forceInfo("Tilling transitions:");
            for (Map.Entry<Block, BlockState> e : HoeItem.HOE_LOOKUP.entrySet())
                Logger.forceInfo(e.getKey().getRegistryName().toString() + " -> " + e.getValue().getBlock().getRegistryName().toString());
        }
    }

    private static HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> villagerTrades() {
        HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> res = new HashMap<>();
        for (VillagerProfession p : ForgeRegistries.PROFESSIONS.getValues()) {
            res.put(p, new Int2ObjectOpenHashMap<>());
            res.get(p).put(1, villagerTrades(p, VILLAGER_1_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            res.get(p).put(2, villagerTrades(p, VILLAGER_2_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            res.get(p).put(3, villagerTrades(p, VILLAGER_3_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            res.get(p).put(4, villagerTrades(p, VILLAGER_4_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            res.get(p).put(5, villagerTrades(p, VILLAGER_5_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            for (int i = 1; i <= 5; i++)
                if (res.get(p).get(i) == null || res.get(p).get(i).length == 0)
                    res.get(p).put(i, VillagerTrades.VILLAGER_DEFAULT_TRADES.get(p).get(i));
        }
        return res;
    }

    private static VillagerTrades.ITrade[] villagerTrades(VillagerProfession p, List<List<String>> l) {
        List<VillagerTrades.ITrade> r = new ArrayList<>();
        l.stream().filter(e -> ForgeRegistries.PROFESSIONS.getValue(new ResourceLocation(e.get(0))) == p).forEach(e -> {
            try {
                int uses = Integer.parseInt(e.get(1));
                int xp = Integer.parseInt(e.get(2));
                float price = Float.parseFloat(e.get(3));
                if (uses < 1 || xp < 1 || price < 0 || price >= 1) return;
                switch (e.get(4)) {
                    case "normal":
                        TradeUtil.addNormalTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "dyed":
                        TradeUtil.addDyedTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "map":
                        TradeUtil.addMapTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "biome":
                        TradeUtil.addBiomeTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "enchantedbook":
                        TradeUtil.addEnchantedBookTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "enchanteditem":
                        TradeUtil.addEnchantedItemTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "potion":
                        TradeUtil.addPotionTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "stew":
                        TradeUtil.addStewTrade(r, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                }
            } catch (RuntimeException x) {
                x.printStackTrace();
            }
        });
        return r.toArray(new VillagerTrades.ITrade[0]);
    }

    private static Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> traderTrades() {
        Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> m = new Int2ObjectOpenHashMap<>();
        m.put(1, traderTrades(TRADER_NORMAL_TRADES.get()));
        m.put(2, traderTrades(TRADER_LAST_TRADES.get()));
        return m;
    }

    private static VillagerTrades.ITrade[] traderTrades(List<String> l) {
        List<VillagerTrades.ITrade> r = new ArrayList<>();
        for (List<String> s : l.stream().filter(e -> e.split(";").length > 2).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList()))
            try {
                int uses = Integer.parseInt(s.get(0));
                float price = Float.parseFloat(s.get(1));
                switch (s.get(2)) {
                    case "normal":
                        TradeUtil.addNormalTrade(r, uses, 1, price, new ArrayList<>(s.subList(3, s.size())));
                        break;
                    case "dyed":
                        TradeUtil.addDyedTrade(r, uses, 1, price, new ArrayList<>(s.subList(3, s.size())));
                        break;
                    case "map":
                        TradeUtil.addMapTrade(r, uses, 1, price, new ArrayList<>(s.subList(3, s.size())));
                        break;
                    case "enchantedbook":
                        TradeUtil.addEnchantedBookTrade(r, uses, 1, price, new ArrayList<>(s.subList(3, s.size())));
                        break;
                    case "enchanteditem":
                        TradeUtil.addEnchantedItemTrade(r, uses, 1, price, new ArrayList<>(s.subList(3, s.size())));
                        break;
                    case "potion":
                        TradeUtil.addPotionTrade(r, uses, 1, price, new ArrayList<>(s.subList(3, s.size())));
                        break;
                    case "stew":
                        TradeUtil.addStewTrade(r, uses, 1, price, new ArrayList<>(s.subList(3, s.size())));
                        break;
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        return r.toArray(new VillagerTrades.ITrade[0]);
    }
}
