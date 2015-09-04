package es.bewom.centrospokemon.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandQuitarCentro extends CommandBase {

	public CommandQuitarCentro() {
		super("quitarcentro", "quitarcp", "qcp");
	}

	@Override
	public String getCommandUsage(CommandSender commandSender) {
		return "Eliminar el centro pokemon de la posici�n actual.";
	}
	
	@Override
	public boolean canBeUsedBy(CommandSender commandSender){
		if(commandSender.getPlayer() != null){
			if(BewomUser.getUser(commandSender.getPlayer()).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN){
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		Player player;

		if(commandSender.getPlayer() != null) {
			player = commandSender.getPlayer();
		} else {
			commandSender.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return;
		}
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		String error = CentroManager.remove(player.getLocation());

		if(error != null) {
			player.sendMessage(TextFormating.RED + error);
			return;
		}

		player.sendMessage(TextMessages.CENTER_REMOVED);
		CentroManager.save();
	}

}
