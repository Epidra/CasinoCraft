package mod.casinocraft.client.logic.other;

import mod.casinocraft.client.logic.LogicModule;
import net.minecraft.nbt.CompoundTag;

public class LogicDummy extends LogicModule {  //  -----
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public LogicDummy(int tableID) {
		super(tableID);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
	
	}
	
	public void updateMotion() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
	
	}
	
	public CompoundTag save2(CompoundTag compound){
		return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return false;
	}
	
	public boolean isMultiplayer(){
		return true;
	}
	
	public int getID(){
		return 48;
	}
	
	public String getName(){
		return "";
	}
	
	
	
}
