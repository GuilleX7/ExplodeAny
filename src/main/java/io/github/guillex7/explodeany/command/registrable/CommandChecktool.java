package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public class CommandChecktool extends RegistrableCommand {
	private final static Set<Player> playersUsingChecktool = new HashSet<Player>();

	public final static Set<Player> getPlayersUsingChecktool() {
		return playersUsingChecktool;
	}
	
	private CommandChecktool() {
		super();
	}
	
	public static CommandChecktool empty() {
		return new CommandChecktool();
	}

	@Override
	public String getName() {
		return "checktool";
	}

	@Override
	public String getUsage() {
		return "[toggle]";
	}
	
	@Override
	public List<RegistrableCommand> getSubcommands() {
		return new ArrayList<RegistrableCommand>(Arrays.asList(
				CommandChecktoolToggle.empty()
				));
	}
}
