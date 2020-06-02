package net.casinocraft.mod.container;

import javax.annotation.Nullable;

import net.casinocraft.mod.tileentity.TileEntityMemory;
import net.casinocraft.mod.util.Vector2;
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

public class ContainerMemory extends Container{
    private final TileEntityMemory tileCardTable;
    public String grid_main[][] = new String[6][6];
    public boolean selected_A;
    public boolean selected_B;
    public Vector2 selected_A_pos;
    public Vector2 selected_B_pos;
    public int timer;
    public boolean end;
    
    public ContainerMemory(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = (TileEntityMemory) blockInventory;
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
        	
        	if(this.selected_A     != this.tileCardTable.selected_A)    { icontainerlistener.sendProgressBarUpdate(this, 0, 0); }
        	if(this.selected_B     != this.tileCardTable.selected_B)    { icontainerlistener.sendProgressBarUpdate(this, 1, 0); }
        	if(this.selected_A_pos != this.tileCardTable.selected_A_pos){ icontainerlistener.sendProgressBarUpdate(this, 2, 0); }
        	if(this.selected_B_pos != this.tileCardTable.selected_B_pos){ icontainerlistener.sendProgressBarUpdate(this, 3, 0); }
        	if(this.timer          != this.tileCardTable.timer)         { icontainerlistener.sendProgressBarUpdate(this, 4, 0); }
        	if(this.end            != this.tileCardTable.end)           { icontainerlistener.sendProgressBarUpdate(this, 5, 0); }
        	for(int y = 0; y < 6; y++){
        		for(int x = 0; x < 6; x++){
    				if(this.grid_main[x][y] != this.tileCardTable.grid_main[x][y]){ icontainerlistener.sendProgressBarUpdate(this, 100+x, y); }
        		}
        	}
        	
        }
        	
        this.selected_A     = this.tileCardTable.selected_A;
    	this.selected_B     = this.tileCardTable.selected_B;
    	this.selected_A_pos = this.tileCardTable.selected_A_pos;
    	this.selected_B_pos = this.tileCardTable.selected_B_pos;
    	this.timer          = this.tileCardTable.timer;
    	this.end            = this.tileCardTable.end;
    	for(int y = 0; y < 6; y++){
    		for(int x = 0; x < 6; x++){
    			grid_main[x][y] = this.tileCardTable.grid_main[x][y];
    		}
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
    	if(id == 0) this.tileCardTable.selected_A     = this.selected_A;
    	if(id == 1) this.tileCardTable.selected_B     = this.selected_B;
    	if(id == 2) this.tileCardTable.selected_A_pos = this.selected_A_pos;
    	if(id == 3) this.tileCardTable.selected_B_pos = this.selected_B_pos;
    	if(id == 4) this.tileCardTable.timer          = this.timer;
    	if(id == 5) this.tileCardTable.end            = this.end;

    	if(id >= 100){
    		this.tileCardTable.grid_main[id-100][data] = this.grid_main[id-100][data];
    	}
    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileCardTable.isUseableByPlayer(playerIn);
    }
}