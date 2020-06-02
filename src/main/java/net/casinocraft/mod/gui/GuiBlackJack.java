package net.casinocraft.mod.gui;

import java.io.IOException;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerArcade;
import net.casinocraft.mod.container.ContainerBlackJack;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityBlackJack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBlackJack extends GuiContainer{
	private ResourceLocation textureGroundBlack     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableBlack.png");
	private ResourceLocation textureGroundBlue      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableBlue.png");
	private ResourceLocation textureGroundBrown     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableBrown.png");
	private ResourceLocation textureGroundCyan      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableCyan.png");
	private ResourceLocation textureGroundGray      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableGray.png");
	private ResourceLocation textureGroundGreen     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableGreen.png");
	private ResourceLocation textureGroundLightBlue = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableLightBlue.png");
	private ResourceLocation textureGroundLime      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableLime.png");
	private ResourceLocation textureGroundMagenta   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableMagenta.png");
	private ResourceLocation textureGroundOrange    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableOrange.png");
	private ResourceLocation textureGroundPink      = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTablePink.png");
	private ResourceLocation textureGroundPurple    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTablePurple.png");
	private ResourceLocation textureGroundRed       = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableRed.png");
	private ResourceLocation textureGroundSilver    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableSilver.png");
	private ResourceLocation textureGroundWhite     = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableWhite.png");
	private ResourceLocation textureGroundYellow    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardTableYellow.png");
	
	private ResourceLocation textureExtra  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/BlackJackExtra.png");
	private ResourceLocation textureHerz   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsHerz.png");
	private ResourceLocation textureKaro   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsKaro.png");
	private ResourceLocation textureKreuz  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsKreuz.png");
	private ResourceLocation texturePik    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsPik.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityBlackJack tileFurnace;
    
    public GuiBlackJack(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerBlackJack(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntityBlackJack) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	if(!tileFurnace.end && guiLeft+4   < mouseX && mouseX < guiLeft+4+92   && guiTop+166 < mouseY && mouseY < guiTop+166+26){ tileFurnace.Click_Hit();    } else
			if( tileFurnace.end && guiLeft+82  < mouseX && mouseX < guiLeft+82+92  && guiTop+161 < mouseY && mouseY < guiTop+161+26){ tileFurnace.Click_Start(0); } else
			if(!tileFurnace.end && guiLeft+160 < mouseX && mouseX < guiLeft+160+92 && guiTop+166 < mouseY && mouseY < guiTop+166+26){ tileFurnace.Click_Stand();  }
        }
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	
    	this.fontRendererObj.drawString("" + tileFurnace.value_player,  50, 5, 4210752);
        this.fontRendererObj.drawString("" + tileFurnace.value_dealer, 150, 5, 4210752);
    	
    	if(tileFurnace.end) {
            if(tileFurnace.value_dealer > 21) {
            	this.fontRendererObj.drawString("The House gone bust!", 100, 100, 4210752);
            } else if(tileFurnace.value_player > 21) {
            	this.fontRendererObj.drawString("The Player gone bust!", 100, 100, 4210752);
            } else if(tileFurnace.value_player == tileFurnace.value_dealer && tileFurnace.cards_player.size() > tileFurnace.cards_dealer.size()) {
            	this.fontRendererObj.drawString("The Player wins!", 100, 100, 4210752);
            } else if(tileFurnace.value_player == tileFurnace.value_dealer && tileFurnace.cards_player.size() == tileFurnace.cards_dealer.size()) {
            	this.fontRendererObj.drawString("DRAW", 100, 100, 4210752);
            } else if(tileFurnace.value_player == 21 && tileFurnace.cards_player.size() == 2) {
            	this.fontRendererObj.drawString("BLACK JACK", 100, 100, 4210752);
            } else if(tileFurnace.value_player == tileFurnace.value_dealer && tileFurnace.cards_player.size() < tileFurnace.cards_dealer.size()) {
            	this.fontRendererObj.drawString("The House wins!", 100, 100, 4210752);
            } else if(tileFurnace.value_player > tileFurnace.value_dealer) {
            	this.fontRendererObj.drawString("The Player wins!", 100, 100, 4210752);
            } else {
            	this.fontRendererObj.drawString("The House wins!", 100, 100, 4210752);
            }
        }
    }
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(getBackground());
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize); // Background
        
        for(int z = 0; z < tileFurnace.cards_player.size(); z++){
        	if(tileFurnace.cards_player.get(z).suit == 0){ this.mc.getTextureManager().bindTexture(textureHerz);  drawTexturedModalRect(guiLeft+8+10*z, guiTop+78+5*z, POS(true,tileFurnace.cards_player.get(z).number), POS(false,tileFurnace.cards_player.get(z).number), 42, 57); }
			if(tileFurnace.cards_player.get(z).suit == 1){ this.mc.getTextureManager().bindTexture(textureKaro);  drawTexturedModalRect(guiLeft+8+10*z, guiTop+78+5*z, POS(true,tileFurnace.cards_player.get(z).number), POS(false,tileFurnace.cards_player.get(z).number), 42, 57); }
			if(tileFurnace.cards_player.get(z).suit == 2){ this.mc.getTextureManager().bindTexture(textureKreuz); drawTexturedModalRect(guiLeft+8+10*z, guiTop+78+5*z, POS(true,tileFurnace.cards_player.get(z).number), POS(false,tileFurnace.cards_player.get(z).number), 42, 57); }
			if(tileFurnace.cards_player.get(z).suit == 3){ this.mc.getTextureManager().bindTexture(texturePik);   drawTexturedModalRect(guiLeft+8+10*z, guiTop+78+5*z, POS(true,tileFurnace.cards_player.get(z).number), POS(false,tileFurnace.cards_player.get(z).number), 42, 57); }
        }
        
        for(int z = 0; z < tileFurnace.cards_dealer.size(); z++){
        	if(tileFurnace.cards_dealer.get(z).suit == 0){ this.mc.getTextureManager().bindTexture(textureHerz);  drawTexturedModalRect(guiLeft+140+10*z, guiTop+17+5*z, POS(true,tileFurnace.cards_dealer.get(z).number), POS(false,tileFurnace.cards_dealer.get(z).number), 42, 57); }
			if(tileFurnace.cards_dealer.get(z).suit == 1){ this.mc.getTextureManager().bindTexture(textureKaro);  drawTexturedModalRect(guiLeft+140+10*z, guiTop+17+5*z, POS(true,tileFurnace.cards_dealer.get(z).number), POS(false,tileFurnace.cards_dealer.get(z).number), 42, 57); }
			if(tileFurnace.cards_dealer.get(z).suit == 2){ this.mc.getTextureManager().bindTexture(textureKreuz); drawTexturedModalRect(guiLeft+140+10*z, guiTop+17+5*z, POS(true,tileFurnace.cards_dealer.get(z).number), POS(false,tileFurnace.cards_dealer.get(z).number), 42, 57); }
			if(tileFurnace.cards_dealer.get(z).suit == 3){ this.mc.getTextureManager().bindTexture(texturePik);   drawTexturedModalRect(guiLeft+140+10*z, guiTop+17+5*z, POS(true,tileFurnace.cards_dealer.get(z).number), POS(false,tileFurnace.cards_dealer.get(z).number), 42, 57); }
        }
        
        if(tileFurnace.active_player){
        	drawTexturedModalRect(guiLeft+140+10*1, guiTop+17+5*1, 0, 0, 42, 57);
        }
        
        this.mc.getTextureManager().bindTexture(textureExtra);
		if(!tileFurnace.end){
			drawTexturedModalRect(guiLeft+4, guiTop+166, 26, 0, 92, 26); // Button Hit
			drawTexturedModalRect(guiLeft+160, guiTop+166, 118, 0, 92, 26); // Button Stand
		}
		if(tileFurnace.end){
			drawTexturedModalRect(guiLeft+82, guiTop+161, 92, 26, 92, 26); // Button New Game
		}
    }
    
    private ResourceLocation getBackground(){
    	if(tileFurnace.color == EnumDyeColor.BLACK)      return textureGroundBlack;
    	if(tileFurnace.color == EnumDyeColor.BLUE)       return textureGroundBlue;
    	if(tileFurnace.color == EnumDyeColor.BROWN)      return textureGroundBrown;
    	if(tileFurnace.color == EnumDyeColor.CYAN)       return textureGroundCyan;
    	if(tileFurnace.color == EnumDyeColor.GRAY)       return textureGroundGray;
    	if(tileFurnace.color == EnumDyeColor.GREEN)      return textureGroundGreen;
    	if(tileFurnace.color == EnumDyeColor.LIGHT_BLUE) return textureGroundLightBlue;
    	if(tileFurnace.color == EnumDyeColor.LIME)       return textureGroundLime;
    	if(tileFurnace.color == EnumDyeColor.MAGENTA)    return textureGroundMagenta;
    	if(tileFurnace.color == EnumDyeColor.ORANGE)     return textureGroundOrange;
    	if(tileFurnace.color == EnumDyeColor.PINK)       return textureGroundPink;
    	if(tileFurnace.color == EnumDyeColor.PURPLE)     return textureGroundPurple;
    	if(tileFurnace.color == EnumDyeColor.RED)        return textureGroundRed;
    	if(tileFurnace.color == EnumDyeColor.SILVER)     return textureGroundSilver;
    	if(tileFurnace.color == EnumDyeColor.WHITE)      return textureGroundWhite;
    	if(tileFurnace.color == EnumDyeColor.YELLOW)     return textureGroundYellow;
    	
    	return textureGroundGray;
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