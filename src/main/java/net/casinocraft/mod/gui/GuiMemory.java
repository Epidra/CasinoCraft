package net.casinocraft.mod.gui;

import java.io.IOException;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerArcade;
import net.casinocraft.mod.container.ContainerMemory;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityMemory;
import net.casinocraft.mod.util.Vector2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMemory extends GuiContainer{
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
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityMemory tileFurnace;
    
    public GuiMemory(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerMemory(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntityMemory) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	for(int y = 0; y < 6; y++){
        		for(int x = 0; x < 6; x++){
        			if(tileFurnace.timer == 0 && guiLeft+17 + 37*x < mouseX && mouseX < guiLeft+17+32 + 37*x && guiTop+12 + 37*y < mouseY && mouseY < guiTop+12+32 + 37*y){
        				tileFurnace.Click_Mino(x, y);
        			}
        		}
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
        
        this.mc.getTextureManager().bindTexture(textureExtra);
        for(int y = 0; y < 6; y++){
        	for(int x = 0; x < 6; x++){
        		if(tileFurnace.grid_main[x][y] != null && tileFurnace.grid_main[x][y].compareTo("empty") != 0){
        			if(tileFurnace.selected_A_pos == new Vector2(x, y)){
        				this.drawTexturedModalRect(guiLeft+17 + 37*x, guiTop+12 + 37*y, MinoPos(tileFurnace.grid_main[x][y], true), MinoPos(tileFurnace.grid_main[x][y], false), 32, 32);
        			} else
        			if(tileFurnace.selected_A_pos == new Vector2(x, y)){
        				this.drawTexturedModalRect(guiLeft+17 + 37*x, guiTop+12 + 37*y, MinoPos(tileFurnace.grid_main[x][y], true), MinoPos(tileFurnace.grid_main[x][y], false), 32, 32);
        			} else {
        				this.drawTexturedModalRect(guiLeft+17 + 37*x, guiTop+12 + 37*y, MinoPos(tileFurnace.grid_main[x][y], true), MinoPos(tileFurnace.grid_main[x][y], false), 32, 32);
        			}
        		}
        	}
        }
        this.drawTexturedModalRect(guiLeft+256-32, guiTop+256-32, 0, 96, 32, 32);
        
        tileFurnace.update();
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
    
    private int MinoPos(String mino, boolean xy){
    	if(mino.compareTo("Fire")    == 0){ if(xy) return  32; else return   0+32; }
    	if(mino.compareTo("Air")     == 0){ if(xy) return  64; else return   0+32; }
    	if(mino.compareTo("Thunder") == 0){ if(xy) return  96; else return   0+32; }
    	if(mino.compareTo("Water")   == 0){ if(xy) return 128; else return   0+32; }
    	if(mino.compareTo("Ice")     == 0){ if(xy) return 160; else return   0+32; }
    	if(mino.compareTo("Earth")   == 0){ if(xy) return   0; else return 128+32; }
    	if(mino.compareTo("Metal")   == 0){ if(xy) return  32; else return 128+32; }
    	if(mino.compareTo("Nature")  == 0){ if(xy) return  64; else return 128+32; }
    	if(mino.compareTo("Light")   == 0){ if(xy) return  96; else return 128+32; }
    	if(mino.compareTo("Dark")    == 0){ if(xy) return 128; else return 128+32; }
    	return 0;
    }
    
}