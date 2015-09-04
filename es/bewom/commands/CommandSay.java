package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;

import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandSay extends CommandBase {
	
	public CommandSay() {
		super("say", "s");
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
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(user.isAdmin()){
			if(args.length > 0){
				
				String text = "";
				
				for (int i = 0; i < args.length; i++) {
					if(i == 0){
						text += args[0]; 
					} else {
						text += " " + args[i];
					}
					
				}
				
				Chat.sendMessage(player, TextMessages.BROADCAST + text + ".", "/say " + text);	
				
			} else {
				
				player.sendMessage(TextMessages.ERROR);
				
			}
			
		} else {
			
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			
		}
		
	}

}
