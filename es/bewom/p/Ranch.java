package es.bewom.p;

import com.google.gson.annotations.Expose;

public class Ranch {
	
	@Expose
	private double x;
	@Expose
	private double y;
	@Expose
	private double z;
	@Expose
	private int dimension;
	@Expose
	private String uuid;
	@Expose
	private boolean creation;
	
	public Ranch(String uuid) {
		super();
		this.uuid = uuid;
		this.creation = true;
	}
	public Ranch(double x, double y, double z, int dimension, String uuid) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
		this.uuid = uuid;
		this.creation = false;
	}
	
	public boolean isCreation() {
		return creation;
	}
	public void setCreation(boolean creation) {
		this.creation = creation;
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean isSelected(double x, double y, double z, int dimension){
		if(		   x >= (getX() - 5) 
				&& x <= (getX() + 5)
				&& z >= (getZ() - 5) 
				&& z <= (getZ() + 5)
				&& y >= (getY() - 2)
				&& y <= (getY() + 255)
				&& this.dimension == dimension){
			return true;
		}
		return false;
	}
}
