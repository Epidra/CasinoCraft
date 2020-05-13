package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoYellow;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoYellow;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Random;

public class GuiMinoYellow extends GuiCasino {   // SicBo

    private int diceColor = 0;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoYellow(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoYellow(playerInv, furnaceInv, pos, world), playerInv);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoYellow logic(){
        return (LogicMinoYellow) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int y = 0; y < 6; y++){
                for(int x = 0; x < 12; x++){
                    int posX = tableID == 1 ? 32 + 16*x : -128+64 + 32*x;
                    int posY = 32+32*y;
                    int sizeX = tableID == 1 ? 16 : 32;
                    int sizeY = 32;
                    if(mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)){
                        action(x + y*12);
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

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {if(keyCode == Keyboard.KEY_RETURN){ action(-2); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate >= 4){
            drawFont(logic().hand, 25, -10);
        }
        if(logic().turnstate == 2){
            if(CasinoKeeper.config_timeout - logic().timeout > 0){
                drawFontInvert("" + (CasinoKeeper.config_timeout - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tableID == 1){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_MIDDLE);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL
        } else {
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_LEFT);
            this.drawTexturedModalRect(guiLeft-128, guiTop, 0, 0, this.xSize, this.ySize); // Background Left
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SICBO_RIGHT);
            this.drawTexturedModalRect(guiLeft+128, guiTop, 0, 0, this.xSize, this.ySize); // Background Right
        }

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);

        if(logic().turnstate >= 2){
            int color = 0;
            for(int y = 0; y < 6; y++){
                for(int x = 0; x < 12; x++){
                    color = logic().grid[x][y];
                    int posX = tableID == 1 ? 32-8 + 16*x : -128+64 + 32*x;
                    int posY = 32+32*y;
                    if(color != 0)
                        this.drawTexturedModalRect(guiLeft+posX, guiTop+posY, 192, 32 * color, 32, 32);
                    if(logic().selector.matches(x, y))
                        this.drawTexturedModalRect(guiLeft+posX, guiTop+posY, 224, 32 * (logic().activePlayer+1), 32, 32);
                }
            }
        }

        if(logic().turnstate == 3){
            this.drawTexturedModalRect(guiLeft + logic().dice[0].posX, guiTop + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.drawTexturedModalRect(guiLeft + logic().dice[1].posX, guiTop + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
            this.drawTexturedModalRect(guiLeft + logic().dice[2].posX, guiTop + logic().dice[2].posY, logic().dice[2].number*32, diceColor*32, 32, 32);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
        if(logic().turnstate == 2){
            if(playerToken == -1) validateBet();
            if(playerToken >= bet)
                drawTexturedModalRect(guiLeft+24+7,  guiTop+251-16,  0, 0, 78, 22); // Button Hit
            drawTexturedModalRect(guiLeft+140+7, guiTop+251-16, 78, 0, 78, 22); // Button Stand
        }
        if(logic().turnstate == 3 && logic().dice[0].shiftX == 0 && logic().dice[1].shiftX == 0 && logic().dice[2].shiftX == 0){
            drawTexturedModalRect(guiLeft+89, guiTop+251-16, 78, 44, 78, 22); // Button Spin
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "sicbo";
    }

}
