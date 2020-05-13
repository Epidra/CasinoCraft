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

public class MessageScoreClient implements IMessage {

    int points;
    String names;
    int length;
    int x;
    int y;
    int z;

    public MessageScoreClient() {

    }

    public MessageScoreClient(String scoreName, int scorePoints, BlockPos pos) {
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

    public static class Handler implements IMessageHandler<MessageScoreClient, IMessage> {
        @Override
        public IMessage onMessage (final MessageScoreClient message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            IThreadListener thread = Minecraft.getMinecraft();
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getMinecraft().world.getTileEntity(pos);
            if(te == null) return null;
            thread.addScheduledTask(() -> {
                te.LOGIC.addScore(message.names, message.points);
                te.LOGIC.resetPlayers();
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            // No response packet
            return null;
        }
    }

}
