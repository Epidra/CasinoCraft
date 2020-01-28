package mod.casinocraft.gui.other;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.gui.GuiCasino;
import net.java.games.input.Keyboard;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

import java.awt.event.KeyEvent;

public class GuiSlotMachine extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSlotMachine(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_TETRIS);
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){

    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KeyEvent.VK_ENTER){ actionTouch(-1); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTWHEEL);
        if(tc.turnstate == 2){
            this.drawTexturedModalRect(guiLeft +  25, guiTop+ 25, 0, 0, 50, 50);
            this.drawTexturedModalRect(guiLeft +  25, guiTop+100, 0, 0, 50, 50);
            this.drawTexturedModalRect(guiLeft + 100, guiTop+ 25, 0, 0, 50, 50);
            this.drawTexturedModalRect(guiLeft + 100, guiTop+100, 0, 0, 50, 50);
            this.drawTexturedModalRect(guiLeft + 175, guiTop+ 25, 0, 0, 50, 50);
            this.drawTexturedModalRect(guiLeft + 175, guiTop+100, 0, 0, 50, 50);
        }
        if(tc.turnstate >= 3){
            this.drawTexturedModalRect(guiLeft +  35, guiTop +   3 - (getValue(-1) % 50)+25, getValue((getValue(-1) / 50 + 0) % 9 + 0*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft +  35, guiTop +  53 - (getValue(-1) % 50)+25, getValue((getValue(-1) / 50 + 1) % 9 + 0*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft +  35, guiTop + 103 - (getValue(-1) % 50)+25, getValue((getValue(-1) / 50 + 2) % 9 + 0*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft +  35, guiTop + 153 - (getValue(-1) % 50)+25, getValue((getValue(-1) / 50 + 3) % 9 + 0*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft +  35, guiTop + 203 - (getValue(-1) % 50)+25, getValue((getValue(-1) / 50 + 4) % 9 + 0*9)*50, 50, 50, 50);

            this.drawTexturedModalRect(guiLeft + 100, guiTop +   3 - (getValue(-2) % 50)+25, getValue((getValue(-2) / 50 + 0) % 9 + 1*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 100, guiTop +  53 - (getValue(-2) % 50)+25, getValue((getValue(-2) / 50 + 1) % 9 + 1*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 100, guiTop + 103 - (getValue(-2) % 50)+25, getValue((getValue(-2) / 50 + 2) % 9 + 1*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 100, guiTop + 153 - (getValue(-2) % 50)+25, getValue((getValue(-2) / 50 + 3) % 9 + 1*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 100, guiTop + 203 - (getValue(-2) % 50)+25, getValue((getValue(-2) / 50 + 4) % 9 + 1*9)*50, 50, 50, 50);

            this.drawTexturedModalRect(guiLeft + 165, guiTop +   3 - (getValue(-3) % 50)+25, getValue((getValue(-3) / 50 + 0) % 9 + 2*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 165, guiTop +  53 - (getValue(-3) % 50)+25, getValue((getValue(-3) / 50 + 1) % 9 + 2*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 165, guiTop + 103 - (getValue(-3) % 50)+25, getValue((getValue(-3) / 50 + 2) % 9 + 2*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 165, guiTop + 153 - (getValue(-3) % 50)+25, getValue((getValue(-3) / 50 + 3) % 9 + 2*9)*50, 50, 50, 50);
            this.drawTexturedModalRect(guiLeft + 165, guiTop + 203 - (getValue(-3) % 50)+25, getValue((getValue(-3) / 50 + 4) % 9 + 2*9)*50, 50, 50, 50);
        }
        this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SLOTMACHINE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }



    //--------------------CUSTOM--------------------

}
