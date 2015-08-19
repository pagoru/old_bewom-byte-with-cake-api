package es.bewom.economy;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;

import com.google.gson.annotations.Expose;

public class Shop {
	
	@Expose
	private int sellPrice;
	@Expose
	private int buyPrice;
	@Expose
	private String itemName;
	@Expose
	private String shopName;
	@Expose
	private int quantity;
	@Expose
	private double x;
	@Expose
	private double y;
	@Expose
	private double z;
	@Expose
	private int dimension;
	
	private boolean building;
	private Player player;
	
	public Shop(Player player, int sellPrice, int buyPrice, String itemName, String shopName, int quantity) {
		super();
		this.player = player;
		this.building = true;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.itemName = itemName;
		this.shopName = shopName;
		this.quantity = quantity;
	}

	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public void setPreciseLocation(PreciseLocation pl) {
		this.x = pl.getX();
		this.y = pl.getY();
		this.z = pl.getZ();
		this.dimension = pl.getDimension();
	}

	public boolean isSelected(Vector3d position, int dimensionID) {
		if(position.getX() == this.x && position.getY() == this.y && position.getZ() == this.z && dimensionID == this.dimension){
			return true;									
		}
		return false;
	}
	
}
