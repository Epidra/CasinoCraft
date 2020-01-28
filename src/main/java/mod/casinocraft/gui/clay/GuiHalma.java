package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicHalma;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiHalma extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiHalma(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicHalma logic(){
        return (LogicHalma) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ action(y*17 + x); }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().table == 1) {
            drawString("POINTS",           24, 24);
            drawString("" + logic().scorePoint, 34, 34);
        } else {
            drawString("POINTS",           24-76-16  , 24);
            drawString("" + logic().scorePoint, 34-76-16  , 34);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 17; x++) {
                if(logic().grid[x][y] == 0) this.blit(guiLeft-4-24*3 + 24*x, guiTop-4+24 + 24*y, 24*9, 232, 24, 24);
                if(logic().grid[x][y] == 1) this.blit(guiLeft-4-24*3 + 24*x, guiTop-4+24 + 24*y,    0, 232, 24, 24);
            }
        }
        this.blit(guiLeft-4-24*3 + 24*logic().selector.X, guiTop-4+24 + 24*logic().selector.Y, 24, 232, 24, 24);
    }



    //--------------------CUSTOM--------------------

}
