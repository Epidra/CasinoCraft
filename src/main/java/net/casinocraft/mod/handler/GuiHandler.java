package net.casinocraft.mod.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.container.ContainerBlackJack;
import net.casinocraft.mod.container.ContainerVideoPoker;
import net.casinocraft.mod.gui.GuiBlackJack;
import net.casinocraft.mod.gui.GuiVideoPoker;
import net.casinocraft.mod.tileentity.TileEntityBlackJack;
import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity != null){
			switch(ID){
				case CasinoCraft.guiIDBlackJack: if(entity instanceof TileEntityBlackJack){ return new ContainerBlackJack(player.inventory, (TileEntityBlackJack) entity); } return null;
				case CasinoCraft.guiIDVideoPoker: if(entity instanceof TileEntityVideoPoker){ return new ContainerVideoPoker(player.inventory, (TileEntityVideoPoker) entity); } return null;
			}
			return null;
		}
		return entity;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity != null){
			switch(ID){
				case CasinoCraft.guiIDBlackJack: if(entity instanceof TileEntityBlackJack){return new GuiBlackJack(player.inventory, (TileEntityBlackJack) entity); } return null;
				case CasinoCraft.guiIDVideoPoker: if(entity instanceof TileEntityVideoPoker){return new GuiVideoPoker(player.inventory, (TileEntityVideoPoker) entity); } return null;
			}
			return null;
		}
		return entity;
	}
	
}
