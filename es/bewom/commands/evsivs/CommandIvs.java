package es.bewom.commands.evsivs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.command.CommandBase;
import org.cakepowered.api.command.CommandSender;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.util.Vector3i;
import org.cakepowered.api.util.text.TextFormating;

import es.bewom.BewomByte;
import es.bewom.chat.Chat;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandIvs extends CommandBase {
	
	public CommandIvs() {
		super("ivs");
	}
	
	@Override
	public List<String> addTabCompletionOptions(CommandSender sender, String[] args, Vector3i pos){
		List<String> ret = new ArrayList<String>();
		for (int i = 1; i < 7; i++) {
			ret.add("" + i);
		}
		return ret;
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		
		Player p = commandSender.getPlayer();
		
		if(args.length == 1){
			int i = Integer.parseInt(args[0]);
			if(i > 0 && i < 7){
				
				NBTCompund nbt = BewomByte.game.getNBTFactory().readNBT(new File("world/pokemon/" + p.getUniqueID() + ".pk"));
				NBTCompund n = (NBTCompund) nbt.getCompound("party" + (i - 1));
				
				if(n != null){
					
					int HPIVs 			= n.getInteger("IVHP");
					int attackIVs 		= n.getInteger("IVAttack");
					int defenseIVs		= n.getInteger("IVDefence");
					int spAttackIVs 	= n.getInteger("IVSpAtt");
					int spDefenseIVs	= n.getInteger("IVSpDef");
					int speedIVs 		= n.getInteger("IVSpeed");
					int total 			= HPIVs + attackIVs + defenseIVs + spAttackIVs + spDefenseIVs + speedIVs;
					int percent 		= (total * 100) / 186;
					String name 		= n.getString("Name");
					
					p.sendMessage(TextFormating.DARK_AQUA 	+ "Estos son los IVs de tu " + name + ".");
					p.sendMessage(TextFormating.DARK_GREEN 	+ "HP " + TextFormating.GOLD + HPIVs);
					p.sendMessage(TextFormating.DARK_GREEN 	+ "ATK " + TextFormating.GOLD + attackIVs);
					p.sendMessage(TextFormating.DARK_GREEN 	+ "DEF " + TextFormating.GOLD + defenseIVs);
					p.sendMessage(TextFormating.DARK_GREEN 	+ "SpATK " + TextFormating.GOLD + spAttackIVs);
					p.sendMessage(TextFormating.DARK_GREEN 	+ "SpDEF " + TextFormating.GOLD + spDefenseIVs);
					p.sendMessage(TextFormating.DARK_GREEN 	+ "Speed " + TextFormating.GOLD + speedIVs);
					p.sendMessage(TextFormating.DARK_GREEN 	+ "TOTAL " + TextFormating.GOLD + total + "/186" + 
						TextFormating.DARK_GREEN + " [" + TextFormating.WHITE + percent + "%" + TextFormating.DARK_GREEN + "]"
					);
					
					return;
					
				}
				
				p.sendMessage(TextFormating.RED + "Introduce un slot donde haya un pokemon.");
				return;
				
			}
			
			p.sendMessage(TextFormating.RED + "Introduce un numero de slot correcto.");
			return;
			
		}
		
		p.sendMessage(TextFormating.RED + "Introduce el slot del pokemon /ivs {numero}");
		
	}
	
}
