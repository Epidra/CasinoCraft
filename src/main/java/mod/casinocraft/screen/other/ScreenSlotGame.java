package mod.casinocraft.screen.other;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.logic.other.LogicSlotGame;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ScreenSlotGame extends ScreenCasino {   // Slot Game

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenSlotGame(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------LOGIC----------------------------------------//

    public LogicSlotGame logic(){
        return (LogicSlotGame) menu.logic();
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClickedSUB(double mouseX, double mouseY, int mouseButton){

    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayerSUB(PoseStack matrixstack, int mouseX, int mouseY){
        if(menu.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            this.font.draw(matrixstack, "SPACE to SPIN ",  128, 210, 16777215);
            this.font.draw(matrixstack, "ENTER for TOKEN", 128, 225, 16777215);
        } else if(logic().turnstate == 3){
            this.font.draw(matrixstack, "SPACE to HOLD", 128, 210, 16777215);
        } else {
            this.font.draw(matrixstack, "ENTER to RESET", 128, 210, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayerSUB(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(menu.logic() instanceof LogicDummy){ return; }
        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_SLOTGAME);

        // ----- Multiplier ----- //
        int m = logic().scoreLevel;
        this.blit(matrixstack, leftPos + 8, topPos +  34, 192 + (m >= 4 ? 32 : 0), 48, 32, 16);
        this.blit(matrixstack, leftPos + 8, topPos +  69, 192 + (m >= 2 ? 32 : 0), 16, 32, 16);
        this.blit(matrixstack, leftPos + 8, topPos + 104, 192 + (m >= 1 ? 32 : 0),  0, 32, 16);
        this.blit(matrixstack, leftPos + 8, topPos + 139, 192 + (m >= 3 ? 32 : 0), 32, 32, 16);
        this.blit(matrixstack, leftPos + 8, topPos + 174, 192 + (m >= 5 ? 32 : 0), 64, 32, 16);

        // ----- Icons ----- //
        drawIcon(matrixstack, leftPos +  48, topPos + 40, 0);
        drawIcon(matrixstack, leftPos + 104, topPos + 40, 1);
        drawIcon(matrixstack, leftPos + 160, topPos + 40, 2);

        // ----- Lines ----- //
        if(logic().lines[0]){
            this.blit(matrixstack, leftPos +  48, topPos +  88, 0, 192, 48, 48);
            this.blit(matrixstack, leftPos + 104, topPos +  88, 0, 192, 48, 48);
            this.blit(matrixstack, leftPos + 160, topPos +  88, 0, 192, 48, 48);
        }
        if(logic().lines[1]){
            this.blit(matrixstack, leftPos +  48, topPos +  44, 48, 192, 48, 48);
            this.blit(matrixstack, leftPos + 104, topPos +  44, 48, 192, 48, 48);
            this.blit(matrixstack, leftPos + 160, topPos +  44, 48, 192, 48, 48);
        }
        if(logic().lines[2]){
            this.blit(matrixstack, leftPos +  48, topPos + 136, 96, 192, 48, 48);
            this.blit(matrixstack, leftPos + 104, topPos + 136, 96, 192, 48, 48);
            this.blit(matrixstack, leftPos + 160, topPos + 136, 96, 192, 48, 48);
        }
        if(logic().lines[3]){
            this.blit(matrixstack, leftPos +  48, topPos +  40, 144, 192, 48, 48);
            this.blit(matrixstack, leftPos + 104, topPos +  88, 144, 192, 48, 48);
            this.blit(matrixstack, leftPos + 160, topPos + 136, 144, 192, 48, 48);
        }
        if(logic().lines[4]){
            this.blit(matrixstack, leftPos +  48, topPos + 136, 192, 192, 48, 48);
            this.blit(matrixstack, leftPos + 104, topPos +  88, 192, 192, 48, 48);
            this.blit(matrixstack, leftPos + 160, topPos +  40, 192, 192, 48, 48);
        }

        // ----- Shadows ----- //
        this.blit(matrixstack, leftPos +  48, topPos +  40, 96,  96, 48, 48);
        this.blit(matrixstack, leftPos + 104, topPos +  40, 96,  96, 48, 48);
        this.blit(matrixstack, leftPos + 160, topPos +  40, 96,  96, 48, 48);
        this.blit(matrixstack, leftPos +  48, topPos + 136, 96, 144, 48, 48);
        this.blit(matrixstack, leftPos + 104, topPos + 136, 96, 144, 48, 48);
        this.blit(matrixstack, leftPos + 160, topPos + 136, 96, 144, 48, 48);
    }

    protected void drawGuiContainerBackgroundLayerGUI(PoseStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }





    //----------------------------------------SUPPORT----------------------------------------//

    private void drawIcon(PoseStack matrixstack, int posX, int posY, int index){
        for(int i = 0; i < 4; i++){
            int z = logic().grid[(logic().wheelPos[index]/48 + i) % 9][index];
            int mod = logic().wheelPos[index] % 48;
            int x = z < 2 ? z : z - 2;
            int y = z < 2 ? 3 : 1;
            if(i == 0) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48) + mod, x*48, y*48 + mod, 48, 48 - mod);
            if(i == 1) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48);
            if(i == 2) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48);
            if(i == 3) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48 - (48 - mod));
        }
    }





    //----------------------------------------BASIC----------------------------------------//

    protected String getGameName() {
        return "";
    }



}
