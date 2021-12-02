package mod.casinocraft.block;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuProvider;
import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.casinocraft.blockentity.BlockEntitySlotMachine;
import mod.lucky77.block.MachinaTall;
import mod.lucky77.blockentity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockSlotMachine extends MachinaTall implements EntityBlock {

    private DyeColor color;
    private static final VoxelShape AABB0 = Block.box(2, 0, 1, 16, 16, 15);
    private static final VoxelShape AABB1 = Block.box(1, 0, 2, 15, 16, 16);
    private static final VoxelShape AABB2 = Block.box(0, 0, 1, 14, 16, 15);
    private static final VoxelShape AABB3 = Block.box(1, 0, 0, 15, 16, 14);





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockSlotMachine(Block block, DyeColor color) {
        super(block);
        this.color = color;
    }





    //----------------------------------------PLACEMENT----------------------------------------//

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        boolean isPrimary = state.getValue(OFFSET);
        BlockEntityMachine tile = (BlockEntityMachine) world.getBlockEntity(getTilePosition(pos, isPrimary, Direction.DOWN));
        if(tile != null && tile.settingDropItemsOnBreak) {
            tile.setItem(3, new ItemStack(tile.getItem(3).getItem(), tile.storageToken));
            tile.setItem(4, new ItemStack(tile.getItem(4).getItem(), tile.storageToken));
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }





    //----------------------------------------INTERACTION----------------------------------------//

    @Override
    public void interact(Level world, BlockPos pos, Player player, BlockEntityBase tile) {
        NetworkHooks.openGui((ServerPlayer) player, new MenuProvider((BlockEntityMachine)tile), buf -> buf.writeBlockPos(pos));
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Deprecated
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        Direction enumfacing = state.getValue(FACING);
        switch(enumfacing) {
            case NORTH: return AABB1;
            case SOUTH: return AABB3;
            case EAST:  return AABB2;
            case WEST:  return AABB0;
            default:
                return Shapes.block();
        }
    }

    public float getDestroyProgress(BlockState state, Player player, BlockGetter worldIn, BlockPos pos) {
        final BlockPos pos2 = getTilePosition(pos, state.getValue(OFFSET), Direction.DOWN);
        BlockEntityMachine tileEntity = (BlockEntityMachine) worldIn.getBlockEntity(pos2);
        boolean unbreakable = tileEntity.settingIndestructableBlock;
        float f = state.getDestroySpeed(worldIn, pos);
        if(unbreakable) f *= 1000;
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.isCorrectToolForDrops(state, player) ? 30 : 100;
            return player.getDigSpeed(state, pos) / f / (float)i;
        }
    }

    public float getExplosionResistance(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion){
        final BlockPos pos2 = getTilePosition(pos, state.getValue(OFFSET), Direction.DOWN);
        BlockEntityMachine tileEntity = (BlockEntityMachine) world.getBlockEntity(pos2);
        boolean unbreakable = tileEntity.settingIndestructableBlock;
        return this.asBlock().getExplosionResistance() * (unbreakable ? 1000 : 1);
    }





    //----------------------------------------BLOCKENTITY----------------------------------------//

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(OFFSET) ? new BlockEntitySlotMachine(pos, state) : null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(level, type, CasinoKeeper.TILE_ARCADE_SLOT.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends BlockEntitySlotMachine> typeCustom) {
        return createTickerHelper(type, typeCustom, BlockEntitySlotMachine::serverTick);
    }



}
