package satisfy.beachparty.client.gui;

import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.config.cloth.CCUtil;
import de.cristelknight.doapi.config.cloth.LinkEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import satisfy.beachparty.Beachparty;
import satisfy.beachparty.config.BeachpartyConfig;

@SuppressWarnings("all")
public class ClothConfigScreen {

    private static Screen lastScreen;

    public static Screen create(Screen parent) {
        lastScreen = parent;
        BeachpartyConfig config = BeachpartyConfig.getActiveInstance().getConfig();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setDefaultBackgroundTexture(new ResourceLocation("textures/block/sand.png"))
                .setTitle(Component.translatable(Beachparty.MOD_ID + ".config.title").withStyle(ChatFormatting.BOLD));


        ConfigEntries entries = new ConfigEntries(builder.entryBuilder(), config, builder.getOrCreateCategory(CCUtil.categoryName("main", Beachparty.MOD_ID)));
        builder.setSavingRunnable(() -> {
            BeachpartyConfig.getActiveInstance().setInstance(entries.createConfig());
            BeachpartyConfig.getActiveInstance().getConfig(true, true);
        });
        return builder.build();
    }


    private static class ConfigEntries {
        private final ConfigEntryBuilder builder;
        private final ConfigCategory category;
        private final BooleanListEntry enableBeachSetBonus;


        public ConfigEntries(ConfigEntryBuilder builder, BeachpartyConfig config, ConfigCategory category) {
            this.builder = builder;
            this.category = category;

            SubCategoryBuilder Beach = new SubCategoryBuilder(Component.empty(), Component.translatable("config.beachparty.subCategory.beach"));
            enableBeachSetBonus = createBooleanField("enableBeachSetBonus", config.enableBeachSetBonus(), "Whether the beach armor should give a set bonus", Beach);


            category.addEntry(Beach.build());
            linkButtons(Beachparty.MOD_ID, category, builder, "https://discord.gg/Vqu6wYZwdZ", "https://www.curseforge.com/minecraft/mc-mods/lets-do-beachparty", lastScreen);
        }


        public BeachpartyConfig createConfig() {
            return new BeachpartyConfig(enableBeachSetBonus.getValue());
        }


        private BooleanListEntry createBooleanField(String key, boolean value, String tooltip, SubCategoryBuilder subCategoryBuilder) {
            BooleanListEntry entry = builder.startBooleanToggle(Component.translatable(Beachparty.MOD_ID + ".config." + key), value)
                    .setDefaultValue(() -> true)
                    .setTooltip(Component.literal(tooltip))
                    .build();
            subCategoryBuilder.add(entry);
            return entry;
        }


        public IntegerListEntry createIntField(String id, int value, int defaultValue, SubCategoryBuilder subCategoryBuilder, int min, int max){
            IntegerListEntry e = CCUtil.createIntField(Beachparty.MOD_ID, id, value, defaultValue, builder).setMaximum(max).setMinimum(min);

            if(subCategoryBuilder == null) category.addEntry(e);
            else subCategoryBuilder.add(e);

            return e;
        }
    }

    public static void linkButtons(String MOD_ID, ConfigCategory category, ConfigEntryBuilder builder, String dcLink, String cfLink, Screen lastScreen){
        if(lastScreen == null) lastScreen = Minecraft.getInstance().screen;

        TextListEntry tle = builder.startTextDescription(Component.literal(" ")).build();
        category.addEntry(tle);
        Screen finalLastScreen = lastScreen;
        category.addEntry(new LinkEntry(CCUtil.entryName(MOD_ID,"dc"), buttonWidget -> Minecraft.getInstance().setScreen(new ConfirmLinkScreen(confirmed -> {
            if (confirmed) {
                Util.getPlatform().openUri(dcLink);
            }
            Minecraft.getInstance().setScreen(create(finalLastScreen)); }, dcLink, true)), new DoApiRL("textures/gui/dc.png"), 3));
        category.addEntry(tle);
        category.addEntry(new LinkEntry(CCUtil.entryName(MOD_ID,"h"), buttonWidget -> Minecraft.getInstance().setScreen(new ConfirmLinkScreen(confirmed -> {
            if (confirmed) {
                Util.getPlatform().openUri(cfLink);
            }
            Minecraft.getInstance().setScreen(create(finalLastScreen)); }, cfLink, true)), new DoApiRL("textures/gui/cf.png"), 10));
    }
}
