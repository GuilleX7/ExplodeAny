package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.command.registrable.checktool.ChecktoolManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;

public class CommandChecktoolSet extends RegistrableCommand {
	@Override
	public String getName() {
		return "set";
	}

	@Override
	public List<PermissionNode> getRequiredPermissions() {
		return new ArrayList<>(Arrays.asList(PermissionNode.CHECKTOOL_SET));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED));
			return true;
		}

		Player player = (Player) sender;
		ItemStack newTool = new ItemStack(player.getInventory().getItemInMainHand());
		newTool.setAmount(1);
		if (ChecktoolManager.getInstance().setChecktool(newTool)) {
			sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_SET));
		} else {
			sender.sendMessage(
					ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_NOT_PERSISTED));
		}
		return true;
	}
}
