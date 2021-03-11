package mod.casinocraft.blocks;

import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class MachinaBasic extends BlockBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public MachinaBasic(Block block) {
        super(block);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }




    //----------------------------------------FUNCTION----------------------------------------//

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        TileEntityMachine tileEntity = (TileEntityMachine) worldIn.getTileEntity(pos);
        float f = state.getBlockHardness(worldIn, pos);
        if(tileEntity.settingIndestructableBlock) f *= 1000;
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
            return player.getDigSpeed(state, pos) / f / (float)i;
        }
    }

    public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion){
        TileEntityMachine tileEntity = (TileEntityMachine) world.getTileEntity(pos);
        return this.getBlock().getExplosionResistance() * (tileEntity.settingIndestructableBlock ? 1000 : 1);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(newState.getBlock() == Blocks.AIR){
            spawnInventory(worldIn, pos, (TileEntityMachine) worldIn.getTileEntity(pos));
            worldIn.destroyBlock(pos,  true);
            worldIn.removeTileEntity(pos);
        }
    }

}
