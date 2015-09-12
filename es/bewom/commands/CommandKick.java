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

public class CommandKick extends CommandBase {
	
	public CommandKick() {
		super("kick", "k");
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
		
		Player player = commandSender.getPlayer();
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(BewomByte.game.getServer().getPlayer(args[0]) != null){
			String kickArgs = "";
			String kick = 
					TextFormating.RED + "Has sido advertid@ por " + 
					TextFormating.BOLD + player.getUserName() + 
					TextFormating.RESET + TextFormating.RED + " por" + TextFormating.BOLD  + " incumplir las normas.";
			if(args.length > 1){
				
				for (int i = 1; i < args.length; i++) {
					kickArgs += " " + args[i];
				}
				kick = 
					TextFormating.RED + "Has sido advertid@ por " + 
					TextFormating.BOLD + player.getUserName() + 
					TextFormating.RESET + TextFormating.RED + " por" +
					TextFormating.BOLD + kickArgs + ".";				
			}
			
			BewomByte.m.executeQuery("INSERT INTO `users_kicks`(`uuid`, `uuidAdmin`, `motivo`) VALUES ('" + 
					BewomByte.game.getServer().getPlayer(args[0]).getUniqueID() + "', '" + player.getUniqueID() + "', '" + kickArgs + "')", null);
			BewomByte.game.getServer().getPlayer(args[0]).kick(kick);
			
			Chat.sendMessage(player, 
					TextMessages.BROADCAST + 
					TextFormating.DARK_RED + TextFormating.BOLD + args[0] + 
					TextFormating.RESET + TextFormating.DARK_RED + " ha sido advertid@ por " + 
					TextFormating.BOLD + player.getUserName() + ".", "/kick " + args[0] + " " + kick);
			
		} else {
			
			player.sendMessage(TextMessages.ERROR);
			
		}
		
	}

}
