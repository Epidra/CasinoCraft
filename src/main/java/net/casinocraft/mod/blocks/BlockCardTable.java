package net.casinocraft.mod.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.CasinoKeeper;
import net.casinocraft.mod.tileentity.TileEntityCardTable;
import net.casinocraft.mod.tileentity.TileEntityCasino;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCardTable extends BlockContainer {
	
    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor>create("color", EnumDyeColor.class);
    
    public BlockCardTable(String name){
        super(Material.WOOD);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setHardness(3);
		this.setResistance(3);
		this.setSoundType(SoundType.WOOD);
		this.setLightOpacity(16);
		this.setLightLevel(0);
		this.setHarvestLevel("axe", 0);
		this.setTickRandomly(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
    }
    
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	if(world.getTileEntity(pos) instanceof TileEntityCardTable){
    		TileEntityCardTable te = (TileEntityCardTable) world.getTileEntity(pos);
			te.Set_Owner(placer.getUniqueID());
		}
    }
    
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if (world.isRemote){
            return true;
        } else {
        	if(world.getTileEntity(pos) instanceof TileEntityCasino){
        		TileEntityCasino te = (TileEntityCasino) world.getTileEntity(pos);
        		
    			if(player.isSneaking()){
    				//if(te.Get_Owner() == player.getUniqueID()){
        				player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDCardTable, world, pos.getX(), pos.getY(), pos.getZ());
        				player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        			//}
            	} else if(te.arcadeItemStacks[0] != null){
            		if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleBlackJack)   player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDBlackJack,   world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleBaccarat)    player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDBaccarat,    world, pos.getX(), pos.getY(), pos.getZ());
            	  //if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleVideoPoker)  player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDVideoPoker,  world, pos.getX(), pos.getY(), pos.getZ());
            	  //if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleTetris)      player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDTetris,      world, pos.getX(), pos.getY(), pos.getZ());
            		if(te.arcadeItemStacks[0].getItem() == CasinoKeeper.moduleMemory)      player.openGui(CasinoCraft.instance, CasinoKeeper.guiIDMemory,      world, pos.getX(), pos.getY(), pos.getZ());
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
    
    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state){
        return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
    }
    
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list){
    	for (EnumDyeColor enumdyecolor : EnumDyeColor.values()){
            list.add(new ItemStack(itemIn, 1, enumdyecolor.getMetadata()));
        }
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
    
    /** Get the MapColor for this Block and the given BlockState */
    public MapColor getMapColor(IBlockState state){
        return ((EnumDyeColor)state.getValue(COLOR)).getMapColor();
    }
    
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
        return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
    }
    
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {COLOR});
    }
}