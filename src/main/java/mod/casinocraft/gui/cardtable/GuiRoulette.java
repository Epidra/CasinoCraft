package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
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

public class GuiRoulette extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiRoulette(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_ROULETTE);
        tc.gridI = new int[25][7];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(tc.turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 25; x++){
                    if(mouseRect(-128+49 + 16*x, 26 + 24*y, 16, 24, mouseX, mouseY)){
                        if(tc.selector.matches(x, y) && getValue(x + y*12) == 0) {
                            if(playerToken >= tc.getBetLow()) {
                                actionTouch(-1);
                                CollectBet();
                                playerToken = -1;
                            } else {
                                actionTouch(-2);
                            }
                        } else {
                            actionTouch(x + y*25);
                        }
                    }
                }
            }
            for(int x = 0; x < 25; x++){
                if(mouseRect(-128+49 + 16*x, 161, 16, 24, mouseX, mouseY)){
                    if(tc.selector.matches(x, 5) && getValue(x + 5*12) == 0) {
                        if(playerToken >= tc.getBetLow()) {
                            actionTouch(-1);
                            CollectBet();
                            playerToken = -1;
                        } else {
                            actionTouch(-2);
                        }
                    } else {
                        actionTouch(x + 5*25);
                    }
                }
                if(mouseRect(-128+49 + 16*x, 193, 16, 24, mouseX, mouseY)){
                    if(tc.selector.matches(x, 6) && getValue(x + 6*12) == 0) {
                        if(playerToken >= tc.getBetLow()) {
                            actionTouch(-1);
                            CollectBet();
                            playerToken = -1;
                        } else {
                            actionTouch(-2);
                        }
                    } else {
                        actionTouch(x + 6*25);
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_ENTER){ actionTouch(-2); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 4){
            this.fontRenderer.drawString("" + getValue(-1),  226, -14, 0);
            this.fontRenderer.drawString("" + getValue(-1),  225, -15, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_LEFT);
        this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_RIGHT);
        this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);

        if(tc.turnstate >= 2){
            int color = 0;
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 25; x++){
                    color = getValue(x + y*25);
                    if(color != 0)
                        this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                    if(tc.selector.matches(x, y)) this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192,   0, 32, 32);
                }
            }
            for(int x = 0; x < 25; x++){
                color = getValue(x + 5*25);
                if(getValue(x + 5*25) != 0) this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+161, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = getValue(x + 6*25);
                if(getValue(x + 6*25) != 0) this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+193, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                if(tc.selector.matches(x, 5))   this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+161, 192,   0, 32, 32);
                if(tc.selector.matches(x, 6))   this.drawTexturedModalRect(guiLeft+-128+49 + 16*x, guiTop+193, 192,   0, 32, 32);
            }
        }

        if(tc.turnstate == 3){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_WHEEL);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            Vector2 v = getVector(1);
            this.drawTexturedModalRect(guiLeft + v.X, guiTop + v.Y, 128, 66, 16, 16);
        }
    }



    //--------------------CUSTOM--------------------




    float rotation_wheel;
    float rotation_ball;

    boolean spinning;
    int result;
    int timer;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityRoulette(){
   //    super(null, null);
   //}

   //public TileEntityRoulette(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new int[25][7];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_ROULETTE.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_ROULETTE.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_ROULETTE.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 7; y++) {
            for(int x = 0; x < 25; x++) {
                tc.gridI[x][y] = 0;
            }
        }
        tc.selector = new Vector2(-1, -1);
        rotation_wheel = 0.00f;
        rotation_ball = 0.00f;
        spinning = false;
        result = 0;
        timer = -1;
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
            tc.selector.set(action%25, action/25);
        }
    }

    public void update(){
        if(tc.turnstate == 3) {
            if(timer > 0) {
                if(timer > 20) {
                    //rotation_wheel += (timer / 5000f);
                    rotation_ball  += (timer / 1000f);
                }
                timer--;
            }
        }
        if(tc.turnstate == 4) {
            tc.selector = new Vector2(-1, -1);
            //GameOver();
        }
    }



    //--------------------GETTER--------------------

    public Vector2 getVector(int index){
        if(index == 1) return vectorWheel();
        return tc.selector;
    }

    public int getValue(int index){
        if(index == -1) return result;
        if(index == -2) return tc.reward;
        return tc.gridI[index%25][index/25];
    }

    public boolean getFlag(int index){
        return false;
    }

    public String getString(int index){
        return "";
    }



    //--------------------CUSTOM--------------------

    private Vector2 vectorWheel(){
        // 1 Number == 0,1698... Radian
        int n = 0;

        float rotation = (rotation_wheel + rotation_ball + 0.085f)%(0.1698f * 37);
        for(int i = 0; i < 37; i++) {
            if(0.17 * i < rotation && rotation < 0.17 * i + 0.17) {
                n = i;
            }
        }
        switch(n){
            case  0: return new Vector2(124,  31);
            case  1: return new Vector2(140,  34);
            case  2: return new Vector2(154,  37);
            case  3: return new Vector2(167,  45);
            case  4: return new Vector2(180,  54);
            case  5: return new Vector2(190,  64);
            case  6: return new Vector2(199,  78);
            case  7: return new Vector2(204,  91);
            case  8: return new Vector2(208, 106);
            case  9: return new Vector2(209, 121);
            case 10: return new Vector2(208, 136);
            case 11: return new Vector2(204, 150);
            case 12: return new Vector2(198, 164);
            case 13: return new Vector2(189, 177);
            case 14: return new Vector2(178, 187);
            case 15: return new Vector2(166, 197);
            case 16: return new Vector2(152, 203);
            case 17: return new Vector2(138, 207);
            case 18: return new Vector2(123, 209);
            case 19: return new Vector2(107, 208);
            case 20: return new Vector2( 93, 205);
            case 21: return new Vector2( 79, 199);
            case 22: return new Vector2( 66, 191);
            case 23: return new Vector2( 55, 181);
            case 24: return new Vector2( 45, 169);
            case 25: return new Vector2( 38, 156);
            case 26: return new Vector2( 33, 142);
            case 27: return new Vector2( 31, 127);
            case 28: return new Vector2( 32, 122);
            case 29: return new Vector2( 35,  97);
            case 30: return new Vector2( 40,  84);
            case 31: return new Vector2( 47,  70);
            case 32: return new Vector2( 57,  58);
            case 33: return new Vector2( 68,  49);
            case 34: return new Vector2( 81,  40);
            case 35: return new Vector2( 95,  35);
            case 36: return new Vector2(110,  32);
        }
        return new Vector2(0, 0);
    }

    private boolean GridValid(Vector2 v) {
        if(v.X == 23) return false;
        if(v == new Vector2(24, 1)) return false;
        if(v == new Vector2(24, 3)) return false;
        if(v == new Vector2( 7, 5)) return false;
        if(v == new Vector2( 7, 6)) return false;
        if(v == new Vector2(15, 5)) return false;
        if(v == new Vector2(15, 6)) return false;
        if(v == new Vector2( 3, 6)) return false;
        if(v == new Vector2(11, 6)) return false;
        return v != new Vector2(19, 6);
    }

    private void Place() { // Unused
        //if(tc.selector != tc.selector) {
        //    if(GridValid(tc.selector) && tc.gridI[(int)tc.selector.X][(int)tc.selector.Y] == 0) tc.selector = tc.selector;
        //} else {
        tc.gridI[tc.selector.X][tc.selector.Y] = 1;
        tc.selector = new Vector2(-1, -1);
        //if(FM.coins >= bet) {
        //    FM.coins -= bet;
        //    FM.coins_bonus -= bet;
        //} else {
        //    Spin();
        // }
        //}
    }

    private void Spin() {
        if(tc.turnstate == 2 && !spinning) {
            if(tc.selector.X != -1) tc.gridI[tc.selector.X][tc.selector.Y] = 1;
            tc.turnstate = 3;
        } else if(tc.turnstate == 3 && !spinning) {
            timer = 100 + tc.rand.nextInt(500);
            spinning = true;
        } else if(tc.turnstate == 3 && timer == 0) {
            Result();
        }
    }

    public void Result() {
        // 1 Number == 0,1698... Radian
        int n = 0;

        float rotation = rotation_wheel + rotation_ball + 0.085f;
        while(rotation > 0.1698 * 37) {
            rotation = rotation - (0.1698f * 37);
        }

        for(int i = 0; i < 37; i++) {
            if(0.17 * i < rotation && rotation < 0.17 * i + 0.17) {
                n = i;
            }
        }

        if(n ==  0) result = 36;
        if(n ==  1) result = 11; if(n ==  2) result = 30; if(n ==  3) result =  8; if(n ==  4) result = 23; if(n ==  5) result = 10; if(n ==  6) result =  5;
        if(n ==  7) result = 24; if(n ==  8) result = 16; if(n ==  9) result = 33; if(n == 10) result =  1; if(n == 11) result = 20; if(n == 12) result = 14;
        if(n == 13) result = 31; if(n == 14) result =  9; if(n == 15) result = 22; if(n == 16) result = 18; if(n == 17) result = 29; if(n == 18) result =  7;
        if(n == 19) result = 28; if(n == 20) result = 12; if(n == 21) result = 35; if(n == 22) result =  3; if(n == 23) result = 26; if(n == 24) result =  0;
        if(n == 25) result = 32; if(n == 26) result = 15; if(n == 27) result = 19; if(n == 28) result =  4; if(n == 29) result = 21; if(n == 30) result =  2;
        if(n == 31) result = 25; if(n == 32) result = 17; if(n == 33) result = 34; if(n == 34) result =  6; if(n == 35) result = 27; if(n == 36) result = 13;

        Set_Result( 0, 4, 1, 36); Set_Result( 0, 2, 1, 36); Set_Result( 0, 0, 1, 36); // Direct Bet:  1 -  2 -  3
        Set_Result( 2, 4, 1, 36); Set_Result( 2, 2, 1, 36); Set_Result( 2, 0, 1, 36); // Direct Bet:  4 -  5 -  6
        Set_Result( 4, 4, 1, 36); Set_Result( 4, 2, 1, 36); Set_Result( 4, 0, 1, 36); // Direct Bet:  7 -  8 -  9
        Set_Result( 6, 4, 1, 36); Set_Result( 6, 2, 1, 36); Set_Result( 6, 0, 1, 36); // Direct Bet: 10 - 11 - 12
        Set_Result( 8, 4, 1, 36); Set_Result( 8, 2, 1, 36); Set_Result( 8, 0, 1, 36); // Direct Bet: 13 - 14 - 15
        Set_Result(10, 4, 1, 36); Set_Result(10, 2, 1, 36); Set_Result(10, 0, 1, 36); // Direct Bet: 16 - 17 - 18
        Set_Result(12, 4, 1, 36); Set_Result(12, 2, 1, 36); Set_Result(12, 0, 1, 36); // Direct Bet: 19 - 20 - 21
        Set_Result(14, 4, 1, 36); Set_Result(14, 2, 1, 36); Set_Result(14, 0, 1, 36); // Direct Bet: 22 - 23 - 24
        Set_Result(16, 4, 1, 36); Set_Result(16, 2, 1, 36); Set_Result(16, 0, 1, 36); // Direct Bet: 25 - 26 - 27
        Set_Result(18, 4, 1, 36); Set_Result(18, 2, 1, 36); Set_Result(18, 0, 1, 36); // Direct Bet: 28 - 29 - 28
        Set_Result(20, 4, 1, 36); Set_Result(20, 2, 1, 36); Set_Result(20, 0, 1, 36); // Direct Bet: 31 - 32 - 33
        Set_Result(22, 4, 1, 36); Set_Result(22, 2, 1, 36); Set_Result(22, 0, 1, 36); // Direct Bet: 34 - 35 - 36

        Set_Result( 1, 4,  1,  4, 18); Set_Result( 1, 2,  2,  5, 18); Set_Result( 1, 0,  3,  6, 18); // Vertical Bet:  1/ 4 -  2/ 5 -  3/ 6
        Set_Result( 3, 4,  4,  7, 18); Set_Result( 3, 2,  5,  8, 18); Set_Result( 3, 0,  6,  9, 18); // Vertical Bet:  4/ 7 -  5/ 8 -  6/ 9
        Set_Result( 5, 4,  7, 10, 18); Set_Result( 5, 2,  8, 11, 18); Set_Result( 5, 0,  9, 12, 18); // Vertical Bet:  7/10 -  8/11 -  9/12
        Set_Result( 7, 4, 10, 13, 18); Set_Result( 7, 2, 11, 14, 18); Set_Result( 7, 0, 12, 15, 18); // Vertical Bet: 10/13 - 11/14 - 12/15
        Set_Result( 9, 4, 13, 16, 18); Set_Result( 9, 2, 14, 17, 18); Set_Result( 9, 0, 15, 18, 18); // Vertical Bet: 13/16 - 14/17 - 15/18
        Set_Result(11, 4, 16, 19, 18); Set_Result(11, 2, 17, 20, 18); Set_Result(11, 0, 18, 21, 18); // Vertical Bet: 16/19 - 17/20 - 18/21
        Set_Result(13, 4, 19, 22, 18); Set_Result(13, 2, 20, 23, 18); Set_Result(13, 0, 21, 24, 18); // Vertical Bet: 19/22 - 20/23 - 21/24
        Set_Result(15, 4, 22, 25, 18); Set_Result(15, 2, 23, 26, 18); Set_Result(15, 0, 24, 27, 18); // Vertical Bet: 22/25 - 23/26 - 24/27
        Set_Result(17, 4, 25, 28, 18); Set_Result(17, 2, 26, 29, 18); Set_Result(17, 0, 27, 30, 18); // Vertical Bet: 25/28 - 26/29 - 27/30
        Set_Result(19, 4, 28, 31, 18); Set_Result(19, 2, 29, 32, 18); Set_Result(19, 0, 30, 33, 18); // Vertical Bet: 28/31 - 29/32 - 30/33
        Set_Result(21, 4, 31, 34, 18); Set_Result(21, 2, 32, 35, 18); Set_Result(21, 0, 33, 36, 18); // Vertical Bet: 31/34 - 32/35 - 33/36

        Set_Result( 0, 3,  1,  2, 18); Set_Result( 0, 1,  2,  3, 18); // Horizontal Bet:   1/ 2 -  2/ 3
        Set_Result( 2, 3,  4,  5, 18); Set_Result( 2, 1,  5,  6, 18); // Horizontal Bet:   4/ 5 -  5/ 6
        Set_Result( 4, 3,  7,  8, 18); Set_Result( 4, 1,  8,  9, 18); // Horizontal Bet:   7/ 8 -  8/ 9
        Set_Result( 6, 3, 10, 11, 18); Set_Result( 6, 1, 11, 12, 18); // Horizontal Bet:  10/11 - 11/12
        Set_Result( 8, 3, 13, 14, 18); Set_Result( 8, 1, 14, 15, 18); // Horizontal Bet:  13/14 - 14/15
        Set_Result(10, 3, 16, 17, 18); Set_Result(10, 1, 17, 18, 18); // Horizontal Bet:  16/17 - 17/18
        Set_Result(12, 3, 19, 20, 18); Set_Result(12, 1, 20, 21, 18); // Horizontal Bet:  19/20 - 20/21
        Set_Result(14, 3, 22, 23, 18); Set_Result(14, 1, 23, 24, 18); // Horizontal Bet:  22/23 - 23/24
        Set_Result(16, 3, 25, 26, 18); Set_Result(16, 1, 26, 27, 18); // Horizontal Bet:  25/26 - 26/27
        Set_Result(18, 3, 28, 29, 18); Set_Result(18, 1, 29, 28, 18); // Horizontal Bet:  28/29 - 29/28
        Set_Result(20, 3, 31, 32, 18); Set_Result(20, 1, 32, 33, 18); // Horizontal Bet:  31/32 - 32/33
        Set_Result(22, 3, 34, 35, 18); Set_Result(22, 1, 35, 36, 18); // Horizontal Bet:  34/35 - 35/36

        Set_Result( 1, 3,  1,  2,  4,  5, 9); Set_Result( 1, 1,  2,  3,  5,  6, 9); // Cross Bet:  1/ 2/ 4/ 5 -  2/ 3/ 5/ 6
        Set_Result( 3, 3,  4,  5,  7,  8, 9); Set_Result( 3, 1,  5,  6,  8,  9, 9); // Cross Bet:  4/ 5/ 7/ 8 -  5/ 6/ 8/ 9
        Set_Result( 5, 3,  7,  8, 10, 11, 9); Set_Result( 5, 1,  8,  9, 11, 12, 9); // Cross Bet:  7/ 8/10/11 -  8/ 9/11/12
        Set_Result( 7, 3, 10, 11, 13, 14, 9); Set_Result( 7, 1, 11, 12, 14, 15, 9); // Cross Bet: 10/11/13/14 - 11/12/14/15
        Set_Result( 9, 3, 13, 14, 16, 17, 9); Set_Result( 9, 1, 14, 15, 17, 18, 9); // Cross Bet: 13/14/16/17 - 14/15/17/18
        Set_Result(11, 3, 16, 17, 19, 20, 9); Set_Result(11, 1, 17, 18, 20, 21, 9); // Cross Bet: 16/17/19/20 - 17/18/20/21
        Set_Result(13, 3, 19, 20, 22, 23, 9); Set_Result(13, 1, 20, 21, 23, 24, 9); // Cross Bet: 19/20/22/23 - 20/21/23/24
        Set_Result(15, 3, 22, 23, 25, 26, 9); Set_Result(15, 1, 23, 24, 26, 27, 9); // Cross Bet: 22/23/25/26 - 23/24/26/27
        Set_Result(17, 3, 25, 26, 28, 29, 9); Set_Result(17, 1, 26, 27, 29, 28, 9); // Cross Bet: 25/26/28/29 - 26/27/29/28
        Set_Result(19, 3, 28, 29, 31, 32, 9); Set_Result(19, 1, 29, 28, 32, 33, 9); // Cross Bet: 28/29/31/32 - 29/28/32/33
        Set_Result(21, 3, 31, 32, 34, 35, 9); Set_Result(21, 1, 32, 33, 35, 36, 9); // Cross Bet: 31/32/34/35 - 32/33/35/36

        for(int i = 1; i <= 36; i++) {
            for(int j = 0; j < 7; j++) {
                if(i <= 12) { Set_Result( 0 + j, 5, i, 3); } // 1st 12
                else if(i <= 24) { Set_Result( 8 + j, 5, i, 3); } // 2nd 12
                else if(i <= 36) { Set_Result(16 + j, 5, i, 3); } // 3rd 12
            }
            if(i % 3 == 1) Set_Result(24, 4, i, 3); // 2 to 1 (1)
            if(i % 3 == 2) Set_Result(24, 2, i, 3); // 2 to 1 (2)
            if(i % 3 == 0) Set_Result(24, 0, i, 3); // 2 to 1 (3)

            if(i     <= 18) { Set_Result( 0, 6, i, 2); Set_Result( 1, 6, i, 2); Set_Result( 2, 6, i, 2); } //  1 to 18
            if(i     >= 19) { Set_Result(20, 6, i, 2); Set_Result(21, 6, i, 2); Set_Result(22, 6, i, 2); } // 19 to 36
            if(i % 2 ==  0) { Set_Result( 4, 6, i, 2); Set_Result( 5, 6, i, 2); Set_Result( 6, 6, i, 2); } // EVEN
            if(i % 2 ==  1) { Set_Result(16, 6, i, 2); Set_Result(17, 6, i, 2); Set_Result(18, 6, i, 2); } // ODD
        }

        Set_Result(24, 5, 0, 36); Set_Result(24, 6, 0, 36); // Direct 0

        for(int i = 0; i < 3; i++) {
            Set_Result( 8 + i, 6,  1,  3,  5,  7, 2); Set_Result( 8 + i, 6,  9, 12, 14, 16, 2); Set_Result( 8 + i, 6, 18, 19, 21, 23, 2); Set_Result( 8 + i, 6, 25, 27, 30, 32, 2); Set_Result( 8 + i, 6, 34, 36, 2); // ROUGE
            Set_Result(12 + i, 6,  2,  4,  6,  8, 2); Set_Result(12 + i, 6, 10, 11, 13, 15, 2); Set_Result(12 + i, 6, 17, 20, 22, 24, 2); Set_Result(12 + i, 6, 26, 28, 29, 31, 2); Set_Result(12 + i, 6, 33, 35, 2); // NOIR
        }

        //int oldbet = bet;
        //bet = 0;
        //for(int y = 0; y < 7; y++) {
        //    for(int x = 0; x < 25; x++) {
        //        if(grid[x][y] > 0) bet += oldbet;
        //    }
        //}

        tc.turnstate = 4;
    }

    private void Set_Result(int x, int y, int r1, int r2, int multi) {
        Set_Result(x, y, r1, multi);
        Set_Result(x, y, r2, multi);
    }

    private void Set_Result(int x, int y, int r1, int r2, int r3, int r4, int multi) {
        Set_Result(x, y, r1, multi);
        Set_Result(x, y, r2, multi);
        Set_Result(x, y, r3, multi);
        Set_Result(x, y, r4, multi);
    }

    private void Set_Result(int x, int y, int r, int multi) {
        if(tc.gridI[x][y] == 1 || tc.gridI[x][y] == 3) {
            if(result == r) {
                tc.reward += multi;
                //coins_plus += (multi * bet);
                tc.gridI[x][y] = 2;
            } else {
                tc.gridI[x][y] = 3;
            }
        }
    }

}