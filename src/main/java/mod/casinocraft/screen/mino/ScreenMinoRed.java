package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoRed;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Vector2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenMinoRed extends ScreenCasino {   // Roulette

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoRed(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoRed logic(){
        return (LogicMinoRed) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0 && isActivePlayer()){
            for(int y = 0; y < 7; y++){
                for(int x = 0; x < 25; x++){
                    int posX = tableID == 1 ? -128+152+6 + 8*x : -128+56+8 + 16*x;
                    int posY = y == 6 ? 200 : y == 5 ? 168 : 24+12 + 24*y;
                    int sizeX = tableID == 1 ? 8 : 16;
                    int sizeY = y > 4 ? 32 : 24;
                    if(mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)){
                        action(x + y*25);
                    }
                }
            }
            if(mouseRect( 24, 251-16, 92, 26, mouseX, mouseY) && playerToken >= bet){
                action(-1);
                collectBet();
                playerToken = -1;
            }
            if(mouseRect(140, 251-16, 92, 26, mouseX, mouseY)){
                action(-2);
            }
        }
        if((logic().turnstate == 3 || logic().turnstate == 4) && mouseRect(82, 251-16, 92, 26, mouseX, mouseY)){
            action(-2);
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate >= 4){
            drawFont(matrixstack, "" + logic().result,  225, -15);
        }
        if(logic().turnstate == 2){
            if(CasinoKeeper.config_timeout.get() - logic().timeout > 0){
                drawFontInvert(matrixstack, "" + (CasinoKeeper.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(tableID == 1){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_MIDDLE);
            this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
        } else {
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_LEFT);
            this.blit(matrixstack, guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_RIGHT);
            this.blit(matrixstack, guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);

        if(logic().turnstate >= 2){
            for(int y = 0; y < 7; y++){
                for(int x = 0; x < 25; x++){
                    int color = logic().grid[x][y];
                    int posX = tableID == 1 ? -128+152+6-6-16+8 + 8*x : -128+56+8-8 + 16*x;
                    int posY = y == 6 ? 200 : y == 5 ? 168 : 24+12-4 + 24*y;
                    if(color != 0)
                        this.blit(matrixstack, guiLeft+posX, guiTop+posY, 192, 32 * color, 32, 32);
                    if(logic().selector.matches(x, y))
                        this.blit(matrixstack, guiLeft+posX, guiTop+posY, 224, 32 * (logic().activePlayer+1), 32, 32);
                }
            }
        }

        if(logic().turnstate == 3){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_WHEEL);
            this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
            Vector2 v = logic().vectorWheel();
            drawMinoSmall(matrixstack, v.X, v.Y, 0, false);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2 && isActivePlayer()){
            if(playerToken == -1) validateBet();
            if(playerToken >= bet)
                blit(matrixstack, guiLeft+24+7,  guiTop+251-16,  0, 0, 78, 22); // Button Hit
            blit(matrixstack, guiLeft+140+7, guiTop+251-16, 78, 0, 78, 22); // Button Stand
        }
        if(logic().turnstate == 3 && ( (!logic().spinning) || (logic().spinning && logic().timer == 0) )){
            blit(matrixstack, guiLeft+89, guiTop+251-16, 78, 44, 78, 22); // Button Spin
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "roulette";
    }

}
