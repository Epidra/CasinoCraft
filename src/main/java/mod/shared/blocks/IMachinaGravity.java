package mod.shared.blocks;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IMachinaGravity extends BlockFalling {
	
	/** ??? */
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	
	
	
	//----------------------------------------CONSTRUCTOR----------------------------------------//
	
	/** Default Constructor */
	public IMachinaGravity(String name, Material material) {
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
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	/** Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    /** Called by ItemBlocks after a block is set in the world, to allow post-place logic */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
    
    /** Start of Falling Event */
	protected void onStartFalling(EntityFallingBlock fallingEntity){
        fallingEntity.setHurtEntities(true);
    }
	
	/** End of Falling Event */
    public void onEndFalling(World worldIn, BlockPos pos){
        worldIn.playEvent(1031, pos, 0);
    }
    
    
    
    
	//----------------------------------------SUPPORT----------------------------------------//
    
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getIndex();
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
        return new BlockStateContainer(this, FACING);
    }
	
}
