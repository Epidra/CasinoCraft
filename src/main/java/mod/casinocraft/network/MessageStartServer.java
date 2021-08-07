package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStartServer {

    static String name;
    static int seed;
    static BlockPos pos;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageStartServer(String name, int seed, BlockPos pos) {
        this.name = name;
        this.seed = seed;
        this.pos = pos;
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageStartServer msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.name);
        buf.writeInt(msg.seed);
        buf.writeBlockPos(msg.pos);
    }

    public static MessageStartServer decode (FriendlyByteBuf buf) {
        String _name = buf.readUtf(24);
        int _seed = buf.readInt();
        BlockPos _pos = buf.readBlockPos();
        return new MessageStartServer(_name, _seed, _pos);
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageStartServer message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() ->{
                BlockEntityMachine te = (BlockEntityMachine) context.get().getSender().level.getBlockEntity(message.pos);
                te.logic.addPlayer(message.name);
                if(message.seed > -1) te.logic.start(message.seed);
            });
            CasinoPacketHandler.sendToChunk(new MessageStartClient(
                    message.name,
                    message.seed,
                    message.pos),
                    context.get().getSender().level.getChunkAt(message.pos));
            context.get().setPacketHandled(true);
        }
    }

}
