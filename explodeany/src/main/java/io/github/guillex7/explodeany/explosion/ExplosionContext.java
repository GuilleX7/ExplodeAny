package io.github.guillex7.explodeany.explosion;

import java.util.EnumSet;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;

import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;

public class ExplosionContext {
    private final Map<Material, EntityMaterialConfiguration> materialConfigurations;
    private final EntityConfiguration entityConfiguration;
    private final Location sourceLocation;
    private final double originalRawExplosionRadius;
    private final EnumSet<ExplosionFlag> flags;

    /* CoreProtect integration */
    private String coreProtectEntityIdentifier;

    private ExplosionContext(final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final EntityConfiguration entityConfiguration, final Location sourceLocation,
            final double originalRawExplosionRadius, final EnumSet<ExplosionFlag> flags) {
        this.materialConfigurations = materialConfigurations;
        this.entityConfiguration = entityConfiguration;
        this.sourceLocation = sourceLocation;
        this.originalRawExplosionRadius = originalRawExplosionRadius;
        this.flags = flags;

        this.coreProtectEntityIdentifier = "#eany";
    }

    public static ExplosionContext of(
            final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final EntityConfiguration entityConfiguration, final Location sourceLocation,
            final double originalRawExplosionRadius) {
        return new ExplosionContext(materialConfigurations, entityConfiguration, sourceLocation,
                originalRawExplosionRadius, EnumSet.noneOf(ExplosionFlag.class));
    }

    public static ExplosionContext of(
            final Map<Material, EntityMaterialConfiguration> materialConfigurations,
            final EntityConfiguration entityConfiguration, final Location sourceLocation,
            final double originalRawExplosionRadius, final EnumSet<ExplosionFlag> flags) {
        return new ExplosionContext(materialConfigurations, entityConfiguration, sourceLocation,
                originalRawExplosionRadius, flags);
    }

    public ExplosionContext setCoreProtectEntityIdentifier(final String identifier) {
        this.coreProtectEntityIdentifier = identifier;
        return this;
    }

    public Map<Material, EntityMaterialConfiguration> getMaterialConfigurations() {
        return materialConfigurations;
    }

    public EntityConfiguration getEntityConfiguration() {
        return entityConfiguration;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public double getOriginalRawExplosionRadius() {
        return originalRawExplosionRadius;
    }

    public EnumSet<ExplosionFlag> getFlags() {
        return flags;
    }

    public String getCoreProtectEntityIdentifier() {
        return coreProtectEntityIdentifier;
    }
}
