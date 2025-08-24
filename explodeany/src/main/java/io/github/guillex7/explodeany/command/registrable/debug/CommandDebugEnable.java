package io.github.guillex7.explodeany.command.registrable.debug;

import java.util.Set;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.services.DebugManager;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandDebugEnable extends RegistrableCommand {
    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils
            .createHashSetOf(PermissionNode.DEBUG_ENABLE);

    @Override
    public String getName() {
        return "enable";
    }

    @Override
    public Set<String> getAliases() {
        return SetUtils.createHashSetOf("on");
    }

    @Override
    public boolean isCommandSenderAllowedToUse(final CommandSender sender) {
        return this.REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        DebugManager.getInstance().setIsDebugEnabled(true);
        sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.DEBUG_ENABLED));
        return true;
    }
}
