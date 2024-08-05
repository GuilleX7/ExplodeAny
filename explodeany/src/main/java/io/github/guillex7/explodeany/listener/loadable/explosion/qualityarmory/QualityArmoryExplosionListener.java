package io.github.guillex7.explodeany.listener.loadable.explosion.qualityarmory;

import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.LoadableConfigurationSection;
import io.github.guillex7.explodeany.configuration.loadable.qualityarmor.QualityArmoryExplosiveConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityMaterialConfiguration;
import io.github.guillex7.explodeany.data.QualityArmoryExplosive;
import io.github.guillex7.explodeany.explosion.ExplosionManager;
import io.github.guillex7.explodeany.listener.loadable.explosion.BaseConfigurableExplosionListener;
import io.github.guillex7.explodeany.services.DebugManager;

import me.zombie_striker.qg.api.QAProjectileExplodeEvent;
import me.zombie_striker.qg.api.QAThrowableExplodeEvent;
import me.zombie_striker.qg.miscitems.Grenade;
import me.zombie_striker.qg.miscitems.ProxyMines;
import me.zombie_striker.qg.miscitems.StickyGrenades;
import me.zombie_striker.qg.miscitems.ThrowableItems;

public class QualityArmoryExplosionListener extends BaseConfigurableExplosionListener {
    @Override
    public String getName() {
        return "QualityArmory";
    }

    @Override
    public boolean isAnnounceable() {
        return true;
    }

    @EventHandler
    public void onQualityArmoryProjectileExplode(QAProjectileExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }

        QualityArmoryExplosive explosive = QualityArmoryExplosive.fromName(event.getProjectile().getName());
        if (explosive == null) {
            return;
        }

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO,
                    "Detected QualityArmory projectile explosion. Projectile name: {0}", explosive.getName());
        }

        this.handleExplosive(explosive, event.getLocation(), event);
    }

    @EventHandler
    public void onQualityArmoryThrowableExplode(QAThrowableExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }

        final ThrowableItems throwable = event.getThrowable();
        QualityArmoryExplosive explosive;

        if (throwable instanceof StickyGrenades) {
            explosive = QualityArmoryExplosive.STICKY_GRENADE;
        } else if (throwable instanceof ProxyMines) {
            explosive = QualityArmoryExplosive.PROXY_MINE;
        } else if (throwable instanceof Grenade) {
            // Make sure to check for Grenade last, as it's the parent class of
            // StickyGrenades and ProxyMines
            explosive = QualityArmoryExplosive.GRENADE;
        } else {
            return;
        }

        if (DebugManager.getInstance().isDebugEnabled()) {
            ExplodeAny.getInstance().getLogger().log(Level.INFO,
                    "Detected QualityArmory throwable explosion. Throwable name: {0}", explosive.getName());
        }

        this.handleExplosive(explosive, event.getLocation(), event);
    }

    private void handleExplosive(QualityArmoryExplosive explosive, Location location, Cancellable event) {
        Map<Material, EntityMaterialConfiguration> materialConfigurations = this.configuration
                .getEntityMaterialConfigurations().get(explosive.getName());
        EntityConfiguration entityConfiguration = this.configuration.getEntityConfigurations().get(explosive.getName());
        if (materialConfigurations == null || entityConfiguration == null) {
            return;
        }

        if (ExplosionManager.getInstance().manageExplosion(materialConfigurations, entityConfiguration,
                location, explosive.getExplosionRadius())) {
            event.setCancelled(true);
        }
    }

    @Override
    public void unload() {
        QAProjectileExplodeEvent.getHandlerList().unregister(this);
        QAThrowableExplodeEvent.getHandlerList().unregister(this);
    }

    @Override
    protected LoadableConfigurationSection<?> getConfiguration() {
        return ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(QualityArmoryExplosiveConfiguration.getConfigurationId());
    }
}
