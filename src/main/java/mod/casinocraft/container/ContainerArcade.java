package mod.casinocraft.container;

import javafx.geometry.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ContainerArcade extends Container {

    private final IInventory tileArcade;
    private final World world;

    public ContainerArcade(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileArcade = blockInventory;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(blockInventory, 0,   8, 62)); // Key Card
        this.addSlot(new Slot(blockInventory, 1, 152, 62)); // Game Module
        this.addSlot(new Slot(blockInventory, 2,  28, 16)); // Token IN
        this.addSlot(new Slot(blockInventory, 3, 132, 16)); // Token OUT
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k){
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileArcade);
    }

    /** Looks for changes made in the container, sends them to every listener. */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = this.listeners.get(i);
        }
        //super.detectAndSendChanges();
//
        //for(IContainerListener icontainerlistener : this.listeners) {
        //    if (this.cookTime != this.tileFurnace.getField(2)) {
        //        icontainerlistener.sendWindowProperty(this, 2, this.tileFurnace.getField(2));
        //    }
//
        //    if (this.furnaceBurnTime != this.tileFurnace.getField(0)) {
        //        icontainerlistener.sendWindowProperty(this, 0, this.tileFurnace.getField(0));
        //    }
//
        //    if (this.currentItemBurnTime != this.tileFurnace.getField(1)) {
        //        icontainerlistener.sendWindowProperty(this, 1, this.tileFurnace.getField(1));
        //    }
//
        //    if (this.totalCookTime != this.tileFurnace.getField(3)) {
        //        icontainerlistener.sendWindowProperty(this, 3, this.tileFurnace.getField(3));
        //    }
        //}
//
        //this.cookTime = this.tileFurnace.getField(2);
        //this.furnaceBurnTime = this.tileFurnace.getField(0);
        //this.currentItemBurnTime = this.tileFurnace.getField(1);
        //this.totalCookTime = this.tileFurnace.getField(3);
    }

    @OnlyIn(Dist.CLIENT)
    public void updateProgressBar(int id, int data){
        this.tileArcade.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileArcade.isUsableByPlayer(playerIn);
    }

    /** Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s). */
    // TAKEN FROM BIOMES'O'PLENTY
    @Override
    //@Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index){
        ItemStack oldStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        //Ensure there is a slot at this index and it has an item in it
        if (slot != null && slot.getHasStack()){
            ItemStack mergedStack = slot.getStack();
            oldStack = mergedStack.copy();
            if (index < 15){
                if (!this.mergeItemStack(mergedStack, 15, this.inventorySlots.size(), true)){
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(mergedStack, 0, 15, false)){
                return ItemStack.EMPTY;
            }
            if (mergedStack.getCount() == 0){
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return oldStack;
        //ItemStack itemstack = ItemStack.EMPTY;
        //Slot slot = this.inventorySlots.get(index);
        //if (slot != null && slot.getHasStack()) {
        //    ItemStack itemstack1 = slot.getStack();
        //    itemstack = itemstack1.copy();
        //    if (index == 2) {
        //        if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
        //            return ItemStack.EMPTY;
        //        }
//
        //        slot.onSlotChange(itemstack1, itemstack);
        //    } else if (index != 1 && index != 0) {
        //        if (this.canSmelt(itemstack1)) {
        //            if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
        //                return ItemStack.EMPTY;
        //            }
        //        } else if (TileEntityFurnace.isItemFuel(itemstack1)) {
        //            if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
        //                return ItemStack.EMPTY;
        //            }
        //        } else if (index >= 3 && index < 30) {
        //            if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
        //                return ItemStack.EMPTY;
        //            }
        //        } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
        //            return ItemStack.EMPTY;
        //        }
        //    } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
        //        return ItemStack.EMPTY;
        //    }
//
        //    if (itemstack1.isEmpty()) {
        //        slot.putStack(ItemStack.EMPTY);
        //    } else {
        //        slot.onSlotChanged();
        //    }
//
        //    if (itemstack1.getCount() == itemstack.getCount()) {
        //        return ItemStack.EMPTY;
        //    }
//
        //    slot.onTake(playerIn, itemstack1);
        //}
//
        //return itemstack;
    }
}
