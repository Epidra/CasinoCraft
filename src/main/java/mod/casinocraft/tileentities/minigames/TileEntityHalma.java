package mod.casinocraft.tileentities.minigames;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import net.minecraft.util.math.BlockPos;

public class TileEntityHalma extends TileEntityCasino {
	
	
	
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityHalma(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new int[17][9];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		if(difficulty == 2) {
			selector.set(8, 2);
            gridI[0][0] = -1; gridI[1][0] = -1; gridI[2][0] = -1; gridI[3][0] = -1; gridI[4][0] = -1; gridI[5][0] = -1; gridI[6][0] =  1; gridI[7][0] =  1; gridI[8][0] =  1; gridI[9][0] =  1; gridI[10][0] =  1; gridI[11][0] = -1; gridI[12][0] = -1; gridI[13][0] = -1; gridI[14][0] = -1; gridI[15][0] = -1; gridI[16][0] = -1;  
            gridI[0][1] = -1; gridI[1][1] = -1; gridI[2][1] = -1; gridI[3][1] = -1; gridI[4][1] = -1; gridI[5][1] = -1; gridI[6][1] =  1; gridI[7][1] =  1; gridI[8][1] =  1; gridI[9][1] =  1; gridI[10][1] =  1; gridI[11][1] = -1; gridI[12][1] = -1; gridI[13][1] = -1; gridI[14][1] = -1; gridI[15][1] = -1; gridI[16][1] = -1;  
            gridI[0][2] = -1; gridI[1][2] = -1; gridI[2][2] = -1; gridI[3][2] = -1; gridI[4][2] =  1; gridI[5][2] =  1; gridI[6][2] =  1; gridI[7][2] =  1; gridI[8][2] =  1; gridI[9][2] =  1; gridI[10][2] =  1; gridI[11][2] =  1; gridI[12][2] =  1; gridI[13][2] = -1; gridI[14][2] = -1; gridI[15][2] = -1; gridI[16][2] = -1;  
            gridI[0][3] = -1; gridI[1][3] = -1; gridI[2][3] = -1; gridI[3][3] = -1; gridI[4][3] =  1; gridI[5][3] =  1; gridI[6][3] =  1; gridI[7][3] =  1; gridI[8][3] =  1; gridI[9][3] =  1; gridI[10][3] =  1; gridI[11][3] =  1; gridI[12][3] =  1; gridI[13][3] = -1; gridI[14][3] = -1; gridI[15][3] = -1; gridI[16][3] = -1;  
            gridI[0][4] = -1; gridI[1][4] = -1; gridI[2][4] = -1; gridI[3][4] = -1; gridI[4][4] =  1; gridI[5][4] =  1; gridI[6][4] =  1; gridI[7][4] =  1; gridI[8][4] =  0; gridI[9][4] =  1; gridI[10][4] =  1; gridI[11][4] =  1; gridI[12][4] =  1; gridI[13][4] = -1; gridI[14][4] = -1; gridI[15][4] = -1; gridI[16][4] = -1;  
            gridI[0][5] = -1; gridI[1][5] = -1; gridI[2][5] = -1; gridI[3][5] = -1; gridI[4][5] =  1; gridI[5][5] =  1; gridI[6][5] =  1; gridI[7][5] =  1; gridI[8][5] =  1; gridI[9][5] =  1; gridI[10][5] =  1; gridI[11][5] =  1; gridI[12][5] =  1; gridI[13][5] = -1; gridI[14][5] = -1; gridI[15][5] = -1; gridI[16][5] = -1;  
            gridI[0][6] = -1; gridI[1][6] = -1; gridI[2][6] = -1; gridI[3][6] = -1; gridI[4][6] =  1; gridI[5][6] =  1; gridI[6][6] =  1; gridI[7][6] =  1; gridI[8][6] =  1; gridI[9][6] =  1; gridI[10][6] =  1; gridI[11][6] =  1; gridI[12][6] =  1; gridI[13][6] = -1; gridI[14][6] = -1; gridI[15][6] = -1; gridI[16][6] = -1;  
            gridI[0][7] = -1; gridI[1][7] = -1; gridI[2][7] = -1; gridI[3][7] = -1; gridI[4][7] = -1; gridI[5][7] = -1; gridI[6][7] =  1; gridI[7][7] =  1; gridI[8][7] =  1; gridI[9][7] =  1; gridI[10][7] =  1; gridI[11][7] = -1; gridI[12][7] = -1; gridI[13][7] = -1; gridI[14][7] = -1; gridI[15][7] = -1; gridI[16][7] = -1;  
            gridI[0][8] = -1; gridI[1][8] = -1; gridI[2][8] = -1; gridI[3][8] = -1; gridI[4][8] = -1; gridI[5][8] = -1; gridI[6][8] =  1; gridI[7][8] =  1; gridI[8][8] =  1; gridI[9][8] =  1; gridI[10][8] =  1; gridI[11][8] = -1; gridI[12][8] = -1; gridI[13][8] = -1; gridI[14][8] = -1; gridI[15][8] = -1; gridI[16][8] = -1;  
        } else {
        	selector.set(8, 4);
        	gridI[0][0] = -1; gridI[1][0] = -1; gridI[2][0] = -1; gridI[3][0] =  1; gridI[4][0] =  1; gridI[5][0] =  1; gridI[6][0] =  1; gridI[7][0] = -1; gridI[8][0] = -1; gridI[9][0] = -1; gridI[10][0] =  1; gridI[11][0] =  1; gridI[12][0] =  1; gridI[13][0] =  1; gridI[14][0] = -1; gridI[15][0] = -1; gridI[16][0] = -1;  
            gridI[0][1] = -1; gridI[1][1] = -1; gridI[2][1] = -1; gridI[3][1] =  1; gridI[4][1] =  1; gridI[5][1] =  1; gridI[6][1] =  1; gridI[7][1] = -1; gridI[8][1] = -1; gridI[9][1] = -1; gridI[10][1] =  1; gridI[11][1] =  1; gridI[12][1] =  1; gridI[13][1] =  1; gridI[14][1] = -1; gridI[15][1] = -1; gridI[16][1] = -1;  
            gridI[0][2] =  1; gridI[1][2] =  1; gridI[2][2] =  1; gridI[3][2] =  1; gridI[4][2] =  1; gridI[5][2] =  1; gridI[6][2] =  1; gridI[7][2] =  1; gridI[8][2] =  1; gridI[9][2] =  1; gridI[10][2] =  1; gridI[11][2] =  1; gridI[12][2] =  1; gridI[13][2] =  1; gridI[14][2] =  1; gridI[15][2] =  1; gridI[16][2] =  1;  
            gridI[0][3] =  1; gridI[1][3] =  1; gridI[2][3] =  1; gridI[3][3] =  1; gridI[4][3] =  1; gridI[5][3] =  1; gridI[6][3] =  1; gridI[7][3] =  1; gridI[8][3] =  1; gridI[9][3] =  1; gridI[10][3] =  1; gridI[11][3] =  1; gridI[12][3] =  1; gridI[13][3] =  1; gridI[14][3] =  1; gridI[15][3] =  1; gridI[16][3] =  1;  
            gridI[0][4] =  1; gridI[1][4] =  1; gridI[2][4] =  1; gridI[3][4] =  1; gridI[4][4] =  0; gridI[5][4] =  0; gridI[6][4] =  1; gridI[7][4] =  1; gridI[8][4] =  1; gridI[9][4] =  1; gridI[10][4] =  1; gridI[11][4] =  0; gridI[12][4] =  0; gridI[13][4] =  1; gridI[14][4] =  1; gridI[15][4] =  1; gridI[16][4] =  1;  
            gridI[0][5] =  1; gridI[1][5] =  1; gridI[2][5] =  1; gridI[3][5] =  1; gridI[4][5] =  1; gridI[5][5] =  1; gridI[6][5] =  1; gridI[7][5] =  1; gridI[8][5] =  1; gridI[9][5] =  1; gridI[10][5] =  1; gridI[11][5] =  1; gridI[12][5] =  1; gridI[13][5] =  1; gridI[14][5] =  1; gridI[15][5] =  1; gridI[16][5] =  1;  
            gridI[0][6] =  1; gridI[1][6] =  1; gridI[2][6] =  1; gridI[3][6] =  1; gridI[4][6] =  1; gridI[5][6] =  1; gridI[6][6] =  1; gridI[7][6] =  1; gridI[8][6] =  1; gridI[9][6] =  1; gridI[10][6] =  1; gridI[11][6] =  1; gridI[12][6] =  1; gridI[13][6] =  1; gridI[14][6] =  1; gridI[15][6] =  1; gridI[16][6] =  1;  
            gridI[0][7] = -1; gridI[1][7] = -1; gridI[2][7] = -1; gridI[3][7] =  1; gridI[4][7] =  1; gridI[5][7] =  1; gridI[6][7] =  1; gridI[7][7] = -1; gridI[8][7] = -1; gridI[9][7] = -1; gridI[10][7] =  1; gridI[11][7] =  1; gridI[12][7] =  1; gridI[13][7] =  1; gridI[14][7] = -1; gridI[15][7] = -1; gridI[16][7] = -1;  
            gridI[0][8] = -1; gridI[1][8] = -1; gridI[2][8] = -1; gridI[3][8] =  1; gridI[4][8] =  1; gridI[5][8] =  1; gridI[6][8] =  1; gridI[7][8] = -1; gridI[8][8] = -1; gridI[9][8] = -1; gridI[10][8] =  1; gridI[11][8] =  1; gridI[12][8] =  1; gridI[13][8] =  1; gridI[14][8] = -1; gridI[15][8] = -1; gridI[16][8] = -1; 
        }
	}
    
