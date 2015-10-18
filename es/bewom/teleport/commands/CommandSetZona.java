package es.bewom.teleport.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.teleport.Homes;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;
import es.bewom.util.Dimensions;

public class CommandSetZona extends CommandBase {

	public CommandSetZona() {
		super("setzona");
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
		if(args.length == 1){
			if(p.getDimensionID() != Dimensions.INTERIORES){
				if(Homes.getHomes(p).size() < 5){
					Homes.setHome(p, args[0]);
					p.sendMessage(TextFormating.RED + "Se ha establecido tu zona correctamente con el nombre " + args[0] + ".");
				} else {
					p.sendMessage(TextFormating.RED + "No puedes asignar mas de 5 zonas, elimina alguna antigua.");
				}
			} else {
				p.sendMessage(TextFormating.RED + "No se puede establecer una zona en este mundo.");
			}
		} else {
			p.sendMessage(TextFormating.RED + "Has de asignar un nombre a tu zona.");
		}
		
	}
}
