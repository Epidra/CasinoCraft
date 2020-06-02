package net.casinocraft.mod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerCardTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.passive.HorseType;
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
import net.minecraft.server.management.PreYggdrasilConverter;
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
        return ItemStackHelper.getAndSplit(this.cardtableItemStacks, index, count);
    }
    
    /** Removes a stack from the given slot and returns it. */
    @Nullable
    public ItemStack removeStackFromSlot(int index){
        return ItemStackHelper.getAndRemove(this.cardtableItemStacks, index);
    }
    
    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    public void setInventorySlotContents(int index, @Nullable ItemStack stack){
        boolean flag = stack != null && stack.isItemEqual(this.cardtableItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.cardtableItemStacks[index]);
        this.cardtableItemStacks[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }
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
                this.cardtableItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
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
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    /*public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("EatingHaystack", this.isEatingHaystack());
        compound.setBoolean("ChestedHorse", this.isChested());
        compound.setBoolean("HasReproduced", this.getHasReproduced());
        compound.setBoolean("Bred", this.isBreeding());
        compound.setInteger("Type", this.getType().getOrdinal());
        compound.setInteger("Variant", this.getHorseVariant());
        compound.setInteger("Temper", this.getTemper());
        compound.setBoolean("Tame", this.isTame());
        compound.setBoolean("SkeletonTrap", this.isSkeletonTrap());
        compound.setInteger("SkeletonTrapTime", this.skeletonTrapTime);

        if (this.getOwnerUniqueId() != null)
        {
            compound.setString("OwnerUUID", this.getOwnerUniqueId().toString());
        }

        if (this.isChested())
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 2; i < this.horseChest.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.horseChest.getStackInSlot(i);

                if (itemstack != null)
                {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.setByte("Slot", (byte)i);
                    itemstack.writeToNBT(nbttagcompound);
                    nbttaglist.appendTag(nbttagcompound);
                }
            }

            compound.setTag("Items", nbttaglist);
        }

        if (this.horseChest.getStackInSlot(1) != null)
        {
            compound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }

        if (this.horseChest.getStackInSlot(0) != null)
        {
            compound.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
        }
    }*/

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    /*public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setEatingHaystack(compound.getBoolean("EatingHaystack"));
        this.setBreeding(compound.getBoolean("Bred"));
        this.setChested(compound.getBoolean("ChestedHorse"));
        this.setHasReproduced(compound.getBoolean("HasReproduced"));
        this.setType(HorseType.getArmorType(compound.getInteger("Type")));
        this.setHorseVariant(compound.getInteger("Variant"));
        this.setTemper(compound.getInteger("Temper"));
        this.setHorseTamed(compound.getBoolean("Tame"));
        this.setSkeletonTrap(compound.getBoolean("SkeletonTrap"));
        this.skeletonTrapTime = compound.getInteger("SkeletonTrapTime");
        String s;

        if (compound.hasKey("OwnerUUID", 8))
        {
            s = compound.getString("OwnerUUID");
        }
        else
        {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }

        if (!s.isEmpty())
        {
            this.setOwnerUniqueId(UUID.fromString(s));
        }

        IAttributeInstance iattributeinstance = this.getAttributeMap().getAttributeInstanceByName("Speed");

        if (iattributeinstance != null)
        {
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(iattributeinstance.getBaseValue() * 0.25D);
        }

        if (this.isChested())
        {
            NBTTagList nbttaglist = compound.getTagList("Items", 10);
            this.initHorseChest();

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;

                if (j >= 2 && j < this.horseChest.getSizeInventory())
                {
                    this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
                }
            }
        }

        if (compound.hasKey("ArmorItem", 10))
        {
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("ArmorItem"));

            if (itemstack != null && HorseArmorType.isHorseArmor(itemstack.getItem()))
            {
                this.horseChest.setInventorySlotContents(1, itemstack);
            }
        }

        if (compound.hasKey("SaddleItem", 10))
        {
            ItemStack itemstack1 = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("SaddleItem"));

            if (itemstack1 != null && itemstack1.getItem() == Items.SADDLE)
            {
                this.horseChest.setInventorySlotContents(0, itemstack1);
            }
        }

        this.updateHorseSlots();
    }*/
    
    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. */
    public int getInventoryStackLimit(){
        return 64;
    }
    
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    /** Do not make give this method the name canInteractWith because it clashes with Container */
    public boolean isUseableByPlayer(EntityPlayer player){
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
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
}