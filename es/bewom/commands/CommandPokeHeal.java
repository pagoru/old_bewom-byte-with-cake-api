package es.bewom.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandPokeHeal extends CommandBase {
	
	public CommandPokeHeal() {
		super("pheal");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;
		
		BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "/pokeheal " + player.getUserName());
		player.sendMessage(TextFormating.RED + "¡Acabas de curar a tus pokemons!");
		
	}

}
