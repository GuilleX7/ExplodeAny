package io.github.guillex7.explodeany.command.registrable.debug;

import java.util.Arrays;
import java.util.List;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;

public class CommandDebug extends RegistrableCommand {
    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public List<RegistrableCommand> getSubcommands() {
        return Arrays.asList(new CommandDebugEnable(), new CommandDebugDisable());
    }
}
