package es.bewom.economy;

import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;
import org.cakepowered.api.util.Vector3i;

public class Shop {
	
	public int sellPrice;
	public int buyPrice;
	public String unlocalizedName;
	public String shopName;
	public int quantity;
	public double x;
	public double y;
	public double z;
	public int dimension;
	
	public Shop(int sellPrice, int buyPrice, String unlocalizedName, String shopName, int quantity, PreciseLocation pl) {
		super();
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.unlocalizedName = unlocalizedName;
		this.shopName = shopName;
		this.quantity = quantity;
		this.x = pl.getX();
		this.y = pl.getY();
		this.z = pl.getZ();
		this.dimension = pl.getDimension();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	public void setUnlocalizedName(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public PreciseLocation getPreciseLocation() {
		return new PreciseLocation(dimension, x, y, z, 0, 0);
	}
	public void setPrciseLocation(PreciseLocation pl) {
		this.x = (int) pl.getX();
		this.y = (int) pl.getY();
		this.z = (int) pl.getZ();
		this.dimension = pl.getDimension();
	}

	public boolean isSelected(Vector3d position, int dimensionID) {
		if(position.getX() == this.x && position.getY() == this.y && position.getZ() == this.z && dimensionID == this.dimension){
			return true;									
		}
		return false;
	}
	
}
