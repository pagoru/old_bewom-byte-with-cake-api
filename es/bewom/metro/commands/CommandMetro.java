package es.bewom.metro.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;

import es.bewom.chat.Chat;
import es.bewom.metro.Metro;
import es.bewom.metro.Station;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandMetro extends CommandBase {
	
	public CommandMetro() {
		super("metro");
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
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos) {
		List<String> str = new ArrayList<String>();
			if(args.length == 1){
				str.add("nuevo");
				str.add("eliminar");
			} 
			if(args.length >= 1){
				str.add("#");
			}
		return str;		
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(args.length >= 1){
			
			if(args[0].equalsIgnoreCase("nuevo")){
				
				String nameStation1 = "";
				String nameStation2 = "";
				boolean first = true;
				
				for (int i = 1; i < args.length; i++) {
					if(args[i].equalsIgnoreCase("#")){
						System.out.println("true");
						first = false;
					} else {
						if(first){
							nameStation1 += args[i] + " ";
						} else {
							nameStation2 += args[i] + " ";
						}
					}
				}
				
				nameStation1 = nameStation1.substring(0, nameStation1.length() - 1);
				nameStation2 = nameStation2.substring(0, nameStation2.length() - 1);
				
				Metro.stations.add(new Station().setPlayer(player).setPos(0).setNameStation(nameStation1).setPos(1).setNameStation(nameStation2));
				player.sendMessage("Selecciona la primera estación.");
				
			} else if(args[0].equalsIgnoreCase("eliminar")){
				
				Metro.playerToDelete = player.getUniqueID();
				player.sendMessage("Selecciona la estación a eliminar.");
				
			}
			
		}
		
	}

}
