package es.bewom.commands;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.chat.Chat;
import es.bewom.user.BewomUser;

public class CommandPerms extends CommandBase {
	
	public CommandPerms() {
		super("perms", "permisos");
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
		} else if(args.length == 2){
			tab.add("admin");
			tab.add("miembro");
			tab.add("vip");
			return tab;			
		} else if(args.length == 3){
			for (int i = 1; i < 101; i++) {
				tab.add(i + "");
			}
			return tab;			
		}
		
		return null;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(args.length >= 2){
			
			int tiempo = 0;
			if(args.length >= 3){
				tiempo = Integer.parseInt(args[2]); 
			}
			String p = BewomUser.getUUIDName(args[0]);
			Date d = new Date();
			Timestamp timestamp = new Timestamp(d.getTime());
			
			if(p != null){
				BewomUser.m.executeQuery("UPDATE `users` SET `type`='" + args[1] + "',`date_type`='" + timestamp.toString() + "',`days_type`='" + tiempo + "' WHERE `uuid`='" + p + "'", null);
			}
			player.sendMessage(TextFormating.RED + "Le has cambiado los permisos de " + args[0] + " a " + args[1] + ".");
			Chat.sendMessage(player, null, "/perms " + args[0] + " " + args[1]);
			
		} else {
			player.sendMessage(TextFormating.RED + "/perms <player> <type> {tiempo}");
		}
		
	}

}
