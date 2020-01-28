package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicCraps;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;

public class GuiCraps extends GuiCasino {


    int diceColor = 0;

    //--------------------CONSTRUCTOR--------------------

    public GuiCraps(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
    }



    //--------------------BASIC--------------------

    public LogicCraps logic(){
        return (LogicCraps) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0) {
            for(int y = 0; y < 5; y++) {
                for(int x = 0; x < 21; x++) {
                    if(mouseRect(-50 + 16*x, 49 + 32*y, 16, 30, mouseX, mouseY)){
                        if(logic().selector.matches(x, y) && logic().grid[x][y] == 0) {
                            if(playerToken >= CONTAINER.getBetLow()) {
                                action(-1);
                                CollectBet();
                                playerToken = -1;
                            } else {
                                action(-2);
                            }
                        } else {
                            action(x + y*21);
                        }
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_ENTER){ action(-2); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 2)    { drawString(logic().hand,        20, 28); }
        if(logic().result > -1) { drawString("" + logic().result,  200, 28); }
        if(logic().point > -1) { drawString("" + logic().point,  220, 28); }
        if(logic().comepoint > -1) { drawString("" + logic().comepoint,  240, 28); }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_LEFT);
        this.blit(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CRAPS_RIGHT);
        this.blit(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);
        if(logic().turnstate >= 2){
            int color = 0;
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 21; x++){
                    color = logic().grid[x][y];
                    if(color != 0) this.blit(guiLeft+-50 + 16*x, guiTop+49 + 32*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                }
            }
        }
        if(logic().selector.X > -1) this.blit(guiLeft+-50 + 16*logic().selector.X, guiTop+49 + 32*logic().selector.Y, 192, 0, 32, 32);

        if(logic().turnstate == 3){
            this.blit(guiLeft + logic().dice[0].posX, guiTop + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.blit(guiLeft + logic().dice[1].posX, guiTop + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
        }
    }



    //--------------------CUSTOM--------------------

}
