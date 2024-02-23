package mod.casinocraft.client.menu;

import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.common.block.entity.BlockEntityMachine;
import mod.lucky77.block.entity.BlockEntityBase;
import mod.lucky77.menu.MenuBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class MenuCasino extends MenuBase {
	
	public DyeColor color;
	public int tableID;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public MenuCasino(MenuType<?> type, int windowID, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
		this(type, windowID, playerInventory, BlockPos.of(packetBuffer.readLong()));
	}
	
	public MenuCasino(MenuType<?> type, int windowID, Inventory playerInventory, BlockPos pos) {
		this(type, windowID, playerInventory, (BlockEntityMachine) playerInventory.player.getCommandSenderWorld().getBlockEntity(pos));
		this.pos = pos;
	}
	
	public MenuCasino(MenuType<?> type, int windowID, Inventory playerInventory, BlockEntityMachine board) {
		super(type, windowID, playerInventory, board);
		color   = board.color;
		tableID = board.tableID;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	protected void createInventory(Container tile, Inventory playerInventory){
	
	}
	
	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
		return ItemStack.EMPTY;
	}
	
	public int       getStorageToken(){          return data.get( 0  );       }
	public int       getStoragePrize(){          return data.get( 1  );       }
	public int       getBettingLow(){            return data.get( 2  );       }
	public int       getBettingHigh(){           return data.get( 3  );       }
	public int       getPrizeScore(int i){       return data.get( 4+i);       }
	public int       getPrizeScore1(){           return data.get( 4  );       }
	public int       getPrizeScore2(){           return data.get( 5  );       }
	public int       getPrizeScore3(){           return data.get( 6  );       }
	public int       getPrizeCount(int i){       return data.get( 7+i);       }
	public int       getPrizeCount1(){           return data.get( 7  );       }
	public int       getPrizeCount2(){           return data.get( 8  );       }
	public int       getPrizeCount3(){           return data.get( 9  );       }
	public boolean   getPrizeMode(int i){        return data.get(10+i) == 1;  }
	public boolean   getPrizeMode1(){            return data.get(10  ) == 1;  }
	public boolean   getPrizeMode2(){            return data.get(11  ) == 1;  }
	public boolean   getPrizeMode3(){            return data.get(12  ) == 1;  }
	public boolean   getTransferTokenIN(){       return data.get(13  ) == 1;  }
	public boolean   getTransferTokenOUT(){      return data.get(14  ) == 1;  }
	public boolean   getTransferPrizeIN(){       return data.get(15  ) == 1;  }
	public boolean   getTransferPrizeOUT(){      return data.get(16  ) == 1;  }
	public boolean   getSettingInfiniteToken(){  return data.get(17  ) == 1;  }
	public boolean   getSettingInfinitePrize(){  return data.get(18  ) == 1;  }
	public boolean   getSettingDropOnBreak(){    return data.get(19  ) == 1;  }
	public boolean   getSettingIndestructable(){ return data.get(20  ) == 1;  }
	public boolean   getSettingAlternateScore(){ return data.get(21  ) == 1;  }
	public int       getSettingAlternateColor(){
		return data.get(22  );
	}
	public int       getSettingRule_1(){ return data.get(23  );       }
	public int       getSettingRule_2(){ return data.get(24  );       }
	public int       getSettingRule_3(){ return data.get(25  );       }
	public int       getSettingRule_4(){ return data.get(26  );       }
	public int       getSettingRule_5(){ return data.get(27  );       }
	public ItemStack getItemToken(){             return container.getItem(3); }
	public ItemStack getItemPrize(){             return container.getItem(4); }
	
	public void setStorageToken(int value){              data.set( 0, value        ); }
	public void setStoragePrize(int value){              data.set( 1, value        ); }
	public void setBettingLow(int value){                data.set( 2, value        ); }
	public void setBettingHigh(int value){               data.set( 3, value        ); }
	public void setPrizeScore1(int value){               data.set( 4, value        ); }
	public void setPrizeScore2(int value){               data.set( 5, value        ); }
	public void setPrizeScore3(int value){               data.set( 6, value        ); }
	public void setPrizeCount1(int value){               data.set( 7, value        ); }
	public void setPrizeCount2(int value){               data.set( 8, value        ); }
	public void setPrizeCount3(int value){               data.set( 9, value        ); }
	public void setPrizeMode1(boolean value){            data.set(10, value ? 1 : 0); }
	public void setPrizeMode2(boolean value){            data.set(11, value ? 1 : 0); }
	public void setPrizeMode3(boolean value){            data.set(12, value ? 1 : 0); }
	public void setTransferTokenIN(boolean value){       data.set(13, value ? 1 : 0); }
	public void setTransferTokenOUT(boolean value){      data.set(14, value ? 1 : 0); }
	public void setTransferPrizeIN(boolean value){       data.set(15, value ? 1 : 0); }
	public void setTransferPrizeOUT(boolean value){      data.set(16, value ? 1 : 0); }
	public boolean setSettingInfiniteToken(boolean value){  data.set(17, value ? 1 : 0); return data.get(17) == 1; }
	public boolean setSettingInfinitePrize(boolean value){  data.set(18, value ? 1 : 0); return data.get(18) == 1; }
	public boolean setSettingDropOnBreak(boolean value){    data.set(19, value ? 1 : 0); return data.get(19) == 1; }
	public boolean setSettingIndestructable(boolean value){ data.set(20, value ? 1 : 0); return data.get(20) == 1; }
	public boolean setSettingAlternateScore(boolean value){ data.set(21, value ? 1 : 0); return data.get(21) == 1; }
	public void setSettingAlternateColor(int value){
		data.set(22, value);
	}
	public void setSettingRule_1(int value){     data.set(23, value); }
	public void setSettingRule_2(int value){     data.set(24, value); }
	public void setSettingRule_3(int value){     data.set(25, value); }
	public void setSettingRule_4(int value){     data.set(26, value); }
	public void setSettingRule_5(int value){     data.set(27, value); }
	public void setItemToken(ItemStack itemStack){       container.setItem(3, itemStack); }
	public void setItemPrize(ItemStack itemStack){       container.setItem(4, itemStack); }
	
	public boolean   hasKey(){       return !this.container.getItem(0).isEmpty(); }
	public boolean   hasModule(){    return !this.container.getItem(1).isEmpty(); }
	public boolean   hasToken(){     return data.get( 0) > 0; }
	public boolean   hasReward(){    return data.get( 1) > 0; }
	
	public Item getModule(){
		return getItems().get(1).getItem();
	}
	
	public BlockPos  pos(){ return pos; }
	public Level world(){
		return this.level;
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