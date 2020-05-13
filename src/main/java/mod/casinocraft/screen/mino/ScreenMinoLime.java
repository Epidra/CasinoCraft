package mod.casinocraft.screen.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
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
        return (LogicMinoLime) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
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

    protected void keyTyped2(int keyCode){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        drawFont("POINTS",           75, 25);
        drawFont("" + logic().scorePoint, 85, 35);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SIMON);
        this.blit(guiLeft +  64, guiTop +  64,   0, logic().alpha[0] > 1 ? 64 : 128, 64, 64);
        this.blit(guiLeft + 128, guiTop +  64,  64, logic().alpha[1] > 1 ? 64 : 128, 64, 64);
        this.blit(guiLeft +  64, guiTop + 128, 128, logic().alpha[2] > 1 ? 64 : 128, 64, 64);
        this.blit(guiLeft + 128, guiTop + 128, 192, logic().alpha[3] > 1 ? 64 : 128, 64, 64);
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
        for(int i = 0; i < logic().color_player.size(); i++){
            drawMino(22, 22 + 12*i, logic().color_player.get(i), 0);
        }
        for(int i = 0; i < logic().color_simon.size(); i++){
            drawMino(256-16-12-6, 22 + 12*i, logic().turnstate <= 3 ? 9 : logic().color_simon.get(i), 0);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "simon";
    }

}
