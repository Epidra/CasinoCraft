package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.util.InventoryUtil;
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

import java.util.function.Predicate;

public class MessagePlayerServer implements IMessage {

    static ItemStack stack;
    static int amount;

    public MessagePlayerServer() {

    }

    public MessagePlayerServer(ItemStack itemStack, int amount) {
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

    public static class Handler implements IMessageHandler<MessagePlayerServer, IMessage> {
        @Override
        public IMessage onMessage (final MessagePlayerServer message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = context.getServerHandler().player;
            // The value that was sent
            int amount = message.amount;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if(amount < 0){
                    //InventoryUtil.decreaseInventory(serverPlayer.inventory, message.stack, -amount);
                    serverPlayer.inventory.clearMatchingItems(message.stack.getItem(), message.stack.getMetadata(), -amount, null);
                } else {
                    serverPlayer.inventory.addItemStackToInventory(new ItemStack(message.stack.getItem(), amount, message.stack.getMetadata()));
                }
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            //CasinoPacketHandler.INSTANCE.sendTo(new MessagePlayerClient(
            //        message.stack,
            //        amount),
            //        context.getServerHandler().player);
            // No response packet
            return null;
        }
    }

}
