package es.bewom.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.cakepowered.api.nbt.NBTBase;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.nbt.NBTFactory;
import org.cakepowered.api.nbt.NBTList;

import es.bewom.BewomByte;

public class PokemonCatcher {
	

	public static void catchPokemons(String uuid){
		
		NBTCompund nbt = BewomByte.game.getNBTFactory().readNBT(new File("world/pokemon/" + uuid + ".pk"));
		
		int[] lvl = {0, 0, 0, 0, 0, 0}; 
		String[] name = {"", "", "", "", "", ""};
		int[] sh = {0, 0, 0, 0, 0, 0};
		
		for (int i = 0; i < 6; i++) {
			
			NBTCompund n = (NBTCompund) nbt.getCompound("party" + i);
			
			if(n != null){
				if(n.getString("Name") != null){
					name[i] = n.getString("Name");
					lvl[i] = n.getInteger("Level");
					
					sh[i] = n.getByte("IsShiny");
				}
			}
			
		}
		
		BewomByte.m.executeQuery("UPDATE `users` SET "
				+ "`poke0name`='" + name[0] + "',`poke0lvl`='" + lvl[0] + "',`poke0shiny`='" + sh[0] + "',"
				+ "`poke1name`='" + name[1] + "',`poke1lvl`='" + lvl[1] + "',`poke1shiny`='" + sh[1] + "',"
				+ "`poke2name`='" + name[2] + "',`poke2lvl`='" + lvl[2] + "',`poke2shiny`='" + sh[2] + "',"
				+ "`poke3name`='" + name[3] + "',`poke3lvl`='" + lvl[3] + "',`poke3shiny`='" + sh[3] + "',"
				+ "`poke4name`='" + name[4] + "',`poke4lvl`='" + lvl[4] + "',`poke4shiny`='" + sh[4] + "',"
				+ "`poke5name`='" + name[5] + "',`poke5lvl`='" + lvl[5] + "',`poke5shiny`='" + sh[5] + "'"
				+ "WHERE `uuid`='" + uuid + "'", null);
		
	}
	
}
