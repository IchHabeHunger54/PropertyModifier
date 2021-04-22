package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorItem.class)
public class MixinArmorItem extends Item {
    public MixinArmorItem(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("HEAD"), method = "getItemEnchantability()I", cancellable = true)
    private void getItemEnchantability(CallbackInfoReturnable<Integer> callback) {
        if (Config.MIXIN_ENCHANTABILITY.containsKey(this.getItem()))
            callback.setReturnValue(Config.MIXIN_ENCHANTABILITY.get(this.getItem()));
    }

    @Inject(at = @At("HEAD"), method = "getIsRepairable(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", cancellable = true)
    private void getIsRepairable(ItemStack toRepair, ItemStack repair, CallbackInfoReturnable<Boolean> callback) {
        if (Config.MIXIN_REPAIR_MATERIAL.containsKey(this.getItem()))
            callback.setReturnValue(Config.MIXIN_REPAIR_MATERIAL.get(this.getItem()).getValue().test(repair));
    }
}
