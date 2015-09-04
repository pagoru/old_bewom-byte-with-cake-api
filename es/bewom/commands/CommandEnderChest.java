package es.bewom.commands;

import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.command.CommandUtil;
import org.cakepowered.api.util.Vector3i;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandEnderChest extends CommandBase{

	public CommandEnderChest() {
		super("enderchest");
	}
	
	@Override
	public boolean canBeUsedBy(CommandSender commandSender){
		if(commandSender.getPlayer() != null){
			if(BewomUser.getUser(commandSender.getPlayer()).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		return args.length == 1 ? CommandUtil.getStringsMachingLastWord(args, CommandUtil.getOnlinePlayers(sender.getWorld())) : null;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		Player p = commandSender.getPlayer();
		
		if(BewomUser.getUser(p).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;

		p.openGui(p.getPlayerEnderChest());
		
		Chat.sendMessage(p, null, "/enderchest");	
	}
}
