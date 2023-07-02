package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.command.registrable.checktool.ChecktoolManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;

public class CommandChecktoolToggle extends RegistrableCommand {
    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public List<PermissionNode> getRequiredPermissions() {
        return new ArrayList<>(Arrays.asList(PermissionNode.CHECKTOOL_TOGGLE));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED));
            return true;
        }

        Player player = (Player) sender;

        ChecktoolManager checktoolManager = ChecktoolManager.getInstance();
        if (checktoolManager.getPlayersUsingChecktool().contains(player)) {
            checktoolManager.getPlayersUsingChecktool().remove(player);
            player.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.LEAVE_CHECKTOOL_MODE));
        } else {
            checktoolManager.getPlayersUsingChecktool().add(player);
            player.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ENTER_CHECKTOOL_MODE));
        }
        return true;
    }
}
