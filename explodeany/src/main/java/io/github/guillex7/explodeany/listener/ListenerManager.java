package io.github.guillex7.explodeany.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.listener.loadable.LoadableListener;

public class ListenerManager {
    private static ListenerManager instance;

    private final Map<String, LoadableListener> registeredListeners;

    private ListenerManager() {
        this.registeredListeners = new HashMap<>();
    }

    public static ListenerManager getInstance() {
        if (instance == null) {
            instance = new ListenerManager();
        }
        return instance;
    }

    public Map<String, LoadableListener> getRegisteredListeners() {
        return registeredListeners;
    }

    public void registerListener(LoadableListener explosionListener) {
        this.getRegisteredListeners().put(explosionListener.getName(), explosionListener);
    }

    public void loadAllListeners() {
        for (LoadableListener listener : this.getRegisteredListeners().values()) {
            if (!listener.shouldBeLoaded()) {
                continue;
            }
            Bukkit.getServer().getPluginManager().registerEvents(listener.getEventListener(), ExplodeAny.getInstance());
            if (listener.isAnnounceable()) {
                this.getPlugin().getLogger().info(String.format("Enabled support for %s", listener.getName()));
            }
        }
    }

    public void unloadAllListeners() {
        for (LoadableListener explosionListener : this.getRegisteredListeners().values()) {
            if (explosionListener.shouldBeLoaded()) {
                explosionListener.unload();
            }
        }
    }

    public void unregisterAllListeners() {
        this.getRegisteredListeners().clear();
    }

    private ExplodeAny getPlugin() {
        return ExplodeAny.getInstance();
    }
}
