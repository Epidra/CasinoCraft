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

public class MessageBoardServer implements IMessage {

    int betLow;
    int betHigh;
    boolean transferIn;
    boolean transferOut;
    boolean isCreative;
    int x;
    int y;
    int z;

    public MessageBoardServer() {

    }

    public MessageBoardServer(int bet_low, int bet_high, boolean transfer_in, boolean transfer_out, boolean is_creative, BlockPos pos) {
        betLow = bet_low;
        betHigh = bet_high;
        transferIn = transfer_in;
        transferOut = transfer_out;
        isCreative = is_creative;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void toBytes (ByteBuf buf) {
        buf.writeInt(betLow);
        buf.writeInt(betHigh);
        buf.writeBoolean(transferIn);
        buf.writeBoolean(transferOut);
        buf.writeBoolean(isCreative);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void fromBytes (ByteBuf buf) {
        try {
            betLow = buf.readInt();
            betHigh = buf.readInt();
            transferIn = buf.readBoolean();
            transferOut = buf.readBoolean();
            isCreative = buf.readBoolean();
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {

        }
    }

    public static class Handler implements IMessageHandler<MessageBoardServer, IMessage> {
        @Override
        public IMessage onMessage (final MessageBoardServer message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = context.getServerHandler().player;
            // The value that was sent
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) context.getServerHandler().player.world.getTileEntity(pos);
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                te.bet_low  = message.betLow;
                te.bet_high = message.betHigh;
                te.transfer_in  = message.transferIn;
                te.transfer_out = message.transferOut;
                te.isCreative = message.isCreative;
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            CasinoPacketHandler.INSTANCE.sendToAllTracking(new MessageBoardClient(
                    message.betLow,
                    message.betHigh,
                    message.transferIn,
                    message.transferOut,
                    message.isCreative,
                    pos),
                    new NetworkRegistry.TargetPoint(context.getServerHandler().player.dimension, message.x, message.y, message.z, 64));
            // No response packet
            return null;
        }
    }

}
