package mod.casinocraft;

import mod.casinocraft.block.*;
import mod.casinocraft.item.ItemRulebook;
import mod.casinocraft.menu.block.MenuArcade;
import mod.casinocraft.menu.block.MenuCardTable;
import mod.casinocraft.menu.block.MenuSlotMachine;
import mod.casinocraft.menu.game.*;
import mod.casinocraft.menu.other.MenuDummy;
import mod.casinocraft.menu.other.MenuSlotGame;
import mod.casinocraft.screen.ScreenMachine;
import mod.casinocraft.screen.game.*;
import mod.casinocraft.blockentity.BlockEntityArcade;
import mod.casinocraft.blockentity.BlockEntityCardTableBase;
import mod.casinocraft.blockentity.BlockEntityCardTableWide;
import mod.casinocraft.blockentity.BlockEntitySlotMachine;
import mod.casinocraft.screen.other.ScreenDummy;
import mod.casinocraft.screen.other.ScreenSlotGame;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static mod.casinocraft.CasinoCraft.MODID;

@SuppressWarnings({"unused", "deprecation"})
public class CasinoKeeper {

    private static final DeferredRegister<Block>              BLOCKS     = DeferredRegister.create(ForgeRegistries.BLOCKS,             MODID);
    private static final DeferredRegister<Item>               ITEMS      = DeferredRegister.create(ForgeRegistries.ITEMS,              MODID);
    private static final DeferredRegister<MenuType<?>>        CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,         MODID);
    private static final DeferredRegister<BlockEntityType<?>> TILES      = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    private static final DeferredRegister<SoundEvent>         SOUNDS     = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,       MODID);





    // ----- Card Tables ----- //
    public static final RegistryObject<Block> CARDTABLE_BASE_WHITE      = registerB("cardtable_base_white",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.WHITE),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_ORANGE     = registerB("cardtable_base_orange",     () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.ORANGE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_MAGENTA    = registerB("cardtable_base_magenta",    () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.MAGENTA),    CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_LIGHT_BLUE = registerB("cardtable_base_light_blue", () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_YELLOW     = registerB("cardtable_base_yellow",     () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.YELLOW),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_LIME       = registerB("cardtable_base_lime",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.LIME),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_PINK       = registerB("cardtable_base_pink",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.PINK),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_GRAY       = registerB("cardtable_base_gray",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.GRAY),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_LIGHT_GRAY = registerB("cardtable_base_light_gray", () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_CYAN       = registerB("cardtable_base_cyan",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.CYAN),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_PURPLE     = registerB("cardtable_base_purple",     () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.PURPLE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_BLUE       = registerB("cardtable_base_blue",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.BLUE),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_BROWN      = registerB("cardtable_base_brown",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.BROWN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_GREEN      = registerB("cardtable_base_green",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.GREEN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_RED        = registerB("cardtable_base_red",        () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.RED),        CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_BLACK      = registerB("cardtable_base_black",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.BLACK),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_WHITE      = registerB("cardtable_wide_white",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.WHITE),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_ORANGE     = registerB("cardtable_wide_orange",     () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.ORANGE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_MAGENTA    = registerB("cardtable_wide_magenta",    () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.MAGENTA),    CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_LIGHT_BLUE = registerB("cardtable_wide_light_blue", () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_YELLOW     = registerB("cardtable_wide_yellow",     () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.YELLOW),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_LIME       = registerB("cardtable_wide_lime",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.LIME),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_PINK       = registerB("cardtable_wide_pink",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.PINK),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_GRAY       = registerB("cardtable_wide_gray",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.GRAY),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_LIGHT_GRAY = registerB("cardtable_wide_light_gray", () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_CYAN       = registerB("cardtable_wide_cyan",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.CYAN),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_PURPLE     = registerB("cardtable_wide_purple",     () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.PURPLE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_BLUE       = registerB("cardtable_wide_blue",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.BLUE),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_BROWN      = registerB("cardtable_wide_brown",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.BROWN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_GREEN      = registerB("cardtable_wide_green",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.GREEN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_RED        = registerB("cardtable_wide_red",        () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.RED),        CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_BLACK      = registerB("cardtable_wide_black",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.BLACK),      CreativeModeTab.TAB_DECORATIONS);

    // ----- Arcades ----- //
    public static final RegistryObject<Block> ARCADE_BASE_WHITE      = registerB("arcade_base_white",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.WHITE),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_ORANGE     = registerB("arcade_base_orange",     () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.ORANGE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_MAGENTA    = registerB("arcade_base_magenta",    () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.MAGENTA),    CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_LIGHT_BLUE = registerB("arcade_base_light_blue", () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_YELLOW     = registerB("arcade_base_yellow",     () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.YELLOW),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_LIME       = registerB("arcade_base_lime",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIME),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_PINK       = registerB("arcade_base_pink",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.PINK),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_GRAY       = registerB("arcade_base_gray",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.GRAY),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_LIGHT_GRAY = registerB("arcade_base_light_gray", () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_CYAN       = registerB("arcade_base_cyan",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.CYAN),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_PURPLE     = registerB("arcade_base_purple",     () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.PURPLE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_BLUE       = registerB("arcade_base_blue",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BLUE),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_BROWN      = registerB("arcade_base_brown",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BROWN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_GREEN      = registerB("arcade_base_green",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.GREEN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_RED        = registerB("arcade_base_red",        () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.RED),        CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_BLACK      = registerB("arcade_base_black",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BLACK),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_WHITE      = registerB("arcade_slot_white",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.WHITE),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_ORANGE     = registerB("arcade_slot_orange",     () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.ORANGE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_MAGENTA    = registerB("arcade_slot_magenta",    () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.MAGENTA),    CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_LIGHT_BLUE = registerB("arcade_slot_light_blue", () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_YELLOW     = registerB("arcade_slot_yellow",     () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.YELLOW),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_LIME       = registerB("arcade_slot_lime",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIME),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_PINK       = registerB("arcade_slot_pink",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.PINK),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_GRAY       = registerB("arcade_slot_gray",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.GRAY),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_LIGHT_GRAY = registerB("arcade_slot_light_gray", () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_CYAN       = registerB("arcade_slot_cyan",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.CYAN),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_PURPLE     = registerB("arcade_slot_purple",     () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.PURPLE),     CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_BLUE       = registerB("arcade_slot_blue",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BLUE),       CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_BROWN      = registerB("arcade_slot_brown",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BROWN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_GREEN      = registerB("arcade_slot_green",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.GREEN),      CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_RED        = registerB("arcade_slot_red",        () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.RED),        CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_BLACK      = registerB("arcade_slot_black",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BLACK),      CreativeModeTab.TAB_DECORATIONS);
    
    // ----- Books ----- //
    public static final RegistryObject<Item> RULEBOOK_1_1 = registerI("rulebook_1_1", () -> new ItemRulebook(1, 11, false, new int[]{1            },  true, false,  1));  //  Roulette
    public static final RegistryObject<Item> RULEBOOK_2_1 = registerI("rulebook_2_1", () -> new ItemRulebook(5, 21, false, new int[]{1            },  true, false,  0));  //  BlackJack
    public static final RegistryObject<Item> RULEBOOK_2_2 = registerI("rulebook_2_2", () -> new ItemRulebook(5, 22, false, new int[]{1            },  true, false,  0));  //  Poker
    public static final RegistryObject<Item> RULEBOOK_3_1 = registerI("rulebook_3_1", () -> new ItemRulebook(3, 31, false, new int[]{1, 4, 3, 3, 2}, false,  true,  0));  //  Solitaire
    public static final RegistryObject<Item> RULEBOOK_3_2 = registerI("rulebook_3_2", () -> new ItemRulebook(3, 32, false, new int[]{2, 3, 5      }, false,  true,  0));  //  Pyramid
    public static final RegistryObject<Item> RULEBOOK_3_3 = registerI("rulebook_3_3", () -> new ItemRulebook(3, 33, false, new int[]{1            }, false,  true,  0));  //  MauMau
    public static final RegistryObject<Item> RULEBOOK_4_1 = registerI("rulebook_4_1", () -> new ItemRulebook(4, 41, false, new int[]{1            }, false,  true, -1));  //  Minesweeper
    public static final RegistryObject<Item> RULEBOOK_4_2 = registerI("rulebook_4_2", () -> new ItemRulebook(4, 42, false, new int[]{1            }, false,  true, -1));  //  Ishido
    public static final RegistryObject<Item> RULEBOOK_5_1 = registerI("rulebook_5_1", () -> new ItemRulebook(2, 51,  true, new int[]{3, 2, 3, 2   }, false,  true,  2));  //  Tetris
    public static final RegistryObject<Item> RULEBOOK_5_2 = registerI("rulebook_5_2", () -> new ItemRulebook(2, 52,  true, new int[]{1, 2         }, false,  true,  2));  //  2048
    public static final RegistryObject<Item> RULEBOOK_6_1 = registerI("rulebook_6_1", () -> new ItemRulebook(6, 61,  true, new int[]{1            }, false,  true,  2));  //  Snake
    public static final RegistryObject<Item> RULEBOOK_6_2 = registerI("rulebook_6_2", () -> new ItemRulebook(6, 62,  true, new int[]{1            }, false,  true,  2));  //  Sokoban
    public static final RegistryObject<Item> RULEBOOK_7_0 = registerI("rulebook_7_0", () -> new ItemRulebook(0, 70,  true, new int[]{1            }, false,  true,  3));  // Slot Machine
    
    // ----- Sounds ----- //
    public static final RegistryObject<SoundEvent> SOUND_PICKUP     = registerS("casinocraft.pickup",     () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.pickup")));
    public static final RegistryObject<SoundEvent> SOUND_IMPACT     = registerS("casinocraft.impact",     () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.impact")));
    public static final RegistryObject<SoundEvent> SOUND_MENU       = registerS("casinocraft.menu",       () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.menu")));
    public static final RegistryObject<SoundEvent> SOUND_PAUSE_ON   = registerS("casinocraft.pause.on",   () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.pause.on")));
    public static final RegistryObject<SoundEvent> SOUND_PAUSE_OFF  = registerS("casinocraft.pause.off",  () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.pause.off")));
    public static final RegistryObject<SoundEvent> SOUND_TETRIS     = registerS("casinocraft.tetris",     () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.tetris")));
    public static final RegistryObject<SoundEvent> SOUND_CARD_PLACE = registerS("casinocraft.card.place", () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.card.place")));
    public static final RegistryObject<SoundEvent> SOUND_CARD_SHOVE = registerS("casinocraft.card.shove", () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.card.shove")));
    public static final RegistryObject<SoundEvent> SOUND_ROULETTE   = registerS("casinocraft.roulette",   () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.roulette")));
    public static final RegistryObject<SoundEvent> SOUND_CHIP       = registerS("casinocraft.chip",       () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.chip")));
    public static final RegistryObject<SoundEvent> SOUND_DICE       = registerS("casinocraft.dice",       () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.dice")));
    public static final RegistryObject<SoundEvent> SOUND_REWARD     = registerS("casinocraft.reward",     () -> new SoundEvent(new ResourceLocation(MODID, "casinocraft.reward")));

    // ----- Textures Basic ----- //
    public static final ResourceLocation TEXTURE_STATIC            = new ResourceLocation(MODID, "textures/gui/static.png");
    public static final ResourceLocation TEXTURE_ARCADEDUMMY       = new ResourceLocation(MODID, "textures/gui/background/dummy.png");
    public static final ResourceLocation TEXTURE_SOKOBAN           = new ResourceLocation(MODID, "textures/gui/background/sokoban.png");
    public static final ResourceLocation TEXTURE_TETRIS            = new ResourceLocation(MODID, "textures/gui/background/tetris.png");
    public static final ResourceLocation TEXTURE_COLUMNS           = new ResourceLocation(MODID, "textures/gui/background/columns.png");
    public static final ResourceLocation TEXTURE_MEANMINOS         = new ResourceLocation(MODID, "textures/gui/background/puyo.png");
    public static final ResourceLocation TEXTURE_ROULETTE_LEFT     = new ResourceLocation(MODID, "textures/gui/background/roulette_left.png");
    public static final ResourceLocation TEXTURE_ROULETTE_RIGHT    = new ResourceLocation(MODID, "textures/gui/background/roulette_right.png");
    public static final ResourceLocation TEXTURE_ROULETTE_MIDDLE   = new ResourceLocation(MODID, "textures/gui/background/roulette_middle.png");
    public static final ResourceLocation TEXTURE_SICBO_LEFT        = new ResourceLocation(MODID, "textures/gui/background/sicbo_left.png");
    public static final ResourceLocation TEXTURE_SICBO_RIGHT       = new ResourceLocation(MODID, "textures/gui/background/sicbo_right.png");
    public static final ResourceLocation TEXTURE_SICBO_MIDDLE      = new ResourceLocation(MODID, "textures/gui/background/sicbo_middle.png");
    public static final ResourceLocation TEXTURE_CRAPS_LEFT        = new ResourceLocation(MODID, "textures/gui/background/craps_left.png");
    public static final ResourceLocation TEXTURE_CRAPS_RIGHT       = new ResourceLocation(MODID, "textures/gui/background/craps_right.png");
    public static final ResourceLocation TEXTURE_CRAPS_MIDDLE      = new ResourceLocation(MODID, "textures/gui/background/craps_middle.png");
    public static final ResourceLocation TEXTURE_2048_GROUND       = new ResourceLocation(MODID, "textures/gui/background/2048.png");
    public static final ResourceLocation TEXTURE_MINOFLIP          = new ResourceLocation(MODID, "textures/gui/background/minoflip.png");
    public static final ResourceLocation TEXTURE_FANTAN            = new ResourceLocation(MODID, "textures/gui/background/fantan.png");
    public static final ResourceLocation TEXTURE_SUDOKU            = new ResourceLocation(MODID, "textures/gui/background/sudoku.png");
    public static final ResourceLocation TEXTURE_SIMON             = new ResourceLocation(MODID, "textures/gui/spritesheet/simon.png");
    public static final ResourceLocation TEXTURE_DICE              = new ResourceLocation(MODID, "textures/gui/spritesheet/dice.png");
    public static final ResourceLocation TEXTURE_ARCADE            = new ResourceLocation(MODID, "textures/gui/spritesheet/arcade.png");
    public static final ResourceLocation TEXTURE_BUTTONS           = new ResourceLocation(MODID, "textures/gui/spritesheet/buttons.png");
    public static final ResourceLocation TEXTURE_MINOS             = new ResourceLocation(MODID, "textures/gui/spritesheet/minos.png");
    public static final ResourceLocation TEXTURE_MYSTICSQUARE      = new ResourceLocation(MODID, "textures/gui/spritesheet/mysticsquare.png");
    public static final ResourceLocation TEXTURE_2048              = new ResourceLocation(MODID, "textures/gui/spritesheet/2048.png");
    public static final ResourceLocation TEXTURE_FONT_ARCADE       = new ResourceLocation(MODID, "textures/gui/spritesheet/font_arcade.png");
    public static final ResourceLocation TEXTURE_FONT_CARDTABLE    = new ResourceLocation(MODID, "textures/gui/spritesheet/font_cardtable.png");
    public static final ResourceLocation TEXTURE_ROULETTE_WHEEL    = new ResourceLocation(MODID, "textures/gui/spritesheet/roulette_wheel.png");

    // ----- Texture Card Tables ----- //
    public static final ResourceLocation TEXTURE_GROUND_BLACK      = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_black.png");
    public static final ResourceLocation TEXTURE_GROUND_BLUE       = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_blue.png");
    public static final ResourceLocation TEXTURE_GROUND_BROWN      = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_brown.png");
    public static final ResourceLocation TEXTURE_GROUND_CYAN       = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_cyan.png");
    public static final ResourceLocation TEXTURE_GROUND_GRAY       = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_gray.png");
    public static final ResourceLocation TEXTURE_GROUND_GREEN      = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_green.png");
    public static final ResourceLocation TEXTURE_GROUND_LIGHT_BLUE = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_light_blue.png");
    public static final ResourceLocation TEXTURE_GROUND_LIME       = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_lime.png");
    public static final ResourceLocation TEXTURE_GROUND_MAGENTA    = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_magenta.png");
    public static final ResourceLocation TEXTURE_GROUND_ORANGE     = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_orange.png");
    public static final ResourceLocation TEXTURE_GROUND_PINK       = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_pink.png");
    public static final ResourceLocation TEXTURE_GROUND_PURPLE     = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_purple.png");
    public static final ResourceLocation TEXTURE_GROUND_RED        = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_red.png");
    public static final ResourceLocation TEXTURE_GROUND_LIGHT_GRAY = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_light_gray.png");
    public static final ResourceLocation TEXTURE_GROUND_WHITE      = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_white.png");
    public static final ResourceLocation TEXTURE_GROUND_YELLOW     = new ResourceLocation(MODID, "textures/gui/cardtable/cardtable_yellow.png");

    // ----- Texture Slot Machines ----- //
    public static final ResourceLocation TEXTURE_SLOTS_BLACK      = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_black.png");
    public static final ResourceLocation TEXTURE_SLOTS_BLUE       = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_blue.png");
    public static final ResourceLocation TEXTURE_SLOTS_BROWN      = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_brown.png");
    public static final ResourceLocation TEXTURE_SLOTS_CYAN       = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_cyan.png");
    public static final ResourceLocation TEXTURE_SLOTS_GRAY       = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_gray.png");
    public static final ResourceLocation TEXTURE_SLOTS_GREEN      = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_green.png");
    public static final ResourceLocation TEXTURE_SLOTS_LIGHT_BLUE = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_light_blue.png");
    public static final ResourceLocation TEXTURE_SLOTS_LIME       = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_lime.png");
    public static final ResourceLocation TEXTURE_SLOTS_MAGENTA    = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_magenta.png");
    public static final ResourceLocation TEXTURE_SLOTS_ORANGE     = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_orange.png");
    public static final ResourceLocation TEXTURE_SLOTS_PINK       = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_pink.png");
    public static final ResourceLocation TEXTURE_SLOTS_PURPLE     = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_purple.png");
    public static final ResourceLocation TEXTURE_SLOTS_RED        = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_red.png");
    public static final ResourceLocation TEXTURE_SLOTS_LIGHT_GRAY = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_light_gray.png");
    public static final ResourceLocation TEXTURE_SLOTS_WHITE      = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_white.png");
    public static final ResourceLocation TEXTURE_SLOTS_YELLOW     = new ResourceLocation(MODID, "textures/gui/slotmachine/slotmachine_yellow.png");

    // ----- Arcade Background ----- //
    public static final ResourceLocation TEXTURE_GROUND_ARCADE    = new ResourceLocation(MODID, "textures/gui/arcade/arcade.png");
    public static final ResourceLocation TEXTURE_PARALLAX_0_LOW   = new ResourceLocation(MODID, "textures/gui/arcade/parallax_1_low.png");
    public static final ResourceLocation TEXTURE_PARALLAX_0_HIGH  = new ResourceLocation(MODID, "textures/gui/arcade/parallax_1_high.png");
    public static final ResourceLocation TEXTURE_PARALLAX_1_LOW   = new ResourceLocation(MODID, "textures/gui/arcade/parallax_2_low.png");
    public static final ResourceLocation TEXTURE_PARALLAX_1_HIGH  = new ResourceLocation(MODID, "textures/gui/arcade/parallax_2_high.png");
    public static final ResourceLocation TEXTURE_PARALLAX_2_LOW   = new ResourceLocation(MODID, "textures/gui/arcade/parallax_3_low.png");
    public static final ResourceLocation TEXTURE_PARALLAX_2_HIGH  = new ResourceLocation(MODID, "textures/gui/arcade/parallax_3_high.png");
    public static final ResourceLocation TEXTURE_PARALLAX_3_LOW   = new ResourceLocation(MODID, "textures/gui/arcade/parallax_4_low.png");
    public static final ResourceLocation TEXTURE_PARALLAX_3_HIGH  = new ResourceLocation(MODID, "textures/gui/arcade/parallax_4_high.png");
    public static final ResourceLocation TEXTURE_PARALLAX_4_LOW   = new ResourceLocation(MODID, "textures/gui/arcade/parallax_5_low.png");
    public static final ResourceLocation TEXTURE_PARALLAX_4_HIGH  = new ResourceLocation(MODID, "textures/gui/arcade/parallax_5_high.png");
    public static final ResourceLocation TEXTURE_PARALLAX_5_LOW   = new ResourceLocation(MODID, "textures/gui/arcade/parallax_6_low.png");
    public static final ResourceLocation TEXTURE_PARALLAX_5_HIGH  = new ResourceLocation(MODID, "textures/gui/arcade/parallax_6_high.png");

    // ----- Texture Cards ----- //
    public static final ResourceLocation TEXTURE_CARDS_0_NOIR      = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_1_noir.png");
    public static final ResourceLocation TEXTURE_CARDS_0_ROUGE     = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_1_rouge.png");
    public static final ResourceLocation TEXTURE_CARDS_1_NOIR      = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_2_noir.png");
    public static final ResourceLocation TEXTURE_CARDS_1_ROUGE     = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_2_rouge.png");
    public static final ResourceLocation TEXTURE_CARDS_2_NOIR      = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_3_noir.png");
    public static final ResourceLocation TEXTURE_CARDS_2_ROUGE     = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_3_rouge.png");
    public static final ResourceLocation TEXTURE_CARDS_3_NOIR      = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_4_noir.png");
    public static final ResourceLocation TEXTURE_CARDS_3_ROUGE     = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_4_rouge.png");
    public static final ResourceLocation TEXTURE_CARDS_4_NOIR      = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_5_noir.png");
    public static final ResourceLocation TEXTURE_CARDS_4_ROUGE     = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_5_rouge.png");
    public static final ResourceLocation TEXTURE_CARDS_5_NOIR      = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_6_noir.png");
    public static final ResourceLocation TEXTURE_CARDS_5_ROUGE     = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_6_rouge.png");

    // ----- Texture Slot Wheels ----- //
    public static final ResourceLocation TEXTURE_SLOTGAME_0 = new ResourceLocation(MODID, "textures/gui/spritesheet/slotgame_1.png");
    public static final ResourceLocation TEXTURE_SLOTGAME_1 = new ResourceLocation(MODID, "textures/gui/spritesheet/slotgame_2.png");
    public static final ResourceLocation TEXTURE_SLOTGAME_2 = new ResourceLocation(MODID, "textures/gui/spritesheet/slotgame_3.png");
    public static final ResourceLocation TEXTURE_SLOTGAME_3 = new ResourceLocation(MODID, "textures/gui/spritesheet/slotgame_4.png");
    public static final ResourceLocation TEXTURE_SLOTGAME_4 = new ResourceLocation(MODID, "textures/gui/spritesheet/slotgame_5.png");
    public static final ResourceLocation TEXTURE_SLOTGAME_5 = new ResourceLocation(MODID, "textures/gui/spritesheet/slotgame_6.png");

    // ----- Tile Entity -- Arcade Base ----- //
    public static  final RegistryObject<BlockEntityType<BlockEntityArcade>> TILE_ARCADE_BASE = TILES.register("arcade_base", () -> BlockEntityType.Builder.of(BlockEntityArcade::new,
            ARCADE_BASE_BLACK.get(),
            ARCADE_BASE_BLUE.get(),
            ARCADE_BASE_BROWN.get(),
            ARCADE_BASE_CYAN.get(),
            ARCADE_BASE_GRAY.get(),
            ARCADE_BASE_GREEN.get(),
            ARCADE_BASE_LIGHT_BLUE.get(),
            ARCADE_BASE_LIME.get(),
            ARCADE_BASE_MAGENTA.get(),
            ARCADE_BASE_ORANGE.get(),
            ARCADE_BASE_PINK.get(),
            ARCADE_BASE_PURPLE.get(),
            ARCADE_BASE_RED.get(),
            ARCADE_BASE_LIGHT_GRAY.get(),
            ARCADE_BASE_WHITE.get(),
            ARCADE_BASE_YELLOW.get()
    ).build(null));

    // ----- Tile Entity -- Slot Machine ----- //
    public static  final RegistryObject<BlockEntityType<BlockEntitySlotMachine>> TILE_ARCADE_SLOT = TILES.register("arcade_slot", () -> BlockEntityType.Builder.of(BlockEntitySlotMachine::new,
            ARCADE_SLOT_BLACK.get(),
            ARCADE_SLOT_BLUE.get(),
            ARCADE_SLOT_BROWN.get(),
            ARCADE_SLOT_CYAN.get(),
            ARCADE_SLOT_GRAY.get(),
            ARCADE_SLOT_GREEN.get(),
            ARCADE_SLOT_LIGHT_BLUE.get(),
            ARCADE_SLOT_LIME.get(),
            ARCADE_SLOT_MAGENTA.get(),
            ARCADE_SLOT_ORANGE.get(),
            ARCADE_SLOT_PINK.get(),
            ARCADE_SLOT_PURPLE.get(),
            ARCADE_SLOT_RED.get(),
            ARCADE_SLOT_LIGHT_GRAY.get(),
            ARCADE_SLOT_WHITE.get(),
            ARCADE_SLOT_YELLOW.get()
    ).build(null));

    // ----- Tile Entity -- Card Table Base ----- //
    public static  final RegistryObject<BlockEntityType<BlockEntityCardTableBase>> TILE_CARDTABLE_BASE = TILES.register("cardtable_base", () -> BlockEntityType.Builder.of(BlockEntityCardTableBase::new,
            CARDTABLE_BASE_BLACK.get(),
            CARDTABLE_BASE_BLUE.get(),
            CARDTABLE_BASE_BROWN.get(),
            CARDTABLE_BASE_CYAN.get(),
            CARDTABLE_BASE_GRAY.get(),
            CARDTABLE_BASE_GREEN.get(),
            CARDTABLE_BASE_LIGHT_BLUE.get(),
            CARDTABLE_BASE_LIME.get(),
            CARDTABLE_BASE_MAGENTA.get(),
            CARDTABLE_BASE_ORANGE.get(),
            CARDTABLE_BASE_PINK.get(),
            CARDTABLE_BASE_PURPLE.get(),
            CARDTABLE_BASE_RED.get(),
            CARDTABLE_BASE_LIGHT_GRAY.get(),
            CARDTABLE_BASE_WHITE.get(),
            CARDTABLE_BASE_YELLOW.get()
    ).build(null));

    // ----- Tile Entity -- Card Table Wide ----- //
    public static  final RegistryObject<BlockEntityType<BlockEntityCardTableWide>> TILE_CARDTABLE_WIDE = TILES.register("cardtable_wide", () -> BlockEntityType.Builder.of(BlockEntityCardTableWide::new,
            CARDTABLE_WIDE_BLACK.get(),
            CARDTABLE_WIDE_BLUE.get(),
            CARDTABLE_WIDE_BROWN.get(),
            CARDTABLE_WIDE_CYAN.get(),
            CARDTABLE_WIDE_GRAY.get(),
            CARDTABLE_WIDE_GREEN.get(),
            CARDTABLE_WIDE_LIGHT_BLUE.get(),
            CARDTABLE_WIDE_LIME.get(),
            CARDTABLE_WIDE_MAGENTA.get(),
            CARDTABLE_WIDE_ORANGE.get(),
            CARDTABLE_WIDE_PINK.get(),
            CARDTABLE_WIDE_PURPLE.get(),
            CARDTABLE_WIDE_RED.get(),
            CARDTABLE_WIDE_LIGHT_GRAY.get(),
            CARDTABLE_WIDE_WHITE.get(),
            CARDTABLE_WIDE_YELLOW.get()
    ).build(null));

    // ----- Container ----- //
    public static final RegistryObject<MenuType<MenuDummy>>       MENU_DUMMY       = CONTAINERS.register("dummy",       () -> IForgeMenuType.create(MenuDummy::new));
    public static final RegistryObject<MenuType<MenuArcade>>      MENU_ARCADE      = CONTAINERS.register("arcade",      () -> IForgeMenuType.create(MenuArcade::new));
    public static final RegistryObject<MenuType<MenuCardTable>>   MENU_CARDTABLE   = CONTAINERS.register("cardtable",   () -> IForgeMenuType.create(MenuCardTable::new));
    public static final RegistryObject<MenuType<MenuSlotMachine>> MENU_SLOTMACHINE = CONTAINERS.register("slotmachine", () -> IForgeMenuType.create(MenuSlotMachine::new));
    public static final RegistryObject<MenuType<MenuSlotGame>>    MENU_SLOTGAME    = CONTAINERS.register("slotgame",    () -> IForgeMenuType.create(MenuSlotGame::new));
    public static final RegistryObject<MenuType<Menu11>>          MENU_11          = CONTAINERS.register("game_11",     () -> IForgeMenuType.create(Menu11::new));
    public static final RegistryObject<MenuType<Menu21>>          MENU_21          = CONTAINERS.register("game_21",     () -> IForgeMenuType.create(Menu21::new));
    public static final RegistryObject<MenuType<Menu22>>          MENU_22          = CONTAINERS.register("game_22",     () -> IForgeMenuType.create(Menu22::new));
    public static final RegistryObject<MenuType<Menu31>>          MENU_31          = CONTAINERS.register("game_31",     () -> IForgeMenuType.create(Menu31::new));
    public static final RegistryObject<MenuType<Menu32>>          MENU_32          = CONTAINERS.register("game_32",     () -> IForgeMenuType.create(Menu32::new));
    public static final RegistryObject<MenuType<Menu33>>          MENU_33          = CONTAINERS.register("game_33",     () -> IForgeMenuType.create(Menu33::new));
    public static final RegistryObject<MenuType<Menu41>>          MENU_41          = CONTAINERS.register("game_41",     () -> IForgeMenuType.create(Menu41::new));
    public static final RegistryObject<MenuType<Menu42>>          MENU_42          = CONTAINERS.register("game_42",     () -> IForgeMenuType.create(Menu42::new));
    public static final RegistryObject<MenuType<Menu51>>          MENU_51          = CONTAINERS.register("game_51",     () -> IForgeMenuType.create(Menu51::new));
    public static final RegistryObject<MenuType<Menu52>>          MENU_52          = CONTAINERS.register("game_52",     () -> IForgeMenuType.create(Menu52::new));
    public static final RegistryObject<MenuType<Menu61>>          MENU_61          = CONTAINERS.register("game_61",     () -> IForgeMenuType.create(Menu61::new));
    public static final RegistryObject<MenuType<Menu62>>          MENU_62          = CONTAINERS.register("game_62",     () -> IForgeMenuType.create(Menu62::new));





    //----------------------------------------REGISTER----------------------------------------//

    static void register(){
        BLOCKS.register(    FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(     FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(     FMLJavaModLoadingContext.get().getModEventBus());
        SOUNDS.register(    FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static RegistryObject<Block> registerB(String name,  java.util.function.Supplier<? extends Block> block){
        return registerB(name, block, null);
    }

    // private static RegistryObject<Block> registerB(String name,  java.util.function.Supplier<? extends Block> block, boolean useItemBlock){
    //     RegistryObject<Block> rob = BLOCKS.register(name, block);
    //     if(useItemBlock){ ITEMS.register(name, () -> new BlockItem(rob.get(), (new Item.Properties()))); }
    //     return rob;
    // }
    
    private static RegistryObject<Block> registerB(String name,  java.util.function.Supplier<? extends Block> block, CreativeModeTab tab){
        RegistryObject<Block> rob = BLOCKS.register(name, block);
        if(tab != null){ ITEMS.register(name, () -> new BlockItem(rob.get(), (new Item.Properties()).tab(tab))); }
        return rob;
    }

    private static RegistryObject<Item> registerI(String name, java.util.function.Supplier<? extends Item> item){
        return ITEMS.register(name, item);
    }

    private static RegistryObject<SoundEvent> registerS(String name, java.util.function.Supplier<? extends SoundEvent> sound){
        return SOUNDS.register(name, sound);
    }

    // Conveniance function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Conveniance function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block, Item.Properties prop) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), prop));
    }

    // Conveniance function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block, CreativeModeTab tab) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), (new Item.Properties()) ));
    }

    private static RegistryObject<Block> register(String name, Block block){
        return register(name, block, null);
    }

    private static RegistryObject<Block> register(String name, Block block, CreativeModeTab tab){
        if(tab != null){ ITEMS.register(name, () -> new BlockItem(block, (new Item.Properties()))); }
        return BLOCKS.register(name, () -> block);
    }

    private static RegistryObject<Item> register(String name, Item item){
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<SoundEvent> register(String name, SoundEvent sound){
        return SOUNDS.register(name, () -> sound);
    }





    //----------------------------------------SETUP----------------------------------------//

    static void setup(FMLCommonSetupEvent event){

    }

    @OnlyIn(Dist.CLIENT)
    static void setup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            MenuScreens.register(MENU_DUMMY.get(      ), ScreenDummy::new);
            MenuScreens.register(MENU_ARCADE.get(     ), ScreenMachine::new);
            MenuScreens.register(MENU_CARDTABLE.get(  ), ScreenMachine::new);
            MenuScreens.register(MENU_SLOTMACHINE.get(), ScreenMachine::new);
            MenuScreens.register(MENU_SLOTGAME.get(   ), ScreenSlotGame::new);
            MenuScreens.register(MENU_11.get(         ), Screen11::new);
            MenuScreens.register(MENU_21.get(         ), Screen21::new);
            MenuScreens.register(MENU_22.get(         ), Screen22::new);
            MenuScreens.register(MENU_31.get(         ), Screen31::new);
            MenuScreens.register(MENU_32.get(         ), Screen32::new);
            MenuScreens.register(MENU_33.get(         ), Screen33::new);
            MenuScreens.register(MENU_41.get(         ), Screen41::new);
            MenuScreens.register(MENU_42.get(         ), Screen42::new);
            MenuScreens.register(MENU_51.get(         ), Screen51::new);
            MenuScreens.register(MENU_52.get(         ), Screen52::new);
            MenuScreens.register(MENU_61.get(         ), Screen61::new);
            MenuScreens.register(MENU_62.get(         ), Screen62::new);
        });
    }
    
    
    
}
