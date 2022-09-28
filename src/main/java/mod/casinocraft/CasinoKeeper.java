package mod.casinocraft;

import mod.casinocraft.block.*;
import mod.casinocraft.menu.block.MenuArcade;
import mod.casinocraft.menu.block.MenuCardTable;
import mod.casinocraft.menu.block.MenuSlotMachine;
import mod.casinocraft.menu.card.*;
import mod.casinocraft.menu.mino.*;
import mod.casinocraft.menu.chip.*;
import mod.casinocraft.menu.other.MenuDummy;
import mod.casinocraft.menu.other.MenuSlotGame;
import mod.casinocraft.screen.ScreenMachine;
import mod.casinocraft.screen.card.*;
import mod.casinocraft.screen.mino.*;
import mod.casinocraft.screen.chip.*;
import mod.casinocraft.screen.other.ScreenDummy;
import mod.casinocraft.screen.other.ScreenSlotGame;
import mod.casinocraft.blockentity.BlockEntityArcade;
import mod.casinocraft.blockentity.BlockEntityCardTableBase;
import mod.casinocraft.blockentity.BlockEntityCardTableWide;
import mod.casinocraft.blockentity.BlockEntitySlotMachine;
import mod.lucky77.item.ItemItem;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static mod.casinocraft.CasinoCraft.MODID;

@SuppressWarnings({"unused", "deprecation"})
public class CasinoKeeper {

