package mod.casinocraft.block;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuProvider;
import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.casinocraft.blockentity.BlockEntityCardTableWide;
import mod.lucky77.block.MachinaWide;
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
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockCardTableWide extends MachinaWide implements EntityBlock {

    private        final DyeColor color;
    private static final VoxelShape AABB0 = Block.box(1, 0, 1, 15, 16, 16);
    private static final VoxelShape AABB1 = Block.box(0, 0, 1, 15, 16, 15);
    private static final VoxelShape AABB2 = Block.box(1, 0, 0, 15, 16, 15);
    private static final VoxelShape AABB3 = Block.box(1, 0, 1, 16, 16, 15);





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockCardTableWide(Block block, DyeColor color) {
        super(block);
        this.color = color;
    }





    //----------------------------------------PLACEMENT----------------------------------------//

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        boolean isPrimary = state.getValue(OFFSET);
        BlockEntityMachine tile = (BlockEntityMachine) world.getBlockEntity(getTilePosition(pos, isPrimary, state.getValue(FACING)));
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





    //----------------------------------------BLOCKENTITY----------------------------------------//

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(OFFSET) ? new BlockEntityCardTableWide(pos, state) : null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(level, type, CasinoKeeper.TILE_CARDTABLE_WIDE.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends BlockEntityCardTableWide> typeCustom) {
        return createTickerHelper(type, typeCustom, BlockEntityCardTableWide::serverTick);
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Deprecated
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        Direction enumfacing = state.getValue(FACING);
        boolean offset = state.getValue(OFFSET);
        switch(enumfacing) {
            case NORTH: return offset ? AABB1 : AABB3;
            case SOUTH: return offset ? AABB3 : AABB1;
            case EAST:  return offset ? AABB2 : AABB0;
            case WEST:  return offset ? AABB0 : AABB2;
            default:
                return Shapes.block();
        }
    }

    public float getDestroyProgress(BlockState state, Player player, BlockGetter worldIn, BlockPos pos) {
        final BlockPos pos2 = getTilePosition(pos, state.getValue(OFFSET), state.getValue(FACING));
        BlockEntityMachine tileEntity = (BlockEntityMachine) worldIn.getBlockEntity(pos2);
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

    public float getExplosionResistance(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion){
        final BlockPos pos2 = getTilePosition(pos, state.getValue(OFFSET), state.getValue(FACING));
        BlockEntityMachine tileEntity = (BlockEntityMachine) world.getBlockEntity(pos2);
        boolean unbreakable = tileEntity.settingIndestructableBlock;
        return this.asBlock().getExplosionResistance() * (unbreakable ? 1000 : 1);
    }



}
