package mod.casinocraft.container;

import com.google.common.collect.Lists;
import mod.casinocraft.CasinoCraft;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityArcade;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.BoardDataArray;
import mod.casinocraft.util.DataCard;
import mod.casinocraft.util.DataDice;
import mod.casinocraft.util.DataEntity;
import mod.shared.container.ContainerBase;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ContainerCasino extends ContainerBase {

    //public static final ContainerType<ContainerCasino> TYPE = (ContainerType<ContainerCasino>) IForgeContainerType.create(ContainerCasino::new).setRegistryName(CasinoCraft.MODID, "casino");

    public final IInventory inventory;
    private final World world;
    protected final IIntArray casinoData;
    protected final BoardDataArray boardData;
    private BlockPos pos = new BlockPos(0, 0, 0);
    public DyeColor color;
    public int tableID;

  //  private final List<IContainerListener> listeners = Lists.newArrayList();
//
  //  public void addListener(IContainerListener listener) {
  //      if (!this.listeners.contains(listener)) {
  //          this.listeners.add(listener);
  //          listener.sendAllContents(this, this.getInventory());
  //          this.detectAndSendChanges();
  //      }
  //  }

  //  /**
  //   * Remove the given Listener. Method name is for legacy.
  //   */
  //  @OnlyIn(Dist.CLIENT)
  //  public void removeListener(IContainerListener listener) {
  //      this.listeners.remove(listener);
  //  }


    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.fromLong(packetBuffer.readLong()));
        //this.pos = BlockPos.fromLong(packetBuffer.readLong());
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityBoard) playerInventory.player.getEntityWorld().getTileEntity(pos));
        this.pos = pos;
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
        super(type, windowID);
        this.inventory = board;
        this.world = playerInventory.player.world;

        // take intarray from here ???
        // this.trackIntArray(p_i50100_3_);

        this.casinoData = board.casinoData;
        this.boardData = board.boardData;
        color = board.color;
        tableID = board.tableID;
    }

   // protected void trackIntArray(IIntArray arrayIn) {
   //     for(int i = 0; i < arrayIn.size(); ++i) {
   //         this.trackInt(IntReferenceHolder.create(arrayIn, i));
   //     }
   // }

    //protected IntReferenceHolder trackInt(IntReferenceHolder intIn) {
    //    this.trackedIntReferences.add(intIn);
    //    return intIn;
    //}

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inventory.isUsableByPlayer(playerIn);
    }

    //    public void addListener(IContainerListener listener);
    //    public void detectAndSendChanges();
    //    public ItemStack transferStackInSlot(EntityPlayer player, int index);

   // public void updateAllValues(Container container, NonNullList<ItemStack> nonNullList){
//
   // }
//
   // public void updateSlotContents(Container container, int i, ItemStack itemStack){
//
   // }
//
   // public void updateSingleValue(Container container, int varToUpdate, int newValue){
//
   // }

   // private final List<DataCard> trackedCardReferences = Lists.newArrayList();
   // private final List<DataDice> trackedDiceReferences = Lists.newArrayList();
   // private final List<DataEntity> trackedEntityReferences = Lists.newArrayList();
//
   // protected DataCard trackCard(DataCard intIn) {
   //     this.trackedCardReferences.add(intIn);
   //     return intIn;
   // }
   // protected DataDice trackDice(DataDice intIn) {
   //     this.trackedDiceReferences.add(intIn);
   //     return intIn;
   // }
   // protected DataEntity trackEntity(DataEntity intIn) {
   //     this.trackedEntityReferences.add(intIn);
   //     return intIn;
   // }

   // /**
   //  * Looks for changes made in the container, sends them to every listener.
   //  */
   // public void detectAndSendChanges() {
   //     //for(int i = 0; i < this.inventorySlots.size(); ++i) {
   //     //    ItemStack itemstack = this.inventorySlots.get(i).getStack();
   //     //    ItemStack itemstack1 = this.inventoryItemStacks.get(i);
   //     //    if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
   //     //        boolean clientStackChanged = !itemstack1.equals(itemstack, true);
   //     //        itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
   //     //        this.inventoryItemStacks.set(i, itemstack1);
   //     //        if (clientStackChanged)
   //     //            for(IContainerListener icontainerlistener : this.listeners) {
   //     //                icontainerlistener.sendSlotContents(this, i, itemstack1);
   //     //            }
   //     //    }
   //     //}
//
   //     for(int j = 0; j < this.trackedCardReferences.size(); ++j) {
   //         DataCard intreferenceholder = this.trackedCardReferences.get(j);
   //         if (intreferenceholder.isDirty()) {
   //             for(IContainerListener icontainerlistener1 : this.listeners) {
   //                 //icontainerlistener1.sendWindowProperty(this, j, intreferenceholder.get());
   //             }
   //         }
   //     }
   //     for(int j = 0; j < this.trackedDiceReferences.size(); ++j) {
   //         DataDice intreferenceholder = this.trackedDiceReferences.get(j);
   //         if (intreferenceholder.isDirty()) {
   //             for(IContainerListener icontainerlistener1 : this.listeners) {
   //                 //icontainerlistener1.sendWindowProperty(this, j, intreferenceholder.get());
   //             }
   //         }
   //     }
   //     for(int j = 0; j < this.trackedEntityReferences.size(); ++j) {
   //         DataEntity intreferenceholder = this.trackedEntityReferences.get(j);
   //         if (intreferenceholder.isDirty()) {
   //             for(IContainerListener icontainerlistener1 : this.listeners) {
   //                 //icontainerlistener1.sendWindowProperty(this, j, intreferenceholder.get());
   //             }
   //         }
   //     }
   // }

    //// From Base Class
    //public void updateProgressBar(int id, int data) {
    //    this.trackedIntReferences.get(id).set(data);
    //}

    //// From Beacon Container Class
    //public void updateProgressBar(int id, int data) {
    //    super.updateProgressBar(id, data);
    //    this.detectAndSendChanges();
    //}

    public void logic(int index, int value){
        boardData.set(index, value);
    }

    public LogicBase logic(){
        return boardData.get();
    }

    public int turnstate(){
        return boardData.get().turnstate;
    }

    public void turnstate(int i){
        boardData.set(0, i);
    }

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

    public abstract String getName();

    public String getCurrentPlayer(){
        TileEntityBoard te = (TileEntityBoard) world.getTileEntity(pos);
        return te.currentPlayer;
    }
}