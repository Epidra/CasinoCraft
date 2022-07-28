package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.Config;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoPink;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.Card;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoPink extends ScreenCasino {   // FanTan

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoPink(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoPink logic(){
        return (LogicMinoPink) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){
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





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(logic().turnstate == 2){
            if(Config.CONFIG.config_timeout.get() - logic().timeout > 0){
                drawFontInvert(matrixstack, "" + (Config.CONFIG.config_timeout.get() - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
        drawBalance(matrixstack);
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_FANTAN);
        this.blit(matrixstack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight); // Background SMALL

        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_DICE);
        if(logic().turnstate == 2){
            this.blit(matrixstack, leftPos+64 + 32*logic().selector.X, topPos+32, 224, 32 + 32*logic().activePlayer, 32, 32);
        }
        for(int i = 0; i < 4; i++){
            if(logic().grid[i][0] > 0){
                this.blit(matrixstack, leftPos+64 + 32*i, topPos+32, 192, 32*logic().grid[i][0], 32, 32);
            }
        }

        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(Card v : logic().chips){
            drawMino(matrixstack, v.number - 12, v.suit - 12);
        }
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {
        if(logic().turnstate == 2){
            RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_BUTTONS);
            blit(matrixstack, leftPos+89, topPos+206, 78, 44, 78, 22); // Button SPIN
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "fantan";
    }



}
