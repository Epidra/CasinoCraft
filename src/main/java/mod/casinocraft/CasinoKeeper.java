package mod.casinocraft;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import mod.casinocraft.blocks.*;
import mod.casinocraft.container.blocks.ContainerArcade;
import mod.casinocraft.container.blocks.ContainerCardTable;
import mod.casinocraft.container.blocks.ContainerSlotMachine;
import mod.casinocraft.container.card.*;
import mod.casinocraft.container.chip.*;
import mod.casinocraft.container.mino.*;
import mod.casinocraft.container.other.ContainerDummy;
import mod.casinocraft.container.other.ContainerSlotGame;
import mod.casinocraft.screen.blocks.ScreenArcade;
import mod.casinocraft.screen.blocks.ScreenCardTable;
import mod.casinocraft.screen.blocks.ScreenSlotMachine;
import mod.casinocraft.screen.card.*;
import mod.casinocraft.screen.chip.*;
import mod.casinocraft.screen.mino.*;
import mod.casinocraft.screen.other.ScreenDummy;
import mod.casinocraft.screen.other.ScreenSlotGame;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityCardTableBase;
import mod.casinocraft.tileentities.TileEntityCardTableWide;
import mod.casinocraft.tileentities.TileEntitySlotMachine;
import mod.casinocraft.items.ItemItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;

import static mod.casinocraft.CasinoCraft.MODID;

public class CasinoKeeper {

