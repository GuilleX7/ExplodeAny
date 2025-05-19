package io.github.guillex7.explodeany.command.registrable.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import io.github.guillex7.explodeany.command.registrable.RegistrableCommand;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.PermissionNode;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.section.ChecktoolConfiguration;
import io.github.guillex7.explodeany.configuration.section.MaterialConfiguration;
import io.github.guillex7.explodeany.configuration.section.WorldHoleProtection;
import io.github.guillex7.explodeany.util.MessageFormatter;
import io.github.guillex7.explodeany.util.SetUtils;

public class CommandConfigurationShow extends RegistrableCommand {
    private static final String GLOBAL_SECTION_NAME = "Global";
    private static final String CHECKTOOL_SECTION_NAME = "Checktool";
    private static final String WORLD_HOLE_PROTECTION_SECTION_NAME = "WorldHoleProtection";
    private static final String MATERIAL_SECTION_NAME = "Material";

    private final List<String> fixedSectionNames;

    private final Set<PermissionNode> REQUIRED_PERMISSIONS = SetUtils
            .createHashSetOf(PermissionNode.CONFIGURATION_SHOW);

    public CommandConfigurationShow() {
        fixedSectionNames = Arrays.asList(GLOBAL_SECTION_NAME, CHECKTOOL_SECTION_NAME,
                WORLD_HOLE_PROTECTION_SECTION_NAME, MATERIAL_SECTION_NAME);
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public boolean isCommandSenderAllowedToUse(CommandSender sender) {
        return this.REQUIRED_PERMISSIONS.stream().allMatch(permission -> sender.hasPermission(permission.getKey()));
    }

    @Override
    public String getUsage() {
        return "<section> [...other parameters]";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return false;
        }

        final ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        final String sectionPath = args[0];
        switch (sectionPath) {
            case CommandConfigurationShow.GLOBAL_SECTION_NAME:
                return showGlobalConfiguration(sender, configurationManager);
            case CommandConfigurationShow.CHECKTOOL_SECTION_NAME:
                return showChecktoolConfiguration(sender, sectionPath, configurationManager);
            case CommandConfigurationShow.WORLD_HOLE_PROTECTION_SECTION_NAME:
                return showWorldHoleProtectionConfiguration(sender, sectionPath, configurationManager, args);
            case CommandConfigurationShow.MATERIAL_SECTION_NAME:
                return showMaterialConfiguration(sender, sectionPath, configurationManager, args);
            default:
                return showEntityOrEntityMaterialConfiguration(sender, sectionPath, configurationManager, args);
        }
    }

