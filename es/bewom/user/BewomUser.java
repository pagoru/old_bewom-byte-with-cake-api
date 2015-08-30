package es.bewom.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.scoreboard.Scoreboard;
import org.cakepowered.api.scoreboard.Team;
import org.cakepowered.api.util.Title;
import org.cakepowered.api.util.text.TextFormating;
import org.cakepowered.api.util.text.TextModifier;

import es.bewom.BewomByte;
import es.bewom.p.House;
import es.bewom.util.Ran;
import es.bewom.util.mysql.MySQL;

/**
 * 
 * @author McMacker4
 *
 */
public class BewomUser {
	
	public static final String PERM_ADMIN = "admin";
	public static final String PERM_VIP = "vip";
	public static final String PERM_USER = "miembro";
	
	public static final int PERM_LEVEL_ADMIN = 3;
	public static final int PERM_LEVEL_VIP = 2;
	public static final int PERM_LEVEL_USER = 1;
	
	private static BewomByte plugin;
	
	public static MySQL m = new MySQL();
	
	static HashMap<UUID, BewomUser> onlineUsers = new HashMap<UUID, BewomUser>();
	
	private boolean logout = false;
	
	private Player player;
	private UUID uuid;
	
	private boolean isAfk;
	private int lastMove;
	
	private int permissionLevel;
	
	public int updateState = 300;
	
	private String registerLink = "http://bewom.es/crear/";
	private boolean getRegisterLink = false;
	public int registration = -1;
	public boolean registerLinkSended = false;
	public long registerDateVariable = 5;
	public long loginDate;
	
	public boolean registerPandY = true;
	public float registerPitch = 0.0f;
	public float registerYaw = 0.0f;
	
	public String lastMessage;
	
	public House houseToBuyConfirm;
	private boolean invisible = false;
	
	private Player mpPlayer = null;
	
	private String prefix = "";
	
	/**
	 * Constructor. Creates a {@link BewomUser} from a player.
	 * @param player to create the {@link BewomUser} from.
	 */
	public BewomUser(Player player) {
		
		this.loginDate = new Date().getTime();
		this.player = player;
		this.uuid = player.getUniqueID();
		lastMove = plugin.getGame().getServer().getRunningTimeTicks();
		registration = checkWebsiteRegistration(); //WebRegistration.VALID
		permissionLevel = checkPermissionLevel(); //PERM_LEVEL_USER
		
		String hash = m.executeQuery("SELECT * FROM `crear` WHERE `uuid`='" + player.getUniqueID() + "'", "hash").get(0);
		if(!hash.equals("")){
			registerLink += hash;
			getRegisterLink = true;
		}
	}
	
