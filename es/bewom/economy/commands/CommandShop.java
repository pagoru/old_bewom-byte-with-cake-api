package es.bewom.economy.commands;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.economy.Shop;
import es.bewom.economy.Shops;
import es.bewom.p.Door;
import es.bewom.p.P;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandShop extends CommandBase {
	
	public CommandShop() {
		super("tienda");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player player = commandSender.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		if(BewomUser.getUser(player).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) return;
		
		if(user.isAdmin()){
			
			if(args.length == 4){
				if(args[3] != null){
					player.sendMessage("Selecciona la tienda.");
					if(player.getCurrentItem() != null){
						String nameShop = args[2].replace("_", " ");
						String itemName = BewomByte.game.getFinder().getCompleteName(player.getCurrentItem().getItem());
						Shops.shops.add(new Shop(player, Integer.parseInt(args[0]), Integer.parseInt(args[1]), itemName, nameShop, Integer.parseInt(args[3])));
					} else {
						player.sendMessage(TextFormating.RED + "No tienes ningun item en la mano.");
					}
					} else {
					player.sendMessage(TextFormating.RED + "/tienda <precio venta> <precio compra> <nombre con _, no espacios> <cantidad>");
				}
			} else {
				player.sendMessage(TextFormating.RED + "/tienda <precio venta> <precio compra> <nombre con _, no espacios> <cantidad>");
			}
			
			
		} else {
			
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			
		}
		
	}

}
