package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;

public class CommandSpawn extends CommandBase {
	
	public CommandSpawn() {
		super("spawn");
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

		player.setLocation(CentroManager.centros.get(0).getLocation());		
		player.sendMessage(TextFormating.RED + "Has sido teletransportado al spawn.");
		
		Chat.sendMessage(player, null, "/spawn");
		
	}

}
