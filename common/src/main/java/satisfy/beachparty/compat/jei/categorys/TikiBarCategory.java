package satisfy.beachparty.compat.jei.categorys;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import satisfy.beachparty.Beachparty;
import satisfy.beachparty.client.gui.TikiBarGui;
import satisfy.beachparty.compat.jei.BeachpartyJEIPlugin;
import satisfy.beachparty.recipe.TikiBarRecipe;
import satisfy.beachparty.registry.ObjectRegistry;

public class TikiBarCategory implements IRecipeCategory<TikiBarRecipe> {
    public static final RecipeType<TikiBarRecipe> TIKI_BAR = RecipeType.create(Beachparty.MOD_ID, "tiki_bar_mixing", TikiBarRecipe.class);
    public static final int WIDTH = 124;
    public static final int HEIGHT = 60;
    public static final int WIDTH_OF = 26;
    public static final int HEIGHT_OF = 13;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawableAnimated shake;
    private final Component localizedName;

    public TikiBarCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TikiBarGui.BG, WIDTH_OF, HEIGHT_OF, WIDTH, HEIGHT);
        this.arrow = helper.drawableBuilder(TikiBarGui.BG, 177, 26, 22, 10)
                .buildAnimated(50, IDrawableAnimated.StartDirection.LEFT, false);

        this.shake = helper.drawableBuilder(TikiBarGui.BG, 179, 2, 15, 20)
                .buildAnimated(50, IDrawableAnimated.StartDirection.BOTTOM, false);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ObjectRegistry.TIKI_BAR.get().asItem().getDefaultInstance());
        this.localizedName = Component.translatable("rei.beachparty.tiki_bar_category");
    }


    @Override
    public void draw(TikiBarRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, TikiBarGui.ARROW_X - WIDTH_OF, TikiBarGui.ARROW_Y - HEIGHT_OF);
        shake.draw(guiGraphics, TikiBarGui.SHAKE_X - WIDTH_OF, TikiBarGui.SHAKE_Y - HEIGHT_OF - 20);
    }

    @Override
    public RecipeType<TikiBarRecipe> getRecipeType() {
        return TIKI_BAR;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TikiBarRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int s = ingredients.size();

        if(s > 0) BeachpartyJEIPlugin.addSlot(builder, 55 - WIDTH_OF, 26 - HEIGHT_OF, ingredients.get(0));
        if(s > 1) BeachpartyJEIPlugin.addSlot(builder, 55 - WIDTH_OF, 44 - HEIGHT_OF, ingredients.get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 128 - WIDTH_OF,  35 - HEIGHT_OF).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }
}
