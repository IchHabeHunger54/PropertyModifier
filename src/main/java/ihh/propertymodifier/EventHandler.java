package ihh.propertymodifier;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PropertyModifier.MOD_ID)
public final class EventHandler {
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
