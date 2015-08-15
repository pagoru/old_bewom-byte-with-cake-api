package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.p.Door;
import es.bewom.p.P;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandBan extends CommandBase {
	
	public CommandBan() {
		super("pban");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		BewomByte.game.getCommandDispacher().executeCommand(commandSender, "/ban LadyMorbidus");
		
	}

}
