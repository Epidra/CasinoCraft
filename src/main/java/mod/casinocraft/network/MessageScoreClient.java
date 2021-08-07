package mod.casinocraft.network;

import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageScoreClient {

    static int points;
    static String names;
    static BlockPos pos;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageScoreClient(String scoreName, int scorePoints, BlockPos pos) {
        this.points = scorePoints;
        this.names  = scoreName;
        this.pos = pos;
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageScoreClient msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.points);
        buf.writeUtf(msg.names, 24);
        buf.writeBlockPos(msg.pos);
    }

    public static MessageScoreClient decode (FriendlyByteBuf buf) {
        int _points = buf.readInt();
        String _names = buf.readUtf(24);
        BlockPos _pos = buf.readBlockPos();
        return new MessageScoreClient(_names, _points, _pos);
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageScoreClient message, Supplier<NetworkEvent.Context> context) {
            BlockEntityMachine te = (BlockEntityMachine) Minecraft.getInstance().level.getBlockEntity(message.pos);
            context.get().enqueueWork(() ->{
                te.logic.addScore(message.names, message.points);
                te.logic.resetPlayers();
            });
            context.get().setPacketHandled(true);
        }
    }

}
