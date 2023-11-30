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
    private Map<String, RegistrableCommand> mappedSubcommands;
    private Set<String> allNames;

    protected RegistrableCommand() {
        this.mapSubcommands();
        this.mapAllNames();
    }

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
        if (this.mappedSubcommands == null) {
            this.mappedSubcommands = new HashMap<>();
        } else {
            this.mappedSubcommands.clear();
        }

        for (RegistrableCommand subcommand : this.getSubcommands()) {
            this.mappedSubcommands.put(subcommand.getName(), subcommand);
            for (String subcommandAlias : subcommand.getAliases()) {
                this.mappedSubcommands.put(subcommandAlias, subcommand);
            }
        }
    }

    private final void mapAllNames() {
        if (this.allNames == null) {
            this.allNames = new HashSet<>();
        } else {
            this.allNames.clear();
        }

        this.allNames.add(this.getName());
        this.allNames.addAll(this.getAliases());
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

    public abstract String getName();
}
