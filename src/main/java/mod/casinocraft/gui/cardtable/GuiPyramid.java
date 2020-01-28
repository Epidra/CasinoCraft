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

public class GuiPyramid extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiPyramid(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_PYRAMID);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 15; x++) {
                    if(mouseRect(0 + 16*x, 0 + 20*y, 16, 20, mouseX, mouseY)){ actionTouch(x + y * 20); }
                }
            }
            if(mouseRect(0 + 16*5, 192, 32, 48, mouseX, mouseY)){ if(getCardStack(1).size() > 0) actionTouch(-1); }
            if(mouseRect(0 + 16*7, 192, 32, 48, mouseX, mouseY)){                                      actionTouch(-2); }
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

            drawCard(16 *  7, 20 * 1, getCard( 0)); if(tc.selector.X ==  0) drawCardBack(16 *  7, 20 * 1, 9);

            drawCard(16 *  6, 20 * 2, getCard( 1)); if(tc.selector.X ==  1) drawCardBack(16 *  6, 20 * 2, 9);
            drawCard(16 *  8, 20 * 2, getCard( 2)); if(tc.selector.X ==  2) drawCardBack(16 *  8, 20 * 2, 9);

            drawCard(16 *  5, 20 * 3, getCard( 3)); if(tc.selector.X ==  3) drawCardBack(16 *  5, 20 * 3, 9);
            drawCard(16 *  7, 20 * 3, getCard( 4)); if(tc.selector.X ==  4) drawCardBack(16 *  7, 20 * 3, 9);
            drawCard(16 *  9, 20 * 3, getCard( 5)); if(tc.selector.X ==  5) drawCardBack(16 *  9, 20 * 3, 9);

            drawCard(16 *  4, 20 * 4, getCard( 6)); if(tc.selector.X ==  6) drawCardBack(16 *  4, 20 * 4, 9);
            drawCard(16 *  6, 20 * 4, getCard( 7)); if(tc.selector.X ==  7) drawCardBack(16 *  6, 20 * 4, 9);
            drawCard(16 *  8, 20 * 4, getCard( 8)); if(tc.selector.X ==  8) drawCardBack(16 *  8, 20 * 4, 9);
            drawCard(16 * 10, 20 * 4, getCard( 9)); if(tc.selector.X ==  9) drawCardBack(16 * 10, 20 * 4, 9);

            drawCard(16 *  3, 20 * 5, getCard(10)); if(tc.selector.X == 10) drawCardBack(16 *  3, 20 * 5, 9);
            drawCard(16 *  5, 20 * 5, getCard(11)); if(tc.selector.X == 11) drawCardBack(16 *  5, 20 * 5, 9);
            drawCard(16 *  7, 20 * 5, getCard(12)); if(tc.selector.X == 12) drawCardBack(16 *  7, 20 * 5, 9);
            drawCard(16 *  9, 20 * 5, getCard(13)); if(tc.selector.X == 13) drawCardBack(16 *  9, 20 * 5, 9);
            drawCard(16 * 11, 20 * 5, getCard(14)); if(tc.selector.X == 14) drawCardBack(16 * 11, 20 * 5, 9);

            drawCard(16 *  2, 20 * 6, getCard(15)); if(tc.selector.X == 15) drawCardBack(16 *  2, 20 * 6, 9);
            drawCard(16 *  4, 20 * 6, getCard(16)); if(tc.selector.X == 16) drawCardBack(16 *  4, 20 * 6, 9);
            drawCard(16 *  6, 20 * 6, getCard(17)); if(tc.selector.X == 17) drawCardBack(16 *  6, 20 * 6, 9);
            drawCard(16 *  8, 20 * 6, getCard(18)); if(tc.selector.X == 18) drawCardBack(16 *  8, 20 * 6, 9);
            drawCard(16 * 10, 20 * 6, getCard(19)); if(tc.selector.X == 19) drawCardBack(16 * 10, 20 * 6, 9);
            drawCard(16 * 12, 20 * 6, getCard(20)); if(tc.selector.X == 20) drawCardBack(16 * 12, 20 * 6, 9);

            drawCard(16 *  1, 20 * 7, getCard(21)); if(tc.selector.X == 21) drawCardBack(16 *  1, 20 * 7, 9);
            drawCard(16 *  3, 20 * 7, getCard(22)); if(tc.selector.X == 22) drawCardBack(16 *  3, 20 * 7, 9);
            drawCard(16 *  5, 20 * 7, getCard(23)); if(tc.selector.X == 23) drawCardBack(16 *  5, 20 * 7, 9);
            drawCard(16 *  7, 20 * 7, getCard(24)); if(tc.selector.X == 24) drawCardBack(16 *  7, 20 * 7, 9);
            drawCard(16 *  9, 20 * 7, getCard(25)); if(tc.selector.X == 25) drawCardBack(16 *  9, 20 * 7, 9);
            drawCard(16 * 11, 20 * 7, getCard(26)); if(tc.selector.X == 26) drawCardBack(16 * 11, 20 * 7, 9);
            drawCard(16 * 13, 20 * 7, getCard(27)); if(tc.selector.X == 27) drawCardBack(16 * 13, 20 * 7, 9);

            drawCardBack(16 * 5, 20 * 10 - 10, 7);
            drawCardBack(16 * 9, 20 * 10 - 10, tc.scoreLives == 0 ? 8 : 10);

            if(getCardStack(2).size() > 1){ drawCard(16 * 9, 20 * 10 - 10, getCardStack(2).get(1)); }
            if(getCardStack(2).size() > 0){ drawCard(16 * 9, 20 * 10 - 10, getCardStack(2).get(0)); }
            if(getCardStack(1).size() > 1){ drawCard(16 * 5, 20 * 10 - 10, getCardStack(1).get(getCardStack(1).size() - 2)); }
            if(getCardStack(1).size() > 0){ drawCard(16 * 5, 20 * 10 - 10, getCardStack(1).get(getCardStack(1).size() - 1)); }

            if(getValue(2) == 28) drawCardBack(16 * 5, 20 * 10 - 10, 9);
            if(getValue(2) == 29) drawCardBack(16 * 9, 20 * 10 - 10, 9);
        }
    }



    //--------------------CUSTOM--------------------



    //public int selector_card = -1;
    //public Vector2 selector_pos;

    public Card[] cards_field = new Card[28];
    public List<Card> cards_stack = new ArrayList<Card>();
    public List<Card> cards_reserve = new ArrayList<Card>();

    public int timer = 0;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityPyramid(){
   //    super(null, null);
   //}

   //public TileEntityPyramid(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_PYRAMID.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_PYRAMID.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_PYRAMID.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        tc.scoreLives = 5-tc.difficulty;
        timer = 0;
        tc.selector.set(-1, -1);

        List<Card> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        for(int i = 0; i < 28; i++) deck.remove(0);
        cards_reserve.addAll(deck);
        cards_stack.clear();

        cards_field[ 1].setShift(+16*1, -20*1, 50);
        cards_field[ 2].setShift(-16*1, -20*1, 50);

        cards_field[ 3].setShift(+16*2, -20*2, 40);
        cards_field[ 4].setShift(    0, -20*2, 40);
        cards_field[ 5].setShift(-16*2, -20*2, 40);

        cards_field[ 6].setShift(+16*3, -20*3, 30);
        cards_field[ 7].setShift(+16*1, -20*3, 30);
        cards_field[ 8].setShift(-16*1, -20*3, 30);
        cards_field[ 9].setShift(-16*3, -20*3, 30);

        cards_field[10].setShift(+16*4, -20*4, 20);
        cards_field[11].setShift(+16*2, -20*4, 20);
        cards_field[12].setShift(    0, -20*4, 20);
        cards_field[13].setShift(-16*2, -20*4, 20);
        cards_field[14].setShift(-16*4, -20*4, 20);

        cards_field[15].setShift(+16*5, -20*5, 10);
        cards_field[16].setShift(+16*3, -20*5, 10);
        cards_field[17].setShift(+16*1, -20*5, 10);
        cards_field[18].setShift(-16*1, -20*5, 10);
        cards_field[19].setShift(-16*3, -20*5, 10);
        cards_field[20].setShift(-16*5, -20*5, 10);

        cards_field[21].setShift(+16*6, -20*6, 0);
        cards_field[22].setShift(+16*4, -20*6, 0);
        cards_field[23].setShift(+16*2, -20*6, 0);
        cards_field[24].setShift(    0, -20*6, 0);
        cards_field[25].setShift(-16*2, -20*6, 0);
        cards_field[26].setShift(-16*4, -20*6, 0);
        cards_field[27].setShift(-16*6, -20*6, 0);
    }

    public void Restart() {
        tc.selector.set(-1, -1);
        List<Card> deck = ShuffleDeck();

        for(int i = 0; i < 28; i++) {
            cards_field[i] = deck.get(i);
        }
        for(int i = 0; i < 28; i++) deck.remove(0);
        cards_reserve.addAll(deck);
        cards_stack.clear();

        cards_field[ 1].setShift(+16*1, -20*1, 50);
        cards_field[ 2].setShift(-16*1, -20*1, 50);

        cards_field[ 3].setShift(+16*2, -20*2, 40);
        cards_field[ 4].setShift(    0, -20*2, 40);
        cards_field[ 5].setShift(-16*2, -20*2, 40);

        cards_field[ 6].setShift(+16*3, -20*3, 30);
        cards_field[ 7].setShift(+16*1, -20*3, 30);
        cards_field[ 8].setShift(-16*1, -20*3, 30);
        cards_field[ 9].setShift(-16*3, -20*3, 30);

        cards_field[10].setShift(+16*4, -20*4, 20);
        cards_field[11].setShift(+16*2, -20*4, 20);
        cards_field[12].setShift(    0, -20*4, 20);
        cards_field[13].setShift(-16*2, -20*4, 20);
        cards_field[14].setShift(-16*4, -20*4, 20);

        cards_field[15].setShift(+16*5, -20*5, 10);
        cards_field[16].setShift(+16*3, -20*5, 10);
        cards_field[17].setShift(+16*1, -20*5, 10);
        cards_field[18].setShift(-16*1, -20*5, 10);
        cards_field[19].setShift(-16*3, -20*5, 10);
        cards_field[20].setShift(-16*5, -20*5, 10);

        cards_field[21].setShift(+16*6, -20*6, 0);
        cards_field[22].setShift(+16*4, -20*6, 0);
        cards_field[23].setShift(+16*2, -20*6, 0);
        cards_field[24].setShift(    0, -20*6, 0);
        cards_field[25].setShift(-16*2, -20*6, 0);
        cards_field[26].setShift(-16*4, -20*6, 0);
        cards_field[27].setShift(-16*6, -20*6, 0);
    }

    public void actionTouch(int action){
        if(action == -1) CompareCards(28);
        else if(action == -2) DrawReserve();
        else if(action == -3) CompareCards(29);
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
            if(timer > 0){
                timer--;
                if(timer == 0){
                    if(cards_stack  .size() > 0) if(cards_stack  .get(cards_stack.size() - 1).dead) cards_stack  .remove(cards_stack.get(cards_stack.size() - 1));
                    if(cards_reserve.size() > 0) if(cards_reserve.get(0                     ).dead) cards_reserve.remove(0);
                    for(int i = 0; i < 28; i++){
                        if(cards_field[i].dead){
                            cards_field[i] = new Card(-1, -1);
                            CheckForClearedLine(i);
                        }
                    }
                }
            }
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        if(index == 0) return tc.scorePoint;
        if(index == 1) return tc.scoreLives;
        return tc.selector.X;
    }

    public List<Card> getCardStack(int index){
        //if(index == 0) return cards_field.;
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
        List<Card> deck  = new ArrayList<Card>();

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
        if(cards_reserve.size() > 0) {
            cards_reserve.get(0).shiftX = 64;
            cards_stack.add(cards_reserve.get(0));
            cards_reserve.remove(0);
        } else {
            cards_reserve.addAll(cards_stack);
            cards_stack.clear();
            if(tc.scoreLives == 0){
                tc.turnstate = 4;
            } else {
                tc.scoreLives--;
            }
        }
    }

    public void TouchField(int x, int y) {
        if(x ==  7 && y == 1) if(IsCardSelectable( 0)){ CompareCards(0); }
        if(x ==  8 && y == 1) if(IsCardSelectable( 0)){ CompareCards(0); }

        if(x ==  6 && y == 2) if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x ==  7 && y == 2) if(IsCardSelectable( 1)){ CompareCards(1); } else if(IsCardSelectable( 0)){CompareCards(0); }
        if(x ==  8 && y == 2) if(IsCardSelectable( 2)){ CompareCards(2); } else if(IsCardSelectable( 0)){CompareCards(0); }
        if(x ==  9 && y == 2) if(IsCardSelectable( 2)){ CompareCards(2); }

        if(x ==  5 && y == 3) if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  6 && y == 3) if(IsCardSelectable( 3)){ CompareCards(3); } else if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x ==  7 && y == 3) if(IsCardSelectable( 4)){ CompareCards(4); } else if(IsCardSelectable( 1)){ CompareCards(1); }
        if(x ==  8 && y == 3) if(IsCardSelectable( 4)){ CompareCards(4); } else if(IsCardSelectable( 2)){ CompareCards(2); }
        if(x ==  9 && y == 3) if(IsCardSelectable( 5)){ CompareCards(5); } else if(IsCardSelectable( 2)){ CompareCards(2); }
        if(x == 10 && y == 3) if(IsCardSelectable( 5)){ CompareCards(5); }

        if(x ==  4 && y == 4) if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  5 && y == 4) if(IsCardSelectable( 6)){ CompareCards(6); } else if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  6 && y == 4) if(IsCardSelectable( 7)){ CompareCards(7); } else if(IsCardSelectable( 3)){ CompareCards(3); }
        if(x ==  7 && y == 4) if(IsCardSelectable( 7)){ CompareCards(7); } else if(IsCardSelectable( 4)){ CompareCards(4); }
        if(x ==  8 && y == 4) if(IsCardSelectable( 8)){ CompareCards(8); } else if(IsCardSelectable( 4)){ CompareCards(4); }
        if(x ==  9 && y == 4) if(IsCardSelectable( 8)){ CompareCards(8); } else if(IsCardSelectable( 5)){ CompareCards(5); }
        if(x == 10 && y == 4) if(IsCardSelectable( 9)){ CompareCards(9); } else if(IsCardSelectable( 5)){ CompareCards(5); }
        if(x == 11 && y == 4) if(IsCardSelectable( 9)){ CompareCards(9); }

        if(x ==  3 && y == 5) if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  4 && y == 5) if(IsCardSelectable(10)){ CompareCards(10); } else if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  5 && y == 5) if(IsCardSelectable(11)){ CompareCards(11); } else if(IsCardSelectable( 6)){ CompareCards(6); }
        if(x ==  6 && y == 5) if(IsCardSelectable(11)){ CompareCards(11); } else if(IsCardSelectable( 7)){ CompareCards(7); }
        if(x ==  7 && y == 5) if(IsCardSelectable(12)){ CompareCards(12); } else if(IsCardSelectable( 7)){ CompareCards(7); }
        if(x ==  8 && y == 5) if(IsCardSelectable(12)){ CompareCards(12); } else if(IsCardSelectable( 8)){ CompareCards(8); }
        if(x ==  9 && y == 5) if(IsCardSelectable(13)){ CompareCards(13); } else if(IsCardSelectable( 8)){ CompareCards(8); }
        if(x == 10 && y == 5) if(IsCardSelectable(13)){ CompareCards(13); } else if(IsCardSelectable( 9)){ CompareCards(9); }
        if(x == 11 && y == 5) if(IsCardSelectable(14)){ CompareCards(14); } else if(IsCardSelectable( 9)){ CompareCards(9); }
        if(x == 12 && y == 5) if(IsCardSelectable(14)){ CompareCards(14); }

        if(x ==  2 && y == 6) if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  3 && y == 6) if(IsCardSelectable(15)){ CompareCards(15); } else if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  4 && y == 6) if(IsCardSelectable(16)){ CompareCards(16); } else if(IsCardSelectable(10)){ CompareCards(10); }
        if(x ==  5 && y == 6) if(IsCardSelectable(16)){ CompareCards(16); } else if(IsCardSelectable(11)){ CompareCards(11); }
        if(x ==  6 && y == 6) if(IsCardSelectable(17)){ CompareCards(17); } else if(IsCardSelectable(11)){ CompareCards(11); }
        if(x ==  7 && y == 6) if(IsCardSelectable(17)){ CompareCards(17); } else if(IsCardSelectable(12)){ CompareCards(12); }
        if(x ==  8 && y == 6) if(IsCardSelectable(18)){ CompareCards(18); } else if(IsCardSelectable(12)){ CompareCards(12); }
        if(x ==  9 && y == 6) if(IsCardSelectable(18)){ CompareCards(18); } else if(IsCardSelectable(13)){ CompareCards(13); }
        if(x == 10 && y == 6) if(IsCardSelectable(19)){ CompareCards(19); } else if(IsCardSelectable(13)){ CompareCards(13); }
        if(x == 11 && y == 6) if(IsCardSelectable(19)){ CompareCards(19); } else if(IsCardSelectable(14)){ CompareCards(14); }
        if(x == 12 && y == 6) if(IsCardSelectable(20)){ CompareCards(20); } else if(IsCardSelectable(14)){ CompareCards(14); }
        if(x == 13 && y == 6) if(IsCardSelectable(20)){ CompareCards(20); }

        if(x ==  1 && y == 7) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  2 && y == 7) if(IsCardSelectable(21)){ CompareCards(21); } else if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  3 && y == 7) if(IsCardSelectable(22)){ CompareCards(22); } else if(IsCardSelectable(15)){ CompareCards(15); }
        if(x ==  4 && y == 7) if(IsCardSelectable(22)){ CompareCards(22); } else if(IsCardSelectable(16)){ CompareCards(16); }
        if(x ==  5 && y == 7) if(IsCardSelectable(23)){ CompareCards(23); } else if(IsCardSelectable(16)){ CompareCards(16); }
        if(x ==  6 && y == 7) if(IsCardSelectable(23)){ CompareCards(23); } else if(IsCardSelectable(17)){ CompareCards(17); }
        if(x ==  7 && y == 7) if(IsCardSelectable(24)){ CompareCards(24); } else if(IsCardSelectable(17)){ CompareCards(17); }
        if(x ==  8 && y == 7) if(IsCardSelectable(24)){ CompareCards(24); } else if(IsCardSelectable(18)){ CompareCards(18); }
        if(x ==  9 && y == 7) if(IsCardSelectable(25)){ CompareCards(25); } else if(IsCardSelectable(18)){ CompareCards(18); }
        if(x == 10 && y == 7) if(IsCardSelectable(25)){ CompareCards(25); } else if(IsCardSelectable(19)){ CompareCards(19); }
        if(x == 11 && y == 7) if(IsCardSelectable(26)){ CompareCards(26); } else if(IsCardSelectable(19)){ CompareCards(19); }
        if(x == 12 && y == 7) if(IsCardSelectable(26)){ CompareCards(26); } else if(IsCardSelectable(20)){ CompareCards(20); }
        if(x == 13 && y == 7) if(IsCardSelectable(27)){ CompareCards(27); } else if(IsCardSelectable(20)){ CompareCards(20); }
        if(x == 14 && y == 7) if(IsCardSelectable(27)){ CompareCards(27); }

        if(x ==  1 && y == 8) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  2 && y == 8) if(IsCardSelectable(21)){ CompareCards(21); }
        if(x ==  3 && y == 8) if(IsCardSelectable(22)){ CompareCards(22); }
        if(x ==  4 && y == 8) if(IsCardSelectable(22)){ CompareCards(22); }
        if(x ==  5 && y == 8) if(IsCardSelectable(23)){ CompareCards(23); }
        if(x ==  6 && y == 8) if(IsCardSelectable(23)){ CompareCards(23); }
        if(x ==  7 && y == 8) if(IsCardSelectable(24)){ CompareCards(24); }
        if(x ==  8 && y == 8) if(IsCardSelectable(24)){ CompareCards(24); }
        if(x ==  9 && y == 8) if(IsCardSelectable(25)){ CompareCards(25); }
        if(x == 10 && y == 8) if(IsCardSelectable(25)){ CompareCards(25); }
        if(x == 11 && y == 8) if(IsCardSelectable(26)){ CompareCards(26); }
        if(x == 12 && y == 8) if(IsCardSelectable(26)){ CompareCards(26); }
        if(x == 13 && y == 8) if(IsCardSelectable(27)){ CompareCards(27); }
        if(x == 14 && y == 8) if(IsCardSelectable(27)){ CompareCards(27); }
    }

    private boolean IsCardSelectable(int id) {
        switch(id) {
            case  0: if(cards_field[ 0].suit != -1 && cards_field[ 1].suit == -1 && cards_field[ 2].suit == -1) return true; break;

            case  1: if(cards_field[ 1].suit != -1 && cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1) return true; break;
            case  2: if(cards_field[ 2].suit != -1 && cards_field[ 4].suit == -1 && cards_field[ 5].suit == -1) return true; break;

            case  3: if(cards_field[ 3].suit != -1 && cards_field[ 6].suit == -1 && cards_field[ 7].suit == -1) return true; break;
            case  4: if(cards_field[ 4].suit != -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1) return true; break;
            case  5: if(cards_field[ 5].suit != -1 && cards_field[ 8].suit == -1 && cards_field[ 9].suit == -1) return true; break;

            case  6: if(cards_field[ 6].suit != -1 && cards_field[10].suit == -1 && cards_field[11].suit == -1) return true; break;
            case  7: if(cards_field[ 7].suit != -1 && cards_field[11].suit == -1 && cards_field[12].suit == -1) return true; break;
            case  8: if(cards_field[ 8].suit != -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1) return true; break;
            case  9: if(cards_field[ 9].suit != -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) return true; break;

            case 10: if(cards_field[10].suit != -1 && cards_field[15].suit == -1 && cards_field[16].suit == -1) return true; break;
            case 11: if(cards_field[11].suit != -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1) return true; break;
            case 12: if(cards_field[12].suit != -1 && cards_field[17].suit == -1 && cards_field[18].suit == -1) return true; break;
            case 13: if(cards_field[13].suit != -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1) return true; break;
            case 14: if(cards_field[14].suit != -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) return true; break;

            case 15: if(cards_field[15].suit != -1 && cards_field[21].suit == -1 && cards_field[22].suit == -1) return true; break;
            case 16: if(cards_field[16].suit != -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1) return true; break;
            case 17: if(cards_field[17].suit != -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1) return true; break;
            case 18: if(cards_field[18].suit != -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1) return true; break;
            case 19: if(cards_field[19].suit != -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1) return true; break;
            case 20: if(cards_field[20].suit != -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) return true; break;

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

    private int findNumber(int id){
        if(id == 28) if(cards_stack  .size() > 0) return cards_stack  .get(cards_stack.size() - 1).number; else return -1;
        if(id == 29) if(cards_reserve.size() > 0) return cards_reserve.get(0                     ).number; else return -1;
        return cards_field[id].number;
    }

    private void setDead(int id){
        if(id == 28){ if(cards_stack  .size() > 0) cards_stack  .get(cards_stack.size() - 1).dead = true;
        } else if(id == 29){ if(cards_reserve.size() > 0) cards_reserve.get(0                     ).dead = true;
        } else cards_field[id].dead = true;
    }

    public void CompareCards(int id) {
        if(tc.selector.X == -1) { // No Card Selected
            if(findNumber(id) == 12){
                setDead(id);
                timer = 48;
                tc.scorePoint += 50;
            } else {
                tc.selector.X = id;
            }
        } else { // A Card already selected
            if(findNumber(id) + findNumber(tc.selector.X) == 11){
                setDead(id);
                setDead(tc.selector.X);
                tc.selector.X = -1;
                timer = 48;
                tc.scorePoint += 50;

            } else if(findNumber(id) == 12){ // Set new tc.selector
                setDead(id);
                timer = 48;
                tc.scorePoint += 50;
            } else {
                tc.selector.X = id;
            }
        }
    }

    private void CheckForClearedLine(int id) {
        if(id ==  0) Restart();
        if(id >=  1 && id <  2) if(cards_field[ 1].suit == -1 && cards_field[ 2].suit == -1) tc.scorePoint += 1000;
        if(id >=  3 && id <  5) if(cards_field[ 3].suit == -1 && cards_field[ 4].suit == -1 && cards_field[ 5].suit == -1) tc.scorePoint += 500;
        if(id >=  6 && id <  9) if(cards_field[ 6].suit == -1 && cards_field[ 7].suit == -1 && cards_field[ 8].suit == -1 && cards_field[ 9].suit == -1) tc.scorePoint += 400;
        if(id >= 10 && id < 14) if(cards_field[10].suit == -1 && cards_field[11].suit == -1 && cards_field[12].suit == -1 && cards_field[13].suit == -1 && cards_field[14].suit == -1) tc.scorePoint += 300;
        if(id >= 15 && id < 20) if(cards_field[15].suit == -1 && cards_field[16].suit == -1 && cards_field[17].suit == -1 && cards_field[18].suit == -1 && cards_field[19].suit == -1 && cards_field[20].suit == -1) tc.scorePoint += 200;
        if(id >= 21 && id < 27) if(cards_field[21].suit == -1 && cards_field[22].suit == -1 && cards_field[23].suit == -1 && cards_field[24].suit == -1 && cards_field[25].suit == -1 && cards_field[26].suit == -1 && cards_field[27].suit == -1) tc.scorePoint += 100;
    }

}
