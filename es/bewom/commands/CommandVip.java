package es.bewom.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.inventory.Inventory;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.p.House;
import es.bewom.p.Houses;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandVip extends CommandBase {

	public CommandVip() {
		super("vip");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
				
		if(args.length == 0){
			
			if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;
			
			int timeVip = BewomUser.getUser(player).getTimeVip();
			
			if(timeVip == 1){
				player.sendMessage(TextFormating.DARK_AQUA + "Te queda 1 minuto restante como vip.");
			} else {
				player.sendMessage(TextFormating.DARK_AQUA + "Te quedan " + timeVip + " minutos restantes como vip.");
			}	
		} else if(args.length == 1) {
			
			if(args[0].length() == 11){
				
				List<String> time = BewomByte.m.executeQuery("SELECT `VIC_time` FROM `vip_codes` WHERE `VIC_uuid` IS NULL AND `VIC_code`='" + args[0] + "'", "VIC_time");
				
				if(time.get(0) != null){
					if(!time.get(0).isEmpty()){
						int t = Integer.parseInt(time.get(0));
						BewomUser.getUser(player).addTimeVip(t);
						
						BewomByte.m.executeQuery("UPDATE `vip_codes` SET `VIC_uuid`='" + player.getUniqueID() + "' WHERE `VIC_uuid`='null' AND `VIC_code`='" + args[0] + "'", null);
						
						player.sendMessage(TextFormating.DARK_AQUA + "Tu código se ha verificado correctamente, en breve serás vip.");
					}
				}
				
				
			}
			
		}
		
	}	
}
