package ihh.propertymodifier;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.common.VillagerTradingManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PropertyModifier.MODID)
public final class EventHandler {
    @SubscribeEvent
    public static void serverStart(ServerStartingEvent e) {
        HashMap<VillagerProfession, Int2ObjectOpenHashMap<VillagerTrades.ItemListing[]>> villagerTrades = ConfigUtil.villagerTrades(Config.VILLAGER_1_TRADES.get(), Config.VILLAGER_2_TRADES.get(), Config.VILLAGER_3_TRADES.get(), Config.VILLAGER_4_TRADES.get(), Config.VILLAGER_5_TRADES.get());
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
            VillagerTrades.TRADES.putAll(villagerTrades);
        }
        Int2ObjectOpenHashMap<VillagerTrades.ItemListing[]> traderTrades = ConfigUtil.traderTrades(Config.TRADER_NORMAL_TRADES.get(), Config.TRADER_LAST_TRADES.get());
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
            if (traderTrades.get(1).length > 0) {
                VillagerTrades.WANDERING_TRADER_TRADES.put(1, traderTrades.get(1));
            }
            if (traderTrades.get(2).length > 0) {
                VillagerTrades.WANDERING_TRADER_TRADES.put(2, traderTrades.get(2));
            }
        }
    }

    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof Mob mob && !mob.getPersistentData().getBoolean("alreadyAppliedAttributes")) {
            if (Config.MODIFIERS.containsKey(mob.getType())) {
                for (Map.Entry<Attribute, List<AttributeModifier>> entry : Config.MODIFIERS.get(mob.getType()).entrySet()) {
                    for (AttributeModifier modifier : entry.getValue()) {
                        AttributeInstance attribute = mob.getAttribute(entry.getKey());
                        if (attribute == null) attribute = new AttributeInstance(entry.getKey(), x -> {
                        });
                        attribute.addPermanentModifier(modifier);
                    }
                    if (entry.getKey() == Attributes.MAX_HEALTH) {
                        mob.setHealth(mob.getMaxHealth());
                    }
                }
            }
            mob.getPersistentData().putBoolean("alreadyAppliedAttributes", true);
        }
    }
}
