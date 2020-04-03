package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientTurnstateMessage {

    static int state;
    static int x;
    static int y;
    static int z;

    public PacketClientTurnstateMessage(int state, BlockPos pos) {
        this.state = state;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (PacketClientTurnstateMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.state);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static PacketClientTurnstateMessage decode (PacketBuffer buf) {
        int _state = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new PacketClientTurnstateMessage(_state, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final PacketClientTurnstateMessage message, Supplier<NetworkEvent.Context> context) {
            int state = message.state;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {
                te.LOGIC.turnstate = state;
                if(state >= 5){
                    te.resetPlayer();
                }
            });
            context.get().setPacketHandled(true);
        }
    }

}
