package mod.casinocraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import mod.casinocraft.tileentities.TileEntityBoard;

public class ClientBoardMessage implements IMessage {
	
	int betLow;
	int betHigh;
	boolean transferIn;
	boolean transferOut;
	boolean isCreative;
    private int x;
    private int y;
    private int z;
    
    public ClientBoardMessage() {
    	
    }
    
    public ClientBoardMessage(int bet_low, int bet_high, boolean transfer_in, boolean transfer_out, boolean is_creative, BlockPos pos) {
    	betLow = bet_low;
    	betHigh = bet_high;
    	transferIn = transfer_in;
    	transferOut = transfer_out;
    	isCreative = is_creative;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
	}

	@Override
    public void toBytes (ByteBuf buf) {
        buf.writeInt(betLow);
        buf.writeInt(betHigh);
        buf.writeBoolean(transferIn);
        buf.writeBoolean(transferOut);
        buf.writeBoolean(isCreative);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        try {
            betLow  = buf.readInt();
            betHigh = buf.readInt();
            transferIn  = buf.readBoolean();
            transferOut = buf.readBoolean();
            isCreative = buf.readBoolean();
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
        } catch (Exception e) {
        	
        }
    }
    
    public static class Handler implements IMessageHandler<ClientBoardMessage, IMessage> {
    	
    	@Override
        public IMessage onMessage (final ClientBoardMessage message, final MessageContext context) {
        	// This is the player the packet was sent to the server from
    		IThreadListener thread = Minecraft.getMinecraft();
    		// The value that was sent
    		BlockPos pos = new BlockPos(message.x, message.y, message.z);
    		TileEntityBoard te = (TileEntityBoard) Minecraft.getMinecraft().world.getTileEntity(pos);
    		thread.addScheduledTask(() -> {
    			te.bet_low  = message.betLow;
        		te.bet_high = message.betHigh;
        		te.transfer_in  = message.transferIn;
        		te.transfer_out = message.transferOut;
        		te.isCreative = message.isCreative;
			});
    		//CasinoPacketHandler.INSTANCE.sendTo(new ClientPlayerMessage(false), serverPlayer);
    		// No response packet
            return null;
        }
    }
}
