package io.github.guillex7.explodeany.command.registrable;

import java.util.Arrays;
import java.util.List;

public class CommandEany extends RegistrableCommand {
    @Override
    public String getName() {
        return "explodeany";
    }

    @Override
    public String getUsage() {
        return "[reload|about|checktool]";
    }

    @Override
    public List<RegistrableCommand> getSubcommands() {
        return Arrays.asList(new CommandAbout(), new CommandChecktool(), new CommandReload());
    }
}
