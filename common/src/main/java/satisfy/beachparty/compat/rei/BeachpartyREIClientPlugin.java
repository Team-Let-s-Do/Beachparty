package satisfy.beachparty.compat.rei;

import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import satisfy.beachparty.compat.rei.category.MiniFridgeCategory;
import satisfy.beachparty.compat.rei.category.TikiBarCategory;
import satisfy.beachparty.compat.rei.display.MiniFridgeDisplay;
import satisfy.beachparty.compat.rei.display.TikiBarDisplay;
import satisfy.beachparty.recipe.MiniFridgeRecipe;
import satisfy.beachparty.recipe.TikiBarRecipe;
import satisfy.beachparty.registry.ObjectRegistry;

public class BeachpartyREIClientPlugin {

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new MiniFridgeCategory());
        registry.add(new TikiBarCategory());

        registry.addWorkstations(MiniFridgeCategory.MINE_FRIDGE_DISPLAY, EntryStacks.of(ObjectRegistry.MINI_FRIDGE.get()));
        registry.addWorkstations(TikiBarCategory.TIKI_BAR_DISPLAY, EntryStacks.of(ObjectRegistry.TIKI_BAR.get()));
    }


    public static void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(MiniFridgeRecipe.class, MiniFridgeDisplay::new);
        registry.registerFiller(TikiBarRecipe.class, TikiBarDisplay::new);
    }
}
