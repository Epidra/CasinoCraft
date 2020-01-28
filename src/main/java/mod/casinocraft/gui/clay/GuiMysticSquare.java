package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicMysticSquare;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiMysticSquare extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiMysticSquare(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicMysticSquare logic(){
        return (LogicMysticSquare) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    if(mouseRect(32 + 48*x, 32 + 48*y, 48, 48, mouseX, mouseY)){
                        if(y > 0) if(logic().gridI[x    ][y - 1] == -1) action(0);
                        if(y < 3) if(logic().gridI[x    ][y + 1] == -1) action(1);
                        if(x > 0) if(logic().gridI[x - 1][y    ] == -1) action(2);
                        if(x < 3) if(logic().gridI[x + 1][y    ] == -1) action(3);
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_UP)   { action(0); }
        if(keyCode == KEY_DOWN) { action(1); }
        if(keyCode == KEY_LEFT) { action(2); }
        if(keyCode == KEY_RIGHT){ action(3); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate >= 2){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MYSTICSQUARE);
            for(int y = 0; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(logic().gridI[x][y] != -1) {
                        this.blit(guiLeft + 32 + 48*x, guiTop + 32 + 48*y, (logic().gridI[x][y] % 4)*48, (logic().gridI[x][y] / 4)*48, 48, 48);
                    }
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

}
