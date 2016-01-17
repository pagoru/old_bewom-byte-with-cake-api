package es.bewom.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandDispatcher;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;
import es.bewom.util.PokemonList;

public class CommandPSpawn extends CommandBase {
		
	public CommandPSpawn() {
		super("spawnpoke");
	}
	
	@Override
	public boolean canBeUsedBy(CommandSender commandSender){
		if(commandSender.getPlayer() != null){
			if(BewomUser.getUser(commandSender.getPlayer()).getPermissionLevel() < BewomUser.PERM_LEVEL_MOD){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		Player player = sender.getPlayer();
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_MOD) return null;
		if(args.length == 2){
			return PokemonList.getList();
		}
		return null;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_MOD) return;
		
		CommandDispatcher cd = BewomByte.game.getCommandDispacher();
		
		if(args.length == 1){
			
			cd.executeCommand(BewomByte.game.getServer().getCommandSender(), "/pokespawn " + args[0] + "boss1");
			
		} else if(args.length == 1){
			
			cd.executeCommand(BewomByte.game.getServer().getCommandSender(), "/pokespawn " + args[0] + "boss1");
			
		}
		
	}

}
