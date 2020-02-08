package mod.casinocraft;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import mod.casinocraft.blocks.*;
import mod.casinocraft.container.ContainerArcade;
import mod.casinocraft.container.ContainerCardTable;
import mod.casinocraft.container.ContainerSlotMachine;
import mod.casinocraft.container.card.*;
import mod.casinocraft.container.clay.*;
import mod.casinocraft.container.dust.*;
import mod.casinocraft.container.other.ContainerField;
import mod.casinocraft.container.other.ContainerNoise;
import mod.casinocraft.container.other.ContainerSlotGame;
import mod.casinocraft.gui.GuiArcade;
import mod.casinocraft.gui.GuiCardTable;
import mod.casinocraft.gui.GuiSlotMachine;
import mod.casinocraft.gui.card.*;
import mod.casinocraft.gui.clay.*;
import mod.casinocraft.gui.dust.*;
import mod.casinocraft.gui.other.GuiField;
import mod.casinocraft.gui.other.GuiNoise;
import mod.casinocraft.gui.other.GuiSlotGame;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityCardTableBase;
import mod.casinocraft.tileentities.TileEntityCardTableWide;
import mod.casinocraft.tileentities.TileEntitySlotMachine;
import mod.shared.Register;
import mod.shared.items.ItemItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.nio.file.Path;

import static mod.casinocraft.CasinoCraft.MODID;

public class CasinoKeeper {

    // Tables
    public static final Block CARDTABLE_BASE_WHITE     = new BlockCardTableBase(MODID, "cardtable_base_white", Blocks.OAK_PLANKS, DyeColor.BLACK);
    public static final Block CARDTABLE_BASE_ORANGE    = new BlockCardTableBase(MODID, "cardtable_base_orange", Blocks.OAK_PLANKS, DyeColor.ORANGE);
    public static final Block CARDTABLE_BASE_MAGENTA   = new BlockCardTableBase(MODID, "cardtable_base_magenta", Blocks.OAK_PLANKS, DyeColor.MAGENTA);
    public static final Block CARDTABLE_BASE_LIGHTBLUE = new BlockCardTableBase(MODID, "cardtable_base_lightblue", Blocks.OAK_PLANKS, DyeColor.LIGHT_BLUE);
    public static final Block CARDTABLE_BASE_YELLOW    = new BlockCardTableBase(MODID, "cardtable_base_yellow", Blocks.OAK_PLANKS, DyeColor.YELLOW);
    public static final Block CARDTABLE_BASE_LIME      = new BlockCardTableBase(MODID, "cardtable_base_lime", Blocks.OAK_PLANKS, DyeColor.LIME);
    public static final Block CARDTABLE_BASE_PINK      = new BlockCardTableBase(MODID, "cardtable_base_pink", Blocks.OAK_PLANKS, DyeColor.PINK);
    public static final Block CARDTABLE_BASE_GRAY      = new BlockCardTableBase(MODID, "cardtable_base_gray", Blocks.OAK_PLANKS, DyeColor.GRAY);
    public static final Block CARDTABLE_BASE_SILVER    = new BlockCardTableBase(MODID, "cardtable_base_silver", Blocks.OAK_PLANKS, DyeColor.LIGHT_GRAY);
    public static final Block CARDTABLE_BASE_CYAN      = new BlockCardTableBase(MODID, "cardtable_base_cyan", Blocks.OAK_PLANKS, DyeColor.CYAN);
    public static final Block CARDTABLE_BASE_PURPLE    = new BlockCardTableBase(MODID, "cardtable_base_purple", Blocks.OAK_PLANKS, DyeColor.PURPLE);
    public static final Block CARDTABLE_BASE_BLUE      = new BlockCardTableBase(MODID, "cardtable_base_blue", Blocks.OAK_PLANKS, DyeColor.BLUE);
    public static final Block CARDTABLE_BASE_BROWN     = new BlockCardTableBase(MODID, "cardtable_base_brown", Blocks.OAK_PLANKS, DyeColor.BROWN);
    public static final Block CARDTABLE_BASE_GREEN     = new BlockCardTableBase(MODID, "cardtable_base_green", Blocks.OAK_PLANKS, DyeColor.GREEN);
    public static final Block CARDTABLE_BASE_RED       = new BlockCardTableBase(MODID, "cardtable_base_red", Blocks.OAK_PLANKS, DyeColor.RED);
    public static final Block CARDTABLE_BASE_BLACK     = new BlockCardTableBase(MODID, "cardtable_base_black", Blocks.OAK_PLANKS, DyeColor.BLACK);
    public static final Block CARDTABLE_WIDE_WHITE     = new BlockCardTableWide(MODID, "cardtable_wide_white", Blocks.OAK_PLANKS, DyeColor.WHITE);
    public static final Block CARDTABLE_WIDE_ORANGE    = new BlockCardTableWide(MODID, "cardtable_wide_orange", Blocks.OAK_PLANKS, DyeColor.ORANGE);
    public static final Block CARDTABLE_WIDE_MAGENTA   = new BlockCardTableWide(MODID, "cardtable_wide_magenta", Blocks.OAK_PLANKS, DyeColor.MAGENTA);
    public static final Block CARDTABLE_WIDE_LIGHTBLUE = new BlockCardTableWide(MODID, "cardtable_wide_lightblue", Blocks.OAK_PLANKS, DyeColor.LIGHT_BLUE);
    public static final Block CARDTABLE_WIDE_YELLOW    = new BlockCardTableWide(MODID, "cardtable_wide_yellow", Blocks.OAK_PLANKS, DyeColor.YELLOW);
    public static final Block CARDTABLE_WIDE_LIME      = new BlockCardTableWide(MODID, "cardtable_wide_lime", Blocks.OAK_PLANKS, DyeColor.LIME);
    public static final Block CARDTABLE_WIDE_PINK      = new BlockCardTableWide(MODID, "cardtable_wide_pink", Blocks.OAK_PLANKS, DyeColor.PINK);
    public static final Block CARDTABLE_WIDE_GRAY      = new BlockCardTableWide(MODID, "cardtable_wide_gray", Blocks.OAK_PLANKS, DyeColor.GRAY);
    public static final Block CARDTABLE_WIDE_SILVER    = new BlockCardTableWide(MODID, "cardtable_wide_silver", Blocks.OAK_PLANKS, DyeColor.LIGHT_GRAY);
    public static final Block CARDTABLE_WIDE_CYAN      = new BlockCardTableWide(MODID, "cardtable_wide_cyan", Blocks.OAK_PLANKS, DyeColor.CYAN);
    public static final Block CARDTABLE_WIDE_PURPLE    = new BlockCardTableWide(MODID, "cardtable_wide_purple", Blocks.OAK_PLANKS, DyeColor.PURPLE);
    public static final Block CARDTABLE_WIDE_BLUE      = new BlockCardTableWide(MODID, "cardtable_wide_blue", Blocks.OAK_PLANKS, DyeColor.BLUE);
    public static final Block CARDTABLE_WIDE_BROWN     = new BlockCardTableWide(MODID, "cardtable_wide_brown", Blocks.OAK_PLANKS, DyeColor.BROWN);
    public static final Block CARDTABLE_WIDE_GREEN     = new BlockCardTableWide(MODID, "cardtable_wide_green", Blocks.OAK_PLANKS, DyeColor.GREEN);
    public static final Block CARDTABLE_WIDE_RED       = new BlockCardTableWide(MODID, "cardtable_wide_red", Blocks.OAK_PLANKS, DyeColor.RED);
    public static final Block CARDTABLE_WIDE_BLACK     = new BlockCardTableWide(MODID, "cardtable_wide_black", Blocks.OAK_PLANKS, DyeColor.BLACK);

