package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinAbstractBlockState {
    @Shadow
    public abstract Block getBlock();

    @Inject(at = @At("HEAD"), method = "getBlockHardness(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)F", cancellable = true)
    private void getBlockHardness(IBlockReader worldIn, BlockPos pos, CallbackInfoReturnable<Float> callback) {
        if (Config.MIXIN_HARDNESS.containsKey(this.getBlock()))
            callback.setReturnValue(Config.MIXIN_HARDNESS.get(this.getBlock()));
    }

    @Inject(at = @At("HEAD"), method = "getLightValue()I", cancellable = true)
    private void getLightValue(CallbackInfoReturnable<Integer> callback) {
        if (Config.MIXIN_LIGHT_LEVEL.containsKey(this.getBlock()))
            callback.setReturnValue(Config.MIXIN_LIGHT_LEVEL.get(this.getBlock()));
    }

    @Inject(at = @At("HEAD"), method = "getRequiresTool()Z", cancellable = true)
    private void getRequiresTool(CallbackInfoReturnable<Boolean> callback) {
        if (Config.MIXIN_REQUIRES_TOOL.containsKey(this.getBlock()))
            callback.setReturnValue(Config.MIXIN_REQUIRES_TOOL.get(this.getBlock()));
    }
}
