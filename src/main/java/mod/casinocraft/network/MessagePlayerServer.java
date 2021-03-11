package mod.casinocraft.network;

import mod.casinocraft.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MessagePlayerServer {

    static ItemStack stack;
    static int amount;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessagePlayerServer(Item item, int amount) {
        this.stack = new ItemStack(item, 1);
        this.amount = amount;
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessagePlayerServer msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack);
        buf.writeInt(msg.amount);
    }

    public static MessagePlayerServer decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItemStack();
        int _amount = buf.readInt();
        return new MessagePlayerServer(_stack.getItem(), _amount);
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessagePlayerServer message, Supplier<NetworkEvent.Context> context) {
            ServerPlayerEntity serverPlayer = context.get().getSender();
            Item item = message.stack.getItem();
            int amount = message.amount;

            context.get().enqueueWork(() ->{
                if(amount < 0){
                    InventoryUtil.decreaseInventory(serverPlayer.inventory, message.stack, -amount);
                    int i = 0;
                    ItemStack itemStack = ItemStack.EMPTY;
                    Predicate<ItemStack> p_195408_1_ = Predicate.isEqual(message.stack);
                    int count = -amount;

                    for(int j = 0; j < serverPlayer.inventory.getSizeInventory(); ++j) {
                        ItemStack itemstack = serverPlayer.inventory.getStackInSlot(j);
                        if (!itemstack.isEmpty() && p_195408_1_.test(itemstack)) {
                            int k = count <= 0 ? itemstack.getCount() : Math.min(count - i, itemstack.getCount());
                            i += k;
                            if (count != 0) {
                                itemstack.shrink(k);
                                if (itemstack.isEmpty()) {
                                    serverPlayer.inventory.setInventorySlotContents(j, ItemStack.EMPTY);
                                }
                            }
                        }
                    }

                    if (!itemStack.isEmpty() && p_195408_1_.test(itemStack)) {
                        int l = count <= 0 ? itemStack.getCount() : Math.min(count - i, itemStack.getCount());
                        i += l;
                        if (count != 0) {
                            itemStack.shrink(l);
                            if (itemStack.isEmpty()) {
                                itemStack = ItemStack.EMPTY;
                            }
                        }
                    }
                } else {
                    serverPlayer.inventory.addItemStackToInventory(new ItemStack(item, amount));
                }
            });
            context.get().setPacketHandled(true);
        }
    }

}
