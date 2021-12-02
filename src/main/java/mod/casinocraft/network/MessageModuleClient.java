package mod.casinocraft.network;

import mod.casinocraft.block.BlockArcade;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageModuleClient {

    static BlockPos pos;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageModuleClient(BlockPos pos) {
        this.pos = pos;
    }





    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageModuleClient msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static MessageModuleClient decode (FriendlyByteBuf buf) {
        BlockPos _pos = buf.readBlockPos();
        return new MessageModuleClient(_pos);
    }





    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageModuleClient message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                BlockArcade.setModuleState(Minecraft.getInstance().player.level, message.pos);
            });
            context.get().setPacketHandled(true);
        }
    }



}
