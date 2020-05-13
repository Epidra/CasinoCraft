package mod.casinocraft;

import mod.casinocraft.blocks.*;
import mod.casinocraft.tileentities.TileEntityCardTableWide;
import mod.casinocraft.tileentities.TileEntitySlotMachine;
import mod.shared.Register;
import mod.shared.items.ItemItem;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityCardTableBase;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static mod.casinocraft.CasinoCraft.modid;

public class CasinoKeeper {
	
	// Tables
	public static final Block CARDTABLE_BASE_WHITE     = new BlockCardTableBase("cardtable_base_white", EnumDyeColor.WHITE);
	public static final Block CARDTABLE_BASE_ORANGE    = new BlockCardTableBase("cardtable_base_orange", EnumDyeColor.ORANGE);
	public static final Block CARDTABLE_BASE_MAGENTA   = new BlockCardTableBase("cardtable_base_magenta", EnumDyeColor.MAGENTA);
	public static final Block CARDTABLE_BASE_LIGHTBLUE = new BlockCardTableBase("cardtable_base_lightblue", EnumDyeColor.LIGHT_BLUE);
	public static final Block CARDTABLE_BASE_YELLOW    = new BlockCardTableBase("cardtable_base_yellow", EnumDyeColor.YELLOW);
	public static final Block CARDTABLE_BASE_LIME      = new BlockCardTableBase("cardtable_base_lime", EnumDyeColor.LIME);
	public static final Block CARDTABLE_BASE_PINK      = new BlockCardTableBase("cardtable_base_pink", EnumDyeColor.PINK);
	public static final Block CARDTABLE_BASE_GRAY      = new BlockCardTableBase("cardtable_base_gray", EnumDyeColor.GRAY);
	public static final Block CARDTABLE_BASE_SILVER    = new BlockCardTableBase("cardtable_base_silver", EnumDyeColor.SILVER);
	public static final Block CARDTABLE_BASE_CYAN      = new BlockCardTableBase("cardtable_base_cyan", EnumDyeColor.CYAN);
	public static final Block CARDTABLE_BASE_PURPLE    = new BlockCardTableBase("cardtable_base_purple", EnumDyeColor.PURPLE);
	public static final Block CARDTABLE_BASE_BLUE      = new BlockCardTableBase("cardtable_base_blue", EnumDyeColor.BLUE);
	public static final Block CARDTABLE_BASE_BROWN     = new BlockCardTableBase("cardtable_base_brown", EnumDyeColor.BROWN);
	public static final Block CARDTABLE_BASE_GREEN     = new BlockCardTableBase("cardtable_base_green", EnumDyeColor.GREEN);
	public static final Block CARDTABLE_BASE_RED       = new BlockCardTableBase("cardtable_base_red", EnumDyeColor.RED);
	public static final Block CARDTABLE_BASE_BLACK     = new BlockCardTableBase("cardtable_base_black", EnumDyeColor.BLACK);
	public static final Block CARDTABLE_WIDE_WHITE     = new BlockCardTableWide("cardtable_wide_white", EnumDyeColor.WHITE);
	public static final Block CARDTABLE_WIDE_ORANGE    = new BlockCardTableWide("cardtable_wide_orange", EnumDyeColor.ORANGE);
	public static final Block CARDTABLE_WIDE_MAGENTA   = new BlockCardTableWide("cardtable_wide_magenta", EnumDyeColor.MAGENTA);
	public static final Block CARDTABLE_WIDE_LIGHTBLUE = new BlockCardTableWide("cardtable_wide_lightblue", EnumDyeColor.LIGHT_BLUE);
	public static final Block CARDTABLE_WIDE_YELLOW    = new BlockCardTableWide("cardtable_wide_yellow", EnumDyeColor.YELLOW);
	public static final Block CARDTABLE_WIDE_LIME      = new BlockCardTableWide("cardtable_wide_lime", EnumDyeColor.LIME);
	public static final Block CARDTABLE_WIDE_PINK      = new BlockCardTableWide("cardtable_wide_pink", EnumDyeColor.PINK);
	public static final Block CARDTABLE_WIDE_GRAY      = new BlockCardTableWide("cardtable_wide_gray", EnumDyeColor.GRAY);
	public static final Block CARDTABLE_WIDE_SILVER    = new BlockCardTableWide("cardtable_wide_silver", EnumDyeColor.SILVER);
	public static final Block CARDTABLE_WIDE_CYAN      = new BlockCardTableWide("cardtable_wide_cyan", EnumDyeColor.CYAN);
	public static final Block CARDTABLE_WIDE_PURPLE    = new BlockCardTableWide("cardtable_wide_purple", EnumDyeColor.PURPLE);
	public static final Block CARDTABLE_WIDE_BLUE      = new BlockCardTableWide("cardtable_wide_blue", EnumDyeColor.BLUE);
	public static final Block CARDTABLE_WIDE_BROWN     = new BlockCardTableWide("cardtable_wide_brown", EnumDyeColor.BROWN);
	public static final Block CARDTABLE_WIDE_GREEN     = new BlockCardTableWide("cardtable_wide_green", EnumDyeColor.GREEN);
	public static final Block CARDTABLE_WIDE_RED       = new BlockCardTableWide("cardtable_wide_red", EnumDyeColor.RED);
	public static final Block CARDTABLE_WIDE_BLACK     = new BlockCardTableWide("cardtable_wide_black", EnumDyeColor.BLACK);
	
