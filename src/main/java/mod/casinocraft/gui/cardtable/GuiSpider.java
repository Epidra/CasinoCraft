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

public class GuiSpider extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSpider(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_SPIDER);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseButton == 0){
            for(int y = 1; y < 20; y++){
                for(int x = 0; x < 10; x++){
                    if(mouseRect(-32 + 32*x, 16 + (24-getValue(0))*y, 32, 24, mouseX, mouseY)){ actionTouch(x + y*10); }
                }
            }
            if(mouseRect(296, 24, 32, 196, mouseX, mouseY)){ actionTouch(-1); }
        }
    }

    protected void keyTyped2(char typedChar, int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < getCardStack(x).size(); y++){
                drawCard(-32 + x*32, 16 + y*(24-getValue(0)), getCardStack(x).get(y));
            }
        }

        //if(tc.selector.Y != -1) drawCardBack(tc.selector.X*32 - 32, 16 + tc.selector.Y*24, 9);
        if(tc.selector.Y != -1) drawCardBack(tc.selector.X*32 - 32, 16 + tc.selector.Y*(24-getValue(0)), 9);

        drawCardBack(296, 24, 7);

        if(getCardStack(14).size() > 0) drawCardBack(296, 24 + 0*24, 0);
        if(getCardStack(13).size() > 0) drawCardBack(296, 24 + 1*24, 0);
        if(getCardStack(12).size() > 0) drawCardBack(296, 24 + 2*24, 0);
        if(getCardStack(11).size() > 0) drawCardBack(296, 24 + 3*24, 0);
        if(getCardStack(10).size() > 0) drawCardBack(296, 24 + 4*24, 0);

        drawCardBack(-72, 24, 7);
        int i = 0;
        for(Card c : getCardStack(15)){
            drawCard(-72, 24 + i*24, getCard(i));
        }
    }



    //--------------------CUSTOM--------------------




    List<Card>[] cards_field   = new ArrayList[10];
    List<Card>[] cards_reserve = new ArrayList[5];
    List<Card>   cards_finish  = new ArrayList<Card>();

    float compress;
    float compressDisplay;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntitySpider(){
   //    super(null, null);
   //}

   //public TileEntitySpider(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_SPIDER.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SPIDER.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SPIDER.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        List<Card> deck = ShuffleDeck();

        cards_field[0] = new ArrayList<Card>(); TransferCards(cards_field[0], deck, 0, 6);
        cards_field[1] = new ArrayList<Card>(); TransferCards(cards_field[1], deck, 0, 6);
        cards_field[2] = new ArrayList<Card>(); TransferCards(cards_field[2], deck, 0, 6);
        cards_field[3] = new ArrayList<Card>(); TransferCards(cards_field[3], deck, 0, 6);
        cards_field[4] = new ArrayList<Card>(); TransferCards(cards_field[4], deck, 0, 5);
        cards_field[5] = new ArrayList<Card>(); TransferCards(cards_field[5], deck, 0, 5);
        cards_field[6] = new ArrayList<Card>(); TransferCards(cards_field[6], deck, 0, 5);
        cards_field[7] = new ArrayList<Card>(); TransferCards(cards_field[7], deck, 0, 5);
        cards_field[8] = new ArrayList<Card>(); TransferCards(cards_field[8], deck, 0, 5);
        cards_field[9] = new ArrayList<Card>(); TransferCards(cards_field[9], deck, 0, 5);

        cards_reserve[0] = new ArrayList<Card>(); TransferCards(cards_reserve[0], deck, 0, 10);
        cards_reserve[1] = new ArrayList<Card>(); TransferCards(cards_reserve[1], deck, 0, 10);
        cards_reserve[2] = new ArrayList<Card>(); TransferCards(cards_reserve[2], deck, 0, 10);
        cards_reserve[3] = new ArrayList<Card>(); TransferCards(cards_reserve[3], deck, 0, 10);
        cards_reserve[4] = new ArrayList<Card>(); TransferCards(cards_reserve[4], deck, 0, 10);

        tc.selector = new Vector2(-1, -1);

        compress = 2;
        compressDisplay = 2;

        for(int x = 0; x < 10; x++){
            int y = 0;
            for(Card c : cards_field[x]){
                c.setShift(0, -24*y, 70-10*y + x*3);
                y++;
            }
        }
    }

    public void actionTouch(int action){
        if(action == -1) DrawReserve();
        if(action >=  0) TouchField(action%10, action/10);
    }

    public void update(){
        for(int x = 0; x < 10; x++){
            if(cards_field[x].size() > 0) for(Card c : cards_field[x]){
                c.update();
            }
        }
        if(cards_finish.size() == 8 && tc.turnstate < 4) {
            tc.scorePoint = 100;
            tc.turnstate = 4;
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
        if(index == 15) return cards_finish;
        if(index >= 10) return cards_reserve[index-10];
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
                stack.add(new Card(x, tc.difficulty == 1 ? 0 : tc.difficulty == 2 ? y / 2 : y));
                stack.add(new Card(x, tc.difficulty == 1 ? 0 : tc.difficulty == 2 ? y / 2 : y));
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

    private void DrawReserve() {
        if(cards_reserve[0].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[0].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[0].get(x));
            }
            cards_reserve[0].clear();
        } else if(cards_reserve[1].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[1].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[1].get(x));
            }
            cards_reserve[1].clear();
        } else if(cards_reserve[2].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[2].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[2].get(x));
            }
            cards_reserve[2].clear();
        } else if(cards_reserve[3].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[3].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[3].get(x));
            }
            cards_reserve[3].clear();
        } else if(cards_reserve[4].size() > 0) {
            for(int x = 0; x < 10; x++) {
                cards_reserve[4].get(x).setShift(0, 24, 0);
                cards_field[x].add(cards_reserve[4].get(x));
            }
            cards_reserve[4].clear();
        }
    }

    private void TouchField(int x, int y) {
        if(cards_field[x].size() >= y - 1) {
            if(tc.selector.matches(-1, -1)) {
                int x2 = x;
                int y2 = cards_field[x].size() < y ? cards_field[x].size()-1 : y - 1;
                float tempCard = cards_field[x2].get(y2).number;
                for(int i = y2; i < cards_field[x2].size(); i++) {
                    if(i != cards_field[x2].size() - 1) {
                        if((cards_field[x2].get(i).number - 1 != cards_field[x2].get(i + 1).number) && !(cards_field[x2].get(i).number == 1 && cards_field[x2].get(i + 1).number == 13)) {
                            return;
                        }
                    }
                }
                tc.selector.set(x2, y2);
            } else {
                if(!MoveStack(x, y)) {
                    tc.selector.set(-1, -1);
                }
            }
        }
        Compress();
    }

    private boolean MoveStack(int x, int y) {
        int x2 = x;
        int y2 = cards_field[x2].size() - 1;
        if(cards_field[x2].size() == 0) {
            TransferCards(cards_field[x2], cards_field[tc.selector.X], tc.selector.Y, cards_field[tc.selector.X].size() - tc.selector.Y, 0, 16);
            tc.selector.set(-1, -1);
            ClearRow(x2);
            return true;
        } else {
            if(cards_field[tc.selector.X].get(tc.selector.Y).number + 1 == cards_field[x2].get(y2).number) {
                TransferCards(cards_field[x2], cards_field[tc.selector.X], tc.selector.Y, cards_field[tc.selector.X].size() - tc.selector.Y, 0, 16);
                tc.selector.set(-1, -1);
                ClearRow(x2);
                return true;
            }
        }
        return false;
    }

    private void ClearRow(int row) {
        if(cards_field[row].size() >= 13) {
            for(int i = 0; i < cards_field[row].size(); i++) {
                if(cards_field[row].get(i).number == 12) {
                    for(int j = 0; j < cards_field[row].size()-1; j++) {
                        if(12 - j == 0 && cards_field[row].get(i + j).number == 13 && cards_field[row].get(i).suit == cards_field[row].get(i + j).suit) {
                            cards_finish.add(cards_field[row].get(cards_field[row].size() - 1));
                            for(int z = cards_field[row].size() - 13; z < cards_field[row].size() - 13; z++) cards_field[row].remove(z);
                            return;
                        }
                        if(12 - j != cards_field[row].get(i + j).number || cards_field[row].get(i).suit != cards_field[row].get(i + j).suit) break;

                    }
                }
            }
        }
    }

    private void Compress() {
        int i = 0;
        for(int x = 0; x < 8; x++) {
            if(cards_field[x].size() > i) i = cards_field[x].size();
        }
        if(i > 8) {
            compress = i-5;
        } else {
            compress = 0;
        }
    }

}
