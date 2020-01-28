package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientPlayerMessage implements IMessage {
    
	private ItemStack stack;
	private int meta;
    private int amount;
    
    public ClientPlayerMessage() {
    	
    }
    
    public ClientPlayerMessage(Item item, int meta, int amount) {
        this.stack = new ItemStack(item, 1, meta);
        this.meta = meta;
        this.amount = amount;
    }

	@Override
    public void toBytes (ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(meta);
        buf.writeInt(amount);
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        try {
            stack = ByteBufUtils.readItemStack(buf);
            meta = buf.readInt();
            amount = buf.readInt();
        } catch (Exception e) {
        	
        }
    }
    
    public static class Handler implements IMessageHandler<ClientPlayerMessage, IMessage> {
        @Override
        public IMessage onMessage (final ClientPlayerMessage message, final MessageContext context) {
        	// This is the player the packet was sent to the server from
        	IThreadListener thread = Minecraft.getMinecraft();
    		// The value that was sent
    		Item item = message.stack.getItem();
    		int amount = message.amount;
    		int meta = message.meta;
    		
    		if(amount < 0){
    			// Execute the action on the main server thread by adding it as a scheduled task
    			thread.addScheduledTask(() -> {
    				Minecraft.getMinecraft().player.inventory.clearMatchingItems(item, meta, -amount, null);
    			});
    		} else {
    			// Execute the action on the main server thread by adding it as a scheduled task
    			thread.addScheduledTask(() -> {
    				Minecraft.getMinecraft().player.inventory.addItemStackToInventory(new ItemStack(item, amount, meta));
    			});
    		}
    		//CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
    		// No response packet
            return null;
        }
    }
}