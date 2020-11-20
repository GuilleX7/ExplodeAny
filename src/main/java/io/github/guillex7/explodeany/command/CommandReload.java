package io.github.guillex7.explodeany.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.utils.MessageFormatter;

public class CommandReload {
	private final static String RELOAD_PERMISSION_NODE = "explodeany.reload";

	public static void executor(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission(RELOAD_PERMISSION_NODE)) {
			ExplodeAny.getInstance().onDisable();
			ExplodeAny.getInstance().onEnable();
			sender.sendMessage(MessageFormatter.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.RELOADED)));
		} else {
			sender.sendMessage(MessageFormatter.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.NOT_ALLOWED)));
		}
	}
}
