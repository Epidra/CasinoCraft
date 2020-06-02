package net.casinocraft.mod.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerArcade;
import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.container.ContainerMysticSquare;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityBaccarat;
import net.casinocraft.mod.tileentity.TileEntityMysticSquare;
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
public class GuiMysticSquare extends GuiContainer{
	
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
	
	private ResourceLocation textureExtra  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/MysticSquare.png");
	
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityMysticSquare tileFurnace;
    
    public GuiMysticSquare(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerMysticSquare(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntityMysticSquare) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
        
    	if(keyCode == Keyboard.KEY_UP) { tileFurnace.Move("up"); }
    	if(keyCode == Keyboard.KEY_LEFT) { tileFurnace.Move("left"); }
    	if(keyCode == Keyboard.KEY_RIGHT){ tileFurnace.Move("right"); }
    	if(keyCode == Keyboard.KEY_DOWN) { tileFurnace.Move("down"); }
    	
    	if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)){
            this.mc.thePlayer.closeScreen();
        }
        
        this.checkHotbarKeys(keyCode);
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	for(int y = 0; y < 4; y++){
        		for(int x = 0; x < 4; x++){
        			if(guiLeft + 64*x < mouseX && mouseX < guiLeft+64 + 64*x && guiTop + 64*y < mouseY && mouseY < guiTop+64 + 64*y){
        				if(y > 0) if(tileFurnace.grid[x][y - 1] == -1) tileFurnace.Move("up");
                        if(y < 3) if(tileFurnace.grid[x][y + 1] == -1) tileFurnace.Move("down");
                        if(x > 0) if(tileFurnace.grid[x - 1][y] == -1) tileFurnace.Move("left");
                        if(x < 3) if(tileFurnace.grid[x + 1][y] == -1) tileFurnace.Move("right");
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
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                if(tileFurnace.grid[x][y] != -1) {
                	this.drawTexturedModalRect(guiLeft + 64*x, guiTop + 64*y, Get_Spritesheet(true, tileFurnace.grid[x][y])*64, Get_Spritesheet(false, tileFurnace.grid[x][y])*64, 64, 64);
                }
            }
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
    
    private int Get_Spritesheet(boolean x, int id) {
        if(x) {
            if(id == 0) return 0;
            if(id == 1) return 1;
            if(id == 2) return 2;
            if(id == 3) return 3;
            if(id == 4) return 0;
            if(id == 5) return 1;
            if(id == 6) return 2;
            if(id == 7) return 3;
            if(id == 8) return 0;
            if(id == 9) return 1;
            if(id == 10) return 2;
            if(id == 11) return 3;
            if(id == 12) return 0;
            if(id == 13) return 1;
            if(id == 14) return 2;
            if(id == -1) return 3;
        } else {
            if(id == 0) return 0;
            if(id == 1) return 0;
            if(id == 2) return 0;
            if(id == 3) return 0;
            if(id == 4) return 1;
            if(id == 5) return 1;
            if(id == 6) return 1;
            if(id == 7) return 1;
            if(id == 8) return 2;
            if(id == 9) return 2;
            if(id == 10) return 2;
            if(id == 11) return 2;
            if(id == 12) return 3;
            if(id == 13) return 3;
            if(id == 14) return 3;
            if(id == -1) return 3;
        }
        return 0;
    }
}