package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.tileentities.TileEntityBoard;

public class ClientPowerMessage implements IMessage {
	
	private int meta;
    private int x;
    private int y;
    private int z;
    
    public ClientPowerMessage() {
    	
    }

	public ClientPowerMessage(int meta, BlockPos pos) {
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
    
    public static class Handler implements IMessageHandler<ClientPowerMessage, IMessage> {
    	private boolean isEmpty(ItemStack stack){
        	if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
    	
    	@Override
        public IMessage onMessage (final ClientPowerMessage message, final MessageContext context) {
        	// This is the player the packet was sent to the server from
    		IThreadListener thread = Minecraft.getMinecraft();
    		// The value that was sent
    		BlockPos pos = new BlockPos(message.x, message.y, message.z);
    		if(!(Minecraft.getMinecraft().player.world.getBlockState(pos).getBlock() instanceof BlockArcade)) return null;
    		TileEntityBoard te = (TileEntityBoard) Minecraft.getMinecraft().player.world.getTileEntity(pos);
    		BlockArcade block = (BlockArcade) Minecraft.getMinecraft().player.world.getBlockState(pos).getBlock();
    		
    		thread.addScheduledTask(() -> {
    			BlockArcade.setPowerState2(Minecraft.getMinecraft().player.world, pos);
			});
    		//CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
    		// No response packet
            return null;
        }
    }
}