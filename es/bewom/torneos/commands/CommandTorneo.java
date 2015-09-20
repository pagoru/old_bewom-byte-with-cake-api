package es.bewom.torneos.commands;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.util.PreciseLocation;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.torneos.Torneo;
import es.bewom.torneos.Torneos;
import es.bewom.user.BewomUser;
import es.bewom.util.Location;
import es.bewom.util.Sounds;

public class CommandTorneo extends CommandBase {

	public CommandTorneo() {
		super("torneo", "t");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos) {
		return null;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		/*
		 * t nuevo <HH:MM> <dd/mm/yyyy> <name>
		 * 
		 * t posicion A/B/C/D
		 * 
		 * t ronda <ronda> combate <batalla>
		 * t ronda <ronda> combate <batalla> ganador <player>
		 * 
		 */
		Torneo t = Torneos.current;
		Player p = commandSender.getPlayer();
		
		if(args.length >= 5){
			if(args[0].equals("nuevo")){
				String name = "";
				for (int i = 3; i < args.length; i++) {
					name += args[i] + " ";
				}
				name.substring(0, name.length() - 1);
				
				String[] date = args[2].split("/");
				
				String dateDate = date[2] + "-" + date[1] + "-" + date[0];
				String dateHour = args[1] + ":00";
				
				Torneos.current = new Torneo(name, dateDate + " " + dateHour);
			} else {
				if(args.length == 6){
					if(args[0].equals("ronda") && args[2].equals("combate") && args[4].equals("ganador")){
						int round = Integer.parseInt(args[1]) - 1;
						int battle = Integer.parseInt(args[3]) - 1;
						String winner = args[5];
						if((round == 0 && battle < 8) 
								|| (round == 1 && battle < 4) 
								|| (round == 2 && battle < 2)
								|| (round == 3 && battle == 0)){
							if(t.setWinner(round, battle, winner)){
								if(!winner.equals("null")){
									if(round == 3){
										Chat.sendMessage(p, TextMessages.BROADCAST + winner + " ha ganado este torneo!", "/t");
										for (int i = 0; i < 2; i++) {
											if(t.getBattle(round, battle)[i] != null){
												Player pBattle = BewomByte.game.getServer().getPlayer(t.getBattle(round, battle)[0]);
												if(pBattle != null){
													pBattle.setLocation(t.getLocation()[2].getPreciseLocation());
												}
											}
										}
										if(t.getThirdWinner() != null){
											Player pThird = BewomByte.game.getServer().getPlayer(t.getThirdWinner());
											if(pThird != null){
												pThird.setLocation(t.getLocation()[2].getPreciseLocation());
											}
										}
									} else {
										Chat.sendMessage(p, TextMessages.BROADCAST + winner + " ha ganado este combate.", "/t");
										for (int i = 0; i < 2; i++) {
											if(t.getBattle(round, battle)[i] != null){
												Player pBattle = BewomByte.game.getServer().getPlayer(t.getBattle(round, battle)[0]);
												if(pBattle != null){
													pBattle.setLocation(t.getLocation()[3].getPreciseLocation());
												}
											}
										}
									}
									Sounds.playSoundToAll("fireworks.largeBlast_far", 1.0F, 1.2F);
								}
							} else {
								p.sendMessage(TextFormating.RED + winner + " no participa en este combate.");
							}
						} else {
							p.sendMessage(TextFormating.RED + "Combate o ronda invalidas");
						}
					} else {
						p.sendMessage(TextFormating.RED + "/r ronda <r> combate <c>");
					}
				}
			}
		} else if(args.length == 1){
			if(args[0].equals("start")){
				t.purgePlayers();
				Chat.sendMessage(p, TextMessages.BROADCAST + t.getName() + " esta a punto de empezar!", "/t");
			}
		} else if(args.length == 2){
			if(args[0].equals("posicion")){
				switch (args[1]) {
				case "A":
					t.setLocation(0, p.getLocation());
					p.sendMessage(TextFormating.RED + "Posición A asignada.");
					break;
				case "B":
					t.setLocation(1, p.getLocation());
					p.sendMessage(TextFormating.RED + "Posición B asignada.");
					break;
				case "C":
					t.setLocation(2, p.getLocation());
					p.sendMessage(TextFormating.RED + "Posición C asignada.");
					break;
				case "D":
					t.setLocation(3, p.getLocation());
					p.sendMessage(TextFormating.RED + "Posición D asignada.");
					break;
				default:
					p.sendMessage(TextFormating.RED + "Error. Posiciones A, B, C y D!");
					break;
				}
			}
		} else if(args.length == 4){
			if(args[0].equals("ronda") && args[2].equals("combate")){
				int round = Integer.parseInt(args[1]) - 1;
				int battle = Integer.parseInt(args[3]) - 1;
				if((round == 0 && battle < 8) 
						|| (round == 1 && battle < 4) 
						|| (round == 2 && battle < 2)
						|| (round == 3 && battle == 0)){
					if(t.getBattle(round, battle)[0] == null && t.getBattle(round, battle)[1] != null){
						if(t.setWinner(round, battle, t.getBattle(round, battle)[1])){
							Chat.sendMessage(p, TextMessages.BROADCAST + t.getBattle(round, battle)[1] + " ha ganado por desclasificación del contrincante!", "/t");
						}
					} else if(t.getBattle(round, battle)[0] != null && t.getBattle(round, battle)[1] == null){
						if(t.setWinner(round, battle, t.getBattle(round, battle)[0])){
							Chat.sendMessage(p, TextMessages.BROADCAST + t.getBattle(round, battle)[0] + " ha ganado por desclasificación del contrincante!", "/t");
						}
					} else if(t.getBattle(round, battle)[0] == null && t.getBattle(round, battle)[1] == null){
						if(t.setWinner(round, battle, null)){
							p.sendMessage(TextFormating.RED + "Las dos partes son nulas.");
						}
					} else {
						p.sendMessage(t.getBattle(round, battle)[0] + "_" + t.getBattle(round, battle)[1]);
						Chat.sendMessage(p, TextMessages.BROADCAST + "Empieza el combate entre " + t.getBattle(round, battle)[0] + " y " + t.getBattle(round, battle)[1], "/t");
						Sounds.playSoundToAll("fireworks.twinkle_far", 1.0F, 1.2F);
						for (int i = 0; i < 2; i++) {
							if(t.getBattle(round, battle)[i] != null){
								Player pBattle = BewomByte.game.getServer().getPlayer(t.getBattle(round, battle)[i]);
								if(pBattle != null){
									pBattle.setLocation(t.getLocation()[i].getPreciseLocation());
								}
							}
						}
					}
				} else {
					p.sendMessage(TextFormating.RED + "Combate o ronda invalidas");
				}
			} else {
				p.sendMessage(TextFormating.RED + "/r ronda <r> combate <c>");
			}
		} 
		Torneos.save();
	}

}
