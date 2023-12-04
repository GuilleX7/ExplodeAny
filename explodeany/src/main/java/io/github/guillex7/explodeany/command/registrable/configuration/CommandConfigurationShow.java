package io.github.guillex7.explodeany.command.registrable.configuration;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.util.MessageFormatter;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandConfigurationShow extends RegistrableCommand {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public Set<PermissionNode> getRequiredPermissions() {
        return SetUtils.createHashSetOf(PermissionNode.CONFIGURATION_SHOW);
    }

    @Override
    public String getUsage() {
        return "<section> <entity> [material]";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2 || args.length > 3) {
            return false;
        }

        final ConfigurationManager configurationManager = ConfigurationManager.getInstance();

        final String sectionPath = args[0];
        LoadableConfigurationSection<? extends Object> loadableConfigurationSection = configurationManager
                .getRegisteredLoadableConfigurationSections()
                .get(sectionPath);

        if (loadableConfigurationSection == null) {
            sender.sendMessage(String.format("Section %s does not exist.", sectionPath));
            return true;
        }

        final String entityName = args[1];
        final Object entity = loadableConfigurationSection.getEntityFromName(entityName);

        if (entity == null || !loadableConfigurationSection.getEntityConfigurations().containsKey(entity)) {
            sender.sendMessage(String.format("Entity %s does not exist in section %s.", entityName, sectionPath));
            return true;
        }

        if (args.length == 2) {
            sender.sendMessage(MessageFormatter
                    .colorize(String.format("&8= %s > %s\n%s", sectionPath,
                            loadableConfigurationSection.reifyEntityName(entityName),
                            loadableConfigurationSection.getEntityConfigurations().get(entity).toString())));
            return true;
        }

        final String materialName = args[2];
        final Material material = loadableConfigurationSection.getMaterialFromName(materialName);

        if (material == null
                || !loadableConfigurationSection.getEntityMaterialConfigurations().get(entity).containsKey(material)) {
            sender.sendMessage(String.format("Material %s does not exist for entity %s in section %s.", materialName,
                    entityName, sectionPath));
            return true;
        }

        sender.sendMessage(MessageFormatter
                .colorize(String.format("&8= %s > %s > %s\n%s", sectionPath,
                        loadableConfigurationSection.reifyEntityName(entityName), material.name(),
                        loadableConfigurationSection.getEntityMaterialConfigurations().get(entity).get(material)
                                .toString())));
        return true;
    }

    @Override
    public void onTabComplete(CommandSender sender, String[] args, List<String> autocompletion) {
        final ConfigurationManager configurationManager = ConfigurationManager.getInstance();

        if (args.length == 1) {
            autocompletion.addAll(configurationManager.getRegisteredLoadableConfigurationSections()
                    .keySet().stream().filter(sectionPath -> sectionPath.startsWith(args[0]))
                    .collect(Collectors.toList()));
        } else if (args.length >= 2) {
            final String sectionPath = args[0];

            LoadableConfigurationSection<? extends Object> loadableConfigurationSection = ConfigurationManager
                    .getInstance().getRegisteredLoadableConfigurationSections().get(sectionPath);

            if (loadableConfigurationSection == null) {
                return;
            }

            if (args.length == 2) {
                autocompletion.addAll(loadableConfigurationSection.getEntitiesNames().stream()
                        .filter(entityName -> entityName.startsWith(args[1])).collect(Collectors.toList()));
                return;
            } else if (args.length == 3) {
                final String entityName = args[1];
                final Object entity = loadableConfigurationSection.getEntityFromName(entityName);

                if (entity == null || !loadableConfigurationSection.getEntityConfigurations().containsKey(entity)) {
                    return;
                }

                autocompletion
                        .addAll(loadableConfigurationSection.getEntityMaterialConfigurations().get(entity).keySet()
                                .stream()
                                .map(Material::name).filter(materialName -> materialName.startsWith(args[2]))
                                .collect(Collectors.toList()));
            }
        }
    }
}
