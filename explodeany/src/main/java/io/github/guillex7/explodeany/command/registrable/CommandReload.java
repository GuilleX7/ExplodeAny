package io.github.guillex7.explodeany.command.registrable;

import java.util.Set;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandReload extends RegistrableCommand {
    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils.createHashSetOf(PermissionNode.RELOAD);

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(final CommandSender sender) {
        return this.REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        ExplodeAny.getInstance().onDisable();
        ExplodeAny.getInstance().onEnable();
        sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.RELOADED));
        return true;
    }
}
