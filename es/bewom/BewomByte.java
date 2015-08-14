package es.bewom;

import org.cakepowered.api.base.CakePlugin;
import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Log;
import org.cakepowered.api.event.EventSuscribe;
import org.cakepowered.api.event.InitializationEvent;
import org.cakepowered.api.event.ServerStoppingEvent;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.commands.Commands;
import es.bewom.spawn.SpawnManager;
import es.bewom.teleport.TPManager;
import es.bewom.user.BewomUser;
import es.bewom.user.UserEventsHandler;

/**
 * 
 * Main plugin class. Here is where the magic happens.
 * @author Pagoru & McMacker4 & Cout970
 *
 */

@CakePlugin(id="bewomByte", name="bewom byte", version = "0.1")

public class BewomByte {
	
	public static Game game;
	
	public static Log log;
	
	/**
	 * Runs when the plugin is initializing.
	 * @param e the {@link InitializationEvent}.
	 */
	@EventSuscribe
	public void onInitialization(InitializationEvent e) {
		game = e.getGame();
		
		log = e.getLogger("Bewom Byte");
		
		log.debug("BewomByte Loading.");
		
		
		
		log.debug("Loading BewomByte commands."); 
		
		Commands commands = new Commands();
		commands.registerAll();
		
		log.debug("Loading BewomByte events.");
		
		BewomUser.setGame(this);
		TPManager.init(this);
		SpawnManager.load();
		CentroManager.init(this);
		
		game.getEventRegistry().registerEventListener(new UserEventsHandler(game));
	}
	
	/**
	 * Runs when the server is stopping.
	 * @param e the event triggered.
	 */
	@EventSuscribe
	public void onServerClosing(ServerStoppingEvent e) {
		CentroManager.save();
	}
	
	/**
	 * Returns the current {@link Game}.
	 * @return The current {@link Game}.
	 */
	public Game getGame() {
		return game;
	}

}
