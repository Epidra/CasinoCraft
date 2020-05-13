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

import java.util.function.Predicate;

public class MessagePlayerClient implements IMessage {

    static ItemStack stack;
    static int amount;

    public MessagePlayerClient() {

    }

    public MessagePlayerClient(ItemStack itemStack, int amount) {
        this.stack = itemStack.copy();
        this.amount = amount;
    }

    public void toBytes (ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(amount);
    }

    public void fromBytes (ByteBuf buf) {
        try {
            stack = ByteBufUtils.readItemStack(buf);
            amount = buf.readInt();
        } catch (Exception e) {

        }
    }

    public static class Handler implements IMessageHandler<MessagePlayerClient, IMessage> {
        @Override
        public IMessage onMessage (final MessagePlayerClient message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            IThreadListener thread = Minecraft.getMinecraft();
            // The value that was sent
            int amount = message.amount;
            Item item = message.stack.getItem();
            thread.addScheduledTask(() -> {
                if(amount < 0){
                    Minecraft.getMinecraft().player.inventory.clearMatchingItems(message.stack.getItem(), message.stack.getMetadata(), -amount, null);
                } else {
                    Minecraft.getMinecraft().player.inventory.addItemStackToInventory(new ItemStack(item, amount));
                }
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            // No response packet
            return null;
        }
    }

}
