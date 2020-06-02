package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerColumns;
import net.casinocraft.mod.util.Card;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityColumns extends TileEntityCasino {
	
	public boolean active_hold;

    public String[] container_next = new String[3];
    public String[] container_hold = new String[3];
    public String[] container_current = new String[3];

    public double time_last;
    public double time_break;
    public int timer;

    public boolean[][] grid_base  = new   boolean[10][15];
    public String[][] grid_color = new String[10][15];

    public Vector2[] tetromino = new Vector2[3];

    public List<Vector2> clear = new ArrayList<Vector2>();

    public boolean won;

    public int alpha;
	
    public int score_level;
    public int score_lives;
    public int score_points;
    
	public EnumDyeColor color;
	
	public TileEntityColumns(){
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerColumns(playerInventory, this);
    }
	
	
	public void start(){
		active_hold = true;
        container_next[0] = Column_Roll();
        container_next[1] = Column_Roll();
        container_next[2] = Column_Roll();
        container_hold[0] = "empty";
        container_hold[1] = "empty";
        container_hold[2] = "empty";
        container_current[0] = Column_Roll();
        container_current[1] = Column_Roll();
        container_current[2] = Column_Roll();
        Column_Create();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 15; j++) {
                grid_base[i][j] = false;
                grid_color[i][j] = "empty";
            }
        }
        time_break = 250 - 25 * 2;
        time_last = 0;
        won = false;
        clear.clear();
        alpha = 255;
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	timer+=15;
    	if(alpha == 255) {
            if(timer > time_last + time_break - score_level * 5 && !won) {
                Column_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 255;
                for(int y = 0; y < 15; y++) {
                    for(int x = 0; x < 10; x++) {
                        if(IsCleared(x, y)) {
                            grid_base[x][y] = false;
                            grid_color[x][y] = "empty";
                        }
                    }
                }
                Command_Collapse();
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
    
    public void Column_Drop() {
        int tempPoints = score_points;
        while(score_points == tempPoints) {
            Column_Fall();
        }
    }

    private boolean IsCleared(int x, int y) {
    	for(int i = 0; i < clear.size(); i++){
    		if(clear.get(i).X == x && clear.get(i).Y == y) return true;
    	}
        return false;
    }

    private void Command_Collapse() {
        // Gravity after match found and cleared
        int temp = 0;
        for(int y = 13; y >= 0; y--) {
            for(int x = 0; x < 10; x++) {
                if(grid_base[x][y]) {
                    temp = 0;
                    if(!grid_base[x][y + 1]) {
                        temp++;
                        if(y+2 < 15 && !grid_base[x][y + 2]) {
                            temp++;
                            if(y+3 < 15 && !grid_base[x][y + 3]) {
                                temp++;
                            }
                        }
                    }
                    if(temp != 0) {
                        grid_base[x][y + temp] = true;
                        grid_base[x][y       ] = false;
                        grid_color[x][y + temp] = grid_color[x][y];
                        grid_color[x][y       ] = "empty";
                    }
                }
            }
        }
        clear.clear();
        alpha = 255;
        Check_Field();
    }

    public void Command_Strafe(String _direction) {
        int dir = 0;
        if(_direction == "left") {
            dir = -1;
            if(tetromino[0].X > 0) {
                if(tetromino[1].X > 0) {
                    if(tetromino[2].X > 0) {
                        if(!grid_base[(int)tetromino[0].X + dir][(int)tetromino[0].Y]) {
                            if(!grid_base[(int)tetromino[1].X + dir][(int)tetromino[1].Y]) {
                                if(!grid_base[(int)tetromino[2].X + dir][(int)tetromino[2].Y]) {
                                    tetromino[0].X = tetromino[0].X + dir;
                                    tetromino[1].X = tetromino[1].X + dir;
                                    tetromino[2].X = tetromino[2].X + dir;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(_direction == "right") {
            dir = 1;
            if(tetromino[0].X < 9) {
                if(tetromino[1].X < 9) {
                    if(tetromino[2].X < 9) {
                        if(!grid_base[(int)tetromino[0].X + dir][(int)tetromino[0].Y]) {
                            if(!grid_base[(int)tetromino[1].X + dir][(int)tetromino[1].Y]) {
                                if(!grid_base[(int)tetromino[2].X + dir][(int)tetromino[2].Y]) {
                                    tetromino[0].X = tetromino[0].X + dir;
                                    tetromino[1].X = tetromino[1].X + dir;
                                    tetromino[2].X = tetromino[2].X + dir;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void Command_Cycle(String _direction) {
        if(_direction == "left") {
            String temp = container_current[0];
            container_current[0] = container_current[1];
            container_current[1] = container_current[2];
            container_current[2] = temp;
        }
        if(_direction == "right") {
            String temp = container_current[2];
            container_current[2] = container_current[1];
            container_current[1] = container_current[0];
            container_current[0] = temp;
        }

    }

    public void Command_Hold() {
    	if(active_hold) {
            active_hold = false;
            if(container_hold[0] == "empty") {
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_hold[2] = container_current[2];
                container_current[0] = container_next[0];
                container_current[1] = container_next[1];
                container_current[2] = container_next[2];
                container_next[0] = Column_Roll();
                container_next[1] = Column_Roll();
                container_next[2] = Column_Roll();
            } else {
                String[] temp = new String[3];
                temp[0] = container_hold[0];
                temp[1] = container_hold[1];
                temp[2] = container_hold[2];
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_hold[2] = container_current[2];
                container_current[0] = temp[0];
                container_current[1] = temp[1];
                container_current[2] = temp[2];
            }
            Column_Create();
        }
    }

    private String Column_Roll() {
        return IntToMino(rand.nextInt(8));
    }

    private void Column_Create() {
        tetromino[0] = new Vector2(4, 0);
        tetromino[1] = new Vector2(4, 1);
        tetromino[2] = new Vector2(4, 2);
    }

    public void Column_Fall() {
        if(tetromino[0].Y < 14 && tetromino[1].Y < 14 && tetromino[2].Y < 14) {
            if(!grid_base[(int)tetromino[0].X][(int)tetromino[0].Y + 1] && !grid_base[(int)tetromino[1].X][(int)tetromino[1].Y + 1] && !grid_base[(int)tetromino[2].X][(int)tetromino[2].Y + 1]) {
                tetromino[0].Y = tetromino[0].Y + 1;
                tetromino[1].Y = tetromino[1].Y + 1;
                tetromino[2].Y = tetromino[2].Y + 1;
            } else {
                Column_Place();
            }
        } else {
            Column_Place();
        }
    }

    private void Column_Place() {
        active_hold = true;
        grid_base[(int)tetromino[0].X][(int)tetromino[0].Y] = true;
        grid_base[(int)tetromino[1].X][(int)tetromino[1].Y] = true;
        grid_base[(int)tetromino[2].X][(int)tetromino[2].Y] = true;
        grid_color[(int)tetromino[0].X][(int)tetromino[0].Y] = container_current[0];
        grid_color[(int)tetromino[1].X][(int)tetromino[1].Y] = container_current[1];
        grid_color[(int)tetromino[2].X][(int)tetromino[2].Y] = container_current[2];
        score_points = score_points + 2 * 2;
        if(tetromino[0].Y == 0) won = true;
        container_current[0] = container_next[0];
        container_current[1] = container_next[1];
        container_current[2] = container_next[2];
        container_next[0] = Column_Roll();
        container_next[1] = Column_Roll();
        container_next[2] = Column_Roll();
        Column_Create();
        Check_Field();
    }

    private void Check_Field() {

        int points = 0;
        int bonus = 0;

        for(int y = 14; y >= 0; y--) {
            for(int x = 0; x < 10; x++) {
                if(x < 8 && grid_color[x][y] != "empty" && grid_color[x][y] == grid_color[x + 1][y] && grid_color[x][y] == grid_color[x + 2][y]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y)); clear.add(new Vector2(x + 2, y));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(y > 1 && grid_color[x][y] != "empty" && grid_color[x][y] == grid_color[x][y - 1] && grid_color[x][y] == grid_color[x][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x, y - 1)); clear.add(new Vector2(x, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
                if(x < 8 && y > 1 && grid_color[x][y] != "empty" && grid_color[x][y] == grid_color[x + 1][y - 1] && grid_color[x][y] == grid_color[x + 2][y - 2]) {
                    clear.add(new Vector2(x, y)); clear.add(new Vector2(x + 1, y - 1)); clear.add(new Vector2(x + 2, y - 2));
                    points += (30 + bonus);
                    bonus += 10;
                }
            }
        }

        if(points > 0) {
            alpha -= 5;
            score_points += (points * 2 * (score_level + 1));
            score_lives++;
            if(score_lives > (1 + score_level) * 10) {
                score_level++;
            }
            //Command_Collapse();
        }
    }
    
}
