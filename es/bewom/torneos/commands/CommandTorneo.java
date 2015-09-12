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

import es.bewom.torneos.Torneo;
import es.bewom.util.Location;

public class CommandTorneo extends CommandBase {

	public CommandTorneo() {
		super("torneo", "t");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos) {
		List<String> ret = new ArrayList<String>();
		if(args.length == 1){
			ret.add("nuevo");
			if(Torneo.current != null){
				ret.add(Torneo.current.getIndex() + "");
			}
			for (int i = 1; i < Torneo.getLastIndex(); i++) {
				ret.add(i + "");
			}
		} else if(args.length == 2){
			ret.add("name");
			ret.add("fecha");
			ret.add("start");
			ret.add("combate");
			ret.add("finish");
			ret.add("location");
		} else if(args.length == 3){
			for (int i = 1; i < 5; i++) {
				ret.add(i + "");
			}
		}
		
		int Y = args.length - 1;
		for (int i = 0; i < ret.size(); i++) {
			if(args[Y].length() <= ret.get(i).length()){
				if(args[Y].substring(0, args[Y].length()).toLowerCase().equals(ret.get(i).substring(0, args[Y].length()).toLowerCase())){
					List<String> p = new ArrayList<String>();
					p.add(ret.get(i));
					return p;
				}
			}
		}
		return ret;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		/*
		 * torneo nuevo <nombre>
		 * torneo <indice> fecha 17/04/15 20:00
		 * torneo <indice> name <collection>
		 * torneo <indice> location <1, 2, 3, 4>
		 * 
		 * torneo <indice> start
		 * torneo <indice> combate {1 - 15} 
		 * torneo <indice> finish
		 * 
		 * torneo <indice> combate {1 - 15} <nick>
		 * 
		 */
		Player p = commandSender.getPlayer();
		if(args.length >= 2){
			if(args[0].equals("nuevo")){
				String torneoName = "";
				for (int i = 1; i < args.length; i++) {
					torneoName += args[i] + " ";
				}
				torneoName = torneoName.substring(0, torneoName.length() - 1);
				Torneo.current = new Torneo(torneoName);
				p.sendMessage(TextFormating.RED + "Torneo " + torneoName + " creado con éxito. Indice [" + Torneo.current.getIndex() + "]");
			}
			if(args[1].equals("name")){
				Torneo.load(Integer.parseInt(args[0]));
				String torneoName = "";
				for (int i = 2; i < args.length; i++) {
					torneoName += args[i] + " ";
				}
				torneoName = torneoName.substring(0, torneoName.length() - 1);
				Torneo.current.setName(torneoName);
				Torneo.current.save();
				p.sendMessage(TextFormating.RED + "Nombre del torneo cambiado!");
				
			}
		}
		if(args.length == 2){
			if(args[1].equals("start")) {
				
			} else if(args[1].equals("finish")) {
				
			}
		} else if(args.length == 3){
			if(args[1].equals("combate")) {
				int battle = Integer.parseInt(args[2]);
				int index = Integer.parseInt(args[0]);
				Torneo.current = Torneo.load(index);
				List<Player> playersBattle = Torneo.current.getBattle(battle);
				p.sendMessage(playersBattle.get(0) + "_" + playersBattle.get(1)); //quitar
				
			} else if(args[1].equals("location")) {
				Torneo.load(Integer.parseInt(args[0]));
				Torneo.current.setLocation(Integer.parseInt(args[2]), p.getLocation());
			}
		} else if(args.length == 4){
			if(args[1].equals("fecha")) {
				if(args[0].equals(Torneo.getLastIndex() - 1 + "")){
					Torneo.load(Integer.parseInt(args[0]));
					String[] date = args[2].split("/");
					String d =  "20" + date[2] + "-" + date[1] + "-" + date[0] + " " + args[3] + ":00";
					Torneo.current.setDate(d);
					Torneo.current.save();
					
					p.sendMessage(TextFormating.RED + "Fecha del torneo " + d + ".");
				}
			} else if(args[1].equals("combate")) {
				
			}
		}
		
	}

}
