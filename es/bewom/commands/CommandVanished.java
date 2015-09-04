package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Potions;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.user.BewomUser;

public class CommandVanished extends CommandBase {
	
	public CommandVanished() {
		super("v", "vanished", "vanish");
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
		
		if(user.isInvisible()){
			user.setInvisible(false);
			player.clearActivePotions();
			player.sendMessage(TextFormating.RED + "Ya no estas en modo invisible!");
		} else {
			user.setInvisible(true);
			player.addPotionEffect(Potions.Invisibility, 999999, 999999, true, false);
			player.sendMessage(TextFormating.RED + "Estas en modo invisible!");
		}
		
	}

}
