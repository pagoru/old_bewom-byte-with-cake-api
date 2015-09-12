package es.bewom.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.Tag;

import es.bewom.BewomByte;

public class PokemonCatcher {
	
	private static FileInputStream a;

	public static void catchPokemons(String uuid){
		
		try {
			a = new FileInputStream(new File("world/pokemon/" + uuid + ".pktemp"));
			
			String path = "world/pokemon/" + uuid + ".data_";
			
			int ch;
		    StringBuffer strContent = new StringBuffer("");
			
			while( (ch = a.read()) != -1){
				strContent.append((char)ch);
			}
			
			File file = new File(path);
			file.setReadable(true, false);
			file.setExecutable(true, false);
			file.setWritable(true, false);
			file.createNewFile();
			
			GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(file));
			gzos.write(strContent.toString().getBytes());
			gzos.close();
			
			
			NBTInputStream ns = new NBTInputStream(new FileInputStream(file));
			Tag master = ns.readTag();
			ns.close();
			
			
			String[] lvl = {"0", "0", "0", "0", "0", "0"}; 
			String[] name = {"", "", "", "", "", ""};
			
			for (int i = 0; i < 200; i++) {
				
				if(getCompounfTag(master).getValue().get("party" + i) != null){
					
					Tag t = getCompounfTag(master).getValue().get("party" + i);
					
					lvl[i] = getValueFromTag(t, "Level");
					name[i] = getValueFromTag(t, "Name");
					
	//				String gender = getValueFromTag(t, "Gender");
	//				String Healh = getValueFromTag(t, "Health");
					
				}
				
			}
			
			BewomByte.m.executeQuery("UPDATE `users` SET "
					+ "`poke0name`='" + name[0] + "',`poke0lvl`='" + lvl[0] + "',"
					+ "`poke1name`='" + name[1] + "',`poke1lvl`='" + lvl[1] + "',"
					+ "`poke2name`='" + name[2] + "',`poke2lvl`='" + lvl[2] + "',"
					+ "`poke3name`='" + name[3] + "',`poke3lvl`='" + lvl[3] + "',"
					+ "`poke4name`='" + name[4] + "',`poke4lvl`='" + lvl[4] + "',"
					+ "`poke5name`='" + name[5] + "',`poke5lvl`='" + lvl[5] + "'"
					+ "WHERE `uuid`='" + uuid + "'", null);
			
		} catch (IOException e) {
			e.printStackTrace();
		};
		
	}
	
	private static String getValueFromTag(Tag t, String key) throws IOException{
		
		return getCompounfTag(t).getValue().get(key).getValue().toString();
		
	}
	
	private static CompoundTag getCompounfTag(Tag master) throws IOException{
		
		if (!(master instanceof CompoundTag)) {
			throw new IllegalArgumentException("Expected CompoundTag, got " + master.getClass());
		}

		return (CompoundTag) master;
		
	}
	
}
