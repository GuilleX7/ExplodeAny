package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import org.bukkit.event.Listener;

import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.compat.common.event.IBukkitListener;
import io.github.guillex7.explodeany.compat.manager.CompatibilityManager;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.listener.loadable.explosion.BaseConfigurableExplosionListener;

public abstract class BaseVanillaBlockExplosionListener extends BaseConfigurableExplosionListener {
    private IBukkitListener bukkitListener;

    public BaseVanillaBlockExplosionListener() {
        this.bukkitListener = CompatibilityManager.getInstance().getApi().getBukkitListenerUtils()
                .createBlockExplodeListener(this::onBlockExplode);
    }

    @Override
    public void unload() {
        this.bukkitListener.unload();
    }

    @Override
    public boolean shouldBeLoaded() {
        return this.bukkitListener.shouldBeLoaded();
    }

    @Override
    public Listener getEventListener() {
        return this.bukkitListener;
    }

    protected boolean isEventHandled(EanyBlockExplodeEvent event) {
        return event.getBlockLocation() != null && event.getBlockMaterial() != null
                && !ConfigurationManager.getInstance().getDisabledWorlds()
                        .contains(event.getBlockLocation().getWorld().getName());
    }

    protected abstract void onBlockExplode(EanyBlockExplodeEvent event);
}
