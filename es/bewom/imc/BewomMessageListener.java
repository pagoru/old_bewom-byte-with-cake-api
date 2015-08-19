package es.bewom.imc;

import java.util.UUID;

import org.cakepowered.api.base.Player;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.util.MessageListener;
import org.cakepowered.api.util.PluginMessage;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;

public class BewomMessageListener implements MessageListener{

	public static BewomMessageListener INSTANCE = new BewomMessageListener();
	
	@Override
	public PluginMessage handleMessage(String sender, PluginMessage msg) {
		BewomByte.log.debug("sender: "+sender+", message: "+msg);
		if("getMoney".equals(msg.getTitle())){
			
			NBTCompund nbt = msg.getNBTCompound();
			UUID id = new UUID(nbt.getLong("msb"), nbt.getLong("lsb"));
			Player p = BewomByte.game.getServer().getPlayer(id);
			BewomUser u = BewomUser.getUser(p);
			
			NBTCompund tag = BewomByte.game.getNBTFactory().newNBTCompound();
			tag.setInteger("money", u.getMoney());
			
			return new PluginMessage("money", tag);
			
		}else if("extractMoney".equals(msg.getTitle())){
			
			NBTCompund nbt = msg.getNBTCompound();
			UUID id = new UUID(nbt.getLong("msb"), nbt.getLong("lsb"));
			Player p = BewomByte.game.getServer().getPlayer(id);
			BewomUser u = BewomUser.getUser(p);
			
			NBTCompund tag = BewomByte.game.getNBTFactory().newNBTCompound();
			int extract = nbt.getInteger("amount");
			
			if(u.canSubstractMoney(extract)){
				u.substractMoney(extract);
				tag.setInteger("money", extract);
			}else{
				tag.setInteger("money", 0);
			}
			
			return new PluginMessage("money", tag);
			
		}else if("addMoney".equals(msg.getTitle())){
			
			NBTCompund nbt = msg.getNBTCompound();
			UUID id = new UUID(nbt.getLong("msb"), nbt.getLong("lsb"));
			Player p = BewomByte.game.getServer().getPlayer(id);
			BewomUser u = BewomUser.getUser(p);
			
			u.addMoney(nbt.getInteger("amount"));
		}
		return null;
	}
}
