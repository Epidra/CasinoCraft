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

public class GuiCraps extends GuiCasino {


    int diceColor = 0;

    //--------------------CONSTRUCTOR--------------------

    public GuiCraps(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_CRAPS);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
        tc.gridI = new int[21][5];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseButton == 0) {
            for(int y = 0; y < 5; y++) {
                for(int x = 0; x < 21; x++) {
                    if(mouseRect(-50 + 16*x, 49 + 32*y, 16, 30, mouseX, mouseY)){
                        if(tc.selector.matches(x, y) && getValue(x + y*21) == 0) {
                            if(playerToken >= tc.getBetLow()) {
                                actionTouch(-1);
                                CollectBet();
                                playerToken = -1;
                            } else {
                                actionTouch(-2);
                            }
                        } else {
                            actionTouch(x + y*21);
                        }
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_ENTER){ actionTouch(-2); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 2)    { this.fontRenderer.drawString(getString(0),        21, 29, 0); }
        if(tc.turnstate >= 2)    { this.fontRenderer.drawString(getString(0),        20, 28, 16777215); }
        if(getValue(-1) > -1) { this.fontRenderer.drawString("" + getValue(-1),  201, 29, 0); }
        if(getValue(-1) > -1) { this.fontRenderer.drawString("" + getValue(-1),  200, 28, 16777215); }
        if(getValue(-2) > -1) { this.fontRenderer.drawString("" + getValue(-2),  221, 29, 0); }
        if(getValue(-2) > -1) { this.fontRenderer.drawString("" + getValue(-2),  220, 28, 16777215); }
        if(getValue(-3) > -1) { this.fontRenderer.drawString("" + getValue(-3),  241, 29, 0); }
        if(getValue(-3) > -1) { this.fontRenderer.drawString("" + getValue(-3),  240, 28, 16777215); }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_LEFT);
        this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_RIGHT);
        this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);
        if(tc.turnstate >= 2){
            int color = 0;
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 21; x++){
                    color = getValue(x + y*21);
                    if(color != 0) this.drawTexturedModalRect(guiLeft+-50 + 16*x, guiTop+49 + 32*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                }
            }
        }
        if(tc.selector.X > -1) this.drawTexturedModalRect(guiLeft+-50 + 16*tc.selector.X, guiTop+49 + 32*tc.selector.Y, 192, 0, 32, 32);

        if(tc.turnstate == 3){
            this.drawTexturedModalRect(guiLeft + getDice(0).posX, guiTop + getDice(0).posY, getDice(0).number*32, diceColor*32, 32, 32);
            this.drawTexturedModalRect(guiLeft + getDice(1).posX, guiTop + getDice(1).posY, getDice(1).number*32, diceColor*32, 32, 32);
        }
    }



    //--------------------CUSTOM--------------------



    Dice[] dice = new Dice[2];

    int result;
    int point;
    int comepoint;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityCraps(){
   //    super(null, null);
   //}

   //public TileEntityCraps(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new int[21][5];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_CRAPS.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_CRAPS.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_CRAPS.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        tc.hand = "Place your Bets..";
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x < 21; x++) {
                tc.gridI[x][y] = 0;
            }
        }
        tc.selector = new Vector2(10, 2);
        result = -1;
        point  = -1;
        comepoint = -1;
        dice[0] = new Dice(0, 1);
        dice[1] = new Dice(0, 1);
    }

    public void actionTouch(int action){
        if(action == -2) {
            if(tc.selector.X > -1 && GridValid(tc.selector)){
                tc.gridI[tc.selector.X][tc.selector.Y] = 1;
                tc.selector.set(-1, -1);
            }
            Spin();
        }
        if(action == -1){
            if(tc.selector.X > -1 && GridValid(tc.selector)){
                tc.gridI[tc.selector.X][tc.selector.Y] = 1;
                tc.selector.set(-1, -1);
            }
        } else {
            tc.selector = new Vector2(action%21, action/21);
        }
    }

    public void update(){
        if(tc.turnstate == 3) {
            for(int i = 0; i < 2; i++) {
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

    public int getValue(int index){
        if(index == -1) return result;
        if(index == -2) return point;
        if(index == -3) return comepoint;
        if(index == -4) return tc.reward;
        return tc.gridI[index%21][index/21];
    }

    public Dice getDice(int index){
        return dice[index];
    }

    public Vector2 getVector(int index){
        return tc.selector;
    }

    public boolean getFlag(int index){
        return false;
    }

    public String getString(int index){
        return tc.hand;
    }



    //--------------------CUSTOM--------------------

    private boolean GridValid(Vector2 v) {
        if(v == new Vector2( 1, 0)) return false;
        if(v == new Vector2(19, 0)) return false;
        if(v == new Vector2( 1, 4)) return false;
        if(v == new Vector2(19, 4)) return false;
        if(v.Y >= 1 && v.Y <= 3) {
            return v.X % 3 != 1;
        }
        return true;
    }

    private void Spin() {
        if(tc.turnstate == 2) {
            dice[0].Set_Up(200 + tc.rand.nextInt( 50),  50 + tc.rand.nextInt(200), tc.rand.nextInt(2) == 0);
            dice[1].Set_Up( 50 + tc.rand.nextInt(200), 200 + tc.rand.nextInt( 50), tc.rand.nextInt(2) == 0);
            if(tc.selector.X > -1) {
                //if(point != -1 && gridI[(int)selector.X][(int)selector.Y] == 0) {
                //FM.coins       -= bet;
                //FM.coins_bonus -= bet;
                //}
                tc.gridI[tc.selector.X][tc.selector.Y] = 1;
                tc.selector = new Vector2(-1, -1);
            }
            tc.turnstate = 3;
        } else if(tc.turnstate == 3) {
            if(dice[0].shiftX == 0 && dice[1].shiftX == 0) {
                Result();
            }
        }
    }

    private void Result() {
        dice[0].Reset();
        dice[1].Reset();
        boolean hasCome = false;
        for(int i = 0; i < 5; i++) {
            if(tc.gridI[ 0][i] == 1) hasCome = true;
            if(tc.gridI[20][i] == 1) hasCome = true;
        }
        if(point == -1) {
            point  = dice[0].number + 1 + dice[1].number + 1;
            result = dice[0].number + 1 + dice[1].number + 1;
            if(result == 7 || result == 11) { // PASS
                Result_Pass(true);
                Result_DontPass(false);
                tc.hand = "Natura Roll!";
                tc.turnstate = 4;
            } else if(result == 2 || result == 3 || result == 12) { // DON'T PASS
                Result_Pass(false);
                Result_DontPass(true);
                tc.hand = "Crab...";
                tc.turnstate = 4;
            } else {
                tc.hand = "Roll again...";
                tc.turnstate = 2;
            }
        } else {
            result = dice[0].number + 1 + dice[1].number + 1;
            Result_SingleOdds();
            tc.turnstate = 2;
            if(hasCome) {
                if(comepoint == -1) {
                    comepoint = result;
                } else {
                    if(result == comepoint) {
                        Result_Come(true);
                        Result_DontCome(false);
                    } else if(result == 7) {
                        Result_Come(false);
                        Result_DontCome(true);
                    }
                }
            }
            if(result == 7) { // DON'T PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                tc.hand = "SEVEN";
                tc.turnstate = 4;
            } else if(result == point) { // PASS
                Result_Pass(false);
                Result_PassOdds(false);
                Result_DontPass(true);
                Result_DontPassOdds(true);
                tc.hand = "POINT";
                tc.turnstate = 4;
            }
        }
    }

    private void Result_Come(boolean won) {
        if(tc.gridI[0][0] == 1) { if(won) { tc.gridI[0][0] = 2; tc.reward += 2; } else { tc.gridI[0][0] = 3; } }
        if(tc.gridI[0][1] == 1) { if(won) { tc.gridI[0][1] = 2; tc.reward += 2; } else { tc.gridI[0][1] = 3; } }
        if(tc.gridI[0][2] == 1) { if(won) { tc.gridI[0][2] = 2; tc.reward += 2; } else { tc.gridI[0][2] = 3; } }
        if(tc.gridI[0][3] == 1) { if(won) { tc.gridI[0][3] = 2; tc.reward += 2; } else { tc.gridI[0][3] = 3; } }
        if(tc.gridI[0][4] == 1) { if(won) { tc.gridI[0][4] = 2; tc.reward += 2; } else { tc.gridI[0][4] = 3; } }
    }

    private void Result_DontCome(boolean won) {
        if(tc.gridI[20][0] == 1) { if(won) { tc.gridI[20][0] = 2; tc.reward += 2; } else { tc.gridI[20][0] = 3; } }
        if(tc.gridI[20][1] == 1) { if(won) { tc.gridI[20][1] = 2; tc.reward += 2; } else { tc.gridI[20][1] = 3; } }
        if(tc.gridI[20][2] == 1) { if(won) { tc.gridI[20][2] = 2; tc.reward += 2; } else { tc.gridI[20][2] = 3; } }
        if(tc.gridI[20][3] == 1) { if(won) { tc.gridI[20][3] = 2; tc.reward += 2; } else { tc.gridI[20][3] = 3; } }
        if(tc.gridI[20][4] == 1) { if(won) { tc.gridI[20][4] = 2; tc.reward += 2; } else { tc.gridI[20][4] = 3; } }
    }

    private void Result_Pass(boolean won) {
        if(tc.gridI[ 2][0] == 1) { if(won) { tc.gridI[ 2][0] = 2; tc.reward += 2; } else { tc.gridI[ 2][0] = 3; } }
        if(tc.gridI[ 3][0] == 1) { if(won) { tc.gridI[ 3][0] = 2; tc.reward += 2; } else { tc.gridI[ 3][0] = 3; } }
        if(tc.gridI[ 4][0] == 1) { if(won) { tc.gridI[ 4][0] = 2; tc.reward += 2; } else { tc.gridI[ 4][0] = 3; } }
        if(tc.gridI[ 5][0] == 1) { if(won) { tc.gridI[ 5][0] = 2; tc.reward += 2; } else { tc.gridI[ 5][0] = 3; } }
        if(tc.gridI[ 6][0] == 1) { if(won) { tc.gridI[ 6][0] = 2; tc.reward += 2; } else { tc.gridI[ 6][0] = 3; } }
        if(tc.gridI[ 7][0] == 1) { if(won) { tc.gridI[ 7][0] = 2; tc.reward += 2; } else { tc.gridI[ 7][0] = 3; } }
        if(tc.gridI[ 8][0] == 1) { if(won) { tc.gridI[ 8][0] = 2; tc.reward += 2; } else { tc.gridI[ 8][0] = 3; } }
        if(tc.gridI[ 9][0] == 1) { if(won) { tc.gridI[ 9][0] = 2; tc.reward += 2; } else { tc.gridI[ 9][0] = 3; } }
        if(tc.gridI[10][0] == 1) { if(won) { tc.gridI[10][0] = 2; tc.reward += 2; } else { tc.gridI[10][0] = 3; } }
        if(tc.gridI[11][0] == 1) { if(won) { tc.gridI[11][0] = 2; tc.reward += 2; } else { tc.gridI[11][0] = 3; } }
        if(tc.gridI[12][0] == 1) { if(won) { tc.gridI[12][0] = 2; tc.reward += 2; } else { tc.gridI[12][0] = 3; } }
        if(tc.gridI[13][0] == 1) { if(won) { tc.gridI[13][0] = 2; tc.reward += 2; } else { tc.gridI[13][0] = 3; } }
        if(tc.gridI[14][0] == 1) { if(won) { tc.gridI[14][0] = 2; tc.reward += 2; } else { tc.gridI[14][0] = 3; } }
        if(tc.gridI[15][0] == 1) { if(won) { tc.gridI[15][0] = 2; tc.reward += 2; } else { tc.gridI[15][0] = 3; } }
        if(tc.gridI[16][0] == 1) { if(won) { tc.gridI[16][0] = 2; tc.reward += 2; } else { tc.gridI[16][0] = 3; } }
        if(tc.gridI[17][0] == 1) { if(won) { tc.gridI[17][0] = 2; tc.reward += 2; } else { tc.gridI[17][0] = 3; } }
        if(tc.gridI[18][0] == 1) { if(won) { tc.gridI[18][0] = 2; tc.reward += 2; } else { tc.gridI[18][0] = 3; } }
    }

    private void Result_DontPass(boolean won) {
        if(tc.gridI[ 2][4] == 1) { if(won) { tc.gridI[ 2][4] = 2; tc.reward += 2; } else { tc.gridI[ 2][4] = 3; } }
        if(tc.gridI[ 3][4] == 1) { if(won) { tc.gridI[ 3][4] = 2; tc.reward += 2; } else { tc.gridI[ 3][4] = 3; } }
        if(tc.gridI[ 4][4] == 1) { if(won) { tc.gridI[ 4][4] = 2; tc.reward += 2; } else { tc.gridI[ 4][4] = 3; } }
        if(tc.gridI[ 5][4] == 1) { if(won) { tc.gridI[ 5][4] = 2; tc.reward += 2; } else { tc.gridI[ 5][4] = 3; } }
        if(tc.gridI[ 6][4] == 1) { if(won) { tc.gridI[ 6][4] = 2; tc.reward += 2; } else { tc.gridI[ 6][4] = 3; } }
        if(tc.gridI[ 7][4] == 1) { if(won) { tc.gridI[ 7][4] = 2; tc.reward += 2; } else { tc.gridI[ 7][4] = 3; } }
        if(tc.gridI[ 8][4] == 1) { if(won) { tc.gridI[ 8][4] = 2; tc.reward += 2; } else { tc.gridI[ 8][4] = 3; } }
        if(tc.gridI[ 9][4] == 1) { if(won) { tc.gridI[ 9][4] = 2; tc.reward += 2; } else { tc.gridI[ 9][4] = 3; } }
        if(tc.gridI[10][4] == 1) { if(won) { tc.gridI[10][4] = 2; tc.reward += 2; } else { tc.gridI[10][4] = 3; } }
        if(tc.gridI[11][4] == 1) { if(won) { tc.gridI[11][4] = 2; tc.reward += 2; } else { tc.gridI[11][4] = 3; } }
        if(tc.gridI[12][4] == 1) { if(won) { tc.gridI[12][4] = 2; tc.reward += 2; } else { tc.gridI[12][4] = 3; } }
        if(tc.gridI[13][4] == 1) { if(won) { tc.gridI[13][4] = 2; tc.reward += 2; } else { tc.gridI[13][4] = 3; } }
        if(tc.gridI[14][4] == 1) { if(won) { tc.gridI[14][4] = 2; tc.reward += 2; } else { tc.gridI[14][4] = 3; } }
        if(tc.gridI[15][4] == 1) { if(won) { tc.gridI[15][4] = 2; tc.reward += 2; } else { tc.gridI[15][4] = 3; } }
        if(tc.gridI[16][4] == 1) { if(won) { tc.gridI[16][4] = 2; tc.reward += 2; } else { tc.gridI[16][4] = 3; } }
        if(tc.gridI[17][4] == 1) { if(won) { tc.gridI[17][4] = 2; tc.reward += 2; } else { tc.gridI[17][4] = 3; } }
        if(tc.gridI[18][4] == 1) { if(won) { tc.gridI[18][4] = 2; tc.reward += 2; } else { tc.gridI[18][4] = 3; } }
    }

    private void Result_PassOdds(boolean won) {
        if(tc.gridI[ 2][1] == 1) { if(won) { if(result ==  4) { tc.gridI[ 2][1] = 2; tc.reward += 2; } else { tc.gridI[ 2][1] = 3; } } else { tc.gridI[ 2][1] = 3; } }
        if(tc.gridI[ 3][1] == 1) { if(won) { if(result ==  4) { tc.gridI[ 3][1] = 2; tc.reward += 2; } else { tc.gridI[ 3][1] = 3; } } else { tc.gridI[ 3][1] = 3; } }
        if(tc.gridI[ 5][1] == 1) { if(won) { if(result ==  5) { tc.gridI[ 5][1] = 2; tc.reward += 2; } else { tc.gridI[ 5][1] = 3; } } else { tc.gridI[ 5][1] = 3; } }
        if(tc.gridI[ 6][1] == 1) { if(won) { if(result ==  5) { tc.gridI[ 6][1] = 2; tc.reward += 2; } else { tc.gridI[ 6][1] = 3; } } else { tc.gridI[ 6][1] = 3; } }
        if(tc.gridI[ 8][1] == 1) { if(won) { if(result ==  6) { tc.gridI[ 8][1] = 2; tc.reward += 2; } else { tc.gridI[ 8][1] = 3; } } else { tc.gridI[ 8][1] = 3; } }
        if(tc.gridI[ 9][1] == 1) { if(won) { if(result ==  6) { tc.gridI[ 9][1] = 2; tc.reward += 2; } else { tc.gridI[ 9][1] = 3; } } else { tc.gridI[ 9][1] = 3; } }
        if(tc.gridI[11][1] == 1) { if(won) { if(result ==  8) { tc.gridI[11][1] = 2; tc.reward += 2; } else { tc.gridI[11][1] = 3; } } else { tc.gridI[11][1] = 3; } }
        if(tc.gridI[12][1] == 1) { if(won) { if(result ==  8) { tc.gridI[12][1] = 2; tc.reward += 2; } else { tc.gridI[12][1] = 3; } } else { tc.gridI[12][1] = 3; } }
        if(tc.gridI[14][1] == 1) { if(won) { if(result ==  9) { tc.gridI[14][1] = 2; tc.reward += 2; } else { tc.gridI[14][1] = 3; } } else { tc.gridI[14][1] = 3; } }
        if(tc.gridI[15][1] == 1) { if(won) { if(result ==  9) { tc.gridI[15][1] = 2; tc.reward += 2; } else { tc.gridI[15][1] = 3; } } else { tc.gridI[15][1] = 3; } }
        if(tc.gridI[17][1] == 1) { if(won) { if(result == 10) { tc.gridI[17][1] = 2; tc.reward += 2; } else { tc.gridI[17][1] = 3; } } else { tc.gridI[17][1] = 3; } }
        if(tc.gridI[18][1] == 1) { if(won) { if(result == 10) { tc.gridI[18][1] = 2; tc.reward += 2; } else { tc.gridI[18][1] = 3; } } else { tc.gridI[18][1] = 3; } }
    }

    private void Result_DontPassOdds(boolean won) {
        if(tc.gridI[ 2][3] == 1) { if(won) { if(result ==  4) { tc.gridI[ 2][3] = 2; tc.reward += 2; } else { tc.gridI[ 2][3] = 3; } } else { tc.gridI[ 2][3] = 3; } }
        if(tc.gridI[ 3][3] == 1) { if(won) { if(result ==  4) { tc.gridI[ 3][3] = 2; tc.reward += 2; } else { tc.gridI[ 3][3] = 3; } } else { tc.gridI[ 3][3] = 3; } }
        if(tc.gridI[ 5][3] == 1) { if(won) { if(result ==  5) { tc.gridI[ 5][3] = 2; tc.reward += 2; } else { tc.gridI[ 5][3] = 3; } } else { tc.gridI[ 5][3] = 3; } }
        if(tc.gridI[ 6][3] == 1) { if(won) { if(result ==  5) { tc.gridI[ 6][3] = 2; tc.reward += 2; } else { tc.gridI[ 6][3] = 3; } } else { tc.gridI[ 6][3] = 3; } }
        if(tc.gridI[ 8][3] == 1) { if(won) { if(result ==  6) { tc.gridI[ 8][3] = 2; tc.reward += 2; } else { tc.gridI[ 8][3] = 3; } } else { tc.gridI[ 8][3] = 3; } }
        if(tc.gridI[ 9][3] == 1) { if(won) { if(result ==  6) { tc.gridI[ 9][3] = 2; tc.reward += 2; } else { tc.gridI[ 9][3] = 3; } } else { tc.gridI[ 9][3] = 3; } }
        if(tc.gridI[11][3] == 1) { if(won) { if(result ==  8) { tc.gridI[11][3] = 2; tc.reward += 2; } else { tc.gridI[11][3] = 3; } } else { tc.gridI[11][3] = 3; } }
        if(tc.gridI[12][3] == 1) { if(won) { if(result ==  8) { tc.gridI[12][3] = 2; tc.reward += 2; } else { tc.gridI[12][3] = 3; } } else { tc.gridI[12][3] = 3; } }
        if(tc.gridI[14][3] == 1) { if(won) { if(result ==  9) { tc.gridI[14][3] = 2; tc.reward += 2; } else { tc.gridI[14][3] = 3; } } else { tc.gridI[14][3] = 3; } }
        if(tc.gridI[15][3] == 1) { if(won) { if(result ==  9) { tc.gridI[15][3] = 2; tc.reward += 2; } else { tc.gridI[15][3] = 3; } } else { tc.gridI[15][3] = 3; } }
        if(tc.gridI[17][3] == 1) { if(won) { if(result == 10) { tc.gridI[17][3] = 2; tc.reward += 2; } else { tc.gridI[17][3] = 3; } } else { tc.gridI[17][3] = 3; } }
        if(tc.gridI[18][3] == 1) { if(won) { if(result == 10) { tc.gridI[18][3] = 2; tc.reward += 2; } else { tc.gridI[18][3] = 3; } } else { tc.gridI[18][3] = 3; } }
    }

    private void Result_SingleOdds() {
        if(tc.gridI[ 2][2] == 1) { if(result ==  4) { tc.gridI[ 2][2] = 2; tc.reward += 10; } else { tc.gridI[ 2][2] = 3; } }
        if(tc.gridI[ 3][2] == 1) { if(result ==  4) { tc.gridI[ 3][2] = 2; tc.reward += 10; } else { tc.gridI[ 3][2] = 3; } }
        if(tc.gridI[ 5][2] == 1) { if(result ==  5) { tc.gridI[ 5][2] = 2; tc.reward +=  8; } else { tc.gridI[ 5][2] = 3; } }
        if(tc.gridI[ 6][2] == 1) { if(result ==  5) { tc.gridI[ 6][2] = 2; tc.reward +=  8; } else { tc.gridI[ 6][2] = 3; } }
        if(tc.gridI[ 8][2] == 1) { if(result ==  6) { tc.gridI[ 8][2] = 2; tc.reward +=  6; } else { tc.gridI[ 8][2] = 3; } }
        if(tc.gridI[ 9][2] == 1) { if(result ==  6) { tc.gridI[ 9][2] = 2; tc.reward +=  6; } else { tc.gridI[ 9][2] = 3; } }
        if(tc.gridI[11][2] == 1) { if(result ==  8) { tc.gridI[11][2] = 2; tc.reward +=  6; } else { tc.gridI[11][2] = 3; } }
        if(tc.gridI[12][2] == 1) { if(result ==  8) { tc.gridI[12][2] = 2; tc.reward +=  6; } else { tc.gridI[12][2] = 3; } }
        if(tc.gridI[14][2] == 1) { if(result ==  9) { tc.gridI[14][2] = 2; tc.reward +=  8; } else { tc.gridI[14][2] = 3; } }
        if(tc.gridI[15][2] == 1) { if(result ==  9) { tc.gridI[15][2] = 2; tc.reward +=  8; } else { tc.gridI[15][2] = 3; } }
        if(tc.gridI[17][2] == 1) { if(result == 10) { tc.gridI[17][2] = 2; tc.reward += 10; } else { tc.gridI[17][2] = 3; } }
        if(tc.gridI[18][2] == 1) { if(result == 10) { tc.gridI[18][2] = 2; tc.reward += 10; } else { tc.gridI[18][2] = 3; } }
    }

}
