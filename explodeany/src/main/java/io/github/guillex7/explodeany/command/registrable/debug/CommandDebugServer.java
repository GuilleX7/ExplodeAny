package io.github.guillex7.explodeany.command.registrable.debug;

import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.services.ServerManager;

public class CommandDebugServer extends RegistrableCommand {
    @Override
    public String getName() {
        return "server";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(CommandSender sender) {
        return true;
    }

    @Override
    public String getUsage() {
        return "<on|off>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return false;
        }

        if ("on".equals(args[0])) {
            if (ServerManager.getInstance().startServer()) {
                sender.sendMessage("Server started");
            } else {
                sender.sendMessage("Server already started");
            }
        } else if ("off".equals(args[0])) {
            if (ServerManager.getInstance().stopServer()) {
                sender.sendMessage("Server stopped");
            } else {
                sender.sendMessage("Server already stopped");
            }
        }

        return true;
    }
}
