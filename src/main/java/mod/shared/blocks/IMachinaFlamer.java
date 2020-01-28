package mod.shared.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IMachinaFlamer extends BlockContainer {
	
	/** Enum Value for horizontal placement */
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	/** Enum Value for Power State */
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	
	
	
	
	//----------------------------------------CONSTRUCTOR----------------------------------------//
	
	/** Default Contructor */
	public IMachinaFlamer(String name, Material material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setHardness(2);
		this.setResistance(2);
		this.setSoundType(SoundType.GROUND);
		this.setHarvestLevel("pickaxe", 0);
		this.setTickRandomly(false);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		if(material == Material.ANVIL){ this.setHarvestLevel("pickaxe", 2); this.setSoundType(SoundType.ANVIL); }
		if(material == Material.IRON) { this.setHarvestLevel("pickaxe", 2); this.setSoundType(SoundType.METAL); }
		if(material == Material.ROCK) { this.setHarvestLevel("pickaxe", 1); this.setSoundType(SoundType.STONE); }
		if(material == Material.WOOD) { this.setHarvestLevel("axe",     0); this.setSoundType(SoundType.WOOD);  }
	}
	
	
	
	
	//----------------------------------------RENDER----------------------------------------//
	
	/** Returns FALSE, the Block is not a full Cube */
	public boolean isFullCube(IBlockState state){
        return false;
    }
	
	/** Returns FALSE, the Block is not a full AND solid Block */
	public boolean isOpaqueCube(IBlockState state){
        return false;
    }
	
	/** The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to skip all rendering */
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
	
	/** Returns the Light Value based on Power State */
	public int getLightValue(IBlockState state){
		boolean ispowered = state.getValue(POWERED);
        return ispowered ? 15 : 0;
    }
	
	
	
	
	//----------------------------------------PLACEMENT----------------------------------------//
	
	/** Called after the block is set in the Chunk data, but before the Tile Entity is set */
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		if (!worldIn.isRemote){
            IBlockState iblockstate  = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);
                 if (enumfacing == EnumFacing.NORTH && iblockstate .isFullBlock() && !iblockstate1.isFullBlock()) { enumfacing = EnumFacing.SOUTH; }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate .isFullBlock()) { enumfacing = EnumFacing.NORTH; }
            else if (enumfacing == EnumFacing.WEST  && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) { enumfacing = EnumFacing.EAST;  }
            else if (enumfacing == EnumFacing.EAST  && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) { enumfacing = EnumFacing.WEST;  }
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(POWERED, Boolean.valueOf(false)), 2);
        }
    }
    
    /** Called by ItemBlocks after a block is set in the world, to allow post-place logic */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(POWERED, Boolean.valueOf(false)), 2);
    }
    
    
    
    
	//----------------------------------------SUPPORT----------------------------------------//
    
    /** Called when the block is right clicked by a player. */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        return false;
    }
    
    /** Sets the Enum Power State */
    public static void setPowerState(boolean active, World world, BlockPos pos){
        IBlockState iblockstate = world.getBlockState(pos);
        TileEntity tileentity   = world.getTileEntity(pos);
        world.setBlockState(pos, iblockstate.withProperty(POWERED, active));
        if (tileentity != null){
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }
    
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
    	EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
        int i = state.getValue(FACING).getIndex();
        if (state.getValue(POWERED).booleanValue()){
            i |= 8;
        }
        return i;
    }
    
    /** Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate. */
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }
    
    /** Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate. */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }
    
    /** Creates the Block State Container */
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING, POWERED);
    }
    
    /** Dummyfied Tile Entity Creation */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
}