	// Arcades
	public static final Block ARCADE_BASE_WHITE     = new BlockArcade(     "arcade_base_white", EnumDyeColor.WHITE);
	public static final Block ARCADE_BASE_ORANGE    = new BlockArcade(     "arcade_base_orange", EnumDyeColor.ORANGE);
	public static final Block ARCADE_BASE_MAGENTA   = new BlockArcade(     "arcade_base_magenta", EnumDyeColor.MAGENTA);
	public static final Block ARCADE_BASE_LIGHTBLUE = new BlockArcade(     "arcade_base_lightblue", EnumDyeColor.LIGHT_BLUE);
	public static final Block ARCADE_BASE_YELLOW    = new BlockArcade(     "arcade_base_yellow", EnumDyeColor.YELLOW);
	public static final Block ARCADE_BASE_LIME      = new BlockArcade(     "arcade_base_lime", EnumDyeColor.LIME);
	public static final Block ARCADE_BASE_PINK      = new BlockArcade(     "arcade_base_pink", EnumDyeColor.PINK);
	public static final Block ARCADE_BASE_GRAY      = new BlockArcade(     "arcade_base_gray", EnumDyeColor.GRAY);
	public static final Block ARCADE_BASE_SILVER    = new BlockArcade(     "arcade_base_silver", EnumDyeColor.SILVER);
	public static final Block ARCADE_BASE_CYAN      = new BlockArcade(     "arcade_base_cyan", EnumDyeColor.CYAN);
	public static final Block ARCADE_BASE_PURPLE    = new BlockArcade(     "arcade_base_purple", EnumDyeColor.PURPLE);
	public static final Block ARCADE_BASE_BLUE      = new BlockArcade(     "arcade_base_blue", EnumDyeColor.BLUE);
	public static final Block ARCADE_BASE_BROWN     = new BlockArcade(     "arcade_base_brown", EnumDyeColor.BROWN);
	public static final Block ARCADE_BASE_GREEN     = new BlockArcade(     "arcade_base_green", EnumDyeColor.GREEN);
	public static final Block ARCADE_BASE_RED       = new BlockArcade(     "arcade_base_red", EnumDyeColor.RED);
	public static final Block ARCADE_BASE_BLACK     = new BlockArcade(     "arcade_base_black", EnumDyeColor.BLACK);
	public static final Block ARCADE_SLOT_WHITE     = new BlockSlotMachine("arcade_slot_white", EnumDyeColor.WHITE);
	public static final Block ARCADE_SLOT_ORANGE    = new BlockSlotMachine("arcade_slot_orange", EnumDyeColor.ORANGE);
	public static final Block ARCADE_SLOT_MAGENTA   = new BlockSlotMachine("arcade_slot_magenta", EnumDyeColor.MAGENTA);
	public static final Block ARCADE_SLOT_LIGHTBLUE = new BlockSlotMachine("arcade_slot_lightblue", EnumDyeColor.LIGHT_BLUE);
	public static final Block ARCADE_SLOT_YELLOW    = new BlockSlotMachine("arcade_slot_yellow", EnumDyeColor.YELLOW);
	public static final Block ARCADE_SLOT_LIME      = new BlockSlotMachine("arcade_slot_lime", EnumDyeColor.LIME);
	public static final Block ARCADE_SLOT_PINK      = new BlockSlotMachine("arcade_slot_pink", EnumDyeColor.PINK);
	public static final Block ARCADE_SLOT_GRAY      = new BlockSlotMachine("arcade_slot_gray", EnumDyeColor.GRAY);
	public static final Block ARCADE_SLOT_SILVER    = new BlockSlotMachine("arcade_slot_silver", EnumDyeColor.SILVER);
	public static final Block ARCADE_SLOT_CYAN      = new BlockSlotMachine("arcade_slot_cyan", EnumDyeColor.CYAN);
	public static final Block ARCADE_SLOT_PURPLE    = new BlockSlotMachine("arcade_slot_purple", EnumDyeColor.PURPLE);
	public static final Block ARCADE_SLOT_BLUE      = new BlockSlotMachine("arcade_slot_blue", EnumDyeColor.BLUE);
	public static final Block ARCADE_SLOT_BROWN     = new BlockSlotMachine("arcade_slot_brown", EnumDyeColor.BROWN);
	public static final Block ARCADE_SLOT_GREEN     = new BlockSlotMachine("arcade_slot_green", EnumDyeColor.GREEN);
	public static final Block ARCADE_SLOT_RED       = new BlockSlotMachine("arcade_slot_red", EnumDyeColor.RED);
	public static final Block ARCADE_SLOT_BLACK     = new BlockSlotMachine("arcade_slot_black", EnumDyeColor.BLACK);
	
