package mod.casinocraft.gui.arcade;

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

public class GuiTetris extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiTetris(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_TETRIS);
        tc.gridI = new int[10][20];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(tc.turnstate == 2) {
            if(keyCode == KEY_UP)    { actionTouch(0); }
            if(keyCode == KEY_DOWN)  { actionTouch(1); }
            if(keyCode == KEY_LEFT)  { actionTouch(2); }
            if(keyCode == KEY_RIGHT) { actionTouch(3); }
            if(keyCode == KEY_M)     { actionTouch(4); }
            if(keyCode == KEY_N)     { actionTouch(5); }
            if(keyCode == KEY_ENTER) { actionTouch(6); }
        }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(tc.turnstate >= 2) {
            this.fontRenderer.drawString("" + tc.scorePoint, 8+16*11 + 4-24, 8+16*0 + 4+8, 9999999);
            this.fontRenderer.drawString("" + tc.scoreLives,  8+16*11 + 4-24, 8+16*1 + 4+8, 9999999);
            this.fontRenderer.drawString("" + tc.scoreLevel,  8+16*11 + 4-24, 8+16*2 + 4+8, 9999999);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_TETRIS);
        this.drawTexturedModalRect(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background

        if(tc.turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 10; x++){
                    if(getValue(y * 10 + x) != -1) this.drawTexturedModalRect(guiLeft+16 + 8 + 12*x, guiTop + 8 + 12*y, tc.turnstate >= 4 ? 12*8 : 12*getValue(y * 10 + x), 204, 12, 12);
                }
            }

            this.drawTexturedModalRect(guiLeft+8+16 + 12*getValue(200), guiTop+8 + 12*getValue(201), tc.turnstate >= 4 ? 12*8 : 12*getValue(210), 204, 12, 12);
            this.drawTexturedModalRect(guiLeft+8+16 + 12*getValue(202), guiTop+8 + 12*getValue(203), tc.turnstate >= 4 ? 12*8 : 12*getValue(210), 204, 12, 12);
            this.drawTexturedModalRect(guiLeft+8+16 + 12*getValue(204), guiTop+8 + 12*getValue(205), tc.turnstate >= 4 ? 12*8 : 12*getValue(210), 204, 12, 12);
            this.drawTexturedModalRect(guiLeft+8+16 + 12*getValue(206), guiTop+8 + 12*getValue(207), tc.turnstate >= 4 ? 12*8 : 12*getValue(210), 204, 12, 12);


            drawTetromino(getValue(211), 8+16*11-24-4, 8+16*4+8);
            drawTetromino(getValue(212), 8+16*11-24-4, 8+16*9+8);

            //this.drawTexturedModalRect(guiLeft+256-20, guiTop+256-20, 0, 32, 16, 16); // Restart Button
        }
    }



    //--------------------CUSTOM--------------------

    private void drawTetromino(int mino, int x, int y) {
        if(mino == 0) { // I
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y +  0  , tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 96/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 1) { // O
            this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 2) { // S
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 80/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 16/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 3) { // Z
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 16/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 80/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 4) { // L
            this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 16/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 48/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 5) { // J
            this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 16/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 48/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 64/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 32/2, guiTop+y + 80/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
        if(mino == 6) { // T
            this.drawTexturedModalRect(guiLeft+x + 16/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 80/2, guiTop+y + 32/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
            this.drawTexturedModalRect(guiLeft+x + 48/2, guiTop+y + 64/2, tc.turnstate >= 4 ? 16*8 : 16*mino, 216, 16, 16);
        }
    }


    //------------------------------------------------------------------------------------------------------------------

    public boolean active_hold;
    public int container_next;
    public int container_hold;
    public int container_current;
    public double time_last;
    public double time_break;
    public int timer;
    public int[]     tetro     = new int[7]; // Color of single Tetromino
    public Vector2[] tetromino = new Vector2[4];
    public int line1;
    public int line2;
    public int line3;
    public int line4;
    public int alpha;



    //--------------------CONSTRUCTOR--------------------

    //public TileEntityTetris(){
    //    super(null, null);
    //}
//
    //public TileEntityTetris(TileEntityBoard te, BlockPos bp){
    //    super(te, bp);
    //    gridI = new int[10][20];
    //}

    //@Override
    //public String getGuiID() {
    //    return CasinoKeeper.GUIID_TETRIS.toString();
    //}
//
    //@Override
    //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
    //    return new ContainerCasino(playerInventory, this.board);
    //}
//
    //@Override
    //public ITextComponent getName() {
    //    return new TextComponentTranslation(CasinoKeeper.GUIID_TETRIS.toString(), new Object[0]);
    //}
//
    //@Nullable
    //@Override
    //public ITextComponent getCustomName() {
    //    return new TextComponentTranslation(CasinoKeeper.GUIID_TETRIS.toString(), new Object[0]);
    //}



    //--------------------BASIC--------------------

    public void start2(){
        active_hold = true;
        container_next = -1;
        container_hold = -1;
        container_current = -1;
        container_current = Tetromino_Roll();
        container_next = Tetromino_Roll();
        Tetromino_Create();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 20; j++) {
                tc.gridI[i][j] = -1;
            }
        }
        time_break = 500;
        time_last = 0;
        line1 = -1;
        line1 = -1;
        line1 = -1;
        line1 = -1;
        alpha = 250;
        timer = 0;
        Colorize();
    }

    public void actionTouch(int action){
        if(action == 0){ Tetromino_Drop();      } // UP
        if(action == 1){ Tetromino_Fall();      } // DOWN
        if(action == 2){ Command_Strafe(true);  } // LEFT
        if(action == 3){ Command_Strafe(false); } // RIGHT
        if(action == 4){ Command_Turn(true);    } // ROTATE LEFT
        if(action == 5){ Command_Turn(false);   } // ROTATE RIGHT
        if(action == 6){ Command_Hold();        } // HOLD
    }

    public void update(){
        timer+=15;
        if(alpha == 255) {
            if(timer > time_last + time_break - tc.scoreLevel * 5 && tc.turnstate == 2) {
                Tetromino_Fall();
                time_last = timer;
            }
        } else {
            alpha -= 10;
            if(alpha <= 0) {
                alpha = 250;
                Command_Collapse();
            }
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){

        if(index == 200) return tetromino[0].X;
        if(index == 201) return tetromino[0].Y;
        if(index == 202) return tetromino[1].X;
        if(index == 203) return tetromino[1].Y;
        if(index == 204) return tetromino[2].X;
        if(index == 205) return tetromino[2].Y;
        if(index == 206) return tetromino[3].X;
        if(index == 207) return tetromino[3].Y;
        if(index == 210) return container_current;
        if(index == 211) return container_next;
        if(index == 212) return container_hold;

        return inLine(index/10) && (alpha/75)%2==0 ? 7 : tc.gridI[index % 10][index / 10];
    }



    //--------------------CUSTOM--------------------

    private boolean inLine(int index){
        if(index == line1) return true;
        if(index == line2) return true;
        if(index == line3) return true;
        return index == line4;
    }

    private void Colorize() {
        boolean[] used = new boolean[10];
        int index = 0;
        for(int i = 0; i < 10; i++) {
            used[i] = false;
            index++;
        }

        tetro[0] = -1;
        tetro[1] = -1;
        tetro[2] = -1;
        tetro[3] = -1;
        tetro[4] = -1;
        tetro[5] = -1;
        tetro[6] = -1;

        int count = 0;
        while(count < index && count < 7) {
            int r = tc.rand.nextInt(7);
            if(!used[r]) {
                tetro[count] = r;
                used[r] = true;
                count++;
            }
        }
    }

    private void Command_Collapse() {
        if(line4 != -1) {
            for(int i = line4; i > 0; i--) {
                for(int x = 0; x < 10; x++){
                    tc.gridI[x][i] = tc.gridI[x][i - 1];
                }
            }
        }
        if(line3 != -1) {
            for(int i = line3; i > 0; i--) {
                for(int x = 0; x < 10; x++){
                    tc.gridI[x][i] = tc.gridI[x][i - 1];
                }
            }
        }
        if(line2 != -1) {
            for(int i = line2; i > 0; i--) {
                for(int x = 0; x < 10; x++){
                    tc.gridI[x][i] = tc.gridI[x][i - 1];
                }
            }
        }
        if(line1 != -1) {
            for(int i = line1; i > 0; i--) {
                for(int x = 0; x < 10; x++){
                    tc.gridI[x][i] = tc.gridI[x][i - 1];
                }
            }
        }
        line1 = -1;
        line2 = -1;
        line3 = -1;
        line4 = -1;
        alpha = 255;
    }

    public void Command_Strafe(boolean totheleft) {
        int dir = 0;
        if(totheleft) {
            dir = -1;
            if(tetromino[0].X > 0) {
                if(tetromino[1].X > 0) {
                    if(tetromino[2].X > 0) {
                        if(tetromino[3].X > 0) {
                            if(tc.gridI[tetromino[0].X + dir][tetromino[0].Y] == -1) {
                                if(tc.gridI[tetromino[1].X + dir][tetromino[1].Y] == -1) {
                                    if(tc.gridI[tetromino[2].X + dir][tetromino[2].Y] == -1) {
                                        if(tc.gridI[tetromino[3].X + dir][tetromino[3].Y] == -1) {
                                            tetromino[0].X = tetromino[0].X + dir;
                                            tetromino[1].X = tetromino[1].X + dir;
                                            tetromino[2].X = tetromino[2].X + dir;
                                            tetromino[3].X = tetromino[3].X + dir;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            dir = 1;
            if(tetromino[0].X < 9) {
                if(tetromino[1].X < 9) {
                    if(tetromino[2].X < 9) {
                        if(tetromino[3].X < 9) {
                            if(tc.gridI[tetromino[0].X + dir][tetromino[0].Y] == -1) {
                                if(tc.gridI[tetromino[1].X + dir][tetromino[1].Y] == -1) {
                                    if(tc.gridI[tetromino[2].X + dir][tetromino[2].Y] == -1) {
                                        if(tc.gridI[tetromino[3].X + dir][tetromino[3].Y] == -1) {
                                            tetromino[0].X = tetromino[0].X + dir;
                                            tetromino[1].X = tetromino[1].X + dir;
                                            tetromino[2].X = tetromino[2].X + dir;
                                            tetromino[3].X = tetromino[3].X + dir;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void Command_Turn(boolean totheleft) {
        Vector2 tempV1 = new Vector2(tetromino[0].X - tetromino[1].X, tetromino[0].Y - tetromino[1].Y);
        Vector2 tempV2 = new Vector2(tetromino[0].X - tetromino[2].X, tetromino[0].Y - tetromino[2].Y);
        Vector2 tempV3 = new Vector2(tetromino[0].X - tetromino[3].X, tetromino[0].Y - tetromino[3].Y);
        if(totheleft) {
            if(tempV1.X == 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y           ); } else
            if(tempV1.X == 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y           ); } else
            if(tempV1.X  < 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X + tempV1.Y, tetromino[0].Y - tempV1.X); }

            if(tempV2.X == 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y           ); } else
            if(tempV2.X == 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y           ); } else
            if(tempV2.X  < 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X + tempV2.Y, tetromino[0].Y - tempV2.X); }

            if(tempV3.X == 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y           ); } else
            if(tempV3.X == 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y           ); } else
            if(tempV3.X  < 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y - tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X + tempV3.Y, tetromino[0].Y - tempV3.X); }
        } else {
            if(tempV1.X == 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y           ); } else
            if(tempV1.X == 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y           ); } else
            if(tempV1.X  < 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y == 0) { tempV1 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  < 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  < 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); } else
            if(tempV1.X  > 0 && tempV1.Y  > 0) { tempV1 = new Vector2(tetromino[0].X - tempV1.Y, tetromino[0].Y + tempV1.X); }

            if(tempV2.X == 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y           ); } else
            if(tempV2.X == 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y           ); } else
            if(tempV2.X  < 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y == 0) { tempV2 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  < 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  < 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); } else
            if(tempV2.X  > 0 && tempV2.Y  > 0) { tempV2 = new Vector2(tetromino[0].X - tempV2.Y, tetromino[0].Y + tempV2.X); }

            if(tempV3.X == 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y           ); } else
            if(tempV3.X == 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y           ); } else
            if(tempV3.X  < 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y == 0) { tempV3 = new Vector2(tetromino[0].X,            tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  < 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  < 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); } else
            if(tempV3.X  > 0 && tempV3.Y  > 0) { tempV3 = new Vector2(tetromino[0].X - tempV3.Y, tetromino[0].Y + tempV3.X); }
        }
        if(tempV1.X > -1 && tempV1.X < 10 && tempV1.Y > -1 && tempV1.Y < 20) {
            if(tempV2.X > -1 && tempV2.X < 10 && tempV2.Y > -1 && tempV2.Y < 20) {
                if(tempV3.X > -1 && tempV3.X < 10 && tempV3.Y > -1 && tempV3.Y < 20) {
                    if(tc.gridI[tempV1.X][tempV1.Y] == -1) {
                        if(tc.gridI[tempV2.X][tempV2.Y] == -1) {
                            if(tc.gridI[tempV3.X][tempV3.Y] == -1) {
                                tetromino[1] = tempV1;
                                tetromino[2] = tempV2;
                                tetromino[3] = tempV3;
                            }
                        }
                    }
                }
            }
        }

    }

    public void Command_Hold() {
        if(active_hold) {
            active_hold = false;
            if(container_hold == -1) {
                container_hold = container_current;
                container_current = container_next;
                container_next = Tetromino_Roll();
            } else {
                int temp;
                temp = container_hold;
                container_hold = container_current;
                container_current = temp;
            }
            Tetromino_Create();
        }
    }

    private int Tetromino_Roll() {
        return tc.rand.nextInt(7);
    }

    private void Tetromino_Create() {
        if(container_current == 0) { // I
            tetromino[0] = new Vector2(4, 1); // OOXO
            tetromino[1] = new Vector2(4, 0); // OOXO
            tetromino[2] = new Vector2(4, 2); // OOXO
            tetromino[3] = new Vector2(4, 3); // OOXO
        }
        if(container_current == 1) { // O
            tetromino[0] = new Vector2(4, 0); // OOOO
            tetromino[1] = new Vector2(4, 1); // OXXO
            tetromino[2] = new Vector2(5, 0); // OXXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
        if(container_current == 2) { // S
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(6, 0); // OOXX
            tetromino[2] = new Vector2(4, 1); // OXXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
        if(container_current == 3) { // Z
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(4, 0); // XXOO
            tetromino[2] = new Vector2(5, 1); // OXXO
            tetromino[3] = new Vector2(6, 1); // OOOO
        }
        if(container_current == 4) { // L
            tetromino[0] = new Vector2(4, 2); // OXOO
            tetromino[1] = new Vector2(4, 0); // OXOO
            tetromino[2] = new Vector2(4, 1); // OXXO
            tetromino[3] = new Vector2(5, 2); // OOOO
        }
        if(container_current == 5) { // J
            tetromino[0] = new Vector2(5, 2); // OOXO
            tetromino[1] = new Vector2(5, 0); // OOXO
            tetromino[2] = new Vector2(5, 1); // OXXO
            tetromino[3] = new Vector2(4, 2); // OOOO
        }
        if(container_current == 6) { // T
            tetromino[0] = new Vector2(5, 0); // OOOO
            tetromino[1] = new Vector2(4, 0); // OXXX
            tetromino[2] = new Vector2(6, 0); // OOXO
            tetromino[3] = new Vector2(5, 1); // OOOO
        }
    }

    private void Tetromino_Drop() {
        int tempPoints = tc.scorePoint;
        while(tc.scorePoint == tempPoints) {
            Tetromino_Fall();
        }
    }

    /*public void Tetromino_Drop() {
    	boolean drop = false;
    	while(!drop){
    		if(tetromino[0].Y < 19 && tetromino[1].Y < 19 && tetromino[2].Y < 19 && tetromino[3].Y < 19) {
                if(gridI[tetromino[0].X][tetromino[0].Y + 1] == -1 && gridI[tetromino[1].X][tetromino[1].Y + 1] == -1 && gridI[tetromino[2].X][tetromino[2].Y + 1] == -1 && gridI[tetromino[3].X][tetromino[3].Y + 1] == -1) {
                    tetromino[0].Y = tetromino[0].Y + 1;
                    tetromino[1].Y = tetromino[1].Y + 1;
                    tetromino[2].Y = tetromino[2].Y + 1;
                    tetromino[3].Y = tetromino[3].Y + 1;
                } else {
                    drop = true;
                }
            } else {
                drop = true;
            }
    	}
    	Tetromino_Place();
    }*/

    public void Tetromino_Fall() {
        if(tetromino[0].Y < 19 && tetromino[1].Y < 19 && tetromino[2].Y < 19 && tetromino[3].Y < 19) {
            if(tc.gridI[tetromino[0].X][tetromino[0].Y + 1] == -1 && tc.gridI[tetromino[1].X][tetromino[1].Y + 1] == -1 && tc.gridI[tetromino[2].X][tetromino[2].Y + 1] == -1 && tc.gridI[tetromino[3].X][tetromino[3].Y + 1] == -1) {
                tetromino[0].Y = tetromino[0].Y + 1;
                tetromino[1].Y = tetromino[1].Y + 1;
                tetromino[2].Y = tetromino[2].Y + 1;
                tetromino[3].Y = tetromino[3].Y + 1;
            } else {
                Tetromino_Place();
            }
        } else {
            Tetromino_Place();
        }
    }

    private void Tetromino_Place() {
        active_hold = true;
        tc.gridI[tetromino[0].X][tetromino[0].Y] = container_current;
        tc.gridI[tetromino[1].X][tetromino[1].Y] = container_current;
        tc.gridI[tetromino[2].X][tetromino[2].Y] = container_current;
        tc.gridI[tetromino[3].X][tetromino[3].Y] = container_current;
        tc.scorePoint = tc.scorePoint + 2;
        if(tetromino[0].Y == 0) tc.turnstate = 4;
        container_current = container_next;
        int container_temp = container_next;
        if((container_next = Tetromino_Roll()) == container_temp) {
            if(tc.rand.nextInt(2) == 0) {
                container_next = Tetromino_Roll();
            }
        }
        Tetromino_Create();
        line1 = -1;
        line2 = -1;
        line3 = -1;
        line4 = -1;
        for(int i = 19; i > -1; i--) {
            if(tc.gridI[0][i] != -1 && tc.gridI[1][i] != -1 && tc.gridI[2][i] != -1 && tc.gridI[3][i] != -1 && tc.gridI[4][i] != -1 && tc.gridI[5][i] != -1 && tc.gridI[6][i] != -1 && tc.gridI[7][i] != -1 && tc.gridI[8][i] != -1 && tc.gridI[9][i] != -1) {
                tc.scoreLives++;
                tc.scorePoint += 50;
                if(line1 == -1) {
                    line1 = i;
                } else if(line2 == -1) {
                    line2 = i;
                } else if(line3 == -1) {
                    line3 = i;
                } else if(line4 == -1) {
                    line4 = i;
                }
                alpha -= 5;
            }
        }
        if(tc.scoreLives + 1 > (1 + tc.scoreLevel) * 10) {
            tc.scoreLevel++;
            time_break -= (time_break / 10);
        }
        if(line1 != -1 && line2 != -1 && line3 != -1 && line4 != -1) {
            tc.scorePoint = tc.scorePoint + 250 * (tc.scoreLevel + 1);
        }
    }

}
