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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GuiKlondike extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiKlondike(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_KLONDIKE);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseButton == 2){
            actionTouch(-9);
        }
        if(tc.turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 8; x++){
                    if(mouseRect(0 + 32*x, 64 + (24-getValue(0))*y, 32, 24, mouseX, mouseY)){ actionTouch(x + y*8); }
                }
            }
            if(mouseRect(0 + 32*0, 16, 32, 48, mouseX, mouseY)){ actionTouch(-1); }
            if(mouseRect(0 + 32*1, 16, 32, 48, mouseX, mouseY)){ actionTouch(-2); }

            if(mouseRect(0 + 32*4, 16, 32, 48, mouseX, mouseY)){ actionTouch(-5); }
            if(mouseRect(0 + 32*5, 16, 32, 48, mouseX, mouseY)){ actionTouch(-6); }
            if(mouseRect(0 + 32*6, 16, 32, 48, mouseX, mouseY)){ actionTouch(-7); }
            if(mouseRect(0 + 32*7, 16, 32, 48, mouseX, mouseY)){ actionTouch(-8); }
        }
    }

    protected void keyTyped2(int keyCode){
        if(tc.turnstate == 2 && keyCode == KEY_ENTER){ actionTouch(-9); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        this.fontRenderer.drawString("POINTS",           76, 26, 0);
        this.fontRenderer.drawString("POINTS",           75, 25, 16777215);
        this.fontRenderer.drawString("" + tc.scorePoint, 86, 36, 0);
        this.fontRenderer.drawString("" + tc.scorePoint, 85, 35, 16777215);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        drawCardBack(32*0, 16, tc.scoreLives == 0 ? 8 : 10);
        drawCardBack(32*1, 16, 7);
        drawCardBack(32*4, 16, 7);
        drawCardBack(32*5, 16, 7);
        drawCardBack(32*6, 16, 7);
        drawCardBack(32*7, 16, 7);

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < getCardStack(x).size(); y++){
                drawCard(0 + 32*x, 64 + (24-getValue(0))*y, getCardStack(x).get(y));
            }
        }

        if(getCardStack(-1).size() > 1) drawCard(32, 16, getCardStack(-1).get(getCardStack(-1).size() - 2));
        if(getCardStack(-1).size() > 0) drawCard(32, 16, getCardStack(-1).get(getCardStack(-1).size() - 1));
        if(getCardStack(-2).size() > 0) drawCardBack(32*0, 16, 0);

        if(getCardStack( 8).size() > 1) drawCard(4*32, 16, getCardStack( 8).get(getCardStack( 8).size() - 2));
        if(getCardStack( 8).size() > 0) drawCard(4*32, 16, getCardStack( 8).get(getCardStack( 8).size() - 1));
        if(getCardStack( 9).size() > 1) drawCard(5*32, 16, getCardStack( 9).get(getCardStack( 9).size() - 2));
        if(getCardStack( 9).size() > 0) drawCard(5*32, 16, getCardStack( 9).get(getCardStack( 9).size() - 1));
        if(getCardStack(10).size() > 1) drawCard(6*32, 16, getCardStack(10).get(getCardStack(10).size() - 2));
        if(getCardStack(10).size() > 0) drawCard(6*32, 16, getCardStack(10).get(getCardStack(10).size() - 1));
        if(getCardStack(11).size() > 1) drawCard(7*32, 16, getCardStack(11).get(getCardStack(11).size() - 2));
        if(getCardStack(11).size() > 0) drawCard(7*32, 16, getCardStack(11).get(getCardStack(11).size() - 1));

        if(tc.selector.Y == -2){
            drawCardBack(32, 16, 9);
        } else if(tc.selector.Y >= 0){
            drawCardBack(tc.selector.X*32, 64 + tc.selector.Y*(24-getValue(0)), 9);
        }
    }



    //--------------------CUSTOM--------------------



    List<Card>[] cards_field   = new ArrayList[8];
    List<Card>   cards_reserve = new ArrayList<Card>();
    List<Card>   cards_stack   = new ArrayList<Card>();
    List<Card>[] cards_finish  = new ArrayList[4];

    float compress;
    float compressDisplay;
    int timer;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityKlondike(){
   //    super(null, null);
   //}

   //public TileEntityKlondike(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_KLONDIKE.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_KLONDIKE.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_KLONDIKE.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        List<Card> deck = ShuffleDeck();

        cards_field[0] = new ArrayList<Card>();
        cards_field[1] = new ArrayList<Card>(); TransferCards(cards_field[1], deck, 0, 1);
        cards_field[2] = new ArrayList<Card>(); TransferCards(cards_field[2], deck, 0, 2);
        cards_field[3] = new ArrayList<Card>(); TransferCards(cards_field[3], deck, 0, 3);
        cards_field[4] = new ArrayList<Card>(); TransferCards(cards_field[4], deck, 0, 4);
        cards_field[5] = new ArrayList<Card>(); TransferCards(cards_field[5], deck, 0, 5);
        cards_field[6] = new ArrayList<Card>(); TransferCards(cards_field[6], deck, 0, 6);
        cards_field[7] = new ArrayList<Card>(); TransferCards(cards_field[7], deck, 0, 7);

        for(int x = 1; x < 8; x++){
            int y = 0;
            for(Card c : cards_field[x]){
                c.setShift(-32*x,16 - (48 + 24*y), y*10 + x);
                if(y < cards_field[x].size() - 1) c.hidden = true;
                y++;
            }
        }

        cards_reserve.clear();
        cards_reserve.addAll(deck);
        cards_stack.clear();

        tc.scoreLives = 3;

        cards_finish[0] = new ArrayList<Card>();
        cards_finish[1] = new ArrayList<Card>();
        cards_finish[2] = new ArrayList<Card>();
        cards_finish[3] = new ArrayList<Card>();

        tc.selector = new Vector2(-1, -1);

        compress = 4;
        compressDisplay = 4;

        timer = -1;
    }

    public void actionTouch(int action){
        if(timer == -1){
            if(action == -9) timer = 1;
            if(action == -1) DrawReserve();
            if(action == -2) TouchStack();
            if(action == -5) TouchFinish(0);
            if(action == -6) TouchFinish(1);
            if(action == -7) TouchFinish(2);
            if(action == -8) TouchFinish(3);
            if(action >=  0) TouchField(action%8, action/8);
        }
    }

    public void update(){
        if(timer == 0){
            timer--;
            boolean[] done = new boolean[4];
            done[0] = done[1] = done[2] = done[3] = false;
            for(int x2 = 0; x2 < 4; x2++){
                if(cards_stack.size() > 0) {
                    if(cards_finish[x2].size() == 0) {
                        if(cards_stack.size() > 0 && cards_stack.get(cards_stack.size() - 1).number == 0){
                            if(!done[x2]){
                                cards_stack.get(cards_stack.size() - 1).setShift(0, 16, 0);
                                cards_finish[x2].add(cards_stack.get(cards_stack.size() - 1));
                                cards_stack.remove(cards_stack.size() - 1);
                                tc.selector.set(-1,  -1);
                                Uncover();
                                timer = 16;
                                tc.scorePoint+=10;
                                done[x2] = true;
                            }
                        }
                    } else {
                        if((cards_stack.get(cards_stack.size() - 1).number - 1 == cards_finish[x2].get(cards_finish[x2].size() - 1).number) && cards_finish[x2].get(cards_finish[x2].size() - 1).suit == cards_stack.get(cards_stack.size() - 1).suit) {
                            if(!done[x2]){
                                cards_stack.get(cards_stack.size() - 1).setShift(0, 16, 0);
                                cards_finish[x2].add(cards_stack.get(cards_stack.size() - 1));
                                cards_stack.remove(cards_stack.size() - 1);
                                tc.selector.set(-1, -1);
                                Uncover();
                                timer = 16;
                                tc.scorePoint+=10;
                                done[x2] = true;
                            }
                        }
                    }
                }
            }
            for(int x1 = 0; x1 < 8; x1++){
                for(int x2 = 0; x2 < 4; x2++){
                    if(cards_field[x1].size() > 0) {
                        if(cards_finish[x2].size() == 0) {
                            if(cards_field[x1].get(cards_field[x1].size() - 1).number == 0){
                                if(!done[x2]){
                                    cards_field[x1].get(cards_field[x1].size() - 1).setShift(0, 16, 0);
                                    cards_finish[x2].add(cards_field[x1].get(cards_field[x1].size() - 1));
                                    cards_field[x1].remove(cards_field[x1].size() - 1);
                                    tc.selector.set(-1,  -1);
                                    Uncover();
                                    timer = 16;
                                    tc.scorePoint+=10;
                                    done[x2] = true;
                                }
                            }
                        } else {
                            if((cards_field[x1].get(cards_field[x1].size() - 1).number - 1 == cards_finish[x2].get(cards_finish[x2].size() - 1).number) && cards_finish[x2].get(cards_finish[x2].size() - 1).suit == cards_field[x1].get(cards_field[x1].size() - 1).suit) {
                                if(!done[x2]){
                                    cards_field[x1].get(cards_field[x1].size() - 1).setShift(0, 16, 0);
                                    cards_finish[x2].add(cards_field[x1].get(cards_field[x1].size() - 1));
                                    cards_field[x1].remove(cards_field[x1].size() - 1);
                                    tc.selector.set(-1,  -1);
                                    Uncover();
                                    timer = 16;
                                    tc.scorePoint+=10;
                                    done[x2] = true;
                                }
                            }
                        }
                    }
                }
            }
            Compress();
        } else if(timer > 0){
            timer--;
        }
        for(int x = 0; x < 8; x++){
            if(cards_field[x].size() > 0) for(Card c : cards_field[x]){
                c.update();
            }
        }
        if(cards_stack.size() > 0) for(Card c : cards_stack){
            c.update();
        }
        for(int x = 0; x < 4; x++){
            if(cards_finish[x].size() > 0) for(Card c : cards_finish[x]){
                c.update();
            }
        }
        if(tc.turnstate == 2){
            if(cards_finish[0].size() == 13 && cards_finish[1].size() == 13 && cards_finish[2].size() == 13 && cards_finish[3].size() == 13 && tc.turnstate < 4) {
                tc.turnstate = 4;
            }
        }
        if(compressDisplay > compress) {
            compressDisplay -= 0.25f;
            if(compressDisplay < compress) {
                compressDisplay = compress;
            }
        }
        if(compressDisplay < compress) {
            compressDisplay += 0.25f;
            if(compressDisplay > compress) {
                compressDisplay = compress;
            }
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        return (int)compressDisplay;
    }

    public List<Card> getCardStack(int index){
        if(index == -1) return cards_stack;
        if(index == -2) return cards_reserve;
        if(index >= 8) return cards_finish[index-8];
        return cards_field[index];
    }



    //--------------------CUSTOM--------------------

    private void TransferCards(List<Card> cards_field2, List<Card> deck, int position, int count){
        for(int i = position; i < position + count; i++){
            cards_field2.add(deck.get(position));
            deck.remove(position);
        }
    }

    private void TransferCards(List<Card> cards_field2, List<Card> deck, int position, int count, int shiftX, int shiftY){
        for(int i = position; i < position + count; i++){
            deck.get(position).setShift(shiftX, shiftY, 0);
            cards_field2.add(deck.get(position));
            deck.remove(position);
        }
    }

    private List<Card> ShuffleDeck() {
        List<Card> stack = new ArrayList<Card>();
        List<Card> deck  = new ArrayList<Card>();

        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 13; x++) {
                stack.add(new Card(x, y));
            }
        }

        while(stack.size() > 1) {
            int r = tc.rand.nextInt(stack.size() - 1);
            deck.add(stack.get(r));
            stack.remove(r);
        }
        deck.add(stack.get(0));

        return deck;
    }

    private void Uncover(){
        for(int x = 0; x < 8; x++){
            if(cards_field[x].size() > 0)
                cards_field[x].get(cards_field[x].size() - 1).hidden = false;
        }
    }

    private void DrawReserve() {
        if(cards_reserve.size() > 0) {
            cards_reserve.get(0).setShift(-32, 0, 0);
            cards_stack.add(cards_reserve.get(0));
            cards_reserve.remove(0);
        } else {
            if(tc.scoreLives > 0){
                cards_reserve.addAll(cards_stack);
                cards_stack.clear();
                if(tc.difficulty != 1) tc.scorePoint-=100;
                if(tc.scorePoint < 0) tc.scorePoint = 0;
                if(tc.difficulty == 3) tc.scoreLives--;
            }
        }
    }

    private void TouchStack() {
        tc.selector.set(-2, -2);
    }

    private void TouchFinish(int slot) {
        if(!tc.selector.matches(-1, -1)) {
            if(tc.selector.Y == -2) { // Cell-to-Finish
                if(cards_stack.size() > 0){
                    if(cards_finish[slot].size() == 0) {
                        if(cards_stack.get(cards_stack.size() - 1).number == 0){
                            cards_stack.get(cards_stack.size() - 1).setShift(0, 16, 0);
                            cards_finish[slot].add(cards_stack.get(cards_stack.size() - 1));
                            cards_stack.remove(cards_stack.size() - 1);
                            tc.selector.set(-1,  -1);
                            Uncover();
                            tc.scorePoint+=10;
                        }
                    } else {
                        if((cards_stack.get(cards_stack.size() - 1).number - 1 == cards_finish[slot].get(cards_finish[slot].size() - 1).number) && cards_finish[slot].get(cards_finish[slot].size() - 1).suit == cards_stack.get(cards_stack.size() - 1).suit) {
                            cards_stack.get(cards_stack.size() - 1).setShift(0, 16, 0);
                            cards_finish[slot].add(cards_stack.get(cards_stack.size() - 1));
                            cards_stack.remove(cards_stack.size() - 1);
                            tc.selector.set(-1, -1);
                            Uncover();
                            tc.scorePoint+=10;
                        }
                    }
                }
            } else { // Field-to-Finish
                if(tc.selector.Y == cards_field[tc.selector.X].size() - 1) {
                    if(cards_finish[slot].size() == 0) {
                        if(cards_field[tc.selector.X].get(tc.selector.Y).number == 0){
                            cards_field[tc.selector.X].get(cards_field[tc.selector.X].size() - 1).setShift(0, 16, 0);
                            cards_finish[slot].add(cards_field[tc.selector.X].get(cards_field[tc.selector.X].size() - 1));
                            cards_field[tc.selector.X].remove(cards_field[tc.selector.X].size() - 1);
                            tc.selector.set(-1,  -1);
                            Uncover();
                            tc.scorePoint+=10;
                        }
                    } else {
                        if((cards_field[tc.selector.X].get(tc.selector.Y).number - 1 == cards_finish[slot].get(cards_finish[slot].size() - 1).number) && cards_finish[slot].get(cards_finish[slot].size() - 1).suit == cards_field[tc.selector.X].get(tc.selector.Y).suit) {
                            cards_field[tc.selector.X].get(cards_field[tc.selector.X].size() - 1).setShift(0, 16, 0);
                            cards_finish[slot].add(cards_field[tc.selector.X].get(cards_field[tc.selector.X].size() - 1));
                            cards_field[tc.selector.X].remove(cards_field[tc.selector.X].size() - 1);
                            tc.selector.set(-1,  -1);
                            Uncover();
                            tc.scorePoint+=10;
                        }
                    }
                }
            }
        }
        Compress();
    }

    private void TouchField(int x, int y) {
        int x2 = x;
        int y2 = y;
        if(tc.selector.Y == -2) {
            if(!MoveStack(x, y)) {
                tc.selector.set(-1,  -1);
                Uncover();
            }
        } else
        if(cards_field[x2].size() >= y2 - 1) {
            if(tc.selector.matches(-1,  -1)) {
                if(cards_field[x2].size() > 0){
                    y2 = cards_field[x2].size() <= y2 ? cards_field[x2].size() - 1 : y2;
                    float tempCard = cards_field[x2].get(y2).number;
                    float tempSuit = cards_field[x2].get(y2).suit;
                    for(int i = y2; i < cards_field[x2].size(); i++) {
                        if(i != cards_field[x2].size() - 1) {
                            if(((cards_field[x2].get(i).number - 1 != cards_field[x2].get(i + 1).number) && !(cards_field[x2].get(i).number == 1 && cards_field[x2].get(i + 1).number == 13)) || !DifferentColors(cards_field[x2].get(i).suit, cards_field[x2].get(i + 1).suit)) {
                                return;
                            }
                        }
                    }
                    tc.selector.set(x2, y2);
                }
            } else {
                if(!MoveStack(x, y)) {
                    tc.selector.set(-1, -1);
                    if(cards_field[x2].size() > 0){
                        y2 = cards_field[x2].size() <= y2 ? cards_field[x2].size() - 1 : y2;
                        float tempCard = cards_field[x2].get(y2).number;
                        float tempSuit = cards_field[x2].get(y2).suit;
                        for(int i = y2; i < cards_field[x2].size(); i++) {
                            if(i != cards_field[x2].size() - 1) {
                                if(((cards_field[x2].get(i).number - 1 != cards_field[x2].get(i + 1).number) && !(cards_field[x2].get(i).number == 1 && cards_field[x2].get(i + 1).number == 13)) || !DifferentColors(cards_field[x2].get(i).suit, cards_field[x2].get(i + 1).suit)) {
                                    return;
                                }
                            }
                        }
                        tc.selector.set(x2, y2);
                    }
                }
            }
        }
        Compress();
    }

    private boolean MoveStack(int x, int y) {
        int x2 = x;
        int y2 = cards_field[x2].size() - 1;
        if(tc.selector.Y != -2) { // Field-to-Field
            if(cards_field[x2].size() == 0) {
                TransferCards(cards_field[x2], cards_field[tc.selector.X], tc.selector.Y, cards_field[tc.selector.X].size() - tc.selector.Y, 0, 16);
                tc.selector.set(-1, -1);
                Uncover();
                return true;
            } else {
                if((cards_field[tc.selector.X].get(tc.selector.Y).number + 1 == cards_field[x2].get(y2).number) && DifferentColors(cards_field[x2].get(y2).suit, cards_field[tc.selector.X].get(tc.selector.Y).suit)) {
                    TransferCards(cards_field[x2], cards_field[tc.selector.X], tc.selector.Y, cards_field[tc.selector.X].size() - tc.selector.Y, 0, 16);
                    tc.selector.set(-1, -1);
                    Uncover();
                    return true;
                }
            }
        } else { // Cell-to-Field
            if(cards_stack.size() > 0){
                if(cards_field[x2].size() == 0) {
                    cards_stack.get(cards_stack.size() - 1).setShift(0, 16, 0);
                    cards_field[x2].add(cards_stack.get(cards_stack.size() - 1));
                    cards_stack.remove(cards_stack.size() - 1);
                    tc.selector.set(-1, -1);
                    Uncover();
                    tc.scorePoint+=5;
                    return true;
                } else {
                    if((cards_stack.get(cards_stack.size() - 1).number + 1 == cards_field[x2].get(y2).number) && DifferentColors(cards_field[x2].get(y2).suit, cards_stack.get(cards_stack.size() - 1).suit)) {
                        cards_stack.get(cards_stack.size() - 1).setShift(0, 16, 0);
                        cards_field[x2].add(cards_stack.get(cards_stack.size() - 1));
                        cards_stack.remove(cards_stack.size() - 1);
                        tc.selector.set(-1, -1);
                        Uncover();
                        tc.scorePoint+=5;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void Compress() {
        int i = 0;
        for(int x = 0; x < 8; x++) {
            if(cards_field[x].size() > i) i = cards_field[x].size();
        }
        if(i > 6) {
            compress = i-3;
        } else {
            compress = 0;
        }
    }

    private boolean DifferentColors(float a, float b) {
        if(a == 0 || a == 1) if(b == 2 || b == 3) return true;
        if(a == 2 || a == 3) return b == 0 || b == 1;
        return false;
    }

}