	// Dice
	public static final Block DICE_BASIC_WHITE     = new BlockDice("dice_basic_white");
	public static final Block DICE_BASIC_ORANGE    = new BlockDice("dice_basic_orange");
	public static final Block DICE_BASIC_MAGENTA   = new BlockDice("dice_basic_magenta");
	public static final Block DICE_BASIC_LIGHTBLUE = new BlockDice("dice_basic_lightblue");
	public static final Block DICE_BASIC_YELLOW    = new BlockDice("dice_basic_yellow");
	public static final Block DICE_BASIC_LIME      = new BlockDice("dice_basic_lime");
	public static final Block DICE_BASIC_PINK      = new BlockDice("dice_basic_pink");
	public static final Block DICE_BASIC_GRAY      = new BlockDice("dice_basic_gray");
	public static final Block DICE_BASIC_SILVER    = new BlockDice("dice_basic_silver");
	public static final Block DICE_BASIC_CYAN      = new BlockDice("dice_basic_cyan");
	public static final Block DICE_BASIC_PURPLE    = new BlockDice("dice_basic_purple");
	public static final Block DICE_BASIC_BLUE      = new BlockDice("dice_basic_blue");
	public static final Block DICE_BASIC_BROWN     = new BlockDice("dice_basic_brown");
	public static final Block DICE_BASIC_GREEN     = new BlockDice("dice_basic_green");
	public static final Block DICE_BASIC_RED       = new BlockDice("dice_basic_red");
	public static final Block DICE_BASIC_BLACK     = new BlockDice("dice_basic_black");
	
