package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageInventoryServer {

    static ItemStack stack0;
    static ItemStack stack1;
    static ItemStack stack2;
    static ItemStack stack3;
    static ItemStack stack4;
    static int storageToken;
    static int storagePrize;
    static BlockPos pos;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageInventoryServer(NonNullList<ItemStack> inventory, int storageToken, int storagePrize, BlockPos pos) {
        this.stack0 = inventory.get(0);
        this.stack1 = inventory.get(1);
        this.stack2 = inventory.get(2);
        this.stack3 = inventory.get(3);
        this.stack4 = inventory.get(4);
        this.storageToken = storageToken;
        this.storagePrize = storagePrize;
        this.pos = pos;
    }





    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageInventoryServer msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.stack0);
        buf.writeItem(msg.stack1);
        buf.writeItem(msg.stack2);
        buf.writeItem(msg.stack3);
        buf.writeItem(msg.stack4);
        buf.writeInt(msg.storageToken);
        buf.writeInt(msg.storagePrize);
        buf.writeBlockPos(msg.pos);
    }

    public static MessageInventoryServer decode (FriendlyByteBuf buf) {
        ItemStack _stack0 = buf.readItem();
        ItemStack _stack1 = buf.readItem();
        ItemStack _stack2 = buf.readItem();
        ItemStack _stack3 = buf.readItem();
        ItemStack _stack4 = buf.readItem();
        int _storageToken = buf.readInt();
        int _storagePrize = buf.readInt();
        BlockPos _pos = buf.readBlockPos();
        NonNullList<ItemStack> inv = NonNullList.withSize(5, ItemStack.EMPTY);
        inv.set(0, _stack0);
        inv.set(1, _stack1);
        inv.set(2, _stack2);
        inv.set(3, _stack3);
        inv.set(4, _stack4);
        return new MessageInventoryServer(inv, _storageToken, _storagePrize, _pos);
    }





    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageInventoryServer message, Supplier<NetworkEvent.Context> context) {
            BlockEntityMachine te = (BlockEntityMachine) context.get().getSender().getLevel().getChunkAt(message.pos).getBlockEntity(message.pos);

            NonNullList<ItemStack> inv = NonNullList.withSize(5, ItemStack.EMPTY);
            inv.set(0, message.stack0);
            inv.set(1, message.stack1);
            inv.set(2, message.stack2);
            inv.set(3, message.stack3);
            inv.set(4, message.stack4);

            te.setItem(0, message.stack0);
            te.setItem(1, message.stack1);
            te.setItem(2, message.stack2);
            te.setItem(3, message.stack3);
            te.setItem(4, message.stack4);
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

            CasinoPacketHandler.sendToChunk(new MessageInventoryClient(inv, message.storageToken, message.storagePrize, message.pos), context.get().getSender().level.getChunkAt(message.pos));
            context.get().setPacketHandled(true);
        }
    }



}
