package mod.casinocraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDice extends BlockBlock {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final IntegerProperty ROTATION = BlockStateProperties.LEVEL_0_3;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Default Constructor */
    public BlockDice(Block block) {
        super(block);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ROTATION, 0));
    }






    //----------------------------------------PLACEMENT----------------------------------------//

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if(context.getPlayer().isSneaking()){
            return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
        }
        return this.getDefaultState().with(FACING, Direction.byIndex(RANDOM.nextInt(4)+2)).with(ROTATION, RANDOM.nextInt(4));
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack){

    }




    //----------------------------------------INTERACTION----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, FACING);
    }

}
