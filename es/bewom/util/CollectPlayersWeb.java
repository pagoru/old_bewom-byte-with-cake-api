package es.bewom.util;

import java.util.Collection;
import java.util.Date;

import org.cakepowered.api.base.Player;

import es.bewom.user.BewomUser;

public class CollectPlayersWeb {
	
	public static int i = 1200;
	
	public static void on(Collection<Player> players, Date date){
		if(i == 1200){
			BewomUser.m.executeQuery("DELETE FROM `serverPing`", null);
			BewomUser.m.executeQuery("DELETE FROM `PlayersOnLine`", null);
			BewomUser.m.executeQuery("INSERT INTO `serverPing`(`lastPing`) VALUES ('" + (date.getTime()/1000) +"')", null);
			if(players.size() != 0){
				String pls = "INSERT INTO `PlayersOnLine` (`name`) VALUES ";
				for (Player p : players) {
					pls += "('" + p.getUserName() + "'), ";
				}
				pls = pls.substring(0, pls.length() - 2);
				BewomUser.m.executeQuery(pls, null);
			}
			i = 0;
		}
		i++;
	}
	
}
