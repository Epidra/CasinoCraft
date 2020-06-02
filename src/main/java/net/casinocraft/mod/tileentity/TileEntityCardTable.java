package net.casinocraft.mod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerCardTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCardTable extends TileEntity implements ITickable, IInventory{
	
	public ItemStack[] cardtableItemStacks = new ItemStack[1];
    
	private UUID ownerID;
	
	public TileEntityCardTable(){
		
	}
	
	public UUID Get_Owner(){
		return ownerID;
	}
	
	public void Set_Owner(UUID id){
		ownerID = id;
	}
	
    /** Returns the number of slots in the inventory. */
    public int getSizeInventory(){
        return this.cardtableItemStacks.length;
    }
    
    /** Returns the stack in the given slot. */
    @Nullable
    public ItemStack getStackInSlot(int index){
        return this.cardtableItemStacks[index];
    }
    
    /** Removes up to a specified number of items from an inventory slot and returns them in a new stack. */
    @Nullable
    public ItemStack decrStackSize(int index, int count){
        return null; // ItemStackHelper.getAndSplit(this.cardtableItemStacks, index, count);
    }
    
    /** Removes a stack from the given slot and returns it. */
    @Nullable
    public ItemStack removeStackFromSlot(int index){
        return null; // ItemStackHelper.getAndRemove(this.cardtableItemStacks, index);
    }
    
    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    public void setInventorySlotContents(int index, @Nullable ItemStack stack){
        boolean flag = stack != null && stack.isItemEqual(this.cardtableItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.cardtableItemStacks[index]);
        this.cardtableItemStacks[index] = stack;
        //if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
        //    stack.stackSize = this.getInventoryStackLimit();
        //}
        if (index == 0 && !flag){
            this.markDirty();
        }
    }
    
    /** Get the name of this object. For players this returns their username */
    public String getName(){
        return "container.furnace";
    }
    
    /** Returns true if this thing is named */
    public boolean hasCustomName(){
        return false;
    }
    
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        //this.ownerID     = compound.getInteger("OwnerID");
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.cardtableItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");
            if (j >= 0 && j < this.cardtableItemStacks.length){
                //this.cardtableItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        //compound.setInteger("OwnerID", this.ownerID);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.cardtableItemStacks.length; ++i){
            if (this.cardtableItemStacks[i] != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.cardtableItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);
        return compound;
    }
    
    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. */
    public int getInventoryStackLimit(){
        return 64;
    }
    
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    /** Do not make give this method the name canInteractWith because it clashes with Container */
    public boolean isUseableByPlayer(EntityPlayer player){
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
    
    public void openInventory(EntityPlayer player){
    	
    }
    
    public void closeInventory(EntityPlayer player){
    	
    }
    
    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack){
        if (index == 2){
            return false;
        } else if (index != 1){
            return true;
        } else {
            ItemStack itemstack = this.cardtableItemStacks[0];
            return true;
        }
    }
    
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerCardTable(playerInventory, this);
    }
    
    public int getField(int id){
    	return 0;
    }
    
    public void setField(int id, int value){
    	
    }
    
    public int getFieldCount(){
        return 1;
    }
    
    public void clear(){
        for (int i = 0; i < this.cardtableItemStacks.length; ++i){
            this.cardtableItemStacks[i] = null;
        }
    }

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}
}