package net.casinocraft.mod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerMemory;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityMemory extends TileEntityCasino {
	
	public String grid_main[][] = new String[6][6];
    public boolean selected_A;
    public boolean selected_B;
    public Vector2 selected_A_pos;
    public Vector2 selected_B_pos;
    public int timer;
    public boolean end;
    
    public EnumDyeColor color;
	
	public TileEntityMemory(EnumDyeColor color){
		this.color = color;
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerMemory(playerInventory, this);
    }
	
	public void start(){
		selected_A = false;
        selected_B = false;
        timer = 0;
        end = true;
        selected_A_pos = new Vector2(-1, -1);
        selected_B_pos = new Vector2(-1, -1);
        Command_Create_Grid();
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	if(timer > 0){
    		timer-=10;
    		if(timer <= 0){
    			if(0 == grid_main[selected_A_pos.X][selected_A_pos.Y].compareTo(grid_main[selected_B_pos.X][selected_B_pos.Y])){
    				grid_main[selected_A_pos.X][selected_A_pos.Y] = "empty";
                    grid_main[selected_B_pos.X][selected_B_pos.Y] = "empty";
    			} else {
    				ChangeNameBack(selected_A_pos.X, selected_A_pos.Y, grid_main[selected_A_pos.X][selected_A_pos.Y]);
    				ChangeNameBack(selected_B_pos.X, selected_B_pos.Y, grid_main[selected_B_pos.X][selected_B_pos.Y]);
    			}
    			boolean temp = false;
                for(int x = 0; x < 6; x++) {
                    for(int y = 0; y < 6; y++) {
                        if(grid_main[x][y] != "empty") temp = true;
                    }
                }
                if(!temp) {
                	end = true;
                }
                selected_A = false;
                selected_B = false;
                selected_A_pos = new Vector2(-1, -1);
                selected_B_pos = new Vector2(-1, -1);
    		}
    	}
    }
    
    private void ChangeName(int x, int y, String s){
    	if(0 == s.compareTo("fire"))    grid_main[x][y] = "Fire";
    	if(0 == s.compareTo("air"))     grid_main[x][y] = "Air";
    	if(0 == s.compareTo("thunder")) grid_main[x][y] = "Thunder";
    	if(0 == s.compareTo("water"))   grid_main[x][y] = "Water";
    	if(0 == s.compareTo("ice"))     grid_main[x][y] = "Ice";
    	if(0 == s.compareTo("earth"))   grid_main[x][y] = "Earth";
    	if(0 == s.compareTo("metal"))   grid_main[x][y] = "Metal";
    	if(0 == s.compareTo("nature"))  grid_main[x][y] = "Nature";
    	if(0 == s.compareTo("light"))   grid_main[x][y] = "Light";
    	if(0 == s.compareTo("dark"))    grid_main[x][y] = "Dark";
    }
    
    private void ChangeNameBack(int x, int y, String s){
    	if(0 == s.compareTo("Fire"))    grid_main[x][y] = "fire";
    	if(0 == s.compareTo("Air"))     grid_main[x][y] = "air";
    	if(0 == s.compareTo("Thunder")) grid_main[x][y] = "thunder";
    	if(0 == s.compareTo("Water"))   grid_main[x][y] = "water";
    	if(0 == s.compareTo("Ice"))     grid_main[x][y] = "ice";
    	if(0 == s.compareTo("Earth"))   grid_main[x][y] = "earth";
    	if(0 == s.compareTo("Metal"))   grid_main[x][y] = "metal";
    	if(0 == s.compareTo("Nature"))  grid_main[x][y] = "nature";
    	if(0 == s.compareTo("Light"))   grid_main[x][y] = "light";
    	if(0 == s.compareTo("Dark"))    grid_main[x][y] = "dark";
    }
    
    public void Click_Mino(int x, int y) {
        if(!selected_A) {
            if(grid_main[x][y] != "empty") {
                selected_A = true;
                selected_A_pos = new Vector2(x, y);
                ChangeName(x, y, grid_main[x][y]);
            }
        } else if(!selected_B) {
            if(grid_main[x][y] != "empty" && selected_A_pos != new Vector2(x, y)) {
                selected_B = true;
                selected_B_pos = new Vector2(x, y);
                ChangeName(x, y, grid_main[x][y]);
                timer = 100;
            }
        }
    }
    
    private void Command_Create_Grid() {
        for(int x = 0; x < 6; x++) {
            for(int y = 0; y < 6; y++) {
                grid_main[x][y] = "empty";
            }
        }
        int filler = 36;

        int color_fire    = 0;
        int color_air     = 0;
        int color_thunder = 0;
        int color_water   = 0;
        int color_ice     = 0;
        int color_earth   = 0;
        int color_metal   = 0;
        int color_nature  = 0;
        int color_light   = 0;
        int color_dark    = 0;
        while(filler > 0) {
            if(filler > 0) { color_fire    += 2; filler -= 2; }
            if(filler > 0) { color_air     += 2; filler -= 2; }
            if(filler > 0) { color_thunder += 2; filler -= 2; }
            if(filler > 0) { color_water   += 2; filler -= 2; }
            if(filler > 0) { color_ice     += 2; filler -= 2; }
            if(filler > 0) { color_earth   += 2; filler -= 2; }
            if(filler > 0) { color_metal   += 2; filler -= 2; }
            if(filler > 0) { color_nature  += 2; filler -= 2; }
            if(filler > 0) { color_light   += 2; filler -= 2; }
            if(filler > 0) { color_dark    += 2; filler -= 2; }
        }
        int X1 = 0; int X2 = 0; int Y1 = 0; int Y2 = 0;
        //X1 = 3; X2 = 6; Y1 = 2; Y2 = 6;
        for(int x = 0; x < 6; x++) {
            for(int y = 0; y < 6; y++) {
                boolean temp = false;
                while(!temp) {
                    int r = rand.nextInt(10);
                    if(r == 0 && color_fire    > 0) { grid_main[X1 + x][Y1 + y] = "fire";    color_fire--;    temp = true; }
                    if(r == 1 && color_air     > 0) { grid_main[X1 + x][Y1 + y] = "air";     color_air--;     temp = true; }
                    if(r == 2 && color_thunder > 0) { grid_main[X1 + x][Y1 + y] = "thunder"; color_thunder--; temp = true; }
                    if(r == 3 && color_water   > 0) { grid_main[X1 + x][Y1 + y] = "water";   color_water--;   temp = true; }
                    if(r == 4 && color_ice     > 0) { grid_main[X1 + x][Y1 + y] = "ice";     color_ice--;     temp = true; }
                    if(r == 5 && color_earth   > 0) { grid_main[X1 + x][Y1 + y] = "earth";   color_earth--;   temp = true; }
                    if(r == 6 && color_metal   > 0) { grid_main[X1 + x][Y1 + y] = "metal";   color_metal--;   temp = true; }
                    if(r == 7 && color_nature  > 0) { grid_main[X1 + x][Y1 + y] = "nature";  color_nature--;  temp = true; }
                    if(r == 8 && color_light   > 0) { grid_main[X1 + x][Y1 + y] = "light";   color_light--;   temp = true; }
                    if(r == 9 && color_dark    > 0) { grid_main[X1 + x][Y1 + y] = "dark";    color_dark--;    temp = true; }
                }
            }
        }
    }

}