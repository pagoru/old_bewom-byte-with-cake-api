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
import es.bewom.chat.Chat;
import es.bewom.user.BewomUser;

public class CommandPlayerInventory extends CommandBase{

	public CommandPlayerInventory() {
		super("inventory", "in", "invsee");
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
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		return args.length == 1 ? CommandUtil.getStringsMachingLastWord(args, CommandUtil.getOnlinePlayers(sender.getWorld())) : null;
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		Player player = commandSender.getPlayer();
		if(player == null) {
			commandSender.sendMessage("Este comando solo lo pueden usar jugadores");
			return;
		}
		BewomUser user = BewomUser.getUser(player);
		if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN){
			commandSender.sendMessage("Insuficientes permisos para usar este comando");
			return;
		}
		if(CommandUtil.checkArguments(args, new ArgumentType[]{ArgumentType.PLAYER}, commandSender.getWorld())){
			Player p = BewomByte.game.getServer().getPlayer(args[0]);
			Inventory inv = p.getPlayerInventory();
			player.openGui(inv);
			Chat.sendMessage(p, null, "/invsee" + args[0]);	
		}else{
			commandSender.sendMessage("Argumentos invalidos: Uso /inventory <player>");
		}
	}
}
