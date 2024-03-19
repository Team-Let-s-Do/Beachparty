package satisfy.beachparty.networking;

import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import satisfy.beachparty.BeachpartyIdentifier;
import satisfy.beachparty.networking.packet.MouseScrollC2SPacket;
import satisfy.beachparty.networking.packet.TuneRadioS2CPacket;
import satisfy.beachparty.networking.packet.TurnRadioS2CPacket;

public class BeachpartyMessages {
    public static final ResourceLocation MOUSE_SCROLL_C2S = new BeachpartyIdentifier("mouse_scroll");
    public static final ResourceLocation TURN_RADIO_S2C = new BeachpartyIdentifier("turn_radio");
    public static final ResourceLocation TUNE_RADIO_S2C = new BeachpartyIdentifier("tune_radio");

    public static void registerC2SPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, MOUSE_SCROLL_C2S, new MouseScrollC2SPacket());
    }

    public static void registerS2CPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, TURN_RADIO_S2C, new TurnRadioS2CPacket());
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, TUNE_RADIO_S2C, new TuneRadioS2CPacket());
    }
}
