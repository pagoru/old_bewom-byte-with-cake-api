package es.bewom.economy;

import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.inventory.ItemStack;
import org.cakepowered.api.item.Item;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;

public class Shops {
	
	public static Game game;
	
	public static List<Shop> shops = new ArrayList<Shop>();
	
	public static void on(Game g, PlayerInteractEvent event) {
		game = g;
		
		Player p = event.getPlayer();
		BewomUser u = BewomUser.getUser(p);
		
		if(shops != null){
			for (Shop s : shops) {
				if(!s.isBuilding()){
					if(s.isSelected(event.getPosition().toVector3d(), p.getDimensionID())){
						if(p.isSneaking()){
							int it = 0;
							for (int i = 0; i < 40; i++) {
								ItemStack item = p.getPlayerInventory().getStackInSlot(i);
								if(item != null){
									String itemName = BewomByte.game.getFinder().getCompleteName(item.getItem());
									if(s.getItemName().equals(itemName) && item.getStackSize() >= s.getQuantity()){
										item.setStackSize(item.getStackSize() - s.getQuantity());
										u.addMoney(s.getSellPrice());
										p.getPlayerInventory().setStackInSlot(i, item);
										it++;
										break;
									}
								}
							}
							if(it == 0){
								p.sendMessage(TextFormating.RED + "No tienes suficiente " + s.getShopName() + " para vender!");
							} else {
								p.sendMessage(TextFormating.AQUA + "Has vendido " + s.getQuantity() + " " + s.getShopName() + " por " + s.getSellPrice() + " Woms.");
							}
							
						} else {
							if(u.canSubstractMoney(s.getBuyPrice())){
								
								String shopName = s.getItemName();
								
								List<String> a = new ArrayList<String>();
								String[] splited = shopName.split(":");
								for (int i = 0; i < splited.length; i++) {
									a.add(splited[i]);
								}
								
								Item it = game.getFinder().findItem(a.get(0), a.get(1));
								ItemStack stackItem = it.createStack(s.getQuantity(), 0);
								p.getPlayerInventory().addItemStackToInventory(stackItem);
								u.substractMoney(s.getBuyPrice());
								p.sendMessage(TextFormating.AQUA + "Has comprado " + s.getQuantity() + " " + s.getShopName() + " por " + s.getBuyPrice() + " Woms.");
								
							} else {
								p.sendMessage(TextFormating.RED + "No tienes suficiente dinero! :(");
							}
						}
						
					}
				} else {

					if(s.getPlayer().equals(p)){
						s.setPreciseLocation(new PreciseLocation(p.getDimensionID(), event.getPosition().toVector3d(), 0, 0));
						s.setBuilding(false);
						s.setPlayer(null);
						p.sendMessage("Tienda creada!");
					}
				}
			}
		}
		
	}

}
