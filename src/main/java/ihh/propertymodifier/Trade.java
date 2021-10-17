package ihh.propertymodifier;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
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

public class Trade implements VillagerTrades.ITrade {
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
            if (sell instanceof DyeableArmorItem) {
                ArrayList<DyeItem> l = new ArrayList<>();
                l.add(DyeItem.getItem(DyeColor.byId(rand.nextInt(16))));
                if (rand.nextFloat() > 0.7F) l.add(DyeItem.getItem(DyeColor.byId(rand.nextInt(16))));
                if (rand.nextFloat() > 0.8F) l.add(DyeItem.getItem(DyeColor.byId(rand.nextInt(16))));
                return IDyeableArmorItem.dyeItem(new ItemStack(sell), l);
            }
            return new ItemStack(sell);
        }
    }

    public static final class MapTrade extends Trade {
        private final Structure<?> structure;
        private final MapDecoration.Type mapDecoration;

        public MapTrade(int uses, int xp, float price, Item buy, int buyCount, Structure<?> structureIn, MapDecoration.Type mapDecorationIn) {
            super(uses, xp, price, buy, buyCount, Items.MAP, 1);
            structure = structureIn;
            mapDecoration = mapDecorationIn;
        }

        public MapTrade(int uses, int xp, float price, Item buy, int buyCount, Item buySecond, int buySecondCount, Structure<?> structureIn, MapDecoration.Type mapDecorationIn) {
            super(uses, xp, price, buy, buyCount, buySecond, buySecondCount, Items.MAP, 1);
            structure = structureIn;
            mapDecoration = mapDecorationIn;
        }

        @Override
        protected ItemStack getSellItem() {
            if (trader.world instanceof ServerWorld) {
                ServerWorld w = (ServerWorld) trader.world;
                BlockPos p = w.getStructureLocation(structure, trader.getPosition(), 100, true);
                if (p != null) {
                    ItemStack i = FilledMapItem.setupNewMap(w, p.getX(), p.getZ(), (byte) 2, true, true);
                    FilledMapItem.func_226642_a_(w, i);
                    MapData.addTargetDecoration(i, p, "+", mapDecoration);
                    i.setDisplayName(new TranslationTextComponent("filled_map." + structure.getStructureName().toLowerCase(Locale.ROOT)));
                    return i;
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
            return trader instanceof IVillagerDataHolder ? new ItemStack(items.get(((IVillagerDataHolder) trader).getVillagerData().getType()).a.a, items.get(((IVillagerDataHolder) trader).getVillagerData().getType()).a.b) : ItemStack.EMPTY;
        }

        @Override
        protected ItemStack getSellItem() {
            return trader instanceof IVillagerDataHolder ? new ItemStack(items.get(((IVillagerDataHolder) trader).getVillagerData().getType()).b.a, items.get(((IVillagerDataHolder) trader).getVillagerData().getType()).b.b) : ItemStack.EMPTY;
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
            List<Enchantment> l = StreamSupport.stream(ForgeRegistries.ENCHANTMENTS.spliterator(), false).filter(Enchantment::canVillagerTrade).collect(Collectors.toList());
            Enchantment e = l.get(rand.nextInt(l.size()));
            int i = MathHelper.nextInt(rand, e.getMinLevel(), e.getMaxLevel());
            ItemStack s = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(e, i));
            int j = 2 + rand.nextInt(5 + i * 10) + 3 * i;
            if (e.isTreasureEnchantment()) j *= 2;
            if (j > 64) j = 64;
            return new MerchantOffer(new ItemStack(buy, j), new ItemStack(buy2, buy2Count), s, maxUses, xpValue, priceMultiplier);
        }

        @Override
        protected ItemStack getSellItem() {
            ItemStack s = new ItemStack(sell);
            s.addEnchantment(enchantment, level);
            return s;
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
            int e = 5 + rand.nextInt(15);
            ItemStack i = new ItemStack(buy, e);
            ItemStack j = EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(sell), e, false);
            return new MerchantOffer(i, new ItemStack(buy2, buy2Count), j, maxUses, xpValue, priceMultiplier);
        }

        @Override
        protected ItemStack getSellItem() {
            ItemStack s = new ItemStack(sell);
            s.addEnchantment(enchantment, level);
            return s;
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
            return PotionUtils.addPotionToItemStack(new ItemStack(sell, sellCount), potion.get(rand.nextInt(potion.size())));
        }
    }

    public static final class StewTrade extends Trade {
        private final Effect effect;
        private final int duration;

        public StewTrade(int uses, int xp, float price, Item buy, int buyCount, Effect effectIn, int durationIn) {
            super(uses, xp, price, buy, buyCount, Items.SUSPICIOUS_STEW, 1);
            effect = effectIn;
            duration = durationIn;
        }

        public StewTrade(int uses, int xp, float price, Item buy, int buyCount, Item buySecond, int buySecondCount, Effect effectIn, int durationIn) {
            super(uses, xp, price, buy, buyCount, buySecond, buySecondCount, Items.SUSPICIOUS_STEW, 1);
            effect = effectIn;
            duration = durationIn;
        }

        @Override
        protected ItemStack getSellItem() {
            ItemStack i = new ItemStack(sell, sellCount);
            SuspiciousStewItem.addEffect(i, effect, duration);
            return i;
        }
    }
}
