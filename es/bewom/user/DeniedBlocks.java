package es.bewom.user;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.event.BlockBreakEvent;
import org.cakepowered.api.event.BlockPlaceEvent;
import org.cakepowered.api.event.EntityAttackedEvent;
import org.cakepowered.api.event.PlayerInteractEntityEvent;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.inventory.PlayerInventory;

import es.bewom.BewomByte;
import es.bewom.util.Dimensions;

public class DeniedBlocks {
	
	public static final String[] DENIED_PLACE = {
			"piston",
			"dispenser",
			"dropper",
			"tnt",
			"sapling",
			"elevator"
			
	};
	
	public static final String[] DENIED_INTERACT = {
			"potion",
			"Sunstone",
			"Leafstone",
			"enderpearl"
	};
	
	public static final String[] DENIED_INTERACT_INTERIOR = {
			"Bike.name",
			"Pixelmon.name"
	};
	
	public static final String[] DENIED_PLACE_INTERIOR = {
			"bike",
			"Ranch"
	};
	
	public static final String[] ALLOWED_INTERACT_EXTERIOR = {
			"apriconr tree",
			"pc",
			"trademachine",
			"mechanicalanvil",
			"anvil",
			"pokechest",
			"ultrachest",
			"masterchest",
			"enderchest",
			"healer",
			"PokeGift",
			"bike",
			"atm",
			"shop",
			"poste",
			"wallet",
			"Bike.name",
			"entity.pixelmon",
			"itemFinder"
			
	};
	
	public static final String[] ALLOWED_PLACE_EXTERIOR = {
			"wallet",
			"bike"
			
	};
	
	public static void on(Game game, BlockPlaceEvent e){
		
		Player p = e.getPlayer();
		int d = p.getDimensionID();
		if(BewomByte.DEBUG){
			if(p.getCurrentItem() != null){
				System.out.println("BlockPlace: " + p.getCurrentItem().getUnlocalizedName());
			}
		}
		
		if(d == Dimensions.EXTERIORES){
			
			if(p.getCurrentItem() != null){
				boolean x = true;
				for (int i = 0; i < ALLOWED_PLACE_EXTERIOR.length; i++) {
					
					if(p.getCurrentItem().getUnlocalizedName().contains(ALLOWED_PLACE_EXTERIOR[i])){
						
						x = false;
						break;
						
					}
					
				}
				if(x){
					e.setEventCanceled(true);
					return;
				}
			}
			
		} else if(d == Dimensions.INTERIORES){
			
			if(p.getCurrentItem() != null){
				for (int i = 0; i < DENIED_PLACE_INTERIOR.length; i++) {
					
					if(p.getCurrentItem().getUnlocalizedName().contains(DENIED_PLACE_INTERIOR[i])){
						
						e.setEventCanceled(true);
						break;
						
					}
					
				}
			}
			
		}
		if(p.getCurrentItem() != null){
			for (int i = 0; i < DENIED_PLACE.length; i++) {
				
				if(p.getCurrentItem().getUnlocalizedName().contains(DENIED_PLACE[i])){
				
					e.setEventCanceled(true);
					break;
					
				}
			}
		}
		
		onNoItemInHand(p);
		
	}
	
	public static void on(Game game, PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		int d = p.getDimensionID();
		if(BewomByte.DEBUG){
			System.out.println("PlayerInteractEvent: " + e.getInteractBlock().getUnlocalizedName());
			if(p.getCurrentItem() != null){
				System.out.println("PlayerInteractEvent: " + p.getCurrentItem().getUnlocalizedName());
			}
		}
		
		if(d == Dimensions.EXTERIORES){
			
			if(p.getCurrentItem() != null){
				boolean x = true;
				for (int i = 0; i < ALLOWED_INTERACT_EXTERIOR.length; i++) {
					
					if(p.getCurrentItem().getUnlocalizedName().contains(ALLOWED_INTERACT_EXTERIOR[i])){
						
						x = false;
						break;
						
					}
					
				}
				for (int i = 0; i < ALLOWED_INTERACT_EXTERIOR.length; i++) {
					
					if(e.getInteractBlock().getUnlocalizedName().contains(ALLOWED_INTERACT_EXTERIOR[i])){
						
						x = false;
						break;
						
					}
					
				}
				if(x){
					e.setEventCanceled(true);
					return;
				}
			}
			
		} else if(d == Dimensions.INTERIORES){
			
			if(p.getCurrentItem() != null){
				for (int i = 0; i < DENIED_PLACE_INTERIOR.length; i++) {
					
					if(p.getCurrentItem().getUnlocalizedName().contains(DENIED_PLACE_INTERIOR[i])){
						
						e.setEventCanceled(true);
						break;
						
					}
					
				}
			}
			
		}
		
		if(p.getCurrentItem() != null){
			for (int i = 0; i < DENIED_INTERACT.length; i++) {
				
				if(p.getCurrentItem().getUnlocalizedName().contains(DENIED_INTERACT[i])){
					
					e.setEventCanceled(true);
					break;
					
				}
				
			}
		}
		
		onNoItemInHand(p);
		
	}

