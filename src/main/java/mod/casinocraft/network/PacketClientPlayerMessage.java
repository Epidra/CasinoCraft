package mod.casinocraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class PacketClientPlayerMessage {

    static ItemStack stack;
    static int meta;
    static int amount;

    public PacketClientPlayerMessage(Item item, int meta, int amount) {
        this.stack = new ItemStack(item, 1);
        this.meta = meta;
        this.amount = amount;
    }

    public static void encode (PacketClientPlayerMessage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack);
        buf.writeInt(msg.meta);
        buf.writeInt(msg.amount);
    }

    public static PacketClientPlayerMessage decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItemStack();
        int _meta = buf.readInt();
        int _amount = buf.readInt();
        return new PacketClientPlayerMessage(_stack.getItem(), _meta, _amount);
    }

    public static class Handler {
        public static void handle (final PacketClientPlayerMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            //IThreadListener thread = Minecraft.getInstance();
            // The value that was sent
            Item item = message.stack.getItem();
            int amount = message.amount;
            int meta = message.meta;

            if(amount < 0){
                // Execute the action on the main server thread by adding it as a scheduled task
                context.get().enqueueWork(() -> {
                    Minecraft.getInstance().player.inventory.clearMatchingItems(Predicate.isEqual(message.stack), -amount);
                });
            } else {
                // Execute the action on the main server thread by adding it as a scheduled task
                context.get().enqueueWork(() -> {
                    Minecraft.getInstance().player.inventory.addItemStackToInventory(new ItemStack(item, amount));
                });
            }
            //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            // No response packet
            context.get().setPacketHandled(true);
        }
    }

}
