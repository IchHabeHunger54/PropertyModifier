package ihh.propertymodifier;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod(PropertyModifier.MODID)
public final class PropertyModifier {
    public static final String MODID = "propertymodifier";
    public static ItemGroup EMPTY = new ItemGroup("none") {
        @Nonnull
        @Override
        public ItemStack createIcon() {
            return ItemStack.EMPTY;
        }
    };

    public PropertyModifier() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().<FMLLoadCompleteEvent>addListener(EventPriority.LOWEST, e -> Config.work());
    }
}
