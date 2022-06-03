package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgeHooks.class)
public class MixinForgeHooks {
    @Inject(at = @At("HEAD"), method = "canHarvestBlock(Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true, remap = false)
    private static void canHarvestBlock(BlockState state, PlayerEntity player, IBlockReader world, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        ItemStack is = player.getHeldItemMainhand();
        Item item = is.getItem();
        ToolType tool = state.getHarvestTool();
        if (item instanceof TieredItem && Config.MIXIN_TOOL_HARVEST_LEVEL.containsKey(item) && tool != null)
            callback.setReturnValue(tool.equals(is.getToolTypes().stream().findAny().orElse(null)) && (Config.MIXIN_TOOL_HARVEST_LEVEL.containsKey((TieredItem) item) ? Config.MIXIN_TOOL_HARVEST_LEVEL.get((TieredItem) item) : is.getHarvestLevel(is.getToolTypes().stream().findAny().orElse(null), player, state)) >= state.getHarvestLevel());
    }
}
