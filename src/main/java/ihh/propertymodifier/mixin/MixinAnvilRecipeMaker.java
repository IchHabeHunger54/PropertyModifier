package ihh.propertymodifier.mixin;

import ihh.propertymodifier.Config;
import ihh.propertymodifier.PropertyModifier;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.plugins.vanilla.anvil.AnvilRecipeMaker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Pseudo
@Mixin(AnvilRecipeMaker.class)
public class MixinAnvilRecipeMaker {
    @Inject(at = @At("RETURN"), method = "getAnvilRecipes", cancellable = true, remap = false)
    private static void getAnvilRecipesMixin(IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager, CallbackInfoReturnable<List<IJeiAnvilRecipe>> callback) {
        List<IJeiAnvilRecipe> result = new ArrayList<>();
        for (IJeiAnvilRecipe recipe : callback.getReturnValue()) {
            for (ItemStack stack : recipe.getLeftInputs()) {
                if (Config.REPAIR_MATERIALS.containsKey(stack.getItem()) && recipe.getRightInputs().stream().map(ItemStack::getItem).noneMatch(e -> e == Items.ENCHANTED_BOOK)) {
                    result.add(vanillaRecipeFactory.createAnvilRecipe(recipe.getLeftInputs(), Arrays.stream(Config.REPAIR_MATERIALS.get(stack.getItem()).get().getItems()).toList(), recipe.getOutputs()));
                } else if (stack.is(Items.ELYTRA)) {
                    result.add(vanillaRecipeFactory.createAnvilRecipe(recipe.getLeftInputs(), Arrays.stream(Ingredient.of(PropertyModifier.ELYTRA_REPAIR_MATERIAL).getItems()).toList(), recipe.getOutputs()));
                } else if (stack.is(Items.SHIELD)) {
                    result.add(vanillaRecipeFactory.createAnvilRecipe(recipe.getLeftInputs(), Arrays.stream(Ingredient.of(PropertyModifier.SHIELD_REPAIR_MATERIAL).getItems()).toList(), recipe.getOutputs()));
                } else {
                    result.add(recipe);
                }
            }
        }
        callback.setReturnValue(result);
    }
}
