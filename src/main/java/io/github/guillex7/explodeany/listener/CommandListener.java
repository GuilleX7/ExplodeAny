package io.github.guillex7.explodeany.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import io.github.guillex7.explodeany.command.CommandAbout;
import io.github.guillex7.explodeany.command.CommandChecktool;
import io.github.guillex7.explodeany.command.CommandReload;

public class CommandListener implements TabExecutor {
	private final static String[] subcommands = { "reload", "checktool", "about" };
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				CommandReload.executor(sender, command, label, args);
			} else if (args[0].equalsIgnoreCase("checktool")) {
				CommandChecktool.executor(sender, command, label, args);
			} else if (args[0].equalsIgnoreCase("about")) {
				CommandAbout.executor(sender, command, label, args);
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> results = new ArrayList<String>();
		if (args.length > 0) {
			if (args[0].length() > 0) {
				for (String subcommand : subcommands) {
					if (subcommand.startsWith(args[0])) {
						results.add(subcommand);
					}
				}
			} else {
				for (String subcommand : subcommands) {
					results.add(subcommand);
				}
			}
		}
		return results;
	}
}