	public void setPrefix(String p) {
		this.prefix = p;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public Player getMpPlayer() {
		return mpPlayer;
	}

	public void setMpPlayer(Player mpPlayer) {
		this.mpPlayer = mpPlayer;
	}
	
	public void leaveAllTeams(){
		
		List<Team> teams = player.getWorld().getScoreboard().getTeams();
		for (Team t : teams) {
			if(t.getPlayers().contains(player)){
				t.removePlayer(player);
			}
		}
		
	}
	
	public void updatePermissions(){
		
		String perm = m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid + "'", "type").get(0);
		String date = m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid + "'", "date_type").get(0);
		String days = m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid + "'", "days_type").get(0);
		
		if(!perm.equals("")){
			
			if(!days.equals("0")){
				
				Date d = new Date();
				Timestamp timestamp = new Timestamp(d.getTime());
				
				int day = Integer.parseInt(days) * 86400;
				
				Timestamp time = Timestamp.valueOf(date);
				
				long a1 = timestamp.getTime()/1000;
				long a2 = time.getTime()/1000;
				
				long f = a1 - a2;
				long ff = day - f;
				
				if(ff <= 0){
					m.executeQuery("UPDATE `users` SET `type`='miembro',`date_type`='" + timestamp.toString() + "',`days_type`='0' WHERE `uuid`='" + player.getUniqueID().toString() + "'", null);
					System.out.println(perm);
					if(perm.equals(PERM_VIP)){
						player.sendTitle(new Title(TextFormating.DARK_AQUA+"Se te ha acabado el vip!", TextFormating.WHITE+"Siempre puedes volver a donar... :)", 120, 0, 60));
					}
				}
			}
			
			switch(perm) {
			case PERM_ADMIN:
				if(permissionLevel != PERM_LEVEL_ADMIN){
					setPermissionLevel(3);
					Team team = player.getWorld().getScoreboard().getTeam(PERM_ADMIN);
					if(team == null) {
						Scoreboard score = player.getWorld().getScoreboard();
						score.addTeam(PERM_ADMIN).setColor(TextFormating.DARK_RED);
						team = player.getWorld().getScoreboard().getTeam(PERM_ADMIN);
					}
					
					if(!team.getPlayers().contains(player)){
						for(Team t : player.getWorld().getScoreboard().getTeams()) {
							t.removePlayer(player);
						}
						team.addPlayer(player);
					}
					BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "/op " + player.getName());
					player.setGameMode(1);
				}
				break;
			case PERM_VIP:
				if(permissionLevel != PERM_LEVEL_VIP){
					setPermissionLevel(2);
					Team teamVip = player.getWorld().getScoreboard().getTeam(PERM_VIP);
					if(teamVip == null) {
						Scoreboard score = player.getWorld().getScoreboard();
						score.addTeam(PERM_VIP).setColor(TextFormating.DARK_AQUA);
						teamVip = player.getWorld().getScoreboard().getTeam(PERM_VIP);
					}
					
					if(!teamVip.getPlayers().contains(player)){
						for(Team t : player.getWorld().getScoreboard().getTeams()) {
							t.removePlayer(player);
						}
						teamVip.addPlayer(player);
					}
					BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "/deop " + player.getName());
					player.setGameMode(2);
				}
				break;
			case PERM_USER:
				if(permissionLevel != PERM_LEVEL_USER){
					setPermissionLevel(1);
					Team teamUser = player.getWorld().getScoreboard().getTeam(PERM_USER);
					if(teamUser == null) {
						Scoreboard score = player.getWorld().getScoreboard();
						score.addTeam(PERM_USER).setColor(TextFormating.GRAY);
						teamUser = player.getWorld().getScoreboard().getTeam(PERM_USER);
					}
					
					if(!teamUser.getPlayers().contains(player)){
						for(Team t : player.getWorld().getScoreboard().getTeams()) {
							t.removePlayer(player);
						}
						teamUser.addPlayer(player);
					}
					BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "/deop " + player.getName());
					player.setGameMode(2);
				}
				break;
			}

		} else {
			
			setPermissionLevel(1);
			Team teamUser = player.getWorld().getScoreboard().getTeam(PERM_USER);
			if(teamUser != null){			
				if(!teamUser.getPlayers().contains(player)){

					for(Team team : player.getWorld().getScoreboard().getTeams()) {
						team.removePlayer(player);
					}
					teamUser.addPlayer(player);
				}
			}
		}
	}

		/**
	 * Returns the {@link Player} attached to this {@link BewomUser}.
	 * @return {@link Player}
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the name of the {@link Player} attached to this {@link BewomUser}.
	 * @return {@link String}
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Returns whether this {@link BewomUser} is Afk or not.
	 * @return boolean
	 */
	public boolean isAfk() {
		return isAfk;
	}
	
	/**
	 * Set the last time the player moved.
	 * @param time in ticks.
	 */
	public void setLastMove(int time) {
		this.lastMove = time;
	}
	
	/**
	 * Get the last time the player moved.
	 * @return Last time player moved.
	 */
	public int getLastMove() {
		return lastMove;
	}
	
	/**
	 * Returns the {@link WebRegistration} value of the player.
	 * @return Registration value.
	 */
	public int getRegistration() {
		return registration;
	}
	
	public void createHashFirstTime(){
		if(!getRegisterLink){
			Ran r = new Ran();
			String h = r.next(32);
			
			registerLink += h;
			
			m.executeQuery("INSERT INTO `crear`(`uuid`, `user`, `hash`) VALUES ('" + player.getUniqueID() + "', '" + player.getUserName() + "', '" + h + "')", null);
			getRegisterLink = true;
		}
	}
	
	/**
	 * Runs when player joins.
	 * Grabs {@link WebRegistration} value from web server.
	 * @return 
	 */
	public int checkWebsiteRegistration() {
		//TODO: Check Registration in the database.
				
		String perm = m.executeQuery("SELECT * FROM `crear` WHERE `uuid`='" + player.getUniqueID() + "'", "valid").get(0);
		if(perm.equals("0")){
			String perm2 = m.executeQuery("SELECT * FROM `users_info` WHERE `uuid`='" + player.getUniqueID() + "'", "active").get(0);
			if(perm2.equals("1")){
				player.setGameMode(2);
				return WebRegistration.VALID;
			} else {
				return WebRegistration.NOT_VALID;
			}
		}
		
		return WebRegistration.NOT_REGISTERED;
	}
	
	private int checkPermissionLevel() {
		updatePermissions();
		return this.permissionLevel;
	}
	
	public boolean isAdmin(){
		if(this.permissionLevel == PERM_LEVEL_ADMIN){
			return true;
		}
		return false;
	}
	
	public boolean isVip(){
		if(this.permissionLevel == PERM_LEVEL_VIP){
			return true;
		}
		return false;
	}
	
	public boolean isUser(){
		if(this.permissionLevel == PERM_LEVEL_USER){
			return true;
		}
		return false;
	}
	
	public String getPermissionType(){
		
		switch (permissionLevel) {
		case PERM_LEVEL_ADMIN:
			return PERM_ADMIN;
		case PERM_LEVEL_VIP:
			return PERM_VIP;
		case PERM_LEVEL_USER:
			return PERM_USER;
		}
		
		return null;
		
	}
	
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}
	public boolean isInvisible() {
		return invisible;
	}
	
	public int getPermissionLevel() {
		return permissionLevel;
	}
	
	public String setPermissionLevel(int level) {
		if(level < PERM_LEVEL_USER || level > PERM_LEVEL_ADMIN) {
			return "Este grupo no existe.";
		}
		this.permissionLevel = level;
		//TODO: Save to database.	
		m.executeQuery("UPDATE `users` SET `type`='" + getPermissionType() + "' WHERE `uuid`='" + player.getUniqueID() + "'", null);	
		return null;
	}
	
	/**
	 * Gets a {@link BewomUser} that is online.
	 * @param name of the player.
	 * @return The {@link BewomUser} specified.
	 */
	public static BewomUser getUser(UUID uuid) {
		return onlineUsers.get(uuid);
	}
	
	/**
	 * Gets a {@link BewomUser} that is online.
	 * @param player
	 * @return The {@link BewomUser} or null if not found.
	 */
	public static BewomUser getUser(Player player) {
		return getUser(player.getUniqueID());
	}
	
	/**
	 * Adds the {@link BewomUser} to the onlineUsers {@link HashMap}
	 * @param user
	 */
	public static void addUser(BewomUser user) {
		onlineUsers.put(user.getUUID(), user);
	}
	
	/**
	 * Removes user from the online users list.
	 */
	public void remove() {
		onlineUsers.remove(this.uuid);
	}
	public static void remove(BewomUser user) {
		onlineUsers.remove(user.getUUID());
	}
	
	public static void remove(UUID uuid) {
		onlineUsers.remove(uuid);
	}
	
	/**
	 * DO NOT USE.
	 * This function sets the current plugin.
	 * This should only be used from BewomByte.class and just once when server starts.
	 * @param game
	 */
	public static void setGame(BewomByte plugin) {
		BewomUser.plugin = plugin;
	}

	public String getRegisterLink() {
		return registerLink;
	}

	public void setRegisterLink(String registerLink) {
		this.registerLink = registerLink;
	}

	public boolean isLogout() {
		return this.logout;
	}

	public void setLogout(boolean logout) {
		this.logout = logout;
	}

	public void updateRegistration() {
		
		if (getRegistration() == WebRegistration.VALID) {
			player.sendTitle(new Title(TextFormating.DARK_AQUA+"Bienvenid@!", TextFormating.WHITE+"Hazte con todos...", 120, 0, 60));
			updatePermissions();
			
		} else if (getRegistration() == WebRegistration.NOT_VALID) {
			player.sendTitle(new Title(TextFormating.DARK_RED+"Verifica tu correo!", TextFormating.WHITE+"Si no encuentras el correo, busca en spam...", 100, 0, 0));
			
			leaveAllTeams();
			player.setGameMode(3);
			
		} else if (getRegistration() == WebRegistration.NOT_REGISTERED) {
			createHashFirstTime();
			player.sendTitle(new Title(TextFormating.DARK_RED+"Porfavor, registrate!", TextFormating.WHITE+"Haz click en el link del chat...", 100, 0, 0));
			if(!registerLinkSended){
				player.sendLink(TextFormating.DARK_AQUA + getRegisterLink());
				registerLinkSended = true;
			}

			leaveAllTeams();
			player.setGameMode(3);
		} else if (getRegistration() == WebRegistration.BANNED) {
			updatePermissions();
			
			player.setGameMode(3);
		}
		
	}
	
	public static int getMoney(UUID uuid2){
		return Integer.parseInt(m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid2 + "'", "money").get(0));
	}
	
	public static int getMoney(String name){
		return Integer.parseInt(m.executeQuery("SELECT * FROM `users` WHERE `user`='" + name + "'", "money").get(0));
	}
	
	public static void addMoney(UUID uuid, int a){
		int money = a + getMoney(uuid);
		m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
	}
	
	public static boolean substractMoney(UUID uuid, int a){
		if(getMoney(uuid) >= a){
			int money = Math.abs(a - getMoney(uuid));
			m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
			return true;
		}
		return false;
		
	}
	
	public int getMoney(){
		return Integer.parseInt(m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid + "'", "money").get(0));
	}
	
	public void addMoney(int a){
		int money = a + getMoney();
		m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
	}
	
	public boolean canSubstractMoney(int a){
		if(getMoney() >= a){
			return true;
		}
		return false;
	}
	
	public void substractMoney(int a){
		int money = 0;
		if(getMoney() >= a){
			money = getMoney() - a;
		}		
		m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
	}
	
	public static List<String> getPlayersUUIDRegistered(){
		return m.executeQuery("SELECT * FROM `users`", "uuid");
	}
	
	public static List<Player> getPlayersRegistered(){
		List<String> uuids = m.executeQuery("SELECT * FROM `users`", "uuid");
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < uuids.size(); i++) {
			players.add(BewomByte.game.getServer().getPlayer(uuids.get(i)));
		}
		return players;
	}
	
	public static List<String> getPlayersUsernameRegistered(){
		return m.executeQuery("SELECT * FROM `users`", "user");
	}
	
	public static String getUUIDName(String name){
		List<String> uuid = m.executeQuery("SELECT * FROM `users` WHERE `user`='" + name + "'", "uuid");
		return uuid.get(0);
	}
	
}
