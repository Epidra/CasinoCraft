package net.casinocraft.mod.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerVideoPoker extends Container {
	
	TileEntityVideoPoker tileentity;
	
	int lastCard1x = 0;
	int lastCard1y = 0;
	int lastCard2x = 0;
	int lastCard2y = 0;
	int lastCard3x = 0;
	int lastCard3y = 0;
	int lastCard4x = 0;
	int lastCard4y = 0;
	int lastCard5x = 0;
	int lastCard5y = 0;
	int lastField = 0;
	int lastHand = 0;
	int lastActiveBet = 1;
	int lastActiveWon = 0;
	int lastHold1 = 0;
	int lastHold2 = 0;
	int lastHold3 = 0;
	int lastHold4 = 0;
	int lastHold5 = 0;
	
	public ContainerVideoPoker(InventoryPlayer inventory, TileEntityVideoPoker entity){
		this.tileentity = entity;
		this.addSlotToContainer(new Slot(entity, 0,  9, 202)); // Input
		this.addSlotToContainer(new Slot(entity, 1,  234, 202)); // Output 1
		this.addSlotToContainer(new Slot(entity, 2,  218, 202)); // Output 2
		this.addSlotToContainer(new Slot(entity, 3,  202, 202)); // Output 3
		this.addSlotToContainer(new Slot(entity, 4,  186, 202)); // Output 4
		this.addSlotToContainer(new Slot(entity, 5,  170, 202)); // Output 5
		this.addSlotToContainer(new Slot(entity, 6,  154, 202)); // Output 6
		this.addSlotToContainer(new Slot(entity, 7,  138, 202)); // Output 7
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
		ic.sendProgressBarUpdate(this,  0, this.tileentity.card1x);
		ic.sendProgressBarUpdate(this,  1, this.tileentity.card1y);
		ic.sendProgressBarUpdate(this,  2, this.tileentity.card2x);
		ic.sendProgressBarUpdate(this,  3, this.tileentity.card2y);
		ic.sendProgressBarUpdate(this,  4, this.tileentity.card3x);
		ic.sendProgressBarUpdate(this,  5, this.tileentity.card3y);
		ic.sendProgressBarUpdate(this,  6, this.tileentity.card4x);
		ic.sendProgressBarUpdate(this,  7, this.tileentity.card4y);
		ic.sendProgressBarUpdate(this,  8, this.tileentity.card5x);
		ic.sendProgressBarUpdate(this,  9, this.tileentity.card5y);
		ic.sendProgressBarUpdate(this, 10, this.tileentity.field);
		ic.sendProgressBarUpdate(this, 11, this.tileentity.hand);
		ic.sendProgressBarUpdate(this, 12, transmute(this.tileentity.active_bet));
		ic.sendProgressBarUpdate(this, 13, transmute(this.tileentity.active_won));
		ic.sendProgressBarUpdate(this, 14, transmute(this.tileentity.hold1));
		ic.sendProgressBarUpdate(this, 15, transmute(this.tileentity.hold2));
		ic.sendProgressBarUpdate(this, 16, transmute(this.tileentity.hold3));
		ic.sendProgressBarUpdate(this, 17, transmute(this.tileentity.hold4));
		ic.sendProgressBarUpdate(this, 18, transmute(this.tileentity.hold5));
	}
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i){
			ICrafting ic = (ICrafting)this.crafters.get(i);
			if(this.lastCard1x != this.tileentity.card1x){ ic.sendProgressBarUpdate(this, 0, this.tileentity.card1x); }
			if(this.lastCard1y != this.tileentity.card1y){ ic.sendProgressBarUpdate(this, 1, this.tileentity.card1y); }
			if(this.lastCard2x != this.tileentity.card2x){ ic.sendProgressBarUpdate(this, 2, this.tileentity.card2x); }
			if(this.lastCard2y != this.tileentity.card2y){ ic.sendProgressBarUpdate(this, 3, this.tileentity.card2y); }
			if(this.lastCard3x != this.tileentity.card3x){ ic.sendProgressBarUpdate(this, 4, this.tileentity.card3x); }
			if(this.lastCard3y != this.tileentity.card3y){ ic.sendProgressBarUpdate(this, 5, this.tileentity.card3y); }
			if(this.lastCard4x != this.tileentity.card4x){ ic.sendProgressBarUpdate(this, 6, this.tileentity.card4x); }
			if(this.lastCard4y != this.tileentity.card4y){ ic.sendProgressBarUpdate(this, 7, this.tileentity.card4y); }
			if(this.lastCard5x != this.tileentity.card5x){ ic.sendProgressBarUpdate(this, 8, this.tileentity.card5x); }
			if(this.lastCard5y != this.tileentity.card5y){ ic.sendProgressBarUpdate(this, 9, this.tileentity.card5y); }
			if(this.lastField != this.tileentity.field){ ic.sendProgressBarUpdate(this, 10, this.tileentity.field); }
			if(this.lastHand != this.tileentity.hand){ ic.sendProgressBarUpdate(this, 11, this.tileentity.hand); }
			if(this.lastActiveBet != transmute(this.tileentity.active_bet)){ ic.sendProgressBarUpdate(this, 12, transmute(this.tileentity.active_bet)); }
			if(this.lastActiveWon != transmute(this.tileentity.active_won)){ ic.sendProgressBarUpdate(this, 13, transmute(this.tileentity.active_won)); }
			if(this.lastHold1 != transmute(this.tileentity.hold1)){ ic.sendProgressBarUpdate(this, 14, transmute(this.tileentity.hold1)); }
			if(this.lastHold2 != transmute(this.tileentity.hold2)){ ic.sendProgressBarUpdate(this, 15, transmute(this.tileentity.hold2)); }
			if(this.lastHold3 != transmute(this.tileentity.hold3)){ ic.sendProgressBarUpdate(this, 16, transmute(this.tileentity.hold3)); }
			if(this.lastHold4 != transmute(this.tileentity.hold4)){ ic.sendProgressBarUpdate(this, 17, transmute(this.tileentity.hold4)); }
			if(this.lastHold5 != transmute(this.tileentity.hold5)){ ic.sendProgressBarUpdate(this, 18, transmute(this.tileentity.hold5)); }
		}
		this.lastCard1x = this.tileentity.card1x;
		this.lastCard1y = this.tileentity.card1y;
		this.lastCard2x = this.tileentity.card2x;
		this.lastCard2y = this.tileentity.card2y;
		this.lastCard3x = this.tileentity.card3x;
		this.lastCard3y = this.tileentity.card3y;
		this.lastCard4x = this.tileentity.card4x;
		this.lastCard4y = this.tileentity.card4y;
		this.lastCard5x = this.tileentity.card5x;
		this.lastCard5y = this.tileentity.card5y;
		this.lastField = this.tileentity.field;
		this.lastHand = this.tileentity.hand;
		this.lastActiveBet = transmute(this.tileentity.active_bet);
		this.lastActiveWon = transmute(this.tileentity.active_won);
		this.lastHold1 = transmute(this.tileentity.hold1);
		this.lastHold2 = transmute(this.tileentity.hold2);
		this.lastHold3 = transmute(this.tileentity.hold3);
		this.lastHold4 = transmute(this.tileentity.hold4);
		this.lastHold5 = transmute(this.tileentity.hold5);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value){
		if(id == 0){ this.tileentity.card1x = value; }
		if(id == 1){ this.tileentity.card1y = value; }
		if(id == 2){ this.tileentity.card2x = value; }
		if(id == 3){ this.tileentity.card2y = value; }
		if(id == 4){ this.tileentity.card3x = value; }
		if(id == 5){ this.tileentity.card3y = value; }
		if(id == 6){ this.tileentity.card4x = value; }
		if(id == 7){ this.tileentity.card4y = value; }
		if(id == 8){ this.tileentity.card5x = value; }
		if(id == 9){ this.tileentity.card5y = value; }
		if(id == 10){ this.tileentity.field = value; }
		if(id == 11){ this.tileentity.hand = value; }
		if(id == 12){ this.tileentity.active_bet = transmute(value); }
		if(id == 13){ this.tileentity.active_won = transmute(value); }
		if(id == 14){ this.tileentity.hold1 = transmute(value); }
		if(id == 15){ this.tileentity.hold2 = transmute(value); }
		if(id == 16){ this.tileentity.hold3 = transmute(value); }
		if(id == 17){ this.tileentity.hold4 = transmute(value); }
		if(id == 18){ this.tileentity.hold5 = transmute(value); }
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
