package es.bewom.imc;

import es.bewom.BewomByte;
import es.bewom.economy.Bank;
import es.bewom.metro.Metro;
import es.bewom.metro.Ticket;
import es.bewom.user.BewomUser;
import org.cakepowered.api.base.Player;
import org.cakepowered.api.nbt.NBTCompund;
import org.cakepowered.api.util.MessageListener;
import org.cakepowered.api.util.PluginMessage;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class BewomMessageListener implements MessageListener {

	public static BewomMessageListener INSTANCE = new BewomMessageListener();
	private static float commissions = 0.03f;

	@Override
	public PluginMessage handleMessage(String sender, PluginMessage msg) {

		if ("getMoney".equals(msg.getTitle())) {

			NBTCompund nbt = msg.getNBTCompound();
			UUID id = new UUID(nbt.getLong("msb"), nbt.getLong("lsb"));
			Player p = BewomByte.game.getServer().getPlayer(id);
			BewomUser u = BewomUser.getUser(p);

			NBTCompund tag = BewomByte.game.getNBTFactory().newNBTCompound();
			tag.setInteger("money", u.getMoney());

			return new PluginMessage("money", tag);

		} else if ("extractMoney".equals(msg.getTitle())) {

			NBTCompund nbt = msg.getNBTCompound();
			UUID id = new UUID(nbt.getLong("msb"), nbt.getLong("lsb"));
			Player p = BewomByte.game.getServer().getPlayer(id);
			BewomUser u = BewomUser.getUser(p);

			NBTCompund tag = BewomByte.game.getNBTFactory().newNBTCompound();
			int extract = nbt.getInteger("amount");

			if (u.canSubstractMoney(applyCommissions(extract))) {
				if (!nbt.getBoolean("simulated")) {
					u.substractMoney(applyCommissions(extract));
					Bank.addMoney(applyCommissions(extract));
				}

				tag.setInteger("money", extract);
			} else {
				tag.setInteger("money", 0);
			}

			return new PluginMessage("money", tag);

		} else if ("addMoney".equals(msg.getTitle())) {
						
			NBTCompund nbt = msg.getNBTCompound();
			UUID id = new UUID(nbt.getLong("msb"), nbt.getLong("lsb"));
			Player p = BewomByte.game.getServer().getPlayer(id);
			BewomUser u = BewomUser.getUser(p);

			u.addMoney(nbt.getInteger("amount"));
		} else if ("getCommissions".equals(msg.getTitle())) {

			NBTCompund tag = BewomByte.game.getNBTFactory().newNBTCompound();
			tag.setFloat("value", commissions);
			return new PluginMessage("value", tag);
		}else if("registerTicket".equals(msg.getTitle())){
			NBTCompund tag = BewomByte.game.getNBTFactory().newNBTCompound();
			boolean registrado = true;//cambia esto para saber si hay colisiones con otras UUID
			NBTCompund nbt = msg.getNBTCompound();

			NBTCompund time = (NBTCompund) nbt.getCompound("time");//momento en el que se creo el ticket
			NBTCompund aux = (NBTCompund) nbt.getCompound("uuid");
			UUID uuid = new UUID(aux.getLong("msb"), aux.getLong("lsb"));//uuid del ticket
			int duracion = nbt.getInteger("duracion");//dias de validez
			int viajes = nbt.getInteger("viajes");//numero de viajes permitidos
			
			//insert code here...
						
			Calendar cal = Calendar.getInstance();                  //TODO cout es imbécil... ¬¬'
			cal.set(time.getInteger("year"), (time.getInteger("month") - 1), time.getInteger("day"), 
					time.getInteger("hour"), time.getInteger("minute"), time.getInteger("second"));
			
			System.out.println(cal.getTime().toString());
			
			Ticket ti = new Ticket(uuid, duracion, cal, viajes);
						
			Metro.tickets.add(ti);
			Metro.saveTickets();
			
			// *** //
			
			tag.setBoolean("value", registrado);
			return new PluginMessage("value", tag);
		}
		return null;
	}

	public int applyCommissions(int amount) {
		float a = Math.max(1, amount * commissions);
		return (int) (amount + a);
	}
}