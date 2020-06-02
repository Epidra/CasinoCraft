package net.casinocraft.mod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerTetris;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityTetris extends TileEntityCasino {
	
	public boolean active_hold;
    public String container_next;
    public String container_hold;
    public String container_current;
    public double time_last;
    public double time_break;
    public int timer;
    public String []   tetro      = new String [7];
    public boolean[][] grid_base  = new boolean[10][15];
    public String [][] grid_color = new String [10][15];
    public Vector2[]   tetromino  = new Vector2[4];
    public boolean won;
    public int line1;
    public int line2;
    public int line3;
    public int line4;
    public int alpha;
    public int score_level;
    public int score_lives;
    public int score_points;
    
	public TileEntityTetris(){
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerTetris(playerInventory, this);
    }
	
	public void start(){
		active_hold = true;
        container_next = "empty";
        container_hold = "empty";
        container_current = "empty";
        container_current = Tetromino_Roll();
        container_next = Tetromino_Roll();
        Tetromino_Create();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 15; j++) {
                grid_base[i][j] = false;
                grid_color[i][j] = "empty";
            }
        }
        time_break = 250 - 50;
        time_last = 0;
        won = false;
        line1 = -1;
        line1 = -1;
        line1 = -1;
        line1 = -1;
        alpha = 255;
        timer = 0;
        Colorize();
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	timer+=15;
    	if(alpha == 255) {
            if(timer > time_last + time_break - score_level * 5 && !won) {
                Tetromino_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 255;
                Command_Collapse();
            }
        }
    }
    
    private void Colorize() {
        boolean[] used = new boolean[10];
        int index = 0;
        for(int i = 0; i < 10; i++) {
            used[i] = false;
            index++;
        }

        tetro[0] = "blank";
        tetro[1] = "blank";
        tetro[2] = "blank";
        tetro[3] = "blank";
        tetro[4] = "blank";
        tetro[5] = "blank";
        tetro[6] = "blank";

        int count = 0;
        while(count < index && count < 7) {
            int r = rand.nextInt(10);
            if(!used[r]) {
                tetro[count] = IntToMino(r);
                used[r] = true;
                count++;
            }
        }
    }

    private String IntToMino(int i) {
        if(i == 0) return "fire";
        if(i == 1) return "air";
        if(i == 2) return "thunder";
        if(i == 3) return "water";
        if(i == 4) return "ice";
        if(i == 5) return "earth";
        if(i == 6) return "metal";
        if(i == 7) return "nature";
        if(i == 8) return "light";
        if(i == 9) return "dark";
        return "blank";
    }

    private String TetroToMino(String t) {
        if(0 == t.compareTo("I")) return tetro[0];
        if(0 == t.compareTo("O")) return tetro[1];
        if(0 == t.compareTo("S")) return tetro[2];
        if(0 == t.compareTo("Z")) return tetro[3];
        if(0 == t.compareTo("L")) return tetro[4];
        if(0 == t.compareTo("J")) return tetro[5];
        if(0 == t.compareTo("T")) return tetro[6];
        return "blank";
    }
    
    private void Command_Collapse() {
        if(line4 != -1) {
            for(int i = line4; i > 0; i--) {
                grid_base[0][i] = grid_base[0][i - 1];
                grid_base[1][i] = grid_base[1][i - 1];
                grid_base[2][i] = grid_base[2][i - 1];
                grid_base[3][i] = grid_base[3][i - 1];
                grid_base[4][i] = grid_base[4][i - 1];
                grid_base[5][i] = grid_base[5][i - 1];
                grid_base[6][i] = grid_base[6][i - 1];
                grid_base[7][i] = grid_base[7][i - 1];
                grid_base[8][i] = grid_base[8][i - 1];
                grid_base[9][i] = grid_base[9][i - 1];
                grid_color[0][i] = grid_color[0][i - 1];
                grid_color[1][i] = grid_color[1][i - 1];
                grid_color[2][i] = grid_color[2][i - 1];
                grid_color[3][i] = grid_color[3][i - 1];
                grid_color[4][i] = grid_color[4][i - 1];
                grid_color[5][i] = grid_color[5][i - 1];
                grid_color[6][i] = grid_color[6][i - 1];
                grid_color[7][i] = grid_color[7][i - 1];
                grid_color[8][i] = grid_color[8][i - 1];
                grid_color[9][i] = grid_color[9][i - 1];
            }
        }
        if(line3 != -1) {
            for(int i = line3; i > 0; i--) {
                grid_base[0][i] = grid_base[0][i - 1];
                grid_base[1][i] = grid_base[1][i - 1];
                grid_base[2][i] = grid_base[2][i - 1];
                grid_base[3][i] = grid_base[3][i - 1];
                grid_base[4][i] = grid_base[4][i - 1];
                grid_base[5][i] = grid_base[5][i - 1];
                grid_base[6][i] = grid_base[6][i - 1];
                grid_base[7][i] = grid_base[7][i - 1];
                grid_base[8][i] = grid_base[8][i - 1];
                grid_base[9][i] = grid_base[9][i - 1];
                grid_color[0][i] = grid_color[0][i - 1];
                grid_color[1][i] = grid_color[1][i - 1];
                grid_color[2][i] = grid_color[2][i - 1];
                grid_color[3][i] = grid_color[3][i - 1];
                grid_color[4][i] = grid_color[4][i - 1];
                grid_color[5][i] = grid_color[5][i - 1];
                grid_color[6][i] = grid_color[6][i - 1];
                grid_color[7][i] = grid_color[7][i - 1];
                grid_color[8][i] = grid_color[8][i - 1];
                grid_color[9][i] = grid_color[9][i - 1];
            }
        }
        if(line2 != -1) {
            for(int i = line2; i > 0; i--) {
                grid_base[0][i] = grid_base[0][i - 1];
                grid_base[1][i] = grid_base[1][i - 1];
                grid_base[2][i] = grid_base[2][i - 1];
                grid_base[3][i] = grid_base[3][i - 1];
                grid_base[4][i] = grid_base[4][i - 1];
                grid_base[5][i] = grid_base[5][i - 1];
                grid_base[6][i] = grid_base[6][i - 1];
                grid_base[7][i] = grid_base[7][i - 1];
                grid_base[8][i] = grid_base[8][i - 1];
                grid_base[9][i] = grid_base[9][i - 1];
                grid_color[0][i] = grid_color[0][i - 1];
                grid_color[1][i] = grid_color[1][i - 1];
                grid_color[2][i] = grid_color[2][i - 1];
                grid_color[3][i] = grid_color[3][i - 1];
                grid_color[4][i] = grid_color[4][i - 1];
                grid_color[5][i] = grid_color[5][i - 1];
                grid_color[6][i] = grid_color[6][i - 1];
                grid_color[7][i] = grid_color[7][i - 1];
                grid_color[8][i] = grid_color[8][i - 1];
                grid_color[9][i] = grid_color[9][i - 1];
            }
        }
        if(line1 != -1) {
            for(int i = line1; i > 0; i--) {
                grid_base[0][i] = grid_base[0][i - 1];
                grid_base[1][i] = grid_base[1][i - 1];
                grid_base[2][i] = grid_base[2][i - 1];
                grid_base[3][i] = grid_base[3][i - 1];
                grid_base[4][i] = grid_base[4][i - 1];
                grid_base[5][i] = grid_base[5][i - 1];
                grid_base[6][i] = grid_base[6][i - 1];
                grid_base[7][i] = grid_base[7][i - 1];
                grid_base[8][i] = grid_base[8][i - 1];
                grid_base[9][i] = grid_base[9][i - 1];
                grid_color[0][i] = grid_color[0][i - 1];
                grid_color[1][i] = grid_color[1][i - 1];
                grid_color[2][i] = grid_color[2][i - 1];
                grid_color[3][i] = grid_color[3][i - 1];
                grid_color[4][i] = grid_color[4][i - 1];
                grid_color[5][i] = grid_color[5][i - 1];
                grid_color[6][i] = grid_color[6][i - 1];
                grid_color[7][i] = grid_color[7][i - 1];
                grid_color[8][i] = grid_color[8][i - 1];
                grid_color[9][i] = grid_color[9][i - 1];
            }
        }
        line1 = -1;
        line2 = -1;
        line3 = -1;
        line4 = -1;
        alpha = 255;
    }

    public void Command_Strafe(String _direction) {
        int dir = 0;
        if(0 == _direction.compareTo("left")) {
            dir = -1;
            if(tetromino[0].X > 0) {
                if(tetromino[1].X > 0) {
                    if(tetromino[2].X > 0) {
                        if(tetromino[3].X > 0) {
                            if(!grid_base[tetromino[0].X + dir][tetromino[0].Y]) {
                                if(!grid_base[tetromino[1].X + dir][tetromino[1].Y]) {
                                    if(!grid_base[tetromino[2].X + dir][tetromino[2].Y]) {
                                        if(!grid_base[tetromino[3].X + dir][tetromino[3].Y]) {
                                            tetromino[0].X = tetromino[0].X + dir;
                                            tetromino[1].X = tetromino[1].X + dir;
                                            tetromino[2].X = tetromino[2].X + dir;
                                            tetromino[3].X = tetromino[3].X + dir;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(0 == _direction.compareTo("right")) {
            dir = 1;
            if(tetromino[0].X < 9) {
                if(tetromino[1].X < 9) {
                    if(tetromino[2].X < 9) {
                        if(tetromino[3].X < 9) {
                            if(!grid_base[tetromino[0].X + dir][tetromino[0].Y]) {
                                if(!grid_base[tetromino[1].X + dir][tetromino[1].Y]) {
                                    if(!grid_base[tetromino[2].X + dir][tetromino[2].Y]) {
                                        if(!grid_base[tetromino[3].X + dir][tetromino[3].Y]) {
                                            tetromino[0].X = tetromino[0].X + dir;
                                            tetromino[1].X = tetromino[1].X + dir;
                                            tetromino[2].X = tetromino[2].X + dir;
                                            tetromino[3].X = tetromino[3].X + dir;
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

    public void Command_Turn(String _direction) {
        Vector2 tempV1 = new Vector2(tetromino[0].X - tetromino[1].X, tetromino[0].Y - tetromino[1].Y);
        Vector2 tempV2 = new Vector2(tetromino[0].X - tetromino[2].X, tetromino[0].Y - tetromino[2].Y);
        Vector2 tempV3 = new Vector2(tetromino[0].X - tetromino[3].X, tetromino[0].Y - tetromino[3].Y);
        if(0 == _direction.compareTo("left")) {
            if(tempV1.X == 0 && tempV1.Y < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y); } else
            if(tempV1.X == 0 && tempV1.Y > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y); } else
            if(tempV1.X < 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X > 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X < 0 && tempV1.Y < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X < 0 && tempV1.Y > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X > 0 && tempV1.Y < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X > 0 && tempV1.Y > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); }

            if(tempV2.X == 0 && tempV2.Y < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y); } else
            if(tempV2.X == 0 && tempV2.Y > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y); } else
            if(tempV2.X < 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X > 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X < 0 && tempV2.Y < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X < 0 && tempV2.Y > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X > 0 && tempV2.Y < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X > 0 && tempV2.Y > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); }

            if(tempV3.X == 0 && tempV3.Y < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y); } else
            if(tempV3.X == 0 && tempV3.Y > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y); } else
            if(tempV3.X < 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X > 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X < 0 && tempV3.Y < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X < 0 && tempV3.Y > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X > 0 && tempV3.Y < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X > 0 && tempV3.Y > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); }
        }
        if(0 == _direction.compareTo("right")) {
            if(tempV1.X == 0 && tempV1.Y < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y); } else
            if(tempV1.X == 0 && tempV1.Y > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y); } else
            if(tempV1.X < 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X > 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X < 0 && tempV1.Y < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X < 0 && tempV1.Y > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X > 0 && tempV1.Y < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X > 0 && tempV1.Y > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); }

            if(tempV2.X == 0 && tempV2.Y < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y); } else
            if(tempV2.X == 0 && tempV2.Y > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y); } else
            if(tempV2.X < 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X > 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X < 0 && tempV2.Y < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X < 0 && tempV2.Y > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X > 0 && tempV2.Y < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X > 0 && tempV2.Y > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); }

            if(tempV3.X == 0 && tempV3.Y < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y); } else
            if(tempV3.X == 0 && tempV3.Y > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y); } else
            if(tempV3.X < 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X > 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X < 0 && tempV3.Y < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X < 0 && tempV3.Y > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X > 0 && tempV3.Y < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X > 0 && tempV3.Y > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); }
        }
        if(tempV1.X > -1 && tempV1.X < 10 && tempV1.Y > -1 && tempV1.Y < 20) {
            if(tempV2.X > -1 && tempV2.X < 10 && tempV2.Y > -1 && tempV2.Y < 20) {
                if(tempV3.X > -1 && tempV3.X < 10 && tempV3.Y > -1 && tempV3.Y < 20) {
                    if(!grid_base[tempV1.X][tempV1.Y]) {
                        if(!grid_base[tempV2.X][tempV2.Y]) {
                            if(!grid_base[tempV3.X][tempV3.Y]) {
                                tetromino[1] = tempV1;
                                tetromino[2] = tempV2;
                                tetromino[3] = tempV3;
                            }
                        }
                    }
                }
            }
        }

    }

    public void Command_Hold() {
    	if(active_hold) {
            active_hold = false;
            if(0 == container_hold.compareTo("empty")) {
                container_hold = container_current;
                container_current = container_next;
                container_next = Tetromino_Roll();
            } else {
                String temp;
                temp = container_hold;
                container_hold = container_current;
                container_current = temp;
            }
            Tetromino_Create();
        }
    }

    private String Tetromino_Roll() {
        int temp = rand.nextInt(7);
        if(temp == 0) return "I";
        if(temp == 1) return "O";
        if(temp == 2) return "S";
        if(temp == 3) return "Z";
        if(temp == 4) return "L";
        if(temp == 5) return "J";
        if(temp == 6) return "T";
        return "O";
    }

    private void Tetromino_Create() {
        if(0 == container_current.compareTo("I")) {
            tetromino[0] = new Vector2(4, 1); // OOXO
            tetromino[1] = new Vector2(4, 0); // OOXO
            tetromino[2] = new Vector2(4, 2); // OOXO
            tetromino[3] = new Vector2(4, 3); // OOXO
        }
        if(0 == container_current.compareTo("O")) {
            tetromino[0] = new Vector2(4, 0); // OOOO
            tetromino[1] = new Vector2(4, 1); // OXXO
            tetromino[2] = new Vector2(5, 0); // OXXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
        if(0 == container_current.compareTo("S")) {
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(6, 0); // OOXX
            tetromino[2] = new Vector2(4, 1); // OXXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
        if(0 == container_current.compareTo("Z")) {
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(4, 0); // XXOO
            tetromino[2] = new Vector2(5, 1); // OXXO
            tetromino[3] = new Vector2(6, 1); // OOOO
        }
        if(0 == container_current.compareTo("L")) {
            tetromino[0] = new Vector2(4, 2); // OXOO
            tetromino[1] = new Vector2(4, 0); // OXOO
            tetromino[2] = new Vector2(4, 1); // OXXO
            tetromino[3] = new Vector2(5, 2); // OOOO
        }
        if(0 == container_current.compareTo("J")) {
            tetromino[0] = new Vector2(5, 2); // OOXO
            tetromino[1] = new Vector2(5, 0); // OOXO
            tetromino[2] = new Vector2(5, 1); // OXXO
            tetromino[3] = new Vector2(4, 2); // OOOO
        }
        if(0 == container_current.compareTo("T")) {
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(4, 0); // OXXX
            tetromino[2] = new Vector2(6, 0); // OOXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
    }

    public void Tetromino_Fall() {
        if(tetromino[0].Y < 14 && tetromino[1].Y < 14 && tetromino[2].Y < 14 && tetromino[3].Y < 14) {
            if(!grid_base[tetromino[0].X][tetromino[0].Y + 1] && !grid_base[tetromino[1].X][tetromino[1].Y + 1] && !grid_base[tetromino[2].X][tetromino[2].Y + 1] && !grid_base[tetromino[3].X][tetromino[3].Y + 1]) {
                tetromino[0].Y = tetromino[0].Y + 1;
                tetromino[1].Y = tetromino[1].Y + 1;
                tetromino[2].Y = tetromino[2].Y + 1;
                tetromino[3].Y = tetromino[3].Y + 1;
            } else {
                Tetromino_Place();
            }
        } else {
            Tetromino_Place();
        }
    }

    private void Tetromino_Place() {
        active_hold = true;
        grid_base[tetromino[0].X][tetromino[0].Y] = true;
        grid_base[tetromino[1].X][tetromino[1].Y] = true;
        grid_base[tetromino[2].X][tetromino[2].Y] = true;
        grid_base[tetromino[3].X][tetromino[3].Y] = true;
        grid_color[tetromino[0].X][tetromino[0].Y] = TetroToMino(container_current);
        grid_color[tetromino[1].X][tetromino[1].Y] = TetroToMino(container_current);
        grid_color[tetromino[2].X][tetromino[2].Y] = TetroToMino(container_current);
        grid_color[tetromino[3].X][tetromino[3].Y] = TetroToMino(container_current);
        score_points = score_points + 2;
        if(tetromino[0].Y == 0) won = true;
        container_current = container_next;
        String container_temp = container_next;
        if((container_next = Tetromino_Roll()) == container_temp) {
            if(rand.nextInt(2) == 0) {
                container_next = Tetromino_Roll();
            }
        }
        Tetromino_Create();
        line1 = -1;
        line2 = -1;
        line3 = -1;
        line4 = -1;
        for(int i = 14; i > -1; i--) {
            if(grid_base[0][i] && grid_base[1][i] && grid_base[2][i] && grid_base[3][i] && grid_base[4][i] && grid_base[5][i] && grid_base[6][i] && grid_base[7][i] && grid_base[8][i] && grid_base[9][i]) {
                score_lives++;
                score_points += 50;
                if(line1 == -1) {
                    line1 = i;
                } else if(line2 == -1) {
                    line2 = i;
                } else if(line3 == -1) {
                    line3 = i;
                } else if(line4 == -1) {
                    line4 = i;
                }
                alpha -= 5;
            }
        }
        if(score_lives > (1 + score_level) * 10) {
            score_level++;
        }
        if(line1 != -1 && line2 != -1 && line3 != -1 && line4 != -1) {
            score_points = score_points + 250 * (score_level + 1);
        }
    }
    
}