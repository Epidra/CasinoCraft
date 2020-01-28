package mod.shared.blocks;

import java.util.List;

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
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class IMachinaDoubleTall extends BlockContainer {
	
	/** Enum Value for horizontal placement */
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool PRIMARY = PropertyBool.create("primary");
	
	
	
	
	//----------------------------------------CONSTRUCTOR----------------------------------------//
	
	/** Default Constructor */
	public IMachinaDoubleTall(String name, Material material) {
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
		if(material == Material.CLOTH){ this.setHarvestLevel("axe",     0); this.setSoundType(SoundType.CLOTH); }
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
   
   /** Called by ItemBlocks after a block is set in the world, to allow post-place logic */
   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
       worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PRIMARY, Boolean.valueOf(true)), 2);
       if(worldIn.isAirBlock(pos.up())) {
    	   worldIn.setBlockState(pos.up(), state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PRIMARY, Boolean.valueOf(false)), 2);
       } else {
    	   worldIn.destroyBlock(pos, true);
       }
   }
   
   /** Gets the {@link IBlockState} to place */
   public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
       return getDefaultState().withProperty(PRIMARY, true);
   }
   
   /** Spawns this Block's drops into the World as EntityItems. */
   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune){
	   boolean isPrimary = state.getValue(PRIMARY);
	   if(isPrimary) {
		   if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots){ // do not drop items while restoring blockstates, prevents item dupe
	           List<ItemStack> drops = getDrops(worldIn, pos, state, fortune); // use the old method until it gets removed, for backward compatibility
	           chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());
	           for (ItemStack drop : drops){
	               if (worldIn.rand.nextFloat() <= chance){
	                   spawnAsEntity(worldIn, pos, drop);
	               }
	           }
	       }
	   }
   }
   
   /** Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect this block */
   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player){
	   boolean isPrimary = state.getValue(PRIMARY);
	   if(isPrimary) {
		   worldIn.destroyBlock(pos.up(),  true);
	   } else {
		   worldIn.destroyBlock(pos.down(),  true);
	   }
   }
   
   /** Called when the block is destroyed by an explosion. */
   public void onBlockExploded(World world, BlockPos pos, Explosion explosion){
	   boolean isPrimary = world.getBlockState(pos).getValue(PRIMARY);
	   if(isPrimary) {
		   world.destroyBlock(pos.up(),  true);
	   } else {
		   world.destroyBlock(pos.down(),  true);
	   }
	   world.setBlockToAir(pos);
       onBlockDestroyedByExplosion(world, pos, explosion);
   }
   
   
   
   
   //----------------------------------------SUPPORT----------------------------------------//
    
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(PRIMARY, Boolean.valueOf((meta & 8) > 0));
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
    	int i = state.getValue(FACING).getIndex();
        if (state.getValue(PRIMARY).booleanValue()){
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
        return new BlockStateContainer(this, FACING, PRIMARY);
    }
    
    /** Dummyfied Tile Entity Creation */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
}
