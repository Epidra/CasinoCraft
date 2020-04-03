package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.card.*;
import mod.casinocraft.logic.clay.*;
import mod.casinocraft.logic.dust.*;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.logic.other.LogicSlotGame;
import mod.casinocraft.network.ServerInventoryMessage;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.util.BoardDataArray;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public abstract class TileEntityBoard extends TileEntity implements IInventory, ITickableTileEntity {

    public TileEntityBoard(TileEntityType<?> tileEntityTypeIn, DyeColor color, int tableID) {
        super(tileEntityTypeIn);
        LOGIC = new LogicDummy();
        this.color = color;
        this.tableID = tableID;
    }

    public int bet_storage = 0;
    public int bet_low  = 0;
    public int bet_high = 0;
    public boolean transfer_in  = false;
    public boolean transfer_out = false;
    public boolean isCreative = false;

    public String currentPlayer = "void";

    private Item lastModule = Items.FLINT;

    public LogicBase LOGIC;

    /** 0 - lock, 1 - module, 2 - inventoryIN, 3 - inventoryOUT, 4 - inventoryTOKEN, 5 - scoreTOKEN **/
    public NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);

    public DyeColor color = DyeColor.BLACK;

    public final int tableID;

    public abstract ITextComponent getName();

    public Item getKey(){
        return inventory.get(0).getItem();
    }

    public final BoardDataArray boardData = new BoardDataArray(){
        public LogicBase get() { return TileEntityBoard.this.LOGIC; }
        public Object get(int index){
            return TileEntityBoard.this.LOGIC.get(index);
        }
        public void set(int index, int    value){
            TileEntityBoard.this.LOGIC.set(index, value);
        }
        public int size(){
            return TileEntityBoard.this.LOGIC.size();
        }
    };

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
                case 3: TileEntityBoard.this.transfer_in  = value == 1 ? true : false; break;
                case 4: TileEntityBoard.this.transfer_out = value == 1 ? true : false; break;
                case 5: TileEntityBoard.this.isCreative   = value == 1 ? true : false; break;
            }

        }

        public int size() {
            return 6;
        }
    };

    private LogicBase setLogic(){
        if(this instanceof TileEntityArcade){
            if(getModule() == CasinoKeeper.MODULE_DUST_WHITE)     return new Logic2048();
            //if(getModule() == CasinoKeeper.MODULE_DUST_ORANGE)    return new Logic2048();
            if(getModule() == CasinoKeeper.MODULE_DUST_MAGENTA)   return new LogicSokoban();
            if(getModule() == CasinoKeeper.MODULE_DUST_LIGHTBLUE) return new LogicMeanMinos();
            //if(getModule() == CasinoKeeper.MODULE_DUST_YELLOW)    return new Logic2048();
            //if(getModule() == CasinoKeeper.MODULE_DUST_LIME)      return new Logic2048();
            //if(getModule() == CasinoKeeper.MODULE_DUST_PINK)      return new Logic2048();
            //if(getModule() == CasinoKeeper.MODULE_DUST_GRAY)      return new Logic2048();
            //if(getModule() == CasinoKeeper.MODULE_DUST_SILVER)    return new Logic2048();
            if(getModule() == CasinoKeeper.MODULE_DUST_CYAN)      return new LogicColumns();
            //if(getModule() == CasinoKeeper.MODULE_DUST_PURPLE)    return new Logic2048();
            if(getModule() == CasinoKeeper.MODULE_DUST_BLUE)      return new LogicTetris();
            //if(getModule() == CasinoKeeper.MODULE_DUST_BROWN)     return new Logic2048();
            //if(getModule() == CasinoKeeper.MODULE_DUST_GREEN)     return new Logic2048();
            if(getModule() == CasinoKeeper.MODULE_DUST_RED)       return new LogicSnake();
            //if(getModule() == CasinoKeeper.MODULE_DUST_BLACK)     return new Logic2048();
        }
        if(this instanceof TileEntityCardTableBase || this instanceof TileEntityCardTableWide){
            if(getModule() == CasinoKeeper.MODULE_CARD_WHITE)     return new LogicAceyDeucey(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CARD_ORANGE)    return new LogicBaccarat(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_MAGENTA)   return new LogicTriPeak(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CARD_LIGHTBLUE) return new LogicBaccarat(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_YELLOW)    return new LogicFreeCell(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CARD_LIME)      return new LogicBaccarat(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CARD_PINK)      return new LogicBaccarat(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_GRAY)      return new LogicBaccarat(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_SILVER)    return new LogicVideoPoker(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_CYAN)      return new LogicKlondike(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_PURPLE)    return new LogicPyramid(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CARD_BLUE)      return new LogicBaccarat(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_BROWN)     return new LogicSpider(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CARD_GREEN)     return new LogicBaccarat(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_RED)       return new LogicRougeEtNoir(tableID);
            if(getModule() == CasinoKeeper.MODULE_CARD_BLACK)     return new LogicBlackJack(tableID);

            if(getModule() == CasinoKeeper.MODULE_CLAY_WHITE)     return new LogicSudoku(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CLAY_ORANGE)    return new LogicRoulette(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_MAGENTA)   return new LogicCraps(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CLAY_LIGHTBLUE) return new LogicRoulette(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_YELLOW)    return new LogicMysticSquare(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_LIME)      return new LogicSimon(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CLAY_PINK)      return new LogicRoulette(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CLAY_GRAY)      return new LogicRoulette(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_SILVER)    return new LogicMinesweeper(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CLAY_CYAN)      return new LogicRoulette(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_PURPLE)    return new LogicSicBo(tableID);
            //if(getModule() == CasinoKeeper.MODULE_CLAY_BLUE)      return new LogicRoulette(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_BROWN)     return new LogicFanTan(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_GREEN)     return new LogicMemory(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_RED)       return new LogicRoulette(tableID);
            if(getModule() == CasinoKeeper.MODULE_CLAY_BLACK)     return new LogicHalma(tableID);
        }
        if(this instanceof TileEntitySlotMachine){
            if(getModule() != Blocks.AIR.asItem()) return new LogicSlotGame();
        }
        return new LogicDummy();
    }


    @Override
    public void tick(){
        if(this.world.isRemote) {
            //return;
        }
        if(getModule() != lastModule){
            lastModule = getModule();
            LOGIC = setLogic();
            if(this.world.isRemote) {
                CasinoPacketHandler.sendToServer(new ServerInventoryMessage(inventory, getPos()));
            }
        }

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
                    int count = CasinoKeeper.config_fastload.get() ? bet_storage >= 64 ? 64 : bet_storage : 1;
                    inventory.set(3, new ItemStack(getTokenStack().getItem(), count));
                    bet_storage-=count;
                    isDirty = true;
                }else if(inventory.get(3).getCount() < 64) {
                    int count = CasinoKeeper.config_fastload .get()? bet_storage >= 64-inventory.get(3).getCount() ? 64-inventory.get(3).getCount() : bet_storage : 1;
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
            LOGIC.updateLogic();
            LOGIC.updateMotion();
        }
    }

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




    public void setPlayer(String name){
        currentPlayer = name;
    }

    public void resetPlayer(){
        currentPlayer = "void";
    }







    /** ??? */
    public void read(CompoundNBT compound){
        super.read(compound);
        bet_storage = compound.getInt("storage");
        bet_low  = compound.getInt("low");
        bet_high = compound.getInt("high");
        isCreative = compound.getBoolean("iscreative");
        currentPlayer = compound.getString("currentplayer");
        this.inventory = NonNullList.withSize(6, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        lastModule = getModule();
        LOGIC = setLogic();
        LOGIC.load(compound);
        /**CasinoPacketHandler.INSTANCE.sendToServer(new ServerPowerMessage(EnumModule.byItem(inventory.get(1).getItem()).meta, pos));*/
    }
    /** ??? */
    public CompoundNBT write(CompoundNBT compound){
        super.write(compound);
        compound.putInt("storage", bet_storage);
        compound.putInt("low", bet_low);
        compound.putInt("high", bet_high);
        compound.putBoolean("iscreative", isCreative);
        compound.putString("currentplayer", currentPlayer);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        LOGIC.save(compound);
        return compound;
    }
    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. */
    public int getInventoryStackLimit(){
        return 64;
    }







    /** ??? */
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    /** Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client */
    @Override
    public CompoundNBT getUpdateTag(){
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    /** Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client */
    @Override
    public void handleUpdateTag(CompoundNBT tag){
        this.read(tag);
    }

}
