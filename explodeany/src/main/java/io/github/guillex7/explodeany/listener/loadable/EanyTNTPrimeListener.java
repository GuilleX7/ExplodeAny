package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import io.github.guillex7.explodeany.compat.common.event.EanyTNTPrimeEvent;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.configuration.loadable.vanilla.entity.RegularVanillaEntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.EntityConfiguration;
import io.github.guillex7.explodeany.configuration.section.specific.TNTSpecificEntityConfiguration;
import io.github.guillex7.explodeany.data.ExplodingVanillaEntity;

public class EanyTNTPrimeListener implements LoadableListener {
    private boolean doPreventExplosionChaining = false;

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    @Override
    public void load() {
        final EntityConfiguration tntConfiguration = ConfigurationManager.getInstance()
                .getRegisteredConfigurationSectionByPath(RegularVanillaEntityConfiguration.getConfigurationId())
                .getEntityConfigurations().get(ExplodingVanillaEntity.PRIMED_TNT);
        if (tntConfiguration == null) {
            return;
        }

        if (tntConfiguration.getSpecificConfiguration() instanceof TNTSpecificEntityConfiguration) {
            final TNTSpecificEntityConfiguration specificConfig = (TNTSpecificEntityConfiguration) tntConfiguration
                    .getSpecificConfiguration();
            this.doPreventExplosionChaining = specificConfig.doDisableExplosionChaining();
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onTNTPrime(final EanyTNTPrimeEvent event) {
        if (event.getReason() == EanyTNTPrimeEvent.PrimeReason.EXPLOSION && this.doPreventExplosionChaining) {
            event.setCancelled(true);
        }
    }

    @Override
    public void unload() {
        EanyTNTPrimeEvent.getHandlerList().unregister(this);
    }
}
