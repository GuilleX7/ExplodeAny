package io.github.guillex7.explodeany.compat.v1_8.api;

import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class CBlockExplodeListener implements LoadableListener {
    @Override
    public final boolean shouldBeLoaded() {
        return false;
    }

    @Override
    public final void unload() {
        // Do nothing
    }

    @Override
    public void load() {
        // Do nothing
    }
}
