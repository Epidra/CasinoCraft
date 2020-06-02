package net.casinocraft.mod;

import net.casinocraft.mod.handler.CasinoGUIHandler;
import net.casinocraft.mod.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = CasinoCraft.modid, version = CasinoCraft.version, name = CasinoCraft.modName, dependencies = CasinoCraft.dependencies)
public class CasinoCraft {
	
	public static final String modid = "casinocraft";
	public static final String version = "v02";
	public static final String modName = "CasinoCraft";
	public static final String dependencies = "";
	
	@Instance(modid)
	public static CasinoCraft instance;
	

	@SidedProxy(clientSide = "net.casinocraft.mod.proxy.ClientProxy", serverSide = "net.casinocraft.mod.proxy.CommonProxy")
	public static CommonProxy casinoProxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent preEvent){
		CasinoKeeper.loadConfig(preEvent);
		casinoProxy.PreInit(preEvent);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event){
		casinoProxy.Init(event);
		//MinecraftForge.EVENT_BUS.register(new CasinoHook());
		NetworkRegistry.INSTANCE.registerGuiHandler(CasinoCraft.instance, new CasinoGUIHandler());
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent){
		casinoProxy.PostInit(postEvent);
	}
}

