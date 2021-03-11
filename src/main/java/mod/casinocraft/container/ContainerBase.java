package mod.casinocraft.container;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityMachine;
import mod.casinocraft.util.LogicData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class ContainerBase extends Container {

    public  IInventory inventory;
    protected  World world;
    protected  IIntArray casinoData;
    protected  LogicData logicData;
    protected BlockPos pos = new BlockPos(0, 0, 0);
    public DyeColor color;
    public int tableID;
    protected LogicBase LOGIC;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerBase(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.fromLong(packetBuffer.readLong()));
    }

    public ContainerBase(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityMachine) playerInventory.player.getEntityWorld().getTileEntity(pos));
        this.pos = pos;
    }

    public ContainerBase(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityMachine board) {
        super(type, windowID);
        this.inventory = board;
        this.world = playerInventory.player.world;
        this.casinoData = board.casinoData;
        this.logicData = board.logicData;
        color = board.color;
        tableID = board.tableID;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    /** Determines whether supplied player can use this container */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inventory.isUsableByPlayer(playerIn);
    }

    /** Adds Slots from Player Inventory at the default Position **/
    protected void addPlayerSlots(PlayerInventory playerInventory) {
        addPlayerSlots(playerInventory, 8, 56);
    }

    /** Adds Slots from Player Inventory at a specific Position **/
    protected void addPlayerSlots(PlayerInventory playerInventory, int inX, int inY) {
        // Slots for the hotbar
        for (int row = 0; row < 9; ++ row) {
            int x = inX + row * 18;
            int y = inY + 86;
            addSlot(new Slot(playerInventory, row, x, y));
        }
        // Slots for the main inventory
        for (int row = 1; row < 4; ++ row) {
            for (int col = 0; col < 9; ++ col) {
                int x = inX + col * 18;
                int y = row * 18 + (inY + 10);
                addSlot(new Slot(playerInventory, col + row * 9, x, y));
            }
        }
    }




    //----------------------------------------INVENTORY----------------------------------------//

    /** Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s). */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }




    //----------------------------------------GET/SET----------------------------------------//

    public int       getStorageToken(){          return casinoData.get( 0); }
    public int       getStoragePrize(){          return casinoData.get( 1); }
    public int       getBettingLow(){            return casinoData.get( 2); }
    public int       getBettingHigh(){           return casinoData.get( 3); }
    public int       getPrizeScore1(){           return casinoData.get( 4); }
    public int       getPrizeScore2(){           return casinoData.get( 5); }
    public int       getPrizeScore3(){           return casinoData.get( 6); }
    public int       getPrizeCount1(){           return casinoData.get( 7); }
    public int       getPrizeCount2(){           return casinoData.get( 8); }
    public int       getPrizeCount3(){           return casinoData.get( 9); }
    public boolean   getPrizeMode1(){            return casinoData.get(10) == 1; }
    public boolean   getPrizeMode2(){            return casinoData.get(11) == 1; }
    public boolean   getPrizeMode3(){            return casinoData.get(12) == 1; }
    public boolean   getTransferTokenIN(){       return casinoData.get(13) == 1; }
    public boolean   getTransferTokenOUT(){      return casinoData.get(14) == 1; }
    public boolean   getTransferPrizeIN(){       return casinoData.get(15) == 1; }
    public boolean   getTransferPrizeOUT(){      return casinoData.get(16) == 1; }
    public boolean   getSettingInfiniteToken(){  return casinoData.get(17) == 1; }
    public boolean   getSettingInfinitePrize(){  return casinoData.get(18) == 1; }
    public boolean   getSettingDropOnBreak(){    return casinoData.get(19) == 1; }
    public boolean   getSettingIndestructable(){ return casinoData.get(20) == 1; }
    public int       getSettingAlternateColor(){ return casinoData.get(21); }
    public ItemStack getItemToken(){             return inventory.getStackInSlot(3); }
    public ItemStack getItemPrize(){             return inventory.getStackInSlot(4); }

    public void setStorageToken(int value){              casinoData.set( 0, value); }
    public void setStoragePrize(int value){              casinoData.set( 1, value); }
    public void setBettingLow(int value){                casinoData.set( 2, value); }
    public void setBettingHigh(int value){               casinoData.set( 3, value); }
    public void setPrizeScore1(int value){               casinoData.set( 4, value); }
    public void setPrizeScore2(int value){               casinoData.set( 5, value); }
    public void setPrizeScore3(int value){               casinoData.set( 6, value); }
    public void setPrizeCount1(int value){               casinoData.set( 7, value); }
    public void setPrizeCount2(int value){               casinoData.set( 8, value); }
    public void setPrizeCount3(int value){               casinoData.set( 9, value); }
    public void setPrizeMode1(boolean value){            casinoData.set(10, value ? 1 : 0); }
    public void setPrizeMode2(boolean value){            casinoData.set(11, value ? 1 : 0); }
    public void setPrizeMode3(boolean value){            casinoData.set(12, value ? 1 : 0); }
    public void setTransferTokenIN(boolean value){       casinoData.set(13, value ? 1 : 0); }
    public void setTransferTokenOUT(boolean value){      casinoData.set(14, value ? 1 : 0); }
    public void setTransferPrizeIN(boolean value){       casinoData.set(15, value ? 1 : 0); }
    public void setTransferPrizeOUT(boolean value){      casinoData.set(16, value ? 1 : 0); }
    public void setSettingInfiniteToken(boolean value){  casinoData.set(17, value ? 1 : 0); }
    public void setSettingInfinitePrize(boolean value){  casinoData.set(18, value ? 1 : 0); }
    public void setSettingDropOnBreak(boolean value){    casinoData.set(19, value ? 1 : 0); }
    public void setSettingIndestructable(boolean value){ casinoData.set(20, value ? 1 : 0); }
    public void setSettingAlternateColor(int value){     casinoData.set(21, value); }
    public void setItemToken(ItemStack itemStack){       inventory.setInventorySlotContents(3, itemStack); }
    public void setItemPrize(ItemStack itemStack){       inventory.setInventorySlotContents(4, itemStack); }

    public boolean   hasKey(){       return !this.inventory.getStackInSlot(0).isEmpty(); }
    public boolean   hasModule(){    return !this.inventory.getStackInSlot(1).isEmpty(); }
    public boolean   hasToken(){     return casinoData.get( 0) > 0; }
    public boolean   hasReward(){    return casinoData.get( 1) > 0; }

    public BlockPos  pos(){ return pos; }
    public World     world(){
        return this.world;
    }
    public LogicBase logic(){
        return logicData.get();
    }
    public int       turnstate(){
        return logicData.get().turnstate;
    }

    public abstract int getID();

    public String getCurrentPlayer(int index){
        TileEntityMachine te = (TileEntityMachine) world.getTileEntity(pos);
        return logic().currentPlayer[index];
    }

}
