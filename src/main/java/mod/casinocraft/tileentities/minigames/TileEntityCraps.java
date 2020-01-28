package mod.casinocraft.tileentities.minigames;

import mod.shared.util.Vector2;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Dice;
import net.minecraft.util.math.BlockPos;

public class TileEntityCraps extends TileEntityCasino {

     Dice[] dice = new Dice[2];

     int result;
     int point;
     int comepoint;
	
     
     
 	//--------------------CONSTRUCTOR--------------------
     
	public TileEntityCraps(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new int[21][5];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		hand = "Place your Bets..";
		for(int y = 0; y < 5; y++) {
            for(int x = 0; x < 21; x++) {
                gridI[x][y] = 0;
            }
        }
        selector = new Vector2(10, 2);
        result = -1;
        point  = -1;
        comepoint = -1;
        dice[0] = new Dice(0, 1);
        dice[1] = new Dice(0, 1);
	}
	
    public void actionTouch(int action){
    	if(action == -2) {
    		if(selector.X > -1 && GridValid(selector)){
    			gridI[selector.X][selector.Y] = 1;
        		selector.set(-1, -1);
    		}
    		Spin();
    	}
    	if(action == -1){
    		if(selector.X > -1 && GridValid(selector)){
    			gridI[selector.X][selector.Y] = 1;
        		selector.set(-1, -1);
    		}
    	} else {
    		selector = new Vector2(action%21, action/21);
    	}
    }
    
	public void update(){
		if(turnstate == 3) {
            for(int i = 0; i < 2; i++) {
                if(dice[i].shiftX > 45) {
                    dice[i].Update(1, rand.nextInt(6));
                } else if(dice[i].shiftX > 0) {
                    dice[i].shiftX = 0;
                    dice[i].shiftY = 0;
                } else {

                }
            }
        }
        if(turnstate == 4) {
            selector  = new Vector2(-1, -1);
        }
	}
	
	
	
	//--------------------GETTER--------------------
	
	public int getValue(int index){
		if(index == -1) return result;
		if(index == -2) return point;
		if(index == -3) return comepoint;
		if(index == -4) return reward;
		return gridI[index%21][index/21];
	}
	
	public Dice getDice(int index){
		return dice[index];
	}
	
	public Vector2 getVector(int index){
		return selector;
	}
	
	public boolean getFlag(int index){
		return false;
	}
	
	public String getString(int index){
		return hand;
	}
	
	
	
	//--------------------CUSTOM--------------------
	
	private boolean GridValid(Vector2 v) {
        if(v == new Vector2( 1, 0)) return false;
        if(v == new Vector2(19, 0)) return false;
        if(v == new Vector2( 1, 4)) return false;
        if(v == new Vector2(19, 4)) return false;
        if(v.Y >= 1 && v.Y <= 3) {
            return v.X % 3 != 1;
        }
        return true;
    }

