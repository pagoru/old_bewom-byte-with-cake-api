package es.bewom.teleport;

import java.util.UUID;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.util.PreciseLocation;

import com.google.gson.annotations.Expose;

public class Home {
	
	@Expose private double x;
	@Expose private double y;
	@Expose private double z;
	@Expose private float yaw;
	@Expose private float pitch;
	@Expose private int dimension;
	@Expose private UUID uuid;
	@Expose private String name;
	
	public Home(Player p, String name) {
		super();
		this.x = p.getLocation().getX();
		this.y = p.getLocation().getY();
		this.z = p.getLocation().getZ();
		this.yaw = p.getLocation().getYaw();
		this.pitch = p.getLocation().getPitch();
		this.dimension = p.getDimensionID();
		this.uuid = p.getUniqueID();
		this.name = name;
	}
	
	public PreciseLocation getLocation(){
		return new PreciseLocation(dimension, x, y, z, yaw, pitch);
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
