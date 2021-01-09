package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.mino.LogicMinoPink;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenMinoPink extends ScreenCasino {   // FanTan

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoPink(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoPink logic(){
        return (LogicMinoPink) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int x = 0; x < 4; x++){
                if(mouseRect(64 + 32*x, 32, 32, 32, mouseX, mouseY)) {
                    action(x);
                }
            }
            if(mouseRect(82, 204, 92, 26, mouseX, mouseY)){
                action(-1);
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            if(CasinoKeeper.config_timeout.get() - logic().timeout > 0){
                drawFontInvert(matrixstack, "" + (CasinoKeeper.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FANTAN);
        this.blit(matrixstack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
        if(logic().turnstate == 2){
            this.blit(matrixstack, guiLeft+64 + 32*logic().selector.X, guiTop+32, 224, 32 + 32*logic().activePlayer, 32, 32);
        }
        for(int i = 0; i < 4; i++){
            if(logic().grid[i][0] > 0){
                this.blit(matrixstack, guiLeft+64 + 32*i, guiTop+32, 192, 32*logic().grid[i][0], 32, 32);
            }
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
        for(Card v : logic().chips){
            drawMino(matrixstack, v.number - 12, v.suit - 12);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
            blit(matrixstack, guiLeft+89, guiTop+206, 78, 44, 78, 22); // Button SPIN
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "fantan";
    }

}
