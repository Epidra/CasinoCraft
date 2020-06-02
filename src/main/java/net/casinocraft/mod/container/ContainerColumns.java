package net.casinocraft.mod.container;

import java.util.ArrayList;
import java.util.List;

import net.casinocraft.mod.tileentity.TileEntityColumns;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerColumns extends Container{
    private final TileEntityColumns tileCardTable;
    
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
    
    public ContainerColumns(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = (TileEntityColumns) blockInventory;
    }
    
    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileCardTable);
    }
    
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
        	
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            
            if(this.active_hold       != this.tileCardTable.active_hold)      { icontainerlistener.sendProgressBarUpdate(this,  0, 0); }
            if(this.container_next    != this.tileCardTable.container_next)   { icontainerlistener.sendProgressBarUpdate(this,  1, 0); }
            if(this.container_hold    != this.tileCardTable.container_hold)   { icontainerlistener.sendProgressBarUpdate(this,  2, 0); }
            if(this.container_current != this.tileCardTable.container_current){ icontainerlistener.sendProgressBarUpdate(this,  3, 0); }
            if(this.time_last         != this.tileCardTable.time_last)        { icontainerlistener.sendProgressBarUpdate(this,  4, 0); }
            if(this.time_break        != this.tileCardTable.time_break)       { icontainerlistener.sendProgressBarUpdate(this,  5, 0); }
            if(this.timer             != this.tileCardTable.timer)            { icontainerlistener.sendProgressBarUpdate(this,  6, 0); }
            if(this.won               != this.tileCardTable.won)              { icontainerlistener.sendProgressBarUpdate(this,  7, 0); }
            if(this.alpha             != this.tileCardTable.alpha)            { icontainerlistener.sendProgressBarUpdate(this,  8, 0); }
            if(this.clear             != this.tileCardTable.clear)            { icontainerlistener.sendProgressBarUpdate(this,  9, 0); }
            if(this.score_level       != this.tileCardTable.score_level)      { icontainerlistener.sendProgressBarUpdate(this, 10, 0); }
            if(this.score_lives       != this.tileCardTable.score_lives)      { icontainerlistener.sendProgressBarUpdate(this, 11, 0); }
            if(this.score_points      != this.tileCardTable.score_points)     { icontainerlistener.sendProgressBarUpdate(this, 12, 0); }
            
            if(this.tetromino[0] != this.tileCardTable.tetromino[0]){ icontainerlistener.sendProgressBarUpdate(this,  13, 0); }
            if(this.tetromino[1] != this.tileCardTable.tetromino[1]){ icontainerlistener.sendProgressBarUpdate(this,  14, 0); }
            if(this.tetromino[2] != this.tileCardTable.tetromino[2]){ icontainerlistener.sendProgressBarUpdate(this,  15, 0); }
            
            for(int y = 0; y < 15; y++){
            	for(int x = 0; x < 10; x++){
            		if(this.grid_base [x][y] != this.tileCardTable.grid_base [x][y]){ icontainerlistener.sendProgressBarUpdate(this,100+x, y); }
            		if(this.grid_color[x][y] != this.tileCardTable.grid_color[x][y]){ icontainerlistener.sendProgressBarUpdate(this,200+x, y); }
            	}
            }
            
        }
        
        this.active_hold       = this.tileCardTable.active_hold;
        this.container_next    = this.tileCardTable.container_next;
        this.container_hold    = this.tileCardTable.container_hold;
        this.container_current = this.tileCardTable.container_current;
        this.time_last         = this.tileCardTable.time_last;
        this.time_break        = this.tileCardTable.time_break;
        this.timer             = this.tileCardTable.timer;
        this.won               = this.tileCardTable.won;
        this.alpha             = this.tileCardTable.alpha;
        this.clear             = this.tileCardTable.clear;
        this.score_level       = this.tileCardTable.score_level;
        this.score_lives       = this.tileCardTable.score_lives;
        this.score_points      = this.tileCardTable.score_points;
        
        this.tetromino[0] = this.tileCardTable.tetromino[0];
        this.tetromino[1] = this.tileCardTable.tetromino[1];
        this.tetromino[2] = this.tileCardTable.tetromino[2];
        
        for(int y = 0; y < 15; y++){
        	for(int x = 0; x < 10; x++){
        		this.grid_base [x][y] = this.tileCardTable.grid_base [x][y];
        		this.grid_color[x][y] = this.tileCardTable.grid_color[x][y];
        	}
        }
        
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
    	if(id ==  0) this.tileCardTable.active_hold       = this.active_hold;
    	if(id ==  1) this.tileCardTable.container_next    = this.container_next;
    	if(id ==  2) this.tileCardTable.container_hold    = this.container_hold;
    	if(id ==  3) this.tileCardTable.container_current = this.container_current;
    	if(id ==  4) this.tileCardTable.time_last         = this.time_last;
    	if(id ==  5) this.tileCardTable.time_break        = this.time_break;
    	if(id ==  6) this.tileCardTable.timer             = this.timer;
    	if(id ==  7) this.tileCardTable.won               = this.won;
    	if(id ==  8) this.tileCardTable.alpha             = this.alpha;
    	if(id ==  9) this.tileCardTable.clear             = this.clear;
    	if(id == 10) this.tileCardTable.score_level       = this.score_level;
    	if(id == 11) this.tileCardTable.score_lives       = this.score_lives;
    	if(id == 12) this.tileCardTable.score_points      = this.score_points;
        
    	if(id == 13) this.tileCardTable.tetromino[0] = this.tetromino[0];
    	if(id == 14) this.tileCardTable.tetromino[1] = this.tetromino[1];
    	if(id == 15) this.tileCardTable.tetromino[2] = this.tetromino[2];
        
    	if(id >= 200){
    		this.tileCardTable.grid_color[id-200][data] = this.grid_color[id-200][data];
    	} else
    	if(id >= 100){
    		this.tileCardTable.grid_base [id-100][data] = this.grid_base [id-100][data];
    	}
    	
        
    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileCardTable.isUseableByPlayer(playerIn);
    }
}