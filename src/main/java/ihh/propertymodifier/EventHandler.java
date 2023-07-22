package ihh.propertymodifier;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = PropertyModifier.MOD_ID)
public final class EventHandler {
    @SubscribeEvent
    static void itemAttributeModifier(ItemAttributeModifierEvent e) {
        if (e.getSlotType() != EquipmentSlot.MAINHAND) return;
        Multimap<Attribute, AttributeModifier> newMap = Config.MODIFIERS.get(e.getItemStack().getItem());
        if (newMap != null) {
            Multimap<Attribute, AttributeModifier> oldMap = e.getOriginalModifiers();
            Multimap<Attribute, AttributeModifier> result = HashMultimap.create();
            for (Attribute attribute : newMap.keys()) {
                Collection<AttributeModifier> collection = result.get(attribute);
                collection.addAll(newMap.get(attribute));
                result.putAll(attribute, collection);
            }
            for (Attribute attribute : oldMap.keys()) {
                Collection<AttributeModifier> collection = result.get(attribute);
                if (!result.containsKey(attribute)) {
                    collection.addAll(oldMap.get(attribute));
                    result.putAll(attribute, collection);
                }
            }
            e.clearModifiers();
            for (Attribute attribute : result.keys()) {
                for (AttributeModifier modifier : result.get(attribute)) {
                    e.addModifier(attribute, modifier);
                }
            }
        }
    }

    @SubscribeEvent
    static void blockToolModification(BlockEvent.BlockToolModificationEvent e) {
        Block block = e.getContext().getLevel().getBlockState(e.getPos()).getBlock();
        if (e.getToolAction() == ToolActions.AXE_STRIP) {
            if (Config.AXE_STRIPPING.containsKey(block)) {
                e.setFinalState(Config.AXE_STRIPPING.get(block));
            } else if (Config.CLEAR_STRIPPING.get()) {
                e.setFinalState(null);
            }
        }
        if (Config.SHOVEL_FLATTENING.containsKey(block)) {
            e.setFinalState(Config.SHOVEL_FLATTENING.get(block));
        } else if (Config.CLEAR_FLATTENING.get()) {
            e.setFinalState(null);
        }
        if (Config.HOE_TILLING.containsKey(block)) {
            Config.Triple<BlockState, Boolean, Item> triple = Config.HOE_TILLING.get(block);
            if (triple.b != null && triple.b && !e.getLevel().getBlockState(e.getPos().above()).isAir()) return;
            if (triple.a != null) {
                e.setFinalState(triple.a);
            }
            if (triple.c != null && !e.getLevel().isClientSide()) {
                Block.popResourceFromFace(e.getContext().getLevel(), e.getPos(), e.getContext().getClickedFace(), new ItemStack(triple.c));
            }
        } else if (Config.CLEAR_TILLING.get()) {
            e.setFinalState(null);
        }
    }

    @SubscribeEvent
    static void entityJoinWorld(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof Mob mob) {
            if (Config.ENTITY_ATTRIBUTES.containsKey(mob.getType())) {
                for (Attribute attribute : Config.ENTITY_ATTRIBUTES.get(mob.getType()).keySet()) {
                    double value = Config.ENTITY_ATTRIBUTES.get(mob.getType()).get(attribute);
                    AttributeInstance instance = mob.getAttributes().getInstance(attribute);
                    if (instance == null) {
                        Logger.error("Entity " + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(mob.getType())) + " doesn't have an attribute " + Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getKey(attribute)) + " that could be set");
                    } else {
                        instance.setBaseValue(value);
                        if (attribute == Attributes.MAX_HEALTH) {
                            mob.setHealth(mob.getMaxHealth());
                        }
                    }
                }
            }
        }
    }
}
