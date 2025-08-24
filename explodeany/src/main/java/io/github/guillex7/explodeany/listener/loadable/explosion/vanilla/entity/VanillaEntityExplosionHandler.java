package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import org.bukkit.event.entity.EntityExplodeEvent;

public interface VanillaEntityExplosionHandler {
    boolean shouldBeLoaded();

    void load();

    boolean isEventHandled(EntityExplodeEvent event);

    void onEntityExplode(EntityExplodeEvent event);

    void unload();
}
