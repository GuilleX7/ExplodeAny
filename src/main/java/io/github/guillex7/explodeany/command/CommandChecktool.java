package io.github.guillex7.explodeany.command;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.utils.MessageFormatter;

public class CommandChecktool {
	private final static String CHECKTOOL_PERMISSION_NODE = "explodeany.checktool";

	private final static Set<Player> playersUsingChecktool = new HashSet<Player>();

	public final static Set<Player> getPlayersUsingChecktool() {
		return playersUsingChecktool;
	}

	public static void executor(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission(CHECKTOOL_PERMISSION_NODE)) {
			sender.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.NOT_ALLOWED)));
			return;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED)));
			return;
		}

		Player player = (Player) sender;
		if (getPlayersUsingChecktool().contains(player)) {
			getPlayersUsingChecktool().remove(player);
			player.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.LEAVE_CHECKTOOL_MODE)));
		} else {
			getPlayersUsingChecktool().add(player);
			player.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ENTER_CHECKTOOL_MODE)));
		}
	}
}
