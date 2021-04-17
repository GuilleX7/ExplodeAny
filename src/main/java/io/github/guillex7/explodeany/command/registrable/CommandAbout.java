package io.github.guillex7.explodeany.command.registrable;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandAbout extends RegistrableCommand {
	private CommandAbout() {
		super();
	}
	
	public static CommandAbout empty() {
		return new CommandAbout();
	}
	
	@Override
	public String getName() {
		return "about";
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		PluginDescriptionFile description = ExplodeAny.getInstance().getDescription();
		sender.sendMessage(MessageFormatter.sign(
				MessageFormatter.colorize(String.format("&f%s\n&7Developed by %s\n&7%s", description.getFullName(),
						String.join(", ", description.getAuthors()), description.getWebsite()))));
		return true;
	}
}
