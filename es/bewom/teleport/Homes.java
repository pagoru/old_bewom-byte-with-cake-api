package es.bewom.teleport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.p.House;

public class Homes {
	
	public static List<Home> homes = new ArrayList<Home>();
	
	public static void init(){
		load();
	}
	
	public static List<String> getHomes(Player p){
		List<String> hom = new ArrayList<String>();
		if(homes != null){
			for(Home h : homes){
				if(h.getUuid().equals(p.getUniqueID())){
					hom.add(h.getName());
				}
			}
		}
		return hom;
	}
	
	public static Home getHome(Player p, String name){
		
		if(homes != null){
			for(Home h : homes){
				if(h.getUuid().equals(p.getUniqueID())){
					if(h.getName().equalsIgnoreCase(name)){
						return h;
					}
				}
			}
		}
		
		return null;
		
	}
	
	public static void removeHome(Player p, String name){
		Home toDelete = null;
		if(homes != null){
			for(Home h : homes){
				if(h.getUuid().equals(p.getUniqueID())){
					if(h.getName().equalsIgnoreCase(name)){
						toDelete = h;
					}
				}
			}
		}
		if(toDelete != null){
			homes.remove(toDelete);
		}
		save();
		
	}
	
	public static void setHome(Player p, String name){
		
		Home ho = null;
		Home toRemove = null;
		
		if(homes != null){
			for(Home h : homes){
				if(h.getUuid().equals(p.getUniqueID())){
					if(h.getName().equalsIgnoreCase(name)){
						ho = new Home(p, name);
						toRemove = h;
					}
				}
			}
			if(toRemove != null){
				homes.add(ho);
				homes.remove(toRemove);
			}
		}
		
		if(ho == null){
			homes.add(new Home(p, name));
		}
		save();
		
	}
	
	public static void save() {
		
		try {
		
			Home[] homesArray = new Home[homes.size()];
			for(int i = 0; i < homes.size(); i++) {
				homesArray[i] = homes.get(i);
			}
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Homes.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(homesArray);
			
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			
			writer.close();
		
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	public static void load() {
		
		try {
		
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Homes.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			Home[] homesArray = gson.fromJson(reader, Home[].class);
			
			homes.clear();
			
			if(homesArray != null){
				for(Home h : homesArray) {
					homes.add(h);
				}			
			}
			
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
}
