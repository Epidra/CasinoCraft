package mod.casinocraft.gui.blocks;

import mod.casinocraft.CasinoCraft;
import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.blocks.ContainerCardTable;
import mod.casinocraft.network.MessageBlockServer;
import mod.casinocraft.network.MessageBoardServer;
import mod.casinocraft.network.MessageInventoryServer;
import mod.casinocraft.network.MessageStateServer;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiSlotMachine extends GuiContainer {

	private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(CasinoCraft.modid + ":" + "textures/gui/inventory.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityBoard tileCardTable;
	private boolean didReset = false;

    public GuiSlotMachine(InventoryPlayer playerInv, IInventory furnaceInv){
        super(new ContainerCardTable(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileCardTable = (TileEntityBoard) furnaceInv;
    }
    
    /** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0){
        	if(mouseRect(72, 3, 16, 16, mouseX, mouseY)) { // Low Bet PLUS
        		tileCardTable.bet_low++;
        		if(tileCardTable.bet_high < tileCardTable.bet_low) tileCardTable.bet_high = tileCardTable.bet_low;
        		CasinoPacketHandler.INSTANCE.sendToServer(new MessageBoardServer(tileCardTable.bet_low, tileCardTable.bet_high, tileCardTable.transfer_in, tileCardTable.transfer_out, tileCardTable.isCreative, tileCardTable.getPos()));
        	}
        	if(mouseRect(72, 33, 16, 16, mouseX, mouseY)) { // Low Bet MINUS
        		if(tileCardTable.bet_low > 0) {
        			tileCardTable.bet_low--;
        			CasinoPacketHandler.INSTANCE.sendToServer(new MessageBoardServer(tileCardTable.bet_low, tileCardTable.bet_high, tileCardTable.transfer_in, tileCardTable.transfer_out, tileCardTable.isCreative, tileCardTable.getPos()));
        		}
        	}
        	if(mouseRect(88, 3, 16, 16, mouseX, mouseY)) { // High Bet PLUS
        		tileCardTable.bet_high++;
        		CasinoPacketHandler.INSTANCE.sendToServer(new MessageBoardServer(tileCardTable.bet_low, tileCardTable.bet_high, tileCardTable.transfer_in, tileCardTable.transfer_out, tileCardTable.isCreative, tileCardTable.getPos()));
        	}
        	if(mouseRect(88, 33, 16, 16, mouseX, mouseY)) { // High Bet MINUS
        		if(tileCardTable.bet_high > 0) {
        			tileCardTable.bet_high--;
            		if(tileCardTable.bet_low > tileCardTable.bet_high) tileCardTable.bet_low = tileCardTable.bet_high;
            		CasinoPacketHandler.INSTANCE.sendToServer(new MessageBoardServer(tileCardTable.bet_low, tileCardTable.bet_high, tileCardTable.transfer_in, tileCardTable.transfer_out, tileCardTable.isCreative, tileCardTable.getPos()));
        		}
        	}
        	if(mouseRect(43, 15, 18, 18, mouseX, mouseY)) { // Transfer IN
        		tileCardTable.transfer_in = !tileCardTable.transfer_in;
        		CasinoPacketHandler.INSTANCE.sendToServer(new MessageBoardServer(tileCardTable.bet_low, tileCardTable.bet_high, tileCardTable.transfer_in, tileCardTable.transfer_out, tileCardTable.isCreative, tileCardTable.getPos()));
        		if(!tileCardTable.transfer_in) {
        			CasinoPacketHandler.INSTANCE.sendToServer(new MessageBlockServer(tileCardTable.inventory.get(0), tileCardTable.inventory.get(1), tileCardTable.inventory.get(4), tileCardTable.bet_storage, tileCardTable.getPos()));
        		}
        	}
        	if(mouseRect(115, 15, 18, 18, mouseX, mouseY)) { // Transfer OUT
        		tileCardTable.transfer_out = !tileCardTable.transfer_out;
        		CasinoPacketHandler.INSTANCE.sendToServer(new MessageBoardServer(tileCardTable.bet_low, tileCardTable.bet_high, tileCardTable.transfer_in, tileCardTable.transfer_out, tileCardTable.isCreative, tileCardTable.getPos()));
        		if(!tileCardTable.transfer_out) {
        			CasinoPacketHandler.INSTANCE.sendToServer(new MessageBlockServer(tileCardTable.inventory.get(0), tileCardTable.inventory.get(1), tileCardTable.inventory.get(4), tileCardTable.bet_storage, tileCardTable.getPos()));
        		}
        	}
        	if(CasinoKeeper.config_allowed_creative) {
        		if(mouseRect(110, 62, 16, 16, mouseX, mouseY)) { // Is Creative
            		tileCardTable.isCreative = !tileCardTable.isCreative;
            		CasinoPacketHandler.INSTANCE.sendToServer(new MessageBoardServer(tileCardTable.bet_low, tileCardTable.bet_high, tileCardTable.transfer_in, tileCardTable.transfer_out, tileCardTable.isCreative, tileCardTable.getPos()));
            	}
        	}
			if(mouseRect(50, 62, 16, 16, mouseX, mouseY)) { // Reset Game
				CasinoPacketHandler.INSTANCE.sendToServer(new MessageStateServer(true, -2, tileCardTable.getPos()));
				didReset = true;
			}
        }
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	this.fontRenderer.drawString("Key",    6, 50, 4210752);
    	this.fontRenderer.drawString("Game", 148, 50, 4210752);
    	if(tileCardTable.bet_storage > 0) {
    		this.itemRender.renderItemIntoGUI(tileCardTable.getTokenStack(), 62, 16);
        	if(tileCardTable.isCreative) {
        		this.fontRenderer.drawString("x CRTV", 80, 20, 4210752);
        	} else {
        		this.fontRenderer.drawString("x" + tileCardTable.bet_storage, 80, 20, 4210752);
        	}
        	this.fontRenderer.drawString(" low:" + tileCardTable.bet_low, 72, 62, 4210752);
        	this.fontRenderer.drawString("high:" + tileCardTable.bet_high, 71, 70, 4210752);
    	} else {
        	this.fontRenderer.drawString("NO TOKEN", 65, 20, 4210752);
    	}
    }
    
    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width  - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        
        this.drawTexturedModalRect(i + 72, j +  3, 224, 0, 16, 16);
        this.drawTexturedModalRect(i + 72, j + 33, 208, 0, 16, 16);
        this.drawTexturedModalRect(i + 88, j +  3, 224, 0, 16, 16);
        this.drawTexturedModalRect(i + 88, j + 33, 208, 0, 16, 16);
        
        this.drawTexturedModalRect(i +  45, j + 15, 240, tileCardTable.transfer_in  ? 16 : 0, 16, 16);
        this.drawTexturedModalRect(i + 115, j + 15, 240, tileCardTable.transfer_out ? 16 : 0, 16, 16);
        
        if(CasinoKeeper.config_allowed_creative) {
        	this.drawTexturedModalRect(i + 110, j + 62, 176, tileCardTable.isCreative ? 16 : 0, 16, 16);
        }

		this.drawTexturedModalRect(i + 50, j + 62, 176, didReset ? 48 : 32, 16, 16);
    }
	
    /** Checks if mouse is inside a rectangle **/
    protected boolean mouseRect(int x, int y, int width, int height, int mouseX, int mouseY){
    	if(guiLeft + x < mouseX && mouseX < guiLeft + x + width){
            return guiTop + y < mouseY && mouseY < guiTop + y + height;
    	}
    	return false;
    }

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	public void onGuiClosed(){
		NonNullList<ItemStack> inv = NonNullList.withSize(6, ItemStack.EMPTY);
		for(int i = 0; i < 6; i++){ inv.set(i, tileCardTable.getStackInSlot(i)); }
		CasinoPacketHandler.INSTANCE.sendToServer(new MessageInventoryServer(inv, tileCardTable.getPos()));
		if (this.mc.player != null){
			this.inventorySlots.onContainerClosed(this.mc.player);
		}
	}
    
}
