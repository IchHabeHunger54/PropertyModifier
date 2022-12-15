package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public class MixinAnvilMenu {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;isValidRepairItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"), method = "createResult")
    private boolean isValidRepairItem(Item instance, ItemStack p_41402_, ItemStack p_41403_) {
        if (Config.REPAIR_MATERIALS.containsKey(instance)) {
            return Config.REPAIR_MATERIALS.get(instance).get().test(p_41403_);
        }
        return instance.isValidRepairItem(p_41402_, p_41403_);
    }
}