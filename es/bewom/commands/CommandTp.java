package es.bewom.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandTp extends CommandBase {
	
	public CommandTp() {
		super("tp");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		Player player = sender.getPlayer();
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return null;	
		if(args.length == 1){
			return getTab(args, 0);
		} else if(args.length == 2){
			return getTab(args, 1);
		}
		
		return null;
	}
	
	public List<String> getTab(String[] args, int pos){
		List<String> tab = new ArrayList<String>();
		Collection<Player> col = BewomByte.game.getServer().getOnlinePlayers();
		for (Player p : col) {
			tab.add(p.getUserName());
		}
		for (int i = 0; i < tab.size(); i++) {
			if(args[pos].length() <= tab.get(i).length()){
				if(args[pos].substring(0, args[pos].length()).toLowerCase().equals(tab.get(i).substring(0, args[pos].length()).toLowerCase())){
					List<String> p = new ArrayList<String>();
					p.add(tab.get(i));
					return p;
				}
			}
		}
		
		return tab;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(args.length == 1){
			if(BewomByte.game.getServer().getPlayer(args[0]) != null){
				
				Player to = BewomByte.game.getServer().getPlayer(args[0]);
				player.setLocation(to.getLocation());
				player.sendMessage(TextFormating.RED + "Acabas de teletransportarte a " + args[0] + ".");
				Chat.sendMessage(player, null, "/tp " + to.getUserName());
				return;
				
			}
		} else if(args.length == 2){
			if(BewomByte.game.getServer().getPlayer(args[0]) != null && BewomByte.game.getServer().getPlayer(args[1]) != null){
				Player from = BewomByte.game.getServer().getPlayer(args[0]);
				Player to = BewomByte.game.getServer().getPlayer(args[1]);
				from.setLocation(to.getLocation());
				player.sendMessage(TextFormating.RED + "Acabas de teletransportar a " + args[0] + " a " + args[1] + ".");
				Chat.sendMessage(player, null, "/tp " + from.getUserName() + " " + to.getUserName());
				return;
			}
		} else if(args.length == 3){
			
			player.setLocation(new PreciseLocation(player.getDimensionID(), 
					Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), 
							player.getLocation().getYaw(), player.getLocation().getPitch()));
			player.sendMessage(TextFormating.RED + "Acabas de teletransportar a " + args[0] + " " + args[1] + " " + args[2] + ".");
			Chat.sendMessage(player, null, "/tp " + args[0] + " " + args[1] + " " + args[2]);
			
		}
		
		player.sendMessage(TextMessages.ERROR);
		
	}

}
