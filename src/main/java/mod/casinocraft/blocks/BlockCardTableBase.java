package mod.casinocraft.blocks;

import mod.shared.blocks.IMachinaBasic;
import mod.casinocraft.CasinoCraft;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCardTableBase extends IMachinaBasic {
	
	public BlockCardTableBase(String name){
        super(name, Material.WOOD);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
    
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if (world.isRemote){
            return true;
        } else {
        	if(world.getTileEntity(pos) instanceof TileEntityBoard){
        		TileEntityBoard te = (TileEntityBoard) world.getTileEntity(pos);
        		
        		if(te.getStackInSlot(0) == null || (player.getHeldItem(hand) != null && te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem() && te.getStackInSlot(0).getDisplayName().matches(player.getHeldItem(hand).getDisplayName()))){
					player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.CARDTABLE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
    				player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
            	} else if(te.inventory.get(1) != null){
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_BLACKJACK)    player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.BLACKJACK.ordinal(),    world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_BACCARAT)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.BACCARAT.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MEMORY)       player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MEMORY.ordinal(),       world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MYSTICSQUARE) player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MYSTICSQUARE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_PYRAMID)      player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.PYRAMID.ordinal(),      world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_TRIPEAK)      player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.TRIPEAK.ordinal(),      world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ACEYDEUCEY)   player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ACEYDEUCEY.ordinal(),   world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_HALMA)        player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.HALMA.ordinal(),        world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ROUGEETNOIR)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ROUGEETNOIR.ordinal(),  world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SUDOKU)       player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SUDOKU.ordinal(),       world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ROULETTE)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ROULETTE.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CRAPS)        player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.CRAPS.ordinal(),        world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SICBO)        player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SICBO.ordinal(),        world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_KLONDIKE)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.KLONDIKE.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_FREECELL)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.FREECELL.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SPIDER)       player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SPIDER.ordinal(),       world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_VIDEOPOKER)   player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.VIDEOPOKER.ordinal(),   world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINESWEEPER)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MINESWEEPER.ordinal(),  world, pos.getX(), pos.getY(), pos.getZ());
            		player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
            	}
    			te.markDirty();
    		}
            return true;
        }
    }
	
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityBoard();
    }
	
}
