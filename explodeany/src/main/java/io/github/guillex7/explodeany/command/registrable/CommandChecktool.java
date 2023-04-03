package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandChecktool extends RegistrableCommand {
	@Override
	public String getName() {
		return "checktool";
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
