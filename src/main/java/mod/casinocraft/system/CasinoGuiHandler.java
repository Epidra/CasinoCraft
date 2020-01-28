package mod.casinocraft.system;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.gui.GuiArcade;
import mod.casinocraft.gui.GuiCardTable;
import mod.casinocraft.gui.arcade.*;
import mod.casinocraft.gui.cardtable.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class CasinoGuiHandler {

    public static GuiScreen openGui(FMLPlayMessages.OpenContainer openContainer){
        //BlockPos pos = openContainer.getAdditionalData().readBlockPos();
        //ResourceLocation value = openContainer.getId();
//
        //String s = value.getPath();
//
        //for (CasinoKeeper.GuiID type : CasinoKeeper.GuiID.values()){
        //    if (type.equals(openContainer.getId())){
        //        return new GuiArcade(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos));
        //    }
        //}
        //return null;
        BlockPos pos = openContainer.getAdditionalData().readBlockPos();
        int i = openContainer.getAdditionalData().readInt();
        boolean b = openContainer.getAdditionalData().readBoolean();

        if(i == -1){
            if(openContainer.getId().equals(CasinoKeeper.GUIID_ARCADE)) {
                return new GuiArcade(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos));
            }
            if(openContainer.getId().equals(CasinoKeeper.GUIID_CARDTABLE)) {
                return new GuiCardTable(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos));
            }
        } else {
            if(i == CasinoKeeper.GuiID._2048.ordinal()) { return new Gui2048(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), 0); }
            if(i == CasinoKeeper.GuiID.ACEYDEUCEY.ordinal()) { return new GuiAceyDeucey(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.BACCARAT.ordinal()) { return new GuiBaccarat(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.BLACKJACK.ordinal()) { return new GuiBlackJack(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.COLUMNS.ordinal()) { return new GuiColumns(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), 0); }
            if(i == CasinoKeeper.GuiID.CRAPS.ordinal()) { return new GuiCraps(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.FREECELL.ordinal()) { return new GuiFreeCell(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.HALMA.ordinal()) { return new GuiHalma(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.KLONDIKE.ordinal()) { return new GuiKlondike(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.MEANMINOS.ordinal()) { return new GuiMeanMinos(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), 0); }
            if(i == CasinoKeeper.GuiID.MEMORY.ordinal()) { return new GuiMemory(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.MINESWEEPER.ordinal()) { return new GuiMinesweeper(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.MYSTICSQUARE.ordinal()) { return new GuiMysticSquare(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.PYRAMID.ordinal()) { return new GuiPyramid(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.ROUGEETNOIR.ordinal()) { return new GuiRougeEtNoir(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.ROULETTE.ordinal()) { return new GuiRoulette(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.SICBO.ordinal()) { return new GuiSicBo(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.SNAKE.ordinal()) { return new GuiSnake(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), 0); }
            if(i == CasinoKeeper.GuiID.SOKOBAN.ordinal()) { return new GuiSokoban(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), 0); }
            if(i == CasinoKeeper.GuiID.SPIDER.ordinal()) { return new GuiSpider(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.SUDOKU.ordinal()) { return new GuiSudoku(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.TETRIS.ordinal()) { return new GuiTetris(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), 0); }
            if(i == CasinoKeeper.GuiID.TRIPEAK.ordinal()) { return new GuiTriPeak(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
            if(i == CasinoKeeper.GuiID.VIDEOPOKER.ordinal()) { return new GuiVideoPoker(Minecraft.getInstance().player.inventory, (IInventory) Minecraft.getInstance().world.getTileEntity(pos), b ? 2 : 1); }
        }



        return null;

    }

}
