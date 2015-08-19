package es.bewom.economy;

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
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.text.TextFormating;
import org.cakepowered.api.world.World;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.p.Door;
import es.bewom.p.P;

public class Houses {
	
	public static List<House> houses = new ArrayList<House>();
	
	public static void on(Game game, PlayerInteractEvent event){
		
		Player p = event.getPlayer();
		double x = event.getPosition().getX();
		double y = event.getPosition().getY();
		double z = event.getPosition().getZ();
		Block b =  event.getInteractBlock();
		World world = p.getWorld();
		
		if(b != null){
			
			if(houses != null){
				
				for (House h : houses) {
					
					if(h.getPlayer() != null){
						if(h.getPlayer().equals(p)){
							if(h.isSelectSign()){
//								h.setUuidPropietario(p.getUniqueID().toString()); //quitar
								h.setSignLocation(new PreciseLocation(p.getDimensionID(), x, y, z, 0, 0));
								h.setSelectSign(false);
								h.setPlayer(null);
								p.sendMessage("Casa seleccionada.");
								Houses.save();
							} 
							if(h.isSelectDoor()){
								int a = 0;
								for(Door d : P.doors){
									if(d.isSelected(x, y, z, p.getDimensionID())){
										h.setDoor(d);
										h.setSelectDoor(false);
										h.setSelectSign(true);
										p.sendMessage("Selecciona el cartel.");
										a++;
										break;
									}
								}
								if(a == 0){
									p.sendMessage(TextFormating.RED + "No se ha seleccionado una puerta valida!");
								}
							}
						}
					}
					
				}
			}
		}
	}
	
	public static void save() {
		
		try {
		
			House[] housesArray = new House[houses.size()];
			for(int i = 0; i < houses.size(); i++) {
				housesArray[i] = houses.get(i);
			}
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/H.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(housesArray);
			
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
			
			File file = new File("bewom/H.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			House[] housesArray = gson.fromJson(reader, House[].class);
			
			houses.clear();
			
			if(housesArray != null){
				for(House h : housesArray) {
					houses.add(h);
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
