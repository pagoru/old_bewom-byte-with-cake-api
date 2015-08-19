package es.bewom.p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.block.Block;
import org.cakepowered.api.block.Blocks;
import org.cakepowered.api.event.BlockBreakEvent;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.util.DirectionYaw;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;
import org.cakepowered.api.world.World;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.economy.House;
import es.bewom.economy.Houses;

public class P {
	
	public static List<Door> doors = new ArrayList<Door>();
	public static Player eliminar;
	public static Door doorToDelete;
	
	public static void on(Game game, PlayerInteractEvent event){
		
		Player p = event.getPlayer();
		double x = event.getPosition().getX();
		double y = event.getPosition().getY();
		double z = event.getPosition().getZ();
		Block b =  event.getInteractBlock();
		World world = p.getWorld();
		
		if(b != null){
			
			if(equalsAnyWoodenDoorTypes(b)){
				if(doorToDelete != null){
					doors.remove(doorToDelete);
					doorToDelete = null;
					p.sendMessage(TextFormating.RED + "Puerta eliminada correctamente!");
					P.save();
				}
				if(doors != null){
					for (Door d : doors) {
						if(!d.isFirstDoor() && !d.isSecondDoor() && eliminar == null){
							if(selectionDoor(p, d, x, y, z, p.getDimensionID())){
								event.setEventCanceled(true);
							}
						}
						
						if(d.getPlayer() != null){
							if(d.getPlayer().equals(p)){
								Block doorW = game.getServer().getWorld(p.getDimensionID()).getBlock(new Vector3i((int) x, (int) y - 1, (int) z));
								if(equalsAnyWoodenDoorTypes(doorW)){
									y -= 1;
								}
								PreciseLocation l = new PreciseLocation(p.getDimensionID(), x, y, z, DirectionYaw.getOpossiteYawFromDirection(p.getDirection()), 0);
								if(d.isSecondDoor()){
									d.setDoorPos(0).setLocation(l);
									d.setSecondDoor(false);
									d.setPlayer(null);
									p.sendMessage("Puertas seleccionadas.");
									P.save();
									event.setEventCanceled(true);
									return;
								}
								if(d.isFirstDoor()){
									d.setDoorPos(1).setLocation(l);
									d.setFirstDoor(false);
									d.setSecondDoor(true);
									p.sendMessage("Selecciona la segunda puerta.");
									event.setEventCanceled(true);
									return;
								}
							}
						}
						
						if(eliminar != null){
							if(eliminar.equals(p)){
								if(d.isSelected(x, y, z, p.getDimensionID())){
									doorToDelete = d;
									eliminar = null;
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static boolean selectionDoor(Player p, Door d, double x, double y, double z, int dimension){
		if(d.setDoorPos(1).isSelected(x, y, z, dimension)){
			if(selectionHouse(p, d)){
				p.setLocation(d.setDoorPos(0).getPreciseLocation());
			}
			return true;
		}
		if(d.setDoorPos(0).isSelected(x, y, z, dimension)){
			if(selectionHouse(p, d)){
				p.setLocation(d.setDoorPos(1).getPreciseLocation());
			}
			return true;
		}
		return false;
	}
	
	private static boolean selectionHouse(Player p, Door d){
		for (int i = 0; i < Houses.houses.size(); i++) {
			House h = Houses.houses.get(i);
			String message = "";
			if(!h.isSelectDoor() && !h.isSelectSign()){
				if(h.getDoor().equals(d)){
					if(h.isOpen() || p.isOP()){
						return true;
					}
					for (int j = 0; j < h.getFriends().size(); j++) {
						String uuid = p.getUniqueID().toString();
						if(uuid.equals(h.getOwner())){
							return true;
						}
						if(uuid.equals(h.getFriends().get(j))){
							return true;
						}
					}
					p.sendMessage(TextFormating.RED + "La puerta esta cerrada!");
					return false;
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	public static void on(Game game, BlockBreakEvent event){
		
		Player p = (Player) event.getPlayer();
		double x = event.getPosition().getX();
		double y = event.getPosition().getY();
		double z = event.getPosition().getZ();
		
		Block b = game.getServer().getWorld(p.getDimensionID()).getBlock(new Vector3i((int) x, (int) y + 1, (int) z));
		
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
	
	public static void save() {
		
		try {
		
			Door[] doorsArray = new Door[doors.size()];
			for(int i = 0; i < doors.size(); i++) {
				doorsArray[i] = doors.get(i);
			}
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/P.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(doorsArray);
			
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			
			writer.close();
		
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	/**
	 * Loads all {@link CentroPokemon} from a Json file.
	 */
	public static void load() {
		
		try {
		
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/P.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			Door[] doorsArray = gson.fromJson(reader, Door[].class);
			
			doors.clear();
			
			if(doorsArray != null){
				for(Door d : doorsArray) {
					doors.add(d);
				}			
			}
			
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	public static void init(BewomByte plugin) {
		load();
	}
	
}
