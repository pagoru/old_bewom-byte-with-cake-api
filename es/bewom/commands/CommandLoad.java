package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.chat.Chat;
import es.bewom.metro.Metro;
import es.bewom.p.Door;
import es.bewom.p.Houses;
import es.bewom.p.P;
import es.bewom.p.Ranch;
import es.bewom.p.Ranchs;
import es.bewom.teleport.Homes;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandLoad extends CommandBase {
	
	public CommandLoad() {
		super("load");
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
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		CentroManager.load();
		Homes.load();
		Ranchs.load();
		P.load();
		Houses.load();
		Metro.loadStations();
		
	}

}
