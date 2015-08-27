package es.bewom.commands;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Potions;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandVanished extends CommandBase {
	
	public CommandVanished() {
		super("v", "vanished", "vanish");
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
			player.addPotionEffect(Potions.Invisibility, 99999, 9999, true, false);
			player.sendMessage(TextFormating.RED + "Estas en modo invisible!");
		}
		
	}

}
