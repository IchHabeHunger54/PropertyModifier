package ihh.propertymodifier.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ihh.propertymodifier.Config;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public class MixinAnvilMenu {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;isValidRepairItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"), method = "createResult")
    private boolean mixinIsValidRepairItem(Item item, ItemStack toRepair, ItemStack repairItem, Operation<Boolean> original) {
        return Config.REPAIR_MATERIALS.containsKey(item) ? Config.REPAIR_MATERIALS.get(item).get().test(repairItem) : original.call(item, toRepair, repairItem);
    }
}
