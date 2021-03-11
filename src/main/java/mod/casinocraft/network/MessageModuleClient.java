package mod.casinocraft.network;

import mod.casinocraft.blocks.BlockArcade;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageModuleClient {

    static int x;
    static int y;
    static int z;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageModuleClient(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageModuleClient msg, PacketBuffer buf) {
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageModuleClient decode (PacketBuffer buf) {
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageModuleClient(new BlockPos(_x, _y, _z));
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageModuleClient message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            context.get().enqueueWork(() -> {
                BlockArcade.setModuleState(Minecraft.getInstance().player.world, pos);
            });
            context.get().setPacketHandled(true);
        }
    }

}
