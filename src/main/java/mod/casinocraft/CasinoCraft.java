package mod.casinocraft;

import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.nio.file.Paths;

@Mod("casinocraft")
public class CasinoCraft {

    public static final String MODID = "casinocraft";

    // TODO:
    // Add extra panel on screen that shows how much you have already won/lost during that session
    // Add a new message about the amount you have just won (best in the lower left corner)
    // TODO:
    // Make the betting texts translatable
    // TODO:
    // Hide the up/down Button for betting if the betting has no range
    // TODO:
    // Make the Buttons and masseges on Roulette more understantable





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public CasinoCraft() {
       FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
       FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CasinoConfig.spec);
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
