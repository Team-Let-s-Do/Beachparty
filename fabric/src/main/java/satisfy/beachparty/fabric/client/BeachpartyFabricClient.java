package satisfy.beachparty.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import satisfy.beachparty.client.BeachPartyClient;

public class BeachpartyFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BeachPartyClient.preInitClient();
        BeachPartyClient.initClient();
    }


}
