package io.github.guillex7.explodeany.command.registrable;

import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.command.registrable.checktool.ChecktoolManager;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandChecktoolSet extends RegistrableCommand {
    @Override
    public String getName() {
        return "set";
    }
    
    @Override
    public Set<PermissionNode> getRequiredPermissions() {
        return SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_SET);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.ONLY_PLAYER_ALLOWED));
            return true;
        }

        Player player = (Player) sender;
        ItemStack newTool = new ItemStack(CompatibilityManager.getInstance().getApi().getPlayerInventoryUtils()
                .getItemInMainHand(player.getInventory()));
        newTool.setAmount(1);
        if (ChecktoolManager.getInstance().setChecktool(newTool)) {
            sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_SET));
        } else {
            sender.sendMessage(
                    ConfigurationManager.getInstance().getLocale(ConfigurationLocale.CHECKTOOL_NOT_PERSISTED));
        }
        return true;
    }
}
