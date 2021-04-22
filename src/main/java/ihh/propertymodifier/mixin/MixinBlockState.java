package ihh.propertymodifier.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import ihh.propertymodifier.Config;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockState.class)
public abstract class MixinBlockState extends AbstractBlock.AbstractBlockState {
    protected MixinBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyValueMap, MapCodec<BlockState> stateCodec) {
        super(block, propertyValueMap, stateCodec);
    }

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/block/Block;Lcom/google/common/collect/ImmutableMap;Lcom/mojang/serialization/MapCodec;)V", require = 1)
    private void MixinBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyValueMap, MapCodec<BlockState> stateCodec, CallbackInfo callback) {
        if (Config.MIXIN_HARDNESS.containsKey(this.getBlock()))
            this.hardness = Config.MIXIN_HARDNESS.get(this.getBlock());
        if (Config.MIXIN_LIGHT_LEVEL.containsKey(this.getBlock()))
            this.lightLevel = Config.MIXIN_LIGHT_LEVEL.get(this.getBlock());
        if (Config.MIXIN_REQUIRES_TOOL.containsKey(this.getBlock()))
            this.requiresTool = Config.MIXIN_REQUIRES_TOOL.get(this.getBlock());
    }
}
