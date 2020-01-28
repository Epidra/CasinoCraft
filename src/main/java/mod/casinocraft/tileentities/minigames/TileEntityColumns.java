package mod.casinocraft.tileentities.minigames;

import java.util.ArrayList;
import java.util.List;

import mod.shared.util.Vector2;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import net.minecraft.util.math.BlockPos;

public class TileEntityColumns extends TileEntityCasino {
	
	public boolean active_hold;

    public int[] container_next    = new int[3];
    public int[] container_hold    = new int[3];
    public int[] container_current = new int[3];

    public double time_last;
    public double time_break;
    public int timer;

    public Vector2[] tromino = new Vector2[3];

    public List<Vector2> clear = new ArrayList<Vector2>();

    public int alpha;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityColumns(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new int[6][15];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		active_hold = true;
        container_next[0] = Column_Roll();
        container_next[1] = Column_Roll();
        container_next[2] = Column_Roll();
        container_hold[0] = -1;
        container_hold[1] = -1;
        container_hold[2] = -1;
        container_current[0] = Column_Roll();
        container_current[1] = Column_Roll();
        container_current[2] = Column_Roll();
        Column_Create();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 15; j++) {
                gridI[i][j] = -1;
            }
        }
        time_break = 500;
        time_last = 0;
        clear.clear();
        alpha = 255;
	}
    
	public void actionTouch(int action){
    	if(action == 0){ Column_Drop();         } // UP
    	if(action == 1){ Column_Fall();         } // DOWN
    	if(action == 2){ Command_Strafe(true);  } // LEFT
    	if(action == 3){ Command_Strafe(false); } // RIGHT
    	if(action == 4){ Command_Cycle(true);   } // ROTATE LEFT
    	if(action == 5){ Command_Cycle(false);  } // ROTATE RIGHT
    	if(action == 6){ Command_Hold();        } // HOLD
    }
	
	public void update(){
		timer+=15;
    	if(alpha == 255) {
            if(timer > time_last + time_break - scoreLevel * 5 && turnstate == 2) {
                Column_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 255;
                for(int y = 0; y < 15; y++) {
                    for(int x = 0; x < 6; x++) {
                        if(IsCleared(x, y)) {
                            gridI[x][y] = -1;
                        }
                    }
                }
                Command_Collapse();
            }
        }
	}
	
	
	
	//--------------------GETTER--------------------
	
    public int getValue(int index){
    	
    	if(index == 200) return tromino[0].X;
    	if(index == 201) return tromino[0].Y;
    	if(index == 202) return tromino[1].X;
    	if(index == 203) return tromino[1].Y;
    	if(index == 204) return tromino[2].X;
    	if(index == 205) return tromino[2].Y;
    	if(index == 210) return turnstate >= 4 ?  8 : container_current[0];
    	if(index == 211) return turnstate >= 4 ?  8 : container_current[1];
    	if(index == 212) return turnstate >= 4 ?  8 : container_current[2];
    	if(index == 213) return turnstate >= 4 ?  8 : container_next[0];
    	if(index == 214) return turnstate >= 4 ?  8 : container_next[1];
    	if(index == 215) return turnstate >= 4 ?  8 : container_next[2];
    	if(index == 216) return turnstate >= 4 ? -1 : container_hold[0];
    	if(index == 217) return turnstate >= 4 ? -1 : container_hold[1];
    	if(index == 218) return turnstate >= 4 ? -1 : container_hold[2];
    	
		if(index == -1) return scorePoints;
		if(index == -2) return scoreLives;
		if(index == -3) return scoreLevel;
		return inLine(index%6, index/6) && (alpha/75)%2==0 ? 7 : gridI[index % 6][index / 6];
		//return gridI[index % 10][index / 10];
	}
    
	
	
	//--------------------CUSTOM--------------------
	
    private boolean inLine(int x, int y){
    	for(Vector2 v : clear) {
    		if(v.matches(x, y)) return true;
    	}
    	return false;
    }
    
    public void Column_Drop() {
        int tempPoints = scorePoints;
        while(scorePoints == tempPoints) {
            Column_Fall();
        }
    }

    private boolean IsCleared(int x, int y) {
    	for(int i = 0; i < clear.size(); i++){
    		if(clear.get(i).X == x && clear.get(i).Y == y) return true;
    	}
        return false;
    }

    private void Command_Collapse() {
        // Gravity after match found and cleared
        int temp = 0;
        for(int y = 13; y >= 0; y--) {
            for(int x = 0; x < 6; x++) {
                if(gridI[x][y] != -1) {
                    temp = 0;
                    if(gridI[x][y + 1] == -1) {
                        temp++;
                        if(y+2 < 15 && gridI[x][y + 2] == -1) {
                            temp++;
                            if(y+3 < 15 && gridI[x][y + 3] == -1) {
                                temp++;
                            }
                        }
                    }
                    if(temp != 0) {
                        gridI[x][y + temp] = gridI[x][y];
                        gridI[x][y       ] = -1;
                    }
                }
            }
        }
        clear.clear();
        alpha = 255;
        Check_Field();
    }

    public void Command_Strafe(boolean totheleft) {
        int dir = 0;
        if(totheleft) {
            dir = -1;
            if(tromino[0].X > 0) {
                if(tromino[1].X > 0) {
                    if(tromino[2].X > 0) {
                        if(gridI[tromino[0].X + dir][tromino[0].Y] == -1) {
                            if(gridI[tromino[1].X + dir][tromino[1].Y] == -1) {
                                if(gridI[tromino[2].X + dir][tromino[2].Y] == -1) {
                                    tromino[0].X = tromino[0].X + dir;
                                    tromino[1].X = tromino[1].X + dir;
                                    tromino[2].X = tromino[2].X + dir;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            dir = 1;
            if(tromino[0].X < 5) {
                if(tromino[1].X < 5) {
                    if(tromino[2].X < 5) {
                        if(gridI[tromino[0].X + dir][tromino[0].Y] == -1) {
                            if(gridI[tromino[1].X + dir][tromino[1].Y] == -1) {
                                if(gridI[tromino[2].X + dir][tromino[2].Y] == -1) {
                                    tromino[0].X = tromino[0].X + dir;
                                    tromino[1].X = tromino[1].X + dir;
                                    tromino[2].X = tromino[2].X + dir;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void Command_Cycle(boolean totheleft) {
        if(totheleft) {
            int temp = container_current[0];
            container_current[0] = container_current[1];
            container_current[1] = container_current[2];
            container_current[2] = temp;
        } else {
            int temp = container_current[2];
            container_current[2] = container_current[1];
            container_current[1] = container_current[0];
            container_current[0] = temp;
        }

    }

    public void Command_Hold() {
    	if(active_hold) {
            active_hold = false;
            if(container_hold[0] == -1) {
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_hold[2] = container_current[2];
                container_current[0] = container_next[0];
                container_current[1] = container_next[1];
                container_current[2] = container_next[2];
                container_next[0] = Column_Roll();
                container_next[1] = Column_Roll();
                container_next[2] = Column_Roll();
            } else {
                int[] temp = new int[3];
                temp[0] = container_hold[0];
                temp[1] = container_hold[1];
                temp[2] = container_hold[2];
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_hold[2] = container_current[2];
                container_current[0] = temp[0];
                container_current[1] = temp[1];
                container_current[2] = temp[2];
            }
            Column_Create();
        }
    }

    private int Column_Roll() {
        return rand.nextInt(7);
    }

    private void Column_Create() {
        tromino[0] = new Vector2(2, 0);
        tromino[1] = new Vector2(2, 1);
        tromino[2] = new Vector2(2, 2);
    }

    public void Column_Fall() {
        if(tromino[0].Y < 14 && tromino[1].Y < 14 && tromino[2].Y < 14) {
            if(gridI[tromino[0].X][tromino[0].Y + 1] == -1 && gridI[tromino[1].X][tromino[1].Y + 1] == -1 && gridI[tromino[2].X][tromino[2].Y + 1] == -1) {
                tromino[0].Y = tromino[0].Y + 1;
                tromino[1].Y = tromino[1].Y + 1;
                tromino[2].Y = tromino[2].Y + 1;
            } else {
                Column_Place();
            }
        } else {
            Column_Place();
        }
    }

    private void Column_Place() {
        active_hold = true;
        gridI[tromino[0].X][tromino[0].Y] = container_current[0];
        gridI[tromino[1].X][tromino[1].Y] = container_current[1];
        gridI[tromino[2].X][tromino[2].Y] = container_current[2];
        scorePoints = scorePoints + 2 * 2;
        if(tromino[0].Y == 0) turnstate = 4;
        container_current[0] = container_next[0];
        container_current[1] = container_next[1];
        container_current[2] = container_next[2];
        container_next[0] = Column_Roll();
        container_next[1] = Column_Roll();
        container_next[2] = Column_Roll();
        Column_Create();
        Check_Field();
    }

    private void Check_Field() {

        int points = 0;
        int bonus = 0;

        for(int y = 14; y >= 0; y--) {
            for(int x = 0; x < 6; x++) {
                if(x < 4 && gridI[x][y] != -1 && gridI[x][y] == gridI[x + 1][y] && gridI[x][y] == gridI[x + 2][y]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y)); clear.add(new Vector2(x + 2, y));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(y > 1 && gridI[x][y] != -1 && gridI[x][y] == gridI[x][y - 1] && gridI[x][y] == gridI[x][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x, y - 1)); clear.add(new Vector2(x, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(x < 4 && y > 1 && gridI[x][y] != -1 && gridI[x][y] == gridI[x + 1][y - 1] && gridI[x][y] == gridI[x + 2][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y - 1)); clear.add(new Vector2(x + 2, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(x < 4 && y < 13 && gridI[x][y] != -1 && gridI[x][y] == gridI[x + 1][y + 1] && gridI[x][y] == gridI[x + 2][y + 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y + 1)); clear.add(new Vector2(x + 2, y + 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
            }
        }

        if(points > 0) {
            alpha -= 5;
            scorePoints += (points * 2 * (scoreLevel + 1));
            scoreLives++;
            if(scoreLives > (1 + scoreLevel) * 10) {
                scoreLevel++;
                time_break -= (time_break / 10);
            }
            //Command_Collapse();
        }
    }
	
}
