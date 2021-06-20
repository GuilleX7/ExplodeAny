package io.github.guillex7.explodeany.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandManager implements TabExecutor {
	private static CommandManager instance;

	private Map<String, RegistrableCommand> registeredCommands;

	private CommandManager() {
		registeredCommands = new HashMap<>();
	}

	public static CommandManager getInstance() {
		if (instance == null) {
			instance = new CommandManager();
		}
		return instance;
	}

	public Map<String, RegistrableCommand> getRegisteredCommands() {
		return registeredCommands;
	}

	public void registerCommand(RegistrableCommand command) {
		registeredCommands.put(command.getName(), command);
	}

	public void unregisterAllCommands() {
		registeredCommands.clear();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		RegistrableCommand rootCommand = getRegisteredCommands().get(command.getName());
		if (rootCommand == null) {
			return true;
		}
		String breadcrumbs = rootCommand.getName();

		int i;
		for (i = 0; i < args.length; i++) {
			RegistrableCommand subcommand = rootCommand.getMappedSubcommands().get(args[i]);
			if (subcommand == null) {
				break;
			}
			rootCommand = subcommand;
			breadcrumbs += " " + rootCommand.getName();
		}

		boolean executable = true;
		for (String permissionName : rootCommand.getRequiredPermissions()) {
			if (!sender.hasPermission(permissionName)) {
				executable = false;
				break;
			}
		}

		if (!executable) {
			sender.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.NOT_ALLOWED)));
			return true;
		}

		if (!rootCommand.execute(sender, Arrays.copyOfRange(args, i, args.length))) {
			sender.sendMessage(MessageFormatter.colorize(
					MessageFormatter.sign(String.format("Usage: /%s &7%s", breadcrumbs, rootCommand.getUsage()))));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> autocompletion = new ArrayList<String>();

		RegistrableCommand rootCommand = getRegisteredCommands().get(command.getName());
		if (rootCommand == null) {
			return autocompletion;
		}

		int i;
		for (i = 0; i < args.length; i++) {
			RegistrableCommand subcommand = rootCommand.getMappedSubcommands().get(args[i]);
			if (subcommand == null) {
				break;
			}
			rootCommand = subcommand;
		}

		if (args.length <= i) {
			return autocompletion;
		}

		if (args[i].length() > 0) {
			for (RegistrableCommand subcommand : rootCommand.getSubcommands()) {
				if (subcommand.getName().startsWith(args[i])) {
					autocompletion.add(subcommand.getName());
				}
			}
		} else {
			for (RegistrableCommand subcommand : rootCommand.getSubcommands()) {
				autocompletion.add(subcommand.getName());
			}
		}

		return autocompletion;
	}
}
