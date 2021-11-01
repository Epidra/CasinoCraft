package mod.casinocraft.blocks;

import mod.casinocraft.container.ContainerProvider;
import mod.casinocraft.tileentities.TileEntityMachine;
import mod.casinocraft.tileentities.TileEntityCardTableBase;
import mod.lucky77.blocks.MachinaBase;
import mod.lucky77.tileentities.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockCardTableBase extends MachinaBase {

    private        final DyeColor color;
    private static final VoxelShape AABB = Block.box(1, 0, 1, 15, 16, 15);





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockCardTableBase(Block block, DyeColor color) {
        super(block);
        this.color = color;
    }





    //----------------------------------------PLACEMENT----------------------------------------//

    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntityMachine tile = (TileEntityMachine) world.getBlockEntity(pos);
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
        return AABB;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCardTableBase(color, 1);
    }

    public float getDestroyProgress(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        TileEntityMachine tileEntity = (TileEntityMachine) worldIn.getBlockEntity(pos);
        float f = state.getDestroySpeed(worldIn, pos);
        if(tileEntity.settingIndestructableBlock) f *= 1000;
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
            return player.getDigSpeed(state, pos) / f / (float)i;
        }
    }

    public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion){
        TileEntityMachine tileEntity = (TileEntityMachine) world.getBlockEntity(pos);
        return this.getBlock().getExplosionResistance() * (tileEntity.settingIndestructableBlock ? 1000 : 1);
    }



}