    // Arcades
    public static final Block ARCADE_BASE_WHITE     = new BlockArcade(MODID, "arcade_base_white", Blocks.IRON_BLOCK, DyeColor.WHITE);
    public static final Block ARCADE_BASE_ORANGE    = new BlockArcade(MODID, "arcade_base_orange", Blocks.IRON_BLOCK, DyeColor.ORANGE);
    public static final Block ARCADE_BASE_MAGENTA   = new BlockArcade(MODID, "arcade_base_magenta", Blocks.IRON_BLOCK, DyeColor.MAGENTA);
    public static final Block ARCADE_BASE_LIGHTBLUE = new BlockArcade(MODID, "arcade_base_lightblue", Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE);
    public static final Block ARCADE_BASE_YELLOW    = new BlockArcade(MODID, "arcade_base_yellow", Blocks.IRON_BLOCK, DyeColor.YELLOW);
    public static final Block ARCADE_BASE_LIME      = new BlockArcade(MODID, "arcade_base_lime", Blocks.IRON_BLOCK, DyeColor.LIME);
    public static final Block ARCADE_BASE_PINK      = new BlockArcade(MODID, "arcade_base_pink", Blocks.IRON_BLOCK, DyeColor.PINK);
    public static final Block ARCADE_BASE_GRAY      = new BlockArcade(MODID, "arcade_base_gray", Blocks.IRON_BLOCK, DyeColor.GRAY);
    public static final Block ARCADE_BASE_SILVER    = new BlockArcade(MODID, "arcade_base_silver", Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY);
    public static final Block ARCADE_BASE_CYAN      = new BlockArcade(MODID, "arcade_base_cyan", Blocks.IRON_BLOCK, DyeColor.CYAN);
    public static final Block ARCADE_BASE_PURPLE    = new BlockArcade(MODID, "arcade_base_purple", Blocks.IRON_BLOCK, DyeColor.PURPLE);
    public static final Block ARCADE_BASE_BLUE      = new BlockArcade(MODID, "arcade_base_blue", Blocks.IRON_BLOCK, DyeColor.BLUE);
    public static final Block ARCADE_BASE_BROWN     = new BlockArcade(MODID, "arcade_base_brown", Blocks.IRON_BLOCK, DyeColor.BROWN);
    public static final Block ARCADE_BASE_GREEN     = new BlockArcade(MODID, "arcade_base_green", Blocks.IRON_BLOCK, DyeColor.GREEN);
    public static final Block ARCADE_BASE_RED       = new BlockArcade(MODID, "arcade_base_red", Blocks.IRON_BLOCK, DyeColor.RED);
    public static final Block ARCADE_BASE_BLACK     = new BlockArcade(MODID, "arcade_base_black", Blocks.IRON_BLOCK, DyeColor.BLACK);
    public static final Block ARCADE_SLOT_WHITE     = new BlockSlotMachine(MODID, "arcade_slot_white", Blocks.IRON_BLOCK, DyeColor.WHITE);
    public static final Block ARCADE_SLOT_ORANGE    = new BlockSlotMachine(MODID, "arcade_slot_orange", Blocks.IRON_BLOCK, DyeColor.ORANGE);
    public static final Block ARCADE_SLOT_MAGENTA   = new BlockSlotMachine(MODID, "arcade_slot_magenta", Blocks.IRON_BLOCK, DyeColor.MAGENTA);
    public static final Block ARCADE_SLOT_LIGHTBLUE = new BlockSlotMachine(MODID, "arcade_slot_lightblue", Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE);
    public static final Block ARCADE_SLOT_YELLOW    = new BlockSlotMachine(MODID, "arcade_slot_yellow", Blocks.IRON_BLOCK, DyeColor.YELLOW);
    public static final Block ARCADE_SLOT_LIME      = new BlockSlotMachine(MODID, "arcade_slot_lime", Blocks.IRON_BLOCK, DyeColor.LIME);
    public static final Block ARCADE_SLOT_PINK      = new BlockSlotMachine(MODID, "arcade_slot_pink", Blocks.IRON_BLOCK, DyeColor.PINK);
    public static final Block ARCADE_SLOT_GRAY      = new BlockSlotMachine(MODID, "arcade_slot_gray", Blocks.IRON_BLOCK, DyeColor.GRAY);
    public static final Block ARCADE_SLOT_SILVER    = new BlockSlotMachine(MODID, "arcade_slot_silver", Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY);
    public static final Block ARCADE_SLOT_CYAN      = new BlockSlotMachine(MODID, "arcade_slot_cyan", Blocks.IRON_BLOCK, DyeColor.CYAN);
    public static final Block ARCADE_SLOT_PURPLE    = new BlockSlotMachine(MODID, "arcade_slot_purple", Blocks.IRON_BLOCK, DyeColor.PURPLE);
    public static final Block ARCADE_SLOT_BLUE      = new BlockSlotMachine(MODID, "arcade_slot_blue", Blocks.IRON_BLOCK, DyeColor.BLUE);
    public static final Block ARCADE_SLOT_BROWN     = new BlockSlotMachine(MODID, "arcade_slot_brown", Blocks.IRON_BLOCK, DyeColor.BROWN);
    public static final Block ARCADE_SLOT_GREEN     = new BlockSlotMachine(MODID, "arcade_slot_green", Blocks.IRON_BLOCK, DyeColor.GREEN);
    public static final Block ARCADE_SLOT_RED       = new BlockSlotMachine(MODID, "arcade_slot_red", Blocks.IRON_BLOCK, DyeColor.RED);
    public static final Block ARCADE_SLOT_BLACK     = new BlockSlotMachine(MODID, "arcade_slot_black", Blocks.IRON_BLOCK, DyeColor.BLACK);

