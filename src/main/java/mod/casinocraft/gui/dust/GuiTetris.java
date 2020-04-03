package mod.casinocraft.gui.dust;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.dust.LogicTetris;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiTetris extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiTetris(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicTetris logic(){
        return (LogicTetris) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(logic().turnstate == 2) {
            if(keyCode == KEY_UP)    { action(0); }
            if(keyCode == KEY_DOWN)  { action(1); }
            if(keyCode == KEY_LEFT)  { action(2); }
            if(keyCode == KEY_RIGHT) { action(3); }
            if(keyCode == KEY_M)     { action(4); }
            if(keyCode == KEY_N)     { action(5); }
            if(keyCode == KEY_ENTER) { action(6); }
        }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 2) {
            this.font.drawString("" + logic().scorePoint, 8+16*11 + 4-24, 8+16*0 + 4+8, 9999999);
            this.font.drawString("" + logic().scoreLives,  8+16*11 + 4-24, 8+16*1 + 4+8, 9999999);
            this.font.drawString("" + logic().scoreLevel,  8+16*11 + 4-24, 8+16*2 + 4+8, 9999999);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_TETRIS);
        if(logic().turnstate < 2){
            this.blit(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background
        } else {
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background
        }

        if(logic().turnstate >= 2){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 20; y++){
                for(int x = 0; x < 10; x++){
                    if(logic().grid[x][y] != -1) this.blit(guiLeft+16 + 8 + 12*x, guiTop + 8 + 12*y, logic().turnstate >= 4 ? 12*8 : 12*tetroColor(x, y), 204-24, 12, 12);
                }
            }

            this.blit(guiLeft+8+16 + 12*logic().tetromino[0].X, guiTop+8 + 12*logic().tetromino[0].Y, logic().turnstate >= 4 ? 12*8 : 12*logic().container_now, 204-24, 12, 12);
            this.blit(guiLeft+8+16 + 12*logic().tetromino[1].X, guiTop+8 + 12*logic().tetromino[1].Y, logic().turnstate >= 4 ? 12*8 : 12*logic().container_now, 204-24, 12, 12);
            this.blit(guiLeft+8+16 + 12*logic().tetromino[2].X, guiTop+8 + 12*logic().tetromino[2].Y, logic().turnstate >= 4 ? 12*8 : 12*logic().container_now, 204-24, 12, 12);
            this.blit(guiLeft+8+16 + 12*logic().tetromino[3].X, guiTop+8 + 12*logic().tetromino[3].Y, logic().turnstate >= 4 ? 12*8 : 12*logic().container_now, 204-24, 12, 12);


            drawTetromino(logic().container_next, 8+16*11-24-4, 8+16*4+8);
            drawTetromino(logic().container_hold, 8+16*11-24-4, 8+16*9+8);

            //this.drawTexturedModalRect(guiLeft+256-20, guiTop+256-20, 0, 32, 16, 16); // Restart Button
        }
    }



    //--------------------CUSTOM--------------------

    private int tetroColor(int x, int y){
        return logic().inLine(y) && (logic().alpha/75)%2==0 ? 7 : logic().grid[x][y];
    }

    private void drawTetromino(int mino, int x, int y) {
        if(mino == 0) { // I
            this.blit(guiLeft+x + 48/2, guiTop+y +  0  , logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 48/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 48/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 48/2, guiTop+y + 96/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
        }
        if(mino == 1) { // O
            this.blit(guiLeft+x + 32/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 64/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 32/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 64/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
        }
        if(mino == 2) { // S
            this.blit(guiLeft+x + 48/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 80/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 48/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 16/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
        }
        if(mino == 3) { // Z
            this.blit(guiLeft+x + 48/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 16/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 48/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 80/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
        }
        if(mino == 4) { // L
            this.blit(guiLeft+x + 32/2, guiTop+y + 16/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 32/2, guiTop+y + 48/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 32/2, guiTop+y + 80/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 64/2, guiTop+y + 80/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
        }
        if(mino == 5) { // J
            this.blit(guiLeft+x + 64/2, guiTop+y + 16/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 64/2, guiTop+y + 48/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 64/2, guiTop+y + 80/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 32/2, guiTop+y + 80/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
        }
        if(mino == 6) { // T
            this.blit(guiLeft+x + 16/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 48/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 80/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
            this.blit(guiLeft+x + 48/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino, 216-24, 16, 16);
        }
    }

}
