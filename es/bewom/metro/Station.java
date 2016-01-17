package es.bewom.metro;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.util.PreciseLocation;

import com.google.gson.annotations.Expose;

public class Station {
	
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
	
	@Expose
	protected double[] xSpawn = {0, 0};
	@Expose
	protected double[] ySpawn = {0, 0};
	@Expose
	protected double[] zSpawn = {0, 0};
	
	@Expose
	protected String[] nameStation = {"", ""};
	
	private int pos;
	private Player p;
	private boolean first;
	private boolean second;
	private boolean delete;
	
	public Station(){
		this.pos 		= 0;
		this.first 		= false;
		this.second 	= false;
		this.delete 	= false;
	}
	
	public String getNameStation(){
		return nameStation[pos];
	}
	
	public Station setNameStation(String nameStation){
		this.nameStation[pos] = nameStation;
		return this;
	}
	
	public void delete(){
		this.delete = true;
	}
	
	public boolean canDelete(){
		return this.delete;
	}
	
	public Station setPos(int pos){
		this.pos = pos;
		return this;
	}
	
	public Station setPlayer(Player p){
		this.p = p;
		return this;
	}
	
	public Player getPlayer(){
		return this.p;
	}
	
	public void setFirst(boolean b){
		this.first = b;
	}
	
	public void setSecond(boolean b){
		this.second = b;
	}
	
	public boolean isFirst(){
		return this.first;
	}
	
	public boolean isSecond(){
		return this.second;
	}
	
	public Station setSpawnLocation(PreciseLocation loc){
		this.xSpawn[pos] 		= loc.getX();
		this.ySpawn[pos] 		= loc.getY();
		this.zSpawn[pos] 		= loc.getZ();
		this.yaw[pos] 			= loc.getYaw();
		this.pitch[pos] 		= loc.getPitch();
		return this;
	}
	
	public PreciseLocation getSpawnLocation(){
		return new PreciseLocation(dimension[pos], xSpawn[pos], ySpawn[pos], zSpawn[pos], yaw[pos], pitch[pos]);
	}
	
	public Station setBlockLocation(PreciseLocation loc){
		this.dimension[pos] 	= loc.getDimension();
		this.x[pos] 			= loc.getX();
		this.y[pos] 			= loc.getY();
		this.z[pos] 			= loc.getZ();
		return this;
	}
	
	public PreciseLocation getBlockLocation(){
		return new PreciseLocation(dimension[pos], x[pos], y[pos], z[pos], yaw[pos], pitch[pos]);
	}
	
	public boolean isAnyBlockSelected(double x, double y, double z, int i){
		pos = 0;
		if(x == this.x[pos] && (y == this.y[pos] || y == this.y[pos] + 1) && z == this.z[pos] && i == this.dimension[pos]){
			return true;									
		}
		pos = 1;
		if(x == this.x[pos] && (y == this.y[pos] || y == this.y[pos] + 1) && z == this.z[pos] && i == this.dimension[pos]){
			return true;									
		}
		return false;
	}
	
	public boolean isBlockSelected(double x, double y, double z, int i){
		if(x == this.x[pos] && (y == this.y[pos] || y == this.y[pos] + 1) && z == this.z[pos] && i == this.dimension[pos]){
			return true;									
		}
		return false;
	}
	
}
