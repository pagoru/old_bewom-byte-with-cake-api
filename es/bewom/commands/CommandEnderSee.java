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

public class CommandEnderSee extends CommandBase{

	public CommandEnderSee() {
		super("endersee");
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
			Inventory inv = p.getPlayerEnderChest();
			player.openGui(inv);
		}else{
			commandSender.sendMessage("Argumentos invalidos: Uso /enderchest <player>");
		}
	}
}
