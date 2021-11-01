package mod.casinocraft.blocks;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerProvider;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityMachine;
import mod.lucky77.blocks.MachinaTall;
import mod.lucky77.tileentities.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
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

public class BlockArcade extends MachinaTall {

    private static final IntegerProperty MODULE = IntegerProperty.create("module", 0, 17);
    private        final DyeColor color;
    private static final VoxelShape AABB_W = Block.box(2, 0, 1, 16, 16, 15);
    private static final VoxelShape AABB_N = Block.box(1, 0, 2, 16, 16, 15);
    private static final VoxelShape AABB_E = Block.box(0, 0, 1, 14, 16, 15);
    private static final VoxelShape AABB_S = Block.box(1, 0, 0, 15, 16, 14);





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockArcade(Block block, DyeColor color) {
        super(block);
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OFFSET, Boolean.TRUE).setValue(MODULE, 17));
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

    public static void setModuleState(World world, BlockPos pos){
        BlockState iblockstate = world.getBlockState(pos);
        TileEntityMachine tileentity = (TileEntityMachine) world.getBlockEntity(pos);
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





    //----------------------------------------SUPPORT----------------------------------------//

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction enumfacing = state.getValue(FACING);
        switch(enumfacing) {
            case NORTH: return AABB_N;
            case SOUTH: return AABB_S;
            case EAST:  return AABB_E;
            case WEST:  return AABB_W;
            default:
                return VoxelShapes.block();
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, OFFSET, MODULE);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return state.getValue(OFFSET) ? new TileEntityArcade(color, 0) : null;
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
