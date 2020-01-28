package mod.casinocraft.tileentities.minigames;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import net.minecraft.util.math.BlockPos;

public class TileEntityMysticSquare extends TileEntityCasino {
    
    public String direction;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityMysticSquare(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new     int[4][4];
        gridB = new boolean[4][4];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                gridI[x][ y] = -1;
                gridB[x][ y] = false;
            }
        }
        int i = 0;
        while(i < 15) {
            int x = rand.nextInt(4);
            int y = rand.nextInt(4);
            if(gridI[x][y] == -1) {
                gridI[x][y] = i;
                i++;
            }
        }
        direction = "null";
	}
    
    public void actionTouch(int action){
    	Move(action);
    }
	
	public void update(){
		
	}
    
	
	
	//--------------------GETTER--------------------
	
	public int getValue(int index){
		return gridI[index % 4][index / 4];
	}
	
	
	
	//--------------------CUSTOM--------------------
	
    public void Move(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && y > 0) if(gridI[x][y - 1] == -1) gridB[x][y] = true; // UP
                if(direction == 1 && y < 3) if(gridI[x][y + 1] == -1) gridB[x][y] = true; // DOWN
                if(direction == 2 && x > 0) if(gridI[x - 1][y] == -1) gridB[x][y] = true; // LEFT
                if(direction == 3 && x < 3) if(gridI[x + 1][y] == -1) gridB[x][y] = true; // RIGHT
            }
        }
        Change(direction);
    }

    private void Change(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && gridB[x][y]) { gridI[x][y - 1] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // UP
                if(direction == 1 && gridB[x][y]) { gridI[x][y + 1] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // DOWN
                if(direction == 2 && gridB[x][y]) { gridI[x - 1][y] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // LEFT
                if(direction == 3 && gridB[x][y]) { gridI[x + 1][y] = gridI[x][y]; gridI[x][y] = -1; gridB[x][y] = false; } // RIGHT
            }
        }
    }
	
}
