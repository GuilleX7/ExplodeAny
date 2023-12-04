package io.github.guillex7.explodeany.command.registrable.checktool;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public class CommandChecktoolToggle extends RegistrableCommand {
    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public Set<String> getAliases() {
        return SetUtils.createHashSetOf("t");
    }

    @Override
    public Set<PermissionNode> getRequiredPermissions() {
        return SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_TOGGLE);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player receiver;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(
                        ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED));
                return true;
            }

            receiver = (Player) sender;
        } else if (sender.hasPermission(PermissionNode.CHECKTOOL_TOGGLE_OTHERS.getKey())) {
            String receiverName = args[0];
            Player possibleReceiver = Bukkit.getPlayer(receiverName);

            if (possibleReceiver == null) {
                sender.sendMessage(
                        ConfigurationManager.getInstance().getLocale(ConfigurationLocale.PLAYER_DOESNT_EXIST)
                                .replace(
                                        "%NAME%", receiverName));
                return true;
            }

            if (!possibleReceiver.isOnline()) {
                sender.sendMessage(
                        ConfigurationManager.getInstance().getLocale(ConfigurationLocale.PLAYER_IS_OFFLINE).replace(
                                "%NAME%", receiverName));
                return true;
            }

            receiver = possibleReceiver;
        } else {
            sender.sendMessage(
                    ConfigurationManager.getInstance().getLocale(ConfigurationLocale.NOT_ALLOWED));
            return true;
        }

        final boolean areSenderAndReceiverSamePlayer = receiver.getName() == sender.getName();
        final ChecktoolManager checktoolManager = ChecktoolManager.getInstance();
        final String checktoolItemName = checktoolManager.getChecktool().getType().name();

        ConfigurationLocale receiverLocaleMessage;
        ConfigurationLocale senderLocaleMessage;

        if (checktoolManager.getPlayersUsingChecktool().contains(receiver)) {
            checktoolManager.getPlayersUsingChecktool().remove(receiver);
            receiverLocaleMessage = ConfigurationLocale.LEAVE_CHECKTOOL_MODE;
            senderLocaleMessage = ConfigurationLocale.CHECKTOOL_TOGGLED_OFF;
        } else {
            checktoolManager.getPlayersUsingChecktool().add(receiver);
            receiverLocaleMessage = ConfigurationLocale.ENTER_CHECKTOOL_MODE;
            senderLocaleMessage = ConfigurationLocale.CHECKTOOL_TOGGLED_ON;
        }

        receiver.sendMessage(
                ConfigurationManager.getInstance().getLocale(receiverLocaleMessage)
                        .replace("%ITEM%", checktoolItemName)
                        .replace("%PRETTY_ITEM%", StringUtils.beautifyName(checktoolItemName)));
        if (!areSenderAndReceiverSamePlayer) {
            sender.sendMessage(
                    ConfigurationManager.getInstance().getLocale(senderLocaleMessage)
                            .replace("%NAME%", receiver.getName()));
        }
        return true;
    }

    @Override
    public void onTabComplete(CommandSender sender, String[] args, List<String> autocompletion) {
        if (args.length == 1 && sender.hasPermission(PermissionNode.CHECKTOOL_TOGGLE_OTHERS.getKey())) {
            Bukkit.getOnlinePlayers().stream().filter(x -> x.getName().startsWith(args[0]))
                    .forEach(x -> autocompletion.add(x.getName()));
        }
    }
}
