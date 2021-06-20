package io.github.guillex7.explodeany.command.registrable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEany extends RegistrableCommand {
	private CommandEany() {
		super();
	}

	public static CommandEany empty() {
		return new CommandEany();
	}

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
		return new ArrayList<RegistrableCommand>(
				Arrays.asList(CommandAbout.empty(), CommandChecktool.empty(), CommandReload.empty()));
	}
}
