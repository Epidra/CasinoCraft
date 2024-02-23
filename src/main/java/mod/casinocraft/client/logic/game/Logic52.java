package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import net.minecraft.nbt.CompoundTag;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_TETRIS;
import static mod.lucky77.util.KeyMap.*;

public class Logic52 extends LogicModule {   //  2048  :  Mystic Square
	
	public boolean canUndo = false;
	public boolean placing = false;
	public boolean timerActive = false;
	public int timer = 0;
	public int direction = 0; // 0 - null, 1 - up, 2 - down, 3 - left, 4 - right
	
	private int[] undo = new int[17];
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic52(int tableID) {
		super(tableID, 4, 4);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		placing = false;
		
		timerActive = false;
		timer = 0;
		direction = 0;
		
		for(int i = 0; i < 17; i++){
			undo[i] = 0;
		}
		
		// Main Rule - 2048
		if(ruleSet[0] == 0){
			grid[0][0] = RANDOM.nextInt(1) + 1;
			grid[3][0] = RANDOM.nextInt(1) + 1;
			grid[0][3] = RANDOM.nextInt(1) + 1;
			grid[3][3] = RANDOM.nextInt(1) + 1;
		}
		
		// Main Rule - Mystic Square
		if(ruleSet[0] == 1){
			for(int y = 0; y < 4; y++) {
				for(int x = 0; x < 4; x++) {
					grid[x][y] = -1;
				}
			}
			int i = 0;
			while(i < 15) {
				int x = RANDOM.nextInt(4);
				int y = RANDOM.nextInt(4);
				if(grid[x][y] == -1) {
					grid[x][y] = i;
					i++;
				}
			}
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		if(action == KEY_UP) move(1);
		if(action == KEY_DOWN) move(2);
		if(action == KEY_LEFT) move(3);
		if(action == KEY_RIGHT) move(4);
		if(action == KEY_ENTER) undoTurn();
		if(action == KEY_UP || action == KEY_DOWN || action == KEY_LEFT || action == KEY_RIGHT){
			if(ruleSet[1] == 0){
				undo[ 0] = grid[0][0];
				undo[ 1] = grid[0][1];
				undo[ 2] = grid[0][2];
				undo[ 3] = grid[0][3];
				undo[ 4] = grid[1][0];
				undo[ 5] = grid[1][1];
				undo[ 6] = grid[1][2];
				undo[ 7] = grid[1][3];
				undo[ 8] = grid[2][0];
				undo[ 9] = grid[2][1];
				undo[10] = grid[2][2];
				undo[11] = grid[2][3];
				undo[12] = grid[3][0];
				undo[13] = grid[3][1];
				undo[14] = grid[3][2];
				undo[15] = grid[3][3];
				undo[16] = scorePoint;
			}
			canUndo = false;
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
		if(timerActive) {
			timer += 16;
			if(timer == 48) {
				timerActive = false;
				timer = 0;
				change();
				if(ruleSet[0] == 0){
					move(direction);
				}
				placing = true;
			}
		} else {
			if(placing) {
				place();
				placing = false;
			}
			direction = 0;
		}
	}
	
	public void updateMotion() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		placing = compound.getBoolean("placing");
		timerActive = compound.getBoolean("timeractive");
		timer = compound.getInt("timer");
		direction = compound.getInt("direction");
	}
	
	public CompoundTag save2(CompoundTag compound){
		compound.putBoolean("placing", placing);
		compound.putBoolean("timeractive", timerActive);
		compound.putInt("timer", timer);
		compound.putInt("direction", direction);
		return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void move(int s) {
		
		// ----- UP ----- //
		if(s == 1) {
			for(int y = 1; y < 4; y++) {
				for(int x = 0; x < 4; x++) {
					if(grid[x][y] != 0) {
						if(grid[x][y - 1] == 0 || grid[x][y - 1] == grid[x][y]) {
							grid[x][y] += 100;
							timerActive = true;
							direction = s;
						}
					}
				}
			}
		}
		
		// ----- DOWN ----- //
		if(s == 2) {
			for(int y = 2; y > -1; y--) {
				for(int x = 3; x > -1; x--) {
					if(grid[x][y] != 0) {
						if(grid[x][y + 1] == 0 || grid[x][y + 1] == grid[x][y]) {
							grid[x][y] += 100;
							timerActive = true;
							direction = s;
						}
					}
				}
			}
		}
		
		// ----- LEFT ----- //
		if(s == 3) {
			for(int x = 1; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					if(grid[x][y] != 0) {
						if(grid[x - 1][y] == 0 || grid[x - 1][y] == grid[x][y]) {
							grid[x][y] += 100;
							timerActive = true;
							direction = s;
						}
					}
				}
			}
		}
		
		// ----- RIGHT ----- //
		if(s == 4) {
			for(int x = 2; x > -1; x--) {
				for(int y = 3; y > -1; y--) {
					if(grid[x][y] != 0) {
						if(grid[x + 1][y] == 0 || grid[x + 1][y] == grid[x][y]) {
							grid[x][y] += 100;
							timerActive = true;
							direction = s;
						}
					}
				}
			}
		}
	}
	
	private void change() {
		
		// ----- UP ----- //
		if(direction == 1) {
			for(int y = 1; y < 4; y++) {
				for(int x = 0; x < 4; x++) {
					if(grid[x][y] >= 100) {
						grid[x][y] -= 100;
						if(grid[x][y - 1] == 0) {
							grid[x][y - 1] = grid[x][y];
						} else {
							grid[x][y - 1] = grid[x][y - 1] + 1;
							addPoints(grid[x][y - 1]);
						}
						grid[x][y] = 0; // sets FLAG automatically to FALSE
					}
				}
			}
		}
		
		// ----- DOWN ----- //
		if(direction == 2) {
			for(int y = 2; y > -1; y--) {
				for(int x = 3; x > -1; x--) {
					if(grid[x][y] >= 100) {
						grid[x][y] -= 100;
						if(grid[x][y + 1] == 0) {
							grid[x][y + 1] = grid[x][y];
						} else {
							grid[x][y + 1] = grid[x][y+1] + 1;
							addPoints(grid[x][y + 1]);
						}
						grid[x][y] = 0;
					}
				}
			}
		}
		
		// ----- LEFT ----- //
		if(direction == 3) {
			for(int x = 1; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					if(grid[x][y] >= 100) {
						grid[x][y] -= 100;
						if(grid[x - 1][y] == 0) {
							grid[x - 1][y] = grid[x][y];
						} else {
							grid[x - 1][y] = grid[x - 1][y] + 1;
							addPoints(grid[x - 1][y]);
						}
						grid[x][y] = 0;
					}
				}
			}
		}
		
		// ----- RIGHT ----- //
		if(direction == 4) {
			for(int x = 2; x > -1; x--) {
				for(int y = 3; y > -1; y--) {
					if(grid[x][y] >= 100) {
						grid[x][y] -= 100;
						if(grid[x + 1][y] == 0) {
							grid[x + 1][y] = grid[x][y];
						} else {
							grid[x + 1][y] = grid[x + 1][y] + 1;
							addPoints(grid[x + 1][y]);
						}
						grid[x][y] = 0;
					}
				}
			}
		}
	}
	
