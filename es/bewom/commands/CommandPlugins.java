package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.chat.Chat;
import es.bewom.user.BewomUser;

public class CommandPlugins extends CommandBase {
	
	public CommandPlugins() {
		super("plugins");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
				
		player.sendMessage(	"Plugins/Apis en el servidor: { [" 
		+ TextFormating.GREEN + "CakeApi" + TextFormating.WHITE + "], [" 
				+ TextFormating.GREEN + "BewomByte" + TextFormating.WHITE + "] }");
		
		Chat.sendMessage(player, null, "/plugins");
		
	}

}
