package mod.casinocraft;

import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.spec);
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

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            event.accept(CasinoKeeper.CARDTABLE_BASE_WHITE);
            event.accept(CasinoKeeper.CARDTABLE_BASE_ORANGE);
            event.accept(CasinoKeeper.CARDTABLE_BASE_MAGENTA);
            event.accept(CasinoKeeper.CARDTABLE_BASE_LIGHT_BLUE);
            event.accept(CasinoKeeper.CARDTABLE_BASE_YELLOW);
            event.accept(CasinoKeeper.CARDTABLE_BASE_LIME);
            event.accept(CasinoKeeper.CARDTABLE_BASE_PINK);
            event.accept(CasinoKeeper.CARDTABLE_BASE_GRAY);
            event.accept(CasinoKeeper.CARDTABLE_BASE_LIGHT_GRAY);
            event.accept(CasinoKeeper.CARDTABLE_BASE_CYAN);
            event.accept(CasinoKeeper.CARDTABLE_BASE_PURPLE);
            event.accept(CasinoKeeper.CARDTABLE_BASE_BLUE);
            event.accept(CasinoKeeper.CARDTABLE_BASE_BROWN);
            event.accept(CasinoKeeper.CARDTABLE_BASE_GREEN);
            event.accept(CasinoKeeper.CARDTABLE_BASE_RED);
            event.accept(CasinoKeeper.CARDTABLE_BASE_BLACK);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_WHITE);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_ORANGE);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_MAGENTA);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_LIGHT_BLUE);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_YELLOW);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_LIME);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_PINK);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_GRAY);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_LIGHT_GRAY);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_CYAN);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_PURPLE);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_BLUE);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_BROWN);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_GREEN);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_RED);
            event.accept(CasinoKeeper.CARDTABLE_WIDE_BLACK);
            event.accept(CasinoKeeper.ARCADE_BASE_WHITE);
            event.accept(CasinoKeeper.ARCADE_BASE_ORANGE);
            event.accept(CasinoKeeper.ARCADE_BASE_MAGENTA);
            event.accept(CasinoKeeper.ARCADE_BASE_LIGHT_BLUE);
            event.accept(CasinoKeeper.ARCADE_BASE_YELLOW);
            event.accept(CasinoKeeper.ARCADE_BASE_LIME);
            event.accept(CasinoKeeper.ARCADE_BASE_PINK);
            event.accept(CasinoKeeper.ARCADE_BASE_GRAY);
            event.accept(CasinoKeeper.ARCADE_BASE_LIGHT_GRAY);
            event.accept(CasinoKeeper.ARCADE_BASE_CYAN);
            event.accept(CasinoKeeper.ARCADE_BASE_PURPLE);
            event.accept(CasinoKeeper.ARCADE_BASE_BLUE);
            event.accept(CasinoKeeper.ARCADE_BASE_BROWN);
            event.accept(CasinoKeeper.ARCADE_BASE_GREEN);
            event.accept(CasinoKeeper.ARCADE_BASE_RED);
            event.accept(CasinoKeeper.ARCADE_BASE_BLACK);
            event.accept(CasinoKeeper.ARCADE_SLOT_WHITE);
            event.accept(CasinoKeeper.ARCADE_SLOT_ORANGE);
            event.accept(CasinoKeeper.ARCADE_SLOT_MAGENTA);
            event.accept(CasinoKeeper.ARCADE_SLOT_LIGHT_BLUE);
            event.accept(CasinoKeeper.ARCADE_SLOT_YELLOW);
            event.accept(CasinoKeeper.ARCADE_SLOT_LIME);
            event.accept(CasinoKeeper.ARCADE_SLOT_PINK);
            event.accept(CasinoKeeper.ARCADE_SLOT_GRAY);
            event.accept(CasinoKeeper.ARCADE_SLOT_LIGHT_GRAY);
            event.accept(CasinoKeeper.ARCADE_SLOT_CYAN);
            event.accept(CasinoKeeper.ARCADE_SLOT_PURPLE);
            event.accept(CasinoKeeper.ARCADE_SLOT_BLUE);
            event.accept(CasinoKeeper.ARCADE_SLOT_BROWN);
            event.accept(CasinoKeeper.ARCADE_SLOT_GREEN);
            event.accept(CasinoKeeper.ARCADE_SLOT_RED);
            event.accept(CasinoKeeper.ARCADE_SLOT_BLACK);
        }
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(CasinoKeeper.MODULE_CARD_WHITE);
            event.accept(CasinoKeeper.MODULE_CARD_ORANGE);
            event.accept(CasinoKeeper.MODULE_CARD_MAGENTA);
            event.accept(CasinoKeeper.MODULE_CARD_LIGHT_BLUE);
            event.accept(CasinoKeeper.MODULE_CARD_YELLOW);
            event.accept(CasinoKeeper.MODULE_CARD_LIME);
            event.accept(CasinoKeeper.MODULE_CARD_PINK);
            event.accept(CasinoKeeper.MODULE_CARD_GRAY);
            event.accept(CasinoKeeper.MODULE_CARD_LIGHT_GRAY);
            event.accept(CasinoKeeper.MODULE_CARD_CYAN);
            event.accept(CasinoKeeper.MODULE_CARD_PURPLE);
            event.accept(CasinoKeeper.MODULE_CARD_BLUE);
            event.accept(CasinoKeeper.MODULE_CARD_BROWN);
            event.accept(CasinoKeeper.MODULE_CARD_GREEN);
            event.accept(CasinoKeeper.MODULE_CARD_RED);
            event.accept(CasinoKeeper.MODULE_CARD_BLACK);
            event.accept(CasinoKeeper.MODULE_MINO_WHITE);
            event.accept(CasinoKeeper.MODULE_MINO_ORANGE);
            event.accept(CasinoKeeper.MODULE_MINO_MAGENTA);
            event.accept(CasinoKeeper.MODULE_MINO_LIGHT_BLUE);
            event.accept(CasinoKeeper.MODULE_MINO_YELLOW);
            event.accept(CasinoKeeper.MODULE_MINO_LIME);
            event.accept(CasinoKeeper.MODULE_MINO_PINK);
            event.accept(CasinoKeeper.MODULE_MINO_GRAY);
            event.accept(CasinoKeeper.MODULE_MINO_LIGHT_GRAY);
            event.accept(CasinoKeeper.MODULE_MINO_CYAN);
            event.accept(CasinoKeeper.MODULE_MINO_PURPLE);
            event.accept(CasinoKeeper.MODULE_MINO_BLUE);
            event.accept(CasinoKeeper.MODULE_MINO_BROWN);
            event.accept(CasinoKeeper.MODULE_MINO_GREEN);
            event.accept(CasinoKeeper.MODULE_MINO_RED);
            event.accept(CasinoKeeper.MODULE_MINO_BLACK);
            event.accept(CasinoKeeper.MODULE_CHIP_WHITE);
            event.accept(CasinoKeeper.MODULE_CHIP_ORANGE);
            event.accept(CasinoKeeper.MODULE_CHIP_MAGENTA);
            event.accept(CasinoKeeper.MODULE_CHIP_LIGHT_BLUE);
            event.accept(CasinoKeeper.MODULE_CHIP_YELLOW);
            event.accept(CasinoKeeper.MODULE_CHIP_LIME);
            event.accept(CasinoKeeper.MODULE_CHIP_PINK);
            event.accept(CasinoKeeper.MODULE_CHIP_GRAY);
            event.accept(CasinoKeeper.MODULE_CHIP_LIGHT_GRAY);
            event.accept(CasinoKeeper.MODULE_CHIP_CYAN);
            event.accept(CasinoKeeper.MODULE_CHIP_PURPLE);
            event.accept(CasinoKeeper.MODULE_CHIP_BLUE);
            event.accept(CasinoKeeper.MODULE_CHIP_BROWN);
            event.accept(CasinoKeeper.MODULE_CHIP_GREEN);
            event.accept(CasinoKeeper.MODULE_CHIP_RED);
            event.accept(CasinoKeeper.MODULE_CHIP_BLACK);
        }
        if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            event.accept(CasinoKeeper.DICE_BASIC_WHITE);
            event.accept(CasinoKeeper.DICE_BASIC_ORANGE);
            event.accept(CasinoKeeper.DICE_BASIC_MAGENTA);
            event.accept(CasinoKeeper.DICE_BASIC_LIGHT_BLUE);
            event.accept(CasinoKeeper.DICE_BASIC_YELLOW);
            event.accept(CasinoKeeper.DICE_BASIC_LIME);
            event.accept(CasinoKeeper.DICE_BASIC_PINK);
            event.accept(CasinoKeeper.DICE_BASIC_GRAY);
            event.accept(CasinoKeeper.DICE_BASIC_LIGHT_GRAY);
            event.accept(CasinoKeeper.DICE_BASIC_CYAN);
            event.accept(CasinoKeeper.DICE_BASIC_PURPLE);
            event.accept(CasinoKeeper.DICE_BASIC_BLUE);
            event.accept(CasinoKeeper.DICE_BASIC_BROWN);
            event.accept(CasinoKeeper.DICE_BASIC_GREEN);
            event.accept(CasinoKeeper.DICE_BASIC_RED);
            event.accept(CasinoKeeper.DICE_BASIC_BLACK);
        }
    }



}
