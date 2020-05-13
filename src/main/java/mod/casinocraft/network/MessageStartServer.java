package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.Charset;

public class MessageStartServer implements IMessage {

    String name;
    int seed;
    int length;
    int x;
    int y;
    int z;

    public MessageStartServer() {

    }

    public MessageStartServer(String name, int seed, BlockPos pos) {
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

    public static class Handler implements IMessageHandler<MessageStartServer, IMessage> {
        @Override
        public IMessage onMessage (final MessageStartServer message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = context.getServerHandler().player;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) context.getServerHandler().player.world.getTileEntity(pos);
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                te.LOGIC.addPlayer(message.name);
                if(message.seed > -1) te.LOGIC.start(message.seed);
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            CasinoPacketHandler.INSTANCE.sendToAllTracking(new MessageStartClient(
                    message.name,
                    message.seed,
                    pos),
                    new NetworkRegistry.TargetPoint(context.getServerHandler().player.dimension, message.x, message.y, message.z, 64));
            // No response packet
            return null;
        }
    }

}
