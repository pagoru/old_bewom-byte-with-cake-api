package es.bewom;

import org.cakepowered.api.base.CakePlugin;
import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Log;
import org.cakepowered.api.event.EventSuscribe;
import org.cakepowered.api.event.InitializationEvent;
import org.cakepowered.api.event.ServerStatingEvent;
import org.cakepowered.api.event.ServerStoppingEvent;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.commands.Commands;
import es.bewom.economy.Houses;
import es.bewom.imc.BewomMessageListener;
import es.bewom.p.P;
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
	
	public static BewomByte INSTANCE;
	/**
	 * Runs when the plugin is initializing.
	 * @param e the {@link InitializationEvent}.
	 */
	@EventSuscribe
	public void onInitialization(InitializationEvent e) {
		game = e.getGame();
		
		log = e.getLogger("Bewom Byte");
		
		log.debug("BewomByte Loading.");
		
		INSTANCE = this;
		
		log.debug("Loading BewomByte commands."); 
		
		Commands commands = new Commands();
		commands.registerAll();
		
		log.debug("Loading BewomByte events.");
		
		BewomUser.setGame(this);
		TPManager.init(this);
		CentroManager.init(this);
		P.init(this);
		Houses.init(this);
		
		game.getEventRegistry().registerEventListener(new UserEventsHandler(game));
		game.getWorldManager().createFlatWorld(2);
		game.getMessageDispatcher().register(BewomMessageListener.INSTANCE, this);
	}
	
	@EventSuscribe
	public void onServerOpen(ServerStatingEvent event){
		
		game.getWorldManager().createFlatWorld(2);
		
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
