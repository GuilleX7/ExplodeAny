package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.configuration.PermissionNode;

public abstract class RegistrableCommand {
    private Map<String, RegistrableCommand> mappedSubcommands = null;
    private Set<String> allNames = null;

    protected RegistrableCommand() {
        mapSubcommands();
        mapAllNames();
    }

    public abstract String getName();

    public Set<String> getAliases() {
        return new HashSet<>();
    }

    public String getUsage() {
        return "";
    }

    public Set<PermissionNode> getRequiredPermissions() {
        return new HashSet<>();
    }

    public List<RegistrableCommand> getSubcommands() {
        return new ArrayList<>();
    }

    private final void mapSubcommands() {
        if (mappedSubcommands == null) {
            mappedSubcommands = new HashMap<>();
        } else {
            mappedSubcommands.clear();
        }

        for (RegistrableCommand subcommand : getSubcommands()) {
            mappedSubcommands.put(subcommand.getName(), subcommand);
            for (String subcommandAlias : subcommand.getAliases()) {
                mappedSubcommands.put(subcommandAlias, subcommand);
            }
        }
    }

    private final void mapAllNames() {
        if (allNames == null) {
            allNames = new HashSet<>();
        } else {
            allNames.clear();
        }

        allNames.add(getName());
        allNames.addAll(getAliases());
    }

    public final Map<String, RegistrableCommand> getMappedSubcommands() {
        return mappedSubcommands;
    }

    public final Set<String> getAllNames() {
        return allNames;
    }

    public boolean execute(CommandSender sender, String[] args) {
        return false;
    }
}
