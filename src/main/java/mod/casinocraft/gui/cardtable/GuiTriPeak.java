package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Card;
import mod.shared.util.Vector2;
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

public class GuiTriPeak extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiTriPeak(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_TRIPEAK);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 20; x++) {
                    if(mouseRect(-32 + 16*x, 0 + 20*y, 16, 20, mouseX, mouseY)){ actionTouch(x + y * 20); }
                }
            }
            if(mouseRect(0 + 16*5, 192, 32, 48, mouseX, mouseY)){ if(getCardStack(1).size() > 0) actionTouch(-1); }
            if(mouseRect(0 + 16*7, 192, 32, 48, mouseX, mouseY)){                                   actionTouch(-2); }
            if(mouseRect(0 + 16*9, 192, 32, 48, mouseX, mouseY)){ if(getCardStack(2).size() > 0) actionTouch(-3); }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 2){
            this.fontRenderer.drawString("POINTS",             25, 25, 0);
            this.fontRenderer.drawString("POINTS",             24, 24, 16777215);
            this.fontRenderer.drawString("" + getValue(0),  35, 35, 0);
            this.fontRenderer.drawString("" + getValue(0),  34, 34, 16777215);
            this.fontRenderer.drawString("DRAWS",             205, 25, 0);
            this.fontRenderer.drawString("DRAWS",             204, 24, 16777215);
            this.fontRenderer.drawString("" + getValue(1), 215, 35, 0);
            this.fontRenderer.drawString("" + getValue(1), 214, 34, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tc.turnstate >= 2){

            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            drawTexturedModalRect(guiLeft + 16*7 + 3+2, guiTop +2+ 20 * 10, 234, 0, 22, 22); // Button Stack

            drawCard(16 *  3-32, 20 * 2, getCard( 0));
            drawCard(16 *  9-32, 20 * 2, getCard( 1));
            drawCard(16 * 15-32, 20 * 2, getCard( 2));

            drawCard(16 *  2-32, 20 * 3, getCard( 3));
            drawCard(16 *  4-32, 20 * 3, getCard( 4));
            drawCard(16 *  8-32, 20 * 3, getCard( 5));
            drawCard(16 * 10-32, 20 * 3, getCard( 6));
            drawCard(16 * 14-32, 20 * 3, getCard( 7));
            drawCard(16 * 16-32, 20 * 3, getCard( 8));

            drawCard(16 *  1-32, 20 * 4, getCard( 9));
            drawCard(16 *  3-32, 20 * 4, getCard(10));
            drawCard(16 *  5-32, 20 * 4, getCard(11));
            drawCard(16 *  7-32, 20 * 4, getCard(12));
            drawCard(16 *  9-32, 20 * 4, getCard(13));
            drawCard(16 * 11-32, 20 * 4, getCard(14));
            drawCard(16 * 13-32, 20 * 4, getCard(15));
            drawCard(16 * 15-32, 20 * 4, getCard(16));
            drawCard(16 * 17-32, 20 * 4, getCard(17));

            drawCard(16 *  0-32, 20 * 5, getCard(18));
            drawCard(16 *  2-32, 20 * 5, getCard(19));
            drawCard(16 *  4-32, 20 * 5, getCard(20));
            drawCard(16 *  6-32, 20 * 5, getCard(21));
            drawCard(16 *  8-32, 20 * 5, getCard(22));
            drawCard(16 * 10-32, 20 * 5, getCard(23));
            drawCard(16 * 12-32, 20 * 5, getCard(24));
            drawCard(16 * 14-32, 20 * 5, getCard(25));
            drawCard(16 * 16-32, 20 * 5, getCard(26));
            drawCard(16 * 18-32, 20 * 5, getCard(27));

            drawCardBack(16 * 5, 20 * 10 - 10, 7);
            drawCardBack(16 * 9, 20 * 10 - 10, tc.scoreLives == 0 ? 8 : 10);

            if(getCardStack(2).size() > 1){ drawCard(16 * 9, 20 * 10 - 10, getCardStack(2).get(1)); }
            if(getCardStack(2).size() > 0){ drawCard(16 * 9, 20 * 10 - 10, getCardStack(2).get(0)); }
            if(getCardStack(1).size() > 1){ drawCard(16 * 5, 20 * 10 - 10, getCardStack(1).get(getCardStack(1).size() - 2)); }
            if(getCardStack(1).size() > 0){ drawCard(16 * 5, 20 * 10 - 10, getCardStack(1).get(getCardStack(1).size() - 1)); }
        }
    }



    //--------------------CUSTOM--------------------



    public int combo;

    public Card[] cards_field = new Card[28];
    public List<Card> cards_stack = new ArrayList<Card>();
    public List<Card> cards_reserve = new ArrayList<Card>();



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityTriPeak(){
   //    super(null, null);
   //}

   //public TileEntityTriPeak(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_TRIPEAK.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_TRIPEAK.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_TRIPEAK.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        tc.scoreLives = 5 - tc.difficulty;

        combo = 0;

        List<Card> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        tc.selector = new Vector2(9, 12);
        for(int i = 0; i < 28; i++){
            deck.remove(0);
        }
        cards_reserve.addAll(deck);
        cards_stack.clear();
        DrawReserve();

        cards_field[ 3].setShift(+16*1, -20*1, 20);
        cards_field[ 4].setShift(-16*1, -20*1, 20);
        cards_field[ 9].setShift(+16*2, -20*2, 10);
        cards_field[10].setShift(    0, -20*2, 10);
        cards_field[11].setShift(-16*2, -20*2, 10);
        cards_field[18].setShift(+16*3, -20*3,  0);
        cards_field[19].setShift(+16*1, -20*3,  0);
        cards_field[20].setShift(-16*1, -20*3,  0);

        cards_field[ 5].setShift(+16*1, -20*1, 20);
        cards_field[ 6].setShift(-16*1, -20*1, 20);
        cards_field[12].setShift(+16*2, -20*2, 10);
        cards_field[13].setShift(    0, -20*2, 10);
        cards_field[14].setShift(-16*2, -20*2, 10);
        cards_field[21].setShift(+16*3, -20*3,  0);
        cards_field[22].setShift(+16*1, -20*3,  0);
        cards_field[23].setShift(-16*1, -20*3,  0);
        cards_field[24].setShift(-16*3, -20*3,  0);

        cards_field[ 7].setShift(+16*1, -20*1, 20);
        cards_field[ 8].setShift(-16*1, -20*1, 20);
        cards_field[15].setShift(+16*2, -20*2, 10);
        cards_field[16].setShift(    0, -20*2, 10);
        cards_field[17].setShift(-16*2, -20*2, 10);
        cards_field[25].setShift(+16*1, -20*3,  0);
        cards_field[26].setShift(-16*1, -20*3,  0);
        cards_field[27].setShift(-16*3, -20*3,  0);
    }

    public void Restart() {

        List<Card> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        tc.selector = new Vector2(9, 12);
        for(int i = 0; i < 28; i++){
            deck.remove(0);
        }
        cards_reserve.addAll(deck);
        cards_stack.clear();
        DrawReserve();

        cards_field[ 3].setShift(+16*1, -20*1, 20);
        cards_field[ 4].setShift(-16*1, -20*1, 20);
        cards_field[ 9].setShift(+16*2, -20*2, 10);
        cards_field[10].setShift(    0, -20*2, 10);
        cards_field[11].setShift(-16*2, -20*2, 10);
        cards_field[18].setShift(+16*3, -20*3,  0);
        cards_field[19].setShift(+16*1, -20*3,  0);
        cards_field[20].setShift(-16*1, -20*3,  0);

        cards_field[ 5].setShift(+16*1, -20*1, 20);
        cards_field[ 6].setShift(-16*1, -20*1, 20);
        cards_field[12].setShift(+16*2, -20*2, 10);
        cards_field[13].setShift(    0, -20*2, 10);
        cards_field[14].setShift(-16*2, -20*2, 10);
        cards_field[21].setShift(+16*3, -20*3,  0);
        cards_field[22].setShift(+16*1, -20*3,  0);
        cards_field[23].setShift(-16*1, -20*3,  0);
        cards_field[24].setShift(-16*3, -20*3,  0);

        cards_field[ 7].setShift(+16*1, -20*1, 20);
        cards_field[ 8].setShift(-16*1, -20*1, 20);
        cards_field[15].setShift(+16*2, -20*2, 10);
        cards_field[16].setShift(    0, -20*2, 10);
        cards_field[17].setShift(-16*2, -20*2, 10);
        cards_field[25].setShift(+16*1, -20*3,  0);
        cards_field[26].setShift(-16*1, -20*3,  0);
        cards_field[27].setShift(-16*3, -20*3,  0);
    }

    public void actionTouch(int action){
        //	 if(action == -1) CompareCards(28);
        if(action == -2) DrawReserve();
            //else if(action == -3) CompareCards(29);
        else TouchField(action % 20, action / 20);
    }

    public void update(){
        if(tc.turnstate >= 2){
            if(cards_stack.size() > 0) for(int i = 0; i < cards_stack.size(); i++){
                cards_stack.get(i).update();
            }
            if(cards_reserve.size() > 0) for(int i = 0; i < cards_reserve.size(); i++){
                cards_reserve.get(i).update();
            }
            for(int i = 0; i < 28; i++){
                cards_field[i].update();
            }
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        if(index == 0) return tc.scorePoint;
        if(index == 1) return tc.scoreLives;
        return 0;
    }

    public List<Card> getCardStack(int index){
        //if(index == 0) return cards_field;
        if(index == 1) return cards_stack;
        if(index == 2) return cards_reserve;
        return cards_stack;
    }

    public Card getCard(int index){
        return cards_field[index];
    }



    //--------------------CUSTOM--------------------

    private List<Card> ShuffleDeck() {
        List<Card> stack = new ArrayList<Card>();
        List<Card> deck = new ArrayList<Card>();

        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 13; x++) {
                stack.add(new Card(x, y));
            }
        }
        while(stack.size() > 0) {
            int r = stack.size() == 1 ? 0 : tc.rand.nextInt(stack.size() - 1);
            deck.add(stack.get(r));
            stack.remove(r);
        }

        return deck;
    }

    public void DrawReserve() {
        combo = 0;
        if(cards_reserve.size() > 0) {
            cards_reserve.get(0).shiftX = 64;
            cards_stack.add(cards_reserve.get(0));
            cards_reserve.remove(0);
        } else {
            if(tc.scoreLives == 0){
                tc.turnstate = 4;
            } else {
                tc.scoreLives--;
                Restart();
            }
        }
    }

    public void TouchField(int x, int y) {
        if(x ==  3 && y == 2) if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  4 && y == 2) if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  9 && y == 2) if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 10 && y == 2) if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 15 && y == 2) if(IsCardSelectable( 2)) { CompareCards( 2); }
        if(x == 16 && y == 2) if(IsCardSelectable( 2)) { CompareCards( 2); }

        if(x ==  2 && y == 3) if(IsCardSelectable( 3)) { CompareCards( 3); }
        if(x ==  3 && y == 3) if(IsCardSelectable( 3)) { CompareCards( 3); } else if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  4 && y == 3) if(IsCardSelectable( 4)) { CompareCards( 4); } else if(IsCardSelectable( 0)) { CompareCards( 0); }
        if(x ==  5 && y == 3) if(IsCardSelectable( 4)) { CompareCards( 4); }
        if(x ==  8 && y == 3) if(IsCardSelectable( 5)) { CompareCards( 5); }
        if(x ==  9 && y == 3) if(IsCardSelectable( 5)) { CompareCards( 5); } else if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 10 && y == 3) if(IsCardSelectable( 6)) { CompareCards( 6); } else if(IsCardSelectable( 1)) { CompareCards( 1); }
        if(x == 11 && y == 3) if(IsCardSelectable( 6)) { CompareCards( 6); }
        if(x == 14 && y == 3) if(IsCardSelectable( 7)) { CompareCards( 7); }
        if(x == 15 && y == 3) if(IsCardSelectable( 7)) { CompareCards( 7); } else if(IsCardSelectable( 2)) { CompareCards( 2); }
        if(x == 16 && y == 3) if(IsCardSelectable( 8)) { CompareCards( 8); } else if(IsCardSelectable( 2)) { CompareCards( 2); }
        if(x == 17 && y == 3) if(IsCardSelectable( 8)) { CompareCards( 8); }

        if(x ==  1 && y == 4) if(IsCardSelectable( 9)) { CompareCards( 9); }
        if(x ==  2 && y == 4) if(IsCardSelectable( 9)) { CompareCards( 9); } else if(IsCardSelectable( 3)) { CompareCards( 3); }
        if(x ==  3 && y == 4) if(IsCardSelectable(10)) { CompareCards(10); } else if(IsCardSelectable( 3)) { CompareCards( 3); }
        if(x ==  4 && y == 4) if(IsCardSelectable(10)) { CompareCards(10); } else if(IsCardSelectable( 4)) { CompareCards( 4); }
        if(x ==  5 && y == 4) if(IsCardSelectable(11)) { CompareCards(11); } else if(IsCardSelectable( 4)) { CompareCards( 4); }
        if(x ==  6 && y == 4) if(IsCardSelectable(11)) { CompareCards(11); }
        if(x ==  7 && y == 4) if(IsCardSelectable(12)) { CompareCards(12); }
        if(x ==  8 && y == 4) if(IsCardSelectable(12)) { CompareCards(12); } else if(IsCardSelectable( 5)) { CompareCards( 5); }
        if(x ==  9 && y == 4) if(IsCardSelectable(13)) { CompareCards(13); } else if(IsCardSelectable( 5)) { CompareCards( 5); }
        if(x == 10 && y == 4) if(IsCardSelectable(13)) { CompareCards(13); } else if(IsCardSelectable( 6)) { CompareCards( 6); }
        if(x == 11 && y == 4) if(IsCardSelectable(14)) { CompareCards(14); } else if(IsCardSelectable( 6)) { CompareCards( 6); }
        if(x == 12 && y == 4) if(IsCardSelectable(14)) { CompareCards(14); }
        if(x == 13 && y == 4) if(IsCardSelectable(15)) { CompareCards(15); }
        if(x == 14 && y == 4) if(IsCardSelectable(15)) { CompareCards(15); } else if(IsCardSelectable( 7)) { CompareCards( 7); }
        if(x == 15 && y == 4) if(IsCardSelectable(16)) { CompareCards(16); } else if(IsCardSelectable( 7)) { CompareCards( 7); }
        if(x == 16 && y == 4) if(IsCardSelectable(16)) { CompareCards(16); } else if(IsCardSelectable( 8)) { CompareCards( 8); }
        if(x == 17 && y == 4) if(IsCardSelectable(17)) { CompareCards(17); } else if(IsCardSelectable( 8)) { CompareCards( 8); }
        if(x == 18 && y == 4) if(IsCardSelectable(17)) { CompareCards(17); }

        if(x ==  0 && y == 5) if(IsCardSelectable(18)) { CompareCards(18); }
        if(x ==  1 && y == 5) if(IsCardSelectable(18)) { CompareCards(18); } else if(IsCardSelectable( 9)) { CompareCards( 9); }
        if(x ==  2 && y == 5) if(IsCardSelectable(19)) { CompareCards(19); } else if(IsCardSelectable( 9)) { CompareCards( 9); }
        if(x ==  3 && y == 5) if(IsCardSelectable(19)) { CompareCards(19); } else if(IsCardSelectable(10)) { CompareCards(10); }
        if(x ==  4 && y == 5) if(IsCardSelectable(20)) { CompareCards(20); } else if(IsCardSelectable(10)) { CompareCards(10); }
        if(x ==  5 && y == 5) if(IsCardSelectable(20)) { CompareCards(20); } else if(IsCardSelectable(11)) { CompareCards(11); }
        if(x ==  6 && y == 5) if(IsCardSelectable(21)) { CompareCards(21); } else if(IsCardSelectable(11)) { CompareCards(11); }
        if(x ==  7 && y == 5) if(IsCardSelectable(21)) { CompareCards(21); } else if(IsCardSelectable(12)) { CompareCards(12); }
        if(x ==  8 && y == 5) if(IsCardSelectable(22)) { CompareCards(22); } else if(IsCardSelectable(12)) { CompareCards(12); }
        if(x ==  9 && y == 5) if(IsCardSelectable(22)) { CompareCards(22); } else if(IsCardSelectable(13)) { CompareCards(13); }
        if(x == 10 && y == 5) if(IsCardSelectable(23)) { CompareCards(23); } else if(IsCardSelectable(13)) { CompareCards(13); }
        if(x == 11 && y == 5) if(IsCardSelectable(23)) { CompareCards(23); } else if(IsCardSelectable(14)) { CompareCards(14); }
        if(x == 12 && y == 5) if(IsCardSelectable(24)) { CompareCards(24); } else if(IsCardSelectable(14)) { CompareCards(14); }
        if(x == 13 && y == 5) if(IsCardSelectable(24)) { CompareCards(24); } else if(IsCardSelectable(15)) { CompareCards(15); }
        if(x == 14 && y == 5) if(IsCardSelectable(25)) { CompareCards(25); } else if(IsCardSelectable(15)) { CompareCards(15); }
        if(x == 15 && y == 5) if(IsCardSelectable(25)) { CompareCards(25); } else if(IsCardSelectable(16)) { CompareCards(16); }
        if(x == 16 && y == 5) if(IsCardSelectable(26)) { CompareCards(26); } else if(IsCardSelectable(16)) { CompareCards(16); }
        if(x == 17 && y == 5) if(IsCardSelectable(26)) { CompareCards(26); } else if(IsCardSelectable(17)) { CompareCards(17); }
        if(x == 18 && y == 5) if(IsCardSelectable(27)) { CompareCards(27); } else if(IsCardSelectable(17)) { CompareCards(17); }
        if(x == 19 && y == 5) if(IsCardSelectable(27)) { CompareCards(27); }

        if(x ==  0 && y == 6) if(IsCardSelectable(18)) { CompareCards(18); }
        if(x ==  1 && y == 6) if(IsCardSelectable(18)) { CompareCards(18); }
        if(x ==  2 && y == 6) if(IsCardSelectable(19)) { CompareCards(19); }
        if(x ==  3 && y == 6) if(IsCardSelectable(19)) { CompareCards(19); }
        if(x ==  4 && y == 6) if(IsCardSelectable(20)) { CompareCards(20); }
        if(x ==  5 && y == 6) if(IsCardSelectable(20)) { CompareCards(20); }
        if(x ==  6 && y == 6) if(IsCardSelectable(21)) { CompareCards(21); }
        if(x ==  7 && y == 6) if(IsCardSelectable(21)) { CompareCards(21); }
        if(x ==  8 && y == 6) if(IsCardSelectable(22)) { CompareCards(22); }
        if(x ==  9 && y == 6) if(IsCardSelectable(22)) { CompareCards(22); }
        if(x == 10 && y == 6) if(IsCardSelectable(23)) { CompareCards(23); }
        if(x == 11 && y == 6) if(IsCardSelectable(23)) { CompareCards(23); }
        if(x == 12 && y == 6) if(IsCardSelectable(24)) { CompareCards(24); }
        if(x == 13 && y == 6) if(IsCardSelectable(24)) { CompareCards(24); }
        if(x == 14 && y == 6) if(IsCardSelectable(25)) { CompareCards(25); }
        if(x == 15 && y == 6) if(IsCardSelectable(25)) { CompareCards(25); }
        if(x == 16 && y == 6) if(IsCardSelectable(26)) { CompareCards(26); }
        if(x == 17 && y == 6) if(IsCardSelectable(26)) { CompareCards(26); }
        if(x == 18 && y == 6) if(IsCardSelectable(27)) { CompareCards(27); }
        if(x == 19 && y == 6) if(IsCardSelectable(27)) { CompareCards(27); }
    }

    private boolean IsCardSelectable(int id) {
        switch(id) {
            case  0: if(cards_field[ 0].suit != -1 && cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1) return true; break;
            case  1: if(cards_field[ 1].suit != -1 && cards_field[ 5].suit == -1 && cards_field[ 6].suit == -1) return true; break;
            case  2: if(cards_field[ 2].suit != -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1) return true; break;

            case  3: if(cards_field[ 3].suit != -1 && cards_field[ 9].suit == -1 && cards_field[10].suit == -1) return true; break;
            case  4: if(cards_field[ 4].suit != -1 && cards_field[10].suit == -1 && cards_field[11].suit == -1) return true; break;
            case  5: if(cards_field[ 5].suit != -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1) return true; break;
            case  6: if(cards_field[ 6].suit != -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) return true; break;
            case  7: if(cards_field[ 7].suit != -1 && cards_field[15].suit == -1 && cards_field[16].suit == -1) return true; break;
            case  8: if(cards_field[ 8].suit != -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1) return true; break;

            case  9: if(cards_field[ 9].suit != -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1) return true; break;
            case 10: if(cards_field[10].suit != -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) return true; break;
            case 11: if(cards_field[11].suit != -1 && cards_field[20].suit == -1 && cards_field[21].suit == -1) return true; break;
            case 12: if(cards_field[12].suit != -1 && cards_field[21].suit == -1 && cards_field[22].suit == -1) return true; break;
            case 13: if(cards_field[13].suit != -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1) return true; break;
            case 14: if(cards_field[14].suit != -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1) return true; break;
            case 15: if(cards_field[15].suit != -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1) return true; break;
            case 16: if(cards_field[16].suit != -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1) return true; break;
            case 17: if(cards_field[17].suit != -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) return true; break;

            case 18: if(cards_field[18].suit != -1) return true; break;
            case 19: if(cards_field[19].suit != -1) return true; break;
            case 20: if(cards_field[20].suit != -1) return true; break;
            case 21: if(cards_field[21].suit != -1) return true; break;
            case 22: if(cards_field[22].suit != -1) return true; break;
            case 23: if(cards_field[23].suit != -1) return true; break;
            case 24: if(cards_field[24].suit != -1) return true; break;
            case 25: if(cards_field[25].suit != -1) return true; break;
            case 26: if(cards_field[26].suit != -1) return true; break;
            case 27: if(cards_field[27].suit != -1) return true; break;
        }
        return false;
    }

    public void CompareCards(int id) {

        if(cards_field[id].number + 1 == cards_stack.get(cards_stack.size() - 1).number || cards_field[id].number - 1 == cards_stack.get(cards_stack.size() - 1).number || (cards_field[id].number == 0 && cards_stack.get(cards_stack.size() - 1).number == 12) || (cards_field[id].number == 12 && cards_stack.get(cards_stack.size() - 1).number == 0)) {
            combo++;
            tc.scorePoint += 50 * combo;
            cards_field[id].shiftX = 0;
            cards_field[id].shiftY = +24;
            cards_stack.add(cards_field[id]);
            cards_field[id] = new Card(-1, -1);
            if(cards_field[0].suit == -1 && cards_field[1].suit == -1 && cards_field[2].suit == -1) Restart();
        }
    }

}
