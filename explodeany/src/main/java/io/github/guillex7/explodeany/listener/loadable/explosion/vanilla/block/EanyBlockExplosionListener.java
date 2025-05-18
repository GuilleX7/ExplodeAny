package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;
import io.github.guillex7.explodeany.configuration.ConfigurationManager;

public class EanyBlockExplosionListener implements LoadableListener {
    private final List<EanyBlockExplosionHandler> registeredHandlers;
    private final List<EanyBlockExplosionHandler> loadedHandlers;

    public EanyBlockExplosionListener() {
        this.registeredHandlers = new ArrayList<>();
        this.loadedHandlers = new ArrayList<>();

        this.registerHandlers();
    }

    @Override
    public boolean shouldBeLoaded() {
        return true;
    }

    private void registerHandlers() {
        this.registeredHandlers.add(new VanillaEanyBlockExplosionListener());
        this.registeredHandlers.add(new CustomEanyBlockExplosionListener());
    }

    private void loadHandlers() {
        for (EanyBlockExplosionHandler handler : this.registeredHandlers) {
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
    public void onEanyBlockExplode(EanyBlockExplodeEvent event) {
        if (!this.isEventHandled(event)) {
            return;
        }

        for (EanyBlockExplosionHandler handler : this.loadedHandlers) {
            if (handler.isEventHandled(event)) {
                handler.onBlockExplode(event);
                break;
            }
        }
    }

    protected boolean isEventHandled(EanyBlockExplodeEvent event) {
        return !event.isCancelled() && event.getBlockLocation() != null
                && !ConfigurationManager.getInstance().getDisabledWorlds()
                        .contains(event.getBlockLocation().getWorld().getName());
    }

    private void unloadHandlers() {
        for (EanyBlockExplosionHandler handler : this.loadedHandlers) {
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
