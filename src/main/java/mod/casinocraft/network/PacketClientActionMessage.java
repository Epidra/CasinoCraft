package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientActionMessage {

    static int action;
    static int x;
    static int y;
    static int z;

    public PacketClientActionMessage(int action, BlockPos pos) {
        this.action = action;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (PacketClientActionMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.action);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static PacketClientActionMessage decode (PacketBuffer buf) {
        int _action = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new PacketClientActionMessage(_action, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final PacketClientActionMessage message, Supplier<NetworkEvent.Context> context) {
            int action = message.action;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {
                te.LOGIC.actionTouch(action);
            });
            context.get().setPacketHandled(true);
        }
    }

}
