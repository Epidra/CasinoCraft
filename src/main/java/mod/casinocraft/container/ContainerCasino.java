package mod.casinocraft.container;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.LogicData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerCasino extends ContainerBase {

    public final IInventory inventory;
    private final World world;
    private final IIntArray casinoData;
    private final LogicData logicData;
    private BlockPos pos = new BlockPos(0, 0, 0);
    public DyeColor color;
    public int tableID;
    protected LogicBase LOGIC;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.fromLong(packetBuffer.readLong()));
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityBoard) playerInventory.player.getEntityWorld().getTileEntity(pos));
        this.pos = pos;
    }

    public ContainerCasino(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityBoard board) {
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




    //----------------------------------------GET/SET----------------------------------------//

    public LogicBase logic(){
        return logicData.get();
    }
    public int turnstate(){
        return logicData.get().turnstate;
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
    public void setBetStorage(int value){
        casinoData.set(0, value);
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
        TileEntityBoard te = (TileEntityBoard) world.getTileEntity(pos);
        return logic().currentPlayer[index];
    }
}