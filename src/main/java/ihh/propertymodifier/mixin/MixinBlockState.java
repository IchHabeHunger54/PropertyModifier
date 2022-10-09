package ihh.propertymodifier.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import ihh.propertymodifier.Config;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockState.class)
public abstract class MixinBlockState extends BlockBehaviour.BlockStateBase {
    protected MixinBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyValueMap, MapCodec<BlockState> stateCodec) {
        super(block, propertyValueMap, stateCodec);
    }

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/level/block/Block;Lcom/google/common/collect/ImmutableMap;Lcom/mojang/serialization/MapCodec;)V")
    private void initMixinBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyValueMap, MapCodec<BlockState> stateCodec, CallbackInfo callback) {
        if (Config.DESTROY_TIME_STATES.containsKey(asState())) {
            destroySpeed = Config.DESTROY_TIME_STATES.get(asState());
        } else if (Config.DESTROY_TIME_BLOCKS.containsKey(getBlock())) {
            destroySpeed = Config.DESTROY_TIME_BLOCKS.get(getBlock());
        }
        if (Config.LIGHT_EMISSION_STATES.containsKey(asState())) {
            lightEmission = Config.LIGHT_EMISSION_STATES.get(asState());
        } else if (Config.LIGHT_EMISSION_BLOCKS.containsKey(getBlock())) {
            lightEmission = Config.LIGHT_EMISSION_BLOCKS.get(getBlock());
        }
        if (Config.REQUIRES_TOOL_STATES.containsKey(asState())) {
            requiresCorrectToolForDrops = Config.REQUIRES_TOOL_STATES.get(asState());
        } else if (Config.REQUIRES_TOOL_BLOCKS.containsKey(getBlock())) {
            requiresCorrectToolForDrops = Config.REQUIRES_TOOL_BLOCKS.get(getBlock());
        }
    }
}
