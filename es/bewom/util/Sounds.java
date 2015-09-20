package es.bewom.util;

import org.cakepowered.api.base.Player;

import es.bewom.BewomByte;

public class Sounds {
	
	public static void playSoundToAll(String sound, float volume, float pitch){
		for(Player p : BewomByte.game.getServer().getOnlinePlayers()){
			p.playSound(sound, volume, pitch);
		}
	}

}
