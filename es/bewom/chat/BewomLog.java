package es.bewom.chat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.cakepowered.api.base.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import es.bewom.BewomByte;

public class BewomLog {
	
	@Expose
	private String day;
	@Expose
	private String hour;
	private String extra = "";
	@Expose
	private ArrayList<Message> messages = new ArrayList<Message>();
	
	private DateFormat df = new SimpleDateFormat("dd-MM-yy");
	private DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
	private Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	
	public BewomLog(){
		Date d = new Date();
		this.day = df.format(d);
		this.hour = df2.format(d);
		
		File folder = new File("bewom/log");
		
		if(!folder.exists()){
			
			folder.mkdirs();
			
		}
		
		if(containsFileInDir(folder, day)){
			
			for (int i = 1; i < 99999; i++) {
				if(!containsFileInDir(folder, day + "(" + i + ")")){
					
					extra = "(" + i + ")";
					break;
					
				}
			}
			
		}
		
	}
	
	public static boolean containsFileInDir(File folder, String file) {
		
	    for (File fileEntry : folder.listFiles()) {
	        if(fileEntry.getName().contains(file)){
	        	return true;
	        }
	    }
	    return false;
	}
	
	public void add(Player p, String m){
		Date d = new Date();
		if(!this.day.equals(df.format(d))){
			
			this.hour = df2.format(d);
			this.extra = "";
			messages.clear();
			
		}
		this.day = df.format(d);
		
		String player = "server";
		UUID uuid = null;
		if(p != null){
			uuid = p.getUniqueID();
			player = p.getUserName();
		}
		
		messages.add(new Message().builder()
				.user(player)
				.uuid(uuid)
				.message(m)
				.hour(df2.format(d)));
		String outLog = g.toJson(this);
		
		try {
			FileWriter w = new FileWriter("bewom/log/" + day + extra + ".log");
			w.write(outLog);
			w.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public String getDay() {
		return day;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

}