    private static final DeferredRegister<Block>              BLOCKS     = DeferredRegister.create(ForgeRegistries.BLOCKS,             MODID);
    private static final DeferredRegister<Item>               ITEMS      = DeferredRegister.create(ForgeRegistries.ITEMS,              MODID);
    private static final DeferredRegister<MenuType<?>>        CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,         MODID);
    private static final DeferredRegister<BlockEntityType<?>> TILES      = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,     MODID);
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

    // ----- Dice Blocks ----- //
    public static final RegistryObject<Block> DICE_BASIC_WHITE      = registerB("dice_basic_white",      () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_ORANGE     = registerB("dice_basic_orange",     () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_MAGENTA    = registerB("dice_basic_magenta",    () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_LIGHT_BLUE = registerB("dice_basic_light_blue", () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_YELLOW     = registerB("dice_basic_yellow",     () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_LIME       = registerB("dice_basic_lime",       () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_PINK       = registerB("dice_basic_pink",       () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_GRAY       = registerB("dice_basic_gray",       () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_LIGHT_GRAY = registerB("dice_basic_light_gray", () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_CYAN       = registerB("dice_basic_cyan",       () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_PURPLE     = registerB("dice_basic_purple",     () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_BLUE       = registerB("dice_basic_blue",       () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_BROWN      = registerB("dice_basic_brown",      () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_GREEN      = registerB("dice_basic_green",      () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_RED        = registerB("dice_basic_red",        () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_BLACK      = registerB("dice_basic_black",      () -> new BlockDice(Blocks.TERRACOTTA), CreativeModeTab.TAB_DECORATIONS);

    // ----- Modules ----- //
    public static final RegistryObject<Item> MODULE_MINO_WHITE      = registerI("module_mino_white",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_ORANGE     = registerI("module_mino_orange",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_MAGENTA    = registerI("module_mino_magenta",    () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_LIGHT_BLUE = registerI("module_mino_light_blue", () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_YELLOW     = registerI("module_mino_yellow",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_LIME       = registerI("module_mino_lime",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_PINK       = registerI("module_mino_pink",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_GRAY       = registerI("module_mino_gray",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_LIGHT_GRAY = registerI("module_mino_light_gray", () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_CYAN       = registerI("module_mino_cyan",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_PURPLE     = registerI("module_mino_purple",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_BLUE       = registerI("module_mino_blue",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_BROWN      = registerI("module_mino_brown",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_GREEN      = registerI("module_mino_green",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_RED        = registerI("module_mino_red",        () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_MINO_BLACK      = registerI("module_mino_black",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_WHITE      = registerI("module_chip_white",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_ORANGE     = registerI("module_chip_orange",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_MAGENTA    = registerI("module_chip_magenta",    () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_LIGHT_BLUE = registerI("module_chip_light_blue", () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_YELLOW     = registerI("module_chip_yellow",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_LIME       = registerI("module_chip_lime",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_PINK       = registerI("module_chip_pink",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_GRAY       = registerI("module_chip_gray",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_LIGHT_GRAY = registerI("module_chip_light_gray", () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_CYAN       = registerI("module_chip_cyan",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_PURPLE     = registerI("module_chip_purple",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_BLUE       = registerI("module_chip_blue",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_BROWN      = registerI("module_chip_brown",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_GREEN      = registerI("module_chip_green",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_RED        = registerI("module_chip_red",        () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CHIP_BLACK      = registerI("module_chip_black",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_WHITE      = registerI("module_card_white",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_ORANGE     = registerI("module_card_orange",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_MAGENTA    = registerI("module_card_magenta",    () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_LIGHT_BLUE = registerI("module_card_light_blue", () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_YELLOW     = registerI("module_card_yellow",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_LIME       = registerI("module_card_lime",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_PINK       = registerI("module_card_pink",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_GRAY       = registerI("module_card_gray",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_LIGHT_GRAY = registerI("module_card_light_gray", () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_CYAN       = registerI("module_card_cyan",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_PURPLE     = registerI("module_card_purple",     () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_BLUE       = registerI("module_card_blue",       () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_BROWN      = registerI("module_card_brown",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_GREEN      = registerI("module_card_green",      () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_RED        = registerI("module_card_red",        () -> new ItemItem(CreativeModeTab.TAB_MISC));
    public static final RegistryObject<Item> MODULE_CARD_BLACK      = registerI("module_card_black",      () -> new ItemItem(CreativeModeTab.TAB_MISC));

    // ----- Books ----- //
    //public static final RegistryObject<Item> BOOK_ARCADE      = register("book_arcade",      new ItemBookArcade());

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
    public static final RegistryObject<MenuType<MenuDummy>>         CONTAINER_DUMMY           = CONTAINERS.register("dummy",           () -> IForgeMenuType.create(MenuDummy::new));
    public static final RegistryObject<MenuType<MenuArcade>>        CONTAINER_ARCADE          = CONTAINERS.register("arcade",          () -> IForgeMenuType.create(MenuArcade::new));
    public static final RegistryObject<MenuType<MenuCardTable>>     CONTAINER_CARDTABLE       = CONTAINERS.register("cardtable",       () -> IForgeMenuType.create(MenuCardTable::new));
    public static final RegistryObject<MenuType<MenuSlotMachine>>   CONTAINER_SLOTMACHINE     = CONTAINERS.register("slotmachine",     () -> IForgeMenuType.create(MenuSlotMachine::new));
    public static final RegistryObject<MenuType<MenuSlotGame>>      CONTAINER_SLOTGAME        = CONTAINERS.register("slotgame",        () -> IForgeMenuType.create(MenuSlotGame::new));
    public static final RegistryObject<MenuType<MenuCardWhite>>     CONTAINER_CARD_WHITE      = CONTAINERS.register("card_white",      () -> IForgeMenuType.create(MenuCardWhite::new));
    public static final RegistryObject<MenuType<MenuCardOrange>>    CONTAINER_CARD_ORANGE     = CONTAINERS.register("card_orange",     () -> IForgeMenuType.create(MenuCardOrange::new));
    public static final RegistryObject<MenuType<MenuCardMagenta>>   CONTAINER_CARD_MAGENTA    = CONTAINERS.register("card_magenta",    () -> IForgeMenuType.create(MenuCardMagenta::new));
    public static final RegistryObject<MenuType<MenuCardLightBlue>> CONTAINER_CARD_LIGHT_BLUE = CONTAINERS.register("card_light_blue", () -> IForgeMenuType.create(MenuCardLightBlue::new));
    public static final RegistryObject<MenuType<MenuCardYellow>>    CONTAINER_CARD_YELLOW     = CONTAINERS.register("card_yellow",     () -> IForgeMenuType.create(MenuCardYellow::new));
    public static final RegistryObject<MenuType<MenuCardLime>>      CONTAINER_CARD_LIME       = CONTAINERS.register("card_lime",       () -> IForgeMenuType.create(MenuCardLime::new));
    public static final RegistryObject<MenuType<MenuCardPink>>      CONTAINER_CARD_PINK       = CONTAINERS.register("card_pink",       () -> IForgeMenuType.create(MenuCardPink::new));
    public static final RegistryObject<MenuType<MenuCardGray>>      CONTAINER_CARD_GRAY       = CONTAINERS.register("card_gray",       () -> IForgeMenuType.create(MenuCardGray::new));
    public static final RegistryObject<MenuType<MenuCardLightGray>> CONTAINER_CARD_LIGHT_GRAY = CONTAINERS.register("card_light_gray", () -> IForgeMenuType.create(MenuCardLightGray::new));
    public static final RegistryObject<MenuType<MenuCardCyan>>      CONTAINER_CARD_CYAN       = CONTAINERS.register("card_cyan",       () -> IForgeMenuType.create(MenuCardCyan::new));
    public static final RegistryObject<MenuType<MenuCardPurple>>    CONTAINER_CARD_PURPLE     = CONTAINERS.register("card_purple",     () -> IForgeMenuType.create(MenuCardPurple::new));
    public static final RegistryObject<MenuType<MenuCardBlue>>      CONTAINER_CARD_BLUE       = CONTAINERS.register("card_blue",       () -> IForgeMenuType.create(MenuCardBlue::new));
    public static final RegistryObject<MenuType<MenuCardBrown>>     CONTAINER_CARD_BROWN      = CONTAINERS.register("card_brown",      () -> IForgeMenuType.create(MenuCardBrown::new));
    public static final RegistryObject<MenuType<MenuCardGreen>>     CONTAINER_CARD_GREEN      = CONTAINERS.register("card_green",      () -> IForgeMenuType.create(MenuCardGreen::new));
    public static final RegistryObject<MenuType<MenuCardRed>>       CONTAINER_CARD_RED        = CONTAINERS.register("card_red",        () -> IForgeMenuType.create(MenuCardRed::new));
    public static final RegistryObject<MenuType<MenuCardBlack>>     CONTAINER_CARD_BLACK      = CONTAINERS.register("card_black",      () -> IForgeMenuType.create(MenuCardBlack::new));
    public static final RegistryObject<MenuType<MenuMinoWhite>>     CONTAINER_MINO_WHITE      = CONTAINERS.register("mino_white",      () -> IForgeMenuType.create(MenuMinoWhite::new));
    public static final RegistryObject<MenuType<MenuMinoOrange>>    CONTAINER_MINO_ORANGE     = CONTAINERS.register("mino_orange",     () -> IForgeMenuType.create(MenuMinoOrange::new));
    public static final RegistryObject<MenuType<MenuMinoMagenta>>   CONTAINER_MINO_MAGENTA    = CONTAINERS.register("mino_magenta",    () -> IForgeMenuType.create(MenuMinoMagenta::new));
    public static final RegistryObject<MenuType<MenuMinoLightBlue>> CONTAINER_MINO_LIGHT_BLUE = CONTAINERS.register("mino_light_blue", () -> IForgeMenuType.create(MenuMinoLightBlue::new));
    public static final RegistryObject<MenuType<MenuMinoYellow>>    CONTAINER_MINO_YELLOW     = CONTAINERS.register("mino_yellow",     () -> IForgeMenuType.create(MenuMinoYellow::new));
    public static final RegistryObject<MenuType<MenuMinoLime>>      CONTAINER_MINO_LIME       = CONTAINERS.register("mino_lime",       () -> IForgeMenuType.create(MenuMinoLime::new));
    public static final RegistryObject<MenuType<MenuMinoPink>>      CONTAINER_MINO_PINK       = CONTAINERS.register("mino_pink",       () -> IForgeMenuType.create(MenuMinoPink::new));
    public static final RegistryObject<MenuType<MenuMinoGray>>      CONTAINER_MINO_GRAY       = CONTAINERS.register("mino_gray",       () -> IForgeMenuType.create(MenuMinoGray::new));
    public static final RegistryObject<MenuType<MenuMinoLightGray>> CONTAINER_MINO_LIGHT_GRAY = CONTAINERS.register("mino_light_gray", () -> IForgeMenuType.create(MenuMinoLightGray::new));
    public static final RegistryObject<MenuType<MenuMinoCyan>>      CONTAINER_MINO_CYAN       = CONTAINERS.register("mino_cyan",       () -> IForgeMenuType.create(MenuMinoCyan::new));
    public static final RegistryObject<MenuType<MenuMinoPurple>>    CONTAINER_MINO_PURPLE     = CONTAINERS.register("mino_purple",     () -> IForgeMenuType.create(MenuMinoPurple::new));
    public static final RegistryObject<MenuType<MenuMinoBlue>>      CONTAINER_MINO_BLUE       = CONTAINERS.register("mino_blue",       () -> IForgeMenuType.create(MenuMinoBlue::new));
    public static final RegistryObject<MenuType<MenuMinoBrown>>     CONTAINER_MINO_BROWN      = CONTAINERS.register("mino_brown",      () -> IForgeMenuType.create(MenuMinoBrown::new));
    public static final RegistryObject<MenuType<MenuMinoGreen>>     CONTAINER_MINO_GREEN      = CONTAINERS.register("mino_green",      () -> IForgeMenuType.create(MenuMinoGreen::new));
    public static final RegistryObject<MenuType<MenuMinoRed>>       CONTAINER_MINO_RED        = CONTAINERS.register("mino_red",        () -> IForgeMenuType.create(MenuMinoRed::new));
    public static final RegistryObject<MenuType<MenuMinoBlack>>     CONTAINER_MINO_BLACK      = CONTAINERS.register("mino_black",      () -> IForgeMenuType.create(MenuMinoBlack::new));
    public static final RegistryObject<MenuType<MenuChipWhite>>     CONTAINER_CHIP_WHITE      = CONTAINERS.register("chip_white",      () -> IForgeMenuType.create(MenuChipWhite::new));
    public static final RegistryObject<MenuType<MenuChipOrange>>    CONTAINER_CHIP_ORANGE     = CONTAINERS.register("chip_orange",     () -> IForgeMenuType.create(MenuChipOrange::new));
    public static final RegistryObject<MenuType<MenuChipMagenta>>   CONTAINER_CHIP_MAGENTA    = CONTAINERS.register("chip_magenta",    () -> IForgeMenuType.create(MenuChipMagenta::new));
    public static final RegistryObject<MenuType<MenuChipLightBlue>> CONTAINER_CHIP_LIGHT_BLUE = CONTAINERS.register("chip_light_blue", () -> IForgeMenuType.create(MenuChipLightBlue::new));
    public static final RegistryObject<MenuType<MenuChipYellow>>    CONTAINER_CHIP_YELLOW     = CONTAINERS.register("chip_yellow",     () -> IForgeMenuType.create(MenuChipYellow::new));
    public static final RegistryObject<MenuType<MenuChipLime>>      CONTAINER_CHIP_LIME       = CONTAINERS.register("chip_lime",       () -> IForgeMenuType.create(MenuChipLime::new));
    public static final RegistryObject<MenuType<MenuChipPink>>      CONTAINER_CHIP_PINK       = CONTAINERS.register("chip_pink",       () -> IForgeMenuType.create(MenuChipPink::new));
    public static final RegistryObject<MenuType<MenuChipGray>>      CONTAINER_CHIP_GRAY       = CONTAINERS.register("chip_gray",       () -> IForgeMenuType.create(MenuChipGray::new));
    public static final RegistryObject<MenuType<MenuChipLightGray>> CONTAINER_CHIP_LIGHT_GRAY = CONTAINERS.register("chip_light_gray", () -> IForgeMenuType.create(MenuChipLightGray::new));
    public static final RegistryObject<MenuType<MenuChipCyan>>      CONTAINER_CHIP_CYAN       = CONTAINERS.register("chip_cyan",       () -> IForgeMenuType.create(MenuChipCyan::new));
    public static final RegistryObject<MenuType<MenuChipPurple>>    CONTAINER_CHIP_PURPLE     = CONTAINERS.register("chip_purple",     () -> IForgeMenuType.create(MenuChipPurple::new));
    public static final RegistryObject<MenuType<MenuChipBlue>>      CONTAINER_CHIP_BLUE       = CONTAINERS.register("chip_blue",       () -> IForgeMenuType.create(MenuChipBlue::new));
    public static final RegistryObject<MenuType<MenuChipBrown>>     CONTAINER_CHIP_BROWN      = CONTAINERS.register("chip_brown",      () -> IForgeMenuType.create(MenuChipBrown::new));
    public static final RegistryObject<MenuType<MenuChipGreen>>     CONTAINER_CHIP_GREEN      = CONTAINERS.register("chip_green",      () -> IForgeMenuType.create(MenuChipGreen::new));
    public static final RegistryObject<MenuType<MenuChipRed>>       CONTAINER_CHIP_RED        = CONTAINERS.register("chip_red",        () -> IForgeMenuType.create(MenuChipRed::new));
    public static final RegistryObject<MenuType<MenuChipBlack>>     CONTAINER_CHIP_BLACK      = CONTAINERS.register("chip_black",      () -> IForgeMenuType.create(MenuChipBlack::new));





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
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), (new Item.Properties()).tab(tab) ));
    }

    private static RegistryObject<Block> register(String name, Block block){
        return register(name, block, null);
    }

    private static RegistryObject<Block> register(String name, Block block, CreativeModeTab tab){
        if(tab != null){ ITEMS.register(name, () -> new BlockItem(block, (new Item.Properties()).tab(tab))); }
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

            MenuScreens.register(CONTAINER_DUMMY.get(),           ScreenDummy::new);
            MenuScreens.register(CONTAINER_ARCADE.get(),          ScreenMachine::new);
            MenuScreens.register(CONTAINER_CARDTABLE.get(),       ScreenMachine::new);
            MenuScreens.register(CONTAINER_SLOTMACHINE.get(),     ScreenMachine::new);
            MenuScreens.register(CONTAINER_SLOTGAME.get(),        ScreenSlotGame::new);
            MenuScreens.register(CONTAINER_CARD_WHITE.get(),      ScreenCardWhite::new);
            MenuScreens.register(CONTAINER_CARD_ORANGE.get(),     ScreenCardOrange::new);
            MenuScreens.register(CONTAINER_CARD_MAGENTA.get(),    ScreenCardMagenta::new);
            MenuScreens.register(CONTAINER_CARD_LIGHT_BLUE.get(), ScreenCardLightBlue::new);
            MenuScreens.register(CONTAINER_CARD_YELLOW.get(),     ScreenCardYellow::new);
            MenuScreens.register(CONTAINER_CARD_LIME.get(),       ScreenCardLime::new);
            MenuScreens.register(CONTAINER_CARD_PINK.get(),       ScreenCardPink::new);
            MenuScreens.register(CONTAINER_CARD_GRAY.get(),       ScreenCardGray::new);
            MenuScreens.register(CONTAINER_CARD_LIGHT_GRAY.get(), ScreenCardLightGray::new);
            MenuScreens.register(CONTAINER_CARD_CYAN.get(),       ScreenCardCyan::new);
            MenuScreens.register(CONTAINER_CARD_PURPLE.get(),     ScreenCardPurple::new);
            MenuScreens.register(CONTAINER_CARD_BLUE.get(),       ScreenCardBlue::new);
            MenuScreens.register(CONTAINER_CARD_BROWN.get(),      ScreenCardBrown::new);
            MenuScreens.register(CONTAINER_CARD_GREEN.get(),      ScreenCardGreen::new);
            MenuScreens.register(CONTAINER_CARD_RED.get(),        ScreenCardRed::new);
            MenuScreens.register(CONTAINER_CARD_BLACK.get(),      ScreenCardBlack::new);
            MenuScreens.register(CONTAINER_MINO_WHITE.get(),      ScreenMinoWhite::new);
            MenuScreens.register(CONTAINER_MINO_ORANGE.get(),     ScreenMinoOrange::new);
            MenuScreens.register(CONTAINER_MINO_MAGENTA.get(),    ScreenMinoMagenta::new);
            MenuScreens.register(CONTAINER_MINO_LIGHT_BLUE.get(), ScreenMinoLightBlue::new);
            MenuScreens.register(CONTAINER_MINO_YELLOW.get(),     ScreenMinoYellow::new);
            MenuScreens.register(CONTAINER_MINO_LIME.get(),       ScreenMinoLime::new);
            MenuScreens.register(CONTAINER_MINO_PINK.get(),       ScreenMinoPink::new);
            MenuScreens.register(CONTAINER_MINO_GRAY.get(),       ScreenMinoGray::new);
            MenuScreens.register(CONTAINER_MINO_LIGHT_GRAY.get(), ScreenMinoLightGray::new);
            MenuScreens.register(CONTAINER_MINO_CYAN.get(),       ScreenMinoCyan::new);
            MenuScreens.register(CONTAINER_MINO_PURPLE.get(),     ScreenMinoPurple::new);
            MenuScreens.register(CONTAINER_MINO_BLUE.get(),       ScreenMinoBlue::new);
            MenuScreens.register(CONTAINER_MINO_BROWN.get(),      ScreenMinoBrown::new);
            MenuScreens.register(CONTAINER_MINO_GREEN.get(),      ScreenMinoGreen::new);
            MenuScreens.register(CONTAINER_MINO_RED.get(),        ScreenMinoRed::new);
            MenuScreens.register(CONTAINER_MINO_BLACK.get(),      ScreenMinoBlack::new);
            MenuScreens.register(CONTAINER_CHIP_WHITE.get(),      ScreenChipWhite::new);
            MenuScreens.register(CONTAINER_CHIP_ORANGE.get(),     ScreenChipOrange::new);
            MenuScreens.register(CONTAINER_CHIP_MAGENTA.get(),    ScreenChipMagenta::new);
            MenuScreens.register(CONTAINER_CHIP_LIGHT_BLUE.get(), ScreenChipLightBlue::new);
            MenuScreens.register(CONTAINER_CHIP_YELLOW.get(),     ScreenChipYellow::new);
            MenuScreens.register(CONTAINER_CHIP_LIME.get(),       ScreenChipLime::new);
            MenuScreens.register(CONTAINER_CHIP_PINK.get(),       ScreenChipPink::new);
            MenuScreens.register(CONTAINER_CHIP_GRAY.get(),       ScreenChipGray::new);
            MenuScreens.register(CONTAINER_CHIP_LIGHT_GRAY.get(), ScreenChipLightGray::new);
            MenuScreens.register(CONTAINER_CHIP_CYAN.get(),       ScreenChipCyan::new);
            MenuScreens.register(CONTAINER_CHIP_PURPLE.get(),     ScreenChipPurple::new);
            MenuScreens.register(CONTAINER_CHIP_BLUE.get(),       ScreenChipBlue::new);
            MenuScreens.register(CONTAINER_CHIP_BROWN.get(),      ScreenChipBrown::new);
            MenuScreens.register(CONTAINER_CHIP_GREEN.get(),      ScreenChipGreen::new);
            MenuScreens.register(CONTAINER_CHIP_RED.get(),        ScreenChipRed::new);
            MenuScreens.register(CONTAINER_CHIP_BLACK.get(),      ScreenChipBlack::new);
        });
    }



}
