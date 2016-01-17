package es.bewom.p;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.tileentity.TileEntity;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3d;
import org.cakepowered.api.util.Vector3i;

import com.google.gson.annotations.Expose;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;
import es.bewom.util.Dimensions;

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
	private double signX;
	@Expose
	private double signY;
	@Expose
	private double signZ;
	@Expose
	private int signDimension;
	@Expose
	
	private boolean selectDoor;
	@Expose
	private boolean selectSign;
	@Expose
	private Player player;
	
	public House(Player player, int sellPrice, int buyPrice) {
		this.player = player;
		this.selectDoor = true;
		this.selectSign = false;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
	}
	public void sellHouse(Player p){
		this.uuidOwner = null;
		setSoldSign(false);
		BewomUser.getUser(p).addMoney(sellPrice);
	}
	
	public void sellHouse(){
		this.uuidOwner = null;
		setSoldSign(false);
	}
	
	public void setSoldSign(boolean sold){
		TileEntity tileEntity = BewomByte.game.getServer().getWorld(signDimension).getTileEntity(new Vector3i(signX, signY, signZ));
		NBTCompund nbt = BewomByte.game.getNBTFactory().newNBTCompound();
		tileEntity.writeToNBT(nbt);

		nbt.setBoolean("sold", sold);
		tileEntity.readFromNBT(nbt);
		tileEntity.writeToNBT(nbt);
		
		for(Player p : BewomByte.game.getServer().getOnlinePlayers()){
			tileEntity.syncPlayer(p);
		}
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
		Houses.save();
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
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
	
	public boolean isSignSelected(double x, double y, double z, int dimension){
		if(x == signX && y == signY && z == signZ && signDimension == dimension){
			return true;
		}
		return false;
	}
}
