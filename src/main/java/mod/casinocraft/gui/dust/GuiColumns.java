package mod.casinocraft.gui.dust;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.dust.LogicColumns;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiColumns extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiColumns(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicColumns logic(){
        return (LogicColumns) CONTAINER.logic();
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

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_COLUMNS);
        this.blit(guiLeft, guiTop + intro, 0, 0, this.xSize, this.ySize - intro); // Background

        if(logic().turnstate >= 2){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 15; y++){
                for(int x = 0; x < 6; x++){
                    //drawMino(tc.getValue(y * 6 + x), x, y);
                    if(logic().grid[x][y] != -1) this.blit(guiLeft+16 + 8+16 + 16*x, guiTop + 8 + 16*y, logic().turnstate >= 4 ? 16*8 : 16*logic().grid[x][y], 216, 16, 16);
                }
            }

            this.blit(guiLeft+8 + 16*logic().tromino[0].X+16+16, guiTop+8 + 16*logic().tromino[0].Y, logic().turnstate >= 4 ? 16*8 : 16*logic().turnstate >= 4 ?  8 : logic().container_current[0], 216, 16, 16);
            this.blit(guiLeft+8 + 16*logic().tromino[1].X+16+16, guiTop+8 + 16*logic().tromino[1].Y, logic().turnstate >= 4 ? 16*8 : 16*logic().turnstate >= 4 ?  8 : logic().container_current[1], 216, 16, 16);
            this.blit(guiLeft+8 + 16*logic().tromino[2].X+16+16, guiTop+8 + 16*logic().tromino[2].Y, logic().turnstate >= 4 ? 16*8 : 16*logic().turnstate >= 4 ?  8 : logic().container_current[2], 216, 16, 16);

            if((logic().turnstate >= 4 ?  8 : logic().container_next[0]) > -1) drawTetromino(logic().turnstate >= 4 ?  8 : logic().container_next[0], logic().turnstate >= 4 ?  8 : logic().container_next[1], logic().turnstate >= 4 ?  8 : logic().container_next[2], 8+16*11-24-4, 8+8+8+16*4);
            if((logic().turnstate >= 4 ? -1 : logic().container_hold[0]) > -1) drawTetromino(logic().turnstate >= 4 ? -1 : logic().container_hold[0], logic().turnstate >= 4 ? -1 : logic().container_hold[1], logic().turnstate >= 4 ? -1 : logic().container_hold[2], 8+16*11-24-4, 8+8+8+16*9);

            //this.drawTexturedModalRect(guiLeft+256-20, guiTop+256-20, 0, 32, 16, 16);

            //if(tc.turnstate == 2) tc.update();
        }
    }



    //--------------------CUSTOM--------------------

    private void drawTetromino(int mino0, int mino1, int mino2, int x, int y) {
        this.blit(guiLeft+x + 48/2, guiTop+y +  0  , logic().turnstate >= 4 ? 16*8 : 16*mino0, 216, 16, 16);
        this.blit(guiLeft+x + 48/2, guiTop+y + 32/2, logic().turnstate >= 4 ? 16*8 : 16*mino1, 216, 16, 16);
        this.blit(guiLeft+x + 48/2, guiTop+y + 64/2, logic().turnstate >= 4 ? 16*8 : 16*mino2, 216, 16, 16);
    }

}
