package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.user.BewomUser;

public class CommandPlugins extends CommandBase {
	
	public CommandPlugins() {
		super("plugins");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(new BewomUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		player.sendMessage(	"Plugins/Apis en el servidor: { [" 
		+ TextFormating.GREEN + "CakeApi" + TextFormating.WHITE + "], [" 
				+ TextFormating.GREEN + "BewomByte" + TextFormating.WHITE + "] }");
		
	}

}
