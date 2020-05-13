package mod.casinocraft.blocks;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntitySlotMachine;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.List;

public class BlockSlotMachine extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool PRIMARY = PropertyBool.create("primary");

	final EnumDyeColor color;

	public BlockSlotMachine(String name, EnumDyeColor colorIn) {
		super(Material.ANVIL);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(2);
		this.setResistance(2);
		this.setSoundType(SoundType.ANVIL);
		this.setHarvestLevel("pickaxe", 0);
		this.setTickRandomly(false);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PRIMARY, true));
		this.color = colorIn;
	}
	
	/** ??? */
	public boolean isFullCube(IBlockState state){
        return false;
    }
	
	/** ??? */
	public boolean isOpaqueCube(IBlockState state){
        return false;
    }
	
	/** The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to skip all rendering */
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
	
	/** Called by ItemBlocks after a block is set in the world, to allow post-place logic */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	TileEntityBoard te = (TileEntityBoard) worldIn.getTileEntity(pos);
    	worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PRIMARY, Boolean.valueOf(true)));
        if(worldIn.isAirBlock(pos.up())) {
        	worldIn.setBlockState(pos.up(), state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PRIMARY, Boolean.valueOf(false)));
        } else {
     	   worldIn.destroyBlock(pos, true);
        }
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
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if (world.isRemote){
            return true;
        } else {
        	BlockPos pos2 = pos;
        	boolean isPrimary = world.getBlockState(pos).getValue(PRIMARY);
        	Item item = Items.FLINT;
        	if(isPrimary) {
        		//pos2 = pos.up();
        	} else {
        		pos2 = pos.down();
        	}
        	if(world.getTileEntity(pos2) instanceof TileEntityBoard){
        		TileEntityBoard te = (TileEntityBoard) world.getTileEntity(pos2);
        		if(player.isSneaking()) {
                	return true;
                }
				if(te.getStackInSlot(0).isEmpty() || (te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem())){
					player.openGui(CasinoCraft.instance, 50, world, pos2.getX(), pos2.getY(), pos2.getZ());
				} else {
            		player.openGui(CasinoCraft.instance, 51, world, pos2.getX(), pos2.getY(), pos2.getZ());
            	}
    			
    			te.markDirty();
    		}
            return true;
        }
    }
	
	public TileEntity createNewTileEntity(World worldIn, int meta){
        return meta < 8 ? null : new TileEntitySlotMachine(color, 3);
    }
	
	/** ??? */
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING, PRIMARY);
    }
	
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
    	EnumFacing enumfacing = EnumFacing.getFront(meta%8);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(PRIMARY, Boolean.valueOf((meta / 8) > 0));
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
    	int i = state.getValue(FACING).getIndex();
        if (state.getValue(PRIMARY).booleanValue()){
            i += 8;
        }
        return i;
    }
	
}
