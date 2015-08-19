package es.bewom.economy;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.util.PreciseLocation;

import com.google.gson.annotations.Expose;

import es.bewom.p.Door;

public class House {
	
	@Expose
	private Door door;
	@Expose
	private String uuidOwner;
	@Expose
	private boolean open;
	@Expose
	private int sellPrice;
	@Expose
	private int buyPrice;
	@Expose
	private List<String> friends = new ArrayList<String>();
	@Expose
	private double signX;
	@Expose
	private double signY;
	@Expose
	private double signZ;
	@Expose
	private int signDimension;
	
	private boolean selectDoor;
	private boolean selectSign;
	private Player player;
	
	public House(Player player) {
		this.player = player;
		this.selectDoor = true;
		this.selectSign = false;
	}
	
	public House(Door door, int sellPrice, int buyPrice) {
		super();
		this.open = false;
		this.door = door;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
	}
	
	public Player getPlayer() {
		return player;
	}
	public Door getDoor() {
		return door;
	}
	public String getOwner() {
		return uuidOwner;
	}
	public boolean isOpen() {
		return open;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public List<String> getFriends() {
		return friends;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setDoor(Door door) {
		this.door = door;
	}
	public void setUuidPropietario(String uuidPropietario) {
		this.uuidOwner = uuidPropietario;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public boolean isSelectDoor() {
		return selectDoor;
	}

	public boolean isSelectSign() {
		return selectSign;
	}

	public void setSelectDoor(boolean selectDoor) {
		this.selectDoor = selectDoor;
	}

	public void setSelectSign(boolean selectSign) {
		this.selectSign = selectSign;
	}

	public PreciseLocation getSignLocation() {
		return new PreciseLocation(signDimension, signX, signY, signZ, 0, 0);
	}
	public void setSignLocation(PreciseLocation loc) {
		this.signX = loc.getX();
		this.signY = loc.getY();
		this.signZ = loc.getZ();
		this.signDimension = loc.getDimension();
	}
}
