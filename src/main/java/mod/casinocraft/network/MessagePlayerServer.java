package mod.casinocraft.network;

import mod.lucky77.util.Inventory;
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
        buf.writeItem(msg.stack);
        buf.writeInt(msg.amount);
    }

    public static MessagePlayerServer decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItem();
        int _amount = buf.readInt();
        return new MessagePlayerServer(_stack.getItem(), _amount);
    }





    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessagePlayerServer message, Supplier<NetworkEvent.Context> context) {
            ServerPlayerEntity serverPlayer = context.get().getSender();
            Item item = message.stack.getItem();

            context.get().enqueueWork(() ->{
                if(message.amount < 0){
                    Inventory.decreaseInventory(serverPlayer.inventory, message.stack, -message.amount);
                    int i = 0;
                    ItemStack itemStack = ItemStack.EMPTY;
                    Predicate<ItemStack> p_195408_1_ = Predicate.isEqual(message.stack);
                    int count = -message.amount;

                    for(int j = 0; j < serverPlayer.inventory.getContainerSize(); ++j) {
                        ItemStack itemstack = serverPlayer.inventory.getItem(j);
                        if (!itemstack.isEmpty() && p_195408_1_.test(itemstack)) {
                            int k = count <= 0 ? itemstack.getCount() : Math.min(count - i, itemstack.getCount());
                            i += k;
                            if (count != 0) {
                                itemstack.shrink(k);
                                if (itemstack.isEmpty()) {
                                    serverPlayer.inventory.setItem(j, ItemStack.EMPTY);
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
                    serverPlayer.inventory.add(new ItemStack(item, message.amount));
                }
            });
            context.get().setPacketHandled(true);
        }
    }



}
