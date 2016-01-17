package es.bewom.p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.block.BlockState;
import org.cakepowered.api.block.Blocks;
import org.cakepowered.api.event.BlockBreakEvent;
import org.cakepowered.api.event.BlockPlaceEvent;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;
import es.bewom.util.Dimensions;

public class Ranchs {
	
	public static List<Ranch> ranchs = new ArrayList<Ranch>();
	
	public static void on(PlayerInteractEvent e){
		Player p = e.getPlayer();
		BewomUser u = BewomUser.getUser(p);
		
		if(u.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN){
			if(p.getDimensionID() == Dimensions.RECURSOS){
				for(Ranch r : Ranchs.ranchs){
					if(r != null){
						if(r.isSelected(e.getPosition().getX(), e.getPosition().getY(), e.getPosition().getZ(), p.getLocation().getDimension())){
							if(!r.getUuid().equals(p.getUniqueID().toString())){
								e.setEventCanceled(true);
							}
						}
					}
				}
			}
			
			if(e.getInteractBlock().getUnlocalizedName().contains("ranchblock")){
				int creation = -1;
				for(Ranch r : ranchs){
					if(r != null){
						if(r.isCreation()){
							if(r.getUuid().equals(p.getUniqueID().toString())){
								int y = 1;
								if(p.getWorld().getBlock(new Vector3i(e.getPosition().getX(), e.getPosition().getY() - 1, e.getPosition().getZ())).getUnlocalizedName().contains("ranchblock")){
									y = 2;
								}
								creation = 0;
								for (int x = -4; x <= 4; x++) {
									for (int z = -4; z <= 4; z++) {
										if(!p.getWorld().canBlockSeeSky(e.getPosition().getX() + x, e.getPosition().getY() - y + 1, e.getPosition().getZ() + z)){
											creation = 2;
										}
										if(p.getWorld().getBlock(new Vector3i(e.getPosition().getX() + x, e.getPosition().getY() - y, e.getPosition().getZ() + z)) != null){
											if(p.getWorld().getBlock(new Vector3i(e.getPosition().getX() + x, e.getPosition().getY() - y, e.getPosition().getZ() + z)).getUnlocalizedName().contains("air")){
												creation = 1;
											}
										} else {
											creation = 1;
										}
									}
								}
							}
							for(Ranch ra : ranchs){
								if(ra.isSelected(e.getPosition().getX(), e.getPosition().getY(), e.getPosition().getZ(), p.getLocation().getDimension())){
									creation = 3;
								}
							}
						}
					}
				}
				if(creation == 0){
					removeAllRanchs(p);
					
					int y = 1;
					if(p.getWorld().getBlock(new Vector3i(e.getPosition().getX(), e.getPosition().getY() - 1, e.getPosition().getZ())).getUnlocalizedName().contains("ranchblock")){
						y = 2;
					}
					ranchs.add(new Ranch(e.getPosition().getX(), e.getPosition().getY() - y + 1, e.getPosition().getZ(), p.getLocation().getDimension(), p.getUniqueID().toString()));
					for (int x = -4; x <= 4; x++) {
						for (int z = -4; z <= 4; z++) {
							p.getWorld().setBlockState(new Vector3i(e.getPosition().getX() + x, e.getPosition().getY() - y, e.getPosition().getZ() + z), Blocks.DIRT.getBlock().getDefuldBlockState());
							
						}
					}
					save();
					p.sendMessage(TextFormating.RED + "¡Rancho protegido!");
				} else if(creation == 1) {
					p.sendMessage(TextFormating.RED + "¡No se puede proteger este rancho!");
					p.sendMessage(TextFormating.RED + "El rancho debe estar en un lugar plano.");
				} else if(creation == 2) {
					p.sendMessage(TextFormating.RED + "¡No se puede proteger este rancho!");
					p.sendMessage(TextFormating.RED + "El rancho debe estar al aire libre.");
				} else if(creation == 3) {
					p.sendMessage(TextFormating.RED + "¡No se puede proteger este rancho!");
				}
			}
		}
	}

	public static void on(BlockBreakEvent e) {
		Player p = e.getPlayer();
		BewomUser u = BewomUser.getUser(p);
		if(u.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN){
			if(p.getDimensionID() == Dimensions.RECURSOS){
				for(Ranch r : Ranchs.ranchs){
					if(r != null){
						if(r.isSelected(e.getPosition().getX(), e.getPosition().getY(), e.getPosition().getZ(), p.getLocation().getDimension())){
							if(!r.getUuid().equals(p.getUniqueID().toString())){
								e.setEventCanceled(true);
							}
						}
					}
				}
			}
		}
	}
	
	public static void removeAllRanchs(Player p){
		List<Ranch> toDelete = new ArrayList<Ranch>();
		for(Ranch ranch : ranchs){
			if(ranch.getUuid().equals(p.getUniqueID().toString())){
				toDelete.add(ranch);
			}
		}
		for(Ranch ranch : toDelete){
			ranchs.remove(ranch);
		}
		
	}
	
	public static void save() {
		
		try {
		
			Ranch[] ranchsArray = new Ranch[ranchs.size()];
			for(int i = 0; i < ranchs.size(); i++) {
				ranchsArray[i] = ranchs.get(i);
			}
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Ranchs.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(ranchsArray);
			
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
			
			File file = new File("bewom/Ranchs.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			Ranch[] ranchsArray = gson.fromJson(reader, Ranch[].class);
			
			ranchs.clear();
			
			if(ranchsArray != null){
				for(Ranch h : ranchsArray) {
					ranchs.add(h);
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
