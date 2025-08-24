package io.github.guillex7.explodeany.configuration.loadable.qualityarmor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.Version;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.data.QualityArmoryExplosive;
import me.zombie_striker.qg.QAMain;

public class QualityArmoryExplosiveConfiguration extends LoadableConfigurationSection<QualityArmoryExplosive> {
    public static String getConfigurationId() {
        return "QualityArmory";
    }

    private ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }

    @Override
    public String getHumanReadableName() {
        return "QualityArmory explosives";
    }

    @Override
    public boolean shouldBeLoaded() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        final Plugin qualityArmoryPlugin = pluginManager.getPlugin("QualityArmory");
        if (qualityArmoryPlugin == null || !qualityArmoryPlugin.isEnabled()
                || !(qualityArmoryPlugin instanceof QAMain)) {
            return false;
        }

        final Version qualityArmoryVersion = Version.fromString(qualityArmoryPlugin.getDescription().getVersion());
        final Version minimumSupportedQualityArmoryVersion = new Version(2, 0, 10);

        if (qualityArmoryVersion.isBefore(minimumSupportedQualityArmoryVersion)) {
            this.getPlugin().getLogger()
                    .warning(String.format("QualityArmory version %s is not supported. Minimum supported version is %s",
                            qualityArmoryVersion, minimumSupportedQualityArmoryVersion));
            return false;
        }

        return true;
    }

    @Override
    public String getSectionPath() {
        return QualityArmoryExplosiveConfiguration.getConfigurationId();
    }

    @Override
    public String getEntityName(final QualityArmoryExplosive entity) {
        return entity.getName();
    }

    @Override
    public QualityArmoryExplosive getEntityFromName(final String name) {
        return QualityArmoryExplosive.fromName(name);
    }

    @Override
    public List<QualityArmoryExplosive> getEntitiesFromPattern(final Pattern pattern, final String name) {
        final List<QualityArmoryExplosive> matchedEntities = new ArrayList<>();

        for (final QualityArmoryExplosive entity : QualityArmoryExplosive.values()) {
            if (pattern.matcher(entity.getName()).matches()) {
                matchedEntities.add(entity);
            }
        }

        return matchedEntities;
    }
}
