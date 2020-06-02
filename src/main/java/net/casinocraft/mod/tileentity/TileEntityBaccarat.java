package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBaccarat;
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

public class TileEntityBaccarat extends TileEntityCasino {
	
	public List<Card> cards_player = new ArrayList<Card>();
	public List<Card> cards_dealer = new ArrayList<Card>();
	public int value_player;
	public int value_dealer;
	public int status;
	public boolean end = true;
	public boolean active_player;
	
	public EnumDyeColor color;
	
	public TileEntityBaccarat(EnumDyeColor color){
		this.color = color;
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerBaccarat(playerInventory, this);
    }
	
	
	public void start(){
		 cards_player.clear();
         cards_dealer.clear();
         value_player = 0;
         value_dealer = 0;
         end = false;
         status = 0;
         
         active_player = true;
         cards_player.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
         cards_player.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
         cards_dealer.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
         cards_dealer.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
         value_player = 0;
         for(int i = 0; i < cards_player.size(); i++){
             if(cards_player.get(i).number == 13) {
                 value_player += 1;
             } else if(cards_player.get(i).number <= 9) {
                 value_player = value_player + cards_player.get(i).number + 1;
             }
         }
         value_dealer = 0;
         for(int i = 0; i < cards_dealer.size(); i++){
             if(cards_dealer.get(i).number == 13) {
                 value_dealer += 1;
             } else if(cards_dealer.get(i).number <= 9) {
                 value_dealer = value_dealer + cards_dealer.get(i).number + 1;
             }
         }
         while(value_player >= 10) { value_player -= 10; }
         while(value_dealer >= 10) { value_dealer -= 10; }
         if(value_player >= 8 || value_dealer >= 8) {
             status = 1;
             Result();
         } else {
             status = 2;
         }
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    public void Click_Hit() {
		Add_Card(true);
	}

	public void Click_Start(int i) {
		start();
	}

	public void Click_Stand() {
		Add_Card(false);
	}
    
    private void Add_Card(boolean player) {
        if(player) {
            value_player = 0;
            cards_player.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
            for(int i = 0; i < cards_player.size(); i++){
                if(cards_player.get(i).number == 13) {
                    value_player += 1;
                } else if(cards_player.get(i).number <= 9) {
                    value_player = value_player + cards_player.get(i).number + 1;
                }
            }
            while(value_player >= 10) { value_player -= 10; }
        }

        boolean temp_draw = false;

        if(cards_player.size() == 2 || value_dealer <= 3) { temp_draw = true; } else if(value_dealer == 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 5 && value_player >= 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 6 && value_player >= 6 && value_player <= 7) { temp_draw = true; }

        if(temp_draw) {
            cards_dealer.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
            value_dealer = 0;
            for(int i = 0; i < cards_dealer.size(); i++){
                if(cards_dealer.get(i).number == 13) {
                    value_dealer += 1;
                } else if(cards_dealer.get(i).number <= 9) {
                    value_dealer = value_dealer + cards_dealer.get(i).number + 1;
                }
            }
            while(value_dealer >= 10) { value_dealer -= 10; }
        }
        Result();
    }

    private void Result() {
        active_player = false;
        end = true;
        if(status == 2) status = 3;
    }
    
}