package net.casinocraft.mod.container;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.casinocraft.mod.tileentity.TileEntityBaccarat;
import net.casinocraft.mod.tileentity.TileEntityPyramid;
import net.casinocraft.mod.util.Card;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPyramid extends Container{
    private final TileEntityPyramid tileCardTable;
    
    public int selector_card = -1;
    public Vector2 selector_pos;

    public Vector2[] cards_field = new Vector2[29];
    public List<Vector2> cards_stack = new ArrayList<Vector2>();
    public List<Vector2> cards_reserve = new ArrayList<Vector2>();
    
    public int score_lives = 2;
    public int score_points = 0;
    
    public ContainerPyramid(InventoryPlayer playerInventory, IInventory blockInventory){
        this.tileCardTable = (TileEntityPyramid) blockInventory;
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