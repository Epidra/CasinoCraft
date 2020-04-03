package mod.casinocraft.network;

import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerPowerMessage {

    static ItemStack stack;
    static int x;
    static int y;
    static int z;

    public ServerPowerMessage(ItemStack item, BlockPos pos) {
        this.stack = item;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (ServerPowerMessage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static ServerPowerMessage decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItemStack();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new ServerPowerMessage(_stack, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
        public static void handle (final ServerPowerMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            //EntityPlayerMP serverPlayer = context.get().getSender();
            // The value that was sent
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) context.get().getSender().world.getTileEntity(pos);

            context.get().enqueueWork(() ->{
                BlockArcade.setPowerState2(context.get().getSender().world, pos);
            });
            CasinoPacketHandler.sendToAll(new PacketClientPowerMessage(te.inventory.get(1), pos));
            // No response packet

            //if(context.get().getSender().world.getBlockState(pos).getBlock() instanceof BlockArcade) {
            //    BlockArcade block = (BlockArcade) context.get().getSender().world.getBlockState(pos).getBlock();
            //    context.get().getSender().getServerWorld().addScheduledTask(() -> {
            //        BlockArcade.setPowerState2(context.get().getSender().world, pos);
            //    });
            //    //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            //    CasinoPacketHandler.sendToAll(new PacketClientPowerMessage(BlockArcade.EnumModule.byItem(te.inventory.get(1).getItem()).meta, pos));
            //    // No response packet
            //}
            context.get().setPacketHandled(true);
        }
    }

}
