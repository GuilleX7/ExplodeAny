package io.github.guillex7.explodeany.compat.common;

import org.bukkit.event.Listener;

public interface LoadableListener extends Listener {
    boolean shouldBeLoaded();

    void load();

    void unload();
}