    public void actionTouch(int action){
    	Jump(action%17, action/17);
    }
    
    public void update(){
		
	}
	
    
    
  //--------------------GETTER--------------------
    
    public int getValue(int index){
		return gridI[index%17][index/17];
	}
	
	
	
	//--------------------CUSTOM--------------------
	
    private void Jump(int x, int y) {
        if(gridI[x][y] == 1) {
            selector.set(x, y);
            scoreLevel = 1;
        } else if(gridI[x][y] == 0) {
                   if(y + 2 == selector.Y && gridI[selector.X][selector.Y - 1] == 1) { // Jump to UP
                gridI[x][y    ] = 1;
                gridI[x][y + 1] = 0;
                gridI[x][y + 2] = 0;
                selector.set(x, y);
                scorePoints += scoreLevel;
                scoreLevel++;
            } else if(y - 2 == selector.Y && gridI[selector.X][selector.Y + 1] == 1) { // Jump to DOWN
                gridI[x][y    ] = 1;
                gridI[x][y - 1] = 0;
                gridI[x][y - 2] = 0;
                selector.set(x, y);
                scorePoints += scoreLevel;
                scoreLevel++;
            } else if(x + 2 == selector.X && gridI[selector.X - 1][selector.Y] == 1) { // Jump to LEFT
                gridI[x    ][y] = 1;
                gridI[x + 1][y] = 0;
                gridI[x + 2][y] = 0;
                selector.set(x, y);
                scorePoints += scoreLevel;
                scoreLevel++;
            } else if(x - 2 == selector.X && gridI[selector.X + 1][selector.Y] == 1) { // Jump to RIGHT
                gridI[x    ][y] = 1;
                gridI[x - 1][y] = 0;
                gridI[x - 2][y] = 0;
                selector.set(x, y);
                scorePoints += scoreLevel;
                scoreLevel++;
            }
            Check_For_GameOver();
        }
    }

    private void Check_For_GameOver() {
        boolean canJump = false;
        int counter = 0;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(gridI[x][y] == 1) {
                    counter++;
                    if(y >=  2 && gridI[x][y - 1] == 1 && gridI[x][y - 2] == 0) canJump = true;
                    if(y <=  6 && gridI[x][y + 1] == 1 && gridI[x][y + 2] == 0) canJump = true;
                    if(x >=  2 && gridI[x - 1][y] == 1 && gridI[x - 2][y] == 0) canJump = true;
                    if(x <= 14 && gridI[x + 1][y] == 1 && gridI[x + 2][y] == 0) canJump = true;
                }
            }
        }
        if(counter == 1) turnstate = 4;
        if(!canJump) turnstate = 4;
    }
    
}