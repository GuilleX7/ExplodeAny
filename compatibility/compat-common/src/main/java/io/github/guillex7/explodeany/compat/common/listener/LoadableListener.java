package io.github.guillex7.explodeany.compat.common.listener;

import org.bukkit.event.Listener;

public interface LoadableListener extends Listener {
    boolean shouldBeLoaded();

    void load();

    void unload();
}
