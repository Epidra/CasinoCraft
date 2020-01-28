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

public class GuiRougeEtNoir extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiRougeEtNoir(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_ROUGEETNOIR);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2) {
            if(mouseButton == 0){
                if(mouseRect(32,  48, 32, 48, mouseX, mouseY)){ actionTouch(0); }
                if(mouseRect(32, 144, 32, 48, mouseX, mouseY)){ actionTouch(1); }
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 3) this.fontRenderer.drawString("" + getValue(0), 37,  39, 0);
        if(tc.turnstate >= 3) this.fontRenderer.drawString("" + getValue(0), 36,  38, 16777215);
        if(tc.turnstate >= 3) this.fontRenderer.drawString("" + getValue(1), 37, 135, 0);
        if(tc.turnstate >= 3) this.fontRenderer.drawString("" + getValue(1), 36, 134, 16777215);
        if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 66, 116, 0);
        if(tc.turnstate >= 4) this.fontRenderer.drawString(tc.hand, 65, 115, 16777215);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        drawCardBack(32,  48, 1);
        drawCardBack(32, 144, 0);
        int i = 0; for(Card c : getCardStack(0)){ drawCard(32+8 + 16*i,  48, c); i++; }
        i = 0; for(Card c : getCardStack(1)){ drawCard(32+8 + 16*i, 144, c); i++; }
    }



    //--------------------CUSTOM--------------------





    List<Card> cards_rouge = new ArrayList<Card>();
    List<Card> cards_noir  = new ArrayList<Card>();

    int value_rouge;
    int value_noir;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityRougeEtNoir(){
   //    super(null, null);
   //}

   //public TileEntityRougeEtNoir(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_ROUGEETNOIR.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_ROUGEETNOIR.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_ROUGEETNOIR.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        Set_Up();
    }

    public void Set_Up() {
        tc.selector.set(-1, -1);
        cards_rouge.clear();
        cards_noir.clear();
        value_rouge = 0;
        value_noir = 0;
    }

    public void actionTouch(int action){
        tc.selector.X = action;
        tc.turnstate = 3;
        cards_rouge.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -50));
    }

    public void update(){
        if(tc.turnstate == 3) {
            if(value_rouge < 31) {
                if(cards_rouge.get(cards_rouge.size() - 1).shiftY >= -10) {
                    value_rouge += (cards_rouge.get(cards_rouge.size() - 1).number >= 9 ? 10 : (cards_rouge.get(cards_rouge.size() - 1).number + 1));
                    if(value_rouge >= 31) {
                        cards_noir.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -50));
                    } else {
                        cards_rouge.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -50));
                    }
                }
            } else {
                if(cards_noir.get(cards_noir.size() - 1).shiftY >= -10) {
                    value_noir += (cards_noir.get(cards_noir.size() - 1).number >= 9 ? 10 : (cards_noir.get(cards_noir.size() - 1).number + 1));
                    if(value_noir >= 31) {
                        result();
                    } else {
                        cards_noir.add(new Card(tc.rand.nextInt(13), tc.rand.nextInt(4), 0, -50));
                    }
                }
            }
        }
        for(Card c : cards_rouge) { c.update(); }
        for(Card c : cards_noir ) { c.update(); }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        if(index == 0) return value_rouge;
        if(index == 1) return value_noir;
        return tc.selector.X;
    }

    public List<Card> getCardStack(int index){
        if(index == 0) return cards_rouge;
        return cards_noir;
    }



    //--------------------CUSTOM--------------------

    public void result(){
        tc.turnstate = 4;
        if(value_rouge <  value_noir) { tc.hand = "Rouge Wins!"; if(tc.selector.X == 0) { tc.reward = 2; } }
        if(value_rouge >  value_noir) { tc.hand = "Noir Wins!";  if(tc.selector.X == 1) { tc.reward = 2; } }
        if(value_rouge == value_noir) { tc.hand = "Tie!";                              tc.reward = 1; }
    }

}
