package mod.casinocraft;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.stream.Collectors;

//@Mod("casinocraft")
public class CasinoCraft {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "casinocraft";

    public CasinoCraft() {
       FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
       MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CasinoKeeper.loadConfig(Paths.get("config", MODID + ".toml"));
        //CasinoPacketHandler.register();
        CasinoKeeper.registerEntities();
        //ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> CasinoGuiHandler::openGui);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            CasinoKeeper.registerBlocks();
        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            CasinoKeeper.registerItems();
        }

        @SubscribeEvent
        public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event){
            CasinoKeeper.registerTileEntities(event);
        }

    }
}
