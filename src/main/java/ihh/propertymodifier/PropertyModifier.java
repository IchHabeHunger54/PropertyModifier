package ihh.propertymodifier;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod(PropertyModifier.MODID)
public final class PropertyModifier {
    public static final String MODID = "propertymodifier";
    public static final Tag<Item> SHIELD_REPAIR_MATERIAL = ItemTags.bind("propertymodifier:shield_repair_material");
    public static final Tag<Item> ELYTRA_REPAIR_MATERIAL = ItemTags.bind("propertymodifier:elytra_repair_material");
    public static CreativeModeTab EMPTY = new CreativeModeTab("none") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return ItemStack.EMPTY;
        }
    };

    public PropertyModifier() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().<FMLCommonSetupEvent>addListener(EventPriority.LOWEST, e -> Config.read());
        FMLJavaModLoadingContext.get().getModEventBus().<FMLCommonSetupEvent>addListener(EventPriority.LOWEST, e -> Config.workCreative());
        FMLJavaModLoadingContext.get().getModEventBus().<FMLLoadCompleteEvent>addListener(EventPriority.LOWEST, e -> Config.work());
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Config::searchReload);
    }
}
