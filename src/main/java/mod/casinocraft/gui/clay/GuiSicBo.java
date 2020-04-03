package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicSicBo;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;

public class GuiSicBo extends GuiCasino {

    int diceColor = 0;

    //--------------------CONSTRUCTOR--------------------

    public GuiSicBo(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
    }



    //--------------------BASIC--------------------

    public LogicSicBo logic(){
        return (LogicSicBo) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 6; y++){
                for(int x = 0; x < 12; x++){
                    int posX = tableID == 1 ? 32 + 16*x : -128+64 + 32*x;
                    int posY = 32+32*y;
                    int sizeX = tableID == 1 ? 16 : 32;
                    int sizeY = 32;
                    if(mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)){
                        action(x + y*12);
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

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_ENTER){ action(-2); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 4){
            drawString(logic().hand, 25, -10);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tableID == 1){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_MIDDLE);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
        } else {
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_LEFT);
            this.blit(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_RIGHT);
            this.blit(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);

        if(logic().turnstate >= 2){
            int color = 0;
            for(int y = 0; y < 6; y++){
                for(int x = 0; x < 12; x++){
                    color = logic().grid[x][y];
                    int posX = tableID == 1 ? 32-8 + 16*x : -128+64 + 32*x;
                    int posY = 32+32*y;
                    if(color != 0)
                        this.blit(guiLeft+posX, guiTop+posY, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                    if(logic().selector.matches(x, y)) this.blit(guiLeft+posX, guiTop+posY, 192,   0, 32, 32);
                }
            }
        }

        if(logic().turnstate == 3){
            this.blit(guiLeft + logic().dice[0].posX, guiTop + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.blit(guiLeft + logic().dice[1].posX, guiTop + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
            this.blit(guiLeft + logic().dice[2].posX, guiTop + logic().dice[2].posY, logic().dice[2].number*32, diceColor*32, 32, 32);
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
        if(logic().turnstate == 3 && logic().dice[0].shiftX == 0 && logic().dice[1].shiftX == 0 && logic().dice[2].shiftX == 0){
            blit(guiLeft+89, guiTop+251-16, 78, 44, 78, 22); // Button Spin
        }
    }



    //--------------------CUSTOM--------------------

}
