package es.bewom.economy;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.block.Block;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.text.TextFormating;
import org.cakepowered.api.world.World;

import es.bewom.p.Door;
import es.bewom.p.P;

public class Houses {
	
	public static List<House> houses = new ArrayList<House>();
	
	public static void on(Game game, PlayerInteractEvent event){
		
		Player p = event.getPlayer();
		double x = event.getPosition().getX();
		double y = event.getPosition().getY();
		double z = event.getPosition().getZ();
		Block b =  event.getInteractBlock();
		World world = p.getWorld();
		
		if(b != null){
			
			if(houses != null){
				
				for (House h : houses) {
					
					if(h.getPlayer() != null){
						if(h.getPlayer().equals(p)){
							if(h.isSelectSign()){
//								h.setUuidPropietario(p.getUniqueID().toString()); //quitar
								h.setSignLocation(new PreciseLocation(p.getDimensionID(), x, y, z, 0, 0));
								h.setSelectSign(false);
								h.setPlayer(null);
								p.sendMessage("Casa seleccionada.");
							} 
							if(h.isSelectDoor()){
								int a = 0;
								for(Door d : P.doors){
									if(d.isSelected(x, y, z, p.getDimensionID())){
										h.setDoor(d);
										h.setSelectDoor(false);
										h.setSelectSign(true);
										p.sendMessage("Selecciona el cartel.");
										a++;
										break;
									}
								}
								if(a == 0){
									p.sendMessage(TextFormating.RED + "No se ha seleccionado una puerta valida!");
								}
							}
						}
					}
					
				}
			}
		}
	}
}
