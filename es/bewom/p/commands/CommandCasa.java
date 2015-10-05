package es.bewom.p.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.p.House;
import es.bewom.p.Houses;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;
import es.bewom.util.Dimensions;

public class CommandCasa extends CommandBase {

	public CommandCasa() {
		super("casa", "home");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		List<String> tab = new ArrayList<String>();
		if(args.length == 1){
			tab.add("vender");
			tab.addAll(BewomUser.getUser(sender.getPlayer()).getFriendsNames());
			
			for (int i = 0; i < tab.size(); i++) {
				if(args[0].length() <= tab.get(i).length()){
					if(args[0].substring(0, args[0].length()).toLowerCase().equals(tab.get(i).substring(0, args[0].length()).toLowerCase())){
						List<String> p = new ArrayList<String>();
						p.add(tab.get(i));
						return p;
					}
				}
			}
			
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
		BewomUser user = BewomUser.getUser(player);
		House house = null;
		for(House h : Houses.houses){
			if(h.getOwner() != null){
				if(h.getOwner().equals(player.getUniqueID().toString())){
					house = h;
					break;
				}
			}
		}
		
		
		if(args.length == 0){
			if(house != null){
				PreciseLocation preciseL = null;
				for(PreciseLocation pl : house.getDoor().getPreciseLocations()){
					if(pl.getDimension() == Dimensions.INTERIORES){
						preciseL = pl;
					}
				}
				if(preciseL != null){
					player.setLocation(preciseL);
					player.sendMessage(TextFormating.RED + "Has sido teletransportado a tu casa!");
				} else {
					player.sendMessage(TextFormating.RED + "No has podido ser teletransportado a tu casa por un error 404!");
				}
			} else {
				player.sendMessage(TextFormating.RED + "No tienes casa...");
			}
		} else if(args.length == 1) {
			if(args[0].equals("vender")){
				if(house != null){
					house.sellHouse(player);
					player.sendMessage(TextFormating.RED + "Has vendido la casa por " + house.getSellPrice() + " woms.");
					Chat.sendMessage(player, null, "/casa vender");
				} else {
					player.sendMessage(TextFormating.RED + "No tienes casa...");
				}
			} else {
				String uuid = BewomUser.getUUIDName(args[0]);
				if(!uuid.equals("")){
					if(user.getFriends().contains(UUID.fromString(uuid))){
						House friendHouse = null;
						for(House h : Houses.houses){
							if(h != null){
								if(h.getOwner() != null){
									if(h.getOwner().equals(uuid)){
										friendHouse = h;
										break;
									}
								}
							}
						}
						if(friendHouse != null){
							for(PreciseLocation pl : friendHouse.getDoor().getPreciseLocations()){
								if(pl.getDimension() == Dimensions.INTERIORES){
									player.setLocation(pl);
								}
							}
							player.sendMessage(TextFormating.RED + "Has sido teletransportado a la casa de tu amigo!");
						} else {
							player.sendMessage(TextFormating.RED + "Este usuario no tiene casa!");
						}
					} else {
						player.sendMessage(TextFormating.RED + "Este usuario no es amigo tuyo!");
					}
				} else {
					player.sendMessage(TextFormating.RED + "Este usuario no existe!");
				}
			}
		}else {
			player.sendMessage(TextFormating.RED + "/casa vender");
		}
	}	
}
