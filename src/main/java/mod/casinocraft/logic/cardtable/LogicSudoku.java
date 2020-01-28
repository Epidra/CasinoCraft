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

public class LogicSudoku extends LogicBase {

    //int[, ] grid        = new int[9, 9];
    //int[, ] grid_mirror = new int[9, 9];

    boolean match;



    //--------------------CONSTRUCTOR--------------------

    public LogicSudoku(){
        super(false, true, false, 9, 9, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        match = false;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                setGRD(x, y, 0);
            }
        }

        int r = RANDOM.nextInt(9)+1;
        setGRD(4, 4, r);
        setFLG(4, 4, true);
        Generate_Square(5 - difficulty, 3, 0);
        Generate_Square(5 - difficulty, 0, 3);
        Generate_Square(5 - difficulty, 6, 3);
        Generate_Square(5 - difficulty, 3, 6);

        Generate_Square(5 - difficulty, 0, 0);
        Generate_Square(5 - difficulty, 6, 0);
        Generate_Square(5 - difficulty, 0, 6);
        Generate_Square(5 - difficulty, 6, 6);
    }

    public void actionTouch(int action){
        if(action >= 100){
            int i = action - 100;
            selector.set(i%9, i/9);
        } else {
            setGRD(selector.X, selector.Y, action);
            Check();
        }
    }
    
    public void updateMotion(){
        
    }

    public void updateLogic(){
        if(match && turnstate() < 4) {
            turnstate(4);
        }
    }



    //--------------------CUSTOM--------------------

    private void Generate_Square(int count, int xi, int yi) {
        int index = 0;
        while(index < count) {
            int x = RANDOM.nextInt(3);
            int y = RANDOM.nextInt(3);
            int i = RANDOM.nextInt(9)+1;
            if(getGRD(0, yi + y) != i && getGRD(1, yi + y) != i && getGRD(2, yi + y) != i && getGRD(3, yi + y) != i && getGRD(4, yi + y) != i && getGRD(5, yi + y) != i && getGRD(6, yi + y) != i && getGRD(7, yi + y) != i && getGRD(8, yi + y) != i) {
                if(getGRD(xi + x, 0) != i && getGRD(xi + x, 1) != i && getGRD(xi + x, 2) != i && getGRD(xi + x, 3) != i && getGRD(xi + x, 4) != i && getGRD(xi + x, 5) != i && getGRD(xi + x, 6) != i && getGRD(xi + x, 7) != i && getGRD(xi + x, 8) != i) {
                    if(getGRD(xi + 0, yi + 0) != i && getGRD(xi + 1, yi + 0) != i && getGRD(xi + 2, yi + 0) != i && getGRD(xi + 0, yi + 1) != i && getGRD(xi + 1, yi + 1) != i && getGRD(xi + 2, yi + 1) != i && getGRD(xi + 0, yi + 2) != i && getGRD(xi + 1, yi + 2) != i && getGRD(xi + 2, yi + 2) != i) {
                        setGRD(xi + x, yi + y, i);
                        setFLG(xi + x, yi + y, true);
                        index++;
                    }
                }
            }
        }
    }

    private void Check() {
        boolean[] match_vert = new boolean[9];
        boolean[] match_hori = new boolean[9];
        boolean[] match_cube = new boolean[9];
        int[] n = new int[10];
        for(int i = 0; i < 9; i++) { n[i] = 0; }

        for(int y = 0; y < 9; y++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int x = 0; x < 9; x++) {
                n[getGRD(x, y)]++;
            }
            match_vert[y] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int x = 0; x < 9; x++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int y = 0; y < 9; y++) {
                n[getGRD(x, y)]++;
            }
            match_hori[x] = n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1;
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                for(int i = 0; i < 9; i++) { n[i] = 0; }
                for(int yi = y * 3; yi < y * 3 + 3; yi++) {
                    for(int xi = x * 3; xi < x * 3 + 3; xi++) {
                        n[getGRD(xi, yi)]++;
                    }
                }
                match_cube[y * 3 + x] = false;
                if(n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1) match_hori[y * 3 + x] = true;
            }
        }

        match = true;
        for(int i = 0; i < 9; i++) {
            if(!match_vert[i]) match = false;
            if(!match_hori[i]) match = false;
            if(!match_cube[i]) match = false;
        }
    }

}
