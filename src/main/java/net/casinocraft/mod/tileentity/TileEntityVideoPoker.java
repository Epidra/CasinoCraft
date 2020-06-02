package net.casinocraft.mod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityVideoPoker extends TileEntity implements IInventory {
	
private ItemStack[] itemStacks = new ItemStack[8];
public boolean active_won = false;
public boolean active_bet = true;
public ItemStack bet = null;
public int card1x = 0;
public int card1y = 0;
public int card2x = 0;
public int card2y = 0;
public int card3x = 0;
public int card3y = 0;
public int card4x = 0;
public int card4y = 0;
public int card5x = 0;
public int card5y = 0;
public boolean hold1 = false;
public boolean hold2 = false;
public boolean hold3 = false;
public boolean hold4 = false;
public boolean hold5 = false;
public int hand = 0;
public int field = 0;
	
	public int getSizeInventory(){
        return this.itemStacks.length;
    }
    
    public ItemStack getStackInSlot(int slot){
        return this.itemStacks[slot];
    }
    
    public ItemStack decrStackSize(int slot, int value){
        if (this.itemStacks[slot] != null){
            ItemStack itemstack;
            if (this.itemStacks[slot].stackSize <= value){
                itemstack = this.itemStacks[slot];
                this.itemStacks[slot] = null;
                return itemstack;
            }else{
                itemstack = this.itemStacks[slot].splitStack(value);
                if (this.itemStacks[slot].stackSize == 0){
                    this.itemStacks[slot] = null;
                }
                return itemstack;
            }
        }else{
            return null;
        }
    }
    
    public ItemStack getStackInSlotOnClosing(int slot){
        if (this.itemStacks[slot] != null){
            ItemStack itemstack = this.itemStacks[slot];
            this.itemStacks[slot] = null;
            return itemstack;
        }else{
            return null;
        }
    }
    
    public void setInventorySlotContents(int slot, ItemStack stack){
        this.itemStacks[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    public String getInventoryName(){
        return "container.videopoker";
    }
    
    public boolean hasCustomInventoryName(){
        return false;
    }
	
    public int getInventoryStackLimit(){
        return 64;
    }
    
    public void readFromNBT(NBTTagCompound nbt){
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 8);
        this.itemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.itemStacks.length){
                this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }
    
    public void writeToNBT(NBTTagCompound nbt){
        super.writeToNBT(nbt);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.itemStacks.length; ++i){
            if (this.itemStacks[i] != null){
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.itemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbt.setTag("Items", nbttaglist);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tagCompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound tag = pkt.func_148857_g();
        readFromNBT(tag);
    }
    
    public boolean isUseableByPlayer(EntityPlayer player){
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }
    
    public void openInventory() {}
    public void closeInventory() {}
    
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        //if(slot == 0){
        	return true;
        //}else{
        //	return false;
        //}
    }
	
    public void updateEntity(){
    	if(!worldObj.isRemote){
    		System.out.println("CLIENT: " + itemStacks[0]);
    		System.out.println("CLIENT: " + itemStacks[1]);
    		System.out.println("CLIENT: " + itemStacks[2]);
    	}else{
    		System.out.println("SERVER: " + itemStacks[0]);
    		System.out.println("SERVER: " + itemStacks[1]);
    		System.out.println("SERVER: " + itemStacks[2]);
    	}
    	if(!active_bet && !active_won){
    		if(itemStacks[0] != null) itemStacks[0] = null;
    		if(itemStacks[1] != null) itemStacks[1] = null;
    		if(itemStacks[2] != null) itemStacks[2] = null;
    		if(itemStacks[3] != null) itemStacks[3] = null;
    		if(itemStacks[4] != null) itemStacks[4] = null;
    		if(itemStacks[5] != null) itemStacks[5] = null;
    		if(itemStacks[6] != null) itemStacks[6] = null;
    		if(itemStacks[7] != null) itemStacks[7] = null;
    	}
    	markDirty();
    }
    
    public void Click_Start(int stage){
    	if(stage == 0){
    		bet = null;
    		if(itemStacks[0] != null) bet = itemStacks[0];
    		if(itemStacks[0] != null) itemStacks[0] = null;
    		if(itemStacks[1] != null) itemStacks[1] = null;
    		if(itemStacks[2] != null) itemStacks[2] = null;
    		if(itemStacks[3] != null) itemStacks[3] = null;
    		if(itemStacks[4] != null) itemStacks[4] = null;
    		if(itemStacks[5] != null) itemStacks[5] = null;
    		if(itemStacks[6] != null) itemStacks[6] = null;
    		if(itemStacks[7] != null) itemStacks[7] = null;
        	card1x = worldObj.rand.nextInt(13)+1;
        	card1y = worldObj.rand.nextInt(4);
        	card2x = worldObj.rand.nextInt(13)+1;
        	card2y = worldObj.rand.nextInt(4);
        	card3x = worldObj.rand.nextInt(13)+1;
        	card3y = worldObj.rand.nextInt(4);
        	card4x = worldObj.rand.nextInt(13)+1;
        	card4y = worldObj.rand.nextInt(4);
        	card5x = worldObj.rand.nextInt(13)+1;
        	card5y = worldObj.rand.nextInt(4);
        	hand = 0;
        	active_bet = false;
        	hold1 = false;
        	hold2 = false;
        	hold3 = false;
        	hold4 = false;
        	hold5 = false;
    	}else if(stage == 1){
    		if(!hold1){ card1x = worldObj.rand.nextInt(13)+1; card1y = worldObj.rand.nextInt(4); }
        	if(!hold2){ card2x = worldObj.rand.nextInt(13)+1; card2y = worldObj.rand.nextInt(4); }
        	if(!hold3){ card3x = worldObj.rand.nextInt(13)+1; card3y = worldObj.rand.nextInt(4); }
        	if(!hold4){ card4x = worldObj.rand.nextInt(13)+1; card4y = worldObj.rand.nextInt(4); }
        	if(!hold5){ card5x = worldObj.rand.nextInt(13)+1; card5y = worldObj.rand.nextInt(4); }
    		active_won = true;
    		Sort();
    		if(card1x == 9 && card2x == 10 && card3x == 11 && card4x == 12 && card5x == 13 && card1y == card2y && card1y == card3y && card1y == card4y && card1y == card5y) { // Royal Flush
    			hand = 8; field = 7;
    			if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
        			itemStacks[4] = bet;
        			itemStacks[5] = bet;
        			itemStacks[6] = bet;
        			itemStacks[7] = bet;
    			}
            } else if(card1x <= 9 && card1x + 1 == card2x && card1x + 2 == card3x && card1x + 3 == card4x && card1x + 4 == card5x && card1y == card2y && card1y == card3y && card1y == card4y && card1y == card5y) { // Straight Flush
            	hand = 7; field = 6;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
        			itemStacks[4] = bet;
        			itemStacks[5] = bet;
        			itemStacks[6] = bet;
    			}
            } else if(card1x == card2x && card1x == card3x && card1x == card4x && card1x != card5x) { // 4 of a Kind
            	hand = 6; field = 5;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
        			itemStacks[4] = bet;
        			itemStacks[5] = bet;
    			}
            } else if(card2x == card3x && card2x == card4x && card2x == card5x && card2x != card1x) { // 4 of a Kind
            	hand = 6;  field = 5;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
        			itemStacks[4] = bet;
        			itemStacks[5] = bet;
    			}
            } else if(card1x == card2x && card1x == card3x && card1x != card4x && card4x == card5x) { // Full House
            	hand = 5;  field = 4;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
        			itemStacks[4] = bet;
    			}
            } else if(card1x == card2x && card1x != card3x && card3x == card4x && card3x == card5x) { // Full House
            	hand = 5; field = 4;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
        			itemStacks[4] = bet;
    			}
            } else if(card1y == card2y && card1y == card3y && card1y == card4y && card1y == card5y) { // Flush
            	hand = 4; field = 3;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
    			}
            } else if(card1x <= 9 && card1x + 1 == card2x && card1x + 2 == card3x && card1x + 3 == card4x && card1x + 4 == card5x) { // Straight
            	hand = 3; field = 3;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
        			itemStacks[3] = bet;
    			}
            } else if(card1x == card2x && card1x == card3x && card1x != card4x && card1x != card5x) { // 3 of a Kind
            	hand = 2; field = 2;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
    			}
            } else if(card2x == card3x && card2x == card4x && card2x != card1x && card2x != card5x) { // 3 of a Kind
            	hand = 2; field = 2;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
    			}
            } else if(card3x == card4x && card3x == card5x && card3x != card1x && card3x != card2x) { // 3 of a Kind
            	hand = 2; field = 2;
            	if(bet != null){
    				itemStacks[1] = bet;
        			itemStacks[2] = bet;
    			}
            } else if(card1x == card2x && card3x == card4x) { // Two Pair
            	hand = 1; field = 1;
            	if(bet != null){
    				itemStacks[1] = bet;
    			}
            } else if(card1x == card2x && card4x == card5x) { // Two Pair
            	hand = 1; field = 1;
            	if(bet != null){
    				itemStacks[1] = bet;
    			}
            } else if(card2x == card3x && card4x == card5x) { // Two Pair
            	hand = 1; field = 1;
            	if(bet != null){
    				itemStacks[1] = bet;
    			}
            } else if(card1x >= 9 && card1x == card2x) { // Jacks or Better
            	hand = 0; field = 0;
            } else if(card2x >= 9 && card2x == card3x) { // Jacks or Better
            	hand = 0; field = 0;
            } else if(card3x >= 9 && card3x == card4x) { // Jacks or Better
            	hand = 0; field = 0;
            } else if(card4x >= 9 && card4x == card5x) { // Jacks or Better
            	hand = 0; field = 0;
            }
    	}else{
    		active_bet = true;
    		active_won = false;
    	}
    	markDirty();
    }
    
    public void Click_Hold(int pos){
    	switch(pos){
	    	case 1: if(hold1){ hold1 = false; } else { hold1 = true; } break;
	    	case 2: if(hold2){ hold2 = false; } else { hold2 = true; } break;
	    	case 3: if(hold3){ hold3 = false; } else { hold3 = true; } break;
	    	case 4: if(hold4){ hold4 = false; } else { hold4 = true; } break;
	    	case 5: if(hold5){ hold5 = false; } else { hold5 = true; } break;
    	}
    	markDirty();
    }
    
    private void Sort(){
		if(card1x > card5x) {
            int z1 = card1x; int z2 = card1y;
            card1x = card2x; card1y = card2y;
            card2x = card3x; card2y = card3y;
            card3x = card4x; card3y = card4y;
            card4x = card5x; card4y = card5y;
            card5x = z1; card5y = z2;
        }
        if(card1x > card4x) {
        	int z1 = card1x; int z2 = card1y;
            card1x = card2x; card1y = card2y;
            card2x = card3x; card2y = card3y;
            card3x = card4x; card3y = card4y;
            card4x = z1; card4y = z2;
        }
        if(card1x > card3x) {
        	int z1 = card1x; int z2 = card1y;
            card1x = card2x; card1y = card2y;
            card2x = card3x; card2y = card3y;
            card3x = z1; card3y = z2;
        }
        if(card1x > card2x) {
        	int z1 = card1x; int z2 = card1y;
            card1x = card2x; card1y = card2y;
            card2x = z1; card2y = z2;
        }
    	if(card2x > card5x) {
        	int z1 = card2x; int z2 = card2y;
            card2x = card3x; card2y = card3y;
            card3x = card4x; card3y = card4y;
            card4x = card5x; card4y = card5y;
            card5x = z1; card5y = z2;
        }
        if(card2x > card4x) {
        	int z1 = card2x; int z2 = card2y;
            card2x = card3x; card2y = card3y;
            card3x = card4x; card3y = card4y;
            card4x = z1; card4y = z2;
        }
        if(card2x > card3x) {
        	int z1 = card2x; int z2 = card2y;
            card2x = card3x; card2y = card3y;
            card3x = z1; card3y = z2;
        }
    	if(card3x > card5x) {
        	int z1 = card3x; int z2 = card3y;
            card3x = card4x; card3y = card4y;
            card4x = card5x; card4y = card5y;
            card5x = z1; card5y = z2;
        }
        if(card3x > card4x) {
        	int z1 = card3x; int z2 = card3y;
            card3x = card4x; card3y = card4y;
            card4x = z1; card4y = z2;
        }
        if(card4x > card5x) {
        	int z1 = card4x; int z2 = card4y;
            card4x = card5x; card4y = card5y;
            card5x = z1; card5y = z2;
        }
    }
    
}
