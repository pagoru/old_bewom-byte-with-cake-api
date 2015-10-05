package es.bewom;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.event.BlockBreakEvent;
import org.cakepowered.api.event.BlockPlaceEvent;
import org.cakepowered.api.event.EntityAttackedEvent;
import org.cakepowered.api.event.EventSuscribe;
import org.cakepowered.api.event.PlayerChatEvent;
import org.cakepowered.api.event.PlayerInteractEntityEvent;
import org.cakepowered.api.event.PlayerInteractEvent;
import org.cakepowered.api.event.PlayerJoinEvent;
import org.cakepowered.api.event.PlayerQuitEvent;
import org.cakepowered.api.event.PlayerRespawnEvent;
import org.cakepowered.api.event.ServerUpdateEvent;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.chat.Chat;
import es.bewom.p.House;
import es.bewom.p.Houses;
import es.bewom.p.P;
import es.bewom.p.Ranchs;
import es.bewom.texts.TextMessages;
import es.bewom.torneos.Torneos;
import es.bewom.user.AwayFromKeyboard;
import es.bewom.user.BewomUser;
import es.bewom.user.DeniedBlocks;
import es.bewom.user.PokemonCatcher;
import es.bewom.user.WebRegistration;
import es.bewom.util.CollectPlayersWeb;
import es.bewom.util.Dimensions;

public class EventsHandler {
	
	private Game game;
	
	public EventsHandler(Game game) {
		this.game = game;
	}

	@EventSuscribe
	public void onUserJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();		
		BewomUser user = new BewomUser(player);	
		if(BewomByte.m.isBanned(player.getUniqueID().toString())){
			player.kick("");
		}
		BewomUser.addUser(user);
		
		PokemonCatcher.catchPokemons(player.getUniqueID().toString());
		
		Chat.sendMessage(player, null, "//login");
		
		user.updateRegistration();
		
