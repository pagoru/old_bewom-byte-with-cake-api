package es.bewom.chat.commands;

import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.command.CommandUtil;
import org.cakepowered.api.command.CommandUtil.ArgumentType;
import org.cakepowered.api.inventory.Inventory;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandMp extends CommandBase{

	public CommandMp() {
		super("tell", "msg", "mp", "w");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		return args.length == 1 ? CommandUtil.getStringsMachingLastWord(args, CommandUtil.getOnlinePlayers(sender.getWorld())) : null;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player p = commandSender.getPlayer();
		BewomUser u = BewomUser.getUser(p);
				
		if(args.length >= 1){
			
			Player p2 = BewomByte.game.getServer().getPlayer(args[0]);
			if(p2 != null){
				if(!p2.equals(p)){
					BewomUser u2 = BewomUser.getUser(p2);
					if(u2 != null){
						u.setMpPlayer(p2);
						p.sendMessage(TextMessages.MP_TALKING_WITH[0] + p2.getUserName() + TextMessages.MP_TALKING_WITH[1]);
						p.sendMessage(TextMessages.MP_TALKING_FINISH_SUGESTION);
					}
				} else {
					p.sendMessage(TextMessages.MP_TALKING_WITH_YOU);
				}
			}
		} else if(args.length == 0){
			if(u.getMpPlayer() != null){
				u.setMpPlayer(null);
				p.sendMessage(TextMessages.MP_TALKING_FINISH);
			}
		}
	}
}
