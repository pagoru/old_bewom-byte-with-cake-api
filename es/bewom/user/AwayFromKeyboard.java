package es.bewom.user;

import org.cakepowered.api.util.text.TextFormating;

public class AwayFromKeyboard {
	
	public static void AFK(BewomUser u){
		
		u.addAfkTime();
		u.setAfkYawAndPitch(u.getPlayer().getLocation().getYaw(), u.getPlayer().getLocation().getPitch());
		
		if(u.isAfk()){
			u.getPlayer().kick(TextFormating.RED + "Away From Keyboard.");
		}
		
	}

}
