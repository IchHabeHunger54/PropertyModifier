package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.ToolType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(at = @At("HEAD"), method = "getHarvestTool(Lnet/minecraft/block/BlockState;)Lnet/minecraftforge/common/ToolType;", cancellable = true, remap = false)
    private void getHarvestTool(BlockState state, CallbackInfoReturnable<ToolType> callback) {
        if (Config.MIXIN_HARVEST_TOOL.containsKey(this))
            callback.setReturnValue(Config.MIXIN_HARVEST_TOOL.get(this).getName().equals("none") ? null : Config.MIXIN_HARVEST_TOOL.get(this));
    }

    @Inject(at = @At("HEAD"), method = "getHarvestLevel(Lnet/minecraft/block/BlockState;)I", cancellable = true, remap = false)
    private void getHarvestLevel(BlockState state, CallbackInfoReturnable<Integer> callback) {
        if (Config.MIXIN_HARVEST_LEVEL.containsKey(this))
            callback.setReturnValue(Config.MIXIN_HARVEST_LEVEL.get(this));
    }
}
