package mod.shared.blocks;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class IMachinaDoubleWide extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OFFSET = BlockStateProperties.ATTACHED;

    /** Contructor with predefined BlockProperty */
    public IMachinaDoubleWide(String modid, String name, Block block) {
        super(Block.Properties.from(block));
        this.setRegistryName(modid, name);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(OFFSET, Boolean.valueOf(true)));
    }

    public boolean isFullCube(IBlockState state){
        return false;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public IBlockState rotate(IBlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING, OFFSET);
    }

    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(true)), 2);
        if(placer.getHorizontalFacing().getOpposite() == EnumFacing.NORTH) {
            if(worldIn.isAirBlock(pos.west())) {
                worldIn.setBlockState(pos.west(), state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
        if(placer.getHorizontalFacing().getOpposite() == EnumFacing.SOUTH) {
            if(worldIn.isAirBlock(pos.east())) {
                worldIn.setBlockState(pos.east(), state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
        if(placer.getHorizontalFacing().getOpposite() == EnumFacing.WEST ) {
            if(worldIn.isAirBlock(pos.south())) {
                worldIn.setBlockState(pos.south(),  state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
        if(placer.getHorizontalFacing().getOpposite() == EnumFacing.EAST ) {
            if(worldIn.isAirBlock(pos.north())) {
                worldIn.setBlockState(pos.north(),  state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
    }

    public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving) {
        boolean isPrimary = state.get(OFFSET);
        EnumFacing enumfacing = state.get(FACING);
        if(!isPrimary) enumfacing = enumfacing.getOpposite();
        if(enumfacing == EnumFacing.NORTH) worldIn.destroyBlock(pos.west(),  !isPrimary);
        if(enumfacing == EnumFacing.SOUTH) worldIn.destroyBlock(pos.east(),  !isPrimary);
        if(enumfacing == EnumFacing.EAST ) worldIn.destroyBlock(pos.north(), !isPrimary);
        if(enumfacing == EnumFacing.WEST ) worldIn.destroyBlock(pos.south(), !isPrimary);

        worldIn.removeTileEntity(pos);
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(StatList.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        if (worldIn.isRemote) {
            LOGGER.debug("Never going to hit this!");
            return;
        }
        if(player.isCreative()){
            return;
        }

        boolean isPrimary = state.get(OFFSET);
        if(isPrimary) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Item item = this.getItemDropped(state, worldIn, pos, i).asItem();

            ItemStack itemstack = new ItemStack(item, this.quantityDropped(state, worldIn.rand));
            itemstack.setDisplayName(((INameable)te).getCustomName());
            spawnAsEntity(worldIn, pos, itemstack);
        }
    }
}