	public static void on(Game game, PlayerInteractEntityEvent e) {

		Player p = e.getPlayer();
		int d = p.getDimensionID();
		if(BewomByte.DEBUG){
			System.out.println("PlayerInteractEntityEvent: " + e.getEntity().getName());
		}
		
		if(d == Dimensions.EXTERIORES){
			
			boolean x = true;
			for (int i = 0; i < ALLOWED_INTERACT_EXTERIOR.length; i++) {
				
				if(e.getEntity().getName().contains(ALLOWED_INTERACT_EXTERIOR[i])){
					
					x = false;
					break;
					
				}
				
			}
			if(x){
				e.setEventCanceled(true);
				return;
			}
			
		} else if(d == Dimensions.INTERIORES){
			
			for (int i = 0; i < DENIED_INTERACT_INTERIOR.length; i++) {
				
				if(e.getEntity().getName().contains(DENIED_INTERACT_INTERIOR[i])){
					
					e.setEventCanceled(true);
					break;
					
				}
				
			}
			
			if(p.getLocation().getZ() > Dimensions.LIMITE_INTERIORES){
				
				boolean x = true;
				for (int i = 0; i < ALLOWED_INTERACT_EXTERIOR.length; i++) {
					
					if(e.getEntity().getName().contains(ALLOWED_INTERACT_EXTERIOR[i])){
						
						x = false;
						break;
						
					}
					
				}
				if(x){
					e.setEventCanceled(true);
					return;
				}
				
			}
			
		}
		
		onNoItemInHand(p);
		
	}

	public static void on(Game game, EntityAttackedEvent e) {

		Player p = e.getPlayer();
		int d = p.getDimensionID();
		if(BewomByte.DEBUG){
			System.out.println("PlayerAttackedEvent: " + e.getEntity().getName());
		}
		
		if(d == Dimensions.EXTERIORES){
			
			for (int i = 0; i < ALLOWED_INTERACT_EXTERIOR.length; i++) {
				
				if(e.getEntity().getName().contains(ALLOWED_INTERACT_EXTERIOR[i])){
					
					return;
					
				}
				
			}
			e.setEventCanceled(true);
			
		} else if(d == Dimensions.INTERIORES){
			
			if(p.getLocation().getZ() > Dimensions.LIMITE_INTERIORES){
				
				for (int i = 0; i < ALLOWED_INTERACT_EXTERIOR.length; i++) {
					
					if(e.getEntity().getName().contains(ALLOWED_INTERACT_EXTERIOR[i])){
						
						return;
						
					}
					
				}
				e.setEventCanceled(true);
				
			}
			
		}
		
		onNoItemInHand(p);
		
	}

	public static void on(Game game, BlockBreakEvent e) {

		Player p = e.getPlayer();
		int d = p.getDimensionID();
		
		if(d == Dimensions.EXTERIORES){
			
			e.setEventCanceled(true);
			
		} else if(d == Dimensions.INTERIORES){
			
		}
		
		onNoItemInHand(p);
		
	}
	
	public static void onNoItemInHand(Player p){
		PlayerInventory i = p.getPlayerInventory();
		for (int j = 0; j < i.getSize(); j++) {
			if(i.getStackInSlot(j) != null){
				if(i.getStackInSlot(j).getStackSize() <= 0){
					i.setStackInSlot(j, null);
				}
			}
		}
	}

}
