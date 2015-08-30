package es.bewom.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
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
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		Player player = sender.getPlayer();
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return null;
		List<String> tab = new ArrayList<String>();
		if(args.length == 2){
			tab.add("0");
			tab.add("1");
			tab.add("2");
			tab.add("3");
			return tab;
		} else if(args.length == 2){
			Collection<Player> col = BewomByte.game.getServer().getOnlinePlayers();
			for (Player p : col) {
				tab.add(p.getUserName());
			}
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
		Player player;

		if(commandSender.getPlayer() != null) {
			player = commandSender.getPlayer();
		} else {
			commandSender.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return;
		}
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		int gm = 0;
		
		if(args.length >= 1){
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
			
			if(args.length == 2){
				if(args[1] != null){
					Player p = BewomByte.game.getServer().getPlayer(args[1]);
					
					if(p != null){
						p.setGameMode(gm);
						p.sendMessage(TextFormating.RED + "Gamemode actualizado.");
						player.sendMessage(TextFormating.RED + "Gamemode de " + p.getUserName() + " actualizado.");
						
						Chat.sendMessage(player, null, "/gm " + p.getUserName() + " " + gm);	
						return;
					}
				}
			} else if(args.length == 1){
				if (args[0] != null){
					
					player.setGameMode(gm);
					player.sendMessage(TextFormating.RED + "Gamemode actualizado.");
					
					Chat.sendMessage(player, null, "/gm " + gm);	
					return;
					
				}
			}
		}
		
	}

}
