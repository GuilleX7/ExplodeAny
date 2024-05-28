package io.github.guillex7.explodeany.command.registrable.checktool;

import java.util.Set;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.services.ChecktoolManager;
import io.github.guillex7.explodeany.util.SetUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public class CommandChecktoolInfo extends RegistrableCommand {
    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_INFO);

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(CommandSender sender) {
        return REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        final String checktoolItemName = ChecktoolManager.getInstance().getChecktool().getType().name();
        sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_INFO)
                .replace("%ITEM%", checktoolItemName)
                .replace("%PRETTY_ITEM%",
                        StringUtils.beautifyName(checktoolItemName)));
        return true;
    }
}
