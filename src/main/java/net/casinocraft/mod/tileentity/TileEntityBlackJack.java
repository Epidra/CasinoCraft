package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBlackJack;
import net.casinocraft.mod.util.Card;
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

public class TileEntityBlackJack extends TileEntityCasino {
	
	public List<Card> cards_player = new ArrayList<Card>();
    public List<Card> cards_dealer = new ArrayList<Card>();
    public int value_player;
    public int value_dealer;
    public boolean end;
    public boolean active_player;
    
    public EnumDyeColor color;
	
	public TileEntityBlackJack(EnumDyeColor color){
		this.color = color;
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerBlackJack(playerInventory, this);
    }
	
	public void start(){
		cards_player.clear();
        cards_dealer.clear();
        value_player = 0;
        value_dealer = 0;
        end = false;
        active_player = true;
        
        cards_player.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
        cards_player.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
        cards_dealer.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
        cards_dealer.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
        value_player = 0;
        int ace = 0;
        for(int i = 0; i < cards_player.size(); i++){
            if(cards_player.get(i).number == 13) {
                ace++;
            } else if(cards_player.get(i).number <= 9) {
                value_player = value_player + cards_player.get(i).number + 1;
            } else {
                value_player = value_player + 10;
            }
        }
        if(ace > 0) {
            while(ace > 0) {
                if(value_player <= 10) {
                    value_player = value_player + 11;
                } else {
                    value_player = value_player + 1;
                }
                ace--;
            }
        }
        if(value_player == 21) {
            end = true;
            active_player = false;
        }
	}
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    public void Click_Hit() {
    	Add_Card();
	}

	public void Click_Start(int i) {
		start();
	}

	public void Click_Stand() {
		active_player = false;
		while(!end){
			Add_Card();
		}
	}
    
    private void Add_Card() {
        if(active_player) {
            value_player = 0;
            cards_player.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
            int ace = 0;
            for(int i = 0; i < cards_player.size(); i++){
                if(cards_player.get(i).number == 13) {
                    ace++;
                } else if(cards_player.get(i).number <= 9) {
                    value_player = value_player + cards_player.get(i).number + 1;
                } else {
                    value_player = value_player + 10;
                }
            }
            if(ace > 0) {
                while(ace > 0) {
                    if(value_player <= 10) {
                        value_player = value_player + 11;
                    } else {
                        value_player = value_player + 1;
                    }
                    ace--;
                }
            }
            if(value_player > 21) {
                active_player = false;
                end = true;
            }
        } else {
            value_dealer = 0;
            int ace = 0;
            for(int i = 0; i < cards_dealer.size(); i++){
                if(cards_dealer.get(i).number == 13) {
                    ace++;
                } else if(cards_dealer.get(i).number <= 9) {
                    value_dealer = value_dealer + cards_dealer.get(i).number + 1;
                } else {
                    value_dealer = value_dealer + 10;
                }
            }
            if(ace > 0) {
                while(ace > 0) {
                    if(value_dealer <= 10) {
                        value_dealer = value_dealer + 11;
                    } else {
                        value_dealer = value_dealer + 1;
                    }
                    ace--;
                }
            }
            if(value_dealer < 17) {
                value_dealer = 0;
                cards_dealer.add(new Card(rand.nextInt(13) + 1, rand.nextInt(4)));
                ace = 0;
                for(int i = 0; i < cards_dealer.size(); i++){
                    if(cards_dealer.get(i).number == 13) {
                        ace++;
                    } else if(cards_dealer.get(i).number <= 9) {
                        value_dealer = value_dealer + cards_dealer.get(i).number + 1;
                    } else {
                        value_dealer = value_dealer + 10;
                    }
                }
                if(ace > 0) {
                    while(ace > 0) {
                        if(value_dealer <= 10) {
                            value_dealer = value_dealer + 11;
                        } else {
                            value_dealer = value_dealer + 1;
                        }
                        ace--;
                    }
                }
            }
            if(value_dealer > 16) end = true;
        }
    }

}