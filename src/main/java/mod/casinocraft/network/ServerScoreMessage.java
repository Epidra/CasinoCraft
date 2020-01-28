package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.nio.charset.Charset;
import java.util.function.Supplier;

public class ServerScoreMessage {

    static ItemStack stack;
    static int[] points = new int[18];
    static int[] length = new int[18];
    static String[] names = new String[18];
    static int x;
    static int y;
    static int z;

    public ServerScoreMessage(Item item, String[] scoreName, int[] scorePoints, BlockPos pos) {
        this.stack = new ItemStack(item);
        for(int i = 0; i < 18; i++) {
            points[i] = scorePoints[i];
            length[i] = scoreName[i].length();
            names[i] = scoreName[i];
        }
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (ServerScoreMessage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack);
        for(int i = 0; i < 18; i++) {
            buf.writeInt(msg.points[i]);
            buf.writeInt(msg.length[i]);
            buf.writeCharSequence(msg.names[i], Charset.defaultCharset());
        }
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static ServerScoreMessage decode (PacketBuffer buf) {
        int[] _points = new int[18];
        int[] _length = new int[18];
        String[] _names = new String[18];
        ItemStack _stack = buf.readItemStack();
        for(int i = 0; i < 18; i++) {
            _points[i] = buf.readInt();
            _length[i] = buf.readInt();
            _names[i] = (String) buf.readCharSequence(_length[i], Charset.defaultCharset());
        }
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new ServerScoreMessage(_stack.getItem(), _names, _points, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
        public static void handle (final ServerScoreMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            //EntityPlayerMP serverPlayer = context.get().getSender();
            // The value that was sent
            Item item = message.stack.getItem();
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);
            //context.get().getSender().getServerWorld().addScheduledTask(() -> {
            //    te.setScoreToken(item);
            //    for(int i = 0; i < 18; i++) {
            //        te.scorePoints[i] = message.points[i];
            //        te.scoreName[i] = message.names[i];
            //    }
            //});
            //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            CasinoPacketHandler.sendToAll(new PacketClientScoreMessage(item, message.names, message.points, te.getPos()));
            // No response packet
            context.get().setPacketHandled(true);
        }
    }

}