	// Modules
	public static final Item MODULE_MINO_WHITE     = new ItemItem("module_mino_white");
	public static final Item MODULE_MINO_ORANGE    = new ItemItem("module_mino_orange");
	public static final Item MODULE_MINO_MAGENTA   = new ItemItem("module_mino_magenta");
	public static final Item MODULE_MINO_LIGHTBLUE = new ItemItem("module_mino_lightblue");
	public static final Item MODULE_MINO_YELLOW    = new ItemItem("module_mino_yellow");
	public static final Item MODULE_MINO_LIME      = new ItemItem("module_mino_lime");
	public static final Item MODULE_MINO_PINK      = new ItemItem("module_mino_pink");
	public static final Item MODULE_MINO_GRAY      = new ItemItem("module_mino_gray");
	public static final Item MODULE_MINO_SILVER    = new ItemItem("module_mino_silver");
	public static final Item MODULE_MINO_CYAN      = new ItemItem("module_mino_cyan");
	public static final Item MODULE_MINO_PURPLE    = new ItemItem("module_mino_purple");
	public static final Item MODULE_MINO_BLUE      = new ItemItem("module_mino_blue");
	public static final Item MODULE_MINO_BROWN     = new ItemItem("module_mino_brown");
	public static final Item MODULE_MINO_GREEN     = new ItemItem("module_mino_green");
	public static final Item MODULE_MINO_RED       = new ItemItem("module_mino_red");
	public static final Item MODULE_MINO_BLACK     = new ItemItem("module_mino_black");
	public static final Item MODULE_CARD_WHITE     = new ItemItem("module_card_white");
	public static final Item MODULE_CARD_ORANGE    = new ItemItem("module_card_orange");
	public static final Item MODULE_CARD_MAGENTA   = new ItemItem("module_card_magenta");
	public static final Item MODULE_CARD_LIGHTBLUE = new ItemItem("module_card_lightblue");
	public static final Item MODULE_CARD_YELLOW    = new ItemItem("module_card_yellow");
	public static final Item MODULE_CARD_LIME      = new ItemItem("module_card_lime");
	public static final Item MODULE_CARD_PINK      = new ItemItem("module_card_pink");
	public static final Item MODULE_CARD_GRAY      = new ItemItem("module_card_gray");
	public static final Item MODULE_CARD_SILVER    = new ItemItem("module_card_silver");
	public static final Item MODULE_CARD_CYAN      = new ItemItem("module_card_cyan");
	public static final Item MODULE_CARD_PURPLE    = new ItemItem("module_card_purple");
	public static final Item MODULE_CARD_BLUE      = new ItemItem("module_card_blue");
	public static final Item MODULE_CARD_BROWN     = new ItemItem("module_card_brown");
	public static final Item MODULE_CARD_GREEN     = new ItemItem("module_card_green");
	public static final Item MODULE_CARD_RED       = new ItemItem("module_card_red");
	public static final Item MODULE_CARD_BLACK     = new ItemItem("module_card_black");
	public static final Item MODULE_CHIP_WHITE     = new ItemItem("module_chip_white");
	public static final Item MODULE_CHIP_ORANGE    = new ItemItem("module_chip_orange");
	public static final Item MODULE_CHIP_MAGENTA   = new ItemItem("module_chip_magenta");
	public static final Item MODULE_CHIP_LIGHTBLUE = new ItemItem("module_chip_lightblue");
	public static final Item MODULE_CHIP_YELLOW    = new ItemItem("module_chip_yellow");
	public static final Item MODULE_CHIP_LIME      = new ItemItem("module_chip_lime");
	public static final Item MODULE_CHIP_PINK      = new ItemItem("module_chip_pink");
	public static final Item MODULE_CHIP_GRAY      = new ItemItem("module_chip_gray");
	public static final Item MODULE_CHIP_SILVER    = new ItemItem("module_chip_silver");
	public static final Item MODULE_CHIP_CYAN      = new ItemItem("module_chip_cyan");
	public static final Item MODULE_CHIP_PURPLE    = new ItemItem("module_chip_purple");
	public static final Item MODULE_CHIP_BLUE      = new ItemItem("module_chip_blue");
	public static final Item MODULE_CHIP_BROWN     = new ItemItem("module_chip_brown");
	public static final Item MODULE_CHIP_GREEN     = new ItemItem("module_chip_green");
	public static final Item MODULE_CHIP_RED       = new ItemItem("module_chip_red");
	public static final Item MODULE_CHIP_BLACK     = new ItemItem("module_chip_black");

