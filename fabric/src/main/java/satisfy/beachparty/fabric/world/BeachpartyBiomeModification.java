package satisfy.beachparty.fabric.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import satisfy.beachparty.BeachpartyIdentifier;
import satisfy.beachparty.world.PlacedFeatures;

import java.util.function.Predicate;


public class BeachpartyBiomeModification {
    public static void init() {
        BiomeModification world = BiomeModifications.create(new BeachpartyIdentifier("world_features"));
        Predicate<BiomeSelectionContext> beachBiomes = getBeachpartySelector();
        world.add(ModificationPhase.ADDITIONS, beachBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.PALM_TREE_KEY));
        world.add(ModificationPhase.ADDITIONS, beachBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SANDWAVES_KEY));
        world.add(ModificationPhase.ADDITIONS, beachBiomes, ctx -> ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SEASHELLS_KEY));

    }

    private static Predicate<BiomeSelectionContext> getBeachpartySelector() {
        return BiomeSelectors.tag(TagKey.create(Registries.BIOME, new BeachpartyIdentifier("beach")));
    }



}
