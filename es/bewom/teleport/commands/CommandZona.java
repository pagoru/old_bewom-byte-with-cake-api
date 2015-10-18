package es.bewom.teleport.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
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
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos) {
		List<String> str = new ArrayList<String>();
		if(args.length == 1){
			str.addAll(Homes.getHomes(sender.getPlayer()));
		} else {
			str.add("eliminar");
		}
		return str;
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
			if(Homes.getHome(p, args[0]) != null){
				p.setLocation(Homes.getHome(p, args[0]).getLocation());
				p.sendMessage(TextFormating.RED + "Has sido teletransportado a tu zona!");
			} else {
				p.sendMessage(TextFormating.RED + "No tienes ninguna zona establecida con este nombre!");
			}
		} else if(args.length == 2){
			if(args[1].equals("eliminar")){
				if(Homes.getHome(p, args[0]) != null){
					Homes.removeHome(p, args[0]);
					p.sendMessage(TextFormating.RED + "Has eliminado tu zona con éxito!");
				} else {
					p.sendMessage(TextFormating.RED + "No tienes ninguna zona establecida con este nombre!");
				}
			}
		}
		
	}
}
