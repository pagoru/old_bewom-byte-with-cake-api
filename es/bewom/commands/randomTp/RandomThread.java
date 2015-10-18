package es.bewom.commands.randomTp;

import java.util.Date;
import java.util.Random;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.block.Block;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;
import es.bewom.util.Dimensions;

public class RandomThread implements Runnable {

	public CommandSender commandSender;
	
	@Override
	public void run() {
				
		try {
			Player player = commandSender.getPlayer();
			BewomUser u = BewomUser.getUser(player);
			
			Date d = new Date();
			
			long time = (d.getTime()/1000) - u.timeTpRandom;
			
			if(player.getDimensionID() == Dimensions.RECURSOS){
				
				if(time >= 60 || u.timeTpRandom == 0){
					
					int x = randInt(-2500, 1000);
					int z = randInt(-1000, 2500);
					int y = 100;
					
					player.setLocation(new PreciseLocation(Dimensions.RECURSOS, x, 50000, z, player.getLocation().getYaw(), player.getLocation().getPitch()));
					
					Thread.currentThread().sleep(4000);
					
					for (int y2 = 0; y2 < 128; y2++) {
						Block b = BewomByte.game.getServer().getWorld(Dimensions.RECURSOS).getBlock(new Vector3i(x, y2, z));
						if(b.getUnlocalizedName().contains("air")){
							y = y2 + 1;
							break;
						}
					}
					
					u.timeTpRandom = d.getTime()/1000;
					player.setLocation(new PreciseLocation(Dimensions.RECURSOS, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch()));
					
				} else {
					
					player.sendMessage(TextFormating.RED + "No puedes usar el comando tantas veces seguidas!");
					
				}
				
			} else {
				
				player.sendMessage(TextFormating.RED + "Este comando solo se puede usar en el mundo de recursos!");
				
			}
			
			this.finalize();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	public static int randInt(int min, int max) {
		
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	    
	}

}
