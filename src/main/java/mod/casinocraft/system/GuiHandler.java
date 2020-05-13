package mod.casinocraft.system;

import mod.casinocraft.container.blocks.ContainerArcade;
import mod.casinocraft.container.blocks.ContainerCardTable;
import mod.casinocraft.container.blocks.ContainerSlotMachine;
import mod.casinocraft.container.card.*;
import mod.casinocraft.container.chip.*;
import mod.casinocraft.container.mino.*;
import mod.casinocraft.container.other.ContainerDummy;
import mod.casinocraft.container.other.ContainerSlotGame;
import mod.casinocraft.gui.blocks.GuiArcade;
import mod.casinocraft.gui.blocks.GuiCardTable;
import mod.casinocraft.gui.blocks.GuiSlotMachine;
import mod.casinocraft.gui.card.*;
import mod.casinocraft.gui.chip.*;
import mod.casinocraft.gui.mino.*;
import mod.casinocraft.gui.other.GuiDummy;
import mod.casinocraft.gui.other.GuiSlotGame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te != null){
        	
        	if(ID ==  0) return new ContainerChipWhite(    player.inventory, (IInventory)te, pos, world);
			if(ID ==  1) return new ContainerChipOrange(   player.inventory, (IInventory)te, pos, world);
			if(ID ==  2) return new ContainerChipMagenta(  player.inventory, (IInventory)te, pos, world);
			if(ID ==  3) return new ContainerChipLightBlue(player.inventory, (IInventory)te, pos, world);
			if(ID ==  4) return new ContainerChipYellow(   player.inventory, (IInventory)te, pos, world);
			if(ID ==  5) return new ContainerChipLime(     player.inventory, (IInventory)te, pos, world);
			if(ID ==  6) return new ContainerChipPink(     player.inventory, (IInventory)te, pos, world);
			if(ID ==  7) return new ContainerChipGray(     player.inventory, (IInventory)te, pos, world);
			if(ID ==  8) return new ContainerChipLightGray(player.inventory, (IInventory)te, pos, world);
			if(ID ==  9) return new ContainerChipCyan(     player.inventory, (IInventory)te, pos, world);
			if(ID == 10) return new ContainerChipPurple(   player.inventory, (IInventory)te, pos, world);
			if(ID == 11) return new ContainerChipBlue(     player.inventory, (IInventory)te, pos, world);
			if(ID == 12) return new ContainerChipBrown(    player.inventory, (IInventory)te, pos, world);
			if(ID == 13) return new ContainerChipGreen(    player.inventory, (IInventory)te, pos, world);
			if(ID == 14) return new ContainerChipRed(      player.inventory, (IInventory)te, pos, world);
			if(ID == 15) return new ContainerChipBlack(    player.inventory, (IInventory)te, pos, world);

			if(ID == 16) return new ContainerCardWhite(    player.inventory, (IInventory)te, pos, world);
			if(ID == 17) return new ContainerCardOrange(   player.inventory, (IInventory)te, pos, world);
			if(ID == 18) return new ContainerCardMagenta(  player.inventory, (IInventory)te, pos, world);
			if(ID == 19) return new ContainerCardLightBlue(player.inventory, (IInventory)te, pos, world);
			if(ID == 20) return new ContainerCardYellow(   player.inventory, (IInventory)te, pos, world);
			if(ID == 21) return new ContainerCardLime(     player.inventory, (IInventory)te, pos, world);
			if(ID == 22) return new ContainerCardPink(     player.inventory, (IInventory)te, pos, world);
			if(ID == 23) return new ContainerCardGray(     player.inventory, (IInventory)te, pos, world);
			if(ID == 24) return new ContainerCardLightGray(player.inventory, (IInventory)te, pos, world);
			if(ID == 25) return new ContainerCardCyan(     player.inventory, (IInventory)te, pos, world);
			if(ID == 26) return new ContainerCardPurple(   player.inventory, (IInventory)te, pos, world);
			if(ID == 27) return new ContainerCardBlue(     player.inventory, (IInventory)te, pos, world);
			if(ID == 28) return new ContainerCardBrown(    player.inventory, (IInventory)te, pos, world);
			if(ID == 29) return new ContainerCardGreen(    player.inventory, (IInventory)te, pos, world);
			if(ID == 30) return new ContainerCardRed(      player.inventory, (IInventory)te, pos, world);
			if(ID == 31) return new ContainerCardBlack(    player.inventory, (IInventory)te, pos, world);

			if(ID == 32) return new ContainerMinoWhite(    player.inventory, (IInventory)te, pos, world);
			if(ID == 33) return new ContainerMinoOrange(   player.inventory, (IInventory)te, pos, world);
			if(ID == 34) return new ContainerMinoMagenta(  player.inventory, (IInventory)te, pos, world);
			if(ID == 35) return new ContainerMinoLightBlue(player.inventory, (IInventory)te, pos, world);
			if(ID == 36) return new ContainerMinoYellow(   player.inventory, (IInventory)te, pos, world);
			if(ID == 37) return new ContainerMinoLime(     player.inventory, (IInventory)te, pos, world);
			if(ID == 38) return new ContainerMinoPink(     player.inventory, (IInventory)te, pos, world);
			if(ID == 39) return new ContainerMinoGray(     player.inventory, (IInventory)te, pos, world);
			if(ID == 40) return new ContainerMinoLightGray(player.inventory, (IInventory)te, pos, world);
			if(ID == 41) return new ContainerMinoCyan(     player.inventory, (IInventory)te, pos, world);
			if(ID == 42) return new ContainerMinoPurple(   player.inventory, (IInventory)te, pos, world);
			if(ID == 43) return new ContainerMinoBlue(     player.inventory, (IInventory)te, pos, world);
			if(ID == 44) return new ContainerMinoBrown(    player.inventory, (IInventory)te, pos, world);
			if(ID == 45) return new ContainerMinoGreen(    player.inventory, (IInventory)te, pos, world);
			if(ID == 46) return new ContainerMinoRed(      player.inventory, (IInventory)te, pos, world);
			if(ID == 47) return new ContainerMinoBlack(    player.inventory, (IInventory)te, pos, world);

			if(ID == 48) return new ContainerArcade(       player.inventory, (IInventory)te);
			if(ID == 49) return new ContainerCardTable(    player.inventory, (IInventory)te);
			if(ID == 50) return new ContainerSlotMachine(  player.inventory, (IInventory)te);
			if(ID == 51) return new ContainerSlotGame(     player.inventory, (IInventory)te, pos, world);
			if(ID == 52) return new ContainerDummy(        player.inventory, (IInventory)te, pos, world);
        }
        return null;
    }
	
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te != null){

            if(ID ==  0) return new GuiChipWhite(    player.inventory, (IInventory)te, pos, world);
            if(ID ==  1) return new GuiChipOrange(   player.inventory, (IInventory)te, pos, world);
            if(ID ==  2) return new GuiChipMagenta(  player.inventory, (IInventory)te, pos, world);
            if(ID ==  3) return new GuiChipLightBlue(player.inventory, (IInventory)te, pos, world);
            if(ID ==  4) return new GuiChipYellow(   player.inventory, (IInventory)te, pos, world);
            if(ID ==  5) return new GuiChipLime(     player.inventory, (IInventory)te, pos, world);
            if(ID ==  6) return new GuiChipPink(     player.inventory, (IInventory)te, pos, world);
            if(ID ==  7) return new GuiChipGray(     player.inventory, (IInventory)te, pos, world);
            if(ID ==  8) return new GuiChipLightGray(player.inventory, (IInventory)te, pos, world);
            if(ID ==  9) return new GuiChipCyan(     player.inventory, (IInventory)te, pos, world);
            if(ID == 10) return new GuiChipPurple(   player.inventory, (IInventory)te, pos, world);
            if(ID == 11) return new GuiChipBlue(     player.inventory, (IInventory)te, pos, world);
            if(ID == 12) return new GuiChipBrown(    player.inventory, (IInventory)te, pos, world);
            if(ID == 13) return new GuiChipGreen(    player.inventory, (IInventory)te, pos, world);
            if(ID == 14) return new GuiChipRed(      player.inventory, (IInventory)te, pos, world);
            if(ID == 15) return new GuiChipBlack(    player.inventory, (IInventory)te, pos, world);

            if(ID == 16) return new GuiCardWhite(    player.inventory, (IInventory)te, pos, world);
            if(ID == 17) return new GuiCardOrange(   player.inventory, (IInventory)te, pos, world);
            if(ID == 18) return new GuiCardMagenta(  player.inventory, (IInventory)te, pos, world);
            if(ID == 19) return new GuiCardLightBlue(player.inventory, (IInventory)te, pos, world);
            if(ID == 20) return new GuiCardYellow(   player.inventory, (IInventory)te, pos, world);
            if(ID == 21) return new GuiCardLime(     player.inventory, (IInventory)te, pos, world);
            if(ID == 22) return new GuiCardPink(     player.inventory, (IInventory)te, pos, world);
            if(ID == 23) return new GuiCardGray(     player.inventory, (IInventory)te, pos, world);
            if(ID == 24) return new GuiCardLightGray(player.inventory, (IInventory)te, pos, world);
            if(ID == 25) return new GuiCardCyan(     player.inventory, (IInventory)te, pos, world);
            if(ID == 26) return new GuiCardPurple(   player.inventory, (IInventory)te, pos, world);
            if(ID == 27) return new GuiCardBlue(     player.inventory, (IInventory)te, pos, world);
            if(ID == 28) return new GuiCardBrown(    player.inventory, (IInventory)te, pos, world);
            if(ID == 29) return new GuiCardGreen(    player.inventory, (IInventory)te, pos, world);
            if(ID == 30) return new GuiCardRed(      player.inventory, (IInventory)te, pos, world);
            if(ID == 31) return new GuiCardBlack(    player.inventory, (IInventory)te, pos, world);

            if(ID == 32) return new GuiMinoWhite(    player.inventory, (IInventory)te, pos, world);
            if(ID == 33) return new GuiMinoOrange(   player.inventory, (IInventory)te, pos, world);
            if(ID == 34) return new GuiMinoMagenta(  player.inventory, (IInventory)te, pos, world);
            if(ID == 35) return new GuiMinoLightBlue(player.inventory, (IInventory)te, pos, world);
            if(ID == 36) return new GuiMinoYellow(   player.inventory, (IInventory)te, pos, world);
            if(ID == 37) return new GuiMinoLime(     player.inventory, (IInventory)te, pos, world);
            if(ID == 38) return new GuiMinoPink(     player.inventory, (IInventory)te, pos, world);
            if(ID == 39) return new GuiMinoGray(     player.inventory, (IInventory)te, pos, world);
            if(ID == 40) return new GuiMinoLightGray(player.inventory, (IInventory)te, pos, world);
            if(ID == 41) return new GuiMinoCyan(     player.inventory, (IInventory)te, pos, world);
            if(ID == 42) return new GuiMinoPurple(   player.inventory, (IInventory)te, pos, world);
            if(ID == 43) return new GuiMinoBlue(     player.inventory, (IInventory)te, pos, world);
            if(ID == 44) return new GuiMinoBrown(    player.inventory, (IInventory)te, pos, world);
            if(ID == 45) return new GuiMinoGreen(    player.inventory, (IInventory)te, pos, world);
            if(ID == 46) return new GuiMinoRed(      player.inventory, (IInventory)te, pos, world);
            if(ID == 47) return new GuiMinoBlack(    player.inventory, (IInventory)te, pos, world);

            if(ID == 48) return new GuiArcade(       player.inventory, (IInventory)te);
            if(ID == 49) return new GuiCardTable(    player.inventory, (IInventory)te);
            if(ID == 50) return new GuiSlotMachine(  player.inventory, (IInventory)te);
            if(ID == 51) return new GuiSlotGame(     player.inventory, (IInventory)te, pos, world);
            if(ID == 52) return new GuiDummy(        player.inventory, (IInventory)te, pos, world);
        }
        return null;
    }
	
}
