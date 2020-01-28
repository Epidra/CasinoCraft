package mod.casinocraft.blocks;

import java.util.List;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.network.ServerPowerMessage;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockArcade extends BlockContainer {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool PRIMARY = PropertyBool.create("primary");
	public static final PropertyEnum<EnumModule> MODULE = PropertyEnum.create("module", EnumModule.class);
	
	public BlockArcade(String name) {
		super(Material.ANVIL);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(2);
		this.setResistance(2);
		this.setSoundType(SoundType.ANVIL);
		this.setHarvestLevel("pickaxe", 0);
		this.setTickRandomly(false);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MODULE, EnumModule.EMPTY).withProperty(PRIMARY, true));
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
    	worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PRIMARY, Boolean.valueOf(true)).withProperty(MODULE, te == null ? EnumModule.EMPTY : EnumModule.byItem(te.inventory.get(1).getItem())));
        if(worldIn.isAirBlock(pos.up())) {
        	worldIn.setBlockState(pos.up(), state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PRIMARY, Boolean.valueOf(false)).withProperty(MODULE, te == null ? EnumModule.EMPTY : EnumModule.byItem(te.inventory.get(1).getItem())));
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
                	setPowerState(te.inventory.get(1).getItem(), pos2);
                	//setPowerState(world, pos2, !isPrimary);
                	return true;
                }
				if(te.getStackInSlot(0) == null || (player.getHeldItem(hand) != null && te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem() && te.getStackInSlot(0).getDisplayName().matches(player.getHeldItem(hand).getDisplayName()))){
					player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ARCADE.ordinal(), world, pos2.getX(), pos2.getY(), pos2.getZ());
    				player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
            	} else if(te.inventory.get(1) != null){
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_TETRIS)   player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.TETRIS.ordinal(),    world, pos2.getX(), pos2.getY(), pos2.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_COLUMNS)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.COLUMNS.ordinal(),   world, pos2.getX(), pos2.getY(), pos2.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MEANMINOS)player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MEANMINOS.ordinal(), world, pos2.getX(), pos2.getY(), pos2.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SOKOBAN)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SOKOBAN.ordinal(),   world, pos2.getX(), pos2.getY(), pos2.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SNAKE)    player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SNAKE.ordinal(),     world, pos2.getX(), pos2.getY(), pos2.getZ());
            		if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_2048)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID._2048.ordinal(),     world, pos2.getX(), pos2.getY(), pos2.getZ());
            		player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
            	}
    			
    			te.markDirty();
    		}
            return true;
        }
    }
	
	public TileEntity createNewTileEntity(World worldIn, int meta){
        return meta < 8 ? null : new TileEntityBoard();
    }
	
	/** ??? */
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, FACING, PRIMARY, MODULE);
    }
	
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
    	EnumFacing enumfacing = EnumFacing.getFront(meta%8);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(PRIMARY, Boolean.valueOf((meta / 8) > 0)).withProperty(MODULE, EnumModule.EMPTY);
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
    	int i = state.getValue(FACING).getIndex();
        if (state.getValue(PRIMARY).booleanValue()){
            i += 8;
        }
        return i;
    }
    
    public void setPowerState(Item item, BlockPos pos) {
    	CasinoPacketHandler.INSTANCE.sendToServer(new ServerPowerMessage(EnumModule.byItem(item).meta, pos));
		//CasinoPacketHandler.INSTANCE.sendToAll(new ClientPowerMessage(EnumModule.byItem(item).meta, pos));
    }
    
	/** ??? */
    public static void setPowerState2(World world, BlockPos pos){
        IBlockState iblockstate = world.getBlockState(pos);
        TileEntityBoard tileentity = (TileEntityBoard) world.getTileEntity(pos);
        if (tileentity != null){
        	world.setBlockState(pos, iblockstate.withProperty(MODULE, EnumModule.byItem(tileentity.inventory.get(1).getItem())));
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }
	
	public enum EnumModule implements IStringSerializable{
        EMPTY    (0, "empty"),
        TETRIS   (1, "tetris"),
        COLUMNS  (2, "columns"),
        MEANMINOS(3, "meanminos"),
        SNAKE    (4, "snake"),
        SOKOBAN  (5, "sokoban"),
        _2048    (6, "_2048");
    	
        public final String name;
        public final int meta;

        EnumModule(int meta, String name){
        	this.meta = meta;
            this.name = name;
        }
        
        public int getMetadata(){
            return this.meta;
        }
        
        public String toString(){
            return this.name;
        }
        
        public String getName(){
            return this.name;
        }
        
        public static EnumModule byMetadata(int meta){
        	if(meta == 0) return EnumModule.EMPTY;
        	if(meta == 1) return EnumModule.TETRIS;
        	if(meta == 2) return EnumModule.COLUMNS;
        	if(meta == 3) return EnumModule.MEANMINOS;
        	if(meta == 4) return EnumModule.SNAKE;
        	if(meta == 5) return EnumModule.SOKOBAN;
        	if(meta == 6) return EnumModule._2048;
        	return EnumModule.EMPTY;
        }
        
        public static EnumModule byItem(Item item){
        	if(item == CasinoKeeper.MODULE_TETRIS)    return EnumModule.TETRIS;
        	if(item == CasinoKeeper.MODULE_COLUMNS)   return EnumModule.COLUMNS;
        	if(item == CasinoKeeper.MODULE_MEANMINOS) return EnumModule.MEANMINOS;
        	if(item == CasinoKeeper.MODULE_SNAKE)     return EnumModule.SNAKE;
        	if(item == CasinoKeeper.MODULE_SOKOBAN)   return EnumModule.SOKOBAN;
        	if(item == CasinoKeeper.MODULE_2048)      return EnumModule._2048;
        	return EnumModule.EMPTY;
        }
        
    }
	
}
