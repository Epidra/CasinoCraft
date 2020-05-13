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

public class MessageBoardClient implements IMessage {

    int betLow;
    int betHigh;
    boolean transferIn;
    boolean transferOut;
    boolean isCreative;
    int x;
    int y;
    int z;

    public MessageBoardClient() {

    }

    public MessageBoardClient(int bet_low, int bet_high, boolean transfer_in, boolean transfer_out, boolean is_creative, BlockPos pos) {
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

    public static class Handler implements IMessageHandler<MessageBoardClient, IMessage> {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }

        @Override
        public IMessage onMessage (final MessageBoardClient message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            IThreadListener thread = Minecraft.getMinecraft();
            // The value that was sent
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getMinecraft().world.getTileEntity(pos);
            if(te == null) return null;
            thread.addScheduledTask(() -> {
                te.bet_low  = message.betLow;
                te.bet_high = message.betHigh;
                te.transfer_in  = message.transferIn;
                te.transfer_out = message.transferOut;
                te.isCreative = message.isCreative;
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            // No response packet
            return null;
        }
    }

}

