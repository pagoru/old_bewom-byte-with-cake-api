package es.bewom.centrospokemon.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandCentro extends CommandBase {
	
	public CommandCentro() {
		super("cp", "centro", "centropokemon");
	}
	
	@Override
	public String getCommandUsage(CommandSender commandSender) {
		return "Ir al centro pokemon mas cercano.";
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		List<String> a = new ArrayList<String>();
		for (int i = 0; i < CentroManager.centros.size(); i++) {
			a.add(i + "");
		}
		return a;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		Player player;
		
		if(commandSender.getPlayer() != null) {
			player = commandSender.getPlayer();
		} else {
			commandSender.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return;
		}
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;
		
		if(args.length == 0){
			CentroPokemon cp = CentroManager.getClosest(player.getLocation());
			if(cp != null) {
				player.setLocation(cp.getLocation());
			}
			player.sendMessage(TextMessages.TP_SUCCESS);
		} else if(args.length == 1){
			CentroPokemon cp = CentroManager.centros.get(Integer.parseInt(args[0]));
			if(cp != null) {
				player.setLocation(cp.getLocation());
			}
			player.sendMessage(TextMessages.TP_SUCCESS);
		}
	}

}
