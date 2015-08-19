package es.bewom.economy;

import java.util.ArrayList; 
import java.util.List;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.event.BlockBreakEvent;
import org.cakepowered.api.event.EntityAttackedEvent;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.user.BewomUser;
import es.bewom.util.BewomUtils;

public class Shops {
	
	public static Player player;
	public static boolean selection = false;
	
	public static Game game;
	
	public static List<Shop> shops = new ArrayList<Shop>();
	
	public static void on(Game g, PlayerInteractEvent event) {
		game = g;
		
		Player p = event.getPlayer();
		BewomUser u = BewomUser.getUser(p);
		
		for (int i = 0; i < shops.size(); i++) {
			Shop s  = shops.get(i);
			if(s.isSelected(event.getPosition().toVector3d(), player.getDimensionID())){
				if(p.isSneaking()){
					u.addMoney(s.sellPrice);
					p.sendMessage(TextFormating.AQUA + "Has vendido " + s.shopName + " por " + s.buyPrice + " Woms.");
//					p.addItemStack(s.getUnlocalizedName(), s.getQuantity());
				} else {
					if(u.substractMoney(s.buyPrice)){
						p.sendMessage(TextFormating.AQUA + "Has comprado " + s.shopName + " por " + s.buyPrice + " Woms.");
						BewomUtils.addItemStack(p, s.getUnlocalizedName(), s.getQuantity());
					} else {
						p.sendMessage(TextFormating.RED + "No tienes suficiente dinero! :(");
					}
				}
				
			}
		}
		
		if(selection && player.equals(p)){
			shops.add(new Shop(10, 20, "pixelmon:Green_Apricorn_Tree", "Green Apricorn Tree", 1, new PreciseLocation(p.getDimensionID(), event.getPosition().toVector3d(), 0, 0)));
			selection = false;
			p.sendMessage("Tienda creada!");
		}
		
	}

}
