package es.bewom.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandExecutor;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;

public class CommandDinero extends CommandBase {

	public CommandDinero() {
		super("dinero", "d");
		
	}
	
	@Override
	public List addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		return null;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		String message = "Tienes " + BewomUser.getMoney(player.getUniqueID()) + " woms.";		
	}

}
