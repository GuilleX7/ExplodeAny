package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;

public class CommandReload extends RegistrableCommand {
	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public List<PermissionNode> getRequiredPermissions() {
		return new ArrayList<>(Arrays.asList(PermissionNode.RELOAD));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		ExplodeAny.getInstance().onDisable();
		ExplodeAny.getInstance().onEnable();
		sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.RELOADED));
		return true;
	}
}
