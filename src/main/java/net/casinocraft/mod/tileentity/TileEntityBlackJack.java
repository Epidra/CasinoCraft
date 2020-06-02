package net.casinocraft.mod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBlackJack extends TileEntity implements IInventory {
	
	private ItemStack[] itemStacks = new ItemStack[3];
	
	ItemStack bet;
	
	public int hand = 0;
	public int field = 0;
	
	public int valuePlayer = 0;
	public int valueDealer = 0;
	
	public int[] cardPx = new int[10];
	public int[] cardPy = new int[10];
	public int[] cardDx = new int[10];
	public int[] cardDy = new int[10];
	
	public boolean active_bet = true;
	public boolean active_won = false;
	
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
        return "container.blackjack";
    }
    
    public boolean hasCustomInventoryName(){
        return false;
    }
	
    public int getInventoryStackLimit(){
        return 64;
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
        return slot == 2 ? false : true;
    }
    
    public void Click_Start(int stage){
    	if(stage == 0){
    		active_bet = false;
    		active_won = false;
    		cardPx[0] = -1; cardPy[0] = -1;
    		cardPx[1] = -1; cardPy[1] = -1;
    		cardPx[2] = -1; cardPy[2] = -1;
    		cardPx[3] = -1; cardPy[3] = -1;
    		cardPx[4] = -1; cardPy[4] = -1;
    		cardPx[5] = -1; cardPy[5] = -1;
    		cardPx[6] = -1; cardPy[6] = -1;
    		cardPx[7] = -1; cardPy[7] = -1;
    		cardPx[8] = -1; cardPy[8] = -1;
    		cardPx[9] = -1; cardPy[9] = -1;
    		
    		cardDx[0] = -1; cardDy[0] = -1;
    		cardDx[1] = -1; cardDy[1] = -1;
    		cardDx[2] = -1; cardDy[2] = -1;
    		cardDx[3] = -1; cardDy[3] = -1;
    		cardDx[4] = -1; cardDy[4] = -1;
    		cardDx[5] = -1; cardDy[5] = -1;
    		cardDx[6] = -1; cardDy[6] = -1;
    		cardDx[7] = -1; cardDy[7] = -1;
    		cardDx[8] = -1; cardDy[8] = -1;
    		cardDx[9] = -1; cardDy[9] = -1;
    		
    		cardPx[0] = worldObj.rand.nextInt(13)+1; cardPy[0] = worldObj.rand.nextInt(4);
    		cardPx[1] = worldObj.rand.nextInt(13)+1; cardPy[1] = worldObj.rand.nextInt(4);
    		cardDx[0] = worldObj.rand.nextInt(13)+1; cardDy[0] = worldObj.rand.nextInt(4);
    		cardDx[1] = worldObj.rand.nextInt(13)+1; cardDy[1] = worldObj.rand.nextInt(4);
    	}else if(stage == 1){
    		active_bet = true;
    		active_won = false;
    		cardPx[0] = -1; cardPy[0] = -1;
    		cardPx[1] = -1; cardPy[1] = -1;
    		cardPx[2] = -1; cardPy[2] = -1;
    		cardPx[3] = -1; cardPy[3] = -1;
    		cardPx[4] = -1; cardPy[4] = -1;
    		cardPx[5] = -1; cardPy[5] = -1;
    		cardPx[6] = -1; cardPy[6] = -1;
    		cardPx[7] = -1; cardPy[7] = -1;
    		cardPx[8] = -1; cardPy[8] = -1;
    		cardPx[9] = -1; cardPy[9] = -1;
    		
    		cardDx[0] = -1; cardDy[0] = -1;
    		cardDx[1] = -1; cardDy[1] = -1;
    		cardDx[2] = -1; cardDy[2] = -1;
    		cardDx[3] = -1; cardDy[3] = -1;
    		cardDx[4] = -1; cardDy[4] = -1;
    		cardDx[5] = -1; cardDy[5] = -1;
    		cardDx[6] = -1; cardDy[6] = -1;
    		cardDx[7] = -1; cardDy[7] = -1;
    		cardDx[8] = -1; cardDy[8] = -1;
    		cardDx[9] = -1; cardDy[9] = -1;
    	}
    }
    
    public void Click_Hit(){
		valuePlayer = 0;
    	for(int i = 0; i < 10; i++){
    		if(cardPx[i] == -1){
    			cardPx[i] = worldObj.rand.nextInt(13)+1;
    			cardPy[i] = worldObj.rand.nextInt(4);
    			break;
    		}
    	}
        int ace = 0;
        for(int i = 0; i < 10; i++){
        	if(cardPx[i] != -1){
        		if(cardPx[i] == 13) {
                    ace++;
                } else if(cardPx[i] <= 9) {
                    valuePlayer = valuePlayer + cardPx[i] + 1;
                } else {
                    valuePlayer = valuePlayer + 10;
                }
        	}
        }
        if(ace > 0) {
            while(ace > 0) {
                if(valuePlayer <= 10) {
                    valuePlayer = valuePlayer + 11;
                } else {
                    valuePlayer = valuePlayer + 1;
                }
                ace--;
            }
        }
        if(valuePlayer > 21) {
        	Result();
        }
    }
    
    public void Click_Stand(){
    	boolean temp = true;
    	while(temp){
    		valueDealer = 0;
            int ace = 0;
            for(int i = 0; i< 10; i++){
            	if(cardDx[i] != -1){
            		if(cardDx[i] == 13) {
                        ace++;
                    } else if(cardDx[i] <= 9) {
                        valueDealer = valueDealer + cardDx[i] + 1;
                    } else {
                        valueDealer = valueDealer + 10;
                    }
            	}
            }
            if(ace > 0) {
                while(ace > 0) {
                    if(valueDealer <= 10) {
                        valueDealer = valueDealer + 11;
                    } else {
                        valueDealer = valueDealer + 1;
                    }
                    ace--;
                }
            }
            if(valueDealer < 17) {
                valueDealer = 0;
                for(int i = 0; i < 10; i++){
            		if(cardDx[i] == -1){
            			cardDx[i] = worldObj.rand.nextInt(13)+1;
            			cardDy[i] = worldObj.rand.nextInt(4);
            			break;
            		}
            	}
                ace = 0;
                for(int i = 0; i< 10; i++){
                	if(cardDx[i] != -1){
                		if(cardDx[i] == 13) {
                            ace++;
                        } else if(cardDx[i] <= 9) {
                            valueDealer = valueDealer + cardDx[i] + 1;
                        } else {
                            valueDealer = valueDealer + 10;
                        }
                	}
                }
                if(ace > 0) {
                    while(ace > 0) {
                        if(valueDealer <= 10) {
                            valueDealer = valueDealer + 11;
                        } else {
                            valueDealer = valueDealer + 1;
                        }
                        ace--;
                    }
                }
            }
            if(valueDealer > 16) temp = false;
    	}
    	Result();
    }
    
    private void Result(){
    	active_bet = false;
    	active_won = true;
    	valuePlayer = 0;
        int ace = 0;
        for(int i = 0; i < 10; i++){
        	if(cardPx[i] != -1){
        		if(cardPx[i] == 13) {
                    ace++;
                } else if(cardPx[i] <= 9) {
                    valuePlayer = valuePlayer + cardPx[i] + 1;
                } else {
                    valuePlayer = valuePlayer + 10;
                }
        	}
        }
        if(ace > 0) {
            while(ace > 0) {
                if(valuePlayer <= 10) {
                    valuePlayer = valuePlayer + 11;
                } else {
                    valuePlayer = valuePlayer + 1;
                }
                ace--;
            }
        }
    	valueDealer = 0;
        ace = 0;
        for(int i = 0; i< 10; i++){
        	if(cardDx[i] != -1){
        		if(cardDx[i] == 13) {
                    ace++;
                } else if(cardDx[i] <= 9) {
                    valueDealer = valueDealer + cardDx[i] + 1;
                } else {
                    valueDealer = valueDealer + 10;
                }
        	}
        }
        if(ace > 0) {
            while(ace > 0) {
                if(valueDealer <= 10) {
                    valueDealer = valueDealer + 11;
                } else {
                    valueDealer = valueDealer + 1;
                }
                ace--;
            }
        }
    	int cardsPlayer = 0;
    	int cardsDealer = 0;
    	for(int i = 0; i < 10; i++){ if(cardPx[i] != -1) cardsPlayer++; }
    	for(int i = 0; i < 10; i++){ if(cardDx[i] != -1) cardsDealer++; }
    	if(valueDealer > 21) {
            hand = 1; field = 1;
        } else if(valuePlayer > 21) {
            hand = 2; field = 0;
        } else if(valuePlayer == valueDealer && cardsPlayer > cardsDealer) {
            hand = 2; field = 0;
        } else if(valuePlayer == valueDealer && cardsPlayer == cardsDealer) {
            hand = 1; field = 1;
        } else if(valuePlayer == valueDealer && cardsPlayer < cardsDealer) {
            hand = 1; field = 1;
        } else if(valuePlayer > valueDealer) {
            hand = 1; field = 1;
        } else if(valuePlayer < valueDealer) {
            hand = 2; field = 0;
        }
    }
    
}
