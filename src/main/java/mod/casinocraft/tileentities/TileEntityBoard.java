package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.card.*;
import mod.casinocraft.logic.mino.*;
import mod.casinocraft.logic.chip.*;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.logic.other.LogicSlotGame;
import mod.casinocraft.util.LogicData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;

public abstract class TileEntityBoard extends TileBase {

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
    public DyeColor color;
    public final int tableID;
    public Item getKey(){
        return inventory.get(0).getItem();
    }
    public final LogicData logicData = () -> TileEntityBoard.this.LOGIC;
    public final IIntArray casinoData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0: return TileEntityBoard.this.bet_storage;
                case 1: return TileEntityBoard.this.bet_low;
                case 2: return TileEntityBoard.this.bet_high;
                case 3: return TileEntityBoard.this.transfer_in  ? 1 : 0;
                case 4: return TileEntityBoard.this.transfer_out ? 1 : 0;
                case 5: return TileEntityBoard.this.isCreative   ? 1 : 0;
                default:
                    return 0;
            }
        }
        public void set(int index, int value) {
            switch(index) {
                case 0: TileEntityBoard.this.bet_storage  = value; break;
                case 1: TileEntityBoard.this.bet_low      = value; break;
                case 2: TileEntityBoard.this.bet_high     = value; break;
                case 3: TileEntityBoard.this.transfer_in  = value == 1; break;
                case 4: TileEntityBoard.this.transfer_out = value == 1; break;
                case 5: TileEntityBoard.this.isCreative   = value == 1; break;
            }
        }
        public int size() {
            return 6;
        }
    };




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public TileEntityBoard(TileEntityType<?> tileEntityTypeIn, DyeColor color, int tableID) {
        super(tileEntityTypeIn);
        LOGIC = new LogicDummy(tableID);
        this.color = color;
        this.tableID = tableID;
    }




    //----------------------------------------UPDATE----------------------------------------//

    @Override
    public void tick(){
        changeLogic();
        boolean isDirty = false;
        if(transfer_in) {
            if(inventory.get(2).getCount() > 0 && (bet_storage == 0 || isToken(inventory.get(2)))) {
                if(getToken() == Item.getItemFromBlock(Blocks.AIR)) setToken(inventory.get(2));
                int count = CasinoKeeper.config_fastload.get() ? inventory.get(2).getCount() : 1;
                inventory.get(2).shrink(count);
                bet_storage+=count;
                if(inventory.get(2).getCount() <= 0) inventory.set(2, new ItemStack(Blocks.AIR));
                isDirty = true;
            }
        }
        if(transfer_out) {
            if(bet_storage > 0 && (isToken(inventory.get(3)) || inventory.get(3).isEmpty())) {
                if(inventory.get(3).isEmpty()) {
                    int count = CasinoKeeper.config_fastload.get() ? Math.min(bet_storage, 64) : 1;
                    inventory.set(3, new ItemStack(getTokenStack().getItem(), count));
                    bet_storage-=count;
                    isDirty = true;
                }else if(inventory.get(3).getCount() < 64) {
                    int count = CasinoKeeper.config_fastload .get()? Math.min(bet_storage, 64 - inventory.get(3).getCount()) : 1;
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
    }




    //----------------------------------------NETWORK----------------------------------------//

    /** ??? */
    //@Override
    //public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SUpdateTileEntityPacket pkt) {
    //    read(pkt.getNbtCompound());
    //}

    /** Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client */
    @Override
    public CompoundNBT getUpdateTag(){
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    /** Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client */
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag){
        this.read(state, tag);
    }




    //----------------------------------------READ/WRITE----------------------------------------//

    public void read(BlockState state, CompoundNBT nbt){
        super.read(state, nbt);
        bet_storage = nbt.getInt("storage");
        bet_low  = nbt.getInt("low");
        bet_high = nbt.getInt("high");
        isCreative = nbt.getBoolean("iscreative");
        this.inventory = NonNullList.withSize(6, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.inventory);
        lastModule = getModule();
        LOGIC = setLogic();
        LOGIC.load(nbt);
    }

    public CompoundNBT write(CompoundNBT compound){
        super.write(compound);
        compound.putInt("storage", bet_storage);
        compound.putInt("low", bet_low);
        compound.putInt("high", bet_high);
        compound.putBoolean("iscreative", isCreative);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        LOGIC.save(compound);
        return compound;
    }




    //----------------------------------------SUPPORT----------------------------------------//

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clear() {

    }

    public void changeLogic(){
        if(lastModule != getModule()){
            lastModule = getModule();
            LOGIC = setLogic();
        }
    }




    //----------------------------------------GET/SET----------------------------------------//

    public Item getModule() {
        return inventory.get(1).getItem();
    }
    public Item getToken() {
        return inventory.get(4).getItem();
    }
    public boolean isToken(ItemStack itemstack) {
        return itemstack.getItem() == inventory.get(4).getItem();
    }
    public ItemStack getTokenStack() {
        return inventory.get(4);
    }
    public void setToken(ItemStack itemstack) {
        inventory.set(4, new ItemStack(itemstack.getItem(), 1));
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

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. */
    public int getInventoryStackLimit(){
        return 64;
    }

    private LogicBase setLogic(){
        if(this instanceof TileEntityArcade){
            if(getModule() == CasinoKeeper.MODULE_CHIP_WHITE.get())      return new LogicChipWhite(    tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_ORANGE.get())     return new LogicChipOrange(   tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_MAGENTA.get())    return new LogicChipMagenta(  tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_LIGHT_BLUE.get()) return new LogicChipLightBlue(tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_YELLOW.get())     return new LogicChipYellow(   tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_LIME.get())       return new LogicChipLime(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_PINK.get())       return new LogicChipPink(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_GRAY.get())       return new LogicChipGray(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_LIGHT_GRAY.get()) return new LogicChipLightGray(tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_CYAN.get())       return new LogicChipCyan(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_PURPLE.get())     return new LogicChipPurple(   tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_BLUE.get())       return new LogicChipBlue(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_BROWN.get())      return new LogicChipBrown(    tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_GREEN.get())      return new LogicChipGreen(    tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_RED.get())        return new LogicChipRed(      tableID);
            if(getModule() == CasinoKeeper.MODULE_CHIP_BLACK.get())      return new LogicChipBlack(    tableID);
        }
        if(this instanceof TileEntityCardTableBase || this instanceof TileEntityCardTableWide){
            if(getModule() == CasinoKeeper.MODULE_CARD_WHITE.get())      return new LogicCardWhite(    tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_ORANGE.get())     return new LogicCardOrange(   tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_MAGENTA.get())    return new LogicCardMagenta(  tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_LIGHT_BLUE.get()) return new LogicCardLightBlue(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_YELLOW.get())     return new LogicCardYellow(   tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_LIME.get())       return new LogicCardLime(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_PINK.get())       return new LogicCardPink(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_GRAY.get())       return new LogicCardGray(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_LIGHT_GRAY.get()) return new LogicCardLightGray(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_CYAN.get())       return new LogicCardCyan(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_PURPLE.get())     return new LogicCardPurple(   tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_BLUE.get())       return new LogicCardBlue(     tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_BROWN.get())      return new LogicCardBrown(    tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_GREEN.get())      return new LogicCardGreen(    tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_RED.get())        return new LogicCardRed(      tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_BLACK.get())      return new LogicCardBlack(    tableID);

            if(getModule() == CasinoKeeper.MODULE_MINO_WHITE.get())      return new LogicMinoWhite(    tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_ORANGE.get())     return new LogicMinoOrange(   tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_MAGENTA.get())    return new LogicMinoMagenta(  tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_LIGHT_BLUE.get()) return new LogicMinoLightBlue(tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_YELLOW.get())     return new LogicMinoYellow(   tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_LIME.get())       return new LogicMinoLime(     tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_PINK.get())       return new LogicMinoPink(     tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_GRAY.get())       return new LogicMinoGray(     tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_LIGHT_GRAY.get()) return new LogicMinoLightGray(tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_CYAN.get())       return new LogicMinoCyan(     tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_PURPLE.get())     return new LogicMinoPurple(   tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_BLUE.get())       return new LogicMinoBlue(     tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_BROWN.get())      return new LogicMinoBrown(    tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_GREEN.get())      return new LogicMinoGreen(    tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_RED.get())        return new LogicMinoRed(      tableID);
            if(getModule() == CasinoKeeper.MODULE_MINO_BLACK.get())      return new LogicMinoBlack(    tableID);
        }
        if(this instanceof TileEntitySlotMachine){
            if(getModule() != Blocks.AIR.asItem()) return new LogicSlotGame(tableID);
        }
        return new LogicDummy(tableID);
    }
}
