package es.bewom.chat;

import java.util.Calendar;
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
							sendMessage(player, formatedMSG);
							sendMessage(p, formatedMSG);
						} else if(user.getPermissionLevel() == BewomUser.PERM_LEVEL_ADMIN){
							if(!p.equals(player)){
								sendMessage(player, TextFormating.GREEN + u.getMpPlayer().getUserName() + " from " + formatedMSG);
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
			if(user.getPermissionLevel() == BewomUser.PERM_LEVEL_ADMIN){
				p.sendMessageWithLinks(getTime() + colorsMessage(formatedMSG));
			} else {
				p.sendMessage(getTime() + Censure.censureText(formatedMSG));
			}
		}
	}
	
	private static String[][] aa = {
			
			{"&0", "" + TextFormating.BLACK + ""},
			{"&1", "" + TextFormating.DARK_BLUE + ""},
			{"&2", "" + TextFormating.DARK_GREEN + ""},
			{"&3", "" + TextFormating.DARK_AQUA + ""},
			{"&4", "" + TextFormating.DARK_RED + ""},
			{"&5", "" + TextFormating.DARK_PURPLE + ""},
			{"&6", "" + TextFormating.GOLD + ""},
			{"&7", "" + TextFormating.GRAY + ""},
			{"&8", "" + TextFormating.DARK_GRAY + ""},
			{"&9", "" + TextFormating.BLUE + ""},
			{"&a", "" + TextFormating.GREEN + ""},
			{"&b", "" + TextFormating.AQUA + ""},
			{"&c", "" + TextFormating.RED + ""},
			{"&d", "" + TextFormating.LIGHT_PURPLE + ""},
			{"&e", "" + TextFormating.YELLOW + ""},
			{"&f", "" + TextFormating.WHITE + ""},
				
			{"&k", "" + TextFormating.OBFUSCATED + ""},
			{"&l", "" + TextFormating.BOLD + ""},
			{"&m", "" + TextFormating.STRIKETHROUGH + ""},
			{"&n", "" + TextFormating.UNDERLINE + ""},
			{"&o", "" + TextFormating.ITALIC + ""},
			{"&r", "" + TextFormating.RESET + ""}
			
	};
	
	private static String colorsMessage(String msg){ //TODO colores
		
		return msg.replaceAll("&([0-9a-f])", "\u00A7$1");
		
	}
	
	/*
	private static String replaceColors(String msg){
		
		msg.replaceAll("&0", "" + TextFormating.BLACK + "");
		msg.replaceAll("&1", "" + TextFormating.DARK_BLUE + "");
		msg.replaceAll("&2", "" + TextFormating.DARK_GREEN + "");
		msg.replaceAll("&3", "" + TextFormating.DARK_AQUA + "");
		msg.replaceAll("&4", "" + TextFormating.DARK_RED + "");
		msg.replaceAll("&5", "" + TextFormating.DARK_PURPLE + "");
		msg.replaceAll("&6", "" + TextFormating.GOLD + "");
		msg.replaceAll("&7", "" + TextFormating.GRAY + "");
		msg.replaceAll("&8", "" + TextFormating.DARK_GRAY + "");
		msg.replaceAll("&9", "" + TextFormating.BLUE + "");
		msg.replaceAll("&a", "" + TextFormating.GREEN + "");
		msg.replaceAll("&b", "" + TextFormating.AQUA + "");
		msg.replaceAll("&c", "" + TextFormating.RED + "");
		msg.replaceAll("&d", "" + TextFormating.LIGHT_PURPLE + "");
		msg.replaceAll("&e", "" + TextFormating.YELLOW + "");
		msg.replaceAll("&f", "" + TextFormating.WHITE + "");
			
		msg.replaceAll("&k", "" + TextFormating.OBFUSCATED + "");
		msg.replaceAll("&l", "" + TextFormating.BOLD + "");
		msg.replaceAll("&m", "" + TextFormating.STRIKETHROUGH + "");
		msg.replaceAll("&n", "" + TextFormating.UNDERLINE + "");
		msg.replaceAll("&o", "" + TextFormating.ITALIC + "");
		msg.replaceAll("&r", "" + TextFormating.RESET + "");
		
		return msg;
		
	}
	*/
	
	public static String getTime(){
		
		Calendar c = Calendar.getInstance();
		String 	m 	= "0";
		String 	h 	= "0";
		int 	mi 	= c.get(Calendar.MINUTE);
		int 	hi 	= c.get(Calendar.HOUR_OF_DAY);
		
		if(mi < 10){
			m += mi;
		} else {
			m = mi + "";
		}
		
		if(hi < 10){
			h += hi;
		} else {
			h = hi + "";
		}
		
		return "[" + h + ":" + m + "] ";
		
	}
	
}
