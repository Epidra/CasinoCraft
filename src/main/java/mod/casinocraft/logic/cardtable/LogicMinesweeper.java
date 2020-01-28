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
import java.util.ArrayList;
import java.util.List;

public class LogicMinesweeper extends LogicBase {

    List<Vector2> FieldList = new ArrayList<Vector2>();

    //boolean[][] grid_cover = new boolean[14][14];
    //boolean[][] grid_flag  = new boolean[30][14];
    //    int[][] grid_base  = new     int[14][14];

    public final int I_BOMBS = 0;

    //int bombs;



    //--------------------CONSTRUCTOR--------------------

    public LogicMinesweeper(){
        super(false, true, false, 26, 14, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        setINT(I_BOMBS, 0);
        selector = new Vector2(5, 5);
        FieldList.clear();
        scoreLevel(1);
        Create_Field();
    }

    public void Restart() {
        turnstate(2);
        FieldList.clear();
        scoreLevel(1);
        Create_Field();
    }

    public void actionTouch(int action){
        if(action == -1) {
            Restart();
        } else if(action == -2) {
            turnstate(4);
        } else {
            selector = new Vector2(action%26, action/26);
            Command_Grid_Enter();
        }
    }

    public void updateMotion(){

    }

    public void updateLogic(){

    }



    //--------------------CUSTOM--------------------

    private void Create_Field() {
        for(int i = 0; i < 14; i++) {
            for(int j = 0; j < 26; j++) {
                setGRD(j, i, 0);
            }
        }
        setINT(I_BOMBS, scoreLevel() * difficulty);
        int max = difficulty == 2 ? 14*14 : 14*26;
        max -= max/4;
        if(getINT(I_BOMBS) > max) setINT(I_BOMBS, max);
        for(int i = 0; i < getINT(I_BOMBS); i++) {
            int x = RANDOM.nextInt(difficulty == 2 ? 14 : 26) + (difficulty == 2 ? 6 : 0);
            int y = RANDOM.nextInt(14);
            if(getGRD(x, y) != 9) {
                setGRD(x, y, 9);
            } else {
                i--;
            }
        }
        for(int y = 0; y < 14; y++) {
            for(int x = difficulty == 2 ? 6 : 0; x < (difficulty == 2 ? 20 : 26); x++) {
                setFLG(x, y, true);
                if(getGRD(x, y) != 9) {
                    int count = 0;
                    if(x >  0 && y >  0) if(getGRD(x - 1, y - 1) == 9) count++; // -X -Y
                    if(          y >  0) if(getGRD(x    , y - 1) == 9) count++; //    -Y
                    if(x < 25 && y >  0) if(getGRD(x + 1, y - 1) == 9) count++; // +X -Y
                    if(x < 25          ) if(getGRD(x + 1, y    ) == 9) count++; // +X
                    if(x < 25 && y < 13) if(getGRD(x + 1, y + 1) == 9) count++; // +X +Y
                    if(          y < 13) if(getGRD(x    , y + 1) == 9) count++; //    +Y
                    if(x >  0 && y < 13) if(getGRD(x - 1, y + 1) == 9) count++; // -X +Y
                    if(x >  0          ) if(getGRD(x - 1, y    ) == 9) count++; // -X
                    setGRD(x, y, count);
                }
            }
        }
        if(difficulty == 2) {
            for(int y = 0; y < 6; y++) {
                for(int x = 0; x < 6; x++) {

                }
            }
        }
    }

    private void Command_Grid_Enter() {
        if(getFLG(selector.X, selector.Y)) {
            //if(active_flag) {
            //    if(grid_flag[(int)selector.X][(int)selector.Y]) {
            //        grid_flag[(int)selector.X][(int)selector.Y] = false;
            //    } else {
            //        grid_flag[(int)selector.X][(int)selector.Y] = true;
            //    }
            //} else {
            setFLG(selector.X, selector.Y, false);
            if(getGRD(selector.X, selector.Y) == 9) {
                setGRD(selector.X, selector.Y, 10);
                Uncover_Bombs();
                turnstate(4);
                scoreScore(-(scoreScore() / 2));
            } else {
                if(getGRD(selector.X, selector.Y) == 0) {
                    FieldList.add(selector);
                    Uncover_Tiles();
                }
                boolean temp = false;
                for(int i = 0; i < 14; i++) {
                    for(int j = 0; j < 26; j++) {
                        if(getGRD(j, i) != 9) {
                            if(getFLG(j, i)) {
                                temp = true;
                            }
                        }
                    }
                }
                if(!temp) {
                    turnstate(3);
                    scoreScore(getINT(I_BOMBS));
                }
            }
            //}
        }
    }

    private void Uncover_Tiles() {
        while(FieldList.size() > 0) {
            boolean temp0 = false;
            for(Vector2 v : FieldList) {
                if(v.X >  0 && v.Y >  0) { if(getFLG(v.X - 1, v.Y - 1)) { setFLG(v.X - 1, v.Y - 1, false); if(getGRD(v.X - 1, v.Y - 1) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y - 1)); temp0 = true; break; } } } } } // -X -Y
                if(            v.Y >  0) { if(getFLG(v.X    , v.Y - 1)) { setFLG(v.X    , v.Y - 1, false); if(getGRD(v.X    , v.Y - 1) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y - 1)); temp0 = true; break; } } } } } //    -Y
                if(v.X < 25 && v.Y >  0) { if(getFLG(v.X + 1, v.Y - 1)) { setFLG(v.X + 1, v.Y - 1, false); if(getGRD(v.X + 1, v.Y - 1) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y - 1)); temp0 = true; break; } } } } } // +X -Y
                if(v.X < 25            ) { if(getFLG(v.X + 1, v.Y    )) { setFLG(v.X + 1, v.Y    , false); if(getGRD(v.X + 1, v.Y    ) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y    )); temp0 = true; break; } } } } } // +X
                if(v.X < 25 && v.Y < 13) { if(getFLG(v.X + 1, v.Y + 1)) { setFLG(v.X + 1, v.Y + 1, false); if(getGRD(v.X + 1, v.Y + 1) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y + 1)); temp0 = true; break; } } } } } // +X +Y
                if(            v.Y < 13) { if(getFLG(v.X    , v.Y + 1)) { setFLG(v.X    , v.Y + 1, false); if(getGRD(v.X    , v.Y + 1) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y + 1)); temp0 = true; break; } } } } } //    +Y
                if(v.X >  0 && v.Y < 13) { if(getFLG(v.X - 1, v.Y + 1)) { setFLG(v.X - 1, v.Y + 1, false); if(getGRD(v.X - 1, v.Y + 1) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y + 1)); temp0 = true; break; } } } } } // -X +Y
                if(v.X >  0            ) { if(getFLG(v.X - 1, v.Y    )) { setFLG(v.X - 1, v.Y    , false); if(getGRD(v.X - 1, v.Y    ) == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y    )); temp0 = true; break; } } } } } // -X
                break;
            }
            if(!temp0) FieldList.remove(0);
        }
    }

    private void Uncover_Bombs() {
        for(int y = 0; y < 14; y++) {
            for(int x = 0; x < 26; x++) {
                if(getGRD(x, y) == 9) {
                    setFLG(x, y, false);
                }
            }
        }
    }

}
