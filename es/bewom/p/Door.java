package es.bewom.p;

import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.world.World;

public class Door {
	
	private PreciseLocation[] loc = {null, null};
	private int pos;
	
	public boolean isSameWorld(){
		
		if(loc[0].getDimension() == loc[1].getDimension()){
			return true;
		}
		return false;
		
	}
	
	public PreciseLocation getPreciseLocation(){
		return new PreciseLocation(loc[pos].getDimension(), loc[pos].getX() + 0.5, loc[pos].getY(), loc[pos].getZ() + 0.5, loc[pos].getYaw(), loc[pos].getPitch());
	}
	
	public Door setDoorPos(int pos){
		this.pos = pos;
		return this;
	}
	public Door setLocation(PreciseLocation loc){
		this.loc[pos] = loc;
		return this;
	}
	
	public boolean isSelected(double x, double y, double z, int i){
		if(x == loc[pos].getX() && (y == loc[pos].getY() || y == loc[pos].getY() + 1) && z == loc[pos].getZ() && i == loc[pos].getDimension()){
			return true;									
		}
		return false;
	}

}
