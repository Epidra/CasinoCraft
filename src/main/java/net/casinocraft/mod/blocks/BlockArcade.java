package net.casinocraft.mod.blocks;

import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.CasinoKeeper;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityCardTable;
import net.casinocraft.mod.tileentity.TileEntityCasino;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockArcade extends BlockContainer {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockArcade(String name) {
		super(Material.ANVIL);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setHardness(3.5f);
		this.setResistance(3.5f);
		this.setSoundType(SoundType.ANVIL);
		this.setLightOpacity(16);
		this.setLightLevel(0);
		this.setHarvestLevel("pickaxe", 0);
		this.setTickRandomly(false);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if (world.isRemote){
            return true;
        } else {
        	if(world.getTileEntity(pos) instanceof TileEntityCasino){
        		TileEntityCasino te = (TileEntityCasino) world.getTileEntity(pos);
    			
    			if(player.isSneaking()){
    				//if(te.Get_Owner() == player.getUniqueID()){
        				player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDArcade, world, pos.getX(), pos.getY(), pos.getZ());
        				player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        			//}
            	} else if(te.arcadeItemStacks[0] != null){
            	  //if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleBlackJack)   player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDBlackJack,   world, pos.getX(), pos.getY(), pos.getZ());
            	  //if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleBaccarat)    player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDBaccarat,    world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleVideoPoker)  player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDVideoPoker,  world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleTetris)      player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDTetris,      world, pos.getX(), pos.getY(), pos.getZ());
            	  //if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleMemory)      player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDMemory,      world, pos.getX(), pos.getY(), pos.getZ());
            	  //if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleSlotMachine) player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDSlotMachine, world, pos.getX(), pos.getY(), pos.getZ());
            	  //if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleRoulette)    player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDRoulette,    world, pos.getX(), pos.getY(), pos.getZ());
            		player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
            	}
    			
    			te.markDirty();
    		}
            return true;
        }
    }
	
	public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityCasino();
    }
	
	public boolean isFullCube(IBlockState state){
        return false;
    }
	
	public boolean isOpaqueCube(IBlockState state){
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }
	
	/**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		if (!worldIn.isRemote){
            IBlockState iblockstate  = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
                 if (enumfacing == EnumFacing.NORTH && iblockstate .isFullBlock() && !iblockstate1.isFullBlock()) { enumfacing = EnumFacing.SOUTH; }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate .isFullBlock()) { enumfacing = EnumFacing.NORTH; }
            else if (enumfacing == EnumFacing.WEST  && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) { enumfacing = EnumFacing.EAST;  }
            else if (enumfacing == EnumFacing.EAST  && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) { enumfacing = EnumFacing.WEST;  }
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        if(world.getTileEntity(pos) instanceof TileEntityArcade){
			TileEntityArcade te = (TileEntityArcade) world.getTileEntity(pos);
			te.Set_Owner(placer.getUniqueID());
		}
        
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta){
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state){
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    
    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
    
    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
	
	
}
