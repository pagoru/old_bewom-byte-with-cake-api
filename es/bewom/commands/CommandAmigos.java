package es.bewom.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

public class CommandAmigos extends CommandBase {

	public CommandAmigos() {
		super("amigos");
	}
	
	@Override
	public List addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		List<String> tab = new ArrayList<String>();
		if(args.length == 1) {
			tab.add("aceptar");
			tab.add("denegar");
			tab.add("eliminar");
			if(BewomUser.getUser(sender.getPlayer().getUniqueID()).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return tab;
			tab.add("añadir");			
		} else if(args.length == 2){
			if(args[0].equals("eliminar")){
				List<UUID> u = BewomUser.getUser(BewomByte.game.getServer().getPlayer(sender.getName())).getFriends();
				for (int i = 0; i < u.size(); i++) {
					tab.add(BewomUser.getUserNameFromUUID(u.get(i)));
				}
			}
			if(args[0].equals("aceptar") || args[0].equals("denegar")){
				List<UUID> u = BewomUser.getUser(BewomByte.game.getServer().getPlayer(sender.getName())).getFriendsPetitions();
				for (int i = 0; i < u.size(); i++) {
					tab.add(BewomUser.getUserNameFromUUID(u.get(i)));
				}
			}
			if(BewomUser.getUser(sender.getPlayer().getUniqueID()).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return tab;
			if(args[0].equals("añadir")){
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
		}
		return tab;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		List<String> uu = BewomUser.getPlayersUUIDRegistered();
		List<String> un = BewomUser.getPlayersUsernameRegistered();
		HashMap<String, String> uuidsPlayers = new HashMap<>();
		for (int i = 0; i < uu.size(); i++) {
			uuidsPlayers.put(un.get(i), uu.get(i));
		}
		
		if(args.length == 2){
			String p = uuidsPlayers.get(args[1]);
			if(p != null){
				UUID pUUID = UUID.fromString(uuidsPlayers.get(args[1]));
				
				if(args[0].equals("aceptar") || args[0].equals("denegar")){
					boolean a = true;
					if(args[0].equals("denegar")){
						a = false;
					}
					switch (user.acceptFriendOnlyIfApplicationWithUUID(pUUID, a)) {
					case 2:
						player.sendMessage(TextFormating.RED + "¡Ya sóis amigos!");
						break;
					case 3:
						player.sendMessage(TextFormating.RED + "¡No tienes ninguna solicitud de amsitad de " + args[1] + "!");
						break;
					case 1:
						player.sendMessage(TextFormating.RED + "¡Acabas de aceptar la solicitud de amistad de " + args[1] + "!");
						break;
					case -1:
						player.sendMessage(TextFormating.RED + "¡Acabas de rechazar la solicitud de amistad de " + args[1] + "!");
						break;
					}
				} else if(args[0].equals("eliminar")){
					user.deleteFriendUUID(pUUID);
					if(BewomUser.getUser(pUUID) != null){
						BewomUser.getUser(pUUID).deleteFriendUUID(user.getUUID());
					}
					player.sendMessage(TextMessages.FRIENDS_NOT_FRIENDS + args[1] + ".");
				}
				
				if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;
				
				if(args[0].equals("añadir")){
					switch (user.addApplicationFriendUUID(pUUID)) {
					case -1:
						player.sendMessage(TextMessages.FRIENDS_WITH_YOU);
						break;
					case 0:
						player.sendMessage(TextMessages.FRIENDS_LIMIT + args[1] + ".");
						break;
					case 1:
						player.sendMessage(TextMessages.FRIENDS_SEND + args[1] + ".");
						Player playerArgs = BewomByte.game.getServer().getPlayer(args[1]);
						if(playerArgs != null){
							playerArgs.sendMessage(TextMessages.FRIENDS_SEND_TO[0] + player.getUserName() + TextMessages.FRIENDS_SEND_TO[1]);
							playerArgs.sendMessage(TextMessages.FRIENDS_SEND_TO_ADD_SUGESTION[0] + player.getUserName() + TextMessages.FRIENDS_SEND_TO_ADD_SUGESTION[1]);
							playerArgs.sendMessage(TextMessages.FRIENDS_SEND_TO_DELETE_SUGESTION[0] + player.getUserName() + TextMessages.FRIENDS_SEND_TO_DELETE_SUGESTION[1]);
						}
						break;
					case 2:
						player.sendMessage(TextMessages.FRIENDS_NOW_FRIENDS + args[1] + ".");
						break;
					case 3:
						player.sendMessage(TextMessages.FRIENDS_JUST_FRIENDS + args[1] + ".");
						break;
					}
				} 
			}
		}
	}	
}
