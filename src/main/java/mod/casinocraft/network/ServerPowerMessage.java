package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.blocks.BlockArcade.EnumModule;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;

public class ServerPowerMessage implements IMessage {
	
	private int meta;
    private int x;
    private int y;
    private int z;
    
    public ServerPowerMessage() {
    	
    }

	public ServerPowerMessage(int meta, BlockPos pos) {
		this.meta = meta;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
	}

	@Override
    public void toBytes (ByteBuf buf) {
        buf.writeInt(meta);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        try {
            meta = buf.readInt();
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {
        	
        }
    }
    
    public static class Handler implements IMessageHandler<ServerPowerMessage, IMessage> {
    	private boolean isEmpty(ItemStack stack){
        	if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
    	
    	@Override
        public IMessage onMessage (final ServerPowerMessage message, final MessageContext context) {
        	// This is the player the packet was sent to the server from
    		EntityPlayerMP serverPlayer = context.getServerHandler().player;
    		// The value that was sent
    		BlockPos pos = new BlockPos(message.x, message.y, message.z);
    		TileEntityBoard te = (TileEntityBoard) context.getServerHandler().player.world.getTileEntity(pos);
    		if(context.getServerHandler().player.world.getBlockState(pos).getBlock() instanceof BlockArcade) {
    			BlockArcade block = (BlockArcade) context.getServerHandler().player.world.getBlockState(pos).getBlock();
        		serverPlayer.getServerWorld().addScheduledTask(() -> {
        			BlockArcade.setPowerState2(context.getServerHandler().player.world, pos);
    			});
        		//CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
        		CasinoPacketHandler.INSTANCE.sendToAll(new ClientPowerMessage(EnumModule.byItem(te.inventory.get(1).getItem()).meta, pos));
        		// No response packet
    		}
            return null;
        }
    }
}
