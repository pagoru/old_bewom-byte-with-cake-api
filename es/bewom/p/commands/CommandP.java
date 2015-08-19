package es.bewom.p.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
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
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(user.isAdmin()){
			
			player.sendMessage("Selecciona la primera puerta.");
			P.doors.add(new Door(player));
			
		} else {
			
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			
		}
		
	}

}
