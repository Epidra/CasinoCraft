package mod.casinocraft.blocks;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerProvider;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BlockArcade extends MachinaDoubleTall {

    private static final IntegerProperty MODULE = IntegerProperty.create("module", 0, 17);
    private DyeColor color;
    private static final VoxelShape AABB0 = Block.makeCuboidShape(2, 0, 1, 16, 16, 15);
    private static final VoxelShape AABB1 = Block.makeCuboidShape(1, 0, 2, 16, 16, 15);
    private static final VoxelShape AABB2 = Block.makeCuboidShape(0, 0, 1, 14, 16, 15);
    private static final VoxelShape AABB3 = Block.makeCuboidShape(1, 0, 0, 15, 16, 14);




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockArcade(Block block, DyeColor color) {
        super(block);
        this.color = color;
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OFFSET, Boolean.TRUE).with(MODULE, 17));
    }




    //----------------------------------------INTERACTION----------------------------------------//

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isRemote() && player instanceof ServerPlayerEntity){
            final BlockPos pos2 = getTilePosition(pos, state.get(OFFSET), Direction.DOWN);
            TileEntityMachine tileEntity = (TileEntityMachine) world.getTileEntity(pos2);
            if (tileEntity instanceof TileEntityArcade) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new ContainerProvider(tileEntity), buf -> buf.writeBlockPos(pos2));
            }

        }
        return ActionResultType.SUCCESS;
    }

    public static void setModuleState(World world, BlockPos pos){
        BlockState iblockstate = world.getBlockState(pos);
        TileEntityMachine tileentity = (TileEntityMachine) world.getTileEntity(pos);
        if (tileentity != null){
            if(tileentity.inventory.get(0).isEmpty()){
                world.setBlockState(pos,      iblockstate.with(                    MODULE, 17), 3);
                world.setBlockState(pos.up(), iblockstate.with(OFFSET, false).with(MODULE, 17), 3);
            }
            else {
                world.setBlockState(pos,      iblockstate.with(                    MODULE, itemToInt(tileentity.inventory.get(1).getItem())), 3);
                world.setBlockState(pos.up(), iblockstate.with(OFFSET, false).with(MODULE, itemToInt(tileentity.inventory.get(1).getItem())), 3);
            }
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

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

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, OFFSET, MODULE);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return state.get(OFFSET) ? new TileEntityArcade(color, 0) : null;
    }




    //----------------------------------------HELPER----------------------------------------//

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
