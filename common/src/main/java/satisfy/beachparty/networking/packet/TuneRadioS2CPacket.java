package satisfy.beachparty.networking.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import satisfy.beachparty.util.RadioHelper;

public class TuneRadioS2CPacket implements NetworkManager.NetworkReceiver {

    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        BlockPos blockPos = buf.readBlockPos();
        int channel = buf.readInt();
        context.queue(() -> RadioHelper.tune(blockPos, channel));
    }
}
