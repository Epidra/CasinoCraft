package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class GuiHalma extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiHalma(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_HALMA);
        tc.gridI = new int[17][9];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ actionTouch(y*17 + x); }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(table == 1) {
            this.fontRenderer.drawString("POINTS",           25, 25, 0);
            this.fontRenderer.drawString("POINTS",           24, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scorePoint, 35, 35, 0);
            this.fontRenderer.drawString("" + tc.scorePoint, 34, 34, 16777215);
        } else {
            this.fontRenderer.drawString("POINTS",           24-76-16+1, 25, 0);
            this.fontRenderer.drawString("POINTS",           24-76-16  , 24, 16777215);
            this.fontRenderer.drawString("" + tc.scorePoint, 34-76-16+1, 35, 0);
            this.fontRenderer.drawString("" + tc.scorePoint, 34-76-16  , 34, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(getValue(y*17+x) == 0) this.drawTexturedModalRect(guiLeft-4-24*3 + 24*x, guiTop-4+24 + 24*y, 24*9, 232, 24, 24);
                if(getValue(y*17+x) == 1) this.drawTexturedModalRect(guiLeft-4-24*3 + 24*x, guiTop-4+24 + 24*y,    0, 232, 24, 24);
            }
        }
        this.drawTexturedModalRect(guiLeft-4-24*3 + 24*tc.selector.X, guiTop-4+24 + 24*tc.selector.Y, 24, 232, 24, 24);
    }



    //--------------------CUSTOM--------------------




   //public TileEntityHalma(){
   //    super(null, null);
   //}

   //public TileEntityHalma(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new int[17][9];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_HALMA.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_HALMA.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_HALMA.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        if(tc.difficulty == 2) {
            tc.selector.set(8, 2);
            tc.gridI[0][0] = -1; tc.gridI[1][0] = -1; tc.gridI[2][0] = -1; tc.gridI[3][0] = -1; tc.gridI[4][0] = -1; tc.gridI[5][0] = -1; tc.gridI[6][0] =  1; tc.gridI[7][0] =  1; tc.gridI[8][0] =  1; tc.gridI[9][0] =  1; tc.gridI[10][0] =  1; tc.gridI[11][0] = -1; tc.gridI[12][0] = -1; tc.gridI[13][0] = -1; tc.gridI[14][0] = -1; tc.gridI[15][0] = -1; tc.gridI[16][0] = -1;
            tc.gridI[0][1] = -1; tc.gridI[1][1] = -1; tc.gridI[2][1] = -1; tc.gridI[3][1] = -1; tc.gridI[4][1] = -1; tc.gridI[5][1] = -1; tc.gridI[6][1] =  1; tc.gridI[7][1] =  1; tc.gridI[8][1] =  1; tc.gridI[9][1] =  1; tc.gridI[10][1] =  1; tc.gridI[11][1] = -1; tc.gridI[12][1] = -1; tc.gridI[13][1] = -1; tc.gridI[14][1] = -1; tc.gridI[15][1] = -1; tc.gridI[16][1] = -1;
            tc.gridI[0][2] = -1; tc.gridI[1][2] = -1; tc.gridI[2][2] = -1; tc.gridI[3][2] = -1; tc.gridI[4][2] =  1; tc.gridI[5][2] =  1; tc.gridI[6][2] =  1; tc.gridI[7][2] =  1; tc.gridI[8][2] =  1; tc.gridI[9][2] =  1; tc.gridI[10][2] =  1; tc.gridI[11][2] =  1; tc.gridI[12][2] =  1; tc.gridI[13][2] = -1; tc.gridI[14][2] = -1; tc.gridI[15][2] = -1; tc.gridI[16][2] = -1;
            tc.gridI[0][3] = -1; tc.gridI[1][3] = -1; tc.gridI[2][3] = -1; tc.gridI[3][3] = -1; tc.gridI[4][3] =  1; tc.gridI[5][3] =  1; tc.gridI[6][3] =  1; tc.gridI[7][3] =  1; tc.gridI[8][3] =  1; tc.gridI[9][3] =  1; tc.gridI[10][3] =  1; tc.gridI[11][3] =  1; tc.gridI[12][3] =  1; tc.gridI[13][3] = -1; tc.gridI[14][3] = -1; tc.gridI[15][3] = -1; tc.gridI[16][3] = -1;
            tc.gridI[0][4] = -1; tc.gridI[1][4] = -1; tc.gridI[2][4] = -1; tc.gridI[3][4] = -1; tc.gridI[4][4] =  1; tc.gridI[5][4] =  1; tc.gridI[6][4] =  1; tc.gridI[7][4] =  1; tc.gridI[8][4] =  0; tc.gridI[9][4] =  1; tc.gridI[10][4] =  1; tc.gridI[11][4] =  1; tc.gridI[12][4] =  1; tc.gridI[13][4] = -1; tc.gridI[14][4] = -1; tc.gridI[15][4] = -1; tc.gridI[16][4] = -1;
            tc.gridI[0][5] = -1; tc.gridI[1][5] = -1; tc.gridI[2][5] = -1; tc.gridI[3][5] = -1; tc.gridI[4][5] =  1; tc.gridI[5][5] =  1; tc.gridI[6][5] =  1; tc.gridI[7][5] =  1; tc.gridI[8][5] =  1; tc.gridI[9][5] =  1; tc.gridI[10][5] =  1; tc.gridI[11][5] =  1; tc.gridI[12][5] =  1; tc.gridI[13][5] = -1; tc.gridI[14][5] = -1; tc.gridI[15][5] = -1; tc.gridI[16][5] = -1;
            tc.gridI[0][6] = -1; tc.gridI[1][6] = -1; tc.gridI[2][6] = -1; tc.gridI[3][6] = -1; tc.gridI[4][6] =  1; tc.gridI[5][6] =  1; tc.gridI[6][6] =  1; tc.gridI[7][6] =  1; tc.gridI[8][6] =  1; tc.gridI[9][6] =  1; tc.gridI[10][6] =  1; tc.gridI[11][6] =  1; tc.gridI[12][6] =  1; tc.gridI[13][6] = -1; tc.gridI[14][6] = -1; tc.gridI[15][6] = -1; tc.gridI[16][6] = -1;
            tc.gridI[0][7] = -1; tc.gridI[1][7] = -1; tc.gridI[2][7] = -1; tc.gridI[3][7] = -1; tc.gridI[4][7] = -1; tc.gridI[5][7] = -1; tc.gridI[6][7] =  1; tc.gridI[7][7] =  1; tc.gridI[8][7] =  1; tc.gridI[9][7] =  1; tc.gridI[10][7] =  1; tc.gridI[11][7] = -1; tc.gridI[12][7] = -1; tc.gridI[13][7] = -1; tc.gridI[14][7] = -1; tc.gridI[15][7] = -1; tc.gridI[16][7] = -1;
            tc.gridI[0][8] = -1; tc.gridI[1][8] = -1; tc.gridI[2][8] = -1; tc.gridI[3][8] = -1; tc.gridI[4][8] = -1; tc.gridI[5][8] = -1; tc.gridI[6][8] =  1; tc.gridI[7][8] =  1; tc.gridI[8][8] =  1; tc.gridI[9][8] =  1; tc.gridI[10][8] =  1; tc.gridI[11][8] = -1; tc.gridI[12][8] = -1; tc.gridI[13][8] = -1; tc.gridI[14][8] = -1; tc.gridI[15][8] = -1; tc.gridI[16][8] = -1;
        } else {
            tc.selector.set(8, 4);
            tc.gridI[0][0] = -1; tc.gridI[1][0] = -1; tc.gridI[2][0] = -1; tc.gridI[3][0] =  1; tc.gridI[4][0] =  1; tc.gridI[5][0] =  1; tc.gridI[6][0] =  1; tc.gridI[7][0] = -1; tc.gridI[8][0] = -1; tc.gridI[9][0] = -1; tc.gridI[10][0] =  1; tc.gridI[11][0] =  1; tc.gridI[12][0] =  1; tc.gridI[13][0] =  1; tc.gridI[14][0] = -1; tc.gridI[15][0] = -1; tc.gridI[16][0] = -1;
            tc.gridI[0][1] = -1; tc.gridI[1][1] = -1; tc.gridI[2][1] = -1; tc.gridI[3][1] =  1; tc.gridI[4][1] =  1; tc.gridI[5][1] =  1; tc.gridI[6][1] =  1; tc.gridI[7][1] = -1; tc.gridI[8][1] = -1; tc.gridI[9][1] = -1; tc.gridI[10][1] =  1; tc.gridI[11][1] =  1; tc.gridI[12][1] =  1; tc.gridI[13][1] =  1; tc.gridI[14][1] = -1; tc.gridI[15][1] = -1; tc.gridI[16][1] = -1;
            tc.gridI[0][2] =  1; tc.gridI[1][2] =  1; tc.gridI[2][2] =  1; tc.gridI[3][2] =  1; tc.gridI[4][2] =  1; tc.gridI[5][2] =  1; tc.gridI[6][2] =  1; tc.gridI[7][2] =  1; tc.gridI[8][2] =  1; tc.gridI[9][2] =  1; tc.gridI[10][2] =  1; tc.gridI[11][2] =  1; tc.gridI[12][2] =  1; tc.gridI[13][2] =  1; tc.gridI[14][2] =  1; tc.gridI[15][2] =  1; tc.gridI[16][2] =  1;
            tc.gridI[0][3] =  1; tc.gridI[1][3] =  1; tc.gridI[2][3] =  1; tc.gridI[3][3] =  1; tc.gridI[4][3] =  1; tc.gridI[5][3] =  1; tc.gridI[6][3] =  1; tc.gridI[7][3] =  1; tc.gridI[8][3] =  1; tc.gridI[9][3] =  1; tc.gridI[10][3] =  1; tc.gridI[11][3] =  1; tc.gridI[12][3] =  1; tc.gridI[13][3] =  1; tc.gridI[14][3] =  1; tc.gridI[15][3] =  1; tc.gridI[16][3] =  1;
            tc.gridI[0][4] =  1; tc.gridI[1][4] =  1; tc.gridI[2][4] =  1; tc.gridI[3][4] =  1; tc.gridI[4][4] =  0; tc.gridI[5][4] =  0; tc.gridI[6][4] =  1; tc.gridI[7][4] =  1; tc.gridI[8][4] =  1; tc.gridI[9][4] =  1; tc.gridI[10][4] =  1; tc.gridI[11][4] =  0; tc.gridI[12][4] =  0; tc.gridI[13][4] =  1; tc.gridI[14][4] =  1; tc.gridI[15][4] =  1; tc.gridI[16][4] =  1;
            tc.gridI[0][5] =  1; tc.gridI[1][5] =  1; tc.gridI[2][5] =  1; tc.gridI[3][5] =  1; tc.gridI[4][5] =  1; tc.gridI[5][5] =  1; tc.gridI[6][5] =  1; tc.gridI[7][5] =  1; tc.gridI[8][5] =  1; tc.gridI[9][5] =  1; tc.gridI[10][5] =  1; tc.gridI[11][5] =  1; tc.gridI[12][5] =  1; tc.gridI[13][5] =  1; tc.gridI[14][5] =  1; tc.gridI[15][5] =  1; tc.gridI[16][5] =  1;
            tc.gridI[0][6] =  1; tc.gridI[1][6] =  1; tc.gridI[2][6] =  1; tc.gridI[3][6] =  1; tc.gridI[4][6] =  1; tc.gridI[5][6] =  1; tc.gridI[6][6] =  1; tc.gridI[7][6] =  1; tc.gridI[8][6] =  1; tc.gridI[9][6] =  1; tc.gridI[10][6] =  1; tc.gridI[11][6] =  1; tc.gridI[12][6] =  1; tc.gridI[13][6] =  1; tc.gridI[14][6] =  1; tc.gridI[15][6] =  1; tc.gridI[16][6] =  1;
            tc.gridI[0][7] = -1; tc.gridI[1][7] = -1; tc.gridI[2][7] = -1; tc.gridI[3][7] =  1; tc.gridI[4][7] =  1; tc.gridI[5][7] =  1; tc.gridI[6][7] =  1; tc.gridI[7][7] = -1; tc.gridI[8][7] = -1; tc.gridI[9][7] = -1; tc.gridI[10][7] =  1; tc.gridI[11][7] =  1; tc.gridI[12][7] =  1; tc.gridI[13][7] =  1; tc.gridI[14][7] = -1; tc.gridI[15][7] = -1; tc.gridI[16][7] = -1;
            tc.gridI[0][8] = -1; tc.gridI[1][8] = -1; tc.gridI[2][8] = -1; tc.gridI[3][8] =  1; tc.gridI[4][8] =  1; tc.gridI[5][8] =  1; tc.gridI[6][8] =  1; tc.gridI[7][8] = -1; tc.gridI[8][8] = -1; tc.gridI[9][8] = -1; tc.gridI[10][8] =  1; tc.gridI[11][8] =  1; tc.gridI[12][8] =  1; tc.gridI[13][8] =  1; tc.gridI[14][8] = -1; tc.gridI[15][8] = -1; tc.gridI[16][8] = -1;
        }
    }

    public void actionTouch(int action){
        Jump(action%17, action/17);
    }

    public void update(){

    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        return tc.gridI[index%17][index/17];
    }



    //--------------------CUSTOM--------------------

    private void Jump(int x, int y) {
        if(tc.gridI[x][y] == 1) {
            tc.selector.set(x, y);
            tc.scoreLevel = 1;
        } else if(tc.gridI[x][y] == 0) {
            if(y + 2 == tc.selector.Y && tc.gridI[tc.selector.X][tc.selector.Y - 1] == 1) { // Jump to UP
                tc.gridI[x][y    ] = 1;
                tc.gridI[x][y + 1] = 0;
                tc.gridI[x][y + 2] = 0;
                tc.selector.set(x, y);
                tc.scorePoint += tc.scoreLevel;
                tc.scoreLevel++;
            } else if(y - 2 == tc.selector.Y && tc.gridI[tc.selector.X][tc.selector.Y + 1] == 1) { // Jump to DOWN
                tc.gridI[x][y    ] = 1;
                tc.gridI[x][y - 1] = 0;
                tc.gridI[x][y - 2] = 0;
                tc.selector.set(x, y);
                tc.scorePoint += tc.scoreLevel;
                tc.scoreLevel++;
            } else if(x + 2 == tc.selector.X && tc.gridI[tc.selector.X - 1][tc.selector.Y] == 1) { // Jump to LEFT
                tc.gridI[x    ][y] = 1;
                tc.gridI[x + 1][y] = 0;
                tc.gridI[x + 2][y] = 0;
                tc.selector.set(x, y);
                tc.scorePoint += tc.scoreLevel;
                tc.scoreLevel++;
            } else if(x - 2 == tc.selector.X && tc.gridI[tc.selector.X + 1][tc.selector.Y] == 1) { // Jump to RIGHT
                tc.gridI[x    ][y] = 1;
                tc.gridI[x - 1][y] = 0;
                tc.gridI[x - 2][y] = 0;
                tc.selector.set(x, y);
                tc.scorePoint += tc.scoreLevel;
                tc.scoreLevel++;
            }
            Check_For_GameOver();
        }
    }

    private void Check_For_GameOver() {
        boolean canJump = false;
        int counter = 0;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(tc.gridI[x][y] == 1) {
                    counter++;
                    if(y >=  2 && tc.gridI[x][y - 1] == 1 && tc.gridI[x][y - 2] == 0) canJump = true;
                    if(y <=  6 && tc.gridI[x][y + 1] == 1 && tc.gridI[x][y + 2] == 0) canJump = true;
                    if(x >=  2 && tc.gridI[x - 1][y] == 1 && tc.gridI[x - 2][y] == 0) canJump = true;
                    if(x <= 14 && tc.gridI[x + 1][y] == 1 && tc.gridI[x + 2][y] == 0) canJump = true;
                }
            }
        }
        if(counter == 1) tc.turnstate = 4;
        if(!canJump) tc.turnstate = 4;
    }

}
