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

public class LogicSicBo extends LogicBase {

    
    public static int D_DICE0 = 0;
    public static int D_DICE1 = 1;
    public static int D_DICE2 = 2;



    //--------------------CONSTRUCTOR--------------------

    public LogicSicBo(){
        super(false, true, false, 12, 6, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 6; y++) {
            for(int x = 0; x < 12; x++) {
                setGRD(x, y, 0);
            }
        }
        selector = new Vector2(-1, -1);
        setDIE(D_DICE0, new Dice(0, 4));
        setDIE(D_DICE1, new Dice(0, 4));
        setDIE(D_DICE2, new Dice(0, 4));
    }

    public void actionTouch(int action){
        if(action == -2) {
            if(selector.X > -1){
                setGRD(selector, 1);
                selector.set(-1, -1);
            }
            Spin();
        } else if(action == -1){
            if(selector.X > -1){
                setGRD(selector, 1);
                selector.set(-1, -1);
            }
        } else {
            selector = new Vector2(action%12, action/12);
        }
    }

    public void updateLogic(){
        if(turnstate() == 3) {
            for(int i = 0; i < 3; i++) {
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
            selector = new Vector2(-1, -1);
        }
    }
    
    public void updateMotion(){
        
    }



    //--------------------CUSTOM--------------------

    private void Spin() {
        if(turnstate() == 2) {
            getDIE(D_DICE0).Set_Up(200 + RANDOM.nextInt( 50),  50 + RANDOM.nextInt(200), RANDOM.nextInt(2) == 0);
            getDIE(D_DICE1).Set_Up(100 + RANDOM.nextInt(100), 100 + RANDOM.nextInt(100), RANDOM.nextInt(2) == 0);
            getDIE(D_DICE2).Set_Up( 50 + RANDOM.nextInt(200), 200 + RANDOM.nextInt( 50), RANDOM.nextInt(2) == 0);
            turnstate(3);
        } else if(turnstate() == 3) {
            if(getDIE(D_DICE0).shiftX == 0 && getDIE(D_DICE0).shiftX == 0 && getDIE(D_DICE0).shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        hand("" + (getDIE(D_DICE0).number + 1) + "-" + (getDIE(D_DICE1).number + 1) + "-" + (getDIE(D_DICE2).number + 1));
        if(getGRD( 0, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD( 0, 0,  2); } else { setGRD( 0, 0,  3); } }
        if(getGRD( 1, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD( 1, 0,  2); } else { setGRD( 1, 0,  3); } }
        if(getGRD( 2, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD( 2, 0,  2); } else { setGRD( 2, 0,  3); } }
        if(getGRD( 3, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD( 3, 0,  2); } else { setGRD( 3, 0,  3); } }
        if(getGRD( 4, 0) == 1) { if(Result_AnyTriple()                         ) { reward(reward() +  31); setGRD( 4, 0,  2); } else { setGRD( 4, 0,  3); } }
        if(getGRD( 5, 0) == 1) { if(Result_AnyTriple()                         ) { reward(reward() +  31); setGRD( 5, 0,  2); } else { setGRD( 5, 0,  3); } }
        if(getGRD( 6, 0) == 1) { if(Result_AnyTriple()                         ) { reward(reward() +  31); setGRD( 6, 0,  2); } else { setGRD( 6, 0,  3); } }
        if(getGRD( 7, 0) == 1) { if(Result_AnyTriple()                         ) { reward(reward() +  31); setGRD( 7, 0,  2); } else { setGRD( 7, 0,  3); } }
        if(getGRD( 8, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD( 8, 0,  2); } else { setGRD( 8, 0,  3); } }
        if(getGRD( 9, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD( 9, 0,  2); } else { setGRD( 9, 0,  3); } }
        if(getGRD(10, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD(10, 0,  2); } else { setGRD(10, 0,  3); } }
        if(getGRD(11, 0) == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { reward(reward() +   2); setGRD(11, 0,  2); } else { setGRD(11, 0,  3); } }

        if(getGRD( 0, 1) == 1) { if(Result_Triple(0)                           ) { reward(reward() + 181); setGRD( 0, 1,  2); } else { setGRD( 0, 1,  3); } }
        if(getGRD( 1, 1) == 1) { if(Result_Triple(0)                           ) { reward(reward() + 181); setGRD( 1, 1,  2); } else { setGRD( 1, 1,  3); } }
        if(getGRD( 2, 1) == 1) { if(Result_Triple(1)                           ) { reward(reward() + 181); setGRD( 2, 1,  2); } else { setGRD( 2, 1,  3); } }
        if(getGRD( 3, 1) == 1) { if(Result_Triple(1)                           ) { reward(reward() + 181); setGRD( 3, 1,  2); } else { setGRD( 3, 1,  3); } }
        if(getGRD( 4, 1) == 1) { if(Result_Triple(2)                           ) { reward(reward() + 181); setGRD( 4, 1,  2); } else { setGRD( 4, 1,  3); } }
        if(getGRD( 5, 1) == 1) { if(Result_Triple(2)                           ) { reward(reward() + 181); setGRD( 5, 1,  2); } else { setGRD( 5, 1,  3); } }
        if(getGRD( 6, 1) == 1) { if(Result_Triple(3)                           ) { reward(reward() + 181); setGRD( 6, 1,  2); } else { setGRD( 6, 1,  3); } }
        if(getGRD( 7, 1) == 1) { if(Result_Triple(3)                           ) { reward(reward() + 181); setGRD( 7, 1,  2); } else { setGRD( 7, 1,  3); } }
        if(getGRD( 8, 1) == 1) { if(Result_Triple(4)                           ) { reward(reward() + 181); setGRD( 8, 1,  2); } else { setGRD( 8, 1,  3); } }
        if(getGRD( 9, 1) == 1) { if(Result_Triple(4)                           ) { reward(reward() + 181); setGRD( 9, 1,  2); } else { setGRD( 9, 1,  3); } }
        if(getGRD(10, 1) == 1) { if(Result_Triple(5)                           ) { reward(reward() + 181); setGRD(10, 1,  2); } else { setGRD(10, 1,  3); } }
        if(getGRD(11, 1) == 1) { if(Result_Triple(5)                           ) { reward(reward() + 181); setGRD(11, 1,  2); } else { setGRD(11, 1,  3); } }

        if(getGRD( 0, 2) == 1) { if(Result_Value() ==  4                       ) { reward(reward() +  61); setGRD( 0, 2,  2); } else { setGRD( 0, 2,  3); } }
        if(getGRD( 1, 2) == 1) { if(Result_Value() ==  5                       ) { reward(reward() +  21); setGRD( 1, 2,  2); } else { setGRD( 1, 2,  3); } }
        if(getGRD( 2, 2) == 1) { if(Result_Value() ==  6                       ) { reward(reward() +  19); setGRD( 2, 2,  2); } else { setGRD( 2, 2,  3); } }
        if(getGRD( 3, 2) == 1) { if(Result_Value() ==  7                       ) { reward(reward() +  13); setGRD( 3, 2,  2); } else { setGRD( 3, 2,  3); } }
        if(getGRD( 4, 2) == 1) { if(Result_Value() ==  8                       ) { reward(reward() +   9); setGRD( 4, 2,  2); } else { setGRD( 4, 2,  3); } }
        if(getGRD( 5, 2) == 1) { if(Result_Double(0, 0)                        ) { reward(reward() +  12); setGRD( 5, 2,  2); } else { setGRD( 5, 2,  3); } }
        if(getGRD( 6, 2) == 1) { if(Result_Double(0, 1)                        ) { reward(reward() +   7); setGRD( 6, 2,  2); } else { setGRD( 6, 2,  3); } }
        if(getGRD( 7, 2) == 1) { if(Result_Double(0, 2)                        ) { reward(reward() +   7); setGRD( 7, 2,  2); } else { setGRD( 7, 2,  3); } }
        if(getGRD( 8, 2) == 1) { if(Result_Double(0, 3)                        ) { reward(reward() +   7); setGRD( 8, 2,  2); } else { setGRD( 8, 2,  3); } }
        if(getGRD( 9, 2) == 1) { if(Result_Double(0, 4)                        ) { reward(reward() +   7); setGRD( 9, 2,  2); } else { setGRD( 9, 2,  3); } }
        if(getGRD(10, 2) == 1) { if(Result_Double(0, 5)                        ) { reward(reward() +   7); setGRD(10, 2,  2); } else { setGRD(10, 2,  3); } }
        if(getGRD(11, 2) == 1) { if(Result_Double(1, 1)                        ) { reward(reward() +  12); setGRD(11, 2,  2); } else { setGRD(11, 2,  3); } }

        if(getGRD( 0, 3) == 1) { if(Result_Value() ==  9                       ) { reward(reward() +   7); setGRD( 0, 3,  2); } else { setGRD( 0, 3,  3); } }
        if(getGRD( 1, 3) == 1) { if(Result_Value() == 10                       ) { reward(reward() +   7); setGRD( 1, 3,  2); } else { setGRD( 1, 3,  3); } }
        if(getGRD( 2, 3) == 1) { if(Result_Value() == 11                       ) { reward(reward() +   7); setGRD( 2, 3,  2); } else { setGRD( 2, 3,  3); } }
        if(getGRD( 3, 3) == 1) { if(Result_Value() == 12                       ) { reward(reward() +   7); setGRD( 3, 3,  2); } else { setGRD( 3, 3,  3); } }
        if(getGRD( 4, 3) == 1) { if(Result_Value() == 13                       ) { reward(reward() +   7); setGRD( 4, 3,  2); } else { setGRD( 4, 3,  3); } }
        if(getGRD( 5, 3) == 1) { if(Result_Double(1, 2)                        ) { reward(reward() +   7); setGRD( 5, 3,  2); } else { setGRD( 5, 3,  3); } }
        if(getGRD( 6, 3) == 1) { if(Result_Double(1, 3)                        ) { reward(reward() +   7); setGRD( 6, 3,  2); } else { setGRD( 6, 3,  3); } }
        if(getGRD( 7, 3) == 1) { if(Result_Double(1, 4)                        ) { reward(reward() +   7); setGRD( 7, 3,  2); } else { setGRD( 7, 3,  3); } }
        if(getGRD( 8, 3) == 1) { if(Result_Double(1, 5)                        ) { reward(reward() +   7); setGRD( 8, 3,  2); } else { setGRD( 8, 3,  3); } }
        if(getGRD( 9, 3) == 1) { if(Result_Double(2, 2)                        ) { reward(reward() +  12); setGRD( 9, 3,  2); } else { setGRD( 9, 3,  3); } }
        if(getGRD(10, 3) == 1) { if(Result_Double(2, 3)                        ) { reward(reward() +   7); setGRD(10, 3,  2); } else { setGRD(10, 3,  3); } }
        if(getGRD(11, 3) == 1) { if(Result_Double(2, 4)                        ) { reward(reward() +   7); setGRD(11, 3,  2); } else { setGRD(11, 3,  3); } }

        if(getGRD( 0, 4) == 1) { if(Result_Value() == 14                       ) { reward(reward() +   7); setGRD( 0, 4,  2); } else { setGRD( 0, 4,  3); } }
        if(getGRD( 1, 4) == 1) { if(Result_Value() == 15                       ) { reward(reward() +   7); setGRD( 1, 4,  2); } else { setGRD( 1, 4,  3); } }
        if(getGRD( 2, 4) == 1) { if(Result_Value() == 16                       ) { reward(reward() +   7); setGRD( 2, 4,  2); } else { setGRD( 2, 4,  3); } }
        if(getGRD( 3, 4) == 1) { if(Result_Value() == 17                       ) { reward(reward() +   7); setGRD( 3, 4,  2); } else { setGRD( 3, 4,  3); } }
        if(getGRD( 4, 4) == 1) {                                                                                             setGRD( 4, 4,  3);   }
        if(getGRD( 5, 4) == 1) { if(Result_Double(2, 5)                        ) { reward(reward() +   7); setGRD( 5, 4,  2); } else { setGRD( 5, 4,  3); } }
        if(getGRD( 6, 4) == 1) { if(Result_Double(3, 3)                        ) { reward(reward() +  12); setGRD( 6, 4,  2); } else { setGRD( 6, 4,  3); } }
        if(getGRD( 7, 4) == 1) { if(Result_Double(3, 4)                        ) { reward(reward() +   7); setGRD( 7, 4,  2); } else { setGRD( 7, 4,  3); } }
        if(getGRD( 8, 4) == 1) { if(Result_Double(3, 5)                        ) { reward(reward() +   7); setGRD( 8, 4,  2); } else { setGRD( 8, 4,  3); } }
        if(getGRD( 9, 4) == 1) { if(Result_Double(4, 4)                        ) { reward(reward() +  12); setGRD( 9, 4,  2); } else { setGRD( 9, 4,  3); } }
        if(getGRD(10, 4) == 1) { if(Result_Double(4, 5)                        ) { reward(reward() +   7); setGRD(10, 4,  2); } else { setGRD(10, 4,  3); } }
        if(getGRD(11, 4) == 1) { if(Result_Double(5, 5)                        ) { reward(reward() +  12); setGRD(11, 4,  2); } else { setGRD(11, 4,  3); } }

        if(getGRD( 0, 5) == 1) { if(Result_Triple(0)) { reward(reward() + 6); setGRD( 0, 5,  2); } else if(Result_Double(0, 0)) { reward(reward() + 3); setGRD( 0, 5,  2); } else if(Result_Single(0)) { reward(reward() + 2); setGRD( 0, 5,  2); } else { setGRD( 0, 5,  3); } }
        if(getGRD( 1, 5) == 1) { if(Result_Triple(0)) { reward(reward() + 6); setGRD( 1, 5,  2); } else if(Result_Double(0, 0)) { reward(reward() + 3); setGRD( 1, 5,  2); } else if(Result_Single(0)) { reward(reward() + 2); setGRD( 1, 5,  2); } else { setGRD( 1, 5,  3); } }
        if(getGRD( 2, 5) == 1) { if(Result_Triple(1)) { reward(reward() + 6); setGRD( 2, 5,  2); } else if(Result_Double(1, 1)) { reward(reward() + 3); setGRD( 2, 5,  2); } else if(Result_Single(1)) { reward(reward() + 2); setGRD( 2, 5,  2); } else { setGRD( 2, 5,  3); } }
        if(getGRD( 3, 5) == 1) { if(Result_Triple(1)) { reward(reward() + 6); setGRD( 3, 5,  2); } else if(Result_Double(1, 1)) { reward(reward() + 3); setGRD( 3, 5,  2); } else if(Result_Single(1)) { reward(reward() + 2); setGRD( 3, 5,  2); } else { setGRD( 3, 5,  3); } }
        if(getGRD( 4, 5) == 1) { if(Result_Triple(2)) { reward(reward() + 6); setGRD( 4, 5,  2); } else if(Result_Double(2, 2)) { reward(reward() + 3); setGRD( 4, 5,  2); } else if(Result_Single(2)) { reward(reward() + 2); setGRD( 4, 5,  2); } else { setGRD( 4, 5,  3); } }
        if(getGRD( 5, 5) == 1) { if(Result_Triple(2)) { reward(reward() + 6); setGRD( 5, 5,  2); } else if(Result_Double(2, 2)) { reward(reward() + 3); setGRD( 5, 5,  2); } else if(Result_Single(2)) { reward(reward() + 2); setGRD( 5, 5,  2); } else { setGRD( 5, 5,  3); } }
        if(getGRD( 6, 5) == 1) { if(Result_Triple(3)) { reward(reward() + 6); setGRD( 6, 5,  2); } else if(Result_Double(3, 3)) { reward(reward() + 3); setGRD( 6, 5,  2); } else if(Result_Single(3)) { reward(reward() + 2); setGRD( 6, 5,  2); } else { setGRD( 6, 5,  3); } }
        if(getGRD( 7, 5) == 1) { if(Result_Triple(3)) { reward(reward() + 6); setGRD( 7, 5,  2); } else if(Result_Double(3, 3)) { reward(reward() + 3); setGRD( 7, 5,  2); } else if(Result_Single(3)) { reward(reward() + 2); setGRD( 7, 5,  2); } else { setGRD( 7, 5,  3); } }
        if(getGRD( 8, 5) == 1) { if(Result_Triple(4)) { reward(reward() + 6); setGRD( 8, 5,  2); } else if(Result_Double(4, 4)) { reward(reward() + 3); setGRD( 8, 5,  2); } else if(Result_Single(4)) { reward(reward() + 2); setGRD( 8, 5,  2); } else { setGRD( 8, 5,  3); } }
        if(getGRD( 9, 5) == 1) { if(Result_Triple(4)) { reward(reward() + 6); setGRD( 9, 5,  2); } else if(Result_Double(4, 4)) { reward(reward() + 3); setGRD( 9, 5,  2); } else if(Result_Single(4)) { reward(reward() + 2); setGRD( 9, 5,  2); } else { setGRD( 9, 5,  3); } }
        if(getGRD(10, 5) == 1) { if(Result_Triple(5)) { reward(reward() + 6); setGRD(10, 5,  2); } else if(Result_Double(5, 5)) { reward(reward() + 3); setGRD(10, 5,  2); } else if(Result_Single(5)) { reward(reward() + 2); setGRD(10, 5,  2); } else { setGRD(10, 5,  3); } }
        if(getGRD(11, 5) == 1) { if(Result_Triple(5)) { reward(reward() + 6); setGRD(11, 5,  2); } else if(Result_Double(5, 5)) { reward(reward() + 3); setGRD(11, 5,  2); } else if(Result_Single(5)) { reward(reward() + 2); setGRD(11, 5,  2); } else { setGRD(11, 5,  3); } }

        turnstate(4);
    }

    private int Result_Value() {
        return getDIE(D_DICE0).number + getDIE(D_DICE1).number + getDIE(D_DICE2).number + 3;
    }

    private boolean Result_Single(int n) {
        if(getDIE(D_DICE0).number == n) return true;
        if(getDIE(D_DICE1).number == n) return true;
        return getDIE(D_DICE2).number == n;
    }

    private boolean Result_Double(int n1, int n2) {
        return Result_Single(n1) && Result_Single(n2);
    }

    private boolean Result_Triple(int n) {
        return getDIE(D_DICE0).number == getDIE(D_DICE1).number && getDIE(D_DICE0).number == getDIE(D_DICE2).number && getDIE(D_DICE0).number == n;
    }

    private boolean Result_AnyTriple() {
        return getDIE(D_DICE0).number == getDIE(D_DICE1).number && getDIE(D_DICE0).number == getDIE(D_DICE2).number;
    }

}
