package satisfy.beachparty.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import satisfy.beachparty.client.BeachPartyClient;
import satisfy.beachparty.client.model.BeachHatModel;
import satisfy.beachparty.client.model.RubberRingAxolotlModel;
import satisfy.beachparty.client.model.RubberRingModel;
import satisfy.beachparty.client.model.RubberRingPelicanModel;
import satisfy.beachparty.config.BeachpartyConfig;
import satisfy.beachparty.item.IBeachpartyArmorSet;

import java.util.List;
import java.util.Map;

public class ArmorRegistry {
    public static void registerCustomArmorLayers(){
        EntityModelLayerRegistry.register(BeachHatModel.LAYER_LOCATION, BeachHatModel::getTexturedModelData);
        EntityModelLayerRegistry.register(RubberRingPelicanModel.LAYER_LOCATION, RubberRingPelicanModel::getTexturedModelData);
        EntityModelLayerRegistry.register(RubberRingAxolotlModel.LAYER_LOCATION, RubberRingAxolotlModel::getTexturedModelData);
        EntityModelLayerRegistry.register(RubberRingModel.LAYER_LOCATION, RubberRingModel::getTexturedModelData);
    }


    public static  <T extends LivingEntity> void registerHatModels(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.BEACH_HAT.get(), new BeachHatModel<>(modelLoader.bakeLayer(BeachHatModel.LAYER_LOCATION)));
        models.put(ObjectRegistry.RUBBER_RING_PELICAN.get(), new RubberRingPelicanModel<>(modelLoader.bakeLayer(RubberRingPelicanModel.LAYER_LOCATION)));
        models.put(ObjectRegistry.RUBBER_RING_AXOLOTL.get(), new RubberRingAxolotlModel<>(modelLoader.bakeLayer(RubberRingAxolotlModel.LAYER_LOCATION)));
        models.put(ObjectRegistry.RUBBER_RING_BLUE.get(), new RubberRingModel<>(modelLoader.bakeLayer(RubberRingModel.LAYER_LOCATION)));
        models.put(ObjectRegistry.RUBBER_RING_PINK.get(), new RubberRingModel<>(modelLoader.bakeLayer(RubberRingModel.LAYER_LOCATION)));
        models.put(ObjectRegistry.RUBBER_RING_STRIPPED.get(), new RubberRingModel<>(modelLoader.bakeLayer(RubberRingModel.LAYER_LOCATION)));
    }

    public static void appendTooltip(List<Component> tooltip) {
        BeachpartyConfig config = BeachpartyConfig.getActiveInstance();

        if (!config.enableBeachSetBonus()) {
            return;
        }
        Player player = BeachPartyClient.getClientPlayer();
        if (player == null) {
            return;
        }
        tooltip.add(Component.translatable(  "tooltip.beachparty.swimwearline1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable(  "tooltip.beachparty.swimwearline2").withStyle(ChatFormatting.BLUE));

        boolean helmet = IBeachpartyArmorSet.hasSwimearHelmet(player);
        boolean breastplate = IBeachpartyArmorSet.hasSwimwearBreastplate(player);
        boolean leggings = IBeachpartyArmorSet.hasSwimearLeggings(player);
        boolean boots = IBeachpartyArmorSet.hasSwimearBoots(player);

        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.translatable("tooltip.beachparty.swimwear_set").withStyle(ChatFormatting.DARK_GREEN));
        tooltip.add(helmet ? Component.translatable("tooltip.beachparty.swimwearhelmet").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.beachparty.swimwearhelmet").withStyle(ChatFormatting.GRAY));
        tooltip.add(breastplate ? Component.translatable("tooltip.beachparty.swimwearbreastplate").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.beachparty.swimwearbreastplate").withStyle(ChatFormatting.GRAY));
        tooltip.add(leggings ? Component.translatable("tooltip.beachparty.swimwearleggings").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.beachparty.swimwearleggings").withStyle(ChatFormatting.GRAY));
        tooltip.add(boots ? Component.translatable("tooltip.beachparty.swimwearboots").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.beachparty.swimwearboots").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.translatable("tooltip.beachparty.swimwear_seteffect").withStyle(ChatFormatting.GRAY));
        tooltip.add(helmet && breastplate && leggings && boots ? Component.translatable("tooltip.beachparty.swimwear_effect").withStyle(ChatFormatting.DARK_GREEN) : Component.translatable("tooltip.beachparty.swimwear_effect").withStyle(ChatFormatting.GRAY));
    }

}
