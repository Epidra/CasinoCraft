package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoRed;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoRed;
import mod.casinocraft.logic.other.LogicDummy;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiMinoRed extends GuiCasino {   // Roulette

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoRed(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoRed(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoRed logic(){
        return (LogicMinoRed) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2 && mouseButton == 0 && isActivePlayer()){
            for(int y = 0; y < 7; y++){
                for(int x = 0; x < 25; x++){
                    int posX = tableID == 1 ? -128+152+6 + 8*x : -128+56+8 + 16*x;
                    int posY = y == 6 ? 200 : y == 5 ? 168 : 24+12 + 24*y;
                    int sizeX = tableID == 1 ? 8 : 16;
                    int sizeY = y > 4 ? 32 : 24;
                    if(mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)){
                        action(x + y*25);
                    }
                }
            }
            if(mouseRect( 24, 251-16, 92, 26, mouseX, mouseY) && playerToken >= bet){
                action(-1);
                collectBet();
                playerToken = -1;
            }
            if(mouseRect(140, 251-16, 92, 26, mouseX, mouseY)){
                action(-2);
            }
        }
        if((logic().turnstate == 3 || logic().turnstate == 4) && mouseRect(82, 251-16, 92, 26, mouseX, mouseY)){
            action(-2);
        }
    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 4){
            drawFont("" + logic().result,  225, -15);
        }
        if(logic().turnstate == 2){
            if(CasinoKeeper.config_timeout - logic().timeout > 0){
                drawFontInvert("" + (CasinoKeeper.config_timeout - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(tableID == 1){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_MIDDLE);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
        } else {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_LEFT);
            this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_RIGHT);
            this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
        }

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);

        if(logic().turnstate >= 2){
            for(int y = 0; y < 7; y++){
                for(int x = 0; x < 25; x++){
                    int color = logic().grid[x][y];
                    int posX = tableID == 1 ? -128+152+6-6-16+8 + 8*x : -128+56+8-8 + 16*x;
                    int posY = y == 6 ? 200 : y == 5 ? 168 : 24+12-4 + 24*y;
                    if(color != 0)
                        this.drawTexturedModalRect(guiLeft+posX, guiTop+posY, 192, 32 * color, 32, 32);
                    if(logic().selector.matches(x, y))
                        this.drawTexturedModalRect(guiLeft+posX, guiTop+posY, 224, 32 * (logic().activePlayer+1), 32, 32);
                }
            }
        }

        if(logic().turnstate == 3){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_ROULETTE_WHEEL);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
            Vector2 v = logic().vectorWheel();
            drawMinoSmall(v.X, v.Y, 0, false);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2 && isActivePlayer()){
            if(playerToken == -1) validateBet();
            if(playerToken >= bet)
                drawTexturedModalRect(guiLeft+24+7,  guiTop+251-16,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+251-16, 78, 0, 78, 22); // Button Stand
        }
        if(logic().turnstate == 3 && ( (!logic().spinning) || (logic().spinning && logic().timer == 0) )){
            drawTexturedModalRect(guiLeft+89, guiTop+251-16, 78, 44, 78, 22); // Button Spin
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "roulette";
    }

}
