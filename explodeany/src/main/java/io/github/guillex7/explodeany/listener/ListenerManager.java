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
        if (instance == null) {
            instance = new ListenerManager();
        }
        return instance;
    }

    public List<LoadableListener> getRegisteredListeners() {
        return registeredListeners;
    }

    public void registerListener(LoadableListener explosionListener) {
        this.getRegisteredListeners().add(explosionListener);
    }

    public void loadAllListeners() {
        for (LoadableListener listener : this.getRegisteredListeners()) {
            if (!listener.shouldBeLoaded()) {
                continue;
            }

            listener.load();
            Bukkit.getServer().getPluginManager().registerEvents(listener, ExplodeAny.getInstance());
        }
    }

    public void unloadAllListeners() {
        for (LoadableListener listener : this.getRegisteredListeners()) {
            if (listener.shouldBeLoaded()) {
                listener.unload();
            }
        }
    }

    public void unregisterAllListeners() {
        this.getRegisteredListeners().clear();
    }
}
