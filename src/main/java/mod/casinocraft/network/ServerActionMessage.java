package mod.casinocraft.network;

import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerActionMessage {

    static int action;
    static int x;
    static int y;
    static int z;

    public ServerActionMessage(int action, BlockPos pos) {
        this.action = action;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (ServerActionMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.action);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static ServerActionMessage decode (PacketBuffer buf) {
        int _action = buf.readInt();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new ServerActionMessage(_action, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        public static void handle (final ServerActionMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            ServerPlayerEntity serverPlayer = context.get().getSender();

            context.get().enqueueWork(() ->{
                int action = message.action;
                BlockPos pos = new BlockPos(message.x, message.y, message.z);
                TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);
                te.LOGIC.actionTouch(action);
            });
            CasinoPacketHandler.sendToAll(new PacketClientActionMessage(
                    action,
                    new BlockPos(message.x, message.y, message.z)));
            context.get().setPacketHandled(true);

            //// The value that was sent
            //int amount = message.amount;
            //BlockPos pos = new BlockPos(message.x, message.y, message.z);
            //TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);
            //context.get().getSender().getServerWorld().addScheduledTask(() -> {
            //    if(amount <= 0) {
            //        te.bet_storage = 0;
            //        te.setToken(new ItemStack(Blocks.AIR));
            //    } else {
            //        te.inventory.set(0, message.stack0);
            //        te.inventory.set(1, message.stack1);
            //        te.inventory.set(4, message.stack4);
            //        te.bet_storage = amount;
            //    }
            //});
            //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            //CasinoPacketHandler.sendToAll(new PacketClientBlockMessage(
            //        message.stack0,
            //        message.stack1,
            //        message.stack4,
            //        amount,
            //        pos));
            //// No response packet
            //context.get().setPacketHandled(true);
        }
    }

}
