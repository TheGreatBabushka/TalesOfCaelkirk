package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ItemTransactionMessage{
	
	public static void encode(ItemTransactionMessage pkt, PacketBuffer buf) {}
	public static ItemTransactionMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final ItemTransactionMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	boolean buying;
	String itemName;
	int count;
	UUID shopKeeperId;
	public ItemTransactionMessage() {}
	public ItemTransactionMessage(boolean isBuying, String itemName, int count, UUID shopKeeperId) {
		this.buying = isBuying;
		this.itemName = itemName;
		this.count = count;
		this.shopKeeperId = shopKeeperId;
	}
//	
//	public void toBytes(ByteBuf buf) {
//		buf.writeBoolean(buying);
//		buf.writeInt(count);
//		buf.writeLong(shopKeeperId.getMostSignificantBits());
//		buf.writeLong(shopKeeperId.getLeastSignificantBits());
//		buf.writeInt(itemName.toCharArray().length);
//		for(int i = 0; i < itemName.toCharArray().length; ++i) {
//			buf.writeByte(itemName.toCharArray()[i]);
//		}
//	}
//	
//	public void fromBytes(ByteBuf buf) {
//		buying = buf.readBoolean();
//		count = buf.readInt();
//		shopKeeperId = new UUID(buf.readLong(), buf.readLong());
//		byte[] chars = new byte[buf.readInt()];
//		buf.readBytes(chars);
//		itemName = new String(chars);
//	}
//	
//	public static class ItemTransactionMessageHandlerHandler<ItemTransactionMessage, IMessage>{
//		public IMessage onMessage(ItemTransactionMessage message, MessageContext ctx) {
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
//				public void run() {
//					Item item = Item.getByNameOrId(message.itemName);
//					if(item == null) {
//						System.out.println("WARNING *********** " + message.itemName + " did not map to an item!!!1!!!!");
//						return;
//					}
//					EntityPlayerMP pl = ctx.getServerHandler().player;
//					
//					ItemStack stack = new ItemStack(item, message.count);
//					EntityShopKeeper theKeeper = null;
//					List<EntityShopKeeper> entities = pl.world.getEntitiesWithinAABB(EntityShopKeeper.class, new AxisAlignedBB(pl.posX - 10, pl.posY - 10, pl.posZ - 10, pl.posX + 10, pl.posY + 10, pl.posZ + 10));
//					for(EntityShopKeeper e : entities) {
//						if(e.getUniqueID().equals(message.shopKeeperId)) {
//							theKeeper = e;
//						}
//					}
//					if(theKeeper != null) {
//						ShopData data = theKeeper.shopData;
//						ItemPrice price = message.buying ? data.getSellPriceForItem(item) : data.getBuyPriceForItem(item);
//						price = CurrencyOperations.multiply(price, message.count);
//						if(message.buying) {
//							if(CurrencyOperations.doesPlayerHaveEnoughMoney(pl, price)) {
//								if(pl.inventory.addItemStackToInventory(stack))
//									CurrencyOperations.subtractMoneyFromPlayer(pl, price);
//								else
//									pl.sendMessage(new TextComponentString("Not enough inventory space!"));
//							}else {
//								pl.sendMessage(new TextComponentString("Not enough money that!"));
//							}
//						}else {
//							ItemStack stackToSell = null;
//							for(ItemStack is : pl.inventory.mainInventory) {
//								if(is.getItem().equals(item)) {
//									stackToSell = is;
//									break;
//								}
//							}
//							if(pl.getHeldItemOffhand().getItem().equals(item)) {
//								stackToSell = pl.getHeldItemOffhand();
//							}
//							
//							if(stackToSell != null) {
//								stackToSell.shrink(message.count);
//								for(int i = 0; i < message.count; ++i) {
//									MinecraftForge.EVENT_BUS.post(new CoinsAddedToInventoryEvent(pl, price));
//								}
//							}
//						}
//					}
//				}
//			});
//			return null;
//		}
//	}
}
