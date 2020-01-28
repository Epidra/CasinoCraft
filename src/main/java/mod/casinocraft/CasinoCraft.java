package mod.casinocraft;

import mod.casinocraft.system.CasinoGuiHandler;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
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

@Mod("casinocraft")
public class CasinoCraft {

    /** Log Manager Instance */
    public static final Logger LOGGER = LogManager.getLogger();
    /** The Mod ID */
    public static final String MODID = "casinocraft";



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor */
    public CasinoCraft() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);




        //FMLJavaModLoadingContext.get().getModEventBus().addListener(CasinoGuiHandler::openGui);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }



    //----------------------------------------SETUP_EVENTS----------------------------------------//

    /** Server Side Setup Event */
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        CasinoKeeper.loadConfig(Paths.get("config", MODID + ".toml"));
        CasinoPacketHandler.register();
        CasinoKeeper.registerEntities();
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> CasinoGuiHandler::openGui);

    }

    /** Client Side Setup Event */
    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    /** Inter Mod Send Event */
    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(MODID, "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    /** Inter Mod Receive Event */
    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    /** Server Start Event */
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    /** Register Event */
    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            CasinoKeeper.registerBlocks();
        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            // register a new block here
            CasinoKeeper.registerItems();
        }
        //@SubscribeEvent
        //public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        //    URN_TILE = TileEntityType.Builder.create(TileEntityUrn::new).build(null);
        //    URN_TILE.setRegistryName("urn");
        //    event.getRegistry().register(URN_TILE);
        //}
    }
}
