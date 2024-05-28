package io.github.guillex7.explodeany.command.registrable;

import java.util.Arrays;
import java.util.List;

import io.github.guillex7.explodeany.command.registrable.checktool.CommandChecktool;
import io.github.guillex7.explodeany.command.registrable.configuration.CommandConfiguration;
import io.github.guillex7.explodeany.command.registrable.debug.CommandDebug;

public class CommandEany extends RegistrableCommand {
    @Override
    public String getName() {
        return "explodeany";
    }

    @Override
    public List<RegistrableCommand> getSubcommands() {
        return Arrays.asList(new CommandAbout(), new CommandChecktool(), new CommandConfiguration(),
                new CommandReload(), new CommandDebug());
    }
}
