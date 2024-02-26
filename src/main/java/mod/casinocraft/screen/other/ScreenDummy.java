package mod.casinocraft.screen.other;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.casinocraft.logic.LogicModule;
import mod.casinocraft.menu.MenuCasino;
import mod.casinocraft.screen.ScreenCasino;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenDummy extends ScreenCasino {   //  Dummy
    
    // ...
    
    
    
    
    
    // ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
    
    public ScreenDummy(MenuCasino container, Inventory player, Component name) {
        super(container, player, name);
    }
    
    
    
    
    
    // ---------- ---------- ---------- ----------  BASIC  ---------- ---------- ---------- ---------- //
    
    public LogicModule logic(){
        return menu.logic();
    }
    
    protected void createGameButtons(){
    
    }
    
    
    
    
    
    // ---------- ---------- ---------- ----------  INPUT  ---------- ---------- ---------- ---------- //
    
    protected void interact(double mouseX, double mouseY, int mouseButton){
    
    }
    
    
    
    
    
    // ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
    
    protected void drawForegroundLayer(PoseStack matrix, int mouseX, int mouseY){
    
    }
    
    protected void drawBackgroundLayer(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    
    }
    
    
    
    
    
    // ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
    
    // ...
    
    
    
}
