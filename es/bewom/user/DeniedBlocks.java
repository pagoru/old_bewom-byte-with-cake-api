package es.bewom.user;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.block.Block;
import org.cakepowered.api.block.Blocks;
import org.cakepowered.api.event.BlockPlaceEvent;
import org.cakepowered.api.event.PlayerInteractEvent;

import es.bewom.util.Dimensions;

public class DeniedBlocks {
	
	public static final Block[] DENIED = {
			Blocks.PISTON,
			Blocks.PISTON_EXTENSION,
			Blocks.PISTON_HEAD,
			Blocks.STICKY_PISTON,
			Blocks.DISPENSER,
			Blocks.DROPPER,
			Blocks.TNT,
			Blocks.SAPLING
			
	};
	
	public static final String[] DENIED_O = {
			"elevator",
			"bike"
	};
	
	public static void on(Game game, BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		Block b = event.getBlock();
		
		System.out.println(player.getCurrentItem().getUnlocalizedName());
		
		if(b != null){
			
			for (int i = 0; i < DENIED.length; i++) {
				
				if(b.getUnlocalizedName().equals(DENIED[i].getUnlocalizedName())){
					event.setEventCanceled(true);
					break;
				}
				
			}
			
		}
		
	}
	
	public static void onAdminsToo(Game game, PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		if(player.getCurrentItem() != null){
			if(player.getDimensionID() == Dimensions.INTERIORES){
				for (int i = 0; i < DENIED_O.length; i++) {
					
					if(player.getCurrentItem().getUnlocalizedName().contains(DENIED_O[i])){
						event.setEventCanceled(true);
						break;
					}
					
				}
			}
		}
		
	}

}
