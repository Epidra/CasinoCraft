package mod.shared.blocks;

import mod.acecraft.ShopKeeper;
import mod.acecraft.tileentities.TileBlastFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class MachinaFlamer extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OFFSET = BlockStateProperties.ATTACHED;
    public static final IProperty<Boolean> LIT = BlockStateProperties.LIT;



    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public MachinaFlamer(String modid, String name, Block block) {
        super(Properties.from(block));
        this.setRegistryName(modid, name);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OFFSET, Boolean.valueOf(true)).with(LIT, Boolean.valueOf(false)));
    }



    //----------------------------------------FUNCTION----------------------------------------//

    public boolean isFullCube(BlockState state){
        return false;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, OFFSET, LIT);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(true)), 2);
        if(placer.getHorizontalFacing().getOpposite() == Direction.NORTH) {
            if(worldIn.isAirBlock(pos.west())) {
                worldIn.setBlockState(pos.west(), state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
        if(placer.getHorizontalFacing().getOpposite() == Direction.SOUTH) {
            if(worldIn.isAirBlock(pos.east())) {
                worldIn.setBlockState(pos.east(), state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
        if(placer.getHorizontalFacing().getOpposite() == Direction.WEST ) {
            if(worldIn.isAirBlock(pos.south())) {
                worldIn.setBlockState(pos.south(),  state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
        if(placer.getHorizontalFacing().getOpposite() == Direction.EAST ) {
            if(worldIn.isAirBlock(pos.north())) {
                worldIn.setBlockState(pos.north(),  state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)), 2);
            } else {
                worldIn.destroyBlock(pos, true);
            }
        }
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        boolean isPrimary = state.get(OFFSET);
        Direction enumfacing = state.get(FACING);
        if(!isPrimary) enumfacing = enumfacing.getOpposite();
        if(enumfacing == Direction.NORTH) worldIn.destroyBlock(pos.west(),  !isPrimary);
        if(enumfacing == Direction.SOUTH) worldIn.destroyBlock(pos.east(),  !isPrimary);
        if(enumfacing == Direction.EAST ) worldIn.destroyBlock(pos.north(), !isPrimary);
        if(enumfacing == Direction.WEST ) worldIn.destroyBlock(pos.south(), !isPrimary);

        worldIn.removeTileEntity(pos);
    }

    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        boolean isPrimary = state.get(OFFSET);
        if(isPrimary){
            spawnDrops(state, worldIn, pos, te, player, stack);
        }
    }

    @Deprecated
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        boolean isPrimary = state.get(OFFSET);
        Direction enumfacing = state.get(FACING);
        //if(!isPrimary) enumfacing = enumfacing.getOpposite();
        if(!isPrimary) return true;
        if(enumfacing == Direction.NORTH) if(worldIn.getBlockState(pos.west()).getBlock()  != Blocks.AIR) return false;
        if(enumfacing == Direction.SOUTH) if(worldIn.getBlockState(pos.east()).getBlock()  != Blocks.AIR) return false;
        if(enumfacing == Direction.EAST ) if(worldIn.getBlockState(pos.north()).getBlock() != Blocks.AIR) return false;
        if(enumfacing == Direction.WEST ) if(worldIn.getBlockState(pos.south()).getBlock() != Blocks.AIR) return false;
        return true;
    }

    /**
     * Amount of light emitted
     * @deprecated prefer calling {@link //IBlockState#getLightValue()}
     */
    public int getLightValue(BlockState state) {
        return state.get(LIT) ? super.getLightValue(state) : 0;
    }

    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            this.interactWith(worldIn, pos, player);
        }

        return true;
    }

    /**
     * Interface for handling interaction with blocks that impliment AbstractFurnaceBlock. Called in onBlockActivated
     * inside AbstractFurnaceBlock.
     */
    protected abstract void interactWith(World worldIn, BlockPos pos, PlayerEntity player);

}
