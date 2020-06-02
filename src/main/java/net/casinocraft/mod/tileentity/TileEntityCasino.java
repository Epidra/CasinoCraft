package net.casinocraft.mod.tileentity;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.CasinoKeeper;
import net.casinocraft.mod.blocks.BlockCardTable;
import net.casinocraft.mod.container.ContainerArcade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCasino extends TileEntity implements ITickable, IInventory{
	
    /** The ItemStacks that hold the items currently being used in the furnace */
	public ItemStack[] arcadeItemStacks = new ItemStack[1];
    
	public EnumDyeColor color;
	
	private ItemStack content;
	
	private UUID ownerID;
	
	public Random rand = new Random();
	
	public TileEntityCasino(){
		
	}
	
	public UUID Get_Owner(){
		return ownerID;
	}
	
	public void Set_Owner(UUID id){
		ownerID = id;
	}
	
    /** Returns the number of slots in the inventory. */
    public int getSizeInventory(){
        return this.arcadeItemStacks.length;
    }
    
    /** Returns the stack in the given slot. */
    @Nullable
    public ItemStack getStackInSlot(int index){
        return this.arcadeItemStacks[index];
    }
    
    /** Removes up to a specified number of items from an inventory slot and returns them in a new stack. */
    @Nullable
    public ItemStack decrStackSize(int index, int count){
        return null; // ItemStackHelper.getAndSplit(this.arcadeItemStacks, index, count);
    }
    
    /** Removes a stack from the given slot and returns it. */
    @Nullable
    public ItemStack removeStackFromSlot(int index){
        return null; // ItemStackHelper.getAndRemove(this.arcadeItemStacks, index);
    }
    
    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    public void setInventorySlotContents(int index, @Nullable ItemStack stack){
        boolean flag = stack != null && stack.isItemEqual(this.arcadeItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.arcadeItemStacks[index]);
        this.arcadeItemStacks[index] = stack;
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
        NBTTagList nbttaglist = compound.getTagList("Items", 1);
        this.arcadeItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");
            if (j >= 0 && j < this.arcadeItemStacks.length){
                //this.arcadeItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        //compound.setInteger("OwnerID", this.ownerID);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.arcadeItemStacks.length; ++i){
            if (this.arcadeItemStacks[i] != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.arcadeItemStacks[i].writeToNBT(nbttagcompound);
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
    	if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.blockCardTable){
    		color = this.world.getBlockState(getPos()).getValue(BlockCardTable.COLOR);
    	}
    	if(content == null){
    		if(arcadeItemStacks[0] != null){
    			content = arcadeItemStacks[0];
    			this.markDirty();
    		}
    	} else if(arcadeItemStacks[0] != null){
    		if(arcadeItemStacks[0] != content){
    			content = arcadeItemStacks[0];
    			this.markDirty();
    		}
    	}
    }
    
    /** Do not make give this method the name canInteractWith because it clashes with Container */
    public boolean isUseableByPlayer(EntityPlayer player){
        return true;
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
            ItemStack itemstack = this.arcadeItemStacks[0];
            return true;
        }
    }
    
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerArcade(playerInventory, this);
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
        for (int i = 0; i < this.arcadeItemStacks.length; ++i){
            this.arcadeItemStacks[i] = null;
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