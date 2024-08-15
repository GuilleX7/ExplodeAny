package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;
import io.github.guillex7.explodeany.explosion.ExplosionManager;

public class VanillaEntityExplosionListener implements LoadableListener {
    private List<VanillaEntityExplosionHandler> registeredHandlers;
    private List<VanillaEntityExplosionHandler> loadedHandlers;

    public VanillaEntityExplosionListener() {
        this.registeredHandlers = new ArrayList<>();
        this.loadedHandlers = new ArrayList<>();

        this.registerHandlers();
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    private void registerHandlers() {
        this.registeredHandlers.add(new MagicVanillaEntityExplosionHandler());
        this.registeredHandlers.add(new TCEVanillaEntityExplosionHandler());
        this.registeredHandlers.add(new RegularVanillaEntityExplosionHandler());
        this.registeredHandlers.add(new CustomVanillaEntityExplosionHandler());
    }

    private void loadHandlers() {
        for (VanillaEntityExplosionHandler handler : this.registeredHandlers) {
            if (handler.shouldBeLoaded()) {
                handler.load();
                this.loadedHandlers.add(handler);
            }
        }
    }

    @Override
    public void load() {
        this.registerHandlers();
        this.loadHandlers();
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        for (VanillaEntityExplosionHandler handler : this.loadedHandlers) {
            if (handler.isEventHandled(event)) {
                handler.onEntityExplode(event);
                break;
            }
        }
    }

    protected boolean isEventHandled(EntityExplodeEvent event) {
        return !event.isCancelled() && event.getEntity() != null
                && !ExplosionManager.getInstance().isEntitySpawnedByExplosionManager(event.getEntity())
                && !ConfigurationManager.getInstance().getDisabledWorlds()
                        .contains(event.getLocation().getWorld().getName());
    }

    private void unloadHandlers() {
        for (VanillaEntityExplosionHandler handler : this.loadedHandlers) {
            handler.unload();
        }

        this.loadedHandlers.clear();
    }

    private void unregisterHandlers() {
        this.registeredHandlers.clear();
    }

    @Override
    public void unload() {
        EntityExplodeEvent.getHandlerList().unregister(this);

        this.unloadHandlers();
        this.unregisterHandlers();
    }
}
