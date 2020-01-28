package mod.casinocraft.tileentities.minigames;

import mod.shared.util.Vector2;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.TileEntityCasino;
import mod.casinocraft.util.Dice;
import net.minecraft.util.math.BlockPos;

public class TileEntitySicBo extends TileEntityCasino {

    Dice[] dice = new Dice[3];
	
    
    
  	//--------------------CONSTRUCTOR--------------------
    
	public TileEntitySicBo(TileEntityBoard te, BlockPos bp){
    	super(te, bp);
    	gridI = new int[12][6];
	}
	
	
	
	//--------------------BASIC--------------------
	
	public void start2(){
		for(int y = 0; y < 6; y++) {
            for(int x = 0; x < 12; x++) {
                gridI[x][y] = 0;
            }
        }
		selector = new Vector2(-1, -1);
        dice[0] = new Dice(0, 4);
        dice[1] = new Dice(0, 4);
        dice[2] = new Dice(0, 4);
	}
	
    public void actionTouch(int action){
    	if(action == -2) {
    		if(selector.X > -1){
    			gridI[selector.X][selector.Y] = 1;
        		selector.set(-1, -1);
    		}
    		Spin();
    	} else if(action == -1){
    		if(selector.X > -1){
    			gridI[selector.X][selector.Y] = 1;
        		selector.set(-1, -1);
    		}
    	} else {
    		selector = new Vector2(action%12, action/12);
    	}
    }
    
