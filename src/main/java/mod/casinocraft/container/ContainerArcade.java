package mod.casinocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerArcade extends Container {
	
private final IInventory tileArcade;
    
    public ContainerArcade(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileArcade = blockInventory;
        this.addSlotToContainer(new Slot(blockInventory, 0,   8, 62)); // Key Card
        this.addSlotToContainer(new Slot(blockInventory, 1, 152, 62)); // Game Module
        this.addSlotToContainer(new Slot(blockInventory, 2,  28, 16)); // Token IN
        this.addSlotToContainer(new Slot(blockInventory, 3, 132, 16)); // Token OUT
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
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileArcade);
    }
    
    /** Looks for changes made in the container, sends them to every listener. */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = this.listeners.get(i);
        }
    }
    
    @SideOnly(Side.CLIENT)
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
    }
	
}