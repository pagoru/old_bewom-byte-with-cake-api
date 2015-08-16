package es.bewom.commands;

import org.cakepowered.api.command.CommandDispatcher;

import es.bewom.BewomByte;
import es.bewom.centrospokemon.commands.CommandCentro;
import es.bewom.centrospokemon.commands.CommandPonerCentro;
import es.bewom.centrospokemon.commands.CommandQuitarCentro;
import es.bewom.p.commands.CommandP;

public class Commands {

	public void registerAll() {
		CommandDispatcher reg = BewomByte.game.getCommandDispacher();
		
		//cp
		reg.registerCommand(new CommandCentro());		
		//ponercentro
		reg.registerCommand(new CommandPonerCentro());	
		//quitarcentro
		reg.registerCommand(new CommandQuitarCentro());	
		//spawn
		reg.registerCommand(new CommandSpawn());
		
		reg.registerCommand(new CommandP());
		
		reg.registerCommand(new CommandGM());
		
		reg.registerCommand(new CommandKick());
		
		reg.registerCommand(new CommandBan());
		
		reg.registerCommand(new CommandSay());
		
		reg.registerCommand(new CommandPlugins());
		
		reg.registerCommand(new CommandDinero());
		
	}
}
