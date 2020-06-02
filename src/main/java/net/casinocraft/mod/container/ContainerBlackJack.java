package net.casinocraft.mod.container;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.casinocraft.mod.tileentity.TileEntityBlackJack;
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

public class ContainerBlackJack extends Container{
    private final TileEntityBlackJack tileCardTable;
    public List<Card> cards_player = new ArrayList<Card>();
    public List<Card> cards_dealer = new ArrayList<Card>();
    public int value_player;
    public int value_dealer;
    public boolean end;
    public boolean active_player;
    
    public ContainerBlackJack(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = (TileEntityBlackJack) blockInventory;
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
            
            if(this.value_player  != this.tileCardTable.value_player) { icontainerlistener.sendProgressBarUpdate(this, 0, 0); }
            if(this.value_dealer  != this.tileCardTable.value_dealer) { icontainerlistener.sendProgressBarUpdate(this, 1, 0); }
            if(this.end           != this.tileCardTable.end)          { icontainerlistener.sendProgressBarUpdate(this, 2, 0); }
            if(this.active_player != this.tileCardTable.active_player){ icontainerlistener.sendProgressBarUpdate(this, 3, 0); }
            if(this.cards_player.size() != this.tileCardTable.cards_player.size()){
            	icontainerlistener.sendProgressBarUpdate(this, 4, 0);
            } else {
            	for(int q = 0; q < cards_player.size(); q++){
            		if(this.cards_player.get(q).number != this.tileCardTable.cards_player.get(q).number){
                       	icontainerlistener.sendProgressBarUpdate(this, 4, 0);
                    }
            	}
            }
            if(this.cards_dealer.size() != this.tileCardTable.cards_dealer.size()){
            	icontainerlistener.sendProgressBarUpdate(this, 5, 0);
            } else {
            	for(int q = 0; q < cards_dealer.size(); q++){
            		if(this.cards_dealer.get(q).number != this.tileCardTable.cards_dealer.get(q).number){
                       	icontainerlistener.sendProgressBarUpdate(this, 5, 0);
                    }
            	}
            }
            
        }
        
        this.value_player  = this.tileCardTable.value_player;
        this.value_dealer  = this.tileCardTable.value_dealer;
        this.end           = this.tileCardTable.end;
        this.active_player = this.tileCardTable.active_player;
        this.cards_player.clear();
        this.cards_player.addAll(this.tileCardTable.cards_player);
        this.cards_dealer.clear();
        this.cards_dealer.addAll(this.tileCardTable.cards_dealer);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
    	if(id == 0)  this.tileCardTable.value_player  = this.value_player;
    	if(id == 1)  this.tileCardTable.value_dealer  = this.value_dealer;
    	if(id == 2)  this.tileCardTable.end           = this.end;
    	if(id == 3)  this.tileCardTable.active_player = this.active_player;
    	if(id == 4){ this.tileCardTable.cards_player.clear(); this.tileCardTable.cards_player.addAll(this.cards_player); } 
    	if(id == 5){ this.tileCardTable.cards_player.clear(); this.tileCardTable.cards_dealer.addAll(this.cards_dealer); } 
    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileCardTable.isUseableByPlayer(playerIn);
    }
    
    private int boolToInt(boolean b){
    	return b ? 1 : 0;
    }
    
    private boolean intToBool(int i){
    	return i > 0;
    }
    
}