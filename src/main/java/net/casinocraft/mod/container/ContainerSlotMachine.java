package net.casinocraft.mod.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSlotMachine extends Container{
    private final IInventory tileCardTable;
    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    
    public ContainerSlotMachine(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = blockInventory;
    }
    
    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileCardTable);
    }
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.cookTime            != this.tileCardTable.getField(2)){ icontainerlistener.sendProgressBarUpdate(this, 2, this.tileCardTable.getField(2)); }
            if (this.furnaceBurnTime     != this.tileCardTable.getField(0)){ icontainerlistener.sendProgressBarUpdate(this, 0, this.tileCardTable.getField(0)); }
            if (this.currentItemBurnTime != this.tileCardTable.getField(1)){ icontainerlistener.sendProgressBarUpdate(this, 1, this.tileCardTable.getField(1)); }
            if (this.totalCookTime       != this.tileCardTable.getField(3)){ icontainerlistener.sendProgressBarUpdate(this, 3, this.tileCardTable.getField(3)); }
        }
        this.cookTime            = this.tileCardTable.getField(2);
        this.furnaceBurnTime     = this.tileCardTable.getField(0);
        this.currentItemBurnTime = this.tileCardTable.getField(1);
        this.totalCookTime       = this.tileCardTable.getField(3);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.tileCardTable.setField(id, data);
    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileCardTable.isUseableByPlayer(playerIn);
    }
}