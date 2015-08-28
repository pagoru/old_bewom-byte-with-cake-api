package es.bewom.teleport.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.teleport.Homes;
import es.bewom.teleport.TPManager;
import es.bewom.teleport.TPRequest;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;
import es.bewom.util.Dimensions;

public class CommandSetHome extends CommandBase {

	public CommandSetHome() {
		super("sethome");
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
			if(p.getDimensionID() != Dimensions.INTERIORES){
				Homes.setHome(p);
				p.sendMessage(TextFormating.RED + "Se ha establecido tu casa correctamente.");
			} else {
				p.sendMessage(TextFormating.RED + "No se puede establecer una casa en este mundo.");
			}
		} else {
			p.sendMessage(TextFormating.RED + "No se puede establecer una casa en este mundo.");
		}
		
	}
}
