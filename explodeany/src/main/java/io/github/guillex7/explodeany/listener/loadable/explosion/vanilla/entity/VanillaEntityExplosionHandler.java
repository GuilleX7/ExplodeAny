package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.entity;

import org.bukkit.event.entity.EntityExplodeEvent;

public interface VanillaEntityExplosionHandler {
    public boolean shouldBeLoaded();

    public void load();

    public boolean isEventHandled(EntityExplodeEvent event);

    public void onEntityExplode(EntityExplodeEvent event);

    public void unload();
}
