package mod.shared.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public abstract class IMachinaBasic extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /** Contructor with predefined BlockProperty */
    public IMachinaBasic(String modid, String name, Block block) {
        super(Block.Properties.from(block));
        this.setRegistryName(modid, name);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    public IBlockState rotate(IBlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }

    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
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
}
