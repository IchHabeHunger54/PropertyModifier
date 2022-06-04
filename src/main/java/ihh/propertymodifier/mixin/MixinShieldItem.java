package ihh.propertymodifier.mixin;

import ihh.propertymodifier.PropertyModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
public class MixinShieldItem {
    @Inject(at = @At("HEAD"), method = "isValidRepairItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void isValidRepairItem(ItemStack toRepair, ItemStack repair, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(repair.is(PropertyModifier.SHIELD_REPAIR_MATERIAL));
    }
}
