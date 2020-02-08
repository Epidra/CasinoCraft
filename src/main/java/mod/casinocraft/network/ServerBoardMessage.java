package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerBoardMessage {

    static int betLow;
    static int betHigh;
    static boolean transferIn;
    static boolean transferOut;
    static boolean isCreative;
    static int x;
    static int y;
    static int z;

    public ServerBoardMessage(int bet_low, int bet_high, boolean transfer_in, boolean transfer_out, boolean is_creative, BlockPos pos) {
        betLow = bet_low;
        betHigh = bet_high;
        transferIn = transfer_in;
        transferOut = transfer_out;
        isCreative = is_creative;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (ServerBoardMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.betLow);
        buf.writeInt(msg.betHigh);
        buf.writeBoolean(msg.transferIn);
        buf.writeBoolean(msg.transferOut);
        buf.writeBoolean(msg.isCreative);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static ServerBoardMessage decode (PacketBuffer buf) {
        int _betLow  = buf.readInt();
        int _betHigh = buf.readInt();
        boolean _transferIn  = buf.readBoolean();
        boolean _transferOut = buf.readBoolean();
        boolean _isCreative = buf.readBoolean();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new ServerBoardMessage(_betLow, _betHigh, _transferIn, _transferOut, _isCreative, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final ServerBoardMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            //EntityPlayerMP serverPlayer = context.get().getSender();
            // The value that was sent

            context.get().enqueueWork(() ->{
                ServerPlayerEntity serverPlayer = context.get().getSender();
                BlockPos pos = new BlockPos(message.x, message.y, message.z);
                TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);
                te.bet_low  = message.betLow;
                te.bet_high = message.betHigh;
                te.transfer_in  = message.transferIn;
                te.transfer_out = message.transferOut;
                te.isCreative = message.isCreative;
            });

            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            //TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);
            //ontext.get().getSender().getServerWorld().addScheduledTask(() -> {
            //   te.bet_low  = message.betLow;
            //   te.bet_high = message.betHigh;
            //   te.transfer_in  = message.transferIn;
            //   te.transfer_out = message.transferOut;
            //   te.isCreative = message.isCreative;
            //);
            //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            CasinoPacketHandler.sendToAll(new PacketClientBoardMessage(message.betLow, message.betHigh, message.transferIn, message.transferOut, message.isCreative, pos));
            // No response packet
            context.get().setPacketHandled(true);
        }
    }

}
