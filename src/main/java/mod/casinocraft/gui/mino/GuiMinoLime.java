package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoLime;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoLime;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiMinoLime extends GuiCasino {   // Simon

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoLime(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoLime(playerInv, furnaceInv, pos, world), playerInv);
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

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        drawFont("POINTS",           75, 25);
        drawFont("" + logic().scorePoint, 85, 35);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SIMON);
        drawTexturedModalRect(guiLeft +  64, guiTop +  64,   0, logic().alpha[0] > 1 ? 64 : 128, 64, 64);
        drawTexturedModalRect(guiLeft + 128, guiTop +  64,  64, logic().alpha[1] > 1 ? 64 : 128, 64, 64);
        drawTexturedModalRect(guiLeft +  64, guiTop + 128, 128, logic().alpha[2] > 1 ? 64 : 128, 64, 64);
        drawTexturedModalRect(guiLeft + 128, guiTop + 128, 192, logic().alpha[3] > 1 ? 64 : 128, 64, 64);
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
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
