package ihh.propertymodifier;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;

public class Trade implements VillagerTrades.ItemListing {
    protected final int maxUses;
    protected final int xpValue;
    protected final float priceMultiplier;
    protected final Item buy;
    protected final int buyCount;
    protected final Item buy2;
    protected final int buy2Count;
    protected final Item sell;
    protected final int sellCount;
    protected Random rand;
    protected Entity trader;

    public Trade(int uses, int xp, float price, Item buy, int buyCount, Item sell, int sellCount) {
        this(uses, xp, price, buy, buyCount, null, 0, sell, sellCount);
    }

    public Trade(int uses, int xp, float price, Item buy, int buyCount, Item buy2, int buy2Count, Item sell, int sellCount) {
        maxUses = uses;
        xpValue = xp;
        priceMultiplier = price;
        this.buy = buy;
        this.buyCount = buyCount;
        this.buy2 = buy2;
        this.buy2Count = buy2Count;
        this.sell = sell;
        this.sellCount = sellCount;
    }

    @Override
    public MerchantOffer getOffer(@Nonnull Entity trader, @Nonnull Random rand) {
        this.rand = rand;
        this.trader = trader;
        return new MerchantOffer(getBuyItem(), buy2 == null ? ItemStack.EMPTY : new ItemStack(buy2, buy2Count), getSellItem(), 0, maxUses, xpValue, priceMultiplier);
    }

    protected ItemStack getBuyItem() {
        return buy == null ? ItemStack.EMPTY : new ItemStack(buy, buyCount);
    }

    protected ItemStack getSellItem() {
        return sell == null ? ItemStack.EMPTY : new ItemStack(sell, sellCount);
    }

    public static final class DyedItemTrade extends Trade {
        public DyedItemTrade(int uses, int xp, float price, Item buy, int buyCount, Item sell) {
            super(uses, xp, price, buy, buyCount, sell, 1);
        }

        public DyedItemTrade(int uses, int xp, float price, Item buy, int buyCount, Item buySecond, int buySecondCount, Item sell) {
            super(uses, xp, price, buy, buyCount, buySecond, buySecondCount, sell, 1);
        }

        @Override
        protected ItemStack getSellItem() {
            if (sell instanceof DyeableLeatherItem) {
                ArrayList<DyeItem> list = new ArrayList<>();
                list.add(DyeItem.byColor(DyeColor.byId(rand.nextInt(16))));
                if (rand.nextFloat() > 0.7F) {
                    list.add(DyeItem.byColor(DyeColor.byId(rand.nextInt(16))));
                }
                if (rand.nextFloat() > 0.8F) {
                    list.add(DyeItem.byColor(DyeColor.byId(rand.nextInt(16))));
                }
                return DyeableLeatherItem.dyeArmor(new ItemStack(sell), list);
            }
            return new ItemStack(sell);
        }
    }

    public static final class MapTrade extends Trade {
        private final StructureFeature<?> structure;
        private final MapDecoration.Type mapDecoration;

        public MapTrade(int uses, int xp, float price, Item buy, int buyCount, StructureFeature<?> structureIn, MapDecoration.Type mapDecorationIn) {
            super(uses, xp, price, buy, buyCount, Items.MAP, 1);
            structure = structureIn;
            mapDecoration = mapDecorationIn;
        }

        public MapTrade(int uses, int xp, float price, Item buy, int buyCount, Item buySecond, int buySecondCount, StructureFeature<?> structureIn, MapDecoration.Type mapDecorationIn) {
            super(uses, xp, price, buy, buyCount, buySecond, buySecondCount, Items.MAP, 1);
            structure = structureIn;
            mapDecoration = mapDecorationIn;
        }

        @Override
        protected ItemStack getSellItem() {
            if (trader.level instanceof ServerLevel level) {
                BlockPos pos = level.findNearestMapFeature(structure, trader.blockPosition(), 100, true);
                if (pos != null) {
                    ItemStack item = MapItem.create(level, pos.getX(), pos.getZ(), (byte) 2, true, true);
                    MapItem.renderBiomePreviewMap(level, item);
                    MapItemSavedData.addTargetDecoration(item, pos, "+", mapDecoration);
                    item.setHoverName(new TranslatableComponent("filled_map." + structure.getFeatureName().toLowerCase(Locale.ROOT)));
                    return item;
                }
            }
            return new ItemStack(sell, sellCount);
        }
    }

    public static final class BiomeTrade extends Trade {
        private final HashMap<VillagerType, Pair<Pair<Item, Integer>, Pair<Item, Integer>>> items;

        public BiomeTrade(int uses, int xp, float price, HashMap<VillagerType, Pair<Pair<Item, Integer>, Pair<Item, Integer>>> items) {
            super(uses, xp, price, null, 1, null, 1);
            this.items = items;
        }

        @Override
        protected ItemStack getBuyItem() {
            return trader instanceof VillagerDataHolder holder ? new ItemStack(items.get(holder.getVillagerData().getType()).a.a, items.get(holder.getVillagerData().getType()).a.b) : ItemStack.EMPTY;
        }

