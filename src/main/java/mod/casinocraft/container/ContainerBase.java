package mod.casinocraft.container;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.tileentities.TileEntityMachine;
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

public abstract class ContainerBase extends mod.lucky77.container.ContainerBase {

    public DyeColor color;
    public int tableID;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ContainerBase(ContainerType<?> type, int windowID, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(type, windowID, playerInventory, BlockPos.of(packetBuffer.readLong()));
    }

    public ContainerBase(ContainerType<?> type, int windowID, PlayerInventory playerInventory, BlockPos pos) {
        this(type, windowID, playerInventory, (TileEntityMachine) playerInventory.player.getCommandSenderWorld().getBlockEntity(pos));
        this.pos = pos;
    }

    public ContainerBase(ContainerType<?> type, int windowID, PlayerInventory playerInventory, TileEntityMachine tile) {
        super(type, windowID, playerInventory, tile);
        color = tile.color;
        tableID = tile.tableID;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------GET/SET----------------------------------------//

    public int       getStorageToken(){          return data.get( 0); }
    public int       getStoragePrize(){          return data.get( 1); }
    public int       getBettingLow(){            return data.get( 2); }
    public int       getBettingHigh(){           return data.get( 3); }
    public int       getPrizeScore1(){           return data.get( 4); }
    public int       getPrizeScore2(){           return data.get( 5); }
    public int       getPrizeScore3(){           return data.get( 6); }
    public int       getPrizeCount1(){           return data.get( 7); }
    public int       getPrizeCount2(){           return data.get( 8); }
    public int       getPrizeCount3(){           return data.get( 9); }
    public boolean   getPrizeMode1(){            return data.get(10) == 1; }
    public boolean   getPrizeMode2(){            return data.get(11) == 1; }
    public boolean   getPrizeMode3(){            return data.get(12) == 1; }
    public boolean   getTransferTokenIN(){       return data.get(13) == 1; }
    public boolean   getTransferTokenOUT(){      return data.get(14) == 1; }
    public boolean   getTransferPrizeIN(){       return data.get(15) == 1; }
    public boolean   getTransferPrizeOUT(){      return data.get(16) == 1; }
    public boolean   getSettingInfiniteToken(){  return data.get(17) == 1; }
    public boolean   getSettingInfinitePrize(){  return data.get(18) == 1; }
    public boolean   getSettingDropOnBreak(){    return data.get(19) == 1; }
    public boolean   getSettingIndestructable(){ return data.get(20) == 1; }
    public int       getSettingAlternateColor(){ return data.get(21); }
    public ItemStack getItemToken(){             return inventory.getItem(3); }
    public ItemStack getItemPrize(){             return inventory.getItem(4); }

    public void setStorageToken(int value){              data.set( 0, value); }
    public void setStoragePrize(int value){              data.set( 1, value); }
    public void setBettingLow(int value){                data.set( 2, value); }
    public void setBettingHigh(int value){               data.set( 3, value); }
    public void setPrizeScore1(int value){               data.set( 4, value); }
    public void setPrizeScore2(int value){               data.set( 5, value); }
    public void setPrizeScore3(int value){               data.set( 6, value); }
    public void setPrizeCount1(int value){               data.set( 7, value); }
    public void setPrizeCount2(int value){               data.set( 8, value); }
    public void setPrizeCount3(int value){               data.set( 9, value); }
    public void setPrizeMode1(boolean value){            data.set(10, value ? 1 : 0); }
    public void setPrizeMode2(boolean value){            data.set(11, value ? 1 : 0); }
    public void setPrizeMode3(boolean value){            data.set(12, value ? 1 : 0); }
    public void setTransferTokenIN(boolean value){       data.set(13, value ? 1 : 0); }
    public void setTransferTokenOUT(boolean value){      data.set(14, value ? 1 : 0); }
    public void setTransferPrizeIN(boolean value){       data.set(15, value ? 1 : 0); }
    public void setTransferPrizeOUT(boolean value){      data.set(16, value ? 1 : 0); }
    public void setSettingInfiniteToken(boolean value){  data.set(17, value ? 1 : 0); }
    public void setSettingInfinitePrize(boolean value){  data.set(18, value ? 1 : 0); }
    public void setSettingDropOnBreak(boolean value){    data.set(19, value ? 1 : 0); }
    public void setSettingIndestructable(boolean value){ data.set(20, value ? 1 : 0); }
    public void setSettingAlternateColor(int value){     data.set(21, value); }
    public void setItemToken(ItemStack itemStack){       inventory.setItem(3, itemStack); }
    public void setItemPrize(ItemStack itemStack){       inventory.setItem(4, itemStack); }

    public boolean   hasKey(){       return !this.inventory.getItem(0).isEmpty(); }
    public boolean   hasModule(){    return !this.inventory.getItem(1).isEmpty(); }
    public boolean   hasToken(){     return data.get( 0) > 0; }
    public boolean   hasReward(){    return data.get( 1) > 0; }

    public BlockPos  pos(){ return pos; }
    public World     world(){
        return this.world;
    }
    public LogicModule logic(){
        return (LogicModule) logic;
    }
    public int       turnstate(){
        return logic().turnstate;
    }

    public abstract int getID();

    public String getCurrentPlayer(int index){
        return logic().currentPlayer[index];
    }



}
