package es.bewom.commands;

import org.cakepowered.api.command.CommandDispatcher;

import es.bewom.BewomByte;
import es.bewom.centrospokemon.commands.CommandCentro;
import es.bewom.centrospokemon.commands.CommandPonerCentro;
import es.bewom.centrospokemon.commands.CommandQuitarCentro;
import es.bewom.chat.commands.CommandMp;
import es.bewom.commands.randomTp.CommandTpb;
import es.bewom.economy.commands.CommandDinero;
import es.bewom.p.commands.CommandCasa;
import es.bewom.p.commands.CommandH;
import es.bewom.p.commands.CommandP;
import es.bewom.p.commands.CommandRanch;
import es.bewom.teleport.commands.CommandZona;
import es.bewom.torneos.commands.CommandTorneo;
import es.bewom.teleport.commands.CommandSetZona;
import es.bewom.teleport.commands.CommandTPA;

public class Commands {

	public void registerAll() {
		CommandDispatcher reg = BewomByte.game.getCommandDispacher();
		
		reg.registerCommand(new CommandCentro());
		reg.registerCommand(new CommandPonerCentro());
		reg.registerCommand(new CommandQuitarCentro());
		reg.registerCommand(new CommandSpawn());
		reg.registerCommand(new CommandP());
		reg.registerCommand(new CommandGM());
		reg.registerCommand(new CommandKick());
		reg.registerCommand(new CommandBan());
		reg.registerCommand(new CommandSay());
		reg.registerCommand(new CommandPlugins());
		reg.registerCommand(new CommandDinero());
		reg.registerCommand(new CommandTpx());
		reg.registerCommand(new CommandH());
		reg.registerCommand(new CommandCasa());
		reg.registerCommand(new CommandTp());
		reg.registerCommand(new CommandTphere());
		reg.registerCommand(new CommandUnBan());
		reg.registerCommand(new CommandPerms());
		reg.registerCommand(new CommandReload());
		reg.registerCommand(new CommandEnderSee());
		reg.registerCommand(new CommandPlayerInventory());
		reg.registerCommand(new CommandVanished());
		reg.registerCommand(new CommandTPA());
		reg.registerCommand(new CommandZona());
		reg.registerCommand(new CommandSetZona());
		reg.registerCommand(new CommandEnderChest());
		reg.registerCommand(new CommandMp());
		reg.registerCommand(new CommandAmigos());
		reg.registerCommand(new CommandPokeHeal());
		reg.registerCommand(new CommandBack());
		reg.registerCommand(new CommandRanch());
		reg.registerCommand(new CommandTorneo());
		reg.registerCommand(new CommandCraft());
		reg.registerCommand(new CommandTpb());
		reg.registerCommand(new CommandTps());
		
	}
}
