package io.github.guillex7.explodeany.compat.v1_8.api;

import io.github.guillex7.explodeany.compat.common.event.IBukkitListener;

public class CBlockExplodeListener implements IBukkitListener {
    @Override
    public final boolean shouldBeLoaded() {
        return false;
    }

    @Override
    public final void unload() {
        // Do nothing
    }
}
