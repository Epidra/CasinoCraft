package net.casinocraft.mod.proxy;

import net.casinocraft.mod.CasinoKeeper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends  CommonProxy {
	
	@Override
	public void PreInit(FMLPreInitializationEvent preEvent){
		CasinoKeeper.removeRecipes();
		CasinoKeeper.registerBlocks(true);
		CasinoKeeper.registerRecipes();
	}
	
	@Override
	public void Init(FMLInitializationEvent event){
		CasinoKeeper.registerBlocks(false);
		CasinoKeeper.registerEntities();
	}
	
	//@Override
	//public void PostInit(FMLPostInitializationEvent postEvent){
		
	//}
	
	public void registerRenderThings(){
		
	}
	
	public void registerTileEntitySpecialRenderer(){
		
	}
	
}
