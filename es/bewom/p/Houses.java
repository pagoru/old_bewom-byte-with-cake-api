package es.bewom.p;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.block.Block;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.tileentity.TileEntity;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.p.Door;
import es.bewom.p.P;
import es.bewom.user.BewomUser;

public class Houses {
	
	public static List<House> houses = new ArrayList<House>();
	public static House eliminar;
	
	public static List<String> housesAVender = new ArrayList<String>();
	
	private static void comprar(House hou, BewomUser u, Player p){
		
		if(hou != null){
			if(u.houseToBuyConfirm != null){
				if(u.houseToBuyConfirm.equals(hou)){
					if(u.canSubstractMoney(hou.getBuyPrice())){
						u.substractMoney(hou.getBuyPrice());
						u.houseToBuyConfirm = null;
						p.sendMessage(TextFormating.GREEN + "Acabas de comprar esta maravillosa casa!");
						hou.setUuidPropietario(p.getUniqueID().toString());
						hou.setSoldSign(true);
						Houses.save();
					} else {
						p.sendMessage(TextFormating.RED + "No tienes suficiente dinero! :(");
						u.houseToBuyConfirm = null;
					}
					return;
				}
			}
			
			p.sendMessage(TextFormating.GREEN + "La inmobiliaria de " + TextFormating.GRAY + TextFormating.BOLD + "BANKIA");
			p.sendMessage(TextFormating.GREEN + "Esta casa cuesta " + hou.getBuyPrice() + " woms.");
			p.sendMessage(TextFormating.RED + "Si quieres comprar esta casa, haz click de nuevo.");
			
			String desc = TextFormating.GREEN + "";
			if(hou.getBuyPrice() < 80000){
				desc += "Esta propiedad mide menos de 20x20x9.";
			} else if(hou.getBuyPrice() == 80000){
				desc += "Esta propiedad mide 20x20x9.";
			} else {
				desc += "Esta propiedad mide mas de 20x20x9.";
			}
			p.sendMessage(desc);
			u.houseToBuyConfirm = hou;
			
		}
		
	}
		
	
	public static void on(Game game, PlayerInteractEvent event){
		
		Player p = event.getPlayer();
		BewomUser u = BewomUser.getUser(p);
		double x = event.getPosition().getX();
		double y = event.getPosition().getY();
		double z = event.getPosition().getZ();
		Block b =  event.getInteractBlock();
		String n = "";
		if(event.getInteractBlock().getUnlocalizedName().length() > 5){
			n = event.getInteractBlock().getUnlocalizedName().substring(5, event.getInteractBlock().getUnlocalizedName().length());
		}
		
		int k = 0;
		for(House g : houses){
			if(g.getPlayer() != null){
				if(g.getPlayer().equals(p)){
					k++;
				}
			}
		}
		
		if(k == 0){
			if(n.equals("poste")){
				int a = 0;
				for(House h : houses){
					if(h.isSignSelected(event.getPosition().getX(), event.getPosition().getY(), event.getPosition().getZ(), p.getLocation().getDimension())){
						a++;
						break;
					}
				}
				if(a != 0 && !p.isSneaking()){
					event.setEventCanceled(true);
				}
				//compra y venta
				House hou = null;
				House houOwner = null;
				int o = 0;
				for(House h : houses){
					if(h.getOwner() != null){
						if(h.getOwner().equals(p.getUniqueID().toString())){
							o++;
						}
					}
					if(h.isSignSelected(event.getPosition().getX(), event.getPosition().getY(), event.getPosition().getZ(), p.getLocation().getDimension())){
						if(h.getOwner() == null){
							hou = h;
						} else {
							houOwner = h;
						}
					}
				}
				if(houOwner != null){
					boolean ab = true;
					if(housesAVender.contains(p.getUniqueID().toString())){
						if(p.getUniqueID().toString().equals(houOwner.getOwner())){
							houOwner.sellHouse(p);
							housesAVender.remove(p.getUniqueID().toString());
							p.sendMessage(TextFormating.RED + "Has vendido la casa por " + houOwner.getSellPrice() + " woms.");
							ab = false;
						}
					}
					if(ab){
						Calendar c1 = BewomUser.getLastLogin(BewomUser.getUserNameFromUUID(UUID.fromString(houOwner.getOwner())));
						Calendar c2 = Calendar.getInstance();
						
						long days = (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000*60*60*24);
						
						if(days < 45){
							p.sendMessage(TextFormating.GREEN + "La inmobiliaria de " + TextFormating.GRAY + TextFormating.BOLD + "BANKIA");
							p.sendMessage(TextFormating.GREEN + "Esta propiedad es de " + BewomUser.getUserNameFromUUID(UUID.fromString(houOwner.getOwner())) + ".");
						} else {
							p.sendMessage(TextFormating.GREEN + "Esta propiedad era de " + BewomUser.getUserNameFromUUID(UUID.fromString(houOwner.getOwner())) + " y ahora esta a la venta.");
							houOwner.sellHouse();
							houOwner = null;
						}
					}
				}
				if(houOwner == null){
					if(o == 0 || (o < 2 && u.getPermissionLevel() >= BewomUser.PERM_LEVEL_VIP)){
						comprar(hou, u, p);
					} else {
						if(u.getPermissionLevel() >= BewomUser.PERM_LEVEL_VIP){
							p.sendMessage(TextFormating.RED + "No puedes tener mas de dos casas!");
						} else {
							p.sendMessage(TextFormating.RED + "No puedes tener mas de una casa!");
						}
					}
				}
			}
		}
		
		if(eliminar != null){
			houses.remove(eliminar);
		}
		
		if(b != null){
			
			if(houses != null){
				
				for (House h : houses) {
					
					if(h.getPlayer() != null){
						if(h.getPlayer().equals(p)){
							if(h.isSelectSign()){
								int a = 0;
								for(House g : houses){
									if(g.getPlayer() == null){
										if(g.isSignSelected(x, y, z, p.getDimensionID())){
											a++;
										}
									}
								}
								if(a == 0){
									if(n.equals("poste")){
										h.setSignLocation(new PreciseLocation(p.getDimensionID(), x, y, z, 0, 0));
										h.setSelectSign(false);
										h.setPlayer(null);
										h.setSoldSign(false);
										p.sendMessage("Casa seleccionada.");
										Houses.save();
									} else {
										p.sendMessage(TextFormating.RED + "No se ha seleccionado un cartel valido!");
									}
								} else {
									p.sendMessage(TextFormating.RED + "Este cartel ya esta asignado a otra casa!");
								}
							} 
							if(h.isSelectDoor()){
								int a = 0;
								for(House g : houses){
									if(g.getPlayer() == null){
										if(g.getDoor().setDoorPos(0).isSelected(x, y, z, p.getDimensionID())
												|| g.getDoor().setDoorPos(1).isSelected(x, y, z, p.getDimensionID())){
											a = 4;
										}
									}
								}
								if(a == 0){
									for(Door d : P.doors){
										if(d.setDoorPos(0).isSelected(x, y, z, p.getDimensionID())
												|| d.setDoorPos(1).isSelected(x, y, z, p.getDimensionID())){
											h.setDoor(d);
											h.setSelectDoor(false);
											h.setSelectSign(true);
											p.sendMessage("Selecciona el cartel.");
											a++;
										}
									}
								}
								if(a == 0){
									p.sendMessage(TextFormating.RED + "No se ha seleccionado una puerta valida!");
								} else if(a == 4){
									p.sendMessage(TextFormating.RED + "Esta puerta ya esta asignada a otra casa!");
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
