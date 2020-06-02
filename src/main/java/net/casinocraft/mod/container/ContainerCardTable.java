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

public class ContainerCardTable extends Container{
    private final IInventory tileCardTable;
    
    public ContainerCardTable(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = blockInventory;
        this.addSlotToContainer(new Slot(blockInventory, 0, 56, 35));
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }
    
    public void addListener(IContainerListener listener){
        //super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileCardTable);
    }
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        //super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.tileCardTable.setField(id, data);
    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileCardTable.isUsableByPlayer(playerIn);
    }
    
    /**
     * Take a stack from the specified inventory slot.
     */
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2){
                if (!this.mergeItemStack(itemstack1, 3, 39, true)){
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0){
                if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null){
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)){
                        return null;
                    }
                } else if (TileEntityFurnace.isItemFuel(itemstack1)){
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)){
                        return null;
                    }
                } else if (index >= 3 && index < 30){
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)){
                        return null;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)){
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)){
                return null;
            }
            if (itemstack1.getCount() == 0){
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()){
                return null;
            }
            //slot.onPickupFromSlot(playerIn, itemstack1);
        }
        return itemstack;
    }
}