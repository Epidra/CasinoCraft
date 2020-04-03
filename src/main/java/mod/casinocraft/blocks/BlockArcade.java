package mod.casinocraft.blocks;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerProvider;
import mod.casinocraft.network.ServerPowerMessage;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.blocks.MachinaDoubleTall;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockArcade extends MachinaDoubleTall {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_15;

    private DyeColor color;

    public static final VoxelShape AABB0 = Block.makeCuboidShape(2, 0, 0, 16, 16, 16);
    public static final VoxelShape AABB1 = Block.makeCuboidShape(0, 0, 2, 16, 16, 16);
    public static final VoxelShape AABB2 = Block.makeCuboidShape(0, 0, 0, 14, 16, 16);
    public static final VoxelShape AABB3 = Block.makeCuboidShape(0, 0, 0, 16, 16, 14);

    /** Contructor with predefined BlockProperty */
    public BlockArcade(String modid, String name, Block block, DyeColor color) {
        super(modid, name, block);
        this.color = color;
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OFFSET, Boolean.valueOf(true)).with(LEVEL, Integer.valueOf(0)));
    }

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

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, OFFSET, LEVEL);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        if(state.get(OFFSET)){
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        if(state.get(OFFSET)){
            return new TileEntityArcade(color, 0);
        }
        return null;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (world.isRemote) {
            return ActionResultType.PASS;
        } else {
            if (!world.isRemote() && player instanceof ServerPlayerEntity) {
                boolean isPrimary = world.getBlockState(pos).get(OFFSET);
                final BlockPos pos2 = isPrimary ? pos : pos.down();
                Item item = Items.FLINT;
                TileEntityBoard tileEntity = (TileEntityBoard) world.getTileEntity(pos2);
                if (tileEntity instanceof TileEntityArcade) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, new ContainerProvider((TileEntityArcade) tileEntity), buf -> buf.writeBlockPos(pos2));
                }
            }
            return ActionResultType.SUCCESS;
        }
    }


    public void setPowerState(Item item, BlockPos pos) {
        CasinoPacketHandler.sendToServer(new ServerPowerMessage(new ItemStack(item), pos));
        //CasinoPacketHandler.INSTANCE.sendToAll(new PacketClientPowerMessage(EnumModule.byItem(item).meta, pos));
    }

    /** ??? */
    public static void setPowerState2(World world, BlockPos pos){
        BlockState iblockstate = world.getBlockState(pos);
        TileEntityBoard tileentity = (TileEntityBoard) world.getTileEntity(pos);
        int level = iblockstate.get(LEVEL);
        if (tileentity != null){

            if(level != itemToInt(tileentity.inventory.get(1).getItem())){
                //world.notifyBlockUpdate(pos, iblockstate, iblockstate.with(LEVEL, itemToInt(tileentity.inventory.get(1).getItem())), 3);
                world.destroyBlock(pos, false);
                world.destroyBlock(pos.up(), false);
                ///world.removeBlock(pos.up());
                world.setBlockState(pos,      iblockstate.with(                    LEVEL, itemToInt(tileentity.inventory.get(1).getItem())), 3);
                world.setBlockState(pos.up(), iblockstate.with(OFFSET, false).with(LEVEL, itemToInt(tileentity.inventory.get(1).getItem())), 3);
                tileentity.validate();
                world.setTileEntity(pos, tileentity);
            }


        }
    }

    public static int itemToInt(Item item){
        if(item == CasinoKeeper.MODULE_DUST_WHITE)      return 1; // 2048
        if(item == CasinoKeeper.MODULE_DUST_MAGENTA)    return 2; // SOKOBAN
        if(item == CasinoKeeper.MODULE_DUST_LIGHTBLUE)  return 3; // MEANMINOS
        if(item == CasinoKeeper.MODULE_DUST_CYAN)       return 4; // COLUMNS
        if(item == CasinoKeeper.MODULE_DUST_BLUE)       return 5; // TETRIS
        if(item == CasinoKeeper.MODULE_DUST_RED)        return 6; // SNAKE
        return 0;
    }

    public enum EnumModule implements IStringSerializable {
        EMPTY    (0, "empty"),
        TETRIS   (1, "tetris"),
        COLUMNS  (2, "columns"),
        MEANMINOS(3, "meanminos"),
        SNAKE    (4, "snake"),
        SOKOBAN  (5, "sokoban"),
        _2048    (6, "_2048");

        public final String name;
        public final int meta;

        EnumModule(int meta, String name){
            this.meta = meta;
            this.name = name;
        }

        public int getMetadata(){
            return this.meta;
        }

        public String toString(){
            return this.name;
        }

        public String getName(){
            return this.name;
        }

        public static EnumModule byMetadata(int meta){
            if(meta == 0) return EnumModule.EMPTY;
            if(meta == 1) return EnumModule.TETRIS;
            if(meta == 2) return EnumModule.COLUMNS;
            if(meta == 3) return EnumModule.MEANMINOS;
            if(meta == 4) return EnumModule.SNAKE;
            if(meta == 5) return EnumModule.SOKOBAN;
            if(meta == 6) return EnumModule._2048;
            return EnumModule.EMPTY;
        }

        public static EnumModule byItem(Item item){
           if(item == CasinoKeeper.MODULE_DUST_BLACK)  return EnumModule.TETRIS;
           if(item == CasinoKeeper.MODULE_DUST_WHITE)  return EnumModule.COLUMNS;
           if(item == CasinoKeeper.MODULE_DUST_GREEN)  return EnumModule.MEANMINOS;
           if(item == CasinoKeeper.MODULE_DUST_ORANGE) return EnumModule.SNAKE;
           if(item == CasinoKeeper.MODULE_DUST_BROWN)  return EnumModule.SOKOBAN;
           if(item == CasinoKeeper.MODULE_DUST_YELLOW) return EnumModule._2048;
            return EnumModule.EMPTY;
        }

    }


}
