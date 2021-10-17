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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"ConstantConditions", "FieldMayBeFinal"})
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
        COMPOSTER_INPUTS = builder.comment("Define additional composter inputs. Format is \"itemid;chance\", with item id being in the modid:itemid format and chance being a percentage between 0.0 and 1.0.").define("inputs", new ArrayList<>());
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
        ENTITY_MODIFIERS = builder.comment("Apply entity attribute modifiers on spawning. To get the default value of an attribute, make a superflat world without mob spawning, spawn the desired mob, and run \"/attribute @e[type=<entityid>,limit=1] <attributeid> get\". Format is \"entity;attribute;amount;operation\":", "entity: the entity id (e.g. minecraft:rabbit)", "attribute: the attribute id (e.g. minecraft:generic.max_health)", "amount: the amount of the modifier (e.g. 5)", "operation: 0 for addition, 1 for multiply base, 2 for multiply total. See https://minecraft.fandom.com/wiki/Attribute to see what they each do").define("modifiers", new ArrayList<>());
        builder.pop();
        builder.push("villager_trading");
        builder.comment("Adds new villager trades. villager_x_trades defines the villager level (1-5). Due to technical reasons, if you add trades for a specific profession for a specific level, you need to re-add all trades for that profession level. E. g. if you wanted to add an enchanted book trade to a level 5 librarian, you need to re-add all other trades for a level 5 librarian as well. See https://minecraft.fandom.com/wiki/Trading for the vanilla defaults", "Format is profession;uses;xp;pricemultiplier;tradetype;tradetype-specific-args", "profession: villager profession (e. g. minecraft:librarian)", "uses: how often the trade can be traded before it is locked", "xp: the amount of villager xp given to the villager", "price multiplier: every time you trade with a villager, the selling price is multiplied with 1 - this value (e. g. 0.1 means that every time you use the trade, the price is lowered by 10%)", "tradetype and corresponding tradetype-specific args can take the following values (values in [] are optional):", "  normal: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;sellItemCount - a normal trade that takes 1 or 2 stacks in and gives 1 stack out. The items are item ids (e. g. minecraft:emerald), the item counts are numbers between 1 and 64", "  dyed: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem\" - the sell item (e. g. leather armor) will be randomly dyed", "  map: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]structure;mapdecoration\" - structure is a structure id (e. g. minecraft:stronghold), mapdecoration can be one of the following: \"player\", \"frame\", \"red_marker\", \"blue_marker\", \"target_x\", \"target_point\", \"player_off_map\", \"player_off_limits\", \"mansion\", \"monument\", \"red_x\", \"banner_black\", \"banner_blue\", \"banner_brown\", \"banner_cyan\", \"banner_gray\", \"banner_green\", \"banner_light_blue\", \"banner_light_gray\", \"banner_lime\", \"banner_magenta\", \"banner_orange\", \"banner_pink\", \"banner_purple\", \"banner_red\", \"banner_white\", \"banner_yellow\"", "  enchantedbook: buyItem1;[buyItem2;buyItemCount2;]enchantment;level - enchantment to use (e. g. minecraft:sharpness, or \"any\" for random enchantments), level for the level. Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 book costs approx. 5 times more than a lvl 1 book. It's currently impossible to add more than one enchantment", "  enchanteditem: buyItem1;[buyItem2;buyItemCount2;]sellItem;enchantment;level - enchantment to use (e. g. minecraft:sharpness, or \"any\" for random enchantments), level for the level. Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 helmet costs approx. 5 times more than a lvl 1 helmet. It's currently impossible to add more than one enchantment", "  potion: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;potion - the potion (using \"any\" will randomly select one) is applied to the sell item, so unless you have additional potion-like items added by other mods, this should only be minecraft:potion, minecraft:splash_potion, minecraft:lingering_potion or minecraft:tipped_arrow", "  stew: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]effect;duration - effect is an effect id (e. g. minecraft:strength), duration is an integer determining the amount of ticks the effect lasts");
        VILLAGER_1_TRADES = builder.define("villager_1_trades", new ArrayList<>());
        VILLAGER_2_TRADES = builder.define("villager_2_trades", new ArrayList<>());
        VILLAGER_3_TRADES = builder.define("villager_3_trades", new ArrayList<>());
        VILLAGER_4_TRADES = builder.define("villager_4_trades", new ArrayList<>());
        VILLAGER_5_TRADES = builder.define("villager_5_trades", new ArrayList<>());
        builder.pop();
        builder.push("wandering_trader_trading");
        builder.comment("Adds new wandering trader trades. Due to how the wandering trader works, there are two lists: normal and last trades. When the trader spawns, five normal trades and one last trade are each randomly chosen from their corresponding lists. Note that as soon as you add anything in any of the lists, it removes all other trades (including the ones from the other list), and only this mod's added trades remain, so you need to re-add most things if you only want to add one trade. See https://minecraft.fandom.com/wiki/Trading for the vanilla defaults", "Format is uses;pricemultiplier;tradetype;tradetype-specific-args", "uses: how often the trade can be traded before it is locked", "price multiplier: every time you trade with a villager, the selling price is multiplied with 1 - this value (e. g. 0.1 means that every time you use the trade, the price is lowered by 10%)", "tradetype and corresponding tradetype-specific args can take the following values (values in [] are optional):", "  normal: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;sellItemCount - a normal trade that takes 1 or 2 stacks in and gives 1 stack out. The items are item ids (e. g. minecraft:emerald), the item counts are numbers between 1 and 64", "  dyed: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem\" - the sell item (e. g. leather armor) will be randomly dyed", "  map: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]structure;mapdecoration\" - structure is a structure id (e. g. minecraft:stronghold), mapdecoration can be one of the following: \"player\", \"frame\", \"red_marker\", \"blue_marker\", \"target_x\", \"target_point\", \"player_off_map\", \"player_off_limits\", \"mansion\", \"monument\", \"red_x\", \"banner_black\", \"banner_blue\", \"banner_brown\", \"banner_cyan\", \"banner_gray\", \"banner_green\", \"banner_light_blue\", \"banner_light_gray\", \"banner_lime\", \"banner_magenta\", \"banner_orange\", \"banner_pink\", \"banner_purple\", \"banner_red\", \"banner_white\", \"banner_yellow\"", "  enchantedbook: buyItem1;[buyItem2;buyItemCount2;]enchantment;level - enchantment to use (e. g. minecraft:sharpness, or \"any\" for random enchantments), level for the level. Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 book costs approx. 5 times more than a lvl 1 book. It's currently impossible to add more than one enchantment", "  enchanteditem: buyItem1;[buyItem2;buyItemCount2;]sellItem;enchantment;level - enchantment to use (e. g. minecraft:sharpness, or \"any\" for random enchantments), level for the level. Outputs an enchanted book, at which the amount of buyItem1 is scaled - a lvl 5 helmet costs approx. 5 times more than a lvl 1 helmet. It's currently impossible to add more than one enchantment", "  potion: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]sellItem;potion - the potion (using \"any\" will randomly select one) is applied to the sell item, so unless you have additional potion-like items added by other mods, this should only be minecraft:potion, minecraft:splash_potion, minecraft:lingering_potion or minecraft:tipped_arrow", "  stew: buyItem1;buyItemCount1;[buyItem2;buyItemCount2;]effect;duration - effect is an effect id (e. g. minecraft:strength), duration is an integer determining the amount of ticks the effect lasts");
        TRADER_NORMAL_TRADES = builder.define("trader_normal_trades", new ArrayList<>());
        TRADER_LAST_TRADES = builder.define("trader_last_trades", new ArrayList<>());
        builder.pop();
        SPEC = builder.build();
    }

    public static void work() {
        if (COMPOSTER_TRANSITIONS == null)
            COMPOSTER_TRANSITIONS = new Object2FloatOpenHashMap<>(ComposterBlock.CHANCES);
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
        for (Map.Entry<Item, Float> entry : ConfigUtil.getMap(COMPOSTER_INPUTS, ForgeRegistries.ITEMS.getValues(), ParsingUtil::parseFloat, e -> e >= 0 && e <= 1).entrySet())
            if (entry.getValue() != null) ComposterBlock.CHANCES.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(AXE_BLOCKS, BLOCK_REGISTRY, ParsingUtil::parseBlock, e -> true).entrySet())
            if (entry.getValue() != null) AxeItem.BLOCK_STRIPPING_MAP.put(entry.getKey(), entry.getValue());
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(SHOVEL_BLOCKS, BLOCK_REGISTRY, ParsingUtil::parseBlock, e -> true).entrySet())
            if (entry.getValue() != null) ShovelItem.SHOVEL_LOOKUP.put(entry.getKey(), entry.getValue().getDefaultState());
        for (Map.Entry<Block, Block> entry : ConfigUtil.getMap(HOE_BLOCKS, BLOCK_REGISTRY, ParsingUtil::parseBlock, e -> true).entrySet())
            if (entry.getValue() != null) HoeItem.HOE_LOOKUP.put(entry.getKey(), entry.getValue().getDefaultState());
        dump(DUMP_COMPOSTER_AFTER, DUMP_STRIPPING_AFTER, DUMP_PATHING_AFTER, DUMP_TILLING_AFTER);
        MODIFIERS.clear();
        for (Map.Entry<EntityType<?>, Map<Attribute, List<AttributeModifier>>> entry : parseAttributeList().entrySet())
            for (Attribute attribute : entry.getValue().keySet())
                for (AttributeModifier modifier : entry.getValue().get(attribute)) {
                    Map<Attribute, List<AttributeModifier>> map = MODIFIERS.getOrDefault(entry.getKey(), new HashMap<>());
                    List<AttributeModifier> list = map.getOrDefault(attribute, new ArrayList<>());
                    list.add(modifier);
                    map.put(attribute, list);
                    MODIFIERS.put(entry.getKey(), map);
                }
        HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> villagerTrades = villagerTrades();
        if (!villagerTrades.isEmpty()) {
            try {
                Field f = VillagerTradingManager.class.getDeclaredField("VANILLA_TRADES");
                f.setAccessible(true);
                Field m = Field.class.getDeclaredField("modifiers");
                m.setAccessible(true);
                m.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, villagerTrades);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            VillagerTrades.VILLAGER_DEFAULT_TRADES.putAll(villagerTrades);
        }
        Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> traderTrades = traderTrades();
        if (!traderTrades.isEmpty()) {
            try {
                Field f = VillagerTradingManager.class.getDeclaredField("WANDERER_TRADES");
                f.setAccessible(true);
                Field m = Field.class.getDeclaredField("modifiers");
                m.setAccessible(true);
                m.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, traderTrades);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (traderTrades.get(1).length > 0) VillagerTrades.field_221240_b.put(1, traderTrades.get(1));
            if (traderTrades.get(2).length > 0) VillagerTrades.field_221240_b.put(2, traderTrades.get(2));
        }
    }

    private static void dump(ForgeConfigSpec.BooleanValue composter, ForgeConfigSpec.BooleanValue stripping, ForgeConfigSpec.BooleanValue pathing, ForgeConfigSpec.BooleanValue tilling) {
        if (composter.get()) {
            Logger.forceInfo("Composter inputs:");
            for (Map.Entry<IItemProvider, Float> entry : ComposterBlock.CHANCES.entrySet())
                Logger.forceInfo(entry.getKey().asItem().getRegistryName().toString() + " -> " + entry.getValue());
        }
        if (stripping.get()) {
            Logger.forceInfo("Stripping transitions:");
            for (Map.Entry<Block, Block> entry : AxeItem.BLOCK_STRIPPING_MAP.entrySet())
                Logger.forceInfo(entry.getKey().getRegistryName().toString() + " -> " + entry.getValue().getRegistryName().toString());
        }
        if (pathing.get()) {
            Logger.forceInfo("Pathing transitions:");
            for (Map.Entry<Block, BlockState> entry : ShovelItem.SHOVEL_LOOKUP.entrySet())
                Logger.forceInfo(entry.getKey().getRegistryName().toString() + " -> " + entry.getValue().getBlock().getRegistryName().toString());
        }
        if (tilling.get()) {
            Logger.forceInfo("Tilling transitions:");
            for (Map.Entry<Block, BlockState> entry : HoeItem.HOE_LOOKUP.entrySet())
                Logger.forceInfo(entry.getKey().getRegistryName().toString() + " -> " + entry.getValue().getBlock().getRegistryName().toString());
        }
    }

    private static HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> villagerTrades() {
        HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> result = new HashMap<>();
        for (VillagerProfession prof : ForgeRegistries.PROFESSIONS.getValues()) {
            result.put(prof, new Int2ObjectOpenHashMap<>());
            result.get(prof).put(1, villagerTrades(prof, VILLAGER_1_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            result.get(prof).put(2, villagerTrades(prof, VILLAGER_2_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            result.get(prof).put(3, villagerTrades(prof, VILLAGER_3_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            result.get(prof).put(4, villagerTrades(prof, VILLAGER_4_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            result.get(prof).put(5, villagerTrades(prof, VILLAGER_5_TRADES.get().stream().filter(e -> e.split(";").length > 4).map(e -> Arrays.asList(e.split(";"))).collect(Collectors.toList())));
            for (int i = 1; i <= 5; i++)
                if (result.get(prof).get(i) == null || result.get(prof).get(i).length == 0)
                    result.get(prof).put(i, VillagerTrades.VILLAGER_DEFAULT_TRADES.get(prof).get(i));
        }
        return result;
    }

    private static VillagerTrades.ITrade[] villagerTrades(VillagerProfession prof, List<List<String>> list) {
        List<VillagerTrades.ITrade> result = new ArrayList<>();
        list.stream().filter(e -> ForgeRegistries.PROFESSIONS.getValue(new ResourceLocation(e.get(0))) == prof).forEach(e -> {
            try {
                int uses = Integer.parseInt(e.get(1));
                int xp = Integer.parseInt(e.get(2));
                float price = Float.parseFloat(e.get(3));
                if (uses < 1 || xp < 1 || price < 0 || price >= 1) return;
                switch (e.get(4)) {
                    case "normal":
                        TradeUtil.addTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "dyed":
                        TradeUtil.addDyedTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "map":
                        TradeUtil.addMapTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "biome":
                        TradeUtil.addBiomeTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "enchantedbook":
                        TradeUtil.addEnchantedBookTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "enchanteditem":
                        TradeUtil.addEnchantedItemTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "potion":
                        TradeUtil.addPotionTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                    case "stew":
                        TradeUtil.addStewTrade(result, uses, xp, price, new ArrayList<>(e.subList(5, e.size())));
                        break;
                }
            } catch (RuntimeException x) {
                x.printStackTrace();
            }
        });
        return result.toArray(new VillagerTrades.ITrade[0]);
    }

    private static Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> traderTrades() {
        Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> result = new Int2ObjectOpenHashMap<>();
        result.put(1, traderTrades(TRADER_NORMAL_TRADES.get()));
        result.put(2, traderTrades(TRADER_LAST_TRADES.get()));
        return result;
    }

    private static VillagerTrades.ITrade[] traderTrades(List<String> list) {
        List<VillagerTrades.ITrade> result = new ArrayList<>();
        list.stream().map(e -> Arrays.asList(e.split(";"))).filter(e -> e.size() > 2).forEach(e -> {
            try {
                int uses = Integer.parseInt(e.get(0));
                float price = Float.parseFloat(e.get(1));
                switch (e.get(2)) {
                    case "normal":
                        TradeUtil.addTrade(result, uses, 1, price, new ArrayList<>(e.subList(3, e.size())));
                        break;
                    case "dyed":
                        TradeUtil.addDyedTrade(result, uses, 1, price, new ArrayList<>(e.subList(3, e.size())));
                        break;
                    case "map":
                        TradeUtil.addMapTrade(result, uses, 1, price, new ArrayList<>(e.subList(3, e.size())));
                        break;
                    case "enchantedbook":
                        TradeUtil.addEnchantedBookTrade(result, uses, 1, price, new ArrayList<>(e.subList(3, e.size())));
                        break;
                    case "enchanteditem":
                        TradeUtil.addEnchantedItemTrade(result, uses, 1, price, new ArrayList<>(e.subList(3, e.size())));
                        break;
                    case "potion":
                        TradeUtil.addPotionTrade(result, uses, 1, price, new ArrayList<>(e.subList(3, e.size())));
                        break;
                    case "stew":
                        TradeUtil.addStewTrade(result, uses, 1, price, new ArrayList<>(e.subList(3, e.size())));
                        break;
                }
            } catch (RuntimeException x) {
                x.printStackTrace();
            }
        });
        return result.toArray(new VillagerTrades.ITrade[0]);
    }

    private static HashMap<EntityType<?>, Map<Attribute, List<AttributeModifier>>> parseAttributeList() {
        HashMap<EntityType<?>, Map<Attribute, List<AttributeModifier>>> result = new HashMap<>();
        for (String s : ENTITY_MODIFIERS.get()) {
            String[] array = ConfigUtil.split(s, 4, ENTITY_MODIFIERS);
            if (array == null) continue;
            EntityType<?> type = ConfigUtil.fromCollection(array[0], ForgeRegistries.ENTITIES.getValues());
            Attribute attribute = ConfigUtil.fromCollection(array[1], ForgeRegistries.ATTRIBUTES.getValues());
            Float value = ParsingUtil.parseFloat(array[2], s, "entity.modifiers", e -> true);
            AttributeModifier.Operation operation = AttributeModifier.Operation.byId(ParsingUtil.parseInt(array[3], s, "entity.modifiers", e -> e > -1 && e < 3));
            if (type == null || attribute == null || value == null || operation == null) continue;
            Map<Attribute, List<AttributeModifier>> modifierMap = result.getOrDefault(type, new HashMap<>());
            List<AttributeModifier> modifiers = modifierMap.getOrDefault(attribute, new ArrayList<>());
            modifiers.add(new AttributeModifier(attribute.getAttributeName(), value, operation));
            modifierMap.put(attribute, modifiers);
            result.put(type, modifierMap);
        }
        return result;
    }
}
