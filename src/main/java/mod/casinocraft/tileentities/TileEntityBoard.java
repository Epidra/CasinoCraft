package mod.casinocraft.tileentities;

import javax.annotation.Nullable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blocks.BlockArcade.EnumModule;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.card.*;
import mod.casinocraft.logic.chip.*;
import mod.casinocraft.logic.mino.*;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.logic.other.LogicSlotGame;
import mod.casinocraft.network.MessageModuleServer;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

public class TileEntityBoard extends TileEntity implements IInventory, ITickable {
	
	/** 0 - lock, 1 - module, 2 - inventoryIN, 3 - inventoryOUT, 4 - inventoryTOKEN, 5 - scoreTOKEN **/
	public NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);

	public int bet_storage = 0;
	public int bet_low  = 0;
	public int bet_high = 0;
	public boolean transfer_in  = false;
	public boolean transfer_out = false;
	public boolean isCreative = false;
	private Item lastModule = Items.FLINT;
	public LogicBase LOGIC;
	public EnumDyeColor color;
	public final int tableID;
	public Item getKey(){
		return inventory.get(0).getItem();
	}
	
	public TileEntityBoard(EnumDyeColor color, int tableID){
		LOGIC = new LogicDummy(tableID);
		this.color = color;
		this.tableID = tableID;
	}
	
	public Item getModule() {
		return inventory.get(1).getItem();
	}
	
	public Item getToken() {
		return inventory.get(4).getItem();
	}
	
	public boolean isToken(ItemStack itemstack) {
		return itemstack.getItem() == inventory.get(4).getItem() && itemstack.getMetadata() == inventory.get(4).getMetadata();
	}
	
	public ItemStack getTokenStack() {
		return inventory.get(4);
	}
	
	public void setToken(ItemStack itemstack) {
		inventory.set(4, new ItemStack(itemstack.getItem(), 1, itemstack.getMetadata()));
	}
	
	public Item getScoreToken() {
		return inventory.get(5).getItem();
	}
	
	public void setScoreToken(Item item) {
		inventory.set(5, new ItemStack(item));
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
        return "container.casinoboard";
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
		bet_storage = compound.getInteger("storage");
		bet_low  = compound.getInteger("low");
		bet_high = compound.getInteger("high");
		isCreative = compound.getBoolean("iscreative");
		this.inventory = NonNullList.withSize(6, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.inventory);
		lastModule = getModule();
		LOGIC = setLogic();
		LOGIC.load(compound);
		//if(!world.isRemote){
			if(tableID == 0){
				CasinoPacketHandler.INSTANCE.sendToServer(new MessageModuleServer(getPos()));
			}
		//}
    }
    
    /** ??? */
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("storage", bet_storage);
		compound.setInteger("low", bet_low);
		compound.setInteger("high", bet_high);
		compound.setBoolean("iscreative", isCreative);
		ItemStackHelper.saveAllItems(compound, this.inventory);
		LOGIC.save(compound);
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

		changeLogic();
		boolean isDirty = false;
		if(transfer_in) {
			if(inventory.get(2).getCount() > 0 && (bet_storage == 0 || isToken(inventory.get(2)))) {
				if(getToken() == Item.getItemFromBlock(Blocks.AIR)) setToken(inventory.get(2));
				int count = CasinoKeeper.config_fastload ? inventory.get(2).getCount() : 1;
				inventory.get(2).shrink(count);
				bet_storage+=count;
				if(inventory.get(2).getCount() <= 0) inventory.set(2, new ItemStack(Blocks.AIR));
				isDirty = true;
			}
		}
		if(transfer_out) {
			if(bet_storage > 0 && (isToken(inventory.get(3)) || inventory.get(3).isEmpty())) {
				if(inventory.get(3).isEmpty()) {
					int count = CasinoKeeper.config_fastload ? Math.min(bet_storage, 64) : 1;
					inventory.set(3, new ItemStack(getTokenStack().getItem(), count));
					bet_storage-=count;
					isDirty = true;
				}else if(inventory.get(3).getCount() < 64) {
					int count = CasinoKeeper.config_fastload ? Math.min(bet_storage, 64 - inventory.get(3).getCount()) : 1;
					inventory.get(3).grow(count);
					bet_storage-=count;
					isDirty = true;
				}
				if(bet_storage == 0) {
					setToken(new ItemStack(Blocks.AIR));
					isDirty = true;
				}
			}
		}
		if (isDirty){
			this.markDirty();
		}

		if(LOGIC.turnstate > 1 && LOGIC.turnstate < 6){
			LOGIC.update();
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
		return false;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void changeLogic(){
		if(lastModule != getModule()){
			lastModule = getModule();
			LOGIC = setLogic();
		}
	}

	private LogicBase setLogic(){
		if(this instanceof TileEntityArcade){
			if(getModule() == CasinoKeeper.MODULE_CHIP_WHITE)     return new LogicChipWhite(    tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_ORANGE)    return new LogicChipOrange(   tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_MAGENTA)   return new LogicChipMagenta(  tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_LIGHTBLUE) return new LogicChipLightBlue(tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_YELLOW)    return new LogicChipYellow(   tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_LIME)      return new LogicChipLime(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_PINK)      return new LogicChipPink(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_GRAY)      return new LogicChipGray(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_SILVER)    return new LogicChipLightGray(tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_CYAN)      return new LogicChipCyan(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_PURPLE)    return new LogicChipPurple(   tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_BLUE)      return new LogicChipBlue(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_BROWN)     return new LogicChipBrown(    tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_GREEN)     return new LogicChipGreen(    tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_RED)       return new LogicChipRed(      tableID);
			if(getModule() == CasinoKeeper.MODULE_CHIP_BLACK)     return new LogicChipBlack(    tableID);
		}
		if(this instanceof TileEntityCardTableBase || this instanceof TileEntityCardTableWide){
			if(getModule() == CasinoKeeper.MODULE_CARD_WHITE)     return new LogicCardWhite(    tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_ORANGE)    return new LogicCardOrange(   tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_MAGENTA)   return new LogicCardMagenta(  tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_LIGHTBLUE) return new LogicCardLightBlue(tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_YELLOW)    return new LogicCardYellow(   tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_LIME)      return new LogicCardLime(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_PINK)      return new LogicCardPink(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_GRAY)      return new LogicCardGray(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_SILVER)    return new LogicCardLightGray(tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_CYAN)      return new LogicCardCyan(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_PURPLE)    return new LogicCardPurple(   tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_BLUE)      return new LogicCardBlue(     tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_BROWN)     return new LogicCardBrown(    tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_GREEN)     return new LogicCardGreen(    tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_RED)       return new LogicCardRed(      tableID);
			if(getModule() == CasinoKeeper.MODULE_CARD_BLACK)     return new LogicCardBlack(    tableID);

			if(getModule() == CasinoKeeper.MODULE_MINO_WHITE)     return new LogicMinoWhite(    tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_ORANGE)    return new LogicMinoOrange(   tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_MAGENTA)   return new LogicMinoMagenta(  tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_LIGHTBLUE) return new LogicMinoLightBlue(tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_YELLOW)    return new LogicMinoYellow(   tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_LIME)      return new LogicMinoLime(     tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_PINK)      return new LogicMinoPink(     tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_GRAY)      return new LogicMinoGray(     tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_SILVER)    return new LogicMinoLightGray(tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_CYAN)      return new LogicMinoCyan(     tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_PURPLE)    return new LogicMinoPurple(   tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_BLUE)      return new LogicMinoBlue(     tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_BROWN)     return new LogicMinoBrown(    tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_GREEN)     return new LogicMinoGreen(    tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_RED)       return new LogicMinoRed(      tableID);
			if(getModule() == CasinoKeeper.MODULE_MINO_BLACK)     return new LogicMinoBlack(    tableID);
		}
		if(this instanceof TileEntitySlotMachine){
			if(getModule() != Item.getItemFromBlock(Blocks.AIR)) return new LogicSlotGame(tableID);
		}
		return new LogicDummy(tableID);
	}
	
}
