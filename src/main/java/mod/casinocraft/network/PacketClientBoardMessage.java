package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientBoardMessage {

    final int betLow;
    final int betHigh;
    final boolean transferIn;
    final boolean transferOut;
    final boolean isCreative;
    final int x;
    final int y;
    final int z;

    public PacketClientBoardMessage(int bet_low, int bet_high, boolean transfer_in, boolean transfer_out, boolean is_creative, BlockPos pos) {
        betLow = bet_low;
        betHigh = bet_high;
        transferIn = transfer_in;
        transferOut = transfer_out;
        isCreative = is_creative;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (PacketClientBoardMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.betLow);
        buf.writeInt(msg.betHigh);
        buf.writeBoolean(msg.transferIn);
        buf.writeBoolean(msg.transferOut);
        buf.writeBoolean(msg.isCreative);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static PacketClientBoardMessage decode (PacketBuffer buf) {
        int _betLow  = buf.readInt();
        int _betHigh = buf.readInt();
        boolean _transferIn  = buf.readBoolean();
        boolean _transferOut = buf.readBoolean();
        boolean _isCreative = buf.readBoolean();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new PacketClientBoardMessage(_betLow, _betHigh, _transferIn, _transferOut, _isCreative, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final PacketClientBoardMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            //IThreadListener thread = Minecraft.getInstance();
            // The value that was sent
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {
                te.bet_low  = message.betLow;
                te.bet_high = message.betHigh;
                te.transfer_in  = message.transferIn;
                te.transfer_out = message.transferOut;
                te.isCreative = message.isCreative;
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            // No response packet
            context.get().setPacketHandled(true);
        }
    }

}

