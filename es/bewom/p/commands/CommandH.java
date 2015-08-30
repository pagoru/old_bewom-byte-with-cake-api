package es.bewom.p.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.p.House;
import es.bewom.p.Houses;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandH extends CommandBase {

	public CommandH() {
		super("h");
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(user.isAdmin()){
			if(args.length == 1){
				if(args[0].equals("exit")){
					for (House h : Houses.houses) {
						if(h.getPlayer() != null){
							if(h.getPlayer().equals(player)){
								Houses.eliminar = h;
							}
						}
					}
				}
				return;
			} else {
				if(args.length == 2){
					player.sendMessage("Selecciona una puerta valida.");
					Houses.houses.add(new House(player, Integer.parseInt(args[0]), Integer.parseInt(args[1])));
				} else {
					player.sendMessage(TextFormating.RED + "/h <$vender> <$comprar>");
				}
			}
			
		} else {
			
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			
		}
		
	}

}
