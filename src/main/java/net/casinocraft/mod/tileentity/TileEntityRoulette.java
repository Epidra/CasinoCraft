package net.casinocraft.mod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerRoulette;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRoulette extends TileEntityCasino {
	
	public TileEntityRoulette(){
		
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerRoulette(playerInventory, this);
    }
	
	public void start(){
		
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
}