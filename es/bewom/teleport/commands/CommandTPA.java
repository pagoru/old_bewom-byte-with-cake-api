package es.bewom.teleport.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.teleport.TPManager;
import es.bewom.teleport.TPRequest;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandTPA extends CommandBase {

	public CommandTPA() {
		super("tpa");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		Player player = sender.getPlayer();
		List<String> tab = new ArrayList<String>();
		if(args.length == 1){
			tab.add("si");
			tab.add("no");
			if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return null;
			tab.add("here");
			for(Player p : BewomByte.game.getServer().getOnlinePlayers()){
				tab.add(p.getUserName());
			}
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
			if(args[0].equals("here")){
				for(Player p : BewomByte.game.getServer().getOnlinePlayers()){
					tab.add(p.getUserName());
				}
				for (int i = 0; i < tab.size(); i++) {
					if(args[0].length() <= tab.get(i).length()){
						if(args[0].substring(0, args[0].length()).toLowerCase().equals(tab.get(i).substring(0, args[0].length()).toLowerCase())){
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
		Player p;

		if(commandSender.getPlayer() != null) {
			p = commandSender.getPlayer();
		} else {
			commandSender.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return;
		}
		if(args.length == 1){
			if(args[0].equals("si")){
				TPRequest request = TPManager.getRequest(p);
				if(TPManager.validateRequest(request)){
					if(request.getObjective() == 1){
						request.getPlayer1().setLocation(p.getLocation());
						p.sendMessage(TextMessages.TP_SUCCESS);
						request.delete();
						return;
					} else {
						p.setLocation(request.getPlayer1().getLocation());
						p.sendMessage(TextMessages.TP_SUCCESS);
						request.delete();
						return;
					}
				}
			} else if(args[0].equals("no")){
				TPRequest request = TPManager.getRequest(p);
				if(request != null){
					request.delete();
					p.sendMessage(TextMessages.TP_DENIED);
				}
				return;
			} else {
				if(BewomUser.getUser(p).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;
				Player p2 = BewomByte.game.getServer().getPlayer(args[0]);
				if(p2 != null){
					BewomUser u2 = BewomUser.getUser(p2);
					if(u2.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN){
						if(!p.getUserName().equals(p2.getUserName())){
							if(BewomUser.getUser(p).isFriend(BewomUser.getUser(p2))){
								p.setLocation(p2.getLocation());
								p.sendMessage(TextMessages.TP_SUCCESS);
							} else {
								TPManager.newRequest(p, p2, 1);
								commandSender.sendMessage(TextMessages.TP_REQUEST_SENT);
								p2.sendMessage(p.getUserName() + TextFormating.GREEN + " quiere teletransportarse a ti.");
								p2.sendMessage(TextFormating.RED + "    Usa /tpa si - para aceptar la solicitud.");
								p2.sendMessage(TextFormating.RED + "    Usa /tpd no - para denegar la solicitud.");
								
							}
							return;
						}
					}
				}
			}
		} else if(args.length == 2){
			if(args[0].equals("here")){
				if(BewomUser.getUser(p).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;
				Player p2 = BewomByte.game.getServer().getPlayer(args[1]);
				if(p2 != null){
					BewomUser u2 = BewomUser.getUser(p2);
					if(u2.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN){
						if(!p.getUserName().equals(p2.getUserName())){
							TPManager.newRequest(p, p2, 2);
							commandSender.sendMessage(TextMessages.TP_REQUEST_SENT);
							p2.sendMessage(p.getUserName() + TextFormating.GREEN + " quiere que te teletransportes a su posición.");
							p2.sendMessage(TextFormating.RED + "    Usa /tpa si - para aceptar la solicitud.");
							p2.sendMessage(TextFormating.RED + "    Usa /tpd no - para denegar la solicitud.");
							return;
						}
					}
				}
			}
		}
		commandSender.sendMessage(TextFormating.RED + "No se ha podido enviar la solicitud");
	}
}
