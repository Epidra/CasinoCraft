package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import mod.casinocraft.tileentities.TileEntityBoard;

public class ClientBlockMessage implements IMessage {
	
	private ItemStack stack0;
	private ItemStack stack1;
	private ItemStack stack4;
    private int amount;
    private int x;
    private int y;
    private int z;
    
    public ClientBlockMessage() {
    	
    }
    
    public ClientBlockMessage(ItemStack stack0, ItemStack stack1, ItemStack stack4, int storage, BlockPos pos) {
    	this.stack0 = stack0.copy();
    	this.stack1 = stack1.copy();
    	this.stack4 = stack4.copy();
        this.amount = storage;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
	}

	@Override
    public void toBytes (ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack0);
        ByteBufUtils.writeItemStack(buf, stack1);
        ByteBufUtils.writeItemStack(buf, stack4);
        buf.writeInt(amount);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        try {
            stack0 = ByteBufUtils.readItemStack(buf);
            stack1 = ByteBufUtils.readItemStack(buf);
            stack4 = ByteBufUtils.readItemStack(buf);
            amount = buf.readInt();
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {
        	
        }
    }
    
    public static class Handler implements IMessageHandler<ClientBlockMessage, IMessage> {
    	private boolean isEmpty(ItemStack stack){
        	if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
    	
    	@Override
        public IMessage onMessage (final ClientBlockMessage message, final MessageContext context) {
        	// This is the player the packet was sent to the server from
    		IThreadListener thread = Minecraft.getMinecraft();
    		// The value that was sent
    		int amount = message.amount;
    		BlockPos pos = new BlockPos(message.x, message.y, message.z);
    		TileEntityBoard te = (TileEntityBoard) Minecraft.getMinecraft().world.getTileEntity(pos);
    		if(te == null) return null;
    		thread.addScheduledTask(() -> {
				if(amount <= 0) {
    				te.bet_storage = 0;
    				te.setToken(new ItemStack(Blocks.AIR));
    			} else {
    				te.inventory.set(0, message.stack0);
    				te.inventory.set(1, message.stack1);
    				te.inventory.set(4, message.stack4);
    				te.bet_storage = amount;
    			}
			});
    		//CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
    		// No response packet
            return null;
        }
    }
}
