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
	
	public static void save(){
		
		l.saveLog();
		
	}
	
	public static void sendMessage(Player p, String formatedMSG, String msg){
		String[] m = msg.split(" ");
		if(!m[0].substring(0, 1).equals("/")){
			for (int i = 0; i < m.length; i++) {
				for (Player player : game.getServer().getOnlinePlayers()){
					if(m[i].equalsIgnoreCase(player.getUserName())){
						player.playSound("random.successful_hit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					}
				}
			}
		}
		if(p != null){
			BewomUser u = BewomUser.getUser(p);
			if(formatedMSG != null){
				Collection<Player> src = game.getServer().getOnlinePlayers();
				for(Player player : src){
					BewomUser user = BewomUser.getUser(player);
					if(u.getMpPlayer() != null && game.getServer().getOnlinePlayers().contains(u.getMpPlayer())){
						u.getMpPlayer().playSound("random.successful_hit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						if(u.getMpPlayer().getUserName().equals(player.getUserName())){
							sendMessage(player, TextFormating.GRAY + "/" + u.getMpPlayer().getUserName() + formatedMSG);
							sendMessage(p, TextFormating.GRAY + "/" + u.getMpPlayer().getUserName() + formatedMSG);
						} else if(user.isAdmin()){
							if(!p.equals(player)){
								sendMessage(player, TextFormating.GRAY + "/" + u.getMpPlayer().getUserName() + formatedMSG);
							}
						}
					} else {
						u.setMpPlayer(null);
						sendMessage(player, formatedMSG);
					}
				}
			}
			if(u.getMpPlayer() != null){
				l.add(p, "/mp " + u.getMpPlayer().getUserName()+ "(" + u.getMpPlayer().getUniqueID() + ")" + msg);
			} else {
				l.add(p, msg);
			}
		} else {
			l.add(null, msg);
		}
	}
	
	public static void sendMessage(Player p, String formatedMSG){
		BewomUser user = BewomUser.getUser(p);
		if(user.getRegistration() == WebRegistration.VALID){
			if(user.isAdmin()){
				p.sendMessageWithLinks(formatedMSG);
			} else {
				p.sendMessage(Censure.censureText(formatedMSG));
			}
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
