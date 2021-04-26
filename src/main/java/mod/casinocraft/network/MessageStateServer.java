package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStateServer {

    static boolean system;
    static int state;
    static BlockPos pos;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageStateServer(boolean system, int state, BlockPos pos) {
        this.system = system;
        this.state = state;
        this.pos = pos;
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageStateServer msg, PacketBuffer buf) {
        buf.writeBoolean(msg.system);
        buf.writeInt(msg.state);
        buf.writeBlockPos(msg.pos);
    }

    public static MessageStateServer decode (PacketBuffer buf) {
        boolean _system = buf.readBoolean();
        int _state = buf.readInt();
        BlockPos _pos = buf.readBlockPos();
        return new MessageStateServer(_system, _state, _pos);
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageStateServer message, Supplier<NetworkEvent.Context> context) {
            TileEntityMachine te = (TileEntityMachine) context.get().getSender().level.getBlockEntity(message.pos);
            context.get().enqueueWork(() -> {
                if(message.system){
                    if(message.state == -1){
                        te.logic.pause = !te.logic.pause;
                    } else if(message.state == -2){
                        te.logic.resetPlayers();
                        te.logic.turnstate = 0;
                    } else if(message.state == -3) {
                        te.logic.resetPlayers();
                    } else if(message.state >= 10){
                        te.logic.reward[message.state - 10] = 0;
                    } else {
                        te.logic.turnstate = message.state;
                    }
                } else {
                    te.logic.command(message.state);
                }
            });
            CasinoPacketHandler.sendToChunk(new MessageStateClient(
                    message.system,
                    message.state,
                    message.pos),
                    context.get().getSender().level.getChunkAt(message.pos)
                    );
            context.get().setPacketHandled(true);
        }
    }

}
