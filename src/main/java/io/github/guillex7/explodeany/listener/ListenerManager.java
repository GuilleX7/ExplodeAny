package io.github.guillex7.explodeany.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.listener.loadable.LoadableListener;

public class ListenerManager {
	private static ListenerManager instance;
	
	private List<LoadableListener> registeredListeners;
	
	private ListenerManager() {
		registeredListeners = new ArrayList<LoadableListener>();
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
		getRegisteredListeners().add(explosionListener);
	}
	
	public void unloadAllListeners() {
		for (LoadableListener explosionListener : getRegisteredListeners()) {
			if (explosionListener.shouldBeLoaded()) {
				explosionListener.unload();
			}
		}
		getRegisteredListeners().clear();
	}
	
	public void loadAllListeners() {
		for (LoadableListener listener : getRegisteredListeners()) {
			if (!listener.shouldBeLoaded()) {
				continue;
			}
			Bukkit.getServer().getPluginManager().registerEvents(listener, ExplodeAny.getInstance());
			if (listener.isAdvisable()) {
				getLogger().log(Level.INFO, String.format("Enabled support for %s", listener.getName()));
			}
		}
	}
	
	private Logger getLogger() {
		return ExplodeAny.getInstance().getLogger();
	}
}
