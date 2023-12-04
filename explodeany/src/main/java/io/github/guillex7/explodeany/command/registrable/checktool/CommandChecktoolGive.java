package io.github.guillex7.explodeany.command.registrable.checktool;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public class CommandChecktoolGive extends RegistrableCommand {
    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_GIVE);

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(CommandSender sender) {
        return REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
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
        } else {
            String receiverName = args[0];
            Player possibleReceiver = Bukkit.getPlayer(receiverName);

            if (possibleReceiver == null) {
                sender.sendMessage(
                        ConfigurationManager.getInstance().getLocale(ConfigurationLocale.PLAYER_DOESNT_EXIST)
                                .replace("%NAME%", receiverName));
                return true;
            }

            if (!possibleReceiver.isOnline()) {
                sender.sendMessage(
                        ConfigurationManager.getInstance().getLocale(ConfigurationLocale.PLAYER_IS_OFFLINE).replace(
                                "%NAME%", receiverName));
                return true;
            }

            receiver = possibleReceiver;
        }

        final ItemStack checktool = ChecktoolManager.getInstance().getChecktool();
        receiver.getInventory().addItem(checktool);

        sender.sendMessage(
                ConfigurationManager.getInstance()
                        .getLocale(ConfigurationLocale.CHECKTOOL_GIVEN)
                        .replace("%NAME%", receiver.getName())
                        .replace("%ITEM%", checktool.getType().name())
                        .replace("%PRETTY_ITEM%", StringUtils.beautifyName(checktool.getType().name())));
        return true;
    }

    @Override
    public void onTabComplete(CommandSender sender, String[] args, List<String> autocompletion) {
        if (args.length == 1) {
            Bukkit.getOnlinePlayers().stream().filter(x -> x.getName().startsWith(args[0]))
                    .forEach(x -> autocompletion.add(x.getName()));
        }
    }
}
