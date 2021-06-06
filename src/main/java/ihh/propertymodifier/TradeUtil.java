package ihh.propertymodifier;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.v4.runtime.misc.Pair;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public final class TradeUtil {
    public static void addNormalTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(0)));
        int buyC = Integer.parseInt(l.get(1));
        if (l.size() == 4) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            int sellC = Integer.parseInt(l.get(3));
            list.add(new Trade(uses, xp, price, buy, buyC, sell, sellC));
            log(uses, xp, price, sellC + " " + sell + " for " + buyC + " " + buy);
        } else if (l.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            int buy2C = Integer.parseInt(l.get(3));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(4)));
            int sellC = Integer.parseInt(l.get(5));
            list.add(new Trade(uses, xp, price, buy, buyC, buy2, buy2C, sell, sellC));
            log(uses, xp, price, sellC + " " + sell + " for " + buyC + " " + buy + " and " + buy2C + " " + buy2);
        }
    }

    public static void addDyedTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(0)));
        int buyCount = Integer.parseInt(l.get(1));
        if (l.size() == 3) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            list.add(new Trade.DyedItemTrade(uses, xp, price, buy, buyCount, sell));
            log(uses, xp, price, "1 " + sell + " (randomly dyed) for " + buyCount + " " + buy);
        } else if (l.size() == 5) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            int buyCount2 = Integer.parseInt(l.get(3));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(4)));
            list.add(new Trade.DyedItemTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, sell));
            log(uses, xp, price, "1 " + sell + " (randomly dyed) for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        }
    }

    public static void addMapTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(0)));
        int buyCount = Integer.parseInt(l.get(1));
        if (l.size() == 4) {
            Structure<?> s = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(l.get(2)));
            MapDecoration.Type m = MapDecoration.Type.valueOf(l.get(3).toUpperCase());
            list.add(new Trade.MapTrade(uses, xp, price, buy, buyCount, s, m));
            log(uses, xp, price, "1 minecraft:map that leads to structure " + s + " for " + buyCount + " " + buy);
        } else if (l.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            int buyCount2 = Integer.parseInt(l.get(3));
            Structure<?> structure = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(l.get(4)));
            MapDecoration.Type mapDecoration = MapDecoration.Type.valueOf(l.get(5).toUpperCase());
            list.add(new Trade.MapTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, structure, mapDecoration));
            log(uses, xp, price, "1 minecraft:map that leads to structure " + structure + " for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        }
    }

    public static void addBiomeTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        HashMap<VillagerType, Pair<Pair<Item, Integer>, Pair<Item, Integer>>> m = new HashMap<>();
        if (l.size() != 35) return;
        for (int i = 0; i < l.size(); i += 5)
            if (getVillagerType(l.get(i)) != null) try {
                m.put(getVillagerType(l.get(i)), new Pair<>(new Pair<>(ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(i + 1))), Integer.parseInt(l.get(i + 2))), new Pair<>(ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(i + 3))), Integer.parseInt(l.get(i + 4)))));
            } catch (RuntimeException e) {
                e.printStackTrace();
                return;
            }
            else return;
        list.add(new Trade.BiomeTrade(uses, xp, price, m));
    }

    public static void addEnchantedBookTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(0)));
        if (l.size() == 2 && l.get(1).equals("any")) {
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, null, 0));
            log(uses, xp, price, "1 minecraft:enchanted_book (randomly enchanted) for a generated amount of " + buy);
        } else if (l.size() == 3) {
            Enchantment e = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(l.get(1)));
            if (e == null) return;
            int i = Integer.parseInt(l.get(2));
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, e, i));
            log(uses, xp, price, "1 minecraft:enchanted_book with enchantment " + (e.getRegistryName().toString()) + " for a generated amount of " + buy);
        } else if (l.size() == 4 && l.get(3).equals("any")) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(1)));
            int buyCount2 = Integer.parseInt(l.get(2));
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, buy2, buyCount2, null, 0));
            log(uses, xp, price, "1 minecraft:enchanted_book (randomly enchanted) for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        } else if (l.size() == 5) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(1)));
            int buyCount2 = Integer.parseInt(l.get(2));
            Enchantment e = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(l.get(3)));
            if (e == null) return;
            int i = Integer.parseInt(l.get(4));
            list.add(new Trade.EnchantedBookTrade(uses, xp, price, buy, e, i));
            log(uses, xp, price, "1 minecraft:enchanted_book with enchantment " + (e.getRegistryName().toString()) + " for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        }
    }

    public static void addEnchantedItemTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(0)));
        if (l.size() == 3 && l.get(2).equals("any")) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(1)));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, sell, null, 0));
            log(uses, xp, price, "1 " + sell + " (randomly enchanted) for a generated amount of " + buy);
        } else if (l.size() == 4) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(1)));
            Enchantment e = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(l.get(2)));
            int i = Integer.parseInt(l.get(3));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, sell, e, i));
            log(uses, xp, price, "1 " + sell + " with enchantment" + e + " for a generated amount of " + buy);
        }
        if (l.size() == 5 && l.get(4).equals("any")) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(1)));
            int buyCount2 = Integer.parseInt(l.get(2));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(3)));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, buy2, buyCount2, sell, null, 0));
            log(uses, xp, price, "1 " + sell + " (randomly enchanted) for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        } else if (l.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(1)));
            int buyCount2 = Integer.parseInt(l.get(2));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(3)));
            Enchantment e = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(l.get(4)));
            int i = Integer.parseInt(l.get(5));
            list.add(new Trade.EnchantedItemTrade(uses, xp, price, buy, buy2, buyCount2, sell, e, i));
            log(uses, xp, price, "1 " + sell + " with enchantment" + e + " for a generated amount of " + buy + " and " + buyCount2 + " " + buy2);
        }
    }

    public static void addPotionTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(0)));
        int buyCount = Integer.parseInt(l.get(1));
        if (l.size() == 4) {
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            Potion p = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(l.get(3)));
            if (p == null && !l.get(3).equals("any")) return;
            list.add(new Trade.PotionItemTrade(uses, xp, price, buy, buyCount, sell, p));
            log(uses, xp, price, "1 " + sell + " with " + (p == null ? "random potion" : "potion " + p) + " applied to it for " + buyCount + " " + buy);
        } else if (l.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            int buyCount2 = Integer.parseInt(l.get(3));
            Item sell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(4)));
            Potion p = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(l.get(5)));
            if (p == null && !l.get(5).equals("any")) return;
            list.add(new Trade.PotionItemTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, sell, p));
            log(uses, xp, price, "1 " + sell + " with " + (p == null ? "random potion" : "potion " + p) + " applied to it for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        }
    }

    public static void addStewTrade(List<VillagerTrades.ITrade> list, int uses, int xp, float price, List<String> l) {
        Item buy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(0)));
        int buyCount = Integer.parseInt(l.get(1));
        if (l.size() == 4) {
            Effect e = ForgeRegistries.POTIONS.getValue(new ResourceLocation(l.get(2)));
            int d = Integer.parseInt(l.get(3));
            list.add(new Trade.StewTrade(uses, xp, price, buy, buyCount, e, d));
            log(uses, xp, price, "1 minecraft:suspicious_stew with " + e + " (" + (d / 20f) + " s) applied to it for " + buyCount + " " + buy);
        } else if (l.size() == 6) {
            Item buy2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(l.get(2)));
            int buyCount2 = Integer.parseInt(l.get(3));
            Effect e = ForgeRegistries.POTIONS.getValue(new ResourceLocation(l.get(4)));
            int d = Integer.parseInt(l.get(5));
            list.add(new Trade.StewTrade(uses, xp, price, buy, buyCount, buy2, buyCount2, e, d));
            log(uses, xp, price, "1 minecraft:suspicious_stew with " + e + " (" + (d / 20f) + " s) applied to it for " + buyCount + " " + buy + " and " + buyCount2 + " " + buy2);
        }
    }

    private static VillagerType getVillagerType(String s) {
        switch (s) {
            case "desert":
                return VillagerType.DESERT;
            case "jungle":
                return VillagerType.JUNGLE;
            case "savanna":
                return VillagerType.SAVANNA;
            case "snow":
                return VillagerType.SNOW;
            case "swamp":
                return VillagerType.SWAMP;
            case "taiga":
                return VillagerType.TAIGA;
            case "plains":
                return VillagerType.PLAINS;
        }
        return null;
    }

    private static void log(int uses, int xp, float price, String s) {
        Logger.debug("Added new trade with " + uses + " uses, " + xp + " villager xp, a price multiplier of " + price + " and selling " + s);
    }
}
