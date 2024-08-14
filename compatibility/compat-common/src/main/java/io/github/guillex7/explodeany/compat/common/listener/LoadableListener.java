package io.github.guillex7.explodeany.compat.common.listener;

import org.bukkit.event.Listener;

public interface LoadableListener extends Listener {
    public abstract boolean shouldBeLoaded();

    public abstract void load();

    public abstract void unload();
}
