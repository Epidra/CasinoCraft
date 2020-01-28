package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.shared.util.Vector2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class GuiMemory extends GuiCasino {

    //--------------------CONSTRUCTOR--------------------

    public GuiMemory(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_MEMORY);
        tc.gridI = new int[17][9];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 17; x++) {
                    if(mouseRect(-4 + x*24-24*3, -4+24 + y*24, 24, 24, mouseX, mouseY)){ actionTouch(y*17 + x); }
                }
            }
            if(tc.turnstate == 3 && mouseButton == 0){
                if(mouseRect( 24, 204, 92, 26, mouseX, mouseY)){ actionTouch(-1); }
                if(mouseRect(140, 204, 92, 26, mouseX, mouseY)){ actionTouch(-2); }
            }
        }
    }

    protected void keyTyped2(int keyCode){

    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){
        if(table == 1) {
            this.fontRenderer.drawString("POINTS",           25, 25, 0);
            this.fontRenderer.drawString("POINTS",           24, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scorePoint, 35, 35, 0);
            this.fontRenderer.drawString("" + tc.scorePoint, 34, 34, 16777215);
            this.fontRenderer.drawString("LIVES",            205, 25, 0);
            this.fontRenderer.drawString("LIVES",            204, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scoreLives, 215, 35, 0);
            this.fontRenderer.drawString("" + tc.scoreLives, 214, 34, 16777215);
        } else {
            this.fontRenderer.drawString("POINTS",           24-76-15, 25, 0);
            this.fontRenderer.drawString("POINTS",           24-76-16, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scorePoint, 34-76-15, 35, 0);
            this.fontRenderer.drawString("" + tc.scorePoint, 34-76-16, 34, 16777215);
            this.fontRenderer.drawString("LIVES",            204+76+17, 25, 0);
            this.fontRenderer.drawString("LIVES",            204+76+16, 24, 16777215);
            this.fontRenderer.drawString("" + tc.scoreLives, 214+76+17, 35, 0);
            this.fontRenderer.drawString("" + tc.scoreLives, 214+76+16, 34, 16777215);
        }
    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tc.turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_OCTAGAMES);
            for(int y = 0; y < 9; y++){
                for(int x = 0; x < 17; x++){
                    if(getValue(x + y*17) != -1){
                        if(getValue(200) == x && getValue(201) == y){
                            this.drawTexturedModalRect(guiLeft-76 + 24*x, guiTop+20 + 24*y, getValue(x + y*17)*24+24, 232, 24, 24);
                        } else
                        if(getValue(202) == x && getValue(203) == y){
                            this.drawTexturedModalRect(guiLeft-76 + 24*x, guiTop+20 + 24*y, getValue(x + y*17)*24+24, 232, 24, 24);
                        } else {
                            this.drawTexturedModalRect(guiLeft-76 + 24*x, guiTop+20 + 24*y, 0, 232, 24, 24);
                        }
                    }
                }
            }
            if(tc.turnstate == 3){
                drawTexturedModalRect(guiLeft+24+7,  guiTop+204+2,  0, 0, 78, 22); // Button Hit
                drawTexturedModalRect(guiLeft+140+7, guiTop+204+2, 78, 0, 78, 22); // Button Stand
            }
        }
    }



    //--------------------CUSTOM--------------------



    public boolean selected_A;
    public boolean selected_B;
    public Vector2 selected_A_pos;
    public Vector2 selected_B_pos;
    public int timer;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityMemory(){
   //    super(null, null);
   //}

   //public TileEntityMemory(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new int[17][9];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_MEMORY.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_MEMORY.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_MEMORY.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        tc.scoreLevel = 1;
        tc.scoreLives = 8;
        selected_A = false;
        selected_B = false;
        timer = 0;
        selected_A_pos = new Vector2(-1, -1);
        selected_B_pos = new Vector2(-1, -1);
        Command_Create_Grid();
    }

    public void restart(){
        tc.turnstate = 2;
        tc.scoreLevel++;
        tc.scoreLives += tc.scoreLevel*2;
        selected_A = false;
        selected_B = false;
        timer = 0;
        selected_A_pos = new Vector2(-1, -1);
        selected_B_pos = new Vector2(-1, -1);
        Command_Create_Grid();
    }

    public void actionTouch(int action){
        if(action == -1) {
            restart();
        } else if(action == -2) {
            tc.turnstate = 4;
        } else {
            Click_Mino(action % 17, action / 17);
        }
    }

    public void update(){
        if(timer > 0){
            timer-=10;
            if(timer <= 0){
                if(tc.gridI[selected_A_pos.X][selected_A_pos.Y] == tc.gridI[selected_B_pos.X][selected_B_pos.Y]){
                    tc.gridI[selected_A_pos.X][selected_A_pos.Y] = -1;
                    tc.gridI[selected_B_pos.X][selected_B_pos.Y] = -1;
                } else {
                    tc.scoreLives--;
                    //ChangeName(selected_A_pos.X, selected_A_pos.Y, grid[selected_A_pos.X][selected_A_pos.Y]);
                    //ChangeName(selected_B_pos.X, selected_B_pos.Y, grid[selected_B_pos.X][selected_B_pos.Y]);
                }
                boolean temp = false;
                for(int x = 0; x < 17; x++) {
                    for(int y = 0; y < 9; y++) {
                        if(tc.gridI[x][y] != -1) temp = true;
                    }
                }
                if(!temp) {
                    tc.turnstate = 3;
                    tc.scorePoint += tc.scoreLevel*4;
                } else {
                    if(tc.scoreLives <= 0) {
                        tc.turnstate = 4;
                        tc.scorePoint /= 2;
                    }
                }
                selected_A = false;
                selected_B = false;
                selected_A_pos = new Vector2(-1, -1);
                selected_B_pos = new Vector2(-1, -1);
            }
        }
    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        if(index == -1) return timer;
        if(index == 200) return selected_A_pos.X;
        if(index == 201) return selected_A_pos.Y;
        if(index == 202) return selected_B_pos.X;
        if(index == 203) return selected_B_pos.Y;
        return tc.gridI[index % 17][index / 17];
    }



    //--------------------CUSTOM--------------------

    public void Click_Mino(int x, int y) {
        if(!selected_A) {
            if(tc.gridI[x][y] != -1) {
                selected_A = true;
                selected_A_pos = new Vector2(x, y);
                //ChangeName(x, y, grid[x][y]);
            }
        } else if(!selected_B) {
            if(tc.gridI[x][y] != -1 && !selected_A_pos.matches(x, y)) {
                selected_B = true;
                selected_B_pos = new Vector2(x, y);
                //ChangeName(x, y, grid[x][y]);
                timer = 400-tc.scoreLevel*10;
            }
        }
    }

    private void Command_Create_Grid() {
        for(int x = 0; x < 17; x++) {
            for(int y = 0; y < 9; y++) {
                tc.gridI[x][y] = -1;
            }
        }
        int max = tc.difficulty == 2 ? 9*9-1 : 17*9-1;
        int filler = tc.scoreLevel*4 < max ? tc.scoreLevel*4 : max;
        int filler2 = filler;

        int color[] = new int[] {0,0,0,0,0,0,0,0};
        while(filler > 0) {
            int z = tc.rand.nextInt(8);
            color[z] += 2;
            filler -= 2;
            //for(int i = 0; i < 8; i++) {
            //	if(filler > 0) { color[i] += 2; filler -= 2; }
            //}
        }

        while(filler2 > 0) {
            int x = tc.rand.nextInt(tc.difficulty == 2 ? 9 : 17) + (tc.difficulty == 2 ? 4 : 0);
            int y = tc.rand.nextInt(9);
            if(tc.gridI[x][y] == -1) {
                for(int i = 0; i < 8; i++) {
                    if(color[i] > 0) {
                        tc.gridI[x][y] = i;
                        color[i]--;
                        filler2--;
                        break;
                    }
                }
            }
        }
    }

}
