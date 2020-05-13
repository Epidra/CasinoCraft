package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageModuleClient implements IMessage {

    int x;
    int y;
    int z;

    public MessageModuleClient() {

    }

    public MessageModuleClient(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void toBytes (ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void fromBytes (ByteBuf buf) {
        try {
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {

        }
    }

    public static class Handler implements IMessageHandler<MessageModuleClient, IMessage> {
        @Override
        public IMessage onMessage (final MessageModuleClient message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            IThreadListener thread = Minecraft.getMinecraft();
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) Minecraft.getMinecraft().world.getTileEntity(pos);
            if(te == null) return null;
            thread.addScheduledTask(() -> {
                BlockArcade.setModuleState(Minecraft.getMinecraft().player.world, pos);
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            // No response packet
            return null;
        }
    }

}
