package mod.casinocraft.tileentities;

import javax.annotation.Nullable;

import mod.casinocraft.CasinoKeeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityCardTable extends TileEntity implements IInventory, ITickable {
	
	public NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

	public EnumDyeColor color;
	
	public Item token = Item.getItemFromBlock(Blocks.AIR);
	public int bet_storage = 0;
	public int bet_low  = 0;
	public int bet_high = 0;
	public boolean transfer_in  = false;
	public boolean transfer_out = false;
	
	public TileEntityCardTable(){
		
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
        return "container.cardtable";
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
    	
    	if(transfer_in) {
    		if(inventory.get(2).getCount() > 0 && (bet_storage == 0 || inventory.get(2).getItem() == token)) {
    			if(token == Item.getItemFromBlock(Blocks.AIR)) token = inventory.get(2).getItem();
    			inventory.get(2).shrink(1);
    			bet_storage++;
    		}
    	}
    	if(transfer_out) {
    		if(bet_storage > 0 && (token == inventory.get(3).getItem() || inventory.get(3).isEmpty())) {
    			if(inventory.get(3).isEmpty()) {
    				inventory.set(3, new ItemStack(token, 1));
    				bet_storage--;
    			}else if(inventory.get(3).getCount() < 64) {
    				inventory.get(3).grow(1);
    				bet_storage--;
    			}
    			if(bet_storage == 0) {
    				token = Item.getItemFromBlock(Blocks.AIR);
    			}
    		}
    	}
    	
    	
    	
    	
    	
    	     if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_WHITE    ){ color = EnumDyeColor.WHITE     ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_ORANGE   ){ color = EnumDyeColor.ORANGE    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_MAGENTA  ){ color = EnumDyeColor.MAGENTA   ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_LIGHTBLUE){ color = EnumDyeColor.LIGHT_BLUE; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_YELLOW   ){ color = EnumDyeColor.YELLOW    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_LIME     ){ color = EnumDyeColor.LIME      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_PINK     ){ color = EnumDyeColor.PINK      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_GRAY     ){ color = EnumDyeColor.GRAY      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_SILVER   ){ color = EnumDyeColor.SILVER    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_CYAN     ){ color = EnumDyeColor.CYAN      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_PURPLE   ){ color = EnumDyeColor.PURPLE    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_BLUE     ){ color = EnumDyeColor.BLUE      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_BROWN    ){ color = EnumDyeColor.BROWN     ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_GREEN    ){ color = EnumDyeColor.GREEN     ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_RED      ){ color = EnumDyeColor.RED       ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BLACK    ){ color = EnumDyeColor.BLACK     ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_WHITE    ){ color = EnumDyeColor.WHITE     ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_ORANGE   ){ color = EnumDyeColor.ORANGE    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_MAGENTA  ){ color = EnumDyeColor.MAGENTA   ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_LIGHTBLUE){ color = EnumDyeColor.LIGHT_BLUE; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_YELLOW   ){ color = EnumDyeColor.YELLOW    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_LIME     ){ color = EnumDyeColor.LIME      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_PINK     ){ color = EnumDyeColor.PINK      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_GRAY     ){ color = EnumDyeColor.GRAY      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_SILVER   ){ color = EnumDyeColor.SILVER    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_CYAN     ){ color = EnumDyeColor.CYAN      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_PURPLE   ){ color = EnumDyeColor.PURPLE    ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BLUE     ){ color = EnumDyeColor.BLUE      ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BROWN    ){ color = EnumDyeColor.BROWN     ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_GREEN    ){ color = EnumDyeColor.GREEN     ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_RED      ){ color = EnumDyeColor.RED       ; }
    	else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BLACK    ){ color = EnumDyeColor.BLACK     ; }
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
