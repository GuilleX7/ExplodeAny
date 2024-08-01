package io.github.guillex7.explodeany.command.registrable;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.SetUtils;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

public class CommandReload extends RegistrableCommand {
    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils.createHashSetOf(PermissionNode.RELOAD);

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(CommandSender sender) {
        return REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Bukkit.getGlobalRegionScheduler().run(ExplodeAny.getInstance(), (ScheduledTask task) -> {
            ExplodeAny.getInstance().onDisable();
            ExplodeAny.getInstance().onEnable();
            sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.RELOADED));
        });
        return true;
    }
}
