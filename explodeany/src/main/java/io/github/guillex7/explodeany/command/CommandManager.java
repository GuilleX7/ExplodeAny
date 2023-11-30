package io.github.guillex7.explodeany.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationLocale;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandManager implements TabExecutor {
    private static CommandManager instance;

    private final Map<String, RegistrableCommand> registeredCommands;

    private CommandManager() {
        this.registeredCommands = new HashMap<>();
    }

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public Map<String, RegistrableCommand> getRegisteredCommands() {
        return registeredCommands;
    }

    public void registerCommand(RegistrableCommand command) {
        this.registeredCommands.put(command.getName(), command);
    }

    public void unregisterAllCommands() {
        this.registeredCommands.clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RegistrableCommand rootCommand = this.getRegisteredCommands().get(command.getName());
        if (rootCommand == null) {
            return true;
        }
        StringBuilder breadcrumbsBuilder = new StringBuilder(rootCommand.getName());

        int i;
        for (i = 0; i < args.length; i++) {
            RegistrableCommand subcommand = rootCommand.getMappedSubcommands().get(args[i]);
            if (subcommand == null) {
                break;
            }
            rootCommand = subcommand;
            breadcrumbsBuilder.append(" ");
            breadcrumbsBuilder.append(rootCommand.getName());
        }

        boolean executable = true;
        for (PermissionNode permissionNode : rootCommand.getRequiredPermissions()) {
            if (!sender.hasPermission(permissionNode.getKey())) {
                executable = false;
                break;
            }
        }

        if (!executable) {
            sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.NOT_ALLOWED));
            return true;
        }

        if (!rootCommand.execute(sender, Arrays.copyOfRange(args, i, args.length))) {
            sender.sendMessage(MessageFormatter.colorize(
                    String.format("Usage: /%s &7%s", breadcrumbsBuilder.toString(), rootCommand.getUsage())));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> autocompletion = new ArrayList<>();

        RegistrableCommand rootCommand = this.getRegisteredCommands().get(command.getName());
        if (rootCommand == null) {
            return autocompletion;
        }

        int i;
        for (i = 0; i < args.length; i++) {
            RegistrableCommand subcommand = rootCommand.getMappedSubcommands().get(args[i]);
            if (subcommand == null) {
                break;
            }
            rootCommand = subcommand;
        }

        if (args.length - 1 != i) {
            return autocompletion;
        }

        final String lastWrittenSubcommand = args[i];

        for (RegistrableCommand subcommand : rootCommand.getSubcommands()) {
            subcommand.getAllNames().stream().filter(x -> x.startsWith(lastWrittenSubcommand))
                    .forEach(autocompletion::add);
        }

        return autocompletion;
    }
}
