package net.casinocraft.mod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerPyramid;
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

public class TileEntityPyramid extends TileEntityCasino {
	
	public int selector_card = -1;
    public Vector2 selector_pos;

    public Vector2[] cards_field = new Vector2[29];
    public List<Vector2> cards_stack = new ArrayList<Vector2>();
    public List<Vector2> cards_reserve = new ArrayList<Vector2>();
	
    public int score_lives = 2;
    public int score_points = 0;
    
	public EnumDyeColor color;
	
	public TileEntityPyramid(EnumDyeColor color){
		this.color = color;
		start();
	}
    
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerPyramid(playerInventory, this);
    }
	
	
	public void start(){
		score_lives = 2;
		score_points = 0;

        List<Vector2> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        cards_field[28] = new Vector2(-1, -1);
        selector_pos = new Vector2(9, 12);
        for(int i = 0; i < 28; i++) deck.remove(0);
        cards_reserve.addAll(deck);
        cards_stack.clear();
	}
	
	public void Restart() {

        List<Vector2> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        cards_field[28] = new Vector2(-1, -1);
        selector_pos = new Vector2(9, 12);
        for(int i = 0; i < 28; i++) deck.remove(0);
        cards_reserve.addAll(deck);
        cards_stack.clear();
    }
	
    /** Like the old updateEntity(), except more generic. */
    public void update(){
    	
    }
    
    private List<Vector2> ShuffleDeck() {
        List<Vector2> stack = new ArrayList<Vector2>();
        List<Vector2> deck = new ArrayList<Vector2>();

        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 13; x++) {
                stack.add(new Vector2(x + 1, y));
            }
        }
        
        while(stack.size() > 0) {
            int r = stack.size() == 1 ? 0 : rand.nextInt(stack.size() - 1);
            deck.add(stack.get(r));
            stack.remove(r);
        }
        
        return deck;
    }
    
    public void DrawReserve() {
        if(cards_reserve.size() > 0) {
            cards_stack.add(cards_reserve.get(0));
            cards_reserve.remove(0);
            cards_field[28] = cards_stack.get(cards_stack.size() - 1);
        } else {
            score_lives--;
            cards_reserve.addAll(cards_stack);
            cards_stack.clear();
        }
    }

    public void TouchField(int x, int y) {
        if(x == 10 && y == 1) if(IsCardSelectable( 0)){ CompareCards(0); }
        if(x == 11 && y == 1) if(IsCardSelectable( 0)){ CompareCards(0); }

        if(x ==  9 && y == 2) if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x == 10 && y == 2) if(IsCardSelectable( 1)){ CompareCards(1); } else if(IsCardSelectable( 0)){CompareCards(0); }
        if(x == 11 && y == 2) if(IsCardSelectable( 2)){ CompareCards(2); } else if(IsCardSelectable( 0)){CompareCards(0); }
        if(x == 12 && y == 2) if(IsCardSelectable( 2)){ CompareCards(2); }

        if(x ==  8 && y == 3) if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  9 && y == 3) if(IsCardSelectable( 3)){ CompareCards(3); } else if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x == 10 && y == 3) if(IsCardSelectable( 4)){ CompareCards(4); } else if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x == 11 && y == 3) if(IsCardSelectable( 4)){ CompareCards(4); } else if(IsCardSelectable( 2)){ CompareCards(2); }
        if(x == 12 && y == 3) if(IsCardSelectable( 5)){ CompareCards(5); } else if(IsCardSelectable( 2)){ CompareCards(2); }
        if(x == 13 && y == 3) if(IsCardSelectable( 5)){ CompareCards(5); }

        if(x ==  7 && y == 4) if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  8 && y == 4) if(IsCardSelectable( 6)){ CompareCards(6); } else if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  9 && y == 4) if(IsCardSelectable( 7)){ CompareCards(7); } else if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x == 10 && y == 4) if(IsCardSelectable( 7)){ CompareCards(7); } else if(IsCardSelectable( 4)){ CompareCards(4); }
        if(x == 11 && y == 4) if(IsCardSelectable( 8)){ CompareCards(8); } else if(IsCardSelectable( 4)){ CompareCards(4); }
        if(x == 12 && y == 4) if(IsCardSelectable( 8)){ CompareCards(8); } else if(IsCardSelectable( 5)){ CompareCards(5); }
        if(x == 13 && y == 4) if(IsCardSelectable( 9)){ CompareCards(9); } else if(IsCardSelectable( 5)){ CompareCards(5); }
        if(x == 14 && y == 4) if(IsCardSelectable( 9)){ CompareCards(9); }

        if(x ==  6 && y == 5) if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  7 && y == 5) if(IsCardSelectable(10)){ CompareCards(10); } else if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  8 && y == 5) if(IsCardSelectable(11)){ CompareCards(11); } else if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  9 && y == 5) if(IsCardSelectable(11)){ CompareCards(11); } else if(IsCardSelectable( 7)){ CompareCards(7); }
        if(x == 10 && y == 5) if(IsCardSelectable(12)){ CompareCards(12); } else if(IsCardSelectable( 7)){ CompareCards(7); }
        if(x == 11 && y == 5) if(IsCardSelectable(12)){ CompareCards(12); } else if(IsCardSelectable( 8)){ CompareCards(8); }
        if(x == 12 && y == 5) if(IsCardSelectable(13)){ CompareCards(13); } else if(IsCardSelectable( 8)){ CompareCards(8); }
        if(x == 13 && y == 5) if(IsCardSelectable(13)){ CompareCards(13); } else if(IsCardSelectable( 9)){ CompareCards(9); }
        if(x == 14 && y == 5) if(IsCardSelectable(14)){ CompareCards(14); } else if(IsCardSelectable( 9)){ CompareCards(9); }
        if(x == 15 && y == 5) if(IsCardSelectable(14)){ CompareCards(14); }

        if(x ==  5 && y == 6) if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  6 && y == 6) if(IsCardSelectable(15)){ CompareCards(15); } else if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  7 && y == 6) if(IsCardSelectable(16)){ CompareCards(16); } else if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  8 && y == 6) if(IsCardSelectable(16)){ CompareCards(16); } else if(IsCardSelectable(11)){ CompareCards(11); }
        if(x ==  9 && y == 6) if(IsCardSelectable(17)){ CompareCards(17); } else if(IsCardSelectable(11)){ CompareCards(11); }
        if(x == 10 && y == 6) if(IsCardSelectable(17)){ CompareCards(17); } else if(IsCardSelectable(12)){ CompareCards(12); }
        if(x == 11 && y == 6) if(IsCardSelectable(18)){ CompareCards(18); } else if(IsCardSelectable(12)){ CompareCards(12); }
        if(x == 12 && y == 6) if(IsCardSelectable(18)){ CompareCards(18); } else if(IsCardSelectable(13)){ CompareCards(13); }
        if(x == 13 && y == 6) if(IsCardSelectable(19)){ CompareCards(19); } else if(IsCardSelectable(13)){ CompareCards(13); }
        if(x == 14 && y == 6) if(IsCardSelectable(19)){ CompareCards(19); } else if(IsCardSelectable(14)){ CompareCards(14); }
        if(x == 15 && y == 6) if(IsCardSelectable(20)){ CompareCards(20); } else if(IsCardSelectable(14)){ CompareCards(14); }
        if(x == 16 && y == 6) if(IsCardSelectable(20)){ CompareCards(20); }

        if(x ==  4 && y == 7) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  5 && y == 7) if(IsCardSelectable(21)){ CompareCards(21); } else if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  6 && y == 7) if(IsCardSelectable(22)){ CompareCards(22); } else if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  7 && y == 7) if(IsCardSelectable(22)){ CompareCards(22); } else if(IsCardSelectable(16)){ CompareCards(16); }
        if(x ==  8 && y == 7) if(IsCardSelectable(23)){ CompareCards(23); } else if(IsCardSelectable(16)){ CompareCards(16); }
        if(x ==  9 && y == 7) if(IsCardSelectable(23)){ CompareCards(23); } else if(IsCardSelectable(17)){ CompareCards(17); }
        if(x == 10 && y == 7) if(IsCardSelectable(24)){ CompareCards(24); } else if(IsCardSelectable(17)){ CompareCards(17); }
        if(x == 11 && y == 7) if(IsCardSelectable(24)){ CompareCards(24); } else if(IsCardSelectable(18)){ CompareCards(18); }
        if(x == 12 && y == 7) if(IsCardSelectable(25)){ CompareCards(25); } else if(IsCardSelectable(18)){ CompareCards(18); }
        if(x == 13 && y == 7) if(IsCardSelectable(25)){ CompareCards(25); } else if(IsCardSelectable(19)){ CompareCards(19); }
        if(x == 14 && y == 7) if(IsCardSelectable(26)){ CompareCards(26); } else if(IsCardSelectable(19)){ CompareCards(19); }
        if(x == 15 && y == 7) if(IsCardSelectable(26)){ CompareCards(26); } else if(IsCardSelectable(20)){ CompareCards(20); }
        if(x == 16 && y == 7) if(IsCardSelectable(27)){ CompareCards(27); } else if(IsCardSelectable(20)){ CompareCards(20); }
        if(x == 17 && y == 7) if(IsCardSelectable(27)){ CompareCards(27); }

        if(x ==  4 && y == 8) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  5 && y == 8) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  6 && y == 8) if(IsCardSelectable(22)){ CompareCards(22); }
        if(x ==  7 && y == 8) if(IsCardSelectable(22)){ CompareCards(22); }
        if(x ==  8 && y == 8) if(IsCardSelectable(23)){ CompareCards(23); }
        if(x ==  9 && y == 8) if(IsCardSelectable(23)){ CompareCards(23); }
        if(x == 10 && y == 8) if(IsCardSelectable(24)){ CompareCards(24); }
        if(x == 11 && y == 8) if(IsCardSelectable(24)){ CompareCards(24); }
        if(x == 12 && y == 8) if(IsCardSelectable(25)){ CompareCards(25); }
        if(x == 13 && y == 8) if(IsCardSelectable(25)){ CompareCards(25); }
        if(x == 14 && y == 8) if(IsCardSelectable(26)){ CompareCards(26); }
        if(x == 15 && y == 8) if(IsCardSelectable(26)){ CompareCards(26); }
        if(x == 16 && y == 8) if(IsCardSelectable(27)){ CompareCards(27); }
        if(x == 17 && y == 8) if(IsCardSelectable(27)){ CompareCards(27); }
    }

    private boolean IsCardSelectable(int id) {
        switch(id) {
            case  0: if(cards_field[ 0].Y != -1 && cards_field[ 1].Y == -1 && cards_field[ 2].Y == -1) return true; break;

            case  1: if(cards_field[ 1].Y != -1 && cards_field[ 3].Y == -1 && cards_field[ 4].Y == -1) return true; break;
            case  2: if(cards_field[ 2].Y != -1 && cards_field[ 4].Y == -1 && cards_field[ 5].Y == -1) return true; break;

            case  3: if(cards_field[ 3].Y != -1 && cards_field[ 6].Y == -1 && cards_field[ 7].Y == -1) return true; break;
            case  4: if(cards_field[ 4].Y != -1 && cards_field[ 7].Y == -1 && cards_field[ 8].Y == -1) return true; break;
            case  5: if(cards_field[ 5].Y != -1 && cards_field[ 8].Y == -1 && cards_field[ 9].Y == -1) return true; break;

            case  6: if(cards_field[ 6].Y != -1 && cards_field[10].Y == -1 && cards_field[11].Y == -1) return true; break;
            case  7: if(cards_field[ 7].Y != -1 && cards_field[11].Y == -1 && cards_field[12].Y == -1) return true; break;
            case  8: if(cards_field[ 8].Y != -1 && cards_field[12].Y == -1 && cards_field[13].Y == -1) return true; break;
            case  9: if(cards_field[ 9].Y != -1 && cards_field[13].Y == -1 && cards_field[14].Y == -1) return true; break;

            case 10: if(cards_field[10].Y != -1 && cards_field[15].Y == -1 && cards_field[16].Y == -1) return true; break;
            case 11: if(cards_field[11].Y != -1 && cards_field[16].Y == -1 && cards_field[17].Y == -1) return true; break;
            case 12: if(cards_field[12].Y != -1 && cards_field[17].Y == -1 && cards_field[18].Y == -1) return true; break;
            case 13: if(cards_field[13].Y != -1 && cards_field[18].Y == -1 && cards_field[19].Y == -1) return true; break;
            case 14: if(cards_field[14].Y != -1 && cards_field[19].Y == -1 && cards_field[20].Y == -1) return true; break;

            case 15: if(cards_field[15].Y != -1 && cards_field[21].Y == -1 && cards_field[22].Y == -1) return true; break;
            case 16: if(cards_field[16].Y != -1 && cards_field[22].Y == -1 && cards_field[23].Y == -1) return true; break;
            case 17: if(cards_field[17].Y != -1 && cards_field[23].Y == -1 && cards_field[24].Y == -1) return true; break;
            case 18: if(cards_field[18].Y != -1 && cards_field[24].Y == -1 && cards_field[25].Y == -1) return true; break;
            case 19: if(cards_field[19].Y != -1 && cards_field[25].Y == -1 && cards_field[26].Y == -1) return true; break;
            case 20: if(cards_field[20].Y != -1 && cards_field[26].Y == -1 && cards_field[27].Y == -1) return true; break;

            case 21: if(cards_field[21].Y != -1) return true; break;
            case 22: if(cards_field[22].Y != -1) return true; break;
            case 23: if(cards_field[23].Y != -1) return true; break;
            case 24: if(cards_field[24].Y != -1) return true; break;
            case 25: if(cards_field[25].Y != -1) return true; break;
            case 26: if(cards_field[26].Y != -1) return true; break;
            case 27: if(cards_field[27].Y != -1) return true; break;
        }
        return false;
    }

    public void CompareCards(int id) {
        if(selector_card == -1) {
            selector_card = id;
            if(cards_field[selector_card].X == 12) {
                CheckForClearedLine(selector_card);
                if(selector_card == 28) {
                    cards_stack.remove(cards_stack.size() - 1);
                    if(cards_stack.size() > 0) {
                        cards_field[28] = cards_stack.get(cards_stack.size() - 1);
                    } else {
                        cards_field[28] = new Vector2(-1, -1);
                    }
                } else {
                    cards_field[selector_card] = new Vector2(-1, -1);
                }
                selector_card = -1;
                score_points += 50;
            }
        } else {
            if(cards_field[selector_card].X + cards_field[id].X == 11 || cards_field[selector_card].X + cards_field[id].X == 24) {
                CheckForClearedLine(selector_card);
                CheckForClearedLine(id);
                if(selector_card == 28) {
                    cards_stack.remove(cards_stack.size() - 1);
                    if(cards_stack.size() > 0) {
                        cards_field[28] = cards_stack.remove(cards_stack.size() - 1);
                    } else {
                        cards_field[28] = new Vector2(-1, -1);
                    }
                } else {
                    cards_field[selector_card] = new Vector2(-1, -1);
                }

                if(id == 28) {
                    cards_stack.remove(cards_stack.size() - 1);
                    if(cards_stack.size() > 0) {
                        cards_field[28] = cards_stack.get(cards_stack.size() - 1);
                    } else {
                        cards_field[28] = new Vector2(-1, -1);
                    }
                } else {
                    cards_field[id] = new Vector2(-1, -1);
                }
                
                
                
                selector_card = -1;
                score_points += 50;
            } else {
                selector_card = id;
                if(cards_field[selector_card].X == 12) {
                    CheckForClearedLine(selector_card);
                    if(selector_card == 28) {
                        cards_stack.remove(cards_stack.size() - 1);
                        if(cards_stack.size() > 0) {
                            cards_field[28] = cards_stack.get(cards_stack.size() - 1);
                        } else {
                            cards_field[28] = new Vector2(-1, -1);
                        }
                    } else {
                        cards_field[selector_card] = new Vector2(-1, -1);
                    }
                    selector_card = -1;
                    score_points += 50;
                }
            }
        }
    }

    private void CheckForClearedLine(int id) {
        if(id == 0) {
            Restart();
        }
        if(id >= 1 && id <= 2) {
            if(cards_field[ 1].Y == -1 && cards_field[ 2].Y == -1) score_points += 1000;
            if(cards_field[ 3].Y == -1 && cards_field[ 4].Y == -1 && cards_field[ 5].Y == -1) score_points += 500;
            if(cards_field[ 6].Y == -1 && cards_field[ 7].Y == -1 && cards_field[ 8].Y == -1 && cards_field[ 9].Y == -1) score_points += 400;
            if(cards_field[10].Y == -1 && cards_field[11].Y == -1 && cards_field[12].Y == -1 && cards_field[13].Y == -1 && cards_field[14].Y == -1) score_points += 300;
            if(cards_field[15].Y == -1 && cards_field[16].Y == -1 && cards_field[17].Y == -1 && cards_field[18].Y == -1 && cards_field[19].Y == -1 && cards_field[20].Y == -1) score_points += 200;
            if(cards_field[21].Y == -1 && cards_field[22].Y == -1 && cards_field[23].Y == -1 && cards_field[24].Y == -1 && cards_field[25].Y == -1 && cards_field[26].Y == -1 && cards_field[27].Y == -1) score_points += 100;
        }
    }
    
}
