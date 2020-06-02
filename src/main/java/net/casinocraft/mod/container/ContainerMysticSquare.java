package net.casinocraft.mod.container;

import net.casinocraft.mod.tileentity.TileEntityMysticSquare;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMysticSquare extends Container{
    private final TileEntityMysticSquare tileCardTable;
    
    public int[][] grid      = new  int[4][4];
    public boolean[][] grid_move = new boolean[4][4];
    
    public String direction;

    public boolean match;
    
    public ContainerMysticSquare(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = (TileEntityMysticSquare) blockInventory;
    }
    
    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileCardTable);
    }
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
        	
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            
            
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){

    }
    
    public boolean canInteractWith(EntityPlayer playerIn){
        return this.tileCardTable.isUseableByPlayer(playerIn);
    }
}