		CentroPokemon cp = CentroManager.getClosest(player.getLocation());
		if(cp != null) {
			if(player.getLocation().getDimension() != Dimensions.EXTERIORES){
				cp = CentroManager.centros.get(0);
			}
			player.setLocation(cp.getLocation());
		}
		
	}

	@EventSuscribe
	public void onUserChat(PlayerChatEvent event) {
		event.setEventCanceled(true);
		
		BewomUser b = BewomUser.getUser(event.getPlayer());
		
		if (b.getRegistration() == WebRegistration.VALID) {
			String message = "";
			String postName = Chat.getCleanText(event.getMessage());
			String name = event.getUsername();
			
			if(!postName.equals(b.lastMessage) || b.getPermissionLevel() == BewomUser.PERM_LEVEL_ADMIN){
				
				switch(b.getPermissionLevel()) {
				case BewomUser.PERM_LEVEL_USER:
					message = b.getPrefix() + TextFormating.GRAY + "/" + name + TextFormating.WHITE + " < " + postName;
					break;
				case BewomUser.PERM_LEVEL_VIP:
					message = b.getPrefix() + TextFormating.DARK_AQUA + "/" + name + TextFormating.WHITE + " < " + postName;
					break;
				case BewomUser.PERM_LEVEL_ADMIN:
					message = b.getPrefix() + TextFormating.DARK_RED + "" + TextFormating.BOLD + "/" + name + TextFormating.WHITE + " < " + postName;
					break;
				}
				
				b.lastMessage = postName;
				Chat.sendMessage(event.getPlayer(), message, event.getMessage());
				
			}
		}
	}

	@EventSuscribe
	public void onUserQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueID();
		BewomUser u = BewomUser.getUser(uuid);
		
		if(u != null){
			
			if(!u.isLogout()){
				
				Chat.sendMessage(player, null, "//logout");
				u.setLogout(true);
				u.remove();
				
			}
			
		}
		
		if(position_map.containsKey(uuid)){
			position_map.remove(uuid);
		}
	}

	@EventSuscribe
	public void onUserRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		playerUpdateGameMode(player);
		
		if(CentroManager.centros.size() > 0){
			CentroPokemon cp = CentroManager.centros.get(0);
			if(cp != null) {
				player.setLocation(cp.getLocation());
			}
		}
		return;
	}
	
	@EventSuscribe
	public void on(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		playerUpdateGameMode(player);
		
		if(!player.isOP()){
			DeniedBlocks.on(game, event);
		}
		
		Ranchs.on(event);
		Houses.on(game, event);
		P.on(game, event);
		AwayFromKeyboard.on(event);
	}

	@EventSuscribe //click derecho
	public void on(PlayerInteractEntityEvent event){
		
		Player player = event.getPlayer();
		
		playerUpdateGameMode(player);
		
		if(!player.isOP()){
			DeniedBlocks.on(game, event);
		}
		
	}
	
	@EventSuscribe
	public void on(EntityAttackedEvent event){
				
		Player player = event.getPlayer();
		
		if(!player.isOP()){
			DeniedBlocks.on(game, event);
		}
		
	}
	
	@EventSuscribe
	public void on(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		
		playerUpdateGameMode(player);
				
		if(!player.isOP()){
			DeniedBlocks.on(game, event);
		}
		
	}
	
	@EventSuscribe
	public void on(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		
		playerUpdateGameMode(player);		
		
		if(!player.isOP()){
			DeniedBlocks.on(game, event);
		}
		
		Ranchs.on(event);
		P.on(game, event);
		
	}
	
	public void playerUpdateGameMode(Player player){
		BewomUser u = BewomUser.getUser(player);
		if(u.getPermissionLevel() != BewomUser.PERM_LEVEL_ADMIN){
			if(player.getGameMode() != 3){
				if(!u.isAdmin()){
					if ((player.getDimensionID() == Dimensions.EXTERIORES
							|| (player.getDimensionID() == Dimensions.INTERIORES && player.getLocation().getZ() > Dimensions.LIMITE_INTERIORES))) {
						if(player.getGameMode() != 2){
							player.setGameMode(2);
						}
					} else {
						if(player.getGameMode() != 0){
							player.setGameMode(0);
						}
					}
				}
			}
		}
	}
	
	public HashMap<UUID, PreciseLocation> position_map = new HashMap<UUID, PreciseLocation>();
	
	@EventSuscribe
	public void tick(ServerUpdateEvent event){
		Date date = new Date();
		Collection<Player> players = event.getServer().getOnlinePlayers();
		if(!BewomByte.DEBUG){
			CollectPlayersWeb.on(players, date);
		}
		
		Chat.save();
		backup(date);
		
		Torneos.fuegos(date);
		for(Player p : players){
//			if(!p.isOP()){
//				DeniedBlocks.on(p);
//			}
			BewomUser user = BewomUser.getUser(p);
			AwayFromKeyboard.AFK(user);
			long d = ((date.getTime()/1000) - (user.loginDate/1000));
			
			if (user.getRegistration() == WebRegistration.VALID) {
				if(d == (user.updateState + user.registerDateVariable)){
					user.updateState += 60;
					user.updatePermissions();
					PokemonCatcher.catchPokemons(p.getUniqueID().toString());
				}
				if(d == (user.timePlaying + user.registerDateVariable)){
					user.timePlaying += 60;
					CollectPlayersWeb.on(p);
				}
			}
			if (user.getRegistration() != WebRegistration.VALID) {
				onPlayerMove(user);
				
				if(user.registerPitch >= 19.0f && user.registerPitch <= 21.0f){
					user.registerPandY = true;
				} else if(user.registerPitch <= -19.0f && user.registerPitch >= -21.0f){
					user.registerPandY = false;
				}
				if(user.registerYaw >= 359 && user.registerYaw <= 360){
					user.registerYaw = 0;
				}
				
				user.registerYaw += 0.05f;
				if(user.registerPandY){
					user.registerPitch -= 0.02f;
				} else if(!user.registerPandY) {
					user.registerPitch += 0.02f;
				}
				
				p.setPitchAndYaw(user.registerPitch, user.registerYaw);
				if(d == user.registerDateVariable){
					user.registration = user.checkWebsiteRegistration();
					user.updateRegistration();
					user.registerDateVariable += 5;
					if(d > 180){
						p.kick(TextFormating.RED + "Has sido kickeado por inactividad durante el registro.");
					}
				}
			}
		}
	}
	
	private int second = 1200;
	private void backup(Date date) {
		
		if(second == 1200){
			
			if(date.getHours() == 3){
				
				if(date.getMinutes() >= 45 && date.getMinutes() < 55){
					Chat.sendMessage(null, TextMessages.BROADCAST + "El servidor se va a reiniciar en " + (55 - date.getMinutes()) + " minuto(s).", "/stop ");	
				} else if(date.getMinutes() == 55){
					Chat.sendMessage(null, TextMessages.BROADCAST + "El servidor se esta reiniciando...", "/stop ");	
					BewomByte.game.getServer().stop();
				}
				
			}
			
			second = 0;
		}
		second++;
		
	}

	private void onPlayerMove(BewomUser user) {
		if(position_map.containsKey(user.getUUID())){
			user.getPlayer().setLocation(position_map.get(user.getUUID()));
		}else{
			PreciseLocation loc = user.getPlayer().getLocation();
			position_map.put(user.getUUID(), loc);
		}
	}
}
