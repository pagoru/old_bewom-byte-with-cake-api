package es.bewom.p;

import org.cakepowered.api.util.Location;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;
import org.cakepowered.api.world.World;

public class Door {
	
	private PreciseLocation[] loc = {null, null};
	private int pos;
	
	public boolean isSameWorld(){
		
		System.out.println(loc[0].getWorld().getName() + "_" + loc[1].getWorld().getName());
		if(loc[0].getWorld().getName().equals(loc[1].getWorld().getName())){
			return true;
		}
		return false;
		
	}
	
	public PreciseLocation getPreciseLocation(){
		return new PreciseLocation(loc[pos].getWorld(), loc[pos].getX() + 0.5, loc[pos].getY(), loc[pos].getZ() + 0.5, loc[pos].getYaw(), loc[pos].getPitch());
	}
	
	public Door setDoorPos(int pos){
		this.pos = pos;
		return this;
	}
	public Door setLocation(PreciseLocation loc){
		this.loc[pos] = loc;
		return this;
	}
	
	public boolean isSelected(double x, double y, double z, World world){
		if(x == loc[pos].getX() && (y == loc[pos].getY() || y == loc[pos].getY() + 1) && z == loc[pos].getZ() && world.getName().equals(loc[pos].getWorld().getName())){
			return true;									
		}
		return false;
	}

}
