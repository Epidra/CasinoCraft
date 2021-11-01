package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicModule;
import mod.lucky77.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

import static mod.casinocraft.util.SoundMap.SOUND_CHIP;

public class LogicMinoLightGray extends LogicModule {   // Minesweeper

    private List<Vector2> FieldList = new ArrayList<Vector2>();
    public int bombs = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoLightGray(int tableID){
        super(tableID, 26, 14);
    }





    //----------------------------------------START----------------------------------------//

    public void start2(){
        bombs = 0;
        FieldList.clear();
        scoreLevel = 1;
        fillGrid();
    }

    private void restart() {
        turnstate = 2;
        FieldList.clear();
        scoreLevel++;
        resetGrid();
        fillGrid();
    }





    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == -1) {
            restart();
        } else if(action == -2) {
            turnstate = 4;
        } else if(action >= 1000){
            setJingle(SOUND_CHIP);
            mark(action - 1000);
        } else {
            setJingle(SOUND_CHIP);
            flip(action);
        }
    }





    //----------------------------------------UPDATE----------------------------------------//

    public void updateLogic(){

    }

    public void updateMotion(){

    }





    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        bombs = compound.getInt("bombs");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putInt("bombs", bombs);
        return compound;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void fillGrid() {
        bombs = scoreLevel + scoreLevel * tableID * 3;
        int max = tableID == 1 ? 14*14 : 14*26;
        max -= max/4;
        if(bombs > max) bombs = max;
        for(int i = 0; i < bombs; i++) {
            int x = RANDOM.nextInt(tableID == 1 ? 14 : 26) + (tableID == 1 ? 6 : 0);
            int y = RANDOM.nextInt(14);
            if(grid[x][y] != 9) {
                grid[x][y] = 9;
            } else {
                i--;
            }
        }
        for(int y = 0; y < 14; y++) {
            for(int x = tableID == 1 ? 6 : 0; x < (tableID == 1 ? 20 : 26); x++) {
                if(grid[x][y] != 9) {
                    int count = 0;
                    if(x >  0 && y >  0) if(grid[x - 1][y - 1] % 100 == 9) count++; // -X -Y
                    if(          y >  0) if(grid[x    ][y - 1] % 100 == 9) count++; //    -Y
                    if(x < 25 && y >  0) if(grid[x + 1][y - 1] % 100 == 9) count++; // +X -Y
                    if(x < 25          ) if(grid[x + 1][y    ] % 100 == 9) count++; // +X
                    if(x < 25 && y < 13) if(grid[x + 1][y + 1] % 100 == 9) count++; // +X +Y
                    if(          y < 13) if(grid[x    ][y + 1] % 100 == 9) count++; //    +Y
                    if(x >  0 && y < 13) if(grid[x - 1][y + 1] % 100 == 9) count++; // -X +Y
                    if(x >  0          ) if(grid[x - 1][y    ] % 100 == 9) count++; // -X
                    grid[x][y] = count;
                }
                grid[x][y] += 100;
            }
        }
    }

    private void flip(int action) {
        int x = action % 26;
        int y = action / 26;
        if(grid[x][y] >= 100) {
            grid[x][y] = grid[x][y] % 100;
            if(grid[x][y] == 9) {
                grid[x][y] = 10;
                uncoverBombs();
                turnstate = 4;
                scorePoint = scorePoint / 2;
            } else {
                if(grid[x][y] == 0) {
                    FieldList.add(new Vector2(x, y));
                    uncoverTiles();
                }
                boolean uncoveredAll = true;
                for(int i = 0; i < 14; i++) {
                    for(int j = 0; j < 26; j++) {
                        if(grid[j][i] % 20 != 9) {
                            if(grid[j][i] >= 20) {
                                uncoveredAll = false;
                            }
                        }
                    }
                }
                if(uncoveredAll) {
                    turnstate = 3;
                    scorePoint += bombs;
                }
            }
        }
    }

    private void mark(int action){
        int x = action % 26;
        int y = action / 26;
        if(grid[x][y] >= 200){
            grid[x][y] -= 100;
        } else if(grid[x][y] >= 100){
            grid[x][y] += 100;
        }
    }

    private void uncoverTiles() {
        while(FieldList.size() > 0) {
            boolean temp0 = false;
            for(Vector2 v : FieldList) {
                if(v.X >  0 && v.Y >  0) { if(grid[v.X - 1][v.Y - 1] >= 100) { grid[v.X - 1][v.Y - 1] %= 100; if(grid[v.X - 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y - 1)); temp0 = true; break; } } } } } // -X -Y
                if(            v.Y >  0) { if(grid[v.X    ][v.Y - 1] >= 100) { grid[v.X    ][v.Y - 1] %= 100; if(grid[v.X    ][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y - 1)); temp0 = true; break; } } } } } //    -Y
                if(v.X < 25 && v.Y >  0) { if(grid[v.X + 1][v.Y - 1] >= 100) { grid[v.X + 1][v.Y - 1] %= 100; if(grid[v.X + 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y - 1)); temp0 = true; break; } } } } } // +X -Y
                if(v.X < 25            ) { if(grid[v.X + 1][v.Y    ] >= 100) { grid[v.X + 1][v.Y    ] %= 100; if(grid[v.X + 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y    )); temp0 = true; break; } } } } } // +X
                if(v.X < 25 && v.Y < 13) { if(grid[v.X + 1][v.Y + 1] >= 100) { grid[v.X + 1][v.Y + 1] %= 100; if(grid[v.X + 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y + 1)); temp0 = true; break; } } } } } // +X +Y
                if(            v.Y < 13) { if(grid[v.X    ][v.Y + 1] >= 100) { grid[v.X    ][v.Y + 1] %= 100; if(grid[v.X    ][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y + 1)); temp0 = true; break; } } } } } //    +Y
                if(v.X >  0 && v.Y < 13) { if(grid[v.X - 1][v.Y + 1] >= 100) { grid[v.X - 1][v.Y + 1] %= 100; if(grid[v.X - 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y + 1)); temp0 = true; break; } } } } } // -X +Y
                if(v.X >  0            ) { if(grid[v.X - 1][v.Y    ] >= 100) { grid[v.X - 1][v.Y    ] %= 100; if(grid[v.X - 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y    )); temp0 = true; break; } } } } } // -X
                break;
            }
            if(!temp0) FieldList.remove(0);
        }
    }

    private void uncoverBombs() {
        for(int y = 0; y < 14; y++) {
            for(int x = 0; x < 26; x++) {
                if(grid[x][y]  % 100 == 9) {
                    grid[x][y] = 9;
                }
            }
        }
    }





    //----------------------------------------BASIC----------------------------------------//

    public boolean hasHighscore(){
        return true;
    }

    public boolean isMultiplayer(){
        return false;
    }

    public int getID(){
        return 39;
    }



}
