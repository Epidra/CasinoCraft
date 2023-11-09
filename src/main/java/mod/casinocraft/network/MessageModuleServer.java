package mod.casinocraft.network;

import mod.casinocraft.block.BlockArcade;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageModuleServer {

    static BlockPos pos;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageModuleServer(BlockPos pos) {
        this.pos = pos;
    }





    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageModuleServer msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static MessageModuleServer decode (FriendlyByteBuf buf) {
        BlockPos _pos = buf.readBlockPos();
        return new MessageModuleServer(_pos);
    }





    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageModuleServer message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() ->{
                BlockArcade.setModuleState(context.get().getSender().level(), message.pos);
            });
            CasinoPacketHandler.sendToChunk(new MessageModuleClient(message.pos), context.get().getSender().level().getChunkAt(message.pos));
            context.get().setPacketHandled(true);
        }
    }



}
