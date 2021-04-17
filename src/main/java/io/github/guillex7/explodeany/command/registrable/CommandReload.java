package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandReload extends RegistrableCommand {
	private CommandReload() {
		super();
	}
	
	public static CommandReload empty() {
		return new CommandReload();
	}

	@Override
	public String getName() {
		return "reload";
	}
	
	@Override
	public List<String> getRequiredPermissions() {
		return new ArrayList<String>(Arrays.asList("explodeany.reload"));
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		ExplodeAny.getInstance().onDisable();
		ExplodeAny.getInstance().onEnable();
		sender.sendMessage(
				MessageFormatter.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.RELOADED))
			);
		return true;
	}
}
