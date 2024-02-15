package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_IMPACT;
import static mod.casinocraft.util.mapping.SoundMap.SOUND_TETRIS;
import static mod.lucky77.util.KeyMap.*;

public class Logic51 extends LogicModule {   //  Tetris
	
	// GAME MODE -- Combination Method (Tetris, Columns, PuyoPuyo)
	// RULE 1 -- Small Size, Large Size
	// RULE 2 -- Hold Button (YES, NO)
	// RULE 3 -- DOMINO, TRIMINO, TETROMINO
	// COLOR VARIATION -- Background Design
	
	// --------------------------------------------------
	
	public boolean canHold = false;
	public int[] container_next = new int[]{ 0, 0, 0, 0 };
	public int[] container_hold = new int[]{ 0, 0, 0, 0 };
	public int[] container_now  = new int[]{ 0, 0, 0, 0 };
	public int timer_last = 0;
	public int timer_break = 0;
	public int timer = 0;
	public int[] color = new int[7]; // Color of single Tetromino
	public int[] lines = new int[4];
	public int alpha = 0;
	public Vector2[] tetromino = new Vector2[]{new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0)}; // Position of the moving tetromino on the grid
	public List<Vector2> clear = new ArrayList<Vector2>();
	private List<Vector2> clear_temp = new ArrayList<Vector2>();
	
	//
	// // ---
	//
	// public boolean active_hold;
	// public int[] container_next    = new int[3];
	// public int[] container_hold    = new int[3];
	// public int[] container_current = new int[3];
	// public double time_last;
	// public double time_break;
	// public int timer;
	// public Vector2[] tromino = new Vector2[]{new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0)};
	// public List<Vector2> clear = new ArrayList<Vector2>();
	// public int alpha;
	//
	// // ---
	//
	// public boolean active_hold;
	// public int[] container_next    = new int[2];
	// public int[] container_hold    = new int[2];
	// public int[] container_current = new int[2];
	// public double time_last;
	// public double time_break;
	// public int timer;
	// public Vector2[] domino = new Vector2[]{new Vector2(0, 0), new Vector2(0, 0)};
	// public List<Vector2> clear = new ArrayList<Vector2>();
	// public int alpha;
	// private List<Vector2> clear_temp = new ArrayList<Vector2>();
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic51(int tableID) {
		super(tableID, 10, 20 /* 6, 15 */ );
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		canHold = true;
		container_next = minoRoll();
		container_hold = new int[]{ -1, -1, -1, -1 };
		container_now = minoRoll();
		minoCreate();
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 20; j++) {
				grid[i][j] = -1;
			}
		}
		timer_break = 200;
		timer_last = 0;
		lines[0] = -1;
		lines[1] = -1;
		lines[2] = -1;
		lines[3] = -1;
		alpha = 250;
		timer = 0;
		colorize();
		clear.clear();
		clear_temp.clear();
		
		//
		// // ---
		//
		// active_hold = true;
		// container_next[0] = Column_Roll();
		// container_next[1] = Column_Roll();
		// container_next[2] = Column_Roll();
		// container_hold[0] = -1;
		// container_hold[1] = -1;
		// container_hold[2] = -1;
		// container_current[0] = Column_Roll();
		// container_current[1] = Column_Roll();
		// container_current[2] = Column_Roll();
		// Column_Create();
		// for(int i = 0; i < 6; i++) {
		// 	for(int j = 0; j < 15; j++) {
		// 		grid[i][j] = -1;
		// 	}
		// }
		// time_break = 200;
		// time_last = 0;
		// clear.clear();
		// alpha = 255;
		//
		// // ---
		//
		// active_hold = true;
		// container_next[0] = dominoRoll();
		// container_next[1] = dominoRoll();
		// container_hold[0] = -1;
		// container_hold[1] = -1;
		// container_current[0] = dominoRoll();
		// container_current[1] = dominoRoll();
		// dominoCreate();
		// for(int i = 0; i < 6; i++) {
		// 	for(int j = 0; j < 15; j++) {
		// 		grid[i][j] = -1;
		// 	}
		// }
		// time_break = 200;
		// time_last = 0;
		// clear.clear();
		// alpha = 255;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		if(action == KEY_UP && ruleGameMode() != 1){    commandTurn();    }
		if(action == KEY_UP && ruleGameMode() == 1){    commandCycle();    }
		if(action == KEY_DOWN){  minoDrop();      }
		if(action == KEY_LEFT){  commandStrafe(true);  }
		if(action == KEY_RIGHT){ commandStrafe(false); }
		if(action == KEY_ENTER){ commandHold();        }
		
		//
		// // ---
		//
		// if(action == KEY_UP){    commandCycle(true);   }
		// if(action == KEY_DOWN){  columnDrop();         }
		// if(action == KEY_LEFT){  commandStrafe(true);  }
		// if(action == KEY_RIGHT){ commandStrafe(false); }
		// if(action == KEY_ENTER){ commandHold();        }
		//
		// // ---
		//
		// if(action == KEY_UP){    commandTurn(true);    }
		// if(action == KEY_DOWN){  dominoDrop();         }
		// if(action == KEY_LEFT){  commandStrafe(true);  }
		// if(action == KEY_RIGHT){ commandStrafe(false); }
		// if(action == KEY_ENTER){ commandHold();        }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
		if(alpha == 255) {
			if(timer > timer_last + timer_break - scoreLevel * 5 && turnstate == 2) {
				minoFall();
				timer_last = timer;
			}
		} else {
			alpha -= 10;
			if(alpha <= 0) {
				alpha = 255;
				
				if(ruleGameMode() == 1){
					for(int y = 0; y < 20; y++) {
						for(int x = 0; x < 10; x++) {
							if(isCleared(x, y, clear)) {
								grid[x][y] = -1;
							}
						}
					}
				}
				
				if(ruleGameMode() == 2){
					for(int y = 0; y < 20; y++) {
						for(int x = 0; x < 10; x++) {
							if(isCleared(x, y, clear)) {
								grid[x][y] = -1;
							}
						}
					}
				}
				
				// clear.clear();
				commandCollapse();
				setJingle(SOUND_TETRIS);
			}
		}
		
		//
		// // ---
		//
		// timer+=15;
		// if(alpha == 255) {
		// 	if(timer > time_last + time_break - scoreLevel * 5 && turnstate == 2) {
		// 		columnFall();
		// 		time_last = timer;
		// 	}
		// } else {
		// 	alpha -= 10;
		// 	if(alpha <= 0) {
		// 		alpha = 255;

		// 		setJingle(SOUND_TETRIS);
		// 		commandCollapse();
		// 	}
		// }
		//
		// // ---
		//
		// timer+=15;
		// if(alpha == 255) {
		// 	if(timer > time_last + time_break && turnstate == 2) {
		// 		dominoFall();
		// 		time_last = timer;
		// 	}
		// } else {
		// 	alpha -= 10;
		// 	if(alpha <= 0) {
		// 		alpha = 255;

		// 		setJingle(SOUND_TETRIS);
		// 		commandCollapse();
		// 	}
		// }
	}
	
	public void updateMotion() {
		timer+=15;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		canHold        = compound.getBoolean("canhold");
		container_next[0] = compound.getInt("container_next_0");
		container_next[1] = compound.getInt("container_next_1");
		container_next[2] = compound.getInt("container_next_2");
		container_next[3] = compound.getInt("container_next_3");
		container_hold[0] = compound.getInt("container_hold_0");
		container_hold[1] = compound.getInt("container_hold_1");
		container_hold[2] = compound.getInt("container_hold_2");
		container_hold[3] = compound.getInt("container_hold_3");
		container_now[0]  = compound.getInt("container_now_0");
		container_now[1]  = compound.getInt("container_now_1");
		container_now[2]  = compound.getInt("container_now_2");
		container_now[3]  = compound.getInt("container_now_3");
		timer_last  = compound.getInt("timerlast");
		timer_break = compound.getInt("timerbreak");
		timer       = compound.getInt("timer");
		tetromino[0].set(compound.getInt("tetromino0x"), compound.getInt("tetromino0y"));
		tetromino[1].set(compound.getInt("tetromino1x"), compound.getInt("tetromino1y"));
		tetromino[2].set(compound.getInt("tetromino2x"), compound.getInt("tetromino2y"));
		tetromino[3].set(compound.getInt("tetromino3x"), compound.getInt("tetromino3y"));
		color[0] = compound.getInt("color0");
		color[1] = compound.getInt("color1");
		color[2] = compound.getInt("color2");
		color[3] = compound.getInt("color3");
		color[4] = compound.getInt("color4");
		color[5] = compound.getInt("color5");
		color[6] = compound.getInt("color6");
		lines[0] = compound.getInt("lines0");
		lines[1] = compound.getInt("lines1");
		lines[2] = compound.getInt("lines2");
		lines[3] = compound.getInt("lines3");
		alpha = compound.getInt("alpha");
		
		//
		// // ---
		//
		// active_hold = compound.getBoolean("activehold");
		// container_next[0] = compound.getInt("container_next0");
		// container_next[1] = compound.getInt("container_next1");
		// container_next[2] = compound.getInt("container_next2");
		//
		// container_hold[0] = compound.getInt("container_hold0");
		// container_hold[1] = compound.getInt("container_hold1");
		// container_hold[2] = compound.getInt("container_hold2");
		//
		// container_current[0] = compound.getInt("container_current0");
		// container_current[1] = compound.getInt("container_current1");
		// container_current[2] = compound.getInt("container_current2");
		//
		// time_last  = compound.getDouble("timelast");
		// time_break = compound.getDouble("timebreak");
		// timer      = compound.getInt("timer");
		//
		// tromino[0].set(compound.getInt("tromino0x"), compound.getInt("tromino0y"));
		// tromino[1].set(compound.getInt("tromino1x"), compound.getInt("tromino1y"));
		// tromino[2].set(compound.getInt("tromino2x"), compound.getInt("tromino2y"));
		//
		// alpha = compound.getInt("alpha");
		//
		// // ---
		//
		// active_hold       = compound.getBoolean("activehold");
		// container_next[0] = compound.getInt("container_next0");
		// container_next[1] = compound.getInt("container_next1");
		//
		// container_hold[0] = compound.getInt("container_hold0");
		// container_hold[1] = compound.getInt("container_hold1");
		//
		// container_current[0] = compound.getInt("container_current0");
		// container_current[1] = compound.getInt("container_current1");
		//
		// time_last  = compound.getDouble("timelast");
		// time_break = compound.getDouble("timebreak");
		// timer      = compound.getInt("timer");
		//
		// domino[0].set(compound.getInt("domino0x"), compound.getInt("domino0y"));
		// domino[1].set(compound.getInt("domino1x"), compound.getInt("domino1y"));
		//
		// alpha = compound.getInt("alpha");
	}
	
	public CompoundTag save2(CompoundTag compound){
		compound.putBoolean("canhold",    canHold);
		compound.putInt("container_next_0", container_next[0]);
		compound.putInt("container_next_1", container_next[1]);
		compound.putInt("container_next_2", container_next[2]);
		compound.putInt("container_next_3", container_next[3]);
		compound.putInt("container_last_0", container_hold[0]);
		compound.putInt("container_last_1", container_hold[1]);
		compound.putInt("container_last_2", container_hold[2]);
		compound.putInt("container_last_3", container_hold[3]);
		compound.putInt("container_now_0",  container_now[0]);
		compound.putInt("container_now_1",  container_now[1]);
		compound.putInt("container_now_2",  container_now[2]);
		compound.putInt("container_now_3",  container_now[3]);
		compound.putInt("timerlast",      timer_last);
		compound.putInt("timerbreak",     timer_break);
		compound.putInt("timer",          timer);
		compound.putInt("tetromino0x", tetromino[0].X);
		compound.putInt("tetromino0y", tetromino[0].Y);
		compound.putInt("tetromino1x", tetromino[1].X);
		compound.putInt("tetromino1y", tetromino[1].Y);
		compound.putInt("tetromino2x", tetromino[2].X);
		compound.putInt("tetromino2y", tetromino[2].Y);
		compound.putInt("tetromino3x", tetromino[3].X);
		compound.putInt("tetromino3y", tetromino[3].Y);
		compound.putInt("alpha",  alpha);
		compound.putInt("color0", color[0]);
		compound.putInt("color1", color[1]);
		compound.putInt("color2", color[2]);
		compound.putInt("color3", color[3]);
		compound.putInt("color4", color[4]);
		compound.putInt("color5", color[5]);
		compound.putInt("color6", color[6]);
		compound.putInt("lines0", lines[0]);
		compound.putInt("lines1", lines[1]);
		compound.putInt("lines2", lines[2]);
		compound.putInt("lines3", lines[3]);
		return compound;
		
		//
		// // ---
		//
		// compound.putBoolean("activehold",  active_hold);
		// compound.putInt("container_next0", container_next[0]);
		// compound.putInt("container_next1", container_next[1]);
		// compound.putInt("container_next2", container_next[2]);
		//
		// compound.putInt("container_hold0", container_hold[0]);
		// compound.putInt("container_hold1", container_hold[1]);
		// compound.putInt("container_hold2", container_hold[2]);
		//
		// compound.putInt("container_current0", container_current[0]);
		// compound.putInt("container_current1", container_current[1]);
		// compound.putInt("container_current2", container_current[2]);
		//
		// compound.putDouble("timelast",  time_last);
		// compound.putDouble("timebreak", time_break);
		// compound.putInt("timer",        timer);
		// compound.putInt("tromino0x", tromino[0].X);
		// compound.putInt("tromino0y", tromino[0].Y);
		// compound.putInt("tromino1x", tromino[1].X);
		// compound.putInt("tromino1y", tromino[1].Y);
		// compound.putInt("tromino2x", tromino[2].X);
		// compound.putInt("tromino2y", tromino[2].Y);
		// compound.putInt("alpha",     alpha);
		// return compound;
		//
		// // ---
		//
		// compound.putBoolean("activehold",  active_hold);
		// compound.putInt("container_next0", container_next[0]);
		// compound.putInt("container_next1", container_next[1]);
		//
		// compound.putInt("container_hold0", container_hold[0]);
		// compound.putInt("container_hold1", container_hold[1]);
		//
		// compound.putInt("container_current0", container_current[0]);
		// compound.putInt("container_current1", container_current[1]);
		//
		// compound.putDouble("timelast",  time_last);
		// compound.putDouble("timebreak", time_break);
		// compound.putInt("timer",    timer);
		// compound.putInt("domino0x", domino[0].X);
		// compound.putInt("domino0y", domino[0].Y);
		// compound.putInt("domino1x", domino[1].X);
		// compound.putInt("domino1y", domino[1].Y);
		// compound.putInt("alpha",    alpha);
		// return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	public boolean inLine(int x, int y){
		if(ruleSet[0] == 0){
			if(y     == lines[0]) return true;
			if(y     == lines[1]) return true;
			if(y     == lines[2]) return true;
			return y == lines[3];
		} else {
			for(Vector2 v : clear) {
				if(v.matches(x, y)) return true;
			}
			return false;
		}
		
		// --- Columns / PuyoPuyo --- //
		
	}
	
	public int ruleGameMode(){
		return ruleSet[0];
	}
	
	public boolean ruleLargeField(){
		return ruleSet[1] == 0;
	}
	
	public boolean ruleHoldButton(){
		return ruleSet[3] == 0;
	}
	
	public int rulePieces(){
		return ruleSet[2] + 2;
	}
	
	private void colorize() {
		boolean[] used = new boolean[10];
		int index = 0;
		for(int i = 0; i < 10; i++) {
			used[i] = false;
			index++;
		}
		color[0] = -1;
		color[1] = -1;
		color[2] = -1;
		color[3] = -1;
		color[4] = -1;
		color[5] = -1;
		color[6] = -1;
		int count = 0;
		while(count < index && count < 7) {
			int r = RANDOM.nextInt(7)+1;
			if(!used[r]) {
				color[count] = r;
				used[r] = true;
				count++;
			}
		}
	}
	
	private void commandCollapse() {
		if(ruleGameMode() == 0){
			for(int z = 0; z < 4; z++){
				if(lines[z] != -1) {
					for(int i = lines[z]; i > 0; i--) {
						for(int x = 0; x < 10; x++){
							grid[x][i] = grid[x][i-1];
						}
					}
				}
				lines[z] = -1;
			}
			alpha = 255;
		}
		
		if(ruleGameMode() == 1){
			// Gravity after match found and cleared
			int temp = 0;
			boolean falling = true;
			for(int x = 0; x < 10; x++){
				for(int y = 19; y >= 1; y--){
					if(grid[x][y] == -1){
						for(int y2 = y-1; y2 >= 0; y2--){
							if(grid[x][y2] != -1){
								grid[x][y] = grid[x][y2];
								grid[x][y2] = -1;
								break;
							}
						}
					}
				}
			}
			clear.clear();
			alpha = 255;
			checkField();
		}
		
		if(ruleGameMode() == 2){
			// Gravity after match found and cleared
			int temp = 0;
			for(int y = 18; y >= 0; y--) {
				for(int x = 0; x < 10; x++) {
					if(grid[x][y] != -1) {
						temp = 0;
						while(y + temp + 1 < 20 && grid[x][y + temp + 1] == -1) {
							temp++;
						}
						if(temp != 0) {
							grid[x][y + temp] = grid[x][y];
							grid[x][y] = -1;
						}
					}
				}
			}
			clear.clear();
			alpha = 255;
			checkField();
		}
		
		// private void commandCollapse() {

		// }
		
		// private void commandCollapse() {

		// }
	}
	
	private void commandStrafe(boolean totheleft) {
		int dir = 0;
		int rightSide = ruleLargeField() ? 9 : 5;
		
		if(rulePieces() == 4){
			
			if(totheleft) {
				dir = -1;
				if(tetromino[0].X > 0) {
					if(tetromino[1].X > 0) {
						if(tetromino[2].X > 0) {
							if(tetromino[3].X > 0) {
								if(grid[tetromino[0].X + dir][tetromino[0].Y] == -1) {
									if(grid[tetromino[1].X + dir][tetromino[1].Y] == -1) {
										if(grid[tetromino[2].X + dir][tetromino[2].Y] == -1) {
											if(grid[tetromino[3].X + dir][tetromino[3].Y] == -1) {
												tetromino[0].add(dir, 0);
												tetromino[1].add(dir, 0);
												tetromino[2].add(dir, 0);
												tetromino[3].add(dir, 0);
											}
										}
									}
								}
							}
						}
					}
				}
			} else {
				dir = 1;
				if(tetromino[0].X < rightSide) {
					if(tetromino[1].X < rightSide) {
						if(tetromino[2].X < rightSide) {
							if(tetromino[3].X < rightSide) {
								if(grid[tetromino[0].X + dir][tetromino[0].Y] == -1) {
									if(grid[tetromino[1].X + dir][tetromino[1].Y] == -1) {
										if(grid[tetromino[2].X + dir][tetromino[2].Y] == -1) {
											if(grid[tetromino[3].X + dir][tetromino[3].Y] == -1) {
												tetromino[0].add(dir, 0);
												tetromino[1].add(dir, 0);
												tetromino[2].add(dir, 0);
												tetromino[3].add(dir, 0);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(rulePieces() == 3){
			if(totheleft) {
				dir = -1;
				if(tetromino[0].X > 0) {
					if(tetromino[1].X > 0) {
						if(tetromino[2].X > 0) {
							if(grid[tetromino[0].X + dir][tetromino[0].Y] == -1) {
								if(grid[tetromino[1].X + dir][tetromino[1].Y] == -1) {
									if(grid[tetromino[2].X + dir][tetromino[2].Y] == -1) {
										tetromino[0].X = tetromino[0].X + dir;
										tetromino[1].X = tetromino[1].X + dir;
										tetromino[2].X = tetromino[2].X + dir;
									}
								}
							}
						}
					}
				}
			} else {
				dir = 1;
				if(tetromino[0].X < rightSide) {
					if(tetromino[1].X < rightSide) {
						if(tetromino[2].X < rightSide) {
							if(grid[tetromino[0].X + dir][tetromino[0].Y] == -1) {
								if(grid[tetromino[1].X + dir][tetromino[1].Y] == -1) {
									if(grid[tetromino[2].X + dir][tetromino[2].Y] == -1) {
										tetromino[0].X = tetromino[0].X + dir;
										tetromino[1].X = tetromino[1].X + dir;
										tetromino[2].X = tetromino[2].X + dir;
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(rulePieces() == 2){
			if(totheleft) {
				dir = -1;
				if(tetromino[0].X > 0) {
					if(tetromino[1].X > 0) {
						if(grid[tetromino[0].X + dir][tetromino[0].Y] == -1) {
							if(grid[tetromino[1].X + dir][tetromino[1].Y] == -1) {
								tetromino[0].X = tetromino[0].X + dir;
								tetromino[1].X = tetromino[1].X + dir;
							}
						}
					}
				}
			} else {
				dir = 1;
				if(tetromino[0].X < rightSide) {
					if(tetromino[1].X < rightSide) {
						if(grid[tetromino[0].X + dir][tetromino[0].Y] == -1) {
							if(grid[tetromino[1].X + dir][tetromino[1].Y] == -1) {
								tetromino[0].X = tetromino[0].X + dir;
								tetromino[1].X = tetromino[1].X + dir;
							}
						}
					}
				}
			}
		}
		
		// private void commandStrafe(boolean totheleft) {
		// 	int dir = 0;

		// }
		
		// private void commandStrafe(boolean totheleft) {
		// 	int dir = 0;

		// }
	}
	
	private void commandTurn(/*boolean totheleft*/) {
		
		if(rulePieces() == 4){
			Vector2 tempV1 = new Vector2(tetromino[0].X - tetromino[1].X, tetromino[0].Y - tetromino[1].Y);
			Vector2 tempV2 = new Vector2(tetromino[0].X - tetromino[2].X, tetromino[0].Y - tetromino[2].Y);
			Vector2 tempV3 = new Vector2(tetromino[0].X - tetromino[3].X, tetromino[0].Y - tetromino[3].Y);
			// if(totheleft) {
			if(tempV1.X == 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y           ); } else
			if(tempV1.X == 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y           ); } else
			if(tempV1.X  < 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV1.X); } else
			if(tempV1.X  > 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV1.X); } else
			if(tempV1.X  < 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
			if(tempV1.X  < 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
			if(tempV1.X  > 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
			if(tempV1.X  > 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); }
			if(tempV2.X == 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y           ); } else
			if(tempV2.X == 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y           ); } else
			if(tempV2.X  < 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV2.X); } else
			if(tempV2.X  > 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV2.X); } else
			if(tempV2.X  < 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
			if(tempV2.X  < 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
			if(tempV2.X  > 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
			if(tempV2.X  > 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); }
			if(tempV3.X == 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y           ); } else
			if(tempV3.X == 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y           ); } else
			if(tempV3.X  < 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV3.X); } else
			if(tempV3.X  > 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV3.X); } else
			if(tempV3.X  < 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
			if(tempV3.X  < 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
			if(tempV3.X  > 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
			if(tempV3.X  > 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); }
			// } else {
			// 	if(tempV1.X == 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y           ); } else
			// 	if(tempV1.X == 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y           ); } else
			// 	if(tempV1.X  < 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV1.X); } else
			// 	if(tempV1.X  > 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV1.X); } else
			// 	if(tempV1.X  < 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
			// 	if(tempV1.X  < 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
			// 	if(tempV1.X  > 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
			// 	if(tempV1.X  > 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); }
			// 		if(tempV2.X == 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y           ); } else
			// 	if(tempV2.X == 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y           ); } else
			// 	if(tempV2.X  < 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV2.X); } else
			// 	if(tempV2.X  > 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV2.X); } else
			// 	if(tempV2.X  < 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
			// 	if(tempV2.X  < 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
			// 	if(tempV2.X  > 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
			// 	if(tempV2.X  > 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); }
			// 		if(tempV3.X == 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y           ); } else
			// 	if(tempV3.X == 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y           ); } else
			// 	if(tempV3.X  < 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV3.X); } else
			// 	if(tempV3.X  > 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV3.X); } else
			// 	if(tempV3.X  < 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
			// 	if(tempV3.X  < 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
			// 	if(tempV3.X  > 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
			// 	if(tempV3.X  > 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); }
			// }
			
			int fieldX = ruleLargeField() ? 10 :  6;
			int fieldY = ruleLargeField() ? 20 : 13;
			
			if(tempV1.X > -1 && tempV1.X < fieldX && tempV1.Y > -1 && tempV1.Y < fieldY) {
				if(tempV2.X > -1 && tempV2.X < fieldX && tempV2.Y > -1 && tempV2.Y < fieldY) {
					if(tempV3.X > -1 && tempV3.X < fieldX && tempV3.Y > -1 && tempV3.Y < fieldY) {
						if(grid[tempV1.X][tempV1.Y] == -1) {
							if(grid[tempV2.X][tempV2.Y] == -1) {
								if(grid[tempV3.X][tempV3.Y] == -1) {
									tetromino[1] = tempV1;
									tetromino[2] = tempV2;
									tetromino[3] = tempV3;
								}
							}
						}
					}
				}
			}
		}
		
		if(rulePieces() == 3){
		
		}
		
		if(rulePieces() == 2){
			int pos = 0; // Position of the rotatable Mino
			if(tetromino[0].Y > tetromino[1].Y) pos = 1; // Up
			if(tetromino[0].X > tetromino[1].X) pos = 2; // Left
			if(tetromino[0].Y < tetromino[1].Y) pos = 3; // Down
			if(tetromino[0].X < tetromino[1].X) pos = 4; // Right
			// 			if(totheleft) {
			if(pos == 1) { if(tetromino[0].X - 1 >=  0 && grid[tetromino[0].X - 1][tetromino[0].Y] == -1) { tetromino[1] = new Vector2(tetromino[0].X - 1, tetromino[0].Y    ); } }
			if(pos == 2) { if(tetromino[0].Y + 1 <= 14 && grid[tetromino[0].X][tetromino[0].Y + 1] == -1) { tetromino[1] = new Vector2(tetromino[0].X    , tetromino[0].Y + 1); } }
			if(pos == 3) { if(tetromino[0].X + 1 <=  5 && grid[tetromino[0].X + 1][tetromino[0].Y] == -1) { tetromino[1] = new Vector2(tetromino[0].X + 1, tetromino[0].Y    ); } }
			if(pos == 4) { if(tetromino[0].Y - 1 >=  0 && grid[tetromino[0].X][tetromino[0].Y - 1] == -1) { tetromino[1] = new Vector2(tetromino[0].X    , tetromino[0].Y - 1); } }
			// } else {
			// 	if(pos == 1) { if(domino[0].X + 1 <=  5 && grid[domino[0].X + 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X + 1, domino[0].Y    ); } }
			// 	if(pos == 2) { if(domino[0].Y - 1 >=  0 && grid[domino[0].X][domino[0].Y - 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y - 1); } }
			// 	if(pos == 3) { if(domino[0].X - 1 >=  0 && grid[domino[0].X - 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X - 1, domino[0].Y    ); } }
			// 	if(pos == 4) { if(domino[0].Y + 1 <= 14 && grid[domino[0].X][domino[0].Y + 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y + 1); } }
			// }
		}
		
		// private void commandTurn(boolean totheleft) {

		// 	}
	}
	
	private void commandCycle(){
		
		if(rulePieces() == 4){
			int temp = container_now[0];
			container_now[0] = container_now[1];
			container_now[1] = container_now[2];
			container_now[2] = container_now[3];
			container_now[3] = temp;
		}
		
		if(rulePieces() == 3){
			int temp = container_now[0];
			container_now[0] = container_now[1];
			container_now[1] = container_now[2];
			container_now[2] = temp;
		}
		
		if(rulePieces() == 2){
			int temp = container_now[0];
			container_now[0] = container_now[1];
			container_now[1] = temp;
		}
		
		// 	private void commandCycle(boolean totheleft) {
		// if(totheleft) {
		
		// } else {
		// 	int temp = container_current[2];
		// 	container_current[2] = container_current[1];
		// 	container_current[1] = container_current[0];
		// 	container_current[0] = temp;
		// }
		// }
	}
	
	private void commandHold() {
		if(ruleHoldButton() && canHold) {
			int[] roll = minoRoll();
			if(container_hold[0] == -1){
				for(int i = 0; i < 4; i++){
					container_hold[i] = container_now[i];
					container_now[i] = container_next[i];
					container_next[i] = roll[i];
				}
			} else {
				for(int i = 0; i < 4; i++){
					int temp = container_hold[i];
					container_hold[i] = container_now[i];
					container_now[i] = temp;
				}
			}
			minoCreate();
			
			// if(rulePieces() == 4){
			// 	canHold = false;
			// 	if(container_hold[0] == -1) {
			// 		container_hold = container_now;
			// 		container_now = container_next;
			// 		container_next = minoRoll();
			// 	} else {
			// 		int temp;
			// 		temp = container_hold[0];
			// 		container_hold = container_now;
			// 		container_now[0] = temp;
			// 	}
			// 	minoCreate();
			// }
			//
			// if(rulePieces() == 3){
			// 	canHold = false;
			// 	if(container_hold[0] == -1) {
			// 		container_hold[0] = container_now[0];
			// 		container_hold[1] = container_now[1];
			// 		container_hold[2] = container_now[2];
			// 		container_now[0] = container_next[0];
			// 		container_now[1] = container_next[1];
			// 		container_now[2] = container_next[2];
			// 		container_next = minoRoll();
			// 		// container_next[0] = Column_Roll();
			// 		// container_next[1] = Column_Roll();
			// 		// container_next[2] = Column_Roll();
			// 	} else {
			// 		int[] temp = new int[3];
			// 		temp[0] = container_hold[0];
			// 		temp[1] = container_hold[1];
			// 		temp[2] = container_hold[2];
			// 		container_hold[0] = container_now[0];
			// 		container_hold[1] = container_now[1];
			// 		container_hold[2] = container_now[2];
			// 		container_now[0] = temp[0];
			// 		container_now[1] = temp[1];
			// 		container_now[2] = temp[2];
			// 	}
			// 	minoCreate();
			// }
			//
			// if(rulePieces() == 2){
			// 	canHold = false;
			// 	if(container_hold[0] == -1) {
			// 		container_hold[0] = container_now[0];
			// 		container_hold[1] = container_now[1];
			// 		container_now[0] = container_next[0];
			// 		container_now[1] = container_next[1];
			// 		container_next = minoRoll();
			// 		// container_next[0] = minoRoll();
			// 		// container_next[1] = minoRoll();
			// 	} else {
			// 		int[] temp = new int[2];
			// 		temp[0] = container_hold[0];
			// 		temp[1] = container_hold[1];
			// 		container_hold[0] = container_now[0];
			// 		container_hold[1] = container_now[1];
			// 		container_now[0] = temp[0];
			// 		container_now[1] = temp[1];
			// 	}
			// 	minoCreate();
			// }
		}
		
		// 	private void commandHold() {
		// 	if(active_hold) {

		// 	}
		// }
		
		// 	private void commandHold() {
		// 	if(active_hold) {

		// 	}
		// }
	}
	
	private int[] minoRoll() {
		boolean ignoreColoring = ruleGameMode() == 0;
		
		if(ignoreColoring){
			int r = RANDOM.nextInt(7);
			int[] roll = new int[]{ -1, -1, -1, -1 };
			for(int i = 0; i < rulePieces(); i++){
				roll[i] = r;
			}
			return roll;
		} else {
			int[] roll = new int[]{ -1, -1, -1, -1 };
			for(int i = 0; i < rulePieces(); i++){
				roll[i] = RANDOM.nextInt(6);
			}
			return roll;
		}
		
		
		
		//return RANDOM.nextInt(7);
		
		// private int Column_Roll() {
		// 	int r = RANDOM.nextInt(6)+1;
		// 	return r >= 3 ? r+1 : r;
		// }
		
		// private int dominoRoll() {
		// 	int r = RANDOM.nextInt(6)+1;
		// 	return r >= 3 ? r+1 : r;
		// }
	}
	
	private void minoCreate() {
		int fieldX = ruleLargeField() ? 0 : -2;
		int fieldY = ruleLargeField() ? 0 :  5;
		if(rulePieces() == 4){
			if(container_now[0] == 0) { // I
				tetromino[0].set(fieldX + 4, fieldY + 1); // OOXO
				tetromino[1].set(fieldX + 4, fieldY + 0); // OOXO
				tetromino[2].set(fieldX + 4, fieldY + 2); // OOXO
				tetromino[3].set(fieldX + 4, fieldY + 3); // OOXO
			}
			if(container_now[0] == 1) { // O
				tetromino[0].set(fieldX + 4, fieldY + 0); // OOOO
				tetromino[1].set(fieldX + 4, fieldY + 1); // OXXO
				tetromino[2].set(fieldX + 5, fieldY + 0); // OXXO
				tetromino[3].set(fieldX + 5, fieldY + 1); // OOOO
			}
			if(container_now[0] == 2) { // S
				tetromino[0].set(fieldX + 5, fieldY + 0); // OOOO
				tetromino[1].set(fieldX + 6, fieldY + 0); // OOXX
				tetromino[2].set(fieldX + 4, fieldY + 1); // OXXO
				tetromino[3].set(fieldX + 5, fieldY + 1); // OOOO
			}
			if(container_now[0] == 3) { // Z
				tetromino[0].set(fieldX + 5, fieldY + 0); // OOOO
				tetromino[1].set(fieldX + 4, fieldY + 0); // XXOO
				tetromino[2].set(fieldX + 5, fieldY + 1); // OXXO
				tetromino[3].set(fieldX + 6, fieldY + 1); // OOOO
			}
			if(container_now[0] == 4) { // L
				tetromino[0].set(fieldX + 4, fieldY + 2); // OXOO
				tetromino[1].set(fieldX + 4, fieldY + 0); // OXOO
				tetromino[2].set(fieldX + 4, fieldY + 1); // OXXO
				tetromino[3].set(fieldX + 5, fieldY + 2); // OOOO
			}
			if(container_now[0] == 5) { // J
				tetromino[0].set(fieldX + 5, fieldY + 2); // OOXO
				tetromino[1].set(fieldX + 5, fieldY + 0); // OOXO
				tetromino[2].set(fieldX + 5, fieldY + 1); // OXXO
				tetromino[3].set(fieldX + 4, fieldY + 2); // OOOO
			}
			if(container_now[0] == 6) { // T
				tetromino[0].set(fieldX + 5, fieldY + 0); // OOOO
				tetromino[1].set(fieldX + 4, fieldY + 0); // OXXX
				tetromino[2].set(fieldX + 6, fieldY + 0); // OOXO
				tetromino[3].set(fieldX + 5, fieldY + 1); // OOOO
			}
			setJingle(SOUND_IMPACT);
		}
		
		if(rulePieces() == 3){
			tetromino[0] = new Vector2(fieldX + 4, fieldY + 0);
			tetromino[1] = new Vector2(fieldX + 4, fieldY + 1);
			tetromino[2] = new Vector2(fieldX + 4, fieldY + 2);
			setJingle(SOUND_IMPACT);
		}
		
		if(rulePieces() == 2){
			tetromino[0] = new Vector2(fieldX + 4, fieldY + 1);
			tetromino[1] = new Vector2(fieldX + 4, fieldY + 0);
			setJingle(SOUND_IMPACT);
		}
		
		// private void Column_Create() {

		// }
		
		// private void dominoCreate() {

		// }
	}
	
	private void minoDrop() {
		int tempPoints = scorePoint;
		while(scorePoint == tempPoints) {
			minoFall();
		}
		
		// private void columnDrop() {
		// 	int tempPoint = scorePoint;
		// 	while(scorePoint == tempPoint) {
		// 		columnFall();
		// 	}
		// }
		
		// private void dominoDrop() {
		// 	int tempPoint = scorePoint;
		// 	while(scorePoint == tempPoint) {
		// 		dominoFall();
		// 	}
		// }
	}
	
	private void minoFall() {
		if(rulePieces() == 4){
			if(tetromino[0].Y < 19 && tetromino[1].Y < 19 && tetromino[2].Y < 19 && tetromino[3].Y < 19) {
				if(grid[tetromino[0].X][tetromino[0].Y + 1] == -1 && grid[tetromino[1].X][tetromino[1].Y + 1] == -1 && grid[tetromino[2].X][tetromino[2].Y + 1] == -1 && grid[tetromino[3].X][tetromino[3].Y + 1] == -1) {
					tetromino[0].add(0, 1);
					tetromino[1].add(0, 1);
					tetromino[2].add(0, 1);
					tetromino[3].add(0, 1);
				} else {
					minoPlace();
				}
			} else {
				minoPlace();
			}
		}
		
		if(rulePieces() == 3){
			if(tetromino[0].Y < 19 && tetromino[1].Y < 19 && tetromino[2].Y < 19) {
				if(grid[tetromino[0].X][tetromino[0].Y + 1] == -1 && grid[tetromino[1].X][tetromino[1].Y + 1] == -1 && grid[tetromino[2].X][tetromino[2].Y + 1] == -1) {
					tetromino[0].Y = tetromino[0].Y + 1;
					tetromino[1].Y = tetromino[1].Y + 1;
					tetromino[2].Y = tetromino[2].Y + 1;
				} else {
					minoPlace();
				}
			} else {
				minoPlace();
			}
		}
		
		if(rulePieces() == 2){
			if(tetromino[0].Y < 19 && tetromino[1].Y < 19) {
				if(grid[tetromino[0].X][tetromino[0].Y + 1] == -1 && grid[tetromino[1].X][tetromino[1].Y + 1] == -1) {
					tetromino[0].Y = tetromino[0].Y + 1;
					tetromino[1].Y = tetromino[1].Y + 1;
				} else {
					minoPlace();
				}
			} else {
				minoPlace();
			}
		}
		
		// private void columnFall() {

		// }
		
		// private void dominoFall() {

		// }
	}
	
	private void minoPlace() {
		canHold = true;
		for(int i = 0; i < tetromino.length; i++){
			grid[tetromino[i].X][tetromino[i].Y] = container_now[i];
		}
		scorePoint += 2;
		if(tetromino[0].Y <= (ruleLargeField() ? 0 : 6) ) turnstate = 4;
		container_now = container_next;
		container_next = minoRoll();
		minoCreate();
		
		if(ruleGameMode() == 0){
			lines[0] = -1;
			lines[1] = -1;
			lines[2] = -1;
			lines[3] = -1;
			for(int i = 19; i > -1; i--) {
				if(ruleLargeField()){
					if(grid[0][i] != -1 && grid[1][i] != -1 && grid[2][i] != -1 && grid[3][i] != -1 && grid[4][i] != -1 && grid[5][i] != -1 && grid[6][i] != -1 && grid[7][i] != -1 && grid[8][i] != -1 && grid[9][i] != -1) {
						scoreLives++;
						scorePoint += 50;
						if(lines[3] == -1) { lines[3] = i; }
						else if(lines[2] == -1) { lines[2] = i; }
						else if(lines[1] == -1) { lines[1] = i; }
						else if(lines[0] == -1) { lines[0] = i; }
						alpha -= 5;
					}
				} else {
					if(grid[0][i] != -1 && grid[1][i] != -1 && grid[2][i] != -1 && grid[3][i] != -1 && grid[4][i] != -1 && grid[5][i] != -1) {
						scoreLives++;
						scorePoint += 50;
						if(lines[3] == -1) { lines[3] = i; }
						else if(lines[2] == -1) { lines[2] = i; }
						else if(lines[1] == -1) { lines[1] = i; }
						else if(lines[0] == -1) { lines[0] = i; }
						alpha -= 5;
					}
				}
			}
			if(scoreLives + 1 > (1 + scoreLevel) * 10) {
				scoreLevel++;
				timer_break -= timer_break / 10;
			}
			if(lines[0] != -1 && lines[1] != -1 && lines[2] != -1 && lines[3] != -1) {
				scorePoint += 250 * (scoreLevel + 1);
			}
		}
		
		if(ruleGameMode() == 1){
			checkField();
		}
		
		if(ruleGameMode() == 2){
			commandCollapse();
			checkField();
		}
		
		if(ruleSet[0] == 0){
			// canHold = true;
			// grid[tetromino[0].X][tetromino[0].Y] = container_now[0];
			// grid[tetromino[1].X][tetromino[1].Y] = container_now[0];
			// grid[tetromino[2].X][tetromino[2].Y] = container_now[0];
			// grid[tetromino[3].X][tetromino[3].Y] = container_now[0];
			// scorePoint += 2;
			// if(tetromino[0].Y == 0) turnstate = 4;
			// container_now = container_next;
			// int container_temp = container_next[0];
			// container_next = minoRoll();
			// if(container_next[0] == container_temp) {
			// 	if(RANDOM.nextInt(2) == 0) {
			// 		container_next = minoRoll();
			// 	}
			// }
			// minoCreate();
			// lines[0] = -1;
			// lines[1] = -1;
			// lines[2] = -1;
			// lines[3] = -1;
			// for(int i = 19; i > -1; i--) {
			// 	if(grid[0][i] != -1 && grid[1][i] != -1 && grid[2][i] != -1 && grid[3][i] != -1 && grid[4][i] != -1 && grid[5][i] != -1 && grid[6][i] != -1 && grid[7][i] != -1 && grid[8][i] != -1 && grid[9][i] != -1) {
			// 		scoreLives++;
			// 		scorePoint += 50;
			// 		if(lines[3] == -1) { lines[3] = i; }
			// 		else if(lines[2] == -1) { lines[2] = i; }
			// 		else if(lines[1] == -1) { lines[1] = i; }
			// 		else if(lines[0] == -1) { lines[0] = i; }
			// 		alpha -= 5;
			// 	}
			// }
			// if(scoreLives + 1 > (1 + scoreLevel) * 10) {
			// 	scoreLevel++;
			// 	timer_break -= timer_break / 10;
			// }
			// if(lines[0] != -1 && lines[1] != -1 && lines[2] != -1 && lines[3] != -1) {
			// 	scorePoint += 250 * (scoreLevel + 1);
			// }
		}
		
		if(ruleSet[0] == 1){
			// canHold = true;
			// grid[tetromino[0].X][tetromino[0].Y] = container_now[0];
			// grid[tetromino[1].X][tetromino[1].Y] = container_now[1];
			// grid[tetromino[2].X][tetromino[2].Y] = container_now[2];
			// scorePoint = scorePoint + 2 * 2;
			// if(tetromino[0].Y == 0) turnstate = 4;
			// container_now[0] = container_next[0];
			// container_now[1] = container_next[1];
			// container_now[2] = container_next[2];
			// container_next = minoRoll();
			// // container_next[0] = Column_Roll();
			// // container_next[1] = Column_Roll();
			// // container_next[2] = Column_Roll();
			// minoCreate();
			// checkField();
		}
		
		if(ruleSet[0] == 2){
			// canHold = true;
			// grid[tetromino[0].X][tetromino[0].Y] = container_now[0];
			// grid[tetromino[1].X][tetromino[1].Y] = container_now[1];
			// scorePoint += 2 * 2;
			// if(tetromino[1].Y == 0) turnstate = 4;
			// container_now[0] = container_next[0];
			// container_now[1] = container_next[1];
			// container_next = minoRoll();
			// // container_next[0] = dominoRoll();
			// // container_next[1] = dominoRoll();
			// commandCollapse();
			// minoCreate();
			// checkField();
		}
		
		// private void columnPlace() {

		// }
		
		// private void dominoPlace() {

		// }
	}
	
	private boolean isCleared(int x, int y, List<Vector2> clearing){
		for (Vector2 v2 : clearing) {
			if (v2.X == x && v2.Y == y) return true;
		} return false;
	}
	
	// private boolean isCleared(int x, int y) {
	// 	for (Vector2 vector2 : clear) {
	// 		if (vector2.X == x && vector2.Y == y) return true;
	// 	} return false;
	// }
	// private boolean isCleared(int x, int y) {
	// 	for (Vector2 vector2 : clear) {
	// 		if (vector2.X == x && vector2.Y == y) return true;
	// 	} return false;
	// }
	// private boolean isClearedTemp(int x, int y) {
	// 	for (Vector2 vector2 : clear_temp) {
	// 		if (vector2.X == x && vector2.Y == y) return true;
	// 	}
	// 	return false;
	// }
	
	private void checkField(){
		if(ruleGameMode() == 1) {
			int points = 0;
			int bonus = 0;
			for(int y = 19; y >= 0; y--) {
				for(int x = 0; x < 10; x++) {
					if(x < 8 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y] && grid[x][y] == grid[x + 2][y]) {
						clear.add(new Vector2(x, y));
						clear.add(new Vector2(x + 1, y));
						clear.add(new Vector2(x + 2, y));
						points += (30 + bonus);
						bonus += 10;
					}
					if(y > 1 && grid[x][y] != -1 && grid[x][y] == grid[x][y - 1] && grid[x][y] == grid[x][y - 2]) {
						clear.add(new Vector2(x, y));
						clear.add(new Vector2(x, y - 1));
						clear.add(new Vector2(x, y - 2));
						points += (30 + bonus);
						bonus += 10;
					}
					if(x < 8 && y > 1 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y - 1] && grid[x][y] == grid[x + 2][y - 2]) {
						clear.add(new Vector2(x, y));
						clear.add(new Vector2(x + 1, y - 1));
						clear.add(new Vector2(x + 2, y - 2));
						points += (30 + bonus);
						bonus += 10;
					}
					if(x < 8 && y < 17 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y + 1] && grid[x][y] == grid[x + 2][y + 2]) {
						clear.add(new Vector2(x, y));
						clear.add(new Vector2(x + 1, y + 1));
						clear.add(new Vector2(x + 2, y + 2));
						points += (30 + bonus);
						bonus += 10;
					}
				}
			}
			
			if(points > 0) {
				alpha -= 5;
				scorePoint += (points * 2 * (scoreLevel + 1));
				scoreLives++;
				if(scoreLives > (1 + scoreLevel) * 10) {
					scoreLevel++;
					timer_break -= (timer_break / 10);
				}
			}
		}
		
		
		if(ruleGameMode() == 2){
			int points = 0;
			int bonus = 0;
			clear_temp.clear();
			for(int y = 19; y >= 0; y--) {
				for(int x = 0; x < 10; x++) {
					if(!isCleared(x, y, clear)) {
						pathfinder(x, y);
						if(clear_temp.size() >= 4) {
							points += (clear_temp.size() * 10);
							bonus++;
							clear.addAll(clear_temp);
						}
						clear_temp.clear();
					}
				}
			}
			if(points > 0) {
				alpha -= 5;
				scorePoint += (points * 2 * bonus * (scoreLevel + 1));
				scoreLives++;
				if(scoreLives > (1 + scoreLevel) * 10) {
					scoreLevel++;
					timer_break -= (timer_break / 10);
				}
				//Command_Collapse();
			}
		}
	}
	
	// private void checkField() {
	// 		int points = 0;
	// 	int bonus = 0;
	// 		for(int y = 14; y >= 0; y--) {
	// 		for(int x = 0; x < 6; x++) {
	// 			if(x < 4 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y] && grid[x][y] == grid[x + 2][y]) {
	// 				clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y)); clear.add(new Vector2(x + 2, y));
	// 				points += (30 + bonus);
	// 				bonus += 10;
	// 			}
	// 			if(y > 1 && grid[x][y] != -1 && grid[x][y] == grid[x][y - 1] && grid[x][y] == grid[x][y - 2]) {
	// 				clear.add(new Vector2(x, y)); clear.add(new Vector2(x, y - 1)); clear.add(new Vector2(x, y - 2));
	// 				points += (30 + bonus);
	// 				bonus += 10;
	// 			}
	// 			if(x < 4 && y > 1 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y - 1] && grid[x][y] == grid[x + 2][y - 2]) {
	// 				clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y - 1)); clear.add(new Vector2(x + 2, y - 2));
	// 				points += (30 + bonus);
	// 				bonus += 10;
	// 			}
	// 			if(x < 4 && y < 13 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y + 1] && grid[x][y] == grid[x + 2][y + 2]) {
	// 				clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y + 1)); clear.add(new Vector2(x + 2, y + 2));
	// 				points += (30 + bonus);
	// 				bonus += 10;
	// 			}
	// 		}
	// 	}
	// 		if(points > 0) {
	// 		alpha -= 5;
	// 		scorePoint += (points * 2 * (scoreLevel + 1));
	// 		scoreLives++;
	// 		if(scoreLives > (1 + scoreLevel) * 10) {
	// 			scoreLevel++;
	// 			time_break -= (time_break / 10);
	// 		}
	// 	}
	// }
	// private void checkField() {
	// 	int points = 0;
	// 	int bonus = 0;
	// 	clear_temp.clear();
	// 	for(int y = 14; y >= 0; y--) {
	// 		for(int x = 0; x < 6; x++) {
	// 			if(!isCleared(x, y)) {
	// 				pathfinder(x, y);
	// 				if(clear_temp.size() >= 4) {
	// 					points += (clear_temp.size() * 10);
	// 					bonus++;
	// 					clear.addAll(clear_temp);
	// 				}
	// 				clear_temp.clear();
	// 			}
	// 		}
	// 	}
	// 		if(points > 0) {
	// 		alpha -= 5;
	// 		scorePoint += (points * 2 * bonus * (scoreLevel + 1));
	// 		scoreLives++;
	// 		if(scoreLives > (1 + scoreLevel) * 10) {
	// 			scoreLevel++;
	// 			time_break -= (time_break / 10);
	// 		}
	// 		//Command_Collapse();
	// 	}
	// }
	
	private void pathfinder(int x, int y){
		clear_temp.add(new Vector2(x, y));
		if(y - 1 >=  0 && grid[x][y] != -1 && grid[x][y] == grid[x    ][y - 1] && !isCleared(x    , y - 1, clear_temp)) pathfinder(x    , y - 1);
		if(y + 1 <= 14 && grid[x][y] != -1 && grid[x][y] == grid[x    ][y + 1] && !isCleared(x    , y + 1, clear_temp)) pathfinder(x    , y + 1);
		if(x - 1 >=  0 && grid[x][y] != -1 && grid[x][y] == grid[x - 1][y    ] && !isCleared(x - 1, y    , clear_temp)) pathfinder(x - 1, y    );
		if(x + 1 <=  5 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y    ] && !isCleared(x + 1, y    , clear_temp)) pathfinder(x + 1, y    );
	}
	
	// private void pathfinder(int x, int y) {
	// 	clear_temp.add(new Vector2(x, y));
	// 	if(y - 1 >=  0 && grid[x][y] != -1 && grid[x][y] == grid[x    ][y - 1] && !isClearedTemp(x    , y - 1)) pathfinder(x    , y - 1);
	// 	if(y + 1 <= 14 && grid[x][y] != -1 && grid[x][y] == grid[x    ][y + 1] && !isClearedTemp(x    , y + 1)) pathfinder(x    , y + 1);
	// 	if(x - 1 >=  0 && grid[x][y] != -1 && grid[x][y] == grid[x - 1][y    ] && !isClearedTemp(x - 1, y    )) pathfinder(x - 1, y    );
	// 	if(x + 1 <=  5 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y    ] && !isClearedTemp(x + 1, y    )) pathfinder(x + 1, y    );
	// }
	
	
	
	
	
	
	
	
	
	
	
	
	// 	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 21;
	}
	
	public String getName(){
		return ruleGameMode() == 0 ? "tetris" : ruleGameMode() == 1 ? "columns" : "poyupoyu";
	}
	
	
	
}