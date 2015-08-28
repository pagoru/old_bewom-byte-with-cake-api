package es.bewom.commands;

import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.command.CommandUtil;
import org.cakepowered.api.command.CommandUtil.ArgumentType;
import org.cakepowered.api.inventory.Inventory;
import org.cakepowered.api.util.Vector3i;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;

public class CommandEnderChest extends CommandBase{

	public CommandEnderChest() {
		super("enderchest");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		return args.length == 1 ? CommandUtil.getStringsMachingLastWord(args, CommandUtil.getOnlinePlayers(sender.getWorld())) : null;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		Player p = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(p);
		
		if(BewomUser.getUser(p).getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) return;

		p.openGui(p.getPlayerEnderChest());
	}
}
