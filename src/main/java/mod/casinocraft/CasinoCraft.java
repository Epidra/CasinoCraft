package mod.casinocraft;

import mod.casinocraft.system.PacketHandler;
import mod.casinocraft.system.ProxyClient;
import mod.casinocraft.system.ProxyCommon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("casinocraft")
public class CasinoCraft {
	
	// Define mod id in a common place for everything to reference
	public static final String MODID = "casinocraft";
	// Client/Server Proxy
	public static ProxyCommon proxy = DistExecutor.runForDist(() -> ProxyClient::new, () -> ProxyCommon::new);
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public CasinoCraft() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addCreative);
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.spec);
		
		MinecraftForge.EVENT_BUS.register(this);
		Register.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SETUP  ---------- ---------- ---------- ---------- //
	
	private void setupCommon(final FMLCommonSetupEvent event) {
		PacketHandler.register();
		Register.setup(event);
	}
	
	private void setupClient(final FMLClientSetupEvent event) {
		Register.setup(event);
	}
	
	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		Register.registerCreativeTabs(event);
	}
	
	
	
}