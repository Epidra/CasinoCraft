package mod.casinocraft.system;

import mod.casinocraft.CasinoKeeper;
import mod.casinocraft.blocks.BlockCardTableBase;
import mod.casinocraft.blocks.BlockCardTableWide;
import mod.casinocraft.container.ContainerArcade;
import mod.casinocraft.container.ContainerCardTable;
import mod.casinocraft.container.minigames.Container2048;
import mod.casinocraft.container.minigames.ContainerAceyDeucey;
import mod.casinocraft.container.minigames.ContainerBaccarat;
import mod.casinocraft.container.minigames.ContainerBlackJack;
import mod.casinocraft.container.minigames.ContainerColumns;
import mod.casinocraft.container.minigames.ContainerCraps;
import mod.casinocraft.container.minigames.ContainerFreeCell;
import mod.casinocraft.container.minigames.ContainerHalma;
import mod.casinocraft.container.minigames.ContainerKlondike;
import mod.casinocraft.container.minigames.ContainerMeanMinos;
import mod.casinocraft.container.minigames.ContainerMemory;
import mod.casinocraft.container.minigames.ContainerMinesweeper;
import mod.casinocraft.container.minigames.ContainerMysticSquare;
import mod.casinocraft.container.minigames.ContainerPyramid;
import mod.casinocraft.container.minigames.ContainerRougeEtNoir;
import mod.casinocraft.container.minigames.ContainerRoulette;
import mod.casinocraft.container.minigames.ContainerSicBo;
import mod.casinocraft.container.minigames.ContainerSlotMachine;
import mod.casinocraft.container.minigames.ContainerSnake;
import mod.casinocraft.container.minigames.ContainerSokoban;
import mod.casinocraft.container.minigames.ContainerSpider;
import mod.casinocraft.container.minigames.ContainerSudoku;
import mod.casinocraft.container.minigames.ContainerTetris;
import mod.casinocraft.container.minigames.ContainerTriPeak;
import mod.casinocraft.container.minigames.ContainerVideoPoker;
import mod.casinocraft.gui.GuiArcade;
import mod.casinocraft.gui.GuiCardTable;
import mod.casinocraft.gui.minigames.Gui2048;
import mod.casinocraft.gui.minigames.GuiAceyDeucey;
import mod.casinocraft.gui.minigames.GuiBaccarat;
import mod.casinocraft.gui.minigames.GuiBlackJack;
import mod.casinocraft.gui.minigames.GuiColumns;
import mod.casinocraft.gui.minigames.GuiCraps;
import mod.casinocraft.gui.minigames.GuiFreeCell;
import mod.casinocraft.gui.minigames.GuiHalma;
import mod.casinocraft.gui.minigames.GuiKlondike;
import mod.casinocraft.gui.minigames.GuiMeanMinos;
import mod.casinocraft.gui.minigames.GuiMemory;
import mod.casinocraft.gui.minigames.GuiMinesweeper;
import mod.casinocraft.gui.minigames.GuiMysticSquare;
import mod.casinocraft.gui.minigames.GuiPyramid;
import mod.casinocraft.gui.minigames.GuiRougeEtNoir;
import mod.casinocraft.gui.minigames.GuiRoulette;
import mod.casinocraft.gui.minigames.GuiSicBo;
import mod.casinocraft.gui.minigames.GuiSlotMachine;
import mod.casinocraft.gui.minigames.GuiSnake;
import mod.casinocraft.gui.minigames.GuiSokoban;
import mod.casinocraft.gui.minigames.GuiSpider;
import mod.casinocraft.gui.minigames.GuiSudoku;
import mod.casinocraft.gui.minigames.GuiTetris;
import mod.casinocraft.gui.minigames.GuiTriPeak;
import mod.casinocraft.gui.minigames.GuiVideoPoker;
import mod.casinocraft.tileentities.TileEntityBoard;
import mod.casinocraft.tileentities.minigames.TileEntity2048;
import mod.casinocraft.tileentities.minigames.TileEntityAceyDeucey;
import mod.casinocraft.tileentities.minigames.TileEntityBaccarat;
import mod.casinocraft.tileentities.minigames.TileEntityBlackJack;
import mod.casinocraft.tileentities.minigames.TileEntityColumns;
import mod.casinocraft.tileentities.minigames.TileEntityCraps;
import mod.casinocraft.tileentities.minigames.TileEntityFreeCell;
import mod.casinocraft.tileentities.minigames.TileEntityHalma;
import mod.casinocraft.tileentities.minigames.TileEntityKlondike;
import mod.casinocraft.tileentities.minigames.TileEntityMeanMinos;
import mod.casinocraft.tileentities.minigames.TileEntityMemory;
import mod.casinocraft.tileentities.minigames.TileEntityMinesweeper;
import mod.casinocraft.tileentities.minigames.TileEntityMysticSquare;
import mod.casinocraft.tileentities.minigames.TileEntityPyramid;
import mod.casinocraft.tileentities.minigames.TileEntityRougeEtNoir;
import mod.casinocraft.tileentities.minigames.TileEntityRoulette;
import mod.casinocraft.tileentities.minigames.TileEntitySicBo;
import mod.casinocraft.tileentities.minigames.TileEntitySlotMachine;
import mod.casinocraft.tileentities.minigames.TileEntitySnake;
import mod.casinocraft.tileentities.minigames.TileEntitySokoban;
import mod.casinocraft.tileentities.minigames.TileEntitySpider;
import mod.casinocraft.tileentities.minigames.TileEntitySudoku;
import mod.casinocraft.tileentities.minigames.TileEntityTetris;
import mod.casinocraft.tileentities.minigames.TileEntityTriPeak;
import mod.casinocraft.tileentities.minigames.TileEntityVideoPoker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te != null){
        	if(ID == CasinoKeeper.GuiID.ARCADE   .ordinal()) if(te instanceof TileEntityBoard){ return new ContainerArcade   (player.inventory, (IInventory)te); }
        	if(ID == CasinoKeeper.GuiID.CARDTABLE.ordinal()) if(te instanceof TileEntityBoard){ return new ContainerCardTable(player.inventory, (IInventory)te); }
        	
        	if(ID == CasinoKeeper.GuiID.BACCARAT    .ordinal()) return new ContainerBaccarat    (player.inventory, new TileEntityBaccarat    ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.BLACKJACK   .ordinal()) return new ContainerBlackJack   (player.inventory, new TileEntityBlackJack   ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.MEMORY      .ordinal()) return new ContainerMemory      (player.inventory, new TileEntityMemory      ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.ROULETTE    .ordinal()) return new ContainerRoulette    (player.inventory, new TileEntityRoulette    ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.MYSTICSQUARE.ordinal()) return new ContainerMysticSquare(player.inventory, new TileEntityMysticSquare((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.PYRAMID     .ordinal()) return new ContainerPyramid     (player.inventory, new TileEntityPyramid     ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.SUDOKU      .ordinal()) return new ContainerSudoku      (player.inventory, new TileEntitySudoku      ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.TRIPEAK     .ordinal()) return new ContainerTriPeak     (player.inventory, new TileEntityTriPeak     ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.ACEYDEUCEY  .ordinal()) return new ContainerAceyDeucey  (player.inventory, new TileEntityAceyDeucey  ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.HALMA       .ordinal()) return new ContainerHalma       (player.inventory, new TileEntityHalma       ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.ROUGEETNOIR .ordinal()) return new ContainerRougeEtNoir (player.inventory, new TileEntityRougeEtNoir ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.CRAPS       .ordinal()) return new ContainerCraps       (player.inventory, new TileEntityCraps       ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.SICBO       .ordinal()) return new ContainerSicBo       (player.inventory, new TileEntitySicBo       ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.KLONDIKE    .ordinal()) return new ContainerKlondike    (player.inventory, new TileEntityKlondike    ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.FREECELL    .ordinal()) return new ContainerFreeCell    (player.inventory, new TileEntityFreeCell    ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.SPIDER      .ordinal()) return new ContainerSpider      (player.inventory, new TileEntitySpider      ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.VIDEOPOKER  .ordinal()) return new ContainerVideoPoker  (player.inventory, new TileEntityVideoPoker  ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.MINESWEEPER .ordinal()) return new ContainerMinesweeper (player.inventory, new TileEntityMinesweeper ((TileEntityBoard) world.getTileEntity(pos), pos));
        	
        	if(ID == CasinoKeeper.GuiID.TETRIS     .ordinal()) return new ContainerTetris     (player.inventory, new TileEntityTetris     ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.COLUMNS    .ordinal()) return new ContainerColumns    (player.inventory, new TileEntityColumns    ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.MEANMINOS  .ordinal()) return new ContainerMeanMinos  (player.inventory, new TileEntityMeanMinos  ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.SOKOBAN    .ordinal()) return new ContainerSokoban    (player.inventory, new TileEntitySokoban    ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID._2048      .ordinal()) return new Container2048       (player.inventory, new TileEntity2048       ((TileEntityBoard) world.getTileEntity(pos), pos));
        	if(ID == CasinoKeeper.GuiID.SNAKE      .ordinal()) return new ContainerSnake      (player.inventory, new TileEntitySnake      ((TileEntityBoard) world.getTileEntity(pos), pos));
        	
        	if(ID == CasinoKeeper.GuiID.SLOTMACHINE.ordinal()) return new ContainerSlotMachine(player.inventory, new TileEntitySlotMachine((TileEntityBoard) world.getTileEntity(pos), pos));
        }
        return null;
    }
	
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if(te != null){
        	if(ID == CasinoKeeper.GuiID.ARCADE   .ordinal()) if(te instanceof TileEntityBoard){ return new GuiArcade   (player.inventory, (IInventory)te); }
        	if(ID == CasinoKeeper.GuiID.CARDTABLE.ordinal()) if(te instanceof TileEntityBoard){ return new GuiCardTable(player.inventory, (IInventory)te); }
        	
        	int table = world.getBlockState(pos).getBlock() instanceof BlockCardTableBase ? 1 : world.getBlockState(pos).getBlock()instanceof BlockCardTableWide ? 2 : 0;
        	
        	if(ID == CasinoKeeper.GuiID.BACCARAT    .ordinal()) return new GuiBaccarat    (player.inventory, new TileEntityBaccarat    ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.BLACKJACK   .ordinal()) return new GuiBlackJack   (player.inventory, new TileEntityBlackJack   ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.MEMORY      .ordinal()) return new GuiMemory      (player.inventory, new TileEntityMemory      ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.ROULETTE    .ordinal()) return new GuiRoulette    (player.inventory, new TileEntityRoulette    ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.MYSTICSQUARE.ordinal()) return new GuiMysticSquare(player.inventory, new TileEntityMysticSquare((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.PYRAMID     .ordinal()) return new GuiPyramid     (player.inventory, new TileEntityPyramid     ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.SUDOKU      .ordinal()) return new GuiSudoku      (player.inventory, new TileEntitySudoku      ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.TRIPEAK     .ordinal()) return new GuiTriPeak     (player.inventory, new TileEntityTriPeak     ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.ACEYDEUCEY  .ordinal()) return new GuiAceyDeucey  (player.inventory, new TileEntityAceyDeucey  ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.HALMA       .ordinal()) return new GuiHalma       (player.inventory, new TileEntityHalma       ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.ROUGEETNOIR .ordinal()) return new GuiRougeEtNoir (player.inventory, new TileEntityRougeEtNoir ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.CRAPS       .ordinal()) return new GuiCraps       (player.inventory, new TileEntityCraps       ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.SICBO       .ordinal()) return new GuiSicBo       (player.inventory, new TileEntitySicBo       ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.KLONDIKE    .ordinal()) return new GuiKlondike    (player.inventory, new TileEntityKlondike    ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.FREECELL    .ordinal()) return new GuiFreeCell    (player.inventory, new TileEntityFreeCell    ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.SPIDER      .ordinal()) return new GuiSpider      (player.inventory, new TileEntitySpider      ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.VIDEOPOKER  .ordinal()) return new GuiVideoPoker  (player.inventory, new TileEntityVideoPoker  ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.MINESWEEPER .ordinal()) return new GuiMinesweeper (player.inventory, new TileEntityMinesweeper ((TileEntityBoard) te, pos), table);
        	
        	if(ID == CasinoKeeper.GuiID._2048      .ordinal()) return new Gui2048       (player.inventory, new TileEntity2048       ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.TETRIS     .ordinal()) return new GuiTetris     (player.inventory, new TileEntityTetris     ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.COLUMNS    .ordinal()) return new GuiColumns    (player.inventory, new TileEntityColumns    ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.MEANMINOS  .ordinal()) return new GuiMeanMinos  (player.inventory, new TileEntityMeanMinos  ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.SNAKE      .ordinal()) return new GuiSnake      (player.inventory, new TileEntitySnake      ((TileEntityBoard) te, pos), table);
        	if(ID == CasinoKeeper.GuiID.SOKOBAN    .ordinal()) return new GuiSokoban    (player.inventory, new TileEntitySokoban    ((TileEntityBoard) te, pos), table);
        	
        	if(ID == CasinoKeeper.GuiID.SLOTMACHINE.ordinal()) return new GuiSlotMachine(player.inventory, new TileEntitySlotMachine((TileEntityBoard) te, pos), table);
        }
        return null;
    }
	
}
