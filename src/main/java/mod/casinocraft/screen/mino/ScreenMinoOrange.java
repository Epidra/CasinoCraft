package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.mino.LogicMinoOrange;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;

public class ScreenMinoOrange extends ScreenCasino {   // Craps

    private final int diceColor;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoOrange(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        Random rand = new Random();
        diceColor = rand.nextInt(8);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoOrange logic(){
        return (LogicMinoOrange) menu.logic();
    }

    protected String getGameName() {
        return "craps";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_BOT_MIDDLE, ButtonMap.PLACE,   () -> isActivePlayer() && logic().turnstate == 2 && !logic().hasPlaced,                       () -> { action(-1);               } );
        buttonSet.addButton(ButtonMap.POS_BOT_LEFT,   ButtonMap.ANOTHER, () -> isActivePlayer() && logic().turnstate == 2 &&  logic().hasPlaced && playerToken >= bet, () -> { action(-2); collectBet(); } );
        buttonSet.addButton(ButtonMap.POS_BOT_RIGHT,  ButtonMap.WAIT,    () -> isActivePlayer() && logic().turnstate == 2 &&  logic().hasPlaced,                       () -> { action(-3);               } );
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && !logic().hasPlaced) {
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 8; x++){
                    int posX = tableID == 1
                            ? x == 0 ?  48 : x == 7 ? 176 :  80 + 16*(x-1)
                            : x == 0 ? -48 : x == 7 ? 272 : -16 + 48*(x-1);
                    int posY = 48 + 32*y;
                    int sizeX = tableID == 1
                            ? x == 0 ? 32 : x == 7 ? 32 : 16
                            : x == 0 ? 32 : x == 7 ? 32 : 48;
                    int sizeY = 32;
                    if(mouseRect(posX, posY, sizeX, sizeY, mouseX, mouseY)){
                        action(x + y*8);
                    }
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(MatrixStack matrix, int mouseX, int mouseY){
        drawFontCenter(                      matrix,      logic().hand,     128, 7, 11119017);
        if(logic().result    > -1){ drawFont(matrix, "" + logic().result,    10, 7, 11119017); }
        if(logic().point     > -1){ drawFont(matrix, "" + logic().point,     25, 7, 11119017); }
        if(logic().comepoint > -1){ drawFont(matrix, "" + logic().comepoint, 40, 7, 11119017); }
        if(logic().turnstate == 2){ drawTimer(matrix); }
    }

    protected void drawBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
        if(tableID == 1){
            drawBackground(matrix, CasinoKeeper.TEXTURE_CRAPS_MIDDLE);
        } else {
            drawBackground(matrix, CasinoKeeper.TEXTURE_CRAPS_LEFT, -128);
            drawBackground(matrix, CasinoKeeper.TEXTURE_CRAPS_RIGHT, 128);
        }

        this.minecraft.getTextureManager().bind(CasinoKeeper.TEXTURE_DICE);
        int color = 0;
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 8; x++){
                int posX = tableID == 1
                        ? x == 0 ?  48 : x == 7 ? 176 : 72 + 16*(x-1)
                        : x == 0 ? -48 : x == 7 ? 272 : -8 + 48*(x-1);
                int posY = 48 + 32*y;
                color = logic().grid[x][y];
                if(color == -1                   ){ this.blit(matrix, leftPos+posX, topPos+posY, 224,               224, 32, 32); }
                if(color  >  0                   ){ this.blit(matrix, leftPos+posX, topPos+posY, 192, 32 * (color % 10), 32, 32); }
                if(logic().selector.matches(x, y)){ this.blit(matrix, leftPos+posX, topPos+posY, 224,                 0, 32, 32); }
            }
        }

        if(logic().turnstate == 3){
            this.blit(matrix, leftPos + logic().dice[0].posX, topPos + logic().dice[0].posY, logic().dice[0].number*32, diceColor*32, 32, 32);
            this.blit(matrix, leftPos + logic().dice[1].posX, topPos + logic().dice[1].posY, logic().dice[1].number*32, diceColor*32, 32, 32);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