    private void Spin() {
        if(turnstate == 2) {
        	dice[0].Set_Up(200 + rand.nextInt( 50),  50 + rand.nextInt(200), rand.nextInt(2) == 0);
            dice[1].Set_Up( 50 + rand.nextInt(200), 200 + rand.nextInt( 50), rand.nextInt(2) == 0);
            if(selector.X > -1) {
                //if(point != -1 && gridI[(int)selector.X][(int)selector.Y] == 0) {
                    //FM.coins       -= bet;
                    //FM.coins_bonus -= bet;
                //}
                gridI[selector.X][selector.Y] = 1;
                selector = new Vector2(-1, -1);
            }
            turnstate = 3;
        } else if(turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[1].shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        dice[0].Reset();
        dice[1].Reset();
        boolean hasCome = false;
        for(int i = 0; i < 5; i++) {
            if(gridI[ 0][i] == 1) hasCome = true;
            if(gridI[20][i] == 1) hasCome = true;
        }
        if(point == -1) {
            point  = dice[0].number + 1 + dice[1].number + 1;
            result = dice[0].number + 1 + dice[1].number + 1;
            if(result == 7 || result == 11) { // PASS
                Result_Pass(true);
                Result_DontPass(false);
                hand = "Natura Roll!";
                turnstate = 4;
            } else if(result == 2 || result == 3 || result == 12) { // DON'T PASS
                Result_Pass(false);
                Result_DontPass(true);
                hand = "Crab...";
                turnstate = 4;
            } else {
                hand = "Roll again...";
                turnstate = 2;
            }
        } else {
            result = dice[0].number + 1 + dice[1].number + 1;
            Result_SingleOdds();
            turnstate = 2;
            if(hasCome) {
                if(comepoint == -1) {
                    comepoint = result;
                } else {
                    if(result == comepoint) {
                        Result_Come(true);
                        Result_DontCome(false);
                    } else if(result == 7) {
                        Result_Come(false);
                        Result_DontCome(true);
                    }
                }
            }
            if(result == 7) { // DON'T PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                hand = "SEVEN";
                turnstate = 4;
            } else if(result == point) { // PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                hand = "POINT";
                turnstate = 4;
            }
        }
    }

    private void Result_Come(boolean won) {
        if(gridI[0][0] == 1) { if(won) { gridI[0][0] = 2; reward += 2; } else { gridI[0][0] = 3; } }
        if(gridI[0][1] == 1) { if(won) { gridI[0][1] = 2; reward += 2; } else { gridI[0][1] = 3; } }
        if(gridI[0][2] == 1) { if(won) { gridI[0][2] = 2; reward += 2; } else { gridI[0][2] = 3; } }
        if(gridI[0][3] == 1) { if(won) { gridI[0][3] = 2; reward += 2; } else { gridI[0][3] = 3; } }
        if(gridI[0][4] == 1) { if(won) { gridI[0][4] = 2; reward += 2; } else { gridI[0][4] = 3; } }
    }

    private void Result_DontCome(boolean won) {
        if(gridI[20][0] == 1) { if(won) { gridI[20][0] = 2; reward += 2; } else { gridI[20][0] = 3; } }
        if(gridI[20][1] == 1) { if(won) { gridI[20][1] = 2; reward += 2; } else { gridI[20][1] = 3; } }
        if(gridI[20][2] == 1) { if(won) { gridI[20][2] = 2; reward += 2; } else { gridI[20][2] = 3; } }
        if(gridI[20][3] == 1) { if(won) { gridI[20][3] = 2; reward += 2; } else { gridI[20][3] = 3; } }
        if(gridI[20][4] == 1) { if(won) { gridI[20][4] = 2; reward += 2; } else { gridI[20][4] = 3; } }
    }

    private void Result_Pass(boolean won) {
        if(gridI[ 2][0] == 1) { if(won) { gridI[ 2][0] = 2; reward += 2; } else { gridI[ 2][0] = 3; } }
        if(gridI[ 3][0] == 1) { if(won) { gridI[ 3][0] = 2; reward += 2; } else { gridI[ 3][0] = 3; } }
        if(gridI[ 4][0] == 1) { if(won) { gridI[ 4][0] = 2; reward += 2; } else { gridI[ 4][0] = 3; } }
        if(gridI[ 5][0] == 1) { if(won) { gridI[ 5][0] = 2; reward += 2; } else { gridI[ 5][0] = 3; } }
        if(gridI[ 6][0] == 1) { if(won) { gridI[ 6][0] = 2; reward += 2; } else { gridI[ 6][0] = 3; } }
        if(gridI[ 7][0] == 1) { if(won) { gridI[ 7][0] = 2; reward += 2; } else { gridI[ 7][0] = 3; } }
        if(gridI[ 8][0] == 1) { if(won) { gridI[ 8][0] = 2; reward += 2; } else { gridI[ 8][0] = 3; } }
        if(gridI[ 9][0] == 1) { if(won) { gridI[ 9][0] = 2; reward += 2; } else { gridI[ 9][0] = 3; } }
        if(gridI[10][0] == 1) { if(won) { gridI[10][0] = 2; reward += 2; } else { gridI[10][0] = 3; } }
        if(gridI[11][0] == 1) { if(won) { gridI[11][0] = 2; reward += 2; } else { gridI[11][0] = 3; } }
        if(gridI[12][0] == 1) { if(won) { gridI[12][0] = 2; reward += 2; } else { gridI[12][0] = 3; } }
        if(gridI[13][0] == 1) { if(won) { gridI[13][0] = 2; reward += 2; } else { gridI[13][0] = 3; } }
        if(gridI[14][0] == 1) { if(won) { gridI[14][0] = 2; reward += 2; } else { gridI[14][0] = 3; } }
        if(gridI[15][0] == 1) { if(won) { gridI[15][0] = 2; reward += 2; } else { gridI[15][0] = 3; } }
        if(gridI[16][0] == 1) { if(won) { gridI[16][0] = 2; reward += 2; } else { gridI[16][0] = 3; } }
        if(gridI[17][0] == 1) { if(won) { gridI[17][0] = 2; reward += 2; } else { gridI[17][0] = 3; } }
        if(gridI[18][0] == 1) { if(won) { gridI[18][0] = 2; reward += 2; } else { gridI[18][0] = 3; } }
    }

    private void Result_DontPass(boolean won) {
        if(gridI[ 2][4] == 1) { if(won) { gridI[ 2][4] = 2; reward += 2; } else { gridI[ 2][4] = 3; } }
        if(gridI[ 3][4] == 1) { if(won) { gridI[ 3][4] = 2; reward += 2; } else { gridI[ 3][4] = 3; } }
        if(gridI[ 4][4] == 1) { if(won) { gridI[ 4][4] = 2; reward += 2; } else { gridI[ 4][4] = 3; } }
        if(gridI[ 5][4] == 1) { if(won) { gridI[ 5][4] = 2; reward += 2; } else { gridI[ 5][4] = 3; } }
        if(gridI[ 6][4] == 1) { if(won) { gridI[ 6][4] = 2; reward += 2; } else { gridI[ 6][4] = 3; } }
        if(gridI[ 7][4] == 1) { if(won) { gridI[ 7][4] = 2; reward += 2; } else { gridI[ 7][4] = 3; } }
        if(gridI[ 8][4] == 1) { if(won) { gridI[ 8][4] = 2; reward += 2; } else { gridI[ 8][4] = 3; } }
        if(gridI[ 9][4] == 1) { if(won) { gridI[ 9][4] = 2; reward += 2; } else { gridI[ 9][4] = 3; } }
        if(gridI[10][4] == 1) { if(won) { gridI[10][4] = 2; reward += 2; } else { gridI[10][4] = 3; } }
        if(gridI[11][4] == 1) { if(won) { gridI[11][4] = 2; reward += 2; } else { gridI[11][4] = 3; } }
        if(gridI[12][4] == 1) { if(won) { gridI[12][4] = 2; reward += 2; } else { gridI[12][4] = 3; } }
        if(gridI[13][4] == 1) { if(won) { gridI[13][4] = 2; reward += 2; } else { gridI[13][4] = 3; } }
        if(gridI[14][4] == 1) { if(won) { gridI[14][4] = 2; reward += 2; } else { gridI[14][4] = 3; } }
        if(gridI[15][4] == 1) { if(won) { gridI[15][4] = 2; reward += 2; } else { gridI[15][4] = 3; } }
        if(gridI[16][4] == 1) { if(won) { gridI[16][4] = 2; reward += 2; } else { gridI[16][4] = 3; } }
        if(gridI[17][4] == 1) { if(won) { gridI[17][4] = 2; reward += 2; } else { gridI[17][4] = 3; } }
        if(gridI[18][4] == 1) { if(won) { gridI[18][4] = 2; reward += 2; } else { gridI[18][4] = 3; } }
    }

    private void Result_PassOdds(boolean won) {
        if(gridI[ 2][1] == 1) { if(won) { if(result ==  4) { gridI[ 2][1] = 2; reward += 2; } else { gridI[ 2][1] = 3; } } else { gridI[ 2][1] = 3; } }
        if(gridI[ 3][1] == 1) { if(won) { if(result ==  4) { gridI[ 3][1] = 2; reward += 2; } else { gridI[ 3][1] = 3; } } else { gridI[ 3][1] = 3; } }
        if(gridI[ 5][1] == 1) { if(won) { if(result ==  5) { gridI[ 5][1] = 2; reward += 2; } else { gridI[ 5][1] = 3; } } else { gridI[ 5][1] = 3; } }
        if(gridI[ 6][1] == 1) { if(won) { if(result ==  5) { gridI[ 6][1] = 2; reward += 2; } else { gridI[ 6][1] = 3; } } else { gridI[ 6][1] = 3; } }
        if(gridI[ 8][1] == 1) { if(won) { if(result ==  6) { gridI[ 8][1] = 2; reward += 2; } else { gridI[ 8][1] = 3; } } else { gridI[ 8][1] = 3; } }
        if(gridI[ 9][1] == 1) { if(won) { if(result ==  6) { gridI[ 9][1] = 2; reward += 2; } else { gridI[ 9][1] = 3; } } else { gridI[ 9][1] = 3; } }
        if(gridI[11][1] == 1) { if(won) { if(result ==  8) { gridI[11][1] = 2; reward += 2; } else { gridI[11][1] = 3; } } else { gridI[11][1] = 3; } }
        if(gridI[12][1] == 1) { if(won) { if(result ==  8) { gridI[12][1] = 2; reward += 2; } else { gridI[12][1] = 3; } } else { gridI[12][1] = 3; } }
        if(gridI[14][1] == 1) { if(won) { if(result ==  9) { gridI[14][1] = 2; reward += 2; } else { gridI[14][1] = 3; } } else { gridI[14][1] = 3; } }
        if(gridI[15][1] == 1) { if(won) { if(result ==  9) { gridI[15][1] = 2; reward += 2; } else { gridI[15][1] = 3; } } else { gridI[15][1] = 3; } }
        if(gridI[17][1] == 1) { if(won) { if(result == 10) { gridI[17][1] = 2; reward += 2; } else { gridI[17][1] = 3; } } else { gridI[17][1] = 3; } }
        if(gridI[18][1] == 1) { if(won) { if(result == 10) { gridI[18][1] = 2; reward += 2; } else { gridI[18][1] = 3; } } else { gridI[18][1] = 3; } }
    }

    private void Result_DontPassOdds(boolean won) {
        if(gridI[ 2][3] == 1) { if(won) { if(result ==  4) { gridI[ 2][3] = 2; reward += 2; } else { gridI[ 2][3] = 3; } } else { gridI[ 2][3] = 3; } }
        if(gridI[ 3][3] == 1) { if(won) { if(result ==  4) { gridI[ 3][3] = 2; reward += 2; } else { gridI[ 3][3] = 3; } } else { gridI[ 3][3] = 3; } }
        if(gridI[ 5][3] == 1) { if(won) { if(result ==  5) { gridI[ 5][3] = 2; reward += 2; } else { gridI[ 5][3] = 3; } } else { gridI[ 5][3] = 3; } }
        if(gridI[ 6][3] == 1) { if(won) { if(result ==  5) { gridI[ 6][3] = 2; reward += 2; } else { gridI[ 6][3] = 3; } } else { gridI[ 6][3] = 3; } }
        if(gridI[ 8][3] == 1) { if(won) { if(result ==  6) { gridI[ 8][3] = 2; reward += 2; } else { gridI[ 8][3] = 3; } } else { gridI[ 8][3] = 3; } }
        if(gridI[ 9][3] == 1) { if(won) { if(result ==  6) { gridI[ 9][3] = 2; reward += 2; } else { gridI[ 9][3] = 3; } } else { gridI[ 9][3] = 3; } }
        if(gridI[11][3] == 1) { if(won) { if(result ==  8) { gridI[11][3] = 2; reward += 2; } else { gridI[11][3] = 3; } } else { gridI[11][3] = 3; } }
        if(gridI[12][3] == 1) { if(won) { if(result ==  8) { gridI[12][3] = 2; reward += 2; } else { gridI[12][3] = 3; } } else { gridI[12][3] = 3; } }
        if(gridI[14][3] == 1) { if(won) { if(result ==  9) { gridI[14][3] = 2; reward += 2; } else { gridI[14][3] = 3; } } else { gridI[14][3] = 3; } }
        if(gridI[15][3] == 1) { if(won) { if(result ==  9) { gridI[15][3] = 2; reward += 2; } else { gridI[15][3] = 3; } } else { gridI[15][3] = 3; } }
        if(gridI[17][3] == 1) { if(won) { if(result == 10) { gridI[17][3] = 2; reward += 2; } else { gridI[17][3] = 3; } } else { gridI[17][3] = 3; } }
        if(gridI[18][3] == 1) { if(won) { if(result == 10) { gridI[18][3] = 2; reward += 2; } else { gridI[18][3] = 3; } } else { gridI[18][3] = 3; } }
    }                                                                        

    private void Result_SingleOdds() {
        if(gridI[ 2][2] == 1) { if(result ==  4) { gridI[ 2][2] = 2; reward += 10; } else { gridI[ 2][2] = 3; } }
        if(gridI[ 3][2] == 1) { if(result ==  4) { gridI[ 3][2] = 2; reward += 10; } else { gridI[ 3][2] = 3; } }
        if(gridI[ 5][2] == 1) { if(result ==  5) { gridI[ 5][2] = 2; reward +=  8; } else { gridI[ 5][2] = 3; } }
        if(gridI[ 6][2] == 1) { if(result ==  5) { gridI[ 6][2] = 2; reward +=  8; } else { gridI[ 6][2] = 3; } }
        if(gridI[ 8][2] == 1) { if(result ==  6) { gridI[ 8][2] = 2; reward +=  6; } else { gridI[ 8][2] = 3; } }
        if(gridI[ 9][2] == 1) { if(result ==  6) { gridI[ 9][2] = 2; reward +=  6; } else { gridI[ 9][2] = 3; } }
        if(gridI[11][2] == 1) { if(result ==  8) { gridI[11][2] = 2; reward +=  6; } else { gridI[11][2] = 3; } }
        if(gridI[12][2] == 1) { if(result ==  8) { gridI[12][2] = 2; reward +=  6; } else { gridI[12][2] = 3; } }
        if(gridI[14][2] == 1) { if(result ==  9) { gridI[14][2] = 2; reward +=  8; } else { gridI[14][2] = 3; } }
        if(gridI[15][2] == 1) { if(result ==  9) { gridI[15][2] = 2; reward +=  8; } else { gridI[15][2] = 3; } }
        if(gridI[17][2] == 1) { if(result == 10) { gridI[17][2] = 2; reward += 10; } else { gridI[17][2] = 3; } }
        if(gridI[18][2] == 1) { if(result == 10) { gridI[18][2] = 2; reward += 10; } else { gridI[18][2] = 3; } }
    }
}