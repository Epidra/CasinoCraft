package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageBlockServer {

    static ItemStack stack0;
    static ItemStack stack1;
    static ItemStack stack4;
    static int amount;
    static int x;
    static int y;
    static int z;

    public MessageBlockServer(ItemStack stack0, ItemStack stack1, ItemStack stack4, int storage, BlockPos pos) {
        this.stack0 = stack0.copy();
        this.stack1 = stack1.copy();
        this.stack4 = stack4.copy();
        this.amount = storage;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (MessageBlockServer msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack0);
        buf.writeItemStack(msg.stack1);
        buf.writeItemStack(msg.stack4);
        buf.writeInt(msg.amount);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageBlockServer decode (PacketBuffer buf) {
        ItemStack _stack0 = buf.readItemStack();
        ItemStack _stack1 = buf.readItemStack();
        ItemStack _stack4 = buf.readItemStack();
        int _amount = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new MessageBlockServer(_stack0, _stack1, _stack4, _amount, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final MessageBlockServer message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            context.get().enqueueWork(() ->{
                int amount = message.amount;
                TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);
                if(amount <= 0) {
                    te.bet_storage = 0;
                    te.setToken(new ItemStack(Blocks.AIR));
                } else {
                    te.inventory.set(0, message.stack0);
                    te.inventory.set(1, message.stack1);
                    te.inventory.set(4, message.stack4);
                    te.bet_storage = amount;
                }
            });
            CasinoPacketHandler.sendToChunk(new MessageBlockClient(
                    message.stack0,
                    message.stack1,
                    message.stack4,
                    amount,
                    new BlockPos(message.x, message.y, message.z)),
                    context.get().getSender().world.getChunkAt(pos));
            context.get().setPacketHandled(true);
        }
    }

}
