package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoLime;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenMinoLime extends ScreenCasino {   // Simon

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoLime(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoLime logic(){
        return (LogicMinoLime) menu.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            if(logic().color_player.size() < logic().color_simon.size()){
                for(int y = 0; y < 2; y++){
                    for(int x = 0; x < 2; x++){
                        if(mouseRect(64 + 64*x, 64 + 64*y, 64, 64, mouseX, mouseY)) {
                            action(y*2 + x);
                        }
                    }
                }
            }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(MatrixStack matrixstack, int mouseX, int mouseY){
        drawFont(matrixstack, "POINTS",                75, 25);
        drawFont(matrixstack, "" + logic().scorePoint, 85, 35);
    }

    protected void drawGuiContainerBackgroundLayerSUB(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_SIMON);
        this.blit(matrixstack, leftPos +  64, topPos +  64,   0, logic().alpha[0] > 1 ? 64 : 128, 64, 64);
        this.blit(matrixstack, leftPos + 128, topPos +  64,  64, logic().alpha[1] > 1 ? 64 : 128, 64, 64);
        this.blit(matrixstack, leftPos +  64, topPos + 128, 128, logic().alpha[2] > 1 ? 64 : 128, 64, 64);
        this.blit(matrixstack, leftPos + 128, topPos + 128, 192, logic().alpha[3] > 1 ? 64 : 128, 64, 64);
        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_MINOS);
        for(int i = 0; i < logic().color_player.size(); i++){
            drawMino(matrixstack, 22, 22 + 12*i, logic().color_player.get(i), 0);
        }
        for(int i = 0; i < logic().color_simon.size(); i++){
            drawMino(matrixstack, 256-16-12-6, 22 + 12*i, logic().turnstate <= 3 ? 9 : logic().color_simon.get(i), 0);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "simon";
    }

}
