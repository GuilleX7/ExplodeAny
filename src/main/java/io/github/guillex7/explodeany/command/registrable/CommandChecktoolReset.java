package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandChecktoolReset extends RegistrableCommand {
	private CommandChecktoolReset() {
		super();
	}

	public static CommandChecktoolReset empty() {
		return new CommandChecktoolReset();
	}

	@Override
	public String getName() {
		return "reset";
	}

	@Override
	public List<String> getRequiredPermissions() {
		return new ArrayList<String>(Arrays.asList("explodeany.checktool.reset"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		CommandChecktool.setChecktool(new ItemStack(Material.AIR));
		sender.sendMessage(MessageFormatter
				.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_RESET)));
		return true;
	}
}
