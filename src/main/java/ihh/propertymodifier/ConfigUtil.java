package ihh.propertymodifier;

import com.mojang.datafixers.util.Function4;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"ConstantConditions", "DuplicatedCode", "unused"})
public final class ConfigUtil {
    public static <T extends IForgeRegistryEntry<T>, U> LinkedHashMap<T, U> getMap(ForgeConfigSpec.ConfigValue<List<String>> list, Collection<? extends T> registry, Function4<String, String, String, Predicate<U>, U> parserU, Predicate<U> predicateU) {
        LinkedHashMap<T, U> map = new LinkedHashMap<>();
        for (String s : list.get()) {
            String[] array = split(s, 2, list);
            if (array == null) continue;
            U u = parserU.apply(array[1], s, getPath(list.getPath()), predicateU);
            if (u == null) continue;
            for (T t : registry.stream().filter(e -> e.getRegistryName().toString().matches(array[0])).collect(Collectors.toList())) {
                map.put(t, u);
                logProperty(getLast(list.getPath()), t, u);
            }
        }
        return map;
    }

    public static <T extends IForgeRegistryEntry<T>, U, V> LinkedHashMap<T, Pair<U, V>> pairMap(ForgeConfigSpec.ConfigValue<List<String>> list, Collection<? extends T> registry, Function4<String, String, String, Predicate<U>, U> parserU, Predicate<U> predicateU, Function4<String, String, String, Predicate<V>, V> parserV, Predicate<V> predicateV) {
        LinkedHashMap<T, Pair<U, V>> map = new LinkedHashMap<>();
        for (String s : list.get()) {
            String[] array = split(s, 3, list);
            if (array == null) continue;
            U u = parserU.apply(array[1], s, getPath(list.getPath()), predicateU);
            if (u == null) continue;
            V v = parserV.apply(array[2], s, getPath(list.getPath()), predicateV);
            if (v == null) continue;
            for (T t : registry.stream().filter(e -> e.getRegistryName().toString().matches(array[0])).collect(Collectors.toList())) {
                map.put(t, new Pair<>(u, v));
                logProperty(getLast(list.getPath()), t, u);
            }
        }
        return map;
    }

    public static <T extends IForgeRegistryEntry<T>, U, V, W> LinkedHashMap<T, Triple<U, V, W>> tripleMap(ForgeConfigSpec.ConfigValue<List<String>> list, Collection<? extends T> registry, Function4<String, String, String, Predicate<U>, U> parserU, Predicate<U> predicateU, Function4<String, String, String, Predicate<V>, V> parserV, Predicate<V> predicateV, Function4<String, String, String, Predicate<W>, W> parserW, Predicate<W> predicateW) {
        LinkedHashMap<T, Triple<U, V, W>> map = new LinkedHashMap<>();
        for (String s : list.get()) {
            String[] array = split(s, 4, list);
            if (array == null) continue;
            U u = parserU.apply(array[1], s, getPath(list.getPath()), predicateU);
            if (u == null) continue;
            V v = parserV.apply(array[2], s, getPath(list.getPath()), predicateV);
            if (v == null) continue;
            W w = parserW.apply(array[3], s, getPath(list.getPath()), predicateW);
            if (w == null) continue;
            for (T t : registry.stream().filter(e -> e.getRegistryName().toString().matches(array[0])).collect(Collectors.toList())) {
                map.put(t, new Triple<>(u, v, w));
                logProperty(getLast(list.getPath()), t, u);
            }
        }
        return map;
    }

    public static <T extends IForgeRegistryEntry<T>> T fromCollection(String string, Collection<? extends T> collection) {
        for (T t : collection) if (t.getRegistryName().toString().equals(string)) return t;
        Logger.error("Could not find " + string);
        return null;
    }

