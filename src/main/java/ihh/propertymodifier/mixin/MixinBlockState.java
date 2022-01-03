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
        if (Config.MIXIN_DESTROY_TIME.containsKey(getBlock())) {
            destroySpeed = Config.MIXIN_DESTROY_TIME.get(getBlock());
        }
        if (Config.MIXIN_LIGHT_EMISSION.containsKey(getBlock())) {
            lightEmission = Config.MIXIN_LIGHT_EMISSION.get(getBlock());
        }
        if (Config.MIXIN_REQUIRES_TOOL.containsKey(getBlock())) {
            requiresCorrectToolForDrops = Config.MIXIN_REQUIRES_TOOL.get(getBlock());
        }
    }
}
