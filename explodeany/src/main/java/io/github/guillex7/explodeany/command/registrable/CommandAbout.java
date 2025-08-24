package io.github.guillex7.explodeany.command.registrable;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.util.MessageFormatter;

public class CommandAbout extends RegistrableCommand {
    @Override
    public String getName() {
        return "about";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(final CommandSender sender) {
        return true;
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        final PluginDescriptionFile description = ExplodeAny.getInstance().getDescription();
        sender.sendMessage(
                MessageFormatter.colorize(String.format("&f%s\n&7Developed by %s\n&7%s", description.getFullName(),
                        String.join(", ", description.getAuthors()), description.getWebsite())));
        return true;
    }
}
