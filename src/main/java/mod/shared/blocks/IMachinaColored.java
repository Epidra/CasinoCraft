package mod.shared.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class IMachinaColored extends BlockContainer {
	
	/** Enum Value for Coloration */
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
	
	
	
	
	//----------------------------------------CONSTRUCTOR----------------------------------------//
	
	/** Default Constructor */
	public IMachinaColored(String name, Material material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setHardness(2);
		this.setResistance(2);
		this.setSoundType(SoundType.GROUND);
		this.setHarvestLevel("pickaxe", 0);
		this.setTickRandomly(false);
		if(material == Material.ANVIL){ this.setHarvestLevel("pickaxe", 2); this.setSoundType(SoundType.ANVIL); }
		if(material == Material.IRON) { this.setHarvestLevel("pickaxe", 2); this.setSoundType(SoundType.METAL); }
		if(material == Material.ROCK) { this.setHarvestLevel("pickaxe", 1); this.setSoundType(SoundType.STONE); }
		if(material == Material.WOOD) { this.setHarvestLevel("axe",     0); this.setSoundType(SoundType.WOOD);  }
		if(material == Material.CLOTH){ this.setHarvestLevel("axe",     0); this.setSoundType(SoundType.CLOTH); }
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
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
    
    
    
    
    
	//----------------------------------------SUPPORT----------------------------------------//
	
	/** Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata of the block. */
    public int damageDropped(IBlockState state){
        return state.getValue(COLOR).getMetadata();
    }
    
    /** returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks) */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items){
        for (EnumDyeColor enumdyecolor : EnumDyeColor.values()){
            items.add(new ItemStack(this, 1, enumdyecolor.getMetadata()));
        }
    }
    
    /** Get the MapColor for this Block and the given BlockState */
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        return MapColor.getBlockColor(state.getValue(COLOR));
    }
    
    /** Convert the given metadata into a BlockState for this Block */
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }
    
    /** Convert the BlockState into the correct metadata value */
    public int getMetaFromState(IBlockState state){
        return state.getValue(COLOR).getMetadata();
    }
    
    /** Creates the Block State Container */
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, COLOR);
    }
    
    /** Dummyfied Tile Entity Creation */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
}
