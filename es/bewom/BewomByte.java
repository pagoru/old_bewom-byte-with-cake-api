package es.bewom;

import org.cakepowered.api.base.CakePlugin;
import org.cakepowered.api.base.Game;
import org.cakepowered.api.base.Log;
import org.cakepowered.api.event.EventSuscribe;
import org.cakepowered.api.event.InitializationEvent;
import org.cakepowered.api.event.ServerStatingEvent;
import org.cakepowered.api.event.ServerStoppingEvent;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.chat.Chat;
import es.bewom.commands.Commands;
import es.bewom.imc.BewomMessageListener;
import es.bewom.p.Houses;
import es.bewom.p.P;
import es.bewom.p.Ranchs;
import es.bewom.teleport.Homes;
import es.bewom.teleport.TPManager;
import es.bewom.torneos.Torneo;
import es.bewom.user.BewomUser;
import es.bewom.util.mysql.MySQL;
/**
 * 
 * Main plugin class. Here is where the magic happens.
 * @author Pagoru & McMacker4 & Cout970
 *
 */

@CakePlugin(id="bewomByte", name="bewom byte", version = "0.1")

public class BewomByte {
	
	public static final boolean DEBUG = true;

	public static Game game;
	public static Log log;
	public static BewomByte INSTANCE;
	
	public static MySQL m = new MySQL();
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
		
		TPManager.init(this);
		CentroManager.init(this);
		P.init(this);
		Houses.init(this);
		Homes.init();
		Ranchs.init(this);
		Torneo.load();
		
		game.getEventRegistry().registerEventListener(new EventsHandler(game));
		game.getWorldManager().createFlatWorld(2);
		game.getWorldManager().createNormalWorld(3, 343822243);
		game.getMessageDispatcher().register(BewomMessageListener.INSTANCE, this);
		
	}
	
	@EventSuscribe
	public void onServerOpen(ServerStatingEvent event){
		Chat.sendMessage(null, null, "Server opened!");
	}
	
	@EventSuscribe
	public void onServerClosing(ServerStoppingEvent e) {
		CentroManager.save();
		
	}
	
	public Game getGame() {
		return game;
	}
}
