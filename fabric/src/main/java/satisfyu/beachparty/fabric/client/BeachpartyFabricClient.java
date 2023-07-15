package satisfyu.beachparty.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import satisfyu.beachparty.client.BeachPartyClient;

public class BeachpartyFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BeachPartyClient.preInitClient();
        BeachPartyClient.initClient();
    }


}
