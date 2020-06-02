package net.casinocraft.mod.gui;

import java.io.IOException;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerArcade;
import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerPyramid;
import net.casinocraft.mod.container.ContainerTriPeak;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityBaccarat;
import net.casinocraft.mod.tileentity.TileEntityPyramid;
import net.casinocraft.mod.tileentity.TileEntityTriPeak;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTriPeak extends GuiContainer{
	
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityTriPeak tileFurnace;
    
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
	private ResourceLocation textureExtra  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/Minos.png");
	private ResourceLocation textureHerz   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsHerz.png");
	private ResourceLocation textureKaro   = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsKaro.png");
	private ResourceLocation textureKreuz  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsKreuz.png");
	private ResourceLocation texturePik    = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/CardsPik.png");
    
    public GuiTriPeak(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerTriPeak(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntityTriPeak) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	for(int y = 0; y < 15; y++) {
                for(int x = 0; x < 23; x++) {
                	if(guiLeft+10 * x < mouseX && mouseX < guiLeft+10 * x+10 && guiTop+14 * y < mouseY && mouseY < guiTop+14 * y+14){
                		tileFurnace.TouchField(x, y);
        			}
                }
            }
        	if(guiLeft+10 * 7 < mouseX && mouseX < guiLeft+10 * 7+10*4 && guiTop+14 * 12 < mouseY && mouseY < guiTop+14 * 12+14*4){
        		if(tileFurnace.cards_stack.size() > 0) tileFurnace.CompareCards(28);
			}
            
            if(guiLeft+10 * 13 < mouseX && mouseX < guiLeft+10 * 13+10*4 && guiTop+14 * 12 < mouseY && mouseY < guiTop+14 * 12+14*4){
            	tileFurnace.DrawReserve();
			}
            
            if(guiLeft+256-32 < mouseX && mouseX < guiLeft+256 && guiTop+256-32 < mouseY && mouseY < guiTop+256){
				tileFurnace.start();
			}
        }
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        
    }
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(getBackground());
        int i = (this.width  - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize); // Background
        
        if(tileFurnace.cards_field[ 0] != null){
        	if(ChangeTexture(tileFurnace.cards_field[ 0])) drawTexturedModalRect(guiLeft+10 *  3+20,   guiTop+14 * 1+14, POS(true,tileFurnace.cards_field[ 0].X), POS(false,tileFurnace.cards_field[ 0].X), 42, 57); // Card  1 Herz
        	if(ChangeTexture(tileFurnace.cards_field[ 1])) drawTexturedModalRect(guiLeft+10 *  9+20,   guiTop+14 * 1+14, POS(true,tileFurnace.cards_field[ 1].X), POS(false,tileFurnace.cards_field[ 1].X), 42, 57); // Card  2 Herz
        	if(ChangeTexture(tileFurnace.cards_field[ 2])) drawTexturedModalRect(guiLeft+10 * 15+20,   guiTop+14 * 1+14, POS(true,tileFurnace.cards_field[ 2].X), POS(false,tileFurnace.cards_field[ 2].X), 42, 57); // Card  3 Herz
    		
        	if(ChangeTexture(tileFurnace.cards_field[ 3])) drawTexturedModalRect(guiLeft+10 *  2+20,   guiTop+14 * 2+14, POS(true,tileFurnace.cards_field[ 3].X), POS(false,tileFurnace.cards_field[ 3].X), 42, 57); // Card  4 Herz
        	if(ChangeTexture(tileFurnace.cards_field[ 4])) drawTexturedModalRect(guiLeft+10 *  4+20,   guiTop+14 * 2+14, POS(true,tileFurnace.cards_field[ 4].X), POS(false,tileFurnace.cards_field[ 4].X), 42, 57); // Card  5 Herz
        	if(ChangeTexture(tileFurnace.cards_field[ 5])) drawTexturedModalRect(guiLeft+10 *  8+20,   guiTop+14 * 2+14, POS(true,tileFurnace.cards_field[ 5].X), POS(false,tileFurnace.cards_field[ 5].X), 42, 57); // Card  6 Herz
        	if(ChangeTexture(tileFurnace.cards_field[ 6])) drawTexturedModalRect(guiLeft+10 * 10+20,   guiTop+14 * 2+14, POS(true,tileFurnace.cards_field[ 6].X), POS(false,tileFurnace.cards_field[ 6].X), 42, 57); // Card  7 Herz
        	if(ChangeTexture(tileFurnace.cards_field[ 7])) drawTexturedModalRect(guiLeft+10 * 14+20,   guiTop+14 * 2+14, POS(true,tileFurnace.cards_field[ 7].X), POS(false,tileFurnace.cards_field[ 7].X), 42, 57); // Card  8 Herz
        	if(ChangeTexture(tileFurnace.cards_field[ 8])) drawTexturedModalRect(guiLeft+10 * 16+20,   guiTop+14 * 2+14, POS(true,tileFurnace.cards_field[ 8].X), POS(false,tileFurnace.cards_field[ 8].X), 42, 57); // Card  9 Herz
    		
        	if(ChangeTexture(tileFurnace.cards_field[ 9])) drawTexturedModalRect(guiLeft+10 *  1+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[ 9].X), POS(false,tileFurnace.cards_field[ 9].X), 42, 57); // Card 10 Herz
        	if(ChangeTexture(tileFurnace.cards_field[10])) drawTexturedModalRect(guiLeft+10 *  3+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[10].X), POS(false,tileFurnace.cards_field[10].X), 42, 57); // Card 11 Herz
        	if(ChangeTexture(tileFurnace.cards_field[11])) drawTexturedModalRect(guiLeft+10 *  5+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[11].X), POS(false,tileFurnace.cards_field[11].X), 42, 57); // Card 12 Herz
        	if(ChangeTexture(tileFurnace.cards_field[12])) drawTexturedModalRect(guiLeft+10 *  7+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[12].X), POS(false,tileFurnace.cards_field[12].X), 42, 57); // Card 13 Herz
        	if(ChangeTexture(tileFurnace.cards_field[13])) drawTexturedModalRect(guiLeft+10 *  9+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[13].X), POS(false,tileFurnace.cards_field[13].X), 42, 57); // Card 14 Herz
        	if(ChangeTexture(tileFurnace.cards_field[14])) drawTexturedModalRect(guiLeft+10 * 11+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[14].X), POS(false,tileFurnace.cards_field[14].X), 42, 57); // Card 15 Herz
        	if(ChangeTexture(tileFurnace.cards_field[15])) drawTexturedModalRect(guiLeft+10 * 13+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[15].X), POS(false,tileFurnace.cards_field[15].X), 42, 57); // Card 16 Herz
        	if(ChangeTexture(tileFurnace.cards_field[16])) drawTexturedModalRect(guiLeft+10 * 15+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[16].X), POS(false,tileFurnace.cards_field[16].X), 42, 57); // Card 17 Herz
        	if(ChangeTexture(tileFurnace.cards_field[17])) drawTexturedModalRect(guiLeft+10 * 17+20,   guiTop+14 * 3+14, POS(true,tileFurnace.cards_field[17].X), POS(false,tileFurnace.cards_field[17].X), 42, 57); // Card 18 Herz
    		
        	if(ChangeTexture(tileFurnace.cards_field[18])) drawTexturedModalRect(guiLeft+10 *  0+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[18].X), POS(false,tileFurnace.cards_field[18].X), 42, 57); // Card 19 Herz
        	if(ChangeTexture(tileFurnace.cards_field[19])) drawTexturedModalRect(guiLeft+10 *  2+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[19].X), POS(false,tileFurnace.cards_field[19].X), 42, 57); // Card 20 Herz
        	if(ChangeTexture(tileFurnace.cards_field[20])) drawTexturedModalRect(guiLeft+10 *  4+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[20].X), POS(false,tileFurnace.cards_field[20].X), 42, 57); // Card 21 Herz
        	if(ChangeTexture(tileFurnace.cards_field[21])) drawTexturedModalRect(guiLeft+10 *  6+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[21].X), POS(false,tileFurnace.cards_field[21].X), 42, 57); // Card 22 Herz
        	if(ChangeTexture(tileFurnace.cards_field[22])) drawTexturedModalRect(guiLeft+10 *  8+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[22].X), POS(false,tileFurnace.cards_field[22].X), 42, 57); // Card 23 Herz
        	if(ChangeTexture(tileFurnace.cards_field[23])) drawTexturedModalRect(guiLeft+10 * 10+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[23].X), POS(false,tileFurnace.cards_field[23].X), 42, 57); // Card 24 Herz
        	if(ChangeTexture(tileFurnace.cards_field[24])) drawTexturedModalRect(guiLeft+10 * 12+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[24].X), POS(false,tileFurnace.cards_field[24].X), 42, 57); // Card 25 Herz
        	if(ChangeTexture(tileFurnace.cards_field[25])) drawTexturedModalRect(guiLeft+10 * 14+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[25].X), POS(false,tileFurnace.cards_field[25].X), 42, 57); // Card 26 Herz
        	if(ChangeTexture(tileFurnace.cards_field[26])) drawTexturedModalRect(guiLeft+10 * 16+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[26].X), POS(false,tileFurnace.cards_field[26].X), 42, 57); // Card 27 Herz
        	if(ChangeTexture(tileFurnace.cards_field[27])) drawTexturedModalRect(guiLeft+10 * 18+20,   guiTop+14 * 4+14, POS(true,tileFurnace.cards_field[27].X), POS(false,tileFurnace.cards_field[27].X), 42, 57); // Card 28 Herz
    		if(tileFurnace.cards_stack.size() > 0) {
    			drawTexturedModalRect(guiLeft+10 * 7,   guiTop+14 * 12, POS(true, tileFurnace.cards_stack.get(tileFurnace.cards_stack.size() - 1).X), POS(false, tileFurnace.cards_stack.get(tileFurnace.cards_stack.size() - 1).X), 42, 57);
            } else {
            	drawTexturedModalRect(guiLeft+10 * 7,   guiTop+14 * 12, POS(true, 0), POS(false, 0), 42, 57);
            }

            if(tileFurnace.cards_reserve.size() > 0) {
            	drawTexturedModalRect(guiLeft+10 * 13,   guiTop+14 * 12, POS(true, 0), POS(false, 0), 42, 57);
            } else {
            	drawTexturedModalRect(guiLeft+10 * 13,   guiTop+14 * 12, POS(true, 0), POS(false, 0), 42, 57);
            }
        }
    }
    
    private boolean ChangeTexture(Vector2 v){
    	if(v.Y == -1) return false;
    	if(v.Y == 0) this.mc.getTextureManager().bindTexture(textureHerz);
    	if(v.Y == 1) this.mc.getTextureManager().bindTexture(textureKaro);
    	if(v.Y == 2) this.mc.getTextureManager().bindTexture(textureKreuz);
    	if(v.Y == 3) this.mc.getTextureManager().bindTexture(texturePik);
    	return true;
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