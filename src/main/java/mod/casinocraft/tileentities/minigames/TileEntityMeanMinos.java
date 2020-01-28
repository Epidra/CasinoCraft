package mod.casinocraft.tileentities.minigames;

import java.util.ArrayList;
import java.util.List;

import mod.shared.util.Vector2;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import net.minecraft.util.math.BlockPos;

public class TileEntityMeanMinos extends TileEntityCasino {
	
	public boolean active_hold;

    public int[] container_next    = new int[2];
    public int[] container_hold    = new int[2];
    public int[] container_current = new int[2];

    public double time_last;
    public double time_break;
    public int timer;

    public Vector2[] domino = new Vector2[2];

    public List<Vector2> clear = new ArrayList<Vector2>();

    public int alpha;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityMeanMinos(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new int[6][15];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		active_hold = true;
        container_next[0] = Domino_Roll();
        container_next[1] = Domino_Roll();
        container_hold[0] = -1;
        container_hold[1] = -1;
        container_current[0] = Domino_Roll();
        container_current[1] = Domino_Roll();
        Domino_Create();
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
    	if(action == 0){ Domino_Drop();         } // UP
    	if(action == 1){ Domino_Fall();         } // DOWN
    	if(action == 2){ Command_Strafe(true);  } // LEFT
    	if(action == 3){ Command_Strafe(false); } // RIGHT
    	if(action == 4){ Command_Turn(true);    } // ROTATE LEFT
    	if(action == 5){ Command_Turn(false);   } // ROTATE RIGHT
    	if(action == 6){ Command_Hold();        } // HOLD
    }
	
