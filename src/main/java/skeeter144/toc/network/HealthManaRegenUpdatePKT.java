package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class HealthManaRegenUpdatePKT {
	public static void encode(HealthManaRegenUpdatePKT pkt, PacketBuffer buf) {}
	public static HealthManaRegenUpdatePKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final HealthManaRegenUpdatePKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public HealthManaRegenUpdatePKT() {}
	
	float mana, health;
	public HealthManaRegenUpdatePKT(float health, float mana) {
	this.health = health;
	this.mana = mana;
	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		health = buf.readFloat();
//		mana = buf.readFloat();
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeFloat(health);
//		buf.writeFloat(mana);
//	}
//
//
//	public static class HealthManaRegenUpdateHandlerHandler<HealthManaRegenUpdateMessage, IMessage>{
//		
//		@Override
//		public IMessage onMessage(HealthManaRegenUpdateMessage message, MessageContext ctx) {
//			TOCMain.localPlayer.setHealthAndManaRegen(message.health, message.mana);
//			return null;
//		}
//	
//	}
	
}