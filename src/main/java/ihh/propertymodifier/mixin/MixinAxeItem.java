package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraftforge.common.ToolType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(AxeItem.class)
public class MixinAxeItem extends ToolItem {
    public MixinAxeItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Set<Block> effectiveBlocksIn, Properties builderIn) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveBlocksIn, builderIn);
    }

    @Inject(at = @At("HEAD"), method = "getDestroySpeed(Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/BlockState;)F", cancellable = true)
    private void getDestroySpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> callback) {
        if (Config.MIXIN_HARVEST_TOOL.containsKey(state.getBlock()))
            callback.setReturnValue(Config.MIXIN_HARVEST_TOOL.get(state.getBlock()) == ToolType.AXE ? this.efficiency : super.getDestroySpeed(stack, state));
    }
}
