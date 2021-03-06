package es.bewom.economy.commands;

import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.user.BewomUser;

public class CommandDinero extends CommandBase {

	public CommandDinero() {
		super("dinero", "d");
		
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		return null;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		
		String message = TextFormating.GREEN + "Tienes " + BewomUser.getMoney(player.getUniqueID()) + " woms.";	
		player.sendMessage(message);
	}

}
