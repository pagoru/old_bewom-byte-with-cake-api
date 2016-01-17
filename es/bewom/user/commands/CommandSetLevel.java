package es.bewom.user.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.scoreboard.Scoreboard;
import org.cakepowered.api.scoreboard.Team;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandSetLevel extends CommandBase {

	public CommandSetLevel() {
		super("perms", "lvl", "perm");
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
		boolean permissions = false;

		if (commandSender.getPlayer() != null) {
			BewomUser user = BewomUser.getUser(commandSender.getPlayer());
			if (user.getPermissionLevel() >= BewomUser.PERM_LEVEL_ADMIN) {
				permissions = true;
			}
		}

		if(permissions == false) {
			commandSender.sendMessage(TextMessages.NO_PERMISSIONS);
			return;
		}

		Player toChange = BewomByte.game.getServer().getPlayer(args[0]);
		int level = 0;
		try{
			level = Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			commandSender.sendMessage("Invalid Level");
			return;
		}

		if (toChange != null) {

			BewomUser user = BewomUser.getUser(toChange);
			user.setPermissionLevel(level);

			Scoreboard score = toChange.getWorld().getScoreboard();
			score.removePlayerFromTeams(toChange);

			BewomUser p = BewomUser.getUser(toChange);
			p.setPermissionLevel(level);
			p.updatePermissions();		

			commandSender.sendMessage(TextFormating.RED + toChange.getUserName() + " ahora es nivel de permisos " + level);
		}
	}

	public int getRequiredPermissionLevel(){
        return 4;
    }
}
