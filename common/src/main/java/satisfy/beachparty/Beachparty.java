package satisfy.beachparty;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfy.beachparty.event.CommonEvents;
import satisfy.beachparty.networking.BeachpartyMessages;
import satisfy.beachparty.registry.*;

public class Beachparty {
    public static final String MOD_ID = "beachparty";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        ObjectRegistry.init();
        EntityRegistry.init();
        BlockEntityRegistry.init();
        BoatsAndSignsRegistry.init();
        RecipeRegistry.init();
        SoundEventRegistry.init();
        ScreenHandlerTypesRegistry.init();
        PlacerTypesRegistry.init();
        CommonEvents.init();
        TabRegistry.init();
        BeachpartyMessages.registerC2SPackets();
    }

    public static void commonSetup(){
        ObjectRegistry.commonInit();

        AxeItemHooks.addStrippable(ObjectRegistry.PALM_LOG.get(), ObjectRegistry.STRIPPED_PALM_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.PALM_WOOD.get(), ObjectRegistry.STRIPPED_PALM_WOOD.get());
    }


}