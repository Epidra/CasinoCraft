package mod.casinocraft.logic.dust;

import mod.casinocraft.logic.LogicBase;
import mod.shared.util.Vector2;

public class LogicTetris extends LogicBase {

    public int[][] grid = new int[10][20];

    public boolean canHold = false;

    public int container_next = 0;
    public int container_hold = 0;
    public int container_now = 0;
    public int timer_last = 0;
    public int timer_break = 0;
    public int timer = 0;
    public int[] color = new int[7]; // Color of single Tetromino
    public int[] lines = new int[4];
    public int alpha = 0;

    public Vector2[] tetromino = new Vector2[4]; // Position of the moving tetromino on the grid



    //--------------------CONSTRUCTOR--------------------

    public LogicTetris(){
        super(true, "tetris");
    }



    //--------------------BASIC--------------------

    public void start2(){
        canHold = true;
        container_next = Tetromino_Roll();
        container_hold = -1;
        container_now = Tetromino_Roll();
        Tetromino_Create();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 20; j++) {
                grid[i][j] = -1;
            }
        }
        timer_break = 500;
        timer_last = 0;
        lines[0] = -1;
        lines[1] = -1;
        lines[2] = -1;
        lines[3] = -1;
        alpha = 250;
        timer = 0;
        Colorize();
    }

    public void actionTouch(int action){
        if(action == COMMAND_UP){ Tetromino_Drop();      } // UP
        if(action == COMMAND_DOWN){ Tetromino_Fall();      } // DOWN
        if(action == COMMAND_LEFT){ Command_Strafe(true);  } // LEFT
        if(action == COMMAND_RIGHT){ Command_Strafe(false); } // RIGHT
        if(action == COMMAND_ROTLEFT){ Command_Turn(true);    } // ROTATE LEFT
        if(action == COMMAND_ROTRIGHT){ Command_Turn(false);   } // ROTATE RIGHT
        if(action == COMMAND_CONTROL){ Command_Hold();        } // HOLD
    }

    public void updateMotion() {
        timer+=15;
    }

    public void updateLogic(){
        if(alpha == 255) {
            if(timer > timer_last + timer_break - scoreLevel * 5 && turnstate == 2) {
                Tetromino_Fall();
                timer_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 250;
                Command_Collapse();
            }
        }
    }



    //--------------------CUSTOM--------------------

    public boolean inLine(int index){
        if(index     == lines[0]) return true;
        if(index     == lines[1]) return true;
        if(index     == lines[2]) return true;
        return index == lines[3];
    }

    private void Colorize() {
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
            int r = RANDOM.nextInt(7);
            if(!used[r]) {
                color[count] = r;
                used[r] = true;
                count++;
            }
        }
    }

    private void Command_Collapse() {
        for(int z = 3; z >= 0; z--){
            if(lines[z] != -1) {
                for(int i = lines[z]; i > 0; i--) {
                    for(int x = 0; x < 10; x++){
                        grid[x][i] = grid[x][i-1];
                    }
                }
            }
            lines[0] = -1;
        }
        alpha = 255;
    }

    private void Command_Strafe(boolean totheleft) {
        int dir = 0;
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
            if(tetromino[0].X < 9) {
                if(tetromino[1].X < 9) {
                    if(tetromino[2].X < 9) {
                        if(tetromino[3].X < 9) {
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

    private void Command_Turn(boolean totheleft) {
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

    private void Command_Hold() {
        if(canHold) {
            canHold = false;
            if(container_hold == -1) {
                container_hold = container_now;
                container_now = container_next;
                container_next = Tetromino_Roll();
            } else {
                int temp;
                temp = container_hold;
                container_hold = container_now;
                container_now = temp;
            }
            Tetromino_Create();
        }
    }

    private int Tetromino_Roll() {
        return RANDOM.nextInt(7);
    }

    private void Tetromino_Create() {
        if(container_now == 0) { // I
            tetromino[0].set(4, 1); // OOXO
            tetromino[1].set(4, 0); // OOXO
            tetromino[2].set(4, 2); // OOXO
            tetromino[3].set(4, 3); // OOXO
        }
        if(container_now == 1) { // O
            tetromino[0].set(4, 0); // OOOO
            tetromino[1].set(4, 1); // OXXO
            tetromino[2].set(5, 0); // OXXO
            tetromino[3].set(5, 1); // OOOO
        }
        if(container_now == 2) { // S
            tetromino[0].set(5, 0); // OOOO
            tetromino[1].set(6, 0); // OOXX
            tetromino[2].set(4, 1); // OXXO
            tetromino[3].set(5, 1); // OOOO
        }
        if(container_now == 3) { // Z
            tetromino[0].set(5, 0); // OOOO
            tetromino[1].set(4, 0); // XXOO
            tetromino[2].set(5, 1); // OXXO
            tetromino[3].set(6, 1); // OOOO
        }
        if(container_now == 4) { // L
            tetromino[0].set(4, 2); // OXOO
            tetromino[1].set(4, 0); // OXOO
            tetromino[2].set(4, 1); // OXXO
            tetromino[3].set(5, 2); // OOOO
        }
        if(container_now == 5) { // J
            tetromino[0].set(5, 2); // OOXO
            tetromino[1].set(5, 0); // OOXO
            tetromino[2].set(5, 1); // OXXO
            tetromino[3].set(4, 2); // OOOO
        }
        if(container_now == 6) { // T
            tetromino[0].set(5, 0); // OOOO
            tetromino[1].set(4, 0); // OXXX
            tetromino[2].set(6, 0); // OOXO
            tetromino[3].set(5, 1); // OOOO
        }
    }

    private void Tetromino_Drop() {
        int tempPoints = scorePoint;
        while(scorePoint == tempPoints) {
            Tetromino_Fall();
        }
    }

    private void Tetromino_Fall() {
        if(tetromino[0].Y < 19 && tetromino[1].Y < 19 && tetromino[2].Y < 19 && tetromino[3].Y < 19) {
            if(grid[tetromino[0].X][tetromino[0].Y + 1] == -1 && grid[tetromino[1].X][tetromino[1].Y + 1] == -1 && grid[tetromino[2].X][tetromino[2].Y + 1] == -1 && grid[tetromino[3].X][tetromino[3].Y + 1] == -1) {
                tetromino[0].add(0, 1);
                tetromino[1].add(0, 1);
                tetromino[2].add(0, 1);
                tetromino[3].add(0, 1);
            } else {
                Tetromino_Place();
            }
        } else {
            Tetromino_Place();
        }
    }

    private void Tetromino_Place() {
        canHold = true;
        grid[tetromino[0].X][tetromino[0].Y] = container_now;
        grid[tetromino[1].X][tetromino[1].Y] = container_now;
        grid[tetromino[2].X][tetromino[2].Y] = container_now;
        grid[tetromino[3].X][tetromino[3].Y] = container_now;
        scorePoint += 2;
        if(tetromino[0].Y == 0) turnstate = 4;
        container_now = container_next;
        int container_temp = container_next;
        container_next = Tetromino_Roll();
        if(container_next == container_temp) {
            if(RANDOM.nextInt(2) == 0) {
                container_next = Tetromino_Roll();
            }
        }
        Tetromino_Create();
        lines[0] = -1;
        lines[1] = -1;
        lines[2] = -1;
        lines[3] = -1;
        for(int i = 19; i > -1; i--) {
            if(grid[0][i] != -1 && grid[1][i] != -1 && grid[2][i] != -1 && grid[3][i] != -1 && grid[4][i] != -1 && grid[5][i] != -1 && grid[6][i] != -1 && grid[7][i] != -1 && grid[8][i] != -1 && grid[9][i] != -1) {
                scoreLives++;
                scorePoint += 50;
                     if(lines[3] == -1) { lines[3] = i; }
                else if(lines[2] == -1) { lines[2] = i; }
                else if(lines[1] == -1) { lines[1] = i; }
                else if(lines[0] == -1) { lines[0] = i; }
                alpha -= 5;
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

}
