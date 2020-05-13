package mod.casinocraft.logic.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

public class LogicMinoRed extends LogicBase {   // Roulette

    private float rotation_wheel;
    private float rotation_ball;
    public boolean spinning;
    public int result;
    public int timer;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoRed(int tableID){
        super(tableID, 25, 7);
    }




    //----------------------------------------START/RESTART----------------------------------------//

    public void start2(){
        selector = new Vector2(0, 4);
        rotation_wheel = 0.00f;
        rotation_ball = 0.00f;
        spinning = false;
        result = 0;
        timer = -1;
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        timeout = 0;
        if(action == -2) {
            if(selector.X > -1 && gridValid(selector)){
                grid[selector.X][selector.Y] = activePlayer+1;
                selector.set(-1, -1);
            }
            spin();
        } else if(action == -1){
            if(selector.X > -1 && gridValid(selector)){
                grid[selector.X][selector.Y] = activePlayer+1;
                selector.set(-1, -1);
            }
        } else {
            int x = action % 25;
            int y = action / 25;
            if(grid[x][y] == 0){
                selector.set(x, y);
            }
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion(){

    }

    public void updateLogic(){
        if(turnstate == 2){
            timeout++;
            if(timeout == CasinoKeeper.config_timeout.get()){
                spin();
            }
        }
        if(turnstate == 3) {
            if(timer > 0) {
                if(timer > 20) {
                    //rotation_wheel += (timer / 5000f);
                    rotation_ball  += (timer / 1000f);
                }
                timer--;
            }
        }
    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        rotation_wheel = compound.getFloat("rotationwheel");
        rotation_ball = compound.getFloat("rotation_ball");
        spinning = compound.getBoolean("spinning");
        result = compound.getInt("result");
        timer = compound.getInt("timer");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putFloat("rotationwheel", rotation_wheel);
        compound.putFloat("rotationball", rotation_ball);
        compound.putBoolean("spinning", spinning);
        compound.putInt("result", result);
        compound.putInt("timer", timer);

        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    public Vector2 vectorWheel(){
        // 1 Number == 0,1698... Radian
        int n = 0;

        float rotation = (rotation_wheel + rotation_ball + 0.085f)%(0.1698f * 37);
        for(int i = 0; i < 37; i++) {
            if(0.17 * i < rotation && rotation < 0.17 * i + 0.17) {
                n = i;
            }
        }
        switch(n){
            case  0: return new Vector2(124,  31);
            case  1: return new Vector2(140,  34);
            case  2: return new Vector2(154,  37);
            case  3: return new Vector2(167,  45);
            case  4: return new Vector2(180,  54);
            case  5: return new Vector2(190,  64);
            case  6: return new Vector2(199,  78);
            case  7: return new Vector2(204,  91);
            case  8: return new Vector2(208, 106);
            case  9: return new Vector2(209, 121);
            case 10: return new Vector2(208, 136);
            case 11: return new Vector2(204, 150);
            case 12: return new Vector2(198, 164);
            case 13: return new Vector2(189, 177);
            case 14: return new Vector2(178, 187);
            case 15: return new Vector2(166, 197);
            case 16: return new Vector2(152, 203);
            case 17: return new Vector2(138, 207);
            case 18: return new Vector2(123, 209);
            case 19: return new Vector2(107, 208);
            case 20: return new Vector2( 93, 205);
            case 21: return new Vector2( 79, 199);
            case 22: return new Vector2( 66, 191);
            case 23: return new Vector2( 55, 181);
            case 24: return new Vector2( 45, 169);
            case 25: return new Vector2( 38, 156);
            case 26: return new Vector2( 33, 142);
            case 27: return new Vector2( 31, 127);
            case 28: return new Vector2( 32, 122);
            case 29: return new Vector2( 35,  97);
            case 30: return new Vector2( 40,  84);
            case 31: return new Vector2( 47,  70);
            case 32: return new Vector2( 57,  58);
            case 33: return new Vector2( 68,  49);
            case 34: return new Vector2( 81,  40);
            case 35: return new Vector2( 95,  35);
            case 36: return new Vector2(110,  32);
        }
        return new Vector2(0, 0);
    }

    private boolean gridValid(Vector2 v) {
        if(v.X == 23) return false;
        if(v.matches(24, 1)) return false;
        if(v.matches(24, 3)) return false;
        if(v.matches( 7, 5)) return false;
        if(v.matches( 7, 6)) return false;
        if(v.matches(15, 5)) return false;
        if(v.matches(15, 6)) return false;
        if(v.matches( 3, 6)) return false;
        if(v.matches(11, 6)) return false;
        return !v.matches(19, 6);
    }

    private void spin() {
        if(turnstate == 2 && !spinning) {
            activePlayer++;
            if(activePlayer >= getFirstFreePlayerSlot()){
                turnstate = 3;
            }
        } else if(turnstate == 3 && !spinning) {
            timer = 100 + RANDOM.nextInt(150);
            spinning = true;
        } else if(turnstate == 3 && timer == 0) {
            result();
        }
    }

    public void result() {
        // 1 Number == 0,1698... Radian
        int n = 0;

        float rotation = rotation_wheel + rotation_ball + 0.085f;
        while(rotation > 0.1698 * 37) {
            rotation = rotation - (0.1698f * 37);
        }

        for(int i = 0; i < 37; i++) {
            if(0.17 * i < rotation && rotation < 0.17 * i + 0.17) {
                n = i;
            }
        }

        if(n ==  0) result = 36;
        if(n ==  1) result = 11; if(n ==  2) result = 30; if(n ==  3) result =  8; if(n ==  4) result = 23; if(n ==  5) result = 10; if(n ==  6) result =  5;
        if(n ==  7) result = 24; if(n ==  8) result = 16; if(n ==  9) result = 33; if(n == 10) result =  1; if(n == 11) result = 20; if(n == 12) result = 14;
        if(n == 13) result = 31; if(n == 14) result =  9; if(n == 15) result = 22; if(n == 16) result = 18; if(n == 17) result = 29; if(n == 18) result =  7;
        if(n == 19) result = 28; if(n == 20) result = 12; if(n == 21) result = 35; if(n == 22) result =  3; if(n == 23) result = 26; if(n == 24) result =  0;
        if(n == 25) result = 32; if(n == 26) result = 15; if(n == 27) result = 19; if(n == 28) result =  4; if(n == 29) result = 21; if(n == 30) result =  2;
        if(n == 31) result = 25; if(n == 32) result = 17; if(n == 33) result = 34; if(n == 34) result =  6; if(n == 35) result = 27; if(n == 36) result = 13;

        setResult( 0, 4, 1, 36); setResult( 0, 2, 1, 36); setResult( 0, 0, 1, 36); // Direct Bet:  1 -  2 -  3
        setResult( 2, 4, 1, 36); setResult( 2, 2, 1, 36); setResult( 2, 0, 1, 36); // Direct Bet:  4 -  5 -  6
        setResult( 4, 4, 1, 36); setResult( 4, 2, 1, 36); setResult( 4, 0, 1, 36); // Direct Bet:  7 -  8 -  9
        setResult( 6, 4, 1, 36); setResult( 6, 2, 1, 36); setResult( 6, 0, 1, 36); // Direct Bet: 10 - 11 - 12
        setResult( 8, 4, 1, 36); setResult( 8, 2, 1, 36); setResult( 8, 0, 1, 36); // Direct Bet: 13 - 14 - 15
        setResult(10, 4, 1, 36); setResult(10, 2, 1, 36); setResult(10, 0, 1, 36); // Direct Bet: 16 - 17 - 18
        setResult(12, 4, 1, 36); setResult(12, 2, 1, 36); setResult(12, 0, 1, 36); // Direct Bet: 19 - 20 - 21
        setResult(14, 4, 1, 36); setResult(14, 2, 1, 36); setResult(14, 0, 1, 36); // Direct Bet: 22 - 23 - 24
        setResult(16, 4, 1, 36); setResult(16, 2, 1, 36); setResult(16, 0, 1, 36); // Direct Bet: 25 - 26 - 27
        setResult(18, 4, 1, 36); setResult(18, 2, 1, 36); setResult(18, 0, 1, 36); // Direct Bet: 28 - 29 - 28
        setResult(20, 4, 1, 36); setResult(20, 2, 1, 36); setResult(20, 0, 1, 36); // Direct Bet: 31 - 32 - 33
        setResult(22, 4, 1, 36); setResult(22, 2, 1, 36); setResult(22, 0, 1, 36); // Direct Bet: 34 - 35 - 36

        setResult( 1, 4,  1,  4, 18); setResult( 1, 2,  2,  5, 18); setResult( 1, 0,  3,  6, 18); // Vertical Bet:  1/ 4 -  2/ 5 -  3/ 6
        setResult( 3, 4,  4,  7, 18); setResult( 3, 2,  5,  8, 18); setResult( 3, 0,  6,  9, 18); // Vertical Bet:  4/ 7 -  5/ 8 -  6/ 9
        setResult( 5, 4,  7, 10, 18); setResult( 5, 2,  8, 11, 18); setResult( 5, 0,  9, 12, 18); // Vertical Bet:  7/10 -  8/11 -  9/12
        setResult( 7, 4, 10, 13, 18); setResult( 7, 2, 11, 14, 18); setResult( 7, 0, 12, 15, 18); // Vertical Bet: 10/13 - 11/14 - 12/15
        setResult( 9, 4, 13, 16, 18); setResult( 9, 2, 14, 17, 18); setResult( 9, 0, 15, 18, 18); // Vertical Bet: 13/16 - 14/17 - 15/18
        setResult(11, 4, 16, 19, 18); setResult(11, 2, 17, 20, 18); setResult(11, 0, 18, 21, 18); // Vertical Bet: 16/19 - 17/20 - 18/21
        setResult(13, 4, 19, 22, 18); setResult(13, 2, 20, 23, 18); setResult(13, 0, 21, 24, 18); // Vertical Bet: 19/22 - 20/23 - 21/24
        setResult(15, 4, 22, 25, 18); setResult(15, 2, 23, 26, 18); setResult(15, 0, 24, 27, 18); // Vertical Bet: 22/25 - 23/26 - 24/27
        setResult(17, 4, 25, 28, 18); setResult(17, 2, 26, 29, 18); setResult(17, 0, 27, 30, 18); // Vertical Bet: 25/28 - 26/29 - 27/30
        setResult(19, 4, 28, 31, 18); setResult(19, 2, 29, 32, 18); setResult(19, 0, 30, 33, 18); // Vertical Bet: 28/31 - 29/32 - 30/33
        setResult(21, 4, 31, 34, 18); setResult(21, 2, 32, 35, 18); setResult(21, 0, 33, 36, 18); // Vertical Bet: 31/34 - 32/35 - 33/36

        setResult( 0, 3,  1,  2, 18); setResult( 0, 1,  2,  3, 18); // Horizontal Bet:   1/ 2 -  2/ 3
        setResult( 2, 3,  4,  5, 18); setResult( 2, 1,  5,  6, 18); // Horizontal Bet:   4/ 5 -  5/ 6
        setResult( 4, 3,  7,  8, 18); setResult( 4, 1,  8,  9, 18); // Horizontal Bet:   7/ 8 -  8/ 9
        setResult( 6, 3, 10, 11, 18); setResult( 6, 1, 11, 12, 18); // Horizontal Bet:  10/11 - 11/12
        setResult( 8, 3, 13, 14, 18); setResult( 8, 1, 14, 15, 18); // Horizontal Bet:  13/14 - 14/15
        setResult(10, 3, 16, 17, 18); setResult(10, 1, 17, 18, 18); // Horizontal Bet:  16/17 - 17/18
        setResult(12, 3, 19, 20, 18); setResult(12, 1, 20, 21, 18); // Horizontal Bet:  19/20 - 20/21
        setResult(14, 3, 22, 23, 18); setResult(14, 1, 23, 24, 18); // Horizontal Bet:  22/23 - 23/24
        setResult(16, 3, 25, 26, 18); setResult(16, 1, 26, 27, 18); // Horizontal Bet:  25/26 - 26/27
        setResult(18, 3, 28, 29, 18); setResult(18, 1, 29, 28, 18); // Horizontal Bet:  28/29 - 29/28
        setResult(20, 3, 31, 32, 18); setResult(20, 1, 32, 33, 18); // Horizontal Bet:  31/32 - 32/33
        setResult(22, 3, 34, 35, 18); setResult(22, 1, 35, 36, 18); // Horizontal Bet:  34/35 - 35/36

        setResult( 1, 3,  1,  2,  4,  5, 9); setResult( 1, 1,  2,  3,  5,  6, 9); // Cross Bet:  1/ 2/ 4/ 5 -  2/ 3/ 5/ 6
        setResult( 3, 3,  4,  5,  7,  8, 9); setResult( 3, 1,  5,  6,  8,  9, 9); // Cross Bet:  4/ 5/ 7/ 8 -  5/ 6/ 8/ 9
        setResult( 5, 3,  7,  8, 10, 11, 9); setResult( 5, 1,  8,  9, 11, 12, 9); // Cross Bet:  7/ 8/10/11 -  8/ 9/11/12
        setResult( 7, 3, 10, 11, 13, 14, 9); setResult( 7, 1, 11, 12, 14, 15, 9); // Cross Bet: 10/11/13/14 - 11/12/14/15
        setResult( 9, 3, 13, 14, 16, 17, 9); setResult( 9, 1, 14, 15, 17, 18, 9); // Cross Bet: 13/14/16/17 - 14/15/17/18
        setResult(11, 3, 16, 17, 19, 20, 9); setResult(11, 1, 17, 18, 20, 21, 9); // Cross Bet: 16/17/19/20 - 17/18/20/21
        setResult(13, 3, 19, 20, 22, 23, 9); setResult(13, 1, 20, 21, 23, 24, 9); // Cross Bet: 19/20/22/23 - 20/21/23/24
        setResult(15, 3, 22, 23, 25, 26, 9); setResult(15, 1, 23, 24, 26, 27, 9); // Cross Bet: 22/23/25/26 - 23/24/26/27
        setResult(17, 3, 25, 26, 28, 29, 9); setResult(17, 1, 26, 27, 29, 28, 9); // Cross Bet: 25/26/28/29 - 26/27/29/28
        setResult(19, 3, 28, 29, 31, 32, 9); setResult(19, 1, 29, 28, 32, 33, 9); // Cross Bet: 28/29/31/32 - 29/28/32/33
        setResult(21, 3, 31, 32, 34, 35, 9); setResult(21, 1, 32, 33, 35, 36, 9); // Cross Bet: 31/32/34/35 - 32/33/35/36

        for(int i = 1; i <= 36; i++) {
            for(int j = 0; j < 7; j++) {
                if(i <= 12) { setResult( 0 + j, 5, i, 3); } // 1st 12
                else if(i <= 24) { setResult( 8 + j, 5, i, 3); } // 2nd 12
                else if(i <= 36) { setResult(16 + j, 5, i, 3); } // 3rd 12
            }
            if(i % 3 == 1) setResult(24, 4, i, 3); // 2 to 1 (1)
            if(i % 3 == 2) setResult(24, 2, i, 3); // 2 to 1 (2)
            if(i % 3 == 0) setResult(24, 0, i, 3); // 2 to 1 (3)

            if(i     <= 18) { setResult( 0, 6, i, 2); setResult( 1, 6, i, 2); setResult( 2, 6, i, 2); } //  1 to 18
            if(i     >= 19) { setResult(20, 6, i, 2); setResult(21, 6, i, 2); setResult(22, 6, i, 2); } // 19 to 36
            if(i % 2 ==  0) { setResult( 4, 6, i, 2); setResult( 5, 6, i, 2); setResult( 6, 6, i, 2); } // EVEN
            if(i % 2 ==  1) { setResult(16, 6, i, 2); setResult(17, 6, i, 2); setResult(18, 6, i, 2); } // ODD
        }

        setResult(24, 5, 0, 36); setResult(24, 6, 0, 36); // Direct 0

        for(int i = 0; i < 3; i++) {
            setResult( 8 + i, 6,  1,  3,  5,  7, 2); setResult( 8 + i, 6,  9, 12, 14, 16, 2); setResult( 8 + i, 6, 18, 19, 21, 23, 2); setResult( 8 + i, 6, 25, 27, 30, 32, 2); setResult( 8 + i, 6, 34, 36, 2); // ROUGE
            setResult(12 + i, 6,  2,  4,  6,  8, 2); setResult(12 + i, 6, 10, 11, 13, 15, 2); setResult(12 + i, 6, 17, 20, 22, 24, 2); setResult(12 + i, 6, 26, 28, 29, 31, 2); setResult(12 + i, 6, 33, 35, 2); // NOIR
        }

        turnstate = 4;

        for(int y = 0; y < grid[0].length; y++){
            for(int x = 0; x < grid.length; x++){
                grid[x][y] = grid[x][y] - 10 <= 0 ? 0 : grid[x][y] - 10;
            }
        }
    }

    private void setResult(int x, int y, int r1, int r2, int multi) {
        setResult(x, y, r1, multi);
        setResult(x, y, r2, multi);
    }

    private void setResult(int x, int y, int r1, int r2, int r3, int r4, int multi) {
        setResult(x, y, r1, multi);
        setResult(x, y, r2, multi);
        setResult(x, y, r3, multi);
        setResult(x, y, r4, multi);
    }

    private void setResult(int x, int y, int r, int multi) {
        if(grid[x][y] > 0 && grid[x][y] < 10) {
            if(result == r) {
                reward[grid[x][y] - 1] += multi;
                grid[x][y] += 10;
            } else {
                //grid[x][y] = 0;
            }
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public boolean hasHighscore(){
        return false;
    }

    public boolean isMultiplayer(){
        return true;
    }

    public int getID(){
        return 45;
    }

}
