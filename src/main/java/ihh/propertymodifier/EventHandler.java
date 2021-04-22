package ihh.propertymodifier;

import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class EventHandler {
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
}
