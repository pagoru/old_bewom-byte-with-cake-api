package es.bewom.p.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.p.House;
import es.bewom.p.Houses;
import es.bewom.p.Ranch;
import es.bewom.p.Ranchs;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;
import es.bewom.util.Dimensions;

public class CommandRanch extends CommandBase {

	public CommandRanch() {
		super("ranch", "r", "rancho");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos) {
		if(args.length == 1){
			List<String> a = new ArrayList<String>();
			a.add("eliminar");
			return a;
		}
		return null;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(args.length == 0){
			if(player.getDimensionID() == Dimensions.RECURSOS){
				Ranchs.removeAllRanchs(player);
				Ranchs.ranchs.add(new Ranch(player.getUniqueID().toString()));
				player.sendMessage(TextFormating.RED + "Selecciona el ranch!");
			} else {
				player.sendMessage(TextFormating.RED + "No se puede poner un ranch fuera de recursos!");
			}
		} else if(args.length == 1){
			if(args[0].equals("eliminar")){
				Ranchs.removeAllRanchs(player);
				player.sendMessage(TextFormating.RED + "Se han eliminado todos tus ranchos!");
			}
		}
		
	}

}
