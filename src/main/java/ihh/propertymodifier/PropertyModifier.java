package ihh.propertymodifier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
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
    public static ItemGroup EMPTY = new ItemGroup("none") {
        @Nonnull
        @Override
        public ItemStack createIcon() {
            return ItemStack.EMPTY;
        }
    };
    public static final ITag.INamedTag<Item> SHIELD_REPAIR_MATERIAL = ItemTags.makeWrapperTag("propertymodifier:shield_repair_material");
    public static final ITag.INamedTag<Item> ELYTRA_REPAIR_MATERIAL = ItemTags.makeWrapperTag("propertymodifier:elytra_repair_material");

    public PropertyModifier() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().<FMLCommonSetupEvent>addListener(EventPriority.LOWEST, e -> Config.read());
        if (ModList.get().isLoaded("jei")) FMLJavaModLoadingContext.get().getModEventBus().<FMLCommonSetupEvent>addListener(EventPriority.LOWEST, e -> Config.workCreative());
        else FMLJavaModLoadingContext.get().getModEventBus().<FMLLoadCompleteEvent>addListener(EventPriority.LOWEST, e -> Config.workCreative());
        FMLJavaModLoadingContext.get().getModEventBus().<FMLLoadCompleteEvent>addListener(EventPriority.LOWEST, e -> Config.work());
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Config::searchReload);
    }
}
