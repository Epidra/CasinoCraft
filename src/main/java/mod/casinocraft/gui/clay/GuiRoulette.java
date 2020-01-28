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
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 25; x++){
                    if(mouseRect(-128+49 + 16*x, 26 + 24*y, 16, 24, mouseX, mouseY)){
                        if(logic().selector.matches(x, y) && logic().grid[x][y] == 0) {
                            if(playerToken >= CONTAINER.getBetLow()) {
                                action(-1);
                                CollectBet();
                                playerToken = -1;
                            } else {
                                action(-2);
                            }
                        } else {
                            action(x + y*25);
                        }
                    }
                }
            }
            for(int x = 0; x < 25; x++){
                if(mouseRect(-128+49 + 16*x, 161, 16, 24, mouseX, mouseY)){
                    if(logic().selector.matches(x, 5) && logic().grid[x][5] == 0) {
                        if(playerToken >= CONTAINER.getBetLow()) {
                            action(-1);
                            CollectBet();
                            playerToken = -1;
                        } else {
                            action(-2);
                        }
                    } else {
                        action(x + 5*25);
                    }
                }
                if(mouseRect(-128+49 + 16*x, 193, 16, 24, mouseX, mouseY)){
                    if(logic().selector.matches(x, 6) && logic().grid[x][6] == 0) {
                        if(playerToken >= CONTAINER.getBetLow()) {
                            action(-1);
                            CollectBet();
                            playerToken = -1;
                        } else {
                            action(-2);
                        }
                    } else {
                        action(x + 6*25);
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_ENTER){ action(-2); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 4){
            drawString("" + logic().result,  225, -15);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_LEFT);
        this.blit(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_RIGHT);
        this.blit(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);

        if(logic().turnstate >= 2){
            int color = 0;
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 25; x++){
                    color = logic().grid[x][y];
                    if(color != 0)
                        this.blit(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                    if(logic().selector.matches(x, y)) this.blit(guiLeft+-128+49 + 16*x, guiTop+17+8 + 24*y, 192,   0, 32, 32);
                }
            }
            for(int x = 0; x < 25; x++){
                color = logic().grid[x][5];
                if(logic().grid[x][5] != 0) this.blit(guiLeft+-128+49 + 16*x, guiTop+161, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = logic().grid[x][6];
                if(logic().grid[x][6] != 0) this.blit(guiLeft+-128+49 + 16*x, guiTop+193, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                if(logic().selector.matches(x, 5))   this.blit(guiLeft+-128+49 + 16*x, guiTop+161, 192,   0, 32, 32);
                if(logic().selector.matches(x, 6))   this.blit(guiLeft+-128+49 + 16*x, guiTop+193, 192,   0, 32, 32);
            }
        }

        if(logic().turnstate == 3){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_WHEEL);
            this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            Vector2 v = logic().vectorWheel();
            this.blit(guiLeft + v.X, guiTop + v.Y, 128, 66, 16, 16);
        }
    }



    //--------------------CUSTOM--------------------

}
