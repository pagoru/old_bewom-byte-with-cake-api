package es.bewom.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.user.BewomUser;

public class CommandUnBan extends CommandBase {
	
	public CommandUnBan() {
		super("unban", "pardon", "uban");
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
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(args.length == 1){
			
			if(BewomUser.getUUIDName(args[0]) != null){
				
				String uuidBanned = BewomUser.getUUIDName(args[0]);
				BewomUser.m.executeQuery("UPDATE `users_ban` SET `active`='false' WHERE `uuid`='" + uuidBanned + "'", null);
				player.sendMessage(TextFormating.RED + "Has perdonado a " + args[0] + ".");
				
			} else {
				player.sendMessage(TextFormating.RED + "Este usuario no existe.");
			}
			
		} else {
			player.sendMessage(TextFormating.RED + "/unban <player>");
		}
		
	}

}
