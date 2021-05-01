package ihh.propertymodifier;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

@Mod("propertymodifier")
public final class PropertyModifier {
    public static ItemGroup EMPTY = new ItemGroup("none") {
        @Nonnull
        @Override
        public ItemStack createIcon() {
            return ItemStack.EMPTY;
        }
    };

    public PropertyModifier() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().<FMLLoadCompleteEvent>addListener(EventPriority.LOWEST, e -> Config.work());
        MinecraftForge.EVENT_BUS.<EntityJoinWorldEvent>addListener(e -> {
            if (e.getEntity() instanceof MobEntity && !e.getEntity().getPersistentData().getBoolean("alreadyAppliedAttributes")) {
                MobEntity l = (MobEntity) e.getEntity();
                if (Config.MODIFIERS.containsKey(l.getType()))
                    for (Map.Entry<Attribute, List<AttributeModifier>> m : Config.MODIFIERS.get(l.getType()).entrySet()) {
                        for (AttributeModifier a : m.getValue()) l.getAttribute(m.getKey()).applyPersistentModifier(a);
                        if (m.getKey() == Attributes.MAX_HEALTH) l.setHealth(l.getMaxHealth());
                    }
                l.getPersistentData().putBoolean("alreadyAppliedAttributes", true);
            }
        });
    }
}
