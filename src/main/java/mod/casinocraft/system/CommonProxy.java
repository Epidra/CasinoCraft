package mod.casinocraft.system;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.network.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	
	public void PreInit(FMLPreInitializationEvent preEvent){
		CasinoKeeper.registerStuff(true);

		CasinoPacketHandler.INSTANCE.registerMessage(MessageBlockClient.Handler.class,     MessageBlockClient.class,      0, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageBoardClient.Handler.class,     MessageBoardClient.class,      1, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageInventoryClient.Handler.class, MessageInventoryClient.class,  2, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageModuleClient.Handler.class,    MessageModuleClient.class,     3, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(MessagePlayerClient.Handler.class,    MessagePlayerClient.class,     4, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageScoreClient.Handler.class,     MessageScoreClient.class,      5, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageStartClient.Handler.class,     MessageStartClient.class,      6, Side.CLIENT);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageStateClient.Handler.class,     MessageStateClient.class,      7, Side.CLIENT);

		CasinoPacketHandler.INSTANCE.registerMessage(MessageBlockServer.Handler.class,     MessageBlockServer.class,      8, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageBoardServer.Handler.class,     MessageBoardServer.class,      9, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageInventoryServer.Handler.class, MessageInventoryServer.class, 10, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageModuleServer.Handler.class,    MessageModuleServer.class,    11, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(MessagePlayerServer.Handler.class,    MessagePlayerServer.class,    12, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageScoreServer.Handler.class,     MessageScoreServer.class,     13, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageStartServer.Handler.class,     MessageStartServer.class,     14, Side.SERVER);
		CasinoPacketHandler.INSTANCE.registerMessage(MessageStateServer.Handler.class,     MessageStateServer.class,     15, Side.SERVER);
		
	}
	
	public void Init(FMLInitializationEvent event){
		CasinoKeeper.registerEntities();
	}
	
	public void PostInit(FMLPostInitializationEvent postEvent){
		
	}
	
}
