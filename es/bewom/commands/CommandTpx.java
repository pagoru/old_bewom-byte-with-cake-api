package es.bewom.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.Location;
import org.cakepowered.api.util.PreciseLocation;

import es.bewom.BewomByte;

public class CommandTpx extends CommandBase{

	public CommandTpx(String name, String[] alias) {
		super(name, alias);
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {

		try{
			Player p = commandSender.getPlayer();
			if(p == null)return;
			int i = Integer.parseInt(args[1]);
			Location l = BewomByte.game.getServer().getWorld(i).getSpawnLocation();
			PreciseLocation pre = new PreciseLocation(i, l.getPosition().toVector3d(), 0, 0);
			p.setLocation(pre);
			
		}catch(Exception e){}
	}
}
