package mod.casinocraft.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoCraft;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.container.ContainerArcade;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.container.ContainerMachine;
import mod.casinocraft.network.ServerBlockMessage;
import mod.casinocraft.network.ServerBoardMessage;
import mod.casinocraft.system.CasinoPacketHandler;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.container.ContainerBase;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiArcade extends ContainerScreen<ContainerMachine> {

    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory.png");
    /** The player inventory bound to this GUI. */
    private   final PlayerInventory PLAYER;
    /** The TileEntity bound to this GUI. */
    protected final ContainerMachine CONTAINER;

    public GuiArcade(ContainerMachine container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        PLAYER = player;
        CONTAINER = container;
    }

    /** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0){
            if(mouseRect(80, 3, 16, 16, mouseX, mouseY)) { // Low Bet PLUS
                CONTAINER.addBetLow(1);
                CONTAINER.addBetHigh(1);
                CasinoPacketHandler.sendToServer(new ServerBoardMessage(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
            }
            if(mouseRect(80, 33, 16, 16, mouseX, mouseY)) { // Low Bet MINUS
                if(CONTAINER.getBetLow() > 0) {
                    CONTAINER.addBetLow(-1);
                    CONTAINER.addBetHigh(-1);
                    CasinoPacketHandler.sendToServer(new ServerBoardMessage(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
                }
            }
            if(mouseRect(43, 15, 18, 18, mouseX, mouseY)) { // Transfer IN
                CONTAINER.setTransferIn(!CONTAINER.getTransferIn());
                CasinoPacketHandler.sendToServer(new ServerBoardMessage(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
                if(!CONTAINER.getTransferIn()) {
                    CasinoPacketHandler.sendToServer(new ServerBlockMessage(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));
                }
            }
            if(mouseRect(115, 15, 18, 18, mouseX, mouseY)) { // Transfer OUT
                CONTAINER.setTransferOut(!CONTAINER.getTransferOut());
                CasinoPacketHandler.sendToServer(new ServerBoardMessage(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
                if(!CONTAINER.getTransferOut()) {
                    CasinoPacketHandler.sendToServer(new ServerBlockMessage(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));
                }
            }
        }
        return false;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        this.font.drawString("Key",    6, 50, 4210752);
        this.font.drawString("Game", 148, 50, 4210752);
        if(CONTAINER.getBetStorage() > 0) {
            this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 62, 16);
            this.font.drawString("x" + CONTAINER.getBetStorage(), 80, 20, 4210752);
            this.font.drawString("bet:" + CONTAINER.getBetLow(), 74, 66, 4210752);
        } else {
            this.font.drawString("NO TOKEN", 65, 20, 4210752);
        }
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width  - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);

        this.blit(i + 80, j +  3, 224, 0, 16, 16);
        this.blit(i + 80, j + 33, 208, 0, 16, 16);

        this.blit(i +  45, j + 15, 240, CONTAINER.getTransferIn()  ? 16 : 0, 16, 16);
        this.blit(i + 115, j + 15, 240, CONTAINER.getTransferOut() ? 16 : 0, 16, 16);
    }

    /** Checks if mouse is inside a rectangle **/
    protected boolean mouseRect(int x, int y, int width, int height, double mouseX, double mouseY){
        if(guiLeft + x < mouseX && mouseX < guiLeft + x + width){
            return guiTop + y < mouseY && mouseY < guiTop + y + height;
        }
        return false;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void removed() {
        if(CONTAINER.world().getBlockState(CONTAINER.getPos()).getBlock() instanceof BlockArcade) {
            BlockArcade block = (BlockArcade) CONTAINER.world().getBlockState(CONTAINER.getPos()).getBlock();
            block.setPowerState(CONTAINER.inventory.getStackInSlot(1).getItem(), CONTAINER.getPos());
        }
        if (this.minecraft.player != null) {
            this.container.onContainerClosed(this.minecraft.player);
        }
    }

}
