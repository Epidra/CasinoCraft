package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_CHIP;

public class Logic41 extends LogicModule {   //  Minesweeper
	
	// GAME MODE -- MINESWEEPER, MINOFLIP
	// RULE 1 -- Lives (1, 2, 3, 4, 5)
	// RULE 2 -- Large Size, Small Size
	// RULE 3 -- +Bombs per Level (1, 2, 3, 4, 5)
	// COLOR VARIATION -- ??
	
	// Add Entry Animation
	
	// --------------------------------------------------
	
	private List<Vector2> FieldList = new ArrayList<Vector2>();
	public int bombs = 0;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic41(int tableID) {
		super(tableID, 26, 14 /* 17, 9 */ );
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		// scorePoint = 1;
		// scoreLevel = 1;
		// fillGrid();
		
		//
		// // ---
		//
		
		bombs = 0;
		FieldList.clear();
		scoreLevel = 1;
		fillGrid();
	}
	
	public void restart(){
		// turnstate = 2;
		// scoreLevel++;
		// fillGrid();
		
		//
		// // ---
		//
		
		turnstate = 2;
		FieldList.clear();
		scoreLevel++;
		resetGrid();
		fillGrid();
		
		//
		// // ---
		//
		// if(tableID == 1) {
		// 	selector.set(8, 2);
		// 	grid[0][0] = -1; grid[1][0] = -1; grid[2][0] = -1; grid[3][0] = -1; grid[4][0] = -1; grid[5][0] = -1; grid[6][0] = 1; grid[7][0] =  1; grid[8][0] =  1; grid[9][0] =  1; grid[10][0] = 1; grid[11][0] = -1; grid[12][0] = -1; grid[13][0] = -1; grid[14][0] = -1; grid[15][0] = -1; grid[16][0] = -1;
		// 	grid[0][1] = -1; grid[1][1] = -1; grid[2][1] = -1; grid[3][1] = -1; grid[4][1] = -1; grid[5][1] = -1; grid[6][1] = 1; grid[7][1] =  1; grid[8][1] =  1; grid[9][1] =  1; grid[10][1] = 1; grid[11][1] = -1; grid[12][1] = -1; grid[13][1] = -1; grid[14][1] = -1; grid[15][1] = -1; grid[16][1] = -1;
		// 	grid[0][2] = -1; grid[1][2] = -1; grid[2][2] = -1; grid[3][2] = -1; grid[4][2] =  1; grid[5][2] =  1; grid[6][2] = 1; grid[7][2] =  1; grid[8][2] =  1; grid[9][2] =  1; grid[10][2] = 1; grid[11][2] =  1; grid[12][2] =  1; grid[13][2] = -1; grid[14][2] = -1; grid[15][2] = -1; grid[16][2] = -1;
		// 	grid[0][3] = -1; grid[1][3] = -1; grid[2][3] = -1; grid[3][3] = -1; grid[4][3] =  1; grid[5][3] =  1; grid[6][3] = 1; grid[7][3] =  1; grid[8][3] =  1; grid[9][3] =  1; grid[10][3] = 1; grid[11][3] =  1; grid[12][3] =  1; grid[13][3] = -1; grid[14][3] = -1; grid[15][3] = -1; grid[16][3] = -1;
		// 	grid[0][4] = -1; grid[1][4] = -1; grid[2][4] = -1; grid[3][4] = -1; grid[4][4] =  1; grid[5][4] =  1; grid[6][4] = 1; grid[7][4] =  1; grid[8][4] =  0; grid[9][4] =  1; grid[10][4] = 1; grid[11][4] =  1; grid[12][4] =  1; grid[13][4] = -1; grid[14][4] = -1; grid[15][4] = -1; grid[16][4] = -1;
		// 	grid[0][5] = -1; grid[1][5] = -1; grid[2][5] = -1; grid[3][5] = -1; grid[4][5] =  1; grid[5][5] =  1; grid[6][5] = 1; grid[7][5] =  1; grid[8][5] =  1; grid[9][5] =  1; grid[10][5] = 1; grid[11][5] =  1; grid[12][5] =  1; grid[13][5] = -1; grid[14][5] = -1; grid[15][5] = -1; grid[16][5] = -1;
		// 	grid[0][6] = -1; grid[1][6] = -1; grid[2][6] = -1; grid[3][6] = -1; grid[4][6] =  1; grid[5][6] =  1; grid[6][6] = 1; grid[7][6] =  1; grid[8][6] =  1; grid[9][6] =  1; grid[10][6] = 1; grid[11][6] =  1; grid[12][6] =  1; grid[13][6] = -1; grid[14][6] = -1; grid[15][6] = -1; grid[16][6] = -1;
		// 	grid[0][7] = -1; grid[1][7] = -1; grid[2][7] = -1; grid[3][7] = -1; grid[4][7] = -1; grid[5][7] = -1; grid[6][7] = 1; grid[7][7] =  1; grid[8][7] =  1; grid[9][7] =  1; grid[10][7] = 1; grid[11][7] = -1; grid[12][7] = -1; grid[13][7] = -1; grid[14][7] = -1; grid[15][7] = -1; grid[16][7] = -1;
		// 	grid[0][8] = -1; grid[1][8] = -1; grid[2][8] = -1; grid[3][8] = -1; grid[4][8] = -1; grid[5][8] = -1; grid[6][8] = 1; grid[7][8] =  1; grid[8][8] =  1; grid[9][8] =  1; grid[10][8] = 1; grid[11][8] = -1; grid[12][8] = -1; grid[13][8] = -1; grid[14][8] = -1; grid[15][8] = -1; grid[16][8] = -1;
		// } else {
		// 	selector.set(8, 4);
		// 	grid[0][0] = -1; grid[1][0] = -1; grid[2][0] = -1; grid[3][0] =  1; grid[4][0] =  1; grid[5][0] =  1; grid[6][0] = 1; grid[7][0] = -1; grid[8][0] = -1; grid[9][0] = -1; grid[10][0] = 1; grid[11][0] =  1; grid[12][0] =  1; grid[13][0] =  1; grid[14][0] = -1; grid[15][0] = -1; grid[16][0] = -1;
		// 	grid[0][1] = -1; grid[1][1] = -1; grid[2][1] = -1; grid[3][1] =  1; grid[4][1] =  1; grid[5][1] =  1; grid[6][1] = 1; grid[7][1] = -1; grid[8][1] = -1; grid[9][1] = -1; grid[10][1] = 1; grid[11][1] =  1; grid[12][1] =  1; grid[13][1] =  1; grid[14][1] = -1; grid[15][1] = -1; grid[16][1] = -1;
		// 	grid[0][2] =  1; grid[1][2] =  1; grid[2][2] =  1; grid[3][2] =  1; grid[4][2] =  1; grid[5][2] =  1; grid[6][2] = 1; grid[7][2] =  1; grid[8][2] =  1; grid[9][2] =  1; grid[10][2] = 1; grid[11][2] =  1; grid[12][2] =  1; grid[13][2] =  1; grid[14][2] =  1; grid[15][2] =  1; grid[16][2] =  1;
		// 	grid[0][3] =  1; grid[1][3] =  1; grid[2][3] =  1; grid[3][3] =  1; grid[4][3] =  1; grid[5][3] =  1; grid[6][3] = 1; grid[7][3] =  1; grid[8][3] =  1; grid[9][3] =  1; grid[10][3] = 1; grid[11][3] =  1; grid[12][3] =  1; grid[13][3] =  1; grid[14][3] =  1; grid[15][3] =  1; grid[16][3] =  1;
		// 	grid[0][4] =  1; grid[1][4] =  1; grid[2][4] =  1; grid[3][4] =  1; grid[4][4] =  0; grid[5][4] =  0; grid[6][4] = 1; grid[7][4] =  1; grid[8][4] =  1; grid[9][4] =  1; grid[10][4] = 1; grid[11][4] =  0; grid[12][4] =  0; grid[13][4] =  1; grid[14][4] =  1; grid[15][4] =  1; grid[16][4] =  1;
		// 	grid[0][5] =  1; grid[1][5] =  1; grid[2][5] =  1; grid[3][5] =  1; grid[4][5] =  1; grid[5][5] =  1; grid[6][5] = 1; grid[7][5] =  1; grid[8][5] =  1; grid[9][5] =  1; grid[10][5] = 1; grid[11][5] =  1; grid[12][5] =  1; grid[13][5] =  1; grid[14][5] =  1; grid[15][5] =  1; grid[16][5] =  1;
		// 	grid[0][6] =  1; grid[1][6] =  1; grid[2][6] =  1; grid[3][6] =  1; grid[4][6] =  1; grid[5][6] =  1; grid[6][6] = 1; grid[7][6] =  1; grid[8][6] =  1; grid[9][6] =  1; grid[10][6] = 1; grid[11][6] =  1; grid[12][6] =  1; grid[13][6] =  1; grid[14][6] =  1; grid[15][6] =  1; grid[16][6] =  1;
		// 	grid[0][7] = -1; grid[1][7] = -1; grid[2][7] = -1; grid[3][7] =  1; grid[4][7] =  1; grid[5][7] =  1; grid[6][7] = 1; grid[7][7] = -1; grid[8][7] = -1; grid[9][7] = -1; grid[10][7] = 1; grid[11][7] =  1; grid[12][7] =  1; grid[13][7] =  1; grid[14][7] = -1; grid[15][7] = -1; grid[16][7] = -1;
		// 	grid[0][8] = -1; grid[1][8] = -1; grid[2][8] = -1; grid[3][8] =  1; grid[4][8] =  1; grid[5][8] =  1; grid[6][8] = 1; grid[7][8] = -1; grid[8][8] = -1; grid[9][8] = -1; grid[10][8] = 1; grid[11][8] =  1; grid[12][8] =  1; grid[13][8] =  1; grid[14][8] = -1; grid[15][8] = -1; grid[16][8] = -1;
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		// if(action == -1) {
		// 	restart();
		// } else if(action == -2) {
		// 	turnstate = 4;
		// } else if(action >= 100){
		// 	setJingle(SOUND_CHIP);
		// 	mark(action - 100);
		// } else {
		// 	setJingle(SOUND_CHIP);
		// 	flip(action);
		// }
		
		//
		// // ---
		//
		
		if(action == -1) {
			restart();
		} else if(action == -2) {
			turnstate = 4;
		} else if(action >= 1000){
			setJingle(SOUND_CHIP);
			mark(action - 1000);
		} else {
			setJingle(SOUND_CHIP);
			flip(action);
		}
		
		//
		// // ---
		//
		// jump(action%17, action/17);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
	
	}
	
