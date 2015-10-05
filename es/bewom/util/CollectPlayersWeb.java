package es.bewom.util;

import java.util.Collection;
import java.util.Date;

import org.cakepowered.api.base.Player;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;

public class CollectPlayersWeb {
	
	public static int i = 1200;
	public static int j = 1200;
	
	public static void on(Collection<Player> players, Date date){
		if(i == 1200){
			BewomByte.m.executeQuery("INSERT INTO `playersUsage`(`players`) VALUES ('" + players.size() + "')", null);
			
			BewomByte.m.executeQuery("DELETE FROM `serverPing`", null);
			BewomByte.m.executeQuery("DELETE FROM `PlayersOnLine`", null);
			BewomByte.m.executeQuery("INSERT INTO `serverPing`(`lastPing`) VALUES ('" + (date.getTime()/1000) +"')", null);
			if(players.size() != 0){
				String pls = "INSERT INTO `PlayersOnLine` (`name`) VALUES ";
				for (Player p : players) {
					pls += "('" + p.getUserName() + "'), ";
				}
				pls = pls.substring(0, pls.length() - 2);
				BewomByte.m.executeQuery(pls, null);
			}
			i = 0;
		}
		if(j == 12000){
			j = 0;
		}
		j++;
		i++;
	}
	
	public static void on(Player p){
		int timeInMinutes = Integer.parseInt(BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + p.getUniqueID() + "'", "timePlaying").get(0));
		timeInMinutes++;
		BewomByte.m.executeQuery("UPDATE `users` SET `timePlaying`='" + timeInMinutes + "' WHERE `uuid`='" + p.getUniqueID() + "'", null);
	}
	
}
