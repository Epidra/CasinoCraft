package mod.casinocraft.container;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ContainerCasino extends Container {
	
    public final TileEntityBoard inventory;
    private final World world;
    private BlockPos pos;
    public EnumDyeColor color;
    public int tableID;
    
    //public ContainerCasino(InventoryPlayer playerInventory, IInventory blockInventory){
    //    this.inventory = (TileEntityBoard) blockInventory;
    //    this.addSlotToContainer(new Slot(blockInventory, 0, 56, 35));
    //    for (int i = 0; i < 3; ++i){
    //        for (int j = 0; j < 9; ++j){
    //            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
    //        }
    //    }
    //    for (int k = 0; k < 9; ++k){
    //        this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
    //    }
    //    this.world = playerInventory.player.world;
    //}
    
    public ContainerCasino(IInventory blockInventory, BlockPos posIn, World worldIn){
        this.inventory = (TileEntityBoard) blockInventory;
        this.world = worldIn;
        color = inventory.color;
        tableID = inventory.tableID;
        pos = posIn;
    }

    //public ContainerCasino(IInventory blockInventory){
    //    this.inventory = (TileEntityBoard) blockInventory;
    //    this.world = Minecraft.getMinecraft().world;
    //}
    
    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.inventory);
    }
    
    /** Looks for changes made in the container, sends them to every listener. */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = this.listeners.get(i);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.inventory.setField(id, data);
    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.inventory.isUsableByPlayer(playerIn);
    }
    
    /** Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s). */
    // TAKEN FROM BIOMES'O'PLENTY
    @Override
    //@Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index){
        ItemStack oldStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        //Ensure there is a slot at this index and it has an item in it
        if (slot != null && slot.getHasStack()){
            ItemStack mergedStack = slot.getStack();
            oldStack = mergedStack.copy();
            if (index < 15){
                if (!this.mergeItemStack(mergedStack, 15, this.inventorySlots.size(), true)){
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(mergedStack, 0, 15, false)){
                return ItemStack.EMPTY;
            }
            if (mergedStack.getCount() == 0){
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return oldStack;
    }




    //----------------------------------------GET/SET----------------------------------------//

    public LogicBase logic(){
        return inventory.LOGIC;
    }
    public int turnstate(){
        return inventory.LOGIC.turnstate;
    }
    public boolean hasToken() {
        return inventory.bet_storage > 0;
    }
    public int getBetStorage(){
        return inventory.bet_storage;
    }
    public int getBetLow(){
        return inventory.bet_low;
    }
    public int getBetHigh(){
        return inventory.bet_high;
    }
    public boolean isCreative(){ return inventory.isCreative; }
    public void setBetStorage(int value){
        inventory.bet_storage = value;
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
    public void setToken(ItemStack itemStack){
        this.inventory.setInventorySlotContents(4, itemStack);
    }

    public abstract int getID();

    public String getCurrentPlayer(int index){
        return logic().currentPlayer[index];
    }
	
}