        @Override
        protected ItemStack getSellItem() {
            return trader instanceof VillagerDataHolder holder ? new ItemStack(items.get(holder.getVillagerData().getType()).b.a, items.get(holder.getVillagerData().getType()).b.b) : ItemStack.EMPTY;
        }
    }

    public static final class EnchantedBookTrade extends Trade {
        private final Enchantment enchantment;
        private final int level;

        public EnchantedBookTrade(int uses, int xp, float price, Item buy, Enchantment enchantmentIn, int levelIn) {
            super(uses, xp, price, buy, 1, Items.ENCHANTED_BOOK, 1);
            enchantment = enchantmentIn;
            level = levelIn;
        }

        public EnchantedBookTrade(int uses, int xp, float price, Item buy, Item buySecond, int buySecondCount, Enchantment enchantmentIn, int levelIn) {
            super(uses, xp, price, buy, 1, buySecond, buySecondCount, Items.ENCHANTED_BOOK, 1);
            enchantment = enchantmentIn;
            level = levelIn;
        }

        @Override
        public MerchantOffer getOffer(@Nonnull Entity trader, @Nonnull Random rand) {
            if (enchantment != null) return super.getOffer(trader, rand);
            List<Enchantment> list = StreamSupport.stream(ForgeRegistries.ENCHANTMENTS.spliterator(), false).filter(Enchantment::isTradeable).collect(Collectors.toList());
            Enchantment enchantment = list.get(rand.nextInt(list.size()));
            int level = Mth.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack item = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
            int cost = 2 + rand.nextInt(5 + level * 10) + 3 * level;
            if (enchantment.isTreasureOnly()) {
                cost *= 2;
            }
            if (cost > 64) {
                cost = 64;
            }
            return new MerchantOffer(new ItemStack(buy, cost), new ItemStack(buy2, buy2Count), item, maxUses, xpValue, priceMultiplier);
        }

        @Override
        protected ItemStack getSellItem() {
            ItemStack item = new ItemStack(sell);
            item.enchant(enchantment, level);
            return item;
        }
    }

    public static final class EnchantedItemTrade extends Trade {
        private final Enchantment enchantment;
        private final int level;

        public EnchantedItemTrade(int uses, int xp, float price, Item buy, Item sell, Enchantment enchantmentIn, int levelIn) {
            super(uses, xp, price, buy, 1, sell, 1);
            enchantment = enchantmentIn;
            level = levelIn;
        }

        public EnchantedItemTrade(int uses, int xp, float price, Item buy, Item buySecond, int buySecondCount, Item sell, Enchantment enchantmentIn, int levelIn) {
            super(uses, xp, price, buy, 1, buySecond, buySecondCount, sell, 1);
            enchantment = enchantmentIn;
            level = levelIn;
        }

        @Override
        public MerchantOffer getOffer(@Nonnull Entity trader, @Nonnull Random rand) {
            if (enchantment != null) return super.getOffer(trader, rand);
            int level = 5 + rand.nextInt(15);
            ItemStack item = EnchantmentHelper.enchantItem(rand, new ItemStack(sell), level, false);
            return new MerchantOffer(new ItemStack(buy, level), new ItemStack(buy2, buy2Count), item, maxUses, xpValue, priceMultiplier);
        }

        @Override
        protected ItemStack getSellItem() {
            ItemStack item = new ItemStack(sell);
            item.enchant(enchantment, level);
            return item;
        }
    }

    public static final class PotionItemTrade extends Trade {
        private final List<Potion> potion;

        public PotionItemTrade(int uses, int xp, float price, Item buy, int buyCount, Item sell, List<Potion> potionIn) {
            super(uses, xp, price, buy, buyCount, sell, 1);
            potion = potionIn;
        }

        public PotionItemTrade(int uses, int xp, float price, Item buy, int buyCount, Item buySecond, int buySecondCount, Item sell, List<Potion> potionIn) {
            super(uses, xp, price, buy, buyCount, buySecond, buySecondCount, sell, 1);
            potion = potionIn;
        }

        @Override
        protected ItemStack getSellItem() {
            return PotionUtils.setPotion(new ItemStack(sell, sellCount), potion.get(rand.nextInt(potion.size())));
        }
    }

    public static final class StewTrade extends Trade {
        private final MobEffect effect;
        private final int duration;

        public StewTrade(int uses, int xp, float price, Item buy, int buyCount, MobEffect effectIn, int durationIn) {
            super(uses, xp, price, buy, buyCount, Items.SUSPICIOUS_STEW, 1);
            effect = effectIn;
            duration = durationIn;
        }

        public StewTrade(int uses, int xp, float price, Item buy, int buyCount, Item buySecond, int buySecondCount, MobEffect effectIn, int durationIn) {
            super(uses, xp, price, buy, buyCount, buySecond, buySecondCount, Items.SUSPICIOUS_STEW, 1);
            effect = effectIn;
            duration = durationIn;
        }

        @Override
        protected ItemStack getSellItem() {
            ItemStack item = new ItemStack(sell, sellCount);
            SuspiciousStewItem.saveMobEffect(item, effect, duration);
            return item;
        }
    }
}
