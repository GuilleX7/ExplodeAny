package io.github.guillex7.explodeany.listener.loadable;

import org.bukkit.event.Listener;

public abstract class LoadableListener implements Listener {
    public abstract void unload();

    public abstract String getName();

    public abstract boolean shouldBeLoaded();

    public abstract boolean isAnnounceable();

    public Listener getEventListener() {
        return this;
    }
}
