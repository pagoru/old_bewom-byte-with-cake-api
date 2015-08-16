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
		List<String> tab = new ArrayList<String>();
		if(args.length == 1){
			tab = BewomUser.getPlayersUsernameRegistered();
			
			for (int i = 0; i < tab.size(); i++) {
				if(args[0].length() <= tab.get(i).length()){
					if(args[0].substring(0, args[0].length()).toLowerCase().equals(tab.get(i).substring(0, args[0].length()).toLowerCase())){
						List<String> p = new ArrayList<String>();
						p.add(tab.get(i));
						return p;
					}
				}
			}
			
			return tab;
		}
		
		return null;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		String message = "Tienes " + BewomUser.getMoney(player.getUniqueID()) + " woms.";
		
		if(args[0] != null){
			List<String> players = BewomUser.getPlayersUsernameRegistered();
			for (int i = 0; i < players.size(); i++) {
				if(players.get(i).equals(args[0])){
					if(players.get(i) != null){
						message = players.get(i) + " tiene " + BewomUser.getMoney(players.get(i)) + " woms.";
					}
				}
			}
		}
		
		player.sendMessage(message);

	}

}
