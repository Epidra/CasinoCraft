package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoOrange;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;

public class ScreenMinoOrange extends ScreenCasino {   // Craps

    private int diceColor = 0;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoOrange(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoOrange logic(){
        return (LogicMinoOrange) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0) {
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 8; x++){
                    int posX = tableID == 1
                            ? x == 0 ? 48      : x == 7 ?     176 :      48+32 + 16*(x-1)
                            : x == 0 ? -128+80 : x == 7 ? 128+144 : -128+80+32 + 48*(x-1);
                    int posY = 48 + 32*y;
                    int sizeX = tableID == 1
                            ? x == 0 ? 32 : x == 7 ? 32 : 16
                            : x == 0 ? 32 : x == 7 ? 32 : 48;
                    int sizeY = 32;
                    if(mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)){
                        action(x + y*8);
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
        if(logic().turnstate >= 2) { drawFont(matrixstack, logic().hand,                 20, 28); }
        if(logic().result > -1) {    drawFont(matrixstack, "" + logic().result,    200, 28); }
        if(logic().point > -1) {     drawFont(matrixstack, "" + logic().point,     220, 28); }
        if(logic().comepoint > -1) { drawFont(matrixstack, "" + logic().comepoint, 240, 28); }
        if(logic().turnstate == 2){
            if(CasinoKeeper.config_timeout.get() - logic().timeout > 0){
                drawFontInvert(matrixstack, "" + (CasinoKeeper.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(tableID == 1){
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_CRAPS_MIDDLE);
            this.blit(matrixstack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background SMALL
        } else {
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_CRAPS_LEFT);
            this.blit(matrixstack, leftPos-128, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background Left
            this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_CRAPS_RIGHT);
            this.blit(matrixstack, leftPos+128, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background Right
        }

        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_DICE);
        if(logic().turnstate >= 2){
            int color = 0;

            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 8; x++){
                    int posX = tableID == 1
                            ? x == 0 ? 48      : x == 7 ?     176 :   48+32    + 16*(x-1)-8
                            : x == 0 ? -128+80 : x == 7 ? 128+144 : -128+80+32 + 48*(x-1)+8;
                    int posY = 48 + 32*y;
                    color = logic().grid[x][y];
                    if(color != 0)
                        this.blit(matrixstack, leftPos+posX, topPos+posY, 192, 32 * color, 32, 32);
                    if(logic().selector.matches(x, y))
                        this.blit(matrixstack, leftPos+posX, topPos+posY, 224, 32 * (logic().activePlayer+1), 32, 32);
                }
            }
        }

        if(logic().turnstate == 3){
            this.blit(matrixstack, leftPos + logic().dice[0].posX, topPos + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.blit(matrixstack, leftPos + logic().dice[1].posX, topPos + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            if(playerToken == -1) validateBet();
            if(playerToken >= bet)
                blit(matrixstack, leftPos+24+7,  topPos+251-16,  0, 0, 78, 22); // Button Hit
            blit(matrixstack, leftPos+140+7, topPos+251-16, 78, 0, 78, 22); // Button Stand
        }
        if(logic().turnstate == 3 && logic().dice[0].shiftX == 0 && logic().dice[1].shiftX == 0){
            blit(matrixstack, leftPos+89, topPos+251-16, 78, 44, 78, 22); // Button Spin
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "craps";
    }



}
