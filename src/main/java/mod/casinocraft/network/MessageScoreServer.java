package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.nio.charset.Charset;
import java.util.function.Supplier;

public class MessageScoreServer {

    static int points;
    static String names;
    static int x;
    static int y;
    static int z;

    public MessageScoreServer(String scoreName, int scorePoints, BlockPos pos) {
        points = scorePoints;
        names  = scoreName;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (MessageScoreServer msg, PacketBuffer buf) {
        buf.writeInt(msg.points);
        buf.writeString(msg.names, 24);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageScoreServer decode (PacketBuffer buf) {
        int _points = buf.readInt();
        String _names = buf.readString(24);
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageScoreServer(_names, _points, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final MessageScoreServer message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);
            context.get().enqueueWork(() ->{
                te.LOGIC.addScore(message.names, message.points);
                te.LOGIC.resetPlayers();
            });
            CasinoPacketHandler.sendToChunk(new MessageScoreClient(message.names, message.points, te.getPos()), context.get().getSender().world.getChunkAt(pos));
            context.get().setPacketHandled(true);
        }
    }

}
