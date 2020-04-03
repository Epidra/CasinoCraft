package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.clay.LogicFanTan;
import mod.casinocraft.util.Card;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiFanTan extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiFanTan(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicFanTan logic(){
        return (LogicFanTan) CONTAINER.logic();
    }

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

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_FANTAN);
        this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize); // Background SMALL

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_CASINO);
        if(logic().turnstate == 2 || logic().turnstate == 3){
            this.blit(guiLeft+64 + 32*logic().selector.X, guiTop+32, 192, 224, 32, 32);
        } else {
            this.blit(guiLeft+64 + 32*logic().selector.X, guiTop+32, 192, logic().reward == 2 ? 128 : 32, 32, 32);
        }

        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        for(Card v : logic().chips){
            blit(guiLeft + v.number - 12, guiTop + v.suit - 12, 168, 208, 24, 24);
        }
    }

    protected void drawGuiContainerBackgroundLayer3(float partialTicks, int mouseX, int mouseY){
        if(logic().turnstate == 2){
            this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            blit(guiLeft+89, guiTop+206, 78, 44, 78, 22); // Button SPIN
        }
    }

}
