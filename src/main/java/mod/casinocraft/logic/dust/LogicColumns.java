package mod.casinocraft.logic.dust;

import mod.casinocraft.logic.LogicBase;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class LogicColumns extends LogicBase {

    public boolean active_hold;

    public int[] container_next    = new int[3];
    public int[] container_hold    = new int[3];
    public int[] container_current = new int[3];

    public double time_last;
    public double time_break;
    public int timer;

    public Vector2[] tromino = new Vector2[]{new Vector2(-1, -1), new Vector2(-1, -1), new Vector2(-1, -1)};

    public List<Vector2> clear = new ArrayList<Vector2>();

    public int alpha;



    //--------------------CONSTRUCTOR--------------------

    public LogicColumns(){
        super(true, 0, "a_columns", 6, 15);
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
                grid[i][j] = -1;
            }
        }
        time_break = 200;
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

    public void updateMotion(){

    }

    public void updateLogic(){
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
                            grid[x][y] = -1;
                        }
                    }
                }
                Command_Collapse();
            }
        }
    }

    public void load2(CompoundNBT compound){
        active_hold = compound.getBoolean("activehold");
        container_next[0] = compound.getInt("containernext0");
        container_next[1] = compound.getInt("containernext1");
        container_next[2] = compound.getInt("containernext2");

        container_hold[0] = compound.getInt("containerhold0");
        container_hold[1] = compound.getInt("containerhold1");
        container_hold[2] = compound.getInt("containerhold2");

        container_current[0] = compound.getInt("containercurrent0");
        container_current[1] = compound.getInt("containercurrent1");
        container_current[2] = compound.getInt("containercurrent2");

        time_last = compound.getDouble("timelast");
        time_break = compound.getDouble("timebreak");
        timer = compound.getInt("timer");

        tromino[0].set(compound.getInt("tromino0x"), compound.getInt("tromino0y"));
        tromino[1].set(compound.getInt("tromino1x"), compound.getInt("tromino1y"));
        tromino[2].set(compound.getInt("tromino2x"), compound.getInt("tromino2y"));

        alpha = compound.getInt("alpha");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putBoolean("activehold", active_hold);
        compound.putInt("container_next0", container_next[0]);
        compound.putInt("container_next1", container_next[1]);
        compound.putInt("container_next2", container_next[2]);

        compound.putInt("container_hold0", container_hold[0]);
        compound.putInt("container_hold1", container_hold[1]);
        compound.putInt("container_hold2", container_hold[2]);

        compound.putInt("container_current0", container_current[0]);
        compound.putInt("container_current1", container_current[1]);
        compound.putInt("container_current2", container_current[2]);

        compound.putDouble("timelast", time_last);
        compound.putDouble("timebreak", time_break);
        compound.putInt("timer", timer);
        compound.putInt("tromino0x", tromino[0].X);
        compound.putInt("tromino0y", tromino[0].Y);
        compound.putInt("tromino1x", tromino[1].X);
        compound.putInt("tromino1y", tromino[1].Y);
        compound.putInt("tromino2x", tromino[2].X);
        compound.putInt("tromino2y", tromino[2].Y);
        compound.putInt("alpha", alpha);
        return compound;
    }



    //--------------------CUSTOM--------------------

    private boolean inLine(int x, int y){
        for(Vector2 v : clear) {
            if(v.matches(x, y)) return true;
        }
        return false;
    }

    public void Column_Drop() {
        int tempPoint = scorePoint;
        while(scorePoint == tempPoint) {
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
                if(grid[x][y] != -1) {
                    temp = 0;
                    if(grid[x][y + 1] == -1) {
                        temp++;
                        if(y+2 < 15 && grid[x][y + 2] == -1) {
                            temp++;
                            if(y+3 < 15 && grid[x][y + 3] == -1) {
                                temp++;
                            }
                        }
                    }
                    if(temp != 0) {
                        grid[x][y + temp] = grid[x][y];
                        grid[x][y       ] = -1;
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
                        if(grid[tromino[0].X + dir][tromino[0].Y] == -1) {
                            if(grid[tromino[1].X + dir][tromino[1].Y] == -1) {
                                if(grid[tromino[2].X + dir][tromino[2].Y] == -1) {
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
                        if(grid[tromino[0].X + dir][tromino[0].Y] == -1) {
                            if(grid[tromino[1].X + dir][tromino[1].Y] == -1) {
                                if(grid[tromino[2].X + dir][tromino[2].Y] == -1) {
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
        return RANDOM.nextInt(7);
    }

    private void Column_Create() {
        tromino[0] = new Vector2(2, 0);
        tromino[1] = new Vector2(2, 1);
        tromino[2] = new Vector2(2, 2);
    }

    public void Column_Fall() {
        if(tromino[0].Y < 14 && tromino[1].Y < 14 && tromino[2].Y < 14) {
            if(grid[tromino[0].X][tromino[0].Y + 1] == -1 && grid[tromino[1].X][tromino[1].Y + 1] == -1 && grid[tromino[2].X][tromino[2].Y + 1] == -1) {
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
        grid[tromino[0].X][tromino[0].Y] = container_current[0];
        grid[tromino[1].X][tromino[1].Y] = container_current[1];
        grid[tromino[2].X][tromino[2].Y] = container_current[2];
        scorePoint = scorePoint + 2 * 2;
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
                if(x < 4 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y] && grid[x][y] == grid[x + 2][y]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y)); clear.add(new Vector2(x + 2, y));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(y > 1 && grid[x][y] != -1 && grid[x][y] == grid[x][y - 1] && grid[x][y] == grid[x][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x, y - 1)); clear.add(new Vector2(x, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(x < 4 && y > 1 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y - 1] && grid[x][y] == grid[x + 2][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y - 1)); clear.add(new Vector2(x + 2, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(x < 4 && y < 13 && grid[x][y] != -1 && grid[x][y] == grid[x + 1][y + 1] && grid[x][y] == grid[x + 2][y + 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y + 1)); clear.add(new Vector2(x + 2, y + 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
            }
        }

        if(points > 0) {
            alpha -= 5;
            scorePoint += (points * 2 * (scoreLevel + 1));
            scoreLives++;
            if(scoreLives > (1 + scoreLevel) * 10) {
                scoreLevel++;
                time_break -= (time_break / 10);
            }
            //Command_Collapse();
        }
    }

}
