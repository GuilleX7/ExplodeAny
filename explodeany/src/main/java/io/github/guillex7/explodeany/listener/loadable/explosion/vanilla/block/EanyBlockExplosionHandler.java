package io.github.guillex7.explodeany.listener.loadable.explosion.vanilla.block;

import io.github.guillex7.explodeany.compat.common.event.EanyBlockExplodeEvent;

public interface EanyBlockExplosionHandler {
    public boolean shouldBeLoaded();

    public void load();

    public boolean isEventHandled(EanyBlockExplodeEvent event);

    public void onBlockExplode(EanyBlockExplodeEvent event);

    public void unload();
}
