package mod.casinocraft.gui.other;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.mino.ContainerMinoBlack;
import mod.casinocraft.container.other.ContainerSlotGame;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.other.LogicDummy;
import mod.casinocraft.logic.other.LogicSlotGame;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiSlotGame extends GuiCasino {   // Slot Game

    // ...




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public GuiSlotGame(InventoryPlayer playerInv, IInventory furnaceInv, BlockPos pos, World world) {
        super(new ContainerSlotGame(playerInv, furnaceInv, pos, world), playerInv);
    }




    //----------------------------------------LOGIC----------------------------------------//

    public LogicSlotGame logic(){
        return (LogicSlotGame) CONTAINER.logic();
    }




    //----------------------------------------INPUT----------------------------------------//

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(char typedChar, int keyCode) throws IOException {
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            if(keyCode == Keyboard.KEY_RETURN && logic().multiplier < 5){
                if(playerToken >= bet){
                    collectBet();
                    playerToken = -1;
                    action(0);
                }
            }
            if(keyCode == Keyboard.KEY_SPACE){
                action(1);
            }
        } else {
            if(keyCode == Keyboard.KEY_SPACE){
                action(1);
            }
        }
    }




    //----------------------------------------DRAW----------------------------------------//

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        if(logic().turnstate == 2){
            this.fontRenderer.drawString("SPACE to SPIN ",  128, 210, 16777215);
            this.fontRenderer.drawString("ENTER for TOKEN", 128, 225, 16777215);
        } else if(logic().turnstate == 3){
            this.fontRenderer.drawString("SPACE to HOLD", 128, 210, 16777215);
        } else {
            this.fontRenderer.drawString("ENTER to RESET", 128, 210, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(CONTAINER.logic() instanceof LogicDummy){ return; }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTGAME);

        // Multiplier
        int m = logic().multiplier;
        drawTexturedModalRect(guiLeft + 8, guiTop +  34, 192 + (m >= 4 ? 32 : 0), 48, 32, 16);
        drawTexturedModalRect(guiLeft + 8, guiTop +  69, 192 + (m >= 2 ? 32 : 0), 16, 32, 16);
        drawTexturedModalRect(guiLeft + 8, guiTop + 104, 192 + (m >= 1 ? 32 : 0),  0, 32, 16);
        drawTexturedModalRect(guiLeft + 8, guiTop + 139, 192 + (m >= 3 ? 32 : 0), 32, 32, 16);
        drawTexturedModalRect(guiLeft + 8, guiTop + 174, 192 + (m >= 5 ? 32 : 0), 64, 32, 16);

        // Icons
        drawIcon(guiLeft +  48, guiTop + 40, 0);
        drawIcon(guiLeft + 104, guiTop + 40, 1);
        drawIcon(guiLeft + 160, guiTop + 40, 2);

        // Lines
        if(logic().lines[0]){
            drawTexturedModalRect(guiLeft +  48, guiTop +  88, 0, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 104, guiTop +  88, 0, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 160, guiTop +  88, 0, 192, 48, 48);
        }
        if(logic().lines[1]){
            drawTexturedModalRect(guiLeft +  48, guiTop +  44, 48, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 104, guiTop +  44, 48, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 160, guiTop +  44, 48, 192, 48, 48);
        }
        if(logic().lines[2]){
            drawTexturedModalRect(guiLeft +  48, guiTop + 136, 96, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 104, guiTop + 136, 96, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 160, guiTop + 136, 96, 192, 48, 48);
        }
        if(logic().lines[3]){
            drawTexturedModalRect(guiLeft +  48, guiTop +  40, 144, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 104, guiTop +  88, 144, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 160, guiTop +  136, 144, 192, 48, 48);
        }
        if(logic().lines[4]){
            drawTexturedModalRect(guiLeft +  48, guiTop +  136, 192, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 104, guiTop +  88, 192, 192, 48, 48);
            drawTexturedModalRect(guiLeft + 160, guiTop +  40, 192, 192, 48, 48);
        }

        // Shadows
        drawTexturedModalRect(guiLeft +  48, guiTop +  40, 96,  96, 48, 48);
        drawTexturedModalRect(guiLeft + 104, guiTop +  40, 96,  96, 48, 48);
        drawTexturedModalRect(guiLeft + 160, guiTop +  40, 96,  96, 48, 48);
        drawTexturedModalRect(guiLeft +  48, guiTop + 136, 96, 144, 48, 48);
        drawTexturedModalRect(guiLeft + 104, guiTop + 136, 96, 144, 48, 48);
        drawTexturedModalRect(guiLeft + 160, guiTop + 136, 96, 144, 48, 48);


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

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY) {

    }




    //----------------------------------------CUSTOM----------------------------------------//

    private void drawIcon(int posX, int posY, int index){
        for(int i = 0; i < 4; i++){
            int z = logic().grid[(logic().wheelPos[index]/48 + i) % 9][index];
            int mod = logic().wheelPos[index] % 48;
            int x = z < 2 ? z : z - 2;
            int y = z < 2 ? 3 : 1;
            if(i == 0) drawTexturedModalRect(posX, posY + i*48 - (logic().wheelPos[index] % 48) + mod, x*48, y*48 + mod, 48, 48 - mod);
            if(i == 1) drawTexturedModalRect(posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48);
            if(i == 2) drawTexturedModalRect(posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48);
            if(i == 3) drawTexturedModalRect(posX, posY + i*48 - (logic().wheelPos[index] % 48), x*48, y*48, 48, 48 - (48 - mod));
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    protected String getGameName() {
        return "";
    }

}
