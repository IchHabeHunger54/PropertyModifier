package ihh.propertymodifier.mixin;

import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;

//TODO
@Mixin(Enchantment.class)
public abstract class MixinEnchantment {
/*
    @Shadow public abstract int getMinEnchantability(int enchantmentLevel);

    @Inject(at = @At("HEAD"), method = "getMaxLevel()I", cancellable = true)
    private void getMaxLevel(CallbackInfoReturnable<Integer> callback) {
        if (Config.MIXIN_MAX_LEVEL.containsKey(this))
            callback.setReturnValue(Config.MIXIN_MAX_LEVEL.get(this));
    }

    @Inject(at = @At("HEAD"), method = "getMinEnchantability(I)I", cancellable = true)
    private void getMinEnchantability(int enchantmentLevel, CallbackInfoReturnable<Integer> callback) {
        if (Config.MIXIN_MIN_ENCHANTABILITY.containsKey(this))
            callback.setReturnValue(Config.MIXIN_MIN_ENCHANTABILITY.get(this).a + Config.MIXIN_MIN_ENCHANTABILITY.get(this).b * enchantmentLevel);
    }
    
    @Inject(at = @At("HEAD"), method = "getMaxEnchantability(I)I", cancellable = true)
    private void getMaxEnchantability(int enchantmentLevel, CallbackInfoReturnable<Integer> callback) {
        if (Config.MIXIN_MAX_ENCHANTABILITY.containsKey(this))
            callback.setReturnValue(Config.MIXIN_MAX_ENCHANTABILITY.get(this).a + Config.MIXIN_MAX_ENCHANTABILITY.get(this).b * enchantmentLevel + (Config.MIXIN_MAX_ENCHANTABILITY.get(this).c ? getMinEnchantability(enchantmentLevel) : 0));
    }

    @Inject(at = @At("HEAD"), method = "isTreasureEnchantment()Z", cancellable = true)
    private void isTreasureEnchantment(CallbackInfoReturnable<Boolean> callback) {
        if (Config.MIXIN_IS_TREASURE.containsKey(this))
            callback.setReturnValue(Config.MIXIN_IS_TREASURE.get(this));
    }

    @Inject(at = @At("HEAD"), method = "canVillagerTrade()Z", cancellable = true)
    private void canVillagerTrade(CallbackInfoReturnable<Boolean> callback) {
        if (Config.MIXIN_CAN_VILLAGER_TRADE.containsKey(this))
            callback.setReturnValue(Config.MIXIN_CAN_VILLAGER_TRADE.get(this));
    }

    @Inject(at = @At("HEAD"), method = "canGenerateInLoot()Z", cancellable = true)
    private void canGenerateInLoot(CallbackInfoReturnable<Boolean> callback) {
        if (Config.MIXIN_CAN_GENERATE_IN_LOOT.containsKey(this))
            callback.setReturnValue(Config.MIXIN_CAN_GENERATE_IN_LOOT.get(this));
    }
*/
}
