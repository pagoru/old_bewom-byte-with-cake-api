package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;

import es.bewom.chat.Chat;
import es.bewom.user.BewomUser;

public class CommandReload extends CommandBase {
	
	public CommandReload() {
		super("reload");
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
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		System.gc();
		Chat.sendMessage(player, null, "/reload");
		
	}

}
