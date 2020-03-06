package mod.casinocraft.system;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.network.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.function.Supplier;

public class CasinoPacketHandler {

    private static final String PROTOCOL_VERSION = Integer.toString(1);

    //private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(CasinoCraft.MODID, "main_channel"))
    //        .clientAcceptedVersions(PROTOCOL_VERSION::equals)
    //        .serverAcceptedVersions(PROTOCOL_VERSION::equals)
    //        .networkProtocolVersion(() -> PROTOCOL_VERSION)
    //        .simpleChannel();

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("casinocraft", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register(){
        int disc = 0;
        INSTANCE.registerMessage(disc++, PacketClientBlockMessage.class, PacketClientBlockMessage::encode, PacketClientBlockMessage::decode, PacketClientBlockMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientBoardMessage.class, PacketClientBoardMessage::encode, PacketClientBoardMessage::decode, PacketClientBoardMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientPlayerMessage.class, PacketClientPlayerMessage::encode, PacketClientPlayerMessage::decode, PacketClientPlayerMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientPowerMessage.class, PacketClientPowerMessage::encode, PacketClientPowerMessage::decode, PacketClientPowerMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientScoreMessage.class, PacketClientScoreMessage::encode, PacketClientScoreMessage::decode, PacketClientScoreMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientActionMessage.class, PacketClientActionMessage::encode, PacketClientActionMessage::decode, PacketClientActionMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientStartMessage.class, PacketClientStartMessage::encode, PacketClientStartMessage::decode, PacketClientStartMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientTurnstateMessage.class, PacketClientTurnstateMessage::encode, PacketClientTurnstateMessage::decode, PacketClientTurnstateMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, PacketClientPauseMessage.class, PacketClientPauseMessage::encode, PacketClientPauseMessage::decode, PacketClientPauseMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerBlockMessage.class, ServerBlockMessage::encode, ServerBlockMessage::decode, ServerBlockMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerBoardMessage.class, ServerBoardMessage::encode, ServerBoardMessage::decode, ServerBoardMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerPlayerMessage.class, ServerPlayerMessage::encode, ServerPlayerMessage::decode, ServerPlayerMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerPowerMessage.class, ServerPowerMessage::encode, ServerPowerMessage::decode, ServerPowerMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerBlockMessage.class, ServerBlockMessage::encode, ServerBlockMessage::decode, ServerBlockMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerActionMessage.class, ServerActionMessage::encode, ServerActionMessage::decode, ServerActionMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerStartMessage.class, ServerStartMessage::encode, ServerStartMessage::decode, ServerStartMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerTurnstateMessage.class, ServerTurnstateMessage::encode, ServerTurnstateMessage::decode, ServerTurnstateMessage.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerPauseMessage.class, ServerPauseMessage::encode, ServerPauseMessage::decode, ServerPauseMessage.Handler::handle);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message){
        INSTANCE.send(target, message);
    }

    public static void sendToServer(Object message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendTo(MSG msg, ServerPlayerEntity player) {
        INSTANCE.send(PacketDistributor.PLAYER.with((Supplier<ServerPlayerEntity>) player), msg);
        //if (!(player instanceof FakePlayer)) {
        //    INSTANCE.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        //}
    }

    public static <MSG> void sendToChunk(MSG msg, Chunk chunk) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with((Supplier<Chunk>) chunk), msg);
    }

    public static <MSG> void sendToAll(MSG msg) {
        //for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
        //    sendTo(packet, player);
        //}
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

}
