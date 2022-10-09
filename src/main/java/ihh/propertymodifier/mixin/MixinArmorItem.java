package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorItem.class)
public class MixinArmorItem {
    @Inject(at = @At("HEAD"), method = "getEnchantmentValue()I", cancellable = true)
    private void getEnchantmentValue(CallbackInfoReturnable<Integer> callback) {
        if (Config.ENCHANTMENT_VALUES.containsKey(this)) {
            callback.setReturnValue(Config.ENCHANTMENT_VALUES.get(this));
        }
    }

    @Inject(at = @At("HEAD"), method = "isValidRepairItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
    private void isValidRepairItem(ItemStack toRepair, ItemStack repair, CallbackInfoReturnable<Boolean> callback) {
        if (Config.REPAIR_MATERIALS.containsKey(this)) {
            callback.setReturnValue(Config.REPAIR_MATERIALS.get(this).get().test(repair));
        }
    }
}
