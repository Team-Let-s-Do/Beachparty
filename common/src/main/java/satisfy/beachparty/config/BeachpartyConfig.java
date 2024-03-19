package satisfy.beachparty.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.cristelknight.doapi.config.jankson.config.CommentedConfig;

import java.util.HashMap;

public record BeachpartyConfig(boolean enableBeachSetBonus) implements CommentedConfig<BeachpartyConfig> {
    private static BeachpartyConfig INSTANCE;

    public static final Codec<BeachpartyConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("enable_beach_armor_set_bonus").orElse(true).forGetter(BeachpartyConfig::enableBeachSetBonus)
            ).apply(instance, BeachpartyConfig::new));

    public static BeachpartyConfig getActiveInstance() {
        if (INSTANCE == null) {
            INSTANCE = loadConfig();
        }
        return INSTANCE;
    }

    private static BeachpartyConfig loadConfig() {
        return new BeachpartyConfig(true);
    }

    @Override
    public HashMap<String, String> getComments() {
        HashMap<String, String> comments = new HashMap<>();
        comments.put("enable_beach_armor_set_bonus", "Whether the beach armor should give a set bonus");
        return comments;
    }
    @Override
    public String getHeader() {
        return """
                Beachparty Config
                               
                ===========
                Discord: https://discord.gg/Vqu6wYZwdZ
                Modrinth: https://modrinth.com/mod/lets-do-beachparty
                CurseForge: https://www.curseforge.com/minecraft/mc-mods/lets-do-beachparty
                """;
    }

    @Override
    public String getSubPath() {
        return "beachparty/config";
    }

    @Override
    public BeachpartyConfig getInstance() {
        return getActiveInstance();
    }

    @Override
    public BeachpartyConfig getDefault() {
        return new BeachpartyConfig(true);
    }

    @Override
    public Codec<BeachpartyConfig> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isSorted() {
        return false;
    }

    @Override
    public void setInstance(BeachpartyConfig instance) {
        INSTANCE = instance;
    }
}
