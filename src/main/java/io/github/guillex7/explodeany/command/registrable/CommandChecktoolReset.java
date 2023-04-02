package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.command.registrable.checktool.ChecktoolManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;

public class CommandChecktoolReset extends RegistrableCommand {
	@Override
	public String getName() {
		return "reset";
	}

	@Override
	public List<PermissionNode> getRequiredPermissions() {
		return new ArrayList<>(Arrays.asList(PermissionNode.CHECKTOOL_RESET));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		ChecktoolManager.getInstance().setChecktool(new ItemStack(Material.AIR));
		sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_RESET));
		return true;
	}
}