	public void update(){
		timer+=15;
    	if(alpha == 255) {
            if(timer > time_last + time_break && turnstate == 2) {
                Domino_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 255;
                for(int y = 0; y < 15; y++) {
                    for(int x = 0; x < 10; x++) {
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
    	
    	if(index == 200) return domino[0].X;
    	if(index == 201) return domino[0].Y;
    	if(index == 202) return domino[1].X;
    	if(index == 203) return domino[1].Y;
    	if(index == 210) return turnstate >= 4 ?  8 : container_current[0];
    	if(index == 211) return turnstate >= 4 ?  8 : container_current[1];
    	if(index == 212) return turnstate >= 4 ?  8 : container_next[0];
    	if(index == 213) return turnstate >= 4 ?  8 : container_next[1];
    	if(index == 214) return turnstate >= 4 ? -1 : container_hold[0];
    	if(index == 215) return turnstate >= 4 ? -1 : container_hold[1];
    	
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
    
    private void Domino_Drop() {
        int tempPoints = scorePoints;
        while(scorePoints == tempPoints) {
            Domino_Fall();
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
                    while(y + temp + 1 < 15 && gridI[x][y + temp + 1] == -1) {
                        temp++;
                    }
                    if(temp != 0) {
                        gridI[x][y + temp] = gridI[x][y];
                        gridI[x][y] = -1;
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
            if(domino[0].X > 0) {
                if(domino[1].X > 0) {
                    if(gridI[domino[0].X + dir][domino[0].Y] == -1) {
                        if(gridI[domino[1].X + dir][domino[1].Y] == -1) {
                            domino[0].X = domino[0].X + dir;
                            domino[1].X = domino[1].X + dir;
                        }
                    }
                }
            }
        } else {
            dir = 1;
            if(domino[0].X < 5) {
                if(domino[1].X < 5) {
                    if(gridI[domino[0].X + dir][domino[0].Y] == -1) {
                        if(gridI[domino[1].X + dir][domino[1].Y] == -1) {
                            domino[0].X = domino[0].X + dir;
                            domino[1].X = domino[1].X + dir;
                        }
                    }
                }
            }
        }
    }

    public void Command_Turn(boolean totheleft) {

        int pos = 0; // Position of the rotatable Mino
        if(domino[0].Y > domino[1].Y) pos = 1; // Up
        if(domino[0].X > domino[1].X) pos = 2; // Left
        if(domino[0].Y < domino[1].Y) pos = 3; // Down
        if(domino[0].X < domino[1].X) pos = 4; // Right
        
        

        if(totheleft) {
            if(pos == 1) { if(domino[0].X - 1 >=  0 && gridI[domino[0].X - 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X - 1, domino[0].Y    ); } }
            if(pos == 2) { if(domino[0].Y + 1 <= 14 && gridI[domino[0].X][domino[0].Y + 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y + 1); } }
            if(pos == 3) { if(domino[0].X + 1 <=  5 && gridI[domino[0].X + 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X + 1, domino[0].Y    ); } }
            if(pos == 4) { if(domino[0].Y - 1 >=  0 && gridI[domino[0].X][domino[0].Y - 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y - 1); } }
        } else {
            if(pos == 1) { if(domino[0].X + 1 <=  5 && gridI[domino[0].X + 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X + 1, domino[0].Y    ); } }
            if(pos == 2) { if(domino[0].Y - 1 >=  0 && gridI[domino[0].X][domino[0].Y - 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y - 1); } }
            if(pos == 3) { if(domino[0].X - 1 >=  0 && gridI[domino[0].X - 1][domino[0].Y] == -1) { domino[1] = new Vector2(domino[0].X - 1, domino[0].Y    ); } }
            if(pos == 4) { if(domino[0].Y + 1 <= 14 && gridI[domino[0].X][domino[0].Y + 1] == -1) { domino[1] = new Vector2(domino[0].X    , domino[0].Y + 1); } }
        }

    }

    public void Command_Hold() {
    	if(active_hold) {
            active_hold = false;
            if(container_hold[0] == -1) {
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_current[0] = container_next[0];
                container_current[1] = container_next[1];
                container_next[0] = Domino_Roll();
                container_next[1] = Domino_Roll();
            } else {
                int[] temp = new int[2];
                temp[0] = container_hold[0];
                temp[1] = container_hold[1];
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_current[0] = temp[0];
                container_current[1] = temp[1];
            }
            Domino_Create();
        }
    }

    private int Domino_Roll() {
        return rand.nextInt(7);
    }

    private void Domino_Create() {
        domino[0] = new Vector2(2, 1);
        domino[1] = new Vector2(2, 0);
    }

    public void Domino_Fall() {
        if(domino[0].Y < 14 && domino[1].Y < 14) {
            if(gridI[domino[0].X][domino[0].Y + 1] == -1 && gridI[domino[1].X][domino[1].Y + 1] == -1) {
                domino[0].Y = domino[0].Y + 1;
                domino[1].Y = domino[1].Y + 1;
            } else {
                Domino_Place();
            }
        } else {
            Domino_Place();
        }
    }

    private void Domino_Place() {
        active_hold = true;
        gridI[domino[0].X][domino[0].Y] = container_current[0];
        gridI[domino[1].X][domino[1].Y] = container_current[1];
        scorePoints += 2 * 2;
        if(domino[1].Y == 0) turnstate = 4;
        container_current[0] = container_next[0];
        container_current[1] = container_next[1];
        container_next[0] = Domino_Roll();
        container_next[1] = Domino_Roll();
        Domino_Create();
        Check_Field();
    }

    List<Vector2> clear_temp = new ArrayList<Vector2>();

    private void Check_Field() {
        int points = 0;
        int bonus = 0;
        clear_temp.clear();
        for(int y = 14; y >= 0; y--) {
            for(int x = 0; x < 6; x++) {
                if(!IsCleared(x, y)) {
                    Pathfinder(x, y);
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
            scorePoints += (points * 2 * bonus * (scoreLevel + 1));
            scoreLives++;
            if(scoreLives > (1 + scoreLevel) * 10) {
                scoreLevel++;
                time_break -= (time_break / 10);
            }
            //Command_Collapse();
        }
    }

    private void Pathfinder(int x, int y) {
        clear_temp.add(new Vector2(x, y));
        if(y - 1 >=  0 && gridI[x][y] != -1 && gridI[x][y] == gridI[x    ][y - 1] && !IsClearedTemp(x    , y - 1)) Pathfinder(x    , y - 1);
        if(y + 1 <= 14 && gridI[x][y] != -1 && gridI[x][y] == gridI[x    ][y + 1] && !IsClearedTemp(x    , y + 1)) Pathfinder(x    , y + 1);
        if(x - 1 >=  0 && gridI[x][y] != -1 && gridI[x][y] == gridI[x - 1][y    ] && !IsClearedTemp(x - 1, y    )) Pathfinder(x - 1, y    );
        if(x + 1 <=  5 && gridI[x][y] != -1 && gridI[x][y] == gridI[x + 1][y    ] && !IsClearedTemp(x + 1, y    )) Pathfinder(x + 1, y    );
    }

    private boolean IsClearedTemp(int x, int y) {
    	for(int i = 0; i < clear_temp.size(); i++){
    		if(clear_temp.get(i).X == x && clear_temp.get(i).Y == y) return true;
    	}
        return false;
    }
	
}
