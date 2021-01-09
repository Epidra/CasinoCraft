package mod.casinocraft.screen.other;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.screen.ScreenCasino;
import mod.casinocraft.logic.other.LogicSlotGame;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static mod.casinocraft.util.KeyMap.*;

public class ScreenSlotGame extends ScreenCasino {   // Slot Game

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenSlotGame(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicSlotGame logic(){
        return (LogicSlotGame) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            if(keyCode == KEY_ENTER && logic().multiplier < 5){
                if(playerToken >= bet){
                    collectBet();
                    playerToken = -1;
                    action(0);
                }
            }
            if(keyCode == KEY_SPACE){
                action(1);
            }
        } else {
            if(keyCode == KEY_SPACE){
                action(1);
            }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(MatrixStack matrixstack, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            this.font.drawString(matrixstack, "SPACE to SPIN ",  128, 210, 16777215);
            this.font.drawString(matrixstack, "ENTER for TOKEN", 128, 225, 16777215);
        } else if(logic().turnstate == 3){
            this.font.drawString(matrixstack, "SPACE to HOLD", 128, 210, 16777215);
        } else {
            this.font.drawString(matrixstack, "ENTER to RESET", 128, 210, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTGAME);

        // Multiplier
        int m = logic().multiplier;
        this.blit(matrixstack, guiLeft + 8, guiTop +  34, 192 + (m >= 4 ? 32 : 0), 48, 32, 16);
        this.blit(matrixstack, guiLeft + 8, guiTop +  69, 192 + (m >= 2 ? 32 : 0), 16, 32, 16);
        this.blit(matrixstack, guiLeft + 8, guiTop + 104, 192 + (m >= 1 ? 32 : 0),  0, 32, 16);
        this.blit(matrixstack, guiLeft + 8, guiTop + 139, 192 + (m >= 3 ? 32 : 0), 32, 32, 16);
        this.blit(matrixstack, guiLeft + 8, guiTop + 174, 192 + (m >= 5 ? 32 : 0), 64, 32, 16);

        // Icons
        drawIcon(matrixstack, guiLeft +  48, guiTop + 40, 0);
        drawIcon(matrixstack, guiLeft + 104, guiTop + 40, 1);
        drawIcon(matrixstack, guiLeft + 160, guiTop + 40, 2);

        // Lines
        if(logic().lines[0]){
            this.blit(matrixstack, guiLeft +  48, guiTop +  88, 0, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 104, guiTop +  88, 0, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 160, guiTop +  88, 0, 192, 48, 48);
        }
        if(logic().lines[1]){
            this.blit(matrixstack, guiLeft +  48, guiTop +  44, 48, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 104, guiTop +  44, 48, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 160, guiTop +  44, 48, 192, 48, 48);
        }
        if(logic().lines[2]){
            this.blit(matrixstack, guiLeft +  48, guiTop + 136, 96, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 104, guiTop + 136, 96, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 160, guiTop + 136, 96, 192, 48, 48);
        }
        if(logic().lines[3]){
            this.blit(matrixstack, guiLeft +  48, guiTop +  40, 144, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 104, guiTop +  88, 144, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 160, guiTop +  136, 144, 192, 48, 48);
        }
        if(logic().lines[4]){
            this.blit(matrixstack, guiLeft +  48, guiTop +  136, 192, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 104, guiTop +  88, 192, 192, 48, 48);
            this.blit(matrixstack, guiLeft + 160, guiTop +  40, 192, 192, 48, 48);
        }

        // Shadows
        this.blit(matrixstack, guiLeft +  48, guiTop +  40, 96,  96, 48, 48);
        this.blit(matrixstack, guiLeft + 104, guiTop +  40, 96,  96, 48, 48);
        this.blit(matrixstack, guiLeft + 160, guiTop +  40, 96,  96, 48, 48);
        this.blit(matrixstack, guiLeft +  48, guiTop + 136, 96, 144, 48, 48);
        this.blit(matrixstack, guiLeft + 104, guiTop + 136, 96, 144, 48, 48);
        this.blit(matrixstack, guiLeft + 160, guiTop + 136, 96, 144, 48, 48);


        //spriteBatch.Draw(SK.texture_background_slotmachine1, SK.Position_DisplayEdge() + new Vector2(52, 0), Color.White);
        //if(turnstate == Turnstate.TURN_PLAYER) {
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(177, 200), new Rectangle(0, 0, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(177, 350), new Rectangle(0, 0, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(302, 200), new Rectangle(0, 0, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(302, 350), new Rectangle(0, 0, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(427, 200), new Rectangle(0, 0, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(427, 350), new Rectangle(0, 0, 100, 100), Color.White);
        //}
        //if(turnstate >= Turnstate.TURN_COMPUTER) {
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(177, 100 +   3 * 2 - (wheelPos[0] % 100) + 50), new Rectangle(Get_Value((wheelPos[0] / 100 + 0) % 9 + 0 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(177, 100 +  53 * 2 - (wheelPos[0] % 100) + 50), new Rectangle(Get_Value((wheelPos[0] / 100 + 1) % 9 + 0 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(177, 100 + 103 * 2 - (wheelPos[0] % 100) + 50), new Rectangle(Get_Value((wheelPos[0] / 100 + 2) % 9 + 0 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(177, 100 + 153 * 2 - (wheelPos[0] % 100) + 50), new Rectangle(Get_Value((wheelPos[0] / 100 + 3) % 9 + 0 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(177, 100 + 203 * 2 - (wheelPos[0] % 100) + 50), new Rectangle(Get_Value((wheelPos[0] / 100 + 4) % 9 + 0 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(302, 100 +   3 * 2 - (wheelPos[1] % 100) + 50), new Rectangle(Get_Value((wheelPos[1] / 100 + 0) % 9 + 1 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(302, 100 +  53 * 2 - (wheelPos[1] % 100) + 50), new Rectangle(Get_Value((wheelPos[1] / 100 + 1) % 9 + 1 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(302, 100 + 103 * 2 - (wheelPos[1] % 100) + 50), new Rectangle(Get_Value((wheelPos[1] / 100 + 2) % 9 + 1 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(302, 100 + 153 * 2 - (wheelPos[1] % 100) + 50), new Rectangle(Get_Value((wheelPos[1] / 100 + 3) % 9 + 1 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(302, 100 + 203 * 2 - (wheelPos[1] % 100) + 50), new Rectangle(Get_Value((wheelPos[1] / 100 + 4) % 9 + 1 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(427, 100 +   3 * 2 - (wheelPos[2] % 100) + 50), new Rectangle(Get_Value((wheelPos[2] / 100 + 0) % 9 + 2 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(427, 100 +  53 * 2 - (wheelPos[2] % 100) + 50), new Rectangle(Get_Value((wheelPos[2] / 100 + 1) % 9 + 2 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(427, 100 + 103 * 2 - (wheelPos[2] % 100) + 50), new Rectangle(Get_Value((wheelPos[2] / 100 + 2) % 9 + 2 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(427, 100 + 153 * 2 - (wheelPos[2] % 100) + 50), new Rectangle(Get_Value((wheelPos[2] / 100 + 3) % 9 + 2 * 9) * 100, 100, 100, 100), Color.White);
        //    spriteBatch.Draw(SK.texture_spritesheet_slotwheel, SK.Position_DisplayEdge() + new Vector2(427, 100 + 203 * 2 - (wheelPos[2] % 100) + 50), new Rectangle(Get_Value((wheelPos[2] / 100 + 4) % 9 + 2 * 9) * 100, 100, 100, 100), Color.White);
        //}
        //spriteBatch.Draw(SK.texture_background_slotmachine2, SK.Position_DisplayEdge() + new Vector2(52, 0), Color.White);
    }

    protected void drawGuiContainerBackgroundLayer3(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void drawIcon(MatrixStack matrixstack, int posX, int posY, int index){
        for(int i = 0; i < 4; i++){
            int z = logic().grid[(logic().wheelPos[index]/48 + i) % 9][index];
            int mod = logic().wheelPos[index] % 48;
            int x = z < 2 ? z : z - 2;
            int y = z < 2 ? 3 : 1;
            if(i == 0) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48) + mod, x*48, y*48 + mod, 48, 48 - mod);
            if(i == 1) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48);
            if(i == 2) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48);
            if(i == 3) this.blit(matrixstack, posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48 - (48 - mod));
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "";
    }

}
