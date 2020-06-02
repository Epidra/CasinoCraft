package net.casinocraft.mod.gui;

import java.io.IOException;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerVideoPoker;
import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiVideoPoker extends GuiContainer{
	private ResourceLocation textureGround = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/Arcade.png");
	private ResourceLocation textureExtra  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/VideoPokerExtra.png");
	private ResourceLocation textureHerz   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsHerz.png");
	private ResourceLocation textureKaro   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsKaro.png");
	private ResourceLocation textureKreuz  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsKreuz.png");
	private ResourceLocation texturePik    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsPik.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityVideoPoker tileFurnace;
    
    public GuiVideoPoker(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerVideoPoker(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntityVideoPoker) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	if(guiLeft+82 < mouseX && mouseX < guiLeft+82+92 && guiTop+164 < mouseY && mouseY < guiTop+164+26){ // Button
    			if(tileFurnace.end) {
    				tileFurnace.Click_Start(0);
    			}else{ 
    				tileFurnace.Click_Start(1);
    			}
        	}
    		if(guiLeft+5 < mouseX && mouseX < guiLeft+5+46 && guiTop+16 < mouseY && mouseY < guiTop+16+26){ // Hold 1
    			if(!tileFurnace.end) tileFurnace.Click_Hold(1);
    		}
    		if(guiLeft+55 < mouseX && mouseX < guiLeft+55+46 && guiTop+16 < mouseY && mouseY < guiTop+16+26){ // Hold 2
    			if(!tileFurnace.end) tileFurnace.Click_Hold(2);
    		}
    		if(guiLeft+105 < mouseX && mouseX < guiLeft+105+46 && guiTop+16 < mouseY && mouseY < guiTop+16+26){ // Hold 3
    			if(!tileFurnace.end) tileFurnace.Click_Hold(3);
    		}
    		if(guiLeft+155 < mouseX && mouseX < guiLeft+155+46 && guiTop+16 < mouseY && mouseY < guiTop+16+26){ // Hold 4
    			if(!tileFurnace.end) tileFurnace.Click_Hold(4);
    		}
    		if(guiLeft+205 < mouseX && mouseX < guiLeft+205+46 && guiTop+16 < mouseY && mouseY < guiTop+16+26){ // Hold 5
    			if(!tileFurnace.end) tileFurnace.Click_Hold(5);
    		}
        }
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	
    	if(tileFurnace.end) {
    		this.fontRendererObj.drawString(tileFurnace.hand, 90, 150, 9999999);
        }
    }
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(textureGround);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize); // Background
        
        if(tileFurnace.card1 != null){
        	this.mc.getTextureManager().bindTexture(textureHerz);
    		if(tileFurnace.card1.suit == 0) drawTexturedModalRect(guiLeft+7,   guiTop+72, POS(true,tileFurnace.card1.number), POS(false,tileFurnace.card1.number), 42, 57); // Card 1 Herz
    		if(tileFurnace.card2.suit == 0) drawTexturedModalRect(guiLeft+57,  guiTop+72, POS(true,tileFurnace.card2.number), POS(false,tileFurnace.card2.number), 42, 57); // Card 2 Herz
    		if(tileFurnace.card3.suit == 0) drawTexturedModalRect(guiLeft+107, guiTop+72, POS(true,tileFurnace.card3.number), POS(false,tileFurnace.card3.number), 42, 57); // Card 3 Herz
    		if(tileFurnace.card4.suit == 0) drawTexturedModalRect(guiLeft+157, guiTop+72, POS(true,tileFurnace.card4.number), POS(false,tileFurnace.card4.number), 42, 57); // Card 4 Herz
    		if(tileFurnace.card5.suit == 0) drawTexturedModalRect(guiLeft+207, guiTop+72, POS(true,tileFurnace.card5.number), POS(false,tileFurnace.card5.number), 42, 57); // Card 5 Herz
    		this.mc.getTextureManager().bindTexture(textureKaro);
    		if(tileFurnace.card1.suit == 1) drawTexturedModalRect(guiLeft+7,   guiTop+72, POS(true,tileFurnace.card1.number), POS(false,tileFurnace.card1.number), 42, 57); // Card 1 Karo
    		if(tileFurnace.card2.suit == 1) drawTexturedModalRect(guiLeft+57,  guiTop+72, POS(true,tileFurnace.card2.number), POS(false,tileFurnace.card2.number), 42, 57); // Card 2 Karo
    		if(tileFurnace.card3.suit == 1) drawTexturedModalRect(guiLeft+107, guiTop+72, POS(true,tileFurnace.card3.number), POS(false,tileFurnace.card3.number), 42, 57); // Card 3 Karo
    		if(tileFurnace.card4.suit == 1) drawTexturedModalRect(guiLeft+157, guiTop+72, POS(true,tileFurnace.card4.number), POS(false,tileFurnace.card4.number), 42, 57); // Card 4 Karo
    		if(tileFurnace.card5.suit == 1) drawTexturedModalRect(guiLeft+207, guiTop+72, POS(true,tileFurnace.card5.number), POS(false,tileFurnace.card5.number), 42, 57); // Card 5 Karo
    		this.mc.getTextureManager().bindTexture(textureKreuz);
    		if(tileFurnace.card1.suit == 2) drawTexturedModalRect(guiLeft+7,   guiTop+72, POS(true,tileFurnace.card1.number), POS(false,tileFurnace.card1.number), 42, 57); // Card 1 Kreuz
    		if(tileFurnace.card2.suit == 2) drawTexturedModalRect(guiLeft+57,  guiTop+72, POS(true,tileFurnace.card2.number), POS(false,tileFurnace.card2.number), 42, 57); // Card 2 Kreuz
    		if(tileFurnace.card3.suit == 2) drawTexturedModalRect(guiLeft+107, guiTop+72, POS(true,tileFurnace.card3.number), POS(false,tileFurnace.card3.number), 42, 57); // Card 3 Kreuz
    		if(tileFurnace.card4.suit == 2) drawTexturedModalRect(guiLeft+157, guiTop+72, POS(true,tileFurnace.card4.number), POS(false,tileFurnace.card4.number), 42, 57); // Card 4 Kreuz
    		if(tileFurnace.card5.suit == 2) drawTexturedModalRect(guiLeft+207, guiTop+72, POS(true,tileFurnace.card5.number), POS(false,tileFurnace.card5.number), 42, 57); // Card 5 Kreuz
    		this.mc.getTextureManager().bindTexture(texturePik);
    		if(tileFurnace.card1.suit == 3) drawTexturedModalRect(guiLeft+7,   guiTop+72, POS(true,tileFurnace.card1.number), POS(false,tileFurnace.card1.number), 42, 57); // Card 1 Pik
    		if(tileFurnace.card2.suit == 3) drawTexturedModalRect(guiLeft+57,  guiTop+72, POS(true,tileFurnace.card2.number), POS(false,tileFurnace.card2.number), 42, 57); // Card 2 Pik
    		if(tileFurnace.card3.suit == 3) drawTexturedModalRect(guiLeft+107, guiTop+72, POS(true,tileFurnace.card3.number), POS(false,tileFurnace.card3.number), 42, 57); // Card 3 Pik
    		if(tileFurnace.card4.suit == 3) drawTexturedModalRect(guiLeft+157, guiTop+72, POS(true,tileFurnace.card4.number), POS(false,tileFurnace.card4.number), 42, 57); // Card 4 Pik
    		if(tileFurnace.card5.suit == 3) drawTexturedModalRect(guiLeft+207, guiTop+72, POS(true,tileFurnace.card5.number), POS(false,tileFurnace.card5.number), 42, 57); // Card 5 Pik
        }
        
        this.mc.getTextureManager().bindTexture(textureExtra);
        if(tileFurnace.end){
			drawTexturedModalRect(guiLeft+82, guiTop+164, 92, 26, 92, 26); // Button New Game
		}
		if(!tileFurnace.end){
			drawTexturedModalRect(guiLeft+82, guiTop+164, 118, 0, 92, 26); // Button Finish
			if(!tileFurnace.hold1){drawTexturedModalRect(guiLeft+5,   guiTop+16, 26, 0, 46, 26);}else{drawTexturedModalRect(guiLeft+5,   guiTop+16, 72, 0, 46, 26);} // Button Hold 1
			if(!tileFurnace.hold2){drawTexturedModalRect(guiLeft+55,  guiTop+16, 26, 0, 46, 26);}else{drawTexturedModalRect(guiLeft+55,  guiTop+16, 72, 0, 46, 26);} // Button Hold 2
			if(!tileFurnace.hold3){drawTexturedModalRect(guiLeft+105, guiTop+16, 26, 0, 46, 26);}else{drawTexturedModalRect(guiLeft+105, guiTop+16, 72, 0, 46, 26);} // Button Hold 3
			if(!tileFurnace.hold4){drawTexturedModalRect(guiLeft+155, guiTop+16, 26, 0, 46, 26);}else{drawTexturedModalRect(guiLeft+155, guiTop+16, 72, 0, 46, 26);} // Button Hold 4
			if(!tileFurnace.hold5){drawTexturedModalRect(guiLeft+205, guiTop+16, 26, 0, 46, 26);}else{drawTexturedModalRect(guiLeft+205, guiTop+16, 72, 0, 46, 26);} // Button Hold 5
		}
    }
    
    private int POS(boolean xy, int i){
		if(xy){
			if(i == 0 || i ==  6 || i == 12) return 0;
			if(i == 1 || i ==  7 || i == 13) return 42;
			if(i == 2 || i ==  8) return 84;
			if(i == 3 || i ==  9) return 126;
			if(i == 4 || i == 10) return 168;
			if(i == 5 || i == 11) return 210;
		}else{
			if(i <  6) return 0;
			if(i < 12) return 57;
			return 114;
		}
		return 0;
	}
    
}