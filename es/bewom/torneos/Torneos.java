package es.bewom.torneos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.cakepowered.api.util.Color;
import org.cakepowered.api.util.DyeColor;
import org.cakepowered.api.util.FireworkProperties;
import org.cakepowered.api.util.FireworkProperties.FireworkExplosion;
import org.cakepowered.api.util.FireworkProperties.FireworkType;
import org.cakepowered.api.util.Vector3d;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.p.House;
import es.bewom.util.Dimensions;

public class Torneos {
	
	public static Torneo current;
	
	public static void loadLastTorneo(){
		current = new Torneo();
	}
	
	public static void loadNewTorneo(String name, String date){
		current = new Torneo(name, date);
	}
	
	public static void save() {
		
		try {
			current.save();
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Torneo.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(current);
			
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
			
			File file = new File("bewom/Torneo.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			current = gson.fromJson(reader, Torneo.class);
			
			if(current == null){
				current = new Torneo();
			}
			
			current.load();
			
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	public static void init() {
		load();
		save();
	}
	
	public static boolean fuegos = false;
	public static int f = 0;
	
	private static int x = -10;
	public static void fuegos(Date date){
		if(fuegos){
			if(f == 5){
				FireworkExplosion explosion = new FireworkExplosion(
						FireworkType.LARGE, new Color[]{DyeColor.RED.getColor(), DyeColor.WHITE.getColor()}, true, true,
						new Color[]{DyeColor.RED.getColor(), DyeColor.WHITE.getColor()});
				FireworkProperties properties = new FireworkProperties((byte) 0, explosion);
				BewomByte.game.getServer().getWorld(Dimensions.EXTERIORES).spawnFirework(
						new Vector3d(current.getLocation()[2].getPosition().getX() + x, 
								current.getLocation()[2].getPosition().getY(), 
								current.getLocation()[2].getPosition().getZ()), properties);
				if(x == 10){
					fuegos = false;
					x = -10;
				}
				x++;
				f = 0;
			}
			f++;
		}
	}
}
