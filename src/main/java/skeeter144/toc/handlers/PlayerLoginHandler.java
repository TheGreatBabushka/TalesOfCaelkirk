package skeeter144.toc.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SetClientTOCPlayerPKT;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerLoginHandler {

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
	   EntityPlayer player = event.getPlayer();
		if(player.world.isRemote) {
		   return;
	   }
	   
	   if(TOCMain.pm.hasPlayerPreviouslyPlayed(player)) {
		   player.sendMessage(new TextComponentTranslation("Welcome back, traveller.  I see you haven't been eaten by a monster yet.  This is good..."));
	   }else {
		   player.sendMessage(new TextComponentTranslation("Welome to the Land of Caelkirk, traveller.  So now, here begins your tale..."));
	   }
	   
	   TOCPlayer tocPlayer = TOCMain.pm.getPlayer(player);
	   Network.INSTANCE.sendTo(new SetClientTOCPlayerPKT(tocPlayer), (EntityPlayerMP)player);
	}
	
	
	
}