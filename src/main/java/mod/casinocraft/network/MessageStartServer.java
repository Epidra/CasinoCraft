package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStartServer {

    static String name;
    static int seed;
    static int x;
    static int y;
    static int z;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageStartServer(String name, int seed, BlockPos pos) {
        this.name = name;
        this.seed = seed;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageStartServer msg, PacketBuffer buf) {
        buf.writeString(msg.name);
        buf.writeInt(msg.seed);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageStartServer decode (PacketBuffer buf) {
        String _name = buf.readString(24);
        int _seed = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageStartServer(_name, _seed, new BlockPos(_x, _y, _z));
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageStartServer message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            context.get().enqueueWork(() ->{
                int seed = message.seed;
                TileEntityMachine te = (TileEntityMachine) context.get().getSender().world.getTileEntity(pos);
                te.LOGIC.addPlayer(message.name);
                if(seed > -1) te.LOGIC.start(seed);
            });
            CasinoPacketHandler.sendToChunk(new MessageStartClient(
                    message.name,
                    message.seed,
                    new BlockPos(message.x, message.y, message.z)),
                    context.get().getSender().world.getChunkAt(pos));
            context.get().setPacketHandled(true);
        }
    }

}
