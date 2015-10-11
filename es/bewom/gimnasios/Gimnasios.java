package es.bewom.gimnasios;

import org.cakepowered.api.block.BlockState;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.event.PlayerInteractEvent.ClickAction;
import org.cakepowered.api.inventory.ItemStack;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.util.Direction;
import org.cakepowered.api.util.Vector3i;

public class Gimnasios {

	public static void on(PlayerInteractEvent event) {
		
		if (event.getAction() == ClickAction.RIGHT_CLICK_BLOCK) {
			if (event.getInteractBlock().getUnlocalizedName() == "tile.gymdoor") {
				ItemStack itemInHand = event.getPlayer().getCurrentItem();
				if(itemInHand.getUnlocalizedName() == "") {
					
					BlockState state = event.getWorld().getBlockState(event.getPosition());
					int meta = event.getInteractBlock().getMetadataFromState(state);
					
					NBTCompund nbt = itemInHand.getNBTCompound();
					if(!nbt.containsTag("puerta")) {
						//TODO: Error. El item no es una llave.
						return;
					}
					
					int puerta = nbt.getInteger("puerta");
					
					if(meta == puerta) {
						Direction oppositeFace = event.getFace().opposite();
						Vector3i posObjective = event.getPosition().add(oppositeFace);
						event.getPlayer().setPosition(posObjective.toVector3d());
					} else {
						//TODO: No se puede abrir la puerta.
						event.getPlayer().sendMessage("Esta llave no puede abrir esta puerta!");
					}
					
				}
			}
		}
		
	}

}
