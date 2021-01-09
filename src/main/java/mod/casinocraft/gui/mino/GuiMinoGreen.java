package mod.casinocraft.gui.mino;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.mino.ContainerMinoGreen;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.mino.LogicMinoGreen;
import mod.casinocraft.logic.other.LogicDummy;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiMinoGreen extends GuiCasino {   // Mystic Square

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiMinoGreen(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerMinoGreen(playerInv, furnaceInv, pos, world), playerInv);
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

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(keyCode == Keyboard.KEY_UP)   { action(0); }
        if(keyCode == Keyboard.KEY_DOWN) { action(1); }
        if(keyCode == Keyboard.KEY_LEFT) { action(2); }
        if(keyCode == Keyboard.KEY_RIGHT){ action(3); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MYSTICSQUARE);
            for(int y = 0; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    int i = logic().grid[x][y] % 20;
                    if(i != -1) {
                        this.drawTexturedModalRect(guiLeft + 32 + 48*x, guiTop + 32 + 48*y, (i % 4)*48, (i / 4)*48, 48, 48);
                    }
                }
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "mystic_square";
    }

}
