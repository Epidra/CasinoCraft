package mod.casinocraft.network;

import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageInventoryClient {

    static ItemStack stack0;
    static ItemStack stack1;
    static ItemStack stack2;
    static ItemStack stack3;
    static ItemStack stack4;
    static int storageToken;
    static int storagePrize;
    static int x;
    static int y;
    static int z;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageInventoryClient(NonNullList<ItemStack> inventory, int storageToken, int storagePrize, BlockPos pos) {
        this.stack0 = inventory.get(0);
        this.stack1 = inventory.get(1);
        this.stack2 = inventory.get(2);
        this.stack3 = inventory.get(3);
        this.stack4 = inventory.get(4);
        this.storageToken = storageToken;
        this.storagePrize = storagePrize;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageInventoryClient msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack0);
        buf.writeItemStack(msg.stack1);
        buf.writeItemStack(msg.stack2);
        buf.writeItemStack(msg.stack3);
        buf.writeItemStack(msg.stack4);
        buf.writeInt(msg.storageToken);
        buf.writeInt(msg.storagePrize);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static MessageInventoryClient decode (PacketBuffer buf) {
        ItemStack _stack0 = buf.readItemStack();
        ItemStack _stack1 = buf.readItemStack();
        ItemStack _stack2 = buf.readItemStack();
        ItemStack _stack3 = buf.readItemStack();
        ItemStack _stack4 = buf.readItemStack();
        int _storageToken = buf.readInt();
        int _storagePrize = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        NonNullList<ItemStack> inv = NonNullList.withSize(5, ItemStack.EMPTY);
        inv.set(0, _stack0);
        inv.set(1, _stack1);
        inv.set(2, _stack2);
        inv.set(3, _stack3);
        inv.set(4, _stack4);
        return new MessageInventoryClient(inv, _storageToken, _storagePrize, new BlockPos(_x, _y, _z));
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageInventoryClient message, Supplier<NetworkEvent.Context> context) {
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityMachine te = (TileEntityMachine) Minecraft.getInstance().world.getTileEntity(pos);
            context.get().enqueueWork(() -> {

                te.inventory.set(0, message.stack0);
                te.inventory.set(1, message.stack1);
                te.inventory.set(2, message.stack2);
                te.inventory.set(3, message.stack3);
                te.inventory.set(4, message.stack4);
                te.storageToken = message.storageToken;
                te.storageReward = message.storagePrize;

                if(message.storageToken <= 0) {
                    te.storageToken = 0;
                    te.setTokenBET(new ItemStack(Blocks.AIR));
                }
                if(message.storagePrize <= 0) {
                    te.storageReward = 0;
                    te.setTokenREW(new ItemStack(Blocks.AIR));
                }
                te.changeLogic();
            });
            context.get().setPacketHandled(true);
        }
    }

}
