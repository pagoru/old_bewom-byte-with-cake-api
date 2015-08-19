package es.bewom.p.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;import es.bewom.economy.House;
import es.bewom.economy.Houses;
import es.bewom.p.Door;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandH extends CommandBase {

	public CommandH() {
		super("h");
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(user.isAdmin()){
			
			player.sendMessage("Selecciona una puerta valida.");
			Houses.houses.add(new House(player));
			
		} else {
			
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			
		}
		
	}

}
