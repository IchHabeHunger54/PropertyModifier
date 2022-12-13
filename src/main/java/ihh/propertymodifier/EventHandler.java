package ihh.propertymodifier;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PropertyModifier.MOD_ID)
public final class EventHandler {
    @SubscribeEvent
    static void entityJoinWorld(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof Mob mob) {
            if (Config.MODIFIERS.containsKey(mob.getType())) {
                for (Attribute attribute : Config.MODIFIERS.get(mob.getType()).keySet()) {
                    Pair<AttributeModifier.Operation, Double> value = Config.MODIFIERS.get(mob.getType()).get(attribute);
                    AttributeInstance instance = mob.getAttributes().getInstance(attribute);
                    if (instance == null) {
                        Logger.error("Entity " + ForgeRegistries.ENTITIES.getKey(mob.getType()).toString() + " doesn't have an attribute " + ForgeRegistries.ATTRIBUTES.getKey(attribute).toString() + " that could be set");
                    } else {
                        double baseValue = instance.getBaseValue();
                        baseValue = switch (value.getFirst()) {
                            case ADDITION -> baseValue + value.getSecond();
                            case MULTIPLY_BASE, MULTIPLY_TOTAL -> baseValue * value.getSecond();
                        };
                        instance.setBaseValue(baseValue);
                        if (attribute == Attributes.MAX_HEALTH) {
                            mob.setHealth(mob.getMaxHealth());
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    static void blockToolInteract(BlockEvent.BlockToolInteractEvent e) {
        Block block = e.getWorld().getBlockState(e.getPos()).getBlock();
        if (e.getToolAction() == ToolActions.AXE_STRIP) {
            if (Config.AXE_STRIPPING.containsKey(block)) {
                e.setFinalState(Config.AXE_STRIPPING.get(block).defaultBlockState());
            } else if (Config.AXE_CLEAR.get()) {
                e.setFinalState(null);
            }
        }
        if (e.getToolAction() == ToolActions.SHOVEL_FLATTEN) {
            if (Config.SHOVEL_FLATTENING.containsKey(block)) {
                e.setFinalState(Config.SHOVEL_FLATTENING.get(block).defaultBlockState());
            } else if (Config.SHOVEL_CLEAR.get()) {
                e.setFinalState(null);
            }
        }
    }
}
