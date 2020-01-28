package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.util.Dice;
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
import java.util.Random;

public class GuiSicBo extends GuiCasino {

    int diceColor = 0;

    //--------------------CONSTRUCTOR--------------------

    public GuiSicBo(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_SICBO);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
        tc.gridI = new int[12][6];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseButton == 0){
            for(int x = 0; x < 12; x++){
                if(mouseRect(-63 + 32*x,  13, 32, 30, mouseX, mouseY)){ action(x, 0); }
                if(mouseRect(-63 + 32*x,  41, 32, 30, mouseX, mouseY)){ action(x, 1); }
                if(mouseRect(-63 + 32*x,  73, 32, 46, mouseX, mouseY)){ action(x, 2); }
                if(mouseRect(-63 + 32*x, 121, 32, 46, mouseX, mouseY)){ action(x, 3); }
                if(mouseRect(-63 + 32*x, 169, 32, 46, mouseX, mouseY)){ action(x, 4); }
                if(mouseRect(-63 + 32*x, 217, 32, 30, mouseX, mouseY)){ action(x, 5); }
            }
        }
    }

    private void action(int x, int y) {
        if(tc.selector.matches(x, y) && getValue(x + y*12) == 0) {
            if(playerToken >= tc.getBetLow()) {
                actionTouch(-1);
                CollectBet();
                playerToken = -1;
            } else {
                actionTouch(-2);
            }
        } else {
            actionTouch(x + y*12);
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_ENTER){ actionTouch(-2); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 4){
            this.fontRenderer.drawString(getString(0), 26,  -9, 0);
            this.fontRenderer.drawString(getString(0), 25, -10, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_LEFT);
        this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_RIGHT);
        this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);

        if(tc.turnstate >= 2){
            int color = 0;
            for(int x = 0; x < 12; x++){
                color = getValue(x + 0*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  13, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = getValue(x + 1*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  41, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = getValue(x + 2*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  73, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = getValue(x + 3*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 121, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = getValue(x + 4*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 169, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = getValue(x + 5*12); if(color != 0) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 217, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);

                if(tc.selector.matches(x, 0)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  13, 192, 0, 32, 32);
                if(tc.selector.matches(x, 1)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  41, 192, 0, 32, 32);
                if(tc.selector.matches(x, 2)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop +  73, 192, 0, 32, 32);
                if(tc.selector.matches(x, 3)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 121, 192, 0, 32, 32);
                if(tc.selector.matches(x, 4)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 169, 192, 0, 32, 32);
                if(tc.selector.matches(x, 5)) this.drawTexturedModalRect(guiLeft+-63 + 32*x, guiTop + 217, 192, 0, 32, 32);
            }
        }

        if(tc.turnstate == 3){
            this.drawTexturedModalRect(guiLeft + getDice(0).posX, guiTop + getDice(0).posY, getDice(0).number*32, diceColor*32, 32, 32);
            this.drawTexturedModalRect(guiLeft + getDice(1).posX, guiTop + getDice(1).posY, getDice(1).number*32, diceColor*32, 32, 32);
            this.drawTexturedModalRect(guiLeft + getDice(2).posX, guiTop + getDice(2).posY, getDice(2).number*32, diceColor*32, 32, 32);
        }
    }



    //--------------------CUSTOM--------------------




    Dice[] dice = new Dice[3];



    //--------------------CONSTRUCTOR--------------------

   //public TileEntitySicBo(){
   //    super(null, null);
   //}

   //public TileEntitySicBo(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new int[12][6];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_SICBO.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SICBO.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_SICBO.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 6; y++) {
            for(int x = 0; x < 12; x++) {
                tc.gridI[x][y] = 0;
            }
        }
        tc.selector = new Vector2(-1, -1);
        dice[0] = new Dice(0, 4);
        dice[1] = new Dice(0, 4);
        dice[2] = new Dice(0, 4);
    }

    public void actionTouch(int action){
        if(action == -2) {
            if(tc.selector.X > -1){
                tc.gridI[tc.selector.X][tc.selector.Y] = 1;
                tc.selector.set(-1, -1);
            }
            Spin();
        } else if(action == -1){
            if(tc.selector.X > -1){
                tc.gridI[tc.selector.X][tc.selector.Y] = 1;
                tc.selector.set(-1, -1);
            }
        } else {
            tc.selector = new Vector2(action%12, action/12);
        }
    }

    public void update(){
        if(tc.turnstate == 3) {
            for(int i = 0; i < 3; i++) {
                if(dice[i].shiftX > 45) {
                    dice[i].Update(1, tc.rand.nextInt(6));
                } else if(dice[i].shiftX > 0) {
                    dice[i].shiftX = 0;
                    dice[i].shiftY = 0;
                } else {

                }
            }
        }
        if(tc.turnstate == 4) {
            tc.selector  = new Vector2(-1, -1);
        }
    }



    //--------------------GETTER--------------------

    public Vector2 getVector(int index){
        return tc.selector;
    }

    public Dice getDice(int index){
        return dice[index];
    }

    public int getValue(int index){
        if(index == -1) return tc.reward;
        return tc.gridI[index%12][index/12];
    }

    public String getString(int index){
        return tc.hand;
    }



    //--------------------CUSTOM--------------------

    private void Place() {
        //if(tc.selector != tc.selector) {
        //    if(tc.gridI[(int)tc.selector.X][(int)tc.selector.Y] == 0) tc.selector = tc.selector;
        //} else {
        tc.gridI[tc.selector.X][tc.selector.Y] = 1;
        tc.selector = new Vector2(-1, -1);
        //if(FM.coins >= bet) {
        //    FM.coins -= bet;
        //    FM.coins_bonus -= bet;
        //} else {
        //    Spin();
        //}
        //}
    }

    private void Spin() {
        if(tc.turnstate == 2) {
            dice[0].Set_Up(200 + tc.rand.nextInt( 50),  50 + tc.rand.nextInt(200), tc.rand.nextInt(2) == 0);
            dice[1].Set_Up(100 + tc.rand.nextInt(100), 100 + tc.rand.nextInt(100), tc.rand.nextInt(2) == 0);
            dice[2].Set_Up( 50 + tc.rand.nextInt(200), 200 + tc.rand.nextInt( 50), tc.rand.nextInt(2) == 0);
            tc.turnstate = 3;
        } else if(tc.turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[1].shiftX == 0 && dice[2].shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        tc.hand = "" + (dice[0].number + 1) + "-" + (dice[1].number + 1) + "-" + (dice[2].number + 1);
        if(tc.gridI[ 0][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[ 0][0] = 2; } else { tc.gridI[ 0][0] = 3; } }
        if(tc.gridI[ 1][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[ 1][0] = 2; } else { tc.gridI[ 1][0] = 3; } }
        if(tc.gridI[ 2][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[ 2][0] = 2; } else { tc.gridI[ 2][0] = 3; } }
        if(tc.gridI[ 3][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[ 3][0] = 2; } else { tc.gridI[ 3][0] = 3; } }
        if(tc.gridI[ 4][0] == 1) { if(Result_AnyTriple()                         ) { tc.reward +=  31; tc.gridI[ 4][0] = 2; } else { tc.gridI[ 4][0] = 3; } }
        if(tc.gridI[ 5][0] == 1) { if(Result_AnyTriple()                         ) { tc.reward +=  31; tc.gridI[ 5][0] = 2; } else { tc.gridI[ 5][0] = 3; } }
        if(tc.gridI[ 6][0] == 1) { if(Result_AnyTriple()                         ) { tc.reward +=  31; tc.gridI[ 6][0] = 2; } else { tc.gridI[ 6][0] = 3; } }
        if(tc.gridI[ 7][0] == 1) { if(Result_AnyTriple()                         ) { tc.reward +=  31; tc.gridI[ 7][0] = 2; } else { tc.gridI[ 7][0] = 3; } }
        if(tc.gridI[ 8][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[ 8][0] = 2; } else { tc.gridI[ 8][0] = 3; } }
        if(tc.gridI[ 9][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[ 9][0] = 2; } else { tc.gridI[ 9][0] = 3; } }
        if(tc.gridI[10][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[10][0] = 2; } else { tc.gridI[10][0] = 3; } }
        if(tc.gridI[11][0] == 1) { if(Result_Value() <= 10 && !Result_AnyTriple()) { tc.reward +=   2; tc.gridI[11][0] = 2; } else { tc.gridI[11][0] = 3; } }

        if(tc.gridI[ 0][1] == 1) { if(Result_Triple(0)                           ) { tc.reward += 181; tc.gridI[ 0][1] = 2; } else { tc.gridI[ 0][1] = 3; } }
        if(tc.gridI[ 1][1] == 1) { if(Result_Triple(0)                           ) { tc.reward += 181; tc.gridI[ 1][1] = 2; } else { tc.gridI[ 1][1] = 3; } }
        if(tc.gridI[ 2][1] == 1) { if(Result_Triple(1)                           ) { tc.reward += 181; tc.gridI[ 2][1] = 2; } else { tc.gridI[ 2][1] = 3; } }
        if(tc.gridI[ 3][1] == 1) { if(Result_Triple(1)                           ) { tc.reward += 181; tc.gridI[ 3][1] = 2; } else { tc.gridI[ 3][1] = 3; } }
        if(tc.gridI[ 4][1] == 1) { if(Result_Triple(2)                           ) { tc.reward += 181; tc.gridI[ 4][1] = 2; } else { tc.gridI[ 4][1] = 3; } }
        if(tc.gridI[ 5][1] == 1) { if(Result_Triple(2)                           ) { tc.reward += 181; tc.gridI[ 5][1] = 2; } else { tc.gridI[ 5][1] = 3; } }
        if(tc.gridI[ 6][1] == 1) { if(Result_Triple(3)                           ) { tc.reward += 181; tc.gridI[ 6][1] = 2; } else { tc.gridI[ 6][1] = 3; } }
        if(tc.gridI[ 7][1] == 1) { if(Result_Triple(3)                           ) { tc.reward += 181; tc.gridI[ 7][1] = 2; } else { tc.gridI[ 7][1] = 3; } }
        if(tc.gridI[ 8][1] == 1) { if(Result_Triple(4)                           ) { tc.reward += 181; tc.gridI[ 8][1] = 2; } else { tc.gridI[ 8][1] = 3; } }
        if(tc.gridI[ 9][1] == 1) { if(Result_Triple(4)                           ) { tc.reward += 181; tc.gridI[ 9][1] = 2; } else { tc.gridI[ 9][1] = 3; } }
        if(tc.gridI[10][1] == 1) { if(Result_Triple(5)                           ) { tc.reward += 181; tc.gridI[10][1] = 2; } else { tc.gridI[10][1] = 3; } }
        if(tc.gridI[11][1] == 1) { if(Result_Triple(5)                           ) { tc.reward += 181; tc.gridI[11][1] = 2; } else { tc.gridI[11][1] = 3; } }

        if(tc.gridI[ 0][2] == 1) { if(Result_Value() ==  4                       ) { tc.reward +=  61; tc.gridI[ 0][2] = 2; } else { tc.gridI[ 0][2] = 3; } }
        if(tc.gridI[ 1][2] == 1) { if(Result_Value() ==  5                       ) { tc.reward +=  21; tc.gridI[ 1][2] = 2; } else { tc.gridI[ 1][2] = 3; } }
        if(tc.gridI[ 2][2] == 1) { if(Result_Value() ==  6                       ) { tc.reward +=  19; tc.gridI[ 2][2] = 2; } else { tc.gridI[ 2][2] = 3; } }
        if(tc.gridI[ 3][2] == 1) { if(Result_Value() ==  7                       ) { tc.reward +=  13; tc.gridI[ 3][2] = 2; } else { tc.gridI[ 3][2] = 3; } }
        if(tc.gridI[ 4][2] == 1) { if(Result_Value() ==  8                       ) { tc.reward +=   9; tc.gridI[ 4][2] = 2; } else { tc.gridI[ 4][2] = 3; } }
        if(tc.gridI[ 5][2] == 1) { if(Result_Double(0, 0)                        ) { tc.reward +=  12; tc.gridI[ 5][2] = 2; } else { tc.gridI[ 5][2] = 3; } }
        if(tc.gridI[ 6][2] == 1) { if(Result_Double(0, 1)                        ) { tc.reward +=   7; tc.gridI[ 6][2] = 2; } else { tc.gridI[ 6][2] = 3; } }
        if(tc.gridI[ 7][2] == 1) { if(Result_Double(0, 2)                        ) { tc.reward +=   7; tc.gridI[ 7][2] = 2; } else { tc.gridI[ 7][2] = 3; } }
        if(tc.gridI[ 8][2] == 1) { if(Result_Double(0, 3)                        ) { tc.reward +=   7; tc.gridI[ 8][2] = 2; } else { tc.gridI[ 8][2] = 3; } }
        if(tc.gridI[ 9][2] == 1) { if(Result_Double(0, 4)                        ) { tc.reward +=   7; tc.gridI[ 9][2] = 2; } else { tc.gridI[ 9][2] = 3; } }
        if(tc.gridI[10][2] == 1) { if(Result_Double(0, 5)                        ) { tc.reward +=   7; tc.gridI[10][2] = 2; } else { tc.gridI[10][2] = 3; } }
        if(tc.gridI[11][2] == 1) { if(Result_Double(1, 1)                        ) { tc.reward +=  12; tc.gridI[11][2] = 2; } else { tc.gridI[11][2] = 3; } }

        if(tc.gridI[ 0][3] == 1) { if(Result_Value() ==  9                       ) { tc.reward +=   7; tc.gridI[ 0][3] = 2; } else { tc.gridI[ 0][3] = 3; } }
        if(tc.gridI[ 1][3] == 1) { if(Result_Value() == 10                       ) { tc.reward +=   7; tc.gridI[ 1][3] = 2; } else { tc.gridI[ 1][3] = 3; } }
        if(tc.gridI[ 2][3] == 1) { if(Result_Value() == 11                       ) { tc.reward +=   7; tc.gridI[ 2][3] = 2; } else { tc.gridI[ 2][3] = 3; } }
        if(tc.gridI[ 3][3] == 1) { if(Result_Value() == 12                       ) { tc.reward +=   7; tc.gridI[ 3][3] = 2; } else { tc.gridI[ 3][3] = 3; } }
        if(tc.gridI[ 4][3] == 1) { if(Result_Value() == 13                       ) { tc.reward +=   7; tc.gridI[ 4][3] = 2; } else { tc.gridI[ 4][3] = 3; } }
        if(tc.gridI[ 5][3] == 1) { if(Result_Double(1, 2)                        ) { tc.reward +=   7; tc.gridI[ 5][3] = 2; } else { tc.gridI[ 5][3] = 3; } }
        if(tc.gridI[ 6][3] == 1) { if(Result_Double(1, 3)                        ) { tc.reward +=   7; tc.gridI[ 6][3] = 2; } else { tc.gridI[ 6][3] = 3; } }
        if(tc.gridI[ 7][3] == 1) { if(Result_Double(1, 4)                        ) { tc.reward +=   7; tc.gridI[ 7][3] = 2; } else { tc.gridI[ 7][3] = 3; } }
        if(tc.gridI[ 8][3] == 1) { if(Result_Double(1, 5)                        ) { tc.reward +=   7; tc.gridI[ 8][3] = 2; } else { tc.gridI[ 8][3] = 3; } }
        if(tc.gridI[ 9][3] == 1) { if(Result_Double(2, 2)                        ) { tc.reward +=  12; tc.gridI[ 9][3] = 2; } else { tc.gridI[ 9][3] = 3; } }
        if(tc.gridI[10][3] == 1) { if(Result_Double(2, 3)                        ) { tc.reward +=   7; tc.gridI[10][3] = 2; } else { tc.gridI[10][3] = 3; } }
        if(tc.gridI[11][3] == 1) { if(Result_Double(2, 4)                        ) { tc.reward +=   7; tc.gridI[11][3] = 2; } else { tc.gridI[11][3] = 3; } }

        if(tc.gridI[ 0][4] == 1) { if(Result_Value() == 14                       ) { tc.reward +=   7; tc.gridI[ 0][4] = 2; } else { tc.gridI[ 0][4] = 3; } }
        if(tc.gridI[ 1][4] == 1) { if(Result_Value() == 15                       ) { tc.reward +=   7; tc.gridI[ 1][4] = 2; } else { tc.gridI[ 1][4] = 3; } }
        if(tc.gridI[ 2][4] == 1) { if(Result_Value() == 16                       ) { tc.reward +=   7; tc.gridI[ 2][4] = 2; } else { tc.gridI[ 2][4] = 3; } }
        if(tc.gridI[ 3][4] == 1) { if(Result_Value() == 17                       ) { tc.reward +=   7; tc.gridI[ 3][4] = 2; } else { tc.gridI[ 3][4] = 3; } }
        if(tc.gridI[ 4][4] == 1) {                                                                                             tc.gridI[ 4][4] = 3;   }
        if(tc.gridI[ 5][4] == 1) { if(Result_Double(2, 5)                        ) { tc.reward +=   7; tc.gridI[ 5][4] = 2; } else { tc.gridI[ 5][4] = 3; } }
        if(tc.gridI[ 6][4] == 1) { if(Result_Double(3, 3)                        ) { tc.reward +=  12; tc.gridI[ 6][4] = 2; } else { tc.gridI[ 6][4] = 3; } }
        if(tc.gridI[ 7][4] == 1) { if(Result_Double(3, 4)                        ) { tc.reward +=   7; tc.gridI[ 7][4] = 2; } else { tc.gridI[ 7][4] = 3; } }
        if(tc.gridI[ 8][4] == 1) { if(Result_Double(3, 5)                        ) { tc.reward +=   7; tc.gridI[ 8][4] = 2; } else { tc.gridI[ 8][4] = 3; } }
        if(tc.gridI[ 9][4] == 1) { if(Result_Double(4, 4)                        ) { tc.reward +=  12; tc.gridI[ 9][4] = 2; } else { tc.gridI[ 9][4] = 3; } }
        if(tc.gridI[10][4] == 1) { if(Result_Double(4, 5)                        ) { tc.reward +=   7; tc.gridI[10][4] = 2; } else { tc.gridI[10][4] = 3; } }
        if(tc.gridI[11][4] == 1) { if(Result_Double(5, 5)                        ) { tc.reward +=  12; tc.gridI[11][4] = 2; } else { tc.gridI[11][4] = 3; } }

        if(tc.gridI[ 0][5] == 1) { if(Result_Triple(0)) { tc.reward += 6; tc.gridI[ 0][5] = 2; } else if(Result_Double(0, 0)) { tc.reward += 3; tc.gridI[ 0][5] = 2; } else if(Result_Single(0)) { tc.reward += 2; tc.gridI[ 0][5] = 2; } else { tc.gridI[ 0][5] = 3; } }
        if(tc.gridI[ 1][5] == 1) { if(Result_Triple(0)) { tc.reward += 6; tc.gridI[ 1][5] = 2; } else if(Result_Double(0, 0)) { tc.reward += 3; tc.gridI[ 1][5] = 2; } else if(Result_Single(0)) { tc.reward += 2; tc.gridI[ 1][5] = 2; } else { tc.gridI[ 1][5] = 3; } }
        if(tc.gridI[ 2][5] == 1) { if(Result_Triple(1)) { tc.reward += 6; tc.gridI[ 2][5] = 2; } else if(Result_Double(1, 1)) { tc.reward += 3; tc.gridI[ 2][5] = 2; } else if(Result_Single(1)) { tc.reward += 2; tc.gridI[ 2][5] = 2; } else { tc.gridI[ 2][5] = 3; } }
        if(tc.gridI[ 3][5] == 1) { if(Result_Triple(1)) { tc.reward += 6; tc.gridI[ 3][5] = 2; } else if(Result_Double(1, 1)) { tc.reward += 3; tc.gridI[ 3][5] = 2; } else if(Result_Single(1)) { tc.reward += 2; tc.gridI[ 3][5] = 2; } else { tc.gridI[ 3][5] = 3; } }
        if(tc.gridI[ 4][5] == 1) { if(Result_Triple(2)) { tc.reward += 6; tc.gridI[ 4][5] = 2; } else if(Result_Double(2, 2)) { tc.reward += 3; tc.gridI[ 4][5] = 2; } else if(Result_Single(2)) { tc.reward += 2; tc.gridI[ 4][5] = 2; } else { tc.gridI[ 4][5] = 3; } }
        if(tc.gridI[ 5][5] == 1) { if(Result_Triple(2)) { tc.reward += 6; tc.gridI[ 5][5] = 2; } else if(Result_Double(2, 2)) { tc.reward += 3; tc.gridI[ 5][5] = 2; } else if(Result_Single(2)) { tc.reward += 2; tc.gridI[ 5][5] = 2; } else { tc.gridI[ 5][5] = 3; } }
        if(tc.gridI[ 6][5] == 1) { if(Result_Triple(3)) { tc.reward += 6; tc.gridI[ 6][5] = 2; } else if(Result_Double(3, 3)) { tc.reward += 3; tc.gridI[ 6][5] = 2; } else if(Result_Single(3)) { tc.reward += 2; tc.gridI[ 6][5] = 2; } else { tc.gridI[ 6][5] = 3; } }
        if(tc.gridI[ 7][5] == 1) { if(Result_Triple(3)) { tc.reward += 6; tc.gridI[ 7][5] = 2; } else if(Result_Double(3, 3)) { tc.reward += 3; tc.gridI[ 7][5] = 2; } else if(Result_Single(3)) { tc.reward += 2; tc.gridI[ 7][5] = 2; } else { tc.gridI[ 7][5] = 3; } }
        if(tc.gridI[ 8][5] == 1) { if(Result_Triple(4)) { tc.reward += 6; tc.gridI[ 8][5] = 2; } else if(Result_Double(4, 4)) { tc.reward += 3; tc.gridI[ 8][5] = 2; } else if(Result_Single(4)) { tc.reward += 2; tc.gridI[ 8][5] = 2; } else { tc.gridI[ 8][5] = 3; } }
        if(tc.gridI[ 9][5] == 1) { if(Result_Triple(4)) { tc.reward += 6; tc.gridI[ 9][5] = 2; } else if(Result_Double(4, 4)) { tc.reward += 3; tc.gridI[ 9][5] = 2; } else if(Result_Single(4)) { tc.reward += 2; tc.gridI[ 9][5] = 2; } else { tc.gridI[ 9][5] = 3; } }
        if(tc.gridI[10][5] == 1) { if(Result_Triple(5)) { tc.reward += 6; tc.gridI[10][5] = 2; } else if(Result_Double(5, 5)) { tc.reward += 3; tc.gridI[10][5] = 2; } else if(Result_Single(5)) { tc.reward += 2; tc.gridI[10][5] = 2; } else { tc.gridI[10][5] = 3; } }
        if(tc.gridI[11][5] == 1) { if(Result_Triple(5)) { tc.reward += 6; tc.gridI[11][5] = 2; } else if(Result_Double(5, 5)) { tc.reward += 3; tc.gridI[11][5] = 2; } else if(Result_Single(5)) { tc.reward += 2; tc.gridI[11][5] = 2; } else { tc.gridI[11][5] = 3; } }

        tc.turnstate = 4;
    }

    private int Result_Value() {
        return dice[0].number + dice[1].number + dice[2].number + 3;
    }

    private boolean Result_Single(int n) {
        if(dice[0].number == n) return true;
        if(dice[1].number == n) return true;
        return dice[2].number == n;
    }

    private boolean Result_Double(int n1, int n2) {
        return Result_Single(n1) && Result_Single(n2);
    }

    private boolean Result_Triple(int n) {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number && dice[0].number == n;
    }

    private boolean Result_AnyTriple() {
        return dice[0].number == dice[1].number && dice[0].number == dice[2].number;
    }

}
