package mod.casinocraft.network;

import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageSlotsServer {
	
	static int wheel1;
	static int wheel2;
	static int wheel3;
	static BlockPos pos;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public MessageSlotsServer(float wheel1, float wheel2, float wheel3, BlockPos pos) {
		this.wheel1 = (int) wheel1;
		this.wheel2 = (int) wheel2;
		this.wheel3 = (int) wheel3;
		this.pos = pos;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ENCODE / DECODE  ---------- ---------- ---------- ---------- //
	
	public static void encode (MessageSlotsServer msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.wheel1);
		buf.writeInt(msg.wheel2);
		buf.writeInt(msg.wheel3);
		buf.writeBlockPos(msg.pos);
	}
	
	public static MessageSlotsServer decode (FriendlyByteBuf buf) {
		int _wheel1 = buf.readInt();
		int _wheel2 = buf.readInt();
		int _wheel3 = buf.readInt();
		BlockPos _pos = buf.readBlockPos();
		return new MessageSlotsServer(_wheel1, _wheel2, _wheel3, _pos);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  HANDLER  ---------- ---------- ---------- ---------- //
	
	public static class Handler {
		public static void handle (final MessageSlotsServer message, Supplier<NetworkEvent.Context> context) {
			BlockEntityMachine te = (BlockEntityMachine) context.get().getSender().level.getChunkAt(message.pos).getBlockEntity(message.pos);
			context.get().enqueueWork(() -> {
				te.logic.scoreLast = message.wheel1;
				te.logic.command(10);
				te.logic.scoreLast = message.wheel2;
				te.logic.command(20);
				te.logic.scoreLast = message.wheel3;
				te.logic.command(30);
				te.logic.command(1);
			});
			CasinoPacketHandler.sendToChunk(new mod.casinocraft.network.MessageSlotsClient(
							message.wheel1,
							message.wheel2,
							message.wheel3,
							message.pos),
					context.get().getSender().level.getChunkAt(message.pos)
			);
			context.get().setPacketHandled(true);
		}
	}
	
	
	
}
