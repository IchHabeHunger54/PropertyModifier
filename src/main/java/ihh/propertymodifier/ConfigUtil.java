package ihh.propertymodifier;

import com.mojang.datafixers.util.Function4;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ConfigUtil {
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
}
