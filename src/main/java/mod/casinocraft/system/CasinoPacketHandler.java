package mod.casinocraft.system;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CasinoPacketHandler {

    private static final String PROTOCOL_VERSION = Integer.toString(1);

    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(CasinoCraft.MODID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register(){
        int disc = 0;
        HANDLER.registerMessage(disc++, PacketClientBlockMessage.class, PacketClientBlockMessage::encode, PacketClientBlockMessage::decode, PacketClientBlockMessage.Handler::handle);
        HANDLER.registerMessage(disc++, PacketClientBoardMessage.class, PacketClientBoardMessage::encode, PacketClientBoardMessage::decode, PacketClientBoardMessage.Handler::handle);
        HANDLER.registerMessage(disc++, PacketClientPlayerMessage.class, PacketClientPlayerMessage::encode, PacketClientPlayerMessage::decode, PacketClientPlayerMessage.Handler::handle);
        HANDLER.registerMessage(disc++, PacketClientPowerMessage.class, PacketClientPowerMessage::encode, PacketClientPowerMessage::decode, PacketClientPowerMessage.Handler::handle);
        HANDLER.registerMessage(disc++, PacketClientScoreMessage.class, PacketClientScoreMessage::encode, PacketClientScoreMessage::decode, PacketClientScoreMessage.Handler::handle);
        HANDLER.registerMessage(disc++, ServerBlockMessage.class, ServerBlockMessage::encode, ServerBlockMessage::decode, ServerBlockMessage.Handler::handle);
        HANDLER.registerMessage(disc++, ServerBoardMessage.class, ServerBoardMessage::encode, ServerBoardMessage::decode, ServerBoardMessage.Handler::handle);
        HANDLER.registerMessage(disc++, ServerPlayerMessage.class, ServerPlayerMessage::encode, ServerPlayerMessage::decode, ServerPlayerMessage.Handler::handle);
        HANDLER.registerMessage(disc++, ServerPowerMessage.class, ServerPowerMessage::encode, ServerPowerMessage::decode, ServerPowerMessage.Handler::handle);
        HANDLER.registerMessage(disc++, ServerScoreMessage.class, ServerScoreMessage::encode, ServerScoreMessage::decode, ServerScoreMessage.Handler::handle);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message){
        HANDLER.send(target, message);
    }

    public static void sendToServer(Object message){
        HANDLER.sendToServer(message);
    }

   public static <MSG> void sendTo(MSG msg, EntityPlayerMP player) {
       if (!(player instanceof FakePlayer)) {
           HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
       }
   }

   public static <MSG> void sendToAll(MSG packet) {
       for (EntityPlayerMP player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
           sendTo(packet, player);
       }
   }

}
