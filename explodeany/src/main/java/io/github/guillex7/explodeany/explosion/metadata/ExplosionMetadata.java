package io.github.guillex7.explodeany.explosion.metadata;

import java.util.Map;

import org.bukkit.Material;

import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.explosion.drop.DropCollector;

public class ExplosionMetadata {
    public Map<Material, EntityMaterialConfiguration> materialConfigurations;
    public DropCollector dropCollector;

    public ExplosionMetadata(Map<Material, EntityMaterialConfiguration> materialConfigurations,
            DropCollector dropCollector) {
        this.materialConfigurations = materialConfigurations;
        this.dropCollector = dropCollector;
    }
}
