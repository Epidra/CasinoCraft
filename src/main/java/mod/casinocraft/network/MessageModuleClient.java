package mod.casinocraft.network;

import mod.casinocraft.blocks.BlockArcade;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageModuleClient {

    static BlockPos pos;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageModuleClient(BlockPos pos) {
        this.pos = pos;
    }





    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageModuleClient msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static MessageModuleClient decode (PacketBuffer buf) {
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
