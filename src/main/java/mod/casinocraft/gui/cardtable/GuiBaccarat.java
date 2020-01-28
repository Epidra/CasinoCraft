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

public class GuiBaccarat extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiBaccarat(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_BACCARAT);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ actionTouch(0); } else
        if(tc.turnstate == 2 && mouseRect(140, 204, 92, 26, mouseX, mouseY)){ actionTouch(1); }
    }

    protected void keyTyped2(char typedChar, int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        this.fontRenderer.drawString("PLAYER:  " + getValue(0), 25, 25, 0);
        this.fontRenderer.drawString("PLAYER:  " + getValue(0), 24, 24, 16777215);
        this.fontRenderer.drawString("DEALER:  " + getValue(1), 25, 41, 0);
        this.fontRenderer.drawString("DEALER:  " + getValue(1), 24, 40, 16777215);

        if(getValue(2) == 1) this.fontRenderer.drawString("Natural Draw!",    81, 171, 0);
        if(getValue(2) == 1) this.fontRenderer.drawString("Natural Draw!",    80, 170, 16777215);
        if(getValue(2) == 2) this.fontRenderer.drawString("continue drawing", 81, 171, 0);
        if(getValue(2) == 2) this.fontRenderer.drawString("continue drawing", 80, 170, 16777215);
        if(tc.turnstate   >= 4) this.fontRenderer.drawString(tc.hand,            81, 191, 0);
        if(tc.turnstate   >= 4) this.fontRenderer.drawString(tc.hand,            80, 190, 16777215);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tc.turnstate >= 2){
            for(int z = 0; z < getCardStack(0).size(); z++){ if(getCardStack(0).get(z).idletimer == 0) drawCard( 24 + 16*z, 80 + 4*z, getCardStack(0).get(z)); }
            for(int z = 0; z < getCardStack(1).size(); z++){ if(getCardStack(1).get(z).idletimer == 0) drawCard(144 + 16*z, 24 + 4*z, getCardStack(1).get(z)); }
        }

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(tc.turnstate == 2){
            drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
        }
    }



    //--------------------CUSTOM--------------------




    public List<Card> cards_player = new ArrayList<Card>();
    public List<Card> cards_dealer = new ArrayList<Card>();
    public int value_player;
    public int value_dealer;
    public int status;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityBaccarat(){
   //    super(null, null);
   //}

   //public TileEntityBaccarat(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_BACCARAT.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_BACCARAT.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_BACCARAT.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        cards_player.clear();
        cards_dealer.clear();
        value_player = 0;
        value_dealer = 0;
        status = 0;

        cards_player.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_player.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_dealer.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_dealer.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4)));
        cards_player.get(0).setShift(-32,   0,  8);
        cards_player.get(1).setShift(-48,   0, 32);
        cards_dealer.get(0).setShift(  0, -48,  8);
        cards_dealer.get(1).setShift(  0, -48, 32);
        value_player = 0;
        for(int i = 0; i < cards_player.size(); i++){
            if(cards_player.get(i).number == 0) {
                value_player += 1;
            } else if(cards_player.get(i).number <= 9) {
                value_player = value_player + cards_player.get(i).number + 1;
            }
        }
        value_dealer = 0;
        for(int i = 0; i < cards_dealer.size(); i++){
            if(cards_dealer.get(i).number == 0) {
                value_dealer += 1;
            } else if(cards_dealer.get(i).number <= 9) {
                value_dealer = value_dealer + cards_dealer.get(i).number + 1;
            }
        }
        while(value_player >= 10) { value_player -= 10; }
        while(value_dealer >= 10) { value_dealer -= 10; }
        if(value_player >= 8 || value_dealer >= 8) {
            status = 1;
            Result();
        } else {
            status = 2;
        }
    }

    public void actionTouch(int action){
        if(action == 0) Add_Card(true);  // HIT
        if(action == 1) Add_Card(false); // STAND
    }

    public void update(){
        if(cards_player.size() > 0) for(int i = 0; i < cards_player.size(); i++){
            cards_player.get(i).update();
        }
        if(cards_dealer.size() > 0) for(int i = 0; i < cards_dealer.size(); i++){
            cards_dealer.get(i).update();
        }
    }



    //--------------------GETTER--------------------

    public List<Card> getCardStack(int index){
        if(index == 0) return cards_player;
        if(index == 1) return cards_dealer;
        return null;
    }

    public int getValue(int index){
        if(index == 0) return value_player;
        if(index == 1) return value_dealer;
        if(index == 2) return status;
        return 0;
    }



    //--------------------CUSTOM--------------------

    private void Add_Card(boolean player) {
        if(player) {
            value_player = 0;
            cards_player.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), -48, 0));
            for(int i = 0; i < cards_player.size(); i++){
                if(cards_player.get(i).number == 0) {
                    value_player += 1;
                } else if(cards_player.get(i).number <= 9) {
                    value_player = value_player + cards_player.get(i).number + 1;
                }
            }
            while(value_player >= 10) { value_player -= 10; }
        }

        boolean temp_draw = false;

        if(cards_player.size() == 2 || value_dealer <= 3) { temp_draw = true; } else if(value_dealer == 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 5 && value_player >= 4 && value_player <= 7) { temp_draw = true; } else if(value_dealer == 6 && value_player >= 6 && value_player <= 7) { temp_draw = true; }

        if(temp_draw) {
            cards_dealer.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -48));
            value_dealer = 0;
            for(int i = 0; i < cards_dealer.size(); i++){
                if(cards_dealer.get(i).number == 0) {
                    value_dealer += 1;
                } else if(cards_dealer.get(i).number <= 9) {
                    value_dealer = value_dealer + cards_dealer.get(i).number + 1;
                }
            }
            while(value_dealer >= 10) { value_dealer -= 10; }
        }
        Result();
    }

    private void Result() {
        tc.turnstate = 4;
        if(status == 2) status = 3;
        if(value_dealer <  value_player){ tc.hand = "The Player Wins!"; tc.reward = 2; }
        if(value_dealer >  value_player){ tc.hand = "The House Wins!";  tc.reward = 0; }
        if(value_dealer == value_player){ tc.hand = "DRAW!";            tc.reward = 1; }
    }

}
