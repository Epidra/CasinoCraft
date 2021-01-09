package mod.casinocraft.gui.card;

import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.card.ContainerCardBlack;
import mod.casinocraft.container.card.ContainerCardBlue;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.card.LogicCardBlue;
import mod.casinocraft.logic.other.LogicDummy;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiCardBlue extends GuiCasino {   // FreeCell

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiCardBlue(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world){
        super(new ContainerCardBlue(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicCardBlue logic(){
        return (LogicCardBlue) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2 && mouseButton == 0){
            for(int x = 0; x < 8; x++){
                for(int y = 0; y < 20; y++){
                    if(mouseRect(32*x, 64+4 + (24-logic().compress)*y, 32, 24, mouseX, mouseY)){ action(x + y*8); }
                }
                if(mouseRect(32*x, 16+4, 32, 48, mouseX, mouseY)){ action((x+1) * -1); }
            }
        }
    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {if(logic().turnstate == 2 && keyCode == Keyboard.KEY_RETURN){ action(-9); }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < logic().cards_field[x].size(); y++){
                drawCard(x*32, 64+4 + y*(24-logic().compress), logic().cards_field[x].get(y));
            }
        }
        drawCardBack(32*0, 16+4, 12);
        drawCardBack(32*1, 16+4, 12);
        drawCardBack(32*2, 16+4, 12);
        drawCardBack(32*3, 16+4, 12);
        drawCardBack(32*4, 16+4,  7);
        drawCardBack(32*5, 16+4,  7);
        drawCardBack(32*6, 16+4,  7);
        drawCardBack(32*7, 16+4,  7);

        drawCard(0*32, 16+4, logic().cards_freecell[0]);
        drawCard(1*32, 16+4, logic().cards_freecell[1]);
        drawCard(2*32, 16+4, logic().cards_freecell[2]);
        drawCard(3*32, 16+4, logic().cards_freecell[3]);

        if(logic().cards_finish[0].size() > 1) drawCard(4*32, 16+4, logic().cards_finish[0].get(logic().cards_finish[0].size() - 2));
        if(logic().cards_finish[0].size() > 0) drawCard(4*32, 16+4, logic().cards_finish[0].get(logic().cards_finish[0].size() - 1));
        if(logic().cards_finish[1].size() > 1) drawCard(5*32, 16+4, logic().cards_finish[1].get(logic().cards_finish[1].size() - 2));
        if(logic().cards_finish[1].size() > 0) drawCard(5*32, 16+4, logic().cards_finish[1].get(logic().cards_finish[1].size() - 1));
        if(logic().cards_finish[2].size() > 1) drawCard(6*32, 16+4, logic().cards_finish[2].get(logic().cards_finish[2].size() - 2));
        if(logic().cards_finish[2].size() > 0) drawCard(6*32, 16+4, logic().cards_finish[2].get(logic().cards_finish[2].size() - 1));
        if(logic().cards_finish[3].size() > 1) drawCard(7*32, 16+4, logic().cards_finish[3].get(logic().cards_finish[3].size() - 2));
        if(logic().cards_finish[3].size() > 0) drawCard(7*32, 16+4, logic().cards_finish[3].get(logic().cards_finish[3].size() - 1));

        if(logic().selector.Y == -2){
            drawCardBack(logic().selector.X*32, 16+4, 9);
        } else if(logic().selector.Y >= 0){
            drawCardBack(logic().selector.X*32, 64+4 + logic().selector.Y*(24-logic().compress), 9);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    // ...




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "freecell";
    }

}
