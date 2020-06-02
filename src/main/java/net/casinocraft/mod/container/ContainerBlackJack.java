package net.casinocraft.mod.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.casinocraft.mod.tileentity.TileEntityBlackJack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerBlackJack extends Container {
	
	int[] lastCardPx = new int[10];
	int[] lastCardPy = new int[10];
	int[] lastCardDx = new int[10];
	int[] lastCardDy = new int[10];
	int lastField;
	int lastHand;
	int lastActiveBet;
	int lastActiveWon;
	
	private TileEntityBlackJack tileentity;
	
	public ContainerBlackJack(InventoryPlayer inventory, TileEntityBlackJack entity){
		this.tileentity = entity;
		this.addSlotToContainer(new Slot(entity, 0,  9, 202)); // Input
		this.addSlotToContainer(new Slot(entity, 1,  234, 202)); // Output 1
		this.addSlotToContainer(new Slot(entity, 2,  218, 202)); // Output 2
		for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                //this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i){
        	this.addSlotToContainer(new Slot(inventory, i, 48 + i * 18, 232));
        }
	}
	
	private boolean transmute(int i){
		if(i == 0){
			return false;
		}else{
			return true;
		}
	}
	
	private int transmute(boolean b){
		if(!b){
			return 0;
		}else{
			return 1;
		}
	}
	
	public void addCraftingToCrafters(ICrafting ic){
		for(int i = 0; i < 10; i++){
			ic.sendProgressBarUpdate(this, 4*i+0, this.tileentity.cardPx[i]);
			ic.sendProgressBarUpdate(this, 4*i+1, this.tileentity.cardPy[i]);
			ic.sendProgressBarUpdate(this, 4*i+2, this.tileentity.cardDx[i]);
			ic.sendProgressBarUpdate(this, 4*i+3, this.tileentity.cardDy[i]);
		}
		ic.sendProgressBarUpdate(this, 40, this.tileentity.field);
		ic.sendProgressBarUpdate(this, 41, this.tileentity.hand);
		ic.sendProgressBarUpdate(this, 42, transmute(this.tileentity.active_bet));
		ic.sendProgressBarUpdate(this, 43, transmute(this.tileentity.active_won));
	}
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting ic = (ICrafting)this.crafters.get(i);
			for(int j = 0; j < 10; j++){
				if(this.lastCardPx[i] != this.tileentity.cardPx[i]){ ic.sendProgressBarUpdate(this, 4*i+0, this.tileentity.cardPx[i]);}
				if(this.lastCardPy[i] != this.tileentity.cardPy[i]){ ic.sendProgressBarUpdate(this, 4*i+1, this.tileentity.cardPy[i]);}
				if(this.lastCardDx[i] != this.tileentity.cardDx[i]){ ic.sendProgressBarUpdate(this, 4*i+2, this.tileentity.cardDx[i]);}
				if(this.lastCardDy[i] != this.tileentity.cardDy[i]){ ic.sendProgressBarUpdate(this, 4*i+3, this.tileentity.cardDy[i]);}
			}
			if(this.lastField != this.tileentity.field){ ic.sendProgressBarUpdate(this, 40, this.tileentity.field); }
			if(this.lastHand != this.tileentity.hand){ ic.sendProgressBarUpdate(this, 41, this.tileentity.hand); }
			if(this.lastActiveBet != transmute(this.tileentity.active_bet)){ ic.sendProgressBarUpdate(this, 42, transmute(this.tileentity.active_bet)); }
			if(this.lastActiveWon != transmute(this.tileentity.active_won)){ ic.sendProgressBarUpdate(this, 43, transmute(this.tileentity.active_won)); }
		}
		this.lastCardPx = this.tileentity.cardPx;
		this.lastCardPy = this.tileentity.cardPy;
		this.lastCardDx = this.tileentity.cardDx;
		this.lastCardDy = this.tileentity.cardDy;
		this.lastField = this.tileentity.field;
		this.lastHand = this.tileentity.hand;
		this.lastActiveBet = transmute(this.tileentity.active_bet);
		this.lastActiveWon = transmute(this.tileentity.active_won);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value){
		for(int i = 0; i < 10; i++){
			if(id == 4*i+0){ this.tileentity.cardPx[i] = value; }
			if(id == 4*i+1){ this.tileentity.cardPx[i] = value; }
			if(id == 4*i+2){ this.tileentity.cardPx[i] = value; }
			if(id == 4*i+3){ this.tileentity.cardPx[i] = value; }
		}
		if(id == 40){ this.tileentity.field = value; }
		if(id == 41){ this.tileentity.hand = value; }
		if(id == 42){ this.tileentity.active_bet = transmute(value); }
		if(id == 43){ this.tileentity.active_won = transmute(value); }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileentity.isUseableByPlayer(player);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int i){
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i == 2){
                if (!this.mergeItemStack(itemstack1, 3, 39, true)){
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }else if (i != 1 && i != 0){
                if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null){
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)){
                        return null;
                    }
                }else if (TileEntityFurnace.isItemFuel(itemstack1)){
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)){
                        return null;
                    }
                }else if (i >= 3 && i < 30){
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)){
                        return null;
                    }
                }else if (i >= 30 && i < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)){
                    return null;
                }
            }else if (!this.mergeItemStack(itemstack1, 3, 39, false)){
                return null;
            }
            if (itemstack1.stackSize == 0){
                slot.putStack((ItemStack)null);
            }else{
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize){
                return null;
            }
            slot.onPickupFromSlot(player, itemstack1);
        }
        return itemstack;
    }
	
}
