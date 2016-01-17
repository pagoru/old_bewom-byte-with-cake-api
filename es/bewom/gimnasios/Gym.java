package es.bewom.gimnasios;

import org.cakepowered.api.util.PreciseLocation;

public class Gym {
	
	private double[] 	x = new double[2];
	private double[] 	y = new double[2];
	private double[] 	z = new double[2];
	private int 		d;
	private String 		o;
	
	private int		pos = 0;
	
	public Gym(PreciseLocation[] pl, String object){
		
		x[0] = pl[0].getX();
		x[1] = pl[1].getX();
		
		y[0] = pl[0].getY();
		y[1] = pl[1].getY();
		
		z[0] = pl[0].getZ();
		z[1] = pl[1].getZ();
		
		d 	 = pl[0].getDimension();
		
		o	 = object;
		
	}
	
	public Gym setPosition(int p){
		pos = p;
		return this;
	}
	
	public PreciseLocation getPreciseLocation(){
		return new PreciseLocation(d, x[pos], y[pos], z[pos], 0f, 0f);
	}
	
	public String getObject(){
		return o;
	}
	
	public boolean isInside(PreciseLocation pl){
		
		if(pl.getDimension() == d){
			
			double x_ = pl.getX();
			double y_ = pl.getY();
			double z_ = pl.getZ();
			
			if(((x[0] >= x_ && x[1] <= x_) || (x[1] >= x_ && x[0] <= x_))
					&& ((y[0] >= y_ && y[1] <= y_) || (y[1] >= y_ && y[0] <= y_))
					&& ((z[0] >= z_ && z[1] <= z_) || (z[1] >= z_ && z[0] <= z_))){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}

}
