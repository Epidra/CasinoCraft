package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoYellow;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.Random;

public class ScreenMinoYellow extends ScreenCasino {   // SicBo

    private final int diceColor;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoYellow(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoYellow logic(){
        return (LogicMinoYellow) menu.logic();
    }

    protected String getGameName() {
        return "sicbo";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_BOT_MIDDLE, ButtonMap.PLACE,   () -> logic().turnstate == 2 && !logic().hasPlaced,                       () -> { action(-1);               } );
        buttonSet.addButton(ButtonMap.POS_BOT_LEFT,   ButtonMap.ANOTHER, () -> logic().turnstate == 2 &&  logic().hasPlaced && playerToken >= bet, () -> { action(-2); collectBet(); } );
        buttonSet.addButton(ButtonMap.POS_BOT_RIGHT,  ButtonMap.WAIT,    () -> logic().turnstate == 2 &&  logic().hasPlaced,                       () -> { action(-3);               } );
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && !logic().hasPlaced) {
            for (int y = 0; y < 6; y++) {
                for (int x = 0; x < 12; x++) {
                    int posX = tableID == 1 ? 32 + 16*x : -64 + 32*x;
                    int posY = 32 + 32*y;
                    int sizeX = tableID == 1 ? 16 : 32;
                    int sizeY = 32;
                    if (mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)) {
                        action(x + y * 12);
                    }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
        if(logic().turnstate == 2){ drawTimer(matrix);                                      }
        if(logic().turnstate == 5){ drawFontCenter(matrix, logic().hand, 128, 7, 11119017); }
    }

    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
        if(tableID == 1){
            drawBackground(matrix, CasinoKeeper.TEXTURE_SICBO_MIDDLE);
        } else {
            drawBackground(matrix, CasinoKeeper.TEXTURE_SICBO_LEFT, -128);
            drawBackground(matrix, CasinoKeeper.TEXTURE_SICBO_RIGHT, 128);
        }

        RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_DICE);

        int color = 0;
        for(int y = 0; y < 6; y++){
            for(int x = 0; x < 12; x++){
                color = logic().grid[x][y];
                int posX = tableID == 1 ? 24 + 16*x : -64 + 32*x;
                int posY = 32 + 32*y;
                if(color == -1                   ){ this.blit(matrix, leftPos+posX, topPos+posY, 224,        224, 32, 32); }
                if(color  >  0                   ){ this.blit(matrix, leftPos+posX, topPos+posY, 192, 32 * color, 32, 32); }
                if(logic().selector.matches(x, y)){ this.blit(matrix, leftPos+posX, topPos+posY, 224,          0, 32, 32); }
            }
        }

        if(logic().turnstate == 3){
            this.blit(matrix, leftPos + logic().dice[0].posX, topPos + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.blit(matrix, leftPos + logic().dice[1].posX, topPos + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
            this.blit(matrix, leftPos + logic().dice[2].posX, topPos + logic().dice[2].posY, logic().dice[2].number*32, diceColor*32, 32, 32);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
