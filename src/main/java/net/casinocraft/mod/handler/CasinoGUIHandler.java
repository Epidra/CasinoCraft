package net.casinocraft.mod.handler;

import net.casinocraft.mod.CasinoKeeper;
import net.casinocraft.mod.container.ContainerArcade;
import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerBlackJack;
import net.casinocraft.mod.container.ContainerCardTable;
import net.casinocraft.mod.container.ContainerMemory;
import net.casinocraft.mod.container.ContainerRoulette;
import net.casinocraft.mod.container.ContainerSlotMachine;
import net.casinocraft.mod.container.ContainerTetris;
import net.casinocraft.mod.container.ContainerVideoPoker;
import net.casinocraft.mod.gui.GuiArcade;
import net.casinocraft.mod.gui.GuiBaccarat;
import net.casinocraft.mod.gui.GuiBlackJack;
import net.casinocraft.mod.gui.GuiCardTable;
import net.casinocraft.mod.gui.GuiMemory;
import net.casinocraft.mod.gui.GuiRoulette;
import net.casinocraft.mod.gui.GuiSlotMachine;
import net.casinocraft.mod.gui.GuiTetris;
import net.casinocraft.mod.gui.GuiVideoPoker;
import net.casinocraft.mod.tileentity.TileEntityBaccarat;
import net.casinocraft.mod.tileentity.TileEntityBlackJack;
import net.casinocraft.mod.tileentity.TileEntityCasino;
import net.casinocraft.mod.tileentity.TileEntityMemory;
import net.casinocraft.mod.tileentity.TileEntityRoulette;
import net.casinocraft.mod.tileentity.TileEntitySlotMachine;
import net.casinocraft.mod.tileentity.TileEntityTetris;
import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CasinoGUIHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te != null){
        	if(ID == CasinoKeeper.guiIDArcade)      if(te instanceof TileEntityCasino){ return new ContainerArcade     (player.inventory, (IInventory)te); }
        	if(ID == CasinoKeeper.guiIDCardTable)   if(te instanceof TileEntityCasino){ return new ContainerCardTable  (player.inventory, (IInventory)te); }
        	if(ID == CasinoKeeper.guiIDBaccarat)    if(te instanceof TileEntityCasino){ return new ContainerBaccarat   (player.inventory, new TileEntityBaccarat (((TileEntityCasino) te).color)); }
        	if(ID == CasinoKeeper.guiIDBlackJack)   if(te instanceof TileEntityCasino){ return new ContainerBlackJack  (player.inventory, new TileEntityBlackJack(((TileEntityCasino) te).color)); }
        	if(ID == CasinoKeeper.guiIDMemory)      if(te instanceof TileEntityCasino){ return new ContainerMemory     (player.inventory, new TileEntityMemory   (((TileEntityCasino) te).color)); }
        	if(ID == CasinoKeeper.guiIDRoulette)    if(te instanceof TileEntityCasino){ return new ContainerRoulette   (player.inventory, new TileEntityRoulette   ()); }
        	if(ID == CasinoKeeper.guiIDSlotMachine) if(te instanceof TileEntityCasino){ return new ContainerSlotMachine(player.inventory, new TileEntitySlotMachine()); }
        	if(ID == CasinoKeeper.guiIDTetris)      if(te instanceof TileEntityCasino){ return new ContainerTetris     (player.inventory, new TileEntityTetris     ()); }
        	if(ID == CasinoKeeper.guiIDVideoPoker)  if(te instanceof TileEntityCasino){ return new ContainerVideoPoker (player.inventory, new TileEntityVideoPoker ()); }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te != null){
        	if(ID == CasinoKeeper.guiIDArcade)      if(te instanceof TileEntityCasino){ return new GuiArcade     (player.inventory, (IInventory)te); }
        	if(ID == CasinoKeeper.guiIDCardTable)   if(te instanceof TileEntityCasino){ return new GuiCardTable  (player.inventory, (IInventory)te); }
        	if(ID == CasinoKeeper.guiIDBaccarat)    if(te instanceof TileEntityCasino){ return new GuiBaccarat   (player.inventory, new TileEntityBaccarat (((TileEntityCasino) te).color)); }
        	if(ID == CasinoKeeper.guiIDBlackJack)   if(te instanceof TileEntityCasino){ return new GuiBlackJack  (player.inventory, new TileEntityBlackJack(((TileEntityCasino) te).color)); }
        	if(ID == CasinoKeeper.guiIDMemory)      if(te instanceof TileEntityCasino){ return new GuiMemory     (player.inventory, new TileEntityMemory   (((TileEntityCasino) te).color)); }
        	if(ID == CasinoKeeper.guiIDRoulette)    if(te instanceof TileEntityCasino){ return new GuiRoulette   (player.inventory, new TileEntityRoulette   ()); }
        	if(ID == CasinoKeeper.guiIDSlotMachine) if(te instanceof TileEntityCasino){ return new GuiSlotMachine(player.inventory, new TileEntitySlotMachine()); }
        	if(ID == CasinoKeeper.guiIDTetris)      if(te instanceof TileEntityCasino){ return new GuiTetris     (player.inventory, new TileEntityTetris     ()); }
        	if(ID == CasinoKeeper.guiIDVideoPoker)  if(te instanceof TileEntityCasino){ return new GuiVideoPoker (player.inventory, new TileEntityVideoPoker ()); }
        }
        return null;
    }
}