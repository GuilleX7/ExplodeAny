package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.configuration.PermissionNode;

public abstract class RegistrableCommand {
	private Map<String, RegistrableCommand> mappedSubcommands = null;

	protected RegistrableCommand() {
		super();
		mapSubcommands();
	}

	public abstract String getName();

	public String getUsage() {
		return "";
	}

	public List<PermissionNode> getRequiredPermissions() {
		return new ArrayList<>();
	}

	public List<RegistrableCommand> getSubcommands() {
		return new ArrayList<>();
	}

	private final void mapSubcommands() {
		if (mappedSubcommands == null) {
			mappedSubcommands = new HashMap<>();
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
