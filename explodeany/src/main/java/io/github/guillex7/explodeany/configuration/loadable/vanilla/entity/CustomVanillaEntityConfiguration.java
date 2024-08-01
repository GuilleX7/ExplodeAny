package io.github.guillex7.explodeany.configuration.loadable.vanilla.entity;

import java.util.Map;

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
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public String getSectionPath() {
        return CustomVanillaEntityConfiguration.getConfigurationId();
    }

    @Override
    protected boolean areEntityAndMaterialConfigurationsValid(String entity, EntityConfiguration entityConfiguration,
            Map<Material, EntityMaterialConfiguration> materialConfigurations) {
        if (entityConfiguration.getExplosionRadius() <= 0) {
            this.getPlugin().getLogger().warning(
                    "Invalid configuration for custom entity " + entity
                            + ": explosion radius must be explicitly set to a positive value.");
            return false;
        }

        return true;
    }

    @Override
    public String getEntityName(String entity) {
        return entity;
    }

    @Override
    public String getEntityFromName(String name) {
        String uppercasedName = name.toUpperCase();
        return ExplodingVanillaEntity.fromEntityTypeName(uppercasedName) == null ? uppercasedName : null;
    }
}
