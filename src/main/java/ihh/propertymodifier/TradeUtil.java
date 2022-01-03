package ihh.propertymodifier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.v4.runtime.misc.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class TradeUtil {
    public static void addTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(0)));
        int buyCount = Integer.parseInt(config.get(1));
        if (config.size() == 4) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            int sellCount = Integer.parseInt(config.get(3));
            list.add(new Trade(uses, xp, price, buy, buyCount, sell, sellCount));
            log(uses, xp, price, sellCount + " " + sell + " for " + buyCount + " " + buy);
        } else if (config.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            int buyCount2 = Integer.parseInt(config.get(3));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(4)));
            int sellCount = Integer.parseInt(config.get(5));
            list.add(new Trade(uses, xp, price, buy, buyCount, buy2, buyCount2, sell, sellCount));
            log(uses, xp, price, sellCount + " " + sell + " for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        } else {
            invalid(config);
        }
    }

    public static void addDyedTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(0)));
        int buyCount = Integer.parseInt(config.get(1));
        if (config.size() == 3) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            list.add(new Trade.DyedItemTrade(uses, xp, price, buy, buyCount, sell));
            log(uses, xp, price, "1 " + sell + " (randomly dyed) for " + buyCount + " " + buy);
        } else if (config.size() == 5) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            int buyCount2 = Integer.parseInt(config.get(3));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(4)));
            list.add(new Trade.DyedItemTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, sell));
            log(uses, xp, price, "1 " + sell + " (randomly dyed) for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        } else {
            invalid(config);
        }
    }

    public static void addMapTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(0)));
        int buyCount = Integer.parseInt(config.get(1));
        if (config.size() == 4) {
            StructureFeature<?> structure = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(config.get(2)));
            MapDecoration.Type mapDecoration = MapDecoration.Type.valueOf(config.get(3).toUpperCase());
            list.add(new Trade.MapTrade(uses, xp, price, buy, buyCount, structure, mapDecoration));
            log(uses, xp, price, "1 minecraft:map that leads to structure " + structure + " for " + buyCount + " " + buy);
        } else if (config.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            int buyCount2 = Integer.parseInt(config.get(3));
            StructureFeature<?> structure = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(config.get(4)));
            MapDecoration.Type mapDecoration = MapDecoration.Type.valueOf(config.get(5).toUpperCase());
            list.add(new Trade.MapTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, structure, mapDecoration));
            log(uses, xp, price, "1 minecraft:map that leads to structure " + structure + "and shows marker " + mapDecoration + " for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        } else {
            invalid(config);
        }
    }

    public static void addBiomeTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        HashMap<VillagerType, Pair<Pair<Item, Integer>, Pair<Item, Integer>>> map = new HashMap<>();
        if (config.size() != 35) {
            invalid(config);
            return;
        }
        for (int i = 0; i < config.size(); i += 5)
            if (getVillagerType(config.get(i)) != null) {
                try {
                    map.put(getVillagerType(config.get(i)), new Pair<>(new Pair<>(ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(i + 1))), Integer.parseInt(config.get(i + 2))), new Pair<>(ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(i + 3))), Integer.parseInt(config.get(i + 4)))));
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return;
                }
            } else return;
        list.add(new Trade.BiomeTrade(uses, xp, price, map));
    }

    public static void addEnchantedBookTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(0)));
        if (config.size() == 2 && config.get(1).equals("any")) {
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, null, 0));
            log(uses, xp, price, "1 minecraft:enchanted_book (randomly enchanted) for a generated amount of " + buy);
        } else if (config.size() == 3) {
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(config.get(1)));
            if (enchantment == null) return;
            int level = Integer.parseInt(config.get(2));
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, enchantment, level));
            log(uses, xp, price, "1 minecraft:enchanted_book with enchantment " + (enchantment.getRegistryName().toString()) + " for a generated amount of " + buy);
        } else if (config.size() == 4 && config.get(3).equals("any")) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(1)));
            int buyCount2 = Integer.parseInt(config.get(2));
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, buy2, buyCount2, null, 0));
            log(uses, xp, price, "1 minecraft:enchanted_book (randomly enchanted) for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        } else if (config.size() == 5) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(1)));
            int buyCount2 = Integer.parseInt(config.get(2));
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(config.get(3)));
            if (enchantment == null) return;
            int level = Integer.parseInt(config.get(4));
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, enchantment, level));
            log(uses, xp, price, "1 minecraft:enchanted_book with enchantment " + (enchantment.getRegistryName().toString()) + " for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        } else {
            invalid(config);
        }
    }

    public static void addEnchantedItemTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(0)));
        if (config.size() == 3 && config.get(2).equals("any")) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(1)));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, sell, null, 0));
            log(uses, xp, price, "1 " + sell + " (randomly enchanted) for a generated amount of " + buy);
        } else if (config.size() == 4) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(1)));
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(config.get(2)));
            int level = Integer.parseInt(config.get(3));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, sell, enchantment, level));
            log(uses, xp, price, "1 " + sell + " with enchantment" + enchantment + " for a generated amount of " + buy);
        }
        if (config.size() == 5 && config.get(4).equals("any")) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(1)));
            int buyCount2 = Integer.parseInt(config.get(2));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(3)));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, buy2, buyCount2, sell, null, 0));
            log(uses, xp, price, "1 " + sell + " (randomly enchanted) for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        } else if (config.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(1)));
            int buyCount2 = Integer.parseInt(config.get(2));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(3)));
            Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(config.get(4)));
            int level = Integer.parseInt(config.get(5));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, buy2, buyCount2, sell, enchantment, level));
            log(uses, xp, price, "1 " + sell + " with enchantment" + enchantment + " for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        } else {
            invalid(config);
        }
    }

    public static void addPotionTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(0)));
        int buyCount = Integer.parseInt(config.get(1));
        if (config.size() == 4) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            List<Potion> potion = ForgeRegistries.POTIONS.getValues().stream().filter(e -> e.getRegistryName().toString().matches(config.get(3))).collect(Collectors.toList());
            list.add(new Trade.PotionItemTrade(uses, xp, price, buy, buyCount, sell, potion));
            log(uses, xp, price, "1 " + sell + " with a potion applied to it for " + buyCount + " " + buy);
        } else if (config.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            int buyCount2 = Integer.parseInt(config.get(3));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(4)));
            List<Potion> potion = ForgeRegistries.POTIONS.getValues().stream().filter(e -> e.getRegistryName().toString().matches(config.get(5))).collect(Collectors.toList());
            list.add(new Trade.PotionItemTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, sell, potion));
            log(uses, xp, price, "1 " + sell + " with a potion applied to it for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        } else {
            invalid(config);
        }
    }

    public static void addStewTrade(List<VillagerTrades.ItemListing> list, int uses, int xp, float price, List<String> config) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(0)));
        int buyCount = Integer.parseInt(config.get(1));
        if (config.size() == 4) {
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(config.get(2)));
            int duration = Integer.parseInt(config.get(3));
            list.add(new Trade.StewTrade(uses, xp, price, buy, buyCount, effect, duration));
            log(uses, xp, price, "1 minecraft:suspicious_stew with " + effect + " (" + duration / 20f + " s) applied to it for " + buyCount + " " + buy);
        } else if (config.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(config.get(2)));
            int buyCount2 = Integer.parseInt(config.get(3));
            MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(config.get(4)));
            int duration = Integer.parseInt(config.get(5));
            list.add(new Trade.StewTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, effect, duration));
            log(uses, xp, price, "1 minecraft:suspicious_stew with " + effect + " (" + duration / 20f + " s) applied to it for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        } else {
            invalid(config);
        }
    }

    private static VillagerType getVillagerType(String type) {
        return switch (type) {
            case "desert" -> VillagerType.DESERT;
            case "jungle" -> VillagerType.JUNGLE;
            case "savanna" -> VillagerType.SAVANNA;
            case "snow" -> VillagerType.SNOW;
            case "swamp" -> VillagerType.SWAMP;
            case "taiga" -> VillagerType.TAIGA;
            case "plains" -> VillagerType.PLAINS;
            default -> null;
        };
    }

    private static void log(int uses, int xp, float price, String sell) {
        Logger.debug("Added new trade with " + uses + " uses, " + xp + " villager xp, a price multiplier of " + price + " and selling " + sell);
    }

    private static void invalid(List<String> config) {
        StringBuilder builder = new StringBuilder();
        for (String s : config) {
            builder.append(s).append(";");
        }
        Logger.error("Invalid trade " + builder.substring(0, builder.length() - 1));
    }
}