	public void updateMotion() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		bombs = compound.getInt("bombs");
	}
	
	public CompoundTag save2(CompoundTag compound){
		//return compound;
		
		// // ---
		//
		
		compound.putInt("bombs", bombs);
		return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void fillGrid(){
		// for(int y = 0; y < 5; y++){
		// 	for(int x = 0; x < 5; x++){
		// 		grid[x][y] = 1;
		// 	}
		// }
		// for(int i = 0; i < Math.min(scoreLevel + 8, 16); i++){
		// 	int x = RANDOM.nextInt(5);
		// 	int y = RANDOM.nextInt(5);
		// 	if(grid[x][y] == 1){
		// 		grid[x][y] = 0;
		// 	} else {
		// 		i--;
		// 	}
		// }
		// for(int i = 0; i < Math.min(scoreLevel * 2 + 6, 32); i++){
		// 	int x = RANDOM.nextInt(5);
		// 	int y = RANDOM.nextInt(5);
		// 	if(grid[x][y] > 0 && grid[x][y] <= 5){
		// 		grid[x][y]++;
		// 	} else {
		// 		i--;
		// 	}
		// }
		// for(int y = 0; y < 5; y++){
		// 	grid[5][y] = 0;
		// 	grid[6][y] = 0;
		// 	for(int x = 0; x < 5; x++){
		// 		if(grid[x][y] == 0){
		// 			grid[6][y]++;
		// 		} else {
		// 			grid[5][y] += grid[x][y];
		// 		}
		// 	}
		// }
		// for(int x = 0; x < 5; x++){
		// 	grid[x][5] = 0;
		// 	grid[x][6] = 0;
		// 	for(int y = 0; y < 5; y++){
		// 		if(grid[x][y] == 0){
		// 			grid[x][6]++;
		// 		} else {
		// 			grid[x][5] += grid[x][y];
		// 		}
		// 	}
		// }
		// for(int y = 0; y < 5; y++){
		// 	for(int x = 0; x < 5; x++){
		// 		grid[x][y] += 100;
		// 	}
		// }
		
		// private void fillGrid() {
		bombs = scoreLevel + scoreLevel * tableID * 3;
		int max = tableID == 1 ? 14*14 : 14*26;
		max -= max/4;
		if(bombs > max) bombs = max;
		for(int i = 0; i < bombs; i++) {
			int x = RANDOM.nextInt(tableID == 1 ? 14 : 26) + (tableID == 1 ? 6 : 0);
			int y = RANDOM.nextInt(14);
			if(grid[x][y] != 9) {
				grid[x][y] = 9;
			} else {
				i--;
			}
		}
		for(int y = 0; y < 14; y++) {
			for(int x = tableID == 1 ? 6 : 0; x < (tableID == 1 ? 20 : 26); x++) {
				if(grid[x][y] != 9) {
					int count = 0;
					if(x >  0 && y >  0) if(grid[x - 1][y - 1] % 100 == 9) count++; // -X -Y
					if(          y >  0) if(grid[x    ][y - 1] % 100 == 9) count++; //    -Y
					if(x < 25 && y >  0) if(grid[x + 1][y - 1] % 100 == 9) count++; // +X -Y
					if(x < 25          ) if(grid[x + 1][y    ] % 100 == 9) count++; // +X
					if(x < 25 && y < 13) if(grid[x + 1][y + 1] % 100 == 9) count++; // +X +Y
					if(          y < 13) if(grid[x    ][y + 1] % 100 == 9) count++; //    +Y
					if(x >  0 && y < 13) if(grid[x - 1][y + 1] % 100 == 9) count++; // -X +Y
					if(x >  0          ) if(grid[x - 1][y    ] % 100 == 9) count++; // -X
					grid[x][y] = count;
				}
				grid[x][y] += 100;
			}
		}
		// }
	}
	
	private void flip(int action){
		// int x = action % 5;
		// int y = action / 5;
		// if(grid[x][y] >= 100){
		// 	grid[x][y] = grid[x][y] % 100;
		// 	if(grid[x][y] == 0){
		// 		turnstate = 4;
		// 		scorePoint /= 2;
		// 	} else {
		// 		scorePoint *= grid[x][y];
		// 		checkGrid();
		// 	}
		// }
		
		// private void flip(int action) {
		int x = action % 26;
		int y = action / 26;
		if(grid[x][y] >= 100) {
			grid[x][y] = grid[x][y] % 100;
			if(grid[x][y] == 9) {
				grid[x][y] = 10;
				uncoverBombs();
				turnstate = 4;
				scorePoint = scorePoint / 2;
			} else {
				if(grid[x][y] == 0) {
					FieldList.add(new Vector2(x, y));
					uncoverTiles();
				}
				boolean uncoveredAll = true;
				for(int i = 0; i < 14; i++) {
					for(int j = 0; j < 26; j++) {
						if(grid[j][i] % 20 != 9) {
							if(grid[j][i] >= 20) {
								uncoveredAll = false;
							}
						}
					}
				}
				if(uncoveredAll) {
					turnstate = 3;
					scorePoint += bombs;
				}
			}
		}
		// }
	}
	
	private void mark(int action){
		// int x = action % 5;
		// int y = action / 5;
		// if(grid[x][y] >= 200){
		// 	grid[x][y] -= 100;
		// } else if(grid[x][y] >= 100){
		// 	grid[x][y] += 100;
		// }
		
		// private void mark(int action){
		int x = action % 26;
		int y = action / 26;
		if(grid[x][y] >= 200){
			grid[x][y] -= 100;
		} else if(grid[x][y] >= 100){
			grid[x][y] += 100;
		}
		// }
	}
	
	private void checkGrid(){
		boolean allCleared = true;
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 5; x++){
				if (grid[x][y] % 100 > 1 && grid[x][y] >= 100) {
					allCleared = false;
					break;
				}
			}
		}
		if(allCleared){
			turnstate = 3;
			uncoverAll();
		}
	}
	
	private void uncoverAll(){
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 5; x++){
				grid[x][y] %= 100;
			}
		}
	}
	
	
	
	
	
	
	private void uncoverTiles() {
		while(FieldList.size() > 0) {
			boolean temp0 = false;
			for(Vector2 v : FieldList) {
				if(v.X >  0 && v.Y >  0) { if(grid[v.X - 1][v.Y - 1] >= 100) { grid[v.X - 1][v.Y - 1] %= 100; if(grid[v.X - 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y - 1)); temp0 = true; break; } } } } } // -X -Y
				if(            v.Y >  0) { if(grid[v.X    ][v.Y - 1] >= 100) { grid[v.X    ][v.Y - 1] %= 100; if(grid[v.X    ][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y - 1)); temp0 = true; break; } } } } } //    -Y
				if(v.X < 25 && v.Y >  0) { if(grid[v.X + 1][v.Y - 1] >= 100) { grid[v.X + 1][v.Y - 1] %= 100; if(grid[v.X + 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y - 1)); temp0 = true; break; } } } } } // +X -Y
				if(v.X < 25            ) { if(grid[v.X + 1][v.Y    ] >= 100) { grid[v.X + 1][v.Y    ] %= 100; if(grid[v.X + 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y    )); temp0 = true; break; } } } } } // +X
				if(v.X < 25 && v.Y < 13) { if(grid[v.X + 1][v.Y + 1] >= 100) { grid[v.X + 1][v.Y + 1] %= 100; if(grid[v.X + 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y + 1)); temp0 = true; break; } } } } } // +X +Y
				if(            v.Y < 13) { if(grid[v.X    ][v.Y + 1] >= 100) { grid[v.X    ][v.Y + 1] %= 100; if(grid[v.X    ][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y + 1)); temp0 = true; break; } } } } } //    +Y
				if(v.X >  0 && v.Y < 13) { if(grid[v.X - 1][v.Y + 1] >= 100) { grid[v.X - 1][v.Y + 1] %= 100; if(grid[v.X - 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y + 1)); temp0 = true; break; } } } } } // -X +Y
				if(v.X >  0            ) { if(grid[v.X - 1][v.Y    ] >= 100) { grid[v.X - 1][v.Y    ] %= 100; if(grid[v.X - 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y    )); temp0 = true; break; } } } } } // -X
				break;
			}
			if(!temp0) FieldList.remove(0);
		}
	}
	
	private void uncoverBombs() {
		for(int y = 0; y < 14; y++) {
			for(int x = 0; x < 26; x++) {
				if(grid[x][y]  % 100 == 9) {
					grid[x][y] = 9;
				}
			}
		}
	}
	
	// private void jump(int x, int y) {
	// 	if(grid[x][y] == 1) {
	// 		selector.set(x, y);
	// 		scoreLevel = 1;
	// 	} else if(grid[x][y] == 0) {
	// 		if(y + 2 == selector.Y && grid[selector.X][selector.Y - 1] == 1) { // Jump to UP
	// 			grid[x][y    ] = 1;
	// 			grid[x][y + 1] = 0;
	// 			grid[x][y + 2] = 0;
	// 			selector.set(x, y);
	// 			scorePoint += scoreLevel;
	// 			scoreLevel += 1;
	// 			setJingle(SOUND_CHIP);
	// 		} else if(y - 2 == selector.Y && grid[selector.X][selector.Y + 1] == 1) { // Jump to DOWN
	// 			grid[x][y    ] = 1;
	// 			grid[x][y - 1] = 0;
	// 			grid[x][y - 2] = 0;
	// 			selector.set(x, y);
	// 			scorePoint += scoreLevel;
	// 			scoreLevel += 1;
	// 			setJingle(SOUND_CHIP);
	// 		} else if(x + 2 == selector.X && grid[selector.X - 1][selector.Y] == 1) { // Jump to LEFT
	// 			grid[x    ][y] = 1;
	// 			grid[x + 1][y] = 0;
	// 			grid[x + 2][y] = 0;
	// 			selector.set(x, y);
	// 			scorePoint += scoreLevel;
	// 			scoreLevel += 1;
	// 			setJingle(SOUND_CHIP);
	// 		} else if(x - 2 == selector.X && grid[selector.X + 1][selector.Y] == 1) { // Jump to RIGHT
	// 			grid[x    ][y] = 1;
	// 			grid[x - 1][y] = 0;
	// 			grid[x - 2][y] = 0;
	// 			selector.set(x, y);
	// 			scorePoint += scoreLevel;
	// 			scoreLevel += 1;
	// 			setJingle(SOUND_CHIP);
	// 		}
	// 		checkForGameOver();
	// 	}
	// }
	
	private void checkForGameOver() {
		boolean canJump = false;
		int counter = 0;
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 17; x++) {
				if(grid[x][y] == 1) {
					counter++;
					if(y >=  2 && grid[x][y - 1] == 1 && grid[x][y - 2] == 0) canJump = true;
					if(y <=  6 && grid[x][y + 1] == 1 && grid[x][y + 2] == 0) canJump = true;
					if(x >=  2 && grid[x - 1][y] == 1 && grid[x - 2][y] == 0) canJump = true;
					if(x <= 14 && grid[x + 1][y] == 1 && grid[x + 2][y] == 0) canJump = true;
				}
			}
		}
		if(counter == 1) turnstate = 4;
		if(!canJump) turnstate = 4;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 14;
	}
	
	public String getName(){
		return "minesweeper";
	}
	
	
	
}