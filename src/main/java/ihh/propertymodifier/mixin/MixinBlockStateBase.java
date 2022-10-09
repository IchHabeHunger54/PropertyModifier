package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow
    public abstract Block getBlock();
    @Shadow
    protected abstract BlockState asState();

    @Inject(at = @At("HEAD"), method = "getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F", cancellable = true)
    private void getDestroySpeed(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> callback) {
        if (Config.DESTROY_TIME_STATES.containsKey(asState())) {
            callback.setReturnValue(Config.DESTROY_TIME_STATES.get(asState()));
        } else if (Config.DESTROY_TIME_BLOCKS.containsKey(getBlock())) {
            callback.setReturnValue(Config.DESTROY_TIME_BLOCKS.get(getBlock()));
        }
    }

    @Inject(at = @At("HEAD"), method = "getLightEmission()I", cancellable = true)
    private void getLightEmission(CallbackInfoReturnable<Integer> callback) {
        if (Config.LIGHT_EMISSION_STATES.containsKey(asState())) {
            callback.setReturnValue(Config.LIGHT_EMISSION_STATES.get(asState()));
        } else if (Config.LIGHT_EMISSION_BLOCKS.containsKey(getBlock())) {
            callback.setReturnValue(Config.LIGHT_EMISSION_BLOCKS.get(getBlock()));
        }
    }

    @Inject(at = @At("HEAD"), method = "requiresCorrectToolForDrops()Z", cancellable = true)
    private void requiresCorrectToolForDrops(CallbackInfoReturnable<Boolean> callback) {
        if (Config.REQUIRES_TOOL_STATES.containsKey(asState())) {
            callback.setReturnValue(Config.REQUIRES_TOOL_STATES.get(asState()));
        } else if (Config.REQUIRES_TOOL_BLOCKS.containsKey(getBlock())) {
            callback.setReturnValue(Config.REQUIRES_TOOL_BLOCKS.get(getBlock()));
        }
    }
}
