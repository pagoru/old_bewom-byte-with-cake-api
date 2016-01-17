package es.bewom.metro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.block.Block;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.inventory.ItemStack;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.util.DirectionYaw;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.text.TextFormating;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.p.House;
import es.bewom.user.BewomUser;
import net.minecraft.nbt.NBTTagCompound;
import scala.Array;

public class Metro {
	
	private static String ticket 			= "ticket";
	private static String ticketMachine 	= "";
	private static String station 			= "barrera";
	
	public static List<Station> stations = new ArrayList<Station>();
	public static UUID playerToDelete;
	public static Station stationToDelete;
	
	public static List<Ticket> tickets = new ArrayList<Ticket>();
	
	public static void on(PlayerInteractEvent event) {
		
		Player 		p 	= event.getPlayer();
		BewomUser	u	= BewomUser.getUser(p);
		Block 		b	= event.getInteractBlock();
		ItemStack 	i	= event.getPlayer().getCurrentItem();
				
		if(b.getUnlocalizedName().contains(station)){
			
			if(playerToDelete != p.getUniqueID()){
				
				for(Station st : stations){
					
					if(st.getPlayer() != null){
						
						if(st.getPlayer().getUniqueID() == p.getUniqueID()){
							
							if(!st.isFirst() && !st.isSecond()){
								
								st.setPos(0).setSpawnLocation(new PreciseLocation(p.getDimensionID(), p.getPosition(), DirectionYaw.getOpossiteYawFromDirection(p.getDirection()), 0));
								st.setPos(0).setBlockLocation(new PreciseLocation(p.getDimensionID(), event.getPosition().toVector3d(), 0, 0));
								st.setFirst(true);
								p.sendMessage("Selecciona la segunda estación.");
								
							} else if(st.isFirst() && !st.isSecond()){
								
								st.setPos(1).setSpawnLocation(new PreciseLocation(p.getDimensionID(), p.getPosition(), DirectionYaw.getOpossiteYawFromDirection(p.getDirection()), 0));
								st.setPos(1).setBlockLocation(new PreciseLocation(p.getDimensionID(), event.getPosition().toVector3d(), 0, 0));
								st.setPlayer(null);
								p.sendMessage("Estaciones seleccionadas.");
								
								saveStations();
								
							}
							
						}
						
					} else {
						
						ItemStack it = p.getCurrentItem();
						
						if(it != null){
							
							if(it.getUnlocalizedName().contains(ticket)){
								
								NBTCompund nbt = it.getNBTCompound();
								
								UUID uuid 		= new UUID(nbt.getCompound("uuid").getLong("msb"), nbt.getCompound("uuid").getLong("lsb"));
								int duration	= nbt.getInteger("duracion");
								int travels		= nbt.getInteger("viajes");
								
								int day			= nbt.getCompound("time").getInteger("day");
								int month		= nbt.getCompound("time").getInteger("month") - 1; //TODO cout capullo X2 ¬¬'
								int year		= nbt.getCompound("time").getInteger("year");
								
								int hour		= nbt.getCompound("time").getInteger("hour");
								int minute		= nbt.getCompound("time").getInteger("minute");
								int second		= nbt.getCompound("time").getInteger("second");
																
								Calendar cal = Calendar.getInstance();
								cal.set(year, month, day, hour, minute, second);
								
								Ticket t = new Ticket(uuid, duration, cal, travels);
								
								if(t.isValid()){
																		
									if(st.setPos(0).isBlockSelected(event.getPosition().getX(), event.getPosition().getY(), event.getPosition().getZ(), p.getDimensionID())){
										
										t.removeOneTravel();
										nbt.setInteger("viajes", t.getTravels());
										
										p.setLocation(st.setPos(1).getSpawnLocation());
										p.sendMessage(TextFormating.DARK_AQUA + "Bienvenido a la estación " + st.setPos(1).getNameStation() + "!");
										
									} else if(st.setPos(1).isBlockSelected(event.getPosition().getX(), event.getPosition().getY(), event.getPosition().getZ(), p.getDimensionID())){
										
										t.removeOneTravel();
										nbt.setInteger("viajes", t.getTravels());
										
										p.setLocation(st.setPos(0).getSpawnLocation());
										p.sendMessage(TextFormating.DARK_AQUA + "Bienvenido a la estación " + st.setPos(0).getNameStation() + "!");
										
									}
									
								} else {
									
									p.sendMessage(TextFormating.RED + "Tu billete no es valido.");
									
								}
								
								updateTicket(t);
								
							}
							
						}
						
					}
					
				}
				
			} else {
				
				for(Station st : stations){
					
					if(st.isAnyBlockSelected(event.getPosition().getX(), event.getPosition().getY(), event.getPosition().getZ(), p.getDimensionID())){
						
						stationToDelete = st;
						p.sendMessage("Estación eliminada.");
						
					}
					
				}
				
				stations.remove(stationToDelete);
				playerToDelete = null;
				stationToDelete = null;
				
			}
			
//			if(i.getUnlocalizedName().contains(ticket)){
//				
//				p.getCurrentItem().setStackSize(-1);
//				p.getPlayerInventory().setStackInSlot(p.getSelectedSlot(), null);
//				
//			} else {
//				
//				
//				
//			}
			
		}
		
	}
	
	private static void updateTicket(Ticket tick){
		
		Ticket ticket = null;
		
		for(Ticket t : tickets){
			
			if(t.isSame(tick)){
				ticket = t;
			}
			
		}
		
		if(ticket != null){
			
			tickets.remove(ticket);
			tickets.add(tick);
			
		}
		
		saveTickets();
		
	}
	
	public static void saveTickets() {
		
		try {
		
			Ticket[] ticketsArray = new Ticket[tickets.size()];
			for(int i = 0; i < tickets.size(); i++) {
				ticketsArray[i] = tickets.get(i);
			}
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Tickets.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(ticketsArray);
			
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			
			writer.close();
		
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	public static void loadTickets() {
		
		try {
		
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Tickets.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			Ticket[] ticketsArray = gson.fromJson(reader, Ticket[].class);
			
			tickets.clear();
			
			if(ticketsArray != null){
				for(Ticket h : ticketsArray) {
					tickets.add(h);
				}			
			}
			
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	
	public static void saveStations() {
		
		try {
		
			Station[] stationsArray = new Station[stations.size()];
			for(int i = 0; i < stations.size(); i++) {
				stationsArray[i] = stations.get(i);
			}
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Stations.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(stationsArray);
			
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			
			writer.close();
		
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	public static void loadStations() {
		
		try {
		
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Stations.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			Station[] stationsArray = gson.fromJson(reader, Station[].class);
			
			stations.clear();
			
			if(stationsArray != null){
				for(Station h : stationsArray) {
					stations.add(h);
				}			
			}
			
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	
	public static void init(BewomByte plugin) {
		loadTickets();
		loadStations();
	}

}
