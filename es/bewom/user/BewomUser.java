package es.bewom.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.scoreboard.Scoreboard;
import org.cakepowered.api.scoreboard.Team;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Title;
import org.cakepowered.api.util.text.TextFormating;
import org.cakepowered.api.util.text.TextModifier;

import es.bewom.BewomByte;
import es.bewom.p.House;
import es.bewom.util.Medallas;
import es.bewom.util.Ran;
import es.bewom.util.mysql.MySQL;

/**
 * 
 * @author McMacker4
 *
 */
public class BewomUser {
	
	public static final String PERM_ADMIN 		= "admin";
	public static final String PERM_MOD 			= "mod";
	public static final String PERM_VIP 			= "vip";
	public static final String PERM_USER 		= "miembro";
	
	public static final int PERM_LEVEL_ADMIN 	= 4;
	public static final int PERM_LEVEL_MOD	 	= 3;
	public static final int PERM_LEVEL_VIP 		= 2;
	public static final int PERM_LEVEL_USER 	= 1;
	
	static HashMap<UUID, BewomUser> onlineUsers = new HashMap<UUID, BewomUser>();
	
	private boolean logout = false;
	
	private Player player;
	private UUID uuid;
	
	private int permissionLevel;
	
	public int updateState = 60;
	
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
	
	private List<UUID> friends = new ArrayList<UUID>();
	private List<UUID> friendsPetitions = new ArrayList<UUID>();
	
	private float afkYaw;
	private float afkPitch;
	private long afk = 0;

	private PreciseLocation back;
	
	public long timePlaying = 60;
	
	public boolean isFirstUpdate = true;
	
	public long timeTpRandom;
	
	public BewomUser(Player player) {
		
		this.loginDate = new Date().getTime();
		this.player = player;
		this.uuid = player.getUniqueID();
		registration = checkWebsiteRegistration(); //WebRegistration.VALID
		permissionLevel = checkPermissionLevel(); //PERM_LEVEL_USER
		
		String hash = BewomByte.m.executeQuery("SELECT * FROM `crear` WHERE `uuid`='" + player.getUniqueID() + "'", "hash").get(0);
		if(!hash.equals("")){
			registerLink += hash;
			getRegisterLink = true;
		}
		
		this.friends = getAllFriends();
		this.friendsPetitions = getAllFriendsPetitions();
		
		Date d = new Date();
		Timestamp timestamp = new Timestamp(d.getTime());
				
		BewomByte.m.executeQuery("UPDATE `users` SET `user`='" + player.getUserName() + "' WHERE `uuid`='" + uuid + "'", null);
		BewomByte.m.executeQuery("UPDATE `users` SET `lastLogin`='" + timestamp.toString() + "' WHERE `uuid`='" + uuid + "'", null);
				
		setMedallas();
		
	}
	
	private void setMedallas(){
		
		Calendar c = Calendar.getInstance();
		
		int d = c.get(Calendar.DAY_OF_MONTH);
		int m = c.get(Calendar.MONTH);
		int y = c.get(Calendar.YEAR);
		
		int h = c.get(Calendar.HOUR_OF_DAY);
		
		System.out.println(d + "-" + m + "-" + y);
		
		if(h >= 3 && h <= 6){
			setMedalla(Medallas.TRASNOCHADOR);
		}
		
		if(y == 2015){
			if(d >= 30 && d <= 31 && m == Calendar.OCTOBER){
				setMedalla(Medallas.HALLOWEEN_2015);
			}
			if(d == 25 && m == Calendar.DECEMBER && y == 2015){
				setMedalla(Medallas.NAVIDAD_2015);
			}
		}
		
		if(y == 2016){
			if(d == 1 && m == Calendar.JANUARY){
				setMedalla(Medallas.NUEVO_AÑO_2016);
			}
			if(d == 6 && m == Calendar.JANUARY){
				setMedalla(Medallas.REYES_2016);
			}
			if(d == 14 && m == Calendar.FEBRUARY){
				setMedalla(Medallas.ST_VALENTIN_2016);
			}
		}
		
	}
	
