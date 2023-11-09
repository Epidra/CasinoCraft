package mod.casinocraft.screen.mino;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.logic.mino.LogicMinoPink;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.util.ButtonMap;
import mod.casinocraft.util.Card;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenMinoPink extends ScreenCasino {   // FanTan

    // ...





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenMinoPink(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }





    //----------------------------------------BASIC----------------------------------------//

    public LogicMinoPink logic(){
        return (LogicMinoPink) menu.logic();
    }

    protected String getGameName() {
        return "fantan";
    }

    protected void createGameButtons(){
        buttonSet.addButton(ButtonMap.POS_BOT_MIDDLE, ButtonMap.PLACE,   () -> isActivePlayer() && logic().turnstate == 2 && !logic().hasPlaced,                       () -> { action(-1);               } );
        buttonSet.addButton(ButtonMap.POS_BOT_LEFT,   ButtonMap.ANOTHER, () -> isActivePlayer() && logic().turnstate == 2 &&  logic().hasPlaced && playerToken >= bet, () -> { action(-2); collectBet(); } );
        buttonSet.addButton(ButtonMap.POS_BOT_RIGHT,  ButtonMap.WAIT,    () -> isActivePlayer() && logic().turnstate == 2 &&  logic().hasPlaced,                       () -> { action(-3);               } );
    }





    //----------------------------------------INPUT----------------------------------------//

    protected void interact(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && !logic().hasPlaced){
            for(int x = 0; x < 4; x++){
                if(mouseRect(64 + 32*x, 32, 32, 32, mouseX, mouseY)) {
                    action(x);
                }
            }
        }
    }





    //----------------------------------------DRAW----------------------------------------//

    protected void drawForegroundLayer(GuiGraphics matrix, int mouseX, int mouseY){
        if(logic().turnstate == 2){ drawTimer(matrix); }
    }

    protected void drawBackgroundLayer(GuiGraphics matrix, float partialTicks, int mouseX, int mouseY){
        drawBackground(matrix, CasinoKeeper.TEXTURE_FANTAN);

        // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_DICE);
        if(logic().turnstate == 2 && logic().selector.X > -1){
            matrix.blit(CasinoKeeper.TEXTURE_DICE, leftPos+64 + 32*logic().selector.X, topPos+32, 224, 0, 32, 32);
        }
        for(int i = 0; i < 4; i++){
            int color = logic().grid[i][0];
            if(            color == -1){ matrix.blit(CasinoKeeper.TEXTURE_DICE, leftPos+64 + 32*i, topPos+32, 224,                   224, 32, 32); }
            if(logic().grid[i][0] >  0){ matrix.blit(CasinoKeeper.TEXTURE_DICE, leftPos+64 + 32*i, topPos+32, 192, 32*logic().grid[i][0], 32, 32); }
        }

        // RenderSystem.setShaderTexture(0, CasinoKeeper.TEXTURE_MINOS);
        for(Card v : logic().chips){
            drawMino(matrix, v.number - 12, v.suit - 12);
        }
    }





    //----------------------------------------SUPPORT----------------------------------------//

    // ...



}
