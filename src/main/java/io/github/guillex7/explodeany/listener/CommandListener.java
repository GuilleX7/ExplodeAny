package io.github.guillex7.explodeany.listener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.command.CommandReload;

public class CommandListener implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				CommandReload.executor(sender, command, label, args);
			}
		}
		return true;
	}
}
