package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageInventoryServer implements IMessage {

    ItemStack stack0;
    ItemStack stack1;
    ItemStack stack2;
    ItemStack stack3;
    ItemStack stack4;
    ItemStack stack5;
    int x;
    int y;
    int z;

    public MessageInventoryServer() {

    }

    public MessageInventoryServer(NonNullList<ItemStack> inventory, BlockPos pos) {
        this.stack0 = inventory.get(0);
        this.stack1 = inventory.get(1);
        this.stack2 = inventory.get(2);
        this.stack3 = inventory.get(3);
        this.stack4 = inventory.get(4);
        this.stack5 = inventory.get(5);
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void toBytes (ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack0);
        ByteBufUtils.writeItemStack(buf, stack1);
        ByteBufUtils.writeItemStack(buf, stack2);
        ByteBufUtils.writeItemStack(buf, stack3);
        ByteBufUtils.writeItemStack(buf, stack4);
        ByteBufUtils.writeItemStack(buf, stack5);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void fromBytes (ByteBuf buf) {
        try {
            stack0 = ByteBufUtils.readItemStack(buf);
            stack1 = ByteBufUtils.readItemStack(buf);
            stack2 = ByteBufUtils.readItemStack(buf);
            stack3 = ByteBufUtils.readItemStack(buf);
            stack4 = ByteBufUtils.readItemStack(buf);
            stack5 = ByteBufUtils.readItemStack(buf);
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {

        }
    }

    public static class Handler implements IMessageHandler<MessageInventoryServer, IMessage> {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }

        @Override
        public IMessage onMessage (final MessageInventoryServer message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = context.getServerHandler().player;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            NonNullList<ItemStack> inv = NonNullList.withSize(6, ItemStack.EMPTY);
            inv.set(0, message.stack0);
            inv.set(1, message.stack1);
            inv.set(2, message.stack2);
            inv.set(3, message.stack3);
            inv.set(4, message.stack4);
            inv.set(5, message.stack5);
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            CasinoPacketHandler.INSTANCE.sendToAllTracking(new MessageInventoryClient(
                    inv,
                    pos),
                    new NetworkRegistry.TargetPoint(context.getServerHandler().player.dimension, message.x, message.y, message.z, 64));
            // No response packet
            return null;
        }
    }

}
