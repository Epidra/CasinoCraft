package mod.casinocraft.client.logic.game;

import mod.casinocraft.client.logic.LogicModule;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_CHIP;

public class Logic42 extends LogicModule {   //  Ishido
	
	// GAME MODE -- ISHIDO
	// RULE 1 -- ??
	// RULE 2 -- ??
	// RULE 3 -- ??
	// COLOR VARIATION -- ??
	
	// --------------------------------------------------
	
	public List<Card> reserve = new ArrayList<Card>();
	
	//
	// // ---
	//
	// public int[] alpha = new int[4];
	// public List<Integer> color_simon  = new ArrayList<>();
	// public List<Integer> color_player = new ArrayList<>();
	// public int alpha_pos = 0;
	// public boolean[] alpha_player = new boolean[4];
	// public boolean result;
	//
	// // ---
	//
	// public boolean selectA = false;
	// public boolean selectB = false;
	//
	// public Vector2 positionA = new Vector2(0, 0);
	// public Vector2 positionB = new Vector2(0, 0);
	//
	// public int timer = 0;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic42(int tableID) {
		super(tableID, /* 12, 8 */ 17, 9 );
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  START / RESTART  ---------- ---------- ---------- ---------- //
	
	public void start2() {
		reserve = fillReserve();
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 12; x++){
				if(tableID == 1){
					if((x < 2 || x > 9)){
						grid[x][y] = -3;
					} else {
						grid[x][y] = -1;
					}
				} else {
					grid[x][y] = -1;
				}
			}
		}
				boolean[] number = new boolean[6];
		boolean[] suit = new boolean[6];
		for(int i = 0; i < 6; i++){
			number[i] = false;
			suit[i] = false;
		}
		int placed = 0;
		for(int i = 0; i < reserve.size(); i++){
			if(!number[reserve.get(i).number] && !suit[reserve.get(i).suit]){
				number[reserve.get(i).number] = true;
				suit[reserve.get(i).suit] = true;
				placed++;
						if(grid[5][3] < 0) grid[5][3] = takeMino(i);
				else if(grid[6][4] < 0) grid[6][4] = takeMino(i);
						else if(grid[tableID == 1 ? 2 :  0][0] < 0) grid[tableID == 1 ? 2 :  0][0] = takeMino(i);
				else if(grid[tableID == 1 ? 9 : 11][0] < 0) grid[tableID == 1 ? 9 : 11][0] = takeMino(i);
				else if(grid[tableID == 1 ? 2 :  0][7] < 0) grid[tableID == 1 ? 2 :  0][7] = takeMino(i);
				else if(grid[tableID == 1 ? 9 : 11][7] < 0) grid[tableID == 1 ? 9 : 11][7] = takeMino(i);
							}
			if(placed == 6){
				break;
			}
		}
		checkForGameOver();
		
		//
		// // ---
		//
		// color_simon.clear();
		// color_simon.add(RANDOM.nextInt(4));
		// color_simon.add(RANDOM.nextInt(4));
		// if(tableID == 2) color_simon.add(RANDOM.nextInt(4));
		// if(tableID == 2) color_simon.add(RANDOM.nextInt(4));
		// color_player.clear();
		// alpha[0] = 0;
		// alpha[1] = 0;
		// alpha[2] = 0;
		// alpha[3] = 0;
		// alpha_pos = 0;
		// result = false;
		// scoreLevel = 1;
		// alpha_player[0] = false;
		// alpha_player[1] = false;
		// alpha_player[2] = false;
		// alpha_player[3] = false;
		// turnstate = 3;
		// alpha[color_simon.get(0)] = getAlpha();
		//
		// // ---
		//
		// scoreLevel = 1;
		// scoreLives = 8;
		// selectA = false;
		// selectB = false;
		// timer = 0;
		// positionA.set(-1, -1);
		// positionB.set(-1, -1);
		// commandCreateGrid();
	}
	
	public void restart(){
		// scoreLevel++;
		// scorePoint += color_simon.size();
		// color_simon.add(RANDOM.nextInt(4));
		// color_simon.add(RANDOM.nextInt(4));
		// color_player.clear();
		// alpha[0] = 0;
		// alpha[1] = 0;
		// alpha[2] = 0;
		// alpha[3] = 0;
		// alpha_pos = 0;
		// alpha_player[0] = false;
		// alpha_player[1] = false;
		// alpha_player[2] = false;
		// alpha_player[3] = false;
		// turnstate = 3;
		// result = false;
		// alpha[color_simon.get(0)] = getAlpha();
		
		//
		// // ---
		//
		// turnstate = 2;
		// scoreLevel++;
		// scoreLives += scoreLevel * 2;
		// selectA = false;
		// selectB = false;
		// timer = 0;
		// positionA.set(-1, -1);
		// positionB.set(-1, -1);
		// commandCreateGrid();
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		place(action);
		
		//
		// // ---
		//
		// color_player.add(action);
		// alpha[action] = getAlpha();
		// setJingle(SOUND_CHIP);
		//
		// // ---
		//
		// if(action == -1) {
		// 	restart();
		// } else if(action == -2) {
		// 	turnstate = 4;
		// } else {
		// 	clickMino(action % 17, action / 17);
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
		// for(int i = 0; i < 4; i++){
		// 	if(alpha[i] > 0){
		// 		alpha[i]--;
		// 		if(alpha[i] <= 0) alpha[i] = 0;
		// 	}
		// }
		// if(turnstate == 2){
		// 	if(color_simon.size() == color_player.size()){
		// 		if(alpha[0] == 0 && alpha[1] == 0 && alpha[2] == 0 && alpha[3] == 0){
		// 			boolean match = true;
		// 			for(int i = 0; i < color_player.size(); i++){
		// 				if(color_player.get(i) != color_simon.get(i)){
		// 					match = false;
		// 				}
		// 			}
		// 			if(!match){
		// 				turnstate = 4;
		// 			} else {
		// 				restart();
		// 			}
		// 		}
		// 	}
		// } else if(turnstate == 3){
		// 	if(alpha[0] == 0 && alpha[1] == 0 && alpha[2] == 0 && alpha[3] == 0){
		// 		alpha_pos++;
		// 		if(alpha_pos == color_simon.size()){
		// 			turnstate = 2;
		// 		} else {
		// 			alpha[color_simon.get(alpha_pos)] = getAlpha();
		// 		}
		// 	}
		// }
		
		//
		// // ---
		//
		// if(timer == -1){
		// 	if(grid[positionA.X][positionA.Y] == grid[positionB.X][positionB.Y]){
		// 		grid[positionA.X][positionA.Y] = -1;
		// 		grid[positionB.X][positionB.Y] = -1;
		// 	} else {
		// 		scoreLives--;
		// 	}
		// 	boolean temp = false;
		// 	for(int x = 0; x < 17; x++) {
		// 		for(int y = 0; y < 9; y++) {
		// 			if(grid[x][y] != -1) temp = true;
		// 		}
		// 	}
		// 	if(!temp) {
		// 		turnstate = 3;
		// 		scorePoint += scoreLevel * 4;
		// 	} else {
		// 		if(scoreLives <= 0) {
		// 			turnstate = 4;
		// 			scorePoint /= 2;
		// 		}
		// 	}
		// 	selectA = false;
		// 	selectB = false;
		// 	positionA.set(-1, -1);
		// 	positionB.set(-1, -1);
		// 	timer = 0;
		// }
	}
	
	public void updateMotion() {
		// if(timer > 0){
		// 	timer -= 10;
		// 	if(timer <= 0)
		// 		timer = -1;
		// }
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		reserve.addAll(loadCardList(compound, 0));
		
		//
		// // ---
		//
		// alpha[0] = compound.getInt("alpha0");
		// alpha[1] = compound.getInt("alpha1");
		// alpha[2] = compound.getInt("alpha2");
		// alpha[3] = compound.getInt("alpha3");
		// {
		// 	int[] i = compound.getIntArray("colorsimon");
		// 	color_simon.clear();
		// 	for(int x : i){
		// 		color_simon.add(x);
		// 	}
		// }
		// {
		// 	int[] i = compound.getIntArray("colorplayer");
		// 	color_player.clear();
		// 	for(int x : i){
		// 		color_player.add(x);
		// 	}
		// }
		// alpha_pos = compound.getInt("alphapos");
		// alpha_player[0] = compound.getBoolean("alphaplayer0");
		// alpha_player[1] = compound.getBoolean("alphaplayer1");
		// alpha_player[2] = compound.getBoolean("alphaplayer2");
		// alpha_player[3] = compound.getBoolean("alphaplayer3");
		// result = compound.getBoolean("result");
		//
		// // ---
		//
		// selectA = compound.getBoolean("selecta");
		// selectB = compound.getBoolean("selectb");
		// positionA.set(compound.getInt("positionax"), compound.getInt("positionay"));
		// positionA.set(compound.getInt("positionbx"), compound.getInt("positionby"));
		// timer = compound.getInt("timer");
	}
	
	public CompoundTag save2(CompoundTag compound){
		saveCardList(compound, 0, reserve);
		return compound;
		
		//
		// // ---
		//
		// compound.putInt("alpha0", alpha[0]);
		// compound.putInt("alpha1", alpha[1]);
		// compound.putInt("alpha2", alpha[2]);
		// compound.putInt("alpha3", alpha[3]);
		// compound.putIntArray("colorsimon",  color_simon);
		// compound.putIntArray("colorplayer", color_player);
		// compound.putInt("alphapos", alpha_pos);
		// compound.putBoolean("alphaplayer0", alpha_player[0]);
		// compound.putBoolean("alphaplayer1", alpha_player[1]);
		// compound.putBoolean("alphaplayer2", alpha_player[2]);
		// compound.putBoolean("alphaplayer3", alpha_player[3]);
		// compound.putBoolean("result", result);
		// return compound;
		//
		// // ---
		//
		// compound.putBoolean("selecta", selectA);
		// compound.putBoolean("selectb", selectB);
		// compound.putInt("positionax", positionA.X);
		// compound.putInt("positionay", positionA.Y);
		// compound.putInt("positionbx", positionB.X);
		// compound.putInt("positionby", positionB.Y);
		// compound.putInt("timer", timer);
		// return compound;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void place(int action){
		int x = action % 12;
		int y = action / 12;
		int connections = canPlace(x, y);
		if(connections > 0){
			setJingle(SOUND_CHIP);
			switch(connections){
				case 1: scorePoint +=  1; break;
				case 2: scorePoint +=  4; break;
				case 3: scorePoint +=  8; break;
				case 4: scorePoint += 16; break;
			}
			grid[x][y] = takeNextMino();
			checkForGameOver();
		}
	}
	
	private List<Card> fillReserve(){
		List<Card> stack = new ArrayList<Card>();
		List<Card> deck  = new ArrayList<Card>();
			for(int z = 0; z < 2; z++){
			for(int y = 0; y < 6; y++) {
				for(int x = 0; x < 6; x++) {
					stack.add(new Card(x, y));
				}
			}
		}
			while(stack.size() > 1) {
			int r = RANDOM.nextInt(stack.size() - 1);
			deck.add(stack.get(r));
			stack.remove(r);
		}
		deck.add(stack.get(0));
			return deck;
	}
	
	private int takeNextMino(){
		return takeMino(0);
	}
	
	private int takeMino(int index){
		int x = reserve.get(index).number;
		int y = reserve.get(index).suit;
		reserve.remove(index);
		return x + y * 6;
	}
	
	private void checkForGameOver(){
		if(reserve.size() == 0){
			turnstate = 4;
		} else {
			boolean placable = false;
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 12; x++){
					int connect = canPlace(x, y);
					if(connect > 0){
						placable = true;
					}
					if(grid[x][y] == -1 || grid[x][y] == -2){
						grid[x][y] = connect > 0 ? -2 : -1;
					}
				}
			}
			if(!placable){
				turnstate = 4;
			}
		}
	}
	
	private int canPlace(int x, int y){
		if(grid[x][y] >= 0 || grid[x][y] == -3){
			return 0;
		}
		int connections = 0;
		boolean noCon = false;
		if(x - 1 >=  0){ if(grid[x-1][y  ] >= 0){ if(grid[x-1][y  ] % 6 == reserve.get(0).number || grid[x-1][y  ] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } }
		if(x + 1 <  12){ if(grid[x+1][y  ] >= 0){ if(grid[x+1][y  ] % 6 == reserve.get(0).number || grid[x+1][y  ] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } }
		if(y - 1 >=  0){ if(grid[x  ][y-1] >= 0){ if(grid[x  ][y-1] % 6 == reserve.get(0).number || grid[x  ][y-1] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } }
		if(y + 1 <   8){ if(grid[x  ][y+1] >= 0){ if(grid[x  ][y+1] % 6 == reserve.get(0).number || grid[x  ][y+1] / 6 == reserve.get(0).suit){ connections++; } else { noCon = true; } } }
		return noCon ? 0 : connections;
	}
	
	public int getAlpha(){
		return Math.max(10 - scoreLevel, 2);
	}
	
	// private void clickMino(int x, int y) {
	// 	if(!selectA) {
	// 		if(grid[x][y] != -1) {
	// 			selectA = true;
	// 			positionA.set(x, y);
	// 			setJingle(SOUND_CHIP);
	// 		}
	// 	} else if(!selectB) {
	// 		if(grid[x][y] != -1 && !positionA.matches(x, y)) {
	// 			selectB = true;
	// 			positionB.set(x, y);
	// 			timer = 200 - scoreLevel*10;
	// 			setJingle(SOUND_CHIP);
	// 		}
	// 	}
	// }
	
	// private void commandCreateGrid() {
	// 	for(int x = 0; x < 17; x++) {
	// 		for(int y = 0; y < 9; y++) {
	// 			grid[x][y] = -1;
	// 		}
	// 	}
	// 	int max = tableID == 1 ? 9*9-1 : 17*9-1;
	// 	int filler = scoreLevel*4 < max ? scoreLevel*4 : max;
	// 	int filler2 = filler;
	// 		int[] color = new int[] {0,0,0,0,0,0,0,0};
	// 	while(filler > 0) {
	// 		int z = RANDOM.nextInt(8);
	// 		color[z] += 2;
	// 		filler -= 2;
	// 	}
	// 		while(filler2 > 0) {
	// 		int x = RANDOM.nextInt(tableID == 1 ? 9 : 17) + (tableID == 1 ? 4 : 0);
	// 		int y = RANDOM.nextInt(9);
	// 		if(grid[x][y] == -1) {
	// 			for(int i = 0; i < 8; i++) {
	// 				if(color[i] > 0) {
	// 					grid[x][y] = i;
	// 					color[i]--;
	// 					filler2--;
	// 					break;
	// 				}
	// 			}
	// 		}
	// 	}
	// }
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 12;
	}
	
	public String getName(){
		return "ishido";
	}
	
	
	
}