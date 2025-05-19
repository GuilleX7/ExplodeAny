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

        if (!rootCommand.isCommandSenderAllowedToUse(sender)) {
            sender.sendMessage(ConfigurationManager.getInstance().getLocale(ConfigurationLocale.NOT_ALLOWED));
            return true;
        }

        if (!rootCommand.execute(sender, Arrays.copyOfRange(args, i, args.length))) {
            String usageDescription;
            if (!rootCommand.isTerminal()) {
                final String possibleSubcommands = rootCommand.getSubcommands()
                        .stream().filter(subcommand -> subcommand.isCommandSenderAllowedToUse(sender))
                        .map(RegistrableCommand::getName)
                        .reduce((subcommandAName, subcommandBName) -> subcommandAName + "/" + subcommandBName)
                        .orElse("no subcommand available");
                usageDescription = String.format("/%s &7<%s>", breadcrumbsBuilder.toString(), possibleSubcommands);

            } else {
                usageDescription = String.format("/%s &7%s", breadcrumbsBuilder.toString(), rootCommand.getUsage());
            }

            sender.sendMessage(
                    ConfigurationManager.getInstance().getLocale(ConfigurationLocale.USAGE)
                            .replace("%DESCRIPTION%", MessageFormatter.colorize(usageDescription)));
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
            final RegistrableCommand subcommand = rootCommand.getMappedSubcommands().get(args[i]);
            if (subcommand == null) {
                break;
            }
            rootCommand = subcommand;
        }

        // Hint: i == args.length (>= for safety) if a subcommand was fully written
        // already and no space was written after it, i.e. waiting for next space
        if (i >= args.length) {
            return autocompletion;
        }

        if (!rootCommand.isTerminal()) {
            final String lastWrittenSubcommand = args[i];

            for (RegistrableCommand subcommand : rootCommand.getSubcommands()) {
                if (subcommand.isCommandSenderAllowedToUse(sender)) {
                    subcommand.getAllNames().stream().filter(x -> x.startsWith(lastWrittenSubcommand))
                            .forEach(autocompletion::add);
                }
            }
        } else if (rootCommand.isCommandSenderAllowedToUse(sender)) {
            rootCommand.onTabComplete(sender, Arrays.copyOfRange(args, i, args.length), autocompletion);
        }

        return autocompletion;
    }
}
