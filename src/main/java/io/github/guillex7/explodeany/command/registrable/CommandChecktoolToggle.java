package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandChecktoolToggle extends RegistrableCommand {
	private CommandChecktoolToggle() {
		super();
	}
	
	public static CommandChecktoolToggle empty() {
		return new CommandChecktoolToggle();
	}
	
	@Override
	public String getName() {
		return "toggle";
	}

	@Override
	public List<String> getRequiredPermissions() {
		return new ArrayList<String>(Arrays.asList("explodeany.checktool"));
	}

	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED)));
			return true;
		}

		Player player = (Player) sender;
		ExplodeAny.getInstance().getLogger().log(Level.INFO, player.getInventory().getItem(EquipmentSlot.HAND).serialize().toString());
		
		if (CommandChecktool.getPlayersUsingChecktool().contains(player)) {
			CommandChecktool.getPlayersUsingChecktool().remove(player);
			player.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.LEAVE_CHECKTOOL_MODE)));
		} else {
			CommandChecktool.getPlayersUsingChecktool().add(player);
			player.sendMessage(MessageFormatter
					.sign(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ENTER_CHECKTOOL_MODE)));
		}
		return true;
	}
}
