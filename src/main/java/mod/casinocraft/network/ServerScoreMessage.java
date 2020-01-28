package mod.casinocraft.network;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;

public class ServerScoreMessage implements IMessage {
	
	private ItemStack stack;
	int[] points = new int[18];
	int[] length = new int[18];
    String[] names = new String[18];
    private int x;
    private int y;
    private int z;
    
    public ServerScoreMessage() {
    	
    }

	public ServerScoreMessage(Item item, String[] scoreName, int[] scorePoints, BlockPos pos) {
		this.stack = new ItemStack(item);
        for(int i = 0; i < 18; i++) {
        	points[i] = scorePoints[i];
        	length[i] = scoreName[i].length();
        	names[i] = scoreName[i];
        }
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
	}

	@Override
    public void toBytes (ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
        for(int i = 0; i < 18; i++) {
        	buf.writeInt(points[i]);
        	buf.writeInt(length[i]);
        	buf.writeCharSequence(names[i], Charset.defaultCharset());
        }
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        try {
            stack = ByteBufUtils.readItemStack(buf);
            for(int i = 0; i < 18; i++) {
            	points[i] = buf.readInt();
            	length[i] = buf.readInt();
            	names[i] = (String) buf.readCharSequence(length[i], Charset.defaultCharset());
            }
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {
        	
        }
    }
    
    public static class Handler implements IMessageHandler<ServerScoreMessage, IMessage> {
    	private boolean isEmpty(ItemStack stack){
        	if(stack.getItem() == Item.getItemFromBlock(Blocks.AIR)) return true;
            return stack.getItem() == null;
        }
    	
    	@Override
        public IMessage onMessage (final ServerScoreMessage message, final MessageContext context) {
        	// This is the player the packet was sent to the server from
    		EntityPlayerMP serverPlayer = context.getServerHandler().player;
    		// The value that was sent
    		Item item = message.stack.getItem();
    		BlockPos pos = new BlockPos(message.x, message.y, message.z);
    		TileEntityBoard te = (TileEntityBoard) context.getServerHandler().player.world.getTileEntity(pos);
    		serverPlayer.getServerWorld().addScheduledTask(() -> {
    			te.setScoreToken(item);
    			for(int i = 0; i < 18; i++) {
    				te.scorePoints[i] = message.points[i];
    				te.scoreName[i] = message.names[i];
    			}
			});
    		//CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
    		CasinoPacketHandler.INSTANCE.sendToAll(new ClientScoreMessage(item, message.names, message.points, te.getPos()));
    		// No response packet
            return null;
        }
    }
}
