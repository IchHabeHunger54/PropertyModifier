package ihh.propertymodifier;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.VillagerTradingManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PropertyModifier.MODID)
public final class EventHandler {
    @SubscribeEvent
    public static void serverStart(FMLServerStartingEvent e) {
        HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ITrade[]>> villagerTrades = ConfigUtil.villagerTrades(Config.VILLAGER_1_TRADES.get(), Config.VILLAGER_2_TRADES.get(), Config.VILLAGER_3_TRADES.get(), Config.VILLAGER_4_TRADES.get(), Config.VILLAGER_5_TRADES.get());
        if (!villagerTrades.isEmpty()) {
            try {
                Field f = VillagerTradingManager.class.getDeclaredField("VANILLA_TRADES");
                f.setAccessible(true);
                Field m = Field.class.getDeclaredField("modifiers");
                m.setAccessible(true);
                m.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, villagerTrades);
            } catch (NoSuchFieldException | IllegalAccessException x) {
                x.printStackTrace();
            }
            VillagerTrades.VILLAGER_DEFAULT_TRADES.putAll(villagerTrades);
        }
        Int2ObjectOpenHashMap<VillagerTrades.ITrade[]> traderTrades = ConfigUtil.traderTrades(Config.TRADER_NORMAL_TRADES.get(), Config.TRADER_LAST_TRADES.get());
        if (!traderTrades.isEmpty()) {
            try {
                Field f = VillagerTradingManager.class.getDeclaredField("WANDERER_TRADES");
                f.setAccessible(true);
                Field m = Field.class.getDeclaredField("modifiers");
                m.setAccessible(true);
                m.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, traderTrades);
            } catch (NoSuchFieldException | IllegalAccessException x) {
                x.printStackTrace();
            }
            if (traderTrades.get(1).length > 0) VillagerTrades.field_221240_b.put(1, traderTrades.get(1));
            if (traderTrades.get(2).length > 0) VillagerTrades.field_221240_b.put(2, traderTrades.get(2));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void blockToolInteract(BlockEvent.BlockToolInteractEvent e) {
        if (e.getToolType() == ToolType.AXE) {
            if (Config.AXE_CLEAR.get()) e.setCanceled(true);
            if (AxeItem.BLOCK_STRIPPING_MAP.containsKey(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock())) {
                e.setFinalState(AxeItem.BLOCK_STRIPPING_MAP.get(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock()).getDefaultState());
                e.setCanceled(false);
            }
        }
        if (e.getToolType() == ToolType.SHOVEL) {
            if (Config.SHOVEL_CLEAR.get()) e.setCanceled(true);
            if (ShovelItem.SHOVEL_LOOKUP.containsKey(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock())) {
                e.setFinalState(ShovelItem.SHOVEL_LOOKUP.get(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock()));
                e.setCanceled(false);
            }
        }
        if (e.getToolType() == ToolType.HOE) {
            if (Config.HOE_CLEAR.get()) e.setCanceled(true);
            if (HoeItem.HOE_LOOKUP.containsKey(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock())) {
                e.setFinalState(HoeItem.HOE_LOOKUP.get(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock()));
                e.setCanceled(false);
            }
        }
    }

    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof MobEntity && !e.getEntity().getPersistentData().getBoolean("alreadyAppliedAttributes")) {
            MobEntity mob = (MobEntity) e.getEntity();
            if (Config.MODIFIERS.containsKey(mob.getType()))
                for (Map.Entry<Attribute, List<AttributeModifier>> entry : Config.MODIFIERS.get(mob.getType()).entrySet()) {
                    for (AttributeModifier modifier : entry.getValue()) {
                        ModifiableAttributeInstance attribute = mob.getAttribute(entry.getKey());
                        if (attribute == null) attribute = new ModifiableAttributeInstance(entry.getKey(), x -> {});
                        attribute.applyPersistentModifier(modifier);
                    }
                    if (entry.getKey() == Attributes.MAX_HEALTH) mob.setHealth(mob.getMaxHealth());
                }
            mob.getPersistentData().putBoolean("alreadyAppliedAttributes", true);
        }
    }
}
