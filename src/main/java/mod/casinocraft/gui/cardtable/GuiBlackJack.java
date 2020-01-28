package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GuiBlackJack extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiBlackJack(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_BLACKJACK);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ actionTouch(0); }
        if(tc.turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ actionTouch(1); }
        if(playerToken >= bet || !tc.hasToken())
            if(tc.turnstate == 2 && mouseRect( 24, 204-40, 92, 26, mouseX, mouseY)){ CollectBet(); actionTouch(2); }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

        if(getValue(3) == 0){
            this.fontRenderer.drawString("PLAYER:  "   + getValue(0),  25, 25, 0);
            this.fontRenderer.drawString("PLAYER:  "   + getValue(0),  24, 24, 16777215);
            if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 81, 191, 0);
            if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 80, 190, 16777215);
        } else {
            this.fontRenderer.drawString("PLAYER L:  " + getValue(0),  25, 25, 0);
            this.fontRenderer.drawString("PLAYER L:  " + getValue(0),  24, 24, 16777215);
            this.fontRenderer.drawString("PLAYER R:  " + getValue(1),  25, 41, 0);
            this.fontRenderer.drawString("PLAYER R:  " + getValue(1),  24, 40, 16777215);
            if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 19, 191, 0);
            if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 18, 190, 16777215);
        }
        if(tc.turnstate >= 3) this.fontRenderer.drawString("DEALER:  " + getValue(2), 25, 57, 0);
        if(tc.turnstate >= 3) this.fontRenderer.drawString("DEALER:  " + getValue(2), 24, 56, 16777215);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        if(tc.turnstate >= 2){
            for(int z = 0; z < getCardStack(0).size(); z++){ if(getCardStack(0).get(z).idletimer == 0) drawCard( 24 + 16*z, 100 + 4*z, getCardStack(0).get(z)); if(getValue(3) == 1) drawCardBack( 24 + 16*z, 100 + 4*z, 10); }
            for(int z = 0; z < getCardStack(1).size(); z++){ if(getCardStack(1).get(z).idletimer == 0) drawCard(144 + 16*z, 100 + 4*z, getCardStack(1).get(z)); if(getValue(3) == 2) drawCardBack(144 + 16*z, 100 + 4*z, 10); }
            for(int z = 0; z < getCardStack(2).size(); z++){ if(getCardStack(2).get(z).idletimer == 0) drawCard(144 + 16*z,  24 + 4*z, getCardStack(2).get(z)); }
        }

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(tc.turnstate == 2){
            drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
            if(getFlag(0)){
                if(playerToken == -1) ValidateBet();
                if(playerToken >= bet){
                    drawTexturedModalRect(guiLeft+24+7, guiTop+204-40+2, 78*2, 0, 78, 22); // Button Split
                }
            }
        }

    }



    //--------------------CUSTOM--------------------




    public List<Card> cards_player1 = new ArrayList<Card>();
    public List<Card> cards_player2 = new ArrayList<Card>();
    public List<Card> cards_dealer  = new ArrayList<Card>();
    public int value_player1;
    public int value_player2;
    public int value_dealer;

    int split; // 0 - no split, 1 - left cards, 2 - right cards


    //--------------------CONSTRUCTOR--------------------

   //public TileEntityBlackJack(){
   //    super(null, null);
   //}

   //public TileEntityBlackJack(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_BLACKJACK.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_BLACKJACK.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_BLACKJACK.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        cards_player1.clear();
        cards_player2.clear();
        cards_dealer.clear();
        value_player1 = 0;
        value_player2 = 0;
        value_dealer  = 0;
        split = 0;

        cards_player1.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_player1.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_dealer.add(new  Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_dealer.add(new  Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_dealer.get(1).hidden = true;
        cards_player1.get(0).setShift(-32,   0,  8);
        cards_player1.get(1).setShift(-48,   0, 32);
        cards_dealer.get(0).setShift(  0, -48,  8);
        cards_dealer.get(1).setShift(  0, -48, 32);
        value_player1 = Add_Card(cards_player1, 0, 0, true);
        value_player2 = 0;
        value_dealer = Add_Card(cards_dealer, 0, 0, true);
        if(value_player1 == 21) {
            result();
            cards_dealer.get(1).hidden = false;
        }
    }

    public void actionTouch(int action){
        if(action == 0){ // Hit
            if(split < 2){
                value_player1 = Add_Card(cards_player1, -48, 0, false);
                if(value_player1 > 21) {
                    if(split == 0){
                        result();
                    } else {
                        split = 2;
                    }
                }
            } else {
                value_player2 = Add_Card(cards_player2, -48, 0, false);
                if(value_player2 > 21) {
                    if(value_player1 > 21) {
                        result();
                    } else {
                        tc.turnstate = 3;
                        cards_dealer.get(1).hidden = false;
                    }
                }
            }
        }
        if(action == 1){ // Stand
            if(split == 1){
                split = 2;
            } else {
                tc.turnstate = 3;
                cards_dealer.get(1).hidden = false;
            }
        }
        if(action == 2){ // Split
            split = 1;
            cards_player2.add(cards_player1.get(1));
            cards_player1.remove(1);
            cards_player1.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
            cards_player2.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
            cards_player2.get(0).setShift(-32,   0,  8);
            cards_player2.get(1).setShift(-48,   0, 32);
            cards_player1.get(1).setShift(-48,   0, 32);
            value_player1 = Add_Card(cards_player1, 0, 0, true);
            value_player2 = Add_Card(cards_player2, 0, 0, true);
        }
    }

    public void update(){
        if(cards_player1.size() > 0) for(int i = 0; i < cards_player1.size(); i++){ cards_player1.get(i).update(); }
        if(cards_player2.size() > 0) for(int i = 0; i < cards_player2.size(); i++){ cards_player2.get(i).update(); }
        if(cards_dealer .size() > 0) for(int i = 0; i < cards_dealer .size(); i++){ cards_dealer .get(i).update(); }
        if(tc.turnstate == 3){
            if(cards_dealer.get(cards_dealer.size() - 1).shiftY >= -16){
                if(value_dealer > 16 || (value_dealer > value_player1 && value_dealer > value_player2)){
                    result();
                } else {
                    value_dealer = Add_Card(cards_dealer, 0, -48, false);
                }
            }
        }
    }



    //--------------------GETTER--------------------

    public List<Card> getCardStack(int index){
        if(index == 0) return cards_player1;
        if(index == 1) return cards_player2;
        if(index == 2) return cards_dealer;
        return null;
    }

    public int getValue(int index){
        if(index == 0) return value_player1;
        if(index == 1) return value_player2;
        if(index == 2) return value_dealer;
        return split;
    }

    public boolean getFlag(int index){
        return cards_player1.get(0).number == cards_player1.get(1).number;
    }



    //--------------------CUSTOM--------------------

    private void result(){
        tc.turnstate = 4;
        if(value_dealer  >  21                                                                ) { tc.hand = "The House gone bust!";  tc.reward = 2;
        } else if(value_player1 >  21                                                         ) { tc.hand = "The Player gone bust!"; tc.reward = 0;
        } else if(value_player1 == value_dealer && cards_player1.size() >  cards_dealer.size()) { tc.hand = "The House wins!";       tc.reward = 0;
        } else if(value_player1 == value_dealer && cards_player1.size() == cards_dealer.size()) { tc.hand = "DRAW";                  tc.reward = 1;
        } else if(value_player1 == 21           && cards_player1.size() == 2                  ) { tc.hand = "BLACK JACK";            tc.reward = 3;
        } else if(value_player1 == value_dealer && cards_player1.size() <  cards_dealer.size()) { tc.hand = "The Player wins!";      tc.reward = 2;
        } else if(value_player1 >  value_dealer                                               ) { tc.hand = "The Player wins!";      tc.reward = 2;
        } else                                                                                  { tc.hand = "The House wins!";       tc.reward = 0;
        }
        if(split > 0){
            if(value_dealer  >  21                                                                ) { tc.hand = tc.hand + " / The House gone bust!";  tc.reward += 2;
            } else if(value_player2 >  21                                                         ) { tc.hand = tc.hand + " / The Player gone bust!"; tc.reward += 0;
            } else if(value_player2 == value_dealer && cards_player2.size() >  cards_dealer.size()) { tc.hand = tc.hand + " / The House wins!";       tc.reward += 0;
            } else if(value_player2 == value_dealer && cards_player2.size() == cards_dealer.size()) { tc.hand = tc.hand + " / DRAW";                  tc.reward += 1;
            } else if(value_player2 == 21           && cards_player2.size() == 2                  ) { tc.hand = tc.hand + " / BLACK JACK";            tc.reward += 3;
            } else if(value_player2 == value_dealer && cards_player2.size() <  cards_dealer.size()) { tc.hand = tc.hand + " / The Player wins!";      tc.reward += 2;
            } else if(value_player2 >  value_dealer                                               ) { tc.hand = tc.hand + " / The Player wins!";      tc.reward += 2;
            } else                                                                                  { tc.hand = tc.hand + " / The House wins!";       tc.reward += 0;
            }
        }
    }

    private int Add_Card(List<Card> cards, int shiftX, int shiftY, boolean noCard) {
        int value = 0;
        if(!noCard) cards.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), shiftX, shiftY));
        int ace = 0;
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).number == 0) {
                ace++;
            } else if(cards.get(i).number <= 9) {
                value += cards.get(i).number + 1;
            } else {
                value += 10;
            }
        }
        if(ace > 0) {
            while(ace > 0) {
                if(value <= 10) {
                    value += 11;
                } else {
                    value += 1;
                }
                ace--;
            }
        }
        return value;
    }

}
