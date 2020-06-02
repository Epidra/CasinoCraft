package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerMysticSquare;
import net.casinocraft.mod.util.Card;
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

public class TileEntityMysticSquare extends TileEntityCasino {
	
	public int[][] grid      = new  int[4][4];
    public boolean[][] grid_move = new boolean[4][4];
    
    public String direction;
	
	public EnumDyeColor color;
	
	public TileEntityMysticSquare(EnumDyeColor color){
		this.color = color;
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerMysticSquare(playerInventory, this);
    }
	
	
	public void start(){
		for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                grid[x][ y] = -1;
                grid_move[x][ y] = false;
            }
        }
        int i = 0;
        while(i < 15) {
            int x = rand.nextInt(4);
            int y = rand.nextInt(4);
            if(grid[x][y] == -1) {
                grid[x][y] = i;
                i++;
            }
        }
        direction = "null";
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    public void Move(String s) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(s == "up" && y > 0) if(grid[x][y - 1] == -1) grid_move[x][y] = true;
                if(s == "down" && y < 3) if(grid[x][y + 1] == -1) grid_move[x][y] = true;
                if(s == "left" && x > 0) if(grid[x - 1][y] == -1) grid_move[x][y] = true;
                if(s == "right" && x < 3) if(grid[x + 1][y] == -1) grid_move[x][y] = true;
            }
        }
        Change(s);
    }

    private void Change(String direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == "up" && grid_move[x][y]) { grid[x][y - 1] = grid[x][y]; grid[x][y] = -1; grid_move[x][y] = false; }
                if(direction == "down" && grid_move[x][y]) { grid[x][y + 1] = grid[x][y]; grid[x][y] = -1; grid_move[x][y] = false; }
                if(direction == "left" && grid_move[x][y]) { grid[x - 1][y] = grid[x][y]; grid[x][y] = -1; grid_move[x][y] = false; }
                if(direction == "right" && grid_move[x][y]) { grid[x + 1][y] = grid[x][y]; grid[x][y] = -1; grid_move[x][y] = false; }
            }
        }
    }
    
}
