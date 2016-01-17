package es.bewom.gimnasios;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Entity;
import org.cakepowered.api.block.BlockState;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.event.PlayerInteractEvent.ClickAction;
import org.cakepowered.api.inventory.ItemStack;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.util.Direction;
import org.cakepowered.api.util.Vector3i;

import es.bewom.BewomByte;

public class Gimnasios {
		
	public static List<Gym> gyms = new ArrayList<Gym>();
	
	public static void on(){
		
//		BewomByte.game.getServer().getWorld(0).getEntitiesExcludingType(null, minX, minY, minZ, maxX, maxY, maxZ);
		
	}

}
