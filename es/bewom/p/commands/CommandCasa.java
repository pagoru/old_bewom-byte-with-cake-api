package es.bewom.p.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.p.House;
import es.bewom.p.Houses;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandCasa extends CommandBase {

	public CommandCasa() {
		super("casa");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		List<String> tab = new ArrayList<String>();
		if(args.length == 1){
			tab.add("vender");
		} else if(args.length == 2){
			tab = BewomUser.getPlayersUsernameRegistered();
			
			for (int i = 0; i < tab.size(); i++) {
				if(args[1].length() <= tab.get(i).length()){
					if(args[1].substring(0, args[1].length()).toLowerCase().equals(tab.get(i).substring(0, args[1].length()).toLowerCase())){
						List<String> p = new ArrayList<String>();
						p.add(tab.get(i));
						return p;
					}
				}
			}
		}
		
		return tab;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		
		House house = null;
		for(House h : Houses.houses){
			if(h.getOwner() != null){
				if(h.getOwner().equals(player.getUniqueID().toString())){
					house = h;
					break;
				}
			}
		}
		
		if(house != null){
			if(args.length == 1) {
				if(args[0].equals("vender")){
					house.sellHouse(player);
					player.sendMessage(TextFormating.RED + "Has vendido la casa por " + house.getSellPrice() + " woms.");
					Chat.sendMessage(player, null, "/casa vender");
				}
			}else {
				player.sendMessage(TextFormating.RED + "/casa <añadir|eliminar> <usuario>");
				player.sendMessage(TextFormating.RED + "/casa <vender>");
			}
		} else {
			player.sendMessage(TextFormating.RED + "No tienes casa...");
		}
	}	
}
