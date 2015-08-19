package es.bewom.centrospokemon;

import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;
import org.cakepowered.api.util.Vector3i;

import com.google.gson.annotations.Expose;

public class CentroPokemon {
	
	@Expose
	public int dimension;
	@Expose
	public double x;
	@Expose
	public double y;
	@Expose
	public double z;
	@Expose
	public float yaw;
	@Expose
	public float pitch;
	
	public CentroPokemon(PreciseLocation loc) {
		this.dimension = loc.getDimension();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.yaw = loc.getYaw();
		this.pitch = loc.getPitch();
	}

	public boolean isEqualTo(PreciseLocation loc) {
		if(loc == new PreciseLocation(dimension, x, y, z, yaw, pitch)) {
			return true;
		}
		return false;
	}
	
	public boolean isNear(PreciseLocation location) {
		if(dimension != location.getDimension()) return false;
		if(location.getPosition().distance(getVector()) < 1) {
			return true;
		}
		return false;
	}
	
	public int distance(Vector3d vector3d) {
		Vector3i pos = new Vector3i(x, y, z);
		Vector3i loc = new Vector3i(vector3d.getX(), vector3d.getY(), vector3d.getZ());
		return (int) Math.abs(pos.toVector3d().distance(loc.toVector3d()));
	}
	
	public Vector3d getVector() {
		return new Vector3d(x, y, z);
	}
	
	public PreciseLocation getLocation(){
		return new PreciseLocation(dimension, x, y, z, yaw, pitch);
	}

}
