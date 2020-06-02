package net.casinocraft.mod.gui;

import java.io.IOException;

import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerArcade;
import net.casinocraft.mod.container.ContainerBaccarat;
import net.casinocraft.mod.tileentity.TileEntityArcade;
import net.casinocraft.mod.tileentity.TileEntityBaccarat;
import net.casinocraft.mod.tileentity.TileEntitySokoban;
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
public class GuiSokoban extends GuiContainer{
	
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntitySokoban tileFurnace;
    
    private ResourceLocation textureGround = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/Arcade.png");
	private ResourceLocation textureExtra  = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/MinoSmall.png");
    
    public GuiSokoban(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerBaccarat(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = (TileEntitySokoban) furnaceInv;
        this.xSize = 256;
		this.ySize = 256;
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	
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
        this.mc.getTextureManager().bindTexture(textureGround);
        int i = (this.width  - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize); // Background
    }
}