package mod.casinocraft.blocks;

import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class MachinaDoubleTall extends BlockBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OFFSET = BlockStateProperties.ATTACHED;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public MachinaDoubleTall(Block block) {
        super(block);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OFFSET, Boolean.valueOf(true)));
    }




    //----------------------------------------PLACEMENT----------------------------------------//

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /** Called by ItemBlocks after a block is set in the world, to allow post-place logic */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(true)));
        if(worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), state.with(FACING, placer.getHorizontalFacing().getOpposite()).with(OFFSET, Boolean.valueOf(false)));
        } else {
            worldIn.destroyBlock(pos, true);
        }
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(newState.getBlock() == Blocks.AIR){
            boolean isPrimary = state.get(OFFSET);
            final BlockPos pos2 = getTilePosition(pos, isPrimary, Direction.DOWN);
            spawnInventory(worldIn, pos2, (TileEntityMachine) worldIn.getTileEntity(pos));
            if(isPrimary) {
                worldIn.destroyBlock(pos.up(),  false);
            } else {
                worldIn.destroyBlock(pos.down(),  false);
            }
            worldIn.removeTileEntity(pos);
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(OFFSET);
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
        builder.add(FACING, OFFSET);
    }

    @Deprecated
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        boolean isPrimary = state.get(OFFSET);
        if(isPrimary) {
            Block block = worldIn.getBlockState(pos.up()).getBlock();
            return block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR;
        }
        return true;
    }

    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        final BlockPos pos2 = getTilePosition(pos, state.get(OFFSET), Direction.DOWN);
        TileEntityMachine tileEntity = (TileEntityMachine) worldIn.getTileEntity(pos2);
        boolean unbreakable = tileEntity.settingIndestructableBlock;
        float f = state.getBlockHardness(worldIn, pos);
        if(unbreakable) f *= 1000;
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
            return player.getDigSpeed(state, pos) / f / (float)i;
        }
    }

    public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion){
        final BlockPos pos2 = getTilePosition(pos, state.get(OFFSET), Direction.DOWN);
        TileEntityMachine tileEntity = (TileEntityMachine) world.getTileEntity(pos2);
        boolean unbreakable = tileEntity.settingIndestructableBlock;
        return this.getBlock().getExplosionResistance() * (unbreakable ? 1000 : 1);
    }

}
