package mod.casinocraft.client.network;

import mod.casinocraft.common.block.entity.BlockEntityMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageSettingClient {
	
	static int[] packetData;
	static BlockPos pos;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public MessageSettingClient(BlockPos _pos, int[] _packetData) {
		this.pos = _pos;
		this.packetData = _packetData;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ENCODE / DECODE  ---------- ---------- ---------- ---------- //
	
	public static void encode (MessageSettingClient msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
		buf.writeVarIntArray(msg.packetData);
	}
	
	public static MessageSettingClient decode (FriendlyByteBuf buf) {
		BlockPos _pos = buf.readBlockPos();
		int[] _packet = buf.readVarIntArray();
		return new MessageSettingClient(_pos, _packet);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  HANDLER  ---------- ---------- ---------- ---------- //
	
	public static class Handler {
		public static void handle (final MessageSettingClient message, Supplier<NetworkEvent.Context> context) {
			context.get().enqueueWork(() -> {
				BlockEntityMachine te = (BlockEntityMachine) Minecraft.getInstance().level.getBlockEntity(message.pos);
				te.bettingLow                  = message.packetData[ 0];
				te.bettingHigh                 = message.packetData[ 1];
				te.rewardScore1                = message.packetData[ 2];
				te.rewardScore2                = message.packetData[ 3];
				te.rewardScore3                = message.packetData[ 4];
				te.rewardAmount1               = message.packetData[ 5];
				te.rewardAmount2               = message.packetData[ 6];
				te.rewardAmount3               = message.packetData[ 7];
				te.prizeMode1                  = message.packetData[ 8] == 1;
				te.prizeMode2                  = message.packetData[ 9] == 1;
				te.prizeMode3                  = message.packetData[10] == 1;
				te.transferTokenIN             = message.packetData[11] == 1;
				te.transferTokenOUT            = message.packetData[12] == 1;
				te.transferRewardIN            = message.packetData[13] == 1;
				te.transferRewardOUT           = message.packetData[14] == 1;
				te.settingInfiniteToken        = message.packetData[15] == 1;
				te.settingInfiniteReward       = message.packetData[16] == 1;
				te.settingDropItemsOnBreak     = message.packetData[17] == 1;
				te.settingIndestructableBlock  = message.packetData[18] == 1;
				te.settingAlternateScore       = message.packetData[19] == 1;
				te.settingAlternateColor       = message.packetData[20];
				te.settingRule1 = message.packetData[21];
				te.settingRule2 = message.packetData[22];
				te.settingRule3 = message.packetData[23];
				te.settingRule4 = message.packetData[24];
				te.settingRule5 = message.packetData[25];
				te.sendRulesToLogic();
			});
			context.get().setPacketHandled(true);
		}
	}
	
	
	
}