	public void update(){
        if(turnstate == 3) {
            for(int i = 0; i < 3; i++) {
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
	
	public Vector2 getVector(int index){
		return selector;
	}
	
	public Dice getDice(int index){
		return dice[index];
	}
	
	public int getValue(int index){
		if(index == -1) return reward;
		return gridI[index%12][index/12];
	}
	
	public String getString(int index){
		return hand;
	}
	
	
	
	//--------------------CUSTOM--------------------
	
	private void Place() {
        //if(selector != selector) {
        //    if(gridI[(int)selector.X][(int)selector.Y] == 0) selector = selector;
        //} else {
            gridI[selector.X][selector.Y] = 1;
            selector = new Vector2(-1, -1);
            //if(FM.coins >= bet) {
            //    FM.coins -= bet;
            //    FM.coins_bonus -= bet;
            //} else {
            //    Spin();
            //}
        //}
    }

    private void Spin() {
        if(turnstate == 2) {
            dice[0].Set_Up(200 + rand.nextInt( 50),  50 + rand.nextInt(200), rand.nextInt(2) == 0);
            dice[1].Set_Up(100 + rand.nextInt(100), 100 + rand.nextInt(100), rand.nextInt(2) == 0);
            dice[2].Set_Up( 50 + rand.nextInt(200), 200 + rand.nextInt( 50), rand.nextInt(2) == 0);
            turnstate = 3;
        } else if(turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[1].shiftX == 0 && dice[2].shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        hand = "" + (dice[0].number + 1) + "-" + (dice[1].number + 1) + "-" + (dice[2].number + 1);
        if(gridI[ 0][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[ 0][0] = 2; } else { gridI[ 0][0] = 3; } }
        if(gridI[ 1][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[ 1][0] = 2; } else { gridI[ 1][0] = 3; } }
        if(gridI[ 2][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[ 2][0] = 2; } else { gridI[ 2][0] = 3; } }
        if(gridI[ 3][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[ 3][0] = 2; } else { gridI[ 3][0] = 3; } }
        if(gridI[ 4][0] == 1) { if(Result_AnyTriple()                         ) { reward +=  31; gridI[ 4][0] = 2; } else { gridI[ 4][0] = 3; } }
        if(gridI[ 5][0] == 1) { if(Result_AnyTriple()                         ) { reward +=  31; gridI[ 5][0] = 2; } else { gridI[ 5][0] = 3; } }
        if(gridI[ 6][0] == 1) { if(Result_AnyTriple()                         ) { reward +=  31; gridI[ 6][0] = 2; } else { gridI[ 6][0] = 3; } }
        if(gridI[ 7][0] == 1) { if(Result_AnyTriple()                         ) { reward +=  31; gridI[ 7][0] = 2; } else { gridI[ 7][0] = 3; } }
        if(gridI[ 8][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[ 8][0] = 2; } else { gridI[ 8][0] = 3; } }
        if(gridI[ 9][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[ 9][0] = 2; } else { gridI[ 9][0] = 3; } }
        if(gridI[10][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[10][0] = 2; } else { gridI[10][0] = 3; } }
        if(gridI[11][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward +=   2; gridI[11][0] = 2; } else { gridI[11][0] = 3; } }

        if(gridI[ 0][1] == 1) { if(Result_Triple(0)                           ) { reward += 181; gridI[ 0][1] = 2; } else { gridI[ 0][1] = 3; } }
        if(gridI[ 1][1] == 1) { if(Result_Triple(0)                           ) { reward += 181; gridI[ 1][1] = 2; } else { gridI[ 1][1] = 3; } }
        if(gridI[ 2][1] == 1) { if(Result_Triple(1)                           ) { reward += 181; gridI[ 2][1] = 2; } else { gridI[ 2][1] = 3; } }
        if(gridI[ 3][1] == 1) { if(Result_Triple(1)                           ) { reward += 181; gridI[ 3][1] = 2; } else { gridI[ 3][1] = 3; } }
        if(gridI[ 4][1] == 1) { if(Result_Triple(2)                           ) { reward += 181; gridI[ 4][1] = 2; } else { gridI[ 4][1] = 3; } }
        if(gridI[ 5][1] == 1) { if(Result_Triple(2)                           ) { reward += 181; gridI[ 5][1] = 2; } else { gridI[ 5][1] = 3; } }
        if(gridI[ 6][1] == 1) { if(Result_Triple(3)                           ) { reward += 181; gridI[ 6][1] = 2; } else { gridI[ 6][1] = 3; } }
        if(gridI[ 7][1] == 1) { if(Result_Triple(3)                           ) { reward += 181; gridI[ 7][1] = 2; } else { gridI[ 7][1] = 3; } }
        if(gridI[ 8][1] == 1) { if(Result_Triple(4)                           ) { reward += 181; gridI[ 8][1] = 2; } else { gridI[ 8][1] = 3; } }
        if(gridI[ 9][1] == 1) { if(Result_Triple(4)                           ) { reward += 181; gridI[ 9][1] = 2; } else { gridI[ 9][1] = 3; } }
        if(gridI[10][1] == 1) { if(Result_Triple(5)                           ) { reward += 181; gridI[10][1] = 2; } else { gridI[10][1] = 3; } }
        if(gridI[11][1] == 1) { if(Result_Triple(5)                           ) { reward += 181; gridI[11][1] = 2; } else { gridI[11][1] = 3; } }

        if(gridI[ 0][2] == 1) { if(Result_Value() ==  4                       ) { reward +=  61; gridI[ 0][2] = 2; } else { gridI[ 0][2] = 3; } }
        if(gridI[ 1][2] == 1) { if(Result_Value() ==  5                       ) { reward +=  21; gridI[ 1][2] = 2; } else { gridI[ 1][2] = 3; } }
        if(gridI[ 2][2] == 1) { if(Result_Value() ==  6                       ) { reward +=  19; gridI[ 2][2] = 2; } else { gridI[ 2][2] = 3; } }
        if(gridI[ 3][2] == 1) { if(Result_Value() ==  7                       ) { reward +=  13; gridI[ 3][2] = 2; } else { gridI[ 3][2] = 3; } }
        if(gridI[ 4][2] == 1) { if(Result_Value() ==  8                       ) { reward +=   9; gridI[ 4][2] = 2; } else { gridI[ 4][2] = 3; } }
        if(gridI[ 5][2] == 1) { if(Result_Double(0, 0)                        ) { reward +=  12; gridI[ 5][2] = 2; } else { gridI[ 5][2] = 3; } }
        if(gridI[ 6][2] == 1) { if(Result_Double(0, 1)                        ) { reward +=   7; gridI[ 6][2] = 2; } else { gridI[ 6][2] = 3; } }
        if(gridI[ 7][2] == 1) { if(Result_Double(0, 2)                        ) { reward +=   7; gridI[ 7][2] = 2; } else { gridI[ 7][2] = 3; } }
        if(gridI[ 8][2] == 1) { if(Result_Double(0, 3)                        ) { reward +=   7; gridI[ 8][2] = 2; } else { gridI[ 8][2] = 3; } }
        if(gridI[ 9][2] == 1) { if(Result_Double(0, 4)                        ) { reward +=   7; gridI[ 9][2] = 2; } else { gridI[ 9][2] = 3; } }
        if(gridI[10][2] == 1) { if(Result_Double(0, 5)                        ) { reward +=   7; gridI[10][2] = 2; } else { gridI[10][2] = 3; } }
        if(gridI[11][2] == 1) { if(Result_Double(1, 1)                        ) { reward +=  12; gridI[11][2] = 2; } else { gridI[11][2] = 3; } }

        if(gridI[ 0][3] == 1) { if(Result_Value() ==  9                       ) { reward +=   7; gridI[ 0][3] = 2; } else { gridI[ 0][3] = 3; } }
        if(gridI[ 1][3] == 1) { if(Result_Value() == 10                       ) { reward +=   7; gridI[ 1][3] = 2; } else { gridI[ 1][3] = 3; } }
        if(gridI[ 2][3] == 1) { if(Result_Value() == 11                       ) { reward +=   7; gridI[ 2][3] = 2; } else { gridI[ 2][3] = 3; } }
        if(gridI[ 3][3] == 1) { if(Result_Value() == 12                       ) { reward +=   7; gridI[ 3][3] = 2; } else { gridI[ 3][3] = 3; } }
        if(gridI[ 4][3] == 1) { if(Result_Value() == 13                       ) { reward +=   7; gridI[ 4][3] = 2; } else { gridI[ 4][3] = 3; } }
        if(gridI[ 5][3] == 1) { if(Result_Double(1, 2)                        ) { reward +=   7; gridI[ 5][3] = 2; } else { gridI[ 5][3] = 3; } }
        if(gridI[ 6][3] == 1) { if(Result_Double(1, 3)                        ) { reward +=   7; gridI[ 6][3] = 2; } else { gridI[ 6][3] = 3; } }
        if(gridI[ 7][3] == 1) { if(Result_Double(1, 4)                        ) { reward +=   7; gridI[ 7][3] = 2; } else { gridI[ 7][3] = 3; } }
        if(gridI[ 8][3] == 1) { if(Result_Double(1, 5)                        ) { reward +=   7; gridI[ 8][3] = 2; } else { gridI[ 8][3] = 3; } }
        if(gridI[ 9][3] == 1) { if(Result_Double(2, 2)                        ) { reward +=  12; gridI[ 9][3] = 2; } else { gridI[ 9][3] = 3; } }
        if(gridI[10][3] == 1) { if(Result_Double(2, 3)                        ) { reward +=   7; gridI[10][3] = 2; } else { gridI[10][3] = 3; } }
        if(gridI[11][3] == 1) { if(Result_Double(2, 4)                        ) { reward +=   7; gridI[11][3] = 2; } else { gridI[11][3] = 3; } }

        if(gridI[ 0][4] == 1) { if(Result_Value() == 14                       ) { reward +=   7; gridI[ 0][4] = 2; } else { gridI[ 0][4] = 3; } }
        if(gridI[ 1][4] == 1) { if(Result_Value() == 15                       ) { reward +=   7; gridI[ 1][4] = 2; } else { gridI[ 1][4] = 3; } }
        if(gridI[ 2][4] == 1) { if(Result_Value() == 16                       ) { reward +=   7; gridI[ 2][4] = 2; } else { gridI[ 2][4] = 3; } }
        if(gridI[ 3][4] == 1) { if(Result_Value() == 17                       ) { reward +=   7; gridI[ 3][4] = 2; } else { gridI[ 3][4] = 3; } }
        if(gridI[ 4][4] == 1) {                                                                                             gridI[ 4][4] = 3;   }
        if(gridI[ 5][4] == 1) { if(Result_Double(2, 5)                        ) { reward +=   7; gridI[ 5][4] = 2; } else { gridI[ 5][4] = 3; } }
        if(gridI[ 6][4] == 1) { if(Result_Double(3, 3)                        ) { reward +=  12; gridI[ 6][4] = 2; } else { gridI[ 6][4] = 3; } }
        if(gridI[ 7][4] == 1) { if(Result_Double(3, 4)                        ) { reward +=   7; gridI[ 7][4] = 2; } else { gridI[ 7][4] = 3; } }
        if(gridI[ 8][4] == 1) { if(Result_Double(3, 5)                        ) { reward +=   7; gridI[ 8][4] = 2; } else { gridI[ 8][4] = 3; } }
        if(gridI[ 9][4] == 1) { if(Result_Double(4, 4)                        ) { reward +=  12; gridI[ 9][4] = 2; } else { gridI[ 9][4] = 3; } }
        if(gridI[10][4] == 1) { if(Result_Double(4, 5)                        ) { reward +=   7; gridI[10][4] = 2; } else { gridI[10][4] = 3; } }
        if(gridI[11][4] == 1) { if(Result_Double(5, 5)                        ) { reward +=  12; gridI[11][4] = 2; } else { gridI[11][4] = 3; } }

        if(gridI[ 0][5] == 1) { if(Result_Triple(0)) { reward += 6; gridI[ 0][5] = 2; } else if(Result_Double(0, 0)) { reward += 3; gridI[ 0][5] = 2; } else if(Result_Single(0)) { reward += 2; gridI[ 0][5] = 2; } else { gridI[ 0][5] = 3; } }
        if(gridI[ 1][5] == 1) { if(Result_Triple(0)) { reward += 6; gridI[ 1][5] = 2; } else if(Result_Double(0, 0)) { reward += 3; gridI[ 1][5] = 2; } else if(Result_Single(0)) { reward += 2; gridI[ 1][5] = 2; } else { gridI[ 1][5] = 3; } }
        if(gridI[ 2][5] == 1) { if(Result_Triple(1)) { reward += 6; gridI[ 2][5] = 2; } else if(Result_Double(1, 1)) { reward += 3; gridI[ 2][5] = 2; } else if(Result_Single(1)) { reward += 2; gridI[ 2][5] = 2; } else { gridI[ 2][5] = 3; } }
        if(gridI[ 3][5] == 1) { if(Result_Triple(1)) { reward += 6; gridI[ 3][5] = 2; } else if(Result_Double(1, 1)) { reward += 3; gridI[ 3][5] = 2; } else if(Result_Single(1)) { reward += 2; gridI[ 3][5] = 2; } else { gridI[ 3][5] = 3; } }
        if(gridI[ 4][5] == 1) { if(Result_Triple(2)) { reward += 6; gridI[ 4][5] = 2; } else if(Result_Double(2, 2)) { reward += 3; gridI[ 4][5] = 2; } else if(Result_Single(2)) { reward += 2; gridI[ 4][5] = 2; } else { gridI[ 4][5] = 3; } }
        if(gridI[ 5][5] == 1) { if(Result_Triple(2)) { reward += 6; gridI[ 5][5] = 2; } else if(Result_Double(2, 2)) { reward += 3; gridI[ 5][5] = 2; } else if(Result_Single(2)) { reward += 2; gridI[ 5][5] = 2; } else { gridI[ 5][5] = 3; } }
        if(gridI[ 6][5] == 1) { if(Result_Triple(3)) { reward += 6; gridI[ 6][5] = 2; } else if(Result_Double(3, 3)) { reward += 3; gridI[ 6][5] = 2; } else if(Result_Single(3)) { reward += 2; gridI[ 6][5] = 2; } else { gridI[ 6][5] = 3; } }
        if(gridI[ 7][5] == 1) { if(Result_Triple(3)) { reward += 6; gridI[ 7][5] = 2; } else if(Result_Double(3, 3)) { reward += 3; gridI[ 7][5] = 2; } else if(Result_Single(3)) { reward += 2; gridI[ 7][5] = 2; } else { gridI[ 7][5] = 3; } }
        if(gridI[ 8][5] == 1) { if(Result_Triple(4)) { reward += 6; gridI[ 8][5] = 2; } else if(Result_Double(4, 4)) { reward += 3; gridI[ 8][5] = 2; } else if(Result_Single(4)) { reward += 2; gridI[ 8][5] = 2; } else { gridI[ 8][5] = 3; } }
        if(gridI[ 9][5] == 1) { if(Result_Triple(4)) { reward += 6; gridI[ 9][5] = 2; } else if(Result_Double(4, 4)) { reward += 3; gridI[ 9][5] = 2; } else if(Result_Single(4)) { reward += 2; gridI[ 9][5] = 2; } else { gridI[ 9][5] = 3; } }
        if(gridI[10][5] == 1) { if(Result_Triple(5)) { reward += 6; gridI[10][5] = 2; } else if(Result_Double(5, 5)) { reward += 3; gridI[10][5] = 2; } else if(Result_Single(5)) { reward += 2; gridI[10][5] = 2; } else { gridI[10][5] = 3; } }
        if(gridI[11][5] == 1) { if(Result_Triple(5)) { reward += 6; gridI[11][5] = 2; } else if(Result_Double(5, 5)) { reward += 3; gridI[11][5] = 2; } else if(Result_Single(5)) { reward += 2; gridI[11][5] = 2; } else { gridI[11][5] = 3; } }

        turnstate = 4;
    }

    private int Result_Value() {
        return dice[0].number + dice[1].number + dice[2].number + 3;
    }

    private boolean Result_Single(int n) {
        if(dice[0].number == n) return true;
        if(dice[1].number == n) return true;
        return dice[2].number == n;
    }

    private boolean Result_Double(int n1, int n2) {
        return Result_Single(n1) && Result_Single(n2);
    }

    private boolean Result_Triple(int n) {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number && dice[0].number == n;
    }

    private boolean Result_AnyTriple() {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number;
    }
}