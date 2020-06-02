package net.casinocraft.mod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerVideoPoker;
import net.casinocraft.mod.util.Card;
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

public class TileEntityVideoPoker extends TileEntityCasino {
	
	public boolean end;
    public String hand;
    public Card card1;
    public Card card2;
    public Card card3;
    public Card card4;
    public Card card5;
    public boolean hold1;
    public boolean hold2;
    public boolean hold3;
    public boolean hold4;
    public boolean hold5;
	
	public TileEntityVideoPoker(){
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerVideoPoker(playerInventory, this);
    }
	
	public void start(){
        card1 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
        card2 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
        card3 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
        card4 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
        card5 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
        hold1 = false;
        hold2 = false;
        hold3 = false;
        hold4 = false;
        hold5 = false;
        end = false;
        hand = "empty";
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    public void Click_Start(int i) {
		if(i == 0){
			start();
		}
		if(i == 1){
			if(!hold1) card1 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
	        if(!hold2) card2 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
	        if(!hold3) card3 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
	        if(!hold4) card4 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
	        if(!hold5) card5 = new Card(rand.nextInt(13) + 1, rand.nextInt(4));
	        Sort();
	        Result();
		}
		
	}

	public void Click_Hold(int i) {
		if(i == 1) { if(hold1) hold1 = false; else { hold1 = true; } }
        if(i == 2) { if(hold2) hold2 = false; else { hold2 = true; } }
        if(i == 3) { if(hold3) hold3 = false; else { hold3 = true; } }
        if(i == 4) { if(hold4) hold4 = false; else { hold4 = true; } }
        if(i == 5) { if(hold5) hold5 = false; else { hold5 = true; } }
	}
    
    private void Sort() {
        if(card1.number > card5.number) {
            Card z = card1;
            card1 = card2;
            card2 = card3;
            card3 = card4;
            card4 = card5;
            card5 = z;
        }
        if(card1.number > card4.number) {
            Card z = card1;
            card1 = card2;
            card2 = card3;
            card3 = card4;
            card4 = z;
        }
        if(card1.number > card3.number) {
            Card z = card1;
            card1 = card2;
            card2 = card3;
            card3 = z;
        }
        if(card1.number > card2.number) {
            Card z = card1;
            card1 = card2;
            card2 = z;
        }
        if(card2.number > card5.number) {
            Card z = card2;
            card2 = card3;
            card3 = card4;
            card4 = card5;
            card5 = z;
        }
        if(card2.number > card4.number) {
            Card z = card2;
            card2 = card3;
            card3 = card4;
            card4 = z;
        }
        if(card2.number > card3.number) {
            Card z = card2;
            card2 = card3;
            card3 = z;
        }
        if(card3.number > card5.number) {
            Card z = card3;
            card3 = card4;
            card4 = card5;
            card5 = z;
        }
        if(card3.number > card4.number) {
            Card z = card3;
            card3 = card4;
            card4 = z;
        }
        if(card4.number > card5.number) {
            Card z = card4;
            card4 = card5;
            card5 = z;
        }
    }

    private void Result() {
        if(card1.number == 9 && card2.number == 10 && card3.number == 11 && card4.number == 12 && card5.number == 13 && card1.suit == card2.suit && card1.suit == card3.suit && card1.suit == card4.suit && card1.suit == card5.suit) {
            hand = "ROYAL FLUSH!!";
        } else if(card1.number <= 9 && card1.number + 1 == card2.number && card1.number + 2 == card3.number && card1.number + 3 == card4.number && card1.number + 4 == card5.number && card1.suit == card2.suit && card1.suit == card3.suit && card1.suit == card4.suit && card1.suit == card5.suit) {
            hand = "Straight Flush";
        } else if(card1.number == card2.number && card1.number == card3.number && card1.number == card4.number && card1.number != card5.number) {
            hand = "4 of a Kind";
        } else if(card2.number == card3.number && card2.number == card4.number && card2.number == card5.number && card2.number != card1.number) {
            hand = "4 of a Kind";
        } else if(card1.number == card2.number && card1.number == card3.number && card1.number != card4.number && card4.number == card5.number) {
            hand = "Full House";
        } else if(card1.number == card2.number && card1.number != card3.number && card3.number == card4.number && card3.number == card5.number) {
            hand = "Full House";
        } else if(card1.suit == card2.suit && card1.suit == card3.suit && card1.suit == card4.suit && card1.suit == card5.suit) {
            hand = "Flush";
        } else if(card1.number <= 9 && card1.number + 1 == card2.number && card1.number + 2 == card3.number && card1.number + 3 == card4.number && card1.number + 4 == card5.number) {
            hand = "Straight";
        } else if(card1.number == card2.number && card1.number == card3.number && card1.number != card4.number && card1.number != card5.number) {
            hand = "3 of a Kind";
        } else if(card2.number == card3.number && card2.number == card4.number && card2.number != card1.number && card2.number != card5.number) {
            hand = "3 of a Kind";
        } else if(card3.number == card4.number && card3.number == card5.number && card3.number != card1.number && card3.number != card2.number) {
            hand = "3 of a Kind";
        } else if(card1.number == card2.number && card3.number == card4.number) {
            hand = "Two Pair";
        } else if(card1.number == card2.number && card4.number == card5.number) {
            hand = "Two Pair";
        } else if(card2.number == card3.number && card4.number == card5.number) {
            hand = "Two Pair";
        } else if(card1.number >= 10 && card1.number == card2.number) {
            hand = "Jacks or Better";
        } else if(card2.number >= 10 && card2.number == card3.number) {
            hand = "Jacks or Better";
        } else if(card3.number >= 10 && card3.number == card4.number) {
            hand = "Jacks or Better";
        } else if(card4.number >= 10 && card4.number == card5.number) {
            hand = "Jacks or Better";
        } else {

        }
        end = true;
    }
    
}