	private void place() {
		if(ruleSet[0] == 0){
			for(int i = 0; i < 24; i++) {
				int x = RANDOM.nextInt(4);
				int y = RANDOM.nextInt(4);
				if(grid[x][y] == 0) { // FLAGGING ???
					grid[x][y] = 1;
					break;
				}
			}
		}
		check();
		if(ruleSet[1] == 0){
			canUndo = true;
		}
	}
	
	private void check() {
		boolean b = false;
		if(ruleSet[0] == 0){
			// boolean b = false;
			for(int y = 1; y < 4; y++) {
				for(int x = 0; x < 4; x++) {
					if(grid[x][y] != 0) {
						if(grid[x][y - 1] == 0 || grid[x][y - 1] == grid[x][y]) {
							b = true;
							break;
						}
					}
				}
				if(b) break;
			}
			for(int y = 2; y > -1; y--) {
				for(int x = 3; x > -1; x--) {
					if(grid[x][y] != 0) {
						if(grid[x][y + 1] == 0 || grid[x][y + 1] == grid[x][y]) {
							b = true;
							break;
						}
					}
				}
				if(b) break;
			}
			for(int x = 1; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					if(grid[x][y] != 0) {
						if(grid[x - 1][y] == 0 || grid[x - 1][y] == grid[x][y]) {
							b = true;
							break;
						}
					}
				}
				if(b) break;
			}
			for(int x = 2; x > -1; x--) {
				for(int y = 3; y > -1; y--) {
					if(grid[x][y] != 0) {
						if(grid[x + 1][y] == 0 || grid[x + 1][y] == grid[x][y]) {
							b = true;
							break;
						}
					}
				}
				if(b) break;
			}
		}
		if(ruleSet[0] == 1){
			if(grid[0][0] != 0 || grid[0][1] != 1 || grid[0][2] != 2 || grid[0][3] != 3
			&& grid[1][0] != 4 || grid[1][1] != 5 || grid[1][2] != 6 || grid[1][3] != 7
			&& grid[2][0] != 8 || grid[2][1] != 9 || grid[2][2] != 10 || grid[2][3] != 11
			&& grid[3][0] != 12 || grid[3][1] != 13 || grid[3][2] != 14){
				b = true;
			}
		}
		if(!b) {
			turnstate = 4;
		}
	}
	
	private void undoTurn(){
		if(canUndo){
			canUndo = false;
			grid[0][0] = undo[ 0];
			grid[0][1] = undo[ 1];
			grid[0][2] = undo[ 2];
			grid[0][3] = undo[ 3];
			
			grid[1][0] = undo[ 4];
			grid[1][1] = undo[ 5];
			grid[1][2] = undo[ 6];
			grid[1][3] = undo[ 7];
			
			grid[2][0] = undo[ 8];
			grid[2][1] = undo[ 9];
			grid[2][2] = undo[10];
			grid[2][3] = undo[11];
			
			grid[3][0] = undo[12];
			grid[3][1] = undo[13];
			grid[3][2] = undo[14];
			grid[3][3] = undo[15];
			
			scorePoint = undo[16];
		}
	}
	
	private void addPoints(int i) {
		if(i ==  1) scorePoint +=     2;
		if(i ==  2) scorePoint +=     4;
		if(i ==  3) scorePoint +=     8;
		if(i ==  4) scorePoint +=    16;
		if(i ==  5) scorePoint +=    32;
		if(i ==  6) scorePoint +=    64;
		if(i ==  7) scorePoint +=   128;
		if(i ==  8) scorePoint +=   256;
		if(i ==  9) scorePoint +=   512;
		if(i == 10) scorePoint +=  1024;
		if(i == 11) scorePoint +=  2048;
		if(i == 12) scorePoint +=  4096;
		if(i == 13) scorePoint +=  8192;
		if(i == 14) scorePoint += 16384;
		if(i == 15) scorePoint += 32768;
		if(i == 16) scorePoint += 65536;
		setJingle(SOUND_TETRIS);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 52;
	}
	
	public String getName(){
		return ruleSet[0] == 0 ? "2048" : "mystic_square";
	}
	
	
	
}