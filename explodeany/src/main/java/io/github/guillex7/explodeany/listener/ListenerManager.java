package io.github.guillex7.explodeany.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.compat.common.listener.LoadableListener;

public class ListenerManager {
    private static ListenerManager instance;

    private final List<LoadableListener> registeredListeners;

    private ListenerManager() {
        this.registeredListeners = new ArrayList<>();
    }

    public static ListenerManager getInstance() {
        if (ListenerManager.instance == null) {
            ListenerManager.instance = new ListenerManager();
        }
        return ListenerManager.instance;
    }

    public List<LoadableListener> getRegisteredListeners() {
        return this.registeredListeners;
    }

    public void registerListener(final LoadableListener explosionListener) {
        this.getRegisteredListeners().add(explosionListener);
    }

    public void loadAllListeners() {
        for (final LoadableListener listener : this.getRegisteredListeners()) {
            if (!listener.shouldBeLoaded()) {
                continue;
            }

            listener.load();
            Bukkit.getServer().getPluginManager().registerEvents(listener, ExplodeAny.getInstance());
        }
    }

    public void unloadAllListeners() {
        for (final LoadableListener listener : this.getRegisteredListeners()) {
            if (listener.shouldBeLoaded()) {
                listener.unload();
            }
        }
    }

    public void unregisterAllListeners() {
        this.getRegisteredListeners().clear();
    }
}
