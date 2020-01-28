package mod.casinocraft.blocks;

import mod.casinocraft.container.ContainerProvider;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCardTable;
import mod.shared.blocks.MachinaBasic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockCardTableBase extends MachinaBasic {

    DyeColor color;

    /** Contructor with predefined BlockProperty */
    public BlockCardTableBase(String modid, String name, Block block, DyeColor color) {
        super(modid, name, block);
        this.color = color;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCardTable(color);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (world.isRemote) {
            return true;
        } else {
            if (!world.isRemote() && player instanceof ServerPlayerEntity) {
                //boolean isPrimary = world.getBlockState(pos).get(OFFSET);
                //final BlockPos pos2 = isPrimary ? pos : pos.down();
                Item item = Items.FLINT;
                TileEntityBoard tileEntity = (TileEntityBoard) world.getTileEntity(pos);
                if (tileEntity instanceof TileEntityCardTable) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, new ContainerProvider((TileEntityCardTable) tileEntity), buf -> buf.writeBlockPos(pos));
                }
            }
            return true;
        }
    }

}
