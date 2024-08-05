package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Material;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.services.DebugManager;

public class RegularVanillaBlockExplosionListener extends BaseVanillaBlockExplosionListener {
    @Override
    public String getName() {
        return "Vanilla blocks";
    }

    @Override
    public boolean isAnnounceable() {
        return true;
    }

    @Override
    protected void onBlockExplode(EanyBlockExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        ExplodingVanillaEntity explodingVanillaEntity = ExplodingVanillaEntity
                .fromEntityTypeName(event.getBlockMaterial());
        String blockMaterialName = explodingVanillaEntity.getName();
        double explosionRadius = explodingVanillaEntity.getExplosionRadius();

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO, "Detected vanilla block explosion. Block ID: {0}",
                    blockMaterialName);
        }

        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(blockMaterialName);
        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations()
                .get(blockMaterialName);

        if (materialConfigurations == null || entityConfiguration == null || explosionRadius == 0d) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                event.getBlockLocation(), explosionRadius)) {
            event.setCancelled(true);
        } else {
            ExplosionManager.getInstance().removeHandledBlocksFromList(materialConfigurations,
                    event.getBlockList());
        }
    }

    @Override
    protected boolean isEventHandled(EanyBlockExplodeEvent event) {
        return super.isEventHandled(event) && ExplodingVanillaEntity.isEntityNameValid(event.getBlockMaterial());
    }

    @Override
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(RegularVanillaEntityConfiguration.getConfigurationId());
    }
}
