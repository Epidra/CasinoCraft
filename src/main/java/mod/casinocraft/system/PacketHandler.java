package mod.casinocraft.system;

import mod.casinocraft.client.network.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static mod.casinocraft.CasinoCraft.MODID;

public class PacketHandler {
	
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	
	
	
	
	
	// ---------- ---------- ---------- ----------  REGISTER  ---------- ---------- ---------- ---------- //
	
	public static void register(){
		int disc = 0;
		INSTANCE.registerMessage(disc++, MessageSettingClient.class,   MessageSettingClient::encode,   MessageSettingClient::decode,   MessageSettingClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageSettingServer.class,   MessageSettingServer::encode,   MessageSettingServer::decode,   MessageSettingServer.Handler::handle);
		INSTANCE.registerMessage(disc++, MessagePlayerClient.class,    MessagePlayerClient::encode,    MessagePlayerClient::decode,    MessagePlayerClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessagePlayerServer.class,    MessagePlayerServer::encode,    MessagePlayerServer::decode,    MessagePlayerServer.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageModuleClient.class,    MessageModuleClient::encode,    MessageModuleClient::decode,    MessageModuleClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageModuleServer.class,    MessageModuleServer::encode,    MessageModuleServer::decode,    MessageModuleServer.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageScoreClient.class,     MessageScoreClient::encode,     MessageScoreClient::decode,     MessageScoreClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageScoreServer.class,     MessageScoreServer::encode,     MessageScoreServer::decode,     MessageScoreServer.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageStartClient.class,     MessageStartClient::encode,     MessageStartClient::decode,     MessageStartClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageStartServer.class,     MessageStartServer::encode,     MessageStartServer::decode,     MessageStartServer.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageStateClient.class,     MessageStateClient::encode,     MessageStateClient::decode,     MessageStateClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageStateServer.class,     MessageStateServer::encode,     MessageStateServer::decode,     MessageStateServer.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageSlotsClient.class,     MessageSlotsClient::encode,     MessageSlotsClient::decode,     MessageSlotsClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageSlotsServer.class,     MessageSlotsServer::encode,     MessageSlotsServer::decode,     MessageSlotsServer.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageInventoryClient.class, MessageInventoryClient::encode, MessageInventoryClient::decode, MessageInventoryClient.Handler::handle);
		INSTANCE.registerMessage(disc++, MessageInventoryServer.class, MessageInventoryServer::encode, MessageInventoryServer::decode, MessageInventoryServer.Handler::handle);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message){
		INSTANCE.send(target, message);
	}
	
	public static void sendToServer(Object message){
		INSTANCE.sendToServer(message);
	}
	
	public static <MSG> void sendTo(MSG msg, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
	}
	
	public static <MSG> void sendToChunk(MSG msg, LevelChunk chunk) {
		INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
	}
	
	public static <MSG> void sendToAll(MSG msg) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
	}
	
	
	
}