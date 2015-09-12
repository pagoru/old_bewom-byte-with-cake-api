package es.bewom.util;

import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;

import com.google.gson.annotations.Expose;

public class Location {
	
	@Expose
	protected int dimension;
	@Expose
	protected double x;
	@Expose
	protected double y;
	@Expose
	protected double z;
	@Expose
	protected float yaw;
	@Expose
	protected float pitch;

	public Location(int d, Vector3d pos, float y, float p) {
		dimension = d;
		x = pos.getX();
		this.y = pos.getY();
		z = pos.getZ();
		yaw = y;
		pitch = p;
	}
	
	public Location(PreciseLocation pl) {
		dimension = pl.getDimension();
		x = pl.getX();
		this.y = pl.getY();
		z = pl.getZ();
		yaw = pl.getYaw();
		pitch = pl.getPitch();
	}

	public Location(int d, double x, double y, double z, float yaw, float pitch) {
		this(d, new Vector3d(x, y, z), yaw, pitch);
	}
	
	public PreciseLocation getPreciseLocation(){
		return new PreciseLocation(dimension, getPosition(), yaw, pitch);
	}
	
	public int getDimension() {
		return dimension;
	}

	public Vector3d getPosition() {
		return new Vector3d(x, y, z);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}
	
}