	public void setMedalla(String m){
		
		List<String> medallas = BewomByte.m.executeQuery("SELECT * FROM `medallas` WHERE `uuid`='" + player.getUniqueID() + "'", "medalla");
		
		if(!medallas.contains(m)){
			
			BewomByte.m.executeQuery("INSERT INTO `medallas`(`medalla`, `uuid`) VALUES ('" + m + "','" + player.getUniqueID() + "')", null);
			
		}
		
	}
	
	public void addPoints(int points){
		if(getPoints() != -48151623){
			int p = getPoints() + points;
			BewomByte.m.executeQuery("UPDATE `users` SET `points`='" + p + "' WHERE `uuid`='" + player.getUniqueID() + "'", null);
		}
	}
	public int getPoints(){
		String p = BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + player.getUniqueID() + "'", "points").get(0);
		System.out.println("Puntos: " + player.getUniqueID() + " - " + p);
		if(!p.isEmpty()){
			return Integer.parseInt(p);
		}
		return -48151623;
	}
	
	public static void addPoints(String name, int points){
		int p = getPoints(name) + points;
		BewomByte.m.executeQuery("UPDATE `users` SET `points`='" + p + "' WHERE `user`='" + name + "'", null);
	}
	public static int getPoints(String name){
		String points = BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `user`='" + name + "'", "points").get(0);
		if(!points.isEmpty()){
			return Integer.parseInt(points);
		}
		return -1;
	}
	
	public PreciseLocation getBack() {
		return back;
	}

	public void setBack() {
		this.back = player.getLocation();
	}

	public boolean isAfk() {
		if(afk >= 72000){ //10 minutos en ticks
			return true;
		}
		return false;
	}
	public void removeAfkTime(){
		this.afk = 0;
	}
	public void addAfkTime(){
		this.afk += 1;
	}
	public void setAfkYawAndPitch(float Yaw, float Pitch) {
		if(this.afkPitch != Pitch && this.afkYaw != Yaw){
			this.afk = 0;
			this.afkPitch = Pitch;
			this.afkYaw = Yaw;
		}
	}
	
	private List<UUID> getAllFriends(){
		List<UUID> friends = new ArrayList<UUID>();
		List<String> uis = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE `uuid`='" + player.getUniqueID() + "' AND `peticion`='1'", "friend_uuid");
		List<String> uis2 = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE `friend_uuid`='" + player.getUniqueID() + "' AND `peticion`='1'", "uuid");
		for (int i = 0; i < uis.size(); i++) {
			if(!uis.get(i).equals(""))
			friends.add(UUID.fromString(uis.get(i)));
		}
		for (int i = 0; i < uis2.size(); i++) {
			if(!uis2.get(i).equals(""))
			friends.add(UUID.fromString(uis2.get(i)));
		}
		return friends;
	}
	
	private List<UUID> getAllFriendsPetitions(){
		List<UUID> friends = new ArrayList<UUID>();
		List<String> uis = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE `friend_uuid`='" + player.getUniqueID() + "' AND `peticion`='0'", "uuid");
		for (int i = 0; i < uis.size(); i++) {
			if(!uis.get(i).equals(""))
			friends.add(UUID.fromString(uis.get(i)));
		}
		return friends;
	}
	public List<UUID> getFriendsPetitions(){
		return friendsPetitions;
	}
	
	public List<UUID> getFriends(){
		return friends;
	}
	
	public boolean isFriend(BewomUser u){
		
		for (UUID uuid : friends) {
			if(u.getUUID().equals(uuid)){
				return true;
			}
		}
		return false;
	}
	
	public List<String> getFriendsNames(){
		List<String> f = new ArrayList<String>();
		for (UUID friend : friends) {
			f.add(getUserNameFromUUID(friend));
		}
		return f;
	}
	
	public void deleteFriendUUID(UUID p){
		BewomByte.m.executeQuery("DELETE FROM `users_friends` WHERE `uuid`='" + player.getUniqueID() + "' AND  `friend_uuid`='" + p.toString() + "'", null);
		BewomByte.m.executeQuery("DELETE FROM `users_friends` WHERE `uuid`='" + p.toString() + "' AND `friend_uuid`='" + player.getUniqueID() + "'", null);
		this.friends.remove(p);
	}
	
	public void acceptFriendUUID(UUID p){
		List<String> l = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE ((`uuid`='" + player.getUniqueID() + "' AND `friend_uuid`='" + p.toString() + "') OR (`friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p.toString() + "')) AND `peticion`='0'", "uuid");
		if(l.get(0).equals("")){
			BewomByte.m.executeQuery("INSERT INTO `users_friends`(`uuid`, `friend_uuid`, `peticion`) VALUES ('" + player.getUniqueID() + "','" + p +"','1')", null);
		} else {
			BewomByte.m.executeQuery("UPDATE `users_friends` SET `peticion`='1' WHERE (`uuid`='" + player.getUniqueID() + "' AND `friend_uuid`='" + p + "') OR (`friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p + "')", "uuid");
		}
		this.friends.add(p);
		this.friendsPetitions.remove(p);
	}
	
	public int acceptFriendOnlyIfApplicationWithUUID(UUID p, boolean yes){
		List<String> l = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE (`uuid`='" + player.getUniqueID() + "' AND `friend_uuid`='" + p.toString() + "') OR (`friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p.toString() + "')", "peticion");
		if(l.get(0).equals("0")) {
			if(yes){
				BewomByte.m.executeQuery("UPDATE `users_friends` SET `peticion`='1' WHERE (`uuid`='" + player.getUniqueID() + "' AND `friend_uuid`='" + p + "') OR (`friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p + "')", "uuid");
				this.friends.add(p);
				this.friendsPetitions.remove(p);
				if(BewomUser.getUser(p) != null){
					BewomUser.getUser(p).friendsPetitions.remove(player.getUniqueID());
					BewomUser.getUser(p).friends.add(player.getUniqueID());
				}
				return 1;
			} else {
				BewomByte.m.executeQuery("DELETE FROM `users_friends` WHERE ((`uuid`='" + player.getUniqueID() + "' AND `friend_uuid`='" + p + "') OR (`friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p + "'))", "uuid");
				this.friendsPetitions.remove(p);
				return -1;
			}
		} else if(l.get(0).equals("1")){
			return 2;
		} else {
			return 3;
		}
	}
	
	public int addApplicationFriendUUID(UUID p){
		if(!player.getUniqueID().toString().equals(p.toString())){
			List<String> f = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE ((`uuid`='" + player.getUniqueID() + "' AND `friend_uuid`='" + p.toString() + "') OR (`friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p.toString() + "')) AND `peticion`='0'", "uuid");
			List<String> ff = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE `friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p.toString() + "' AND `peticion`='0'", "uuid");
			
			List<String> f2 = BewomByte.m.executeQuery("SELECT * FROM `users_friends` WHERE ((`uuid`='" + player.getUniqueID() + "' AND `friend_uuid`='" + p.toString() + "') OR (`friend_uuid`='" + player.getUniqueID() + "' AND `uuid`='" + p.toString() + "')) AND `peticion`='1'", "uuid");
			if(!f2.get(0).equals("")){
				return 3;
			} else {
				if(!ff.get(0).equals("")){
					acceptFriendUUID(p);
					return 2;
				} else {
					if(f.get(0).equals("")){
						BewomByte.m.executeQuery("INSERT INTO `users_friends` (`uuid`, `friend_uuid`, `peticion`) VALUES ('" + player.getUniqueID() + "', '" + p +"', '0')", null);;
						if(BewomUser.getUser(p) != null){
							BewomUser.getUser(p).friendsPetitions.remove(player.getUniqueID());
							BewomUser.getUser(p).friendsPetitions.add(player.getUniqueID());
						}
						return 1;
					} else {
						return 0;
					}
				}
			}
		} else {
			return -1;
		}
	}
	
	public List<UUID> getFriendsUUID() {
		List<String> friends = BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + player.getUniqueID() + "' AND `peticion`='1'", "friend_uuid");
		List<UUID> friendsUUID = new ArrayList<UUID>();
		for (int i = 0; i < friends.size(); i++) {
			friendsUUID.add(UUID.fromString(friends.get(i)));
		}
		this.friends = friendsUUID;
		return this.friends;
	}

	public void setFriends(List<UUID> friends) {
		this.friends = friends;
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
		Scoreboard score = player.getWorld().getScoreboard();
		score.removePlayerFromTeams(player);
		
	}
	
	public int getTimeVip(){
		return Integer.parseInt(BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + player.getUniqueID() + "'", "timeVip").get(0));
	}
	
	public void addTimeVip(int o){
		int i = getTimeVip();
		i += o;
		BewomByte.m.executeQuery("UPDATE `users` SET `timeVip`='" + i + "' WHERE `uuid`='" + player.getUniqueID() + "'", null);
	}
	
	public void substractTimeVip(int o){
		int i = getTimeVip();
		i -= o;
		BewomByte.m.executeQuery("UPDATE `users` SET `timeVip`='" + i + "' WHERE `uuid`='" + player.getUniqueID() + "'", null);
	}
	
	public void updatePermissions(){
		
		String perm = BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid + "'", "type").get(0);
		int timeVip = getTimeVip();
		
		Scoreboard score = player.getWorld().getScoreboard();
		if(!perm.equals("")){
			
			if(isFirstUpdate){
				
				if(timeVip > 0){
					
					perm = PERM_VIP;
					BewomByte.m.executeQuery("UPDATE `users` SET `type`='vip' WHERE `uuid`='" + uuid + "'", null);
					
					if(timeVip == 1){
						player.sendMessage(TextFormating.DARK_AQUA + "Te queda 1 minuto restante como vip.");
					} else {
						player.sendMessage(TextFormating.DARK_AQUA + "Te quedan " + timeVip + " minutos restantes como vip.");
					}
					
				}
				
				isFirstUpdate = false;
				
			}
				
			if(!perm.equals(PERM_VIP)){
				if(timeVip > 0){
					perm = PERM_VIP;
					BewomByte.m.executeQuery("UPDATE `users` SET `type`='vip' WHERE `uuid`='" + uuid + "'", null);
					player.sendTitle(new Title(TextFormating.DARK_AQUA + "¡Ya eres vip!", TextFormating.WHITE + "Muchas gracias por tu donación :)", 120, 0, 60));
				}
			}
			
			if(timeVip == 0){
				if(perm.equals(PERM_VIP)){
					perm = PERM_USER;
					BewomByte.m.executeQuery("UPDATE `users` SET `type`='miembro', `days_type`='0' WHERE `uuid`='" + uuid + "'", null);
					player.sendTitle(new Title(TextFormating.DARK_AQUA + "¡Se te ha acabado el vip!", TextFormating.WHITE + "Siempre puedes volver a donar :)", 120, 0, 60));
				}
			}
			
			switch(perm) {
			case PERM_ADMIN:
				if(permissionLevel != PERM_LEVEL_ADMIN){
					setPermissionLevel(PERM_LEVEL_ADMIN);
					Team team = player.getWorld().getScoreboard().getTeam(PERM_ADMIN);
					if(team == null) {
						score.addTeam(PERM_ADMIN).setColor(TextFormating.DARK_RED);
						team = player.getWorld().getScoreboard().getTeam(PERM_ADMIN);
					}
					
					if(!team.getPlayers().contains(player)){
						score.removePlayerFromTeams(player);
						score.addPlayerToTeam(player, team);
					}
					BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "/op " + player.getName());
					player.setGameMode(1);
				}
				break;
			case PERM_MOD:
				if(permissionLevel != PERM_LEVEL_MOD){
					setPermissionLevel(PERM_LEVEL_MOD);
					Team team = player.getWorld().getScoreboard().getTeam("ae_" + PERM_MOD);
					if(team == null) {
						score.addTeam("ae_" + PERM_MOD).setColor(TextFormating.GOLD);
						team = player.getWorld().getScoreboard().getTeam("ae_" + PERM_MOD);
					}
					
					if(!team.getPlayers().contains(player)){
						score.removePlayerFromTeams(player);
						score.addPlayerToTeam(player, team);
					}
					BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "/deop " + player.getName());
					player.setGameMode(2);
				}
				break;
			case PERM_VIP:
				if(permissionLevel != PERM_LEVEL_VIP){
					setPermissionLevel(PERM_LEVEL_VIP);
					Team teamVip = player.getWorld().getScoreboard().getTeam("af_" + PERM_VIP);
					if(teamVip == null) {
						score.addTeam("af_" + PERM_VIP).setColor(TextFormating.DARK_AQUA);
						teamVip = player.getWorld().getScoreboard().getTeam("af_" + PERM_VIP);
					}
					
					if(!teamVip.getPlayers().contains(player)){
						score.removePlayerFromTeams(player);
						score.addPlayerToTeam(player, teamVip);
					}
					BewomByte.game.getCommandDispacher().executeCommand(BewomByte.game.getServer().getCommandSender(), "/deop " + player.getName());
					player.setGameMode(2);
				}
				break;
			case PERM_USER:
				if(permissionLevel != PERM_LEVEL_USER){
					setPermissionLevel(PERM_LEVEL_USER);
					Team teamUser = player.getWorld().getScoreboard().getTeam(PERM_USER);
					if(teamUser == null) {
						score.addTeam(PERM_USER).setColor(TextFormating.GRAY);
						teamUser = player.getWorld().getScoreboard().getTeam(PERM_USER);
					}
					
					if(!teamUser.getPlayers().contains(player)){
						score.removePlayerFromTeams(player);
						score.addPlayerToTeam(player, teamUser);
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

					score.removePlayerFromTeams(player);
					score.addPlayerToTeam(player, teamUser);
				}
			}
		}
	}

	public Player getPlayer() {
		return player;
	}
	public UUID getUUID() {
		return uuid;
	}
	public int getRegistration() {
		return registration;
	}
	
	public void createHashFirstTime(){
		if(!getRegisterLink){
			Ran r = new Ran();
			String h = r.next(32);
			
			registerLink += h;
			
			BewomByte.m.executeQuery("INSERT INTO `crear`(`uuid`, `user`, `hash`) VALUES ('" + player.getUniqueID() + "', '" + player.getUserName() + "', '" + h + "')", null);
			getRegisterLink = true;
		}
	}
	
	public int checkWebsiteRegistration() {
		String perm = BewomByte.m.executeQuery("SELECT * FROM `crear` WHERE `uuid`='" + player.getUniqueID() + "'", "valid").get(0);
		if(perm.equals("0")){
			String perm2 = BewomByte.m.executeQuery("SELECT * FROM `users_info` WHERE `uuid`='" + player.getUniqueID() + "'", "active").get(0);
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
	
	public String getPermissionType(){
		
		switch (permissionLevel) {
		case PERM_LEVEL_ADMIN:
			return PERM_ADMIN;
		case PERM_LEVEL_MOD:
			return PERM_MOD;
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
	
	public void setPermissionLevel(int level) {
		this.permissionLevel = level;
		BewomByte.m.executeQuery("UPDATE `users` SET `type`='" + getPermissionType() + "' WHERE `uuid`='" + player.getUniqueID() + "'", null);	
	}
	
	public static BewomUser getUser(UUID uuid) {
		return onlineUsers.get(uuid);
	}
	
	public static BewomUser getUser(Player player) {
		
		BewomUser us = getUser(player.getUniqueID());
		
		if(us == null){
			us = new BewomUser(player);
			addUser(us);
		}
		
		return us;
	}
	
	public static void addUser(BewomUser user) {
		onlineUsers.put(user.getUUID(), user);
	}
	
	public void remove() {
		onlineUsers.remove(this.uuid);
	}
	public static void remove(BewomUser user) {
		onlineUsers.remove(user.getUUID());
	}
	
	public static void remove(UUID uuid) {
		onlineUsers.remove(uuid);
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
			
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);
			
			String bienvenid = TextFormating.DARK_AQUA + "Bienvenid@!";			
			player.sendTitle(new Title(bienvenid, TextFormating.WHITE + "Hazte con todos...", 120, 0, 60));
			updatePermissions();
			
			if(!getFriendsPetitions().isEmpty()){
				String plural = "";
				if(getFriendsPetitions().size() != 1){
					plural = "es";
				}
				player.sendMessage(TextFormating.GREEN + "Tienes " + getFriendsPetitions().size() + " solicitud" + plural +" de amistad.");
				String petitions = TextFormating.WHITE + "[";
				for (int i = 0; i < getFriendsPetitions().size(); i++) {
					petitions += TextFormating.GREEN + BewomUser.getUserNameFromUUID(getFriendsPetitions().get(i)) + TextFormating.WHITE + "]";
					if(i + 1 != getFriendsPetitions().size()){
						petitions += ", [";
					}
				}
				player.sendMessage(petitions);
			}
			
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
		return Integer.parseInt(BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid2 + "'", "money").get(0));
	}
	
	public static int getMoney(String name){
		return Integer.parseInt(BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `user`='" + name + "'", "money").get(0));
	}
	
	public static void addMoney(UUID uuid, int a){
		int money = a + getMoney(uuid);
		BewomByte.m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
	}
	
	public static boolean substractMoney(UUID uuid, int a){
		if(getMoney(uuid) >= a){
			int money = Math.abs(a - getMoney(uuid));
			BewomByte.m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
			return true;
		}
		return false;
		
	}
	
	public int getMoney(){
		return Integer.parseInt(BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + uuid + "'", "money").get(0));
	}
	
	public void addMoney(int a){
		int money = a + getMoney();
		BewomByte.m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
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
		BewomByte.m.executeQuery("UPDATE `users` SET `money`='" + money + "' WHERE `uuid`='" + uuid + "'", null);
	}
	
	public static List<String> getPlayersUUIDRegistered(){
		return BewomByte.m.executeQuery("SELECT * FROM `users`", "uuid");
	}
	
	public static String getUserNameFromUUID(UUID u){
		List<String> uuid = BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `uuid`='" + u.toString() + "'", "user");
		return uuid.get(0);
	}
	
	public static List<Player> getPlayersRegistered(){
		List<String> uuids = BewomByte.m.executeQuery("SELECT * FROM `users`", "uuid");
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < uuids.size(); i++) {
			players.add(BewomByte.game.getServer().getPlayer(uuids.get(i)));
		}
		return players;
	}
	
	public static List<String> getPlayersUsernameRegistered(){
		return BewomByte.m.executeQuery("SELECT * FROM `users`", "user");
	}
	
	public static String getUUIDName(String name){
		List<String> uuid = BewomByte.m.executeQuery("SELECT * FROM `users` WHERE `user`='" + name + "'", "uuid");
		return uuid.get(0);
	}
	
	public static Calendar getLastLogin(String nick){
		List<String> uuid = BewomByte.m.executeQuery("SELECT `lastLogin` FROM `users` WHERE `user`='" + nick + "'", "lastLogin");
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(Timestamp.valueOf("2015-01-01 00:00:00").getTime());
		if(!uuid.get(0).equalsIgnoreCase("0000-00-00 00:00:00")){
			ca.setTimeInMillis(Timestamp.valueOf(uuid.get(0)).getTime());
		}
		return ca;
	}
	
}
