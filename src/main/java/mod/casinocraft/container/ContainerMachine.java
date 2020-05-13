package mod.casinocraft.container;

import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerMachine extends ContainerBase {

    public final IInventory inventory;
    private final World world;
    private final IIntArray casinoData;
    private BlockPos pos = new BlockPos(0, 0, 0);
    public DyeColor color;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.fromLong(packetBuffer.readLong()));
    }

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityBoard) playerInventory.player.getEntityWorld().getTileEntity(pos));
        this.pos = pos;
    }

    public ContainerMachine(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(type, windowID);
        this.inventory = board;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(board, 0,   8, 62)); // Key Card
        this.addSlot(new Slot(board, 1, 152, 62)); // Game Module
        this.addSlot(new Slot(board, 2,  28, 16)); // Token IN
        this.addSlot(new Slot(board, 3, 132, 16)); // Token OUT
        addPlayerSlots(playerInventory);
        this.casinoData = board.casinoData;
        color = board.color;
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
                //} else if (index != 1 && index != 0) {
                //    if (this.func_217057_a(itemstack1)) {
                //        if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                //            return ItemStack.EMPTY;
                //        }
                //    } else if (this.isFuel(itemstack1)) {
                //        if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                //            return ItemStack.EMPTY;
                //        }
                //    } else if (index >= 3 && index < 30) {
                //        if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                //            return ItemStack.EMPTY;
                //        }
                //    } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                //        return ItemStack.EMPTY;
                //    }
                //} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
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




    //----------------------------------------SUPPORT----------------------------------------//

    /** Determines whether supplied player can use this container */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inventory.isUsableByPlayer(playerIn);
    }




    //----------------------------------------GET/SET----------------------------------------//

    public boolean hasToken() {
        return casinoData.get(0) > 0;
    }
    public int getBetStorage(){
        return casinoData.get(0);
    }
    public int getBetLow(){
        return casinoData.get(1);
    }
    public int getBetHigh(){
        return casinoData.get(2);
    }
    public boolean isCreative(){ return casinoData.get(5) == 1; }
    public boolean getTransferIn(){
        return casinoData.get(3) == 1;
    }
    public boolean getTransferOut(){
        return casinoData.get(4) == 1;
    }
    public boolean getIsCreative(){
        return casinoData.get(5) == 1;
    }
    public void setBetStorage(int value){
        casinoData.set(0, value);
    }
    public void setBetLow(int value){
        casinoData.set(1, value);
    }
    public void setBetHigh(int value){
        casinoData.set(2, value);
    }
    public void addBetStorage(int value){
        casinoData.set(0, casinoData.get(0) + value);
    }
    public void addBetLow(int value){
        casinoData.set(1, casinoData.get(1) + value);
    }
    public void addBetHigh(int value){
        casinoData.set(2, casinoData.get(2) + value);
    }
    public void setTransferIn(boolean value){
        casinoData.set(3, value ? 1 : 0);
    }
    public void setTransferOut(boolean value){
        casinoData.set(4, value ? 1 : 0);
    }
    public void setIsCreative(boolean value){
        casinoData.set(5, value ? 1 : 0);
    }
    public World world(){
        return this.world;
    }
    public ItemStack getToken(){
        return this.inventory.getStackInSlot(4);
    }
    public BlockPos getPos(){
        return pos;
    }
    public ItemStack getScoreToken(){
        return this.inventory.getStackInSlot(5);
    }
    public void setToken(ItemStack itemStack){
        this.inventory.setInventorySlotContents(4, itemStack);
    }

}
