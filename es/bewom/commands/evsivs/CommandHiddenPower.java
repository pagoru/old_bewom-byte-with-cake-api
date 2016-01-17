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

public class CommandHiddenPower extends CommandBase {
	
	public CommandHiddenPower() {
		super("hiddenpower");
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
					
					int intPower = 0;
					
					int HPIVs 			= n.getInteger("IVHP");
					int attackIVs 		= n.getInteger("IVAttack");
					int defenseIVs		= n.getInteger("IVDefense");
					int spAttackIVs 	= n.getInteger("IVSpAtt");
					int spDefenseIVs	= n.getInteger("IVSpDef");
					int speedIVs 		= n.getInteger("IVSpeed");
					String name 		= n.getString("Name");
					
					int 		a 				= HPIVs 		% 2;
					int 		b 				= attackIVs 	% 2;
					int 		c 				= defenseIVs 	% 2;
					int 		d 				= speedIVs 		% 2;
					int 		e 				= spAttackIVs 	% 2;
					int 		f 				= spDefenseIVs 	% 2;
					double 		fedbca			= 32 * f + 16 * e + 8 * d + 4 * c + 2 * b + a;
					int 		intTypeIndex 	= (int)Math.floor(fedbca * 15.0D / 63.0D);
					String[] 	strTypes 		= { "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost", "Steel", "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark" };
					
					int u = 0;
					int v = 0;
					int w = 0;
					int x = 0;
					int y = 0;
					int z = 0;
			        
			        int tmp = HPIVs % 4;
			        if ((tmp == 2) || (tmp == 3)) {
			          u = 1;
			        }
			        tmp = attackIVs % 4;
			        if ((tmp == 2) || (tmp == 3)) {
			          v = 1;
			        }
			        tmp = defenseIVs % 4;
			        if ((tmp == 2) || (tmp == 3)) {
			          w = 1;
			        }
			        tmp = speedIVs % 4;
			        if ((tmp == 2) || (tmp == 3)) {
			          x = 1;
			        }
			        tmp = spAttackIVs % 4;
			        if ((tmp == 2) || (tmp == 3)) {
			          y = 1;
			        }
			        tmp = spDefenseIVs % 4;
			        if ((tmp == 2) || (tmp == 3)) {
			          z = 1;
			        }
			        intPower = (int)Math.floor((u + 2 * v + 4 * w + 8 * x + 16 * y + 32 * z) * 40.0D / 63.0D + 30.0D);
					
			        p.sendMessage(TextFormating.DARK_AQUA + "El poder oculto de tu " + name + " es " + TextFormating.GOLD + strTypes[intTypeIndex] + TextFormating.DARK_AQUA + ".");
			        p.sendMessage(TextFormating.DARK_AQUA + "El poder oculto de tu " + name + " tiene " + TextFormating.GOLD + intPower + TextFormating.DARK_AQUA + " de poder.");
			        
			        return;
			        
				}
				
				p.sendMessage(TextFormating.RED + "Introduce un slot donde haya un pokemon.");
				return;
				
			}
			
			p.sendMessage(TextFormating.RED + "Introduce un numero de slot correcto.");
			return;
			
		}
		
		p.sendMessage(TextFormating.RED + "Introduce el slot del pokemon /hiddenpower {numero}");
		
	}
	
}