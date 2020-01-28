package mod.casinocraft;

import mod.shared.Register;
import mod.shared.items.ItemItem;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.blocks.BlockCardTableBase;
import mod.casinocraft.blocks.BlockCardTableWide;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCardTable;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.tileentities.minigames.TileEntity2048;
import mod.casinocraft.tileentities.minigames.TileEntityAceyDeucey;
import mod.casinocraft.tileentities.minigames.TileEntityBaccarat;
import mod.casinocraft.tileentities.minigames.TileEntityBlackJack;
import mod.casinocraft.tileentities.minigames.TileEntityColumns;
import mod.casinocraft.tileentities.minigames.TileEntityCraps;
import mod.casinocraft.tileentities.minigames.TileEntityFreeCell;
import mod.casinocraft.tileentities.minigames.TileEntityHalma;
import mod.casinocraft.tileentities.minigames.TileEntityKlondike;
import mod.casinocraft.tileentities.minigames.TileEntityMeanMinos;
import mod.casinocraft.tileentities.minigames.TileEntityMemory;
import mod.casinocraft.tileentities.minigames.TileEntityMinesweeper;
import mod.casinocraft.tileentities.minigames.TileEntityMysticSquare;
import mod.casinocraft.tileentities.minigames.TileEntityPyramid;
import mod.casinocraft.tileentities.minigames.TileEntityRougeEtNoir;
import mod.casinocraft.tileentities.minigames.TileEntityRoulette;
import mod.casinocraft.tileentities.minigames.TileEntitySicBo;
import mod.casinocraft.tileentities.minigames.TileEntitySlotMachine;
import mod.casinocraft.tileentities.minigames.TileEntitySnake;
import mod.casinocraft.tileentities.minigames.TileEntitySokoban;
import mod.casinocraft.tileentities.minigames.TileEntitySpider;
import mod.casinocraft.tileentities.minigames.TileEntitySudoku;
import mod.casinocraft.tileentities.minigames.TileEntityTetris;
import mod.casinocraft.tileentities.minigames.TileEntityTriPeak;
import mod.casinocraft.tileentities.minigames.TileEntityVideoPoker;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CasinoKeeper {
	
	// Tables
	public static final Block CARDTABLE_BASE_WHITE     = new BlockCardTableBase("cardtable_base_white");
	public static final Block CARDTABLE_BASE_ORANGE    = new BlockCardTableBase("cardtable_base_orange");
	public static final Block CARDTABLE_BASE_MAGENTA   = new BlockCardTableBase("cardtable_base_magenta");
	public static final Block CARDTABLE_BASE_LIGHTBLUE = new BlockCardTableBase("cardtable_base_lightblue");
	public static final Block CARDTABLE_BASE_YELLOW    = new BlockCardTableBase("cardtable_base_yellow");
	public static final Block CARDTABLE_BASE_LIME      = new BlockCardTableBase("cardtable_base_lime");
	public static final Block CARDTABLE_BASE_PINK      = new BlockCardTableBase("cardtable_base_pink");
	public static final Block CARDTABLE_BASE_GRAY      = new BlockCardTableBase("cardtable_base_gray");
	public static final Block CARDTABLE_BASE_SILVER    = new BlockCardTableBase("cardtable_base_silver");
	public static final Block CARDTABLE_BASE_CYAN      = new BlockCardTableBase("cardtable_base_cyan");
	public static final Block CARDTABLE_BASE_PURPLE    = new BlockCardTableBase("cardtable_base_purple");
	public static final Block CARDTABLE_BASE_BLUE      = new BlockCardTableBase("cardtable_base_blue");
	public static final Block CARDTABLE_BASE_BROWN     = new BlockCardTableBase("cardtable_base_brown");
	public static final Block CARDTABLE_BASE_GREEN     = new BlockCardTableBase("cardtable_base_green");
	public static final Block CARDTABLE_BASE_RED       = new BlockCardTableBase("cardtable_base_red");
	public static final Block CARDTABLE_BASE_BLACK     = new BlockCardTableBase("cardtable_base_black");
	public static final Block CARDTABLE_WIDE_WHITE     = new BlockCardTableWide("cardtable_wide_white");
	public static final Block CARDTABLE_WIDE_ORANGE    = new BlockCardTableWide("cardtable_wide_orange");
	public static final Block CARDTABLE_WIDE_MAGENTA   = new BlockCardTableWide("cardtable_wide_magenta");
	public static final Block CARDTABLE_WIDE_LIGHTBLUE = new BlockCardTableWide("cardtable_wide_lightblue");
	public static final Block CARDTABLE_WIDE_YELLOW    = new BlockCardTableWide("cardtable_wide_yellow");
	public static final Block CARDTABLE_WIDE_LIME      = new BlockCardTableWide("cardtable_wide_lime");
	public static final Block CARDTABLE_WIDE_PINK      = new BlockCardTableWide("cardtable_wide_pink");
	public static final Block CARDTABLE_WIDE_GRAY      = new BlockCardTableWide("cardtable_wide_gray");
	public static final Block CARDTABLE_WIDE_SILVER    = new BlockCardTableWide("cardtable_wide_silver");
	public static final Block CARDTABLE_WIDE_CYAN      = new BlockCardTableWide("cardtable_wide_cyan");
	public static final Block CARDTABLE_WIDE_PURPLE    = new BlockCardTableWide("cardtable_wide_purple");
	public static final Block CARDTABLE_WIDE_BLUE      = new BlockCardTableWide("cardtable_wide_blue");
	public static final Block CARDTABLE_WIDE_BROWN     = new BlockCardTableWide("cardtable_wide_brown");
	public static final Block CARDTABLE_WIDE_GREEN     = new BlockCardTableWide("cardtable_wide_green");
	public static final Block CARDTABLE_WIDE_RED       = new BlockCardTableWide("cardtable_wide_red");
	public static final Block CARDTABLE_WIDE_BLACK     = new BlockCardTableWide("cardtable_wide_black");
	
	// Arcades
	public static final Block ARCADE_BASE_WHITE     = new BlockArcade("arcade_base_white");
	public static final Block ARCADE_BASE_ORANGE    = new BlockArcade("arcade_base_orange");
	public static final Block ARCADE_BASE_MAGENTA   = new BlockArcade("arcade_base_magenta");
	public static final Block ARCADE_BASE_LIGHTBLUE = new BlockArcade("arcade_base_lightblue");
	public static final Block ARCADE_BASE_YELLOW    = new BlockArcade("arcade_base_yellow");
	public static final Block ARCADE_BASE_LIME      = new BlockArcade("arcade_base_lime");
	public static final Block ARCADE_BASE_PINK      = new BlockArcade("arcade_base_pink");
	public static final Block ARCADE_BASE_GRAY      = new BlockArcade("arcade_base_gray");
	public static final Block ARCADE_BASE_SILVER    = new BlockArcade("arcade_base_silver");
	public static final Block ARCADE_BASE_CYAN      = new BlockArcade("arcade_base_cyan");
	public static final Block ARCADE_BASE_PURPLE    = new BlockArcade("arcade_base_purple");
	public static final Block ARCADE_BASE_BLUE      = new BlockArcade("arcade_base_blue");
	public static final Block ARCADE_BASE_BROWN     = new BlockArcade("arcade_base_brown");
	public static final Block ARCADE_BASE_GREEN     = new BlockArcade("arcade_base_green");
	public static final Block ARCADE_BASE_RED       = new BlockArcade("arcade_base_red");
	public static final Block ARCADE_BASE_BLACK     = new BlockArcade("arcade_base_black");
	
	// Casino Games
	public static final Item MODULE_BLACKJACK    = new ItemItem("module_blackjack");
	public static final Item MODULE_BACCARAT     = new ItemItem("module_baccarat");
	public static final Item MODULE_VIDEOPOKER   = new ItemItem("module_videopoker");
	public static final Item MODULE_ACEYDEUCEY   = new ItemItem("module_aceydeucey");
	public static final Item MODULE_ROUGEETNOIR  = new ItemItem("module_rougeetnoir");
	public static final Item MODULE_CRAPS        = new ItemItem("module_craps");
	public static final Item MODULE_SICBO        = new ItemItem("module_sicbo");
	public static final Item MODULE_ROULETTE     = new ItemItem("module_roulette");
	
	// Card Games
	public static final Item MODULE_PYRAMID      = new ItemItem("module_pyramid");
	public static final Item MODULE_TRIPEAK      = new ItemItem("module_tripeak");
	public static final Item MODULE_FREECELL     = new ItemItem("module_freecell");
	public static final Item MODULE_KLONDIKE     = new ItemItem("module_klondike");
	public static final Item MODULE_SPIDER       = new ItemItem("module_spider");
	
	// Normal Games
	public static final Item MODULE_MEMORY       = new ItemItem("module_memory");
	public static final Item MODULE_MYSTICSQUARE = new ItemItem("module_mysticsquare");
	public static final Item MODULE_SUDOKU       = new ItemItem("module_sudoku");
	public static final Item MODULE_HALMA        = new ItemItem("module_halma");
	public static final Item MODULE_MINESWEEPER  = new ItemItem("module_minesweeper");
	
	// Arcade Games
	public static final Item MODULE_TETRIS       = new ItemItem("module_tetris");
	public static final Item MODULE_COLUMNS      = new ItemItem("module_columns");
	public static final Item MODULE_MEANMINOS    = new ItemItem("module_meanminos");
	public static final Item MODULE_SNAKE        = new ItemItem("module_snake");
	public static final Item MODULE_SOKOBAN      = new ItemItem("module_sokoban");
	public static final Item MODULE_2048         = new ItemItem("module_2048");
	
	
	
	
	
	public enum GuiID{CARDTABLE, ARCADE, BLACKJACK, BACCARAT, VIDEOPOKER, MEMORY, TETRIS, SLOTMACHINE, ROULETTE, COLUMNS, MEANMINOS, MYSTICSQUARE, PYRAMID, SOKOBAN, SUDOKU, TRIPEAK, _2048, ACEYDEUCEY, HALMA, ROUGEETNOIR, SNAKE, CRAPS, SICBO, KLONDIKE, FREECELL, SPIDER, MINESWEEPER}
	
	public static ResourceLocation TEXTURE_GROUND_BLACK      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_black.png");
	public static ResourceLocation TEXTURE_GROUND_BLUE       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_blue.png");
	public static ResourceLocation TEXTURE_GROUND_BROWN      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_brown.png");
	public static ResourceLocation TEXTURE_GROUND_CYAN       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_cyan.png");
	public static ResourceLocation TEXTURE_GROUND_GRAY       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_gray.png");
	public static ResourceLocation TEXTURE_GROUND_GREEN      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_green.png");
	public static ResourceLocation TEXTURE_GROUND_LIGHTBLUE  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_lightblue.png");
	public static ResourceLocation TEXTURE_GROUND_LIME       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_lime.png");
	public static ResourceLocation TEXTURE_GROUND_MAGENTA    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_magenta.png");
	public static ResourceLocation TEXTURE_GROUND_ORANGE     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_orange.png");
	public static ResourceLocation TEXTURE_GROUND_PINK       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_pink.png");
	public static ResourceLocation TEXTURE_GROUND_PURPLE     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_purple.png");
	public static ResourceLocation TEXTURE_GROUND_RED        = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_red.png");
	public static ResourceLocation TEXTURE_GROUND_SILVER     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_silver.png");
	public static ResourceLocation TEXTURE_GROUND_WHITE      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_white.png");
	public static ResourceLocation TEXTURE_GROUND_YELLOW     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cardtable_yellow.png");
	public static ResourceLocation TEXTURE_GROUND_ARCADE     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/arcade.png");
	public static ResourceLocation TEXTURE_GROUND_STARFIELD0 = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/starfield0.png");
	public static ResourceLocation TEXTURE_GROUND_STARFIELD1 = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/starfield1.png");
	public static ResourceLocation TEXTURE_ARCADEDUMMY       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/arcadedummy.png");
	public static ResourceLocation TEXTURE_TETRIS            = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/tetris.png");
	public static ResourceLocation TEXTURE_COLUMNS           = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/columns.png");
	public static ResourceLocation TEXTURE_MEANMINOS         = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/meanminos.png");
	public static ResourceLocation TEXTURE_OCTAGAMES         = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/octagames.png");
	public static ResourceLocation TEXTURE_CASINO            = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/casino.png");
	public static ResourceLocation TEXTURE_SLOTMACHINE       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/slotmachine.png");
	public static ResourceLocation TEXTURE_SLOTWHEEL         = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/slotwheel.png");
	public static ResourceLocation TEXTURE_ROULETTE_LEFT     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/roulette_left.png");
	public static ResourceLocation TEXTURE_ROULETTE_RIGHT    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/roulette_right.png");
	public static ResourceLocation TEXTURE_ROULETTE_WHEEL    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/roulette_wheel.png");
	public static ResourceLocation TEXTURE_SICBO_LEFT        = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/sicbo_left.png");
	public static ResourceLocation TEXTURE_SICBO_RIGHT       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/sicbo_right.png");
	public static ResourceLocation TEXTURE_CRAPS_LEFT        = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/craps_left.png");
	public static ResourceLocation TEXTURE_CRAPS_RIGHT       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/craps_right.png");
	public static ResourceLocation TEXTURE_ROUGE             = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cards_rouge.png");
	public static ResourceLocation TEXTURE_NOIR              = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/cards_noir.png");
	public static ResourceLocation TEXTURE_MYSTICSQUARE      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/mysticsquare.png");
	public static ResourceLocation TEXTURE_2048              = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/2048.png");
	public static ResourceLocation TEXTURE_SUDOKU            = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/sudoku.png");
	public static ResourceLocation TEXTURE_FONT_ARCADE       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/font_arcade.png");
	public static ResourceLocation TEXTURE_FONT_CARDTABLE    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/font_cardtable.png");
	
	public static boolean config_allowed_creative;
	public static boolean config_fastload;
	
	/**Reads out Config File**/
	public static void loadConfig(FMLPreInitializationEvent preEvent){
		Configuration config = new Configuration(preEvent.getSuggestedConfigurationFile());
		config.load();
		config_allowed_creative = config.get("Stuff", "Activate Creative Toggle on Card Tables", false).getBoolean();
		config_fastload         = config.get("Stuff", "Transfer Tokens instantly",               false).getBoolean();
		System.out.println("Config loaded");
		config.save();
	}
	
	/**Initialize Items/Blocks that need Reference to other Items/Blocks**/
	public static void init(){
		
	}
	
	/**Register all stuff, pre is true during preInit and false during Init**/
	public static void registerStuff(boolean pre){
		
		// Card Tables
		Register.registerBlock(CARDTABLE_BASE_WHITE    , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_ORANGE   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_MAGENTA  , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_LIGHTBLUE, pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_YELLOW   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_LIME     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_PINK     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_GRAY     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_SILVER   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_CYAN     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_PURPLE   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_BLUE     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_BROWN    , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_GREEN    , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_RED      , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_BASE_BLACK    , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_WHITE    , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_ORANGE   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_MAGENTA  , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_LIGHTBLUE, pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_YELLOW   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_LIME     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_PINK     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_GRAY     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_SILVER   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_CYAN     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_PURPLE   , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_BLUE     , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_BROWN    , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_GREEN    , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_RED      , pre, CasinoCraft.modid);
		Register.registerBlock(CARDTABLE_WIDE_BLACK    , pre, CasinoCraft.modid);
		
		// Arcdades
		Register.registerBlock(ARCADE_BASE_WHITE    , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_ORANGE   , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_MAGENTA  , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_LIGHTBLUE, pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_YELLOW   , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_LIME     , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_PINK     , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_GRAY     , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_SILVER   , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_CYAN     , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_PURPLE   , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_BLUE     , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_BROWN    , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_GREEN    , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_RED      , pre, CasinoCraft.modid);
		Register.registerBlock(ARCADE_BASE_BLACK    , pre, CasinoCraft.modid);
		
		// Casino Games
		Register.registerItem(MODULE_BLACKJACK   , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_BACCARAT    , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_VIDEOPOKER  , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_ACEYDEUCEY  , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_ROUGEETNOIR , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_CRAPS       , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_SICBO       , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_ROULETTE    , pre, CasinoCraft.modid);
		
		// Card Games
		Register.registerItem(MODULE_PYRAMID , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_TRIPEAK , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_FREECELL, pre, CasinoCraft.modid);
		Register.registerItem(MODULE_KLONDIKE, pre, CasinoCraft.modid);
		Register.registerItem(MODULE_SPIDER  , pre, CasinoCraft.modid);
		
		// Normal Games
		Register.registerItem(MODULE_MEMORY      , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_MYSTICSQUARE, pre, CasinoCraft.modid);
		Register.registerItem(MODULE_SUDOKU      , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_HALMA       , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_MINESWEEPER , pre, CasinoCraft.modid);
		
		// Arcade Games
		Register.registerItem(MODULE_TETRIS     , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_COLUMNS    , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_MEANMINOS  , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_SNAKE      , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_SOKOBAN    , pre, CasinoCraft.modid);
		Register.registerItem(MODULE_2048       , pre, CasinoCraft.modid);
	}
	
	/**Register Sounds**/
	public static void registerSounds(){
		
	}
	
	/**Register Crafting and Furnace Recipes**/
	public static void registerRecipes(){
		
	}
	
	/**Register Living Entities**/
	public static void registerEntities(){
		GameRegistry.registerTileEntity(TileEntityArcade      .class, new ResourceLocation(CasinoCraft.modid, "casino_arcade"));
		GameRegistry.registerTileEntity(TileEntityCardTable   .class, new ResourceLocation(CasinoCraft.modid, "casino_cardtable"));
		GameRegistry.registerTileEntity(TileEntityBoard       .class, new ResourceLocation(CasinoCraft.modid, "casino_board"));
		GameRegistry.registerTileEntity(TileEntityCasino      .class, new ResourceLocation(CasinoCraft.modid, "casino_casino"));
		
		// Casino Games
		GameRegistry.registerTileEntity(TileEntityBlackJack  .class, new ResourceLocation(CasinoCraft.modid, "casino_blackjack"));
		GameRegistry.registerTileEntity(TileEntityBaccarat   .class, new ResourceLocation(CasinoCraft.modid, "casino_baccarat"));
		GameRegistry.registerTileEntity(TileEntityAceyDeucey .class, new ResourceLocation(CasinoCraft.modid, "casino_aceydeucey"));
		GameRegistry.registerTileEntity(TileEntityRougeEtNoir.class, new ResourceLocation(CasinoCraft.modid, "casino_rougeetnoir"));
		GameRegistry.registerTileEntity(TileEntityCraps      .class, new ResourceLocation(CasinoCraft.modid, "casino_craps"));
		GameRegistry.registerTileEntity(TileEntitySicBo      .class, new ResourceLocation(CasinoCraft.modid, "casino_sicbo"));
		GameRegistry.registerTileEntity(TileEntityRoulette   .class, new ResourceLocation(CasinoCraft.modid, "casino_roulette"));
		GameRegistry.registerTileEntity(TileEntitySlotMachine.class, new ResourceLocation(CasinoCraft.modid, "casino_slotmachine"));
		GameRegistry.registerTileEntity(TileEntityVideoPoker .class, new ResourceLocation(CasinoCraft.modid, "casino_videopoker"));
		
		// Card Games
		GameRegistry.registerTileEntity(TileEntityPyramid .class, new ResourceLocation(CasinoCraft.modid, "casino_pyramid"));
		GameRegistry.registerTileEntity(TileEntityTriPeak .class, new ResourceLocation(CasinoCraft.modid, "casino_tripeak"));
		GameRegistry.registerTileEntity(TileEntityFreeCell.class, new ResourceLocation(CasinoCraft.modid, "casino_freecell"));
		GameRegistry.registerTileEntity(TileEntityKlondike.class, new ResourceLocation(CasinoCraft.modid, "casino_klondike"));
		GameRegistry.registerTileEntity(TileEntitySpider  .class, new ResourceLocation(CasinoCraft.modid, "casino_spider"));
		
		// Arcade Games
		GameRegistry.registerTileEntity(TileEntityTetris     .class, new ResourceLocation(CasinoCraft.modid, "casino_tetris"));
		GameRegistry.registerTileEntity(TileEntityColumns    .class, new ResourceLocation(CasinoCraft.modid, "casino_columns"));
		GameRegistry.registerTileEntity(TileEntityMeanMinos  .class, new ResourceLocation(CasinoCraft.modid, "casino_meanminos"));
		GameRegistry.registerTileEntity(TileEntity2048       .class, new ResourceLocation(CasinoCraft.modid, "casino_2048"));
		GameRegistry.registerTileEntity(TileEntitySokoban    .class, new ResourceLocation(CasinoCraft.modid, "casino_sokoban"));
		GameRegistry.registerTileEntity(TileEntitySnake      .class, new ResourceLocation(CasinoCraft.modid, "casino_snake"));
		GameRegistry.registerTileEntity(TileEntityMinesweeper.class, new ResourceLocation(CasinoCraft.modid, "casino_minesweeper"));
		
		// Normal Games
		GameRegistry.registerTileEntity(TileEntityMemory      .class, new ResourceLocation(CasinoCraft.modid, "casino_memory"));
		GameRegistry.registerTileEntity(TileEntityMysticSquare.class, new ResourceLocation(CasinoCraft.modid, "casino_mysticsquare"));
		GameRegistry.registerTileEntity(TileEntitySudoku      .class, new ResourceLocation(CasinoCraft.modid, "casino_sudoku"));
		GameRegistry.registerTileEntity(TileEntityHalma       .class, new ResourceLocation(CasinoCraft.modid, "casino_halma"));
	}
	
	/**Registers all Renderer to their Living Entities**/
	public static void registerRenders(){
		
	}
	
}
