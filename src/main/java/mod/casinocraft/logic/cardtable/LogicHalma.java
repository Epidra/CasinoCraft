package mod.casinocraft.logic.cardtable;

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

public class LogicHalma extends LogicBase {

    //--------------------CONSTRUCTOR--------------------

    public LogicHalma(){
        super(false, true, false, 17, 9, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        if(difficulty == 2) {
            selector.set(8, 2);
            setGRD(0, 0, -1); setGRD(1, 0, -1); setGRD(2, 0, -1); setGRD(3, 0, -1); setGRD(4, 0, -1); setGRD(5, 0, -1); setGRD(6, 0,  1); setGRD(7, 0,  1); setGRD(8, 0,  1); setGRD(9, 0,  1); setGRD(10, 0,  1); setGRD(11, 0, -1); setGRD(12, 0, -1); setGRD(13, 0, -1); setGRD(14, 0, -1); setGRD(15, 0, -1); setGRD(16, 0, -1);
            setGRD(0, 1, -1); setGRD(1, 1, -1); setGRD(2, 1, -1); setGRD(3, 1, -1); setGRD(4, 1, -1); setGRD(5, 1, -1); setGRD(6, 1,  1); setGRD(7, 1,  1); setGRD(8, 1,  1); setGRD(9, 1,  1); setGRD(10, 1,  1); setGRD(11, 1, -1); setGRD(12, 1, -1); setGRD(13, 1, -1); setGRD(14, 1, -1); setGRD(15, 1, -1); setGRD(16, 1, -1);
            setGRD(0, 2, -1); setGRD(1, 2, -1); setGRD(2, 2, -1); setGRD(3, 2, -1); setGRD(4, 2,  1); setGRD(5, 2,  1); setGRD(6, 2,  1); setGRD(7, 2,  1); setGRD(8, 2,  1); setGRD(9, 2,  1); setGRD(10, 2,  1); setGRD(11, 2,  1); setGRD(12, 2,  1); setGRD(13, 2, -1); setGRD(14, 2, -1); setGRD(15, 2, -1); setGRD(16, 2, -1);
            setGRD(0, 3, -1); setGRD(1, 3, -1); setGRD(2, 3, -1); setGRD(3, 3, -1); setGRD(4, 3,  1); setGRD(5, 3,  1); setGRD(6, 3,  1); setGRD(7, 3,  1); setGRD(8, 3,  1); setGRD(9, 3,  1); setGRD(10, 3,  1); setGRD(11, 3,  1); setGRD(12, 3,  1); setGRD(13, 3, -1); setGRD(14, 3, -1); setGRD(15, 3, -1); setGRD(16, 3, -1);
            setGRD(0, 4, -1); setGRD(1, 4, -1); setGRD(2, 4, -1); setGRD(3, 4, -1); setGRD(4, 4,  1); setGRD(5, 4,  1); setGRD(6, 4,  1); setGRD(7, 4,  1); setGRD(8, 4,  0); setGRD(9, 4,  1); setGRD(10, 4,  1); setGRD(11, 4,  1); setGRD(12, 4,  1); setGRD(13, 4, -1); setGRD(14, 4, -1); setGRD(15, 4, -1); setGRD(16, 4, -1);
            setGRD(0, 5, -1); setGRD(1, 5, -1); setGRD(2, 5, -1); setGRD(3, 5, -1); setGRD(4, 5,  1); setGRD(5, 5,  1); setGRD(6, 5,  1); setGRD(7, 5,  1); setGRD(8, 5,  1); setGRD(9, 5,  1); setGRD(10, 5,  1); setGRD(11, 5,  1); setGRD(12, 5,  1); setGRD(13, 5, -1); setGRD(14, 5, -1); setGRD(15, 5, -1); setGRD(16, 5, -1);
            setGRD(0, 6, -1); setGRD(1, 6, -1); setGRD(2, 6, -1); setGRD(3, 6, -1); setGRD(4, 6,  1); setGRD(5, 6,  1); setGRD(6, 6,  1); setGRD(7, 6,  1); setGRD(8, 6,  1); setGRD(9, 6,  1); setGRD(10, 6,  1); setGRD(11, 6,  1); setGRD(12, 6,  1); setGRD(13, 6, -1); setGRD(14, 6, -1); setGRD(15, 6, -1); setGRD(16, 6, -1);
            setGRD(0, 7, -1); setGRD(1, 7, -1); setGRD(2, 7, -1); setGRD(3, 7, -1); setGRD(4, 7, -1); setGRD(5, 7, -1); setGRD(6, 7,  1); setGRD(7, 7,  1); setGRD(8, 7,  1); setGRD(9, 7,  1); setGRD(10, 7,  1); setGRD(11, 7, -1); setGRD(12, 7, -1); setGRD(13, 7, -1); setGRD(14, 7, -1); setGRD(15, 7, -1); setGRD(16, 7, -1);
            setGRD(0, 8, -1); setGRD(1, 8, -1); setGRD(2, 8, -1); setGRD(3, 8, -1); setGRD(4, 8, -1); setGRD(5, 8, -1); setGRD(6, 8,  1); setGRD(7, 8,  1); setGRD(8, 8,  1); setGRD(9, 8,  1); setGRD(10, 8,  1); setGRD(11, 8, -1); setGRD(12, 8, -1); setGRD(13, 8, -1); setGRD(14, 8, -1); setGRD(15, 8, -1); setGRD(16, 8, -1);
        } else {
            selector.set(8, 4);
            setGRD(0, 0, -1); setGRD(1, 0, -1); setGRD(2, 0, -1); setGRD(3, 0,  1); setGRD(4, 0,  1); setGRD(5, 0,  1); setGRD(6, 0,  1); setGRD(7, 0, -1); setGRD(8, 0, -1); setGRD(9, 0, -1); setGRD(10, 0,  1); setGRD(11, 0,  1); setGRD(12, 0,  1); setGRD(13, 0,  1); setGRD(14, 0, -1); setGRD(15, 0, -1); setGRD(16, 0, -1);
            setGRD(0, 1, -1); setGRD(1, 1, -1); setGRD(2, 1, -1); setGRD(3, 1,  1); setGRD(4, 1,  1); setGRD(5, 1,  1); setGRD(6, 1,  1); setGRD(7, 1, -1); setGRD(8, 1, -1); setGRD(9, 1, -1); setGRD(10, 1,  1); setGRD(11, 1,  1); setGRD(12, 1,  1); setGRD(13, 1,  1); setGRD(14, 1, -1); setGRD(15, 1, -1); setGRD(16, 1, -1);
            setGRD(0, 2,  1); setGRD(1, 2,  1); setGRD(2, 2,  1); setGRD(3, 2,  1); setGRD(4, 2,  1); setGRD(5, 2,  1); setGRD(6, 2,  1); setGRD(7, 2,  1); setGRD(8, 2,  1); setGRD(9, 2,  1); setGRD(10, 2,  1); setGRD(11, 2,  1); setGRD(12, 2,  1); setGRD(13, 2,  1); setGRD(14, 2,  1); setGRD(15, 2,  1); setGRD(16, 2,  1);
            setGRD(0, 3,  1); setGRD(1, 3,  1); setGRD(2, 3,  1); setGRD(3, 3,  1); setGRD(4, 3,  1); setGRD(5, 3,  1); setGRD(6, 3,  1); setGRD(7, 3,  1); setGRD(8, 3,  1); setGRD(9, 3,  1); setGRD(10, 3,  1); setGRD(11, 3,  1); setGRD(12, 3,  1); setGRD(13, 3,  1); setGRD(14, 3,  1); setGRD(15, 3,  1); setGRD(16, 3,  1);
            setGRD(0, 4,  1); setGRD(1, 4,  1); setGRD(2, 4,  1); setGRD(3, 4,  1); setGRD(4, 4,  0); setGRD(5, 4,  0); setGRD(6, 4,  1); setGRD(7, 4,  1); setGRD(8, 4,  1); setGRD(9, 4,  1); setGRD(10, 4,  1); setGRD(11, 4,  0); setGRD(12, 4,  0); setGRD(13, 4,  1); setGRD(14, 4,  1); setGRD(15, 4,  1); setGRD(16, 4,  1);
            setGRD(0, 5,  1); setGRD(1, 5,  1); setGRD(2, 5,  1); setGRD(3, 5,  1); setGRD(4, 5,  1); setGRD(5, 5,  1); setGRD(6, 5,  1); setGRD(7, 5,  1); setGRD(8, 5,  1); setGRD(9, 5,  1); setGRD(10, 5,  1); setGRD(11, 5,  1); setGRD(12, 5,  1); setGRD(13, 5,  1); setGRD(14, 5,  1); setGRD(15, 5,  1); setGRD(16, 5,  1);
            setGRD(0, 6,  1); setGRD(1, 6,  1); setGRD(2, 6,  1); setGRD(3, 6,  1); setGRD(4, 6,  1); setGRD(5, 6,  1); setGRD(6, 6,  1); setGRD(7, 6,  1); setGRD(8, 6,  1); setGRD(9, 6,  1); setGRD(10, 6,  1); setGRD(11, 6,  1); setGRD(12, 6,  1); setGRD(13, 6,  1); setGRD(14, 6,  1); setGRD(15, 6,  1); setGRD(16, 6,  1);
            setGRD(0, 7, -1); setGRD(1, 7, -1); setGRD(2, 7, -1); setGRD(3, 7,  1); setGRD(4, 7,  1); setGRD(5, 7,  1); setGRD(6, 7,  1); setGRD(7, 7, -1); setGRD(8, 7, -1); setGRD(9, 7, -1); setGRD(10, 7,  1); setGRD(11, 7,  1); setGRD(12, 7,  1); setGRD(13, 7,  1); setGRD(14, 7, -1); setGRD(15, 7, -1); setGRD(16, 7, -1);
            setGRD(0, 8, -1); setGRD(1, 8, -1); setGRD(2, 8, -1); setGRD(3, 8,  1); setGRD(4, 8,  1); setGRD(5, 8,  1); setGRD(6, 8,  1); setGRD(7, 8, -1); setGRD(8, 8, -1); setGRD(9, 8, -1); setGRD(10, 8,  1); setGRD(11, 8,  1); setGRD(12, 8,  1); setGRD(13, 8,  1); setGRD(14, 8, -1); setGRD(15, 8, -1); setGRD(16, 8, -1);
        }
    }

    public void actionTouch(int action){
        Jump(action%17, action/17);
    }

    public void updateMotion(){

    }

    public void updateLogic(){

    }



    //--------------------CUSTOM--------------------

    private void Jump(int x, int y) {
        if(getGRD(x, y) == 1) {
            selector.set(x, y);
            scoreLevel(1);
        } else if(getGRD(x, y) == 0) {
            if(y + 2 == selector.Y && getGRD(selector.X, selector.Y - 1) == 1) { // Jump to UP
                setGRD(x, y    , 1);
                setGRD(x, y + 1, 0);
                setGRD(x, y + 2, 0);
                selector.set(x, y);
                scoreScore(scoreLevel());
                scoreLevel(1);
            } else if(y - 2 == selector.Y && getGRD(selector.X, selector.Y + 1) == 1) { // Jump to DOWN
                setGRD(x, y    , 1);
                setGRD(x, y - 1, 0);
                setGRD(x, y - 2, 0);
                selector.set(x, y);
                scoreScore(scoreLevel());
                scoreLevel(1);
            } else if(x + 2 == selector.X && getGRD(selector.X - 1, selector.Y) == 1) { // Jump to LEFT
                setGRD(x    , y, 1);
                setGRD(x + 1, y, 0);
                setGRD(x + 2, y, 0);
                selector.set(x, y);
                scoreScore(scoreLevel());
                scoreLevel(1);
            } else if(x - 2 == selector.X && getGRD(selector.X + 1, selector.Y) == 1) { // Jump to RIGHT
                setGRD(x    , y, 1);
                setGRD(x - 1, y, 0);
                setGRD(x - 2, y, 0);
                selector.set(x, y);
                scoreScore(scoreLevel());
                scoreLevel(1);
            }
            Check_For_GameOver();
        }
    }

    private void Check_For_GameOver() {
        boolean canJump = false;
        int counter = 0;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(getGRD(x, y) == 1) {
                    counter++;
                    if(y >=  2 && getGRD(x, y - 1) == 1 && getGRD(x, y - 2) == 0) canJump = true;
                    if(y <=  6 && getGRD(x, y + 1) == 1 && getGRD(x, y + 2) == 0) canJump = true;
                    if(x >=  2 && getGRD(x - 1, y) == 1 && getGRD(x - 2, y) == 0) canJump = true;
                    if(x <= 14 && getGRD(x + 1, y) == 1 && getGRD(x + 2, y) == 0) canJump = true;
                }
            }
        }
        if(counter == 1) turnstate(4);
        if(!canJump) turnstate(4);
    }

}
