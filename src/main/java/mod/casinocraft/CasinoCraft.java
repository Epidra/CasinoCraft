package mod.casinocraft;

import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("casinocraft")
public class CasinoCraft {

    public static final String MODID = "casinocraft";





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public CasinoCraft() {
       FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
       FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);
       MinecraftForge.EVENT_BUS.register(this);
       CasinoKeeper.register();
    }





    //----------------------------------------SETUP----------------------------------------//

    private void setupCommon(final FMLCommonSetupEvent event) {
        CasinoPacketHandler.register();
        CasinoKeeper.setup(event);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        CasinoKeeper.setup(event);
    }



}
