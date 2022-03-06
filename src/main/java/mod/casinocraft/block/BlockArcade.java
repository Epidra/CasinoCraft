package mod.casinocraft.block;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuProvider;
import mod.casinocraft.blockentity.BlockEntityArcade;
import mod.casinocraft.blockentity.BlockEntityMachine;
import mod.lucky77.block.MachinaTall;
import mod.lucky77.blockentity.BlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockArcade extends MachinaTall implements EntityBlock {

    private static final IntegerProperty MODULE = IntegerProperty.create("module", 0, 17);
    private        final DyeColor color;
    private static final VoxelShape AABB0 = Block.box(2, 0, 1, 16, 16, 15);
    private static final VoxelShape AABB1 = Block.box(1, 0, 2, 16, 16, 15);
    private static final VoxelShape AABB2 = Block.box(0, 0, 1, 14, 16, 15);
    private static final VoxelShape AABB3 = Block.box(1, 0, 0, 15, 16, 14);





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockArcade(Block block, DyeColor color) {
        super(block);
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OFFSET, Boolean.TRUE).setValue(MODULE, 17));
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

    public static void setModuleState(Level world, BlockPos pos){
        BlockState iblockstate = world.getBlockState(pos);
        BlockEntityMachine tileentity = (BlockEntityMachine) world.getBlockEntity(pos);
        if (tileentity != null){
            if(tileentity.getItem(0).isEmpty()){
                world.setBlock(pos,         iblockstate.setValue(                        MODULE, 17), 3);
                world.setBlock(pos.above(), iblockstate.setValue(OFFSET, false).setValue(MODULE, 17), 3);
            }
            else {
                world.setBlock(pos,         iblockstate.setValue(                        MODULE, itemToInt(tileentity.getItem(1).getItem())), 3);
                world.setBlock(pos.above(), iblockstate.setValue(OFFSET, false).setValue(MODULE, itemToInt(tileentity.getItem(1).getItem())), 3);
            }
        }
    }





    //----------------------------------------BLOCKENTITY----------------------------------------//

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(OFFSET) ? new BlockEntityArcade(pos, state, color, 0) : null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(level, type, CasinoKeeper.TILE_ARCADE_BASE.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends BlockEntityArcade> typeCustom) {
        return createTickerHelper(type, typeCustom, BlockEntityArcade::serverTick);
    }





    //----------------------------------------SUPPORT----------------------------------------//

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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OFFSET, MODULE);
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

    private static int itemToInt(Item item){
        if(item == CasinoKeeper.MODULE_CHIP_BLACK.get())      return  0;
        if(item == CasinoKeeper.MODULE_CHIP_RED.get())        return  1;
        if(item == CasinoKeeper.MODULE_CHIP_GREEN.get())      return  2;
        if(item == CasinoKeeper.MODULE_CHIP_BROWN.get())      return  3;
        if(item == CasinoKeeper.MODULE_CHIP_BLUE.get())       return  4;
        if(item == CasinoKeeper.MODULE_CHIP_PURPLE.get())     return  5;
        if(item == CasinoKeeper.MODULE_CHIP_CYAN.get())       return  6;
        if(item == CasinoKeeper.MODULE_CHIP_LIGHT_GRAY.get()) return  7;
        if(item == CasinoKeeper.MODULE_CHIP_GRAY.get())       return  8;
        if(item == CasinoKeeper.MODULE_CHIP_PINK.get())       return  9;
        if(item == CasinoKeeper.MODULE_CHIP_LIME.get())       return 10;
        if(item == CasinoKeeper.MODULE_CHIP_YELLOW.get())     return 11;
        if(item == CasinoKeeper.MODULE_CHIP_LIGHT_BLUE.get()) return 12;
        if(item == CasinoKeeper.MODULE_CHIP_MAGENTA.get())    return 13;
        if(item == CasinoKeeper.MODULE_CHIP_ORANGE.get())     return 14;
        if(item == CasinoKeeper.MODULE_CHIP_WHITE.get())      return 15;
        return 16;
    }



}
