package mod.casinocraft.screen.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mod.casinocraft.CasinoCraft;
import mod.casinocraft.blocks.BlockArcade;
import mod.casinocraft.container.ContainerMachine;
import mod.casinocraft.network.*;
import mod.casinocraft.system.CasinoPacketHandler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScreenArcade extends ContainerScreen<ContainerMachine> {

    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory.png");
    /** The player inventory bound to this GUI. */
    private   final PlayerInventory PLAYER;
    /** The TileEntity bound to this GUI. */
    protected final ContainerMachine CONTAINER;
    private boolean didReset = false;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ScreenArcade(ContainerMachine container, PlayerInventory player, ITextComponent name) {
        super(container, player, name);
        PLAYER = player;
        CONTAINER = container;
    }




    //----------------------------------------INPUT----------------------------------------//

    /** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0){
            if(mouseRect(80, 3, 16, 16, mouseX, mouseY)) { // Low Bet PLUS
                CONTAINER.addBetLow(1);
                CONTAINER.addBetHigh(1);
                CasinoPacketHandler.sendToServer(new MessageBoardServer(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
            }
            if(mouseRect(80, 33, 16, 16, mouseX, mouseY)) { // Low Bet MINUS
                if(CONTAINER.getBetLow() > 0) {
                    CONTAINER.addBetLow(-1);
                    CONTAINER.addBetHigh(-1);
                    CasinoPacketHandler.sendToServer(new MessageBoardServer(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
                }
            }
            if(mouseRect(43, 15, 18, 18, mouseX, mouseY)) { // Transfer IN
                CONTAINER.setTransferIn(!CONTAINER.getTransferIn());
                CasinoPacketHandler.sendToServer(new MessageBoardServer(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
                if(!CONTAINER.getTransferIn()) {
                    CasinoPacketHandler.sendToServer(new MessageBlockServer(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));
                }
            }
            if(mouseRect(115, 15, 18, 18, mouseX, mouseY)) { // Transfer OUT
                CONTAINER.setTransferOut(!CONTAINER.getTransferOut());
                CasinoPacketHandler.sendToServer(new MessageBoardServer(CONTAINER.getBetLow(), CONTAINER.getBetHigh(), CONTAINER.getTransferIn(), CONTAINER.getTransferOut(), false, CONTAINER.getPos()));
                if(!CONTAINER.getTransferOut()) {
                    CasinoPacketHandler.sendToServer(new MessageBlockServer(CONTAINER.inventory.getStackInSlot(0), CONTAINER.inventory.getStackInSlot(1), CONTAINER.inventory.getStackInSlot(4), CONTAINER.getBetStorage(), CONTAINER.getPos()));
                }
            }
            if(mouseRect(50, 62, 16, 16, mouseX, mouseY)) { // Reset Game
                CasinoPacketHandler.sendToServer(new MessageStateServer(true, -2, CONTAINER.getPos()));
                didReset = true;
            }
        }
        return false;
    }




    //----------------------------------------DRAW----------------------------------------//

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        //this.renderHoveredToolTip(matrixStack, mouseX, mouseY);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY){
        this.font.drawString(matrixStack, "Key",    6, 50, 4210752);
        this.font.drawString(matrixStack, "Game", 148, 50, 4210752);
        if(CONTAINER.getBetStorage() > 0) {
            this.itemRenderer.renderItemIntoGUI(CONTAINER.getToken(), 62, 16);
            this.font.drawString(matrixStack, "x" + CONTAINER.getBetStorage(), 80, 20, 4210752);
            this.font.drawString(matrixStack, "bet:" + CONTAINER.getBetLow(), 74, 66, 4210752);
        } else {
            this.font.drawString(matrixStack, "NO TOKEN", 65, 20, 4210752);
        }
    }

    /** Draws the background layer of this container (behind the items). */
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y){
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width  - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        this.blit(matrixStack, i + 80, j +  3, 224, 0, 16, 16);
        this.blit(matrixStack, i + 80, j + 33, 208, 0, 16, 16);

        this.blit(matrixStack, i +  45, j + 15, 240, CONTAINER.getTransferIn()  ? 16 : 0, 16, 16);
        this.blit(matrixStack, i + 115, j + 15, 240, CONTAINER.getTransferOut() ? 16 : 0, 16, 16);

        this.blit(matrixStack, i + 50, j + 62, 176, didReset ? 48 : 32, 16, 16);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    /** Checks if mouse is inside a rectangle **/
    protected boolean mouseRect(int x, int y, int width, int height, double mouseX, double mouseY){
        if(guiLeft + x < mouseX && mouseX < guiLeft + x + width){
            return guiTop + y < mouseY && mouseY < guiTop + y + height;
        }
        return false;
    }

    /** Called when the screen is unloaded. Used to disable keyboard repeat events */
    public void onClose() {
        if(CONTAINER.world().getBlockState(CONTAINER.getPos()).getBlock() instanceof BlockArcade) {
            CasinoPacketHandler.sendToServer(new MessageModuleServer(CONTAINER.getPos()));
            {
                NonNullList<ItemStack> inv = NonNullList.withSize(6, ItemStack.EMPTY);
                for(int i = 0; i < 6; i++){ inv.set(i, CONTAINER.inventory.getStackInSlot(i)); }
                CasinoPacketHandler.sendToServer(new MessageInventoryServer(inv, CONTAINER.getPos()));
            }

        }
        if (this.minecraft.player != null) {
            this.container.onContainerClosed(this.minecraft.player);
        }
    }

}
