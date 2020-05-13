package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStateClient {

    static boolean system;
    static int state;
    static int x;
    static int y;
    static int z;

    public MessageStateClient(boolean system, int state, BlockPos pos) {
        this.system = system;
        this.state = state;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (MessageStateClient msg, PacketBuffer buf) {
        buf.writeBoolean(msg.system);
        buf.writeInt(msg.state);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageStateClient decode (PacketBuffer buf) {
        boolean _system = buf.readBoolean();
        int _state = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageStateClient(_system, _state, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final MessageStateClient message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {
                if(message.system){
                    if(message.state == -1){
                        te.LOGIC.pause = !te.LOGIC.pause;
                    } else if(message.state == -2){
                        te.LOGIC.resetPlayers();
                        te.LOGIC.turnstate = 0;
                    } else if(message.state == -3){
                        te.LOGIC.resetPlayers();
                    } else if(message.state >= 10){
                        te.LOGIC.reward[message.state - 10] = 0;
                    }else {
                        te.LOGIC.turnstate = state;
                    }
                } else {
                    te.LOGIC.command(message.state);
                }
            });
            context.get().setPacketHandled(true);
        }
    }

}
