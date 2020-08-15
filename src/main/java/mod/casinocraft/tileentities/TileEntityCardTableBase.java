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

public class TileEntityCardTableBase extends TileEntityBoard {

	public TileEntityCardTableBase(){
		super(EnumDyeColor.BLACK, 1);
	}
	
	public TileEntityCardTableBase(EnumDyeColor color, int tableID){
		super(color, tableID);
	}
    
    /** Get the name of this object. For players this returns their username */
    public String getName(){
        return "container.cardtablebase";
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

	///** ??? */
	//public void readFromNBT(NBTTagCompound compound){
	//	super.readFromNBT(compound);
	//	this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
	//	ItemStackHelper.loadAllItems(compound, this.inventory);
	//}
//
	///** ??? */
	//public NBTTagCompound writeToNBT(NBTTagCompound compound){
	//	super.writeToNBT(compound);
	//	ItemStackHelper.saveAllItems(compound, this.inventory);
	//	return compound;
	//}
	
}
