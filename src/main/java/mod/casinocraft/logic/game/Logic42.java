package mod.casinocraft.logic.game;

import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.util.Card;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.mapping.SoundMap.SOUND_CHIP;

public class Logic42 extends LogicModule {   //  Ishido
	
	public List<Card> reserve = new ArrayList<Card>();
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public Logic42(int tableID) {
		super(tableID, 17, 9 );
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
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  COMMAND  ---------- ---------- ---------- ---------- //
	
	public void command(int action) {
		place(action);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  UPDATE  ---------- ---------- ---------- ---------- //
	
	public void updateLogic() {
	
	}
	
	public void updateMotion() {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SAVE / LOAD  ---------- ---------- ---------- ---------- //
	
	public void load2(CompoundTag compound){
		reserve.addAll(loadCardList(compound, 0));
	}
	
	public CompoundTag save2(CompoundTag compound){
		saveCardList(compound, 0, reserve);
		return compound;
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
	
	
	
	
	
	// ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
	
	public boolean hasHighscore(){
		return true;
	}
	
	public boolean isMultiplayer(){
		return false;
	}
	
	public int getID(){
		return 42;
	}
	
	public String getName(){
		return "ishido";
	}
	
	
	
}