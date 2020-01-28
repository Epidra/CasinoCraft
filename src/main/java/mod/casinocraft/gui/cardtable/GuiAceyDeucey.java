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

public class GuiAceyDeucey extends GuiCasino {



    //--------------------CONSTRUCTOR--------------------

    public GuiAceyDeucey(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_ACEYDEUCEY);
    }

    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ actionTouch(AnotherBet() ? 0 : 1); } else
        if(tc.turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ actionTouch(1); }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(getValue(0) > -1) this.fontRenderer.drawString("Spread: " + getValue(0),  101, 126, 0);
        if(getValue(0) > -1) this.fontRenderer.drawString("Spread: " + getValue(0),  100, 125, 16777215);
        this.fontRenderer.drawString(getString(0),  76, 151, 0);
        this.fontRenderer.drawString(getString(0),  75, 150, 16777215);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        drawCard(64,  72, getCard( 0));
        if(getCard(2) != null){ drawCard(112, 72, getCard( 2)); }
        drawCard(160, 72, getCard( 1));

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(tc.turnstate == 2){
            drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }


    }



    //--------------------CUSTOM--------------------



    Card[] cards = new Card[3];

    int spread;

    boolean doublebet;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityAceyDeucey(){
   //    super(null, null);
   //}

   //public TileEntityAceyDeucey(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_ACEYDEUCEY.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_ACEYDEUCEY.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_ACEYDEUCEY.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        spread = -1;
        Set_Up();
        tc.hand = "";
    }

    public void Set_Up() {
        cards[0] = new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -25);
        cards[1] = new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -50);
        cards[2] = null;
        spread = -1;
        tc.turnstate = 3;
        doublebet = false;
    }

    public void actionTouch(int action){
        doublebet = action == 0;
        cards[2] = new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -50);
        tc.turnstate = 3;
    }

    public void update(){
        if(tc.turnstate == 2) {

        }
        if(tc.turnstate == 3) {
            if(cards[0].number == cards[1].number) {
                if(cards[2] == null) {
                    if(cards[0].shiftY == 0) {
                        cards[2] = new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -75);
                    }
                } else {
                    if(cards[2].shiftY == 0) {
                        if(cards[0].number == cards[2].number) {
                            tc.hand = "Drilling!";
                            tc.reward = 11;
                            tc.turnstate = 4;
                        } else {
                            tc.hand = "Tie!";
                            tc.reward = 1;
                            tc.turnstate = 4;
                        }
                    }
                }
            } else if(cards[0].SortedNumber() == cards[1].SortedNumber() + 1 || cards[0].SortedNumber() + 1 == cards[1].SortedNumber()) {
                if(cards[0].shiftY == 0) {
                    tc.hand = "Tie!";
                    tc.reward = 1;
                    tc.turnstate = 4;
                }
            } else {
                if(cards[2] == null) {
                    if(cards[1].shiftY == 0) {
                        spread = cards[0].SortedNumber() - cards[1].SortedNumber();
                        if(spread < 0) spread *= -1;
                        spread -= 1;
                        tc.turnstate = 2;
                        tc.hand = "Double Your Bet..?";
                    }
                } else {
                    if(cards[2].shiftY == 0) {
                        if(cards[0].SortedNumber() < cards[2].SortedNumber() && cards[2].SortedNumber() < cards[1].SortedNumber()) {
                            tc.hand = "In Between";
                            result();
                            tc.turnstate = 4;
                        } else if(cards[0].SortedNumber() > cards[2].SortedNumber() && cards[2].SortedNumber() > cards[1].SortedNumber()) {
                            tc.hand = "In Between";
                            result();
                            tc.turnstate = 4;
                        } else {
                            tc.hand = "Lost!";
                            tc.reward = 0;
                            tc.turnstate = 4;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < 3; i++) {
            if(cards[i] != null) cards[i].update();
        }
    }



    //--------------------GETTER--------------------

    public Card getCard(int index){
        return cards[index];
    }

    public int getValue(int index){
        if(index == 0) return spread;
        if(index == 1) return tc.reward;
        return 0;
    }

    public boolean getFlag(int index){
        return false;
    }

    public String getString(int index){
        return tc.hand;
    }



    //--------------------CUSTOM--------------------

    private void result(){
        if(spread == 1) tc.reward = doublebet ? 12 : 6; // 1:5
        if(spread == 2) tc.reward = doublebet ? 10 : 5; // 1:4
        if(spread == 3) tc.reward = doublebet ?  6 : 3; // 1:2
        if(spread >= 4) tc.reward = doublebet ?  4 : 2; // 1:1
    }

}
