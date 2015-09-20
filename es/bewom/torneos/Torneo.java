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
import com.google.gson.annotations.Expose;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;
import es.bewom.util.Location;

public class Torneo {
	
	
	@Expose private int index = -1;
	@Expose private String name = "";
	@Expose private String date = "";
	
	@Expose private Location[] location = new Location[4];
	
	@Expose private String[] playersRound1 = new String[16];
	@Expose private String[] playersRound2 = new String[8];
	@Expose private String[] playersRound3 = new String[4];
	@Expose private String[] playersRound4 = new String[2];
	@Expose private String playerRound5 = null;
	
	@Expose private int[] winnersRound1 = {-1, -1, -1, -1, -1, -1, -1, -1};
	@Expose private int[] winnersRound2 = {-1, -1, -1, -1};
	@Expose private int[] winnersRound3 = {-1, -1};
	@Expose private int winnerRound4 = -1;
	
	public Torneo(){
		index = Integer.parseInt(BewomByte.m.executeQuery("SELECT `index` FROM `torneos` ORDER BY `index` DESC", "index").get(0));
	}
	
	public Torneo(int index){
		this.index = index;
	}
	
	public Torneo(String name, String date){
		index = Integer.parseInt(BewomByte.m.executeQuery("SELECT `index` FROM `torneos` ORDER BY `index` DESC", "index").get(0)) + 1;
		
		this.name = name;
		this.date = date;
		
		insert();
		
		playersRound1 = new String[16];
		playersRound2 = new String[8];
		playersRound3 = new String[4];
		playersRound4 = new String[2];
		playerRound5 = null;
		
		for (int i = 0; i < winnersRound1.length; i++) {
			winnersRound1[i] = -1;
		}
		for (int i = 0; i < winnersRound2.length; i++) {
			winnersRound2[i] = -1;
		}
		for (int i = 0; i < winnersRound3.length; i++) {
			winnersRound3[i] = -1;
		}
		winnerRound4 = -1;
		
		save();
	}
	
	public void purgePlayers(){
		for (int i = 0; i < playersRound1.length; i++) {
			if(playersRound1[i] != null){
				if(BewomByte.game.getServer().getPlayer(playersRound1[i]) == null){
					System.out.println(playersRound1[i]);
					BewomUser.addPoints(playersRound1[i], -1);
					playersRound1[i] = null;
				}
			}
		}
		for(Player p : BewomByte.game.getServer().getOnlinePlayers()){
			BewomUser u = BewomUser.getUser(p);
			if(u.getPoints() < 0){
				u.addPoints(1);
			}
		}
	}
	
	public void setLocation(int i, PreciseLocation pl){
		this.location[i] = new Location(pl);
	}
	
	public Location[] getLocation(){
		return this.location;
	}
	
	public String getThirdWinner(){
		int w = -1;
		if(winnerRound4 == 0){
			w = 1;
		}
		return playersRound3[winnersRound3[winnerRound4] + (winnerRound4*2) + w];
	}
	
	private void updateWinners(){
		for (int i = 0; i < 8; i++) {
			if(winnersRound1[i] != -1){
				playersRound2[i] = playersRound1[(i*2) + winnersRound1[i]];
			} else {
				playersRound2[i] = null;
			}
		}
		for (int i = 0; i < 4; i++) {
			if(winnersRound2[i] != -1){
				playersRound3[i] = playersRound2[(i*2) + winnersRound2[i]];
			} else {
				playersRound3[i] = null;
			}
		}
		for (int i = 0; i < 2; i++) {
			if(winnersRound3[i] != -1){
				playersRound4[i] = playersRound3[(i*2) + winnersRound3[i]];
			} else {
				playersRound4[i] = null;
			}
		}
		if(winnerRound4 != -1){
			playerRound5 = playersRound4[winnerRound4];
		} else {
			playerRound5 = null;
		}
	}
	
	public String[] getBattle(int ronda, int batalla){
		load();
		switch(ronda){
			case 0:
				return getBattleFromRound(playersRound1, batalla);
			case 1:
				return getBattleFromRound(playersRound2, batalla);
			case 2:
				return getBattleFromRound(playersRound3, batalla);
			case 3:
				return getBattleFromRound(playersRound4, batalla);
		}
		return null;
	}
	
	private String[] getBattleFromRound(String[] ronda, int batalla){
		String[] battle = new String[2];
		battle[0] = ronda[batalla*2];
		battle[1] = ronda[(batalla*2) + 1];
		return battle;
	}
	
	public boolean setWinner(int ronda, int batalla, String winner){
		switch(ronda){
			case 0:
				return getWinnerFrom(playersRound1, playersRound2, batalla, winnersRound1, winner);
			case 1:
				return getWinnerFrom(playersRound2, playersRound3, batalla, winnersRound2, winner);
			case 2:
				return getWinnerFrom(playersRound3, playersRound4, batalla, winnersRound3, winner);
			case 3:
				return getWinnerFrom(playersRound4, playerRound5, batalla, winner);
		}
		return false;
	}
	