    private static final DeferredRegister<Block> BLOCKS     = new DeferredRegister<>(ForgeRegistries.BLOCKS,             MODID);
    private static final DeferredRegister<Item> ITEMS      = new DeferredRegister<>(ForgeRegistries.ITEMS,              MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS,         MODID);
    private static final DeferredRegister<TileEntityType<?>> TILES      = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES,      MODID);




    // Card Tables
    public static final RegistryObject<Block> CARDTABLE_BASE_WHITE      = register("cardtable_base_white",      new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.BLACK),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_ORANGE     = register("cardtable_base_orange",     new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.ORANGE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_MAGENTA    = register("cardtable_base_magenta",    new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.MAGENTA),    ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_LIGHT_BLUE = register("cardtable_base_light_blue", new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.LIGHT_BLUE), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_YELLOW     = register("cardtable_base_yellow",     new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.YELLOW),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_LIME       = register("cardtable_base_lime",       new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.LIME),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_PINK       = register("cardtable_base_pink",       new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.PINK),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_GRAY       = register("cardtable_base_gray",       new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.GRAY),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_LIGHT_GRAY = register("cardtable_base_light_gray", new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.LIGHT_GRAY), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_CYAN       = register("cardtable_base_cyan",       new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.CYAN),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_PURPLE     = register("cardtable_base_purple",     new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.PURPLE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_BLUE       = register("cardtable_base_blue",       new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.BLUE),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_BROWN      = register("cardtable_base_brown",      new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.BROWN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_GREEN      = register("cardtable_base_green",      new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.GREEN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_RED        = register("cardtable_base_red",        new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.RED),        ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_BASE_BLACK      = register("cardtable_base_black",      new BlockCardTableBase(Blocks.OAK_PLANKS, DyeColor.BLACK),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_WHITE      = register("cardtable_wide_white",      new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.WHITE),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_ORANGE     = register("cardtable_wide_orange",     new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.ORANGE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_MAGENTA    = register("cardtable_wide_magenta",    new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.MAGENTA),    ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_LIGHT_BLUE = register("cardtable_wide_light_blue", new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.LIGHT_BLUE), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_YELLOW     = register("cardtable_wide_yellow",     new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.YELLOW),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_LIME       = register("cardtable_wide_lime",       new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.LIME),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_PINK       = register("cardtable_wide_pink",       new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.PINK),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_GRAY       = register("cardtable_wide_gray",       new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.GRAY),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_LIGHT_GRAY = register("cardtable_wide_light_gray", new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.LIGHT_GRAY), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_CYAN       = register("cardtable_wide_cyan",       new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.CYAN),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_PURPLE     = register("cardtable_wide_purple",     new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.PURPLE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_BLUE       = register("cardtable_wide_blue",       new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.BLUE),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_BROWN      = register("cardtable_wide_brown",      new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.BROWN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_GREEN      = register("cardtable_wide_green",      new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.GREEN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_RED        = register("cardtable_wide_red",        new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.RED),        ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> CARDTABLE_WIDE_BLACK      = register("cardtable_wide_black",      new BlockCardTableWide(Blocks.OAK_PLANKS, DyeColor.BLACK),      ItemGroup.DECORATIONS);

    // Arcades
    public static final RegistryObject<Block> ARCADE_BASE_WHITE      = register("arcade_base_white",      new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.WHITE),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_ORANGE     = register("arcade_base_orange",     new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.ORANGE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_MAGENTA    = register("arcade_base_magenta",    new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.MAGENTA),    ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_LIGHT_BLUE = register("arcade_base_light_blue", new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_YELLOW     = register("arcade_base_yellow",     new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.YELLOW),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_LIME       = register("arcade_base_lime",       new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIME),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_PINK       = register("arcade_base_pink",       new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.PINK),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_GRAY       = register("arcade_base_gray",       new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.GRAY),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_LIGHT_GRAY = register("arcade_base_light_gray", new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_CYAN       = register("arcade_base_cyan",       new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.CYAN),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_PURPLE     = register("arcade_base_purple",     new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.PURPLE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_BLUE       = register("arcade_base_blue",       new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BLUE),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_BROWN      = register("arcade_base_brown",      new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BROWN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_GREEN      = register("arcade_base_green",      new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.GREEN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_RED        = register("arcade_base_red",        new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.RED),        ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_BASE_BLACK      = register("arcade_base_black",      new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BLACK),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_WHITE      = register("arcade_slot_white",      new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.WHITE),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_ORANGE     = register("arcade_slot_orange",     new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.ORANGE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_MAGENTA    = register("arcade_slot_magenta",    new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.MAGENTA),    ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_LIGHT_BLUE = register("arcade_slot_light_blue", new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_YELLOW     = register("arcade_slot_yellow",     new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.YELLOW),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_LIME       = register("arcade_slot_lime",       new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIME),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_PINK       = register("arcade_slot_pink",       new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.PINK),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_GRAY       = register("arcade_slot_gray",       new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.GRAY),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_LIGHT_GRAY = register("arcade_slot_light_gray", new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_CYAN       = register("arcade_slot_cyan",       new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.CYAN),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_PURPLE     = register("arcade_slot_purple",     new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.PURPLE),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_BLUE       = register("arcade_slot_blue",       new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BLUE),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_BROWN      = register("arcade_slot_brown",      new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BROWN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_GREEN      = register("arcade_slot_green",      new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.GREEN),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_RED        = register("arcade_slot_red",        new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.RED),        ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> ARCADE_SLOT_BLACK      = register("arcade_slot_black",      new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BLACK),      ItemGroup.DECORATIONS);

    // Dice Blocks
    public static final RegistryObject<Block> DICE_BASIC_WHITE      = register("dice_basic_white",      new BlockDice(Blocks.WHITE_TERRACOTTA),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_ORANGE     = register("dice_basic_orange",     new BlockDice(Blocks.ORANGE_TERRACOTTA),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_MAGENTA    = register("dice_basic_magenta",    new BlockDice(Blocks.MAGENTA_TERRACOTTA),    ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_LIGHT_BLUE = register("dice_basic_light_blue", new BlockDice(Blocks.LIGHT_BLUE_TERRACOTTA), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_YELLOW     = register("dice_basic_yellow",     new BlockDice(Blocks.YELLOW_TERRACOTTA),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_LIME       = register("dice_basic_lime",       new BlockDice(Blocks.LIME_TERRACOTTA),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_PINK       = register("dice_basic_pink",       new BlockDice(Blocks.PINK_TERRACOTTA),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_GRAY       = register("dice_basic_gray",       new BlockDice(Blocks.GRAY_TERRACOTTA),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_LIGHT_GRAY = register("dice_basic_light_gray", new BlockDice(Blocks.LIGHT_GRAY_TERRACOTTA), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_CYAN       = register("dice_basic_cyan",       new BlockDice(Blocks.CYAN_TERRACOTTA),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_PURPLE     = register("dice_basic_purple",     new BlockDice(Blocks.PURPLE_TERRACOTTA),     ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_BLUE       = register("dice_basic_blue",       new BlockDice(Blocks.BLUE_TERRACOTTA),       ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_BROWN      = register("dice_basic_brown",      new BlockDice(Blocks.BROWN_TERRACOTTA),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_GREEN      = register("dice_basic_green",      new BlockDice(Blocks.GREEN_TERRACOTTA),      ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_RED        = register("dice_basic_red",        new BlockDice(Blocks.RED_TERRACOTTA),        ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> DICE_BASIC_BLACK      = register("dice_basic_black",      new BlockDice(Blocks.BLACK_TERRACOTTA),      ItemGroup.DECORATIONS);

    // Modules
    public static final RegistryObject<Item> MODULE_MINO_WHITE      = register("module_mino_white",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_ORANGE     = register("module_mino_orange",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_MAGENTA    = register("module_mino_magenta",    new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_LIGHT_BLUE = register("module_mino_light_blue", new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_YELLOW     = register("module_mino_yellow",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_LIME       = register("module_mino_lime",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_PINK       = register("module_mino_pink",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_GRAY       = register("module_mino_gray",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_LIGHT_GRAY = register("module_mino_light_gray", new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_CYAN       = register("module_mino_cyan",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_PURPLE     = register("module_mino_purple",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_BLUE       = register("module_mino_blue",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_BROWN      = register("module_mino_brown",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_GREEN      = register("module_mino_green",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_RED        = register("module_mino_red",        new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_MINO_BLACK      = register("module_mino_black",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_WHITE      = register("module_chip_white",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_ORANGE     = register("module_chip_orange",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_MAGENTA    = register("module_chip_magenta",    new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_LIGHT_BLUE = register("module_chip_light_blue", new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_YELLOW     = register("module_chip_yellow",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_LIME       = register("module_chip_lime",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_PINK       = register("module_chip_pink",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_GRAY       = register("module_chip_gray",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_LIGHT_GRAY = register("module_chip_light_gray", new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_CYAN       = register("module_chip_cyan",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_PURPLE     = register("module_chip_purple",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_BLUE       = register("module_chip_blue",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_BROWN      = register("module_chip_brown",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_GREEN      = register("module_chip_green",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_RED        = register("module_chip_red",        new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CHIP_BLACK      = register("module_chip_black",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_WHITE      = register("module_card_white",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_ORANGE     = register("module_card_orange",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_MAGENTA    = register("module_card_magenta",    new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_LIGHT_BLUE = register("module_card_light_blue", new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_YELLOW     = register("module_card_yellow",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_LIME       = register("module_card_lime",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_PINK       = register("module_card_pink",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_GRAY       = register("module_card_gray",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_LIGHT_GRAY = register("module_card_light_gray", new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_CYAN       = register("module_card_cyan",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_PURPLE     = register("module_card_purple",     new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_BLUE       = register("module_card_blue",       new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_BROWN      = register("module_card_brown",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_GREEN      = register("module_card_green",      new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_RED        = register("module_card_red",        new ItemItem(ItemGroup.MISC));
    public static final RegistryObject<Item> MODULE_CARD_BLACK      = register("module_card_black",      new ItemItem(ItemGroup.MISC));

    // Textures
    public static final ResourceLocation TEXTURE_STATIC            = new ResourceLocation(MODID, "textures/gui/static.png");
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
    public static final ResourceLocation TEXTURE_GROUND_ARCADE     = new ResourceLocation(MODID, "textures/gui/arcade/arcade.png");
    public static final ResourceLocation TEXTURE_GROUND_STARFIELD0 = new ResourceLocation(MODID, "textures/gui/arcade/starfield0.png");
    public static final ResourceLocation TEXTURE_GROUND_STARFIELD1 = new ResourceLocation(MODID, "textures/gui/arcade/starfield1.png");
    public static final ResourceLocation TEXTURE_ARCADEDUMMY       = new ResourceLocation(MODID, "textures/gui/background/arcadedummy.png");
    public static final ResourceLocation TEXTURE_TETRIS            = new ResourceLocation(MODID, "textures/gui/background/tetris.png");
    public static final ResourceLocation TEXTURE_COLUMNS           = new ResourceLocation(MODID, "textures/gui/background/columns.png");
    public static final ResourceLocation TEXTURE_MEANMINOS         = new ResourceLocation(MODID, "textures/gui/background/meanminos.png");
    public static final ResourceLocation TEXTURE_SLOTMACHINE       = new ResourceLocation(MODID, "textures/gui/background/slotmachine.png");
    public static final ResourceLocation TEXTURE_ROULETTE_LEFT     = new ResourceLocation(MODID, "textures/gui/background/roulette_left.png");
    public static final ResourceLocation TEXTURE_ROULETTE_RIGHT    = new ResourceLocation(MODID, "textures/gui/background/roulette_right.png");
    public static final ResourceLocation TEXTURE_ROULETTE_MIDDLE   = new ResourceLocation(MODID, "textures/gui/background/roulette_middle.png");
    public static final ResourceLocation TEXTURE_SICBO_LEFT        = new ResourceLocation(MODID, "textures/gui/background/sicbo_left.png");
    public static final ResourceLocation TEXTURE_SICBO_RIGHT       = new ResourceLocation(MODID, "textures/gui/background/sicbo_right.png");
    public static final ResourceLocation TEXTURE_SICBO_MIDDLE      = new ResourceLocation(MODID, "textures/gui/background/sicbo_middle.png");
    public static final ResourceLocation TEXTURE_CRAPS_LEFT        = new ResourceLocation(MODID, "textures/gui/background/craps_left.png");
    public static final ResourceLocation TEXTURE_CRAPS_RIGHT       = new ResourceLocation(MODID, "textures/gui/background/craps_right.png");
    public static final ResourceLocation TEXTURE_CRAPS_MIDDLE      = new ResourceLocation(MODID, "textures/gui/background/craps_middle.png");
    public static final ResourceLocation TEXTURE_FANTAN            = new ResourceLocation(MODID, "textures/gui/background/fantan.png");
    public static final ResourceLocation TEXTURE_SUDOKU            = new ResourceLocation(MODID, "textures/gui/background/sudoku.png");
    public static final ResourceLocation TEXTURE_SIMON             = new ResourceLocation(MODID, "textures/gui/spritesheet/simon.png");
    public static final ResourceLocation TEXTURE_DICE              = new ResourceLocation(MODID, "textures/gui/spritesheet/dice.png");
    public static final ResourceLocation TEXTURE_ARCADE            = new ResourceLocation(MODID, "textures/gui/spritesheet/arcade.png");
    public static final ResourceLocation TEXTURE_BUTTONS           = new ResourceLocation(MODID, "textures/gui/spritesheet/buttons.png");
    public static final ResourceLocation TEXTURE_MINOS             = new ResourceLocation(MODID, "textures/gui/spritesheet/minos.png");
    public static final ResourceLocation TEXTURE_SLOTGAME          = new ResourceLocation(MODID, "textures/gui/spritesheet/slotgame.png");
    public static final ResourceLocation TEXTURE_ROUGE             = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_rouge.png");
    public static final ResourceLocation TEXTURE_NOIR              = new ResourceLocation(MODID, "textures/gui/spritesheet/cards_noir.png");
    public static final ResourceLocation TEXTURE_MYSTICSQUARE      = new ResourceLocation(MODID, "textures/gui/spritesheet/mysticsquare.png");
    public static final ResourceLocation TEXTURE_2048              = new ResourceLocation(MODID, "textures/gui/spritesheet/2048.png");
    public static final ResourceLocation TEXTURE_FONT_ARCADE       = new ResourceLocation(MODID, "textures/gui/spritesheet/font_arcade.png");
    public static final ResourceLocation TEXTURE_FONT_CARDTABLE    = new ResourceLocation(MODID, "textures/gui/spritesheet/font_cardtable.png");
    public static final ResourceLocation TEXTURE_ROULETTE_WHEEL    = new ResourceLocation(MODID, "textures/gui/spritesheet/roulette_wheel.png");

    // Tile Entities
    public static  final RegistryObject<TileEntityType<TileEntityArcade>> TILE_ARCADE_BASE = TILES.register("arcade_base", () -> TileEntityType.Builder.create(TileEntityArcade::new,
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
    public static  final RegistryObject<TileEntityType<TileEntitySlotMachine>> TILE_ARCADE_SLOT = TILES.register("arcade_slot", () -> TileEntityType.Builder.create(TileEntitySlotMachine::new,
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
    public static  final RegistryObject<TileEntityType<TileEntityCardTableBase>> TILE_CARDTABLE_BASE = TILES.register("cardtable_base", () -> TileEntityType.Builder.create(TileEntityCardTableBase::new,
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
    public static  final RegistryObject<TileEntityType<TileEntityCardTableWide>> TILE_CARDTABLE_WIDE = TILES.register("cardtable_wide", () -> TileEntityType.Builder.create(TileEntityCardTableWide::new,
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

    // Container
    public static final RegistryObject<ContainerType<ContainerDummy>> CONTAINER_DUMMY       = CONTAINERS.register("dummy",       () -> IForgeContainerType.create(ContainerDummy::new));
    public static final RegistryObject<ContainerType<ContainerArcade>> CONTAINER_ARCADE       = CONTAINERS.register("arcade",       () -> IForgeContainerType.create(ContainerArcade::new));
    public static final RegistryObject<ContainerType<ContainerCardTable>> CONTAINER_CARDTABLE    = CONTAINERS.register("cardtable",    () -> IForgeContainerType.create(ContainerCardTable::new));
    public static final RegistryObject<ContainerType<ContainerSlotMachine>> CONTAINER_SLOTMACHINE  = CONTAINERS.register("slotmachine",  () -> IForgeContainerType.create(ContainerSlotMachine::new));
    public static final RegistryObject<ContainerType<ContainerSlotGame>> CONTAINER_SLOTGAME        = CONTAINERS.register("slotgame",     () -> IForgeContainerType.create(ContainerSlotGame::new));
    public static final RegistryObject<ContainerType<ContainerCardWhite>> CONTAINER_CARD_WHITE      = CONTAINERS.register("card_white",      () -> IForgeContainerType.create(ContainerCardWhite::new));
    public static final RegistryObject<ContainerType<ContainerCardOrange>> CONTAINER_CARD_ORANGE     = CONTAINERS.register("card_orange",     () -> IForgeContainerType.create(ContainerCardOrange::new));
    public static final RegistryObject<ContainerType<ContainerCardMagenta>> CONTAINER_CARD_MAGENTA    = CONTAINERS.register("card_magenta",    () -> IForgeContainerType.create(ContainerCardMagenta::new));
    public static final RegistryObject<ContainerType<ContainerCardLightBlue>> CONTAINER_CARD_LIGHT_BLUE = CONTAINERS.register("card_light_blue", () -> IForgeContainerType.create(ContainerCardLightBlue::new));
    public static final RegistryObject<ContainerType<ContainerCardYellow>> CONTAINER_CARD_YELLOW     = CONTAINERS.register("card_yellow",     () -> IForgeContainerType.create(ContainerCardYellow::new));
    public static final RegistryObject<ContainerType<ContainerCardLime>> CONTAINER_CARD_LIME       = CONTAINERS.register("card_lime",       () -> IForgeContainerType.create(ContainerCardLime::new));
    public static final RegistryObject<ContainerType<ContainerCardPink>> CONTAINER_CARD_PINK       = CONTAINERS.register("card_pink",       () -> IForgeContainerType.create(ContainerCardPink::new));
    public static final RegistryObject<ContainerType<ContainerCardGray>> CONTAINER_CARD_GRAY       = CONTAINERS.register("card_gray",       () -> IForgeContainerType.create(ContainerCardGray::new));
    public static final RegistryObject<ContainerType<ContainerCardLightGray>> CONTAINER_CARD_LIGHT_GRAY = CONTAINERS.register("card_light_gray", () -> IForgeContainerType.create(ContainerCardLightGray::new));
    public static final RegistryObject<ContainerType<ContainerCardCyan>> CONTAINER_CARD_CYAN       = CONTAINERS.register("card_cyan",       () -> IForgeContainerType.create(ContainerCardCyan::new));
    public static final RegistryObject<ContainerType<ContainerCardPurple>> CONTAINER_CARD_PURPLE     = CONTAINERS.register("card_purple",     () -> IForgeContainerType.create(ContainerCardPurple::new));
    public static final RegistryObject<ContainerType<ContainerCardBlue>> CONTAINER_CARD_BLUE       = CONTAINERS.register("card_blue",       () -> IForgeContainerType.create(ContainerCardBlue::new));
    public static final RegistryObject<ContainerType<ContainerCardBrown>> CONTAINER_CARD_BROWN      = CONTAINERS.register("card_brown",      () -> IForgeContainerType.create(ContainerCardBrown::new));
    public static final RegistryObject<ContainerType<ContainerCardGreen>> CONTAINER_CARD_GREEN      = CONTAINERS.register("card_green",      () -> IForgeContainerType.create(ContainerCardGreen::new));
    public static final RegistryObject<ContainerType<ContainerCardRed>> CONTAINER_CARD_RED        = CONTAINERS.register("card_red",        () -> IForgeContainerType.create(ContainerCardRed::new));
    public static final RegistryObject<ContainerType<ContainerCardBlack>> CONTAINER_CARD_BLACK      = CONTAINERS.register("card_black",      () -> IForgeContainerType.create(ContainerCardBlack::new));
    public static final RegistryObject<ContainerType<ContainerMinoWhite>> CONTAINER_MINO_WHITE      = CONTAINERS.register("mino_white",      () -> IForgeContainerType.create(ContainerMinoWhite::new));
    public static final RegistryObject<ContainerType<ContainerMinoOrange>> CONTAINER_MINO_ORANGE     = CONTAINERS.register("mino_orange",     () -> IForgeContainerType.create(ContainerMinoOrange::new));
    public static final RegistryObject<ContainerType<ContainerMinoMagenta>> CONTAINER_MINO_MAGENTA    = CONTAINERS.register("mino_magenta",    () -> IForgeContainerType.create(ContainerMinoMagenta::new));
    public static final RegistryObject<ContainerType<ContainerMinoLightBlue>> CONTAINER_MINO_LIGHT_BLUE = CONTAINERS.register("mino_light_blue", () -> IForgeContainerType.create(ContainerMinoLightBlue::new));
    public static final RegistryObject<ContainerType<ContainerMinoYellow>> CONTAINER_MINO_YELLOW     = CONTAINERS.register("mino_yellow",     () -> IForgeContainerType.create(ContainerMinoYellow::new));
    public static final RegistryObject<ContainerType<ContainerMinoLime>> CONTAINER_MINO_LIME       = CONTAINERS.register("mino_lime",       () -> IForgeContainerType.create(ContainerMinoLime::new));
    public static final RegistryObject<ContainerType<ContainerMinoPink>> CONTAINER_MINO_PINK       = CONTAINERS.register("mino_pink",       () -> IForgeContainerType.create(ContainerMinoPink::new));
    public static final RegistryObject<ContainerType<ContainerMinoGray>> CONTAINER_MINO_GRAY       = CONTAINERS.register("mino_gray",       () -> IForgeContainerType.create(ContainerMinoGray::new));
    public static final RegistryObject<ContainerType<ContainerMinoLightGray>> CONTAINER_MINO_LIGHT_GRAY = CONTAINERS.register("mino_light_gray", () -> IForgeContainerType.create(ContainerMinoLightGray::new));
    public static final RegistryObject<ContainerType<ContainerMinoCyan>> CONTAINER_MINO_CYAN       = CONTAINERS.register("mino_cyan",       () -> IForgeContainerType.create(ContainerMinoCyan::new));
    public static final RegistryObject<ContainerType<ContainerMinoPurple>> CONTAINER_MINO_PURPLE     = CONTAINERS.register("mino_purple",     () -> IForgeContainerType.create(ContainerMinoPurple::new));
    public static final RegistryObject<ContainerType<ContainerMinoBlue>> CONTAINER_MINO_BLUE       = CONTAINERS.register("mino_blue",       () -> IForgeContainerType.create(ContainerMinoBlue::new));
    public static final RegistryObject<ContainerType<ContainerMinoBrown>> CONTAINER_MINO_BROWN      = CONTAINERS.register("mino_brown",      () -> IForgeContainerType.create(ContainerMinoBrown::new));
    public static final RegistryObject<ContainerType<ContainerMinoGreen>> CONTAINER_MINO_GREEN      = CONTAINERS.register("mino_green",      () -> IForgeContainerType.create(ContainerMinoGreen::new));
    public static final RegistryObject<ContainerType<ContainerMinoRed>> CONTAINER_MINO_RED        = CONTAINERS.register("mino_red",        () -> IForgeContainerType.create(ContainerMinoRed::new));
    public static final RegistryObject<ContainerType<ContainerMinoBlack>> CONTAINER_MINO_BLACK      = CONTAINERS.register("mino_black",      () -> IForgeContainerType.create(ContainerMinoBlack::new));
    public static final RegistryObject<ContainerType<ContainerChipWhite>> CONTAINER_CHIP_WHITE      = CONTAINERS.register("chip_white",      () -> IForgeContainerType.create(ContainerChipWhite::new));
    public static final RegistryObject<ContainerType<ContainerChipOrange>> CONTAINER_CHIP_ORANGE     = CONTAINERS.register("chip_orange",     () -> IForgeContainerType.create(ContainerChipOrange::new));
    public static final RegistryObject<ContainerType<ContainerChipMagenta>> CONTAINER_CHIP_MAGENTA    = CONTAINERS.register("chip_magenta",    () -> IForgeContainerType.create(ContainerChipMagenta::new));
    public static final RegistryObject<ContainerType<ContainerChipLightBlue>> CONTAINER_CHIP_LIGHT_BLUE = CONTAINERS.register("chip_light_blue", () -> IForgeContainerType.create(ContainerChipLightBlue::new));
    public static final RegistryObject<ContainerType<ContainerChipYellow>> CONTAINER_CHIP_YELLOW     = CONTAINERS.register("chip_yellow",     () -> IForgeContainerType.create(ContainerChipYellow::new));
    public static final RegistryObject<ContainerType<ContainerChipLime>> CONTAINER_CHIP_LIME       = CONTAINERS.register("chip_lime",       () -> IForgeContainerType.create(ContainerChipLime::new));
    public static final RegistryObject<ContainerType<ContainerChipPink>> CONTAINER_CHIP_PINK       = CONTAINERS.register("chip_pink",       () -> IForgeContainerType.create(ContainerChipPink::new));
    public static final RegistryObject<ContainerType<ContainerChipGray>> CONTAINER_CHIP_GRAY       = CONTAINERS.register("chip_gray",       () -> IForgeContainerType.create(ContainerChipGray::new));
    public static final RegistryObject<ContainerType<ContainerChipLightGray>> CONTAINER_CHIP_LIGHT_GRAY = CONTAINERS.register("chip_light_gray", () -> IForgeContainerType.create(ContainerChipLightGray::new));
    public static final RegistryObject<ContainerType<ContainerChipCyan>> CONTAINER_CHIP_CYAN       = CONTAINERS.register("chip_cyan",       () -> IForgeContainerType.create(ContainerChipCyan::new));
    public static final RegistryObject<ContainerType<ContainerChipPurple>> CONTAINER_CHIP_PURPLE     = CONTAINERS.register("chip_purple",     () -> IForgeContainerType.create(ContainerChipPurple::new));
    public static final RegistryObject<ContainerType<ContainerChipBlue>> CONTAINER_CHIP_BLUE       = CONTAINERS.register("chip_blue",       () -> IForgeContainerType.create(ContainerChipBlue::new));
    public static final RegistryObject<ContainerType<ContainerChipBrown>> CONTAINER_CHIP_BROWN      = CONTAINERS.register("chip_brown",      () -> IForgeContainerType.create(ContainerChipBrown::new));
    public static final RegistryObject<ContainerType<ContainerChipGreen>> CONTAINER_CHIP_GREEN      = CONTAINERS.register("chip_green",      () -> IForgeContainerType.create(ContainerChipGreen::new));
    public static final RegistryObject<ContainerType<ContainerChipRed>> CONTAINER_CHIP_RED        = CONTAINERS.register("chip_red",        () -> IForgeContainerType.create(ContainerChipRed::new));
    public static final RegistryObject<ContainerType<ContainerChipBlack>> CONTAINER_CHIP_BLACK      = CONTAINERS.register("chip_black",      () -> IForgeContainerType.create(ContainerChipBlack::new));

    // Config
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec.BooleanValue config_allowed_creative = BUILDER.comment("Activate Creative Toggle on Card Tables").define("config_allowed_creative", true);
    public static ForgeConfigSpec.BooleanValue config_fastload         = BUILDER.comment("Transfer Tokens instantly").define("config_fastload", true);
    public static ForgeConfigSpec.BooleanValue config_animated_cards   = BUILDER.comment("Face Cards do a one frame animation sometimes").define("config_animated_cards", true);
    public static ForgeConfigSpec.IntValue     config_timeout          = BUILDER.comment("How long until a player receives a timeout").defineInRange("config_timeout", 300, 1, 10000);
    /**Reads out Config File**/
    public static void loadConfig(Path file){
        SPEC.setConfig(CommentedFileConfig.builder(file).build());
    }
    public static final ForgeConfigSpec SPEC = BUILDER.build();




    //----------------------------------------REGISTER----------------------------------------//

    static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static RegistryObject<Block> register(String name, Block block){
        return register(name, block, null);
    }

    private static RegistryObject<Block> register(String name, Block block, ItemGroup itemGroup){
        if(itemGroup != null){ ITEMS.register(name, () -> new BlockItem(block, (new Item.Properties()).group(itemGroup))); }
        return BLOCKS.register(name, () -> block);
    }

    private static RegistryObject<Item> register(String name, Item item){
        return ITEMS.register(name, () -> item);
    }




    //----------------------------------------SETUP----------------------------------------//

    static void setup(FMLCommonSetupEvent event){

    }

    @OnlyIn(Dist.CLIENT)
    static void setup(FMLClientSetupEvent event){
        DeferredWorkQueue.runLater(() -> {
            ScreenManager.registerFactory(CONTAINER_DUMMY.get(),           ScreenDummy::new);
            ScreenManager.registerFactory(CONTAINER_ARCADE.get(),          ScreenArcade::new);
            ScreenManager.registerFactory(CONTAINER_CARDTABLE.get(),       ScreenCardTable::new);
            ScreenManager.registerFactory(CONTAINER_SLOTMACHINE.get(),     ScreenSlotMachine::new);
            ScreenManager.registerFactory(CONTAINER_SLOTGAME.get(),        ScreenSlotGame::new);
            ScreenManager.registerFactory(CONTAINER_CARD_WHITE.get(),      ScreenCardWhite::new);
            ScreenManager.registerFactory(CONTAINER_CARD_ORANGE.get(),     ScreenCardOrange::new);
            ScreenManager.registerFactory(CONTAINER_CARD_MAGENTA.get(),    ScreenCardMagenta::new);
            ScreenManager.registerFactory(CONTAINER_CARD_LIGHT_BLUE.get(), ScreenCardLightBlue::new);
            ScreenManager.registerFactory(CONTAINER_CARD_YELLOW.get(),     ScreenCardYellow::new);
            ScreenManager.registerFactory(CONTAINER_CARD_LIME.get(),       ScreenCardLime::new);
            ScreenManager.registerFactory(CONTAINER_CARD_PINK.get(),       ScreenCardPink::new);
            ScreenManager.registerFactory(CONTAINER_CARD_GRAY.get(),       ScreenCardGray::new);
            ScreenManager.registerFactory(CONTAINER_CARD_LIGHT_GRAY.get(), ScreenCardLightGray::new);
            ScreenManager.registerFactory(CONTAINER_CARD_CYAN.get(),       ScreenCardCyan::new);
            ScreenManager.registerFactory(CONTAINER_CARD_PURPLE.get(),     ScreenCardPurple::new);
            ScreenManager.registerFactory(CONTAINER_CARD_BLUE.get(),       ScreenCardBlue::new);
            ScreenManager.registerFactory(CONTAINER_CARD_BROWN.get(),      ScreenCardBrown::new);
            ScreenManager.registerFactory(CONTAINER_CARD_GREEN.get(),      ScreenCardGreen::new);
            ScreenManager.registerFactory(CONTAINER_CARD_RED.get(),        ScreenCardRed::new);
            ScreenManager.registerFactory(CONTAINER_CARD_BLACK.get(),      ScreenCardBlack::new);
            ScreenManager.registerFactory(CONTAINER_MINO_WHITE.get(),      ScreenMinoWhite::new);
            ScreenManager.registerFactory(CONTAINER_MINO_ORANGE.get(),     ScreenMinoOrange::new);
            ScreenManager.registerFactory(CONTAINER_MINO_MAGENTA.get(),    ScreenMinoMagenta::new);
            ScreenManager.registerFactory(CONTAINER_MINO_LIGHT_BLUE.get(), ScreenMinoLightBlue::new);
            ScreenManager.registerFactory(CONTAINER_MINO_YELLOW.get(),     ScreenMinoYellow::new);
            ScreenManager.registerFactory(CONTAINER_MINO_LIME.get(),       ScreenMinoLime::new);
            ScreenManager.registerFactory(CONTAINER_MINO_PINK.get(),       ScreenMinoPink::new);
            ScreenManager.registerFactory(CONTAINER_MINO_GRAY.get(),       ScreenMinoGray::new);
            ScreenManager.registerFactory(CONTAINER_MINO_LIGHT_GRAY.get(), ScreenMinoLightGray::new);
            ScreenManager.registerFactory(CONTAINER_MINO_CYAN.get(),       ScreenMinoCyan::new);
            ScreenManager.registerFactory(CONTAINER_MINO_PURPLE.get(),     ScreenMinoPurple::new);
            ScreenManager.registerFactory(CONTAINER_MINO_BLUE.get(),       ScreenMinoBlue::new);
            ScreenManager.registerFactory(CONTAINER_MINO_BROWN.get(),      ScreenMinoBrown::new);
            ScreenManager.registerFactory(CONTAINER_MINO_GREEN.get(),      ScreenMinoGreen::new);
            ScreenManager.registerFactory(CONTAINER_MINO_RED.get(),        ScreenMinoRed::new);
            ScreenManager.registerFactory(CONTAINER_MINO_BLACK.get(),      ScreenMinoBlack::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_WHITE.get(),      ScreenChipWhite::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_ORANGE.get(),     ScreenChipOrange::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_MAGENTA.get(),    ScreenChipMagenta::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_LIGHT_BLUE.get(), ScreenChipLightBlue::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_YELLOW.get(),     ScreenChipYellow::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_LIME.get(),       ScreenChipLime::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_PINK.get(),       ScreenChipPink::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_GRAY.get(),       ScreenChipGray::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_LIGHT_GRAY.get(), ScreenChipLightGray::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_CYAN.get(),       ScreenChipCyan::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_PURPLE.get(),     ScreenChipPurple::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_BLUE.get(),       ScreenChipBlue::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_BROWN.get(),      ScreenChipBrown::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_GREEN.get(),      ScreenChipGreen::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_RED.get(),        ScreenChipRed::new);
            ScreenManager.registerFactory(CONTAINER_CHIP_BLACK.get(),      ScreenChipBlack::new);
        });
    }

}
