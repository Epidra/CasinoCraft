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

public class GuiVideoPoker extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiVideoPoker(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_VIDEOPOKER);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2){
            if(mouseRect(  5,  24, 46, 26, mouseX, mouseY)){ actionTouch(0); } // Hold 1
            if(mouseRect( 55,  24, 46, 26, mouseX, mouseY)){ actionTouch(1); } // Hold 2
            if(mouseRect(105,  24, 46, 26, mouseX, mouseY)){ actionTouch(2); } // Hold 3
            if(mouseRect(155,  24, 46, 26, mouseX, mouseY)){ actionTouch(3); } // Hold 4
            if(mouseRect(205,  24, 46, 26, mouseX, mouseY)){ actionTouch(4); } // Hold 5
            if(mouseRect( 82, 204, 92, 26, mouseX, mouseY)){ actionTouch(5); } // Finish
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 4) this.fontRenderer.drawString(getString(0), 76, 151, 0);
        if(tc.turnstate >= 4) this.fontRenderer.drawString(getString(0), 75, 150, 16777215);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tc.turnstate >= 2) {
            drawCardBack(16,  72, 7); // Card 1
            drawCardBack(64,  72, 7); // Card 2
            drawCardBack(112, 72, 7); // Card 3
            drawCardBack(160, 72, 7); // Card 4
            drawCardBack(208, 72, 7); // Card 5
            drawCard(16,  72, getCard(0)); // Card 1
            drawCard(64,  72, getCard(1)); // Card 2
            drawCard(112, 72, getCard(2)); // Card 3
            drawCard(160, 72, getCard(3)); // Card 4
            drawCard(208, 72, getCard(4)); // Card 5
        }

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(tc.turnstate == 2){
            drawTexturedModalRect(guiLeft+82+7, guiTop+204+2, 78*2, 22, 78, 22); // Button Finish
            if(getFlag(0)){drawTexturedModalRect(guiLeft+3+5,   guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+5,   guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 1
            if(getFlag(1)){drawTexturedModalRect(guiLeft+3+55,  guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+55,  guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 2
            if(getFlag(2)){drawTexturedModalRect(guiLeft+3+105, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+105, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 3
            if(getFlag(3)){drawTexturedModalRect(guiLeft+3+155, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+155, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 4
            if(getFlag(4)){drawTexturedModalRect(guiLeft+3+205, guiTop+2+24, 78*2, 44, 39, 22);} else {drawTexturedModalRect(guiLeft+3+205, guiTop+2+24, 78*2+39, 44, 39, 22);} // Button Hold 5
        }
    }



    //--------------------CUSTOM--------------------




    public boolean end;
    public Card[] card = new Card[5];
    public boolean[] hold = new boolean[5];
    private int ticker;
    private int movestate;

    //--------------------CONSTRUCTOR--------------------

   //public TileEntityVideoPoker(){
   //    super(null, null);
   //}

   //public TileEntityVideoPoker(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_VIDEOPOKER.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_VIDEOPOKER.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_VIDEOPOKER.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        for(int i = 0; i < 5; i++){
            card[i] = new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, 20 + 5*i);
            hold[i] = false;
        }
        end = false;
        ticker = 0;
        movestate = 0;
    }

    public void actionTouch(int action){
        if(action == 5 && tc.turnstate == 2){
            tc.turnstate = 3;
            movestate = 1;
            for(int i = 0; i < 5; i++){
                if(!hold[i]) card[i].hidden = true;
            }
        } else {
            hold[action] = !hold[action];
        }
    }

    public void update(){
        if(tc.turnstate == 2) {
            for(int i = 0; i < 5; i++){
                card[i].update();
            }
        }
        switch(movestate){
            case 0: // NULL
                break;
            case 1: // Cards Move up
                ticker++;
                for(int i = 0; i < 5; i++){
                    if(!hold[i]) card[i].shiftY--;
                }
                if(ticker >= 30){
                    if(!hold[0]){ card[0].number = tc.rand.nextInt(13); card[0].suit = tc.rand.nextInt(4); card[0].hidden = false; }
                    if(!hold[1]){ card[1].number = tc.rand.nextInt(13); card[1].suit = tc.rand.nextInt(4); card[1].hidden = false; }
                    if(!hold[2]){ card[2].number = tc.rand.nextInt(13); card[2].suit = tc.rand.nextInt(4); card[2].hidden = false; }
                    if(!hold[3]){ card[3].number = tc.rand.nextInt(13); card[3].suit = tc.rand.nextInt(4); card[3].hidden = false; }
                    if(!hold[4]){ card[4].number = tc.rand.nextInt(13); card[4].suit = tc.rand.nextInt(4); card[4].hidden = false; }
                    movestate = 2;
                }
                break;
            case 2: // Cards Move down
                ticker--;
                for(int i = 0; i < 5; i++){
                    if(!hold[i]) card[i].shiftY++;
                }
                if(ticker == 0){
                    movestate = 3;
                }
                break;
            case 3: // Cards Move Together
                ticker++;
                card[0].shiftX+=2;
                card[1].shiftX+=1;
                card[3].shiftX-=1;
                card[4].shiftX-=2;
                if(ticker == 48){
                    Sort();
                    card[0].shiftX =  48*2;
                    card[1].shiftX =  48;
                    card[2].shiftX =   0;
                    card[3].shiftX = -48;
                    card[4].shiftX = -48*2;
                    movestate = 4;
                }
                break;
            case 4: // Cards Move apart
                ticker--;
                card[0].shiftX-=2;
                card[1].shiftX-=1;
                card[3].shiftX+=1;
                card[4].shiftX+=2;
                if(ticker == 0){
                    Result();
                    tc.turnstate = 4;
                    movestate = 0;
                }
                break;
        }
    }



    //--------------------GETTER--------------------

    public Card getCard(int index){
        return card[index];
    }

    public boolean getFlag(int index){
        return hold[index];
    }

    public String getString(int index){
        return tc.hand;
    }



    //--------------------CUSTOM--------------------

    public void Click_Hold(int i) {
        hold[i] = !hold[i];
    }

    private void Sort() {
        if(card[0].number > card[4].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[0].number > card[3].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[0].number > card[2].number) { Card z = card[0]; card[0] = card[1]; card[1] = card[2]; card[2]                                       = z; }
        if(card[0].number > card[1].number) { Card z = card[0]; card[0] = card[1]; card[1]                                                          = z; }
        if(card[1].number > card[4].number) { Card z =                    card[1]; card[1] = card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[1].number > card[3].number) { Card z =                    card[1]; card[1] = card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[1].number > card[2].number) { Card z =                    card[1]; card[1] = card[2]; card[2]                                       = z; }
        if(card[2].number > card[4].number) { Card z =                                       card[2]; card[2] = card[3]; card[3] = card[4]; card[4] = z; }
        if(card[2].number > card[3].number) { Card z =                                       card[2]; card[2] = card[3]; card[3]                    = z; }
        if(card[3].number > card[4].number) { Card z =                                                          card[3]; card[3] = card[4]; card[4] = z; }
    }

    private void Result() {
        if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number == card[4-1].number && card[1-1].number == card[5-1].number) {
            tc.hand = "5 of a Kind";
            tc.reward = 20;
        } else if(card[1-1].number == 9 && card[2-1].number == 10 && card[3-1].number == 11 && card[4-1].number == 12 && card[5-1].number == 0 && card[1-1].suit == card[2-1].suit && card[1-1].suit == card[3-1].suit && card[1-1].suit == card[4-1].suit && card[1-1].suit == card[5-1].suit) {
            tc.hand = "ROYAL FLUSH!!";
            tc.reward = 16;
        } else if(card[1-1].number <= 9 && card[1-1].number + 1 == card[2-1].number && card[1-1].number + 2 == card[3-1].number && card[1-1].number + 3 == card[4-1].number && card[1-1].number + 4 == card[5-1].number && card[1-1].suit == card[2-1].suit && card[1-1].suit == card[3-1].suit && card[1-1].suit == card[4-1].suit && card[1-1].suit == card[5-1].suit) {
            tc.hand = "Straight Flush";
            tc.reward = 12;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number == card[4-1].number && card[1-1].number != card[5-1].number) {
            tc.hand = "4 of a Kind";
            tc.reward = 10;
        } else if(card[2-1].number == card[3-1].number && card[2-1].number == card[4-1].number && card[2-1].number == card[5-1].number && card[2-1].number != card[1-1].number) {
            tc.hand = "4 of a Kind";
            tc.reward = 10;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number != card[4-1].number && card[4-1].number == card[5-1].number) {
            tc.hand = "Full House";
            tc.reward = 8;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number != card[3-1].number && card[3-1].number == card[4-1].number && card[3-1].number == card[5-1].number) {
            tc.hand = "Full House";
            tc.reward = 8;
        } else if(card[1-1].suit == card[2-1].suit && card[1-1].suit == card[3-1].suit && card[1-1].suit == card[4-1].suit && card[1-1].suit == card[5-1].suit) {
            tc.hand = "Flush";
            tc.reward = 7;
        } else if(card[1-1].number <= 9 && card[1-1].number + 1 == card[2-1].number && card[1-1].number + 2 == card[3-1].number && card[1-1].number + 3 == card[4-1].number && card[1-1].number + 4 == card[5-1].number) {
            tc.hand = "Straight";
            tc.reward = 6;
        } else if(card[1-1].number == card[2-1].number && card[1-1].number == card[3-1].number && card[1-1].number != card[4-1].number && card[1-1].number != card[5-1].number) {
            tc.hand = "3 of a Kind";
            tc.reward = 4;
        } else if(card[2-1].number == card[3-1].number && card[2-1].number == card[4-1].number && card[2-1].number != card[1-1].number && card[2-1].number != card[5-1].number) {
            tc.hand = "3 of a Kind";
            tc.reward = 4;
        } else if(card[3-1].number == card[4-1].number && card[3-1].number == card[5-1].number && card[3-1].number != card[1-1].number && card[3-1].number != card[2-1].number) {
            tc.hand = "3 of a Kind";
            tc.reward = 4;
        } else if(card[1-1].number == card[2-1].number && card[3-1].number == card[4-1].number) {
            tc.hand = "Two Pair";
            tc.reward = 2;
        } else if(card[1-1].number == card[2-1].number && card[4-1].number == card[5-1].number) {
            tc.hand = "Two Pair";
            tc.reward = 2;
        } else if(card[2-1].number == card[3-1].number && card[4-1].number == card[5-1].number) {
            tc.hand = "Two Pair";
            tc.reward = 2;
        } else if((card[1-1].number >= 10 || card[1-1].number == 0) && card[1-1].number == card[2-1].number) {
            tc.hand = "Jacks or Better";
            tc.reward = 1;
        } else if((card[2-1].number >= 10 || card[2-1].number == 0) && card[2-1].number == card[3-1].number) {
            tc.hand = "Jacks or Better";
            tc.reward = 1;
        } else if((card[3-1].number >= 10 || card[3-1].number == 0) && card[3-1].number == card[4-1].number) {
            tc.hand = "Jacks or Better";
            tc.reward = 1;
        } else if((card[4-1].number >= 10 || card[4-1].number == 0) && card[4-1].number == card[5-1].number) {
            tc.hand = "Jacks or Better";
            tc.reward = 1;
        } else {

        }
        end = true;
    }

}