	private boolean getWinnerFrom(String[] ronda, String[] ronda2, int batalla, int[] winnerRound, String winner){
		String[] battle = new String[2];
		battle[0] = ronda[batalla*2];
		battle[1] = ronda[(batalla*2) + 1];
		
		if(winner.equalsIgnoreCase(battle[0])){
			winnerRound[batalla] = 0;
			ronda2[batalla] = battle[0];
			updateWinners();
			save();
		} else if(winner.equalsIgnoreCase(battle[1])){
			winnerRound[batalla] = 1;
			ronda2[batalla] = battle[1];
			updateWinners();
			save();
		} else if(winner.equalsIgnoreCase("null")){
			winnerRound[batalla] = -1;
			ronda2 = null;
			updateWinners();
			save();
		} else {
			return false;
		}
		return true;
	}
	
	private boolean getWinnerFrom(String[] ronda, String ronda2, int batalla, String winner){
		String[] battle = new String[2];
		battle[0] = ronda[batalla*2];
		battle[1] = ronda[(batalla*2) + 1];
		
		if(winner.equalsIgnoreCase(battle[0])){
			winnerRound4 = 0;
			ronda2 = battle[0];
			updateWinners();
			save();
		} else if(winner.equalsIgnoreCase(battle[1])){
			winnerRound4 = 1;
			ronda2 = battle[1];
			updateWinners();
			save();
		} else if(winner.equalsIgnoreCase("null")){
			winnerRound4 = -1;
			ronda2 = null;
			updateWinners();
			save();
		} else {
			return false;
		}
		return true;
	}
	
	public void load(){
		this.name = BewomByte.m.executeQuery("SELECT `name` FROM `torneos` WHERE `index`='" + index + "'", "name").get(0);
		this.date = BewomByte.m.executeQuery("SELECT `date` FROM `torneos` WHERE `index`='" + index + "'", "date").get(0);
		
		this.playersRound1 = getArrayStringFromList(BewomByte.m.executeQuery("SELECT `playersName` FROM `torneos` WHERE `index`='" + index + "'", "playersName"));
		
		this.winnersRound1 = getArrayIntFromList(BewomByte.m.executeQuery("SELECT `winsRound1` FROM `torneos` WHERE `index`='" + index + "'", "winsRound1"));
		this.winnersRound2 = getArrayIntFromList(BewomByte.m.executeQuery("SELECT `winsRound2` FROM `torneos` WHERE `index`='" + index + "'", "winsRound2"));
		this.winnersRound3 = getArrayIntFromList(BewomByte.m.executeQuery("SELECT `winsRound3` FROM `torneos` WHERE `index`='" + index + "'", "winsRound3"));
		this.winnerRound4 = Integer.parseInt(BewomByte.m.executeQuery("SELECT `winsRound4` FROM `torneos` WHERE `index`='" + index + "'", "winsRound4").get(0));
		
		updateWinners();
	}
	
	private int[] getArrayIntFromList(List<String> executeQuery) {
		int[] i = new int[executeQuery.size()];
		for (int j = 0; j < executeQuery.size(); j++) {
			i[j] = Integer.parseInt(executeQuery.get(j));
		}
		return i;
	}

	private String[] getArrayStringFromList(List<String> executeQuery) {
		String[] i = new String[executeQuery.size()];
		for (int j = 0; j < executeQuery.size(); j++) {
			if(executeQuery.get(j).length() != 0){
				i[j] = executeQuery.get(j);
			}
		}
		return i;
	}
	
	public void insert(){
		BewomByte.m.executeQuery(
				"INSERT INTO `torneos` SET "
				+ "`index`='" + index+ "',"
				+ "`name`='" + name + "',"
				+ "`date`='" + date + "'", null);
	}

	public void save(){
		BewomByte.m.executeQuery(
				"UPDATE `torneos` SET "
				+ "`name`='" + name + "',"
				+ "`date`='" + date + "',"
				
				+ "`playersName`='" + getStringComasFromArrayString(playersRound1) + "',"
				
				+ "`winsRound1`='" + getStringComasFromArrayInt(winnersRound1) + "',"
				+ "`winsRound2`='" + getStringComasFromArrayInt(winnersRound2) + "',"
				+ "`winsRound3`='" + getStringComasFromArrayInt(winnersRound3) + "',"
				+ "`winsRound4`='" + winnerRound4 + "' "
				
				+ "WHERE `index`='" + index + "'", "name");
	}
	
	//UTIL
	private String getStringComasFromArrayString(String[] str){
		String strComas = "";
		for (int i = 0; i < str.length; i++) {
			if(str[i] != null){
				strComas += str[i] + ",";
			} else {
				strComas += ",";
			}
		}
		return strComas.substring(0, strComas.length()-1);
	}
	
	private String getStringComasFromArrayInt(int[] str){
		String strComas = "";
		for (int i = 0; i < str.length; i++) {
			strComas += str[i] + ",";
		}
		return strComas.substring(0, strComas.length()-1);
	}
	
	private String[] getArrayStringFromStringComas(String str){
		String[] splited = str.split(",");
		for (int i = 0; i < splited.length; i++) {
			if(splited[i].length() == 0){
				splited[i] = null;
			}
		}
		return splited;
	}
	
	private int[] getArrayIntFromStringComas(String str){
		String[] splited = str.split(",");
		int[] in = new int[splited.length];
		for (int i = 0; i < splited.length; i++) {
			if(splited[i].length() == 0){
				in[i] = Integer.parseInt(splited[i]);
			} else {
				in[i] = -1;
			}
		}
		return in;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
