package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageScoreClient {

    static int points;
    static String names;
    static int x;
    static int y;
    static int z;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageScoreClient(String scoreName, int scorePoints, BlockPos pos) {
        points = scorePoints;
        names  = scoreName;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageScoreClient msg, PacketBuffer buf) {
        buf.writeInt(msg.points);
        buf.writeString(msg.names, 24);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageScoreClient decode (PacketBuffer buf) {
        int _points = buf.readInt();
        String _names = buf.readString(24);
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageScoreClient(_names, _points, new BlockPos(_x, _y, _z));
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageScoreClient message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityMachine te = (TileEntityMachine) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() ->{
                te.LOGIC.addScore(message.names, message.points);
                te.LOGIC.resetPlayers();
            });
            context.get().setPacketHandled(true);
        }
    }

}
