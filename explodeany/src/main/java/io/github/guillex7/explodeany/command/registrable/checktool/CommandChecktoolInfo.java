package io.github.guillex7.explodeany.command.registrable.checktool;

import java.util.Set;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public class CommandChecktoolInfo extends RegistrableCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public Set<PermissionNode> getRequiredPermissions() {
        return SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_INFO);
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
