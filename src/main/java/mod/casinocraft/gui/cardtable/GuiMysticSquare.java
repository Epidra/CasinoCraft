package mod.casinocraft.gui.cardtable;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.container.ContainerCasino;
import mod.casinocraft.gui.GuiCasino;
import mod.casinocraft.tileentities.TileEntityBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.awt.event.KeyEvent;

public class GuiMysticSquare extends GuiCasino {


    //--------------------CONSTRUCTOR--------------------

    public GuiMysticSquare(InventoryPlayer playerInv, IInventory furnaceInv, int table){
        super(playerInv, furnaceInv, table, CasinoKeeper.MODULE_MYSTICSQUARE);
        tc.gridI = new     int[4][4];
        tc.gridB = new boolean[4][4];
    }



    //--------------------BASIC--------------------

    protected void mouseClicked2(double mouseX, double mouseY, int mouseButton){
        if (mouseButton == 0){
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){
                    if(mouseRect(32 + 48*x, 32 + 48*y, 48, 48, mouseX, mouseY)){
                        if(y > 0) if(getValue((x    ) + (y - 1)*4) == -1) actionTouch(0);
                        if(y < 3) if(getValue((x    ) + (y + 1)*4) == -1) actionTouch(1);
                        if(x > 0) if(getValue((x - 1) + (y    )*4) == -1) actionTouch(2);
                        if(x < 3) if(getValue((x + 1) + (y    )*4) == -1) actionTouch(3);
                    }
                }
            }
        }
    }

    protected void keyTyped2(int keyCode){
        if(keyCode == KEY_UP)   { actionTouch(0); }
        if(keyCode == KEY_DOWN) { actionTouch(1); }
        if(keyCode == KEY_LEFT) { actionTouch(2); }
        if(keyCode == KEY_RIGHT){ actionTouch(3); }
    }

    protected void drawGuiContainerForegroundLayer2(int mouseX, int mouseY){

    }

    protected void drawGuiContainerBackgroundLayer2(float partialTicks, int mouseX, int mouseY){
        if(tc.turnstate >= 2){
            this.mc.getTextureManager().bindTexture(CasinoKeeper.TEXTURE_MYSTICSQUARE);
            for(int y = 0; y < 4; y++) {
                for(int x = 0; x < 4; x++) {
                    if(getValue(x + y*4) != -1) {
                        this.drawTexturedModalRect(guiLeft + 32 + 48*x, guiTop + 32 + 48*y, Get_Spritesheet(true, getValue(x + y*4))*48, Get_Spritesheet(false, getValue(x + y*4))*48, 48, 48);
                    }
                }
            }
        }
    }



    //--------------------CUSTOM--------------------

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




    public String direction;



    //--------------------CONSTRUCTOR--------------------

   //public TileEntityMysticSquare(){
   //    super(null, null);
   //}

   //public TileEntityMysticSquare(TileEntityBoard te, BlockPos bp){
   //    super(te, bp);
   //    gridI = new     int[4][4];
   //    gridB = new boolean[4][4];
   //}

   //@Override
   //public String getGuiID() {
   //    return CasinoKeeper.GUIID_MYSTICSQUARE.toString();
   //}

   //@Override
   //public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
   //    return new ContainerCasino(playerInventory, this.board);
   //}

   //@Override
   //public ITextComponent getName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_MYSTICSQUARE.toString(), new Object[0]);
   //}

   //@Nullable
   //@Override
   //public ITextComponent getCustomName() {
   //    return new TextComponentTranslation(CasinoKeeper.GUIID_MYSTICSQUARE.toString(), new Object[0]);
   //}



    //--------------------BASIC--------------------

    public void start2(){
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                tc.gridI[x][ y] = -1;
                tc.gridB[x][ y] = false;
            }
        }
        int i = 0;
        while(i < 15) {
            int x = tc.rand.nextInt(4);
            int y = tc.rand.nextInt(4);
            if(tc.gridI[x][y] == -1) {
                tc.gridI[x][y] = i;
                i++;
            }
        }
        direction = "null";
    }

    public void actionTouch(int action){
        Move(action);
    }

    public void update(){

    }



    //--------------------GETTER--------------------

    public int getValue(int index){
        return tc.gridI[index % 4][index / 4];
    }



    //--------------------CUSTOM--------------------

    public void Move(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && y > 0) if(tc.gridI[x][y - 1] == -1) tc.gridB[x][y] = true; // UP
                if(direction == 1 && y < 3) if(tc.gridI[x][y + 1] == -1) tc.gridB[x][y] = true; // DOWN
                if(direction == 2 && x > 0) if(tc.gridI[x - 1][y] == -1) tc.gridB[x][y] = true; // LEFT
                if(direction == 3 && x < 3) if(tc.gridI[x + 1][y] == -1) tc.gridB[x][y] = true; // RIGHT
            }
        }
        Change(direction);
    }

    private void Change(int direction) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                if(direction == 0 && tc.gridB[x][y]) { tc.gridI[x][y - 1] = tc.gridI[x][y]; tc.gridI[x][y] = -1; tc.gridB[x][y] = false; } // UP
                if(direction == 1 && tc.gridB[x][y]) { tc.gridI[x][y + 1] = tc.gridI[x][y]; tc.gridI[x][y] = -1; tc.gridB[x][y] = false; } // DOWN
                if(direction == 2 && tc.gridB[x][y]) { tc.gridI[x - 1][y] = tc.gridI[x][y]; tc.gridI[x][y] = -1; tc.gridB[x][y] = false; } // LEFT
                if(direction == 3 && tc.gridB[x][y]) { tc.gridI[x + 1][y] = tc.gridI[x][y]; tc.gridI[x][y] = -1; tc.gridB[x][y] = false; } // RIGHT
            }
        }
    }

}
