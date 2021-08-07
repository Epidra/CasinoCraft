package mod.casinocraft.network;

import mod.casinocraft.blockentity.BlockEntityMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStartClient {

    static String name;
    static int seed;
    static BlockPos pos;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public MessageStartClient(String name, int seed, BlockPos pos) {
        this.name = name;
        this.seed = seed;
        this.pos = pos;
    }




    //----------------------------------------ENCODE/DECODE----------------------------------------//

    public static void encode (MessageStartClient msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.name);
        buf.writeInt(msg.seed);
        buf.writeBlockPos(msg.pos);
    }

    public static MessageStartClient decode (FriendlyByteBuf buf) {
        String _name = buf.readUtf(24);
        int _seed = buf.readInt();
        BlockPos _pos = buf.readBlockPos();
        return new MessageStartClient(_name, _seed, _pos);
    }




    //----------------------------------------HANDLER----------------------------------------//

    public static class Handler {
        public static void handle (final MessageStartClient message, Supplier<NetworkEvent.Context> context) {
            BlockEntityMachine te = (BlockEntityMachine) Minecraft.getInstance().level.getBlockEntity(message.pos);
            context.get().enqueueWork(() -> {
                if(message.seed > -1) te.logic.start(message.seed);
                te.logic.addPlayer(message.name);
            });
            context.get().setPacketHandled(true);
        }
    }

}
