package es.bewom.teleport.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.teleport.Homes;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandZona extends CommandBase {

	public CommandZona() {
		super("zona");
	}
	
	@Override
	public boolean canBeUsedBy(CommandSender commandSender){
		if(commandSender.getPlayer() != null){
			if(BewomUser.getUser(commandSender.getPlayer()).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		Player p;

		if(commandSender.getPlayer() != null) {
			p = commandSender.getPlayer();
		} else {
			commandSender.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return;
		}
		if(BewomUser.getUser(p).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;
		if(args.length == 0){
			if(Homes.getHome(p) != null){
				p.setLocation(Homes.getHome(p).getLocation());
				p.sendMessage(TextFormating.RED + "Has sido teletransportado a tu ona!");
			} else {
				p.sendMessage(TextFormating.RED + "No tienes ninguna zona establecida!");
			}
		}
		
	}
}
