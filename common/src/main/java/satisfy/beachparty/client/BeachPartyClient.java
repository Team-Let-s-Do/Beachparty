package satisfy.beachparty.client;


import de.cristelknight.doapi.terraform.sign.TerraformSignHelper;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.player.Player;
import satisfy.beachparty.client.gui.MiniFridgeGui;
import satisfy.beachparty.client.gui.TikiBarGui;
import satisfy.beachparty.networking.BeachpartyMessages;
import satisfy.beachparty.registry.*;
import satisfy.beachparty.util.BeachpartyUtil;

@Environment(EnvType.CLIENT)
public class BeachPartyClient {


    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.TABLE.get(), ObjectRegistry.CHAIR.get(),
                ObjectRegistry.TIKI_CHAIR.get(), ObjectRegistry.PALM_TRAPDOOR.get(), ObjectRegistry.PALM_DOOR.get(),
                ObjectRegistry.PALM_TORCH.get(), ObjectRegistry.PALM_WALL_TORCH.get(), ObjectRegistry.PALM_TALL_TORCH.get(),
                ObjectRegistry.MELON_COCKTAIL.get(), ObjectRegistry.COCONUT_COCKTAIL.get(), ObjectRegistry.HONEY_COCKTAIL.get(),
                ObjectRegistry.SWEETBERRIES_COCKTAIL.get(), ObjectRegistry.PUMPKIN_COCKTAIL.get(), ObjectRegistry.COCOA_COCKTAIL.get(),
                ObjectRegistry.SANDCASTLE.get(), ObjectRegistry.MESSAGE_IN_A_BOTTLE.get(), ObjectRegistry.BEACH_TOWEL.get(),
                ObjectRegistry.DECK_CHAIR.get(), ObjectRegistry.PALM_SAPLING.get(), ObjectRegistry.HAMMOCK.get(),
                ObjectRegistry.SEASHELL_BLOCK.get()
        );

        MenuRegistry.registerScreenFactory(ScreenHandlerTypesRegistry.TIKI_BAR_GUI_HANDLER.get(), TikiBarGui::new);
        MenuRegistry.registerScreenFactory(ScreenHandlerTypesRegistry.MINI_FRIDGE_GUI_HANDLER.get(), MiniFridgeGui::new);


        BeachpartyMessages.registerS2CPackets();
        initColorItems();
    }

    public static void preInitClient(){
        TerraformSignHelper.regsterSignSprite(BoatsAndSignsRegistry.PALM_SIGN_TEXTURE);
        TerraformSignHelper.regsterSignSprite(BoatsAndSignsRegistry.PALM_HANGING_SIGN_TEXTURE);

        registerEntityEntityRenderers();
        registerEntityModelLayers();
    }

    private static void initColorItems() {
        BeachpartyUtil.registerColorArmor(ObjectRegistry.TRUNKS.get(), 16715535);
        BeachpartyUtil.registerColorArmor(ObjectRegistry.BIKINI.get(), 987135);
        BeachpartyUtil.registerColorArmor(ObjectRegistry.CROCS.get(), 1048335);
        BeachpartyUtil.registerColorArmor(ObjectRegistry.POOL_NOODLE.get(), 1017855);
    }

    public static void registerEntityEntityRenderers(){
        EntityRendererRegistry.register(EntityRegistry.COCONUT, ThrownItemRenderer::new);
    }

    public static void registerEntityModelLayers(){
        ArmorRegistry.registerCustomArmorLayers();
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}