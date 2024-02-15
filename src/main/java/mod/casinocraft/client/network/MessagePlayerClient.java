package mod.casinocraft.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MessagePlayerClient {
	
	static ItemStack stack;
	static int amount;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public MessagePlayerClient(Item item, int amount) {
		this.stack = new ItemStack(item, 1);
		this.amount = amount;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ENCODE / DECODE  ---------- ---------- ---------- ---------- //
	
	public static void encode (MessagePlayerClient msg, FriendlyByteBuf buf) {
		buf.writeItem(msg.stack);
		buf.writeInt(msg.amount);
	}
	
	public static MessagePlayerClient decode (FriendlyByteBuf buf) {
		ItemStack _stack = buf.readItem();
		int _amount = buf.readInt();
		return new MessagePlayerClient(_stack.getItem(), _amount);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  HANDLER  ---------- ---------- ---------- ---------- //
	
	public static class Handler {
		public static void handle (final MessagePlayerClient message, Supplier<NetworkEvent.Context> context) {
			Item item = message.stack.getItem();
			
			if(message.amount < 0){
				context.get().enqueueWork(() -> {
					int i = 0;
					ItemStack itemStack = ItemStack.EMPTY;
					Predicate<ItemStack> p_195408_1_ = Predicate.isEqual(message.stack);
					int count = -message.amount;
					
					for(int j = 0; j < Minecraft.getInstance().player.getInventory().getContainerSize(); ++j) {
						ItemStack itemstack = Minecraft.getInstance().player.getInventory().getItem(j);
						if (!itemstack.isEmpty() && p_195408_1_.test(itemstack)) {
							int k = count <= 0 ? itemstack.getCount() : Math.min(count - i, itemstack.getCount());
							i += k;
							if (count != 0) {
								itemstack.shrink(k);
								if (itemstack.isEmpty()) {
									Minecraft.getInstance().player.getInventory().setItem(j, ItemStack.EMPTY);
								}
							}
						}
					}
					
					if (!itemStack.isEmpty() && p_195408_1_.test(itemStack)) {
						int l = count <= 0 ? itemStack.getCount() : Math.min(count - i, itemStack.getCount());
						i += l;
						if (count != 0) {
							itemStack.shrink(l);
							if (itemStack.isEmpty()) {
								itemStack = ItemStack.EMPTY;
							}
						}
					}
				});
			} else {
				context.get().enqueueWork(() -> {
					Minecraft.getInstance().player.getInventory().add(new ItemStack(item, message.amount));
				});
			}
			context.get().setPacketHandled(true);
		}
	}
	
	
	
}
