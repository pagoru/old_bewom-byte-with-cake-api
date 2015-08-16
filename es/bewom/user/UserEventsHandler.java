package es.bewom.user;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.event.BlockBreakEvent;
import org.cakepowered.api.event.BlockPlaceEvent;
import org.cakepowered.api.event.EntityAttackedEvent;
import org.cakepowered.api.event.EntitySpawnEvent;
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

import es.bewom.BewomByte;
import es.bewom.centrospokemon.CentroManager;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.chat.Chat;
import es.bewom.p.P;
import es.bewom.util.Dimensions;

public class UserEventsHandler {
	
	private Game game;
	
	public UserEventsHandler(Game game) {
		this.game = game;
	}

	/**
	 * Event triggered when a player joins the server.
	 * 
	 * @param event
	 */
	@EventSuscribe
	public void onUserJoin(PlayerJoinEvent event) {
				
		Player player = event.getPlayer();
		
		BewomUser user = new BewomUser(player);
		BewomUser.addUser(user);
		
		Chat.sendMessage(player, null, "//login");
		
		user.updateRegistration();
		
		CentroPokemon cp = CentroManager.getClosest(player.getLocation());
		if(cp != null) {
			player.setLocation(cp.getLocation());
		}
		
	}

	@EventSuscribe
	public void onUserChat(PlayerChatEvent event) {
		
		BewomUser b = BewomUser.getUser(event.getPlayer());
		
		if (b.getRegistration() == WebRegistration.VALID) {
			String message = "";
			String postName = Chat.getCleanText(event.getMessage());
			String name = event.getUsername();
			
			if(!postName.equals(b.lastMessage)){
				
				switch(b.getPermissionLevel()) {
				case BewomUser.PERM_LEVEL_USER:
					message = TextFormating.GRAY + "/" + name + TextFormating.WHITE + " < " + postName;
					break;
				case BewomUser.PERM_LEVEL_VIP:
					message = TextFormating.DARK_AQUA + "/" + name + TextFormating.WHITE + " < " + postName;
					break;
				case BewomUser.PERM_LEVEL_ADMIN:
					message = TextFormating.DARK_RED + "" + TextFormating.BOLD + "/" + name + TextFormating.WHITE + " < " + postName;
					break;
				}
				
				b.lastMessage = postName;
				Chat.sendMessage(event.getPlayer(), message, event.getMessage());
				
			}
		}
		event.setEventCanceled(true);
	}

	/**
	 * Event triggered when a player leaves the server.
	 * 
	 * @param event
	 */
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
		CentroPokemon cp = CentroManager.getClosest(player.getLocation());

		playerUpdateGameMode(player);
		if(cp != null) {
			player.setLocation(cp.getLocation());
		}
		System.out.println("Respawn!" + cp.getLocation().getX());
	}
	
	@EventSuscribe
	public void on(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		BewomUser user = BewomUser.getUser(player);

		playerUpdateGameMode(player);
		if (!user.isAdmin() && player.getDimensionID() == Dimensions.EXTERIORES) {
			
			if(isPixelmonInteraction(event)){
				event.setEventCanceled(true);
			}
		}
		
		P.on(game, event);
	}
	
	private boolean isPixelmonInteraction(PlayerInteractEvent e) {
		String n = e.getInteractBlock().getUnlocalizedName().substring(5, e.getInteractBlock().getUnlocalizedName().length());	
		if(n.equals("apricorn")
				|| n.endsWith("pc")
				|| n.endsWith("trademachine")
				|| n.endsWith("mechanicalanvil")
				|| n.endsWith("anvil")
				|| n.endsWith("pokechest")
				|| n.endsWith("ultrachest")
				|| n.endsWith("masterchest")
				|| n.endsWith("PokeGift")){
			return false;
		}
		return true;
	}

	@EventSuscribe //click derecho
	public void on(PlayerInteractEntityEvent event){
		
		Player player = event.getPlayer();
		BewomUser user = BewomUser.getUser(player);

		playerUpdateGameMode(player);
		if(event.getEntity().getName().contains("entity.pixelmon")&& player.getDimensionID() == Dimensions.INTERIORES){
			event.setEventCanceled(true);
		}		
		if (!user.isAdmin() && player.getDimensionID() == Dimensions.EXTERIORES) {
			event.setEventCanceled(true);
		}
		
	}
	
	@EventSuscribe
	public void on(EntityAttackedEvent event){
				
		Player player = event.getPlayer();
		BewomUser user = BewomUser.getUser(player);

		playerUpdateGameMode(player);
		if (!user.isAdmin() && player.getDimensionID() == Dimensions.EXTERIORES) {
			event.setEventCanceled(true);
		}
	}
	
	@EventSuscribe
	public void on(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		BewomUser user = BewomUser.getUser(player);

		playerUpdateGameMode(player);
		if(!user.isAdmin()){
			DeniedBlocks.on(game, event);
			if (player.getDimensionID() == Dimensions.EXTERIORES) {
				event.setEventCanceled(true);
			}
		}
		
	}
	
	@EventSuscribe
	public void on(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		BewomUser user = BewomUser.getUser(player);
		
		playerUpdateGameMode(player);		
		if (!user.isAdmin() && player.getDimensionID() == Dimensions.EXTERIORES) {
			event.setEventCanceled(true);
		}
		
		P.on(game, event);
		
	}
	
	public void playerUpdateGameMode(Player player){
		BewomUser u = BewomUser.getUser(player);
		if(u.getPermissionLevel() != u.PERM_LEVEL_ADMIN){
			if(player.getGameMode() != 3){
				if(player.getDimensionID() == Dimensions.EXTERIORES){
					if(player.getGameMode() != 2)
					player.setGameMode(2);
				} else {
					if(player.getGameMode() != 0)
					player.setGameMode(0);
				}
			}
		}
	}
	
	public HashMap<UUID, PreciseLocation> position_map = new HashMap<UUID, PreciseLocation>();
	
	@EventSuscribe
	public void tick(ServerUpdateEvent event){
		Collection<Player> players = event.getServer().getOnlinePlayers();
		for(Player p : players){
			BewomUser user = BewomUser.getUser(p);
			if (user.getRegistration() != WebRegistration.VALID) {
				onPlayerMove(user);
				Date date = new Date();
				long d = ((date.getTime()/1000) - (user.loginDate/1000));
				
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
				
				p.setLocation(new PreciseLocation(p.getDimensionID(), p.getLocation().getX(), p.getLocation().getY() + 5, p.getLocation().getZ(), user.registerYaw, user.registerPitch));
				if(d == user.registerDateVariable){
					user.registration = user.checkWebsiteRegistration();
					user.updateRegistration();
					user.registerDateVariable += 15;
					if(d > 180){
						p.kick(TextFormating.RED + "Has sido kickeado por inactividad durante el registro.");
					}
				}
			}
		}
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
