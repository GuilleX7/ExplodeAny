package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.data.EanyMetaPersistentDataType;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.MagicVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class MagicVanillaEntityExplosionHandler implements VanillaEntityExplosionHandler {
    private final String MAGIC_SPAWNED_KEY = "magicspawned";

    private MagicVanillaEntityConfiguration configuration;
    private Plugin magicPlugin;

    @Override
    public boolean shouldBeLoaded() {
        return ConfigurationManager.getInstance()
                .isConfigurationSectionLoaded(MagicVanillaEntityConfiguration.getConfigurationId());
    }

    @Override
    public void load() {
        this.configuration = (MagicVanillaEntityConfiguration) ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(MagicVanillaEntityConfiguration.getConfigurationId());
        this.magicPlugin = Bukkit.getPluginManager().getPlugin("Magic");
    }

    private boolean isEntitySpawnedByMagic(final Entity entity) {
        return this.magicPlugin != null
                && CompatibilityManager.getInstance().getApi().getPersistentStorageUtils().getForEntity(entity)
                        .has(this.magicPlugin, this.MAGIC_SPAWNED_KEY, EanyMetaPersistentDataType.BYTE);
    }

    @Override
    public boolean isEventHandled(final EntityExplodeEvent event) {
        return ExplodingVanillaEntity.isEntityNameValid(event.getEntityType().name())
                && this.isEntitySpawnedByMagic(event.getEntity());
    }

    @Override
    public void onEntityExplode(final EntityExplodeEvent event) {
        final Entity entity = event.getEntity();

        final ExplodingVanillaEntity explodingEntity = ExplodingVanillaEntity.fromEntity(entity);
        final double explosionRadius = explodingEntity.getExplosionRadius();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected Magic entity explosion. Entity type: {0}",
                    explodingEntity.getName());
        }

        final Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(explodingEntity);
        final EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(explodingEntity);

        if (materialConfigurations == null || entityConfiguration == null || explosionRadius == 0d) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getLocation(), explosionRadius)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations, event.blockList(),
                    event.getLocation());
        }
    }

    @Override
    public void unload() {
        /* Do nothing */
    }
}
