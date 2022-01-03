package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow
    public abstract Block getBlock();

    @Inject(at = @At("HEAD"), method = "getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F", cancellable = true)
    private void getDestroySpeed(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> callback) {
        if (Config.MIXIN_DESTROY_TIME.containsKey(getBlock())) {
            callback.setReturnValue(Config.MIXIN_DESTROY_TIME.get(getBlock()));
        }
    }

    @Inject(at = @At("HEAD"), method = "getLightEmission()I", cancellable = true)
    private void getLightEmission(CallbackInfoReturnable<Integer> callback) {
        if (Config.MIXIN_LIGHT_EMISSION.containsKey(getBlock())) {
            callback.setReturnValue(Config.MIXIN_LIGHT_EMISSION.get(getBlock()));
        }
    }

    @Inject(at = @At("HEAD"), method = "requiresCorrectToolForDrops()Z", cancellable = true)
    private void requiresCorrectToolForDrops(CallbackInfoReturnable<Boolean> callback) {
        if (Config.MIXIN_REQUIRES_TOOL.containsKey(getBlock())) {
            callback.setReturnValue(Config.MIXIN_REQUIRES_TOOL.get(getBlock()));
        }
    }
}
