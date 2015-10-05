package es.bewom.user;

import java.util.Date;

import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.util.text.TextFormating;

import com.ibm.icu.util.Calendar;

public class AwayFromKeyboard {
	
	public static long dateInteract;
	public static void AFK(BewomUser u){
		long date = new Date().getTime()/1000;
		
		if(u != null){
			if(date != dateInteract){
				u.addAfkTime();
				u.setAfkYawAndPitch(u.getPlayer().getLocation().getYaw(), u.getPlayer().getLocation().getPitch());
			} else {
				u.removeAfkTime();			
			}
			
			if(u.isAfk()){
				u.getPlayer().kick(TextFormating.RED + "Away From Keyboard.");
			}
		}
		
	}

	public static void on(PlayerInteractEvent event) {
		dateInteract = new Date().getTime()/1000;		
	}
}
