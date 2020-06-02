package net.casinocraft.mod;

import net.casinocraft.mod.blocks.BlockArcade;
import net.casinocraft.mod.blocks.BlockCardTable;
import net.casinocraft.mod.items.ItemCardTable;
import net.casinocraft.mod.items.ItemModules;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityBaccarat;
import net.casinocraft.mod.tileentity.TileEntityBlackJack;
import net.casinocraft.mod.tileentity.TileEntityCardTable;
import net.casinocraft.mod.tileentity.TileEntityCasino;
import net.casinocraft.mod.tileentity.TileEntityMemory;
import net.casinocraft.mod.tileentity.TileEntityRoulette;
import net.casinocraft.mod.tileentity.TileEntitySlotMachine;
import net.casinocraft.mod.tileentity.TileEntityTetris;
import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CasinoKeeper {
	
	public static Block blockArcade      = new BlockArcade ("CasinoArcade");
	public static Block blockCardTable   = new BlockCardTable("CasinoCardTable");
	public static Item moduleBlackJack   = new ItemModules("ModuleBlackJack",   0);
	public static Item moduleBaccarat    = new ItemModules("ModuleBaccarat",    0);
	public static Item moduleVideoPoker  = new ItemModules("ModuleVideoPoker",  1);
	public static Item moduleTetris      = new ItemModules("ModuleTetris",      1);
	//public static Item moduleSlotMachine = new ItemModules("ModuleSlotMachine", 1);
	public static Item moduleMemory      = new ItemModules("ModuleMemory",      1);
	//public static Item moduleRoulette    = new ItemModules("ModuleRoulette",    0);
	
	// GUI IDs
	public static int guiIDCardTable   = 0;
	public static int guiIDArcade      = 1;
	public static int guiIDBlackJack   = 2;
	public static int guiIDBaccarat    = 3;
	public static int guiIDVideoPoker  = 4;
	public static int guiIDTetris      = 5;
	public static int guiIDSlotMachine = 6;
	public static int guiIDMemory      = 7;
	public static int guiIDRoulette    = 8;
	
	public static void loadConfig(FMLPreInitializationEvent preEvent){
		
	}
	
	public static void registerBlocks(boolean pre){
		registerBlock(blockArcade,           pre);
		registerColoredBlock(blockCardTable, pre);
		registerItem(moduleBlackJack,        pre);
		registerItem(moduleBaccarat,         pre);
		registerItem(moduleVideoPoker,       pre);
		registerItem(moduleTetris,           pre);
		//registerItem(moduleSlotMachine,      pre);
		registerItem(moduleMemory,           pre);
		//registerItem(moduleRoulette,         pre);
	}
	
	public static void registerEntities(){
		GameRegistry.registerTileEntity(TileEntityArcade     .class, "CasinoArcade");
		GameRegistry.registerTileEntity(TileEntityCardTable  .class, "CasinoCardTable");
		GameRegistry.registerTileEntity(TileEntityCasino     .class, "CasinoCasino");
		GameRegistry.registerTileEntity(TileEntityBaccarat   .class, "CasinoBaccarat");
		GameRegistry.registerTileEntity(TileEntityBlackJack  .class, "CasinoBlackJack");
		GameRegistry.registerTileEntity(TileEntityMemory     .class, "CasinoMemory");
		//GameRegistry.registerTileEntity(TileEntityRoulette   .class, "CasinoRoulette");
		//GameRegistry.registerTileEntity(TileEntitySlotMachine.class, "CasinoSlotMachine");
		GameRegistry.registerTileEntity(TileEntityTetris     .class, "CasinoTetris");
		GameRegistry.registerTileEntity(TileEntityVideoPoker .class, "CasinoVideoPoker");
	}
	
	private static void registerBlock(Block block, boolean pre){
		if(pre){
			GameRegistry.register(block);
			GameRegistry.register(new ItemBlock(block).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getRegistryName()));
		}else{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
		}
	}
	
	private static void registerColoredBlock(Block block, boolean pre){
		if(pre){
			GameRegistry.register(block);
			GameRegistry.register(new ItemCardTable(block).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getRegistryName()));
		}else{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 15, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),     "color=black"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 14, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),       "color=red"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 13, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),     "color=green"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 12, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),     "color=brown"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 11, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),      "color=blue"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 10, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),    "color=purple"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  9, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),      "color=cyan"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  8, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),    "color=silver"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  7, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),      "color=gray"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  6, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),      "color=pink"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  5, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),      "color=lime"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  4, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),    "color=yellow"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  3, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),"color=light_blue"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  2, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),   "color=magenta"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  1, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),    "color=orange"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  0, new ModelResourceLocation(CasinoCraft.modid + ":" + block.getUnlocalizedName().substring(5),     "color=white"));
		}
	}
	
	private static void registerItem(Item item, boolean pre){
		if(pre){
			GameRegistry.register(item);
		}else{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(CasinoCraft.modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}
	}
	
	public static void removeRecipes(){
		
	}
	
	public static void registerRecipes(){
		GameRegistry.addRecipe(new ItemStack(blockArcade), new Object[]{"XQX", "XXX", "XXX", 'Q', Items.QUARTZ, 'X', Items.IRON_INGOT});
		for(int i = 0; i < 16; i++){
			GameRegistry.addRecipe(new ItemStack(blockCardTable, 1, i), new Object[]{"XWX", " X ", "XXX", 'W', new ItemStack(Blocks.WOOL, 1, i), 'X', new ItemStack(Blocks.PLANKS, 1)});
		}
		GameRegistry.addRecipe(new ItemStack(moduleBlackJack),  new Object[]{"M", "P", "P", 'P', Items.PAPER,    'M', Items.DYE});
		GameRegistry.addRecipe(new ItemStack(moduleBaccarat),   new Object[]{"P", "P", "M", 'P', Items.PAPER,    'M', Items.DYE});
		GameRegistry.addRecipe(new ItemStack(moduleVideoPoker), new Object[]{"R", "M", "R", 'R', Items.REDSTONE, 'M', Items.DYE});
		GameRegistry.addRecipe(new ItemStack(moduleTetris),     new Object[]{"R", "M", "R", 'R', Items.REDSTONE, 'M', Items.CLAY_BALL});
		GameRegistry.addRecipe(new ItemStack(moduleMemory),     new Object[]{"P", "M", "P", 'P', Items.PAPER,    'M', Items.CLAY_BALL});
	}
	
}
