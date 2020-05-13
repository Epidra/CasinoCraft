package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoPink;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoPink;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class GuiMinoPink extends GuiCasino {   // FanTan

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoPink(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoPink(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicMinoPink logic(){
        return (LogicMinoPink) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
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

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {

    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(logic().turnstate == 2){
            if(CasinoKeeper.config_timeout - logic().timeout > 0){
                drawFontInvert("" + (CasinoKeeper.config_timeout - logic().timeout), tableID == 1 ? 256-18 : 336, 4);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FANTAN);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_DICE);
        if(logic().turnstate == 2){
            this.drawTexturedModalRect(guiLeft+64 + 32*logic().selector.X, guiTop+32, 224, 32 + 32*logic().activePlayer, 32, 32);
        }
        for(int i = 0; i < 4; i++){
            if(logic().grid[i][0] > 0){
                this.drawTexturedModalRect(guiLeft+64 + 32*i, guiTop+32, 192, 32*logic().grid[i][0], 32, 32);
            }
        }

        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MINOS);
        for(Card v : logic().chips){
            drawMino(v.number - 12, v.suit - 12);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {
        if(logic().turnstate == 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_BUTTONS);
            drawTexturedModalRect(guiLeft+89, guiTop+206, 78, 44, 78, 22); // Button SPIN
        }
    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "fantan";
    }

}