    private boolean showGlobalConfiguration(CommandSender sender,
            ConfigurationManager configurationManager) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MessageFormatter.colorize(String.format("&8= %s\n", "Global properties")));
        stringBuilder.append(String.format("&fUse block database: %s\n",
                configurationManager.doUseBlockDatabase()));
        stringBuilder.append(String.format("&fSanitize block database at startup: %s\n",
                configurationManager.doCheckBlockDatabaseAtStartup()));
        stringBuilder.append(String.format("&fDefault block durability: %.2f\n",
                configurationManager.getGlobalBlockDurability()));
        stringBuilder.append(String.format("&fEnable metrics: %s\n",
                configurationManager.doEnableMetrics()));
        stringBuilder.append(String.format("&fDisabled worlds: %s\n",
                configurationManager.getDisabledWorlds().isEmpty() ? "none"
                : configurationManager.getDisabledWorlds().stream().collect(Collectors.joining(", "))));
        sender.sendMessage(MessageFormatter.colorize(stringBuilder.toString()));
        return true;
    }

    private boolean showChecktoolConfiguration(CommandSender sender, String sectionPath,
            ConfigurationManager configurationManager) {
        ChecktoolConfiguration checktoolConfiguration = configurationManager.getChecktoolConfiguration();
        sender.sendMessage(MessageFormatter.colorize(String.format(
                "&8= %s\n&f%s", sectionPath, checktoolConfiguration.toString())));
        return true;
    }

    public boolean showWorldHoleProtectionConfiguration(CommandSender sender, String sectionPath,
            ConfigurationManager configurationManager,
            String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Command usage: /configuration show WorldHoleProtection <worldName>");
            return true;
        }

        final String worldName = args[1];
        final WorldHoleProtection worldHoleProtection = configurationManager.getWorldHoleProtection(worldName);
        if (worldHoleProtection == null) {
            sender.sendMessage(String.format("World %s does not exist in section %s.", worldName, sectionPath));
            return true;
        }

        sender.sendMessage(MessageFormatter.colorize(String.format(
                "&8= %s > %s\n%s", "World hole protection", worldName, worldHoleProtection.toString())));
        return true;
    }

    private boolean showMaterialConfiguration(CommandSender sender, String sectionPath,
            ConfigurationManager configurationManager,
            String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Command usage: /configuration show Material <materialName>");
            return true;
        }

        final String materialName = args[1];
        Material material = Material.getMaterial(materialName);
        if (material == null) {
            sender.sendMessage(String.format("Material %s is invalid", materialName, sectionPath));
            return true;
        }

        final MaterialConfiguration materialConfiguration = configurationManager.getMaterialConfiguration(material);
        if (materialConfiguration == null) {
            sender.sendMessage(String.format("Material %s does not exist in section %s.", materialName, sectionPath));
            return true;
        }

        sender.sendMessage(MessageFormatter.colorize(String.format(
                "&8= %s > %s\n%s", "Material", material.toString(),
                materialConfiguration.toString())));
        return true;
    }

    private boolean showEntityOrEntityMaterialConfiguration(CommandSender sender, String sectionPath,
            ConfigurationManager configurationManager,
            String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Command usage: /configuration show <section> <entityName> [<materialName>]");
            return true;
        }

        final LoadableConfigurationSection<? extends Object> loadableConfigurationSection = configurationManager
                .getRegisteredConfigurationSectionByPath(sectionPath);
        if (loadableConfigurationSection == null) {
            return false;
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
        if (args.length < 1) {
            return;
        }

        final ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        final String userSectionName = args[0];
        if (args.length == 1) {
            autocompletion.addAll(fixedSectionNames.stream().filter(sectionName -> sectionName
                    .startsWith(userSectionName)).collect(Collectors.toList()));
            autocompletion.addAll(configurationManager.getRegisteredConfigurationSectionsByPath()
                    .keySet().stream().filter(sectionPath -> sectionPath.startsWith(userSectionName))
                    .collect(Collectors.toList()));
            return;
        }

        switch (userSectionName) {
            case CommandConfigurationShow.GLOBAL_SECTION_NAME:
                return;
            case CommandConfigurationShow.CHECKTOOL_SECTION_NAME:
                return;
            case CommandConfigurationShow.WORLD_HOLE_PROTECTION_SECTION_NAME:
                if (args.length == 2) {
                    autocompletion.addAll(configurationManager.getWorldHoleProtectionWorldNames().stream()
                            .filter(worldName -> worldName.startsWith(args[1])).collect(Collectors.toList()));
                }
                return;
            case CommandConfigurationShow.MATERIAL_SECTION_NAME:
                if (args.length == 2) {
                    autocompletion.addAll(configurationManager.getMaterialConfigurationNames().stream()
                            .filter(materialName -> materialName.startsWith(args[1])).collect(Collectors.toList()));
                }
                return;
            default: {
                LoadableConfigurationSection<? extends Object> loadableConfigurationSection = ConfigurationManager
                        .getInstance().getRegisteredConfigurationSectionsByPath().get(userSectionName);
                if (loadableConfigurationSection == null) {
                    return;
                }

                final String userEntityName = args[1];
                if (args.length == 2) {
                    autocompletion.addAll(loadableConfigurationSection.getLoadedEntityNames().stream()
                            .filter(entityName -> entityName.startsWith(userEntityName)).collect(Collectors.toList()));
                    return;
                }

                if (args.length == 3) {
                    final Object entity = loadableConfigurationSection.getEntityFromName(userEntityName);
                    if (entity == null || !loadableConfigurationSection.getEntityConfigurations().containsKey(entity)) {
                        return;
                    }

                    final String userMaterialName = args[2];
                    autocompletion
                            .addAll(loadableConfigurationSection.getEntityMaterialConfigurations().get(entity).keySet()
                                    .stream()
                                    .map(Material::name)
                                    .filter(materialName -> materialName.startsWith(userMaterialName))
                                    .collect(Collectors.toList()));
                }
            }
        }
    }
}
