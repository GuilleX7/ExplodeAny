package io.github.guillex7.explodeany.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.utils.MessageFormatter;

public class CommandAbout {
	public static void executor(CommandSender sender, Command cmd, String label, String[] args) {
		PluginDescriptionFile description = ExplodeAny.getInstance().getDescription();
		sender.sendMessage(MessageFormatter.sign(
				MessageFormatter.colorize(String.format("&f%s\n&7Developed by %s\n&7%s", description.getFullName(),
						String.join(", ", description.getAuthors()), description.getWebsite()))));
	}
}
