package mod.casinocraft.network;

import mod.lucky77.system.SystemPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

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

    public static void encode (MessagePlayerServer msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.stack);
        buf.writeInt(msg.amount);
    }

    public static MessagePlayerServer decode (FriendlyByteBuf buf) {
        ItemStack _stack = buf.readItem();
        int _amount = buf.readInt();
        return new MessagePlayerServer(_stack.getItem(), _amount);
    }





    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessagePlayerServer message, Supplier<NetworkEvent.Context> context) {
            ServerPlayer serverPlayer = context.get().getSender();
            Item item = message.stack.getItem();

            context.get().enqueueWork(() ->{
                if(message.amount < 0){
                    decreaseInventory(serverPlayer.getInventory(), message.stack, -message.amount);
                    
                    // int i = 0;
                    // ItemStack itemStack = ItemStack.EMPTY;
                    // Predicate<ItemStack> p_195408_1_ = Predicate.isEqual(message.stack);
                    // int count = -message.amount;
                    // for(int j = 0; j < serverPlayer.getInventory().getContainerSize(); ++j) {
                    //     ItemStack itemstack = serverPlayer.getInventory().getItem(j);
                    //     if (!itemstack.isEmpty() && p_195408_1_.test(itemstack)) {
                    //         int k = count <= 0 ? itemstack.getCount() : Math.min(count - i, itemstack.getCount());
                    //         i += k;
                    //         if (count != 0) {
                    //             itemstack.shrink(k);
                    //             if (itemstack.isEmpty()) {
                    //                 serverPlayer.getInventory().setItem(j, ItemStack.EMPTY);
                    //             }
                    //         }
                    //     }
                    // }
                    // if (!itemStack.isEmpty() && p_195408_1_.test(itemStack)) {
                    //     int l = count <= 0 ? itemStack.getCount() : Math.min(count - i, itemStack.getCount());
                    //     i += l;
                    //     if (count != 0) {
                    //         itemStack.shrink(l);
                    //         if (itemStack.isEmpty()) {
                    //             itemStack = ItemStack.EMPTY;
                    //         }
                    //     }
                    // }
                    
                } else {
                    serverPlayer.getInventory().add(new ItemStack(item, message.amount));
                }
            });
            context.get().setPacketHandled(true);
        }
    
        private static boolean decreaseInventory(Inventory inv, ItemStack item, int amount){
            boolean decreased = false;
            int leftAmount = amount;
            if(item.isEmpty() || amount <= 0){
                return false;
            }
            for(int j = 0; j < inv.getContainerSize(); ++j) {
                if(leftAmount > 0){
                    ItemStack itemstack = inv.getItem(j);
                    if (item.getItem() == itemstack.getItem()) {
                        decreased = true;
                        int count = itemstack.getCount();
                        if(leftAmount - count >= 0){
                            inv.setItem(j, ItemStack.EMPTY);
                            leftAmount -= count;
                        } else {
                            itemstack.shrink(leftAmount);
                            if(itemstack.isEmpty()){
                                inv.setItem(j, ItemStack.EMPTY);
                            }
                            leftAmount = 0;
                        }
                    }
                }
            }
            return decreased;
        }
    }



}
