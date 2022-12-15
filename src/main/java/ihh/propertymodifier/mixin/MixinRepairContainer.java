package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RepairContainer.class)
public class MixinRepairContainer {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getIsRepairable(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"), method = "updateRepairOutput")
    private boolean getIsRepairable(Item instance, ItemStack p_82789_1_, ItemStack p_82789_2_) {
        if (Config.MIXIN_REPAIR_MATERIAL.containsKey(instance)) {
            return Config.MIXIN_REPAIR_MATERIAL.get(instance).getValue().test(p_82789_2_);
        }
        return instance.getIsRepairable(p_82789_1_, p_82789_2_);
    }
}
