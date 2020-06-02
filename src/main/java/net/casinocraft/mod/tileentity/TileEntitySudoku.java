package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerSudoku;
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

public class TileEntitySudoku extends TileEntityCasino {
	
	public Vector2 selector_grid = new Vector2(4,4);
    public int selector_number = 0;

    public boolean active_selection = false;

    public int[][] grid        = new int[9][9];
    public int[][] grid_mirror = new int[9][9];

    public boolean match;
	
	public EnumDyeColor color;
	
	public TileEntitySudoku(EnumDyeColor color){
		this.color = color;
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerSudoku(playerInventory, this);
    }
	
	
	public void start(){
		match = false;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                grid[x][y] = 0;
            }
        }

        int r = rand.nextInt(9)+1;
        grid[4][4] = r;
        grid_mirror[4][4] = r;
        Generate_Square(5, 3, 0);
        Generate_Square(5, 0, 3);
        Generate_Square(5, 6, 3);
        Generate_Square(5, 3, 6);

        Generate_Square(5, 0, 0);
        Generate_Square(5, 6, 0);
        Generate_Square(5, 0, 6);
        Generate_Square(5, 6, 6);
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    private void Generate_Square(int index2, int xi, int yi) {
        int index = 1;
        while(index < 5) {
            int x = rand.nextInt(3);
            int y = rand.nextInt(3);
            int i = rand.nextInt(9);
            if(grid[0][yi + y] != i && grid[1][yi + y] != i && grid[2][yi + y] != i && grid[3][yi + y] != i && grid[4][yi + y] != i && grid[5][yi + y] != i && grid[6][yi + y] != i && grid[7][yi + y] != i && grid[8][yi + y] != i) {
                if(grid[xi + x][0] != i && grid[xi + x][1] != i && grid[xi + x][2] != i && grid[xi + x][3] != i && grid[xi + x][4] != i && grid[xi + x][5] != i && grid[xi + x][6] != i && grid[xi + x][7] != i && grid[xi + x][8] != i) {
                    if(grid[xi + 0][yi + 0] != i && grid[xi + 1][yi + 0] != i && grid[xi + 2][yi + 0] != i && grid[xi + 0][yi + 1] != i && grid[xi + 1][yi + 1] != i && grid[xi + 2][yi + 1] != i && grid[xi + 0][yi + 2] != i && grid[xi + 1][yi + 2] != i && grid[xi + 2][yi + 2] != i) {
                        grid[xi + x][yi + y] = i;
                        grid_mirror[xi + x][yi + y] = i;
                        index++;
                    }
                }
            }
        }
    }

    private void Select() {
        if(grid_mirror[(int)selector_grid.X][(int)selector_grid.Y] == 0) {
            active_selection = true;
        }
    }

    private void Insert() {
        grid[(int)selector_grid.X][(int)selector_grid.Y] = selector_number + 1;
        Check();
        active_selection = false;
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
                n[grid[x][y]]++;
            }
            match_vert[y] = false;
            if(n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1) match_vert[y] = true;
        }

        for(int x = 0; x < 9; x++) {
            for(int i = 0; i < 9; i++) { n[i] = 0; }
            for(int y = 0; y < 9; y++) {
                n[grid[x][y]]++;
            }
            match_hori[x] = false;
            if(n[1] == 1 && n[2] == 1 && n[3] == 1 && n[4] == 1 && n[5] == 1 && n[6] == 1 && n[7] == 1 && n[8] == 1 && n[9] == 1) match_hori[x] = true;
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                for(int i = 0; i < 9; i++) { n[i] = 0; }
                for(int yi = y * 3; yi < y * 3 + 3; yi++) {
                    for(int xi = x * 3; xi < x * 3 + 3; xi++) {
                        n[grid[xi][yi]]++;
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
