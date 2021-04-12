package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;

public abstract class RegistrableCommand {
	private Map<String, RegistrableCommand> mappedSubcommands = null;
	
	protected RegistrableCommand() {
		super();
		mapSubcommands();
	}
	
	public abstract String getName();

	public abstract String getUsage();

	public List<String> getRequiredPermissions() {
		return new ArrayList<String>();
	}

	public List<RegistrableCommand> getSubcommands() {
		return new ArrayList<RegistrableCommand>();
	}
	
	private final void mapSubcommands() {
		if (mappedSubcommands == null) {
			mappedSubcommands = new HashMap<String, RegistrableCommand>();
		} else {
			mappedSubcommands.clear();
		}
		
		for (RegistrableCommand subcommand : getSubcommands()) {
			mappedSubcommands.put(subcommand.getName(), subcommand);
		}
	}
	
	public final Map<String, RegistrableCommand> getMappedSubcommands() {
		return mappedSubcommands;
	}

	public boolean execute(CommandSender sender, String[] args) {
		return false;
	}
}
