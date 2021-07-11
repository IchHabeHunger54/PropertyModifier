package ihh.propertymodifier;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PropertyModifier.MODID)
public final class EventHandler {
    @SubscribeEvent
    public static void serverStart(FMLServerStartingEvent e) {
        ServerConfig.work();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void blockToolInteract(BlockEvent.BlockToolInteractEvent e) {
        if (e.getToolType() == ToolType.AXE) {
            if (ServerConfig.AXE_CLEAR.get()) e.setCanceled(true);
            if (AxeItem.BLOCK_STRIPPING_MAP.containsKey(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock())) {
                e.setFinalState(AxeItem.BLOCK_STRIPPING_MAP.get(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock()).getDefaultState());
                e.setCanceled(false);
            }
        }
        if (e.getToolType() == ToolType.SHOVEL) {
            if (ServerConfig.SHOVEL_CLEAR.get()) e.setCanceled(true);
            if (ShovelItem.SHOVEL_LOOKUP.containsKey(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock())) {
                e.setFinalState(ShovelItem.SHOVEL_LOOKUP.get(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock()));
                e.setCanceled(false);
            }
        }
        if (e.getToolType() == ToolType.HOE) {
            if (ServerConfig.HOE_CLEAR.get()) e.setCanceled(true);
            if (HoeItem.HOE_LOOKUP.containsKey(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock())) {
                e.setFinalState(HoeItem.HOE_LOOKUP.get(e.getPlayer().getEntityWorld().getBlockState(e.getPos()).getBlock()));
                e.setCanceled(false);
            }
        }
    }

    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof MobEntity && !e.getEntity().getPersistentData().getBoolean("alreadyAppliedAttributes")) {
            MobEntity l = (MobEntity) e.getEntity();
            if (ServerConfig.MODIFIERS.containsKey(l.getType()))
                for (Map.Entry<Attribute, List<AttributeModifier>> m : ServerConfig.MODIFIERS.get(l.getType()).entrySet()) {
                    for (AttributeModifier a : m.getValue()) {
                        ModifiableAttributeInstance t = l.getAttribute(m.getKey());
                        if (t == null) t = new ModifiableAttributeInstance(m.getKey(), x -> {});
                        t.applyPersistentModifier(a);
                    }
                    if (m.getKey() == Attributes.MAX_HEALTH) l.setHealth(l.getMaxHealth());
                }
            l.getPersistentData().putBoolean("alreadyAppliedAttributes", true);
        }
    }

    @SubscribeEvent
    public static void modConfig(ModConfig.ModConfigEvent e) {
        if (e.getConfig().getType() == ModConfig.Type.SERVER) ServerConfig.work();
    }
}
