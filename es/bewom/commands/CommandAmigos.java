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
			tab.add("añadir");
			tab.add("eliminar");			
		} else if(args.length == 2){
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
			} else if(args[0].equals("eliminar")){
				List<UUID> u = BewomUser.getUser(BewomByte.game.getServer().getPlayer(sender.getName())).getFriends();
				for (int i = 0; i < u.size(); i++) {
					tab.add(BewomUser.getUserNameFromUUID(u.get(i)));
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
				if(args[0].equals("añadir")){
					switch (user.addApplicationFriendUUID(pUUID)) {
					case -1:
						player.sendMessage(TextFormating.RED + "No puedes enviarte una solicitud a ti mismo. ¿Eres tonto?");
						break;
					case 0:
						player.sendMessage(TextFormating.RED + "No puedes enviar mas solicitudes de amistad a " + args[1] + ".");
						break;
					case 1:
						player.sendMessage(TextFormating.GREEN + "Has enviado una solicitud a " + args[1] + ".");
						Player playerArgs = BewomByte.game.getServer().getPlayer(args[1]);
						if(playerArgs != null){
							playerArgs.sendMessage(TextFormating.GREEN + player.getUserName() + " quiere ser tu amigo.");
							playerArgs.sendMessage(TextFormating.GREEN + "Usa /amigos añadir " + player.getUserName() + " para aceotar la solicitud.");
							playerArgs.sendMessage(TextFormating.GREEN + "Usa /amigos eliminar " + player.getUserName() + " para eliminar la solicitud.");
						}
						break;
					case 2:
						player.sendMessage(TextFormating.GREEN + "Ahora eres amigo de " + args[1] + ".");
						break;
					case 3:
						player.sendMessage(TextFormating.RED + "Ya eres amigo de " + args[1] + ".");
						break;
					}
				} else if(args[0].equals("eliminar")){
					user.deleteFriendUUID(pUUID);
					player.sendMessage(TextFormating.RED + "Ya no eres amigo de " + args[1] + ".");
				}
			}
		}
	}	
}
