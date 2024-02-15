package mod.casinocraft;

import mod.casinocraft.client.menu.block.*;
import mod.casinocraft.client.menu.game.*;
import mod.casinocraft.client.menu.other.*;
import mod.casinocraft.client.screen.ScreenMachine;
import mod.casinocraft.client.screen.game.*;
import mod.casinocraft.client.screen.other.*;
import mod.casinocraft.common.block.*;
import mod.casinocraft.common.block.entity.*;
import mod.casinocraft.common.item.*;
import mod.lucky77.register.RegisterMod;
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
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static mod.casinocraft.CasinoCraft.MODID;

@SuppressWarnings("unused")
public class Register {
	
	// Create Deferred Registers to hold whatever is written in <...> which will all be registered under the MODID namespace
	private static final DeferredRegister<Block>               BLOCKS         = DeferredRegister.create(ForgeRegistries.BLOCKS,             MODID);
	private static final DeferredRegister<Item>                ITEMS          = DeferredRegister.create(ForgeRegistries.ITEMS,              MODID);
	private static final DeferredRegister<MenuType<?>>         MENUS          = DeferredRegister.create(ForgeRegistries.MENU_TYPES,         MODID);
	private static final DeferredRegister<BlockEntityType<?>>  BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
	private static final DeferredRegister<SoundEvent>          SOUNDS         = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,       MODID);
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BLOCKS / ITEMS  ---------- ---------- ---------- ---------- //
	
	// ----- Card Tables ----- //
	public static final RegistryObject<Block> CARDTABLE_BASE_WHITE      = registerBlock("cardtable_base_white",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.WHITE     ));
	public static final RegistryObject<Block> CARDTABLE_BASE_ORANGE     = registerBlock("cardtable_base_orange",     () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.ORANGE    ));
	public static final RegistryObject<Block> CARDTABLE_BASE_MAGENTA    = registerBlock("cardtable_base_magenta",    () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.MAGENTA   ));
	public static final RegistryObject<Block> CARDTABLE_BASE_LIGHT_BLUE = registerBlock("cardtable_base_light_blue", () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Block> CARDTABLE_BASE_YELLOW     = registerBlock("cardtable_base_yellow",     () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.YELLOW    ));
	public static final RegistryObject<Block> CARDTABLE_BASE_LIME       = registerBlock("cardtable_base_lime",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.LIME      ));
	public static final RegistryObject<Block> CARDTABLE_BASE_PINK       = registerBlock("cardtable_base_pink",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.PINK      ));
	public static final RegistryObject<Block> CARDTABLE_BASE_GRAY       = registerBlock("cardtable_base_gray",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.GRAY      ));
	public static final RegistryObject<Block> CARDTABLE_BASE_LIGHT_GRAY = registerBlock("cardtable_base_light_gray", () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Block> CARDTABLE_BASE_CYAN       = registerBlock("cardtable_base_cyan",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.CYAN      ));
	public static final RegistryObject<Block> CARDTABLE_BASE_PURPLE     = registerBlock("cardtable_base_purple",     () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.PURPLE    ));
	public static final RegistryObject<Block> CARDTABLE_BASE_BLUE       = registerBlock("cardtable_base_blue",       () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.BLUE      ));
	public static final RegistryObject<Block> CARDTABLE_BASE_BROWN      = registerBlock("cardtable_base_brown",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.BROWN     ));
	public static final RegistryObject<Block> CARDTABLE_BASE_GREEN      = registerBlock("cardtable_base_green",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.GREEN     ));
	public static final RegistryObject<Block> CARDTABLE_BASE_RED        = registerBlock("cardtable_base_red",        () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.RED       ));
	public static final RegistryObject<Block> CARDTABLE_BASE_BLACK      = registerBlock("cardtable_base_black",      () -> new BlockCardTableBase(Blocks.IRON_BLOCK, DyeColor.BLACK     ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_WHITE      = registerBlock("cardtable_wide_white",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.WHITE     ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_ORANGE     = registerBlock("cardtable_wide_orange",     () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.ORANGE    ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_MAGENTA    = registerBlock("cardtable_wide_magenta",    () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.MAGENTA   ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_LIGHT_BLUE = registerBlock("cardtable_wide_light_blue", () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Block> CARDTABLE_WIDE_YELLOW     = registerBlock("cardtable_wide_yellow",     () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.YELLOW    ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_LIME       = registerBlock("cardtable_wide_lime",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.LIME      ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_PINK       = registerBlock("cardtable_wide_pink",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.PINK      ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_GRAY       = registerBlock("cardtable_wide_gray",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.GRAY      ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_LIGHT_GRAY = registerBlock("cardtable_wide_light_gray", () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Block> CARDTABLE_WIDE_CYAN       = registerBlock("cardtable_wide_cyan",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.CYAN      ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_PURPLE     = registerBlock("cardtable_wide_purple",     () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.PURPLE    ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_BLUE       = registerBlock("cardtable_wide_blue",       () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.BLUE      ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_BROWN      = registerBlock("cardtable_wide_brown",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.BROWN     ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_GREEN      = registerBlock("cardtable_wide_green",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.GREEN     ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_RED        = registerBlock("cardtable_wide_red",        () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.RED       ));
	public static final RegistryObject<Block> CARDTABLE_WIDE_BLACK      = registerBlock("cardtable_wide_black",      () -> new BlockCardTableWide(Blocks.IRON_BLOCK, DyeColor.BLACK     ));
	
	// ----- Arcades ----- //
	public static final RegistryObject<Block> ARCADE_BASE_WHITE      = registerBlock("arcade_base_white",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.WHITE     ));
	public static final RegistryObject<Block> ARCADE_BASE_ORANGE     = registerBlock("arcade_base_orange",     () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.ORANGE    ));
	public static final RegistryObject<Block> ARCADE_BASE_MAGENTA    = registerBlock("arcade_base_magenta",    () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.MAGENTA   ));
	public static final RegistryObject<Block> ARCADE_BASE_LIGHT_BLUE = registerBlock("arcade_base_light_blue", () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Block> ARCADE_BASE_YELLOW     = registerBlock("arcade_base_yellow",     () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.YELLOW    ));
	public static final RegistryObject<Block> ARCADE_BASE_LIME       = registerBlock("arcade_base_lime",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIME      ));
	public static final RegistryObject<Block> ARCADE_BASE_PINK       = registerBlock("arcade_base_pink",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.PINK      ));
	public static final RegistryObject<Block> ARCADE_BASE_GRAY       = registerBlock("arcade_base_gray",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.GRAY      ));
	public static final RegistryObject<Block> ARCADE_BASE_LIGHT_GRAY = registerBlock("arcade_base_light_gray", () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Block> ARCADE_BASE_CYAN       = registerBlock("arcade_base_cyan",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.CYAN      ));
	public static final RegistryObject<Block> ARCADE_BASE_PURPLE     = registerBlock("arcade_base_purple",     () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.PURPLE    ));
	public static final RegistryObject<Block> ARCADE_BASE_BLUE       = registerBlock("arcade_base_blue",       () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BLUE      ));
	public static final RegistryObject<Block> ARCADE_BASE_BROWN      = registerBlock("arcade_base_brown",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BROWN     ));
	public static final RegistryObject<Block> ARCADE_BASE_GREEN      = registerBlock("arcade_base_green",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.GREEN     ));
	public static final RegistryObject<Block> ARCADE_BASE_RED        = registerBlock("arcade_base_red",        () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.RED       ));
	public static final RegistryObject<Block> ARCADE_BASE_BLACK      = registerBlock("arcade_base_black",      () -> new BlockArcade(     Blocks.IRON_BLOCK, DyeColor.BLACK     ));
	public static final RegistryObject<Block> ARCADE_SLOT_WHITE      = registerBlock("arcade_slot_white",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.WHITE     ));
	public static final RegistryObject<Block> ARCADE_SLOT_ORANGE     = registerBlock("arcade_slot_orange",     () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.ORANGE    ));
	public static final RegistryObject<Block> ARCADE_SLOT_MAGENTA    = registerBlock("arcade_slot_magenta",    () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.MAGENTA   ));
	public static final RegistryObject<Block> ARCADE_SLOT_LIGHT_BLUE = registerBlock("arcade_slot_light_blue", () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Block> ARCADE_SLOT_YELLOW     = registerBlock("arcade_slot_yellow",     () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.YELLOW    ));
	public static final RegistryObject<Block> ARCADE_SLOT_LIME       = registerBlock("arcade_slot_lime",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIME      ));
	public static final RegistryObject<Block> ARCADE_SLOT_PINK       = registerBlock("arcade_slot_pink",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.PINK      ));
	public static final RegistryObject<Block> ARCADE_SLOT_GRAY       = registerBlock("arcade_slot_gray",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.GRAY      ));
	public static final RegistryObject<Block> ARCADE_SLOT_LIGHT_GRAY = registerBlock("arcade_slot_light_gray", () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Block> ARCADE_SLOT_CYAN       = registerBlock("arcade_slot_cyan",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.CYAN      ));
	public static final RegistryObject<Block> ARCADE_SLOT_PURPLE     = registerBlock("arcade_slot_purple",     () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.PURPLE    ));
	public static final RegistryObject<Block> ARCADE_SLOT_BLUE       = registerBlock("arcade_slot_blue",       () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BLUE      ));
	public static final RegistryObject<Block> ARCADE_SLOT_BROWN      = registerBlock("arcade_slot_brown",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BROWN     ));
	public static final RegistryObject<Block> ARCADE_SLOT_GREEN      = registerBlock("arcade_slot_green",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.GREEN     ));
	public static final RegistryObject<Block> ARCADE_SLOT_RED        = registerBlock("arcade_slot_red",        () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.RED       ));
	public static final RegistryObject<Block> ARCADE_SLOT_BLACK      = registerBlock("arcade_slot_black",      () -> new BlockSlotMachine(Blocks.IRON_BLOCK, DyeColor.BLACK     ));
	
	// ----- Books ----- //
	public static final RegistryObject<Item> RULEBOOK_1_1 = registerItem("rulebook_1_1", () -> new ItemRulebook(1, 11, false, new int[]{1         },  true, false,  1));  //  Roulette
	public static final RegistryObject<Item> RULEBOOK_2_1 = registerItem("rulebook_2_1", () -> new ItemRulebook(5, 21, false, new int[]{1         },  true, false,  0));  //  BlackJack
	public static final RegistryObject<Item> RULEBOOK_2_2 = registerItem("rulebook_2_2", () -> new ItemRulebook(5, 22, false, new int[]{1         },  true, false,  0));  //  Poker
	public static final RegistryObject<Item> RULEBOOK_3_1 = registerItem("rulebook_3_1", () -> new ItemRulebook(3, 31, false, new int[]{1         }, false,  true,  0));  //  Solitaire
	public static final RegistryObject<Item> RULEBOOK_3_2 = registerItem("rulebook_3_2", () -> new ItemRulebook(3, 31, false, new int[]{1         }, false,  true,  0));  //  Pyramid
	public static final RegistryObject<Item> RULEBOOK_3_3 = registerItem("rulebook_3_3", () -> new ItemRulebook(3, 33, false, new int[]{1         }, false,  true,  0));  //  MauMau
	public static final RegistryObject<Item> RULEBOOK_4_1 = registerItem("rulebook_4_1", () -> new ItemRulebook(4, 41, false, new int[]{1         }, false,  true, -1));  //  Minesweeper
	public static final RegistryObject<Item> RULEBOOK_4_2 = registerItem("rulebook_4_2", () -> new ItemRulebook(4, 42, false, new int[]{1         }, false,  true, -1));  //  Ishido
	public static final RegistryObject<Item> RULEBOOK_5_1 = registerItem("rulebook_5_1", () -> new ItemRulebook(2, 51,  true, new int[]{3, 2, 3, 2}, false,  true,  2));  //  Tetris
	public static final RegistryObject<Item> RULEBOOK_5_2 = registerItem("rulebook_5_2", () -> new ItemRulebook(2, 52,  true, new int[]{2, 2      }, false,  true,  2));  //  2048
	public static final RegistryObject<Item> RULEBOOK_6_1 = registerItem("rulebook_6_1", () -> new ItemRulebook(6, 61,  true, new int[]{1         }, false,  true,  2));  //  Snake
	public static final RegistryObject<Item> RULEBOOK_6_2 = registerItem("rulebook_6_2", () -> new ItemRulebook(6, 62,  true, new int[]{1         }, false,  true,  2));  //  Sokoban
	public static final RegistryObject<Item> RULEBOOK_7_0 = registerItem("rulebook_7_0", () -> new ItemRulebook(0, 70,  true, new int[]{1         }, false,  true,  3));  // Slot Machine
	
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SOUNDS  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<SoundEvent> SOUND_PICKUP              = SOUNDS.register("casinocraft.pickup",         () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.pickup"        )));
	public static final RegistryObject<SoundEvent> SOUND_IMPACT              = SOUNDS.register("casinocraft.impact",         () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.impact"        )));
	public static final RegistryObject<SoundEvent> SOUND_MENU                = SOUNDS.register("casinocraft.menu",           () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.menu"          )));
	public static final RegistryObject<SoundEvent> SOUND_PAUSE_ON            = SOUNDS.register("casinocraft.pause.on",       () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.pause.on"      )));
	public static final RegistryObject<SoundEvent> SOUND_PAUSE_OFF           = SOUNDS.register("casinocraft.pause.off",      () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.pause.off"     )));
	public static final RegistryObject<SoundEvent> SOUND_TETRIS              = SOUNDS.register("casinocraft.tetris",         () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.tetris"        )));
	public static final RegistryObject<SoundEvent> SOUND_CARD_PLAC           = SOUNDS.register("casinocraft.card.place",     () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.card.place"    )));
	public static final RegistryObject<SoundEvent> SOUND_CARD_SHOV           = SOUNDS.register("casinocraft.card.shove",     () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.card.shove"    )));
	public static final RegistryObject<SoundEvent> SOUND_ROULETTE            = SOUNDS.register("casinocraft.roulette",       () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.roulette"      )));
	public static final RegistryObject<SoundEvent> SOUND_CHIP                = SOUNDS.register("casinocraft.chip",           () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.chip"          )));
	public static final RegistryObject<SoundEvent> SOUND_DICE                = SOUNDS.register("casinocraft.dice",           () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.dice"          )));
	public static final RegistryObject<SoundEvent> SOUND_REWARD              = SOUNDS.register("casinocraft.reward",         () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "casinocraft.reward"        )));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BLOCK ENTITES  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<BlockEntityType<?>> TILE_ARCADE_BASE    = BLOCK_ENTITIES.register("arcade_base",    () -> BlockEntityType.Builder.of(BlockEntityArcade::new,        ARCADE_BASE_BLACK.get(   ), ARCADE_BASE_BLUE.get(   ), ARCADE_BASE_BROWN.get(   ), ARCADE_BASE_CYAN.get(   ), ARCADE_BASE_GRAY.get(   ), ARCADE_BASE_GREEN.get(   ), ARCADE_BASE_LIGHT_BLUE.get(   ), ARCADE_BASE_LIME.get(   ), ARCADE_BASE_MAGENTA.get(   ), ARCADE_BASE_ORANGE.get(   ), ARCADE_BASE_PINK.get(   ), ARCADE_BASE_PURPLE.get(   ), ARCADE_BASE_RED.get(   ), ARCADE_BASE_LIGHT_GRAY.get(   ), ARCADE_BASE_WHITE.get(   ), ARCADE_BASE_YELLOW.get(   )).build(null));
	public static final RegistryObject<BlockEntityType<?>> TILE_ARCADE_SLOT    = BLOCK_ENTITIES.register("arcade_slot",    () -> BlockEntityType.Builder.of(BlockEntitySlotMachine::new,   ARCADE_SLOT_BLACK.get(   ), ARCADE_SLOT_BLUE.get(   ), ARCADE_SLOT_BROWN.get(   ), ARCADE_SLOT_CYAN.get(   ), ARCADE_SLOT_GRAY.get(   ), ARCADE_SLOT_GREEN.get(   ), ARCADE_SLOT_LIGHT_BLUE.get(   ), ARCADE_SLOT_LIME.get(   ), ARCADE_SLOT_MAGENTA.get(   ), ARCADE_SLOT_ORANGE.get(   ), ARCADE_SLOT_PINK.get(   ), ARCADE_SLOT_PURPLE.get(   ), ARCADE_SLOT_RED.get(   ), ARCADE_SLOT_LIGHT_GRAY.get(   ), ARCADE_SLOT_WHITE.get(   ), ARCADE_SLOT_YELLOW.get(   )).build(null));
	public static final RegistryObject<BlockEntityType<?>> TILE_CARDTABLE_BASE = BLOCK_ENTITIES.register("cardtable_base", () -> BlockEntityType.Builder.of(BlockEntityCardTableBase::new, CARDTABLE_BASE_BLACK.get(), CARDTABLE_BASE_BLUE.get(), CARDTABLE_BASE_BROWN.get(), CARDTABLE_BASE_CYAN.get(), CARDTABLE_BASE_GRAY.get(), CARDTABLE_BASE_GREEN.get(), CARDTABLE_BASE_LIGHT_BLUE.get(), CARDTABLE_BASE_LIME.get(), CARDTABLE_BASE_MAGENTA.get(), CARDTABLE_BASE_ORANGE.get(), CARDTABLE_BASE_PINK.get(), CARDTABLE_BASE_PURPLE.get(), CARDTABLE_BASE_RED.get(), CARDTABLE_BASE_LIGHT_GRAY.get(), CARDTABLE_BASE_WHITE.get(), CARDTABLE_BASE_YELLOW.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>> TILE_CARDTABLE_WIDE = BLOCK_ENTITIES.register("cardtable_wide", () -> BlockEntityType.Builder.of(BlockEntityCardTableWide::new, CARDTABLE_WIDE_BLACK.get(), CARDTABLE_WIDE_BLUE.get(), CARDTABLE_WIDE_BROWN.get(), CARDTABLE_WIDE_CYAN.get(), CARDTABLE_WIDE_GRAY.get(), CARDTABLE_WIDE_GREEN.get(), CARDTABLE_WIDE_LIGHT_BLUE.get(), CARDTABLE_WIDE_LIME.get(), CARDTABLE_WIDE_MAGENTA.get(), CARDTABLE_WIDE_ORANGE.get(), CARDTABLE_WIDE_PINK.get(), CARDTABLE_WIDE_PURPLE.get(), CARDTABLE_WIDE_RED.get(), CARDTABLE_WIDE_LIGHT_GRAY.get(), CARDTABLE_WIDE_WHITE.get(), CARDTABLE_WIDE_YELLOW.get()).build(null));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  MENUS  ---------- ---------- ---------- ---------- //
	
	public static final RegistryObject<MenuType<MenuDummy>>         MENU_DUMMY           = MENUS.register("dummy",           () -> IForgeMenuType.create(MenuDummy::new));
	public static final RegistryObject<MenuType<MenuArcade>>        MENU_ARCADE          = MENUS.register("arcade",          () -> IForgeMenuType.create(MenuArcade::new));
	public static final RegistryObject<MenuType<MenuCardTable>>     MENU_CARDTABLE       = MENUS.register("cardtable",       () -> IForgeMenuType.create(MenuCardTable::new));
	public static final RegistryObject<MenuType<MenuSlotMachine>>   MENU_SLOTMACHINE     = MENUS.register("slotmachine",     () -> IForgeMenuType.create(MenuSlotMachine::new));
	public static final RegistryObject<MenuType<MenuSlotGame>>      MENU_SLOTGAME        = MENUS.register("slotgame",        () -> IForgeMenuType.create(MenuSlotGame::new));
	public static final RegistryObject<MenuType<Menu21>>     MENU_BLACKJACK      = MENUS.register("game_blackjack",      () -> IForgeMenuType.create(Menu21::new));
	public static final RegistryObject<MenuType<Menu22>>     MENU_POKER      = MENUS.register("game_poker",      () -> IForgeMenuType.create(Menu22::new));
	public static final RegistryObject<MenuType<Menu11>>     MENU_ROULETTE      = MENUS.register("game_roulette",      () -> IForgeMenuType.create(Menu11::new));
	public static final RegistryObject<MenuType<Menu31>>     MENU_SOLITAIRE      = MENUS.register("game_solitaire",      () -> IForgeMenuType.create(Menu31::new));
	public static final RegistryObject<MenuType<Menu32>>     MENU_PYRAMID      = MENUS.register("game_pyramid",      () -> IForgeMenuType.create(Menu32::new));
	public static final RegistryObject<MenuType<Menu33>>     MENU_MAUMAU      = MENUS.register("game_maumau",      () -> IForgeMenuType.create(Menu33::new));
	public static final RegistryObject<MenuType<Menu41>>     MENU_MINESWEEPER      = MENUS.register("game_minesweepr",      () -> IForgeMenuType.create(Menu41::new));
	public static final RegistryObject<MenuType<Menu42>>     MENU_ISHIDO      = MENUS.register("game_ishido",      () -> IForgeMenuType.create(Menu42::new));
	public static final RegistryObject<MenuType<Menu61>>     MENU_SNAKE      = MENUS.register("game_snake",      () -> IForgeMenuType.create(Menu61::new));
	public static final RegistryObject<MenuType<Menu51>>     MENU_TETRIS      = MENUS.register("game_tetris",      () -> IForgeMenuType.create(Menu51::new));
	public static final RegistryObject<MenuType<Menu62>>     MENU_SOKOBAN      = MENUS.register("game_sokoban",      () -> IForgeMenuType.create(Menu62::new));
	public static final RegistryObject<MenuType<Menu52>>     MENU_2048      = MENUS.register("game_2048",      () -> IForgeMenuType.create(Menu52::new));
	
	
	
	
	
	// ---------- ---------- ---------- ----------  TEXTURES  ---------- ---------- ---------- ---------- //
	
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
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SETUP  ---------- ---------- ---------- ---------- //
	
	static void setup(FMLCommonSetupEvent event){
		RegisterMod.register("casinocraft");
	}
	
	@OnlyIn(Dist.CLIENT)
	static void setup(FMLClientSetupEvent event){
		event.enqueueWork(() -> {
			MenuScreens.register(MENU_DUMMY.get(          ), ScreenDummy::new);
			MenuScreens.register(MENU_ARCADE.get(         ), ScreenMachine::new);
			MenuScreens.register(MENU_CARDTABLE.get(      ), ScreenMachine::new);
			MenuScreens.register(MENU_SLOTMACHINE.get(    ), ScreenMachine::new);
			MenuScreens.register(MENU_SLOTGAME.get(       ), ScreenSlotGame::new);
			MenuScreens.register(MENU_BLACKJACK.get(     ), Screen21::new);
			MenuScreens.register(MENU_POKER.get(     ), Screen22::new);
			MenuScreens.register(MENU_ROULETTE.get(     ), Screen11::new);
			MenuScreens.register(MENU_SOLITAIRE.get(     ), Screen31::new);
			MenuScreens.register(MENU_PYRAMID.get(     ), Screen32::new);
			MenuScreens.register(MENU_MAUMAU.get(     ), Screen33::new);
			MenuScreens.register(MENU_MINESWEEPER.get(     ), Screen41::new);
			MenuScreens.register(MENU_ISHIDO.get(     ), Screen42::new);
			MenuScreens.register(MENU_SNAKE.get(     ), Screen61::new);
			MenuScreens.register(MENU_TETRIS.get(     ), Screen51::new);
			MenuScreens.register(MENU_SOKOBAN.get(     ), Screen62::new);
			MenuScreens.register(MENU_2048.get(     ), Screen52::new);
		});
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  REGISTER  ---------- ---------- ---------- ---------- //
	
	// registers all deferred registries to the game
	public static void register(IEventBus bus){
		BLOCKS.register(        bus);
		ITEMS.register(         bus);
		MENUS.register(         bus);
		BLOCK_ENTITIES.register(bus);
		SOUNDS.register(        bus);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  REGISTER CREATIVE TABS  ---------- ---------- ---------- ---------- //
	
	public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event){
		if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
			event.accept(Register.CARDTABLE_BASE_WHITE);
			event.accept(Register.CARDTABLE_BASE_ORANGE);
			event.accept(Register.CARDTABLE_BASE_MAGENTA);
			event.accept(Register.CARDTABLE_BASE_LIGHT_BLUE);
			event.accept(Register.CARDTABLE_BASE_YELLOW);
			event.accept(Register.CARDTABLE_BASE_LIME);
			event.accept(Register.CARDTABLE_BASE_PINK);
			event.accept(Register.CARDTABLE_BASE_GRAY);
			event.accept(Register.CARDTABLE_BASE_LIGHT_GRAY);
			event.accept(Register.CARDTABLE_BASE_CYAN);
			event.accept(Register.CARDTABLE_BASE_PURPLE);
			event.accept(Register.CARDTABLE_BASE_BLUE);
			event.accept(Register.CARDTABLE_BASE_BROWN);
			event.accept(Register.CARDTABLE_BASE_GREEN);
			event.accept(Register.CARDTABLE_BASE_RED);
			event.accept(Register.CARDTABLE_BASE_BLACK);
			event.accept(Register.CARDTABLE_WIDE_WHITE);
			event.accept(Register.CARDTABLE_WIDE_ORANGE);
			event.accept(Register.CARDTABLE_WIDE_MAGENTA);
			event.accept(Register.CARDTABLE_WIDE_LIGHT_BLUE);
			event.accept(Register.CARDTABLE_WIDE_YELLOW);
			event.accept(Register.CARDTABLE_WIDE_LIME);
			event.accept(Register.CARDTABLE_WIDE_PINK);
			event.accept(Register.CARDTABLE_WIDE_GRAY);
			event.accept(Register.CARDTABLE_WIDE_LIGHT_GRAY);
			event.accept(Register.CARDTABLE_WIDE_CYAN);
			event.accept(Register.CARDTABLE_WIDE_PURPLE);
			event.accept(Register.CARDTABLE_WIDE_BLUE);
			event.accept(Register.CARDTABLE_WIDE_BROWN);
			event.accept(Register.CARDTABLE_WIDE_GREEN);
			event.accept(Register.CARDTABLE_WIDE_RED);
			event.accept(Register.CARDTABLE_WIDE_BLACK);
			event.accept(Register.ARCADE_BASE_WHITE);
			event.accept(Register.ARCADE_BASE_ORANGE);
			event.accept(Register.ARCADE_BASE_MAGENTA);
			event.accept(Register.ARCADE_BASE_LIGHT_BLUE);
			event.accept(Register.ARCADE_BASE_YELLOW);
			event.accept(Register.ARCADE_BASE_LIME);
			event.accept(Register.ARCADE_BASE_PINK);
			event.accept(Register.ARCADE_BASE_GRAY);
			event.accept(Register.ARCADE_BASE_LIGHT_GRAY);
			event.accept(Register.ARCADE_BASE_CYAN);
			event.accept(Register.ARCADE_BASE_PURPLE);
			event.accept(Register.ARCADE_BASE_BLUE);
			event.accept(Register.ARCADE_BASE_BROWN);
			event.accept(Register.ARCADE_BASE_GREEN);
			event.accept(Register.ARCADE_BASE_RED);
			event.accept(Register.ARCADE_BASE_BLACK);
			event.accept(Register.ARCADE_SLOT_WHITE);
			event.accept(Register.ARCADE_SLOT_ORANGE);
			event.accept(Register.ARCADE_SLOT_MAGENTA);
			event.accept(Register.ARCADE_SLOT_LIGHT_BLUE);
			event.accept(Register.ARCADE_SLOT_YELLOW);
			event.accept(Register.ARCADE_SLOT_LIME);
			event.accept(Register.ARCADE_SLOT_PINK);
			event.accept(Register.ARCADE_SLOT_GRAY);
			event.accept(Register.ARCADE_SLOT_LIGHT_GRAY);
			event.accept(Register.ARCADE_SLOT_CYAN);
			event.accept(Register.ARCADE_SLOT_PURPLE);
			event.accept(Register.ARCADE_SLOT_BLUE);
			event.accept(Register.ARCADE_SLOT_BROWN);
			event.accept(Register.ARCADE_SLOT_GREEN);
			event.accept(Register.ARCADE_SLOT_RED);
			event.accept(Register.ARCADE_SLOT_BLACK);
		}
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
			event.accept(Register.RULEBOOK_1_1);
			event.accept(Register.RULEBOOK_2_1);
			event.accept(Register.RULEBOOK_2_2);
			event.accept(Register.RULEBOOK_3_1);
			event.accept(Register.RULEBOOK_3_2);
			event.accept(Register.RULEBOOK_3_3);
			event.accept(Register.RULEBOOK_4_1);
			event.accept(Register.RULEBOOK_4_2);
			event.accept(Register.RULEBOOK_5_1);
			event.accept(Register.RULEBOOK_5_2);
			event.accept(Register.RULEBOOK_6_1);
			event.accept(Register.RULEBOOK_6_2);
			event.accept(Register.RULEBOOK_7_0);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// creates a block for the registry
	// sets up a creative tab for an additional registry later
	// if no creative tab, it does not create an fitting itemblock
	protected static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> block, boolean registerItemBlock){
		RegistryObject<Block> supply = BLOCKS.register(name, block);
		if(registerItemBlock){
			ITEMS.register(name, () -> new BlockItem(supply.get(), new Item.Properties()));
		}
		return supply;
	}
	
	protected static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> block){
		return registerBlock(name, block, true);
	}
	
	// creates an item fo the registry
	protected static RegistryObject<Item> registerItem(String name, Supplier<? extends Item> item){
		return ITEMS.register(name, item);
	}
	
	
	
}