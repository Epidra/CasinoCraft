package mod.casinocraft.logic.arcade;

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

public class LogicTetris extends LogicBase {

    public final int B_HOLD = 0;

    public final int I_CONTAINER_NEXT = 0;
    public final int I_CONTAINER_HOLD = 1;
    public final int I_CONTAINER_NOW = 2;
    public final int I_TIME_LAST = 3;
    public final int I_TIME_BREAK = 4;
    public final int I_TIMER = 5;
    public final int I_COLOR_0 = 6; // Color of single Tetromino
    public final int I_COLOR_1 = 7;
    public final int I_COLOR_2 = 8;
    public final int I_COLOR_3 = 9;
    public final int I_COLOR_4 = 10;
    public final int I_COLOR_5 = 11;
    public final int I_COLOR_6 = 12;
    public final int I_LINE_0 = 13;
    public final int I_LINE_1 = 14;
    public final int I_LINE_2 = 15;
    public final int I_LINE_3 = 16;
    public final int I_ALPHA = 17;

    public final int V_TETRO_0 = 0; // Position of the moving tetromino on the grid
    public final int V_TETRO_1 = 1;
    public final int V_TETRO_2 = 2;
    public final int V_TETRO_3 = 3;



    //--------------------CONSTRUCTOR--------------------

    public LogicTetris(){
        super(false, true, true, 10, 20, 1);
    }



    //--------------------BASIC--------------------

