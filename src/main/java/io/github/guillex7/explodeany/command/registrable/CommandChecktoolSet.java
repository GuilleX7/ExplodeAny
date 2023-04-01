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
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandChecktoolSet extends RegistrableCommand {
	@Override
	public String getName() {
		return "set";
	}

	@Override
	public List<String> getRequiredPermissions() {
		return new ArrayList<String>(Arrays.asList("explodeany.checktool.set"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED)));
			return true;
		}

		Player player = (Player) sender;
		ItemStack newTool = new ItemStack(player.getInventory().getItemInMainHand());
		newTool.setAmount(1);
		if (ChecktoolManager.setChecktool(newTool)) {
			sender.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_SET)));
		} else {
			sender.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_NOT_PERSISTED)));
		}
		return true;
	}
}
