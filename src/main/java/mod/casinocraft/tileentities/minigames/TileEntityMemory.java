package mod.casinocraft.tileentities.minigames;

import mod.shared.util.Vector2;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import net.minecraft.util.math.BlockPos;

public class TileEntityMemory extends TileEntityCasino {
	
    public boolean selected_A;
    public boolean selected_B;
    public Vector2 selected_A_pos;
    public Vector2 selected_B_pos;
    public int timer;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityMemory(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new int[17][9];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		scoreLevel = 1;
		scoreLives = 8;
		selected_A = false;
        selected_B = false;
        timer = 0;
        selected_A_pos = new Vector2(-1, -1);
        selected_B_pos = new Vector2(-1, -1);
        Command_Create_Grid();
	}
	
	public void restart(){
		turnstate = 2;
		scoreLevel++;
		scoreLives += scoreLevel*2;
		selected_A = false;
        selected_B = false;
        timer = 0;
        selected_A_pos = new Vector2(-1, -1);
        selected_B_pos = new Vector2(-1, -1);
        Command_Create_Grid();
	}
    
    public void actionTouch(int action){
    	if(action == -1) {
    		restart();
    	} else if(action == -2) {
    		turnstate = 4;
    	} else {
    		Click_Mino(action % 17, action / 17);
    	}
    }
    
    public void update(){
		if(timer > 0){
    		timer-=10;
    		if(timer <= 0){
    			if(gridI[selected_A_pos.X][selected_A_pos.Y] == gridI[selected_B_pos.X][selected_B_pos.Y]){
    				gridI[selected_A_pos.X][selected_A_pos.Y] = -1;
                    gridI[selected_B_pos.X][selected_B_pos.Y] = -1;
    			} else {
    				scoreLives--;
    				//ChangeName(selected_A_pos.X, selected_A_pos.Y, grid[selected_A_pos.X][selected_A_pos.Y]);
    				//ChangeName(selected_B_pos.X, selected_B_pos.Y, grid[selected_B_pos.X][selected_B_pos.Y]);
    			}
    			boolean temp = false;
                for(int x = 0; x < 17; x++) {
                    for(int y = 0; y < 9; y++) {
                        if(gridI[x][y] != -1) temp = true;
                    }
                }
                if(!temp) {
                	turnstate = 3;
                	scorePoints += scoreLevel*4;
                } else {
                	if(scoreLives <= 0) {
            			turnstate = 4;
            			scorePoints /= 2;
            		}
                }
                selected_A = false;
                selected_B = false;
                selected_A_pos = new Vector2(-1, -1);
                selected_B_pos = new Vector2(-1, -1);
    		}
    	}
	}
	
    
    
  //--------------------GETTER--------------------
    
    public int getValue(int index){
    	if(index == -1) return timer;
    	if(index == 200) return selected_A_pos.X;
    	if(index == 201) return selected_A_pos.Y;
    	if(index == 202) return selected_B_pos.X;
    	if(index == 203) return selected_B_pos.Y;
    	return gridI[index % 17][index / 17];
    }
    
	
	
	//--------------------CUSTOM--------------------
	
    public void Click_Mino(int x, int y) {
        if(!selected_A) {
            if(gridI[x][y] != -1) {
                selected_A = true;
                selected_A_pos = new Vector2(x, y);
                //ChangeName(x, y, grid[x][y]);
            }
        } else if(!selected_B) {
            if(gridI[x][y] != -1 && !selected_A_pos.matches(x, y)) {
                selected_B = true;
                selected_B_pos = new Vector2(x, y);
                //ChangeName(x, y, grid[x][y]);
                timer = 400-scoreLevel*10;
            }
        }
    }
    
    private void Command_Create_Grid() {
        for(int x = 0; x < 17; x++) {
            for(int y = 0; y < 9; y++) {
                gridI[x][y] = -1;
            }
        }
        int max = difficulty == 2 ? 9*9-1 : 17*9-1;
        int filler = scoreLevel*4 < max ? scoreLevel*4 : max;
        int filler2 = filler;
        
        int color[] = new int[] {0,0,0,0,0,0,0,0};
        while(filler > 0) {
        	int z = rand.nextInt(8);
        	color[z] += 2;
        	filler -= 2;
        	//for(int i = 0; i < 8; i++) {
        	//	if(filler > 0) { color[i] += 2; filler -= 2; }
        	//}
        }
        
        while(filler2 > 0) {
        	int x = rand.nextInt(difficulty == 2 ? 9 : 17) + (difficulty == 2 ? 4 : 0);
        	int y = rand.nextInt(9);
        	if(gridI[x][y] == -1) {
        		for(int i = 0; i < 8; i++) {
        			if(color[i] > 0) {
        				gridI[x][y] = i;
        				color[i]--;
        				filler2--;
        				break;
        			}
        		}
        	}
        }
    }
	
}
