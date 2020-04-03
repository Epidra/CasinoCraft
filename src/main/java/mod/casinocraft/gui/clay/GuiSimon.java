package mod.casinocraft.gui.clay;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.logic.LogicBase;
import mod.casinocraft.logic.clay.LogicSimon;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiSimon extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiSimon(ContainerCasino container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
    }



    //--------------------BASIC--------------------

    public LogicSimon logic(){
        return (LogicSimon) CONTAINER.logic();
    }

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if(logic().turnstate == 2 && mouseButton == 0){
            if(logic().color_player.size() < logic().color_simon.size()){
                for(int y = 0; y < 2; y++){
                    for(int x = 0; x < 2; x++){
                        if(mouseRect(64 + 64*x, 64 + 64*y, 64, 64, mouseX, mouseY)) {
                            action(y*2 + x);
                        }
                    }
                }
            }
        }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        drawString("POINTS",           75, 25);
        drawString("" + logic().scorePoint, 85, 35);
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_SIMON);
        this.blit(guiLeft +  64, guiTop +  64,   0, logic().alpha[0] > 1 ? 64 : 128, 64, 64);
        this.blit(guiLeft + 128, guiTop +  64,  64, logic().alpha[1] > 1 ? 64 : 128, 64, 64);
        this.blit(guiLeft +  64, guiTop + 128, 128, logic().alpha[2] > 1 ? 64 : 128, 64, 64);
        this.blit(guiLeft + 128, guiTop + 128, 192, logic().alpha[3] > 1 ? 64 : 128, 64, 64);
        this.minecraft.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
        for(int i = 0; i < logic().color_player.size(); i++){
            drawMino(guiLeft + 22, guiTop + 22 + 12*i, logic().color_player.get(i));
        }
        for(int i = 0; i < logic().color_simon.size(); i++){
            drawMino(guiLeft + 256-16-12-6, guiTop + 22 + 12*i, logic().turnstate <= 3 ? 4 : logic().color_simon.get(i));
        }
    }

    public void drawMino(int posX, int posY, int index){
        if(index == 0){
            blit(posX, posY, 48, 180, 12, 12);
        }
        if(index == 1){
            blit(posX, posY, 24, 180, 12, 12);
        }
        if(index == 2){
            blit(posX, posY, 72, 180, 12, 12);
        }
        if(index == 3){
            blit(posX, posY, 60, 180, 12, 12);
        }
        if(index == 4){
            blit(posX, posY, 0, 180, 12, 12);
        }
    }

    //protected override void Draw_Animation() {
//        spriteBatch.Draw(SK.texture_static_simon_red, SK.Position_Simon(), Get_Color(0));
//        spriteBatch.Draw(SK.texture_static_simon_blue, SK.Position_Simon(), Get_Color(1));
//        spriteBatch.Draw(SK.texture_static_simon_green, SK.Position_Simon(), Get_Color(2));
//        spriteBatch.Draw(SK.texture_static_simon_yellow, SK.Position_Simon(), Get_Color(3));
//
//        spriteBatch.Draw(SK.texture_static_simon_console, SK.Position_Simon(), Color.White);
//
//        int index = 0;
//
//        foreach(int c in color_player) {
//        spriteBatch.Draw(SK.texture_spritesheet_minos_32x, SK.Position_Simon_Player() + new Vector2(0, 35 * index), Get_Mino(c), Color.White);
//        index++;
//        }
//
//        index = 0;
//
//        foreach(int c in color_simon) {
//        spriteBatch.Draw(SK.texture_spritesheet_minos_32x, SK.Position_Simon_Simon() + new Vector2(0, 35 * index), Get_Mino(result ? c : -1), Color.White);
//        index++;
//        }
//        }
//
//private Rectangle Get_Mino(int c) {
//        switch(c) {
//        case 0: return Get_Mino_Texture(Mino.FIRE, 0, 32);
//        case 1: return Get_Mino_Texture(Mino.WATER, 0, 32);
//        case 2: return Get_Mino_Texture(Mino.NATURE, 0, 32);
//        case 3: return Get_Mino_Texture(Mino.THUNDER, 0, 32);
//        }
//        return Get_Mino_Texture(Mino.BLANK, 0, 32);
//        }
//
//private Color Get_Color(int c) {
//        return new Color(new Vector4(0.5f + alpha[c] / 2, 0.5f + alpha[c] / 2, 0.5f + alpha[c] / 2, 100));
//        }
//
//

}
