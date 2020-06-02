package net.casinocraft.mod.proxy;

import net.casinocraft.mod.CasinoKeeper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void PreInit(FMLPreInitializationEvent preEvent){
		CasinoKeeper.removeRecipes();
		CasinoKeeper.registerBlocks(true);
		CasinoKeeper.registerRecipes();
	}
	
	public void Init(FMLInitializationEvent event){
		//StairKeeper.registerBlocks(false);
		CasinoKeeper.registerEntities();
	}
	
	public void PostInit(FMLPostInitializationEvent postEvent){
		
	}
	
	
	
	public void registerRenderThings(){
		
	}
	
	public void registerTileEntitySpecialRenderer(){
		
	}
	
}
