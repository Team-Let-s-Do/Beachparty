package satisfy.beachparty.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import satisfy.beachparty.BeachpartyIdentifier;

public class BeachpartyTags {
    public static final TagKey<Biome> WARM_BIOME = TagKey.create(Registries.BIOME, new BeachpartyIdentifier("warm_biome"));
}