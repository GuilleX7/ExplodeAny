package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.Material;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class CustomVanillaEntityConfiguration extends LoadableConfigurationSection<String> {
    public static String getConfigurationId() {
        return "CustomEntity";
    }

    private ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }

    @Override
    public String getHumanReadableName() {
        return "Custom/modded entities and blocks";
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public String getSectionPath() {
        return CustomVanillaEntityConfiguration.getConfigurationId();
    }

    @Override
    protected boolean areEntityAndMaterialConfigurationsValid(final String entity,
            final EntityConfiguration entityConfiguration,
            final Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        if (entityConfiguration.getExplosionRadius() <= 0) {
            this.getPlugin().getLogger().log(Level.WARNING,
                    "Invalid configuration for custom entity {0}: explosion radius must be explicitly set to a positive value.",
                    entity);
            return false;
        }

        return true;
    }

    @Override
    public String getEntityName(final String entity) {
        return entity;
    }

    @Override
    public String getEntityFromName(final String name) {
        final String uppercasedName = name.toUpperCase();
        return ExplodingVanillaEntity.fromEntityTypeName(uppercasedName) == null ? uppercasedName : null;
    }

    @Override
    public List<String> getEntitiesFromPattern(final Pattern pattern, final String name) {
        return Arrays.asList(name);
    }
}
