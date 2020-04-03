package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientInventoryMessage {

    static ItemStack stack0;
    static ItemStack stack1;
    static ItemStack stack2;
    static ItemStack stack3;
    static ItemStack stack4;
    static ItemStack stack5;
    static int x;
    static int y;
    static int z;

    public PacketClientInventoryMessage(NonNullList<ItemStack> inventory, BlockPos pos) {
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

    public static void encode (PacketClientInventoryMessage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack0);
        buf.writeItemStack(msg.stack1);
        buf.writeItemStack(msg.stack2);
        buf.writeItemStack(msg.stack3);
        buf.writeItemStack(msg.stack4);
        buf.writeItemStack(msg.stack5);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static PacketClientInventoryMessage decode (PacketBuffer buf) {
        ItemStack _stack0 = buf.readItemStack();
        ItemStack _stack1 = buf.readItemStack();
        ItemStack _stack2 = buf.readItemStack();
        ItemStack _stack3 = buf.readItemStack();
        ItemStack _stack4 = buf.readItemStack();
        ItemStack _stack5 = buf.readItemStack();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        NonNullList<ItemStack> inv = NonNullList.withSize(6, ItemStack.EMPTY);
        inv.set(0, _stack0);
        inv.set(1, _stack1);
        inv.set(2, _stack2);
        inv.set(3, _stack3);
        inv.set(4, _stack4);
        inv.set(5, _stack5);
        return new PacketClientInventoryMessage(inv, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final PacketClientInventoryMessage message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {
                te.inventory.set(0, message.stack0);
                te.inventory.set(1, message.stack1);
                te.inventory.set(2, message.stack2);
                te.inventory.set(3, message.stack3);
                te.inventory.set(4, message.stack4);
                te.inventory.set(5, message.stack5);
            });
            context.get().setPacketHandled(true);
        }
    }

}
