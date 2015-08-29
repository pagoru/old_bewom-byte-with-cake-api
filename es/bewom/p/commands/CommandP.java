package es.bewom.p.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;

import es.bewom.p.Door;
import es.bewom.p.P;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandP extends CommandBase {
	
	public CommandP() {
		super("p");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		List<String> tab = new ArrayList<String>();
		tab.add("eliminar");
		tab.add("exit");
		return tab;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(user.isAdmin()){
			
			if(args.length == 1){
				
				if(args[0].equals("eliminar")){
					player.sendMessage("Selecciona una de las dos puertas a borrar.");
					P.eliminar.add(player);
				} else if(args[0].equals("exit")){
					for (Door d : P.doors) {
						if(d.getPlayer() != null){
							P.eliminar.remove(player);
							P.doors.remove(new Door(player));
							if(d.getPlayer().equals(player)){
								P.doorToDelete = d;
							}
						}
					}
				}
				return;
			} else {
				player.sendMessage("Selecciona la primera puerta.");
				P.doors.add(new Door(player));
			}
			
		} else {
			
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			
		}
		
	}

}
