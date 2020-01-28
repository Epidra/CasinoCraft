package mod.casinocraft.tileentities.minigames;

import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import net.minecraft.util.math.BlockPos;

public class TileEntity2048 extends TileEntityCasino {

    boolean placing;
    boolean active_timer;
    int timer;
    int direction; // 0 - null, 1 - up, 2 - down, 3 - left, 4 - right
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntity2048(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new     int[4][4];
        gridB = new boolean[4][4];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                gridI[x][y] = 0;
                gridB[x][y] = false;
            }
        }
        placing = false;
        gridI[0][0] = rand.nextInt(1) + 1;
        gridI[3][0] = rand.nextInt(1) + 1;
        gridI[0][3] = rand.nextInt(1) + 1;
        gridI[3][3] = rand.nextInt(1) + 1;
        active_timer = false;
        timer = 0;
        direction = 0;
	}
    
    public void actionTouch(int action){
        if(action == 0) { Move(1); }
        if(action == 1) { Move(2); }
        if(action == 2) { Move(3); }
        if(action == 3) { Move(4); }
    }
    
    public void update(){
		if(active_timer) {
            timer = timer + 6;
            if(timer == 48) {
                active_timer = false;
                timer = 0;
                Change();
                Move(direction);
                placing = true;
            }
        } else {
            if(placing) {
                Place();
                placing = false;
            }
            direction = 0;
        }
	}
	
    
    
  //--------------------GETTER--------------------
    
    public int getValue(int index){
		if(index == 16) return timer;
		if(index == 17) return direction;
		return gridI[index%4][index/4];
	}
	
	public boolean getFlag(int index){
		if(index == 16) return placing;
		if(index == 17) return active_timer;
		return gridB[index%4][index/4];
	}
	
	
	
	//--------------------CUSTOM--------------------
	
	private void Move(int s) {
        if(s == 1) { // up
            for(int y = 1; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(gridI[x][y] != 0) {
                        if(gridI[x][y - 1] == 0 || gridI[x][y - 1] == gridI[x][y]) {
                            gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(gridI[x][y] != 0) {
                        if(gridI[x][y + 1] == 0 || gridI[x][y + 1] == gridI[x][y]) {
                            gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(gridI[x][y] != 0) {
                        if(gridI[x - 1][y] == 0 || gridI[x - 1][y] == gridI[x][y]) {
                            gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(gridI[x][y] != 0) {
                        if(gridI[x + 1][y] == 0 || gridI[x + 1][y] == gridI[x][y]) {
                            gridB[x][y] = true;
                            active_timer = true;
                            direction = s;
                        }
                    }
                }
            }
        }
    }

    private void Change() {
        if(direction == 1) { // up
            for(int y = 1; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(gridB[x][y]) {
                        if(gridI[x][y - 1] == 0) {
                            gridI[x][y - 1] = gridI[x][y];
                        } else {
                            gridI[x][y - 1]++;
                            Add_Points(gridI[x][y - 1]);
                        }
                        gridI[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(gridB[x][y]) {
                        if(gridI[x][y + 1] == 0) {
                            gridI[x][y + 1] = gridI[x][y];
                        } else {
                            gridI[x][y + 1]++;
                            Add_Points(gridI[x][y + 1]);
                        }
                        gridI[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(gridB[x][y]) {
                        if(gridI[x - 1][y] == 0) {
                            gridI[x - 1][y] = gridI[x][y];
                        } else {
                            gridI[x - 1][y]++;
                            Add_Points(gridI[x - 1][y]);
                        }
                        gridI[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(gridB[x][y]) {
                        if(gridI[x + 1][y] == 0) {
                            gridI[x + 1][y] = gridI[x][y];
                        } else {
                            gridI[x + 1][y]++;
                            Add_Points(gridI[x + 1][y]);
                        }
                        gridI[x][y] = 0;
                    }
                }
            }
        }
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                gridB[x][y] = false;
            }
        }
    }

    private void Place() {
        for(int i = 0; i < 24; i++) {
            int x = rand.nextInt(4);
            int y = rand.nextInt(4);
            if(gridI[x][y] == 0) {
                gridI[x][y] = 1;
                break;
            }
        }
        Check();
    }

    private void Check() {
        boolean b = false;
        for(int y = 1; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                if(gridI[x][y] != 0) {
                    if(gridI[x][y - 1] == 0 || gridI[x][y - 1] == gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int y = 2; y > -1; y--) {
            for(int x = 3; x > -1; x--) {
                if(gridI[x][y] != 0) {
                    if(gridI[x][y + 1] == 0 || gridI[x][y + 1] == gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 1; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(gridI[x][y] != 0) {
                    if(gridI[x - 1][y] == 0 || gridI[x - 1][y] == gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 2; x > -1; x--) {
            for(int y = 3; y > -1; y--) {
                if(gridI[x][y] != 0) {
                    if(gridI[x + 1][y] == 0 || gridI[x + 1][y] == gridI[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        if(!b) {
            turnstate = 4;
        }
    }

    private int Get_Direction(boolean horizontal, int x, int y) {
        if(direction == 0)
            return 0;
        if( horizontal && direction == 3) if(gridB[x][y]) return -timer; // left
        if( horizontal && direction == 4) if(gridB[x][y]) return  timer; // right
        if(!horizontal && direction == 1) if(gridB[x][y]) return -timer; // up
        if(!horizontal && direction == 2) if(gridB[x][y]) return  timer; // down
        return 0;
    }

    private void Add_Points(int i) {
        if(i ==  1) scorePoints +=     2;
        if(i ==  2) scorePoints +=     4;
        if(i ==  3) scorePoints +=     8;
        if(i ==  4) scorePoints +=    16;
        if(i ==  5) scorePoints +=    32;
        if(i ==  6) scorePoints +=    64;
        if(i ==  7) scorePoints +=   128;
        if(i ==  8) scorePoints +=   256;
        if(i ==  9) scorePoints +=   512;
        if(i == 10) scorePoints +=  1024;
        if(i == 11) scorePoints +=  2048;
        if(i == 12) scorePoints +=  4096;
        if(i == 13) scorePoints +=  8192;
        if(i == 14) scorePoints += 16384;
        if(i == 15) scorePoints += 32768;
        if(i == 16) scorePoints += 65536;
    }
	
}