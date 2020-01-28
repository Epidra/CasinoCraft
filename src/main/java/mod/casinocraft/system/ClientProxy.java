package mod.casinocraft.system;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.network.ClientBlockMessage;
import mod.casinocraft.network.ClientBoardMessage;
import mod.casinocraft.network.ClientPlayerMessage;
import mod.casinocraft.network.ClientPowerMessage;
import mod.casinocraft.network.ClientScoreMessage;
import mod.casinocraft.network.ServerBlockMessage;
import mod.casinocraft.network.ServerBoardMessage;
import mod.casinocraft.network.ServerPlayerMessage;
import mod.casinocraft.network.ServerPowerMessage;
import mod.casinocraft.network.ServerScoreMessage;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void PreInit(FMLPreInitializationEvent preEvent){
		CasinoKeeper.init();
		CasinoKeeper.registerStuff(true);
		CasinoKeeper.registerRecipes();
		CasinoKeeper.registerSounds();
		
		CasinoPacketHandler.INSTANCE.registerMessage(ClientBlockMessage.Handler.class,  ClientBlockMessage.class,  0, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(ClientPlayerMessage.Handler.class, ClientPlayerMessage.class, 1, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(ClientBoardMessage.Handler.class,  ClientBoardMessage.class,  2, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(ClientScoreMessage.Handler.class,  ClientScoreMessage.class,  3, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(ClientPowerMessage.Handler.class,  ClientPowerMessage.class,  4, Side.CLIENT);
		
        CasinoPacketHandler.INSTANCE.registerMessage(ServerBlockMessage.Handler.class,  ServerBlockMessage.class,  5, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(ServerPlayerMessage.Handler.class, ServerPlayerMessage.class, 6, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(ServerBoardMessage.Handler.class,  ServerBoardMessage.class,  7, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(ServerScoreMessage.Handler.class,  ServerScoreMessage.class,  8, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(ServerPowerMessage.Handler.class,  ServerPowerMessage.class,  9, Side.SERVER);
		
	}
	
	@Override
	public void Init(FMLInitializationEvent event){
		CasinoKeeper.registerRenders();
		CasinoKeeper.registerStuff(false);
		CasinoKeeper.registerEntities();
	}
	
	@Override
	public void PostInit(FMLPostInitializationEvent postEvent){
		
	}
	
}
