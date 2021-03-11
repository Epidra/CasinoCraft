package mod.casinocraft.network;

import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageModuleServer {

    static int x;
    static int y;
    static int z;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageModuleServer(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (MessageModuleServer msg, PacketBuffer buf) {
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static MessageModuleServer decode (PacketBuffer buf) {
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageModuleServer(new BlockPos(_x, _y, _z));
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageModuleServer message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            context.get().enqueueWork(() ->{
                BlockArcade.setModuleState(context.get().getSender().world, pos);
            });
            CasinoPacketHandler.sendToChunk(new MessageModuleClient(pos), context.get().getSender().world.getChunkAt(pos));
            context.get().setPacketHandled(true);
        }
    }

}
