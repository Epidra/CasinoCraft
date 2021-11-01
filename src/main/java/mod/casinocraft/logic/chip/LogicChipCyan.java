package mod.casinocraft.logic.chip;

import mod.casinocraft.logic.LogicModule;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundTag;
import java.util.ArrayList;
import java.util.List;
import static mod.casinocraft.util.KeyMap.*;
import static mod.casinocraft.util.SoundMap.SOUND_IMPACT;
import static mod.casinocraft.util.SoundMap.SOUND_TETRIS;

public class LogicChipCyan extends LogicModule {   // Columns

    public boolean active_hold;
    public int[] container_next    = new int[3];
    public int[] container_hold    = new int[3];
    public int[] container_current = new int[3];
    public double time_last;
    public double time_break;
    public int timer;
    public Vector2[] tromino = new Vector2[]{new Vector2(0, 0), new Vector2(0, 0), new Vector2(0, 0)};
    public List<Vector2> clear = new ArrayList<Vector2>();
    public int alpha;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicChipCyan(int tableID){
        super(tableID, 6, 15);
    }





    //----------------------------------------START/RESTART----------------------------------------//

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





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == KEY_UP){    commandCycle(true);   } // UP
        if(action == KEY_DOWN){  columnDrop();         } // DOWN
        if(action == KEY_LEFT){  commandStrafe(true);  } // LEFT
        if(action == KEY_RIGHT){ commandStrafe(false); } // RIGHT
        if(action == KEY_ENTER){ commandHold();        } // HOLD
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){
        timer+=15;
        if(alpha == 255) {
            if(timer > time_last + time_break - scoreLevel * 5 && turnstate == 2) {
                columnFall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 255;
                for(int y = 0; y < 15; y++) {
                    for(int x = 0; x < 6; x++) {
                        if(isCleared(x, y)) {
                            grid[x][y] = -1;
                        }
                    }
                }
                setJingle(SOUND_TETRIS);
                commandCollapse();
            }
        }
    }

    public void updateMotion(){

    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundTag compound){
        active_hold = compound.getBoolean("activehold");
        container_next[0] = compound.getInt("container_next0");
        container_next[1] = compound.getInt("container_next1");
        container_next[2] = compound.getInt("container_next2");

        container_hold[0] = compound.getInt("container_hold0");
        container_hold[1] = compound.getInt("container_hold1");
        container_hold[2] = compound.getInt("container_hold2");

        container_current[0] = compound.getInt("container_current0");
        container_current[1] = compound.getInt("container_current1");
        container_current[2] = compound.getInt("container_current2");

        time_last = compound.getDouble("timelast");
        time_break = compound.getDouble("timebreak");
        timer = compound.getInt("timer");

        tromino[0].set(compound.getInt("tromino0x"), compound.getInt("tromino0y"));
        tromino[1].set(compound.getInt("tromino1x"), compound.getInt("tromino1y"));
        tromino[2].set(compound.getInt("tromino2x"), compound.getInt("tromino2y"));

        alpha = compound.getInt("alpha");
    }

    public CompoundTag save2(CompoundTag compound){
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





    //----------------------------------------SUPPORT----------------------------------------//

    public boolean inLine(int x, int y){
        for(Vector2 v : clear) {
            if(v.matches(x, y)) return true;
        }
        return false;
    }

    private void columnDrop() {
        int tempPoint = scorePoint;
        while(scorePoint == tempPoint) {
            columnFall();
        }
    }

    private boolean isCleared(int x, int y) {
        for(int i = 0; i < clear.size(); i++){
            if(clear.get(i).X == x && clear.get(i).Y == y) return true;
        }
        return false;
    }

    private void commandCollapse() {
        // Gravity after match found and cleared
        int temp = 0;
        boolean falling = true;
        for(int x = 0; x < 6; x++){
            for(int y = 14; y >= 1; y--){
                if(grid[x][y] == -1){
                    for(int y2 = y-1; y2 >= 0; y2--){
                        if(grid[x][y2] != -1){
                            grid[x][y] = grid[x][y2];
                            grid[x][y2] = -1;
                            break;
                        }
                    }
                }
            }
        }
        clear.clear();
        alpha = 255;
        checkField();
    }

    private void commandStrafe(boolean totheleft) {
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

    private void commandCycle(boolean totheleft) {
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

    private void commandHold() {
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
        int r = RANDOM.nextInt(6)+1;
        return r >= 3 ? r+1 : r;
    }

    private void Column_Create() {
        tromino[0] = new Vector2(2, 0);
        tromino[1] = new Vector2(2, 1);
        tromino[2] = new Vector2(2, 2);
        setJingle(SOUND_IMPACT);
    }

    private void columnFall() {
        if(tromino[0].Y < 14 && tromino[1].Y < 14 && tromino[2].Y < 14) {
            if(grid[tromino[0].X][tromino[0].Y + 1] == -1 && grid[tromino[1].X][tromino[1].Y + 1] == -1 && grid[tromino[2].X][tromino[2].Y + 1] == -1) {
                tromino[0].Y = tromino[0].Y + 1;
                tromino[1].Y = tromino[1].Y + 1;
                tromino[2].Y = tromino[2].Y + 1;
            } else {
                columnPlace();
            }
        } else {
            columnPlace();
        }
    }

    private void columnPlace() {
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
        checkField();
    }

    private void checkField() {

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
        }
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 19;
    }



}
