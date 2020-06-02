package net.casinocraft.mod.blocks;

import java.util.Random;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.casinocraft.mod.CasinoCraft;
import net.casinocraft.mod.tileentity.TileEntityVideoPoker;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockVideoPoker extends BlockContainer {
	
	public static final int[][] field_a = new int[][] {{0, 1}, { -1, 0}, {0, -1}, {1, 0}};
	IIcon iconSide;
	IIcon iconBottom;
	 private static final String __OBFID = "CL_00000198";
	
	private final boolean isActive;
	
	private static boolean keepInventory = true;
	private Random rand = new Random();
	
	public BlockVideoPoker(){
		super(Material.wood);
		this.setHardness(5.0F);
		this.isActive = true;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){
		this.blockIcon = iconRegister.registerIcon(CasinoCraft.modid + ":" + this.getUnlocalizedName().substring(5) + "Top");
		this.iconSide = iconRegister.registerIcon(CasinoCraft.modid + ":" + this.getUnlocalizedName().substring(5) + "Side");
		this.iconBottom = iconRegister.registerIcon(CasinoCraft.modid + ":" + this.getUnlocalizedName().substring(5) + "Bottom");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(side == 1) return blockIcon;
		if(side == 0) return iconBottom;
		if(side == 2) return iconSide;
		if(side == 3) return iconSide;
		if(side == 4) return iconSide;
		if(side == 5) return iconSide;
		return blockIcon;
	}
	
	public Item getItemDropped(int i, Random random, int j){
		return Item.getItemFromBlock(CasinoCraft.blockVideoPoker);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if(world.isRemote){
			return true;
		}else if(!player.isSneaking()){
			FMLNetworkHandler.openGui(player, CasinoCraft.instance, CasinoCraft.guiIDVideoPoker, world, x, y, z);
		}
		return true;
	}
	
	public static boolean func_149976_c(int p_149976_0_){
		return (p_149976_0_ & 4) != 0;
	}
	
	public TileEntity createNewTileEntity(World world, int i){
		return new TileEntityVideoPoker();
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		int l = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if(l == 0){ world.setBlockMetadataWithNotify(x, y, z, 2, 2); }
		if(l == 1){ world.setBlockMetadataWithNotify(x, y, z, 5, 2); }
		if(l == 2){ world.setBlockMetadataWithNotify(x, y, z, 3, 2); }
		if(l == 3){ world.setBlockMetadataWithNotify(x, y, z, 4, 2); }
	}
	
	public Item getItem(World world, int x, int y, int z){
		return Item.getItemFromBlock(CasinoCraft.blockVideoPoker);
	}
	
}
