package mod.casinocraft.logic.arcade;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class Logic2048 extends LogicBase {

    final int B_PLACING = 0;
    final int B_TIMER = 1;

    final int I_TIMER = 0;
    final int I_DIRECTION = 1; // 0 - null, 1 - up, 2 - down, 3 - left, 4 - right



    //--------------------CONSTRUCTOR--------------------

    public Logic2048(){
        super(false, true, false, 4, 4, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                setGRD(x, y, 0);
            }
        }
        setBOL(B_PLACING, false);
        setGRD(0, 0, RANDOM.nextInt(1) + 1);
        setGRD(3, 0, RANDOM.nextInt(1) + 1);
        setGRD(0, 3, RANDOM.nextInt(1) + 1);
        setGRD(3, 3, RANDOM.nextInt(1) + 1);
        setBOL(B_TIMER, false);
        setINT(I_TIMER, 0);
        setINT(I_DIRECTION, 0);
    }

    public void actionTouch(int action){
        if(action == 0) { Move(1); }
        if(action == 1) { Move(2); }
        if(action == 2) { Move(3); }
        if(action == 3) { Move(4); }
    }

    public void updateLogic(){
        if(getBOL(B_TIMER)) {
            updINT(I_TIMER, 6);
            if(getINT(I_TIMER) == 48) {
                setBOL(B_TIMER, false);
                setINT(I_TIMER, 0);
                Change();
                Move(getINT(I_DIRECTION));
                setBOL(B_PLACING, true);
            }
        } else {
            if(getBOL(B_PLACING)) {
                Place();
                setBOL(B_PLACING, false);
            }
            setINT(I_DIRECTION, 0);
        }
    }

    public void updateMotion() {

    }



    //--------------------CUSTOM--------------------

    private void Move(int s) {
        if(s == 1) { // up
            for(int y = 1; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(getGRD(x, y) != 0) {
                        if(getGRD(x, y - 1) == 0 || getGRD(x, y - 1) == getGRD(x, y)) {
                            setFLG(x, y, true);
                            setBOL(B_TIMER, true);
                            setINT(I_DIRECTION, s);
                        }
                    }
                }
            }
        }
        if(s == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(getGRD(x, y) != 0) {
                        if(getGRD(x, y + 1) == 0 || getGRD(x, y + 1) == getGRD(x, y)) {
                            setFLG(x, y, true);
                            setBOL(B_TIMER, true);
                            setINT(I_DIRECTION, s);
                        }
                    }
                }
            }
        }
        if(s == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(getGRD(x, y) != 0) {
                        if(getGRD(x - 1, y) == 0 || getGRD(x - 1, y) == getGRD(x, y)) {
                            setFLG(x, y, true);
                            setBOL(B_TIMER, true);
                            setINT(I_DIRECTION, s);
                        }
                    }
                }
            }
        }
        if(s == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(getGRD(x, y) != 0) {
                        if(getGRD(x + 1, y) == 0 || getGRD(x + 1, y) == getGRD(x, y)) {
                            setFLG(x, y, true);
                            setBOL(B_TIMER, true);
                            setINT(I_DIRECTION, s);
                        }
                    }
                }
            }
        }
    }

    private void Change() {
        if(getINT(I_DIRECTION) == 1) { // up
            for(int y = 1; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(getFLG(x, y)) {
                        if(getGRD(x, y - 1) == 0) {
                            setGRD(x, y - 1, getGRD(x, y));
                        } else {
                            setGRD(x, y - 1, getGRD(x, y - 1) + 1);
                            Add_Points(getGRD(x, y - 1));
                        }
                        setGRD(x, y, 0); // sets FLAG automatically to FALSE
                    }
                }
            }
        }
        if(getINT(I_DIRECTION) == 2) { // down
            for(int y = 2; y > -1; y--) {
                for(int x = 3; x > -1; x--) {
                    if(getFLG(x, y)) {
                        if(getGRD(x, y + 1) == 0) {
                            setGRD(x, y + 1, getGRD(x, y));
                        } else {
                            setGRD(x, y + 1, getGRD(x, y+1) + 1);
                            Add_Points(getGRD(x, y + 1));
                        }
                        setGRD(x, y, 0);
                    }
                }
            }
        }
        if(getINT(I_DIRECTION) == 3) { // left
            for(int x = 1; x < 4; x++) {
                for(int y = 0; y < 4; y++) {
                    if(getFLG(x, y)) {
                        if(getGRD(x - 1, y) == 0) {
                            setGRD(x - 1, y, getGRD(x, y));
                        } else {
                            setGRD(x - 1, y, getGRD(x - 1, y) + 1);
                            Add_Points(getGRD(x - 1, y));
                        }
                        setGRD(x, y, 0);
                    }
                }
            }
        }
        if(getINT(I_DIRECTION) == 4) { // right
            for(int x = 2; x > -1; x--) {
                for(int y = 3; y > -1; y--) {
                    if(getFLG(x, y)) {
                        if(getGRD(x + 1, y) == 0) {
                            setGRD(x + 1, y, getGRD(x, y));
                        } else {
                            setGRD(x + 1, y, getGRD(x + 1, y) + 1);
                            Add_Points(getGRD(x + 1, y));
                        }
                        setGRD(x, y, 0);
                    }
                }
            }
        }
    }

    private void Place() {
        for(int i = 0; i < 24; i++) {
            int x = RANDOM.nextInt(4);
            int y = RANDOM.nextInt(4);
            if(getGRD(x, y) == 0) { // FLAGGING ???
                setGRD(x, y, 1);
                break;
            }
        }
        Check();
    }

    private void Check() {
        boolean b = false;
        for(int y = 1; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                if(getGRD(x, y) != 0) {
                    if(getGRD(x, y - 1) == 0 || getGRD(x, y - 1) == getGRD(x, y)) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int y = 2; y > -1; y--) {
            for(int x = 3; x > -1; x--) {
                if(getGRD(x, y) != 0) {
                    if(getGRD(x, y + 1) == 0 || getGRD(x, y + 1) == getGRD(x, y)) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 1; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(getGRD(x, y) != 0) {
                    if(getGRD(x - 1, y) == 0 || getGRD(x - 1, y) == getGRD(x, y)) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        for(int x = 2; x > -1; x--) {
            for(int y = 3; y > -1; y--) {
                if(getGRD(x, y) != 0) {
                    if(getGRD(x + 1, y) == 0 || getGRD(x + 1, y) == getGRD(x, y)) {
                        b = true;
                        break;
                    }
                }
            }
            if(b) break;
        }
        if(!b) {
            turnstate(4);
        }
    }

    private int Get_Direction(boolean horizontal, int x, int y) {
        if(getINT(I_DIRECTION) == 0)
            return 0;
        if( horizontal && getINT(I_DIRECTION) == 3) if(getFLG(x, y)) return -getINT(I_TIMER); // left
        if( horizontal && getINT(I_DIRECTION) == 4) if(getFLG(x, y)) return  getINT(I_TIMER); // right
        if(!horizontal && getINT(I_DIRECTION) == 1) if(getFLG(x, y)) return -getINT(I_TIMER); // up
        if(!horizontal && getINT(I_DIRECTION) == 2) if(getFLG(x, y)) return  getINT(I_TIMER); // down
        return 0;
    }

    private void Add_Points(int i) {
        if(i ==  1) scoreScore(    2);
        if(i ==  2) scoreScore(    4);
        if(i ==  3) scoreScore(    8);
        if(i ==  4) scoreScore(   16);
        if(i ==  5) scoreScore(   32);
        if(i ==  6) scoreScore(   64);
        if(i ==  7) scoreScore(  128);
        if(i ==  8) scoreScore(  256);
        if(i ==  9) scoreScore(  512);
        if(i == 10) scoreScore( 1024);
        if(i == 11) scoreScore( 2048);
        if(i == 12) scoreScore( 4096);
        if(i == 13) scoreScore( 8192);
        if(i == 14) scoreScore(16384);
        if(i == 15) scoreScore(32768);
        if(i == 16) scoreScore(65536);
    }

}
