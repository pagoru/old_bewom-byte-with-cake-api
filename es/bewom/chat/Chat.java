package es.bewom.chat;

import java.util.Collection;
import java.util.Random;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;
import es.bewom.user.WebRegistration;

public class Chat {
	
	private static Game game = BewomByte.game;
	private static BewomLog l = new BewomLog();
	private static Random rand = new Random();
	
	public static void sendMessage(Player p, String formatedMSG, String msg){
		String[] m = msg.split(" ");
		for (int i = 0; i < m.length; i++) {
			for (Player player : game.getServer().getOnlinePlayers()){
				if(m[i].equals(player.getUserName())){
					player.playSound("random.pop", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
				}
			}
		}
		BewomUser u = BewomUser.getUser(p);
		if(formatedMSG != null){
			Collection<Player> src = game.getServer().getOnlinePlayers();			
			for(Player player : src){
				BewomUser user = BewomUser.getUser(player);
				if(u.getMpPlayer() != null){
					if(u.getMpPlayer().getUserName().equals(player.getUserName()) || user.isAdmin()){
						sendMessage(player, TextFormating.GRAY + "/" + u.getMpPlayer().getUserName() + formatedMSG);
					}
				} else {
					sendMessage(player, formatedMSG);
				}
			}
		}
		if(u.getMpPlayer() != null){
			l.add(p, "/mp " + u.getMpPlayer().getUserName()+ "(" + u.getMpPlayer().getUniqueID() + ")" + msg);
		} else {
			l.add(p, msg);
		}
	}
	
	public static void sendMessage(Player p, String formatedMSG){
		BewomUser user = BewomUser.getUser(p);
		if(user.getRegistration() == WebRegistration.VALID){
			p.sendMessage(formatedMSG);
		}
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
