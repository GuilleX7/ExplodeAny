package io.github.guillex7.explodeany.command.registrable.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandConfiguration extends RegistrableCommand {
    @Override
    public String getName() {
        return "configuration";
    }

    @Override
    public Set<String> getAliases() {
        return SetUtils.createHashSetOf("config");
    }

    @Override
    public List<RegistrableCommand> getSubcommands() {
        return Arrays.asList(new CommandConfigurationShow());
    }
}
