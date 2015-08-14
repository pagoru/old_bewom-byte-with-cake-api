package es.bewom.p.commands;

import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandExecutor;
import org.cakepowered.api.command.CommandSender;

import es.bewom.p.Door;
import es.bewom.p.P;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandP extends CommandBase {
	
	public CommandP() {
		super("p");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(user.isAdmin()){
			
			player.sendMessage("Selecciona la primera puerta.");
			P.first = true;
			P.lastDoor = P.doors.size();
			P.doors.add(new Door());
			
		} else {
			
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			
		}
		
	}

}
