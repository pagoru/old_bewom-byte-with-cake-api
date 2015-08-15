package es.bewom.p;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.event.BlockBreakEvent;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.util.DirectionYaw;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.world.World;
import org.cakepowered.api.world.block.Block;
import org.cakepowered.api.world.block.Blocks;

public class P {
	
	public static boolean first = false;
	public static boolean second = false;
	public static int lastDoor = 0;
	
	public static List<Door> doors = new ArrayList<Door>();
	
	public static void on(Game game, PlayerInteractEvent event){
		
		Player p = event.getPlayer();
		double x = event.getPosition().getX();
		double y = event.getPosition().getY();
		double z = event.getPosition().getZ();
		Block b =  event.getWorld().getBlock((int)x, (int)y, (int)z);
		World world = p.getWorld();
		
		if(b != null){
			
			if(equalsAnyWoodenDoorTypes(b)){
				
				if(doors != null){
					if(!second && !first){
						for (Door d : doors) {
							if(d.setDoorPos(0).isSelected(x, y, z, world.getDimension())){
								PreciseLocation loc = d.setDoorPos(1).getPreciseLocation();
								p.setLocation(loc);
								event.setEventCanceled(true);
							}
							if(d.setDoorPos(1).isSelected(x, y, z, world.getDimension())){
								PreciseLocation loc = d.setDoorPos(0).getPreciseLocation();
								p.setLocation(loc);
								event.setEventCanceled(true);
							}
						}					
					}
				}
				
				if(second){
					event.setEventCanceled(true);
					Block doorW = game.getServer().getWorld(p.getDimensionID()).getBlock((int) x, (int) y - 1, (int) z);
					if(equalsAnyWoodenDoorTypes(doorW)){
						y -= 1;
					}
					PreciseLocation l = new PreciseLocation(p.getDimensionID(), x, y, z, DirectionYaw.getOpossiteYawFromDirection(p.getDirection()), 0);
					doors.get(lastDoor).setDoorPos(0).setLocation(l);
					second = false;
					p.sendMessage("Puertas seleccionadas.");
				}
				if(first){
					event.setEventCanceled(true);
					Block doorW = game.getServer().getWorld(p.getDimensionID()).getBlock((int) x, (int) y - 1, (int) z);
					if(equalsAnyWoodenDoorTypes(doorW)){
						y -= 1;
					}
					PreciseLocation l = new PreciseLocation(p.getDimensionID(), x, y, z, DirectionYaw.getOpossiteYawFromDirection(p.getDirection()), 0);
					doors.get(lastDoor).setDoorPos(1).setLocation(l);
					first = false;
					second = true;
					p.sendMessage("Selecciona la segunda puerta.");
				}
				
			}
			
		}
		
	}
	
	public static void on(Game game, BlockBreakEvent event){
		
		Player p = (Player) event.getPlayer();
		double x = event.getPosition().getX();
		double y = event.getPosition().getY();
		double z = event.getPosition().getZ();
		
		Block b = game.getServer().getWorld(p.getDimensionID()).getBlock((int) x, (int) y + 1, (int) z);
		
		if(b != null){
			
			if(equalsAnyWoodenDoorTypes(b)){
				
				event.setEventCanceled(true);
				
			}
			
		}
		
	}
	
	public static boolean equalsAnyWoodenDoorTypes(Block b){
		
		if(b.getUnlocalizedName().equals(Blocks.WOODEN_DOOR.getUnlocalizedName())
				|| b.getUnlocalizedName().equals(Blocks.ACACIA_DOOR.getUnlocalizedName())
				|| b.getUnlocalizedName().equals(Blocks.BIRCH_DOOR.getUnlocalizedName())
				|| b.getUnlocalizedName().equals(Blocks.DARK_OAK_DOOR.getUnlocalizedName())
				|| b.getUnlocalizedName().equals(Blocks.JUNGLE_DOOR.getUnlocalizedName())
				|| b.getUnlocalizedName().equals(Blocks.SPRUCE_DOOR.getUnlocalizedName())){
			return true;
		}
		return false;
	}
	
}
