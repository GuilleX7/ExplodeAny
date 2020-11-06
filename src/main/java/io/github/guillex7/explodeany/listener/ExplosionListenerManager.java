package io.github.guillex7.explodeany.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import io.github.guillex7.explodeany.ExplodeAny;
import io.github.guillex7.explodeany.listener.loadable.LoadableExplosionListener;

public class ExplosionListenerManager {
	private static ExplosionListenerManager instance;
	
	private List<LoadableExplosionListener> explosionListeners = new ArrayList<LoadableExplosionListener>();
	
	private ExplosionListenerManager() {}
	
	public static ExplosionListenerManager getInstance() {
		if (instance == null) {
			instance = new ExplosionListenerManager();
		}
		return instance;
	}
	
	public List<LoadableExplosionListener> getRegisteredExplosionListeners() {
		return explosionListeners;
	}
	
	public void registerExplosionListener(LoadableExplosionListener explosionListener) {
		getRegisteredExplosionListeners().add(explosionListener);
	}
	
	public void unloadAllExplosionListeners() {
		for (LoadableExplosionListener explosionListener : getRegisteredExplosionListeners()) {
			if (explosionListener.shouldBeLoaded()) {
				explosionListener.unload();
			}
		}
		getRegisteredExplosionListeners().clear();
	}
	
	public void loadAllExplosionListeners() {
		for (LoadableExplosionListener explosionListener : getRegisteredExplosionListeners()) {
			if (explosionListener.shouldBeLoaded()) {
				Bukkit.getServer().getPluginManager().registerEvents(explosionListener, ExplodeAny.getInstance());
				getLogger().log(Level.INFO, String.format("Enabled support for %s", explosionListener.getName()));
			}
		}
	}
	
	private Logger getLogger() {
		return ExplodeAny.getInstance().getLogger();
	}
}
