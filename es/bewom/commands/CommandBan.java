package es.bewom.commands;

import java.util.ArrayList;
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

public class CommandBan extends CommandBase {
	
	public CommandBan() {
		super("ban");
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
			tab.add("permanente");
			for (int i = 1; i < 90; i++) {
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
			
			String motivo = " incumplir las normas.";
			if(args.length > 2){
				motivo = "";
				for (int i = 2; i < args.length; i++) {
					motivo += " " + args[i];
				}
			}
			int time = 0;
			int perm = 0;
			if(args[1].equals("permanente")){
				perm = 1;
			} else {
				time = Integer.parseInt(args[1]);
			}
			
			String uuidBanned = BewomUser.getUUIDName(args[0]);
			
			BewomByte.m.executeQuery("UPDATE `users_ban` SET `active`='false' WHERE `uuid`='" + uuidBanned + "'", null);
			BewomByte.m.executeQuery("INSERT INTO `users_ban`(`uuid`, `uuidAdmin`, `motivo`, `perm`, `exp`) "
					+ "VALUES ('" + uuidBanned + "','" + player.getUniqueID().toString() + "','" + motivo + "', '" + perm + "','" + time + "')", null);
			
			Chat.sendMessage(player, 
					TextMessages.BROADCAST + 
					TextFormating.DARK_RED + TextFormating.BOLD + args[0] + 
					TextFormating.RESET + TextFormating.DARK_RED + " ha sido banead@ por " + 
					TextFormating.BOLD + player.getUserName() + ".", "/ban " + args[0] + " " + motivo);
			
			String kick = 
					TextFormating.RED + "Has sido banead@ por " + 
					TextFormating.BOLD + player.getUserName() + 
					TextFormating.RESET + TextFormating.RED + " por" + TextFormating.BOLD  + " incumplir las normas.";
			
			for(Player p : BewomByte.game.getServer().getOnlinePlayers()){
				if(p.getUniqueID().toString().equals(uuidBanned)){
					p.kick(kick);
					return;
				}
			}
			
		} else {
			player.sendMessage(TextFormating.RED + "/ban <player> <tiempo> {motivo}");
		}
		
	}

}
