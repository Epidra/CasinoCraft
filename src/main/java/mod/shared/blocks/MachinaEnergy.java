package mod.shared.blocks;

public class MachinaEnergy {

}

//public class IMachinaEnergy extends BlockContainer {
//
//    /** Enum Value for horizontal placement */
//    public static final PropertyDirection FACING = PropertyDirection.create("facing");
//
//
//
//
//    //----------------------------------------CONSTRUCTOR----------------------------------------//
//
//    /** Default Constructor */
//    public IMachinaEnergy(String name, Material material) {
//        super(material);
//        this.setUnlocalizedName(name);
//        this.setRegistryName(name);
//        this.setCreativeTab(CreativeTabs.REDSTONE);
//        this.setHardness(2);
//        this.setResistance(2);
//        this.setSoundType(SoundType.GROUND);
//        this.setHarvestLevel("pickaxe", 0);
//        this.setTickRandomly(false);
//        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
//        if(material == Material.ANVIL){ this.setHarvestLevel("pickaxe", 2); this.setSoundType(SoundType.ANVIL); }
//        if(material == Material.IRON) { this.setHarvestLevel("pickaxe", 2); this.setSoundType(SoundType.METAL); }
//        if(material == Material.ROCK) { this.setHarvestLevel("pickaxe", 1); this.setSoundType(SoundType.STONE); }
//        if(material == Material.WOOD) { this.setHarvestLevel("axe",     0); this.setSoundType(SoundType.WOOD);  }
//    }
//
//
//
//
//    //----------------------------------------RENDER----------------------------------------//
//
//    /** Returns FALSE, the Block is not a full Cube */
//    public boolean isFullCube(IBlockState state){
//        return false;
//    }
//
//    /** Returns FALSE, the Block is not a full AND solid Block */
//    public boolean isOpaqueCube(IBlockState state){
//        return false;
//    }
//
//    /** The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to skip all rendering */
//    public EnumBlockRenderType getRenderType(IBlockState state){
//        return EnumBlockRenderType.MODEL;
//    }
//
//    /** Returns the Light Value */
//    public int getLightValue(IBlockState state){
//        return 0;
//    }
//
//
//
//
//    //----------------------------------------PLACEMENT----------------------------------------//
//
//    /** Called after the block is set in the Chunk data, but before the Tile Entity is set */
//    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
//        if (!worldIn.isRemote){
//            EnumFacing enumfacing = state.getValue(FACING);
//            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
//        }
//    }
//
//    /** Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate */
//    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
//        return this.getDefaultState().withProperty(FACING, getFacingFromEntity(pos, placer));
//    }
//
//    /** Called by ItemBlocks after a block is set in the world, to allow post-place logic */
//    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
//        worldIn.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
//    }
//
//    /** Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated */
//    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
//        super.breakBlock(worldIn, pos, state);
//        worldIn.removeTileEntity(pos);
//    }
//
//
//
//
//    //----------------------------------------SUPPORT----------------------------------------//
//
//    /** Convert the given metadata into a BlockState for this Block */
//    public IBlockState getStateFromMeta(int meta){
//        EnumFacing enumfacing = EnumFacing.getFront(meta);
//        return this.getDefaultState().withProperty(FACING, enumfacing);
//    }
//
//    /** Convert the BlockState into the correct metadata value */
//    public int getMetaFromState(IBlockState state){
//        return state.getValue(FACING).getIndex();
//    }
//
//    /** Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate. */
//    public IBlockState withRotation(IBlockState state, Rotation rot){
//        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
//    }
//
//    /** Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate. */
//    public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
//        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
//    }
//
//    /** Return cubic facing from given Entity */
//    public static EnumFacing getFacingFromEntity(BlockPos pos, EntityLivingBase placer){
//        if (MathHelper.abs((float)placer.posX - (float)pos.getX()) < 2.0F && MathHelper.abs((float)placer.posZ - (float)pos.getZ()) < 2.0F){
//            double d0 = placer.posY + (double)placer.getEyeHeight();
//            if (d0 - (double)pos.getY() > 2.0D){
//                return EnumFacing.DOWN;
//            }
//            if ((double)pos.getY() - d0 > 0.0D){
//                return EnumFacing.UP;
//            }
//        }
//        return placer.getHorizontalFacing();
//    }
//
//    /** Creates the Block State Container */
//    protected BlockStateContainer createBlockState(){
//        return new BlockStateContainer(this, FACING);
//    }
//
//    /** Creates the Tile Entity */
//    public TileEntity createNewTileEntity(World worldIn, int meta){
//        return new ITileEntityEnergy();
//    }
//
//}
