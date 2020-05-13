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

public class MessageScoreServer implements IMessage {

    int points;
    String names;
    int length;
    int x;
    int y;
    int z;

    public MessageScoreServer() {

    }

    public MessageScoreServer(String scoreName, int scorePoints, BlockPos pos) {
        points = scorePoints;
        names  = scoreName;
        length = scoreName.length();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void toBytes (ByteBuf buf) {
        buf.writeInt(points);
        buf.writeInt(length);
        buf.writeCharSequence(names, Charset.defaultCharset());
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void fromBytes (ByteBuf buf) {
        try {
            points = buf.readInt();
            length = buf.readInt();
            names = (String) buf.readCharSequence(length, Charset.defaultCharset());
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {

        }
    }

    public static class Handler implements IMessageHandler<MessageScoreServer, IMessage> {
        @Override
        public IMessage onMessage (final MessageScoreServer message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = context.getServerHandler().player;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) context.getServerHandler().player.world.getTileEntity(pos);
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                te.LOGIC.addScore(message.names, message.points);
                te.LOGIC.resetPlayers();
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            CasinoPacketHandler.INSTANCE.sendToAllTracking(new MessageScoreClient(
                    message.names,
                    message.points,
                    pos),
                    new NetworkRegistry.TargetPoint(context.getServerHandler().player.dimension, message.x, message.y, message.z, 64));
            // No response packet
            return null;
        }
    }

}
