package es.bewom.torneos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.util.PreciseLocation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;
import es.bewom.util.Location;

public class Torneo {
	
	private int index;
	private String name;
	private String date;
	private int maxPlayers;
	public static Location[] pl = new Location[4];
	private String[] players = new String[16];
	private int[] round1 = new int[8];
	private String[] winnersRound1 = new String[8];
	private int[] round2 = new int[4];
	private String[] winnersRound2 = new String[4];
	private int[] round3 = new int[2];
	private String[] winnersRound3 = new String[2];
	private int round4;
	private String winner;
	
	public static Torneo current;
	
	public List<Player> getBattle(int battle){
		List<Player> players = new ArrayList<Player>();
		if(battle >= 1 && battle <= 8){
			updateWinners();
			
			String play1 = this.players[(battle*2)-2];
			Player p1 = BewomByte.game.getServer().getPlayer(play1);
			players.add(p1);
			
			String play2 = this.players[(battle*2)-1];
			Player p2 = BewomByte.game.getServer().getPlayer(play2);
			players.add(p2);
			
			return players;
		} else if(battle >= 9 && battle <= 12){
			int b = battle - 8;
			updateWinners();
			return getPlayersBattle(winnersRound1[(b*2)-1], winnersRound1[(b*2)-2]);
		} else if(battle >= 13 && battle <= 14){
			int b = battle - 12;
			updateWinners();
			return getPlayersBattle(winnersRound2[(b*2)-1], winnersRound2[(b*2)-2]);
		} else if(battle == 15){
			updateWinners();
			return getPlayersBattle(winnersRound3[0], winnersRound3[1]);
		}
		
		return null;
	}
	
	private List<Player> getPlayersBattle(String wR1, String wR2){
		List<Player> ps = new ArrayList<Player>();
		if(wR1 != null){
			Player p = BewomByte.game.getServer().getPlayer(wR1);
			ps.add(p);
		} else {
			ps.add(null);
		}
		if(wR2 != null){
			Player p = BewomByte.game.getServer().getPlayer(wR2);
			ps.add(p);
		} else {
			ps.add(null);
		}
		return ps;
	}
	
	private void updateWinners(){
		int w = 0;
		for (int i = 1; i < 9; i++) {
			if(round1[i-1] == 1){
				winnersRound1[w] = players[(i*2)-1];
				w++;
			} else if(round1[i-1] == 0){
				winnersRound1[w] = players[(i*2)-2];
				w++;
			}
		}
		w = 0;
		for (int i = 1; i < 5; i++) {
			if(round2[i-1] == 1){
				winnersRound2[w] = winnersRound1[(i*2)-1];
				w++;
			} else if(round2[i-1] == 0){
				winnersRound2[w] = winnersRound1[(i*2)-2];
				w++;
			}
		}
		w = 0;
		for (int i = 1; i < 3; i++) {
			if(round3[i-1] == 1){
				winnersRound3[w] = winnersRound2[(i*2)-1];
				w++;
			} else if(round3[i-1] == 0){
				winnersRound3[w] = winnersRound2[(i*2)-2];
				w++;
			}
		}
		if(round4 == 1){
			winner = winnersRound3[1];
		} else if(round4 == 0){
			winner = winnersRound3[0];
		}
	}
	
	private void init(){
		maxPlayers = 16;
		for (int i = 0; i < 8; i++) {
			round1[i] = -1;
		}
		for (int i = 0; i < 4; i++) {
			round2[i] = -1;
		}
		for (int i = 0; i < 2; i++) {
			round3[i] = -1;
		}
		round4 = -1;
		index = -1;
		load();
	}
	
	public Torneo(String name){
		init();
		this.name = name;
		save();
	}
	
	public Torneo(){
		init();
	}
	
	public void setLocation(int position, PreciseLocation loc){
		pl[position - 1] = new Location(loc);
		save();
	}
	
	public PreciseLocation getLocation(int position){
		load();
		return pl[position].getPreciseLocation();
	}
	
	public void setRound(int round, int battle, int win){
		switch (round) {
		case 1:
			round1[battle] = win;
			break;
		case 2:
			round2[battle] = win;
			break;
		case 3:
			round3[battle] = win;
			break;
		}
	}
	
	public static int getLastIndex(){
		List<String> lastIndex = BewomByte.m.executeQuery("SELECT * FROM `torneos` ORDER BY `index` DESC", "index");
		return Integer.parseInt(lastIndex.get(0)) + 1;
	}
	
	public static Torneo load(int index){
		Torneo.current = new Torneo();
		Torneo.current.setIndex(index);
		
		String name = BewomByte.m.executeQuery("SELECT * FROM `torneos` WHERE `index`='" + index +"'", "name").get(0);
		Torneo.current.setName(name);
		
		int maxPlayers = Integer.parseInt(BewomByte.m.executeQuery("SELECT * FROM `torneos` WHERE `index`='" + index +"'", "maxPlayers").get(0));
		
		String date = BewomByte.m.executeQuery("SELECT * FROM `torneos` WHERE `index`='" + index +"'", "date").get(0);
		Torneo.current.setDate(date);
		
		Torneo.current.setPlayers(BewomByte.m.executeQuery("SELECT * FROM `torneos` WHERE `index`='" + index +"'", "playersName"));
		
		return Torneo.current;	
	}
	
	private void setPlayers(List<String> playerArray) {
		int x = 0;
		for(String p : playerArray){
			if(!p.equals("")){
				this.players[x] = p;
			} else {
				this.players[x] = null;
			}
			x++;
		}
	}

	public void save(){
		
		if(index == -1){
			BewomByte.m.executeQuery("INSERT INTO `torneos` SET `index`='" + getLastIndex() + "', `name`='" + name + "', `maxPlayers`='" + maxPlayers +"'", null);
		} else {
			BewomByte.m.executeQuery("UPDATE `torneos` SET `name`='" + name + "',`date`='" + date + "',`maxPlayers`='" + maxPlayers +"',"
					+ "`playersName`='" + getPlayersString() + "',"
					+ "`winsRound1`='" + getRoundString(round1) + "',`winsRound2`='" + getRoundString(round2) + "',"
					+ "`winsRound3`='" + getRoundString(round3) + "',`winsRound4`='" + round4 + "' WHERE `index`='" + index + "'", null);
		}
		
		try {
			
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/Torneos.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			String json = gson.toJson(pl);
			
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
			
			File file = new File("bewom/Torneos.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			
			Location[] l = gson.fromJson(reader, Location[].class);
			pl = l;
			
		} catch (IOException e) {
			BewomByte.log.debug(e.getMessage());
		}
		
	}
	
	private String getPlayersString(){
		if(players.length != 0){
			String playersString = "";
			for(String p : players){
				playersString += p + ",";
			}
			return playersString.substring(0 , playersString.length() - 1);
		}
		return ",,,,,,,,,,,,,,,";
	}
	
	private String getRoundString(int[] round){
		String roundString = "";
		for(int r : round){
			roundString += r + ",";
		}
		return roundString.substring(0 , roundString.length() - 1);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