	// Textures
	public static final ResourceLocation TEXTURE_STATIC            = new ResourceLocation(modid, "textures/gui/static.png");
	public static final ResourceLocation TEXTURE_GROUND_BLACK      = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_black.png");
	public static final ResourceLocation TEXTURE_GROUND_BLUE       = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_blue.png");
	public static final ResourceLocation TEXTURE_GROUND_BROWN      = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_brown.png");
	public static final ResourceLocation TEXTURE_GROUND_CYAN       = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_cyan.png");
	public static final ResourceLocation TEXTURE_GROUND_GRAY       = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_gray.png");
	public static final ResourceLocation TEXTURE_GROUND_GREEN      = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_green.png");
	public static final ResourceLocation TEXTURE_GROUND_LIGHTBLUE  = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_lightblue.png");
	public static final ResourceLocation TEXTURE_GROUND_LIME       = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_lime.png");
	public static final ResourceLocation TEXTURE_GROUND_MAGENTA    = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_magenta.png");
	public static final ResourceLocation TEXTURE_GROUND_ORANGE     = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_orange.png");
	public static final ResourceLocation TEXTURE_GROUND_PINK       = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_pink.png");
	public static final ResourceLocation TEXTURE_GROUND_PURPLE     = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_purple.png");
	public static final ResourceLocation TEXTURE_GROUND_RED        = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_red.png");
	public static final ResourceLocation TEXTURE_GROUND_SILVER     = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_silver.png");
	public static final ResourceLocation TEXTURE_GROUND_WHITE      = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_white.png");
	public static final ResourceLocation TEXTURE_GROUND_YELLOW     = new ResourceLocation(modid, "textures/gui/cardtable/cardtable_yellow.png");
	public static final ResourceLocation TEXTURE_GROUND_ARCADE     = new ResourceLocation(modid, "textures/gui/arcade/arcade.png");
	public static final ResourceLocation TEXTURE_GROUND_STARFIELD0 = new ResourceLocation(modid, "textures/gui/arcade/starfield0.png");
	public static final ResourceLocation TEXTURE_GROUND_STARFIELD1 = new ResourceLocation(modid, "textures/gui/arcade/starfield1.png");
	public static final ResourceLocation TEXTURE_ARCADEDUMMY       = new ResourceLocation(modid, "textures/gui/background/arcadedummy.png");
	public static final ResourceLocation TEXTURE_TETRIS            = new ResourceLocation(modid, "textures/gui/background/tetris.png");
	public static final ResourceLocation TEXTURE_COLUMNS           = new ResourceLocation(modid, "textures/gui/background/columns.png");
	public static final ResourceLocation TEXTURE_MEANMINOS         = new ResourceLocation(modid, "textures/gui/background/meanminos.png");
	public static final ResourceLocation TEXTURE_SLOTMACHINE       = new ResourceLocation(modid, "textures/gui/background/slotmachine.png");
	public static final ResourceLocation TEXTURE_ROULETTE_LEFT     = new ResourceLocation(modid, "textures/gui/background/roulette_left.png");
	public static final ResourceLocation TEXTURE_ROULETTE_RIGHT    = new ResourceLocation(modid, "textures/gui/background/roulette_right.png");
	public static final ResourceLocation TEXTURE_ROULETTE_MIDDLE   = new ResourceLocation(modid, "textures/gui/background/roulette_middle.png");
	public static final ResourceLocation TEXTURE_SICBO_LEFT        = new ResourceLocation(modid, "textures/gui/background/sicbo_left.png");
	public static final ResourceLocation TEXTURE_SICBO_RIGHT       = new ResourceLocation(modid, "textures/gui/background/sicbo_right.png");
	public static final ResourceLocation TEXTURE_SICBO_MIDDLE      = new ResourceLocation(modid, "textures/gui/background/sicbo_middle.png");
	public static final ResourceLocation TEXTURE_CRAPS_LEFT        = new ResourceLocation(modid, "textures/gui/background/craps_left.png");
	public static final ResourceLocation TEXTURE_CRAPS_RIGHT       = new ResourceLocation(modid, "textures/gui/background/craps_right.png");
	public static final ResourceLocation TEXTURE_CRAPS_MIDDLE      = new ResourceLocation(modid, "textures/gui/background/craps_middle.png");
	public static final ResourceLocation TEXTURE_FANTAN            = new ResourceLocation(modid, "textures/gui/background/fantan.png");
	public static final ResourceLocation TEXTURE_SUDOKU            = new ResourceLocation(modid, "textures/gui/background/sudoku.png");
	public static final ResourceLocation TEXTURE_SIMON             = new ResourceLocation(modid, "textures/gui/spritesheet/simon.png");
	public static final ResourceLocation TEXTURE_DICE              = new ResourceLocation(modid, "textures/gui/spritesheet/dice.png");
	public static final ResourceLocation TEXTURE_ARCADE            = new ResourceLocation(modid, "textures/gui/spritesheet/arcade.png");
	public static final ResourceLocation TEXTURE_BUTTONS           = new ResourceLocation(modid, "textures/gui/spritesheet/buttons.png");
	public static final ResourceLocation TEXTURE_MINOS             = new ResourceLocation(modid, "textures/gui/spritesheet/minos.png");
	public static final ResourceLocation TEXTURE_SLOTGAME          = new ResourceLocation(modid, "textures/gui/spritesheet/slotgame.png");
	public static final ResourceLocation TEXTURE_ROUGE             = new ResourceLocation(modid, "textures/gui/spritesheet/cards_rouge.png");
	public static final ResourceLocation TEXTURE_NOIR              = new ResourceLocation(modid, "textures/gui/spritesheet/cards_noir.png");
	public static final ResourceLocation TEXTURE_MYSTICSQUARE      = new ResourceLocation(modid, "textures/gui/spritesheet/mysticsquare.png");
	public static final ResourceLocation TEXTURE_2048              = new ResourceLocation(modid, "textures/gui/spritesheet/2048.png");
	public static final ResourceLocation TEXTURE_FONT_ARCADE       = new ResourceLocation(modid, "textures/gui/spritesheet/font_arcade.png");
	public static final ResourceLocation TEXTURE_FONT_CARDTABLE    = new ResourceLocation(modid, "textures/gui/spritesheet/font_cardtable.png");
	public static final ResourceLocation TEXTURE_ROULETTE_WHEEL    = new ResourceLocation(modid, "textures/gui/spritesheet/roulette_wheel.png");
	
