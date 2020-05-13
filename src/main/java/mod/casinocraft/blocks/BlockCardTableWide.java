package mod.casinocraft.blocks;

import mod.casinocraft.tileentities.TileEntityCardTableWide;
import mod.shared.blocks.IMachinaDoubleWide;
import mod.casinocraft.CasinoCraft;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCardTableWide extends IMachinaDoubleWide {

	final EnumDyeColor color;
	
	public BlockCardTableWide(String name, EnumDyeColor colorIn){
        super(name, Material.WOOD);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.color = colorIn;
    }
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if (world.isRemote){
            return true;
        } else {
        	BlockPos pos2 = pos;
        	boolean isPrimary = world.getBlockState(pos).getValue(PRIMARY);
        	EnumFacing enumfacing = world.getBlockState(pos).getValue(FACING);
        	if(!isPrimary) {
        		if(enumfacing == EnumFacing.NORTH) pos2 = pos.east();
            	if(enumfacing == EnumFacing.SOUTH) pos2 = pos.west();
            	if(enumfacing == EnumFacing.EAST ) pos2 = pos.south();
            	if(enumfacing == EnumFacing.WEST ) pos2 = pos.north();
        	}
        	if(world.getTileEntity(pos2) instanceof TileEntityBoard){
        		TileEntityBoard te = (TileEntityBoard) world.getTileEntity(pos2);

				if(te.getStackInSlot(0).isEmpty() || (te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem())){
					player.openGui(CasinoCraft.instance, 49, world, pos2.getX(), pos2.getY(), pos2.getZ());
				} else {
					     if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_WHITE)     player.openGui(CasinoCraft.instance, 16, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_ORANGE)    player.openGui(CasinoCraft.instance, 17, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_MAGENTA)   player.openGui(CasinoCraft.instance, 18, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_LIGHTBLUE) player.openGui(CasinoCraft.instance, 19, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_YELLOW)    player.openGui(CasinoCraft.instance, 20, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_LIME)      player.openGui(CasinoCraft.instance, 21, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_PINK)      player.openGui(CasinoCraft.instance, 22, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_GRAY)      player.openGui(CasinoCraft.instance, 23, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_SILVER)    player.openGui(CasinoCraft.instance, 24, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_CYAN)      player.openGui(CasinoCraft.instance, 25, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_PURPLE)    player.openGui(CasinoCraft.instance, 26, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_BLUE)      player.openGui(CasinoCraft.instance, 27, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_BROWN)     player.openGui(CasinoCraft.instance, 28, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_GREEN)     player.openGui(CasinoCraft.instance, 29, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_RED)       player.openGui(CasinoCraft.instance, 30, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CARD_BLACK)     player.openGui(CasinoCraft.instance, 31, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_WHITE)     player.openGui(CasinoCraft.instance, 32, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_ORANGE)    player.openGui(CasinoCraft.instance, 33, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_MAGENTA)   player.openGui(CasinoCraft.instance, 34, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_LIGHTBLUE) player.openGui(CasinoCraft.instance, 35, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_YELLOW)    player.openGui(CasinoCraft.instance, 36, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_LIME)      player.openGui(CasinoCraft.instance, 37, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_PINK)      player.openGui(CasinoCraft.instance, 38, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_GRAY)      player.openGui(CasinoCraft.instance, 39, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_SILVER)    player.openGui(CasinoCraft.instance, 40, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_CYAN)      player.openGui(CasinoCraft.instance, 41, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_PURPLE)    player.openGui(CasinoCraft.instance, 42, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_BLUE)      player.openGui(CasinoCraft.instance, 43, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_BROWN)     player.openGui(CasinoCraft.instance, 44, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_GREEN)     player.openGui(CasinoCraft.instance, 45, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_RED)       player.openGui(CasinoCraft.instance, 46, world, pos2.getX(), pos2.getY(), pos2.getZ());
					else if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINO_BLACK)     player.openGui(CasinoCraft.instance, 47, world, pos2.getX(), pos2.getY(), pos2.getZ());
						 else { player.openGui(CasinoCraft.instance, 52, world, pos2.getX(), pos2.getY(), pos2.getZ()); }
            	}
    			te.markDirty();
    		}
            return true;
        }
    }
	
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return meta < 8 ? null : new TileEntityCardTableWide(color, 2);
    }
	
}
