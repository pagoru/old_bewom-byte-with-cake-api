package es.bewom.p;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.util.PreciseLocation;

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
	
	private boolean firstDoor;
	private boolean secondDoor;
	private Player player;
	
	public Door(Player player) {
		this.player = player;
		this.firstDoor = true;
		this.secondDoor = false;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public boolean isFirstDoor() {
		return firstDoor;
	}

	public void setFirstDoor(boolean firstDoor) {
		this.firstDoor = firstDoor;
	}

	public boolean isSecondDoor() {
		return secondDoor;
	}

	public void setSecondDoor(boolean secondDoor) {
		this.secondDoor = secondDoor;
	}

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
