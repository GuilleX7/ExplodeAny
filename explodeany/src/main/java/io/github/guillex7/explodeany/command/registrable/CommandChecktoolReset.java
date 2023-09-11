package io.github.guillex7.explodeany.command.registrable;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.command.registrable.checktool.ChecktoolManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandChecktoolReset extends RegistrableCommand {
    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public Set<PermissionNode> getRequiredPermissions() {
        return SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_RESET);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        ChecktoolManager.getInstance().setChecktool(new ItemStack(Material.AIR));
        sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_RESET));
        return true;
    }
}
