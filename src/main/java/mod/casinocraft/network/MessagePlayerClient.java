package mod.casinocraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MessagePlayerClient {

    static ItemStack stack;
    static int amount;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessagePlayerClient(Item item, int amount) {
        this.stack = new ItemStack(item, 1);
        this.amount = amount;
    }





    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessagePlayerClient msg, PacketBuffer buf) {
        buf.writeItem(msg.stack);
        buf.writeInt(msg.amount);
    }

    public static MessagePlayerClient decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItem();
        int _amount = buf.readInt();
        return new MessagePlayerClient(_stack.getItem(), _amount);
    }





    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessagePlayerClient message, Supplier<NetworkEvent.Context> context) {
            Item item = message.stack.getItem();

            if(message.amount < 0){
                context.get().enqueueWork(() -> {
                    int i = 0;
                    ItemStack itemStack = ItemStack.EMPTY;
                    Predicate<ItemStack> p_195408_1_ = Predicate.isEqual(message.stack);
                    int count = -message.amount;

                    for(int j = 0; j < Minecraft.getInstance().player.inventory.getContainerSize(); ++j) {
                        ItemStack itemstack = Minecraft.getInstance().player.inventory.getItem(j);
                        if (!itemstack.isEmpty() && p_195408_1_.test(itemstack)) {
                            int k = count <= 0 ? itemstack.getCount() : Math.min(count - i, itemstack.getCount());
                            i += k;
                            if (count != 0) {
                                itemstack.shrink(k);
                                if (itemstack.isEmpty()) {
                                    Minecraft.getInstance().player.inventory.setItem(j, ItemStack.EMPTY);
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
                });
            } else {
                context.get().enqueueWork(() -> {
                    Minecraft.getInstance().player.inventory.add(new ItemStack(item, message.amount));
                });
            }
            context.get().setPacketHandled(true);
        }
    }



}
