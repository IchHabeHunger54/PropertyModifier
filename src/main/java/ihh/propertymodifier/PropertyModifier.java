package ihh.propertymodifier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod(PropertyModifier.MOD_ID)
public final class PropertyModifier {
    public static final String MOD_ID = "propertymodifier";
    public static final TagKey<Item> SHIELD_REPAIR_MATERIAL = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MOD_ID, "shield_repair_material"));
    public static final TagKey<Item> ELYTRA_REPAIR_MATERIAL = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MOD_ID, "elytra_repair_material"));
    public static CreativeModeTab MISSINGNO_TAB = new CreativeModeTab("missingno") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return ItemStack.EMPTY;
        }
    };

    public PropertyModifier() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().<FMLLoadCompleteEvent>addListener(EventPriority.LOWEST, e -> Config.read());
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Config::searchReload);
    }
}
