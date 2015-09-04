package es.bewom.commands;

import java.util.ArrayList;
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

public class CommandBack extends CommandBase {
	
	public CommandBack() {
		super("back");
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
		BewomUser user = BewomUser.getUser(player);
		PreciseLocation pl = user.getBack();
		user.setBack();
		if(pl != null){
			player.setLocation(pl);
		}
	}
}