    public static <T> T getLast(List<? extends T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> String getPath(List<T> list) {
        StringBuilder stringbuilder = new StringBuilder();
        for (T t : list) stringbuilder.append(t).append('.');
        return stringbuilder.substring(0, stringbuilder.length() - 1);
    }

    public static void logInvalid(String value, String entry, String config) {
        Logger.error(value + " is an invalid value (at " + entry + " in " + config + ")");
    }

    public static void logProperty(String value, IForgeRegistryEntry<?> entry, Object newValue) {
        Logger.info("Set " + value + " for " + entry.getRegistryName().toString() + " to " + newValue);
    }

    public static String[] split(String string, int length, ForgeConfigSpec.ConfigValue<List<String>> config) {
        String[] array = string.split(";");
        if (array.length != length) {
            Logger.error("Entry " + string + " in " + getPath(config.getPath()) + " is invalid, needs " + length + " entries separated by semicolons");
            return null;
        }
        return array;
    }

    public static HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> villagerTrades(List<String> trade1, List<String> trade2, List<String> trade3, List<String> trade4, List<String> trade5) {
        HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> result = new HashMap<>();
        List<List<String>> filtered1 = filterTrades(trade1, 4);
        List<List<String>> filtered2 = filterTrades(trade2, 4);
        List<List<String>> filtered3 = filterTrades(trade3, 4);
        List<List<String>> filtered4 = filterTrades(trade4, 4);
        List<List<String>> filtered5 = filterTrades(trade5, 4);
        for (VillagerProfession prof : ForgeRegistries.PROFESSIONS.getValues()) {
            result.put(prof, new Int2ObjectOpenHashMap<>());
            result.get(prof).put(1, villagerTrades(prof, filtered1));
            result.get(prof).put(2, villagerTrades(prof, filtered2));
            result.get(prof).put(3, villagerTrades(prof, filtered3));
            result.get(prof).put(4, villagerTrades(prof, filtered4));
            result.get(prof).put(5, villagerTrades(prof, filtered5));
            for (int i = 1; i <= 5; i++)
                if (result.get(prof).get(i) == null || result.get(prof).get(i).length == 0)
                    result.getOrDefault(prof, new Int2ObjectOpenHashMap<>()).put(i, VillagerTrades.VILLAGER_DEFAULT_TRADES.getOrDefault(prof, new Int2ObjectOpenHashMap<>()).get(i));
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

    public static Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> traderTrades(List<String> trade1, List<String> trade2) {
        Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> result = new Int2ObjectOpenHashMap<>();
        result.put(1, traderTrades(filterTrades(trade1, 2)));
        result.put(2, traderTrades(filterTrades(trade2, 2)));
        return result;
    }

    private static VillagerTrades.ITrade[] traderTrades(List<List<String>> list) {
        List<VillagerTrades.ITrade> result = new ArrayList<>();
        list.forEach(e -> {
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

    private static List<List<String>> filterTrades(List<String> list, int size) {
        return list.stream().map(e -> Arrays.asList(e.split(";"))).filter(e -> e.size() > size).collect(Collectors.toList());
    }

    public static HashMap<EntityType<?>, Map<Attribute, List<AttributeModifier>>> parseAttributeList(ForgeConfigSpec.ConfigValue<List<String>> config) {
        HashMap<EntityType<?>, Map<Attribute, List<AttributeModifier>>> result = new HashMap<>();
        for (String s : config.get()) {
            String[] array = split(s, 4, config);
            if (array == null) continue;
            EntityType<?> type = fromCollection(array[0], ForgeRegistries.ENTITIES.getValues());
            Attribute attribute = fromCollection(array[1], ForgeRegistries.ATTRIBUTES.getValues());
            Float value = ParsingUtil.parseFloat(array[2], s, "entity.modifiers", e -> true);
            Integer op = ParsingUtil.parseInt(array[3], s, "entity.modifiers", e -> e > -1 && e < 3);
            if (type == null || attribute == null || value == null || op == null) continue;
            AttributeModifier.Operation operation = AttributeModifier.Operation.byId(op);
            Map<Attribute, List<AttributeModifier>> modifierMap = result.getOrDefault(type, new HashMap<>());
            List<AttributeModifier> modifiers = modifierMap.getOrDefault(attribute, new ArrayList<>());
            modifiers.add(new AttributeModifier(attribute.getAttributeName(), value, operation));
            modifierMap.put(attribute, modifiers);
            result.put(type, modifierMap);
        }
        return result;
    }
}
