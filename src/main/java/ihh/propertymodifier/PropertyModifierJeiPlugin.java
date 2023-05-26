package ihh.propertymodifier;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

@JeiPlugin
public final class PropertyModifierJeiPlugin implements IModPlugin {
    private static final ResourceLocation UID = new ResourceLocation(PropertyModifier.MOD_ID, PropertyModifier.MOD_ID);

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    /**
     * {@see https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/integration/jei/QuarkJeiPlugin.java#L321-L337}
     */
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IVanillaRecipeFactory factory = registration.getVanillaRecipeFactory();
        for (Item item : Config.REPAIR_MATERIALS.keySet()) {
            ItemStack left = new ItemStack(item);
            ItemStack out = left.copy();
            int max = item.getMaxDamage(left);
            left.setDamageValue(max - 1);
            out.setDamageValue(max - max / 4);
            for (ItemStack repair : Config.REPAIR_MATERIALS.get(item).get().getItems()) {
                IJeiAnvilRecipe toolRepair = factory.createAnvilRecipe(left, Collections.singletonList(repair), Collections.singletonList(out));
                registration.addRecipes(RecipeTypes.ANVIL, List.of(toolRepair));
            }
        }
    }

    /**
     * {@see https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/integration/jei/QuarkJeiPlugin.java#L295-L319}
     */
    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
        IRecipeManager manager = jeiRuntime.getRecipeManager();
        manager.hideRecipes(RecipeTypes.ANVIL, manager.createRecipeLookup(RecipeTypes.ANVIL).get().filter(e -> {
            ItemStack left = e.getLeftInputs().stream()
                    .filter(s -> Config.REPAIR_MATERIALS.containsKey(s.getItem()))
                    .findFirst()
                    .orElse(null);
            if (left != null) {
                for (ItemStack right : e.getRightInputs()) {
                    if (!(left.getItem().isValidRepairItem(left, right) || right.is(Items.ENCHANTED_BOOK) || right.is(left.getItem()))) return true;
                }
            }
            return false;
        }).toList());
    }
}
