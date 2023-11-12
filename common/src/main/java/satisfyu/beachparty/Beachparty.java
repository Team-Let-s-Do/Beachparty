package satisfyu.beachparty;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.beachparty.event.CommonEvents;
import satisfyu.beachparty.networking.BeachpartyMessages;
import satisfyu.beachparty.registry.*;

public class Beachparty {
    public static final String MOD_ID = "beachparty";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        ObjectRegistry.init();
        EntityRegistry.init();
        BlockEntityRegistry.init();
        TerraformRegistry.init();
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