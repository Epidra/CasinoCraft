package mod.casinocraft.blocks;

import mod.casinocraft.container.ContainerProvider;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntitySlotMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockSlotMachine extends MachinaDoubleTall {

    private DyeColor color;
    private static final VoxelShape AABB0 = Block.makeCuboidShape(2, 0, 1, 16, 16, 15);
    private static final VoxelShape AABB1 = Block.makeCuboidShape(1, 0, 2, 15, 16, 16);
    private static final VoxelShape AABB2 = Block.makeCuboidShape(0, 0, 1, 14, 16, 15);
    private static final VoxelShape AABB3 = Block.makeCuboidShape(1, 0, 0, 15, 16, 14);




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockSlotMachine(Block block, DyeColor color) {
        super(block);
        this.color = color;
    }




    //----------------------------------------INTERACTION----------------------------------------//

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (world.isRemote) {
            return true;
        } else {
            if (!world.isRemote() && player instanceof ServerPlayerEntity) {
                boolean isPrimary = world.getBlockState(pos).get(OFFSET);
                final BlockPos pos2 = isPrimary ? pos : pos.down();
                Item item = Items.FLINT;
                TileEntityBoard tileEntity = (TileEntityBoard) world.getTileEntity(pos2);
                if (tileEntity instanceof TileEntitySlotMachine) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, new ContainerProvider((TileEntitySlotMachine) tileEntity), buf -> buf.writeBlockPos(pos2));
                }
            }
            return true;
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Deprecated
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction enumfacing = state.get(FACING);
        switch(enumfacing) {
            case NORTH: return AABB1;
            case SOUTH: return AABB3;
            case EAST:  return AABB2;
            case WEST:  return AABB0;
            default:
                return VoxelShapes.fullCube();
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(OFFSET);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return state.get(OFFSET) ? new TileEntitySlotMachine(color, 3) : null;
    }

}
