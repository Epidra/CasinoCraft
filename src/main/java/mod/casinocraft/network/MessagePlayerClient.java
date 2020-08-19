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
    static int meta;
    static int amount;

    public MessagePlayerClient(Item item, int meta, int amount) {
        this.stack = new ItemStack(item, 1);
        this.meta = meta;
        this.amount = amount;
    }

    public static void encode (MessagePlayerClient msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack);
        buf.writeInt(msg.meta);
        buf.writeInt(msg.amount);
    }

    public static MessagePlayerClient decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItemStack();
        int _meta = buf.readInt();
        int _amount = buf.readInt();
        return new MessagePlayerClient(_stack.getItem(), _meta, _amount);
    }

    public static class Handler {
        public static void handle (final MessagePlayerClient message, Supplier<NetworkEvent.Context> context) {
            Item item = message.stack.getItem();
            int amount = message.amount;

            if(amount < 0){
                context.get().enqueueWork(() -> {
                    Minecraft.getInstance().player.inventory.clearMatchingItems(Predicate.isEqual(message.stack), -amount);
                });
            } else {
                context.get().enqueueWork(() -> {
                    Minecraft.getInstance().player.inventory.addItemStackToInventory(new ItemStack(item, amount));
                });
            }
            context.get().setPacketHandled(true);
        }
    }

}