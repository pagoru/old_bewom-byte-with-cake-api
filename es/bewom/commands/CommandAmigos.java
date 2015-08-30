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
		tab.add("añadir");
		tab.add("eliminar");
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
					user.addFriendUUID(pUUID);
					player.sendMessage("Has añadido a " + args[1]);
				} else if(args[0].equals("eliminar")){
					user.deleteFriendUUID(pUUID);
					player.sendMessage("Has eliminado a " + args[1]);
				}
			}
		}
	}	
}
