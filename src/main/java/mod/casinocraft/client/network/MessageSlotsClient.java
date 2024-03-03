package mod.casinocraft.client.network;

import mod.casinocraft.common.block.entity.BlockEntityMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageSlotsClient {
	
	static int wheel1;
	static int wheel2;
	static int wheel3;
	static BlockPos pos;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public MessageSlotsClient(float wheel1, float wheel2, float wheel3, BlockPos pos) {
		this.wheel1 = (int)wheel1;
		this.wheel2 = (int)wheel2;
		this.wheel3 = (int)wheel3;
		this.pos = pos;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ENCODE / DECODE  ---------- ---------- ---------- ---------- //
	
	public static void encode (MessageSlotsClient msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.wheel1);
		buf.writeInt(msg.wheel2);
		buf.writeInt(msg.wheel3);
		buf.writeBlockPos(msg.pos);
	}
	
	public static MessageSlotsClient decode (FriendlyByteBuf buf) {
		int _wheel1 = buf.readInt();
		int _wheel2 = buf.readInt();
		int _wheel3 = buf.readInt();
		BlockPos _pos = buf.readBlockPos();
		return new MessageSlotsClient(_wheel1, _wheel2, _wheel3, _pos);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  HANDLER  ---------- ---------- ---------- ---------- //
	
	public static class Handler {
		public static void handle (final MessageSlotsClient message, Supplier<NetworkEvent.Context> context) {
			BlockEntityMachine te = (BlockEntityMachine) Minecraft.getInstance().level.getBlockEntity(message.pos);
			context.get().enqueueWork(() -> {
				te.logic.scoreLast = message.wheel1;
				te.logic.command(10);
				te.logic.scoreLast = message.wheel2;
				te.logic.command(20);
				te.logic.scoreLast = message.wheel3;
				te.logic.command(30);
				te.logic.command(1);
			});
			context.get().setPacketHandled(true);
		}
	}
	
	
	
}
