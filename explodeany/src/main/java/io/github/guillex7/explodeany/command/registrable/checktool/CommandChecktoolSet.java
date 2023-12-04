package io.github.guillex7.explodeany.command.registrable.checktool;

import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;
import io.github.guillex7.explodeany.util.StringUtils;

public class CommandChecktoolSet extends RegistrableCommand {
    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils.createHashSetOf(PermissionNode.CHECKTOOL_SET);

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(CommandSender sender) {
        return REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
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

        final ConfigurationLocale senderLocaleMessage = ChecktoolManager.getInstance().setChecktool(newTool)
                ? ConfigurationLocale.CHECKTOOL_SET
                : ConfigurationLocale.CHECKTOOL_NOT_PERSISTED;

        sender.sendMessage(ConfigurationManager.getInstance().getLocale(senderLocaleMessage)
                .replace("%ITEM%", newTool.getType().name())
                .replace("%PRETTY_ITEM%", StringUtils.beautifyName(newTool.getType().name())));

        return true;
    }
}
