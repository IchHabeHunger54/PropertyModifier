package ihh.propertymodifier.mixin;

import ihh.propertymodifier.PropertyModifier;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class MixinElytraItem {
    @Inject(at = @At("HEAD"), method = "isValidRepairItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void isValidRepairItem(ItemStack toRepair, ItemStack repair, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(repair.is(PropertyModifier.ELYTRA_REPAIR_MATERIAL));
    }
}
