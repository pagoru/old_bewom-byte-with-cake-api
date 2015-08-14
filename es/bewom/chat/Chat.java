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
			
			for(Player player : src){
				
				BewomUser user = BewomUser.getUser(player);
				if(user.getRegistration() == WebRegistration.VALID){
					player.sendMessage(formatedMSG);
				}
				
			}
			
		}
		
		l.add(p.getUniqueID(), msg);
	}
	
	public static String getCleanText(String t){
		
		if(t.length() > 5){
			String s1 = t.substring(0, 1).toUpperCase();
			String s2 = t.substring(t.length() - 1, t.length());
			String s3 = t.substring(1, t.length());
			
			if(!s2.equals("?") && !s2.equals("!") && !s2.equals(".") && !s2.equals(":") && !s2.equals(";")){
				return s1 + s3 + ".";
			}
			
			return s1 + s3;
		}
		
		return t;
	}
	
}
