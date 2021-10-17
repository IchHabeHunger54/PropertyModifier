package ihh.propertymodifier;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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

    @SubscribeEvent(priority = EventPriority.LOWEST)
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
            MobEntity mob = (MobEntity) e.getEntity();
            if (ServerConfig.MODIFIERS.containsKey(mob.getType()))
                for (Map.Entry<Attribute, List<AttributeModifier>> entry : ServerConfig.MODIFIERS.get(mob.getType()).entrySet()) {
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

    @SubscribeEvent
    public static void modConfig(ModConfig.ModConfigEvent e) {
        if (e.getConfig().getType() == ModConfig.Type.SERVER) ServerConfig.work();
    }
}
