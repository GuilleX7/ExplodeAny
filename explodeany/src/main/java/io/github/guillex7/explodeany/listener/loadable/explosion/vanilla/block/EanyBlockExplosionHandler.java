package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;

public interface EanyBlockExplosionHandler {
    boolean shouldBeLoaded();

    void load();

    boolean isEventHandled(EanyBlockExplodeEvent event);

    void onBlockExplode(EanyBlockExplodeEvent event);

    void unload();
}
