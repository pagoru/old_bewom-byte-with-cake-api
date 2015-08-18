package es.bewom.util;

import org.cakepowered.api.base.Player;

import es.bewom.BewomByte;

public class BewomUtils {

	public static void addItemStack(Player p, String unlocalizedName, int quantity){
		BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "give " + p.getName() + " " + unlocalizedName  +" " + quantity);	
	}
}
