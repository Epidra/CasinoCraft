package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.util.Inventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ServerPlayerMessage {

    static ItemStack stack;
    static int meta;
    static int amount;

    public ServerPlayerMessage() {

    }

    public ServerPlayerMessage(Item item, int meta, int amount) {
        this.stack = new ItemStack(item, 1);
        this.meta = meta;
        this.amount = amount;
    }

    public static void encode (ServerPlayerMessage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack);
        buf.writeInt(msg.meta);
        buf.writeInt(msg.amount);
    }

    public static ServerPlayerMessage decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItemStack();
        int _meta = buf.readInt();
        int _amount = buf.readInt();
        return new ServerPlayerMessage(_stack.getItem(), _meta, _amount);
    }

    public static class Handler {
        public static void handle (final ServerPlayerMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            ServerPlayerEntity serverPlayer = context.get().getSender();
            // The value that was sent
            Item item = message.stack.getItem();
            int amount = message.amount;
            int meta = message.meta;

            context.get().enqueueWork(() ->{
                if(amount < 0){
                    Inventory.decreaseInventory(serverPlayer.inventory, message.stack, -amount);
                    serverPlayer.inventory.clearMatchingItems(Predicate.isEqual(message.stack), -amount);
                } else {
                    serverPlayer.inventory.addItemStackToInventory(new ItemStack(item, amount));
                }
            });
            // No response packet
            context.get().setPacketHandled(true);


            //if(amount < 0){
            //        // Execute the action on the main server thread by adding it as a scheduled task
            //        context.get().getSender().getServerWorld().addScheduledTask(() -> {
            //                Inventory.decreaseInventory(serverPlayer.inventory, message.stack, -amount);
            //                //serverPlayer.inventory.clearMatchingItems(Predicate.isEqual(message.stack), -amount);
            //            });
            //    } else {
            //        // Execute the action on the main server thread by adding it as a scheduled task
            //        context.get().getSender().getServerWorld().addScheduledTask(() -> {
            //                serverPlayer.inventory.addItemStackToInventory(new ItemStack(item, amount));
            //            });
            //    }
            //CasinoPacketHandler.sendTo(new PacketClientPlayerMessage(item, amount, meta), serverPlayer);
            // No response packet
            context.get().setPacketHandled(true);
        }
    }

}
