package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicRoulette;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiRoulette extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiRoulette(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicRoulette logic(){
        return (LogicRoulette) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
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
                CollectBet();
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

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 4){
            drawString("" + logic().result,  225, -15);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tableID == 1){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_MIDDLE);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
        } else {
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_LEFT);
            this.blit(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_RIGHT);
            this.blit(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);

        if(logic().turnstate >= 2){
            int color = 0;
            for(int y = 0; y < 7; y++){
                for(int x = 0; x < 25; x++){
                    color = logic().grid[x][y];
                    int posX = tableID == 1 ? -128+152+6-6-16+8 + 8*x : -128+56+8-8 + 16*x;
                    int posY = y == 6 ? 200 : y == 5 ? 168 : 24+12-4 + 24*y;
                    if(color != 0)
                        this.blit(guiLeft+posX, guiTop+posY, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                    if(logic().selector.matches(x, y)) this.blit(guiLeft+posX, guiTop+posY, 192,   0, 32, 32);
                }
            }


            //int color = 0;
            //for(int y = 0; y < 5; y++){
            //    for(int x = 0; x < 25; x++){
            //        color = logic().grid[x][y];
            //        if(color != 0)
            //            this.blit(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
            //        if(logic().selector.matches(x, y)) this.blit(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192,   0, 32, 32);
            //    }
            //}
            //for(int x = 0; x < 25; x++){
            //    color = logic().grid[x][5];
            //    if(logic().grid[x][5] != 0) this.blit(guiLeft+-128+49 + 16*x, guiTop+161, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
            //    color = logic().grid[x][6];
            //    if(logic().grid[x][6] != 0) this.blit(guiLeft+-128+49 + 16*x, guiTop+193, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
            //    if(logic().selector.matches(x, 5))   this.blit(guiLeft+-128+49 + 16*x, guiTop+161, 192,   0, 32, 32);
            //    if(logic().selector.matches(x, 6))   this.blit(guiLeft+-128+49 + 16*x, guiTop+193, 192,   0, 32, 32);
            //}
        }

        if(logic().turnstate == 3){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_WHEEL);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            Vector2 v = logic().vectorWheel();
            this.blit(guiLeft + v.X, guiTop + v.Y, 128, 112, 16, 16);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        if(logic().turnstate == 2){
            if(playerToken == -1) ValidateBet();
            if(playerToken >= bet)
            blit(guiLeft+24+7,  guiTop+251-16,  0, 0, 78, 22); // Button Hit
            blit(guiLeft+140+7, guiTop+251-16, 78, 0, 78, 22); // Button Stand
        }
        if(logic().turnstate == 3 && ( (!logic().spinning) || (logic().spinning && logic().timer == 0) )){
            blit(guiLeft+89, guiTop+251-16, 78, 44, 78, 22); // Button Spin
        }
    }



    //--------------------CUSTOM--------------------

}
