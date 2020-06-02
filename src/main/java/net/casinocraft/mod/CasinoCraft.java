package net.casinocraft.mod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.casinocraft.mod.blocks.BlockBlackJack;
import net.casinocraft.mod.blocks.BlockVideoPoker;
import net.casinocraft.mod.handler.GuiHandler;
import net.casinocraft.mod.tileentity.TileEntityBlackJack;
import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = CasinoCraft.modid, version = CasinoCraft.version, name = CasinoCraft.modName, dependencies = CasinoCraft.dependencies)
public class CasinoCraft {
	
	public static final String modid = "CasinoCraft";
	public static final String version = "v01";
	public static final String modName = "CasinoCraft";
	public static final String dependencies = "";
	
	@Instance(modid)
	public static CasinoCraft instance;
	
	public static CreativeTabs casinoTab;
	
	public static Block blockBlackJack;
	public static Block blockVideoPoker;
	
	public static final int guiIDBlackJack  = 0;
	public static final int guiIDVideoPoker = 1;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent preEvent){
		casinoTab = new CreativeTabs("CasinoCraft"){
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem(){
				return blockBlackJack.getItem(null, 0, 0, 0);
			}
		};
		blockBlackJack =  new BlockBlackJack().setBlockName("BlockBlackJack").setCreativeTab(casinoTab); GameRegistry.registerBlock(blockBlackJack, "BlockBlackJack");
		blockVideoPoker = new BlockVideoPoker().setBlockName("BlockVideoPoker").setCreativeTab(casinoTab); GameRegistry.registerBlock(blockVideoPoker, "BlockVideoPoker");
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event){
		NetworkRegistry.INSTANCE.registerGuiHandler(this,  new GuiHandler());
		GameRegistry.registerTileEntity(TileEntityBlackJack.class, "BlackJack");
		GameRegistry.registerTileEntity(TileEntityVideoPoker.class, "VideoPoker");
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent){
		
	}
	
}
