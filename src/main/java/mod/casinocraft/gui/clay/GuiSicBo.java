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
            for(int x = 0; x < 12; x++){
                if(mouseRect(-63 + 32*x,  13, 32, 30, mouseX, mouseY)){ action(x, 0); }
                if(mouseRect(-63 + 32*x,  41, 32, 30, mouseX, mouseY)){ action(x, 1); }
                if(mouseRect(-63 + 32*x,  73, 32, 46, mouseX, mouseY)){ action(x, 2); }
                if(mouseRect(-63 + 32*x, 121, 32, 46, mouseX, mouseY)){ action(x, 3); }
                if(mouseRect(-63 + 32*x, 169, 32, 46, mouseX, mouseY)){ action(x, 4); }
                if(mouseRect(-63 + 32*x, 217, 32, 30, mouseX, mouseY)){ action(x, 5); }
            }
        }
    }

    private void action(int x, int y) {
        if(logic().selector.matches(x, y) && logic().grid[x][y] == 0) {
            if(playerToken >= CONTAINER.getBetLow()) {
                action(-1);
                CollectBet();
                playerToken = -1;
            } else {
                action(-2);
            }
        } else {
            action(x + y*12);
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
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_LEFT);
        this.blit(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_RIGHT);
        this.blit(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);

        if(logic().turnstate >= 2){
            int color = 0;
            for(int x = 0; x < 12; x++){
                color = logic().grid[x][0]; if(color != 0) this.blit(guiLeft+-63 + 32*x, guiTop +  13, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = logic().grid[x][1]; if(color != 0) this.blit(guiLeft+-63 + 32*x, guiTop +  41, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = logic().grid[x][2]; if(color != 0) this.blit(guiLeft+-63 + 32*x, guiTop +  73, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = logic().grid[x][3]; if(color != 0) this.blit(guiLeft+-63 + 32*x, guiTop + 121, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = logic().grid[x][4]; if(color != 0) this.blit(guiLeft+-63 + 32*x, guiTop + 169, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);
                color = logic().grid[x][5]; if(color != 0) this.blit(guiLeft+-63 + 32*x, guiTop + 217, 192, color == 1 ? 224 : color == 2 ? 128 : 32, 32, 32);

                if(logic().selector.matches(x, 0)) this.blit(guiLeft+-63 + 32*x, guiTop +  13, 192, 0, 32, 32);
                if(logic().selector.matches(x, 1)) this.blit(guiLeft+-63 + 32*x, guiTop +  41, 192, 0, 32, 32);
                if(logic().selector.matches(x, 2)) this.blit(guiLeft+-63 + 32*x, guiTop +  73, 192, 0, 32, 32);
                if(logic().selector.matches(x, 3)) this.blit(guiLeft+-63 + 32*x, guiTop + 121, 192, 0, 32, 32);
                if(logic().selector.matches(x, 4)) this.blit(guiLeft+-63 + 32*x, guiTop + 169, 192, 0, 32, 32);
                if(logic().selector.matches(x, 5)) this.blit(guiLeft+-63 + 32*x, guiTop + 217, 192, 0, 32, 32);
            }
        }

        if(logic().turnstate == 3){
            this.blit(guiLeft + logic().dice[0].posX, guiTop + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.blit(guiLeft + logic().dice[1].posX, guiTop + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
            this.blit(guiLeft + logic().dice[2].posX, guiTop + logic().dice[2].posY, logic().dice[2].number*32, diceColor*32, 32, 32);
        }
    }



    //--------------------CUSTOM--------------------

}
