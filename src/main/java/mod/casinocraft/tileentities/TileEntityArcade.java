package mod.casinocraft.tileentities;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityArcade extends TileEntity implements IInventory, ITickable {
	
	public NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
	
	Item token = Item.getItemFromBlock(Blocks.AIR);
	int bet_storage = 0;
	int bet_bet = 0;
	int bet_high = 0;
	boolean transfer_in = false;
	boolean transfer_out = false;
	
	public TileEntityArcade(){
		
	}
	
    /** Returns the number of slots in the inventory. */
    public int getSizeInventory(){
        return this.inventory.size();
    }
    
    /** Returns the stack in the given slot. */
    public ItemStack getStackInSlot(int index){
        return this.inventory.get(index);
    }
    
    /** Removes up to a specified number of items from an inventory slot and returns them in a new stack. */
    public ItemStack decrStackSize(int index, int count){
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }
    
    /** Removes a stack from the given slot and returns it. */
    public ItemStack removeStackFromSlot(int index){
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }
    
    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    public void setInventorySlotContents(int index, ItemStack stack){
        ItemStack itemstack = this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()){
            stack.setCount(this.getInventoryStackLimit());
        }
        if (index == 0 && !flag){
            this.markDirty();
        }
    }
    
    /** Get the name of this object. For players this returns their username */
    public String getName(){
        return "container.arcade";
    }
    
    /** Returns true if this thing is named */
    public boolean hasCustomName(){
        return false;
    }
    
    /** When the world loads from disk, the server needs to send the TileEntity information to the client
	*  it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this:
	*  getUpdatePacket() and onDataPacket() are used for one-at-a-time TileEntity updates
	*  getUpdateTag() and handleUpdateTag() are used by vanilla to collate together into a single chunk update packet
	* In this case, we need it for the gem colour.  There's no need to save the gem angular position because
	*  the player will never notice the difference and the client<-->server synchronisation lag will make it
	*  inaccurate anyway*/
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}
	
	/** ??? */
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	/** Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client */
	@Override
	public NBTTagCompound getUpdateTag(){
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}
	
	/** Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client */
	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		this.readFromNBT(tag);
	}
    
    /** ??? */
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
    }
    
    /** ??? */
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        return compound;
    }
    
    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. */
    public int getInventoryStackLimit(){
        return 64;
    }
    
    /** Do not make give this method the name canInteractWith because it clashes with Container */
    public boolean isUseableByPlayer(EntityPlayer player){
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
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
            ItemStack itemstack = this.inventory.get(0);
            return true;
        }
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
        for (int i = 0; i < this.inventory.size(); ++i){
            this.inventory.set(i, null);
        }
    }

    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
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
