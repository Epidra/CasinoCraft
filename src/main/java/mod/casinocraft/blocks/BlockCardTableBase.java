package mod.casinocraft.blocks;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCardTable;
import mod.shared.blocks.IMachinaBasic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockCardTableBase extends IMachinaBasic {


    /** Contructor with predefined BlockProperty */
    public BlockCardTableBase(String modid, String name, Block block) {
        super(modid, name, block);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityCardTable();
    }

    @Deprecated
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote){
            return true;
        } else {
            if(world.getTileEntity(pos) instanceof TileEntityBoard){
                TileEntityBoard te = (TileEntityBoard) world.getTileEntity(pos);

                final BlockPos pos2 = pos;

                if (player instanceof EntityPlayerMP && !(player instanceof FakePlayer)){
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;

                    if(te.getStackInSlot(0).getItem() == Item.getItemFromBlock(Blocks.AIR) || (player.getHeldItem(hand) != null && te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem() && te.getStackInSlot(0).getDisplayName().equals(player.getHeldItem(hand).getDisplayName()))){
                        NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(-1).writeBoolean(false));
                    } else if(te.inventory.get(1) != null){
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ACEYDEUCEY)   NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.ACEYDEUCEY.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_BACCARAT)     NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.BACCARAT.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_BLACKJACK)    NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.BLACKJACK.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CRAPS)        NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.CRAPS.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_FREECELL)     NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.FREECELL.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_HALMA)        NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.HALMA.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_KLONDIKE)     NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.KLONDIKE.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MEMORY)       NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.MEMORY.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINESWEEPER)  NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.MINESWEEPER.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MYSTICSQUARE) NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.MYSTICSQUARE.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_PYRAMID)      NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.PYRAMID.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ROUGEETNOIR)  NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.ROUGEETNOIR.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ROULETTE)     NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.ROULETTE.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SICBO)        NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.SICBO.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SPIDER)       NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.SPIDER.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SUDOKU)       NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.SUDOKU.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_TRIPEAK)      NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.TRIPEAK.ordinal()).writeBoolean(false));
                        if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_VIDEOPOKER)   NetworkHooks.openGui(entityPlayerMP, te, buf -> buf.writeBlockPos(pos2).writeInt(CasinoKeeper.GuiID.VIDEOPOKER.ordinal()).writeBoolean(false));
                    }



                    return  true;
                }


                //if(te.getStackInSlot(0) == null || (player.getHeldItem(hand) != null && te.getStackInSlot(0).getItem() == player.getHeldItem(hand).getItem() && te.getStackInSlot(0).getDisplayName().matches(player.getHeldItem(hand).getDisplayName()))){
                //    player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.CARDTABLE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                //    player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
                //} else if(te.inventory.get(1) != null){
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_BLACKJACK)    player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.BLACKJACK.ordinal(),    world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_BACCARAT)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.BACCARAT.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MEMORY)       player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MEMORY.ordinal(),       world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MYSTICSQUARE) player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MYSTICSQUARE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_PYRAMID)      player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.PYRAMID.ordinal(),      world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_TRIPEAK)      player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.TRIPEAK.ordinal(),      world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ACEYDEUCEY)   player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ACEYDEUCEY.ordinal(),   world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_HALMA)        player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.HALMA.ordinal(),        world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ROUGEETNOIR)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ROUGEETNOIR.ordinal(),  world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SUDOKU)       player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SUDOKU.ordinal(),       world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_ROULETTE)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.ROULETTE.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_CRAPS)        player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.CRAPS.ordinal(),        world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SICBO)        player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SICBO.ordinal(),        world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_KLONDIKE)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.KLONDIKE.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_FREECELL)     player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.FREECELL.ordinal(),     world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_SPIDER)       player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.SPIDER.ordinal(),       world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_VIDEOPOKER)   player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.VIDEOPOKER.ordinal(),   world, pos.getX(), pos.getY(), pos.getZ());
                //    if(te.inventory.get(1).getItem() == CasinoKeeper.MODULE_MINESWEEPER)  player.openGui(CasinoCraft.instance, CasinoKeeper.GuiID.MINESWEEPER.ordinal(),  world, pos.getX(), pos.getY(), pos.getZ());
                //    player.addStat(StatList.CRAFTING_TABLE_INTERACTION);
                //}
                te.markDirty();
            }
            //return true;
        }
        return false;
    }
}
