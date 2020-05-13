package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStartClient {

    static String name;
    static int seed;
    static int x;
    static int y;
    static int z;

    public MessageStartClient(String name, int seed, BlockPos pos) {
        this.name = name;
        this.seed = seed;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (MessageStartClient msg, PacketBuffer buf) {
        buf.writeString(msg.name);
        buf.writeInt(msg.seed);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageStartClient decode (PacketBuffer buf) {
        String _name = buf.readString(24);
        int _seed = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageStartClient(_name, _seed, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final MessageStartClient message, Supplier<NetworkEvent.Context> context) {
            int seed = message.seed;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {
                if(seed > -1) te.LOGIC.start(seed);
                te.LOGIC.addPlayer(name);
            });
            context.get().setPacketHandled(true);
        }
    }

}
