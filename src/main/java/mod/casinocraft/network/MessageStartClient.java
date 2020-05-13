package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.Charset;

public class MessageStartClient implements IMessage {

    static String name;
    static int length;
    static int seed;
    static int x;
    static int y;
    static int z;

    public MessageStartClient() {

    }

    public MessageStartClient(String name, int seed, BlockPos pos) {
        this.name = name;
        this.seed = seed;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.length = name.length();
    }

    public void toBytes (ByteBuf buf) {
        buf.writeInt(length);
        buf.writeCharSequence(name, Charset.defaultCharset());
        buf.writeInt(seed);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void fromBytes (ByteBuf buf) {
        try {
            length = buf.readInt();
            name = (String) buf.readCharSequence(length, Charset.defaultCharset());
            seed = buf.readInt();
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {

        }
    }

    public static class Handler implements IMessageHandler<MessageStartClient, IMessage> {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }

        @Override
        public IMessage onMessage (final MessageStartClient message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            IThreadListener thread = Minecraft.getMinecraft();
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getMinecraft().world.getTileEntity(pos);
            if(te == null) return null;
            thread.addScheduledTask(() -> {
                if(seed > -1) te.LOGIC.start(seed);
                te.LOGIC.addPlayer(name);
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            // No response packet
            return null;
        }
    }

}
