package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;
import org.cakepowered.api.util.text.TextFormating;
import org.cakepowered.api.world.World;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.user.BewomUser;

public class CommandTpx extends CommandBase{

	public CommandTpx() {
		super("tpx");
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player p = commandSender.getPlayer();
		
		if(BewomUser.getUser(p).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(p == null)return;
		int i = Integer.parseInt(args[0]);
		World l = BewomByte.game.getServer().getWorld(i);
		if(l == null) return; 
		PreciseLocation pre = new PreciseLocation(l.getDimension(), new Vector3d(0, 128, 0), p.getLocation().getYaw(), p.getLocation().getPitch());
		p.setLocation(pre);
		p.sendMessage(TextFormating.RED + "Te has teletransportado a la dimensi�n " + args[0] + ".");
		return;
		
	}
}
