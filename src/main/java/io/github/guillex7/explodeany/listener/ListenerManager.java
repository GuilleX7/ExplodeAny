package io.github.guillex7.explodeany.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.listener.loadable.LoadableListener;

public class ListenerManager {
	private static ListenerManager instance;

	private Map<String, LoadableListener> registeredListeners;

	private ListenerManager() {
		registeredListeners = new HashMap<>();
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
		getRegisteredListeners().put(explosionListener.getName(), explosionListener);
	}

	public void loadAllListeners() {
		for (LoadableListener listener : getRegisteredListeners().values()) {
			if (!listener.shouldBeLoaded()) {
				continue;
			}
			Bukkit.getServer().getPluginManager().registerEvents(listener, ExplodeAny.getInstance());
			if (listener.isAdvisable()) {
				getPlugin().getLogger().info(String.format("Enabled support for %s", listener.getName()));
			}
		}
	}

	public void unloadAllListeners() {
		for (LoadableListener explosionListener : getRegisteredListeners().values()) {
			if (explosionListener.shouldBeLoaded()) {
				explosionListener.unload();
			}
		}
	}

	public void unregisterAllListeners() {
		getRegisteredListeners().clear();
	}

	private ExplodeAny getPlugin() {
		return ExplodeAny.getInstance();
	}
}
