package es.bewom.p;

import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;
import org.cakepowered.api.world.World;

import com.google.gson.annotations.Expose;

public class Door {
	
	@Expose
	protected int[] dimension = {0, 0};
	@Expose
	protected double[] x = {0, 0};
	@Expose
	protected double[] y = {0, 0};
	@Expose
	protected double[] z = {0, 0};
	@Expose
	protected float[] yaw = {0, 0};
	@Expose
	protected float[] pitch = {0, 0};
	
	private int pos;
	
	public boolean isSameWorld(){
		
		if(dimension[0] == dimension[1]){
			return true;
		}
		return false;
		
	}
	
	public PreciseLocation getPreciseLocation(){
		return new PreciseLocation(dimension[pos], x[pos] + 0.5, y[pos], z[pos] + 0.5, yaw[pos], pitch[pos]);
	}
	
	public Door setDoorPos(int pos){
		this.pos = pos;
		return this;
	}
	public Door setLocation(PreciseLocation loc){
		this.dimension[pos] = loc.getDimension();
		this.x[pos] = loc.getX();
		this.y[pos] = loc.getY();
		this.z[pos] = loc.getZ();
		this.yaw[pos] = loc.getYaw();
		this.pitch[pos] = loc.getPitch();
		return this;
	}
	
	public boolean isSelected(double x, double y, double z, int i){
		if(x == this.x[pos] && (y == this.y[pos] || y == this.y[pos] + 1) && z == this.z[pos] && i == this.dimension[pos]){
			return true;									
		}
		return false;
	}

}
