package mod.casinocraft.screen.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoOrange;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;

import static mod.casinocraft.util.KeyMap.KEY_ENTER;

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
        return (LogicMinoOrange) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
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

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_ENTER){ action(-2); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2) { drawFont(logic().hand,        20, 28); }
        if(logic().result > -1) {    drawFont("" + logic().result,  200, 28); }
        if(logic().point > -1) {     drawFont("" + logic().point,  220, 28); }
        if(logic().comepoint > -1) { drawFont("" + logic().comepoint,  240, 28); }
        if(logic().turnstate == 2){
            if(CasinoKeeper.config_timeout.get() - logic().timeout > 0){
                drawFontInvert("" + (CasinoKeeper.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(tableID == 1){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_MIDDLE);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
        } else {
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_LEFT);
            this.blit(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_RIGHT);
            this.blit(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
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
                        this.blit(guiLeft+posX, guiTop+posY, 192, 32 * color, 32, 32);
                    if(logic().selector.matches(x, y))
                        this.blit(guiLeft+posX, guiTop+posY, 224, 32 * (logic().activePlayer+1), 32, 32);
                }
            }

            //for(int y = 0; y < 5; y++){
            //    for(int x = 0; x < 8; x++){
            //        color = logic().grid[x][y];
            //        if(color != 0) this.blit(guiLeft+-50 + 16*x, guiTop+49 + 32*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
            //    }
            //}
        }
        //if(logic().selector.X > -1) this.blit(guiLeft+-50 + 16*logic().selector.X, guiTop+49 + 32*logic().selector.Y, 192, 0, 32, 32);

        if(logic().turnstate == 3){
            this.blit(guiLeft + logic().dice[0].posX, guiTop + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.blit(guiLeft + logic().dice[1].posX, guiTop + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            if(playerToken == -1) validateBet();
            if(playerToken >= bet)
                blit(guiLeft+24+7,  guiTop+251-16,  0, 0, 78, 22); // Button Hit
            blit(guiLeft+140+7, guiTop+251-16, 78, 0, 78, 22); // Button Stand
        }
        if(logic().turnstate == 3 && logic().dice[0].shiftX == 0 && logic().dice[1].shiftX == 0){
            blit(guiLeft+89, guiTop+251-16, 78, 44, 78, 22); // Button Spin
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "craps";
    }

}
