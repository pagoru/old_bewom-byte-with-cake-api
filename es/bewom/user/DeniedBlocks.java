package es.bewom.user;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.block.Block;
import org.cakepowered.api.block.Blocks;
import org.cakepowered.api.event.BlockPlaceEvent;

public class DeniedBlocks {
	
	public static final Block[] DENIED = {
			Blocks.PISTON,
			Blocks.PISTON_EXTENSION,
			Blocks.PISTON_HEAD,
			Blocks.STICKY_PISTON,
			Blocks.DISPENSER,
			Blocks.DROPPER,
			Blocks.TNT
			
	};
	
	public static void on(Game game, BlockPlaceEvent event){
		
		Block b = event.getBlock();
		
		if(b != null){
			
			for (int i = 0; i < DENIED.length; i++) {
				
				if(b.getUnlocalizedName().equals(DENIED[i].getUnlocalizedName())){
					
					event.setEventCanceled(true);
					break;
					
				}
				
			}
			
		}
		
	}

}
