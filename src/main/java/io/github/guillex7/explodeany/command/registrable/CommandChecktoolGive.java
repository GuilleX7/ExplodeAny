package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.command.registrable.checktool.ChecktoolManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandChecktoolGive extends RegistrableCommand {
	@Override
	public String getName() {
		return "give";
	}

	@Override
	public List<String> getRequiredPermissions() {
		return new ArrayList<String>(Arrays.asList("explodeany.checktool.give"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		Player receiver;

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(MessageFormatter
						.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED)));
				return true;
			}

			receiver = (Player) sender;
		} else {
			String receiverName = args[0];
			Player possibleReceiver = Bukkit.getPlayer(receiverName);

			if (possibleReceiver == null) {
				sender.sendMessage(MessageFormatter
						.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.PLAYER_DOESNT_EXIST)));
				return true;
			}

			if (!possibleReceiver.isOnline()) {
				sender.sendMessage(MessageFormatter
						.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.PLAYER_IS_OFFLINE)));
				return true;
			}

			receiver = possibleReceiver;
		}

		receiver.getInventory().addItem(ChecktoolManager.getChecktool());
		sender.sendMessage(MessageFormatter.sign(ConfigurationManager.getInstance()
				.getLocale(ConfigurationLocale.CHECKTOOL_GIVEN).replaceAll("%NAME%", receiver.getName())));
		return true;
	}
}
