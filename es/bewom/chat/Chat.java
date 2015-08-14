package es.bewom.chat;

import java.util.Collection;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;
import es.bewom.user.WebRegistration;

public class Chat {
	
	private static Game game = BewomByte.game;
	private static BewomLog l = new BewomLog();
	
	public static void sendMessage(Player p, String formatedMSG, String msg){
		
		if(formatedMSG != null){
			
			Collection<Player> src = game.getServer().getOnlinePlayers();
			
			game.getServer().sendMessageToAll(formatedMSG);		
			
		}
		
		l.add(p.getUniqueID(), msg);
	}

}
