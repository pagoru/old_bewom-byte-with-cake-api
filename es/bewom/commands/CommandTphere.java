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
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandTphere extends CommandBase {
	
	public CommandTphere() {
		super("tphere", "tph");
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
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		Player player = sender.getPlayer();
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return null;	
		if(args.length == 1){
			return getTab(args, 0);
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
				to.setLocation(player.getLocation());
				player.sendMessage(TextFormating.RED + "Acabas de teletransportar a " + args[0] + " tu posición.");
				return;
				
			}
		}
		
		player.sendMessage(TextMessages.ERROR);
		
	}

}
