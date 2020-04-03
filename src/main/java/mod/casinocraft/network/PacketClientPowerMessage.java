package mod.casinocraft.network;

import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientPowerMessage {

    static ItemStack stack;
    static int x;
    static int y;
    static int z;

    public PacketClientPowerMessage(ItemStack item, BlockPos pos) {
        this.stack = item;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public static void encode (PacketClientPowerMessage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.stack);
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static PacketClientPowerMessage decode (PacketBuffer buf) {
        ItemStack _stack = buf.readItemStack();
        int _x = buf.readInt();
        int _y = buf.readInt();
        int _z = buf.readInt();
        return new PacketClientPowerMessage(_stack, new BlockPos(_x, _y, _z));
    }

    public static class Handler {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
        public static void handle (final PacketClientPowerMessage message, Supplier<NetworkEvent.Context> context) {
            // This is the player the packet was sent to the server from
            //IThreadListener thread = Minecraft.getInstance();
            // The value that was sent
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getInstance().player.world.getTileEntity(pos);
            BlockArcade block = (BlockArcade) Minecraft.getInstance().player.world.getBlockState(pos).getBlock();

            context.get().enqueueWork(() -> {
                BlockArcade.setPowerState2(Minecraft.getInstance().player.world, pos);
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new PacketClientPlayerMessage(false), serverPlayer);
            // No response packet
            context.get().setPacketHandled(true);
        }
    }

}
