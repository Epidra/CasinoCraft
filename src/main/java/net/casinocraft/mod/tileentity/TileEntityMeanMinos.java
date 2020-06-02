package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerMeanMinos;
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

public class TileEntityMeanMinos extends TileEntityCasino {
	
	public boolean active_hold;

    public String[] container_next = new String[2];
    public String[] container_hold = new String[2];
    public String[] container_current = new String[2];

    public double time_last;
    public double time_break;
    public int timer;
    
    public int score_level;
    public int score_lives;
    public int score_points;

    public boolean[][] grid_base  = new   boolean[10][15];
    public String[][] grid_color = new String[10][15];

    public Vector2[] tetromino = new Vector2[2];

    public List<Vector2> clear = new ArrayList<Vector2>();

    public boolean won;

    public int alpha;
	
	public EnumDyeColor color;
	
	public TileEntityMeanMinos(){
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerMeanMinos(playerInventory, this);
    }
	
	
	public void start(){
		active_hold = true;
        container_next[0] = Domino_Roll();
        container_next[1] = Domino_Roll();
        container_hold[0] = "empty";
        container_hold[1] = "empty";
        container_current[0] = Domino_Roll();
        container_current[1] = Domino_Roll();
        Domino_Create();
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
                Domino_Fall();
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
    
    private void Domino_Drop() {
        int tempPoints = score_points;
        while(score_points == tempPoints) {
            Domino_Fall();
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
                    while(y + temp + 1 < 15 && !grid_base[x][y + temp + 1]) {
                        temp++;
                    }
                    if(temp != 0) {
                        grid_base[x][y + temp] = true;
                        grid_base[x][y] = false;
                        grid_color[x][y + temp] = grid_color[x][y];
                        grid_color[x][y] = "empty";
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
                    if(!grid_base[(int)tetromino[0].X + dir][(int)tetromino[0].Y]) {
                        if(!grid_base[(int)tetromino[1].X + dir][(int)tetromino[1].Y]) {
                            tetromino[0].X = tetromino[0].X + dir;
                            tetromino[1].X = tetromino[1].X + dir;
                        }
                    }
                }
            }
        }
        if(_direction == "right") {
            dir = 1;
            if(tetromino[0].X < 9) {
                if(tetromino[1].X < 9) {
                    if(!grid_base[(int)tetromino[0].X + dir][(int)tetromino[0].Y]) {
                        if(!grid_base[(int)tetromino[1].X + dir][(int)tetromino[1].Y]) {
                            tetromino[0].X = tetromino[0].X + dir;
                            tetromino[1].X = tetromino[1].X + dir;
                        }
                    }
                }
            }
        }
    }

    public void Command_Turn(String _direction) {

        int pos = 0; // Position of the rotatable Mino
        if(tetromino[0].Y > tetromino[1].Y) pos = 1; // Up
        if(tetromino[0].X > tetromino[1].X) pos = 2; // Left
        if(tetromino[0].Y < tetromino[1].Y) pos = 3; // Down
        if(tetromino[0].X < tetromino[1].X) pos = 4; // Right
        
        

        if(_direction == "left") {
            if(pos == 1) { if(tetromino[0].X - 1 >=  0 && !grid_base[(int)tetromino[0].X - 1][(int)tetromino[0].Y    ]) { tetromino[1] = new Vector2(tetromino[0].X - 1, tetromino[0].Y    ); } }
            if(pos == 2) { if(tetromino[0].Y + 1 <= 14 && !grid_base[(int)tetromino[0].X    ][(int)tetromino[0].Y + 1]) { tetromino[1] = new Vector2(tetromino[0].X    , tetromino[0].Y + 1); } }
            if(pos == 3) { if(tetromino[0].X + 1 <=  9 && !grid_base[(int)tetromino[0].X + 1][(int)tetromino[0].Y    ]) { tetromino[1] = new Vector2(tetromino[0].X + 1, tetromino[0].Y    ); } }
            if(pos == 4) { if(tetromino[0].Y - 1 >=  0 && !grid_base[(int)tetromino[0].X    ][(int)tetromino[0].Y - 1]) { tetromino[1] = new Vector2(tetromino[0].X    , tetromino[0].Y - 1); } }
        }
        if(_direction == "right") {
            if(pos == 1) { if(tetromino[0].X + 1 <=  9 && !grid_base[(int)tetromino[0].X + 1][(int)tetromino[0].Y    ]) { tetromino[1] = new Vector2(tetromino[0].X + 1, tetromino[0].Y    ); } }
            if(pos == 2) { if(tetromino[0].Y - 1 >=  0 && !grid_base[(int)tetromino[0].X    ][(int)tetromino[0].Y - 1]) { tetromino[1] = new Vector2(tetromino[0].X    , tetromino[0].Y - 1); } }
            if(pos == 3) { if(tetromino[0].X - 1 >=  0 && !grid_base[(int)tetromino[0].X - 1][(int)tetromino[0].Y    ]) { tetromino[1] = new Vector2(tetromino[0].X - 1, tetromino[0].Y    ); } }
            if(pos == 4) { if(tetromino[0].Y + 1 <= 14 && !grid_base[(int)tetromino[0].X    ][(int)tetromino[0].Y + 1]) { tetromino[1] = new Vector2(tetromino[0].X    , tetromino[0].Y + 1); } }
        }

    }

