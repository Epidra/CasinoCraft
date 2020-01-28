package mod.casinocraft.gui;

public class GuiArcade {

}

//public class GuiArcade extends GuiContainer {
//
//    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(CasinoCraft.MODID + ":" + "textures/gui/inventory.png");
//    /** The player inventory bound to this GUI. */
//    private final InventoryPlayer playerInventory;
//    private final TileEntityBoard tileArcade;
//
//    public GuiArcade(InventoryPlayer playerInv, IInventory furnaceInv){
//        super(new ContainerArcade(playerInv, furnaceInv));
//        this.playerInventory = playerInv;
//        this.tileArcade = (TileEntityBoard) furnaceInv;
//    }
//
//    /** Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton */
//    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
//        super.mouseClicked(mouseX, mouseY, mouseButton);
//        if (mouseButton == 0){
//            if(mouseRect(80, 3, 16, 16, mouseX, mouseY)) { // Low Bet PLUS
//                tileArcade.bet_low++;
//                tileArcade.bet_high++;
//                CasinoPacketHandler.sendToServer(new ServerBoardMessage(tileArcade.bet_low, tileArcade.bet_high, tileArcade.transfer_in, tileArcade.transfer_out, false, tileArcade.getPos()));
//            }
//            if(mouseRect(80, 33, 16, 16, mouseX, mouseY)) { // Low Bet MINUS
//                if(tileArcade.bet_low > 0) {
//                    tileArcade.bet_low--;
//                    tileArcade.bet_high--;
//                    CasinoPacketHandler.sendToServer(new ServerBoardMessage(tileArcade.bet_low, tileArcade.bet_high, tileArcade.transfer_in, tileArcade.transfer_out, false, tileArcade.getPos()));
//                }
//            }
//            if(mouseRect(43, 15, 18, 18, mouseX, mouseY)) { // Transfer IN
//                tileArcade.transfer_in = !tileArcade.transfer_in;
//                CasinoPacketHandler.sendToServer(new ServerBoardMessage(tileArcade.bet_low, tileArcade.bet_high, tileArcade.transfer_in, tileArcade.transfer_out, false, tileArcade.getPos()));
//                if(!tileArcade.transfer_in) {
//                    CasinoPacketHandler.sendToServer(new ServerBlockMessage(tileArcade.inventory.get(0), tileArcade.inventory.get(1), tileArcade.inventory.get(4), tileArcade.bet_storage, tileArcade.getPos()));
//                }
//            }
//            if(mouseRect(115, 15, 18, 18, mouseX, mouseY)) { // Transfer OUT
//                tileArcade.transfer_out = !tileArcade.transfer_out;
//                CasinoPacketHandler.sendToServer(new ServerBoardMessage(tileArcade.bet_low, tileArcade.bet_high, tileArcade.transfer_in, tileArcade.transfer_out, false, tileArcade.getPos()));
//                if(!tileArcade.transfer_out) {
//                    CasinoPacketHandler.sendToServer(new ServerBlockMessage(tileArcade.inventory.get(0), tileArcade.inventory.get(1), tileArcade.inventory.get(4), tileArcade.bet_storage, tileArcade.getPos()));
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Draw the foreground layer for the GuiContainer (everything in front of the items)
//     */
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
//        this.fontRenderer.drawString("Key",    6, 50, 4210752);
//        this.fontRenderer.drawString("Game", 148, 50, 4210752);
//        if(tileArcade.bet_storage > 0) {
//            this.itemRender.renderItemIntoGUI(tileArcade.getTokenStack(), 62, 16);
//            this.fontRenderer.drawString("x" + tileArcade.bet_storage, 80, 20, 4210752);
//            this.fontRenderer.drawString("bet:" + tileArcade.bet_low, 74, 66, 4210752);
//        } else {
//            this.fontRenderer.drawString("NO TOKEN", 65, 20, 4210752);
//        }
//    }
//
//    /**
//     * Draws the background layer of this container (behind the items).
//     */
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
//        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
//        int i = (this.width  - this.xSize) / 2;
//        int j = (this.height - this.ySize) / 2;
//        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
//
//        this.drawTexturedModalRect(i + 80, j +  3, 224, 0, 16, 16);
//        this.drawTexturedModalRect(i + 80, j + 33, 208, 0, 16, 16);
//
//        this.drawTexturedModalRect(i +  45, j + 15, 240, tileArcade.transfer_in  ? 16 : 0, 16, 16);
//        this.drawTexturedModalRect(i + 115, j + 15, 240, tileArcade.transfer_out ? 16 : 0, 16, 16);
//    }
//
//    /** Checks if mouse is inside a rectangle **/
//    protected boolean mouseRect(int x, int y, int width, int height, double mouseX, double mouseY){
//        if(guiLeft + x < mouseX && mouseX < guiLeft + x + width){
//            return guiTop + y < mouseY && mouseY < guiTop + y + height;
//        }
//        return false;
//    }
//
//    /**
//     * Called when the screen is unloaded. Used to disable keyboard repeat events
//     */
//    public void onGuiClosed(){
//        if(tileArcade.getWorld().getBlockState(tileArcade.getPos()).getBlock() instanceof BlockArcade) {
//            BlockArcade block = (BlockArcade) tileArcade.getWorld().getBlockState(tileArcade.getPos()).getBlock();
//            block.setPowerState(tileArcade.inventory.get(1).getItem(), tileArcade.getPos());
//        }
//        if (this.mc.player != null){
//            this.inventorySlots.onContainerClosed(this.mc.player);
//        }
//    }
//
//}
