package net.casinocraft.mod.container;

import javax.annotation.Nullable;

import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.casinocraft.mod.util.Card;
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

public class ContainerVideoPoker extends Container{
    private final TileEntityVideoPoker tileCardTable;
    public boolean end;
    public String hand;
    public Card card1;
    public Card card2;
    public Card card3;
    public Card card4;
    public Card card5;
    public boolean hold1;
    public boolean hold2;
    public boolean hold3;
    public boolean hold4;
    public boolean hold5;
    
    public ContainerVideoPoker(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = (TileEntityVideoPoker) blockInventory;
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
            
            if(this.end   != this.tileCardTable.end)  { icontainerlistener.sendProgressBarUpdate(this,  0, 0); }
            if(this.hand  != this.tileCardTable.hand) { icontainerlistener.sendProgressBarUpdate(this,  1, 0); }
            if(this.card1 != this.tileCardTable.card1){ icontainerlistener.sendProgressBarUpdate(this,  2, 0); }
            if(this.card2 != this.tileCardTable.card2){ icontainerlistener.sendProgressBarUpdate(this,  3, 0); }
            if(this.card3 != this.tileCardTable.card3){ icontainerlistener.sendProgressBarUpdate(this,  4, 0); }
            if(this.card4 != this.tileCardTable.card4){ icontainerlistener.sendProgressBarUpdate(this,  5, 0); }
            if(this.card5 != this.tileCardTable.card5){ icontainerlistener.sendProgressBarUpdate(this,  6, 0); }
            if(this.hold1 != this.tileCardTable.hold1){ icontainerlistener.sendProgressBarUpdate(this,  7, 0); }
            if(this.hold2 != this.tileCardTable.hold2){ icontainerlistener.sendProgressBarUpdate(this,  8, 0); }
            if(this.hold3 != this.tileCardTable.hold3){ icontainerlistener.sendProgressBarUpdate(this,  9, 0); }
            if(this.hold4 != this.tileCardTable.hold4){ icontainerlistener.sendProgressBarUpdate(this, 10, 0); }
            if(this.hold5 != this.tileCardTable.hold5){ icontainerlistener.sendProgressBarUpdate(this, 11, 0); }
        }
        this.end   = this.tileCardTable.end;
        this.hand  = this.tileCardTable.hand;
        this.card1 = this.tileCardTable.card1;
        this.card2 = this.tileCardTable.card2;
        this.card3 = this.tileCardTable.card3;
        this.card4 = this.tileCardTable.card4;
        this.card5 = this.tileCardTable.card5;
        this.hold1 = this.tileCardTable.hold1;
        this.hold2 = this.tileCardTable.hold2;
        this.hold3 = this.tileCardTable.hold3;
        this.hold4 = this.tileCardTable.hold4;
        this.hold5 = this.tileCardTable.hold5;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
    	if(id ==  0) this.tileCardTable.end   = this.end;
    	if(id ==  1) this.tileCardTable.hand  = this.hand;
    	if(id ==  2) this.tileCardTable.card1 = this.card1;
    	if(id ==  3) this.tileCardTable.card2 = this.card2;
    	if(id ==  4) this.tileCardTable.card3 = this.card3;
    	if(id ==  5) this.tileCardTable.card4 = this.card4;
    	if(id ==  6) this.tileCardTable.card5 = this.card5;
    	if(id ==  7) this.tileCardTable.hold1 = this.hold1;
    	if(id ==  8) this.tileCardTable.hold2 = this.hold2;
    	if(id ==  9) this.tileCardTable.hold3 = this.hold3;
    	if(id == 10) this.tileCardTable.hold4 = this.hold4;
    	if(id == 11) this.tileCardTable.hold5 = this.hold5;
    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileCardTable.isUseableByPlayer(playerIn);
    }
}