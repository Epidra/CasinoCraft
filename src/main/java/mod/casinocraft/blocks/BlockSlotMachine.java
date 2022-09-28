package mod.casinocraft.blocks;

import mod.casinocraft.container.ContainerProvider;
import mod.casinocraft.tileentities.TileEntityMachine;
import mod.casinocraft.tileentities.TileEntitySlotMachine;
import mod.lucky77.blocks.MachinaTall;
import mod.lucky77.tileentities.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockSlotMachine extends MachinaTall {

    private DyeColor color;
    private static final VoxelShape AABB0 = Block.box(6, 0, 2, 16, 16, 14);
    private static final VoxelShape AABB1 = Block.box(2, 0, 6, 14, 16, 16);
    private static final VoxelShape AABB2 = Block.box(0, 0, 2, 10, 16, 14);
    private static final VoxelShape AABB3 = Block.box(2, 0, 0, 14, 16, 10);





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockSlotMachine(Block block, DyeColor color) {
        super(block);
        this.color = color;
    }





    //----------------------------------------PLACEMENT----------------------------------------//

    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        boolean isPrimary = state.getValue(OFFSET);
        TileEntityMachine tile = (TileEntityMachine) world.getBlockEntity(getTilePosition(pos, isPrimary, Direction.DOWN));
        if(tile != null && tile.settingDropItemsOnBreak) {
            tile.setItem(3, new ItemStack(tile.getItem(3).getItem(), tile.storageToken));
            tile.setItem(4, new ItemStack(tile.getItem(4).getItem(), tile.storageToken));
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }





    //----------------------------------------INTERACTION----------------------------------------//

    @Override
    public void interact(World world, BlockPos pos, PlayerEntity player, TileBase tile) {
        NetworkHooks.openGui((ServerPlayerEntity) player, new ContainerProvider((TileEntityMachine)tile), buf -> buf.writeBlockPos(pos));
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Deprecated
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction enumfacing = state.getValue(FACING);
        switch(enumfacing) {
            case NORTH: return AABB1;
            case SOUTH: return AABB3;
            case EAST:  return AABB2;
            case WEST:  return AABB0;
            default:
                return VoxelShapes.block();
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return state.getValue(OFFSET) ? new TileEntitySlotMachine(color, 3) : null;
    }

    public float getDestroyProgress(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        final BlockPos pos2 = getTilePosition(pos, state.getValue(OFFSET), Direction.DOWN);
        TileEntityMachine tileEntity = (TileEntityMachine) worldIn.getBlockEntity(pos2);
        boolean unbreakable = tileEntity.settingIndestructableBlock;
        float f = state.getDestroySpeed(worldIn, pos);
        if(unbreakable) f *= 1000;
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
            return player.getDigSpeed(state, pos) / f / (float)i;
        }
    }

    public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion){
        final BlockPos pos2 = getTilePosition(pos, state.getValue(OFFSET), Direction.DOWN);
        TileEntityMachine tileEntity = (TileEntityMachine) world.getBlockEntity(pos2);
        boolean unbreakable = tileEntity.settingIndestructableBlock;
        return this.getBlock().getExplosionResistance() * (unbreakable ? 1000 : 1);
    }



}
