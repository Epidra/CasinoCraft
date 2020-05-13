package mod.casinocraft.logic.mino;

import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class LogicMinoLightGray extends LogicBase {   // Minesweeper

    private List<Vector2> FieldList = new ArrayList<Vector2>();
    public int bombs = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public LogicMinoLightGray(int tableID){
        super(tableID, 26, 14);
    }




    //----------------------------------------START----------------------------------------//

    public void start2(){
        bombs = 0;
        selector = new Vector2(5, 5);
        FieldList.clear();
        scoreLevel = 1;
        createField();
    }

    private void restart() {
        turnstate = 2;
        FieldList.clear();
        scoreLevel++;
        resetGrid();
        createField();
    }




    //----------------------------------------COMMAND----------------------------------------//

    public void command(int action){
        if(action == -1) {
            restart();
        } else if(action == -2) {
            turnstate = 4;
        } else {
            selector = new Vector2(action%26, action/26);
            commandGridEnter();
        }
    }




    //----------------------------------------UPDATE----------------------------------------//

    public void updateMotion(){

    }

    public void updateLogic(){

    }




    //----------------------------------------SAVE/LOAD----------------------------------------//

    public void load2(CompoundNBT compound){
        bombs = compound.getInt("bombs");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putInt("bombs", bombs);
        return compound;
    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void createField() {
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
                    if(x >  0 && y >  0) if(grid[x - 1][y - 1] % 20 == 9) count++; // -X -Y
                    if(          y >  0) if(grid[x    ][y - 1] % 20 == 9) count++; //    -Y
                    if(x < 25 && y >  0) if(grid[x + 1][y - 1] % 20 == 9) count++; // +X -Y
                    if(x < 25          ) if(grid[x + 1][y    ] % 20 == 9) count++; // +X
                    if(x < 25 && y < 13) if(grid[x + 1][y + 1] % 20 == 9) count++; // +X +Y
                    if(          y < 13) if(grid[x    ][y + 1] % 20 == 9) count++; //    +Y
                    if(x >  0 && y < 13) if(grid[x - 1][y + 1] % 20 == 9) count++; // -X +Y
                    if(x >  0          ) if(grid[x - 1][y    ] % 20 == 9) count++; // -X
                    grid[x][y] = count;
                }
                grid[x][y] += 20;
            }
        }
    }

    private void commandGridEnter() {
        if(grid[selector.X][selector.Y] >= 20) {
            grid[selector.X][selector.Y] -= 20;
            if(grid[selector.X][selector.Y] == 9) {
                grid[selector.X][selector.Y] = 10;
                uncoverBombs();
                turnstate = 4;
                scorePoint -= scorePoint / 2;
            } else {
                if(grid[selector.X][selector.Y] == 0) {
                    FieldList.add(selector);
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

    private void uncoverTiles() {
        while(FieldList.size() > 0) {
            boolean temp0 = false;
            for(Vector2 v : FieldList) {
                if(v.X >  0 && v.Y >  0) { if(grid[v.X - 1][v.Y - 1] >= 20) { grid[v.X - 1][v.Y - 1] -= 20; if(grid[v.X - 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y - 1)); temp0 = true; break; } } } } } // -X -Y
                if(            v.Y >  0) { if(grid[v.X    ][v.Y - 1] >= 20) { grid[v.X    ][v.Y - 1] -= 20; if(grid[v.X    ][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y - 1)); temp0 = true; break; } } } } } //    -Y
                if(v.X < 25 && v.Y >  0) { if(grid[v.X + 1][v.Y - 1] >= 20) { grid[v.X + 1][v.Y - 1] -= 20; if(grid[v.X + 1][v.Y - 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y - 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y - 1)); temp0 = true; break; } } } } } // +X -Y
                if(v.X < 25            ) { if(grid[v.X + 1][v.Y    ] >= 20) { grid[v.X + 1][v.Y    ] -= 20; if(grid[v.X + 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y    )); temp0 = true; break; } } } } } // +X
                if(v.X < 25 && v.Y < 13) { if(grid[v.X + 1][v.Y + 1] >= 20) { grid[v.X + 1][v.Y + 1] -= 20; if(grid[v.X + 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X + 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X + 1, v.Y + 1)); temp0 = true; break; } } } } } // +X +Y
                if(            v.Y < 13) { if(grid[v.X    ][v.Y + 1] >= 20) { grid[v.X    ][v.Y + 1] -= 20; if(grid[v.X    ][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X    , v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X    , v.Y + 1)); temp0 = true; break; } } } } } //    +Y
                if(v.X >  0 && v.Y < 13) { if(grid[v.X - 1][v.Y + 1] >= 20) { grid[v.X - 1][v.Y + 1] -= 20; if(grid[v.X - 1][v.Y + 1] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y + 1)) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y + 1)); temp0 = true; break; } } } } } // -X +Y
                if(v.X >  0            ) { if(grid[v.X - 1][v.Y    ] >= 20) { grid[v.X - 1][v.Y    ] -= 20; if(grid[v.X - 1][v.Y    ] == 0) { boolean temp = false; for(Vector2 v2 : FieldList) { if(v2.matches(v.X - 1, v.Y    )) { temp = true; break; } if(!temp) { FieldList.add(new Vector2(v.X - 1, v.Y    )); temp0 = true; break; } } } } } // -X
                break;
            }
            if(!temp0) FieldList.remove(0);
        }
    }

    private void uncoverBombs() {
        for(int y = 0; y < 14; y++) {
            for(int x = 0; x < 26; x++) {
                if(grid[x][y] == 29) {
                    grid[x][y] = 9;
                }
            }
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

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
