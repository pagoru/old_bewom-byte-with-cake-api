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

public class CommandTp extends CommandBase {
	
	public CommandTp() {
		super("tp");
	}
	
	@Override
	public List addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		if(args.length == 1){
			return getTab(args, 0);
		} else if(args.length == 2){
			return getTab(args, 1);
		}
		
		return null;
	}
	
	public List getTab(String[] args, int pos){
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
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(args.length == 1){
			if(BewomByte.game.getServer().getPlayer(args[0]) != null){
				
				Player to = BewomByte.game.getServer().getPlayer(args[0]);
				player.setLocation(to.getLocation());
				Chat.sendMessage(player, null, "/tp " + to.getUserName());
				return;
				
			}
		} else if(args.length == 2){
			if(BewomByte.game.getServer().getPlayer(args[0]) != null && BewomByte.game.getServer().getPlayer(args[1]) != null){
				Player from = BewomByte.game.getServer().getPlayer(args[0]);
				Player to = BewomByte.game.getServer().getPlayer(args[1]);
				from.setLocation(to.getLocation());
				Chat.sendMessage(player, null, "/tp " + from.getUserName() + " " + to.getUserName());
				return;
			}
		}
		
		player.sendMessage(TextMessages.ERROR);
		
	}

}
