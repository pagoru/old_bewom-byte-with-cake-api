package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandGM extends CommandBase {
	
	int SURVIVAL = 0;
	int CREATIVE = 1;
	int ADVENTURE = 2;
	int SPECTATOR = 3;
	
	public CommandGM() {
		super("gm");
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

		BewomUser user = BewomUser.getUser(player);
		if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			return;
		}
		
		int gm = 0;
		
		switch(args[0]) {
		case "survival":
			gm = SURVIVAL;
			break;
		case "s":
			gm = SURVIVAL;
			break;
		case "0":
			gm = SURVIVAL;
			break;
			
		case "creative":
			gm = CREATIVE;
			break;
		case "c":
			gm = CREATIVE;
			break;
		case "1":
			gm = CREATIVE;
			break;
			
		case "adventure":
			gm = ADVENTURE;
			break;
		case "a":
			gm = ADVENTURE;
			break;
		case "2":
			gm = ADVENTURE;
			break;
			
		case "spectator":
			gm = SPECTATOR;
			break;
		case "3":
			gm = SPECTATOR;
			break;
		}
		
		player.setGameMode(gm);
		player.sendMessage(TextFormating.RED + "Gamemode actualizado.");
		
	}

}
