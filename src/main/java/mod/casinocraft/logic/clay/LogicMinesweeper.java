package mod.casinocraft.logic.clay;

import mod.casinocraft.logic.LogicBase;
import mod.shared.util.Vector2;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class LogicMinesweeper extends LogicBase {

    private List<Vector2> FieldList = new ArrayList<Vector2>();

    //boolean[][] grid_cover = new boolean[14][14];
    //boolean[][] grid_flag  = new boolean[30][14];
    //    int[][] grid_base  = new     int[14][14];

    public int bombs = 0;

    //int bombs;



    //--------------------CONSTRUCTOR--------------------

    public LogicMinesweeper(int table){
        super(true, table, "c_minesweeper", 26, 14);
    }



    //--------------------BASIC--------------------

    public void start2(){
        bombs = 0;
        selector = new Vector2(5, 5);
        FieldList.clear();
        scoreLevel = 1;
        Create_Field();
    }

    private void Restart() {
        turnstate = 2;
        FieldList.clear();
        scoreLevel++;
        ResetGrid();
        Create_Field();
    }

    public void actionTouch(int action){
        if(action == -1) {
            Restart();
        } else if(action == -2) {
            turnstate = 4;
        } else {
            selector = new Vector2(action%26, action/26);
            Command_Grid_Enter();
        }
    }

    public void updateMotion(){

    }

    public void updateLogic(){

    }

    public void load2(CompoundNBT compound){
        bombs = compound.getInt("bombs");
    }

    public CompoundNBT save2(CompoundNBT compound){
        compound.putInt("bombs", bombs);
        return compound;
    }



    //--------------------CUSTOM--------------------

    private void Create_Field() {
        bombs = scoreLevel + scoreLevel * table * 3;
        int max = table == 1 ? 14*14 : 14*26;
        max -= max/4;
        if(bombs > max) bombs = max;
        for(int i = 0; i < bombs; i++) {
            int x = RANDOM.nextInt(table == 1 ? 14 : 26) + (table == 1 ? 6 : 0);
            int y = RANDOM.nextInt(14);
            if(grid[x][y] != 9) {
                grid[x][y] = 9;
            } else {
                i--;
            }
        }
        for(int y = 0; y < 14; y++) {
            for(int x = table == 1 ? 6 : 0; x < (table == 1 ? 20 : 26); x++) {
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
        //if(table == 2) {
        //    for(int y = 0; y < 6; y++) {
        //        for(int x = 0; x < 6; x++) {
        //        }
        //    }
        //}
    }

    private void Command_Grid_Enter() {
        if(grid[selector.X][selector.Y] >= 20) {
            //if(active_flag) {
            //    if(grid_flag[(int)selector.X][(int)selector.Y]) {
            //        grid_flag[(int)selector.X][(int)selector.Y] = false;
            //    } else {
            //        grid_flag[(int)selector.X][(int)selector.Y] = true;
            //    }
            //} else {
            grid[selector.X][selector.Y] -= 20;
            if(grid[selector.X][selector.Y] == 9) {
                grid[selector.X][selector.Y] = 10;
                Uncover_Bombs();
                turnstate = 4;
                scorePoint -= scorePoint / 2;
            } else {
                if(grid[selector.X][selector.Y] == 0) {
                    FieldList.add(selector);
                    Uncover_Tiles();
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
            //}
        }
    }

    private void Uncover_Tiles() {
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

    private void Uncover_Bombs() {
        for(int y = 0; y < 14; y++) {
            for(int x = 0; x < 26; x++) {
                if(grid[x][y] == 29) {
                    grid[x][y] = 9;
                }
            }
        }
    }

}
