package ihh.propertymodifier.mixin;

import ihh.propertymodifier.PropertyModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
public class MixinShieldItem extends Item {
    public MixinShieldItem(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("HEAD"), method = "getIsRepairable(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", cancellable = true)
    private void getIsRepairable(ItemStack toRepair, ItemStack repair, CallbackInfoReturnable<Boolean> callback) {
        if (PropertyModifier.SHIELD_REPAIR_MATERIAL.contains(repair.getItem()))
            callback.setReturnValue(true);
    }
}
