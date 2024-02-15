package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.util.Ship;
import mod.casinocraft.util.mapping.MapRoom;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_IMPACT;
import static mod.lucky77.util.KeyMap.*;

public class Logic62 extends LogicModule {   //  Sokoban
	
	// GAME MODE -- SOKOBAN
	// RULE 1 -- ??
	// RULE 2 -- ??
	// RULE 3 -- ??
	// COLOR VARIATION -- Background Design
	
	// --------------------------------------------------
	
	public Ship octanom = new Ship(1, new Vector2(16 * 15, 16 * 15), new Vector2(16 * 15, 16 * 15));
	public List<Ship> crate = new ArrayList<Ship>();
	public List<Ship> cross = new ArrayList<Ship>();
	public boolean moving;
	public int mapID;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic62(int tableID) {
		super(tableID, 12, 15);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		crate.clear();
		cross.clear();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		if(turnstate == 3){
			commandMove(action);
		} else if(turnstate == 2){
			commandSelect(action);
			if(action == KEY_ENTER){
				loadMap();
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
		if(moving) {
			boolean swittch = true;
			for(Ship c : crate) {
				if(c.getPos().X == c.getNext().X * 16 && c.getPos().Y == c.getNext().Y * 16) {
					c.setInMotion(0, 0);
				} else {
					swittch = false;
				}
			}
			if(swittch) {
				moving = false;
			}
		}
		if(turnstate >= 2 && turnstate < 4) {
			octanom.update();
			boolean win = true;
			for(Ship e : crate) {
				e.update();
				boolean hp1 = true;
				for(Ship c : cross) {
					if(c.getPos().matches(e.getPos())) {
						hp1 = false;
					}
				}
				if(hp1) { e.setHP(1); win = false; } else { e.setHP(2); }
			}
			for(Ship e : cross) {
				e.update();
			}
			if(win && turnstate == 3) {
				boolean unlocked = false;
				for(int i = 0; i < 20; i++){
					if(scoreHigh[i] == mapID+1){
						unlocked = true;
					}
				}
				if(!unlocked){
					scorePoint = mapID+1;
				}
				turnstate = 4;
			}
		}
	}
	
	public void updateMotion() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		octanom = loadEntity(     compound, 0);
		crate   = (loadEntityList(compound, 1));
		cross   = (loadEntityList(compound, 2));
		moving  = compound.getBoolean("moving");
		mapID   = compound.getInt("map_id");
	}
	
	public CompoundTag save2(CompoundTag compound){
		saveEntity(    compound, 0, octanom);
		saveEntityList(compound, 1, crate);
		saveEntityList(compound, 2, cross);
		compound.putBoolean("moving", moving);
		compound.putInt("map_id",     mapID);
		return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void loadMap() {
		turnstate = 3;
		List<String> list = MapRoom.loadSokoban(mapID);
		int y = 0;
		for(String s : list) {
			for(int x = 0; x < s.length(); x++) {
				char c = s.charAt(x);
				switch(c) {
					case ' ': break;
					case 'X': grid[x][y] = 1; break;
					case 'O': octanom = new Ship(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y));  break;
					case 'M': cross.add(new Ship(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
					case 'C': crate.add(new Ship(1, new Vector2(16 * x, 16 * y), new Vector2(16 * x, 16 * y))); break;
				}
			}
			y++;
		}
	}
	
	private void commandMove(int direction) {
		if(!octanom.isMoving()){
			int x = 0;
			int y = 0;
			if(direction == KEY_UP){    x =  0; y = -1; }
			if(direction == KEY_DOWN){  x =  0; y =  1; }
			if(direction == KEY_LEFT){  x = -1; y =  0; }
			if(direction == KEY_RIGHT){ x =  1; y =  0; }
			if(grid[octanom.getGrid().X + x][octanom.getGrid().Y + y] == 0) { // Free space
				boolean blockedO = false;
				for(Ship c : crate) {
					if(c.getGrid().X == octanom.getGrid().X + x && c.getGrid().Y == octanom.getGrid().Y + y) {
						blockedO = true;
						setJingle(SOUND_IMPACT);
						if(grid[octanom.getGrid().X + x*2][octanom.getGrid().Y + y*2] == 0) {
							boolean blockedC = false;
							for(Ship c2 : crate) {
								if(c.getGrid().X == octanom.getGrid().X + x*2 && c.getGrid().Y == octanom.getGrid().Y + y*2) {
									blockedC = true;
									setJingle(SOUND_IMPACT);
									// crate blocked by crate
								}
							}
							if(!blockedC) {
								moving = true;
								c.setInMotion(x*4, y*4);
							}
						}
					}
				}
				if(!blockedO)
					octanom.setInMotion(x*4, y*4);
			}
		}
	}
	
	private void commandSelect(int direction){
		if(direction == KEY_UP){    if(mapID / 4 > 0) { mapID -= 4; } }
		if(direction == KEY_DOWN){  if(mapID / 4 < 4) { mapID += 4; } }
		if(direction == KEY_LEFT){  if(mapID % 4 > 0) { mapID -= 1; } }
		if(direction == KEY_RIGHT){ if(mapID % 4 < 3) { mapID += 1; } }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 19;
	}
	
	public String getName(){
		return "sokoban";
	}
	
	
	
}