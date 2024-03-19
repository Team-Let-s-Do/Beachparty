package satisfy.beachparty.networking.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import satisfy.beachparty.block.RadioBlock;
import satisfy.beachparty.util.RadioHelper;

public class TurnRadioS2CPacket implements NetworkManager.NetworkReceiver {

    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        BlockPos blockPos = buf.readBlockPos();
        int channel = buf.readInt();
        boolean on = buf.readBoolean();
        context.queue(() -> RadioHelper.setPlaying(blockPos, channel, on, on ? RadioBlock.DELAY : 0));
    }
}
