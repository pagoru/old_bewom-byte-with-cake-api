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

public class CommandKick extends CommandBase {
	
	public CommandKick() {
		super("kick", "k");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(BewomByte.game.getServer().getPlayer(args[0]) != null){
			String kickArgs = "";
			String kick = 
					TextFormating.RED + "Has sido advertid@ por " + 
					TextFormating.BOLD + player.getUserName() + 
					TextFormating.RESET + TextFormating.RED + " por" + TextFormating.BOLD  + " incumplir las normas.";
			if(args.length > 1){
				
				for (int i = 1; i < args.length; i++) {
					kickArgs += " " + args[i];
				}
				kick = 
					TextFormating.RED + "Has sido advertid@ por " + 
					TextFormating.BOLD + player.getUserName() + 
					TextFormating.RESET + TextFormating.RED + " por" +
					TextFormating.BOLD + kickArgs + ".";				
			}
			
			BewomByte.game.getServer().getPlayer(args[0]).kick(kick);
			user.m.executeQuery("INSERT INTO `users_kicks`(`uuid`, `uuidAdmin`, `motivo`) VALUES ('" + 
					BewomByte.game.getServer().getPlayer(args[0]).getUniqueID() + "', '" + player.getUniqueID() + "', '" + kickArgs + "')", null);
			
			Chat.sendMessage(player, 
					TextMessages.BROADCAST + 
					TextFormating.DARK_RED + TextFormating.BOLD + args[0] + 
					TextFormating.RESET + TextFormating.DARK_RED + " ha sido advertid@ por " + 
					TextFormating.BOLD + player.getUserName() + ".", "/kick " + args[0] + " " + kick);	
			
		} else {
			
			player.sendMessage(TextMessages.ERROR);
			
		}
		
	}

}
