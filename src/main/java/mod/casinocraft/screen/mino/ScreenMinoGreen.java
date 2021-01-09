package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.mino.LogicMinoGreen;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenMinoGreen extends ScreenCasino {   // Mystic Square

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoGreen(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoGreen logic(){
        return (LogicMinoGreen) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if (mouseButton == 0){
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    if(mouseRect(32 + 48*x, 32 + 48*y, 48, 48, mouseX, mouseY)){
                        if(y > 0) if(logic().grid[x    ][y - 1] == -1)
                            action(0);
                        if(y < 3) if(logic().grid[x    ][y + 1] == -1)
                            action(1);
                        if(x > 0) if(logic().grid[x - 1][y    ] == -1)
                            action(2);
                        if(x < 3) if(logic().grid[x + 1][y    ] == -1)
                            action(3);
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(keyCode == KEY_UP)   { action(0); }
        if(keyCode == KEY_DOWN) { action(1); }
        if(keyCode == KEY_LEFT) { action(2); }
        if(keyCode == KEY_RIGHT){ action(3); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MYSTICSQUARE);
            for(int y = 0; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    int i = logic().grid[x][y] % 20;
                    if(i != -1) {
                        this.blit(matrixstack, guiLeft + 32 + 48*x, guiTop + 32 + 48*y, (i % 4)*48, (i / 4)*48, 48, 48);
                    }
                }
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "mystic_square";
    }

}