    public void start2(){
        setBOL(B_HOLD, true);
        setINT(I_CONTAINER_NEXT, Tetromino_Roll());
        setINT(I_CONTAINER_HOLD, -1);
        setINT(I_CONTAINER_NOW, Tetromino_Roll());
        Tetromino_Create();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 20; j++) {
                setGRD(i, j, -1);
            }
        }
        setINT(I_TIME_BREAK, 500);
        setINT(I_TIME_LAST, 0);
        setINT(I_LINE_0, -1);
        setINT(I_LINE_1, -1);
        setINT(I_LINE_2, -1);
        setINT(I_LINE_3, -1);
        setINT(I_ALPHA, 250);
        setINT(I_TIMER, 0);
        Colorize();
    }

    public void actionTouch(int action){
        if(action == COMMAND_UP){ Tetromino_Drop();      } // UP
        if(action == COMMAND_DOWN){ Tetromino_Fall();      } // DOWN
        if(action == COMMAND_LEFT){ Command_Strafe(true);  } // LEFT
        if(action == COMMAND_RIGHT){ Command_Strafe(false); } // RIGHT
        if(action == COMMAND_ROTLEFT){ Command_Turn(true);    } // ROTATE LEFT
        if(action == COMMAND_ROTRIGHT){ Command_Turn(false);   } // ROTATE RIGHT
        if(action == COMMAND_CONTROL){ Command_Hold();        } // HOLD
    }

    public void updateMotion() {
        updINT(I_TIMER, +15);
    }

    public void updateLogic(){
        if(getINT(I_ALPHA) == 255) {
            if(getINT(I_TIMER) > getINT(I_TIME_LAST) + getINT(I_TIME_BREAK) - scoreLevel() * 5 && turnstate() == 2) {
                Tetromino_Fall();
                rplINT(I_TIME_LAST, I_TIMER);
            }
        } else {
            updINT(I_ALPHA, +10);
            if(getINT(I_ALPHA) <= 0) {
                setINT(I_ALPHA, 250);
                Command_Collapse();
            }
        }
    }



    //--------------------CUSTOM--------------------

    private boolean inLine(int index){
        if(index     == getINT(I_LINE_0)) return true;
        if(index     == getINT(I_LINE_1)) return true;
        if(index     == getINT(I_LINE_2)) return true;
        return index == getINT(I_LINE_3);
    }

    private void Colorize() {
        boolean[] used = new boolean[10];
        int index = 0;
        for(int i = 0; i < 10; i++) {
            used[i] = false;
            index++;
        }

        setINT(I_COLOR_0, -1);
        setINT(I_COLOR_1, -1);
        setINT(I_COLOR_2, -1);
        setINT(I_COLOR_3, -1);
        setINT(I_COLOR_4, -1);
        setINT(I_COLOR_5, -1);
        setINT(I_COLOR_6, -1);

        int count = 0;
        while(count < index && count < 7) {
            int r = RANDOM.nextInt(7);
            if(!used[r]) {
                setINT(I_COLOR_0 + count, r);
                used[r] = true;
                count++;
            }
        }
    }

    private void Command_Collapse() {
        for(int z = 3; z >= 0; z--){
            if(getINT(I_LINE_0 + z) != -1) {
                for(int i = getINT(I_LINE_0 + z); i > 0; i--) {
                    for(int x = 0; x < 10; x++){
                        setGRD(x, i, getGRD(x, i-1));
                    }
                }
            }
            setINT(I_LINE_0, -1);
        }
        setINT(I_ALPHA, 255);
    }

    public void Command_Strafe(boolean totheleft) {
        int dir = 0;
        if(totheleft) {
            dir = -1;
            if(getVEC(V_TETRO_0).X > 0) {
                if(getVEC(V_TETRO_1).X > 0) {
                    if(getVEC(V_TETRO_2).X > 0) {
                        if(getVEC(V_TETRO_3).X > 0) {
                            if(getGRD(getVEC(V_TETRO_0).X + dir, getVEC(V_TETRO_0).Y) == -1) {
                                if(getGRD(getVEC(V_TETRO_1).X + dir, getVEC(V_TETRO_1).Y) == -1) {
                                    if(getGRD(getVEC(V_TETRO_2).X + dir, getVEC(V_TETRO_2).Y) == -1) {
                                        if(getGRD(getVEC(V_TETRO_3).X + dir, getVEC(V_TETRO_3).Y) == -1) {
                                            updVEC(V_TETRO_0, dir, 0);
                                            updVEC(V_TETRO_1, dir, 0);
                                            updVEC(V_TETRO_2, dir, 0);
                                            updVEC(V_TETRO_3, dir, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            dir = 1;
            if(getVEC(V_TETRO_0).X < 9) {
                if(getVEC(V_TETRO_1).X < 9) {
                    if(getVEC(V_TETRO_2).X < 9) {
                        if(getVEC(V_TETRO_3).X < 9) {
                            if(getGRD(getVEC(V_TETRO_0).X + dir, getVEC(V_TETRO_0).Y) == -1) {
                                if(getGRD(getVEC(V_TETRO_1).X + dir, getVEC(V_TETRO_1).Y) == -1) {
                                    if(getGRD(getVEC(V_TETRO_2).X + dir, getVEC(V_TETRO_2).Y) == -1) {
                                        if(getGRD(getVEC(V_TETRO_3).X + dir, getVEC(V_TETRO_3).Y) == -1) {
                                            updVEC(V_TETRO_0, dir, 0);
                                            updVEC(V_TETRO_1, dir, 0);
                                            updVEC(V_TETRO_2, dir, 0);
                                            updVEC(V_TETRO_3, dir, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void Command_Turn(boolean totheleft) {
        Vector2 tempV1 = new Vector2(getVEC(V_TETRO_0).X - getVEC(V_TETRO_1).X, getVEC(V_TETRO_0).Y - getVEC(V_TETRO_1).Y);
        Vector2 tempV2 = new Vector2(getVEC(V_TETRO_0).X - getVEC(V_TETRO_2).X, getVEC(V_TETRO_0).Y - getVEC(V_TETRO_2).Y);
        Vector2 tempV3 = new Vector2(getVEC(V_TETRO_0).X - getVEC(V_TETRO_3).X, getVEC(V_TETRO_0).Y - getVEC(V_TETRO_3).Y);
        if(totheleft) {
            if(tempV1.X == 0 && tempV1.Y  < 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X + tempV1.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV1.X == 0 && tempV1.Y  > 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X + tempV1.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV1.X  < 0 && tempV1.Y == 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y - tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y == 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y - tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  < 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X + tempV1.Y, getVEC(V_TETRO_0).Y - tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  > 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X + tempV1.Y, getVEC(V_TETRO_0).Y - tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  < 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X + tempV1.Y, getVEC(V_TETRO_0).Y - tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  > 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X + tempV1.Y, getVEC(V_TETRO_0).Y - tempV1.X); }

            if(tempV2.X == 0 && tempV2.Y  < 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X + tempV2.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV2.X == 0 && tempV2.Y  > 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X + tempV2.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV2.X  < 0 && tempV2.Y == 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y - tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y == 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y - tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  < 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X + tempV2.Y, getVEC(V_TETRO_0).Y - tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  > 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X + tempV2.Y, getVEC(V_TETRO_0).Y - tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  < 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X + tempV2.Y, getVEC(V_TETRO_0).Y - tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  > 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X + tempV2.Y, getVEC(V_TETRO_0).Y - tempV2.X); }

            if(tempV3.X == 0 && tempV3.Y  < 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X + tempV3.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV3.X == 0 && tempV3.Y  > 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X + tempV3.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV3.X  < 0 && tempV3.Y == 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y - tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y == 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y - tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  < 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X + tempV3.Y, getVEC(V_TETRO_0).Y - tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  > 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X + tempV3.Y, getVEC(V_TETRO_0).Y - tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  < 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X + tempV3.Y, getVEC(V_TETRO_0).Y - tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  > 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X + tempV3.Y, getVEC(V_TETRO_0).Y - tempV3.X); }
        } else {
            if(tempV1.X == 0 && tempV1.Y  < 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X - tempV1.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV1.X == 0 && tempV1.Y  > 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X - tempV1.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV1.X  < 0 && tempV1.Y == 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y == 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y + tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  < 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X - tempV1.Y, getVEC(V_TETRO_0).Y + tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  > 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X - tempV1.Y, getVEC(V_TETRO_0).Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  < 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X - tempV1.Y, getVEC(V_TETRO_0).Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  > 0) { tempV1 = new Vector2(getVEC(V_TETRO_0).X - tempV1.Y, getVEC(V_TETRO_0).Y + tempV1.X); }

            if(tempV2.X == 0 && tempV2.Y  < 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X - tempV2.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV2.X == 0 && tempV2.Y  > 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X - tempV2.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV2.X  < 0 && tempV2.Y == 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y == 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y + tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  < 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X - tempV2.Y, getVEC(V_TETRO_0).Y + tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  > 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X - tempV2.Y, getVEC(V_TETRO_0).Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  < 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X - tempV2.Y, getVEC(V_TETRO_0).Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  > 0) { tempV2 = new Vector2(getVEC(V_TETRO_0).X - tempV2.Y, getVEC(V_TETRO_0).Y + tempV2.X); }

            if(tempV3.X == 0 && tempV3.Y  < 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X - tempV3.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV3.X == 0 && tempV3.Y  > 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X - tempV3.Y, getVEC(V_TETRO_0).Y           ); } else
            if(tempV3.X  < 0 && tempV3.Y == 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y == 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X,            getVEC(V_TETRO_0).Y + tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  < 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X - tempV3.Y, getVEC(V_TETRO_0).Y + tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  > 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X - tempV3.Y, getVEC(V_TETRO_0).Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  < 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X - tempV3.Y, getVEC(V_TETRO_0).Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  > 0) { tempV3 = new Vector2(getVEC(V_TETRO_0).X - tempV3.Y, getVEC(V_TETRO_0).Y + tempV3.X); }
        }
        if(tempV1.X > -1 && tempV1.X < 10 && tempV1.Y > -1 && tempV1.Y < 20) {
            if(tempV2.X > -1 && tempV2.X < 10 && tempV2.Y > -1 && tempV2.Y < 20) {
                if(tempV3.X > -1 && tempV3.X < 10 && tempV3.Y > -1 && tempV3.Y < 20) {
                    if(getGRD(tempV1.X, tempV1.Y) == -1) {
                        if(getGRD(tempV2.X, tempV2.Y) == -1) {
                            if(getGRD(tempV3.X, tempV3.Y) == -1) {
                                setVEC(V_TETRO_1, tempV1);
                                setVEC(V_TETRO_2, tempV2);
                                setVEC(V_TETRO_3, tempV3);
                            }
                        }
                    }
                }
            }
        }

    }

    public void Command_Hold() {
        if(getBOL(B_HOLD)) {
            setBOL(B_HOLD, false);
            if(getINT(I_CONTAINER_HOLD) == -1) {
                rplINT(I_CONTAINER_HOLD, I_CONTAINER_NOW);
                rplINT(I_CONTAINER_NOW, I_CONTAINER_NEXT);
                setINT(I_CONTAINER_NEXT, Tetromino_Roll());
            } else {
                int temp;
                temp = getINT(I_CONTAINER_HOLD);
                rplINT(I_CONTAINER_HOLD, I_CONTAINER_NOW);
                setINT(I_CONTAINER_NOW, temp);
            }
            Tetromino_Create();
        }
    }

    private int Tetromino_Roll() {
        return RANDOM.nextInt(7);
    }

    private void Tetromino_Create() {
        if(getINT(I_CONTAINER_NOW) == 0) { // I
            setVEC(V_TETRO_0, 4, 1); // OOXO
            setVEC(V_TETRO_1, 4, 0); // OOXO
            setVEC(V_TETRO_2, 4, 2); // OOXO
            setVEC(V_TETRO_3, 4, 3); // OOXO
        }
        if(getINT(I_CONTAINER_NOW) == 1) { // O
            setVEC(V_TETRO_0, 4, 0); // OOOO
            setVEC(V_TETRO_1, 4, 1); // OXXO
            setVEC(V_TETRO_2, 5, 0); // OXXO
            setVEC(V_TETRO_3, 5, 1); // OOOO
        }
        if(getINT(I_CONTAINER_NOW) == 2) { // S
            setVEC(V_TETRO_0, 5, 0); // OOOO
            setVEC(V_TETRO_1, 6, 0); // OOXX
            setVEC(V_TETRO_2, 4, 1); // OXXO
            setVEC(V_TETRO_3, 5, 1); // OOOO
        }
        if(getINT(I_CONTAINER_NOW) == 3) { // Z
            setVEC(V_TETRO_0, 5, 0); // OOOO
            setVEC(V_TETRO_1, 4, 0); // XXOO
            setVEC(V_TETRO_2, 5, 1); // OXXO
            setVEC(V_TETRO_3, 6, 1); // OOOO
        }
        if(getINT(I_CONTAINER_NOW) == 4) { // L
            setVEC(V_TETRO_0, 4, 2); // OXOO
            setVEC(V_TETRO_1, 4, 0); // OXOO
            setVEC(V_TETRO_2, 4, 1); // OXXO
            setVEC(V_TETRO_3, 5, 2); // OOOO
        }
        if(getINT(I_CONTAINER_NOW) == 5) { // J
            setVEC(V_TETRO_0, 5, 2); // OOXO
            setVEC(V_TETRO_1, 5, 0); // OOXO
            setVEC(V_TETRO_2, 5, 1); // OXXO
            setVEC(V_TETRO_3, 4, 2); // OOOO
        }
        if(getINT(I_CONTAINER_NOW) == 6) { // T
            setVEC(V_TETRO_0, 5, 0); // OOOO
            setVEC(V_TETRO_1, 4, 0); // OXXX
            setVEC(V_TETRO_2, 6, 0); // OOXO
            setVEC(V_TETRO_3, 5, 1); // OOOO
        }
    }

    private void Tetromino_Drop() {
        int tempPoints = scoreScore();
        while(scoreScore() == tempPoints) {
            Tetromino_Fall();
        }
    }

    public void Tetromino_Fall() {
        if(getVEC(V_TETRO_0).Y < 19 && getVEC(V_TETRO_1).Y < 19 && getVEC(V_TETRO_2).Y < 19 && getVEC(V_TETRO_3).Y < 19) {
            if(getGRD(getVEC(V_TETRO_0).X, getVEC(V_TETRO_0).Y + 1) == -1 && getGRD(getVEC(V_TETRO_1).X, getVEC(V_TETRO_1).Y + 1) == -1 && getGRD(getVEC(V_TETRO_2).X, getVEC(V_TETRO_2).Y + 1) == -1 && getGRD(getVEC(V_TETRO_3).X, getVEC(V_TETRO_3).Y + 1) == -1) {
                updVEC(V_TETRO_0, 0, 1);
                updVEC(V_TETRO_1, 0, 1);
                updVEC(V_TETRO_2, 0, 1);
                updVEC(V_TETRO_3, 0, 1);
            } else {
                Tetromino_Place();
            }
        } else {
            Tetromino_Place();
        }
    }

    private void Tetromino_Place() {
        setBOL(B_HOLD, true);
        setGRD(getVEC(V_TETRO_0).X, getVEC(V_TETRO_0).Y, getINT(I_CONTAINER_NOW));
        setGRD(getVEC(V_TETRO_1).X, getVEC(V_TETRO_1).Y, getINT(I_CONTAINER_NOW));
        setGRD(getVEC(V_TETRO_2).X, getVEC(V_TETRO_2).Y, getINT(I_CONTAINER_NOW));
        setGRD(getVEC(V_TETRO_3).X, getVEC(V_TETRO_3).Y, getINT(I_CONTAINER_NOW));
        scoreScore(2);
        if(getVEC(V_TETRO_0).Y == 0) turnstate(4);
        rplINT(I_CONTAINER_NOW, I_CONTAINER_NEXT);
        int container_temp = getINT(I_CONTAINER_NEXT);
        setINT(I_CONTAINER_NEXT, Tetromino_Roll());
        if(getINT(I_CONTAINER_NEXT) == container_temp) {
            if(RANDOM.nextInt(2) == 0) {
                setINT(I_CONTAINER_NEXT, Tetromino_Roll());
            }
        }
        Tetromino_Create();
        setINT(I_LINE_0, -1);
        setINT(I_LINE_1, -1);
        setINT(I_LINE_2, -1);
        setINT(I_LINE_3, -1);
        for(int i = 19; i > -1; i--) {
            if(getGRD(0, i) != -1 && getGRD(1, i) != -1 && getGRD(2, i) != -1 && getGRD(3, i) != -1 && getGRD(4, i) != -1 && getGRD(5, i) != -1 && getGRD(6, i) != -1 && getGRD(7, i) != -1 && getGRD(8, i) != -1 && getGRD(9, i) != -1) {
                scoreLives(1);
                scoreScore(50);
                if(getINT(I_LINE_0) == -1) {
                    setINT(I_LINE_0, i);
                } else if(getINT(I_LINE_0) == -1) {
                    setINT(I_LINE_0, i);
                } else if(getINT(I_LINE_0) == -1) {
                    setINT(I_LINE_0, i);
                } else if(getINT(I_LINE_0) == -1) {
                    setINT(I_LINE_0, i);
                }
                updINT(I_ALPHA, -5);
            }
        }
        if(scoreLives() + 1 > (1 + scoreLevel()) * 10) {
            scoreLevel(1);
            updINT(I_TIME_BREAK, getINT(I_TIME_BREAK) / 10);
        }
        if(getINT(I_LINE_0) != -1 && getINT(I_LINE_0) != -1 && getINT(I_LINE_0) != -1 && getINT(I_LINE_0) != -1) {
            scoreScore(250 * (scoreLevel() + 1));
        }
    }

}
