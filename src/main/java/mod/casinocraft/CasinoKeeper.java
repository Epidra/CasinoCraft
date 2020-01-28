package mod.casinocraft;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.blocks.BlockCardTableBase;
import mod.casinocraft.blocks.BlockCardTableWide;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityCardTable;
import mod.casinocraft.tileentities.minigames.*;
import mod.shared.Register;
import mod.shared.items.ItemItem;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;

import static mod.casinocraft.CasinoCraft.MODID;

public class CasinoKeeper {

    // Tables
    public static final Block CARDTABLE_BASE_WHITE     = new BlockCardTableBase(MODID, "cardtable_base_white", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_ORANGE    = new BlockCardTableBase(MODID, "cardtable_base_orange", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_MAGENTA   = new BlockCardTableBase(MODID, "cardtable_base_magenta", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_LIGHTBLUE = new BlockCardTableBase(MODID, "cardtable_base_lightblue", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_YELLOW    = new BlockCardTableBase(MODID, "cardtable_base_yellow", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_LIME      = new BlockCardTableBase(MODID, "cardtable_base_lime", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_PINK      = new BlockCardTableBase(MODID, "cardtable_base_pink", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_GRAY      = new BlockCardTableBase(MODID, "cardtable_base_gray", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_SILVER    = new BlockCardTableBase(MODID, "cardtable_base_silver", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_CYAN      = new BlockCardTableBase(MODID, "cardtable_base_cyan", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_PURPLE    = new BlockCardTableBase(MODID, "cardtable_base_purple", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_BLUE      = new BlockCardTableBase(MODID, "cardtable_base_blue", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_BROWN     = new BlockCardTableBase(MODID, "cardtable_base_brown", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_GREEN     = new BlockCardTableBase(MODID, "cardtable_base_green", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_RED       = new BlockCardTableBase(MODID, "cardtable_base_red", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_BASE_BLACK     = new BlockCardTableBase(MODID, "cardtable_base_black", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_WHITE     = new BlockCardTableWide(MODID, "cardtable_wide_white", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_ORANGE    = new BlockCardTableWide(MODID, "cardtable_wide_orange", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_MAGENTA   = new BlockCardTableWide(MODID, "cardtable_wide_magenta", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_LIGHTBLUE = new BlockCardTableWide(MODID, "cardtable_wide_lightblue", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_YELLOW    = new BlockCardTableWide(MODID, "cardtable_wide_yellow", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_LIME      = new BlockCardTableWide(MODID, "cardtable_wide_lime", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_PINK      = new BlockCardTableWide(MODID, "cardtable_wide_pink", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_GRAY      = new BlockCardTableWide(MODID, "cardtable_wide_gray", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_SILVER    = new BlockCardTableWide(MODID, "cardtable_wide_silver", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_CYAN      = new BlockCardTableWide(MODID, "cardtable_wide_cyan", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_PURPLE    = new BlockCardTableWide(MODID, "cardtable_wide_purple", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_BLUE      = new BlockCardTableWide(MODID, "cardtable_wide_blue", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_BROWN     = new BlockCardTableWide(MODID, "cardtable_wide_brown", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_GREEN     = new BlockCardTableWide(MODID, "cardtable_wide_green", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_RED       = new BlockCardTableWide(MODID, "cardtable_wide_red", Blocks.OAK_PLANKS);
    public static final Block CARDTABLE_WIDE_BLACK     = new BlockCardTableWide(MODID, "cardtable_wide_black", Blocks.OAK_PLANKS);

    // Arcades
    public static final Block ARCADE_BASE_WHITE     = new BlockArcade(MODID, "arcade_base_white", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_ORANGE    = new BlockArcade(MODID, "arcade_base_orange", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_MAGENTA   = new BlockArcade(MODID, "arcade_base_magenta", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_LIGHTBLUE = new BlockArcade(MODID, "arcade_base_lightblue", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_YELLOW    = new BlockArcade(MODID, "arcade_base_yellow", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_LIME      = new BlockArcade(MODID, "arcade_base_lime", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_PINK      = new BlockArcade(MODID, "arcade_base_pink", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_GRAY      = new BlockArcade(MODID, "arcade_base_gray", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_SILVER    = new BlockArcade(MODID, "arcade_base_silver", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_CYAN      = new BlockArcade(MODID, "arcade_base_cyan", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_PURPLE    = new BlockArcade(MODID, "arcade_base_purple", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_BLUE      = new BlockArcade(MODID, "arcade_base_blue", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_BROWN     = new BlockArcade(MODID, "arcade_base_brown", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_GREEN     = new BlockArcade(MODID, "arcade_base_green", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_RED       = new BlockArcade(MODID, "arcade_base_red", Blocks.IRON_BLOCK);
    public static final Block ARCADE_BASE_BLACK     = new BlockArcade(MODID, "arcade_base_black", Blocks.IRON_BLOCK);

    // Arcades
    //public static final Block ARCADE_SLOT_WHITE     = new BlockArcade(MODID, "arcade_slot_white", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_ORANGE    = new BlockArcade(MODID, "arcade_slot_orange", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_MAGENTA   = new BlockArcade(MODID, "arcade_slot_magenta", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_LIGHTBLUE = new BlockArcade(MODID, "arcade_slot_lightblue", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_YELLOW    = new BlockArcade(MODID, "arcade_slot_yellow", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_LIME      = new BlockArcade(MODID, "arcade_slot_lime", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_PINK      = new BlockArcade(MODID, "arcade_slot_pink", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_GRAY      = new BlockArcade(MODID, "arcade_slot_gray", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_SILVER    = new BlockArcade(MODID, "arcade_slot_silver", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_CYAN      = new BlockArcade(MODID, "arcade_slot_cyan", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_PURPLE    = new BlockArcade(MODID, "arcade_slot_purple", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_BLUE      = new BlockArcade(MODID, "arcade_slot_blue", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_BROWN     = new BlockArcade(MODID, "arcade_slot_brown", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_GREEN     = new BlockArcade(MODID, "arcade_slot_green", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_RED       = new BlockArcade(MODID, "arcade_slot_red", Blocks.IRON_BLOCK);
    //public static final Block ARCADE_SLOT_BLACK     = new BlockArcade(MODID, "arcade_slot_black", Blocks.IRON_BLOCK);

    // Casino Games
    public static final Item MODULE_BLACKJACK    = new ItemItem(MODID, "module_blackjack", ItemGroup.MISC);
    public static final Item MODULE_BACCARAT     = new ItemItem(MODID, "module_baccarat", ItemGroup.MISC);
    public static final Item MODULE_VIDEOPOKER   = new ItemItem(MODID, "module_videopoker", ItemGroup.MISC);
    public static final Item MODULE_ACEYDEUCEY   = new ItemItem(MODID, "module_aceydeucey", ItemGroup.MISC);
    public static final Item MODULE_ROUGEETNOIR  = new ItemItem(MODID, "module_rougeetnoir", ItemGroup.MISC);
    public static final Item MODULE_CRAPS        = new ItemItem(MODID, "module_craps", ItemGroup.MISC);
    public static final Item MODULE_SICBO        = new ItemItem(MODID, "module_sicbo", ItemGroup.MISC);
    public static final Item MODULE_ROULETTE     = new ItemItem(MODID, "module_roulette", ItemGroup.MISC);

    // Card Games
    public static final Item MODULE_PYRAMID      = new ItemItem(MODID, "module_pyramid", ItemGroup.MISC);
    public static final Item MODULE_TRIPEAK      = new ItemItem(MODID, "module_tripeak", ItemGroup.MISC);
    public static final Item MODULE_FREECELL     = new ItemItem(MODID, "module_freecell", ItemGroup.MISC);
    public static final Item MODULE_KLONDIKE     = new ItemItem(MODID, "module_klondike", ItemGroup.MISC);
    public static final Item MODULE_SPIDER       = new ItemItem(MODID, "module_spider", ItemGroup.MISC);

    // Normal Games
    public static final Item MODULE_MEMORY       = new ItemItem(MODID, "module_memory", ItemGroup.MISC);
    public static final Item MODULE_MYSTICSQUARE = new ItemItem(MODID, "module_mysticsquare", ItemGroup.MISC);
    public static final Item MODULE_SUDOKU       = new ItemItem(MODID, "module_sudoku", ItemGroup.MISC);
    public static final Item MODULE_HALMA        = new ItemItem(MODID, "module_halma", ItemGroup.MISC);
    public static final Item MODULE_MINESWEEPER  = new ItemItem(MODID, "module_minesweeper", ItemGroup.MISC);

    // Arcade Games
    public static final Item MODULE_TETRIS       = new ItemItem(MODID, "module_tetris", ItemGroup.MISC);
    public static final Item MODULE_COLUMNS      = new ItemItem(MODID, "module_columns", ItemGroup.MISC);
    public static final Item MODULE_MEANMINOS    = new ItemItem(MODID, "module_meanminos", ItemGroup.MISC);
    public static final Item MODULE_SNAKE        = new ItemItem(MODID, "module_snake", ItemGroup.MISC);
    public static final Item MODULE_SOKOBAN      = new ItemItem(MODID, "module_sokoban", ItemGroup.MISC);
    public static final Item MODULE_2048         = new ItemItem(MODID, "module_2048", ItemGroup.MISC);





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
        Register.registerBlock(CARDTABLE_BASE_WHITE    , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_ORANGE   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_MAGENTA  , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_LIGHTBLUE, ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_YELLOW   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_LIME     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_PINK     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_GRAY     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_SILVER   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_CYAN     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_PURPLE   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_BLUE     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_BROWN    , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_GREEN    , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_RED      , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_BASE_BLACK    , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_WHITE    , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_ORANGE   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_MAGENTA  , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_LIGHTBLUE, ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_YELLOW   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_LIME     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_PINK     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_GRAY     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_SILVER   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_CYAN     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_PURPLE   , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_BLUE     , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_BROWN    , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_GREEN    , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_RED      , ItemGroup.REDSTONE);
        Register.registerBlock(CARDTABLE_WIDE_BLACK    , ItemGroup.REDSTONE);

        // Arcdades
        Register.registerBlock(ARCADE_BASE_WHITE    , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_ORANGE   , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_MAGENTA  , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_LIGHTBLUE, ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_YELLOW   , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_LIME     , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_PINK     , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_GRAY     , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_SILVER   , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_CYAN     , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_PURPLE   , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_BLUE     , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_BROWN    , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_GREEN    , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_RED      , ItemGroup.REDSTONE);
        Register.registerBlock(ARCADE_BASE_BLACK    , ItemGroup.REDSTONE);

        // Slot Machines
        //Register.registerBlock(ARCADE_SLOT_WHITE    , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_ORANGE   , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_MAGENTA  , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_LIGHTBLUE, ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_YELLOW   , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_LIME     , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_PINK     , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_GRAY     , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_SILVER   , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_CYAN     , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_PURPLE   , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_BLUE     , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_BROWN    , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_GREEN    , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_RED      , ItemGroup.REDSTONE);
        //Register.registerBlock(ARCADE_SLOT_BLACK    , ItemGroup.REDSTONE);
    }

    public static void registerItems(){
        // Casino Games
        Register.registerItem(MODULE_BLACKJACK  );
        Register.registerItem(MODULE_BACCARAT   );
        Register.registerItem(MODULE_VIDEOPOKER );
        Register.registerItem(MODULE_ACEYDEUCEY );
        Register.registerItem(MODULE_ROUGEETNOIR);
        Register.registerItem(MODULE_CRAPS      );
        Register.registerItem(MODULE_SICBO      );
        Register.registerItem(MODULE_ROULETTE   );

        // Card Games
        Register.registerItem(MODULE_PYRAMID );
        Register.registerItem(MODULE_TRIPEAK );
        Register.registerItem(MODULE_FREECELL);
        Register.registerItem(MODULE_KLONDIKE);
        Register.registerItem(MODULE_SPIDER  );

        // Normal Games
        Register.registerItem(MODULE_MEMORY      );
        Register.registerItem(MODULE_MYSTICSQUARE);
        Register.registerItem(MODULE_SUDOKU      );
        Register.registerItem(MODULE_HALMA       );
        Register.registerItem(MODULE_MINESWEEPER );

        // Arcade Games
        Register.registerItem(MODULE_TETRIS   );
        Register.registerItem(MODULE_COLUMNS  );
        Register.registerItem(MODULE_MEANMINOS);
        Register.registerItem(MODULE_SNAKE    );
        Register.registerItem(MODULE_SOKOBAN  );
        Register.registerItem(MODULE_2048     );
    }

    public static final TileEntityType<TileEntityArcade>    TILETYPE_ARCADE    = TileEntityType.register(MODID + ":arcade",    TileEntityType.Builder.create(TileEntityArcade::new));
    public static final TileEntityType<TileEntityCardTable> TILETYPE_CARDTABLE = TileEntityType.register(MODID + ":cardtable", TileEntityType.Builder.create(TileEntityCardTable::new));

    public static final TileEntityType<TileEntityBlackJack>   TILETYPE_BLACKJACK   = TileEntityType.register(MODID + ":blackjack",   TileEntityType.Builder.create(TileEntityBlackJack::new));
    public static final TileEntityType<TileEntityBaccarat>    TILETYPE_BACCARAT    = TileEntityType.register(MODID + ":baccarat",    TileEntityType.Builder.create(TileEntityBaccarat::new));
    public static final TileEntityType<TileEntityVideoPoker>  TILETYPE_VIDEOPOKER  = TileEntityType.register(MODID + ":videopoker",  TileEntityType.Builder.create(TileEntityVideoPoker::new));
    public static final TileEntityType<TileEntityAceyDeucey>  TILETYPE_ACEYDEUCEY  = TileEntityType.register(MODID + ":aceydeucey",  TileEntityType.Builder.create(TileEntityAceyDeucey::new));
    public static final TileEntityType<TileEntityRougeEtNoir> TILETYPE_ROUGEETNOIR = TileEntityType.register(MODID + ":rougeetnoir", TileEntityType.Builder.create(TileEntityRougeEtNoir::new));
    public static final TileEntityType<TileEntityCraps>       TILETYPE_CRAPS       = TileEntityType.register(MODID + ":craps",       TileEntityType.Builder.create(TileEntityCraps::new));
    public static final TileEntityType<TileEntitySicBo>       TILETYPE_SICBO       = TileEntityType.register(MODID + ":sicbo",       TileEntityType.Builder.create(TileEntitySicBo::new));

    public static final TileEntityType<TileEntityPyramid>  TILETYPE_PYRAMID  = TileEntityType.register(MODID + ":pyramid",  TileEntityType.Builder.create(TileEntityPyramid::new));
    public static final TileEntityType<TileEntityTriPeak>  TILETYPE_TRIPEAK  = TileEntityType.register(MODID + ":tripeak",  TileEntityType.Builder.create(TileEntityTriPeak::new));
    public static final TileEntityType<TileEntityFreeCell> TILETYPE_FREECELL = TileEntityType.register(MODID + ":freecell", TileEntityType.Builder.create(TileEntityFreeCell::new));
    public static final TileEntityType<TileEntityKlondike> TILETYPE_KLONDIKE = TileEntityType.register(MODID + ":klondike", TileEntityType.Builder.create(TileEntityKlondike::new));
    public static final TileEntityType<TileEntitySpider>   TILETYPE_SPIDER   = TileEntityType.register(MODID + ":spider",   TileEntityType.Builder.create(TileEntitySpider::new));

    public static final TileEntityType<TileEntityMemory>       TILETYPE_MEMORY       = TileEntityType.register(MODID + ":memory",       TileEntityType.Builder.create(TileEntityMemory::new));
    public static final TileEntityType<TileEntityMysticSquare> TILETYPE_MYSTICSQUARE = TileEntityType.register(MODID + ":mysticsquare", TileEntityType.Builder.create(TileEntityMysticSquare::new));
    public static final TileEntityType<TileEntitySudoku>       TILETYPE_SUDOKU       = TileEntityType.register(MODID + ":sudoku",       TileEntityType.Builder.create(TileEntitySudoku::new));
    public static final TileEntityType<TileEntityHalma>        TILETYPE_HALMA        = TileEntityType.register(MODID + ":halma",        TileEntityType.Builder.create(TileEntityHalma::new));
    public static final TileEntityType<TileEntityMinesweeper>  TILETYPE_MINESWEEPER  = TileEntityType.register(MODID + ":minesweeper",  TileEntityType.Builder.create(TileEntityMinesweeper::new));

    public static final TileEntityType<TileEntityTetris>    TILETYPE_TETRIS    = TileEntityType.register(MODID + ":tetris",    TileEntityType.Builder.create(TileEntityTetris::new));
    public static final TileEntityType<TileEntityColumns>   TILETYPE_COLUMNS   = TileEntityType.register(MODID + ":columns",   TileEntityType.Builder.create(TileEntityColumns::new));
    public static final TileEntityType<TileEntityMeanMinos> TILETYPE_MEANMINOS = TileEntityType.register(MODID + ":meanminos", TileEntityType.Builder.create(TileEntityMeanMinos::new));
    public static final TileEntityType<TileEntitySnake>     TILETYPE_SNAKE     = TileEntityType.register(MODID + ":snake",     TileEntityType.Builder.create(TileEntitySnake::new));
    public static final TileEntityType<TileEntitySokoban>   TILETYPE_SOKOBAN   = TileEntityType.register(MODID + ":sokoban",   TileEntityType.Builder.create(TileEntitySokoban::new));
    public static final TileEntityType<TileEntity2048>      TILETYPE_2048      = TileEntityType.register(MODID + ":_2048",     TileEntityType.Builder.create(TileEntity2048::new));

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

    /**Register Living Entities**/
    public static void registerEntities(){
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_ARCADE);
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_CARDTABLE);

        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_BLACKJACK  );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_BACCARAT   );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_VIDEOPOKER );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_ACEYDEUCEY );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_ROUGEETNOIR);
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_CRAPS      );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SICBO      );

        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_PYRAMID );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_TRIPEAK );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_FREECELL);
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_KLONDIKE);
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SPIDER  );

        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MEMORY      );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MYSTICSQUARE);
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SUDOKU      );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_HALMA       );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MINESWEEPER );

        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_TETRIS   );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_COLUMNS  );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_MEANMINOS);
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SNAKE    );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_SOKOBAN  );
        ForgeRegistries.TILE_ENTITIES.register(TILETYPE_2048     );
        //GameRegistry.registerTileEntity(TileEntityArcade      .class, new ResourceLocation(CasinoCraft.modid, "casino_arcade"));
        //GameRegistry.registerTileEntity(TileEntityCardTable   .class, new ResourceLocation(CasinoCraft.modid, "casino_cardtable"));
        //GameRegistry.registerTileEntity(TileEntityBoard       .class, new ResourceLocation(CasinoCraft.modid, "casino_board"));
        //GameRegistry.registerTileEntity(TileEntityCasino      .class, new ResourceLocation(CasinoCraft.modid, "casino_casino"));
//
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
//
        //// Card Games
        //GameRegistry.registerTileEntity(TileEntityPyramid .class, new ResourceLocation(CasinoCraft.modid, "casino_pyramid"));
        //GameRegistry.registerTileEntity(TileEntityTriPeak .class, new ResourceLocation(CasinoCraft.modid, "casino_tripeak"));
        //GameRegistry.registerTileEntity(TileEntityFreeCell.class, new ResourceLocation(CasinoCraft.modid, "casino_freecell"));
        //GameRegistry.registerTileEntity(TileEntityKlondike.class, new ResourceLocation(CasinoCraft.modid, "casino_klondike"));
        //GameRegistry.registerTileEntity(TileEntitySpider  .class, new ResourceLocation(CasinoCraft.modid, "casino_spider"));
//
        //// Arcade Games
        //GameRegistry.registerTileEntity(TileEntityTetris     .class, new ResourceLocation(CasinoCraft.modid, "casino_tetris"));
        //GameRegistry.registerTileEntity(TileEntityColumns    .class, new ResourceLocation(CasinoCraft.modid, "casino_columns"));
        //GameRegistry.registerTileEntity(TileEntityMeanMinos  .class, new ResourceLocation(CasinoCraft.modid, "casino_meanminos"));
        //GameRegistry.registerTileEntity(TileEntity2048       .class, new ResourceLocation(CasinoCraft.modid, "casino_2048"));
        //GameRegistry.registerTileEntity(TileEntitySokoban    .class, new ResourceLocation(CasinoCraft.modid, "casino_sokoban"));
        //GameRegistry.registerTileEntity(TileEntitySnake      .class, new ResourceLocation(CasinoCraft.modid, "casino_snake"));
        //GameRegistry.registerTileEntity(TileEntityMinesweeper.class, new ResourceLocation(CasinoCraft.modid, "casino_minesweeper"));
//
        //// Normal Games
        //GameRegistry.registerTileEntity(TileEntityMemory      .class, new ResourceLocation(CasinoCraft.modid, "casino_memory"));
        //GameRegistry.registerTileEntity(TileEntityMysticSquare.class, new ResourceLocation(CasinoCraft.modid, "casino_mysticsquare"));
        //GameRegistry.registerTileEntity(TileEntitySudoku      .class, new ResourceLocation(CasinoCraft.modid, "casino_sudoku"));
        //GameRegistry.registerTileEntity(TileEntityHalma       .class, new ResourceLocation(CasinoCraft.modid, "casino_halma"));
    }

}
