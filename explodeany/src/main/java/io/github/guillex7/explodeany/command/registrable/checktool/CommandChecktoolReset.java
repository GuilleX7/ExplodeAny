package io.github.guillex7.explodeany.command.registrable.checktool;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.services.ChecktoolManager;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandChecktoolReset extends RegistrableCommand {
    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_RESET);

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(final CommandSender sender) {
        return this.REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        ChecktoolManager.getInstance().setChecktool(new ItemStack(Material.AIR));
        sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_RESET));
        return true;
    }
}
