package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import io.github.guillex7.explodeany.util.SetUtils;

public class CommandChecktool extends RegistrableCommand {
    @Override
    public String getName() {
        return "checktool";
    }

    @Override
    public Set<String> getAliases() {
        return SetUtils.createHashSetOf("check", "ct");
    }

    @Override
    public String getUsage() {
        return "[toggle|set|give|reset]";
    }

    @Override
    public List<RegistrableCommand> getSubcommands() {
        return new ArrayList<>(Arrays.asList(new CommandChecktoolToggle(),
                new CommandChecktoolReset(), new CommandChecktoolGive(), new CommandChecktoolSet()));
    }
}
