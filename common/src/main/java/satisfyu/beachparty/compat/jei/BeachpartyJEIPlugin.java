package satisfyu.beachparty.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import satisfyu.beachparty.BeachpartyIdentifier;
import satisfyu.beachparty.client.gui.handler.MiniFridgeGuiHandler;
import satisfyu.beachparty.client.gui.handler.TikiBarGuiHandler;
import satisfyu.beachparty.compat.jei.categorys.MiniFridgeCategory;
import satisfyu.beachparty.compat.jei.categorys.TikiBarCategory;
import satisfyu.beachparty.recipe.MiniFridgeRecipe;
import satisfyu.beachparty.recipe.TikiBarRecipe;
import satisfyu.beachparty.registry.ObjectRegistry;
import satisfyu.beachparty.registry.RecipeRegistry;
import satisfyu.beachparty.registry.ScreenHandlerTypesRegistry;

import java.util.List;
import java.util.Objects;


@JeiPlugin
public class BeachpartyJEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MiniFridgeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new TikiBarCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<MiniFridgeRecipe> fridgeRecipes = rm.getAllRecipesFor(RecipeRegistry.MINI_FRIDGE_RECIPE_TYPE.get());
        registration.addRecipes(MiniFridgeCategory.MINI_FRIDGE, fridgeRecipes);

        List<TikiBarRecipe> tikiBarRecipes = rm.getAllRecipesFor(RecipeRegistry.TIKI_BAR_RECIPE_TYPE.get());
        registration.addRecipes(TikiBarCategory.TIKI_BAR, tikiBarRecipes);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new BeachpartyIdentifier("jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ObjectRegistry.MINI_FRIDGE.get().asItem().getDefaultInstance(), MiniFridgeCategory.MINI_FRIDGE);
        registration.addRecipeCatalyst(ObjectRegistry.TIKI_BAR.get().asItem().getDefaultInstance(), TikiBarCategory.TIKI_BAR);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(MiniFridgeGuiHandler.class, ScreenHandlerTypesRegistry.MINI_FRIDGE_GUI_HANDLER.get(), MiniFridgeCategory.MINI_FRIDGE, 1, 2, 3, 36);
        registration.addRecipeTransferHandler(TikiBarGuiHandler.class, ScreenHandlerTypesRegistry.TIKI_BAR_GUI_HANDLER.get(), TikiBarCategory.TIKI_BAR, 1, 2, 3, 36);
    }

    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient){
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }
}
