package mod.casinocraft.tileentities;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerArcade;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.network.ServerPowerMessage;
import mod.casinocraft.util.Card;
import mod.casinocraft.util.Dice;
import mod.casinocraft.util.Entity;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public abstract class TileEntityBoard extends TileEntity implements IInventory, ITickable, IInteractionObject {

    public int bet_storage = 0;
    public int bet_low  = 0;
    public int bet_high = 0;
    public boolean transfer_in  = false;
    public boolean transfer_out = false;
    public boolean isCreative = false;

    public final LogicBase LOGIC;


    /** 0 - lock, 1 - module, 2 - inventoryIN, 3 - inventoryOUT, 4 - inventoryTOKEN, 5 - scoreTOKEN **/
    public NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);

    public EnumDyeColor color = EnumDyeColor.BLACK;

    public TileEntityBoard(boolean isArcade, LogicBase logicbase) {
        super(isArcade ? CasinoKeeper.TILETYPE_ARCADE : CasinoKeeper.TILETYPE_CARDTABLE);
        LOGIC = logicbase;
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

    /** Get the name of this object. For players this returns their username */
    public ITextComponent getName(){
        return new TextComponentTranslation("casinoboard", new Object[0]);
    }

    public ITextComponent getCustomName(){
        return new TextComponentTranslation("casinoboard", new Object[0]);
    }

    /** Returns true if this thing is named */
    public boolean hasCustomName(){
        return false;
    }

    /** ??? */
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        read(pkt.getNbtCompound());
    }

    /** Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client */
    @Override
    public NBTTagCompound getUpdateTag(){
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    /** Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client */
    @Override
    public void handleUpdateTag(NBTTagCompound tag){
        this.read(tag);
    }

    /** ??? */
    public void read(NBTTagCompound compound){
        super.read(compound);
        bet_storage = compound.getInt("storage");
        bet_low  = compound.getInt("low");
        bet_high = compound.getInt("high");
        isCreative = compound.getBoolean("iscreative");
        this.inventory = NonNullList.withSize(6, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);

        LOGIC.load(compound);

        /**CasinoPacketHandler.INSTANCE.sendToServer(new ServerPowerMessage(EnumModule.byItem(inventory.get(1).getItem()).meta, pos));*/
    }

    /** ??? */
    public NBTTagCompound write(NBTTagCompound compound){
        super.write(compound);
        compound.setInt("storage", bet_storage);
        compound.setInt("low", bet_low);
        compound.setInt("high", bet_high);
        compound.setBoolean("iscreative", isCreative);
        ItemStackHelper.saveAllItems(compound, this.inventory);

        LOGIC.save(compound);

        return compound;
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. */
    public int getInventoryStackLimit(){
        return 64;
    }

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    public boolean isUsableByPlayer(EntityPlayer player){
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
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
            ItemStack itemstack = this.inventory.get(0);
            return true;
        }
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
        for (int i = 0; i < this.inventory.size(); ++i){
            this.inventory.set(i, null);
        }
    }

    /** Like the old updateEntity(), except more generic. */
    public void tick(){

        boolean isDirty = false;

        if(transfer_in) {
            if(inventory.get(2).getCount() > 0 && (bet_storage == 0 || isToken(inventory.get(2)))) {
                if(getToken() == Item.getItemFromBlock(Blocks.AIR)) setToken(inventory.get(2));
                int count = CasinoKeeper.config_fastload.get() ? inventory.get(2).getCount() : 1;
                inventory.get(2).shrink(count);
                bet_storage+=count;
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



        if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_WHITE    ){ color = EnumDyeColor.WHITE     ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_ORANGE   ){ color = EnumDyeColor.ORANGE    ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_MAGENTA  ){ color = EnumDyeColor.MAGENTA   ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_LIGHTBLUE){ color = EnumDyeColor.LIGHT_BLUE; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_YELLOW   ){ color = EnumDyeColor.YELLOW    ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_LIME     ){ color = EnumDyeColor.LIME      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_PINK     ){ color = EnumDyeColor.PINK      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_GRAY     ){ color = EnumDyeColor.GRAY      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_SILVER   ){ color = EnumDyeColor.LIGHT_GRAY; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_CYAN     ){ color = EnumDyeColor.CYAN      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_PURPLE   ){ color = EnumDyeColor.PURPLE    ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_BLUE     ){ color = EnumDyeColor.BLUE      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_BROWN    ){ color = EnumDyeColor.BROWN     ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_GREEN    ){ color = EnumDyeColor.GREEN     ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_BASE_RED      ){ color = EnumDyeColor.RED       ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BLACK    ){ color = EnumDyeColor.BLACK     ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_WHITE    ){ color = EnumDyeColor.WHITE     ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_ORANGE   ){ color = EnumDyeColor.ORANGE    ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_MAGENTA  ){ color = EnumDyeColor.MAGENTA   ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_LIGHTBLUE){ color = EnumDyeColor.LIGHT_BLUE; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_YELLOW   ){ color = EnumDyeColor.YELLOW    ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_LIME     ){ color = EnumDyeColor.LIME      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_PINK     ){ color = EnumDyeColor.PINK      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_GRAY     ){ color = EnumDyeColor.GRAY      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_SILVER   ){ color = EnumDyeColor.LIGHT_GRAY; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_CYAN     ){ color = EnumDyeColor.CYAN      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_PURPLE   ){ color = EnumDyeColor.PURPLE    ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BLUE     ){ color = EnumDyeColor.BLUE      ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BROWN    ){ color = EnumDyeColor.BROWN     ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_GREEN    ){ color = EnumDyeColor.GREEN     ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_RED      ){ color = EnumDyeColor.RED       ; }
        else if(this.world.getBlockState(getPos()).getBlock() == CasinoKeeper.CARDTABLE_WIDE_BLACK    ){ color = EnumDyeColor.BLACK     ; }
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    private String getScorename(int r) {
        switch(r) {
            case  0: return "Ruby";
            case  1: return "Weiss";
            case  2: return "Blake";
            case  3: return "Yang";
            case  4: return "Jaune";
            case  5: return "Nora";
            case  6: return "Pyrrha";
            case  7: return "Ren";
            case  8: return "Sun";
            case  9: return "Neptune";
            case 10: return "Flynt";
            case 11: return "Neon";
            case 12: return "Cinder";
            case 13: return "Mercury";
            case 14: return "Emerald";
            case 15: return "Summer";
            case 16: return "Taiyang";
            case 17: return "Qrow";
            case 18: return "Raven";
            case 19: return "Winter";
            case 20: return "Coco";
            case 21: return "Fox";
            case 22: return "Velvet";
            case 23: return "Yatsuhashi";
        }
        return "Zwei";
    }

    public void addScore(String name, int points) {
        int pos = 18;
        for(int i = 17; i >= 0; i--) {
            if(points > LOGIC.scorePoints[i]) {
                pos = i;
            }
        }
        if(pos == 17) {
            LOGIC.scorePoints[17] = points;
            LOGIC.scoreName[17] = name;
            LOGIC.scoreLast = 17;
        }
        if(pos < 17) {
            for(int i = 16; i >= pos; i--) {
                LOGIC.scorePoints[i+1] = LOGIC.scorePoints[i];
                LOGIC.scoreName[i+1] = LOGIC.scoreName[i];
            }
            LOGIC.scorePoints[pos] = points;
            LOGIC.scoreName[pos] = name;
        }
        LOGIC.scoreLast = pos;
    }

    public void setupHighscore(Item item) {
        LOGIC.scoreLast = 18;
        if(getScoreToken() != item) {
            setScoreToken(item);
            Random rand = new Random();
            for(int i = 17; i >= 0; i--) {
                LOGIC.scorePoints[i] = (18-i) * 5;
                LOGIC.scoreName[i] = getScorename(rand.nextInt(24));
            }
            /**CasinoPacketHandler.INSTANCE.sendToServer(new ServerScoreMessage(getScoreToken(), scoreName, scorePoints, getPos()));*/
        }
        LOGIC.nbtHasHighscore = scoreActive(item);
    }

    private boolean scoreActive(Item item) {
        if(item == CasinoKeeper.MODULE_PYRAMID) return true;
        if(item == CasinoKeeper.MODULE_TRIPEAK) return true;
        if(item == CasinoKeeper.MODULE_KLONDIKE) return true;
        if(item == CasinoKeeper.MODULE_MEMORY) return true;
        if(item == CasinoKeeper.MODULE_HALMA) return true;
        if(item == CasinoKeeper.MODULE_MINESWEEPER) return true;
        if(item == CasinoKeeper.MODULE_TETRIS) return true;
        if(item == CasinoKeeper.MODULE_COLUMNS) return true;
        if(item == CasinoKeeper.MODULE_MEANMINOS) return true;
        if(item == CasinoKeeper.MODULE_SNAKE) return true;
        return item == CasinoKeeper.MODULE_2048;
    }

    //public static TileEntityType<TileEntityCasino> TILETYPE;

    //public EnumDyeColor color;

    public Random rand = new Random();
    public int difficulty = 2; // 1 - easy, 2 - normal, 3 - hard
    public int scorePoint = -1;
    public int scoreLevel  = -1;
    public int scoreLives  = -1;

    public int    [][] gridI = new     int[4][4];
    public boolean[][] gridB = new boolean[4][4];

    public String hand = "NULL";
    public int reward = -1;

    public Vector2 selector = new Vector2(0,0);

    /** 0 - bet, 1 - unused, 2 - turn player, 3 - turn computer, 4 - game over, 5 - result **/
    public int turnstate;
    //public TileEntityBoard board;

    /** Basic Constructor **/
    //public TileEntityCasino(){
    //    super(TILETYPE);
    //}

    /** Advanced Constructor **/
    //public TileEntityCasino(TileEntityBoard te, BlockPos bp){
    //    super(TILETYPE);
    //    this.color = te.color;
    //    board = te;
    //    this.setPos(bp);
    //    turnstate = 0;
    //}

    public boolean hasToken() {
        return bet_storage > 0;
    }

    //public Item getToken() {
    //    return board.getToken();
    //}
    //public ItemStack getTokenStack() {
    //    return board.getTokenStack();
    //}
    public int getBetStorage() {
        return bet_storage;
    }
    public int getBetLow() {
        return bet_low;
    }
    public int getBetHigh() {
        return bet_high;
    }





    //public void start(){
    //    turnstate = 2;
    //    scorePoint = 0;
    //    scoreLevel  = 0;
    //    scoreLives  = 0;
    //    hand   = "empty";
    //    reward = 0;
    //    selector.set(0,0);
    //    start2();
    //}
//
    //public void start2(){
//
    //}
//
    //public void actionTouch(int action){
//
    //}
//
    //public void actionStart(int diff) {
    //    difficulty = diff;
    //    if(turnstate == 0) {
    //        turnstate = 2;
    //        start();
    //    } else {
    //        if(turnstate >= 5 && !hasToken()){
    //            turnstate = 1;
    //            start();
    //        } else {
    //            turnstate = 0;
    //        }
    //    }
    //}
//
    //public void update(){
//
    //}
//
//
//
    //// Getter Stuff
    //public Vector2      getVector    (int index){ return  null; }
    //public Dice getDice      (int index){ return  null; }
    //public Card getCard      (int index){ return  null; }
    //public List<Card> getCardStack (int index){ return  null; }
    //public Entity getEntity    (int index){ return  null; }
    //public List<Entity> getEntityList(int index){ return  null; }
    //public int          getValue     (int index){ return     0; }
    //public boolean      getFlag      (int index){ return false; }
    //public String       getString    (int index){ return    ""; }



    // Basic Stuff
    //public boolean   hasCustomName() { return false; }
    //public int       getSizeInventory() { return 0; }
    //public ItemStack getStackInSlot(int index) { return null; }
    //public ItemStack decrStackSize(int index, int count) { return null; }
    //public ItemStack removeStackFromSlot(int index) { return null; }
    //public void      setInventorySlotContents(int index, ItemStack stack) { }
    //public int       getInventoryStackLimit() { return 0; }
    //public void      openInventory(EntityPlayer player) { }
    //public void      closeInventory(EntityPlayer player) { }
    //public boolean   isItemValidForSlot(int index, ItemStack stack) { return false; }
    //public int       getField(int id) { return 0; }
    //public void      setField(int id, int value) { }
    //public int       getFieldCount() { return 0; }
    //public void      clear() { }
    //public boolean   isEmpty() { return false; }
    //public boolean   isUsableByPlayer(EntityPlayer player) { return true; }
}