    public void Command_Hold() {
    	if(active_hold) {
            active_hold = false;
            if(container_hold[0] == "empty") {
                container_hold = container_current;
                container_hold = container_current;
                container_current = container_next;
                container_current = container_next;
                container_next[0] = Domino_Roll();
                container_next[1] = Domino_Roll();
            } else {
                String[] temp = new String[2];
                temp[0] = container_hold[0];
                temp[1] = container_hold[1];
                container_hold[0] = container_current[0];
                container_hold[1] = container_current[1];
                container_current[0] = temp[0];
                container_current[1] = temp[1];
            }
            Domino_Create();
        }
    }

    private String Domino_Roll() {
        return IntToMino(rand.nextInt(8));
    }

    private void Domino_Create() {
        tetromino[0] = new Vector2(4, 1);
        tetromino[1] = new Vector2(4, 0);
    }

    public void Domino_Fall() {
        if(tetromino[0].Y < 14 && tetromino[1].Y < 14) {
            if(!grid_base[(int)tetromino[0].X][(int)tetromino[0].Y + 1] && !grid_base[(int)tetromino[1].X][(int)tetromino[1].Y + 1]) {
                tetromino[0].Y = tetromino[0].Y + 1;
                tetromino[1].Y = tetromino[1].Y + 1;
            } else {
                Domino_Place();
            }
        } else {
            Domino_Place();
        }
    }

    private void Domino_Place() {
        active_hold = true;
        grid_base[(int)tetromino[0].X][(int)tetromino[0].Y] = true;
        grid_base[(int)tetromino[1].X][(int)tetromino[1].Y] = true;
        grid_color[(int)tetromino[0].X][(int)tetromino[0].Y] = container_current[0];
        grid_color[(int)tetromino[1].X][(int)tetromino[1].Y] = container_current[1];
        score_points = score_points + 2 * 2;
        if(tetromino[1].Y == 0) won = true;
        container_current[0] = container_next[0];
        container_current[1] = container_next[1];
        container_next[0] = Domino_Roll();
        container_next[1] = Domino_Roll();
        Domino_Create();
        Check_Field();
    }

    List<Vector2> clear_temp = new ArrayList<Vector2>();

    private void Check_Field() {
        int points = 0;
        int bonus = 0;
        clear_temp.clear();
        for(int y = 14; y >= 0; y--) {
            for(int x = 0; x < 10; x++) {
                if(!IsCleared(x, y)) {
                    Pathfinder(x, y);
                    if(clear_temp.size() >= 4) {
                        points += (clear_temp.size() * 10);
                        bonus++;
                        clear.addAll(clear_temp);
                    }
                    clear_temp.clear();
                }
            }
        }

        if(points > 0) {
            alpha -= 5;
            score_points += (points * 2 * bonus * (score_level + 1));
            score_lives++;
            if(score_lives > (1 + score_level) * 10) {
                score_level++;
            }
            //Command_Collapse();
        }
    }

    private void Pathfinder(int x, int y) {
        clear_temp.add(new Vector2(x, y));
        if(y - 1 >=  0 && grid_color[x][y].compareTo("empty") != 0 && grid_color[x][y].compareTo(grid_color[x    ][y - 1]) == 0 && !IsClearedTemp(x    , y - 1)) Pathfinder(x    , y - 1);
        if(y + 1 <= 14 && grid_color[x][y].compareTo("empty") != 0 && grid_color[x][y].compareTo(grid_color[x    ][y + 1]) == 0 && !IsClearedTemp(x    , y + 1)) Pathfinder(x    , y + 1);
        if(x - 1 >=  0 && grid_color[x][y].compareTo("empty") != 0 && grid_color[x][y].compareTo(grid_color[x - 1][y    ]) == 0 && !IsClearedTemp(x - 1, y    )) Pathfinder(x - 1, y    );
        if(x + 1 <=  9 && grid_color[x][y].compareTo("empty") != 0 && grid_color[x][y].compareTo(grid_color[x + 1][y    ]) == 0 && !IsClearedTemp(x + 1, y    )) Pathfinder(x + 1, y    );
    }

    private boolean IsClearedTemp(int x, int y) {
    	for(int i = 0; i < clear_temp.size(); i++){
    		if(clear_temp.get(i).X == x && clear_temp.get(i).Y == y) return true;
    	}
        return false;
    }
    
}