    // Dice Blocks
    public static final Block DICE_BASIC_WHITE     = new BlockDice(MODID, "dice_basic_white", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_ORANGE    = new BlockDice(MODID, "dice_basic_orange", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_MAGENTA   = new BlockDice(MODID, "dice_basic_magenta", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_LIGHTBLUE = new BlockDice(MODID, "dice_basic_lightblue", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_YELLOW    = new BlockDice(MODID, "dice_basic_yellow", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_LIME      = new BlockDice(MODID, "dice_basic_lime", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_PINK      = new BlockDice(MODID, "dice_basic_pink", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_GRAY      = new BlockDice(MODID, "dice_basic_gray", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_SILVER    = new BlockDice(MODID, "dice_basic_silver", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_CYAN      = new BlockDice(MODID, "dice_basic_cyan", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_PURPLE    = new BlockDice(MODID, "dice_basic_purple", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_BLUE      = new BlockDice(MODID, "dice_basic_blue", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_BROWN     = new BlockDice(MODID, "dice_basic_brown", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_GREEN     = new BlockDice(MODID, "dice_basic_green", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_RED       = new BlockDice(MODID, "dice_basic_red", Blocks.TERRACOTTA);
    public static final Block DICE_BASIC_BLACK     = new BlockDice(MODID, "dice_basic_black", Blocks.TERRACOTTA);
    //public static final Block DICE_POWER_WHITE     = new BlockDice(MODID, "dice_power_white", Blocks.CLAY);
    //public static final Block DICE_POWER_ORANGE    = new BlockDice(MODID, "dice_power_orange", Blocks.CLAY);
    //public static final Block DICE_POWER_MAGENTA   = new BlockDice(MODID, "dice_power_magenta", Blocks.CLAY);
    //public static final Block DICE_POWER_LIGHTBLUE = new BlockDice(MODID, "dice_power_lightblue", Blocks.CLAY);
    //public static final Block DICE_POWER_YELLOW    = new BlockDice(MODID, "dice_power_yellow", Blocks.CLAY);
    //public static final Block DICE_POWER_LIME      = new BlockDice(MODID, "dice_power_lime", Blocks.CLAY);
    //public static final Block DICE_POWER_PINK      = new BlockDice(MODID, "dice_power_pink", Blocks.CLAY);
    //public static final Block DICE_POWER_GRAY      = new BlockDice(MODID, "dice_power_gray", Blocks.CLAY);
    //public static final Block DICE_POWER_SILVER    = new BlockDice(MODID, "dice_power_silver", Blocks.CLAY);
    //public static final Block DICE_POWER_CYAN      = new BlockDice(MODID, "dice_power_cyan", Blocks.CLAY);
    //public static final Block DICE_POWER_PURPLE    = new BlockDice(MODID, "dice_power_purple", Blocks.CLAY);
    //public static final Block DICE_POWER_BLUE      = new BlockDice(MODID, "dice_power_blue", Blocks.CLAY);
    //public static final Block DICE_POWER_BROWN     = new BlockDice(MODID, "dice_power_brown", Blocks.CLAY);
    //public static final Block DICE_POWER_GREEN     = new BlockDice(MODID, "dice_power_green", Blocks.CLAY);
    //public static final Block DICE_POWER_RED       = new BlockDice(MODID, "dice_power_red", Blocks.CLAY);
    //public static final Block DICE_POWER_BLACK     = new BlockDice(MODID, "dice_power_black", Blocks.CLAY);

    // Modules
    public static final Item MODULE_CLAY_WHITE     = new ItemItem(MODID, "module_clay_white", ItemGroup.MISC);
    public static final Item MODULE_CLAY_ORANGE    = new ItemItem(MODID, "module_clay_orange", ItemGroup.MISC);
    public static final Item MODULE_CLAY_MAGENTA   = new ItemItem(MODID, "module_clay_magenta", ItemGroup.MISC);
    public static final Item MODULE_CLAY_LIGHTBLUE = new ItemItem(MODID, "module_clay_lightblue", ItemGroup.MISC);
    public static final Item MODULE_CLAY_YELLOW    = new ItemItem(MODID, "module_clay_yellow", ItemGroup.MISC);
    public static final Item MODULE_CLAY_LIME      = new ItemItem(MODID, "module_clay_lime", ItemGroup.MISC);
    public static final Item MODULE_CLAY_PINK      = new ItemItem(MODID, "module_clay_pink", ItemGroup.MISC);
    public static final Item MODULE_CLAY_GRAY      = new ItemItem(MODID, "module_clay_gray", ItemGroup.MISC);
    public static final Item MODULE_CLAY_SILVER    = new ItemItem(MODID, "module_clay_silver", ItemGroup.MISC);
    public static final Item MODULE_CLAY_CYAN      = new ItemItem(MODID, "module_clay_cyan", ItemGroup.MISC);
    public static final Item MODULE_CLAY_PURPLE    = new ItemItem(MODID, "module_clay_purple", ItemGroup.MISC);
    public static final Item MODULE_CLAY_BLUE      = new ItemItem(MODID, "module_clay_blue", ItemGroup.MISC);
    public static final Item MODULE_CLAY_BROWN     = new ItemItem(MODID, "module_clay_brown", ItemGroup.MISC);
    public static final Item MODULE_CLAY_GREEN     = new ItemItem(MODID, "module_clay_green", ItemGroup.MISC);
    public static final Item MODULE_CLAY_RED       = new ItemItem(MODID, "module_clay_red", ItemGroup.MISC);
    public static final Item MODULE_CLAY_BLACK     = new ItemItem(MODID, "module_clay_black", ItemGroup.MISC);
    public static final Item MODULE_DUST_WHITE     = new ItemItem(MODID, "module_dust_white", ItemGroup.MISC);
    public static final Item MODULE_DUST_ORANGE    = new ItemItem(MODID, "module_dust_orange", ItemGroup.MISC);
    public static final Item MODULE_DUST_MAGENTA   = new ItemItem(MODID, "module_dust_magenta", ItemGroup.MISC);
    public static final Item MODULE_DUST_LIGHTBLUE = new ItemItem(MODID, "module_dust_lightblue", ItemGroup.MISC);
    public static final Item MODULE_DUST_YELLOW    = new ItemItem(MODID, "module_dust_yellow", ItemGroup.MISC);
    public static final Item MODULE_DUST_LIME      = new ItemItem(MODID, "module_dust_lime", ItemGroup.MISC);
    public static final Item MODULE_DUST_PINK      = new ItemItem(MODID, "module_dust_pink", ItemGroup.MISC);
    public static final Item MODULE_DUST_GRAY      = new ItemItem(MODID, "module_dust_gray", ItemGroup.MISC);
    public static final Item MODULE_DUST_SILVER    = new ItemItem(MODID, "module_dust_silver", ItemGroup.MISC);
    public static final Item MODULE_DUST_CYAN      = new ItemItem(MODID, "module_dust_cyan", ItemGroup.MISC);
    public static final Item MODULE_DUST_PURPLE    = new ItemItem(MODID, "module_dust_purple", ItemGroup.MISC);
    public static final Item MODULE_DUST_BLUE      = new ItemItem(MODID, "module_dust_blue", ItemGroup.MISC);
    public static final Item MODULE_DUST_BROWN     = new ItemItem(MODID, "module_dust_brown", ItemGroup.MISC);
    public static final Item MODULE_DUST_GREEN     = new ItemItem(MODID, "module_dust_green", ItemGroup.MISC);
    public static final Item MODULE_DUST_RED       = new ItemItem(MODID, "module_dust_red", ItemGroup.MISC);
    public static final Item MODULE_DUST_BLACK     = new ItemItem(MODID, "module_dust_black", ItemGroup.MISC);
    public static final Item MODULE_CARD_WHITE     = new ItemItem(MODID, "module_card_white", ItemGroup.MISC);
    public static final Item MODULE_CARD_ORANGE    = new ItemItem(MODID, "module_card_orange", ItemGroup.MISC);
    public static final Item MODULE_CARD_MAGENTA   = new ItemItem(MODID, "module_card_magenta", ItemGroup.MISC);
    public static final Item MODULE_CARD_LIGHTBLUE = new ItemItem(MODID, "module_card_lightblue", ItemGroup.MISC);
    public static final Item MODULE_CARD_YELLOW    = new ItemItem(MODID, "module_card_yellow", ItemGroup.MISC);
    public static final Item MODULE_CARD_LIME      = new ItemItem(MODID, "module_card_lime", ItemGroup.MISC);
    public static final Item MODULE_CARD_PINK      = new ItemItem(MODID, "module_card_pink", ItemGroup.MISC);
    public static final Item MODULE_CARD_GRAY      = new ItemItem(MODID, "module_card_gray", ItemGroup.MISC);
    public static final Item MODULE_CARD_SILVER    = new ItemItem(MODID, "module_card_silver", ItemGroup.MISC);
    public static final Item MODULE_CARD_CYAN      = new ItemItem(MODID, "module_card_cyan", ItemGroup.MISC);
    public static final Item MODULE_CARD_PURPLE    = new ItemItem(MODID, "module_card_purple", ItemGroup.MISC);
    public static final Item MODULE_CARD_BLUE      = new ItemItem(MODID, "module_card_blue", ItemGroup.MISC);
    public static final Item MODULE_CARD_BROWN     = new ItemItem(MODID, "module_card_brown", ItemGroup.MISC);
    public static final Item MODULE_CARD_GREEN     = new ItemItem(MODID, "module_card_green", ItemGroup.MISC);
    public static final Item MODULE_CARD_RED       = new ItemItem(MODID, "module_card_red", ItemGroup.MISC);
    public static final Item MODULE_CARD_BLACK     = new ItemItem(MODID, "module_card_black", ItemGroup.MISC);

    public enum GuiID{CARDTABLE, ARCADE, BLACKJACK, BACCARAT, VIDEOPOKER, MEMORY, TETRIS, SLOTMACHINE, ROULETTE, COLUMNS, MEANMINOS, MYSTICSQUARE, PYRAMID, SOKOBAN, SUDOKU, TRIPEAK, _2048, ACEYDEUCEY, HALMA, ROUGEETNOIR, SNAKE, CRAPS, SICBO, KLONDIKE, FREECELL, SPIDER, MINESWEEPER}

    public static ResourceLocation TEXTURE_GROUND_BLACK      = new ResourceLocation(MODID, "textures/gui/cardtable_black.png");
    public static ResourceLocation TEXTURE_GROUND_BLUE       = new ResourceLocation(MODID, "textures/gui/cardtable_blue.png");
    public static ResourceLocation TEXTURE_GROUND_BROWN      = new ResourceLocation(MODID, "textures/gui/cardtable_brown.png");
    public static ResourceLocation TEXTURE_GROUND_CYAN       = new ResourceLocation(MODID, "textures/gui/cardtable_cyan.png");
    public static ResourceLocation TEXTURE_GROUND_GRAY       = new ResourceLocation(MODID, "textures/gui/cardtable_gray.png");
    public static ResourceLocation TEXTURE_GROUND_GREEN      = new ResourceLocation(MODID, "textures/gui/cardtable_green.png");
    public static ResourceLocation TEXTURE_GROUND_LIGHTBLUE  = new ResourceLocation(MODID, "textures/gui/cardtable_lightblue.png");
    public static ResourceLocation TEXTURE_GROUND_LIME       = new ResourceLocation(MODID, "textures/gui/cardtable_lime.png");
    public static ResourceLocation TEXTURE_GROUND_MAGENTA    = new ResourceLocation(MODID, "textures/gui/cardtable_magenta.png");
    public static ResourceLocation TEXTURE_GROUND_ORANGE     = new ResourceLocation(MODID, "textures/gui/cardtable_orange.png");
    public static ResourceLocation TEXTURE_GROUND_PINK       = new ResourceLocation(MODID, "textures/gui/cardtable_pink.png");
    public static ResourceLocation TEXTURE_GROUND_PURPLE     = new ResourceLocation(MODID, "textures/gui/cardtable_purple.png");
    public static ResourceLocation TEXTURE_GROUND_RED        = new ResourceLocation(MODID, "textures/gui/cardtable_red.png");
    public static ResourceLocation TEXTURE_GROUND_SILVER     = new ResourceLocation(MODID, "textures/gui/cardtable_silver.png");
    public static ResourceLocation TEXTURE_GROUND_WHITE      = new ResourceLocation(MODID, "textures/gui/cardtable_white.png");
    public static ResourceLocation TEXTURE_GROUND_YELLOW     = new ResourceLocation(MODID, "textures/gui/cardtable_yellow.png");
    public static ResourceLocation TEXTURE_GROUND_ARCADE     = new ResourceLocation(MODID, "textures/gui/arcade.png");
    public static ResourceLocation TEXTURE_GROUND_STARFIELD0 = new ResourceLocation(MODID, "textures/gui/starfield0.png");
    public static ResourceLocation TEXTURE_GROUND_STARFIELD1 = new ResourceLocation(MODID, "textures/gui/starfield1.png");
    public static ResourceLocation TEXTURE_ARCADEDUMMY       = new ResourceLocation(MODID, "textures/gui/arcadedummy.png");
    public static ResourceLocation TEXTURE_TETRIS            = new ResourceLocation(MODID, "textures/gui/tetris.png");
    public static ResourceLocation TEXTURE_COLUMNS           = new ResourceLocation(MODID, "textures/gui/columns.png");
    public static ResourceLocation TEXTURE_MEANMINOS         = new ResourceLocation(MODID, "textures/gui/meanminos.png");
    public static ResourceLocation TEXTURE_OCTAGAMES         = new ResourceLocation(MODID, "textures/gui/octagames.png");
    public static ResourceLocation TEXTURE_CASINO            = new ResourceLocation(MODID, "textures/gui/casino.png");
    public static ResourceLocation TEXTURE_SLOTMACHINE       = new ResourceLocation(MODID, "textures/gui/slotmachine.png");
    public static ResourceLocation TEXTURE_SLOTWHEEL         = new ResourceLocation(MODID, "textures/gui/slotwheel.png");
    public static ResourceLocation TEXTURE_ROULETTE_LEFT     = new ResourceLocation(MODID, "textures/gui/roulette_left.png");
    public static ResourceLocation TEXTURE_ROULETTE_RIGHT    = new ResourceLocation(MODID, "textures/gui/roulette_right.png");
    public static ResourceLocation TEXTURE_ROULETTE_WHEEL    = new ResourceLocation(MODID, "textures/gui/roulette_wheel.png");
    public static ResourceLocation TEXTURE_SICBO_LEFT        = new ResourceLocation(MODID, "textures/gui/sicbo_left.png");
    public static ResourceLocation TEXTURE_SICBO_RIGHT       = new ResourceLocation(MODID, "textures/gui/sicbo_right.png");
    public static ResourceLocation TEXTURE_CRAPS_LEFT        = new ResourceLocation(MODID, "textures/gui/craps_left.png");
    public static ResourceLocation TEXTURE_CRAPS_RIGHT       = new ResourceLocation(MODID, "textures/gui/craps_right.png");
    public static ResourceLocation TEXTURE_ROUGE             = new ResourceLocation(MODID, "textures/gui/cards_rouge.png");
    public static ResourceLocation TEXTURE_NOIR              = new ResourceLocation(MODID, "textures/gui/cards_noir.png");
    public static ResourceLocation TEXTURE_MYSTICSQUARE      = new ResourceLocation(MODID, "textures/gui/mysticsquare.png");
    public static ResourceLocation TEXTURE_2048              = new ResourceLocation(MODID, "textures/gui/2048.png");
    public static ResourceLocation TEXTURE_SUDOKU            = new ResourceLocation(MODID, "textures/gui/sudoku.png");
    public static ResourceLocation TEXTURE_FONT_ARCADE       = new ResourceLocation(MODID, "textures/gui/font_arcade.png");
    public static ResourceLocation TEXTURE_FONT_CARDTABLE    = new ResourceLocation(MODID, "textures/gui/font_cardtable.png");
    public static ResourceLocation TEXTURE_STATIC            = new ResourceLocation(MODID, "textures/gui/static.png");

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.BooleanValue config_allowed_creative = BUILDER.comment("Activate Creative Toggle on Card Tables").define("config_allowed_creative", true);
    public static ForgeConfigSpec.BooleanValue config_fastload         = BUILDER.comment("Transfer Tokens instantly").define("config_fastload", true);

    /**Reads out Config File**/
    public static void loadConfig(Path file){
        SPEC.setConfig(CommentedFileConfig.builder(file).build());
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    /**Register all stuff, pre is true during preInit and false during Init**/
    public static void registerBlocks(){

        // Card Tables
        Register.registerBlock(CARDTABLE_BASE_WHITE    , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_ORANGE   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_MAGENTA  , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_LIGHTBLUE, ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_YELLOW   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_LIME     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_PINK     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_GRAY     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_SILVER   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_CYAN     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_PURPLE   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_BLUE     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_BROWN    , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_GREEN    , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_RED      , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_BASE_BLACK    , ItemGroup.DECORATIONS);

        Register.registerBlock(CARDTABLE_WIDE_WHITE    , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_ORANGE   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_MAGENTA  , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_LIGHTBLUE, ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_YELLOW   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_LIME     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_PINK     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_GRAY     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_SILVER   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_CYAN     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_PURPLE   , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_BLUE     , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_BROWN    , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_GREEN    , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_RED      , ItemGroup.DECORATIONS);
        Register.registerBlock(CARDTABLE_WIDE_BLACK    , ItemGroup.DECORATIONS);

        // Arcdades
        Register.registerBlock(ARCADE_BASE_WHITE    , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_ORANGE   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_MAGENTA  , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_LIGHTBLUE, ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_YELLOW   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_LIME     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_PINK     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_GRAY     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_SILVER   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_CYAN     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_PURPLE   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_BLUE     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_BROWN    , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_GREEN    , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_RED      , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_BASE_BLACK    , ItemGroup.DECORATIONS);

        // Slot Machines
        Register.registerBlock(ARCADE_SLOT_WHITE    , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_ORANGE   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_MAGENTA  , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_LIGHTBLUE, ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_YELLOW   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_LIME     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_PINK     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_GRAY     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_SILVER   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_CYAN     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_PURPLE   , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_BLUE     , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_BROWN    , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_GREEN    , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_RED      , ItemGroup.DECORATIONS);
        Register.registerBlock(ARCADE_SLOT_BLACK    , ItemGroup.DECORATIONS);

        // Basic Dice Blocks
        Register.registerBlock(DICE_BASIC_WHITE    , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_ORANGE   , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_MAGENTA  , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_LIGHTBLUE, ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_YELLOW   , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_LIME     , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_PINK     , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_GRAY     , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_SILVER   , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_CYAN     , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_PURPLE   , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_BLUE     , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_BROWN    , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_GREEN    , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_RED      , ItemGroup.DECORATIONS);
        Register.registerBlock(DICE_BASIC_BLACK    , ItemGroup.DECORATIONS);

        // Powered Dice Blocks
        //Register.registerBlock(DICE_POWER_WHITE    , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_ORANGE   , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_MAGENTA  , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_LIGHTBLUE, ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_YELLOW   , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_LIME     , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_PINK     , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_GRAY     , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_SILVER   , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_CYAN     , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_PURPLE   , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_BLUE     , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_BROWN    , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_GREEN    , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_RED      , ItemGroup.DECORATIONS);
        //Register.registerBlock(DICE_POWER_BLACK    , ItemGroup.DECORATIONS);
    }

    public static void registerItems(){
        Register.registerItem(MODULE_CLAY_WHITE    );
        Register.registerItem(MODULE_CLAY_ORANGE   );
        Register.registerItem(MODULE_CLAY_MAGENTA  );
        Register.registerItem(MODULE_CLAY_LIGHTBLUE);
        Register.registerItem(MODULE_CLAY_YELLOW   );
        Register.registerItem(MODULE_CLAY_LIME     );
        Register.registerItem(MODULE_CLAY_PINK     );
        Register.registerItem(MODULE_CLAY_GRAY     );
        Register.registerItem(MODULE_CLAY_SILVER   );
        Register.registerItem(MODULE_CLAY_CYAN     );
        Register.registerItem(MODULE_CLAY_PURPLE   );
        Register.registerItem(MODULE_CLAY_BLUE     );
        Register.registerItem(MODULE_CLAY_BROWN    );
        Register.registerItem(MODULE_CLAY_GREEN    );
        Register.registerItem(MODULE_CLAY_RED      );
        Register.registerItem(MODULE_CLAY_BLACK    );

        Register.registerItem(MODULE_DUST_WHITE    );
        Register.registerItem(MODULE_DUST_ORANGE   );
        Register.registerItem(MODULE_DUST_MAGENTA  );
        Register.registerItem(MODULE_DUST_LIGHTBLUE);
        Register.registerItem(MODULE_DUST_YELLOW   );
        Register.registerItem(MODULE_DUST_LIME     );
        Register.registerItem(MODULE_DUST_PINK     );
        Register.registerItem(MODULE_DUST_GRAY     );
        Register.registerItem(MODULE_DUST_SILVER   );
        Register.registerItem(MODULE_DUST_CYAN     );
        Register.registerItem(MODULE_DUST_PURPLE   );
        Register.registerItem(MODULE_DUST_BLUE     );
        Register.registerItem(MODULE_DUST_BROWN    );
        Register.registerItem(MODULE_DUST_GREEN    );
        Register.registerItem(MODULE_DUST_RED      );
        Register.registerItem(MODULE_DUST_BLACK    );

        Register.registerItem(MODULE_CARD_WHITE    );
        Register.registerItem(MODULE_CARD_ORANGE   );
        Register.registerItem(MODULE_CARD_MAGENTA  );
        Register.registerItem(MODULE_CARD_LIGHTBLUE);
        Register.registerItem(MODULE_CARD_YELLOW   );
        Register.registerItem(MODULE_CARD_LIME     );
        Register.registerItem(MODULE_CARD_PINK     );
        Register.registerItem(MODULE_CARD_GRAY     );
        Register.registerItem(MODULE_CARD_SILVER   );
        Register.registerItem(MODULE_CARD_CYAN     );
        Register.registerItem(MODULE_CARD_PURPLE   );
        Register.registerItem(MODULE_CARD_BLUE     );
        Register.registerItem(MODULE_CARD_BROWN    );
        Register.registerItem(MODULE_CARD_GREEN    );
        Register.registerItem(MODULE_CARD_RED      );
        Register.registerItem(MODULE_CARD_BLACK    );
    }

    //public static final TileEntityType<TileEntityArcade>    TILETYPE_ARCADE    = TileEntityType.register(MODID + ":arcade",    TileEntityType.Builder.create(TileEntityArcade::new));
    //public static final TileEntityType<TileEntityCardTable> TILETYPE_CARDTABLE = TileEntityType.register(MODID + ":cardtable", TileEntityType.Builder.create(TileEntityCardTable::new));

    //public static final TileEntityType<TileEntityBlackJack>   TILETYPE_BLACKJACK   = TileEntityType.register(MODID + ":blackjack",   TileEntityType.Builder.create(TileEntityBlackJack::new));
    //public static final TileEntityType<TileEntityBaccarat>    TILETYPE_BACCARAT    = TileEntityType.register(MODID + ":baccarat",    TileEntityType.Builder.create(TileEntityBaccarat::new));
    //public static final TileEntityType<TileEntityVideoPoker>  TILETYPE_VIDEOPOKER  = TileEntityType.register(MODID + ":videopoker",  TileEntityType.Builder.create(TileEntityVideoPoker::new));
    //public static final TileEntityType<TileEntityAceyDeucey>  TILETYPE_ACEYDEUCEY  = TileEntityType.register(MODID + ":aceydeucey",  TileEntityType.Builder.create(TileEntityAceyDeucey::new));
    //public static final TileEntityType<TileEntityRougeEtNoir> TILETYPE_ROUGEETNOIR = TileEntityType.register(MODID + ":rougeetnoir", TileEntityType.Builder.create(TileEntityRougeEtNoir::new));
    //public static final TileEntityType<TileEntityCraps>       TILETYPE_CRAPS       = TileEntityType.register(MODID + ":craps",       TileEntityType.Builder.create(TileEntityCraps::new));
    //public static final TileEntityType<TileEntitySicBo>       TILETYPE_SICBO       = TileEntityType.register(MODID + ":sicbo",       TileEntityType.Builder.create(TileEntitySicBo::new));

    //public static final TileEntityType<TileEntityPyramid>  TILETYPE_PYRAMID  = TileEntityType.register(MODID + ":pyramid",  TileEntityType.Builder.create(TileEntityPyramid::new));
    //public static final TileEntityType<TileEntityTriPeak>  TILETYPE_TRIPEAK  = TileEntityType.register(MODID + ":tripeak",  TileEntityType.Builder.create(TileEntityTriPeak::new));
    //public static final TileEntityType<TileEntityFreeCell> TILETYPE_FREECELL = TileEntityType.register(MODID + ":freecell", TileEntityType.Builder.create(TileEntityFreeCell::new));
    //public static final TileEntityType<TileEntityKlondike> TILETYPE_KLONDIKE = TileEntityType.register(MODID + ":klondike", TileEntityType.Builder.create(TileEntityKlondike::new));
    //public static final TileEntityType<TileEntitySpider>   TILETYPE_SPIDER   = TileEntityType.register(MODID + ":spider",   TileEntityType.Builder.create(TileEntitySpider::new));

    //public static final TileEntityType<TileEntityMemory>       TILETYPE_MEMORY       = TileEntityType.register(MODID + ":memory",       TileEntityType.Builder.create(TileEntityMemory::new));
    //public static final TileEntityType<TileEntityMysticSquare> TILETYPE_MYSTICSQUARE = TileEntityType.register(MODID + ":mysticsquare", TileEntityType.Builder.create(TileEntityMysticSquare::new));
    //public static final TileEntityType<TileEntitySudoku>       TILETYPE_SUDOKU       = TileEntityType.register(MODID + ":sudoku",       TileEntityType.Builder.create(TileEntitySudoku::new));
    //public static final TileEntityType<TileEntityHalma>        TILETYPE_HALMA        = TileEntityType.register(MODID + ":halma",        TileEntityType.Builder.create(TileEntityHalma::new));
    //public static final TileEntityType<TileEntityMinesweeper>  TILETYPE_MINESWEEPER  = TileEntityType.register(MODID + ":minesweeper",  TileEntityType.Builder.create(TileEntityMinesweeper::new));

    //public static final TileEntityType<TileEntityTetris>    TILETYPE_TETRIS    = TileEntityType.register(MODID + ":tetris",    TileEntityType.Builder.create(TileEntityTetris::new));
    //public static final TileEntityType<TileEntityColumns>   TILETYPE_COLUMNS   = TileEntityType.register(MODID + ":columns",   TileEntityType.Builder.create(TileEntityColumns::new));
    //public static final TileEntityType<TileEntityMeanMinos> TILETYPE_MEANMINOS = TileEntityType.register(MODID + ":meanminos", TileEntityType.Builder.create(TileEntityMeanMinos::new));
    //public static final TileEntityType<TileEntitySnake>     TILETYPE_SNAKE     = TileEntityType.register(MODID + ":snake",     TileEntityType.Builder.create(TileEntitySnake::new));
    //public static final TileEntityType<TileEntitySokoban>   TILETYPE_SOKOBAN   = TileEntityType.register(MODID + ":sokoban",   TileEntityType.Builder.create(TileEntitySokoban::new));
    //public static final TileEntityType<TileEntity2048>      TILETYPE_2048      = TileEntityType.register(MODID + ":_2048",     TileEntityType.Builder.create(TileEntity2048::new));

    public static final ResourceLocation GUIID_ARCADE    = new ResourceLocation(MODID, "arcade");
    public static final ResourceLocation GUIID_CARDTABLE = new ResourceLocation(MODID, "cardtable");

    public static final ResourceLocation GUIID_BLACKJACK   = new ResourceLocation(MODID, "blackjack");
    public static final ResourceLocation GUIID_BACCARAT    = new ResourceLocation(MODID, "baccarat");
    public static final ResourceLocation GUIID_VIDEOPOKER  = new ResourceLocation(MODID, "videopoker");
    public static final ResourceLocation GUIID_ACEYDEUCEY  = new ResourceLocation(MODID, "aceydeucey");
    public static final ResourceLocation GUIID_ROUGEETNOIR = new ResourceLocation(MODID, "rougeetnoir");
    public static final ResourceLocation GUIID_CRAPS       = new ResourceLocation(MODID, "craps");
    public static final ResourceLocation GUIID_SICBO       = new ResourceLocation(MODID, "sicbo");
    public static final ResourceLocation GUIID_ROULETTE    = new ResourceLocation(MODID, "roulette");

    public static final ResourceLocation GUIID_PYRAMID  = new ResourceLocation(MODID, "pyramid");
    public static final ResourceLocation GUIID_TRIPEAK  = new ResourceLocation(MODID, "tripeak");
    public static final ResourceLocation GUIID_FREECELL = new ResourceLocation(MODID, "freecell");
    public static final ResourceLocation GUIID_KLONDIKE = new ResourceLocation(MODID, "klondike");
    public static final ResourceLocation GUIID_SPIDER   = new ResourceLocation(MODID, "spider");

    public static final ResourceLocation GUIID_MEMORY       = new ResourceLocation(MODID, "memory");
    public static final ResourceLocation GUIID_MYSTICSQUARE = new ResourceLocation(MODID, "mysticsquare");
    public static final ResourceLocation GUIID_SUDOKU       = new ResourceLocation(MODID, "sudoku");
    public static final ResourceLocation GUIID_HALMA        = new ResourceLocation(MODID, "halma");
    public static final ResourceLocation GUIID_MINESWEEPER  = new ResourceLocation(MODID, "minesweeper");

    public static final ResourceLocation GUIID_TETRIS    = new ResourceLocation(MODID, "tetris");
    public static final ResourceLocation GUIID_COLUMNS   = new ResourceLocation(MODID, "columns");
    public static final ResourceLocation GUIID_MEANMINOS = new ResourceLocation(MODID, "meanminos");
    public static final ResourceLocation GUIID_SNAKE     = new ResourceLocation(MODID, "snake");
    public static final ResourceLocation GUIID_SOKOBAN   = new ResourceLocation(MODID, "sokoban");
    public static final ResourceLocation GUIID_2048      = new ResourceLocation(MODID, "_2048");

    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event){
        event.getRegistry().register(TileEntityArcade.TYPE_BLACK    );
        event.getRegistry().register(TileEntityArcade.TYPE_BLUE     );
        event.getRegistry().register(TileEntityArcade.TYPE_BROWN    );
        event.getRegistry().register(TileEntityArcade.TYPE_CYAN     );
        event.getRegistry().register(TileEntityArcade.TYPE_GRAY     );
        event.getRegistry().register(TileEntityArcade.TYPE_GREEN    );
        event.getRegistry().register(TileEntityArcade.TYPE_LIGHTBLUE);
        event.getRegistry().register(TileEntityArcade.TYPE_LIME     );
        event.getRegistry().register(TileEntityArcade.TYPE_MAGENTA  );
        event.getRegistry().register(TileEntityArcade.TYPE_ORANGE   );
        event.getRegistry().register(TileEntityArcade.TYPE_PINK     );
        event.getRegistry().register(TileEntityArcade.TYPE_PURPLE   );
        event.getRegistry().register(TileEntityArcade.TYPE_RED      );
        event.getRegistry().register(TileEntityArcade.TYPE_SILVER   );
        event.getRegistry().register(TileEntityArcade.TYPE_WHITE    );
        event.getRegistry().register(TileEntityArcade.TYPE_YELLOW   );

        event.getRegistry().register(TileEntityCardTableBase.TYPE_BLACK    );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_BLUE     );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_BROWN    );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_CYAN     );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_GRAY     );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_GREEN    );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_LIGHTBLUE);
        event.getRegistry().register(TileEntityCardTableBase.TYPE_LIME     );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_MAGENTA  );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_ORANGE   );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_PINK     );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_PURPLE   );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_RED      );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_SILVER   );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_WHITE    );
        event.getRegistry().register(TileEntityCardTableBase.TYPE_YELLOW   );

        event.getRegistry().register(TileEntityCardTableWide.TYPE_BLACK    );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_BLUE     );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_BROWN    );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_CYAN     );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_GRAY     );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_GREEN    );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_LIGHTBLUE);
        event.getRegistry().register(TileEntityCardTableWide.TYPE_LIME     );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_MAGENTA  );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_ORANGE   );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_PINK     );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_PURPLE   );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_RED      );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_SILVER   );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_WHITE    );
        event.getRegistry().register(TileEntityCardTableWide.TYPE_YELLOW   );

        event.getRegistry().register(TileEntitySlotMachine.TYPE_BLACK    );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_BLUE     );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_BROWN    );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_CYAN     );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_GRAY     );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_GREEN    );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_LIGHTBLUE);
        event.getRegistry().register(TileEntitySlotMachine.TYPE_LIME     );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_MAGENTA  );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_ORANGE   );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_PINK     );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_PURPLE   );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_RED      );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_SILVER   );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_WHITE    );
        event.getRegistry().register(TileEntitySlotMachine.TYPE_YELLOW   );

    }

    public static void registerScreen(){
        ScreenManager.registerFactory(ContainerArcade.TYPE, GuiArcade::new);
        ScreenManager.registerFactory(ContainerCardTable.TYPE, GuiCardTable::new);
        ScreenManager.registerFactory(ContainerSlotMachine.TYPE, GuiSlotMachine::new);

        ScreenManager.registerFactory(ContainerAceyDeucey.TYPE, GuiAceyDeucey::new);
        ScreenManager.registerFactory(ContainerBaccarat.TYPE, GuiBaccarat::new);
        ScreenManager.registerFactory(ContainerBlackJack.TYPE, GuiBlackJack::new);
        ScreenManager.registerFactory(ContainerFreeCell.TYPE, GuiFreeCell::new);
        ScreenManager.registerFactory(ContainerKlondike.TYPE, GuiKlondike::new);
        ScreenManager.registerFactory(ContainerPyramid.TYPE, GuiPyramid::new);
        ScreenManager.registerFactory(ContainerRougeEtNoir.TYPE, GuiRougeEtNoir::new);
        ScreenManager.registerFactory(ContainerSpider.TYPE, GuiSpider::new);
        ScreenManager.registerFactory(ContainerTriPeak.TYPE, GuiTriPeak::new);
        ScreenManager.registerFactory(ContainerVideoPoker.TYPE, GuiVideoPoker::new);

        ScreenManager.registerFactory(ContainerCraps.TYPE, GuiCraps::new);
        ScreenManager.registerFactory(ContainerHalma.TYPE, GuiHalma::new);
        ScreenManager.registerFactory(ContainerMemory.TYPE, GuiMemory::new);
        ScreenManager.registerFactory(ContainerMysticSquare.TYPE, GuiMysticSquare::new);
        ScreenManager.registerFactory(ContainerRoulette.TYPE, GuiRoulette::new);
        ScreenManager.registerFactory(ContainerSicBo.TYPE, GuiSicBo::new);
        ScreenManager.registerFactory(ContainerSudoku.TYPE, GuiSudoku::new);

        ScreenManager.registerFactory(Container2048.TYPE, Gui2048::new);
        ScreenManager.registerFactory(ContainerColumns.TYPE, GuiColumns::new);
        ScreenManager.registerFactory(ContainerMeanMinos.TYPE, GuiMeanMinos::new);
        ScreenManager.registerFactory(ContainerMinesweeper.TYPE, GuiMinesweeper::new);
        ScreenManager.registerFactory(ContainerSnake.TYPE, GuiSnake::new);
        ScreenManager.registerFactory(ContainerSokoban.TYPE, GuiSokoban::new);
        ScreenManager.registerFactory(ContainerTetris.TYPE, GuiTetris::new);

        ScreenManager.registerFactory(ContainerSlotGame.TYPE, GuiSlotGame::new);
        ScreenManager.registerFactory(ContainerField.TYPE, GuiField::new);
        ScreenManager.registerFactory(ContainerNoise.TYPE, GuiNoise::new);
    }

    public static void registerContainer(IForgeRegistry<ContainerType<?>> registry) {
        registry.register(ContainerArcade.TYPE);
        registry.register(ContainerCardTable.TYPE);
        registry.register(ContainerSlotMachine.TYPE);

        registry.register(ContainerAceyDeucey.TYPE);
        registry.register(ContainerBaccarat.TYPE);
        registry.register(ContainerBlackJack.TYPE);
        registry.register(ContainerFreeCell.TYPE);
        registry.register(ContainerKlondike.TYPE);
        registry.register(ContainerPyramid.TYPE);
        registry.register(ContainerRougeEtNoir.TYPE);
        registry.register(ContainerSpider.TYPE);
        registry.register(ContainerTriPeak.TYPE);
        registry.register(ContainerVideoPoker.TYPE);

        registry.register(ContainerCraps.TYPE);
        registry.register(ContainerHalma.TYPE);
        registry.register(ContainerMemory.TYPE);
        registry.register(ContainerMysticSquare.TYPE);
        registry.register(ContainerRoulette.TYPE);
        registry.register(ContainerSicBo.TYPE);
        registry.register(ContainerSudoku.TYPE);

        registry.register(Container2048.TYPE);
        registry.register(ContainerColumns.TYPE);
        registry.register(ContainerMeanMinos.TYPE);
        registry.register(ContainerMinesweeper.TYPE);
        registry.register(ContainerSnake.TYPE);
        registry.register(ContainerSokoban.TYPE);
        registry.register(ContainerTetris.TYPE);

        registry.register(ContainerField.TYPE);
        registry.register(ContainerNoise.TYPE);
        registry.register(ContainerSlotGame.TYPE);
    }

    //public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
    //    for(String entityName : specialEntities.keySet()) {
    //        EntityType.Builder entityTypeBuilder = EntityType.Builder.create(EntityFactory.getInstance(), EntityClassification.MISC);
    //        entityTypeBuilder.setTrackingRange(10);
    //        entityTypeBuilder.setUpdateInterval(3);
    //        entityTypeBuilder.setShouldReceiveVelocityUpdates(true);
    //        entityTypeBuilder.disableSummoning();
    //        EntityType entityType = entityTypeBuilder.build(entityName);
    //        entityType.setRegistryName(LycanitesMobs.MODID, entityName);
    //        EntityFactory.getInstance().addEntityType(entityType, specialEntityConstructors.get(specialEntities.get(entityName)), entityName);
    //        specialEntityTypes.put(specialEntities.get(entityName), entityType);
    //        event.getRegistry().register(entityType);
    //    }
    //}



    /**Register Living Entities**/
    public static void registerEntities(){
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_ARCADE);
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_CARDTABLE);

        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_BLACKJACK  );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_BACCARAT   );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_VIDEOPOKER );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_ACEYDEUCEY );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_ROUGEETNOIR);
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_CRAPS      );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SICBO      );

        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_PYRAMID );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_TRIPEAK );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_FREECELL);
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_KLONDIKE);
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SPIDER  );

        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MEMORY      );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MYSTICSQUARE);
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SUDOKU      );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_HALMA       );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MINESWEEPER );

        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_TETRIS   );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_COLUMNS  );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MEANMINOS);
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SNAKE    );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SOKOBAN  );
        //ForgeRegistries.TILE_ENTITIES.register(TILETYPE_2048     );
        //GameRegistry.registerTileEntity(TileEntityArcade      .class, new ResourceLocation(CasinoCraft.modid, "casino_arcade"));
        //GameRegistry.registerTileEntity(TileEntityCardTable   .class, new ResourceLocation(CasinoCraft.modid, "casino_cardtable"));
        //GameRegistry.registerTileEntity(TileEntityBoard       .class, new ResourceLocation(CasinoCraft.modid, "casino_board"));
        //GameRegistry.registerTileEntity(TileEntityCasino      .class, new ResourceLocation(CasinoCraft.modid, "casino_casino"));

        //// Casino Games
        //GameRegistry.registerTileEntity(TileEntityBlackJack  .class, new ResourceLocation(CasinoCraft.modid, "casino_blackjack"));
        //GameRegistry.registerTileEntity(TileEntityBaccarat   .class, new ResourceLocation(CasinoCraft.modid, "casino_baccarat"));
        //GameRegistry.registerTileEntity(TileEntityAceyDeucey .class, new ResourceLocation(CasinoCraft.modid, "casino_aceydeucey"));
        //GameRegistry.registerTileEntity(TileEntityRougeEtNoir.class, new ResourceLocation(CasinoCraft.modid, "casino_rougeetnoir"));
        //GameRegistry.registerTileEntity(TileEntityCraps      .class, new ResourceLocation(CasinoCraft.modid, "casino_craps"));
        //GameRegistry.registerTileEntity(TileEntitySicBo      .class, new ResourceLocation(CasinoCraft.modid, "casino_sicbo"));
        //GameRegistry.registerTileEntity(TileEntityRoulette   .class, new ResourceLocation(CasinoCraft.modid, "casino_roulette"));
        //GameRegistry.registerTileEntity(TileEntitySlotMachine.class, new ResourceLocation(CasinoCraft.modid, "casino_slotmachine"));
        //GameRegistry.registerTileEntity(TileEntityVideoPoker .class, new ResourceLocation(CasinoCraft.modid, "casino_videopoker"));

        //// Card Games
        //GameRegistry.registerTileEntity(TileEntityPyramid .class, new ResourceLocation(CasinoCraft.modid, "casino_pyramid"));
        //GameRegistry.registerTileEntity(TileEntityTriPeak .class, new ResourceLocation(CasinoCraft.modid, "casino_tripeak"));
        //GameRegistry.registerTileEntity(TileEntityFreeCell.class, new ResourceLocation(CasinoCraft.modid, "casino_freecell"));
        //GameRegistry.registerTileEntity(TileEntityKlondike.class, new ResourceLocation(CasinoCraft.modid, "casino_klondike"));
        //GameRegistry.registerTileEntity(TileEntitySpider  .class, new ResourceLocation(CasinoCraft.modid, "casino_spider"));

        //// Arcade Games
        //GameRegistry.registerTileEntity(TileEntityTetris     .class, new ResourceLocation(CasinoCraft.modid, "casino_tetris"));
        //GameRegistry.registerTileEntity(TileEntityColumns    .class, new ResourceLocation(CasinoCraft.modid, "casino_columns"));
        //GameRegistry.registerTileEntity(TileEntityMeanMinos  .class, new ResourceLocation(CasinoCraft.modid, "casino_meanminos"));
        //GameRegistry.registerTileEntity(TileEntity2048       .class, new ResourceLocation(CasinoCraft.modid, "casino_2048"));
        //GameRegistry.registerTileEntity(TileEntitySokoban    .class, new ResourceLocation(CasinoCraft.modid, "casino_sokoban"));
        //GameRegistry.registerTileEntity(TileEntitySnake      .class, new ResourceLocation(CasinoCraft.modid, "casino_snake"));
        //GameRegistry.registerTileEntity(TileEntityMinesweeper.class, new ResourceLocation(CasinoCraft.modid, "casino_minesweeper"));

        //// Normal Games
        //GameRegistry.registerTileEntity(TileEntityMemory      .class, new ResourceLocation(CasinoCraft.modid, "casino_memory"));
        //GameRegistry.registerTileEntity(TileEntityMysticSquare.class, new ResourceLocation(CasinoCraft.modid, "casino_mysticsquare"));
        //GameRegistry.registerTileEntity(TileEntitySudoku      .class, new ResourceLocation(CasinoCraft.modid, "casino_sudoku"));
        //GameRegistry.registerTileEntity(TileEntityHalma       .class, new ResourceLocation(CasinoCraft.modid, "casino_halma"));
    }

}
