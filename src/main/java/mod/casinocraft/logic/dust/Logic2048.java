package mod.casinocraft.logic.dust;

import mod.casinocraft.logic.LogicBase;
import net.minecraft.nbt.CompoundNBT;

public class Logic2048 extends LogicBase {

    public boolean placing = false;
    public boolean timerActive = false;

    public int timer = 0;
    public int direction = 0; // 0 - null, 1 - up, 2 - down, 3 - left, 4 - right



    //--------------------CONSTRUCTOR--------------------

    public Logic2048(){
        super(false, 0, "a_2048", 4, 4);
    }



    //--------------------BASIC--------------------

    public void start2(){
        placing = false;
        grid[0][0] = RANDOM.nextInt(1) + 1;
        grid[3][0] = RANDOM.nextInt(1) + 1;
        grid[0][3] = RANDOM.nextInt(1) + 1;
        grid[3][3] = RANDOM.nextInt(1) + 1;
        timerActive = false;
        timer = 0;
        direction = 0;
    }

    public void actionTouch(int action){
        if(action == 0) { Move(1); }
        if(action == 1) { Move(2); }
        if(action == 2) { Move(3); }
        if(action == 3) { Move(4); }
    }

    public void updateLogic(){
        if(timerActive) {
            timer += 8;
            if(timer == 48) {
                timerActive = false;
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

    public void updateMotion() {

    }

    public void load2(CompoundNBT compound){
        placing = compound.getBoolean("placing");
        timerActive = compound.getBoolean("timeractive");

        timer = compound.getInt("timer");
        direction = compound.getInt("direction");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putBoolean("placing", placing);
        compound.putBoolean("timeractive", timerActive);

        compound.putInt("timer", timer);
        compound.putInt("direction", direction);

        return compound;
    }



    //--------------------CUSTOM--------------------

    private void Move(int s) {
        if(s == 1) { // up
            for(int y = 1; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(grid[x][y] != 0) {
                        if(grid[x][y - 1] == 0 || grid[x][y - 1] == grid[x][y]) {
                            grid[x][y] += 100;
                            timerActive = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(grid[x][y] != 0) {
                        if(grid[x][y + 1] == 0 || grid[x][y + 1] == grid[x][y]) {
                            grid[x][y] += 100;
                            timerActive = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(grid[x][y] != 0) {
                        if(grid[x - 1][y] == 0 || grid[x - 1][y] == grid[x][y]) {
                            grid[x][y] += 100;
                            timerActive = true;
                            direction = s;
                        }
                    }
                }
            }
        }
        if(s == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(grid[x][y] != 0) {
                        if(grid[x + 1][y] == 0 || grid[x + 1][y] == grid[x][y]) {
                            grid[x][y] += 100;
                            timerActive = true;
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
                    if(grid[x][y] >= 100) {
                        grid[x][y] -= 100;
                        if(grid[x][y - 1] == 0) {
                            grid[x][y - 1] = grid[x][y];
                        } else {
                            grid[x][y - 1] = grid[x][y - 1] + 1;
                            Add_Points(grid[x][y - 1]);
                        }
                        grid[x][y] = 0; // sets FLAG automatically to FALSE
                    }
                }
            }
        }
        if(direction == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(grid[x][y] >= 100) {
                        grid[x][y] -= 100;
                        if(grid[x][y + 1] == 0) {
                            grid[x][y + 1] = grid[x][y];
                        } else {
                            grid[x][y + 1] = grid[x][y+1] + 1;
                            Add_Points(grid[x][y + 1]);
                        }
                        grid[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(grid[x][y] >= 100) {
                        grid[x][y] -= 100;
                        if(grid[x - 1][y] == 0) {
                            grid[x - 1][y] = grid[x][y];
                        } else {
                            grid[x - 1][y] = grid[x - 1][y] + 1;
                            Add_Points(grid[x - 1][y]);
                        }
                        grid[x][y] = 0;
                    }
                }
            }
        }
        if(direction == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(grid[x][y] >= 100) {
                        grid[x][y] -= 100;
                        if(grid[x + 1][y] == 0) {
                            grid[x + 1][y] = grid[x][y];
                        } else {
                            grid[x + 1][y] = grid[x + 1][y] + 1;
                            Add_Points(grid[x + 1][y]);
                        }
                        grid[x][y] = 0;
                    }
                }
            }
        }
    }

    private void Place() {
        for(int i = 0; i < 24; i++) {
            int x = RANDOM.nextInt(4);
            int y = RANDOM.nextInt(4);
            if(grid[x][y] == 0) { // FLAGGING ???
                grid[x][y] = 1;
                break;
            }
        }
        Check();
    }

    private void Check() {
        boolean b = false;
        for(int y = 1; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                if(grid[x][y] != 0) {
                    if(grid[x][y - 1] == 0 || grid[x][y - 1] == grid[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int y = 2; y > -1; y--) {
            for(int x = 3; x > -1; x--) {
                if(grid[x][y] != 0) {
                    if(grid[x][y + 1] == 0 || grid[x][y + 1] == grid[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 1; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(grid[x][y] != 0) {
                    if(grid[x - 1][y] == 0 || grid[x - 1][y] == grid[x][y]) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 2; x > -1; x--) {
            for(int y = 3; y > -1; y--) {
                if(grid[x][y] != 0) {
                    if(grid[x + 1][y] == 0 || grid[x + 1][y] == grid[x][y]) {
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
        if( horizontal && direction == 3) if(grid[x][y] >= 100) return -timer; // left
        if( horizontal && direction == 4) if(grid[x][y] >= 100) return  timer; // right
        if(!horizontal && direction == 1) if(grid[x][y] >= 100) return -timer; // up
        if(!horizontal && direction == 2) if(grid[x][y] >= 100) return  timer; // down
        return 0;
    }

    private void Add_Points(int i) {
        if(i ==  1) scorePoint +=     2;
        if(i ==  2) scorePoint +=     4;
        if(i ==  3) scorePoint +=     8;
        if(i ==  4) scorePoint +=    16;
        if(i ==  5) scorePoint +=    32;
        if(i ==  6) scorePoint +=    64;
        if(i ==  7) scorePoint +=   128;
        if(i ==  8) scorePoint +=   256;
        if(i ==  9) scorePoint +=   512;
        if(i == 10) scorePoint +=  1024;
        if(i == 11) scorePoint +=  2048;
        if(i == 12) scorePoint +=  4096;
        if(i == 13) scorePoint +=  8192;
        if(i == 14) scorePoint += 16384;
        if(i == 15) scorePoint += 32768;
        if(i == 16) scorePoint += 65536;
    }

}
