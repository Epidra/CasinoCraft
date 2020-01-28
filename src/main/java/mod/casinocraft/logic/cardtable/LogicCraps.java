package mod.casinocraft.logic.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Dice;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class LogicCraps extends LogicBase {

    public final int D_DICE0 = 0;
    public final int D_DICE1 = 1;

    public final int I_RESULT = 0;
    public final int I_POINT = 1;
    public final int I_COMEPOINT = 2;



    //--------------------CONSTRUCTOR--------------------

    public LogicCraps(){
        super(false, true, false, 21, 5, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        hand("Place your Bets..");
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x < 21; x++) {
                setGRD(x, y, 0);
            }
        }
        selector = new Vector2(10, 2);
        setINT(I_RESULT, -1);
        setINT(I_POINT, -1);
        setINT(I_COMEPOINT, -1);
        setDIE(D_DICE0, new Dice(0, 1));
        setDIE(D_DICE1, new Dice(0, 1));
    }

    public void actionTouch(int action){
        if(action == -2) {
            if(selector.X > -1 && GridValid(selector)){
                setGRD(selector.X, selector.Y, 1);
                selector.set(-1, -1);
            }
            Spin();
        }
        if(action == -1){
            if(selector.X > -1 && GridValid(selector)){
                setGRD(selector.X, selector.Y, 1);
                selector.set(-1, -1);
            }
        } else {
            selector = new Vector2(action%21, action/21);
        }
    }

    public void updateMotion(){
        if(turnstate() == 3) {
            for(int i = 0; i < 2; i++) {
                if(getDIE(D_DICE0 + i).shiftX > 45) {
                    getDIE(D_DICE0 + i).Update(1, RANDOM.nextInt(6));
                } else if(getDIE(D_DICE0 + i).shiftX > 0) {
                    getDIE(D_DICE0 + i).shiftX = 0;
                    getDIE(D_DICE0 + i).shiftY = 0;
                } else {

                }
            }
        }
        if(turnstate() == 4) {
            selector  = new Vector2(-1, -1);
        }
    }
    
    public void updateLogic(){
        
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
        if(turnstate() == 2) {
            getDIE(D_DICE0).Set_Up(200 + RANDOM.nextInt( 50),  50 + RANDOM.nextInt(200), RANDOM.nextInt(2) == 0);
            getDIE(D_DICE1).Set_Up( 50 + RANDOM.nextInt(200), 200 + RANDOM.nextInt( 50), RANDOM.nextInt(2) == 0);
            if(selector.X > -1) {
                //if(point != -1 && gridI[(int)selector.X][(int)selector.Y] == 0) {
                //FM.coins       -= bet;
                //FM.coins_bonus -= bet;
                //}
                setGRD(selector.X, selector.Y, 1);
                selector = new Vector2(-1, -1);
            }
            turnstate(3);
        } else if(turnstate() == 3) {
            if(getDIE(D_DICE0).shiftX == 0 && getDIE(D_DICE1).shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        getDIE(D_DICE0).Reset();
        getDIE(D_DICE1).Reset();
        boolean hasCome = false;
        for(int i = 0; i < 5; i++) {
            if(getGRD( 0, i )== 1) hasCome = true;
            if(getGRD(20, i) == 1) hasCome = true;
        }
        if(getINT(I_POINT) == -1) {
            setINT(I_POINT, getDIE(D_DICE0).number + 1 + getDIE(D_DICE1).number + 1);
            setINT(I_RESULT, getDIE(D_DICE0).number + 1 + getDIE(D_DICE1).number + 1);
            if(getINT(I_RESULT) == 7 || getINT(I_RESULT) == 11) { // PASS
                Result_Pass(true);
                Result_DontPass(false);
                hand("Natura Roll!");
                turnstate(4);
            } else if(getINT(I_RESULT) == 2 || getINT(I_RESULT) == 3 || getINT(I_RESULT) == 12) { // DON'T PASS
                Result_Pass(false);
                Result_DontPass(true);
                hand("Crab...");
                turnstate(4);
            } else {
                hand("Roll again...");
                turnstate(2);
            }
        } else {
            setINT(I_RESULT, getDIE(D_DICE0).number + 1 + getDIE(D_DICE1).number + 1);
            Result_SingleOdds();
            turnstate(2);
            if(hasCome) {
                if(getINT(I_COMEPOINT) == -1) {
                    rplINT(I_COMEPOINT, I_RESULT);
                } else {
                    if(getINT(I_RESULT) == getINT(I_COMEPOINT)) {
                        Result_Come(true);
                        Result_DontCome(false);
                    } else if(getINT(I_COMEPOINT) == 7) {
                        Result_Come(false);
                        Result_DontCome(true);
                    }
                }
            }
            if(getINT(I_COMEPOINT) == 7) { // DON'T PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                hand("SEVEN");
                turnstate(4);
            } else if(getINT(I_RESULT) == getINT(I_POINT)) { // PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                hand("POINT");
                turnstate(4);
            }
        }
    }

    private void Result_Come(boolean won) {
        if(getGRD(0, 0) == 1) { if(won) { setGRD(0, 0, 2); reward(reward() + 2); } else { setGRD(0, 0, 3); } }
        if(getGRD(0, 1) == 1) { if(won) { setGRD(0, 1, 2); reward(reward() + 2); } else { setGRD(0, 1, 3); } }
        if(getGRD(0, 2) == 1) { if(won) { setGRD(0, 2, 2); reward(reward() + 2); } else { setGRD(0, 2, 3); } }
        if(getGRD(0, 3) == 1) { if(won) { setGRD(0, 3, 2); reward(reward() + 2); } else { setGRD(0, 3, 3); } }
        if(getGRD(0, 4) == 1) { if(won) { setGRD(0, 4, 2); reward(reward() + 2); } else { setGRD(0, 4, 3); } }
    }

    private void Result_DontCome(boolean won) {
        if(getGRD(20, 0) == 1) { if(won) { setGRD(20, 0, 2); reward(reward() + 2); } else { setGRD(20, 0, 3); } }
        if(getGRD(20, 1) == 1) { if(won) { setGRD(20, 1, 2); reward(reward() + 2); } else { setGRD(20, 1, 3); } }
        if(getGRD(20, 2) == 1) { if(won) { setGRD(20, 2, 2); reward(reward() + 2); } else { setGRD(20, 2, 3); } }
        if(getGRD(20, 3) == 1) { if(won) { setGRD(20, 3, 2); reward(reward() + 2); } else { setGRD(20, 3, 3); } }
        if(getGRD(20, 4) == 1) { if(won) { setGRD(20, 4, 2); reward(reward() + 2); } else { setGRD(20, 4, 3); } }
    }

    private void Result_Pass(boolean won) {
        if(getGRD( 2, 0) == 1) { if(won) { setGRD( 2, 0, 2); reward(reward() + 2); } else { setGRD( 2, 0, 3); } }
        if(getGRD( 3, 0) == 1) { if(won) { setGRD( 3, 0, 2); reward(reward() + 2); } else { setGRD( 3, 0, 3); } }
        if(getGRD( 4, 0) == 1) { if(won) { setGRD( 4, 0, 2); reward(reward() + 2); } else { setGRD( 4, 0, 3); } }
        if(getGRD( 5, 0) == 1) { if(won) { setGRD( 5, 0, 2); reward(reward() + 2); } else { setGRD( 5, 0, 3); } }
        if(getGRD( 6, 0) == 1) { if(won) { setGRD( 6, 0, 2); reward(reward() + 2); } else { setGRD( 6, 0, 3); } }
        if(getGRD( 7, 0) == 1) { if(won) { setGRD( 7, 0, 2); reward(reward() + 2); } else { setGRD( 7, 0, 3); } }
        if(getGRD( 8, 0) == 1) { if(won) { setGRD( 8, 0, 2); reward(reward() + 2); } else { setGRD( 8, 0, 3); } }
        if(getGRD( 9, 0) == 1) { if(won) { setGRD( 9, 0, 2); reward(reward() + 2); } else { setGRD( 9, 0, 3); } }
        if(getGRD(10, 0) == 1) { if(won) { setGRD(10, 0, 2); reward(reward() + 2); } else { setGRD(10, 0, 3); } }
        if(getGRD(11, 0) == 1) { if(won) { setGRD(11, 0, 2); reward(reward() + 2); } else { setGRD(11, 0, 3); } }
        if(getGRD(12, 0) == 1) { if(won) { setGRD(12, 0, 2); reward(reward() + 2); } else { setGRD(12, 0, 3); } }
        if(getGRD(13, 0) == 1) { if(won) { setGRD(13, 0, 2); reward(reward() + 2); } else { setGRD(13, 0, 3); } }
        if(getGRD(14, 0) == 1) { if(won) { setGRD(14, 0, 2); reward(reward() + 2); } else { setGRD(14, 0, 3); } }
        if(getGRD(15, 0) == 1) { if(won) { setGRD(15, 0, 2); reward(reward() + 2); } else { setGRD(15, 0, 3); } }
        if(getGRD(16, 0) == 1) { if(won) { setGRD(16, 0, 2); reward(reward() + 2); } else { setGRD(16, 0, 3); } }
        if(getGRD(17, 0) == 1) { if(won) { setGRD(17, 0, 2); reward(reward() + 2); } else { setGRD(17, 0, 3); } }
        if(getGRD(18, 0) == 1) { if(won) { setGRD(18, 0, 2); reward(reward() + 2); } else { setGRD(18, 0, 3); } }
    }

    private void Result_DontPass(boolean won) {
        if(getGRD( 2, 4) == 1) { if(won) { setGRD( 2, 4, 2); reward(reward() + 2); } else { setGRD( 2, 4, 3); } }
        if(getGRD( 3, 4) == 1) { if(won) { setGRD( 3, 4, 2); reward(reward() + 2); } else { setGRD( 3, 4, 3); } }
        if(getGRD( 4, 4) == 1) { if(won) { setGRD( 4, 4, 2); reward(reward() + 2); } else { setGRD( 4, 4, 3); } }
        if(getGRD( 5, 4) == 1) { if(won) { setGRD( 5, 4, 2); reward(reward() + 2); } else { setGRD( 5, 4, 3); } }
        if(getGRD( 6, 4) == 1) { if(won) { setGRD( 6, 4, 2); reward(reward() + 2); } else { setGRD( 6, 4, 3); } }
        if(getGRD( 7, 4) == 1) { if(won) { setGRD( 7, 4, 2); reward(reward() + 2); } else { setGRD( 7, 4, 3); } }
        if(getGRD( 8, 4) == 1) { if(won) { setGRD( 8, 4, 2); reward(reward() + 2); } else { setGRD( 8, 4, 3); } }
        if(getGRD( 9, 4) == 1) { if(won) { setGRD( 9, 4, 2); reward(reward() + 2); } else { setGRD( 9, 4, 3); } }
        if(getGRD(10, 4) == 1) { if(won) { setGRD(10, 4, 2); reward(reward() + 2); } else { setGRD(10, 4, 3); } }
        if(getGRD(11, 4) == 1) { if(won) { setGRD(11, 4, 2); reward(reward() + 2); } else { setGRD(11, 4, 3); } }
        if(getGRD(12, 4) == 1) { if(won) { setGRD(12, 4, 2); reward(reward() + 2); } else { setGRD(12, 4, 3); } }
        if(getGRD(13, 4) == 1) { if(won) { setGRD(13, 4, 2); reward(reward() + 2); } else { setGRD(13, 4, 3); } }
        if(getGRD(14, 4) == 1) { if(won) { setGRD(14, 4, 2); reward(reward() + 2); } else { setGRD(14, 4, 3); } }
        if(getGRD(15, 4) == 1) { if(won) { setGRD(15, 4, 2); reward(reward() + 2); } else { setGRD(15, 4, 3); } }
        if(getGRD(16, 4) == 1) { if(won) { setGRD(16, 4, 2); reward(reward() + 2); } else { setGRD(16, 4, 3); } }
        if(getGRD(17, 4) == 1) { if(won) { setGRD(17, 4, 2); reward(reward() + 2); } else { setGRD(17, 4, 3); } }
        if(getGRD(18, 4) == 1) { if(won) { setGRD(18, 4, 2); reward(reward() + 2); } else { setGRD(18, 4, 3); } }
    }

    private void Result_PassOdds(boolean won) {
        if(getGRD( 2, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  4) { setGRD( 2, 1, 2); reward(reward() + 2); } else { setGRD( 2, 1, 3); } } else { setGRD( 2, 1, 3); } }
        if(getGRD( 3, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  4) { setGRD( 3, 1, 2); reward(reward() + 2); } else { setGRD( 3, 1, 3); } } else { setGRD( 3, 1, 3); } }
        if(getGRD( 5, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  5) { setGRD( 5, 1, 2); reward(reward() + 2); } else { setGRD( 5, 1, 3); } } else { setGRD( 5, 1, 3); } }
        if(getGRD( 6, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  5) { setGRD( 6, 1, 2); reward(reward() + 2); } else { setGRD( 6, 1, 3); } } else { setGRD( 6, 1, 3); } }
        if(getGRD( 8, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  6) { setGRD( 8, 1, 2); reward(reward() + 2); } else { setGRD( 8, 1, 3); } } else { setGRD( 8, 1, 3); } }
        if(getGRD( 9, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  6) { setGRD( 9, 1, 2); reward(reward() + 2); } else { setGRD( 9, 1, 3); } } else { setGRD( 9, 1, 3); } }
        if(getGRD(11, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  8) { setGRD(11, 1, 2); reward(reward() + 2); } else { setGRD(11, 1, 3); } } else { setGRD(11, 1, 3); } }
        if(getGRD(12, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  8) { setGRD(12, 1, 2); reward(reward() + 2); } else { setGRD(12, 1, 3); } } else { setGRD(12, 1, 3); } }
        if(getGRD(14, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  9) { setGRD(14, 1, 2); reward(reward() + 2); } else { setGRD(14, 1, 3); } } else { setGRD(14, 1, 3); } }
        if(getGRD(15, 1) == 1) { if(won) { if(getINT(I_RESULT) ==  9) { setGRD(15, 1, 2); reward(reward() + 2); } else { setGRD(15, 1, 3); } } else { setGRD(15, 1, 3); } }
        if(getGRD(17, 1) == 1) { if(won) { if(getINT(I_RESULT) == 10) { setGRD(17, 1, 2); reward(reward() + 2); } else { setGRD(17, 1, 3); } } else { setGRD(17, 1, 3); } }
        if(getGRD(18, 1) == 1) { if(won) { if(getINT(I_RESULT) == 10) { setGRD(18, 1, 2); reward(reward() + 2); } else { setGRD(18, 1, 3); } } else { setGRD(18, 1, 3); } }
    }

    private void Result_DontPassOdds(boolean won) {
        if(getGRD( 2, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  4) { setGRD( 2, 3, 2); reward(reward() + 2); } else { setGRD( 2, 3, 3); } } else { setGRD( 2, 3, 3); } }
        if(getGRD( 3, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  4) { setGRD( 3, 3, 2); reward(reward() + 2); } else { setGRD( 3, 3, 3); } } else { setGRD( 3, 3, 3); } }
        if(getGRD( 5, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  5) { setGRD( 5, 3, 2); reward(reward() + 2); } else { setGRD( 5, 3, 3); } } else { setGRD( 5, 3, 3); } }
        if(getGRD( 6, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  5) { setGRD( 6, 3, 2); reward(reward() + 2); } else { setGRD( 6, 3, 3); } } else { setGRD( 6, 3, 3); } }
        if(getGRD( 8, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  6) { setGRD( 8, 3, 2); reward(reward() + 2); } else { setGRD( 8, 3, 3); } } else { setGRD( 8, 3, 3); } }
        if(getGRD( 9, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  6) { setGRD( 9, 3, 2); reward(reward() + 2); } else { setGRD( 9, 3, 3); } } else { setGRD( 9, 3, 3); } }
        if(getGRD(11, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  8) { setGRD(11, 3, 2); reward(reward() + 2); } else { setGRD(11, 3, 3); } } else { setGRD(11, 3, 3); } }
        if(getGRD(12, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  8) { setGRD(12, 3, 2); reward(reward() + 2); } else { setGRD(12, 3, 3); } } else { setGRD(12, 3, 3); } }
        if(getGRD(14, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  9) { setGRD(14, 3, 2); reward(reward() + 2); } else { setGRD(14, 3, 3); } } else { setGRD(14, 3, 3); } }
        if(getGRD(15, 3) == 1) { if(won) { if(getINT(I_RESULT) ==  9) { setGRD(15, 3, 2); reward(reward() + 2); } else { setGRD(15, 3, 3); } } else { setGRD(15, 3, 3); } }
        if(getGRD(17, 3) == 1) { if(won) { if(getINT(I_RESULT) == 10) { setGRD(17, 3, 2); reward(reward() + 2); } else { setGRD(17, 3, 3); } } else { setGRD(17, 3, 3); } }
        if(getGRD(18, 3) == 1) { if(won) { if(getINT(I_RESULT) == 10) { setGRD(18, 3, 2); reward(reward() + 2); } else { setGRD(18, 3, 3); } } else { setGRD(18, 3, 3); } }
    }

    private void Result_SingleOdds() {
        if(getGRD( 2, 2) == 1) { if(getINT(I_RESULT) ==  4) { setGRD( 2, 2, 2); reward(reward() + 10); } else { setGRD( 2, 2, 3); } }
        if(getGRD( 3, 2) == 1) { if(getINT(I_RESULT) ==  4) { setGRD( 3, 2, 2); reward(reward() + 10); } else { setGRD( 3, 2, 3); } }
        if(getGRD( 5, 2) == 1) { if(getINT(I_RESULT) ==  5) { setGRD( 5, 2, 2); reward(reward() +  8); } else { setGRD( 5, 2, 3); } }
        if(getGRD( 6, 2) == 1) { if(getINT(I_RESULT) ==  5) { setGRD( 6, 2, 2); reward(reward() +  8); } else { setGRD( 6, 2, 3); } }
        if(getGRD( 8, 2) == 1) { if(getINT(I_RESULT) ==  6) { setGRD( 8, 2, 2); reward(reward() +  6); } else { setGRD( 8, 2, 3); } }
        if(getGRD( 9, 2) == 1) { if(getINT(I_RESULT) ==  6) { setGRD( 9, 2, 2); reward(reward() +  6); } else { setGRD( 9, 2, 3); } }
        if(getGRD(11, 2) == 1) { if(getINT(I_RESULT) ==  8) { setGRD(11, 2, 2); reward(reward() +  6); } else { setGRD(11, 2, 3); } }
        if(getGRD(12, 2) == 1) { if(getINT(I_RESULT) ==  8) { setGRD(12, 2, 2); reward(reward() +  6); } else { setGRD(12, 2, 3); } }
        if(getGRD(14, 2) == 1) { if(getINT(I_RESULT) ==  9) { setGRD(14, 2, 2); reward(reward() +  8); } else { setGRD(14, 2, 3); } }
        if(getGRD(15, 2) == 1) { if(getINT(I_RESULT) ==  9) { setGRD(15, 2, 2); reward(reward() +  8); } else { setGRD(15, 2, 3); } }
        if(getGRD(17, 2) == 1) { if(getINT(I_RESULT) == 10) { setGRD(17, 2, 2); reward(reward() + 10); } else { setGRD(17, 2, 3); } }
        if(getGRD(18, 2) == 1) { if(getINT(I_RESULT) == 10) { setGRD(18, 2, 2); reward(reward() + 10); } else { setGRD(18, 2, 3); } }
    }

}
