package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientBlockMessage {

    static ItemStack stack0;
    static ItemStack stack1;
    static ItemStack stack4;
    static int amount;
    static int x;
    static int y;
    static int z;

    public PacketClientBlockMessage(ItemStack stack0, ItemStack stack1, ItemStack stack4, int storage, BlockPos pos) {
        this.stack0 = stack0.copy();
        this.stack1 = stack1.copy();
        this.stack4 = stack4.copy();
        this.amount = storage;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (PacketClientBlockMessage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack0);
        buf.writeItemStack(msg.stack1);
        buf.writeItemStack(msg.stack4);
        buf.writeInt(msg.amount);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static PacketClientBlockMessage decode (PacketBuffer buf) {
        ItemStack _stack0 = buf.readItemStack();
        ItemStack _stack1 = buf.readItemStack();
        ItemStack _stack4 = buf.readItemStack();
        int _amount = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new PacketClientBlockMessage(_stack0, _stack1, _stack4, _amount, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
        public static void handle (final PacketClientBlockMessage message, Supplier<NetworkEvent.Context> context) {

            // This is the player the packet was sent to the server from
            //ThreadListener thread = Minecraft.getInstance();
            // The value that was sent
            int amount = message.amount;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            //TileEntityBoard te = (TileEntityBoard) context.getSender().world.
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {
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
            //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            // No response packet
            context.get().setPacketHandled(true);
        }
    }

}
