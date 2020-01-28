package mod.casinocraft.tileentities.minigames;

import mod.shared.util.Vector2;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import net.minecraft.util.math.BlockPos;

public class TileEntityTetris extends TileEntityCasino {
	
	public boolean active_hold;
    public int container_next;
    public int container_hold;
    public int container_current;
    public double time_last;
    public double time_break;
    public int timer;
    public int[]     tetro     = new int[7]; // Color of single Tetromino
    public Vector2[] tetromino = new Vector2[4];
    public int line1;
    public int line2;
    public int line3;
    public int line4;
    public int alpha;
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntityTetris(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new int[10][20];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		active_hold = true;
        container_next = -1;
        container_hold = -1;
        container_current = -1;
        container_current = Tetromino_Roll();
        container_next = Tetromino_Roll();
        Tetromino_Create();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 20; j++) {
                gridI[i][j] = -1;
            }
        }
        time_break = 500;
        time_last = 0;
        line1 = -1;
        line1 = -1;
        line1 = -1;
        line1 = -1;
        alpha = 250;
        timer = 0;
        Colorize();
	}
    
    public void actionTouch(int action){
    	if(action == 0){ Tetromino_Drop();      } // UP
    	if(action == 1){ Tetromino_Fall();      } // DOWN
    	if(action == 2){ Command_Strafe(true);  } // LEFT
    	if(action == 3){ Command_Strafe(false); } // RIGHT
    	if(action == 4){ Command_Turn(true);    } // ROTATE LEFT
    	if(action == 5){ Command_Turn(false);   } // ROTATE RIGHT
    	if(action == 6){ Command_Hold();        } // HOLD
    }
    
    public void update(){
		timer+=15;
    	if(alpha == 255) {
            if(timer > time_last + time_break - scoreLevel * 5 && turnstate == 2) {
                Tetromino_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 250;
                Command_Collapse();
            }
        }
	}
	
    
    
  //--------------------GETTER--------------------
    
    public int getValue(int index){
    	
    	if(index == 200) return tetromino[0].X;
    	if(index == 201) return tetromino[0].Y;
    	if(index == 202) return tetromino[1].X;
    	if(index == 203) return tetromino[1].Y;
    	if(index == 204) return tetromino[2].X;
    	if(index == 205) return tetromino[2].Y;
    	if(index == 206) return tetromino[3].X;
    	if(index == 207) return tetromino[3].Y;
    	if(index == 210) return container_current;
    	if(index == 211) return container_next;
    	if(index == 212) return container_hold;
    	
		return inLine(index/10) && (alpha/75)%2==0 ? 7 : gridI[index % 10][index / 10];
	}
    
    
    
  //--------------------CUSTOM--------------------
    
    private boolean inLine(int index){
    	if(index == line1) return true;
    	if(index == line2) return true;
    	if(index == line3) return true;
        return index == line4;
    }
    
    private void Colorize() {
        boolean[] used = new boolean[10];
        int index = 0;
        for(int i = 0; i < 10; i++) {
            used[i] = false;
            index++;
        }

        tetro[0] = -1;
        tetro[1] = -1;
        tetro[2] = -1;
        tetro[3] = -1;
        tetro[4] = -1;
        tetro[5] = -1;
        tetro[6] = -1;

        int count = 0;
        while(count < index && count < 7) {
            int r = rand.nextInt(7);
            if(!used[r]) {
                tetro[count] = r;
                used[r] = true;
                count++;
            }
        }
    }
    
    private void Command_Collapse() {
        if(line4 != -1) {
            for(int i = line4; i > 0; i--) {
            	for(int x = 0; x < 10; x++){
            		gridI[x][i] = gridI[x][i - 1];
            	}
            }
        }
        if(line3 != -1) {
            for(int i = line3; i > 0; i--) {
            	for(int x = 0; x < 10; x++){
            		gridI[x][i] = gridI[x][i - 1];
            	}
            }
        }
        if(line2 != -1) {
            for(int i = line2; i > 0; i--) {
            	for(int x = 0; x < 10; x++){
            		gridI[x][i] = gridI[x][i - 1];
            	}
            }
        }
        if(line1 != -1) {
            for(int i = line1; i > 0; i--) {
            	for(int x = 0; x < 10; x++){
            		gridI[x][i] = gridI[x][i - 1];
            	}
            }
        }
        line1 = -1;
        line2 = -1;
        line3 = -1;
        line4 = -1;
        alpha = 255;
    }

    public void Command_Strafe(boolean totheleft) {
        int dir = 0;
        if(totheleft) {
            dir = -1;
            if(tetromino[0].X > 0) {
                if(tetromino[1].X > 0) {
                    if(tetromino[2].X > 0) {
                        if(tetromino[3].X > 0) {
                            if(gridI[tetromino[0].X + dir][tetromino[0].Y] == -1) {
                                if(gridI[tetromino[1].X + dir][tetromino[1].Y] == -1) {
                                    if(gridI[tetromino[2].X + dir][tetromino[2].Y] == -1) {
                                        if(gridI[tetromino[3].X + dir][tetromino[3].Y] == -1) {
                                            tetromino[0].X = tetromino[0].X + dir;
                                            tetromino[1].X = tetromino[1].X + dir;
                                            tetromino[2].X = tetromino[2].X + dir;
                                            tetromino[3].X = tetromino[3].X + dir;
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
            if(tetromino[0].X < 9) {
                if(tetromino[1].X < 9) {
                    if(tetromino[2].X < 9) {
                        if(tetromino[3].X < 9) {
                            if(gridI[tetromino[0].X + dir][tetromino[0].Y] == -1) {
                                if(gridI[tetromino[1].X + dir][tetromino[1].Y] == -1) {
                                    if(gridI[tetromino[2].X + dir][tetromino[2].Y] == -1) {
                                        if(gridI[tetromino[3].X + dir][tetromino[3].Y] == -1) {
                                            tetromino[0].X = tetromino[0].X + dir;
                                            tetromino[1].X = tetromino[1].X + dir;
                                            tetromino[2].X = tetromino[2].X + dir;
                                            tetromino[3].X = tetromino[3].X + dir;
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

    public void Command_Turn(boolean totheleft) {
        Vector2 tempV1 = new Vector2(tetromino[0].X - tetromino[1].X, tetromino[0].Y - tetromino[1].Y);
        Vector2 tempV2 = new Vector2(tetromino[0].X - tetromino[2].X, tetromino[0].Y - tetromino[2].Y);
        Vector2 tempV3 = new Vector2(tetromino[0].X - tetromino[3].X, tetromino[0].Y - tetromino[3].Y);
        if(totheleft) {
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
        } else {
            if(tempV1.X == 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y           ); } else
            if(tempV1.X == 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y           ); } else
            if(tempV1.X  < 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); }

            if(tempV2.X == 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y           ); } else
            if(tempV2.X == 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y           ); } else
            if(tempV2.X  < 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); }

            if(tempV3.X == 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y           ); } else
            if(tempV3.X == 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y           ); } else
            if(tempV3.X  < 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); }
        }
        if(tempV1.X > -1 && tempV1.X < 10 && tempV1.Y > -1 && tempV1.Y < 20) {
            if(tempV2.X > -1 && tempV2.X < 10 && tempV2.Y > -1 && tempV2.Y < 20) {
                if(tempV3.X > -1 && tempV3.X < 10 && tempV3.Y > -1 && tempV3.Y < 20) {
                    if(gridI[tempV1.X][tempV1.Y] == -1) {
                        if(gridI[tempV2.X][tempV2.Y] == -1) {
                            if(gridI[tempV3.X][tempV3.Y] == -1) {
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

    public void Command_Hold() {
    	if(active_hold) {
            active_hold = false;
            if(container_hold == -1) {
                container_hold = container_current;
                container_current = container_next;
                container_next = Tetromino_Roll();
            } else {
                int temp;
                temp = container_hold;
                container_hold = container_current;
                container_current = temp;
            }
            Tetromino_Create();
        }
    }

    private int Tetromino_Roll() {
        return rand.nextInt(7);
    }

    private void Tetromino_Create() {
        if(container_current == 0) { // I
            tetromino[0] = new Vector2(4, 1); // OOXO
            tetromino[1] = new Vector2(4, 0); // OOXO
            tetromino[2] = new Vector2(4, 2); // OOXO
            tetromino[3] = new Vector2(4, 3); // OOXO
        }
        if(container_current == 1) { // O
            tetromino[0] = new Vector2(4, 0); // OOOO
            tetromino[1] = new Vector2(4, 1); // OXXO
            tetromino[2] = new Vector2(5, 0); // OXXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
        if(container_current == 2) { // S
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(6, 0); // OOXX
            tetromino[2] = new Vector2(4, 1); // OXXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
        if(container_current == 3) { // Z
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(4, 0); // XXOO
            tetromino[2] = new Vector2(5, 1); // OXXO
            tetromino[3] = new Vector2(6, 1); // OOOO
        }
        if(container_current == 4) { // L
            tetromino[0] = new Vector2(4, 2); // OXOO
            tetromino[1] = new Vector2(4, 0); // OXOO
            tetromino[2] = new Vector2(4, 1); // OXXO
            tetromino[3] = new Vector2(5, 2); // OOOO
        }
        if(container_current == 5) { // J
            tetromino[0] = new Vector2(5, 2); // OOXO
            tetromino[1] = new Vector2(5, 0); // OOXO
            tetromino[2] = new Vector2(5, 1); // OXXO
            tetromino[3] = new Vector2(4, 2); // OOOO
        }
        if(container_current == 6) { // T
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(4, 0); // OXXX
            tetromino[2] = new Vector2(6, 0); // OOXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
    }
    
    private void Tetromino_Drop() {
        int tempPoints = scorePoints;
        while(scorePoints == tempPoints) {
            Tetromino_Fall();
        }
    }
    
    /*public void Tetromino_Drop() {
    	boolean drop = false;
    	while(!drop){
    		if(tetromino[0].Y < 19 && tetromino[1].Y < 19 && tetromino[2].Y < 19 && tetromino[3].Y < 19) {
                if(gridI[tetromino[0].X][tetromino[0].Y + 1] == -1 && gridI[tetromino[1].X][tetromino[1].Y + 1] == -1 && gridI[tetromino[2].X][tetromino[2].Y + 1] == -1 && gridI[tetromino[3].X][tetromino[3].Y + 1] == -1) {
                    tetromino[0].Y = tetromino[0].Y + 1;
                    tetromino[1].Y = tetromino[1].Y + 1;
                    tetromino[2].Y = tetromino[2].Y + 1;
                    tetromino[3].Y = tetromino[3].Y + 1;
                } else {
                    drop = true;
                }
            } else {
                drop = true;
            }
    	}
    	Tetromino_Place();
    }*/
    
    public void Tetromino_Fall() {
        if(tetromino[0].Y < 19 && tetromino[1].Y < 19 && tetromino[2].Y < 19 && tetromino[3].Y < 19) {
            if(gridI[tetromino[0].X][tetromino[0].Y + 1] == -1 && gridI[tetromino[1].X][tetromino[1].Y + 1] == -1 && gridI[tetromino[2].X][tetromino[2].Y + 1] == -1 && gridI[tetromino[3].X][tetromino[3].Y + 1] == -1) {
                tetromino[0].Y = tetromino[0].Y + 1;
                tetromino[1].Y = tetromino[1].Y + 1;
                tetromino[2].Y = tetromino[2].Y + 1;
                tetromino[3].Y = tetromino[3].Y + 1;
            } else {
                Tetromino_Place();
            }
        } else {
            Tetromino_Place();
        }
    }

    private void Tetromino_Place() {
        active_hold = true;
        gridI[tetromino[0].X][tetromino[0].Y] = container_current;
        gridI[tetromino[1].X][tetromino[1].Y] = container_current;
        gridI[tetromino[2].X][tetromino[2].Y] = container_current;
        gridI[tetromino[3].X][tetromino[3].Y] = container_current;
        scorePoints = scorePoints + 2;
        if(tetromino[0].Y == 0) turnstate = 4;
        container_current = container_next;
        int container_temp = container_next;
        if((container_next = Tetromino_Roll()) == container_temp) {
            if(rand.nextInt(2) == 0) {
                container_next = Tetromino_Roll();
            }
        }
        Tetromino_Create();
        line1 = -1;
        line2 = -1;
        line3 = -1;
        line4 = -1;
        for(int i = 19; i > -1; i--) {
            if(gridI[0][i] != -1 && gridI[1][i] != -1 && gridI[2][i] != -1 && gridI[3][i] != -1 && gridI[4][i] != -1 && gridI[5][i] != -1 && gridI[6][i] != -1 && gridI[7][i] != -1 && gridI[8][i] != -1 && gridI[9][i] != -1) {
                scoreLives++;
                scorePoints += 50;
                if(line1 == -1) {
                    line1 = i;
                } else if(line2 == -1) {
                    line2 = i;
                } else if(line3 == -1) {
                    line3 = i;
                } else if(line4 == -1) {
                    line4 = i;
                }
                alpha -= 5;
            }
        }
        if(scoreLives + 1 > (1 + scoreLevel) * 10) {
            scoreLevel++;
            time_break -= (time_break / 10);
        }
        if(line1 != -1 && line2 != -1 && line3 != -1 && line4 != -1) {
            scorePoints = scorePoints + 250 * (scoreLevel + 1);
        }
    }
	
}