	public static boolean config_allowed_creative;
	public static boolean config_fastload;
	public static boolean config_animated_cards;
	public static int     config_timeout;
	
	/**Reads out Config File**/
	public static void loadConfig(FMLPreInitializationEvent preEvent){
		Configuration config = new Configuration(preEvent.getSuggestedConfigurationFile());
		config.load();
		config_allowed_creative = config.get("Stuff", "Activate Creative Toggle on Card Tables", false).getBoolean();
		config_fastload         = config.get("Stuff", "Transfer Tokens instantly",               false).getBoolean();
		config_animated_cards   = config.get("Stuff", "Animates Face Cards",                      true).getBoolean();
		config_timeout          = config.get("Stuff", "Timeout for Inactive Players",              300).getInt();
		System.out.println("Config loaded");
		config.save();
	}
	
	/**Register all stuff, pre is true during preInit and false during Init**/
	public static void registerStuff(boolean pre){
		
		// Card Tables
		Register.registerBlock(CARDTABLE_BASE_WHITE    , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_ORANGE   , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_MAGENTA  , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_LIGHTBLUE, pre, modid);
		Register.registerBlock(CARDTABLE_BASE_YELLOW   , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_LIME     , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_PINK     , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_GRAY     , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_SILVER   , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_CYAN     , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_PURPLE   , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_BLUE     , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_BROWN    , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_GREEN    , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_RED      , pre, modid);
		Register.registerBlock(CARDTABLE_BASE_BLACK    , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_WHITE    , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_ORANGE   , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_MAGENTA  , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_LIGHTBLUE, pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_YELLOW   , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_LIME     , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_PINK     , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_GRAY     , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_SILVER   , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_CYAN     , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_PURPLE   , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_BLUE     , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_BROWN    , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_GREEN    , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_RED      , pre, modid);
		Register.registerBlock(CARDTABLE_WIDE_BLACK    , pre, modid);
		
		// Arcdades
		Register.registerBlock(ARCADE_BASE_WHITE    , pre, modid);
		Register.registerBlock(ARCADE_BASE_ORANGE   , pre, modid);
		Register.registerBlock(ARCADE_BASE_MAGENTA  , pre, modid);
		Register.registerBlock(ARCADE_BASE_LIGHTBLUE, pre, modid);
		Register.registerBlock(ARCADE_BASE_YELLOW   , pre, modid);
		Register.registerBlock(ARCADE_BASE_LIME     , pre, modid);
		Register.registerBlock(ARCADE_BASE_PINK     , pre, modid);
		Register.registerBlock(ARCADE_BASE_GRAY     , pre, modid);
		Register.registerBlock(ARCADE_BASE_SILVER   , pre, modid);
		Register.registerBlock(ARCADE_BASE_CYAN     , pre, modid);
		Register.registerBlock(ARCADE_BASE_PURPLE   , pre, modid);
		Register.registerBlock(ARCADE_BASE_BLUE     , pre, modid);
		Register.registerBlock(ARCADE_BASE_BROWN    , pre, modid);
		Register.registerBlock(ARCADE_BASE_GREEN    , pre, modid);
		Register.registerBlock(ARCADE_BASE_RED      , pre, modid);
		Register.registerBlock(ARCADE_BASE_BLACK    , pre, modid);
		Register.registerBlock(ARCADE_SLOT_WHITE    , pre, modid);
		Register.registerBlock(ARCADE_SLOT_ORANGE   , pre, modid);
		Register.registerBlock(ARCADE_SLOT_MAGENTA  , pre, modid);
		Register.registerBlock(ARCADE_SLOT_LIGHTBLUE, pre, modid);
		Register.registerBlock(ARCADE_SLOT_YELLOW   , pre, modid);
		Register.registerBlock(ARCADE_SLOT_LIME     , pre, modid);
		Register.registerBlock(ARCADE_SLOT_PINK     , pre, modid);
		Register.registerBlock(ARCADE_SLOT_GRAY     , pre, modid);
		Register.registerBlock(ARCADE_SLOT_SILVER   , pre, modid);
		Register.registerBlock(ARCADE_SLOT_CYAN     , pre, modid);
		Register.registerBlock(ARCADE_SLOT_PURPLE   , pre, modid);
		Register.registerBlock(ARCADE_SLOT_BLUE     , pre, modid);
		Register.registerBlock(ARCADE_SLOT_BROWN    , pre, modid);
		Register.registerBlock(ARCADE_SLOT_GREEN    , pre, modid);
		Register.registerBlock(ARCADE_SLOT_RED      , pre, modid);
		Register.registerBlock(ARCADE_SLOT_BLACK    , pre, modid);
		
		// Dice
		Register.registerBlock(DICE_BASIC_WHITE    , pre, modid);
		Register.registerBlock(DICE_BASIC_ORANGE   , pre, modid);
		Register.registerBlock(DICE_BASIC_MAGENTA  , pre, modid);
		Register.registerBlock(DICE_BASIC_LIGHTBLUE, pre, modid);
		Register.registerBlock(DICE_BASIC_YELLOW   , pre, modid);
		Register.registerBlock(DICE_BASIC_LIME     , pre, modid);
		Register.registerBlock(DICE_BASIC_PINK     , pre, modid);
		Register.registerBlock(DICE_BASIC_GRAY     , pre, modid);
		Register.registerBlock(DICE_BASIC_SILVER   , pre, modid);
		Register.registerBlock(DICE_BASIC_CYAN     , pre, modid);
		Register.registerBlock(DICE_BASIC_PURPLE   , pre, modid);
		Register.registerBlock(DICE_BASIC_BLUE     , pre, modid);
		Register.registerBlock(DICE_BASIC_BROWN    , pre, modid);
		Register.registerBlock(DICE_BASIC_GREEN    , pre, modid);
		Register.registerBlock(DICE_BASIC_RED      , pre, modid);
		Register.registerBlock(DICE_BASIC_BLACK    , pre, modid);
		
		// Modules
		Register.registerItem(MODULE_MINO_WHITE    , pre, modid);
		Register.registerItem(MODULE_MINO_ORANGE   , pre, modid);
		Register.registerItem(MODULE_MINO_MAGENTA  , pre, modid);
		Register.registerItem(MODULE_MINO_LIGHTBLUE, pre, modid);
		Register.registerItem(MODULE_MINO_YELLOW   , pre, modid);
		Register.registerItem(MODULE_MINO_LIME     , pre, modid);
		Register.registerItem(MODULE_MINO_PINK     , pre, modid);
		Register.registerItem(MODULE_MINO_GRAY     , pre, modid);
		Register.registerItem(MODULE_MINO_SILVER   , pre, modid);
		Register.registerItem(MODULE_MINO_CYAN     , pre, modid);
		Register.registerItem(MODULE_MINO_PURPLE   , pre, modid);
		Register.registerItem(MODULE_MINO_BLUE     , pre, modid);
		Register.registerItem(MODULE_MINO_BROWN    , pre, modid);
		Register.registerItem(MODULE_MINO_GREEN    , pre, modid);
		Register.registerItem(MODULE_MINO_RED      , pre, modid);
		Register.registerItem(MODULE_MINO_BLACK    , pre, modid);
		Register.registerItem(MODULE_CARD_WHITE    , pre, modid);
		Register.registerItem(MODULE_CARD_ORANGE   , pre, modid);
		Register.registerItem(MODULE_CARD_MAGENTA  , pre, modid);
		Register.registerItem(MODULE_CARD_LIGHTBLUE, pre, modid);
		Register.registerItem(MODULE_CARD_YELLOW   , pre, modid);
		Register.registerItem(MODULE_CARD_LIME     , pre, modid);
		Register.registerItem(MODULE_CARD_PINK     , pre, modid);
		Register.registerItem(MODULE_CARD_GRAY     , pre, modid);
		Register.registerItem(MODULE_CARD_SILVER   , pre, modid);
		Register.registerItem(MODULE_CARD_CYAN     , pre, modid);
		Register.registerItem(MODULE_CARD_PURPLE   , pre, modid);
		Register.registerItem(MODULE_CARD_BLUE     , pre, modid);
		Register.registerItem(MODULE_CARD_BROWN    , pre, modid);
		Register.registerItem(MODULE_CARD_GREEN    , pre, modid);
		Register.registerItem(MODULE_CARD_RED      , pre, modid);
		Register.registerItem(MODULE_CARD_BLACK    , pre, modid);
		Register.registerItem(MODULE_CHIP_WHITE    , pre, modid);
		Register.registerItem(MODULE_CHIP_ORANGE   , pre, modid);
		Register.registerItem(MODULE_CHIP_MAGENTA  , pre, modid);
		Register.registerItem(MODULE_CHIP_LIGHTBLUE, pre, modid);
		Register.registerItem(MODULE_CHIP_YELLOW   , pre, modid);
		Register.registerItem(MODULE_CHIP_LIME     , pre, modid);
		Register.registerItem(MODULE_CHIP_PINK     , pre, modid);
		Register.registerItem(MODULE_CHIP_GRAY     , pre, modid);
		Register.registerItem(MODULE_CHIP_SILVER   , pre, modid);
		Register.registerItem(MODULE_CHIP_CYAN     , pre, modid);
		Register.registerItem(MODULE_CHIP_PURPLE   , pre, modid);
		Register.registerItem(MODULE_CHIP_BLUE     , pre, modid);
		Register.registerItem(MODULE_CHIP_BROWN    , pre, modid);
		Register.registerItem(MODULE_CHIP_GREEN    , pre, modid);
		Register.registerItem(MODULE_CHIP_RED      , pre, modid);
		Register.registerItem(MODULE_CHIP_BLACK    , pre, modid);
	}
	
	/**Register Living Entities**/
	public static void registerEntities(){
		GameRegistry.registerTileEntity(TileEntityArcade.class,        new ResourceLocation(modid, "casino_arcade_base"));
		GameRegistry.registerTileEntity(TileEntitySlotMachine.class,   new ResourceLocation(modid, "casino_arcade_slot"));
		GameRegistry.registerTileEntity(TileEntityCardTableBase.class, new ResourceLocation(modid, "casino_cardtable_base"));
		GameRegistry.registerTileEntity(TileEntityCardTableWide.class, new ResourceLocation(modid, "casino_cardtable_wide"));
	}
	
}
