package mod.casinocraft.logic.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class LogicMemory extends LogicBase {

    public static int B_SELECT_A = 0;
    public static int B_SELECT_B = 1;

    public static int V_SELECT_A = 0;
    public static int V_SELECT_B = 1;

    public static int I_TIMER = 0;



    //--------------------CONSTRUCTOR--------------------

    public LogicMemory(){
        super(false, true, false, 17, 9, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        scoreLevel(1);
        scoreLives(8);
        setBOL(B_SELECT_A, false);
        setBOL(B_SELECT_B, false);
        setINT(I_TIMER, 0);
        setVEC(V_SELECT_A, -1, -1);
        setVEC(V_SELECT_B, -1, -1);
        Command_Create_Grid();
    }

    public void restart(){
        turnstate(2);
        scoreLevel(1);
        scoreLives(scoreLevel()*2);
        setBOL(B_SELECT_A, false);
        setBOL(B_SELECT_B, false);
        setINT(I_TIMER, 0);
        setVEC(V_SELECT_A, -1, -1);
        setVEC(V_SELECT_B, -1, -1);
        Command_Create_Grid();
    }

    public void actionTouch(int action){
        if(action == -1) {
            restart();
        } else if(action == -2) {
            turnstate(4);
        } else {
            Click_Mino(action % 17, action / 17);
        }
    }

    public void updateLogic(){
        if(getINT(I_TIMER) == -1){
            if(getGRD(getVEC(V_SELECT_A)) == getGRD(getVEC(V_SELECT_A))){
                setGRD(getVEC(V_SELECT_A), -1);
                setGRD(getVEC(V_SELECT_B), -1);
            } else {
                scoreLives(-1);
                //ChangeName(selected_A_pos.X, selected_A_pos.Y, grid[selected_A_pos.X][selected_A_pos.Y]);
                //ChangeName(selected_B_pos.X, selected_B_pos.Y, grid[selected_B_pos.X][selected_B_pos.Y]);
            }
            boolean temp = false;
            for(int x = 0; x < 17; x++) {
                for(int y = 0; y < 9; y++) {
                    if(getGRD(x, y) != -1) temp = true;
                }
            }
            if(!temp) {
                turnstate(3);
                scoreScore(scoreLevel()*4);
            } else {
                if(scoreLives() <= 0) {
                    turnstate(4);
                    scoreScore(-(scoreScore() / 2));
                }
            }
            setBOL(B_SELECT_A, false);
            setBOL(B_SELECT_B, false);
            setVEC(V_SELECT_A, -1, -1);
            setVEC(V_SELECT_B, -1, -1);
        }
    }

    public void updateMotion(){
        if(getINT(I_TIMER) > 0){
            updINT(I_TIMER, -10);
            if(getINT(I_TIMER) <= 0)
                setINT(I_TIMER, -1);
        }
    }



    //--------------------CUSTOM--------------------

    public void Click_Mino(int x, int y) {
        if(!getBOL(B_SELECT_A)) {
            if(getGRD(x, y) != -1) {
                setBOL(B_SELECT_A, true);
                setVEC(V_SELECT_A, x, y);
            }
        } else if(!getBOL(B_SELECT_B)) {
            if(getGRD(x, y) != -1 && !getVEC(V_SELECT_A).matches(x, y)) {
                setBOL(B_SELECT_B, true);
                setVEC(V_SELECT_B, x, y);
                //ChangeName(x, y, grid[x][y]);
                setINT(I_TIMER, 400-scoreLevel()*10);
            }
        }
    }

    private void Command_Create_Grid() {
        for(int x = 0; x < 17; x++) {
            for(int y = 0; y < 9; y++) {
                setGRD(x, y, -1);
            }
        }
        int max = difficulty == 2 ? 9*9-1 : 17*9-1;
        int filler = scoreLevel()*4 < max ? scoreLevel()*4 : max;
        int filler2 = filler;

        int color[] = new int[] {0,0,0,0,0,0,0,0};
        while(filler > 0) {
            int z = RANDOM.nextInt(8);
            color[z] += 2;
            filler -= 2;
            //for(int i = 0; i < 8; i++) {
            //	if(filler > 0) { color[i] += 2; filler -= 2; }
            //}
        }

        while(filler2 > 0) {
            int x = RANDOM.nextInt(difficulty == 2 ? 9 : 17) + (difficulty == 2 ? 4 : 0);
            int y = RANDOM.nextInt(9);
            if(getGRD(x, y) == -1) {
                for(int i = 0; i < 8; i++) {
                    if(color[i] > 0) {
                        setGRD(x, y, i);
                        color[i]--;
                        filler2--;
                        break;
                    }
                }
            }
        }
    }

}
