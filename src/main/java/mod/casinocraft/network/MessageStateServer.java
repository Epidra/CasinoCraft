package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageStateServer implements IMessage {

    boolean system;
    int state;
    int x;
    int y;
    int z;

    public MessageStateServer() {

    }

    public MessageStateServer(boolean system, int state, BlockPos pos) {
        this.system = system;
        this.state = state;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void toBytes (ByteBuf buf) {
        buf.writeBoolean(system);
        buf.writeInt(state);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public void fromBytes (ByteBuf buf) {
        try {
            system = buf.readBoolean();
            state = buf.readInt();
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {

        }
    }

    public static class Handler implements IMessageHandler<MessageStateServer, IMessage> {
        private boolean isEmpty(ItemStack stack){
            if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }

        @Override
        public IMessage onMessage (final MessageStateServer message, final MessageContext context) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = context.getServerHandler().player;
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityBoard te = (TileEntityBoard) context.getServerHandler().player.world.getTileEntity(pos);
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if(message.system){
                    if(message.state == -1){
                        te.LOGIC.pause = !te.LOGIC.pause;
                    } else if(message.state == -2){
                        te.LOGIC.resetPlayers();
                        te.LOGIC.turnstate = 0;
                    } else if(message.state == -3){
                        te.LOGIC.resetPlayers();
                    } else if(message.state >= 10){
                        te.LOGIC.reward[message.state - 10] = 0;
                    } else {
                        te.LOGIC.turnstate = message.state;
                    }
                } else {
                    te.LOGIC.command(message.state);
                }
            });
            //CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
            CasinoPacketHandler.INSTANCE.sendToAllTracking(new MessageStateClient(
                    message.system,
                    message.state,
                    pos),
                    new NetworkRegistry.TargetPoint(context.getServerHandler().player.dimension, message.x, message.y, message.z, 64));
            // No response packet
            return null;
        }
    }

}
